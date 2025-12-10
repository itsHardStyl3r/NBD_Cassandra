package me.hardstyl3r.nbd.objects;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.Transient;

import java.util.UUID;

@Entity
public class Resource {
    @PartitionKey
    private UUID id;
    private String type;
    private String title;
    private String author;
    private Integer issueNumber;

    public Resource() {}

    private Resource(UUID id, ResourceType type, String title, String author, Integer issueNumber) {
        this.id = id;
        this.type = type.name();
        this.title = title;
        this.author = author;
        this.issueNumber = issueNumber;
    }

    public static Resource createBook(UUID id, String title, String author) {
        return new Resource(id, ResourceType.BOOK, title, author, null);
    }

    public static Resource createMagazine(UUID id, String title, int issueNumber) {
        return new Resource(id, ResourceType.MAGAZINE, title, null, issueNumber);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Transient
    public ResourceType getResourceType() {
        return ResourceType.valueOf(this.type);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getIssueNumber() { return issueNumber; }
    public void setIssueNumber(Integer issueNumber) { this.issueNumber = issueNumber; }

    @Override
    public String toString() {
        if (ResourceType.BOOK.name().equals(type)) {
            return "[BOOK] '" + title + "' by " + author;
        } else if (ResourceType.MAGAZINE.name().equals(type)) {
            return "[MAGAZINE] '" + title + "' issue no. " + issueNumber;
        }
        return "[RESOURCE] " + title;
    }
}