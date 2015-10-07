package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.Client;
import junit.framework.TestCase;
import org.junit.Test;

public class TestClientServiceImpl extends TestCase {

    private ClientService service;

    public static final String testEmail   = "mail@mail.mail";
    public static final String testName    = "John Doe";
    public static final String testAddress = "Hello address";

    protected void setUp() {
        service = new ClientServiceImpl();
        ClientServiceImpl.resetData();
    }

    @Test
    public void testClientCreation (){
        Client client = createTestClient();

        int returnedId = service.createClient(client);

        // Client storage supposed empty after setUp()
        assertTrue("Client reported not created", returnedId != -1);

        Client foundClient = service.getClientById(returnedId);

        assertEquals("Returned client does not have the same Id",      returnedId,  foundClient.getId());
        assertEquals("Returned client does not have the same email",   testEmail,   foundClient.getEmail());
        assertEquals("Returned client does not have the same name",    testName,    foundClient.getName());
        assertEquals("Returned client does not have the same address", testAddress, foundClient.getAddress());
    }

    @Test
    public void testEmailDupplication() {
        Client client = createTestClient();

        int returnedId = service.createClient(client);

        assertTrue("Client reported not created",  returnedId != -1);

        int secondId = service.createClient(client);

        assertEquals("Client should not be created twice with same email",
                -1, secondId);
    }

    @Test
    public void testRetreiveClientByEmail() {
        Client client = createTestClient();

        int returnedId = service.createClient(client);

        Client foundClient = service.getClientByEmail(testEmail);

        assertEquals("Returned client does not have the same Id",      returnedId,     foundClient.getId());
        assertEquals("Returned client does not have the same email",   testEmail,      foundClient.getEmail());
        assertEquals("Returned client does not have the same name",    testName,       foundClient.getName());
        assertEquals("Returned client does not have the same address", testAddress,    foundClient.getAddress());
    }


    public static Client createTestClient() {
        Client client = new Client();

        client.setAddress(testAddress);
        client.setName(testName);
        client.setEmail(testEmail);

        return client;
    }
}
