/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifroutard;

import java.util.Calendar;
import model.Adresse;
import model.Civilite;
import model.Client;
import model.Pays;
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
        Pays pays = new Pays("A", "Test", "Testville", 123, 4, "Testois");
        
        Service.AjouterPays(pays);
    }
}
