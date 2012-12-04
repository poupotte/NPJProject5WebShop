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

/**
 * Database implementation of a customer
 * @author zoe
 */
@Entity
public class Customer implements Serializable, CustomerDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String pseudo;
    
    private String password;
    private Boolean isLog;
    private Integer money;
    private Integer debt;
    private Integer quantityBeer;
    private Integer quantityBearded;
    private Integer quantityAxe;
    private Boolean isbanned;
    
    public Customer (String pseudo,String password) {
        this.pseudo = pseudo;
        this.password = password;
        this.isLog = true;
        this.money = 100;
        this.debt = 0;
        this.quantityAxe = 0;
        this.quantityBearded = 0;
        this.quantityBeer = 0;
        this.isbanned = false;    }
    
    public Customer(){
        
    }
    
    /**************************************************************************/
    /********************** Getter and Setter *********************************/
    /**************************************************************************/
    
    @Override
    public String getId() {
        return pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setIsLog(Boolean isLog) {
        this.isLog = isLog;
    }

    @Override
    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public void setDebt(Integer debt) {
        this.debt = debt;
    }

    @Override
    public Boolean getIsbanned() {
        return isbanned;
    }

    @Override
    public void setIsbanned(Boolean isbanned) {
        this.isbanned = isbanned;
    }

    @Override
    public Integer getDebt() {
        return debt;
    }

    @Override
    public Integer getMoney() {
        return money;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Boolean getIsLog() {
        return isLog;
    }

    @Override
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String getPseudo() {
        return pseudo;
    }

    public void setId(String id) {
        this.pseudo = id;
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
    /*************************************************************************/
    /****************** Management of customer *******************************/
    /*************************************************************************/
    
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
        hash += (pseudo != null ? pseudo.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.pseudo == null && other.pseudo != null) || 
                (this.pseudo != null && !this.pseudo.equals(other.pseudo))) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "webShop.model.Customer[ id=" + pseudo + " ]";
    }
    
}
