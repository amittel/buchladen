/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.inject.Named;


/**
 *
 * @author Arnulf
 */
@Named(value = "Login")
public class Login {

    private String ACCName;
    private String ACCPWD;

    public Login(String ACCName, String ACCPWD) {
        this.ACCName = ACCName;
        this.ACCPWD = ACCPWD;
    }
    
    /**
     * Creates a new instance of Login
     */
    public Login() {
    }

    /**
     * @param ACCName the ACCName to set
     */
    public void setACCName(String ACCName) {
        this.ACCName = ACCName;
    }

    /**
     * @return the ACCPWD
     */
    public String getACCPWD() {
        return ACCPWD;
    }

    /**
     * @param ACCPWD the ACCPWD to set
     */
    public void setACCPWD(String ACCPWD) {
        this.ACCPWD = ACCPWD;
    }
    
    /**
     * @return the ACCName
     */
    public String getACCName() {
        return ACCName;
    }
    
}
