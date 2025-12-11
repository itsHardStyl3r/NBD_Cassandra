package me.hardstyl3r.nbd;

import me.hardstyl3r.nbd.daos.AllocationDao;
import me.hardstyl3r.nbd.objects.Allocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AllocationCrudTest extends BaseDatabaseTest {

    private AllocationDao allocationDao;

    @BeforeEach
    void initDao() {
        allocationDao = mapper.allocationDao(keyspaceId);
    }

    @Test
    void testAllocationCrud() {
        UUID clientId = UUID.randomUUID();
        UUID allocationId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();

        Allocation alloc = new Allocation(
                clientId,
                allocationId,
                resourceId,
                "Komputer Świat",
                "Gencel",
                Instant.now()
        );

        allocationDao.save(alloc);

        Allocation fetched = allocationDao.findById(clientId, allocationId);
        assertNotNull(fetched);
        assertEquals("Komputer Świat", fetched.getResourceTitle());

        fetched.setResourceTitle("Komputer Świat - Special Edition");
        allocationDao.update(fetched);

        Allocation updated = allocationDao.findById(clientId, allocationId);
        assertEquals("Komputer Świat - Special Edition", updated.getResourceTitle());

        allocationDao.delete(updated);
        assertNull(allocationDao.findById(clientId, allocationId));
    }

    @Test
    void testAllocationList() {
        UUID clientId = UUID.randomUUID();
        Allocation alloc1 = new Allocation(clientId, UUID.randomUUID(), UUID.randomUUID(), "Book 1", "Kowalski", Instant.now());
        Allocation alloc2 = new Allocation(clientId, UUID.randomUUID(), UUID.randomUUID(), "Book 2", "Kowalski", Instant.now());

        allocationDao.save(alloc1);
        allocationDao.save(alloc2);

        var allocations = allocationDao.findAllByClientId(clientId);

        int count = 0;
        for (Allocation a : allocations) {
            assertEquals(clientId, a.getClientId());
            count++;
        }

        assertEquals(2, count, "Should find exactly 2 allocations for this client");

        // Cleanup
        allocationDao.delete(alloc1);
        allocationDao.delete(alloc2);
    }
}