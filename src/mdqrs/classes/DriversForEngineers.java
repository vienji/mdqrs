/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

/**
 *
 * @author Vienji
 */
public class DriversForEngineers {

    public String getId() {
        return id;
    }

    public Double getLaborEquipmentCost() {
        return laborEquipmentCost;
    }

    public Double getEquipmentFuelCost() {
        return equipmentFuelCost;
    }

    public Double getLubricantCost() {
        return lubricantCost;
    }

    public String getImplementationMode() {
        return implementationMode;
    }

    public double getNumberOfCD() {
        return numberOfCD;
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

    public void setLaborEquipmentCost(Double laborEquipmentCost) {
        this.laborEquipmentCost = laborEquipmentCost;
    }

    public void setEquipmentFuelCost(Double equipmentFuelCost) {
        this.equipmentFuelCost = equipmentFuelCost;
    }

    public void setLubricantCost(Double lubricantCost) {
        this.lubricantCost = lubricantCost;
    }

    public void setImplementationMode(String implementationMode) {
        this.implementationMode = implementationMode;
    }

    public void setNumberOfCD(double numberOfCD) {
        this.numberOfCD = numberOfCD;
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
    private Double laborEquipmentCost;
    private Double equipmentFuelCost;
    private Double lubricantCost;
    private String implementationMode;
    private double numberOfCD;
    private String month;
    private int year;
}
