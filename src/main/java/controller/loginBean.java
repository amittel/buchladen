/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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

    FacesContext context = FacesContext.getCurrentInstance();

    @PersistenceUnit
    private EntityManagerFactory emf;
    private String username;
    private String password;
    private String vname;
   //  private boolean isLoggedIn = false;
    private boolean isLoggedIn = context != null && context.getExternalContext().getSessionMap().get("user") != null;

    @Inject
    private DbAPIBean data; //Zentraler DB-Zugriff
    @Inject
    private Account account; //Login-Objekt
    
    
    //private final JDBCLogin jdbcBean;
    //private List<Account> accountList;
    /**
     * Creates a new instance of loginBean
     */
    public loginBean() {
        //jdbcBean = new JDBCLogin();
        //accountList = jdbcBean.getAccountList();
    }

    public List<Account> getAccountList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> query
                = em.createNamedQuery("Account.findAll", Account.class);

        return query.getResultList();
    }



    public void loginUser() {
        // Als return bekommne wir Liste mit nur einem Element
        // Deswegen .get(0) mit Index 0
        // Alles andere ist outOfBounds
        Account userDB = data.getAccount(username);

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


        // Wenn Liste nicht leer ist, existiert User
        // Führe dann weitere Scrhitte durch
        /*if (!account.isEmpty()) {
            if (account.get(0).getAccpwd().equals(password)) {
                this.isLoggedIn = true;
                context.getExternalContext().getSessionMap().put("user", username);
                System.out.println("loginUser ...");
                System.out.println(username);
                System.out.println(account.get(0).getACCAdmin());
                System.out.println(account.get(0).getAccpwd());

                try {
                    context.getExternalContext().redirect("hallo.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("User existiert");
                System.out.println("Falsches Passwort");
                context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));
            }

        } else {
            System.out.println("User existiert nicht");
            this.isLoggedIn = false;
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));
        }*/
    }

    public void logoutUser() {
        FacesContext context = FacesContext.getCurrentInstance();
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
}
