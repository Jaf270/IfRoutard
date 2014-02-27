/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import model.Pays;
import model.Voyages;

/**
 *
 * @author B3229
 */
public abstract class VoyageDao {
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
    
    public abstract DaoError creerVoyage(Voyages voyage);
    public abstract Voyages majVoyage(Voyages voyage);
    public abstract DaoError supprimerVoyage(Voyages voyage);
    public abstract Voyages trouverVoyageParNum(int numVoyage);
    public abstract Voyages trouverVoyageParRef(String uneRef);
    public abstract List<Voyages> listerVoyages();
    public abstract List<Voyages> listerVoyagesParPays(Pays pays);
}
