/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

import java.util.Iterator;

/**
 *
 * @author zoe
 */
public interface InventoryDTO {
    
    public Integer getId();
        
    public Gnome getBeerGnome();

    public Gnome getBeardedGnome();
    
    public Gnome getAxeGnome();
    
    /*public Iterator<Gnome> getGnomes();
    
    public void addGnome(Gnome gnome);
    
    public void removeGnome(Gnome gnome);*/
    
    public void add (Integer amount, Type type);
    
    public void remove (Integer amount, Type type);
    
    public void addNewGnome(Gnome gnome);
    
    public Integer getQuantity(Type type);
}
