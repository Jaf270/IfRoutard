/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.Query;
import model.Client;

/**
 *
 * @author B3229
 */
public class ClientDaoJpa extends ClientDao {
    

    @Override
    public ClientDaoError createClient(Client client) {
        try {
            JpaUtil.obtenirEntityManager().persist(client);
            error = ClientDaoError.OK;
        }
        catch(Exception e) {
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Client updateClient(Client client) {
        Client ret;
        try {
            ret = (Client)JpaUtil.obtenirEntityManager().merge(client);
            error = ClientDaoError.OK;
        }
        catch(Exception e)
        {
            ret = null;
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public ClientDaoError deleteClient(Client client) {
        try {
            JpaUtil.obtenirEntityManager().remove(client);
            error = ClientDaoError.OK;
        }
        catch(Exception e)
        {
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Client findClientByNum(int numClient) {
        Client ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.numClient = :numClient");
            q.setParameter("numClient", numClient);
            ret = (Client)q.getSingleResult();
            error = ClientDaoError.OK;
        }
        catch(Exception e)
        {
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
    @Override
    public Client findClientByName(String unNom) {
        Client ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.nom = :unNom");
            q.setParameter("nom", unNom);
            ret = (Client)q.getSingleResult();
            error = ClientDaoError.OK;
        }
        catch(Exception e)
        {
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
    @Override
    public Client findClientByEMail(String email) {
        Client ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.adrMail = :adrMail");
            q.setParameter("adrMail", email);
            ret = (Client)q.getSingleResult();
            error = ClientDaoError.OK;
        }
        catch(Exception e)
        {
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
        
    }
    
    @Override
    public List<Client> listingClient() {
        List<Client> ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c");
            ret = (List<Client>) q.getResultList();
            error = ClientDaoError.OK;
        }
        catch(Exception e)
        {
            error = ClientDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
}
