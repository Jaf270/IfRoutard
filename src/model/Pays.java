/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author B3229
 */
@Entity
public class Pays implements Serializable {
   
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    private String nom;
    private String capitale;
    private int superficie;
    private long nbHab;
    private String langue;

    public Pays() {
    }

    public Pays(int num, String nom, String capitale, int superficie, long nbHab, String langue) {
        this.num = num;
        this.nom = nom;
        this.capitale = capitale;
        this.superficie = superficie;
        this.nbHab = nbHab;
        this.langue = langue;
    }
    
    public int getId() {
        return id;
    }

    public int getNum() {
        return num;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.num;
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
        final Pays other = (Pays) obj;
        if (this.num != other.num) {
            return false;
        }
        return true;
    }
    
    
    
}
