package dao;

import java.util.List;
import model.Client;

/**
 *
 * @author B3229
 */
public abstract class ClientDao {
    protected DaoError error = null;
    protected String errorMessage = null;
    
    /**
     *
     * @return The error indicator of the last failed call
     */
    public final DaoError getError()
    {
        return error;
    }
    /**
     *
     * @return The error message of the last failed call
     */
    public final String getErrorMessage()
    {
        return errorMessage;
    }
    
    public abstract DaoError createClient(Client client);
    public abstract Client updateClient(Client client);
    public abstract DaoError deleteClient(Client client);
    public abstract Client findClientByNum(int numClient);
    public abstract Client findClientByName(String unNom);
    public abstract Client findClientByEMail(String email);
    public abstract List<Client> listingClient();
    
}
