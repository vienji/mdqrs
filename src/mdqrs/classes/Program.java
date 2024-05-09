/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

/**
 *
 * @author Vienji
 */
public class Program {

    public Double getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(Double totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    public String getId() {
        return id;
    }

    public String getSourceOfFund() {
        return sourceOfFund;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSourceOfFund(String sourceOfFund) {
        this.sourceOfFund = sourceOfFund;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public String getDate(){
        return month + " " + year;
    }
    
    private String id;
    private String sourceOfFund;
    private String month;
    private Double totalProjectCost;
    private int year;
}
