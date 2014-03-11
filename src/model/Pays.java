/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author B3229
 */
@Entity
public class Pays {
   
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    private String nom;
    private String capitale;
    private int superficie;
    private long nbHab;
    private String langue;

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
