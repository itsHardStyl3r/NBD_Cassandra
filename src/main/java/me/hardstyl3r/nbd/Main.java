package me.hardstyl3r.nbd;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import me.hardstyl3r.nbd.daos.ClientDao;
import me.hardstyl3r.nbd.db.CassandraSetup;
import me.hardstyl3r.nbd.mappers.LibraryMapper;
import me.hardstyl3r.nbd.mappers.LibraryMapperBuilder;
import me.hardstyl3r.nbd.objects.Client;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1")
                .build()) {

            CassandraSetup.createKeyspaceAndTables(session);

            CqlIdentifier keyspaceId = CqlIdentifier.fromCql("library");
            LibraryMapper mapper = new LibraryMapperBuilder(session).build();
            ClientDao clientDao = mapper.clientDao(keyspaceId);

            // Create
            UUID id = UUID.randomUUID();
            Client client = new Client(id, "Aleksander", "Gencel");
            clientDao.save(client);
            System.out.println("[C] Created client: " + id);

            // Read
            Client retrieved = clientDao.findById(id);
            System.out.println("[R] Read: " + retrieved.getFirstName() + " " + retrieved.getLastName());

            // Update
            retrieved.setLastName("Kwaśniewski");
            clientDao.update(retrieved);
            System.out.println("[U] Updated last name to 'Kwaśniewski'.");

            Client updatedClient = clientDao.findById(id);
            System.out.println("[R] Read: " + updatedClient.getFirstName() + " " + updatedClient.getLastName());

            // Delete
            clientDao.delete(updatedClient);
            System.out.println("[D] Deleted client.");
            Client deletedClient = clientDao.findById(id);
            if (deletedClient != null) {
                System.out.println("Failed: client still exists!");
            }
        }
    }
}
