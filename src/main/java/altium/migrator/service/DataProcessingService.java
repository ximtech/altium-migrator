package altium.migrator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessingService implements CommandLineRunner {

    private final GitRepositoryService gitRepositoryService;
    private final LiquibaseMigrationService migrationService;

    @Override
    public void run(String... args) {
        gitRepositoryService.cloneRepositoryWithChangelog();
        migrationService.processDataMigration();
    }
}
