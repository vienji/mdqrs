/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**s
 *
 * @author Vienji
 */
public class OpsEquipment {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public double getNumberOfCd() {
        return numberOfCd;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public double getTotalWages() {
        return totalWages;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public double getFuelCost() {
        return fuelCost;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public double getLubricant() {
        return lubricant;
    }

    public double getLubricantCost() {
        return lubricantCost;
    }

    public double getLubricantAmount() {
        return lubricantAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getOpsEquipmentListId() {
        return opsEquipmentListId;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public void setNumberOfCd(double numberOfCd) {
        this.numberOfCd = numberOfCd;
    }

    public void setRatePerDay(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public void setTotalWages(double totalWages) {
        this.totalWages = totalWages;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setFuelCost(double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public void setLubricant(double lubricant) {
        this.lubricant = lubricant;
    }

    public void setLubricantCost(double lubricantCost) {
        this.lubricantCost = lubricantCost;
    }

    public void setLubricantAmount(double lubricantAmount) {
        this.lubricantAmount = lubricantAmount;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setOpsEquipmentListId(String opsEquipmentListId) {
        this.opsEquipmentListId = opsEquipmentListId;
    }
    
    private String id;
    private Equipment equipment;
    private Personnel personnel;
    private double numberOfCd;
    private double ratePerDay;
    private double totalWages;
    private int fuelConsumption;
    private double fuelCost;
    private double fuelAmount;
    private double lubricant;
    private double lubricantCost;
    private double lubricantAmount;
    private double totalCost;
    private String opsEquipmentListId;
}
