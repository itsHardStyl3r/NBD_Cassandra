package me.hardstyl3r.nbd;

import me.hardstyl3r.nbd.daos.ResourceDao;
import me.hardstyl3r.nbd.objects.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ResourceCrudTest extends BaseDatabaseTest {

    private ResourceDao resourceDao;

    @BeforeEach
    void initDao() {
        resourceDao = mapper.resourceDao(keyspaceId);
    }

    @Test
    void testBookCrud() {
        UUID id = UUID.randomUUID();
        Resource book = Resource.createBook(id, "The Murder of Roger Ackroyd", "Agatha Christie");
        resourceDao.save(book);

        Resource fetched = resourceDao.findById(id);
        assertNotNull(fetched);
        assertEquals("BOOK", fetched.getType());
        assertEquals("Agatha Christie", fetched.getAuthor());

        fetched.setTitle("Hercule Poirot's Christmas");
        resourceDao.update(fetched);

        Resource updated = resourceDao.findById(id);
        assertEquals("Hercule Poirot's Christmas", updated.getTitle());

        resourceDao.delete(updated);
        assertNull(resourceDao.findById(id));
    }

    @Test
    void testMagazineCrud() {
        UUID id = UUID.randomUUID();
        Resource magazine = Resource.createMagazine(id, "Komputer Świat", 1);
        resourceDao.save(magazine);

        Resource fetched = resourceDao.findById(id);
        assertNotNull(fetched);
        assertEquals("MAGAZINE", fetched.getType());
        assertEquals(1, fetched.getIssueNumber());

        fetched.setIssueNumber(2);
        resourceDao.update(fetched);

        Resource updated = resourceDao.findById(id);
        assertEquals(2, updated.getIssueNumber());

        resourceDao.delete(updated);
        assertNull(resourceDao.findById(id));
    }
}