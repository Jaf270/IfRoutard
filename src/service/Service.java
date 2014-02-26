/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.DaoError;
import dao.ClientDaoJpa;
import dao.JpaUtil;
import model.Client;

/**
 *
 * @author B3229
 */
public class Service {
    protected ServiceError error = null;
    protected String errorMessage = null;
    
    /**
     *
     * @return The error indicator of the last failed call
     */
    public final ServiceError getError()
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
    
    private ClientDao clientDao = new ClientDaoJpa();
    
    /**
     *
     * @param client Le client à inscrire
     * @return le numéro du client si succès, -1 sinon
     */
    public int InscriptionClient(Client client)
    {
        int ret = -1;
        JpaUtil.creerEntityManager();
        clientDao.findClientByEMail(client.getEMail());
        if(clientDao.getError() == DaoError.OK) {
            error = ServiceError.EXISTING_EMAIL;
            JpaUtil.fermerEntityManager();
        }
        else {
            try {
                JpaUtil.ouvrirTransaction();
                if(clientDao.createClient(client) != DaoError.OK)
                    throw new Exception(clientDao.getErrorMessage());
                JpaUtil.validerTransaction();
                ret = client.getNum();
                error = ServiceError.OK;
            }
            catch(Exception e)
            {
                JpaUtil.annulerTransaction();
                error = ServiceError.GENERIC_ERROR;
                errorMessage = e.getMessage();
                ret = -1;
            }
            finally
            {
                JpaUtil.fermerEntityManager();
            }
        }
        return ret;
    }
    
    public Client ConnexionClient(String email, String motDePasse)
    {
        Client returnClient = null;
        JpaUtil.creerEntityManager();
        returnClient = clientDao.findClientByEMail(email);
        if(clientDao.getError() != DaoError.OK) {
            error = ServiceError.WRONG_EMAIL;
            errorMessage = clientDao.getErrorMessage();
        }
        else if(!returnClient.getMotDePasse().equals(motDePasse)) {
            error = ServiceError.WRONG_PASSWORD;
        }
        else {
            error = ServiceError.OK;
        }
        JpaUtil.fermerEntityManager();
        return returnClient;
    }
    
}
