/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

/**
 *
 * @author zoe
 */
public interface BasketDTO {
    
    public Integer getId() ;

    public Gnome getGnome() ;

    public void setGnome(Gnome gnome);

    public void setCustomer(Customer customer) ;
    
}
