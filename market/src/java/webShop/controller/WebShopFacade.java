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
import webShop.model.Administrator;
import webShop.model.AdministratorDTO;
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
    private Boolean inventoryInitialize = false;
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
    
    public Boolean ban(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        if (customer == null) {
            return false;
        } else {
            customer.setIsbanned(true);
            em.merge(customer);
            return true;            
        }
    }
        
    
    /* Inventory Management */
          
     public void addGnome(Type type,Integer amount) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.add(amount, type);        
        em.merge(inventory);
    }
    
    public void removeGnomeToInventory(Type type, Integer amount){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.remove(amount, type);
        //em.remove(gnome);
        em.merge(inventory);
    }
    
    public Integer getQuantityInInventory(Type type) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Integer quantity = inventory.getQuantity(type);
        return quantity;
    }
    
    public Integer getPrice(Type type){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        switch (type) {
            case BEER : 
                return inventory.getBeerGnome().getPrice();
            case BEARDED : 
                return inventory.getBeardedGnome().getPrice();
            case AXE : 
                return inventory.getAxeGnome().getPrice();                
        }
        return null;
    }
    
    /* Basket Management */
    
    public void addGnomeToBasket(Integer amount, Type type, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        customer.add(amount, type);
        em.merge(customer);
    }
    
    public void removeGnomeToBasket(String nameCustomer) {
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        customer.emptyBasket();
        em.merge(customer);
    }
    
    public Integer getQuantityInBasket(Type type, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        return customer.getQuantity(type);   
    }  
    
    public Integer getBasketAmount(String nameCustomer){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Integer priceAxe = inventory.getAxeGnome().getPrice();
        Integer priceBearded = inventory.getBeardedGnome().getPrice();
        Integer priceBeer = inventory.getBeerGnome().getPrice();
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        return priceAxe*customer.getQuantity(Type.AXE) +
                priceBearded*customer.getQuantity(Type.BEARDED)+
                priceBeer*customer.getQuantity(Type.BEER);
    }
    
    
    
    
    public void emptyBasket(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.emptyBasket();
    }
    
    /* Management of Administrator */
    
    public void createAdministrator(String pseudo, String password){
        AdministratorDTO administrator = new Administrator(pseudo, password);
        em.persist(administrator);        
    }
    
    public AdministratorDTO getAdministrator(String pseudo){
        AdministratorDTO administrator = em.find(Administrator.class, pseudo);
        return administrator;
    }
    
    /* Initialization */
    
    
    public void createInventory(){
        if (!inventoryInitialize) {
            InventoryDTO inventory = new Inventory(1);
            inventoryInitialize = true;
            em.persist(inventory);
        }   
    }
    
    public void init(){
        if (em.find(Inventory.class, 1) == null) {
            createInventory();
            createAdministrator("root", "javajava");
        }
    }
}
