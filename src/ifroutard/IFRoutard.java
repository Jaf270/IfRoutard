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
        Client c1 = new Client(1234, Civilite.MASCULIN, "Losbar", "Thomas", cal, new Adresse(53, "rue courte","31000","Toulouse"),email,"6646464",true,motDePasse);
        
        serv.InscriptionClient(c1);
        System.out.println(serv.getError().name());
        serv.ConnexionClient(email, motDePasse);
        System.out.println(serv.getError().name());
        System.out.println(serv.getErrorMessage());
    }
}
