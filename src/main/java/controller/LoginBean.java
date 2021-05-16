/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private String password;

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

    public void login(String username, String password) {

        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("Username: " + username);
        for (Login person : userList) {
            if (person.getACCPWD().equals(password) && person.getACCName().equals(username)) {
                context.getExternalContext().getSessionMap().put("user", username);
                try {
                    context.getExternalContext().redirect("hallo.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //Send an error message on Login Failure 
                context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));
                System.out.println("Login Flasch!");

            }
        }

        //return "index.xhtml";
    }

    public void logLogin(ActionEvent event) {
        System.out.println(username);
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
}
