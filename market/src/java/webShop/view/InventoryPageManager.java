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
import javax.inject.Inject;
import webShop.controller.WebShopFacade;
import webShop.model.Type;

/**
 *
 * @author Simon Cathébras
 * @author Zoé Bellot
 */
@ManagedBean(name = "inventoryPageManager")
@ApplicationScoped
public class InventoryPageManager implements Serializable {
    private static final long serialVersionUID = 16247164406L;
    @EJB
    private WebShopFacade webShopFacade;
    private HomePageManager homePageManager;
    private Boolean logIn;
    private String error = null;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;
    
    /**
     * Creates a new instance of InventoryPageManager
     */
    public InventoryPageManager() {
    }
    
    
    /*************************************************************************/
    /*************************** Getter and Setter ***************************/
    /*************************************************************************/
    
    
    public Integer getQuantityBeer() {
         startConversation();
        return webShopFacade.getQuantityInInventory(Type.BEER);
    }

    public Integer getQuantityAxe() {
         startConversation();
        return webShopFacade.getQuantityInInventory(Type.AXE);
    }

    public Integer getQuantityBearded() {
         startConversation();
        return webShopFacade.getQuantityInInventory(Type.BEARDED);
    }

    
    public String getError() {
        return error;
    }

    public Boolean getLogIn() {
        return logIn;
    }

    public HomePageManager getHomePageManager() {
        return homePageManager;
    }
    
    public Integer getPriceBeer() {
        startConversation();
        return webShopFacade.getPrice(Type.BEER);
    }
    
    public void setPriceBeer(){
        int i = 1;
    }
    
    public Integer getPriceBearded() {
        startConversation();
        return webShopFacade.getPrice(Type.BEARDED);
    }
    
    public void setPriceBearded(){
        int i = 1;
    }
    
    public Integer getPriceAxe() {
        startConversation();
        return webShopFacade.getPrice(Type.AXE);
    }
    
    public void setPriceAxe(){
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

    public void setHomePageManager(HomePageManager homePageManager) {
        this.homePageManager = homePageManager;
    }

    public void setLogIn(Boolean logIn) {
        this.logIn = logIn;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public void setQuantityBeer(Integer newQuantity) {
        int i = 1;      
    }

    public void setQuantityAxe(Integer newQuantity) {
        int i = 1; 
    }

    public void setQuantityBearded(Integer newQuantity) {
        int i = 1; 
    }
    
    
     /*************************************************************************/
     /******************* Management conversation and exception ***************/
     /*************************************************************************/
    
    /**
     * Stop the conversation and handle the excpetion e
     * @param e the exception to handle
     */
     private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
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

    /**
     * Returns
     * <code>false</code> if the latest transaction failed, otherwise
     * <code>false</code>.
     */
    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }
    
    
    /*************************************************************************/
    /********************** Management of redirection ************************/
    /*************************************************************************/
    
    /**
     * Redirect the xhtml page in the homePage of the user.
     */
    public void redirect(){
            this.logIn = false;
    }
    
}
