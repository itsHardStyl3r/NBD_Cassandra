package me.hardstyl3r.nbd.db;

import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraSetup {

    public static void createKeyspaceAndTables(CqlSession session) {
        session.execute("DROP KEYSPACE IF EXISTS library");
        System.out.println("Keyspace 'library' dropped.");

        session.execute("CREATE KEYSPACE library " +
                "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");

        System.out.println("Keyspace 'library' created.");

        session.execute("CREATE TABLE library.client (" +
                "id uuid PRIMARY KEY, first_name text, last_name text)");

        session.execute("CREATE TABLE library.resource (" +
                "id uuid PRIMARY KEY, type text, title text, author text, issue_number int)");

        session.execute("CREATE TABLE library.allocations_by_client (" +
                "client_id uuid, id uuid, resource_id uuid, resource_title text, client_last_name text, start_time timestamp, " +
                "PRIMARY KEY ((client_id), id))");

        System.out.println("Tables initialized from scratch.");
    }
}