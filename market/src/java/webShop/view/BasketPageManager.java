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
@ManagedBean(name = "basketPageManager")
@ApplicationScoped
public class BasketPageManager implements Serializable {
    private static final long serialVersionUID = 16247164406L;
    @EJB
    private WebShopFacade webShopFacade;
    private HomePageManager homePageManager;
    private String pseudo;
    private Boolean logIn;
    private String error = null;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;
    
    /**
     * Creates a new instance of InventoryPageManager
     */
    public BasketPageManager() {
    }
    
    
    /*************************************************************************/
    /*************************** Getter and Setter ***************************/
    /*************************************************************************/
    
    
     public Integer getQuantityBeer() {
        startConversation(); 
        return webShopFacade.getQuantityInBasket(Type.BEER, pseudo);
    }

    public Integer getQuantityAxe() {
        startConversation(); 
        return webShopFacade.getQuantityInBasket(Type.AXE, pseudo);
    }

    public Integer getQuantityBearded() {
        startConversation(); 
        return webShopFacade.getQuantityInBasket(Type.BEARDED, pseudo);
    }
    
    public String getError() {
        return error;
    }

    public HomePageManager getHomePageManager() {
        return homePageManager;
    }

    public String getPseudo() {
        return pseudo;
    }
    
    public Integer getAmount(){
        startConversation();
        return webShopFacade.getBasketAmount(pseudo);
    }
    
    public void setAmount(Integer amount){
        int i = 1;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setHomePageManager(HomePageManager homePageManager) {
        this.homePageManager = homePageManager;
    }

    public Boolean getLogIn() {
        return logIn;
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
     * Start the conversation with the bean.
     */
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    /**
     * stop the conversation with the beans.
     */
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    /**
     * Stop conversation and handle the conversation
     * @param e : exception to handle
     */
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
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
         logIn = false;
    }
    
}
