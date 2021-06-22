/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import org.primefaces.PrimeFaces;

/**
 *
 * @author vm-dba
 */
@Named(value = "dFView")
@RequestScoped
public class DFView {

    /**
     * Creates a new instance of DFView
     */
    public DFView() {
    }
    
    public void viewProducts() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("viewProducts", options, null);
    }
    
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", " Always Bet on Prime!");
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
    
}
