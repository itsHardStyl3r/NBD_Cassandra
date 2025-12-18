package me.hardstyl3r.nbd;

import me.hardstyl3r.nbd.objects.Resource;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ResourceCrudTest extends BaseDatabaseTest {

    @Test
    void testBookCrud() {
        UUID id = UUID.randomUUID();
        resourceManager.addBook(id, "The Murder of Roger Ackroyd", "Agatha Christie");

        Resource fetched = resourceManager.findResource(id);
        assertNotNull(fetched);
        assertEquals("BOOK", fetched.getType());
        assertEquals("Agatha Christie", fetched.getAuthor());

        fetched.setTitle("Hercule Poirot's Christmas");
        resourceManager.updateResource(fetched);

        Resource updated = resourceManager.findResource(id);
        assertEquals("Hercule Poirot's Christmas", updated.getTitle());

        resourceManager.removeResource(updated);
        assertNull(resourceManager.findResource(id));
    }

    @Test
    void testMagazineCrud() {
        UUID id = UUID.randomUUID();
        resourceManager.addMagazine(id, "Komputer Świat", 1);

        Resource fetched = resourceManager.findResource(id);
        assertNotNull(fetched);
        assertEquals("MAGAZINE", fetched.getType());
        assertEquals(1, fetched.getIssueNumber());

        fetched.setIssueNumber(2);
        resourceManager.updateResource(fetched);

        Resource updated = resourceManager.findResource(id);
        assertEquals(2, updated.getIssueNumber());

        resourceManager.removeResource(updated);
        assertNull(resourceManager.findResource(id));
    }
}
