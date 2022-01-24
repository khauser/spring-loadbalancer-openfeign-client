package org.test.openfeign;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
// annotation inheritance works only with classes not with interfaces
public abstract class TestAnnotations
{


    // // start a testcontainer (for iste-execution-db) that is used for all
    // tests
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("iste-execution-db").withUsername("iste-execution-db")
                    .withPassword("iste-execution-db");
    //
    // // start a testcontainer (for iste-artifact-db)
    // public static PostgreSQLContainer<?> postgresContainerArtifact = new
    // PostgreSQLContainer<>("postgres")
    // .withDatabaseName("iste-artifact-db").withUsername("iste-artifact-db")
    // .withPassword("iste-artifact-db").withNetwork(network)
    // .withNetworkAliases("postgresContainerArtifact");

    // start a testcontainer (for artifact service)
    @Container
    public static GenericContainer<?> artifactService = new GenericContainer<>(
                    "klhauser/spring-loadbalancer-openfeign-server:latest")
                    .waitingFor(Wait.forLogMessage(".*Started ArtifactApplication.*", 1)).withExposedPorts(2020);

    static
    {
        //postgresContainer.start();
        // postgresContainerArtifact.start();
        //
        // log.info("postgresContainerArtifact IP:",
        // postgresContainerArtifact.getHost());
        //
        // artifactService.withEnv("POSTGRES_HOST",
        // "postgresContainerArtifact");
        // // artifactService.withEnv("POSTGRES_PORT",
        // // String.valueOf(postgresContainerArtifact.getMappedPort(5432)));
        artifactService.start();

        String artifactServiceUrl = "http://" + artifactService.getHost() + ":" + artifactService.getFirstMappedPort();
        System.setProperty("ISTE_ARTIFACT_URL", artifactServiceUrl);
        log.info("ISTE_ARTIFACT_URL was set to: {}", artifactServiceUrl);
    }

    @BeforeEach
    public void setupConfig()
    {
        //String artifactServiceUrl = "http://" + artifactService.getHost() + ":" + artifactService.getFirstMappedPort();
        //System.setProperty("ISTE_ARTIFACT_URL", artifactServiceUrl);
        //log.info("ISTE_ARTIFACT_URL was set to: {}", artifactServiceUrl);
//        String propertyName = "spring.datasource.url";
//        String connection = postgresContainer.getJdbcUrl();
//        System.setProperty(propertyName, connection);
        //
        // String propertyNameArtifact = "spring.datasource.url-artifact";
        // String connectionArtifact = postgresContainerArtifact.getJdbcUrl();
        // System.setProperty(propertyNameArtifact, connectionArtifact);
    }
}
