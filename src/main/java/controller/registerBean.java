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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import model.Adresse;
import util.DbAPIBean;

/**
 *
 * @author vm-dba
 */
@Named(value = "registerBean")
@ManagedBean
@RequestScoped
public class registerBean {

    private static final Logger log
            = Logger.getLogger(registerBean.class.getName());
    
    // Kunde Entity
    private String vname;
    private String nname;
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
    private int fk_aid;
    private int fk_acc;

    ResultSet rs;

    private boolean usernameUsed = false;

    private List<Adresse> adressList;

    @Inject
    private DbAPIBean dbBean; //Zentraler DB-Zugriff
    @Inject
    private Adresse adress; //Login-Objekt

    /**
     * Creates a new instance of registerBean
     */
    public registerBean() {
        // Erstelle neues Objekt
        // Verbindung zum Server wird hier aufgebaut
        //jdbcLogin = new JDBCLogin();
    }

    /*
    // Wenn Email bereits registriet return true
    public boolean isUsed(String accname) {

        try {
            String get_acc_names = "SELECT ACCName FROM account";
      /     rs = jdbcLogin.conn.createStatement().executeQuery(get_acc_names);
            while (rs.next() == true) {
                if (rs.getString("ACCName").equals(accname)) {
                    System.out.println("User existiert bereits");
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(registerBean.class.getName()).log(Level.SEVERE, null, ex);

        }
        System.out.println("Neuer User wird angelegt.");
        return false;
    }

    public void registerUser() {
        try {

            PreparedStatement prepStmt;
            usernameUsed = isUsed(this.getAccname());

            if (!usernameUsed) {
                // Account Entity
                String userAccount = "INSERT INTO account(ACID, ACCName, ACCPWD, ACCAdmin) VALUES (NULL, ?, ?, ?)";
                prepStmt = jdbcLogin.conn.prepareStatement(userAccount);
                prepStmt.setString(1, this.getAccname());
                prepStmt.setString(2, this.getPassword());
                prepStmt.setString(3, "User");
                prepStmt.executeUpdate();

                // Adresse Entity
                String adressAccount = "INSERT INTO adresse(ADRID, AStrasse, AOrt, APLZ, ABundesland, ALand) VALUES (NULL, ?, ?, ?, ?, ?)";
                prepStmt = jdbcLogin.conn.prepareStatement(adressAccount);
                prepStmt.setString(1, this.getStrasse());
                prepStmt.setString(2, this.getOrt());
                prepStmt.setString(3, this.getPlz());
                prepStmt.setString(4, this.getBundesland());
                prepStmt.setString(5, this.getLand());
                prepStmt.executeUpdate();

                // Kunde Entity - Foreigns Keys zusammenführen
                // Adresse FK auslesen
                String get_fk_aid = "SELECT MAX(ADRID) as aid FROM `adresse`";
                rs = jdbcLogin.conn.createStatement().executeQuery(get_fk_aid);
                while (rs.next() == true) {
                    this.fk_aid = rs.getInt("aid");
                }

                // Account FK auslesen
                String get_fk_acc = "SELECT MAX(ACID) as acid FROM `account`";
                rs = jdbcLogin.conn.createStatement().executeQuery(get_fk_acc);
                while (rs.next() == true) {
                    this.fk_acc = rs.getInt("acid");
                }

                // Kunde Entity
                String kunde = "INSERT INTO `kunde`(`KID`, `KVName`, `KName`, `KEmail`, `KTel`, `FK_AID`, `FK_ACC`) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
                prepStmt = jdbcLogin.conn.prepareStatement(kunde);
                prepStmt.setString(1, this.getVname());
                prepStmt.setString(2, this.getNname());
                prepStmt.setString(3, this.getEmail());
                prepStmt.setString(4, "0");
                prepStmt.setInt(5, this.fk_aid);
                prepStmt.setInt(6, this.fk_acc);
                prepStmt.executeUpdate();
            } else {
                // System.out.println("User bereits vorhanden!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(registerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

        public void foo() {
        log.info("getAdressList");
        System.out.println("TEST");
        
        this.adressList = dbBean.getAdressList();
        log.info("getAdressList");
        for (Adresse ad: adressList){
            System.out.println(ad.getABundesland());  
        }
        
        // return adressList;
    }
/*
    public void setAdressList(List<Adresse> adressList) {
        this.adressList = adressList;
    }
*/
    public void registerUser() {
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
}
