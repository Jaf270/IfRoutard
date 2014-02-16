/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifroutard;

import model.Client;
import dao.JpaUtil;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Adresse;
import model.Civilite;
import service.Service;

/**
 *
 * @author Administrateur
 */
public class IFRoutard {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        service.Service serv = new Service();
        Calendar cal = Calendar.getInstance();
        cal.set(1993, 7, 24);
        Client c1 = new Client(1234, Civilite.MASCULIN, "Losbar", "Thomas", cal, new Adresse(53, "rue courte","31000","Toulouse"),"thomas.losbar@insa-lyon.fr","6646464",true,"test");
        
        serv.test(c1);
        
    }
}
