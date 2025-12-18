package me.hardstyl3r.nbd.managers;

import com.datastax.oss.driver.api.core.PagingIterable;
import me.hardstyl3r.nbd.objects.Allocation;
import me.hardstyl3r.nbd.objects.Client;
import me.hardstyl3r.nbd.objects.Resource;
import me.hardstyl3r.nbd.repositories.AllocationRepository;
import me.hardstyl3r.nbd.repositories.ClientRepository;
import me.hardstyl3r.nbd.repositories.ResourceRepository;

import java.time.Instant;
import java.util.UUID;

public class AllocationManager {
    private final AllocationRepository allocationRepository;
    private final ClientRepository clientRepository;
    private final ResourceRepository resourceRepository;

    public AllocationManager(AllocationRepository allocationRepository, ClientRepository clientRepository, ResourceRepository resourceRepository) {
        this.allocationRepository = allocationRepository;
        this.clientRepository = clientRepository;
        this.resourceRepository = resourceRepository;
    }

    public Allocation allocateResource(UUID clientId, UUID resourceId) {
        Client client = clientRepository.findById(clientId);
        Resource resource = resourceRepository.findById(resourceId);

        if (client == null || resource == null) {
            throw new IllegalArgumentException("Client or Resource not found.");
        }

        Allocation allocation = new Allocation(
                client.getId(),
                UUID.randomUUID(),
                resource.getId(),
                resource.getTitle(),
                client.getLastName(),
                Instant.now()
        );
        allocationRepository.save(allocation);
        return allocation;
    }

    public Allocation findAllocation(UUID clientId, UUID allocationId) {
        return allocationRepository.findById(clientId, allocationId);
    }

    public PagingIterable<Allocation> findAllocationsForClient(UUID clientId) {
        return allocationRepository.findAllByClientId(clientId);
    }

    public void updateAllocation(Allocation allocation) {
        allocationRepository.update(allocation);
    }

    public void deallocateResource(Allocation allocation) {
        allocationRepository.delete(allocation);
    }
}
