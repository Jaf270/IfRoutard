/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.ClientDaoJpa;
import dao.ConseillerDao;
import dao.ConseillerDaoJpa;
import dao.DaoError;
import dao.DevisDao;
import dao.DevisDaoJpa;
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
import model.TypeVoyage;
import model.Voyages;

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
    private DevisDao devisDao = new DevisDaoJpa();
    private ConseillerDao conseillerDao = new ConseillerDaoJpa();
    
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
                {
                    throw new Exception(clientDao.getErrorMessage());
                }
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
        if(clientDao.getError() == DaoError.NOT_FOUND)
        {
            error = ServiceError.NOT_FOUND;
        }
        else if(clientDao.getError() != DaoError.OK) {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = clientDao.getErrorMessage();
        }
        else if(!returnClient.getMotDePasse().equals(motDePasse))
        {
            error = ServiceError.WRONG_PASSWORD;
        }
        else
        {
            error = ServiceError.OK;
        }
        JpaUtil.fermerEntityManager();
        return returnClient;
    }

    public List<Voyages> RechercheVoyage(TypeVoyage type, Pays pays) {
        List<Voyages> liste;
        JpaUtil.creerEntityManager();
        liste = voyageDao.listerVoyagesParTypeETPays(type, pays);
        if(voyageDao.getError() != DaoError.OK)
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = voyageDao.getErrorMessage();
        }
        else
        {
            error = ServiceError.OK;
        }
        JpaUtil.fermerEntityManager();
        return liste;
    }
    
    public Voyages DetailsVoyage(int numVoyage) {
        Voyages ret;
        JpaUtil.creerEntityManager();
        ret = voyageDao.trouverVoyageParNum(numVoyage);
        if(voyageDao.getError() == DaoError.OK)
        {
            error = ServiceError.OK;
        }
        else if(voyageDao.getError() == DaoError.NOT_FOUND)
        {
            error = ServiceError.NOT_FOUND;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = voyageDao.getErrorMessage();
        }
        
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public List<Pays> RecherchePays() {
        List<Pays> ret;
        JpaUtil.creerEntityManager();
        ret = paysDao.listerPays();
        if(paysDao.getError() == DaoError.OK)
        {
            error = ServiceError.OK;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = paysDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public Pays DetailPays(int numPays) {
        Pays ret;
        JpaUtil.creerEntityManager();
        ret = paysDao.trouverPaysParNum(numPays);
        if(paysDao.getError() == DaoError.OK)
        {
            error = ServiceError.OK;
        }
        else if(paysDao.getError() == DaoError.NOT_FOUND)
        {
            error = ServiceError.NOT_FOUND;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = paysDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public Conseillers CréerDevis(Devis devis) {
        JpaUtil.creerEntityManager();
        Conseillers ret = conseillerDao.trouverConseillerAdequat(devis.getPays());
        if(conseillerDao.getError() == DaoError.NOT_FOUND)
        {
            error = ServiceError.NOT_FOUND;
        }
        else if(conseillerDao.getError() == DaoError.OK)
        {
            JpaUtil.ouvrirTransaction();
            ret.ajouterDevis();
            ret = conseillerDao.majConseiller(ret);
            if(conseillerDao.getError() == DaoError.OK)
            {
                devis.setConseiller(ret);
                devisDao.creerDevis(devis);
                if(devisDao.getError() == DaoError.OK)
                {
                    JpaUtil.validerTransaction();
                    error = ServiceError.OK;
                }
                else
                {
                    JpaUtil.annulerTransaction();
                    error = ServiceError.GENERIC_ERROR;
                    errorMessage = devisDao.getErrorMessage();
                }
            }
            else
            {
                JpaUtil.annulerTransaction();
                error = ServiceError.GENERIC_ERROR;
                errorMessage = conseillerDao.getErrorMessage();
            }
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = conseillerDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public int AjouterPays(Pays pays) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        paysDao.creerPays(pays);
        if(paysDao.getError() == DaoError.OK)
        {
            ret = pays.getNum();
            error = ServiceError.OK;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = paysDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public int EditionPays(Pays pays) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        pays = paysDao.majPays(pays);
        if(paysDao.getError() == DaoError.OK)
        {
            ret = pays.getNum();
            error = ServiceError.OK;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = paysDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public boolean SuppressionPays(int numPays) {
        boolean ret = false;
        JpaUtil.creerEntityManager();
        Pays toDelete = paysDao.trouverPaysParNum(numPays);
        if(paysDao.getError() == DaoError.OK)
        {
            paysDao.supprimerPays(toDelete);
            if(paysDao.getError() == DaoError.OK)
            {
                ret = true;
                error = ServiceError.OK;
            }
            else
            {
                error = ServiceError.GENERIC_ERROR;
                errorMessage = paysDao.getErrorMessage();
            }
        }
        else if(paysDao.getError() == DaoError.NOT_FOUND)
        {
            error = ServiceError.NOT_FOUND;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = paysDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public int AjouterVoyage(Voyages voyage) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        voyageDao.creerVoyage(voyage);
        if(voyageDao.getError() == DaoError.OK)
        {
            ret = voyage.getNum();
            error = ServiceError.OK;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = voyageDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public int EditionVoyage(Voyages voyage) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        voyage = voyageDao.majVoyage(voyage);
        if(voyageDao.getError() == DaoError.OK)
        {
            ret = voyage.getNum();
            error = ServiceError.OK;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = voyageDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
    public boolean SuppressionVoyage(int numVoyage) {
        boolean ret = false;
        JpaUtil.creerEntityManager();
        Voyages toDelete = voyageDao.trouverVoyageParNum(numVoyage);
        if(voyageDao.getError() == DaoError.OK)
        {
            voyageDao.supprimerVoyage(toDelete);
            if(voyageDao.getError() == DaoError.OK)
            {
                ret = true;
                error = ServiceError.OK;
            }
            else
            {
                error = ServiceError.GENERIC_ERROR;
                errorMessage = voyageDao.getErrorMessage();
            }
        }
        else if(voyageDao.getError() == DaoError.NOT_FOUND)
        {
            error = ServiceError.NOT_FOUND;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = voyageDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
}
