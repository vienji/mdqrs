/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

/**
 *
 * @author Vienji
 */
public class JobType {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Double getRatePerDay() {
        return ratePerDay;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRatePerDay(Double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }
    
    private int id;
    private String type;
    private Double ratePerDay;
}
