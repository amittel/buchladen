/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import model.Buch;
import model.WarenkorbItem;

/**
 *
 * @author vm-dba
 */
@Named(value = "warenkorbBean")
@ManagedBean // (?)
@SessionScoped
public class WarenkorbBean implements Serializable {

    private static final Logger log
            = Logger.getLogger(WarenkorbBean.class.getName());
    
    private String numberOfItems;
    // @Inject
    List<WarenkorbItem> items = new ArrayList<>();
    
    @Inject
    private Buch book;
    

    /**
     * Creates a new instance of WarenkorbBean
     */
    public WarenkorbBean() {
    }
    
    public void init() {
        
    }

    public void addToCart(Buch book, String anzahl) {
        
         //items.add(new WarenkorbItem(book,3));
         //log.info(book.getBName());
         String info = "Name: "+ book.getBName() + " ISBN: "+ book.getBisbn();
         System.out.println(info);
         //String anzahl = this.getNumberOfItems();
         System.out.println("Anzahl Bücher: " +anzahl);
    }

    public Buch getBook() {
        return book;
    }

    public void setBook(Buch book) {
        this.book = book;
    }

    public String getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(String numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
    
}
