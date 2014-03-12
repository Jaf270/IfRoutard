/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifroutard;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Adresse;
import model.Civilite;
import model.Client;
import model.Conseillers;
import model.Devis;
import model.Pays;
import model.Periodes;
import model.TypeVoyage;
import model.Voyages;
import service.Service;
import service.ServiceError;
import util.Saisie;

/**
 *
 * @author B3229
 */
public class IFRoutard {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Client> clis = Service.listerClients();
        System.out.println("Il y a "+clis.size()+" clients");
        for(Client c:clis)
        {
            System.out.println(c.getEMail());
        }
        
        System.out.println("Inscription interactive d'un client");
        int c = Saisie.lireInteger("Civilité ? (1) MASCULIN (2) FEMININ");
        Civilite civ;
        if(c==1)
            civ = Civilite.MASCULIN;
        else if(c==2)
            civ = Civilite.FEMININ;
        else
            civ = Civilite.AUTRE;
        String nom = Saisie.lireChaine("Nom ?");
        String prenom = Saisie.lireChaine("Prenom ?");
        int annee = Saisie.lireInteger("Année de naissance ?");
        int mois = Saisie.lireInteger("Mois ?");
        int jour = Saisie.lireInteger("Jour ?");
        String dateNaiss = annee+"-"+mois+"-"+jour;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dn = new Date();
        try {
            dn = format.parse(dateNaiss);
        } catch (ParseException ex) {
            Logger.getLogger(IFRoutard.class.getName()).log(Level.SEVERE, null, ex);
        }
        format.format(dn);
        Calendar cal = format.getCalendar();
        String adresse = Saisie.lireChaine("Adresse postale ?");
        String email = Saisie.lireChaine("Adresse email ?");
        String telephone = Saisie.lireChaine("Téléphone ?");
        String motDePasse = Saisie.lireChaine("Mot de passe ?");
        
        Client cli = new Client(0, civ, nom, prenom, cal, adresse, email, telephone, true, motDePasse);
        Service.InscriptionClient(cli);
        if(Service.getError() == ServiceError.OK)
        {
            Service.ConnexionClient(email, motDePasse);
            if(Service.getError() == ServiceError.OK)
            {
                System.out.println("Client créé avec succès !");
            }
            else
            {
                System.err.println(Service.getErrorMessage());
            }
        }
        else
        {
            System.err.println(Service.getErrorMessage());
        }
        
        TypeVoyage tv;
        int tve = Saisie.lireInteger("Sélectionner type de voyage : (1) Circuit (2) Sejour");
        if(tve == 1)
            tv = TypeVoyage.CIRCUIT;
        else
            tv = TypeVoyage.SEJOUR;
        
        List<Pays> pays = Service.RecherchePays();
        for(int i = 0;i<pays.size();i++)
        {
            System.out.println(i+" - "+pays.get(i).getCode());
        }
        String codePays = Saisie.lireChaine("Entrez le code du pays voulu");
        List<Voyages> voyages = Service.RechercheVoyage(tv, Service.DetailPays(codePays));
        if(Service.getError() != ServiceError.OK)
        {
            System.err.println(Service.getErrorMessage());
        }
        for(int i=0; i<voyages.size();i++)
        {
            System.out.println(i+" - "+voyages.get(i).getReference());
        }
        String refVoy = Saisie.lireChaine("Entrez la référence du voyage voulu");
        Voyages voy = Service.DetailsVoyage(refVoy);
        
        int nbPers = Saisie.lireInteger("Combien de personnes ?");
        
        List<Periodes> periodes = voy.getPeriodes();
        for(int i=0; i<periodes.size();i++)
        {
            System.out.println(i+" - "+periodes.get(i).toString());
        }
        int idPer = Saisie.lireInteger("Quelle période ?");
        Periodes per = periodes.get(idPer);
        
        Calendar calNow = Calendar.getInstance();
        Devis dev = new Devis(0, calNow, nbPers, cli, voy, per);
        Conseillers cons = Service.CréerDevis(dev);
        if(Service.getError() == ServiceError.OK)
        {
            System.out.println("Devis créé pour le client "+cli.getEMail()+" avec "+cons.getNom()+" comme conseiller");
            List<Devis> devisCli = Service.listerDevisParClient(cli);
            if(Service.getError() == ServiceError.OK)
            {
                System.out.println("Les devis du client "+cli.getEMail()+" sont :");
                for(Devis d:devisCli)
                {
                    System.out.println(d.toString());
                }
            }
        }
        else
        {
            System.err.println(Service.getErrorMessage());
        }
    }
}
