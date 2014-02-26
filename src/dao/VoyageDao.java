/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import model.Voyages;

/**
 *
 * @author B3229
 */
public abstract class VoyageDao {
    protected VoyageDaoError error = null;
    protected String errorMessage = null;
    
    /**
     *
     * @return The error indicator of the last failed call
     */
    public final VoyageDaoError getError()
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
    
    public abstract VoyageDaoError createVoyage(Voyages voyage);
    public abstract Voyages updateVoyage(Voyages voyage);
    public abstract VoyageDaoError deleteVoyage(Voyages voyage);
    public abstract Voyages findVoyageByNum(int numVoyage);
    public abstract Voyages findVoyageByRef(String uneRef);
    public abstract List<Voyages> listingVoyages();
}
