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
    private String code;
    private String nom;
    private String capitale;
    private int superficie;
    private float nbHab;
    private String langue;

    public Pays() {
    }

    public Pays(String code, String nom, String capitale, int superficie, float nbHab, String langue) {
        this.code = code;
        this.nom = nom;
        this.capitale = capitale;
        this.superficie = superficie;
        this.nbHab = nbHab;
        this.langue = langue;
    }
    
    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.code.hashCode();
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
        if (!this.code.equals(other.code)) {
            return false;
        }
        return true;
    }
    
    
    
}
