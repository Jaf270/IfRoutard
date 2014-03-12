/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifroutard;

import java.util.Calendar;
import model.Adresse;
import model.Civilite;
import model.Client;
import service.Service;

/**
 *
 * @author B3229
 */
public class IFRoutard {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        service.Service serv = new Service();
        Calendar cal = Calendar.getInstance();
        cal.set(1993, 7, 24);
        String email = "thomas.losbar@insa-l.fr";
        String motDePasse = "test";
    }
}
