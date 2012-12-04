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
 * @author Simon Cathébras
 * @author Zoé Bellot
 */
@ManagedBean(name="homePageManager")
@ApplicationScoped
public class HomePageManager implements Serializable {
    private static final long serialVersionUID = 16247164405L;
    @EJB
    private WebShopFacade webShopFacade;
    private Boolean logedIn = false;
    private Boolean isBuy = false;
    private String userName;
    private Type boughtGnomeType; // Type of gnome to put in the basket
    private Integer boughtAmount; // Amount of gnome to put in the basket
    private Exception transactionFailure;
    private String error;
    @ManagedProperty(value="#{inventoryPageManager}")
    private InventoryPageManager inventoryPageManager;
    @ManagedProperty(value="#{basketPageManager}")
    private BasketPageManager basketPageManager;
    @Inject
    private Conversation conversation;
    
    
    /**
     * Creates a new instance of HomePageManager
     */
    public HomePageManager() {
    }
    
    
    /*************************************************************************/
    /*************************** Getter and Setter ***************************/
    /*************************************************************************/

    public String getError() {
        return error;
    }
   
    public void setError(String error) {
        this.error = error;
    }    
    
    public void setLogIn(){
        this.logedIn = true;
    }    
    
    public boolean getLogIn(){
        return logedIn;
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
    
    public Integer getBasketAmount(){
        startConversation();
        return webShopFacade.getBasketAmount(userName);
    }
    
    public void setBasketAmount(Integer amount){
        int i = 1;
    }
    
    public Integer getAccountAmount(){
        startConversation();
        return webShopFacade.getMoney(userName);
    }
    
    public void setAccountAmount(Integer amount){
        int i = 1;
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
    
    public Integer getDebt(){
        startConversation();
        return webShopFacade.getDebt(userName);
    }
    
    public void setDebet(){
        int i = 1;
    }
    
    
    
    public Boolean getBeerIsAvailable(){
        startConversation();
        return webShopFacade.getIsAvailable(Type.BEER);
    }
    
    public Boolean getBeardedIsAvailable(){
        startConversation();
        return webShopFacade.getIsAvailable(Type.BEARDED);
    }
    
    public Boolean getAxeIsAvailable(){
        startConversation();
        return webShopFacade.getIsAvailable(Type.AXE);
    }
    
     public void setBeerIsAvailable(){
        int i = 1;
    }
    
    public void setBeardedIsAvailable(){
        int i = 1;
    }
    
    public void setAxeIsAvailable(){
        int i = 1;
    }
    
     /*************************************************************************/
     /******************* Management conversation and exception ***************/
     /*************************************************************************/
    
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
     
    /**
     * Stop the conversation and handle the exception.
     * @param e  Exception to handle
     */
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }
        
    
    /**************************************************************************/    
    /********************* Management basket, pay and buy *********************/
    /**************************************************************************/
    
    
    /**
     * Put in the basket 'boughtAmount' of gnomes which have 
     * the type 'boughtGnomeType'.
     */
    public void putInBasket(){
        startConversation();
        try {
            if (webShopFacade.getQuantityInInventory(boughtGnomeType) < boughtAmount) {
                error = "Error : There are not enough gnomes";
            } else {
                webShopFacade.addGnomeToBasket(boughtAmount, boughtGnomeType, userName);
                error = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    /**
     * Log out the customer and redirect the page on home.xhtml.
     */    
    public void logOut(){
        startConversation();
        webShopFacade.logoutCustomer(this.userName);
        this.logedIn = false;
    }
    
    /**
     * Remove basket's gnomes in the inventory,
     * empty the customer's basket and
     * add to the debt the amount of the basket.
     *
     * An error message are displayed if  :
     *        - there not enough gnomes in the inventory
     *        - the customer hasn't enough money
     */
    public void buy(){
        startConversation();
        try {
            Integer quantityBeer = webShopFacade.getQuantityInBasket(Type.BEER, userName);
            Integer quantityBearded = webShopFacade.getQuantityInBasket(Type.BEARDED, userName);
            Integer quantityAxe = webShopFacade.getQuantityInBasket(Type.AXE, userName);
            if ((quantityBeer>0) && (!webShopFacade.getIsAvailable(Type.BEER))){
                error = "Error : Gnome with beer is no longer available";
            } else if ((quantityAxe>0) && (!webShopFacade.getIsAvailable(Type.AXE))){
                error = "Error : Gnome with axe is no longer available";
            } else if ((quantityBearded>0) && (!webShopFacade.getIsAvailable(Type.BEARDED))){
                error = "Error : Bearded gnome is no longer available";
            } else if ((webShopFacade.getQuantityInInventory(Type.BEER) < quantityBeer) ||
                (webShopFacade.getQuantityInInventory(Type.BEER) < quantityBearded)||
                (webShopFacade.getQuantityInInventory(Type.BEER) < quantityAxe)){
                error = "Error : There are not enough gnomes";
            } else if(webShopFacade.getBasketAmount(userName) > webShopFacade.getMoney(userName)) {
                error = "Error : You have not enough money";                
            } else {
                webShopFacade.setDebt(userName, webShopFacade.getBasketAmount(userName));
                webShopFacade.removeGnomeToInventory(Type.BEER, quantityBeer); 
                webShopFacade.removeGnomeToInventory(Type.BEARDED, quantityBearded); 
                webShopFacade.removeGnomeToInventory(Type.AXE, quantityAxe); 
                isBuy = true;
                error = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    /**
     * Cancel the bought of a basket
     */
    public void cancelPay(){
        startConversation();
        try {
            Integer beerInBasket = webShopFacade.getQuantityInBasket(Type.BEER, userName);
            Integer beardInBasket = webShopFacade.getQuantityInBasket(Type.BEARDED, userName);
            Integer axeInBasket = webShopFacade.getQuantityInBasket(Type.AXE, userName);
            
            webShopFacade.setDebt(userName, 0);
            
            webShopFacade.addGnome(Type.AXE, axeInBasket);
            webShopFacade.addGnome(Type.BEARDED, beardInBasket);
            webShopFacade.addGnome(Type.BEER, beerInBasket);
            isBuy = false;
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    /**
     * Pay the last bought.
     * The amount of the debt is removed of the customer's money.
     */
    public void pay(){
        startConversation();
        try{
            webShopFacade.withdraw(userName);
            isBuy = false;
        } catch (Exception e) {
            handleException(e);
        }
        
    }
    

    
    /**
     * Empty the customer's basket
     */
    public void emptyBasket(){
        startConversation();
        webShopFacade.emptyBasket(userName);
    }
    
    
    /**************************************************************************/    
    /********************* Management basket, pay and buy *********************/
    /**************************************************************************/
        
    
    /**
     * Redirect the xhtml page to the inventoryPage
     */
    public void redirectInventory(){
        boughtAmount = null;
        error = null;
        inventoryPageManager.setHomePageManager(this);
        inventoryPageManager.setLogIn(true);
    }
    
    /**
     * Redirect the xhtml page to the basketPage
     */
    public void redirectBasket(){
        boughtAmount = null;
        error = null;
        basketPageManager.setPseudo(userName);    
        basketPageManager.setHomePageManager(this);
        basketPageManager.setLogIn(true);        
    }
}
