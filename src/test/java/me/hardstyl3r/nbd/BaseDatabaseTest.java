package me.hardstyl3r.nbd;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import me.hardstyl3r.nbd.db.CassandraSetup;
import me.hardstyl3r.nbd.mappers.LibraryMapper;
import me.hardstyl3r.nbd.mappers.LibraryMapperBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.net.InetSocketAddress;
import java.time.Duration;

public abstract class BaseDatabaseTest {

    protected static CqlSession session;
    protected static LibraryMapper mapper;
    protected static CqlIdentifier keyspaceId;

    private static boolean initialized = false;

    @BeforeAll
    public static void globalSetup() {
        if (!initialized) {
            DriverConfigLoader loader = DriverConfigLoader.programmaticBuilder()
                    .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(10))
                    .build();

            session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                    .withLocalDatacenter("datacenter1")
                    .withConfigLoader(loader)
                    .build();

            CassandraSetup.createKeyspaceAndTables(session);

            keyspaceId = CqlIdentifier.fromCql("library");
            mapper = new LibraryMapperBuilder(session).build();

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