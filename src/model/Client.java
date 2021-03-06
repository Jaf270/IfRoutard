/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author B3229
 */
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue //(strategy= GenerationType.AUTO)
    private int id;
    private int numClient;
    @Enumerated(EnumType.STRING)
    private Civilite civilite;
    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Calendar dateNaissance;
    private String adrPostale;
    private String adrMail;
    private String telephone;
    private boolean contactPart;
    private String motDePasse;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    
    public int getNum() {
        return numClient;
    }
    
    public String getEMail() {
        return adrMail;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }

    public Client() {
    }

    public Client(int numClient, Civilite civilite, String nom, String prenom, Calendar dateField, String adrPostale, String adrMail, String telephone, boolean contactPart, String motDePasse) {
        this.numClient = numClient;
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateField;
        this.adrPostale = adrPostale;
        this.adrMail = adrMail;
        this.telephone = telephone;
        this.contactPart = contactPart;
        this.motDePasse = motDePasse;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.adrMail.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!this.adrMail.equals(other.adrMail)) {
            return false;
        }
        return true;
    }

    
}
