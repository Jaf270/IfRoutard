package dao;

import model.Client;

/**
 *
 * @author B3229
 */
public interface ClientDao {
    public void createClient(Client client);
    public Client updateClient(Client client);
    public void deleteClient(Client client);
    public Client findClientByNum(int numClient);
}
