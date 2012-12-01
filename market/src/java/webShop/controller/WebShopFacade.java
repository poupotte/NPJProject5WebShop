/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import webShop.model.BasketDTO;
import webShop.model.Customer;
import webShop.model.CustomerDTO;
import webShop.model.Gnome;
import webShop.model.Inventory;
import webShop.model.InventoryDTO;
import webShop.model.Type;

/**
 *
 * @author zoe
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class WebShopFacade {
    
    @PersistenceContext(unitName = "marketPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    /* Customer Management */
    
    public CustomerDTO createCustomerDTO(String pseudo, String password) {
        CustomerDTO customer = new Customer(pseudo, password);
        em.persist(customer);
        return customer;
    }
    
    public void loginCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setIsLog(true);
        em.merge(customer);  
    }
        
    public void logoutCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setIsLog(false);
        em.merge(customer);    
    }
    
    public CustomerDTO getCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        if (customer == null) {
            return null;
        }
        return customer;
    }
    
    public Integer getMoney(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        return customer.getMoney();
    }
    
    public Integer getDebt(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        return customer.getDebt();
    }
    
    public void setDebt(String pseudo, Integer debt){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setDebt(debt);
        em.merge(customer);
    }
    
    public void withdraw(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setMoney(customer.getMoney()-customer.getDebt());
        customer.setDebt(0);
        em.merge(customer);
    }
    
    
    /* Inventory Management */
      
    public void addGnome(Integer price, Type type,Integer amount) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Gnome gnome = new Gnome(price, type, amount, (Inventory) inventory);
        em.persist(gnome);
        inventory.addNewGnome(gnome);        
        em.getTransaction().commit();
    }
    
     public void addGnome(Type type,Integer amount) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.add(amount, type);        
        em.getTransaction().commit();
    }
    
    public void removeGnomeToInventory(Type type, Integer amount){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.remove(amount, type);
        //em.remove(gnome);
        em.getTransaction().commit();
    }
    
    public Integer getQuantityInInventory(Type type) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Integer quantity = inventory.getQuantity(type);
        return quantity;
    }
    
    
    /* Basket Management */
    
    public void addGnomeToBasket(Integer amount, Type type, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        BasketDTO basket = customer.getBasket();
        basket.add(amount, type);
        em.merge(basket);
    }
    
    public void removeGnomeToBasket(String nameCustomer) {
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        BasketDTO basket = customer.getBasket();
        basket.emptyBasket();
        em.merge(basket);
    }
    
    public Integer getQuantityInBasket(Type type, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        BasketDTO basket = customer.getBasket();
        return basket.getQuantity(type);   
    }  
    
    public Integer getAmount(String nameCustomer){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Integer priceAxe = inventory.getAxeGnome().getPrice();
        Integer priceBearded = inventory.getBeardedGnome().getPrice();
        Integer priceBeer = inventory.getBeerGnome().getPrice();
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        BasketDTO basket = customer.getBasket();
        return priceAxe*basket.getQuantity(Type.AXE) +
                priceBearded*basket.getQuantity(Type.BEARDED)+
                priceBeer*basket.getQuantity(Type.BEER);
    }
}
