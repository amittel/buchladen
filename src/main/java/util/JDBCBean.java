/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import model.Product;

/**
 *
 * @author Arnulf
 */
@Named(value = "jDBCBean")
@Dependent
public class JDBCBean {

    private Logger log = Logger.getLogger(JDBCBean.class.getName());
    private Connection conn = null;
    private List<Product> productList;
    /**
     * Creates a new instance of JDBCBean
     */
    public JDBCBean() {
        try {
            String driver = "org.mariadb.jdbc.driver";
            Class.forName(driver);
            String dbUrl = "jdbc:mariadb://localhost:3306/buchladen";
            conn = DriverManager.getConnection(dbUrl, "dba", "dba");
            log.info("JDBC-Verbindung hergestellt!");
            
            productList = new ArrayList<>();
            this.getProductListFromJCDB();
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
       
    }

    public void getProductListFromJCDB() {
        try {
            String sql = "SELECT * FROM produkt ORDER BY PName";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while(rs.next()) {
                productList.add(new Product(
                    rs.getString("PName"),
                    rs.getString("PType")
                ));
            }
            log.info("Datensätze abgefragt aus Produkt.");
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the productList
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * @param productList the productList to set
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    
}
