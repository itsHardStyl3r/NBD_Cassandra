package me.hardstyl3r.nbd.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import me.hardstyl3r.nbd.objects.Client;

import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    void save(Client client);

    @Select
    @StatementAttributes(consistencyLevel = "QUORUM")
    Client findById(UUID id);

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(Client client);

    @Delete
    @StatementAttributes(consistencyLevel = "QUORUM")
    void delete(Client client);
}
