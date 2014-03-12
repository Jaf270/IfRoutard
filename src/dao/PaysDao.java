/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import model.Pays;


/**
 *
 * @author B3229
 */
public abstract class PaysDao {
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
    
    public abstract DaoError creerPays(Pays pays);
    public abstract Pays majPays(Pays pays);
    public abstract DaoError supprimerPays(Pays pays);
    public abstract Pays trouverPaysParCode(String codePays);
    public abstract Pays trouverPaysParNom(String unNom);
    public abstract List<Pays> listerPays();
}
