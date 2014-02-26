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
public class TypeTransport {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String type;
    private String description;

    public int getId() {
        return id;
    }
    
}
