
package dao;

import java.util.List;
import javax.persistence.Query;
import model.Client;
import model.Voyages;

/**
 *
 * @author B3229
 */
public class VoyageDaoJpa extends VoyageDao {
    
    
    @Override
    public VoyageDaoError createVoyage(Voyages voyage) {
        try {
            JpaUtil.obtenirEntityManager().persist(voyage);
            error = VoyageDaoError.OK;
        }
        catch(Exception e) {
            error = VoyageDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Voyages updateVoyage(Voyages  voyage) {
        Voyages ret;
        try {
            ret = (Voyages)JpaUtil.obtenirEntityManager().merge(voyage);
            error = VoyageDaoError.OK;
        }
        catch(Exception e)
        {
            ret = null;
            error = VoyageDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public VoyageDaoError deleteVoyage(Voyages voyage) {
        try {
            JpaUtil.obtenirEntityManager().remove(voyage);
            error = VoyageDaoError.OK;
        }
        catch(Exception e)
        {
            error = VoyageDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Voyages findVoyageByNum(int numVoyage) {
        Voyages ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.numVoyage = :numVoyage");
            q.setParameter("numVoyage", numVoyage);
            ret = (Voyages)q.getSingleResult();
            error = VoyageDaoError.OK;
        }
        catch(Exception e)
        {
            error = VoyageDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
    @Override
    public Voyages findVoyageByRef(String uneRef) {
        Voyages ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c where c.ref = :uneRef");
            q.setParameter("ref", uneRef);
            ret = (Voyages)q.getSingleResult();
            error = VoyageDaoError.OK;
        }
        catch(Exception e)
        {
            error = VoyageDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
   
    
    @Override
    public List<Voyages> listingVoyages() {
        List<Voyages> ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Voyages c");
            ret = (List<Voyages>) q.getResultList();
            error = VoyageDaoError.OK;
        }
        catch(Exception e)
        {
            error = VoyageDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
}
