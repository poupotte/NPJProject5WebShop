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
      
      public String getPseudo();
      
      public Boolean getIsbanned();

      public void setIsbanned(Boolean isbanned);
      
      public void setPseudo(String pseudo);
      
      public void setIsLog(Boolean isLog);
      
      public void setMoney(Integer money);
      
      public Integer getMoney() ;
      
      public void add (Integer amount, Type type);
    
      public void emptyBasket();
    
      public Integer getQuantity(Type type);

}
