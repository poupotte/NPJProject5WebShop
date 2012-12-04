/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;

/**
 * Database entity of a customer.
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
      
      public Integer getQuantity(Type type);
      
      /**
       * Add a given quantity of gnome of a given type to the basket
       * @param amount number of gnome to add
       * @param type type of gnome
       */
      public void add (Integer amount, Type type);
    
      /**
       * empty the basket of the customer
       */
      public void emptyBasket();
    
      

}
