package altium.migrator.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigrationCommandRunner implements CommandLineRunner {
    
    private final GitRepositoryService gitRepositoryService;
    private final LiquibaseMigrationService migrationService;

    @Value("${migration.root.folder}")
    private String destinationPath;
    
    @Override
    public void run(String... args) {
        checkDestinationFolder();
        gitRepositoryService.cloneRepositoryWithChangelog();
        migrationService.processDataMigration();
    }

    @SneakyThrows
    private void checkDestinationFolder() {
        File destinationFolder = new File(destinationPath);
        if (!destinationFolder.exists()) {
            log.info("Database migration directory not found: {}. Creating new one.", destinationPath);
            if (!destinationFolder.mkdirs()) {
                log.error("Failed to create directory: {}", destinationFolder);
            }
        }
    }
}
