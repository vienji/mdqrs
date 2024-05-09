/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mdqrs.listeners;

import classes.CrewEquipment;
import classes.CrewMaterials;
import classes.CrewPersonnel;
import classes.OpsEquipment;
import mdqrs.classes.Project;

/**
 *
 * @author Vienji
 */
public interface MainListener {
    void updateWorkCategory();
    void updateActivity();
    void updateSubActivity();
    void updateEquipment();
    void updatePersonnel();
    
    void addRegularActivityOpsEquipment(OpsEquipment opsEquipment, int formType);
    void addRegularActivityOpsMaintenanceCrew(CrewPersonnel crewPersonnel, int formType);
    void addRegularActivityOpsCrewMaterials(CrewMaterials crewMaterials, int formType);
    void addRegularActivityOpsCrewEquipment(CrewEquipment crewEquipment, int formType);
    void addProject(Project project, int formType);
    
    void editRegularActivityOpsEquipment(int i, OpsEquipment opsEquipment, int formType);
    void editRegularActivityOpsMaintenanceCrew(int i, CrewPersonnel crewPersonnel, int formType);
    void editRegularActivityOpsCrewMaterials(int i, CrewMaterials crewMaterials, int formType);
    void editRegularActivityOpsCrewEquipment(int i, CrewEquipment crewEquipment, int formType);
    void editProject(int i, Project project, int formType);
}
