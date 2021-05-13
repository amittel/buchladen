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
import model.Login;

/**
 *
 * @author Arnulf
 */
@Named(value = "jDBCLogin")
@Dependent
public final class JDBCLogin {

    private static final Logger log = Logger.getLogger(JDBCBean.class.getName());
    private Connection conn = null;
    private  List<Login> userList;
    /**
     * Creates a new instance of JDBCLogin
     */
    public JDBCLogin() {
        try {
            String driver = "org.mariadb.jdbc.Driver";
            Class.forName(driver);
            String dbUrl = "jdbc:mariadb://localhost:3306/buchladen";
            conn = DriverManager.getConnection(dbUrl, "dba", "dba");
            //log.info("JDBC-Verbindung hergestellt!");
            //println("JDBC Verbindung");
            userList = new ArrayList<>();
            this.getuserListfromJDBC();
            //log.info("Nach Fkt aufruf");
            //println("Nach fkt println");
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }  
    }

    public void getuserListfromJDBC() {
        //log.info("Fkt getuserListJDBC");
        try {
            String sql = "SELECT * FROM account";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while(rs.next()) {
                //log.info("Fülle Daten");
                userList.add(new Login(
                    rs.getString("ACCName"),
                    rs.getString("ACCPWD")
                ));
            }
            log.info("Datensätze abgefragt aus Produkt.");
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }}

    public List<Login> getUserList() {
        return userList;
    }

    public void setUserList(List<Login> userList) {
        this.userList = userList;
    }
    
}
