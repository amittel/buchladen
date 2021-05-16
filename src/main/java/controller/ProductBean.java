/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import model.Product;
import util.JDBCBean;

/**
 *
 * @author Arnulf
 */
@Named(value = "productBean")
@RequestScoped
public class ProductBean {

    private final JDBCBean jdbcBeanProdukt;
    private List<Product> productList;
    
    /**
     * Creates a new instance of ProductBean
     */
    public ProductBean() {
        jdbcBeanProdukt = new JDBCBean();
        productList = jdbcBeanProdukt.getProductList();
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
