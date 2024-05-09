/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class RoadSection {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
    
    private String id;
    private String name;
    private String locationId;
}
