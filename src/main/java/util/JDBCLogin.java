/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.println;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import model.Account;

/**
 *
 * @author Arnulf
 */
@Named(value = "jDBCLogin")
@Dependent
public final class JDBCLogin {

    // private
    public Logger log = Logger.getLogger(JDBCLogin.class.getName());
    public Connection conn = null;
    private  List<Account> accountList;
    /**
     * Creates a new instance of JDBCLogin
     */
    
    public JDBCLogin() {
        try {
            String driver = "org.mariadb.jdbc.Driver";
            Class.forName(driver);
            String dbUrl = "jdbc:mariadb://localhost:3306/buchladen";
            conn = DriverManager.getConnection(dbUrl, "dba", "dba");
            log.info("Buchladen JDBC-Verbindung hergestellt!");
            println("JDBC Verbindung");
            
            accountList = new ArrayList<>();
            this.getaccountListfromJDBC();
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }  
    }

    public void getaccountListfromJDBC() {
        try {
            String sql = "SELECT * FROM account";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while(rs.next()) {
                //log.info("Fülle Daten");
                accountList.add(new Account(
                    rs.getString("ACCName")
                ));
            }
            log.info("Datensätze abgefragt aus Produkt.");
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }}
    
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    
}
