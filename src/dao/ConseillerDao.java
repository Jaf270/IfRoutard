/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import model.Conseillers;
import model.Pays;

/**
 *
 * @author B3229
 */
public abstract class ConseillerDao {
    protected DaoError error = null;
    protected String errorMessage = null;
    
    /**
     *
     * @return The error indicator of the last failed call
     */
    public final DaoError getError()
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
    
    public abstract DaoError creerConseiller(Conseillers conseiller);
    public abstract Conseillers majConseiller(Conseillers conseiller);
    public abstract DaoError supprimerConseiller(Conseillers conseiller);
    public abstract Conseillers trouverConseillerParNum(int numConseiller);
    public abstract Conseillers trouverConseillerAdequat(Pays pays);
    public abstract List<Conseillers> listerConseillers();
}
