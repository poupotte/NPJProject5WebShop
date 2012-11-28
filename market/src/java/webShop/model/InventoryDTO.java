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
    
    public Iterator<Gnome> getGnomes();
    
    public void addGnome(Gnome gnome);
    
    public void removeGnome(Gnome gnome);
}
