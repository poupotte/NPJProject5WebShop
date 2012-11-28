/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author zoe
 */
@Entity
public class BeardGnome extends Gnome implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer BeardLength;

    public BeardGnome() {
    }

    public BeardGnome(Integer price, Integer BeardLength) {
        this.price = price;
        this.BeardLength = BeardLength;
    }
    
    

    public Integer getBeardLength() {
        return BeardLength;
    }

    public void setBeardLength(Integer ageOfBeard) {
        this.BeardLength = ageOfBeard;
    }

   
    
}
