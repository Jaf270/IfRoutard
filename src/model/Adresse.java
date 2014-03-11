/*
 * To change this template, choose Tools | Templates
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
public class Adresse implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int noRue;
    private String nomRue;
    private String zipCode;
    private String ville;

    public Adresse() {
    }

    public Adresse(int noRue, String nomRue, String zipCode, String ville) {
        this.noRue = noRue;
        this.nomRue = nomRue;
        this.zipCode = zipCode;
        this.ville = ville;
    }
    
   
}
