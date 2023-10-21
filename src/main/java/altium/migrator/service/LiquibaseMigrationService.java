package altium.migrator.service;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiquibaseMigrationService {
    
    @Value("${db.changelog.file.name}")
    private String dbChangelogFileName;

    @Value("${migration.root.folder}/${git.repository.directory.name}")
    private String migrationRootFolder;

    @Value("${db.schema.name}")
    private String defaultSchemaName;

    private final DataSource dataSource;
    
    @SneakyThrows
    public void processDataMigration() {
        Liquibase liquibase = null;
        log.info("Started database migration");
        File changelogDirectory = new File(migrationRootFolder);
        try (Connection connection = this.dataSource.getConnection()) {
            DirectoryResourceAccessor resourceAccessor = new DirectoryResourceAccessor(changelogDirectory);
            PostgresDatabase postgresDatabase = new PostgresDatabase();
            postgresDatabase.setConnection(new JdbcConnection(connection));
            postgresDatabase.setDefaultSchemaName(defaultSchemaName);
            liquibase = new Liquibase(dbChangelogFileName, resourceAccessor, postgresDatabase);
            Contexts contexts = new Contexts();
            liquibase.update(contexts);
            connection.commit();
            
        } catch (SQLException | LiquibaseException e) {
            log.error("Error during liquibase execution: [{}]", e.getMessage());
            if (liquibase != null) {
                try {
                    liquibase.forceReleaseLocks();
                } catch (LiquibaseException ignore) {}
            }
            throw new RuntimeException(e);
        }
        log.info("Migration successfully finished");
    }

}
