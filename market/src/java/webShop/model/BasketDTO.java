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
    
    public void add (Integer amount, Type type);
    
    public void emptyBasket();
    
    public Integer getQuantity(Type type);
    
}
