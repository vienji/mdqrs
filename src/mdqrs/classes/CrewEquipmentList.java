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
public class CrewEquipmentList {
    private String id;
    private ArrayList<CrewEquipment> list = new ArrayList();
    private boolean use;
    
    
    public void setUse(boolean use){
        this.use = use;
    }
    
    public boolean isUse(){
        return use;
    }
    
    public void addCrewEquipment(CrewEquipment crewEquipment){
        list.add(crewEquipment);
    }
    
    public CrewEquipment getCrewEquipment(String id){
        List<CrewEquipment> selectedEquipment = list.stream()
                                              .filter(e -> 
                                                      e.getId()
                                                       .equals(id))
                                              .toList();
        return selectedEquipment.get(0);
    }
    
    public CrewEquipment get(int i){
        return list.get(i);
    }
    
    public void editCrewEquipment(int i, CrewEquipment crewEquipment){
        list.set(i, crewEquipment);
    }
    
    public ArrayList<CrewEquipment> toList(){
        return list;
    }
    
    public void removeCrewEquipment(int i){
        list.remove(i);
    }
    
    public double getTotal(){
        double total = 0.00;
        for(CrewEquipment ce : list){
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
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    public boolean contains(CrewEquipment operationCrewEquipment){
        for(CrewEquipment crewEquipment: list){
            if(crewEquipment.getId().equals(operationCrewEquipment.getId())){
                return true;
            } 
        }
        return false;
    }
}
