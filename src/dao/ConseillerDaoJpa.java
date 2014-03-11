/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Iterator;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Conseillers;
import model.Pays;

/**
 *
 * @author B3229
 */
public class ConseillerDaoJpa extends ConseillerDao
{
    @Override
    public DaoError creerConseiller(Conseillers conseiller) {
        try {
            JpaUtil.obtenirEntityManager().persist(conseiller);
            error = DaoError.OK;
        }
        catch(Exception e) {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return error;
    }

    @Override
    public Conseillers majConseiller(Conseillers conseiller) {
        Conseillers ret;
        try {
            ret = (Conseillers)JpaUtil.obtenirEntityManager().merge(conseiller);
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
    public DaoError supprimerConseiller(Conseillers conseiller) {
        try {
            JpaUtil.obtenirEntityManager().remove(conseiller);
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
    public Conseillers trouverConseillerParNum(int numConseiller) {
        Conseillers ret = null;
        try
        {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Conseillers c where c.num = :num");
            q.setParameter("num", numConseiller);
            ret = (Conseillers)q.getSingleResult();
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
    public List<Conseillers> listerConseillers() {
        List<Conseillers> ret = null;
        try {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Conseillers c");
            ret = (List<Conseillers>) q.getResultList();
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
    public Conseillers trouverConseillerAdequat(Pays pays) {
        Conseillers ret = null;
        try
        {
            Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Conseillers c order by nbDevis ASC");
            List<Conseillers> list = q.getResultList();
            for(Iterator<Conseillers> it = list.iterator();it.hasNext();)
            {
                Conseillers c = it.next();
                if(!c.estSpecialise(pays))
                {
                    it.remove();
                }
            }
            if(list.isEmpty())
            {
                ret = null;
                error = DaoError.NOT_FOUND;
            }
            else
            {
                
                ret = list.get(0);
                error = DaoError.OK;
            }
        }
        catch(Exception e)
        {
            error = DaoError.GENERIC_ERROR;
            errorMessage = e.getMessage();
        }
        return ret;
    }
    
}
