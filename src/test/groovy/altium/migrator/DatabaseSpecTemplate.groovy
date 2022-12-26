package altium.migrator

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Slf4j
@Testcontainers
@SpringBootTest
@ActiveProfiles(["it-test"])
@ContextConfiguration(initializers = DataSourceInitializer)
class DatabaseSpecTemplate extends Specification {

    static final String POSTGRES_TEST_IMAGE = "postgres:12.13-alpine"
    static final String POSTGRES_DB_NAME = "test-altium-components"
    static final String POSTGRES_USERNAME = "postgres"
    static final String POSTGRES_PASSWORD = "postgres"
    
    @Shared
    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(POSTGRES_TEST_IMAGE)
            .withDatabaseName(POSTGRES_DB_NAME)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD)


    static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        void initialize(ConfigurableApplicationContext applicationContext) {
            POSTGRE_SQL_CONTAINER.start()
            log.info("Database started with url: [{}]", POSTGRE_SQL_CONTAINER.getJdbcUrl())
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.test.database.replace=none",
                    "spring.datasource.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRE_SQL_CONTAINER.getPassword()
            )
        }
    }
}
