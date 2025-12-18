package me.hardstyl3r.nbd;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import me.hardstyl3r.nbd.db.CassandraSetup;
import me.hardstyl3r.nbd.mappers.LibraryMapper;
import me.hardstyl3r.nbd.mappers.LibraryMapperBuilder;
import me.hardstyl3r.nbd.managers.AllocationManager;
import me.hardstyl3r.nbd.managers.ClientManager;
import me.hardstyl3r.nbd.managers.ResourceManager;
import me.hardstyl3r.nbd.repositories.AllocationRepository;
import me.hardstyl3r.nbd.repositories.ClientRepository;
import me.hardstyl3r.nbd.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.net.InetSocketAddress;
import java.time.Duration;

public abstract class BaseDatabaseTest {

    protected static CqlSession session;
    protected static LibraryMapper mapper;
    protected static CqlIdentifier keyspaceId;

    protected static ClientManager clientManager;
    protected static ResourceManager resourceManager;
    protected static AllocationManager allocationManager;

    private static boolean initialized = false;

    @BeforeAll
    public static void globalSetup() {
        if (!initialized) {
            DriverConfigLoader loader = DriverConfigLoader.programmaticBuilder()
                    .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(10))
                    .build();

            session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                    .addContactPoint(new InetSocketAddress("127.0.0.1", 9043))
                    .withAuthCredentials("cassandra", "cassandra")
                    .withLocalDatacenter("datacenter1")
                    .withConfigLoader(loader)
                    .build();

            CassandraSetup.createKeyspaceAndTables(session);

            keyspaceId = CqlIdentifier.fromCql("library");
            mapper = new LibraryMapperBuilder(session).build();

            ClientRepository clientRepository = new ClientRepository(mapper.clientDao(keyspaceId));
            ResourceRepository resourceRepository = new ResourceRepository(mapper.resourceDao(keyspaceId));
            AllocationRepository allocationRepository = new AllocationRepository(mapper.allocationDao(keyspaceId));

            clientManager = new ClientManager(clientRepository);
            resourceManager = new ResourceManager(resourceRepository);
            allocationManager = new AllocationManager(allocationRepository, clientRepository, resourceRepository);


            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (session != null) {
                    session.close();
                    System.out.println("Global teardown complete.");
                }
            }));

            initialized = true;
        }
    }

    @BeforeEach
    public void cleanTables() {
        session.execute("TRUNCATE library.client");
        session.execute("TRUNCATE library.resource");
        session.execute("TRUNCATE library.allocations_by_client");
    }
}
