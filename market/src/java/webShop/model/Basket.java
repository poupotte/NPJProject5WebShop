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
public class Basket implements Serializable, BasketDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Gnome gnome;
    @ManyToOne
    private Customer customer;

    public Basket() {
    }

    public Basket(Gnome gnome, Customer customer) {
        this.gnome = gnome;
        this.customer = customer;
    }
    
    public Integer getId() {
        return id;
    }

    public Gnome getGnome() {
        return gnome;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setGnome(Gnome gnome) {
        this.gnome = gnome;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Basket)) {
            return false;
        }
        Basket other = (Basket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webShop.model.Basket[ id=" + id + " ]";
    }
    
}