/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
    private Integer quantityBeer;
    private Integer quantityBearded;
    private Integer quantityAxe;
    @OneToOne (cascade = CascadeType.PERSIST)
    private Customer customer;
    

    public Basket() {
    }

    public Basket(Integer quantityBeer, Integer quantityBearded, Integer quantityAxe, Customer customer) {
        this.quantityBeer = quantityBeer;
        this.quantityBearded = quantityBearded;   
        this.quantityAxe = quantityAxe;   
        this.customer = customer;
    }

    public Integer getQuantityBeer() {
        return quantityBeer;
    }

    public Integer getQuantityBearded() {
        return quantityBearded;
    }

    public Integer getQuantityAxe() {
        return quantityAxe;
    }

    public void setQuantityBeer(Integer quantityBeer) {
        this.quantityBeer = quantityBeer;
    }

    public void setQuantityBearded(Integer quantityBearded) {
        this.quantityBearded = quantityBearded;
    }

    public void setQuantityAxe(Integer quantityAxe) {
        this.quantityAxe = quantityAxe;
    }
    
    @Override
    public void add (Integer amount, Type type) {
        switch (type) {
            case BEER : 
                quantityBeer = quantityBeer + amount;
                break;
            case BEARDED :
                quantityBearded = quantityBearded + amount;
                break;
            case AXE :
                quantityAxe = quantityAxe + amount;
                break;
        }
    }
        
    @Override
        public void emptyBasket(){
            quantityBeer = 0;
            quantityBearded = 0;
            quantityAxe = 0;
        }
    
    @Override
    public Integer getQuantity(Type type){
        switch (type) {
            case BEER : 
                return quantityBeer;
            case BEARDED :
                return quantityBearded;
            case AXE :
                return quantityAxe;
        }
        return null;
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