/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.ClientDaoJpa;
import dao.DaoError;
import dao.JpaUtil;
import dao.PaysDao;
import dao.PaysDaoJpa;
import dao.VoyageDao;
import dao.VoyageDaoJpa;
import java.util.List;
import model.Client;
import model.Conseillers;
import model.Devis;
import model.Pays;
import model.TypeTransport;
import model.Voyages;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    public final ServiceError getError() {
        return error;
    }
    /**
     *
     * @return The error message of the last failed call
     */
    public final String getErrorMessage() {
        return errorMessage;
    }
    
    private ClientDao clientDao = new ClientDaoJpa();
    private PaysDao paysDao = new PaysDaoJpa();
    private VoyageDao voyageDao = new VoyageDaoJpa();
    
    /**
     *
     * @param client Le client à inscrire
     * @return le numéro du client si succès, -1 sinon
     */
    public int InscriptionClient(Client client) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        clientDao.trouverClientParEMail(client.getEMail());
        if(clientDao.getError() == DaoError.OK) {
            error = ServiceError.EXISTING_EMAIL;
            JpaUtil.fermerEntityManager();
        }
        else {
            try {
                JpaUtil.ouvrirTransaction();
                if(clientDao.creerClient(client) != DaoError.OK)
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
    
    public Client ConnexionClient(String email, String motDePasse) {
        Client returnClient = null;
        JpaUtil.creerEntityManager();
        returnClient = clientDao.trouverClientParEMail(email);
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

    public List<Voyages> RechercheVoyage(TypeTransport type, Pays pays) {
        throw new NotImplementedException();
    }
    
    public Voyages DetailsVoyage(int numVoyage) {
        throw new NotImplementedException();
    }
    
    public List<Pays> RecherchePays() {
        throw  new NotImplementedException();
    }
    
    public Pays DetailPays(int numPays) {
        throw new NotImplementedException();
    }
    
    public Conseillers CréerDevis(Devis devis) {
        throw new NotImplementedException();
    }
    
    public int AjouterPays(Pays pays) {
        throw new NotImplementedException();
    }
    
    public int EditionPays(Pays pays) {
        throw new NotImplementedException();
    }
    
    public boolean SuppressionPays(int numPays) {
        throw new NotImplementedException();
    }
    
    public int AjouterVoyage(Voyages voyage) {
        throw new NotImplementedException();
    }
    
    public int EditionVoyage(Voyages voyage) {
        throw new NotImplementedException();
    }
    
    public boolean SuppressionVoyage(int numVoyage) {
        throw new NotImplementedException();
    }
    
}
