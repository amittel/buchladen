/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import model.Account;
import util.DbAPIBean;


/**
 *
 * @author vm-dba
 */
@Named(value = "loginBean")
@ManagedBean
@RequestScoped
public class loginBean {

    private static final Logger log
            = Logger.getLogger(loginBean.class.getName());
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    private String username;
    private String password;
    private String vname;
   //  private boolean isLoggedIn = false;
    private boolean isLoggedIn = context != null && context.getExternalContext().getSessionMap().get("user") != null;

    private List<Account> accountList;
    
    @Inject
    private DbAPIBean dbBean; //Zentraler DB-Zugriff
    @Inject
    private Account account; //Login-Objekt
    
    
    //private final JDBCLogin jdbcBean;
    //private List<Account> accountList;
    /**
     * Creates a new instance of loginBean
     */
    public loginBean() {

    }

    public List<Account> getAccountList() {
        this.accountList = dbBean.getAccountList();
        return accountList;
    }

    public void loginUser() {
        // Als return bekommne wir Liste mit nur einem Element
        // Deswegen .get(0) mit Index 0
        // Alles andere ist outOfBounds
        Account userDB = dbBean.getAccount(username);

        if(userDB.getAcid() != null) {
            if(userDB.getACCName().equals(username) && userDB.getAccpwd().equals(password)) {
                // User ok for login and redirect
                this.isLoggedIn = true;
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.getExternalContext().getSessionMap().put("user", username);
                FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Login erfolgreich", "login");
                context.addMessage("sucessInfo",faceMsg);
                
                try {
                    context.getExternalContext().redirect("hallo.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
            //Something wrong            
            }    
        }
    }

    public void logoutUser() {
        log.info("Logging Out");
        // FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        this.isLoggedIn = false;
        try {
            context.getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    
     public void redirectBestellung(){
       
        System.out.println("Bestellen ()!");
        if(isLoggedIn){
            try {
            //context.getExternalContext().getSessionMap().get("user") != null) {
            context.getExternalContext().redirect("bestellen.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(WarenkorbBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Login OK!");
        }else{
         FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bitte zuerst anmelden!", "login");
            System.out.println("Login NICHT ok!");
                context.addMessage("sucessInfo",faceMsg);
        //context.getExternalContext().redirect("warenkorb.xhtml");
        }
        
    }
}
