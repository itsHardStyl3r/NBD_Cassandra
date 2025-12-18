package me.hardstyl3r.nbd.managers;

import me.hardstyl3r.nbd.objects.Resource;
import me.hardstyl3r.nbd.repositories.ResourceRepository;

import java.util.UUID;

public class ResourceManager {
    private final ResourceRepository resourceRepository;

    public ResourceManager(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void addBook(UUID id, String title, String author) {
        Resource book = Resource.createBook(id, title, author);
        resourceRepository.save(book);
    }

    public void addMagazine(UUID id, String title, int issueNumber) {
        Resource magazine = Resource.createMagazine(id, title, issueNumber);
        resourceRepository.save(magazine);
    }

    public Resource findResource(UUID id) {
        return resourceRepository.findById(id);
    }

    public void updateResource(Resource resource) {
        resourceRepository.update(resource);
    }

    public void removeResource(Resource resource) {
        resourceRepository.delete(resource);
    }
}
