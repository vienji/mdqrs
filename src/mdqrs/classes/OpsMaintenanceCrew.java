/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class OpsMaintenanceCrew {

    public String getId() {
        return id;
    }

    public String getCrewPersonelListId() {
        return crewPersonelListId;
    }

    public String getCrewMaterialsListId() {
        return crewMaterialsListId;
    }

    public String getCrewEquipmentListId() {
        return crewEquipmentListId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCrewPersonelListId(String crewPersonelListId) {
        this.crewPersonelListId = crewPersonelListId;
    }

    public void setCrewMaterialsListId(String crewMaterialsListId) {
        this.crewMaterialsListId = crewMaterialsListId;
    }

    public void setCrewEquipmentListId(String crewEquipmentListId) {
        this.crewEquipmentListId = crewEquipmentListId;
    }
    
    private String id;
    private String crewPersonelListId;
    private String crewMaterialsListId;
    private String crewEquipmentListId;
}
