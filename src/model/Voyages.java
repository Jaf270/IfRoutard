
package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author B3229
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Voyages implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    private String reference;
    private int duree;  
    @ManyToOne
    private Pays pays;
    @Enumerated (EnumType.STRING)
    private TypeVoyage type;
    @OneToMany
    private List<Periodes> periodes;
    
    public Voyages() {
        
    }

    public Voyages(int num, String reference, int duree, Pays pays, TypeVoyage type, List<Periodes> periodes) {
        this.num = num;
        this.reference = reference;
        this.duree = duree;
        this.pays = pays;
        this.type = type;
        this.periodes = periodes;
    }
    
    public int getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public Pays getPays() {
        return pays;
    }
    
    public void ajouterPeriode(Periodes periode)
    {
        this.periodes.add(periode);
    }
    
    public Periodes getRandomPeriode()
    {
        List<Periodes> p = this.periodes;
        Collections.shuffle(p);
        return p.get(0);
    }
    
}
