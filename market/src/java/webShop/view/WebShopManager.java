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
 * @author Simon Cathébras
 * @author Zoé Bellot
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
    
    
    /*************************************************************************/
    /*************************** Getter and Setter ***************************/
    /*************************************************************************/

    
    public HomePageManager getHomePageManager() {
        return homePageManager;
    }

    public void setHomePageManager(HomePageManager homePageManager) {
        this.homePageManager = homePageManager;
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
    
    
     /*************************************************************************/
     /******************* Management conversation and exception ***************/
     /*************************************************************************/
    
    /**
     * Stop the conversation and handle the exception e
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

 
    /**************************************************************************/    
    /*********************** Management log and init***** *********************/
    /**************************************************************************/
    
    /**
     * Log on the customer.
     * 
     * Redirect on the xhtlm homePage of the customer
     * A error message is displayed if :
     *          - The name does not exist
     *          - The password and the name don't correspond
     *          - The user is banned
     */
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
    
    /**
     * Create a new customer.
     * 
     * A error message is displayed if :
     *          - The password lentgh in smaller than 8 characters
     *          - The user name is used by another customer
     */
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
    
    /**
     * Initialize the database.
     *      - Initialize the inventory
     *      - Create an default administrator : (root, javajava)
     */
    public void init(){
        startConversation();
        webShopFacade.init();
    }
    
    /**
     * Log on an administrator.
     * Redisrect on the xhtml page administratorPage
     * A error message are displayed if :
     *          - The pseudo and the password don't correspond
     */
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
