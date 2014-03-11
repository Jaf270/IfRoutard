
package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Client;
import model.Pays;
import model.Voyages;

/**
 *
 * @author B3229
 */
public class VoyageDaoJpa extends VoyageDao {
    
    
    @Override
    public DaoError creerVoyage(Voyages voyage) {
        try {
            JpaUtil.obtenirEntityManager().persist(voyage);
            error = DaoError.OK;
        }
        catch(Exception e) {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Voyages majVoyage(Voyages  voyage) {
        Voyages ret;
        try {
            ret = (Voyages)JpaUtil.obtenirEntityManager().merge(voyage);
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
    public DaoError supprimerVoyage(Voyages voyage) {
        try {
            JpaUtil.obtenirEntityManager().remove(voyage);
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
    public Voyages trouverVoyageParNum(int numVoyage) {
        Voyages ret = null;
        try
        {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.numVoyage = :numVoyage");
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
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.ref = :uneRef");
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
        List<Voyages> ret = null;
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
        List<Voyages> ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where pays = :pays");
            q.setParameter(":pays", pays);
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
