package me.hardstyl3r.nbd.db;

import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraSetup {

    public static void createKeyspaceAndTables(CqlSession session) {
        session.execute("CREATE KEYSPACE IF NOT EXISTS library " +
                "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");

        System.out.println("Keyspace 'library' created or already exists.");

        session.execute("CREATE TABLE IF NOT EXISTS library.client (" +
                "id uuid PRIMARY KEY, first_name text, last_name text)");

        System.out.println("Tables initialized.");
    }
}