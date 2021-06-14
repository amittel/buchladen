/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import model.Account;
import model.Buch;

/**
 *
 * @author vm-dba
 */
@Named(value = "dbAPIBean")
@Dependent
public class DbAPIBean implements Serializable {

    private static final Logger log
            = Logger.getLogger(DbAPIBean.class.getName());
    private List<Buch> bookList;
    private List<String> accountList;

    @Inject
    private Account account;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    /**
     * Creates a new instance of DbAPIBean
     */
    public DbAPIBean() {
    }

    private void findAnAccount(String username) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> query
                = em.createNamedQuery("Account.findByACCName", Account.class);
        query.setParameter("aCCName", username);

        account = query.getSingleResult();
    }

    public void setBookList(List<Buch> bookList) {
        this.bookList = bookList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void setUt(UserTransaction ut) {
        this.ut = ut;
    }

    public static Logger getLog() {
        return log;
    }

    public List<Buch> getBookList() {
        return bookList;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public Account getAccount(String username) {
        this.findAnAccount(username);
        return account;
    }



}

// get set
