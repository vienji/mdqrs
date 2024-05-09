/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vienji
 */
public class OpsEquipmentList {
    private String id;
    private ArrayList<OpsEquipment> list = new ArrayList();
    
    public void addEquipment(OpsEquipment opsEquipment){
        list.add(opsEquipment);
    }
    
    public OpsEquipment getEquipment(String id){
        List<OpsEquipment> selectedEquipment = list.stream()
                                              .filter(c -> 
                                                      c.getId()
                                                       .equals(id))
                                              .toList();
        return selectedEquipment.get(0);
    }
    
    public OpsEquipment get(int i){
        return list.get(i);
    }
    
    public int size(){
        return list.size();
    }
    
    public ArrayList<OpsEquipment> toList(){
        return list;
    }
    
    public void removeEquipment(int i){
        list.remove(i);
    }
    
    public double getTotal(){
        double total = 0.00;
        for(OpsEquipment ce : list){
            total += ce.getTotalCost();
        }
        return total;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void editEquipment(int i, OpsEquipment opsEquipment) {
        list.set(i, opsEquipment);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    public boolean contains(OpsEquipment operationEquipment){
        for(OpsEquipment opsEquipment: list){
            if(opsEquipment.getId().equals(operationEquipment.getId())){
                return true;
            }
        }
        return false;
    }
}
