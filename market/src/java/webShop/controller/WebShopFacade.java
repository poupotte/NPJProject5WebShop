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
import webShop.model.GnomeDTO;
import webShop.model.Inventory;
import webShop.model.InventoryDTO;
import webShop.model.Type;

/**
 *
 * @author Simon Cathébras
 * @author Zoé Bellot
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class WebShopFacade {
    
    @PersistenceContext(unitName = "marketPU")
    private EntityManager em;
    private Boolean inventoryInitialize = false;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    /**************************************************************************/
    /************************* Customer Management ****************************/
    /**************************************************************************/
    
    /**
     * Create a new customer.
     * @param pseudo pseudo of the new customer
     * @param password password of the new customer
     * @return the new customer
     */
    public CustomerDTO createCustomerDTO(String pseudo, String password) {
        CustomerDTO customer = new Customer(pseudo, password);
        em.persist(customer);
        return customer;
    }
    
    /**
     * Log in a customer
     * @param pseudo customer's pseudo
     */
    public void loginCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setIsLog(true);
        em.merge(customer);  
    }
        
    /**
     * Log out a customer
     * @param pseudo customer's pseudo 
     */
    public void logoutCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setIsLog(false);
        em.merge(customer);    
    }
    
    /**
     * Get the customer wich calls 'pseudo'
     * @param pseudo the customer's pseudo
     * @return the customer or null if the customer doesn't exist
     */
    public CustomerDTO getCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        if (customer == null) {
            return null;
        }
        return customer;
    }
    
    /**
     * Get the amount of money of the customer which calls 'pseudo'
     * @param pseudo the customer's pseudo
     * @return the amount of money of the customer
     */
    public Integer getMoney(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        return customer.getMoney();
    }
    
    /**
     * Get the debt of the customer which calls 'pseudo'
     * @param pseudo the customer's pseudo
     * @return the debt of the customer
     */
    public Integer getDebt(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        return customer.getDebt();
    }
    
    /**
     * Set debt
     * @param pseudo the customer's pseudo
     * @param debt the new debt
     */
    public void setDebt(String pseudo, Integer debt){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setDebt(debt);
        em.merge(customer);
    }
    
    /**
     * withdraw from a customer's account
     * @param pseudo the customer's pseudo
     */
    public void withdraw(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setMoney(customer.getMoney()-customer.getDebt());
        customer.setDebt(0);
        em.merge(customer);
    }
    
    /**
     * Ban a customer
     * @param pseudo the customer's pseudo
     * @return if the customer has been found
     */
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
        
    /**************************************************************************/
    /************************ Inventory Management ****************************/
    /**************************************************************************/
          
    /**
     * Add gnomes in the inventory
     * @param type the gnomes' type
     * @param amount the amount of the gnomes
     */
     public void addGnome(Type type,Integer amount) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.add(amount, type);        
        em.merge(inventory);
    }
    
    /**
     * Remove gnomes in the inventory
     * @param typethe gnomes' type
     * @param amount the amount of the gnomes
     */
    public void removeGnomeToInventory(Type type, Integer amount){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.remove(amount, type);
        em.merge(inventory);
    }
    
    /**
     * Get the quantity of a type of gnome in the inventory
     * @param type the gnome's type
     * @return the quantity of gnomes
     */
    public Integer getQuantityInInventory(Type type) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Integer quantity = inventory.getQuantity(type);
        return quantity;
    }
    
    
    /**************************************************************************/
    /************************** Gnome Management ******************************/
    /**************************************************************************/
    
    
    /**
     * Get price of a type of gnome
     * @param type the gnome's type
     * @return the price of the gnome
     */
    public Integer getPrice(Type type){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        GnomeDTO gnome = inventory.getGnome(type);
        if (gnome == null){
            return null;
        } else {
            return gnome.getPrice();
        }
    }
    
    public Boolean getIsAvailable(Type type){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        GnomeDTO gnome = inventory.getGnome(type);
        if (gnome == null){
            return null;
        } else {
            return gnome.getIsAvailable();
        }
    }
    
    /**
     * Add the type 'type'
     * @param type the gnome's type
     * @return 
     */
    public Boolean addType(Type type){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        GnomeDTO gnome = inventory.getGnome(type);
        Boolean isAvailable = gnome.getIsAvailable();
        if (isAvailable){
            return false;
        } else {
            gnome.setIsAvailable(true);
            return true;
        }
    }
    
    /**
     * Add the type 'type'
     * @param type the gnome's type
     * @return 
     */
    public Boolean removeType(Type type){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        GnomeDTO gnome = inventory.getGnome(type);
        if (gnome == null){
            return null;
        } else {
            Boolean available = gnome.getIsAvailable();
            if (!available){
                return false;
            } else {
                gnome.setIsAvailable(false);
                return true;
            }
        }
    }
    
    
    /**************************************************************************/
    /************************** Basket Management *****************************/
    /**************************************************************************/
    
    /**
     * Add gnomes in the customer's basket.
     * @param amount the amount of gnomes
     * @param type the gnomes' type
     * @param nameCustomer the customer's name
     */
    public void addGnomeToBasket(Integer amount, Type type, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        customer.add(amount, type);
        em.merge(customer);
    }
    
    /**
     * Remove gnomes in the customer's basket.     
     * @param nameCustomer the customer's name
     */
    public void removeGnomeToBasket(String nameCustomer) {
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        customer.emptyBasket();
        em.merge(customer);
    }
    
    /**
     * Get quantity of a type of gnome in the customer's basket.
     * @param type the gnomes' type
     * @param nameCustomer the customer's name
     * @return the quantity of the gnomes in the basket
     */
    public Integer getQuantityInBasket(Type type, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        return customer.getQuantity(type);   
    }  
    
    /**
     * Get the total price of the basket.
     * @param nameCustomer customer's name
     * @return the price of the customer's basket
     */
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
    
    /**
     * Empty the basket.
     * @param pseudo the customer's pseudo
     */   
    public void emptyBasket(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.emptyBasket();
    }
    
    
    /**************************************************************************/
    /********************* Administrator Management ***************************/
    /**************************************************************************/
    
    /**
     * Create a new administrator.
     * @param pseudo the administrator's pseudo
     * @param password the administrator's password
     */
    public void createAdministrator(String pseudo, String password){
        AdministratorDTO administrator = new Administrator(pseudo, password);
        em.persist(administrator);        
    }
    
    /**
     * Get administrator.
     * @param pseudo the administrator's pseudo
     * @return the administrator
     */
    public AdministratorDTO getAdministrator(String pseudo){
        AdministratorDTO administrator = em.find(Administrator.class, pseudo);
        return administrator;
    }
    
    
    /**************************************************************************/
    /************************** Initialization ********************************/
    /**************************************************************************/
    
    /**
     * Create the inventory with 10 units of each type of gnomes
     */
    public void createInventory(){
            InventoryDTO inventory = new Inventory(1);
            inventoryInitialize = true;
            em.persist(inventory);
    }
    
    /**
     * Initialize the database :
     *      - Initialize the inventory
     *      - Create the default administrator : (root, javajava)
     */
    public void init(){
        if (em.find(Inventory.class, 1) == null) {
            createInventory();
            createAdministrator("root", "javajava");
        }
    }
}
