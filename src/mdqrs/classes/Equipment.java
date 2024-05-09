/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class Equipment {

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public String getType() {
        return type;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    private String equipmentNumber;
    private String type;
}
