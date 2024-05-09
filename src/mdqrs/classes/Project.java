/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

/**
 *
 * @author Vienji
 */
public class Project {

    public int getId() {
        return id;
    }

    public String getSourceOfFundID() {
        return sourceOfFundID;
    }

    public String getDescription() {
        return description;
    }

    public double getProjectCost() {
        return projectCost;
    }

    public String getImplementationMode() {
        return implementationMode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSourceOfFundID(String sourceOfFundID) {
        this.sourceOfFundID = sourceOfFundID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectCost(double projectCost) {
        this.projectCost = projectCost;
    }

    public void setImplementationMode(String implementationMode) {
        this.implementationMode = implementationMode;
    }
    
    private int id;
    private String sourceOfFundID;
    private String description;
    private double projectCost;
    private String implementationMode;
}
