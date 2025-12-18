package me.hardstyl3r.nbd.repositories;

import com.datastax.oss.driver.api.core.PagingIterable;
import me.hardstyl3r.nbd.daos.AllocationDao;
import me.hardstyl3r.nbd.objects.Allocation;

import java.util.UUID;

public class AllocationRepository {
    private final AllocationDao allocationDao;

    public AllocationRepository(AllocationDao allocationDao) {
        this.allocationDao = allocationDao;
    }

    public void save(Allocation allocation) {
        allocationDao.save(allocation);
    }

    public Allocation findById(UUID clientId, UUID id) {
        return allocationDao.findById(clientId, id);
    }

    public void update(Allocation allocation) {
        allocationDao.update(allocation);
    }

    public void delete(Allocation allocation) {
        allocationDao.delete(allocation);
    }

    public PagingIterable<Allocation> findAllByClientId(UUID clientId) {
        return allocationDao.findAllByClientId(clientId);
    }
}
