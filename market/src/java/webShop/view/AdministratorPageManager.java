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
@ManagedBean(name="administratorPageManager")
@ApplicationScoped
public class AdministratorPageManager implements Serializable {
    private static final long serialVersionUID = 16247164405L;
    @EJB
    private WebShopFacade webShopFacade;
    private Boolean logedIn = false;
    private Exception transactionFailure;
    private String error;
    private Type gnomeType;
    private Integer gnomeAmount = 0;
    private String userName;
    private String newAdministratorName;
    private String newAdministratorPassword;
    @Inject
    private Conversation conversation;
    
    /**
     * Creates a new instance of AdministratorPageManager
     */
    public AdministratorPageManager() {
    }
    
    
    /*************************************************************************/
    /*************************** Getter and Setter ***************************/
    /*************************************************************************/
    
    
    public Boolean getLogedIn() {
        return logedIn;
    }

    public void setLogedIn(Boolean logedIn) {
        this.logedIn = logedIn;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Type getGnomeType() {
        return gnomeType;
    }

    public void setGnomeType(Type gnomeType) {
        this.gnomeType = gnomeType;
    }

    public Integer getGnomeAmount() {
        return gnomeAmount;
    }

    public void setGnomeAmount(Integer gnomeAmount) {
        this.gnomeAmount = gnomeAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewAdministratorName() {
        return newAdministratorName;
    }

    public void setNewAdministratorName(String newAdministratorName) {
        this.newAdministratorName = newAdministratorName;
    }

    public String getNewAdministratorPassword() {
        return newAdministratorPassword;
    }

    public void setNewAdministratorPassword(String newAdministratorPassword) {
        this.newAdministratorPassword = newAdministratorPassword;
    }
    
    public Integer getQuantityBeer(){
        startConversation();
        return webShopFacade.getQuantityInInventory(Type.BEER);
    }
    
    public void setQuantityBeer(){
        int i =1;
    }
    
    public Integer getQuantityBearded(){
        startConversation();
        return webShopFacade.getQuantityInInventory(Type.BEARDED);
    }
    
    public void setQuantityBearded(){
        int i =1;
    }
    
    public Integer getQuantityAxe(){
        startConversation();
        return webShopFacade.getQuantityInInventory(Type.AXE);
    }
    
    public void setQuantityAxe(){
        int i =1;
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
     * Stop the conversation and handle exception 
     * @param e : Exception to handle
     */
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }
    
    
    /*************************************************************************/
    /************************** Management of admin **************************/
    /*************************************************************************/
    
    
    /**
     * Add in the inventory 'gnomeAmount' gnome of type 'gnomeType'
     */
    public void addInInventory(){
        startConversation();
        webShopFacade.addGnome(gnomeType, gnomeAmount);        
    }
    
    /**
     * Remove in the inventory 'gnomeAmount' gnome of type 'gnomeType'
     */
    public void removeInInventory(){
        startConversation();
        webShopFacade.removeGnomeToInventory(gnomeType, gnomeAmount); 
    }
    
    /**
     * Ban the user 'userName'
     */
    public void banUser(){
        startConversation();
        Boolean check = webShopFacade.ban(userName);
        if (!check) {
            error = "Error : this user does not exist";
        }
    }
    
    /**
     * create an administrator with the name 'newAdministratorName' 
     * and the password 'newAdministratorPassword'
     */
    public void createAdministrator(){
        startConversation();
        webShopFacade.createAdministrator(newAdministratorName, newAdministratorPassword);  
    }
    
    /**
     * Logout the current administrator
     */
    public void logOut(){
        logedIn = false;
    }
    
    /**
     * Add the type 'gnomeType' of gnome
     */
    public void addType(){
        startConversation();
        if (!webShopFacade.addType(gnomeType)) {
            error = "This gnome has already been avaible.";
        }
    }
    
    /**
     * Remove the type 'gnomeType' of gnome
     */
    public void removeType(){
        startConversation();
        Boolean ok = webShopFacade.removeType(gnomeType);
        if (!ok) {
            error = "This gnome has already been remove.";
        }
    }
    
}
