package me.hardstyl3r.nbd.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import me.hardstyl3r.nbd.objects.Allocation;

import java.util.UUID;

@Dao
public interface AllocationDao {
    @Insert
    void save(Allocation allocation);

    @Select
    Allocation findById(UUID clientId, UUID id);

    @Update
    void update(Allocation allocation);

    @Delete
    void delete(Allocation allocation);
}