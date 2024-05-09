/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class CrewMaterials {

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getCrewMaterialsListId() {
        return crewMaterialsListId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCrewMaterialsListId(String crewMaterialsListId) {
        this.crewMaterialsListId = crewMaterialsListId;
    }
    
    private String id;
    private String description;
    private double quantity;
    private String unit;
    private String crewMaterialsListId;
}
