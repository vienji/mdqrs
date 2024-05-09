/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */

public class CrewEquipment {

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfCd() {
        return numberOfCd;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public double getTotalWages() {
        totalWages = ratePerDay * numberOfCd;
        return totalWages;
    }

    public double getFuelConsumption() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfCd(int numberOfCd) {
        this.numberOfCd = numberOfCd;
    }

    public void setRatePerDay(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public void setTotalWages(double totalWages) {
        this.totalWages = totalWages;
    }

    public void setFuelConsumption(double fuelConsumption) {
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
    
    private String id;
    private String name;
    private Equipment equipment;
    private int numberOfCd;
    private double ratePerDay;
    private double totalWages;
    private double fuelConsumption;
    private double fuelCost;
    private double fuelAmount;
    private double lubricant;
    private double lubricantCost;
    private double lubricantAmount;
    private double totalCost;
}
