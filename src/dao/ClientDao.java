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
    
    public abstract DaoError creerClient(Client client);
    public abstract Client majClient(Client client);
    public abstract DaoError supprimerClient(Client client);
    public abstract Client trouverClientParNum(int numClient);
    public abstract Client trouverClientParName(String unNom);
    public abstract Client trouverClientParEMail(String email);
    public abstract List<Client> listerClient();
    
}
