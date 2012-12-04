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
 *
 * @author zoe
 */
@Entity
public class Inventory implements Serializable, InventoryDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private Gnome []gnomes= new Gnome[3];;
  /*  @OneToMany(mappedBy = "inventory")
    private List<Gnome> gnomes;*/
    private final Integer beer = 0;
    private final Integer bearded = 1;
    private final Integer axe = 2;
    
    public Inventory(){
        
    }
    
    public Inventory(Integer id){
        this.id = id;
        gnomes[beer]= new Gnome(10, Type.BEER, 10, this);
        gnomes[bearded]= new Gnome(12, Type.BEARDED, 10, this);
        gnomes[axe]= new Gnome(15, Type.AXE, 10, this);
    }
    
    /**************************************************************************/
    /********************** Getter and Setter *********************************/
    /**************************************************************************/

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
      public Gnome getBeerGnome() {
        return gnomes[beer];
    }

    @Override
    public Gnome getBeardedGnome() {
        return gnomes[bearded];
    }

    @Override
    public Gnome getAxeGnome() {
        return gnomes[axe];
    }

    public void setBeerGnome(Gnome beerGnome) {
        this.gnomes[beer] = beerGnome;
    }

    public void setBeardedGnome(Gnome beardedGnome) {
        this.gnomes[bearded] = beardedGnome;
    }

    public void setAxeGnome(Gnome axeGnome) {
        this.gnomes[axe] = axeGnome;
    }
    /**************************************************************************/
    /********************** Inventory management *********************************/
    /**************************************************************************/
    @Override
    public void add (Integer amount, Type type) {
        switch (type) {
            case BEER : 
                gnomes[beer].setAmount(gnomes[beer].getAmount() + amount);
                break;
            case BEARDED :
                gnomes[bearded].setAmount(gnomes[bearded].getAmount() + amount);
                break;
            case AXE :
                gnomes[axe].setAmount(gnomes[axe].getAmount() + amount);
                break;
        }
    }
    
    @Override
    public Integer getQuantity(Type type){
        switch (type) {
            case BEER : 
                return gnomes[beer].getAmount();
            case BEARDED :
                return gnomes[bearded].getAmount();
            case AXE :
                return gnomes[axe].getAmount();
        }
        return null;
        
    }
    
    @Override
    public void remove (Integer amount, Type type) {
        switch (type) {
            case BEER : 
                gnomes[beer].setAmount(gnomes[beer].getAmount() - amount);
                break;
            case BEARDED :
                gnomes[bearded].setAmount(gnomes[bearded].getAmount() - amount);
                break;
            case AXE :
                gnomes[axe].setAmount(gnomes[axe].getAmount() - amount);
                break;
        }
    }
    
    public void addNewGnome(Gnome gnome){
        switch (gnome.getType()) {
            case BEER : 
                gnomes[beer] = gnome;
                break;
            case BEARDED :
                gnomes[bearded] = gnome;
                break;
            case AXE :
                gnomes[axe] = gnome;
                break;
        }
    }
    
    public GnomeDTO getGnome(Type type){
        switch (type) {
            case BEER : 
                return gnomes[beer];
            case BEARDED :
                return gnomes[bearded];
            case AXE :
                return gnomes[axe] ;
        }
        return null;
    }
    
        
    /**************************************************************************/
    /********************** HashCode and Equals *********************************/
    /**************************************************************************/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webShop.model.Inventory[ id=" + id + " ]";
    }
    
}
