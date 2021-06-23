/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Buch;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import util.DbAPIBean;

/**
 *
 * @author vm-dba
 */
@Named(value = "bookHelper")
@SessionScoped
public class bookHelper implements Serializable {

    @Inject
    private Buch mybook;
    @Inject
    private DbAPIBean dbBean;
    
    private Buch selectedBook;
    private String category;

    private static final Logger log
            = Logger.getLogger(bookHelper.class.getName());

    /**
     * Creates a new instance of newItemBean
     */
    public bookHelper() {
        log.info("bookHelper start ...");    
    }

    // @PostConstruct
    public void initBook(){
        log.info("Init new book!");
        this.mybook = new Buch();
        this.selectedBook = mybook;   
    }
    // Erstelle neues Buchobjekt
    // welches im Forumular neu erzeugt wird
    public void createNewBook() {
        log.info("Creating new Book!");
        this.mybook = new Buch();
        this.selectedBook = mybook;     
    }
    
    public void saveProduct() {
        System.out.println("saveProduct ...");

        if (this.selectedBook == null) {
            System.out.println("selectedProduct is null");
        } else {
            System.out.println("Alles ok");

            System.out.println("ISBN: " + this.selectedBook.getBisbn());
            System.out.println("Titel: " + this.selectedBook.getBName());
        }

        Buch dbBook = dbBean.getBookByISBN(this.selectedBook.getBisbn());

        if (dbBook == null) {
            // Buch mit gleicher ISBN ex. noch nicht
            // Buch kann in Datenbank aufgenommen werden
            // success = dbBean.insertRegisterData(account, kunde, adresse);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt hinzugefügt"));
        } else {
            // Buch mit gleicher ISBN schon vorhanden 
            // success = false;
            System.out.println("ISBN vorhanden: " + this.selectedBook.getBisbn());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt aktualisiert"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

        this.createNewBook();
    }
    
    public void deselectProduct() {
        System.out.println("Deselecting product!");
        this.selectedBook = null;
    }
                
    public void onRowSelect(SelectEvent<Buch> event) {
        this.selectedBook = event.getObject();
        FacesMessage msg = new FacesMessage("Produkt wurde ausgewält!", String.valueOf(event.getObject().getBName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowSelect(Buch selectedItem) {
        FacesMessage msg = new FacesMessage("Produkt in Warenkorb hinzugefügt!", selectedItem.getBName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Buch getMybook() {
        return mybook;
    }

    public void setMybook(Buch mybook) {
        log.info("Neues Buch wurde ausgewählt: " + mybook.getBName());
        this.mybook = mybook;
    }

    public Buch getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Buch selectedBook) {
        this.selectedBook = selectedBook;
    }

    public String getCategoryByFK(int fkid) {
        // log.info("getCategoryByFK: " + fkid);
        this.category = dbBean.getCategoryByFK(fkid);
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo() {
        addMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Message Content");
    }

    public void showWarn() {
        addMessage(FacesMessage.SEVERITY_WARN, "Warn Message", "Message Content");
    }

    public void showError() {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Message Content");
    }
}
