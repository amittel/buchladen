/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.println;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import util.JDBCLogin;

/**
 *
 * @author vm-dba
 */
@Named(value = "registerBean")
@RequestScoped
public class registerBean {

    // Kunde Entity
    private String vname;
    private String nname;
    private String anrede;  // enum
    private String email;

    // Adress Entity
    private String strasse;
    private String ort;
    private String plz;
    private String bundesland; // enum
    private String land;

    // Account Entity
    private String password;
    private String accname;
    private String accadmin; // enum

    private boolean emailUsed = true;

    // Neue Instanzvariable von util/JDBCLogin
    private final JDBCLogin jdbcLogin;
    /**
     * Creates a new instance of registerBean
     */
    public registerBean() {
        // Erstelle neues Objekt
        // Verbindung zum Server wird hier aufgebaut
        jdbcLogin = new JDBCLogin();
    }

    // Wenn Email bereits registriet return true
    public void isUsed() {
        System.out.println("In registerBean");
    }

    public void registerUser() {
        try {
            System.out.println("In registerBean");
            
            String sql = "SELECT * FROM account";
            ResultSet rs = jdbcLogin.conn.createStatement().executeQuery(sql);
            
            while(rs.next()) {
                System.out.println(rs.getString("ACCName"));;
            }
            
            //PreparedStatement prepStmt;
            // emailUsed = isUsed();
            // Registrieren
            //String userAccount = "INSERT INTO account(ACCName, ACCPWD, ACCAdmin) VALUES ('javaSql','dba','User')";
            // INSERT INTO `account`(`ACCName`, `ACCPWD`, `ACCAdmin`) VALUES ('javaSql','dba','User')
            //prepStmt = jdbcLogin.conn.prepareStatement(userAccount);
            //prepStmt.setString(0, this.getAccname());
            //prepStmt.setString(1, this.getPassword());
            // prepStmt.setString(2, this.getAccadmin());
            //prepStmt.executeUpdate();
            // ResultSet rs = jdbcLogin.conn.createStatement().executeQuery(userAccount);
        } catch (SQLException ex) {
            Logger.getLogger(registerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getAccadmin() {
        return accadmin;
    }

    public void setAccadmin(String accadmin) {
        this.accadmin = accadmin;
    }

    public boolean isEmailUsed() {
        return emailUsed;
    }

    public void setEmailUsed(boolean emailUsed) {
        this.emailUsed = emailUsed;
    }

}
