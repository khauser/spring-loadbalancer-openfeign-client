package org.test.openfeign;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
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
    public static Network network = Network.newNetwork();
    // // start a testcontainer (for iste-execution-db) that is used for all
    // tests
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("iste-execution-db").withUsername("iste-execution-db")
                    .withPassword("iste-execution-db").withNetwork(network);
    //
    // // start a testcontainer (for iste-artifact-db)
    @Container
    public static PostgreSQLContainer<?> postgresContainerArtifact = new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("iste-artifact-db").withUsername("iste-artifact-db")
                    .withPassword("iste-artifact-db").withNetwork(network).withNetworkAliases("postgresContainerArtifact");

    // start a testcontainer (for artifact service)
    @Container
    public static GenericContainer<?> artifactService = new GenericContainer<>(
                    "ishisteacr.azurecr.io/iste/iste-artifact:latest")
                                    .waitingFor(Wait.forLogMessage(".*Started ISTEArtifactApplication.*", 1))
                                    .withExposedPorts(2020).withNetwork(network);

    static
    {
        // postgresContainer.start();
        postgresContainerArtifact.start();
        log.info("postgresContainerArtifact IP:", postgresContainerArtifact.getHost());
        artifactService.withEnv("POSTGRES_HOST", "postgresContainerArtifact");
        artifactService.withEnv("POSTGRES_PORT", "5432");
        artifactService.start();

        String artifactServiceUrl = "http://" + artifactService.getHost() + ":" + artifactService.getFirstMappedPort();
        System.setProperty("ISTE_ARTIFACT_URL", artifactServiceUrl);
        log.info("ISTE_ARTIFACT_URL was set to: {}", artifactServiceUrl);
    }

    @BeforeEach
    public void setupConfig()
    {
        // String artifactServiceUrl = "http://" + artifactService.getHost() +
        // ":" + artifactService.getFirstMappedPort();
        // System.setProperty("ISTE_ARTIFACT_URL", artifactServiceUrl);
        // log.info("ISTE_ARTIFACT_URL was set to: {}", artifactServiceUrl);
        // String propertyName = "spring.datasource.url";
        // String connection = postgresContainer.getJdbcUrl();
        // System.setProperty(propertyName, connection);
        //
        // String propertyNameArtifact = "spring.datasource.url-artifact";
        // String connectionArtifact = postgresContainerArtifact.getJdbcUrl();
        // System.setProperty(propertyNameArtifact, connectionArtifact);
    }
}
