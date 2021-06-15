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
import javax.inject.Inject;
import model.Buch;
import model.WarenkorbItem;

/**
 *
 * @author vm-dba
 */
@Named(value = "warenkorbBean")
@ManagedBean // (?)
@Dependent
public class WarenkorbBean implements Serializable {

    private static final Logger log
            = Logger.getLogger(WarenkorbBean.class.getName());
    
    int numberOfItems;
    // @Inject
    List<WarenkorbItem> items = new ArrayList<>();

    /**
     * Creates a new instance of WarenkorbBean
     */
    public WarenkorbBean() {
    }

    public void addToCart(Buch book, int numberOfItems) {
        // log.info("FOOOOOOOOOOOOOOOOOO");
        log.info("numberOfItems:" + numberOfItems);
        items.add(new WarenkorbItem(book, numberOfItems));
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
    
}
