/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import model.Devis;

/**
 *
 * @author B3229
 */
public abstract class DevisDao {
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
    
    public abstract DaoError creerDevis(Devis devis);
    public abstract Devis majDevis(Devis devis);
    public abstract DaoError supprimerDevis(Devis devis);
    public abstract Devis trouverDevisParNum(int numDevis);
    public abstract List<Devis> listerDevis();
}
