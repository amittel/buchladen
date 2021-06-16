/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private double totalSum = 0;

    // Stellt den gesamten Warenkorb dar.
    // Jedes Element in <items> bildet ein kaufbares Produkt ab.
    List<WarenkorbItem> items = new ArrayList<>();

    @Inject
    private Buch book;

    /**
     * Creates a new instance of WarenkorbBean
     */
    public WarenkorbBean() {
        // totalSum = new BigDecimal(0);
    }

    public void addToCart(Buch book, String strNumberOfItems) {

        int intNumberOfItems = Integer.parseInt(strNumberOfItems);

        for (WarenkorbItem myItem : this.items) {
            // Überprüfe ob Produkt bereits im Warenkorb enthalten ist
            if (myItem.getBook().getBid().equals(book.getBid())) {
                System.out.println("Dieses Buch ist bereits im Warenkorb!");
                myItem.setNumberOfItems(
                        myItem.getNumberOfItems() + intNumberOfItems
                );         
                return;
            }        
        }

        // Produkt ist noch nicht im Warenkorb angelegt
        // Erstelle dieses Objekt also
        // Erstelle neues Objekt, welches das Produkt Buch und die Stückzahl
        // enthält.
        WarenkorbItem newItem = new WarenkorbItem(book, intNumberOfItems);
        // Füge erstelltes Objekt dem gesamten Warenkorb hinzu
        this.items.add(newItem); 
    }

    public void updateNumberOfItemIncrease(WarenkorbItem item, int newNumberOfItems){
        item.setNumberOfItems(newNumberOfItems);
        increaseTotalPrice(items);
    }
    
    public void updateNumberOfItemDecrease(WarenkorbItem item, int newNumberOfItems){
        item.setNumberOfItems(newNumberOfItems);
        decreaseTotalPrice(items);
    }
        
    public void increaseTotalPrice(List<WarenkorbItem> items){
        
        for(WarenkorbItem myItem: items){
            totalSum += myItem.getBook().getBPreis().doubleValue() * myItem.getNumberOfItems();
        }
        System.out.println("totalSumIncrease: " + totalSum);
    }
    
    public void decreaseTotalPrice(List<WarenkorbItem> items){
        
        for(WarenkorbItem myItem: items){
            totalSum -= myItem.getBook().getBPreis().doubleValue() * myItem.getNumberOfItems();
        }
        System.out.println("totalSumDecrease: " + totalSum);
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

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public List<WarenkorbItem> getItems() {
        return items;
    }

    public void setItems(List<WarenkorbItem> items) {
        this.items = items;
    }

}
