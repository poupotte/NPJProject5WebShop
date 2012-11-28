/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author zoe
 */
@Entity
public class Gnome implements Serializable, GnomeDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Type type;
    private Integer price;
    private Integer amount;
    @ManyToOne ()
    private Inventory inventory;

    public Gnome() {
    }

    public Gnome(Integer price, Type type, Integer amount, Inventory inventory) {
        this.price = price;
        this.type = type;
        this.amount = amount;
        this.inventory = inventory;
    }
    

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webShop.model.Gnome[ id=" + type + " ]";
    }
    
}
