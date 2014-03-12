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
    protected static ServiceError error = null;
    protected static String errorMessage = null;
    
    /**
     *
     * @return The error indicator of the last failed call
     */
    public static ServiceError getError() {
        return error;
    }
    /**
     *
     * @return The error message of the last failed call
     */
    public static String getErrorMessage() {
        return errorMessage;
    }
    
    private static ClientDao clientDao = new ClientDaoJpa();
    private static PaysDao paysDao = new PaysDaoJpa();
    private static VoyageDao voyageDao = new VoyageDaoJpa();
    private static DevisDao devisDao = new DevisDaoJpa();
    private static ConseillerDao conseillerDao = new ConseillerDaoJpa();
    
//============= Module Client =================================================
    
    /**
     * Permet de créer un compte client à partir de l'ensemble des informations fournies
     * @param client Le client à inscrire
     * @return le numéro du client si succès, -1 sinon
     */
    public static int InscriptionClient(Client client) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        clientDao.trouverClientParEMail(client.getEMail());
        if(clientDao.getError() == DaoError.OK) {
            error = ServiceError.EXISTING_EMAIL;
            JpaUtil.fermerEntityManager();
        }
        else if(clientDao.getError() == DaoError.NOT_FOUND) {
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
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = clientDao.getErrorMessage();
        }
        return ret;
    }
    
    /**
     * Récupère un client selon ses identifiants
     * @param email L'email du client
     * @param motDePasse Le mot de passe du client
     * @return Le client si succès et null sinon
     */
    public static Client ConnexionClient(String email, String motDePasse) {
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
    
//============= Fin Module Client =============================================

//============= Module Conseiller =============================================
    
    public static int ajouterConseiller(Conseillers cons)
    {
        int ret = -1;
        JpaUtil.creerEntityManager();
        conseillerDao.creerConseiller(cons);
        if(conseillerDao.getError() == DaoError.OK)
        {
            ret = cons.getId();
            error = ServiceError.OK;
        }
        else
        {
            error = ServiceError.GENERIC_ERROR;
            errorMessage = conseillerDao.getErrorMessage();
        }
        JpaUtil.fermerEntityManager();
        return ret;
    }
    
//============= Fin Module Conseiller =============================================
    
//============= Module Devis ==================================================
    
    /**
     * Permet d'établir un devis et de récupérer le conseiller associé au voyage
     * @param devis Le devis à établir
     * @return Le conseiller attribué au devis si succès, et null sinon
     */
    public static Conseillers CréerDevis(Devis devis) {
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
    
// ============= Fin Module Devis =============================================
    
// ============= Module Pays ==================================================
    
    /**
     * Permet d'ajouter un pays dans la base de l'agence
     * @param pays Le pays à ajouter
     * @return L'identifiant du pays si succès, -1 sinon
     */
    public static int AjouterPays(Pays pays) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        paysDao.creerPays(pays);
        if(paysDao.getError() == DaoError.OK)
        {
            ret = pays.getId();
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
    
    /**
     * Permet de mettre à jour le pays dans la base de l'agence
     * @param pays Le pays à mettre à jour
     * @return L'identifiant du pays si succès, -1 sinon
     */
    public static int EditionPays(Pays pays) {
        int ret = -1;
        JpaUtil.creerEntityManager();
        pays = paysDao.majPays(pays);
        if(paysDao.getError() == DaoError.OK)
        {
            ret = pays.getId();
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
    
    /**
     * Permet de lister tous les pays
     * @return La liste des pays si succès, null sinon
     */
    public static List<Pays> RecherchePays() {
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
    
    /**
     * Permet d'obtenir les détails d'un pays
     * @param numPays L'identifiant du pays
     * @return Le pays si succès, null sinon
     */
    public static Pays DetailPays(String codePays) {
        Pays ret;
        JpaUtil.creerEntityManager();
        ret = paysDao.trouverPaysParCode(codePays);
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
    
    /**
     * Permet de supprimer un pays de la base de l'agence
     * @param numPays L'identifiant du pays
     * @return TRUE si succès, FALSE sinon
     */
    public static boolean SuppressionPays(String codePays) {
        boolean ret = false;
        JpaUtil.creerEntityManager();
        Pays toDelete = paysDao.trouverPaysParCode(codePays);
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
    
// ============= Fin Module Pays ==============================================

// ============= Module Voyages ===============================================

    /**
     * Permet d'ajouter un voyage dans la base de l'agence
     * @param voyage Le voyage à ajouter
     * @return L'identifiant du voyage si succès, -1 sinon
     */
    public static int AjouterVoyage(Voyages voyage) {
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
    
    /**
     * Permet de mettre à jour le voyage dans la base de l'agence
     * @param voyage Le voyage à mettre à jour
     * @return L'identifiant du voyage si succès, -1 sinon
     */
    public static int EditionVoyage(Voyages voyage) {
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
    
    /**
     * Permet de récupérer les voyages selon leur type et leur pays
     * @param type Le type du voyage
     * @param pays Le pays où se déroule le voyage
     * @return Liste filtrée de voyages si succès, null sinon
     */
    public static List<Voyages> RechercheVoyage(TypeVoyage type, Pays pays) {
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
    
    /**
     * Permet d'obtenir les détails d'un voyage
     * @param numVoyage La référence du voyage
     * @return Le voyage si succès, null sinon
     */
    public static Voyages DetailsVoyage(String refVoyage) {
        Voyages ret;
        JpaUtil.creerEntityManager();
        ret = voyageDao.trouverVoyageParRef(refVoyage);
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
    
    /**
     * Permet de supprimer un voyage de la base de l'agence
     * @param numVoyage Le référence du voyage
     * @return TRUE si succès, FALSE sinon
     */
    public static boolean SuppressionVoyage(String refVoyage) {
        boolean ret = false;
        JpaUtil.creerEntityManager();
        Voyages toDelete = voyageDao.trouverVoyageParRef(refVoyage);
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

// ============= Fin Module Voyages ===========================================

}
