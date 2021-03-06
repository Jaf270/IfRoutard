
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Pays;
import model.TypeVoyage;
import model.Voyages;

/**
 *
 * @author B3229
 */
public class VoyageDaoJpa extends VoyageDao {
    
    
    @Override
    public DaoError creerVoyage(Voyages voyage) {
        try {
            JpaUtil.ouvrirTransaction();
            JpaUtil.obtenirEntityManager().persist(voyage);
            JpaUtil.validerTransaction();
            error = DaoError.OK;
        }
        catch(Exception e) {
            JpaUtil.annulerTransaction();
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Voyages majVoyage(Voyages  voyage) {
        Voyages ret;
        try {
            JpaUtil.ouvrirTransaction();
            ret = (Voyages)JpaUtil.obtenirEntityManager().merge(voyage);
            JpaUtil.validerTransaction();
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            JpaUtil.annulerTransaction();
            ret = null;
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public DaoError supprimerVoyage(Voyages voyage) {
        try {
            JpaUtil.ouvrirTransaction();
            JpaUtil.obtenirEntityManager().remove(voyage);
            JpaUtil.validerTransaction();
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            JpaUtil.annulerTransaction();
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Voyages trouverVoyageParNum(int numVoyage) {
        Voyages ret = null;
        try
        {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.num = :numVoyage");
            q.setParameter("numVoyage", numVoyage);
            ret = (Voyages)q.getSingleResult();
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
    public Voyages trouverVoyageParRef(String uneRef) {
        Voyages ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.reference = :ref");
            q.setParameter("ref", uneRef);
            ret = (Voyages)q.getSingleResult();
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
    public List<Voyages> listerVoyages() {
        List<Voyages> ret = new ArrayList<Voyages>();
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c");
            ret = (List<Voyages>) q.getResultList();
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public List<Voyages> listerVoyagesParPays(Pays pays) {
        List<Voyages> ret = new ArrayList<Voyages>();
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where pays = :pays");
            q.setParameter("pays", pays);
            ret = (List<Voyages>) q.getResultList();
            error = DaoError.OK;
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public List<Voyages> listerVoyagesParTypeETPays(TypeVoyage type, Pays pays) {
        List<Voyages> ret = new ArrayList<Voyages>();
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.pays = :pays and c.type = :type");
            q.setParameter("pays", pays);
            q.setParameter("type", type);
            ret = (List<Voyages>) q.getResultList();
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
