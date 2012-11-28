/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.controller;

import java.util.Iterator;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import webShop.model.AxeGnome;
import webShop.model.Basket;
import webShop.model.BasketDTO;
import webShop.model.BeardGnome;
import webShop.model.BeerGnome;
import webShop.model.Customer;
import webShop.model.CustomerDTO;
import webShop.model.Gnome;
import webShop.model.GnomeDTO;
import webShop.model.Inventory;
import webShop.model.InventoryDTO;

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
    
    
    /* Inventory Management */
    
    public void addAxeGnome(Integer price, Integer axeSize) {
        AxeGnome gnome = new AxeGnome(price, axeSize);
        em.persist(gnome);
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.addGnome(gnome);        
        em.getTransaction().commit();
    }
    
    public void addBeardGnome(Integer price, Integer beardLength) {
        BeardGnome gnome = new BeardGnome(price, beardLength);
        em.persist(gnome);
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.addGnome(gnome);        
        em.getTransaction().commit();
    }
        
    public void addBeerGnome(Integer price, Integer liter) {
        BeerGnome gnome = new BeerGnome(price, liter);
        em.persist(gnome);
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.addGnome(gnome);        
        em.getTransaction().commit();
    }
    
    public Iterator<Gnome> getInventory(){
        InventoryDTO inventory = em.find(Inventory.class, 1);
        if (inventory == null) {
            return null;
        }
        return inventory.getGnomes();        
    }
    
    public void removeGnomeToInventory(Integer idGnome){
        GnomeDTO gnome = em.find(Gnome.class, idGnome);
        InventoryDTO inventory = em.find(Inventory.class, 1);
        inventory.removeGnome((Gnome) gnome);
        em.remove(gnome);
        em.getTransaction().commit();
    }
    
    
    /* Basket Management */
    
    public void addGnomeToBasket(Integer idGnome, String nameCustomer){
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        GnomeDTO gnome = em.find(Gnome.class, idGnome);
        BasketDTO basket = new Basket((Gnome) gnome,(Customer) customer);
        em.persist(basket);
    }
    
    public void removeGnomeToBasket(Integer idGnome, String nameCustomer) {
        CustomerDTO customer = em.find(Customer.class, nameCustomer);
        GnomeDTO gnome = em.find(Gnome.class, idGnome);
        Iterator<Basket> it = customer.getBasket();
        while (it.hasNext()){
            Basket basket = it.next();
            if (basket.getGnome() == gnome) {
                customer.removeBasket(basket);
                em.remove(basket);
                break;
            }
        }
        em.getTransaction().commit();
    }
    
}
