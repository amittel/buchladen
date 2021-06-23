/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.xml.internal.ws.client.RequestContext;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
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
@Named(value = "bookBean")
@RequestScoped
@ManagedBean
public class bookBean {

    private static final Logger log
            = Logger.getLogger(bookBean.class.getName());

    private List<Buch> bookList;

    @Inject
    private DbAPIBean dbBean;

    private Buch selectedBook;

    private boolean isAdmin = false;

    /**
     * Creates a new instance of bookBean
     */
    public bookBean() {
    }

    public List<Buch> getBookList() {
        // log.info("getBookList");
        this.bookList = dbBean.getBookList();
        return this.bookList;
    }

    public void showEditButton() {

        Object sessionAttribute = null;
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ExternalContext externalcontext = facescontext.getExternalContext();
        Map sessionMap = externalcontext.getSessionMap();
        if (sessionMap != null) {
            sessionAttribute = sessionMap.get("admin");
            this.isAdmin = (boolean) sessionAttribute;
        }

        System.out.println("bookBean: Zeige Button");

        System.out.println("bookBean isAdmin: " + this.isAdmin);

    }

    // ---------------
    // Getter & Setter
    // ---------------
    public Buch getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Buch selectedBook) {
        this.selectedBook = selectedBook;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

}
