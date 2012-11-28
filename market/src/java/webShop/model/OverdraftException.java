/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webShop.model;
/**
 * Thrown when currency doesn't exist.
 */
public class OverdraftException extends Exception {
    private static final long serialVersionUID = 16247164402L;

    public OverdraftException(String msg) {
        super(msg);
    }

}