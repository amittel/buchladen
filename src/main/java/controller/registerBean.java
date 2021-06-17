/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.println;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Account;
import model.Adresse;
import model.Kunde;
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
    /*private String vname;
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
    private int fk_acc;*/
    ResultSet rs;

    private boolean usernameUsed = false;

    private String laenderListe;

    private List<Adresse> adressList;

    @Inject
    private DbAPIBean dbBean; //Zentraler DB-Zugriff
    @Inject
    private Adresse adresse; //Login-Objekt
    @Inject
    private Account account;
    @Inject
    private Kunde kunde;

    private boolean isLoggedIn;
    
    private ArrayList<String> bland = new ArrayList<String>();

    FacesContext context = FacesContext.getCurrentInstance();

    /**
     * Creates a new instance of registerBean
     */
    public registerBean() {
        
    }
    
    /* Sehr ineffiziente Methode um Bundesländer aus Struktur zu bekommen!*/
    /*
    @PostConstruct
    public void init() {
        this.adressList = dbBean.getAdressList();
        // ArrayList<String> bland = new ArrayList<String>();
        
        for (Adresse ad : adressList) {
            if (!this.bland.contains(ad.getABundesland())) {
                this.bland.add(ad.getABundesland());
            }  
        }

        for (String s : this.bland){
            System.out.println(s);
        }
    }*/

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
     */
    public void registerUser() {

        boolean success = dbBean.insertRegisterData(account, kunde, adresse);

        if (success) {
            // User ok for login and redirect
            this.isLoggedIn = true;
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.getExternalContext().getSessionMap().put("user", account.getACCName());
            FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrierung erfolgreich", "login");
            context.addMessage("sucessInfo", faceMsg);

            try {
                context.getExternalContext().redirect("hallo.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Something wrong
            FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrierung FEHLER", "Reg");
            context.addMessage("errorInfo", faceMsg);
            try {
                context.getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void bundesland() {
        laenderListe = dbBean.getBundesland();
        log.info(laenderListe);

    }

    // Getter und Setter auo generated
    public String getLaenderListe() {
        return laenderListe;
    }

    public void setLaenderListe(String laenderListe) {
        this.laenderListe = laenderListe;
    }

    public List<Adresse> getAdressList() {
        return adressList;
    }

    public void setAdressList(List<Adresse> adressList) {
        this.adressList = adressList;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public ArrayList<String> getBland() {
        return bland;
    }

    public void setBland(ArrayList<String> bland) {
        this.bland = bland;
    }
}
