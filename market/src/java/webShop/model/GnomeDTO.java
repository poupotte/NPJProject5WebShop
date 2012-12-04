/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

/**
 *
 * @author zoe
 */
public interface GnomeDTO {
      
    public Type getType();

    public Integer getPrice();
    
    public Integer getAmount();

    /**
     * set the price of the current kind of gnome
     * @param price 
     */
    public void setPrice(Integer price);
    
    /**
     * set the type of the gnome
     * @param type 
     */
     public void setType(Type type);
     
     /**
      * set the amount of gnome in the shop
      * @param amount 
      */
    public void setAmount(Integer amount);
}
