/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Client;
import model.Devis;

/**
 *
 * @author B3229
 */
public class DevisDaoJpa extends DevisDao
{
    @Override
    public DaoError creerDevis(Devis devis) {
        try {
            //JpaUtil.ouvrirTransaction();
            JpaUtil.obtenirEntityManager().persist(devis);
            //JpaUtil.validerTransaction();
            error = DaoError.OK;
        }
        catch(Exception e) {
            //JpaUtil.annulerTransaction();
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Devis majDevis(Devis devis) {
        Devis ret;
        try {
            JpaUtil.ouvrirTransaction();
            ret = (Devis)JpaUtil.obtenirEntityManager().merge(devis);
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
    public DaoError supprimerDevis(Devis devis) {
        try {
            JpaUtil.ouvrirTransaction();
            JpaUtil.obtenirEntityManager().remove(devis);
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
    public Devis trouverDevisParNum(int numDevis) {
        Devis ret = null;
        try
        {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Devis c where c.num = :num");
            q.setParameter("num", numDevis);
            ret = (Devis)q.getSingleResult();
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
    public List<Devis> listerDevis() {
        List<Devis> ret = new ArrayList<Devis>();
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Devis c");
            ret = (List<Devis>) q.getResultList();
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
    public List<Devis> listerDevisParClient(Client cli) {
        List<Devis> ret = new ArrayList<Devis>();
        try
        {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Devis c where c.client = :client");
            q.setParameter("client", cli);
            ret = (List<Devis>)q.getResultList();
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
