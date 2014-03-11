/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
// * @author B3229
 */
@Entity
public class Conseillers {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    private String nom;
    private String prenom;
    private int nbDevis;
    @ManyToMany
    private List<Pays> pays;

    public int getId() {
        return id;
    }
    
    public void ajouterDevis()
    {
        nbDevis++;
    }
    
    public void enleverDevis() {
        nbDevis--;
    }
    
    public boolean estSpecialise(Pays pays)
    {
        return this.pays.contains(pays); 
    }
    
}
