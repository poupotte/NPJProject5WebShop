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
import webShop.model.Basket;
import webShop.model.BasketDTO;
import webShop.model.Customer;
import webShop.model.CustomerDTO;
import webShop.model.Gnome;
import webShop.model.GnomeDTO;
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
        em.getTransaction().commit();  
    }
        
    public void logoutCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        customer.setIsLog(false);
        em.getTransaction().commit();    
    }
    
    public CustomerDTO getCustomer(String pseudo){
        CustomerDTO customer = em.find(Customer.class, pseudo);
        if (customer == null) {
            return null;
        }
        return customer;
    }
    
    
    /* Inventory Management */
      
    public void addGnome(Integer price, Type type,Integer amount) {
        InventoryDTO inventory = em.find(Inventory.class, 1);
        Gnome gnome = new Gnome(price, type, amount, (Inventory) inventory);
        em.persist(gnome);
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
