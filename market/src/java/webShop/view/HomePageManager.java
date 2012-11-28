/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.view;


import java.io.Serializable;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import webShop.controller.WebShopFacade;
import webShop.model.Type;

/**
 *
 * @author fingolfin
 */
@ManagedBean("homePageManager")
@ConversationScoped
public class HomePageManager implements Serializable {
    @EJB
    private WebShopFacade webShopFacade;
    private Boolean logedIn;
    private String userName;
    private Map<Type,Integer> quantity;
    private Type boughtGnomeType;
    private Integer boughtAmount;
    private String error;
    @Inject
    private Conversation conversation;

    public String getError() {
        return error;
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
    
    
    public void setLogOut(){
        startConversation();
        webShopFacade.logoutCustomer(this.userName);
        this.logedIn = false;
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
    
    
    /**
     * Creates a new instance of HomePageManager
     */
    public HomePageManager() {
    }
    
    public void putInBasket(){
        startConversation();
        if (quantity.get(boughtGnomeType) < boughtAmount) {
            error = "Error : There are not anough gnomes";
        } else {
        webShopFacade.addGnomeToBasket(boughtAmount, boughtGnomeType, userName); 
        }
    }
}
