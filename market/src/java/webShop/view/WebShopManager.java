/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import webShop.controller.WebShopFacade;
import webShop.model.CustomerDTO;

/**
 *
 * @author zoe
 */
@Named(value = "webShopManager")
@ConversationScoped
public class WebShopManager implements Serializable {
 
    @EJB
    private WebShopFacade webShopFacade;
    private String currentPseudo;
    private String currentPassword;
    private String error = null;
    //private Boolean homePage = false;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;
    @ManagedProperty(value="#{homePageManager}")
    private HomePageManager homePage;
    /**
     * Creates a new instance of webShopManager
     */
    public WebShopManager() {
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

    /*
    public Boolean getHomePage() {
        return homePage;
    }
*/
    /*
    public void setHomePage(Boolean homePage) {
        this.homePage = homePage;
    }
*/
  

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
            } else {
                webShopFacade.loginCustomer(currentPseudo);
                error = null;
                homePage.setUserName(customer.getId());
                homePage.setLogIn();
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
                homePage.setUserName(customer.getId());
                homePage.setLogIn();
            }
        } catch (Exception e) {
            handleException(e);
        }
    
    }

}
