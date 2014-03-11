/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Client;

/**
 *
 * @author B3229
 */
public class ClientDaoJpa extends ClientDao {
    

    @Override
    public DaoError creerClient(Client client) {
        try {
            JpaUtil.obtenirEntityManager().persist(client);
            error = DaoError.OK;
        }
        catch(Exception e) {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Client majClient(Client client) {
        Client ret;
        try {
            ret = (Client)JpaUtil.obtenirEntityManager().merge(client);
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            ret = null;
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public DaoError supprimerClient(Client client) {
        try {
            JpaUtil.obtenirEntityManager().remove(client);
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Client trouverClientParNum(int numClient) {
        Client ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.numClient = :numClient");
            q.setParameter("numClient", numClient);
            ret = (Client)q.getSingleResult();
            error = DaoError.OK;
        }
        catch(NoResultException e)
        {
            error = DaoError.NOT_FOUND;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
    @Override
    public Client trouverClientParName(String unNom) {
        Client ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.nom = :unNom");
            q.setParameter("nom", unNom);
            ret = (Client)q.getSingleResult();
            error = DaoError.OK;
        }
        catch(NoResultException e)
        {
            error = DaoError.NOT_FOUND;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
    @Override
    public Client trouverClientParEMail(String email) {
        Client ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.adrMail = :adrMail");
            q.setParameter("adrMail", email);
            ret = (Client)q.getSingleResult();
            error = DaoError.OK;
        }
        catch(NoResultException e)
        {
            error = DaoError.NOT_FOUND;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
        
    }
    
    @Override
    public List<Client> listerClient() {
        List<Client> ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c");
            ret = (List<Client>) q.getResultList();
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
}
