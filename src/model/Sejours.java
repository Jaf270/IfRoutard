/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author B3229
 */
@Entity
public class Sejours extends Voyages implements Serializable {
    private String residence;

    public Sejours() {
        super();
    }

    public Sejours(String residence, int num, String reference, int duree, Pays pays, TypeVoyage type) {
        super(num, reference, duree, pays, type);
        this.residence = residence;
    }
    
    
    
}
