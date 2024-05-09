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
public class CrewMaterialsList {
    private String id;
    private ArrayList<CrewMaterials> list = new ArrayList();
    
    public void addMaterial(CrewMaterials crewMaterials){
        list.add(crewMaterials);
    }
    
    public CrewMaterials getMaterial(String id){
        List<CrewMaterials> selectedMaterial = list.stream()
                                              .filter(m -> 
                                                      m.getId()
                                                       .equals(id))
                                              .toList();
        return selectedMaterial.get(0);
    }
    
    public void editMaterial(int i, CrewMaterials crewMaterials){
        list.set(i, crewMaterials);
    }
    
    public CrewMaterials get(int i){
        return list.get(i);
    }
    
    public void removeMaterial(int i){
        list.remove(i);
    }
    
    public ArrayList<CrewMaterials> toList(){
        return list;
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
    
    public boolean contains(CrewMaterials operationCrewMaterials){
        for(CrewMaterials crewMaterials: list){
            if(crewMaterials.getId().equals(operationCrewMaterials.getId())){
                return true;
            }
        }
        return false;
    }
}
