package me.hardstyl3r.nbd.repositories;

import me.hardstyl3r.nbd.daos.ClientDao;
import me.hardstyl3r.nbd.objects.Client;

import java.util.UUID;

public class ClientRepository {
    private final ClientDao clientDao;

    public ClientRepository(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void save(Client client) {
        clientDao.save(client);
    }

    public Client findById(UUID id) {
        return clientDao.findById(id);
    }

    public void update(Client client) {
        clientDao.update(client);
    }

    public void delete(Client client) {
        clientDao.delete(client);
    }
}
