/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import model.Account;
import util.JDBCLogin;

/**
 *
 * @author vm-dba
 */
@Named(value = "loginBean")
@RequestScoped
public class loginBean{
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    
    private final JDBCLogin jdbcBean;
    private List<Account> accountList;

    /**
     * Creates a new instance of loginBean
     */
    public loginBean() {
        jdbcBean = new JDBCLogin();
        accountList = jdbcBean.getAccountList();
    } 
    
    public List<Account> getAccountList(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> query 
                = em.createNamedQuery("Account.findAll", Account.class);
        
        return query.getResultList();
    }
    
    public List<Account> getAccount(String username){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> account 
                = em.createNamedQuery("Account.findByACCName", Account.class);
        account.setParameter("aCCName", username);
        
        return account.getResultList();
    }
    
    public void loginUser() {
        // Als return bekommne wir Liste mit nur einem Element
        // Deswegen .get(0) mit Index 0
        // Alles andere ist outOfBounds
        List<Account> account = this.getAccount(username);

        // Wenn Liste nicht leer ist, existiert User
        // Führe dann weitere Scrhitte durch
        if (!account.isEmpty()) {
            if (account.get(0).getAccpwd().equals(password)) {
                this.isLoggedIn = true;

                System.out.println("loginUser ...");
                System.out.println(username);
                System.out.println(account.get(0).getACCAdmin());
                System.out.println(account.get(0).getAccpwd());
            } else {
                System.out.println("User existiert");
                System.out.println("Falsches Passwort");
            }

        } else {
            System.out.println("User existiert nicht");
            this.isLoggedIn = false;
        }

    }
    
    public void logoutUser(){
        this.isLoggedIn = false;
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
