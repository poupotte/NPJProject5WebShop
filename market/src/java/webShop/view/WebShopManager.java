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
import webShop.controller.*;
import webShop.model.*;

/**
 *
 * @author zoe
 */
@ManagedBean(name="webShopManager")
@ApplicationScoped
public class WebShopManager implements Serializable {
    
    private static final long serialVersionUID = 16247164406L;
    @EJB
    private WebShopFacade webShopFacade;
    private String currentPseudo;
    private String currentPassword;
    private String error = null;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;
    @ManagedProperty(value="#{homePageManager}")
    private HomePageManager homePageManager;
    @ManagedProperty(value="#{administratorPageManager}")
    private AdministratorPageManager administratorPageManager;
    
    /**
     * Creates a new instance of webShopManager
     */
    public WebShopManager() {
    }

    public HomePageManager getHomePageManager() {
        return homePageManager;
    }

    public void setHomePageManager(HomePageManager homePageManager) {
        this.homePageManager = homePageManager;
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

    public String getCurrentPseudo() {
        return currentPseudo;
    }

    public String getError() {
        return error;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public AdministratorPageManager getAdministratorPageManager() {
        return administratorPageManager;
    }

    public void setAdministratorPageManager(AdministratorPageManager administratorPageManager) {
        this.administratorPageManager = administratorPageManager;
    }
  

    public void setError(String error) {
        this.error = error;
    }

    public void setCurrentPseudo(String currentPseudo) {
        this.currentPseudo = currentPseudo;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    public void loginCustomer(){
        startConversation();
        CustomerDTO customer;
        try {
            customer = webShopFacade.getCustomer(currentPseudo);
            if (customer == null) {
                error = "Error : This name does not exist";
            } else if (! (customer.getPassword().equals(currentPassword))) {
                error = "Error : The password or the pseudo is not correct";
            }else if (customer.getIsbanned()) {
                error = "Error : This user has been banned";
            } else {
                webShopFacade.loginCustomer(currentPseudo);
                error = null;
                homePageManager.setUserName(customer.getId());
                homePageManager.setLogIn();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void createNewCustomer() {
        startConversation();
        try {
            if (currentPassword.length() < 8) {
                error = "Error : Password length should be longer than 8 characters";            
            } else if (webShopFacade.getCustomer(currentPseudo) != null) {
                error = "Error : This name has already been registered";
            } else {
                error = null;
                CustomerDTO customer;     
                customer = webShopFacade.createCustomerDTO(
                                                    currentPseudo,
                                                    currentPassword);
                homePageManager.setUserName(customer.getId());
                homePageManager.setLogIn();
            }
        } catch (Exception e) {
            handleException(e);
        }
    
    }
    
    public void init(){
        startConversation();
        webShopFacade.init();
    }
    
    public void logAdministrator(){
        startConversation();
        AdministratorDTO administrator = webShopFacade.getAdministrator(currentPseudo);
        if ((administrator == null) || 
                (!administrator.getPassword().equals(currentPassword))) {
            error = "Error : The password or the pseudo is not correct";
        } else {
            error = null;
            administratorPageManager.setLogedIn(true);
        }
    }

}
