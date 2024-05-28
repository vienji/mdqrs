/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class CrewPersonnel {

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrewPersonelId() {
        return crewPersonelId;
    }

    public void setCrewPersonelId(String crewPersonelId) {
        this.crewPersonelId = crewPersonelId;
    }

    public String getPersonelId() {
        return personelId;
    }

    public double getNumberOfCd() {
        return numberOfCd;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public double getTotalWages() {
        totalWages = numberOfCd * ratePerDay;
        return totalWages;
    }

    public String getCrewPersonelListId() {
        return crewPersonelListId;
    }

    public void setPersonelId(String personelId) {
        this.personelId = personelId;
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

    public void setCrewPersonelListId(String crewPersonelListId) {
        this.crewPersonelListId = crewPersonelListId;
    }
    
    private String id;
    private String crewPersonelId;
    private String personelId;
    private Personnel personnel;
    private double numberOfCd;
    private double ratePerDay;
    private double totalWages;
    private String crewPersonelListId;
}
