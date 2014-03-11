/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author B3229
 */
@Entity
public class Periodes implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String ville;
    @Temporal(TemporalType.DATE)
    private Calendar datep;
    private int montant;
    @ManyToMany
    private TypeTransport typeTransport;

    public Periodes() {
    }

    public Periodes(String ville, Calendar datep, int montant, TypeTransport typeTransport) {
        this.ville = ville;
        this.datep = datep;
        this.montant = montant;
        this.typeTransport = typeTransport;
    }
    
    public int getId() {
        return id;
    }
    
}
