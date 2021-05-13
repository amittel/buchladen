/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
}
