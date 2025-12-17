package me.hardstyl3r.nbd.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import me.hardstyl3r.nbd.objects.Resource;

import java.util.UUID;

@Dao
public interface ResourceDao {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    void save(Resource resource);

    @Select
    @StatementAttributes(consistencyLevel = "QUORUM")
    Resource findById(UUID id);

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(Resource resource);

    @Delete
    @StatementAttributes(consistencyLevel = "QUORUM")
    void delete(Resource resource);
}
