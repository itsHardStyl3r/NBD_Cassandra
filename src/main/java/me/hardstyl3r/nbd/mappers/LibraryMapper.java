package me.hardstyl3r.nbd.mappers;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import me.hardstyl3r.nbd.daos.AllocationDao;
import me.hardstyl3r.nbd.daos.ClientDao;
import me.hardstyl3r.nbd.daos.ResourceDao;

@Mapper
public interface LibraryMapper {
    @DaoFactory
    ClientDao clientDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    ResourceDao resourceDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    AllocationDao allocationDao(@DaoKeyspace CqlIdentifier keyspace);
}