/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

/**
 *
 * @author zoe
 */
public interface CustomerDTO {
    
      public String getId();

      public String getPassword();

      public Boolean getIsLog();
      
      public void setDebt(Integer debt);

      public Integer getDebt();
      
      public void setIsLog(Boolean isLog);
      
      public void setMoney(Integer money);
      
      public Integer getMoney() ;
      
      public Basket getBasket();

}
