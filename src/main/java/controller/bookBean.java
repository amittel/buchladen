/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Account;
import model.Buch;
import model.Kategorie;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import util.DbAPIBean;

/**
 *
 * @author vm-dba
 */
//@RequestScoped
@Named(value = "bookBean")
//@ViewScoped
@RequestScoped
// @SessionScoped implements Serializable
public class bookBean {

    private static final Logger log
            = Logger.getLogger(bookBean.class.getName());

    FacesContext context = FacesContext.getCurrentInstance();
    
    private List<Buch> bookList;
    private List<Buch> products;
    private Buch selectedProduct;
    private List<Buch> selectedProducts;
    private String category;
    private List<String> categories = new ArrayList<>();
    private List<Kategorie> categoryObjects;

    @Inject
    private DbAPIBean dbBean;

    @Inject
    Buch book;

    /**
     * Creates a new instance of bookBean
     */
    public bookBean() {
    }

    @PostConstruct
    public void init() {
        // this.selectedProduct = new Buch();
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

    public List<Buch> getBookList() {
        this.bookList = dbBean.getBookList();
        return this.bookList;
    }

    public void setBookList(List<Buch> bookList) {
        this.bookList = bookList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getCategoriesFoo() {
        this.categoryObjects = dbBean.getCategoryList();

        for (Kategorie cat : this.categoryObjects) {
            if (!this.categories.contains(cat.getKKategorie())) {
                this.categories.add(cat.getKKategorie());
            }
        }
        return this.categories;
    }

    public List<Buch> getProducts() {
        return products;
    }

    public void setProducts(List<Buch> products) {
        this.products = products;
    }

    public Buch getSelectedProduct() {

        FacesMessage faceMsg;
        
        if (selectedProduct == null) {
            faceMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Kein Artikel ausgewählt", "");
            context.addMessage("warnInfo", faceMsg);
        } else {
        }
        return selectedProduct;
    }

    public void setSelectedProduct(Buch selectedProduct) {
        log.info("Buch wurde ausgewählt: " + selectedProduct.getBName());
        this.selectedProduct = selectedProduct;
    }

    public List<Buch> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Buch> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Kategorie> getCategoryObjects() {
        return categoryObjects;
    }

    public void setCategoryObjects(List<Kategorie> categoryObjects) {
        this.categoryObjects = categoryObjects;
    }

}
