/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

import java.util.Iterator;

/**
 * Management of the inventory
 * @author zoe
 */
public interface InventoryDTO {
    
    public Integer getId();
        
    public Gnome getBeerGnome();

    public Gnome getBeardedGnome();
    
    public Gnome getAxeGnome();
    
    public GnomeDTO getGnome(Type type);
    
    public Integer getQuantity(Type type);
    
    /**
     * Add a given amount of gnome for a given type to the inventory
     * @param amount amount of gnome to add
     * @param type type of gnome to add
     */
    public void add (Integer amount, Type type);
    
    /**
     * Removes a given amount of gnome for a given type of the inventory
     * @param amount
     * @param type 
     */
    public void remove (Integer amount, Type type);
    
    
}
