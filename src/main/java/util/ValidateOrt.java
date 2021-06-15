/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Arnulf
 */
@FacesValidator("buchladen.validateOrt")
public class ValidateOrt implements Validator, Serializable {
    
    private static final Pattern pattern_password = Pattern.compile("/^[a-zA-Z_äÄöÖüÜß]+(?:[\\s-][a-zA-Z]+)*$");
    
    @Override
    public void validate(FacesContext ctx, UIComponent uic,Object obj) throws ValidatorException {
        String input = (String) obj;
        Matcher match = pattern_password.matcher(input);
        
        if(!match.matches()) {
            throw new ValidatorException(new FacesMessage("Geben Sie einen richtigen Ortsnamen ein!"));
        }
    
    }
}