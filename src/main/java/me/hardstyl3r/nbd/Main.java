package me.hardstyl3r.nbd;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import me.hardstyl3r.nbd.daos.*;
import me.hardstyl3r.nbd.db.CassandraSetup;
import me.hardstyl3r.nbd.mappers.*;
import me.hardstyl3r.nbd.objects.*;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1")
                .build()) {

            CassandraSetup.createKeyspaceAndTables(session);

            CqlIdentifier keyspace = CqlIdentifier.fromCql("library");
            LibraryMapper mapper = new LibraryMapperBuilder(session).build();

            ClientDao clientDao = mapper.clientDao(keyspace);
            ResourceDao resourceDao = mapper.resourceDao(keyspace);
            AllocationDao allocationDao = mapper.allocationDao(keyspace);

            System.out.println("--- CLIENT CRUD ---");
            UUID clientId = UUID.randomUUID();
            Client client = new Client(clientId, "Jan", "Kowalski");

            clientDao.save(client);
            System.out.println("Client saved.");

            Client fetchedClient = clientDao.findById(clientId);
            System.out.println("Client read: " + fetchedClient.getLastName());

            fetchedClient.setLastName("Nowak");
            clientDao.update(fetchedClient);
            System.out.println("Client updated.");

            clientDao.delete(fetchedClient);
            System.out.println("Client deleted.");


            System.out.println("--- BOOK CRUD ---");
            UUID bookId = UUID.randomUUID();
            Resource book = Resource.createBook(bookId, "The Murder of Roger Ackroyd", "Agatha Christie");

            resourceDao.save(book);
            System.out.println("Book saved.");

            Resource fetchedBook = resourceDao.findById(bookId);
            System.out.println("Book read: " + fetchedBook.getTitle());

            fetchedBook.setTitle("Hercule Poirot's Christmas");
            resourceDao.update(fetchedBook);
            System.out.println("Book updated.");

            resourceDao.delete(fetchedBook);
            System.out.println("Book deleted.");


            System.out.println("--- MAGAZINE CRUD ---");
            UUID magId = UUID.randomUUID();
            Resource magazine = Resource.createMagazine(magId, "Komputer Swiat", 1);

            resourceDao.save(magazine);
            System.out.println("Magazine saved.");

            Resource fetchedMag = resourceDao.findById(magId);
            System.out.println("Magazine read: " + fetchedMag.getTitle() + " #" + fetchedMag.getIssueNumber());

            fetchedMag.setIssueNumber(2);
            resourceDao.update(fetchedMag);
            System.out.println("Magazine updated.");

            resourceDao.delete(fetchedMag);
            System.out.println("Magazine deleted.");


            System.out.println("--- ALLOCATION CRUD ---");
            UUID allocId = UUID.randomUUID();
            Allocation alloc = new Allocation(
                    UUID.randomUUID(),
                    allocId,
                    UUID.randomUUID(),
                    "Komputer Swiat",
                    "Kowalski",
                    Instant.now()
            );

            allocationDao.save(alloc);
            System.out.println("Allocation saved.");

            Allocation fetchedAlloc = allocationDao.findById(alloc.getClientId(), allocId);
            System.out.println("Allocation read: " + fetchedAlloc.getResourceTitle());

            fetchedAlloc.setResourceTitle("Komputer Swiat - Special Edition");
            allocationDao.update(fetchedAlloc);
            System.out.println("Allocation updated.");

            allocationDao.delete(fetchedAlloc);
            System.out.println("Allocation deleted.");
        }
    }
}
