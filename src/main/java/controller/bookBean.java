/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import model.Buch;
import org.primefaces.PrimeFaces;
import util.DbAPIBean;

/**
 *
 * @author vm-dba
 */
//@RequestScoped
@Named(value = "bookBean")
@ViewScoped
@RequestScoped
public class bookBean implements Serializable {

    private static final Logger log
            = Logger.getLogger(bookBean.class.getName());

    private List<Buch> bookList;
    private List<Buch> products;
    private Buch selectedProduct;
    private List<Buch> selectedProducts;

    @Inject
    private DbAPIBean dbBean;

    @Inject 
    Buch book;
    
    /**
     * Creates a new instance of bookBean
     */
    public bookBean() {
    }

    // Erstelle neues Buchobjekt
    // welches im Forumular neu erzeugt wird
    public void createNewBook() {
        log.info("Creating new Book!");
        this.selectedProduct = new Buch();
    }

    public void saveProduct() {
        if (this.selectedProduct.getBisbn() == null) {
            this.selectedProduct.setBisbn(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.products.add(this.selectedProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt hinzugefügt"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt aktualisiert"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void deleteProduct() {
        this.products.remove(this.selectedProduct);
        this.selectedProduct = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt entfernt"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selectedProducts.size();
            return size > 1 ? size + " Produkte ausgewählt" : "1 Produkt ausgewählt";
        }

        return "Delete";
    }

    public boolean hasSelectedProducts() {
        return this.selectedProducts != null && !this.selectedProducts.isEmpty();
    }

    public void deleteSelectedProducts() {
        this.products.removeAll(this.selectedProducts);
        this.selectedProducts = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producte entfernt"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public List<Buch>getBookList(){
        this.bookList = dbBean.getBookList();  
        return this.bookList;
    }

    public void setBookList(List<Buch> bookList) {
        this.bookList = bookList;
    }

    public List<Buch> getProducts() {
        return products;
    }

    public void setProducts(List<Buch> products) {
        this.products = products;
    }

    public Buch getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Buch selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Buch> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Buch> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

}
