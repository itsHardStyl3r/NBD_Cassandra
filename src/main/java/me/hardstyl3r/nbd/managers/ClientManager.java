package me.hardstyl3r.nbd.managers;

import me.hardstyl3r.nbd.objects.Client;
import me.hardstyl3r.nbd.repositories.ClientRepository;

import java.util.UUID;

public class ClientManager {
    private final ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(UUID id, String firstName, String lastName) {
        Client client = new Client(id, firstName, lastName);
        clientRepository.save(client);
    }

    public Client findClient(UUID id) {
        return clientRepository.findById(id);
    }

    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    public void removeClient(Client client) {
        clientRepository.delete(client);
    }
}
