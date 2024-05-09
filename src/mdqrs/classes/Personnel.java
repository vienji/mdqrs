/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class Personnel {

    public Double getRatePerDay(){
        return ratePerDay;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setRatePerDay(Double ratePerDay){
        this.ratePerDay = ratePerDay;
    }
    
    private String id;
    private String name;
    private String type;
    private Double ratePerDay;
}
