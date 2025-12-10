package me.hardstyl3r.nbd;

import me.hardstyl3r.nbd.daos.ClientDao;
import me.hardstyl3r.nbd.objects.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ClientCrudTest extends BaseDatabaseTest {

    private ClientDao clientDao;

    @BeforeEach
    void initDao() {
        clientDao = mapper.clientDao(keyspaceId);
    }

    @Test
    void testClientCrud() {
        UUID id = UUID.randomUUID();
        Client client = new Client(id, "Aleksander", "Gencel");
        clientDao.save(client);

        Client fetched = clientDao.findById(id);
        assertNotNull(fetched);
        assertEquals("Aleksander", fetched.getFirstName());
        assertEquals("Gencel", fetched.getLastName());

        fetched.setLastName("Kwaśniewski");
        clientDao.update(fetched);

        Client updated = clientDao.findById(id);
        assertEquals("Kwaśniewski", updated.getLastName());

        clientDao.delete(updated);
        assertNull(clientDao.findById(id));
    }
}