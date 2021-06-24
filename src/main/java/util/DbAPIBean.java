/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
import model.Autor;
import model.Bestelldetail;
import model.Bestellung;
import model.Buch;
import model.Buchautor;
import model.Kategorie;
import model.Kunde;
import model.Verlag;
import model.WarenkorbItem;

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
    private Autor autor;
    @Inject
    private Buchautor buchautor;
    @Inject
    private Account account;
    @Inject
    private Buch book;
    @Inject
    private Kunde kunde;
    @Inject
    private Bestellung bestellung;
    @Inject
    private Bestelldetail bestelldetail;
    @Inject
    private Kategorie category;
    @Inject
    private Verlag verlag;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    /**
     * Creates a new instance of DbAPIBean
     */
    public DbAPIBean() {
    }

    public Buch getBookByISBN(String isbn) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Buch> query
                    = em.createNamedQuery("Buch.findByBisbn", Buch.class);
            query.setParameter("bisbn", isbn);

            book = query.getSingleResult();
            log.info("Buch mit ISBN gefunden");
            return book;
        } catch (Exception e) {
            log.info("Es gibt kein Buch mit dieser ISBN");
            return null;
        }
    }
    
    public Buch getBookByID(int id) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Buch> query
                    = em.createNamedQuery("Buch.findByBid", Buch.class);
            query.setParameter("bid", id);

            book = query.getSingleResult();
            log.info("Buch mit ID gefunden");
            return book;
        } catch (Exception e) {
            log.info("Es gibt kein Buch mit dieser ID");
            return null;
        }
    }

    public List<Buch> getBookList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Buch> query
                = em.createNamedQuery("Buch.findAll", Buch.class);
        return query.getResultList();
    }

    public String getCategoryByFK(int fkid) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Kategorie> query
                    = em.createNamedQuery("Kategorie.findByKatid", Kategorie.class);
            query.setParameter("katid", fkid);

            return query.getSingleResult().getKKategorie();
        } catch (NoResultException e) {
            return "Kategorie";
        }
    }

    private void findAnAccount(String username) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Account> query
                    = em.createNamedQuery("Account.findByACCName", Account.class);
            query.setParameter("aCCName", username);

            account = query.getSingleResult();
            log.info("findAnAccount() -> Account erhalten!");
        } catch (Exception ex) {
            log.info("Es gibt keinen Account für diesen Benutzernamen");
        }
    }

    public Account getAccount(String username) {
        this.findAnAccount(username);
        return account;
    }

    public List<Account> getAccountList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Account> query
                = em.createNamedQuery("Account.findAll", Account.class);
        return query.getResultList();
    }

    public boolean insertRegisterData(Account account, Kunde kunde, Adresse adresse) {

        log.info("Account Name: " + account.getACCName());
        //TODO: Add to form!
        account.setACCAdmin("User");
        kunde.setKTel("0000");
        EntityManager entityManager = emf.createEntityManager();

        try {
            ut.begin();
            //Ablauf: Erst Account, dann Adresse anlegen, dann Kunde (hat beide Fremdschlüssel!)
            entityManager.joinTransaction();
            entityManager.persist(account);
            entityManager.persist(adresse);
            kunde.setFkAcc(account);
            kunde.setFkAid(adresse);
            entityManager.persist(kunde);
            ut.commit();

            return true;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Insert Acc+Kunde Fehler: " + e.toString());
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                //do nothing
            }
        } finally {
            entityManager.close();
        }

        return false;
    }
    
    public boolean insertNewBook(Buch book, String myCategory) {

        log.info("trying to insert new book...");
        
        EntityManager em = emf.createEntityManager();
                 
        // Autor Entity holen
        TypedQuery<Autor> queryAutor
                = em.createNamedQuery("Autor.findByAid", Autor.class);
        queryAutor.setParameter("aid", 1);
        autor = queryAutor.getSingleResult();
        buchautor.setFkAid(autor);
        buchautor.setFkBid(book);
               
        // Kategory Entity holen
        TypedQuery<Kategorie> queryCategory
                = em.createNamedQuery("Kategorie.findByKKategorie", Kategorie.class);
        queryCategory.setParameter("kKategorie", myCategory);
        category = queryCategory.getSingleResult();
        book.setFkKatid(category);
        
        // Verlag Entity holen
        TypedQuery<Verlag> queryVerlag
                = em.createNamedQuery("Verlag.findByVid", Verlag.class);
        queryVerlag.setParameter("vid", 2);
        verlag = queryVerlag.getSingleResult();
        book.setFkVid(verlag);
       
        try {
            ut.begin();
            em.joinTransaction();
            em.persist(book);
            em.persist(buchautor);
            ut.commit();

            return true;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Insert Book: " + e.toString());
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                //do nothing
            }
        } finally {
            em.close();
        }

        return false;
    }
    
    public boolean updateBook(Buch book, String myCategory) {

        log.info("trying to update new book...");
        
        EntityManager em = emf.createEntityManager();
        
        // Kategory Entity holen
        TypedQuery<Kategorie> queryCategory
                = em.createNamedQuery("Kategorie.findByKKategorie", Kategorie.class);
        queryCategory.setParameter("kKategorie", myCategory);
        category = queryCategory.getSingleResult();
        book.setFkKatid(category);
       
        try {
            ut.begin();
            em.joinTransaction();
            em.merge(book);
            ut.commit();

            return true;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Insert Book: " + e.toString());
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                //do nothing
            }
        } finally {
            em.close();
        }

        return false;
    }

    public boolean insertWarenkorbinDB(List<WarenkorbItem> items, double totalSum, String currentUserId, Date lieferdatum) {

        EntityManager em = emf.createEntityManager();
        // Workaround damit query klappt - wie anders lösen (?)
        account.setAcid(Integer.parseInt(currentUserId));

        // KundenID mit Hilfe von currentUserId beschaffen
        TypedQuery<Kunde> query
                = em.createNamedQuery("Kunde.findByFK_ACC", Kunde.class);
        query.setParameter("fkAcc", account);

        // Erhalte Kundenobjekt
        kunde = query.getSingleResult();

        try {
            ut.begin();

            // Durchlaufe jeden Artikel aus dem Warenkorb
            // und speicher diesen als eigene Bestellung ab
            em.joinTransaction();   // innerhalb der for-Schleife?

            bestellung.setBLieferDatum(lieferdatum);
            bestellung.setBStatus("offen");
            bestellung.setBKommentar(" ");
            bestellung.setFkKid(kunde);
            em.persist(bestellung);

            for (WarenkorbItem myItem : items) {
                // Neues Objekt erstellen, um 
                // alle Objekte übernehmen zu können
                // und überschreiben zu verhindern
                Bestelldetail bestelldetail = new Bestelldetail();

                bestelldetail.setFkBeid(bestellung);
                bestelldetail.setFkBid(myItem.getBook());
                bestelldetail.setBDMenge((short) myItem.getNumberOfItems());
                em.persist(bestelldetail);
            }
            // In Datenbank übertragen
            ut.commit();

            // em.close();
            return true;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("insert Bestellung fehlgeschlagen: " + e.toString());
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                //do nothing
            }
        } finally {
            em.close();
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

    public List<Kategorie> getCategoryList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Kategorie> query
                = em.createNamedQuery("Kategorie.findAll", Kategorie.class);

        return query.getResultList();
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
