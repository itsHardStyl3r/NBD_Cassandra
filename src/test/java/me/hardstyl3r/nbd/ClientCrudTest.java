package me.hardstyl3r.nbd;

import me.hardstyl3r.nbd.objects.Client;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ClientCrudTest extends BaseDatabaseTest {

    @Test
    void testClientCrud() {
        UUID id = UUID.randomUUID();
        clientManager.addClient(id, "Aleksander", "Gencel");

        Client fetched = clientManager.findClient(id);
        assertNotNull(fetched);
        assertEquals("Aleksander", fetched.getFirstName());
        assertEquals("Gencel", fetched.getLastName());

        fetched.setLastName("Kwaśniewski");
        clientManager.updateClient(fetched);

        Client updated = clientManager.findClient(id);
        assertEquals("Kwaśniewski", updated.getLastName());

        clientManager.removeClient(updated);
        assertNull(clientManager.findClient(id));
    }
}
