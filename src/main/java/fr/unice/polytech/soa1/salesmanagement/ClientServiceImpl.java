package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.Client;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/salesmanagement/",
        portName          = "ExternalClientServicePort",
        serviceName       = "ExternalClientService",
        endpointInterface = "fr.unice.polytech.soa1.salesmanagement.ClientService")
public class ClientServiceImpl implements ClientService {
    private static Map <Integer, Client> clients;
    private static int nextClientId;

    public ClientServiceImpl() {
        if (clients == null) {
            clients = new HashMap <Integer, Client>();
            nextClientId = 0;
        }
    }

    public Client getClientById(int clientId) {
        return clients.get(clientId);
    }

    public Client getClientByEmail (String email) {
        for (Client client : clients.values()) {
            if (client.getEmail().equals(email)) {
                return client;
            }
        }

        return null;
    }

    public int createClient(Client client) {
        if (getClientByEmail(client.getEmail()) != null) {
            return -1;
        }

        int clientId = nextClientId++;
        client.setId(clientId);

        clients.put(clientId, client);

        return clientId;
    }
}
