/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author Vienji
 */
public class DataValidation {
    
    public boolean validateInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    
    public boolean validateCurrency(String value){
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        
        try{
            format.parse(value);
            return true;
        } catch (ParseException e){
            return false;
        }   
    }
    
    public static Double convertToDouble(String currencyString) {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        
        try {
            Number number = format.parse(currencyString);
            return number.doubleValue();
        } catch (ParseException e) {
            
            System.err.println("Error parsing the currency string: " + e.getMessage());
            return null;
        }
    }
}
