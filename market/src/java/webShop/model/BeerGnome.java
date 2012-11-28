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
public class BeerGnome extends Gnome implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer liter;

    public BeerGnome() {
    }

    public BeerGnome(Integer price, Integer liter) {
        this.price = price;
        this.liter = liter;
    }

    public Integer getLiter() {
        return liter;
    }

    public void setLiter(Integer liter) {
        this.liter = liter;
    }

  
    
}
