/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.view;


import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import webShop.controller.WebShopFacade;
import webShop.model.Type;

/**
 *
 * @author fingolfin
 */
@ManagedBean(name="homePageManager")
@ApplicationScoped
public class HomePageManager implements Serializable {
    private static final long serialVersionUID = 16247164405L;
    @EJB
    private WebShopFacade webShopFacade;
    private Boolean logedIn;
    private Boolean isBuy = false;
    private String userName;
    private Type boughtGnomeType;
    private Integer boughtAmount;
    private Exception transactionFailure;
    private String error;
    @ManagedProperty(value="#{inventoryPageManager}")
    private InventoryPageManager inventoryPageManager;
    @ManagedProperty(value="#{basketPageManager}")
    private BasketPageManager basketPageManager;
    @Inject
    private Conversation conversation;
    
    

    public String getError() {
        return error;
    }

   
    public void setError(String error) {
        this.error = error;
    }

    
    
    public void setLogIn(){
        this.logedIn = true;
    }
    
    
    /**
     * not used
     * @return 
     */
    public boolean getLogIn(){
        return logedIn;
    }
    
    
    public void logOut(){
        startConversation();
        webShopFacade.logoutCustomer(this.userName);
        this.logedIn = false;
    }

    public Boolean getLogedIn() {
        return logedIn;
    }

    public void setLogedIn(Boolean logedIn) {
        this.logedIn = logedIn;
    }

    public Boolean getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Boolean isBuy) {
        this.isBuy = isBuy;
    }

    public Type getBoughtGnomeType() {
        return boughtGnomeType;
    }

    public void setBoughtGnomeType(Type boughtGnomeType) {
        this.boughtGnomeType = boughtGnomeType;
    }

    public Integer getBoughtAmount() {
        return boughtAmount;
    }

    public void setBoughtAmount(Integer boughtAmount) {
        this.boughtAmount = boughtAmount;
    }

    public InventoryPageManager getInventoryPageManager() {
        return inventoryPageManager;
    }

    public void setInventoryPageManager(InventoryPageManager inventoryPageManager) {
        this.inventoryPageManager = inventoryPageManager;
    }

    public BasketPageManager getBasketPageManager() {
        return basketPageManager;
    }

    public void setBasketPageManager(BasketPageManager basketPageManager) {
        this.basketPageManager = basketPageManager;
    }
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    
    
    /**
     * Start the conversation with the bean
     */
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    /**
     * stop the conversation with the beans
     */
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }
    
    
    /**
     * Creates a new instance of HomePageManager
     */
    public HomePageManager() {
    }
    
    public void putInBasket(){
        startConversation();
        try {
            if (webShopFacade.getQuantityInInventory(boughtGnomeType) < boughtAmount) {
                error = "Error : There are not anough gnomes";
            } else {
                webShopFacade.addGnomeToBasket(boughtAmount, boughtGnomeType, userName); 
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void buy(){
        startConversation();
        try {
            Integer quantityBeer = webShopFacade.getQuantityInBasket(Type.BEER, userName);
            Integer quantityBearded = webShopFacade.getQuantityInBasket(Type.BEARDED, userName);
            Integer quantityAxe = webShopFacade.getQuantityInBasket(Type.AXE, userName);
            if ((webShopFacade.getQuantityInInventory(Type.BEER) < quantityBeer) ||
                (webShopFacade.getQuantityInInventory(Type.BEER) < quantityBearded)||
                (webShopFacade.getQuantityInInventory(Type.BEER) < quantityAxe)){
                error = "Error : There are not enough gnomes";
            } else if(webShopFacade.getAmount(userName) < webShopFacade.getMoney(userName)) {
                error = "Error : You have not enough money";                
            } else {
                webShopFacade.setDebt(userName, webShopFacade.getAmount(userName));
                webShopFacade.removeGnomeToBasket(userName);
                webShopFacade.removeGnomeToInventory(Type.BEER, boughtAmount); 
                webShopFacade.removeGnomeToInventory(Type.BEARDED, boughtAmount); 
                webShopFacade.removeGnomeToInventory(Type.AXE, boughtAmount); 
                isBuy = true;
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void pay(){
        startConversation();
        try{
            webShopFacade.withdraw(userName);
            isBuy = false;
        } catch (Exception e) {
            handleException(e);
        }
        
    }
    
    public void redirectInventory(){
        inventoryPageManager.setCurrentPseudo(userName);
        inventoryPageManager.setLogIn(true);
    }
    
    public void redirectBasket(){
        basketPageManager.setCurrentPseudo(userName);
        basketPageManager.setLogIn(true);        
    }
}
