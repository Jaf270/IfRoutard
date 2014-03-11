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
public class Circuits extends Voyages implements Serializable {
    private String transport;
    private int nbKm;

    public Circuits() {
        super();
    }

    public Circuits(String transport, int nbKm, int num, String reference, int duree, Pays pays, TypeVoyage type) {
        super(num, reference, duree, pays, type);
        this.transport = transport;
        this.nbKm = nbKm;
    }
}
