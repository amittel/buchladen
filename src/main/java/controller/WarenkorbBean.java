/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.faces.facelets.util.Path.context;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Bestelldetail;
import model.Bestellung;
import model.Buch;
import model.Kunde;
import model.WarenkorbItem;
import util.DbAPIBean;

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

    FacesContext context = FacesContext.getCurrentInstance();

    @Inject
    private DbAPIBean dbBean;

    @Inject
    private Buch book;

    private String numberOfItems;
    private double totalSum = 0;

    // Stellt den gesamten Warenkorb dar.
    // Jedes Element in <items> bildet ein kaufbares Produkt ab.
    List<WarenkorbItem> items = new ArrayList<>();

    private String lieferdatum;

    /**
     * Creates a new instance of WarenkorbBean
     */
    public WarenkorbBean() {
        // totalSum = new BigDecimal(0);

    }

    public String insertWarenkorbinDB() {

        String currentUser;
        String currentUserId;
        boolean state = false;

        // Magie um aktuell angemeldeten User zu erhalten
        Object sessionAttribute = null;
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ExternalContext externalcontext = facescontext.getExternalContext();
        Map sessionMap = externalcontext.getSessionMap();
        if (sessionMap != null) {
            sessionAttribute = sessionMap.get("user");
            currentUser = (String) sessionAttribute;

            sessionAttribute = sessionMap.get("userID");
            currentUserId = sessionAttribute.toString();

            state = dbBean.insertWarenkorbinDB(this.items, this.totalSum, currentUserId);

            System.out.println("state: " + state);

            if (state == true) {
                return "/danke.xhtml";
                //context.getExternalContext().redirect("bestellen.xhtml");  
            }
        }
        return "";
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
                for (int i = 0; i < intNumberOfItems; i++) {
                    increaseTotalPrice(myItem);
                }
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
        for (int i = 0; i < intNumberOfItems; i++) {
            increaseTotalPrice(newItem);
        }
    }

    public boolean getToggleBuyButton() {
        return this.items.isEmpty();
    }

    public void updateNumberOfItemIncrease(WarenkorbItem item, int newNumberOfItems) {
        item.setNumberOfItems(newNumberOfItems + 1);
        increaseTotalPrice(item);
    }

    public void updateNumberOfItemDecrease(WarenkorbItem item, int newNumberOfItems) {
        item.setNumberOfItems(newNumberOfItems - 1);
        decreaseTotalPrice(item);
    }

    public void increaseTotalPrice(WarenkorbItem bookItem) {
        totalSum += bookItem.getBook().getBPreis().doubleValue();
    }

    public void decreaseTotalPrice(WarenkorbItem bookItem) {
        totalSum -= bookItem.getBook().getBPreis().doubleValue();
    }

    public void updateTotalPrice(List<WarenkorbItem> items) {

        for (WarenkorbItem myItem : items) {
            totalSum += myItem.getBook().getBPreis().doubleValue() * myItem.getNumberOfItems();
        }
        System.out.println("totalSumIncrease: " + totalSum);
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

    public String getLieferdatum() {
        return lieferdatum;
    }

    public void setLieferdatum(String lieferdatum) {
        this.lieferdatum = lieferdatum;
    }
}
