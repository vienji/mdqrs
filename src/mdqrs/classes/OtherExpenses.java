/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

/**
 *
 * @author Vienji
 */
public class OtherExpenses {

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public Double getLaborCrewCost() {
        return laborCrewCost;
    }

    public Double getLaborEquipmentCost() {
        return laborEquipmentCost;
    }

    public String getImplementationMode() {
        return implementationMode;
    }

    public int getNumberOfCD() {
        return numberOfCD;
    }

    public Double getLightEquipments() {
        return lightEquipments;
    }

    public Double getHeavyEquipments() {
        return heavyEquipments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLaborCrewCost(Double laborCrewCost) {
        this.laborCrewCost = laborCrewCost;
    }

    public void setLaborEquipmentCost(Double laborEquipmentCost) {
        this.laborEquipmentCost = laborEquipmentCost;
    }

    public void setImplementationMode(String implementationMode) {
        this.implementationMode = implementationMode;
    }

    public void setNumberOfCD(int numberOfCD) {
        this.numberOfCD = numberOfCD;
    }

    public void setLightEquipments(Double lightEquipments) {
        this.lightEquipments = lightEquipments;
    }

    public void setHeavyEquipments(Double heavyEquipments) {
        this.heavyEquipments = heavyEquipments;
    }
    
    public String getDate(){
        return month + " " + year;
    }
    
    public OtherExpenses(){
        this.laborCrewCost = 0.0;
        this.laborEquipmentCost = 0.0;
        this.numberOfCD = 0;
        this.lightEquipments = 0.0;
        this.heavyEquipments = 0.0;
    }
    
    private String id;
    private Double laborCrewCost;
    private Double laborEquipmentCost;
    private String implementationMode;
    private int numberOfCD;
    private Double lightEquipments;
    private Double heavyEquipments;
    private String month;
    private int year;
    private String date;
    public final String DESCRIPTION = "Meal Allowance";
}
