/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

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
public class Periodes {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String ville;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Calendar date;
    private int montant;
    @ManyToMany
    private TypeTransport typeTransport;

    public int getId() {
        return id;
    }
    
}
