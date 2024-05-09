/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.*;

/**
 *
 * @author Vienji
 */
public class CrewPersonnelList {
    private String id;
    private ArrayList<CrewPersonnel> list = new ArrayList();
    
    public void addCrew(CrewPersonnel crewPersonnel){
        list.add(crewPersonnel);
    }
    
    public CrewPersonnel getCrew(String id){
        List<CrewPersonnel> selectedCrew = list.stream()
                                              .filter(c -> 
                                                      c.getId()
                                                       .equals(id))
                                              .toList();
        return selectedCrew.get(0);
    }
    
    public CrewPersonnel get(int i){
        return list.get(i);
    }
    
    public double getTotal(){
        double totalWages = 0.00;
        for(CrewPersonnel c : list){
            totalWages += c.getTotalWages();
        }
        return totalWages;
    }
    
    public void removeCrew(int i){
        list.remove(i);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public ArrayList<CrewPersonnel> toList(){
        return list;
    }
    
    public void editCrewPersonnel(int i, CrewPersonnel crewPersonnel){
        list.set(i, crewPersonnel);
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    public boolean contains(CrewPersonnel operationCrewPersonnel){
        for(CrewPersonnel crewPersonnel: list){
            if(crewPersonnel.getId().equals(operationCrewPersonnel.getId())){
                return true;
            }
        }
        return false;
    }
}
