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
        log.info("[bookHelper] -> bookHelper constructor ...");
    }

    // @PostConstruct
    public void initBook() {
        log.info("[bookHelper] -> Init new book ...");
        this.mybook = new Buch();
        // this.selectedBook = mybook;
        this.selectedBook = null;
    }

    // Erstelle neues Buchobjekt
    // welches im Forumular neu erzeugt wird
    public void createNewBook() {
        log.info("[bookHelper] -> Creating new Book!");
        this.mybook = new Buch();
        this.selectedBook = mybook;
    }

    public void saveProduct() {
        log.info("[bookHelper] -> saveProduct ...");

        boolean success = false;

        if (this.selectedBook == null) {
            System.out.println("[bookHelper] -> selectedProduct is null");
        } else {
            if(this.selectedBook.getBName().isEmpty() || this.selectedBook.getBisbn().isEmpty()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Eingabe unvollständig", ""));
                PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
                return;
            }
        }

        Buch dbBook = dbBean.getBookByISBN(this.selectedBook.getBisbn());

        if (dbBook == null) {
            // Buch mit gleicher ISBN ex. noch nicht
            // Buch kann in Datenbank aufgenommen werden
            success = dbBean.insertNewBook(this.selectedBook, this.category);

            if (success) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt hinzugefügt", ""));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt nicht hinzugefügt", ""));
            }

        } else {
            // Buch mit gleicher ISBN schon vorhanden 
            // success = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Buch bereits vorhanden!", ""));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

        this.createNewBook();
    }
    
    public void updateProduct() {
        log.info("[bookHelper] -> updateProduct ...");

        boolean success = false;

        if (this.selectedBook == null) {
            System.out.println("[bookHelper] -> selectedProduct is null");
        } else {
            System.out.println("[bookHelper] -> Alles ok");

            System.out.println("[bookHelper] -> ISBN: " + this.selectedBook.getBisbn());
            System.out.println("[bookHelper] -> Titel: " + this.selectedBook.getBName());
        }

        Buch dbBook = dbBean.getBookByID(this.selectedBook.getBid());

        if (dbBook != null) {
            
            success = dbBean.updateBook(this.selectedBook, this.category);

            if (success) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt aktualisiert"));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt nicht aktualisiert"));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Buch nicht in Datenbank vorhanden!"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

        this.createNewBook();
    }

    public void deselectProduct() {
        log.info("[bookHelper] -> Deselecting book ...");
        this.selectedBook = null;
    }

    public void onRowSelect(SelectEvent<Buch> event) {
        log.info("[bookHelper] -> onRowSelect ...");
        this.selectedBook = event.getObject();
        FacesMessage msg = new FacesMessage("Produkt wurde ausgewält!", String.valueOf(event.getObject().getBName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowSelect(Buch selectedItem) {
        FacesMessage msg = new FacesMessage("Produkt in Warenkorb hinzugefügt!", selectedItem.getBName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowUnselect(SelectEvent<Buch> event) {
        log.info("[bookHelper] -> onRowUnSelect ...");
        FacesMessage msg = new FacesMessage("Product Unselected", String.valueOf(event.getObject().getBName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Buch getMybook() {
        return mybook;
    }

    public void setMybook(Buch mybook) {
        log.info("[bookHelper] -> setMyBook: " + mybook.getBName());
        this.mybook = mybook;
    }

    public Buch getSelectedBook() {
        log.info("[bookHelper] -> getSelectedBook: " + selectedBook);
        
        if (selectedBook == null){      
        }
        
        return selectedBook;
    }

    public void setSelectedBook(Buch selectedBook) {
        log.info("[bookHelper] -> setSelectedBook: " + selectedBook.getBName());
        this.selectedBook = selectedBook;
    }

    public String getCategoryByFK(int fkid) {
        // log.info("getCategoryByFK: " + fkid);
        this.category = dbBean.getCategoryByFK(fkid);
        return category;
    }

    public String getCategory() {
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
