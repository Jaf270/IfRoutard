/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author B3229
 */
@Entity
public class Devis implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    @Temporal(TemporalType.DATE)
    private Calendar datec;
    private int nbPersonne;
    private int montant;
    @ManyToOne
    private Conseillers conseiller;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Voyages voyage;
    @ManyToOne
    private Periodes periode;

    public Devis() {
        
    }

    public Devis(int num, Calendar datec, int nbPersonne, Client client, Voyages voyage, Periodes periode) {
        this.num = num;
        this.datec = datec;
        this.nbPersonne = nbPersonne;
        this.montant = periode.getMontantTotal(nbPersonne);
        this.conseiller = null;
        this.client = client;
        this.voyage = voyage;
        this.periode = periode;
    }
    
    public int getId() {
        return id;
    }
    
    public void setConseiller(Conseillers cons)
    {
        this.conseiller = cons;
    }
    
    public Pays getPays() {
        return voyage.getPays();
    }
    
    public String toString()
    {
        return "Devis vers "+voyage.getPays().getCode()+" pour "+nbPersonne+" personnes à "+montant+" euros";
    }
    
}
