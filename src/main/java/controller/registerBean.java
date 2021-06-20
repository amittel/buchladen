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

    // Return the FacesContext instance for the request that is being processed by the current thread, if any.
    // FacesContext contains all of the per-request state information related to the processing of a single 
    // JavaServer Faces request, and the rendering of the corresponding response. 
    // It is passed to, and potentially modified by, each phase of the request processing lifecycle.
    FacesContext context = FacesContext.getCurrentInstance();

    /**
     * Creates a new instance of registerBean
     */
    public registerBean() {
        
    }

    public void registerUser() {
        
        List<Account> myAccountList = dbBean.getAccountList();
        
        boolean success = dbBean.insertRegisterData(account, kunde, adresse);

        if (success) {
            // User OK für login und redirect
            this.isLoggedIn = true;
            
            // Sorgt dafür, dass die Fehlermeldung auf der Seite erhalten bleibt
            // und zu sehen ist.       
            // Variables stored in the flash scope will survive a redirection and they will be discarded afterwards. 
            // https://stackoverflow.com/questions/11194112/understand-flash-scope-in-jsf2
            // .getFlash().setKeepMessages(true): 
            // This line tells JSF you want to keep the FacesMessage in the flash scope. That's a requirement when making a redirection
            context.getExternalContext().getFlash().setKeepMessages(true);  // Return the ExternalContext instance for this FacesContext instance.
                                                                            // This class allows the Faces API to be unaware of the nature of its containing application environment.
            context.getExternalContext().getSessionMap().put("user", account.getACCName());
            // Setze den Ausgabetext der Fehlermeldung
            FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrierung erfolgreich", "login");
            context.addMessage("sucessInfo", faceMsg);

            try {
                context.getExternalContext().redirect("hallo.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Sorgt dafür, dass die Fehlermeldung auf der Seite erhalten bleibt
            // und zu sehen ist.
            // Variables stored in the flash scope will survive a redirection and they will be discarded afterwards. 
            // https://stackoverflow.com/questions/11194112/understand-flash-scope-in-jsf2
            // .getFlash().setKeepMessages(true): 
            // This line tells JSF you want to keep the FacesMessage in the flash scope. That's a requirement when making a redirection
            context.getExternalContext().getFlash().setKeepMessages(true); // Return the ExternalContext instance for this FacesContext instance.
                                                                           // This class allows the Faces API to be unaware of the nature of its containing application environment.
            // Setze den Ausgabetext der Fehlermeldung
            FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account bereits vorhanden!", "Reg");
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
