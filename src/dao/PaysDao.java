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
public abstract class PaysDao {
    protected PaysDaoError error = null;
    protected String errorMessage = null;
    
    /**
     *
     * @return The error indicator of the last failed call
     */
    public final PaysDaoError getError()
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
    
    public abstract PaysDaoError createPays(Pays pays);
    public abstract Pays updatePays(Pays pays);
    public abstract PaysDaoError deletePays(Pays pays);
    public abstract Pays findPaysByNum(int numPays);
    public abstract Pays findPaysByName(String unNom);
    public abstract List<Pays> listingPays();
}
