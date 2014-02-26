/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import javax.persistence.Query;
import model.Pays;
import model.Voyages;

/**
 *
 * @author Thomas
 */
public class PaysDaoJpa extends PaysDao {
    
    @Override
    public PaysDaoError createPays(Pays pays) {
        try {
            JpaUtil.obtenirEntityManager().persist(pays);
            error = PaysDaoError.OK;
        }
        catch(Exception e) {
            error = PaysDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Pays updatePays(Pays pays) {
        Pays ret;
        try {
            ret = (Pays)JpaUtil.obtenirEntityManager().merge(pays);
            error = PaysDaoError.OK;
        }
        catch(Exception e)
        {
            ret = null;
            error = PaysDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }

    @Override
    public PaysDaoError deletePays(Pays pays) {
        try {
            JpaUtil.obtenirEntityManager().remove(pays);
            error = PaysDaoError.OK;
        }
        catch(Exception e)
        {
            error = PaysDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Pays findPaysByNum(int numPays) {
        Pays ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Pays c where c.numPays = :numPays");
            q.setParameter("numVoyage", numPays);
            ret = (Pays)q.getSingleResult();
            error = PaysDaoError.OK;
        }
        catch(Exception e)
        {
            error = PaysDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
    @Override
    public Pays findPaysByName(String unNom) {
        Pays ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Pays c where c.nom = :unNom");
            q.setParameter("ref", unNom);
            ret = (Pays)q.getSingleResult();
            error = PaysDaoError.OK;
        }
        catch(Exception e)
        {
            error = PaysDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
   
    
    @Override
    public List<Pays> listingPays() {
        List<Pays> ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Pays c");
            ret = (List<Pays>) q.getResultList();
            error = PaysDaoError.OK;
        }
        catch(Exception e)
        {
            error = PaysDaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
}
