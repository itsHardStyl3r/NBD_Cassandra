package me.hardstyl3r.nbd.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import me.hardstyl3r.nbd.objects.Client;

import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    void save(Client client);

    @Select
    Client findById(UUID id);

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);
}
