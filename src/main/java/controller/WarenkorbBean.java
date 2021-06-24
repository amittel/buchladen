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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private boolean minusButtonDisabled = false;

    // Stellt den gesamten Warenkorb dar.
    // Jedes Element in <items> bildet ein kaufbares Produkt ab.
    List<WarenkorbItem> items = new ArrayList<>();

    private String lieferdatum;
    private LocalDate localLieferDatum;

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
        // The SessionMap is a Map<String, Object> and you can access your stored objects 
        // with the help of the get(Object key) method
        Object sessionAttribute = null;
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ExternalContext externalcontext = facescontext.getExternalContext();
        Map sessionMap = externalcontext.getSessionMap();
        
        Date myDate = new Date();
        myDate = java.sql.Date.valueOf(this.localLieferDatum);
        
        log.info("Lieferdatum: " + this.localLieferDatum);
        log.info("myDate: " + myDate);
        if (sessionMap != null) {
            sessionAttribute = sessionMap.get("user");
            currentUser = (String) sessionAttribute;

            sessionAttribute = sessionMap.get("userID");
            currentUserId = sessionAttribute.toString();

            state = dbBean.insertWarenkorbinDB(this.items, this.totalSum, currentUserId, myDate);

            // Löschen den gesamten Warenkorb
            if (state == true) {
                this.items.removeAll(items);
                this.totalSum = 0;
                return "/danke.xhtml"; 
            }
        }
        return "";
    }

    public void addToCart(Buch book, String strNumberOfItems) {

        log.info("adding item to cart ...");
                
        if (book == null){
            log.info("book is null");
            System.out.println("book: " + book);
            return;
        }
        
        int intNumberOfItems = Integer.parseInt(strNumberOfItems);

        // Menge an ausgewählten Büchern ist 0
        // Nehme diese nicht in die Bestellung auf
        if (intNumberOfItems == 0){
            return;
        }
        
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
                
                this.showInfo(book);
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
        
        this.showInfo(book);
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void showInfo(Buch book) {
        addMessage(FacesMessage.SEVERITY_INFO, "Artikel wurde dem Warenkorb hinzugefügt", book.getBName());
    }
    
    public void showWarn() {
        addMessage(FacesMessage.SEVERITY_WARN, "Warn Message", "Message Content");
    }

    public void deleteItemFromCart(WarenkorbItem currentItem) {
        // Reduziere den Gesamtpreis beim Löschvorgang
        for (int numberOfItems = 0;
                numberOfItems < currentItem.getNumberOfItems(); numberOfItems++) {
            this.decreaseTotalPrice(currentItem);
        }
        
        // Löschung des Objekts
        this.items.remove(currentItem);
    }

    public boolean getToggleBuyButton() {
        return this.items.isEmpty();
    }

    public boolean getInputZero(String myvalue) {
        System.out.println("myvalue: " + myvalue);
        return false;
    }

    public void updateNumberOfItemIncrease(WarenkorbItem item, int newNumberOfItems) {
        item.setNumberOfItems(newNumberOfItems + 1);
        increaseTotalPrice(item);

        this.minusButtonDisabled = false;
    }

    public void updateNumberOfItemDecrease(WarenkorbItem item, int newNumberOfItems) {
        if (newNumberOfItems - 1 <= 0) {
            this.minusButtonDisabled = true;
        } else {
            item.setNumberOfItems(newNumberOfItems - 1);
            decreaseTotalPrice(item);
            this.minusButtonDisabled = false;
        }
    }

    public void increaseTotalPrice(WarenkorbItem bookItem) {
        totalSum += bookItem.getBook().getBPreis().doubleValue();
    }

    public void decreaseTotalPrice(WarenkorbItem bookItem) {
        totalSum -= bookItem.getBook().getBPreis().doubleValue();
    }

    // Überbleibsel -- später löschen!
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

    public boolean isMinusButtonDisabled() {
        return minusButtonDisabled;
    }

    public void setMinusButtonDisabled(boolean minusButtonDisabled) {
        this.minusButtonDisabled = minusButtonDisabled;
    }

    public LocalDate getLocalLieferDatum() {
        return localLieferDatum;
    }

    public void setLocalLieferDatum(LocalDate localLieferDatum) {
        this.localLieferDatum = localLieferDatum;
    }

    
}
