/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author vm-dba
 */
public class WarenkorbItem {
    
    private Buch book;
    private int numberOfItems;

    public WarenkorbItem(){   
    }
    
    public WarenkorbItem(Buch book, int numberOfItems){
        this.book = book;
        this.numberOfItems = numberOfItems;
    }
    
    public Buch getBook() {
        return book;
    }

    public void setBook(Buch book) {
        this.book = book;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
    
    
    
}
