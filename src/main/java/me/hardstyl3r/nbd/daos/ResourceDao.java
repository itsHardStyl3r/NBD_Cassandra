package me.hardstyl3r.nbd.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import me.hardstyl3r.nbd.objects.Resource;

import java.util.UUID;

@Dao
public interface ResourceDao {
    @Insert
    void save(Resource resource);

    @Select
    Resource findById(UUID id);

    @Update
    void update(Resource resource);

    @Delete
    void delete(Resource resource);
}