/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.view;

import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import webShop.controller.WebShopFacade;
import webShop.model.CustomerDTO;
import webShop.model.Type;

/**
 *
 * @author zoe
 */
@ManagedBean(name = "basketPageManager")
@ApplicationScoped
public class BasketPageManager implements Serializable {
    private static final long serialVersionUID = 16247164406L;
    @EJB
    private WebShopFacade webShopFacade;
    private String currentPseudo;
    private Boolean logIn;
    private Map<Type,Integer> quantity;
    private String error = null;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;
    
    /**
     * Creates a new instance of InventoryPageManager
     */
    public BasketPageManager() {
    }
    
     public Integer getQuantityBeer() {
        return quantity.get(Type.BEER);
    }

    public Integer getQuantityAxe() {
        return quantity.get(Type.AXE);
    }

    public Integer getQuantityBearded() {
        return quantity.get(Type.BEARDED);
    }

    
    public String getError() {
        return error;
    }

    public String getCurrentPseudo() {
        return currentPseudo;
    }

    public void setCurrentPseudo(String currentPseudo) {
        this.currentPseudo = currentPseudo;
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
        quantity.remove(Type.BEER);
        quantity.put(Type.BEER, newQuantity);      
    }

    public void setQuantityAxe(Integer newQuantity) {
        quantity.remove(Type.AXE);
        quantity.put(Type.AXE, newQuantity); 
    }

    public void setQuantityBearded(Integer newQuantity) {
        quantity.remove(Type.BEARDED);
        quantity.put(Type.BEARDED, newQuantity); 
    }
    
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
    
   /* public void redirect(){
            CustomerDTO customer = webShopFacade.getCustomer(currentPseudo);
            homePageManager.setUserName(customer.getId());
            homePageManager.setLogIn();
    }*/
    
}
