/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import model.Login;
import util.JDBCLogin;

/**
 *
 * @author Arnulf
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {
    
    private final JDBCLogin jdbcLogin;
    private List<Login> userList;
    private String username;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        jdbcLogin = new JDBCLogin();
        userList = jdbcLogin.getUserList();
    }
    
    public List<Login> getUserList() {
        return userList;
    }

    public void setUserList(List<Login> userList) {
        this.userList = userList;
    }
    
    public String login(String username, String password){
        System.out.println(username);
        for (Login person: userList) {
            if (person.getACCName().contains(username) && person.getACCPWD().contains(password)){
                System.out.println("Logindaten richtig");
                return "hallo.xhtml";
            }else{
                System.out.println("Logindaten falsch");
            }
        } 
        return "0";
    }
    
    public void logLogin (ActionEvent event){
        System.out.println(username);
    }
}
