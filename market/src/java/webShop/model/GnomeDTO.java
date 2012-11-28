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

    public void setPrice(Integer price);
    
     public void setType(Type type);
     
    public void setAmount(Integer amount);
}
