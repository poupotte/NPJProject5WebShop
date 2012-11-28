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
public class AxeGnome extends Gnome implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer axeSize;

    public AxeGnome() {
    }
    
    public AxeGnome(Integer price, Integer axeSize) {
        this.price = price;
        this.axeSize = axeSize;
    }

    public Integer getAxeSize() {
        return axeSize;
    }

    public void setAxeSize(Integer axeSize) {
        this.axeSize = axeSize;
    }

    
    
}
