package util;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Adresse;
import model.Circuits;
import model.Civilite;
import model.Client;
import model.Conseillers;
import model.Pays;
import model.Periodes;
import model.Sejours;
import model.TypeTransport;
import model.TypeVoyage;
import model.Voyages;
import service.Service;
import service.ServiceError;

/**
 * La classe LectureDonneesCsv permet (comme son nom l'indique) la lecture de données CSV
 * dans des fichiers. Elle doit être complétée et personnalisée pour interagir avec VOTRE couche
 * service pour la création effective des entités. Elle comprend initialement la lecture d'un fichier
 * Clients et d'un fichier Pays. Une méthode {@link main()} permet de tester cette classe avant de
 * l'intégrer dans le reste de votre code.
 * @author Équipe DASI - 2013/2014
 */

public class LectureDonneesCsv {

    /**
     * Format de date pour la lecture des dates dans les fichiers CSV fournis.
     */
    protected static DateFormat CSV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Format de date pour l'affichage à l'écran.
     */
    protected static DateFormat USER_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Le lecteur de fichier CSV.
     * Il doit être initialisé avant l'appel aux méthodes de la classe.
     */
    protected CSVReader lecteurFichier;

    /**
     * Unique constructeur de la classe. Le fichier CSV donné en paramètre doit
     * avoir le point-virgule ';' comme séparateur et être encodé en UTF-8. Le fichier est
     * immédiatement ouvert (en lecture) par ce constructeur.
     * @param cheminVersFichier Chemin vers le fichier CSV.
     * @throws FileNotFoundException Si le chemin vers le fichier n'est pas valide ou le fichier non-lisible.
     */
    public LectureDonneesCsv(String cheminVersFichier) throws FileNotFoundException, UnsupportedEncodingException {

        this.lecteurFichier = new CSVReader(new InputStreamReader(new FileInputStream(cheminVersFichier), "UTF-8"), ';');
    }
    
    /**
     * Ferme le fichier CSV. Les autres méthodes ne doivent plus être appelées après cela.
     * @throws IOException 
     */
    public void fermer() throws IOException {

        this.lecteurFichier.close();
    }

    /**
     * Méthode statique pour lire une date à partir d'une chaîne de caractère.
     * Adapté au format de date des fichiers CSV fournis, par exemple: 2014-02-01.
     * @param date Chaîne de caractère représentant la date.
     * @return La date interpétée ou la date actuelle en cas mauvais format en entrée.
     */
    protected static Date parseDate(String date) {
        try {
            return CSV_DATE_FORMAT.parse(date);
        } catch (ParseException ex) {
            return new Date();
        }
    }
    
    /**
     * Méthode statique pour formater une date pour l'affichage.
     * Par exemple: 01/02/2014.
     * @param date Date à formater.
     * @return Chaîne de caractère représentant la date.
     */
    protected static String formatDate(Date date) {
        return USER_DATE_FORMAT.format(date);
    }

    /**
     * Méthode statique pour afficher l'en-tête d'un fichier CSV lu par le lecteur.
     * L'affichage se fait sur la "sortie d'erreur" (en rouge dans la console sous Netbeans).
     * Le nom des colonnes est précédé de leur index dans le tableau (commençant à 0).
     * @param colonnes le tableau des noms de colonnes.
     */
    protected static void afficherEnTeteCsv(String[] colonnes) {
        
        for (int i=0; i<colonnes.length; i++) {
            if (i>0) {
                System.err.print("; " );
            }
            System.err.print("#" + Integer.toString(i) + " => " + colonnes[i] );
        }
        System.err.println();
    }
    
    /**
     * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Client pour chaque ligne.
     * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
     * @throws IOException 
     */
    public void lireClients(int limite) throws IOException {

        String[] nextLine;

         // En-tete du fichier CSV
        nextLine = this.lecteurFichier.readNext();
        afficherEnTeteCsv(nextLine);
        
        
        // Lecture des lignes
        while ((nextLine = this.lecteurFichier.readNext()) != null) {
        
            creerClient(nextLine);
            
            // Limite (ou -1 si pas de limite)
            if ( !(limite < 0) && (--limite < 1) ) {
                break;
            }
        }

    }
    
    /**
     * Créée un Client à partir de sa description.
     * La date de naissance est notamment interpétée comme un objet Date.
     * @param descriptionClient Ligne du fichier CSV de Clients.
     */
    public void creerClient(String[] descriptionClient) {
        
        String civilite = descriptionClient[0];
        String nom = descriptionClient[1];
        String prenom = descriptionClient[2];
        Date dateNaissance = parseDate(descriptionClient[3]);
        String adresse = descriptionClient[4];
        String telephone = descriptionClient[5];
        String email = descriptionClient[6];
        
        Civilite civ;
        if(civilite.equals("M."))
            civ = Civilite.MASCULIN;
        else if(civilite.equals("Mme"))
            civ = Civilite.FEMININ;
        else
            civ = Civilite.AUTRE;
        Calendar cal = Calendar.getInstance();
        CSV_DATE_FORMAT.format(dateNaissance);
        cal = CSV_DATE_FORMAT.getCalendar();
        Client client = new Client(0, civ, nom, prenom, cal, adresse, email, telephone, true, null);
        Service.InscriptionClient(client);
        if(Service.getError() != ServiceError.OK)
            System.out.println(Service.getErrorMessage());
    }

    /**
     * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Pays pour chaque ligne.
     * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
     * @throws IOException 
     */
    public void lirePays(int limite) throws IOException {

        String[] nextLine;

         // En-tete du fichier CSV
        nextLine = this.lecteurFichier.readNext();
        afficherEnTeteCsv(nextLine);
        
        
        // Lecture des lignes
        while ((nextLine = this.lecteurFichier.readNext()) != null) {
        
            creerPays(nextLine);
            
            // Limite (ou -1 si pas de limite)
            if ( !(limite < 0) && (--limite < 1) ) {
                break;
            }
        }

    }
    
    /**
     * Créée un Pays à partir de sa description.
     * La superficie et la population sont notamment interpétées comme des nombres.
     * @param descriptionClient Ligne du fichier CSV de Pays.
     */
    public void creerPays(String[] descriptionPays) {
        
        String nom = descriptionPays[0];
        String code = descriptionPays[1];
        String region = descriptionPays[2];
        String capitale = descriptionPays[3];
        String langues = descriptionPays[4];
        Integer superficie = Integer.parseInt(descriptionPays[5]);
        float population = Float.parseFloat(descriptionPays[6]);
        String regime = descriptionPays[7];
        
        Pays pays = new Pays(code, nom, capitale, superficie, population, langues);
        Service.AjouterPays(pays);
        if(Service.getError() != ServiceError.OK)
            System.out.println(Service.getErrorMessage());
    }
    
    /**
     * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Circuits pour chaque ligne.
     * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
     * @throws IOException 
     */
    public void lireCircuits(int limite) throws IOException {

        String[] nextLine;

         // En-tete du fichier CSV
        nextLine = this.lecteurFichier.readNext();
        afficherEnTeteCsv(nextLine);
        
        
        // Lecture des lignes
        while ((nextLine = this.lecteurFichier.readNext()) != null) {
        
            creerCircuit(nextLine);
            
            // Limite (ou -1 si pas de limite)
            if ( !(limite < 0) && (--limite < 1) ) {
                break;
            }
        }

    }
    
    /**
     * Créée un Circuit à partir de sa description.
     * @param descriptionCircuit Ligne du fichier CSV de Circuit.
     */
    public void creerCircuit(String[] descriptionCircuit) {
        
        String codePays = descriptionCircuit[0];
        String codeVoyage = descriptionCircuit[1];
        String intitule = descriptionCircuit[2];
        int duree = Integer.parseInt(descriptionCircuit[3]);
        String description = descriptionCircuit[4];
        String transport = descriptionCircuit[5];
        int longueur = Integer.parseInt(descriptionCircuit[6]);
        
        Pays pays = Service.DetailPays(codePays);
        if(Service.getError() == ServiceError.OK)
        {
            Voyages circuit = new Circuits(transport, longueur, 0, codeVoyage, duree, pays, TypeVoyage.CIRCUIT, null);
            Service.AjouterVoyage(circuit);
            if(Service.getError() != ServiceError.OK)
                System.out.println(Service.getErrorMessage());
        }
    }
    
    /**
     * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Sejours pour chaque ligne.
     * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
     * @throws IOException 
     */
    public void lireSejours(int limite) throws IOException {

        String[] nextLine;

         // En-tete du fichier CSV
        nextLine = this.lecteurFichier.readNext();
        afficherEnTeteCsv(nextLine);
        
        
        // Lecture des lignes
        while ((nextLine = this.lecteurFichier.readNext()) != null) {
        
            creerSejour(nextLine);
            
            // Limite (ou -1 si pas de limite)
            if ( !(limite < 0) && (--limite < 1) ) {
                break;
            }
        }

    }
    
    /**
     * Créée un Séjour à partir de sa description.
     * @param descriptionSejour Ligne du fichier CSV de Sejour.
     */
    public void creerSejour(String[] descriptionSejour) {
        
        String codePays = descriptionSejour[0];
        String codeVoyage = descriptionSejour[1];
        String intitule = descriptionSejour[2];
        int duree = Integer.parseInt(descriptionSejour[3]);
        String description = descriptionSejour[4];
        String residence = descriptionSejour[5];
        
        Pays pays = Service.DetailPays(codePays);
        if(Service.getError() == ServiceError.OK)
        {
            Voyages sejour = new Sejours(residence, 0, codeVoyage, duree, pays, TypeVoyage.SEJOUR, null);
            Service.AjouterVoyage(sejour);
            if(Service.getError() != ServiceError.OK)
                System.out.println(Service.getErrorMessage());
        }
    }
    
    /**
     * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Conseillers pour chaque ligne.
     * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
     * @throws IOException 
     */
    public void lireConseillers(int limite) throws IOException {

        String[] nextLine;

         // En-tete du fichier CSV
        nextLine = this.lecteurFichier.readNext();
        afficherEnTeteCsv(nextLine);
        
        
        // Lecture des lignes
        while ((nextLine = this.lecteurFichier.readNext()) != null) {
        
            creerConseiller(nextLine);
            
            // Limite (ou -1 si pas de limite)
            if ( !(limite < 0) && (--limite < 1) ) {
                break;
            }
        }

    }
    
    /**
     * Créée un Conseiller à partir de sa description.
     * @param descriptionConseiller Ligne du fichier CSV de Conseiller.
     */
    public void creerConseiller(String[] descriptionConseiller) {
        
        String civilite = descriptionConseiller[0];
        String nom = descriptionConseiller[1];
        String prenom = descriptionConseiller[2];
        String dateNaiss = descriptionConseiller[3];
        String adresse = descriptionConseiller[4];
        String telephone = descriptionConseiller[5];
        String email = descriptionConseiller[6];
        
        List<Pays> pays = new ArrayList<Pays>();
        int nbPays = 10;
        for(int i=7; i<nbPays+7; i++)
        {
            Pays temp = Service.DetailPays(descriptionConseiller[i]);
            if(Service.getError() == ServiceError.OK)
                pays.add(temp);
            else
                break;
        }
        if(!pays.isEmpty() && pays.size() == nbPays)
        {
            Conseillers cons = new Conseillers(0, nom, prenom, 0, pays);
            Service.ajouterConseiller(cons);
            if(Service.getError() != ServiceError.OK)
                System.out.println(Service.getErrorMessage());
        }
    }
    
    /**
     * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Periodes pour chaque ligne.
     * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
     * @throws IOException 
     */
    public void lirePeriodes(int limite) throws IOException {

        String[] nextLine;

         // En-tete du fichier CSV
        nextLine = this.lecteurFichier.readNext();
        afficherEnTeteCsv(nextLine);
        
        
        // Lecture des lignes
        while ((nextLine = this.lecteurFichier.readNext()) != null) {
        
            creerPeriode(nextLine);
            
            // Limite (ou -1 si pas de limite)
            if ( !(limite < 0) && (--limite < 1) ) {
                break;
            }
        }

    }
    
    /**
     * Créée une Periode à partir de sa description.
     * @param descriptionPeriode Ligne du fichier CSV de Periode.
     */
    public void creerPeriode(String[] descriptionPeriode) {
        
        String codeVoyage = descriptionPeriode[0];
        Date nom = parseDate(descriptionPeriode[1]);
        String ville = descriptionPeriode[2];
        int montant = Integer.parseInt(descriptionPeriode[3]);
        
        List<TypeTransport> transports = new ArrayList<TypeTransport>();
        transports.add(new TypeTransport(descriptionPeriode[4]));
        
        CSV_DATE_FORMAT.format(nom);
        Calendar cal = CSV_DATE_FORMAT.getCalendar();
        
        Voyages voyage = Service.DetailsVoyage(codeVoyage);
        if(Service.getError() == ServiceError.OK)
        {
            Periodes periode = new Periodes(ville, cal, montant, transports);
            voyage.ajouterPeriode(periode);
            Service.EditionVoyage(voyage);
            if(Service.getError() != ServiceError.OK)
                System.out.println(Service.getErrorMessage());
        }
    }
    
    /**
     * Cette méthode main() permet de tester cette classe avant de l'intégrer dans votre code.
     * Elle exploite initialement un fichier de Client et un fichier de Pays, en limitant la lecture aux
     * 10 premiers éléments de chaque fichier.
     * @param args non utilisé ici
     */
    public static void main(String[] args) {
        
        try {
            String racineProjet = System.getProperty("user.dir");
            String separator = "/";
            List<String> dirs = Arrays.asList("src","util","data");
            String cheminFichier = racineProjet+separator;
            for(String s:dirs)
            {
                cheminFichier += s+separator;
            }
            
            String fichierClients = cheminFichier+"IFRoutard-Clients.csv";
            String fichierPays = cheminFichier+"IFRoutard-Pays.csv";
            String fichierConseillers = cheminFichier+"IFRoutard-Conseillers.csv";
            String fichierDeparts = cheminFichier+"IFRoutard-Departs.csv";
            String fichierCircuits = cheminFichier+"IFRoutard-Voyages-Circuits.csv";
            String fichierSejours = cheminFichier+"IFRoutard-Voyages-Sejours.csv";
            
            LectureDonneesCsv lectureDonneesCsv_Clients = new LectureDonneesCsv(fichierClients);
            lectureDonneesCsv_Clients.lireClients(10);
            lectureDonneesCsv_Clients.fermer();

            LectureDonneesCsv lectureDonneesCsv_Pays = new LectureDonneesCsv(fichierPays);
            lectureDonneesCsv_Pays.lirePays(10);
            lectureDonneesCsv_Pays.fermer();
            
            LectureDonneesCsv lectureDonneesCsv_Circuits = new LectureDonneesCsv(fichierCircuits);
            lectureDonneesCsv_Circuits.lireCircuits(10);
            lectureDonneesCsv_Circuits.fermer();
            
            LectureDonneesCsv lectureDonneesCsv_Sejours = new LectureDonneesCsv(fichierSejours);
            lectureDonneesCsv_Sejours.lireSejours(10);
            lectureDonneesCsv_Sejours.fermer();
            
            LectureDonneesCsv lectureDonneesCsv_Conseillers = new LectureDonneesCsv(fichierConseillers);
            lectureDonneesCsv_Conseillers.lireConseillers(10);
            lectureDonneesCsv_Conseillers.fermer();
            
            LectureDonneesCsv lectureDonneesCsv_Periodes = new LectureDonneesCsv(fichierDeparts);
            lectureDonneesCsv_Periodes.lirePeriodes(10);
            lectureDonneesCsv_Periodes.fermer();
            
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

    }
}
