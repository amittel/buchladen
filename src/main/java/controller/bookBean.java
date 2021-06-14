/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import model.Buch;
import util.DbAPIBean;

/**
 *
 * @author vm-dba
 */
@Named(value = "bookBean")
@RequestScoped
public class bookBean {

    private static final Logger log
            = Logger.getLogger(bookBean.class.getName());
    
    private List<Buch> bookList;
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    @Inject
    private DbAPIBean dbBean;
    /**
     * Creates a new instance of bookBean
     */
    public bookBean() {
    }
    
    public List<Buch>getBookList(){
        log.info("log: in bookBean vor dem Aufruf!");
        this.bookList = dbBean.getBookList();
        log.info("log: in bookBean nach dem Aufruf!");
        
        EntityManager em = emf.createEntityManager();
        TypedQuery<Buch> query
                = em.createNamedQuery("Buch.findAll", Buch.class);
        // System.out.println(query.getResultList().get(0).getBName());
        return query.getResultList();
    }  
}
