package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.Client;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "ClientService")
public interface ClientService {

    @WebResult(name = "client")
    public Client getClientById (@WebParam(name = "client_id") int clientId);

    @WebResult(name = "client")
    public Client getClientByEmail (@WebParam(name = "email") String email);

    @WebResult(name = "client")
    public int createClient (@WebParam(name = "client") Client client);

}
