package altium.migrator.service;

import liquibase.Contexts;
import liquibase.Liquibase;
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

    private final DataSource dataSource;
    
    @SneakyThrows
    public void processDataMigration() {
        Liquibase liquibase = null;
        log.info("Started database migration");
        File changelogDirectory = new File(migrationRootFolder);
        try (Connection connection = this.dataSource.getConnection()) {
            DirectoryResourceAccessor resourceAccessor = new DirectoryResourceAccessor(changelogDirectory);
            liquibase = new Liquibase(dbChangelogFileName, resourceAccessor, new JdbcConnection(connection));
            Contexts contexts = new Contexts();
            liquibase.update(contexts);
            connection.commit();
            
        } catch (SQLException | LiquibaseException e) {
            if (liquibase != null) {
                try {
                    liquibase.forceReleaseLocks();
                } catch (LiquibaseException ignore) {}
            }
            throw new RuntimeException("Error during liquibase execution", e);
        }
        log.info("Migration successfully finished");
    }

}
