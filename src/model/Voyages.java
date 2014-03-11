
package model;

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

/**
 *
 * @author B3229
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Voyages {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int num;
    private String reference;
    private int durée;  
    @ManyToOne (cascade = CascadeType.ALL)
    private Pays pays;
    @Enumerated (EnumType.STRING)
    private TypeVoyage type;
    
    public int getId() {
        return id;
    }

    public int getNum() {
        return num;
    }
    
    
}
