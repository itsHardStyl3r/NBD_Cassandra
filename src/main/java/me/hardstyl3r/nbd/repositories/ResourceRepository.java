package me.hardstyl3r.nbd.repositories;

import me.hardstyl3r.nbd.daos.ResourceDao;
import me.hardstyl3r.nbd.objects.Resource;

import java.util.UUID;

public class ResourceRepository {
    private final ResourceDao resourceDao;

    public ResourceRepository(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    public void save(Resource resource) {
        resourceDao.save(resource);
    }

    public Resource findById(UUID id) {
        return resourceDao.findById(id);
    }

    public void update(Resource resource) {
        resourceDao.update(resource);
    }

    public void delete(Resource resource) {
        resourceDao.delete(resource);
    }
}
