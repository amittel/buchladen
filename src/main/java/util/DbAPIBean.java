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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import model.Account;
import model.Adresse;
import model.Buch;
import model.Kunde;

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
    private List<Account> accountList;
    private List<Adresse> adressList;

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
        try{
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> query
                = em.createNamedQuery("Account.findByACCName", Account.class);
        query.setParameter("aCCName", username);
        
        account = query.getSingleResult();
        log.info("findAnAccount() -> Account erhalten!");
        } catch(Exception ex){
            log.info("Es gibt keinen Account für diesen Benutzernamen");
        }
    }
    
     public boolean insertRegisterData(Account account, Kunde kunde) {
        
        
        EntityManager entityManager = emf.createEntityManager();
        try {           
            ut.begin( );          
            entityManager.joinTransaction();
            entityManager.persist(account);
            kunde.setFkAcc(account);
            entityManager.persist(kunde);
            ut.commit( );
           
            return true;
        } 
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Insert Acc+Kunde Fehler: " + e.toString());
            try {            
                ut.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException ex) {
                //do nothing
            }
        }
        finally{
            entityManager.close();
        }
      
        return false;
    } 

    public void setBookList(List<Buch> bookList) {
        this.bookList = bookList;
    }

    public void setAccountList(List<Account> accountList) {
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
    
    public void setAdressList(List<Adresse> adressList) {
        this.adressList = adressList;
    }

    public static Logger getLog() {
        return log;
    }

    public List<Buch> getBookList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Buch> query
                = em.createNamedQuery("Buch.findAll", Buch.class);
        return query.getResultList();
    }

    public List<Account> getAccountList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> query
                = em.createNamedQuery("Account.findAll", Account.class);
        return query.getResultList();    
    }

    public Account getAccount(String username) {
        this.findAnAccount(username);
        return account;
    }

    public List<Adresse> getAdressList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Adresse> query
                = em.createNamedQuery("Adresse.findAll", Adresse.class);
        return query.getResultList();
    }
    
    public String getBundesland() {
        EntityManager em = emf.createEntityManager();
        //Query query
                //= (TypedQuery<String>) em.createQuery("SELECT COLUMN_TYPE as AllPossibleEnumValues FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'buchladen' AND TABLE_NAME = 'adresse' AND COLUMN_NAME = 'ABundesland'");
          
        Query query1;
        query1 = em.createQuery("SELECT COLUMN_TYPE as AllPossibleEnumValues FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = buchladen AND TABLE_NAME = adresse AND COLUMN_NAME = ABundesland");
      String result = (String) query1.getSingleResult();
      System.out.println("Max Employee Salary :" + result);
        
        return "Test";
        
    }
}
