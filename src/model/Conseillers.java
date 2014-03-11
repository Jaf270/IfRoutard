/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
// * @author B3229
 */
@Entity
public class Conseillers implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    private String nom;
    private String prenom;
    private int nbDevis;
    @ManyToMany
    private List<Pays> pays;
    
    public Conseillers() {
        
    }

    public Conseillers(int num, String nom, String prenom, int nbDevis, List<Pays> pays) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.nbDevis = nbDevis;
        this.pays = pays;
    }

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
