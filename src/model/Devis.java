/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Devis {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int numDevis;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Calendar date;
    private int nbPersonne;
    private int montant;
    @ManyToOne (cascade = CascadeType.ALL)
    private Conseillers conseiller;
    @ManyToOne (cascade = CascadeType.ALL)
    private Client client;
    @ManyToOne (cascade = CascadeType.ALL)
    private Voyages voyage;
    @ManyToOne (cascade = CascadeType.ALL)
    private Periodes periode;

    public int getId() {
        return id;
    }
    
}
