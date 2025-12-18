package me.hardstyl3r.nbd.objects;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.time.Instant;
import java.util.UUID;

@Entity
@CqlName("allocations_by_client")
public class Allocation {

    @PartitionKey
    private UUID clientId;
    @ClusteringColumn
    private UUID id;
    private UUID resourceId;
    private String resourceTitle;
    private String clientLastName;
    private Instant startTime;

    public Allocation() {
    }

    public Allocation(UUID clientId, UUID id, UUID resourceId, String resourceTitle, String clientLastName, Instant startTime) {
        this.clientId = clientId;
        this.id = id;
        this.resourceId = resourceId;
        this.resourceTitle = resourceTitle;
        this.clientLastName = clientLastName;
        this.startTime = startTime;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
}