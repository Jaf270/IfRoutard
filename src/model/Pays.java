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
    private int numPays;
    private String nom;
    private String capitale;
    private int superficie;
    private long nbHab;
    private String langue;

    public int getId() {
        return id;
    }
    
}
