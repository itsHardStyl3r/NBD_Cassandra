package me.hardstyl3r.nbd;

import me.hardstyl3r.nbd.objects.Allocation;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AllocationCrudTest extends BaseDatabaseTest {

    @Test
    void testAllocationCrud() {
        UUID clientId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();

        clientManager.addClient(clientId, "Aleksander", "Gencel");
        resourceManager.addMagazine(resourceId, "Komputer Świat", 1);

        Allocation alloc = allocationManager.allocateResource(clientId, resourceId);
        assertNotNull(alloc);

        Allocation fetched = allocationManager.findAllocation(clientId, alloc.getId());
        assertNotNull(fetched);
        assertEquals("Komputer Świat", fetched.getResourceTitle());

        fetched.setResourceTitle("Komputer Świat - Special Edition");
        allocationManager.updateAllocation(fetched);

        Allocation updated = allocationManager.findAllocation(clientId, alloc.getId());
        assertEquals("Komputer Świat - Special Edition", updated.getResourceTitle());

        allocationManager.deallocateResource(updated);
        assertNull(allocationManager.findAllocation(clientId, alloc.getId()));
    }

    @Test
    void testAllocationList() {
        UUID clientId = UUID.randomUUID();
        UUID resource1Id = UUID.randomUUID();
        UUID resource2Id = UUID.randomUUID();

        clientManager.addClient(clientId, "Jan", "Kowalski");
        resourceManager.addBook(resource1Id, "Book 1", "Author 1");
        resourceManager.addBook(resource2Id, "Book 2", "Author 2");

        Allocation alloc1 = allocationManager.allocateResource(clientId, resource1Id);
        Allocation alloc2 = allocationManager.allocateResource(clientId, resource2Id);

        var allocations = allocationManager.findAllocationsForClient(clientId);

        int count = 0;
        for (Allocation a : allocations) {
            assertEquals(clientId, a.getClientId());
            count++;
        }

        assertEquals(2, count, "Should find exactly 2 allocations for this client");

        allocationManager.deallocateResource(alloc1);
        allocationManager.deallocateResource(alloc2);
    }
}
