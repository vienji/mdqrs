/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.*;
import dbcontroller.Driver;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Vienji
 */
public class ActivityListDBController {
    private String query = "";
    
    public void add(Activity activity, Location location, RoadSection roadSection,boolean isOtherRoadSection, String month, 
                    int year, String implementationMode, int numberOfCD, 
                    OpsEquipmentList opsEquipmentList, CrewPersonnelList crewPersonnelList, 
                    CrewMaterialsList crewMaterialsList, CrewEquipmentList crewEquipmentList, SubActivity subActivity){
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            connection = Driver.getConnection();
            String equipmentListID = "";
            String opsMaintenanceCrewListID = "";
            
            // ---------Tables-----------
            
            
            //Operation Equipment
            if(!opsEquipmentList.isEmpty()){

                query = "INSERT INTO ops_equipment_list (type) VALUES (?)";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, "false");
                preparedStatement.executeUpdate();

                query = "UPDATE ops_equipment_list SET oelid = CONCAT('OEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();

                query = "SELECT oelid FROM ops_equipment_list WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);

                result = preparedStatement.executeQuery();
                while(result.next()){
                    equipmentListID = result.getString(1);
                }

                for(int i = 0; i < opsEquipmentList.size(); i++){
                    addOperationEquipment(opsEquipmentList.get(i), equipmentListID);
                }
            }    

            //Maintenance Crew
            if(!crewPersonnelList.isEmpty() || !crewMaterialsList.isEmpty() || !crewEquipmentList.isEmpty()){
                
                query = "INSERT INTO ops_maintenance_crew (type) VALUES (?)";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, "false");
                preparedStatement.executeUpdate();

                query = "UPDATE ops_maintenance_crew SET omid = CONCAT('OM', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                
                query = "SELECT omid FROM ops_maintenance_crew WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);

                result = preparedStatement.executeQuery();
                while(result.next()){
                    opsMaintenanceCrewListID = result.getString(1);
                }
                
                //Crew Personnel
                if(!crewPersonnelList.isEmpty()){
                    String crewPersonnelListID = "";
                
                    query = "INSERT INTO crew_personnel_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_personnel_list SET cplid = CONCAT('CPL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT cplid FROM crew_personnel_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewPersonnelListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_personnel_list_id = '"+ crewPersonnelListID +"' WHERE omid = '" + opsMaintenanceCrewListID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                        addOperationCrewPersonnel(crewPersonnelList.get(i), crewPersonnelListID);
                    }
                }
                
                //Crew Materials
                if(!crewMaterialsList.isEmpty()){
                    String crewMaterialsListID = "";
                
                    query = "INSERT INTO crew_materials_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_materials_list SET cmlid = CONCAT('CML', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT cmlid FROM crew_materials_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewMaterialsListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_materials_list_id = '"+ crewMaterialsListID +"' WHERE  omid = '" + opsMaintenanceCrewListID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewMaterialsList.toList().size(); i++){
                        addOperationCrewMaterials(crewMaterialsList.get(i), crewMaterialsListID);
                    }
                }
                
                //Crew Equipment
                if(!crewEquipmentList.isEmpty()){
                    String crewEquipmentListID = "";
                
                    query = "INSERT INTO crew_equipment_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_equipment_list SET celid = CONCAT('CEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT celid FROM crew_equipment_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewEquipmentListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_equipment_list_id = '"+ crewEquipmentListID +"' WHERE  omid = '" + opsMaintenanceCrewListID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewEquipmentList.toList().size(); i++){
                        addOperationCrewEquipment(crewEquipmentList.get(i), crewEquipmentListID);
                    }
                }  
            }
               
            // ---------Data----------- 
            
            query = "INSERT INTO regular_activity (activity_number, road_section, is_other_road_section, location_id, days_of_operation, month, year, ops_equipment_list_id, ops_maintenance_crew_id, implementation_mode, sub_activity_id) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, activity.getItemNumber());
            preparedStatement.setString(2, roadSection.getId());
            preparedStatement.setString(3, String.valueOf(isOtherRoadSection));
            preparedStatement.setString(4, location.getId());
            preparedStatement.setInt(5, numberOfCD);
            preparedStatement.setString(6, month);
            preparedStatement.setInt(7, year);
            preparedStatement.setString(8, equipmentListID);
            preparedStatement.setString(9, opsMaintenanceCrewListID);
            preparedStatement.setString(10, implementationMode);
            preparedStatement.setInt(11, subActivity.getId());
            
            preparedStatement.executeUpdate();
            
            query = "UPDATE regular_activity SET ralid = CONCAT('RAL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
    }
    
    public void add(Activity activity, Location location, String roadSection, boolean isOtherRoadSection, String month, 
                    int year, String implementationMode, int numberOfCD, 
                    OpsEquipmentList opsEquipmentList, CrewPersonnelList crewPersonnelList, 
                    CrewMaterialsList crewMaterialsList, CrewEquipmentList crewEquipmentList, SubActivity subActivity){
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            connection = Driver.getConnection();
            String equipmentListID = "";
            String opsMaintenanceCrewListID = "";
            
            // ---------Tables-----------
            
            
            //Operation Equipment
                if(!opsEquipmentList.isEmpty()){
                
                    query = "INSERT INTO ops_equipment_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE ops_equipment_list SET oelid = CONCAT('OEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT oelid FROM ops_equipment_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        equipmentListID = result.getString(1);
                    }
                    
                    for(int i = 0; i < opsEquipmentList.toList().size(); i++){
                        try {
                            OpsEquipment opsEquipment = opsEquipmentList.get(i);
                            
                            query = "INSERT INTO ops_equipment (equipment_number, personel_id, number_of_cd, rate_per_day, fuel_consumption, fuel_cost, lubricant_amount, equipment_list_id) "
                                    + "VALUES (?,?,?,?,?,?,?,?)";

                            preparedStatement = connection.prepareStatement(query);
                            
                            preparedStatement.setString(1, opsEquipment.getEquipment().getEquipmentNumber());
                            preparedStatement.setString(2, opsEquipment.getPersonnel().getId());
                            preparedStatement.setInt(3, opsEquipment.getNumberOfCd());
                            preparedStatement.setDouble(4, opsEquipment.getRatePerDay());
                            preparedStatement.setDouble(5, opsEquipment.getFuelConsumption());
                            preparedStatement.setDouble(6, opsEquipment.getFuelCost());
                            preparedStatement.setDouble(7, opsEquipment.getLubricantAmount());
                            preparedStatement.setString(8, equipmentListID);
                            
                            preparedStatement.executeUpdate();
                            
                            query = "UPDATE ops_equipment SET oeid = CONCAT('OE', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.executeUpdate();
                            
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }    
                
            
            //Maintenance Crew
            if(!crewPersonnelList.isEmpty() || !crewMaterialsList.isEmpty() || !crewEquipmentList.isEmpty()){
                
                query = "INSERT INTO ops_maintenance_crew (type) VALUES (?)";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, "false");
                preparedStatement.executeUpdate();

                query = "UPDATE ops_maintenance_crew SET omid = CONCAT('OM', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                
                query = "SELECT omid FROM ops_maintenance_crew WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);

                result = preparedStatement.executeQuery();
                while(result.next()){
                    opsMaintenanceCrewListID = result.getString(1);
                }
                
                //Crew Personnel
                if(!crewPersonnelList.isEmpty()){
                    String crewPersonnelListID = "";
                
                    query = "INSERT INTO crew_personnel_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_personnel_list SET cplid = CONCAT('CPL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT cplid FROM crew_personnel_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewPersonnelListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_personnel_list_id = '"+ crewPersonnelListID +"' WHERE omid = '" + opsMaintenanceCrewListID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                        try {
                            CrewPersonnel crewPersonnel = crewPersonnelList.get(i);
                            
                            query = "INSERT INTO crew_personnel (personnel_id, number_of_cd, rate_per_day, crew_personnel_list_id) "
                                    + "VALUES (?,?,?,?)";

                            preparedStatement = connection.prepareStatement(query);
                            
                            preparedStatement.setString(1, crewPersonnel.getPersonnel().getId());
                            preparedStatement.setInt(2, crewPersonnel.getNumberOfCd());
                            preparedStatement.setDouble(3, crewPersonnel.getRatePerDay());
                            preparedStatement.setString(4, crewPersonnelListID);                            
                            
                            preparedStatement.executeUpdate();
                            
                            query = "UPDATE crew_personnel SET cpid = CONCAT('CP', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.executeUpdate();
                            
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                //Crew Materials
                if(!crewMaterialsList.isEmpty()){
                    String crewMaterialsListID = "";
                
                    query = "INSERT INTO crew_materials_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_materials_list SET cmlid = CONCAT('CML', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT cmlid FROM crew_materials_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewMaterialsListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_materials_list_id = '"+ crewMaterialsListID +"' WHERE  omid = '" + opsMaintenanceCrewListID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewMaterialsList.toList().size(); i++){
                        try {
                            CrewMaterials crewMaterials = crewMaterialsList.get(i);
                            
                            query = "INSERT INTO crew_materials (description, quantity, unit, crew_materials_list_id) "
                                    + "VALUES (?,?,?,?)";

                            preparedStatement = connection.prepareStatement(query);
                            
                            preparedStatement.setString(1, crewMaterials.getDescription());
                            preparedStatement.setDouble(2, crewMaterials.getQuantity());
                            preparedStatement.setString(3, crewMaterials.getUnit());
                            preparedStatement.setString(4, crewMaterialsListID);
                            
                            preparedStatement.executeUpdate();
                            
                            query = "UPDATE crew_materials SET cmid = CONCAT('CM', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.executeUpdate();
                            
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                //Crew Equipment
                if(!crewEquipmentList.isEmpty()){
                    String crewEquipmentListID = "";
                
                    query = "INSERT INTO crew_equipment_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_equipment_list SET celid = CONCAT('CEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT celid FROM crew_equipment_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewEquipmentListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_equipment_list_id = '"+ crewEquipmentListID +"' WHERE  omid = '" + opsMaintenanceCrewListID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewEquipmentList.toList().size(); i++){
                        try {
                            CrewEquipment crewEquipment = crewEquipmentList.get(i);
                            
                            query = "INSERT INTO crew_equipment (equipment_number, number_of_cd, rate_per_day, fuel_consumption, fuel_cost, lubricant_amount, crew_equipment_list_id) "
                                    + "VALUES (?,?,?,?,?,?,?)";

                            preparedStatement = connection.prepareStatement(query);
                            
                            preparedStatement.setString(1, crewEquipment.getEquipment().getEquipmentNumber());
                            preparedStatement.setInt(2, crewEquipment.getNumberOfCd());
                            preparedStatement.setDouble(3, crewEquipment.getRatePerDay());
                            preparedStatement.setDouble(4, crewEquipment.getFuelConsumption());
                            preparedStatement.setDouble(5, crewEquipment.getFuelCost());
                            preparedStatement.setDouble(6, crewEquipment.getLubricantAmount());
                            preparedStatement.setString(7, crewEquipmentListID);
                            
                            preparedStatement.executeUpdate();
                            
                            query = "UPDATE crew_equipment SET ceid = CONCAT('CE', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.executeUpdate();
                            
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
            }
            
         
            // ---------Data----------- 
            
            query = "INSERT INTO regular_activity (activity_number, road_section, is_other_road_section, location_id, days_of_operation, month, year, ops_equipment_list_id, ops_maintenance_crew_id, implementation_mode, sub_activity_id) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, activity.getItemNumber());
            preparedStatement.setString(2, roadSection);
            preparedStatement.setString(3, String.valueOf(isOtherRoadSection));
            preparedStatement.setString(4, location.getId());
            preparedStatement.setInt(5, numberOfCD);
            preparedStatement.setString(6, month);
            preparedStatement.setInt(7, year);
            preparedStatement.setString(8, equipmentListID);
            preparedStatement.setString(9, opsMaintenanceCrewListID);
            preparedStatement.setString(10, implementationMode);
            preparedStatement.setInt(11, subActivity.getId());
            
            preparedStatement.executeUpdate();
            
            query = "UPDATE regular_activity SET ralid = CONCAT('RAL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
    }
    
    public void addOperationEquipment(OpsEquipment opsEquipment, String equipmentListID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
                
        try {               
            query = "INSERT INTO ops_equipment (equipment_number, personel_id, number_of_cd, rate_per_day, fuel_consumption, fuel_cost, lubricant_amount, equipment_list_id) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
                            
            preparedStatement.setString(1, opsEquipment.getEquipment().getEquipmentNumber());
            preparedStatement.setString(2, opsEquipment.getPersonnel().getId());
            preparedStatement.setInt(3, opsEquipment.getNumberOfCd());
            preparedStatement.setDouble(4, opsEquipment.getRatePerDay());
            preparedStatement.setDouble(5, opsEquipment.getFuelConsumption());
            preparedStatement.setDouble(6, opsEquipment.getFuelCost());
            preparedStatement.setDouble(7, opsEquipment.getLubricantAmount());
            preparedStatement.setString(8, equipmentListID);
                            
            preparedStatement.executeUpdate();
                            
            query = "UPDATE ops_equipment SET oeid = CONCAT('OE', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void addOperationCrewPersonnel(CrewPersonnel crewPersonnel, String crewPersonnelListID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = Driver.getConnection();
                            
            query = "INSERT INTO crew_personnel (personnel_id, number_of_cd, rate_per_day, crew_personnel_list_id) "
                    + "VALUES (?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, crewPersonnel.getPersonnel().getId());
            preparedStatement.setInt(2, crewPersonnel.getNumberOfCd());
            preparedStatement.setDouble(3, crewPersonnel.getRatePerDay());
            preparedStatement.setString(4, crewPersonnelListID);                            

            preparedStatement.executeUpdate();

            query = "UPDATE crew_personnel SET cpid = CONCAT('CP', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void addOperationCrewMaterials(CrewMaterials crewMaterials, String crewMaterialsListID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = Driver.getConnection();

            query = "INSERT INTO crew_materials (description, quantity, unit, crew_materials_list_id) "
                    + "VALUES (?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, crewMaterials.getDescription());
            preparedStatement.setDouble(2, crewMaterials.getQuantity());
            preparedStatement.setString(3, crewMaterials.getUnit());
            preparedStatement.setString(4, crewMaterialsListID);

            preparedStatement.executeUpdate();

            query = "UPDATE crew_materials SET cmid = CONCAT('CM', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void addOperationCrewEquipment(CrewEquipment crewEquipment, String crewEquipmentListID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = Driver.getConnection();
            query = "INSERT INTO crew_equipment (equipment_number, number_of_cd, rate_per_day, fuel_consumption, fuel_cost, lubricant_amount, crew_equipment_list_id) "
                    + "VALUES (?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, crewEquipment.getEquipment().getEquipmentNumber());
            preparedStatement.setInt(2, crewEquipment.getNumberOfCd());
            preparedStatement.setDouble(3, crewEquipment.getRatePerDay());
            preparedStatement.setDouble(4, crewEquipment.getFuelConsumption());
            preparedStatement.setDouble(5, crewEquipment.getFuelCost());
            preparedStatement.setDouble(6, crewEquipment.getLubricantAmount());
            preparedStatement.setString(7, crewEquipmentListID);

            preparedStatement.executeUpdate();

            query = "UPDATE crew_equipment SET ceid = CONCAT('CE', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void deleteOperationEquipment(String id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "DELETE FROM ops_equipment WHERE oeid = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void deleteOperationCrewPersonnel(String id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "DELETE FROM crew_personnel WHERE cpid = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void deleteOperationCrewMaterials(String id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "DELETE FROM crew_materials WHERE cmid = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void deleteOperationCrewEquipment(String id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "DELETE FROM crew_equipment WHERE ceid = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void updateOperationEquipment(OpsEquipment opsEquipment){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE ops_equipment SET equipment_number = ?, personel_id = ?, number_of_cd = ?, rate_per_day = ?, fuel_consumption = ?, "
                    + "fuel_cost = ?, lubricant_amount = ? WHERE oeid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, opsEquipment.getEquipment().getEquipmentNumber());
            preparedStatement.setString(2, opsEquipment.getPersonnel().getId());
            preparedStatement.setInt(3, opsEquipment.getNumberOfCd());
            preparedStatement.setDouble(4, opsEquipment.getRatePerDay());
            preparedStatement.setDouble(5, opsEquipment.getFuelConsumption());
            preparedStatement.setDouble(6, opsEquipment.getFuelCost());
            preparedStatement.setDouble(7, opsEquipment.getLubricantAmount());
            preparedStatement.setString(8, opsEquipment.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void updateOperationCrewPersonnel(CrewPersonnel crewPersonnel){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE crew_personnel SET personnel_id = ?, number_of_cd = ?, rate_per_day = ? WHERE cpid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, crewPersonnel.getPersonnel().getId());
            preparedStatement.setInt(2, crewPersonnel.getNumberOfCd());
            preparedStatement.setDouble(3, crewPersonnel.getRatePerDay());
            preparedStatement.setString(4, crewPersonnel.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void updateOperationCrewMaterials(CrewMaterials crewMaterials){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE crew_materials SET description = ?, quantity = ?, unit = ? WHERE cmid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, crewMaterials.getDescription());
            preparedStatement.setDouble(2, crewMaterials.getQuantity());
            preparedStatement.setString(3, crewMaterials.getUnit());
            preparedStatement.setString(4, crewMaterials.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void updateOperationCrewEquipment(CrewEquipment crewEquipment){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE crew_equipment SET equipment_number = ?, number_of_cd = ?, rate_per_day = ?, fuel_consumption = ?, fuel_cost = ?, lubricant_amount = ? WHERE ceid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, crewEquipment.getEquipment().getEquipmentNumber());
            preparedStatement.setInt(2, crewEquipment.getNumberOfCd());
            preparedStatement.setDouble(3, crewEquipment.getRatePerDay());
            preparedStatement.setDouble(4, crewEquipment.getFuelConsumption());
            preparedStatement.setDouble(5, crewEquipment.getFuelCost());
            preparedStatement.setDouble(6, crewEquipment.getLubricantAmount());
            preparedStatement.setString(7, crewEquipment.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public ArrayList<RegularActivity> getList(){
        ArrayList<RegularActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM regular_activity "
                    + "ORDER BY ralid DESC";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
                RegularActivity regularActivity = new RegularActivity();
                
                boolean isOtherRoadSection = Boolean.valueOf(result.getString(5));
                
                regularActivity.setId(result.getString(2));
                regularActivity.setActivity(new ActivityDBController().getActivity(result.getString(3)));
                
                if(isOtherRoadSection){
                    regularActivity.setOtherRoadSection(result.getString(4));
                } else {
                    regularActivity.setRoadSection(new RoadSectionDBController().getRoadSection(result.getString(4)));
                }
                
                regularActivity.setIsOtherRoadSection(isOtherRoadSection);
                regularActivity.setLocation(new LocationDBController().getLocation(result.getString(6)));
                regularActivity.setNumberOfCD(result.getInt(7));
                regularActivity.setMonth(result.getString(8));
                regularActivity.setYear(result.getInt(9));
                regularActivity.setOpsEquipmentListID(result.getString(10));
                regularActivity.setOpsMaintenanceCrewID(result.getString(11));
                regularActivity.setImplementationMode(result.getString(12));
                
                SubActivity subActivity = result.getInt(13) != 0 ? 
                        new SubActivityDBController().getSubActivity(result.getInt(13)) : new SubActivity();
                regularActivity.setSubActivity(subActivity);
                
                list.add(regularActivity);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
            
        return list;
    }
    
    public CrewPersonnelList getRegularActivityOpsCrewPersonnelList(String id){
        CrewPersonnelList list = new CrewPersonnelList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            String opsMaintenanceCrewID = "";
            query = "SELECT crew_personnel_list_id FROM ops_maintenance_crew WHERE omid = '" + id + "'";
            connection = Driver.getConnection();
            
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
               opsMaintenanceCrewID = result.getString(1);
            }
            
            query = "SELECT crew_personnel.cpid, crew_personnel.personnel_id, crew_personnel.number_of_cd, crew_personnel.rate_per_day " +
                    "FROM crew_personnel_list " +
                    "INNER JOIN crew_personnel ON crew_personnel_list.cplid = crew_personnel.crew_personnel_list_id " +
                    "WHERE crew_personnel_list.cplid = '" + opsMaintenanceCrewID + "'";
            
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
               CrewPersonnel crewPersonnel = new CrewPersonnel();
               
               crewPersonnel.setId(result.getString(1));
               crewPersonnel.setPersonnel(new PersonnelDBController().getPersonnel(result.getString(2)));
               crewPersonnel.setNumberOfCd(result.getInt(3));
               crewPersonnel.setRatePerDay(result.getDouble(4));
               
               list.addCrew(crewPersonnel);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
        
        return list;
    }
    
    public OpsEquipmentList getRegularActivityOpsEquipmentList(String id){
        OpsEquipmentList list = new OpsEquipmentList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT ops_equipment.oeid, ops_equipment.equipment_number, ops_equipment.personel_id, ops_equipment.number_of_cd, ops_equipment.rate_per_day, ops_equipment.fuel_consumption, ops_equipment.fuel_cost, ops_equipment.lubricant_amount " +
                    "FROM ops_equipment_list " +
                    "INNER JOIN ops_equipment ON ops_equipment_list.oelid = ops_equipment.equipment_list_id " +
                    "WHERE ops_equipment_list.oelid = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
                OpsEquipment opsEquipment = new OpsEquipment();
                
                opsEquipment.setId(result.getString(1));
                opsEquipment.setEquipment(new EquipmentDBController().getEquipment(result.getString(2)));
                opsEquipment.setPersonnel(new PersonnelDBController().getPersonnel(result.getString(3)));
                opsEquipment.setNumberOfCd(result.getInt(4));
                opsEquipment.setRatePerDay(result.getDouble(5));
                opsEquipment.setTotalWages(opsEquipment.getNumberOfCd() * opsEquipment.getRatePerDay());
                opsEquipment.setFuelConsumption(result.getInt(6));
                opsEquipment.setFuelCost(result.getDouble(7));
                opsEquipment.setFuelAmount(opsEquipment.getFuelConsumption() * opsEquipment.getFuelCost());
                opsEquipment.setLubricantAmount(result.getDouble(8));
                opsEquipment.setTotalCost(opsEquipment.getTotalWages() + opsEquipment.getFuelAmount() + opsEquipment.getLubricantAmount());
                              
                list.addEquipment(opsEquipment);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
             
        return list;
    }
    
    public CrewMaterialsList getRegularActivityOpsCrewMaterialsList(String id){
        CrewMaterialsList list = new CrewMaterialsList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            String opsCrewMaterialsID = "";
            query = "SELECT crew_materials_list_id FROM ops_maintenance_crew WHERE omid = '" + id + "'";
            connection = Driver.getConnection();
            
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
               opsCrewMaterialsID = result.getString(1);
            }
            
            query = "SELECT crew_materials.cmid, crew_materials.description, crew_materials.quantity, crew_materials.unit " +
                    "FROM crew_materials_list " +
                    "INNER JOIN crew_materials ON crew_materials_list.cmlid = crew_materials.crew_materials_list_id " +
                    "WHERE crew_materials_list.cmlid = '" + opsCrewMaterialsID + "'";
            
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
                CrewMaterials crewMaterials = new CrewMaterials();
                
                crewMaterials.setId(result.getString(1));
                crewMaterials.setDescription(result.getString(2));
                crewMaterials.setQuantity(result.getDouble(3));
                crewMaterials.setUnit(result.getString(4));
                
                list.addMaterial(crewMaterials);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
             
        return list;
    
    }
    
    public CrewEquipmentList getRegularActivityOpsCrewEquipmentList(String id){
        CrewEquipmentList list = new CrewEquipmentList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            String opsCrewEquipmentID = "";
            query = "SELECT crew_equipment_list_id FROM ops_maintenance_crew WHERE omid = '" + id + "'";
            connection = Driver.getConnection();
            
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
               opsCrewEquipmentID = result.getString(1);
            }
            
            query = "SELECT crew_equipment.ceid, crew_equipment.equipment_number, crew_equipment.number_of_cd, crew_equipment.rate_per_day, crew_equipment.fuel_consumption, crew_equipment.fuel_cost, crew_equipment.lubricant_amount " +
                    "FROM crew_equipment_list " +
                    "INNER JOIN crew_equipment ON crew_equipment_list.celid = crew_equipment.crew_equipment_list_id " +
                    "WHERE crew_equipment_list.celid = '" + opsCrewEquipmentID + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
                CrewEquipment opsEquipment = new CrewEquipment();
                
                opsEquipment.setId(result.getString(1));
                opsEquipment.setEquipment(new EquipmentDBController().getEquipment(result.getString(2)));
                opsEquipment.setNumberOfCd(result.getInt(3));
                opsEquipment.setRatePerDay(result.getDouble(4));
                opsEquipment.setFuelConsumption(result.getInt(5));
                opsEquipment.setFuelCost(result.getDouble(6));
                opsEquipment.setFuelAmount(opsEquipment.getFuelConsumption() * opsEquipment.getFuelCost());
                opsEquipment.setLubricantAmount(result.getDouble(7));
                opsEquipment.setTotalCost(opsEquipment.getTotalWages() + opsEquipment.getFuelAmount() + opsEquipment.getLubricantAmount());
                              
                list.addCrewEquipment(opsEquipment);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
             
        return list;
    }
    
    public String[] getOpsMaintenanceCrewID(String id){
        String[] listIDs = new String[3];
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT crew_personnel_list_id, crew_materials_list_id, crew_equipment_list_id FROM ops_maintenance_crew WHERE omid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, id);
            
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                listIDs[0] = result.getString(1);
                listIDs[1] = result.getString(2);
                listIDs[2] = result.getString(3);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
        
        return listIDs;
    }
    
    public void edit(RegularActivity regularActivity, OpsEquipmentList opsEquipmentList, CrewPersonnelList crewPersonnelList, 
                    CrewMaterialsList crewMaterialsList, CrewEquipmentList crewEquipmentList){
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
    
        try {   
            String opsEquipmentListID = regularActivity.getOpsEquipmentListID();
            String opsMaintenanceCrewID = regularActivity.getOpsMaintenanceCrewID();
            String roadSection = regularActivity.isIsOtherRoadSection() ? regularActivity.getOtherRoadSection() : regularActivity.getRoadSection().getId();
            
            query = "UPDATE regular_activity SET "
                    + "activity_number = '" + regularActivity.getActivity().getItemNumber() + "', "
                    + "road_section = '" + roadSection + "', "
                    + "is_other_road_section = '" + String.valueOf(regularActivity.isIsOtherRoadSection()) + "', "
                    + "location_id = '" + regularActivity.getLocation().getId() + "', "
                    + "days_of_operation = " + regularActivity.getNumberOfCD() + ", "
                    + "month = '" + regularActivity.getMonth() + "', "
                    + "year = " + regularActivity.getYear() + ", "
                    + "implementation_mode = '" + regularActivity.getImplementationMode() + "', " 
                    + "sub_activity_id = " + regularActivity.getSubActivity().getId() + " "
                    + "WHERE ralid = '" + regularActivity.getId() + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
            //Operation Equipment
            if(opsEquipmentListID.isBlank()){
                
                query = "INSERT INTO ops_equipment_list (type) VALUES (?)";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, "false");
                preparedStatement.executeUpdate();

                query = "UPDATE ops_equipment_list SET oelid = CONCAT('OEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();

                query = "SELECT oelid FROM ops_equipment_list WHERE id = last_insert_id()";

                preparedStatement = connection.prepareStatement(query);

                result = preparedStatement.executeQuery();
                while(result.next()){
                    regularActivity.setOpsEquipmentListID(result.getString(1));
                }

                for(int i = 0; i < opsEquipmentList.size(); i++){
                    addOperationEquipment(opsEquipmentList.get(i), regularActivity.getOpsEquipmentListID());
                }
                
            } else {
                OpsEquipmentList operationEquipmentList = getRegularActivityOpsEquipmentList(opsEquipmentListID);
                
                if(!opsEquipmentList.isEmpty()){
                    for(int i = 0; i < operationEquipmentList.size(); i++){
                        OpsEquipment opsEquipment = operationEquipmentList.get(i);
                        if(!opsEquipmentList.contains(opsEquipment)){
                            deleteOperationEquipment(opsEquipment.getId());
                        }
                    } 

                    for(int i = 0; i < opsEquipmentList.size(); i++){
                        OpsEquipment opsEquipment = opsEquipmentList.get(i);

                        if(!opsEquipment.getId().equalsIgnoreCase("New")){
                            updateOperationEquipment(opsEquipment);
                        } else {
                            addOperationEquipment(opsEquipment,opsEquipmentListID);
                        }
                    }
                } else {
                    for(int i = 0; i < operationEquipmentList.size(); i++){
                        OpsEquipment opsEquipment = operationEquipmentList.get(i);
                        deleteOperationEquipment(opsEquipment.getId());
                    }
                    
                    regularActivity.setOpsEquipmentListID("");
                }
            }
            
            //Maintenance Crew

            if(!opsMaintenanceCrewID.isBlank()){
                String[] opsMaintenanceCrewIDs = getOpsMaintenanceCrewID(opsMaintenanceCrewID);
                
                //Crew Personnel
                if(opsMaintenanceCrewIDs[0].isBlank()){
                    String crewPersonnelListID = "";
                
                    query = "INSERT INTO crew_personnel_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_personnel_list SET cplid = CONCAT('CPL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT cplid FROM crew_personnel_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewPersonnelListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_personnel_list_id = '"+ crewPersonnelListID +"' WHERE omid = '" + opsMaintenanceCrewID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                        addOperationCrewPersonnel(crewPersonnelList.get(i), crewPersonnelListID);
                    }
                    
                    opsMaintenanceCrewIDs[0] = crewPersonnelListID;
                } else {
                    CrewPersonnelList operationCrewPersonnelList = getRegularActivityOpsCrewPersonnelList(opsMaintenanceCrewID);
                    
                    if(!crewPersonnelList.isEmpty()){
                        
                        for(int i = 0; i < operationCrewPersonnelList.toList().size(); i++){
                            CrewPersonnel crewPersonnel = operationCrewPersonnelList.get(i);
                            if(!crewPersonnelList.contains(crewPersonnel)){
                                deleteOperationCrewPersonnel(crewPersonnel.getId());
                            }
                            
                        } 

                        for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                            CrewPersonnel crewPersonnel = crewPersonnelList.get(i);
                            
                            if(!crewPersonnel.getId().equalsIgnoreCase("New")){
                                updateOperationCrewPersonnel(crewPersonnel);
                            } else {
                                addOperationCrewPersonnel(crewPersonnel,opsMaintenanceCrewIDs[0]);
                            }
                        }
                        
                    } else {

                        for(int i = 0; i < operationCrewPersonnelList.toList().size(); i++){
                            CrewPersonnel crewPersonnel = operationCrewPersonnelList.get(i);
                            deleteOperationCrewPersonnel(crewPersonnel.getId());
                        }

                        opsMaintenanceCrewIDs[0] = "";
                    }
                }
                
                //Crew Materials
                if(opsMaintenanceCrewIDs[1].isBlank()){
                    String crewMaterialsListID = "";
                
                    query = "INSERT INTO crew_materials_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_materials_list SET cmlid = CONCAT('CML', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT cmlid FROM crew_materials_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewMaterialsListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_materials_list_id = '"+ crewMaterialsListID +"' WHERE  omid = '" + opsMaintenanceCrewID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewMaterialsList.toList().size(); i++){
                        addOperationCrewMaterials(crewMaterialsList.get(i), crewMaterialsListID);
                    }
                    
                    opsMaintenanceCrewIDs[1] = crewMaterialsListID;
                } else {
                    CrewMaterialsList operationCrewMaterialsList = getRegularActivityOpsCrewMaterialsList(opsMaintenanceCrewID);
                    
                    if(!crewMaterialsList.isEmpty()){
                        
                        for(int i = 0; i < operationCrewMaterialsList.toList().size(); i++){
                            CrewMaterials crewMaterials = operationCrewMaterialsList.get(i);
                            if(!crewMaterialsList.contains(crewMaterials)){
                                deleteOperationCrewMaterials(crewMaterials.getId());
                            }
                            
                        } 

                        for(int i = 0; i < crewMaterialsList.toList().size(); i++){
                            CrewMaterials crewMaterials = crewMaterialsList.get(i);
                            
                            if(!crewMaterials.getId().equalsIgnoreCase("New")){
                                updateOperationCrewMaterials(crewMaterials);
                            } else {
                                addOperationCrewMaterials(crewMaterials,opsMaintenanceCrewIDs[1]);
                            }
                        }
                        
                    } else {

                        for(int i = 0; i < operationCrewMaterialsList.toList().size(); i++){
                            CrewMaterials crewMaterials = operationCrewMaterialsList.get(i);
                            deleteOperationCrewMaterials(crewMaterials.getId());
                        }

                        opsMaintenanceCrewIDs[1] = "";
                    }
                }
                             
                //Crew Equipment
                if(opsMaintenanceCrewIDs[2].isBlank()){
                    String crewEquipmentListID = "";
                
                    query = "INSERT INTO crew_equipment_list (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE crew_equipment_list SET celid = CONCAT('CEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT celid FROM crew_equipment_list WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        crewEquipmentListID = result.getString(1);
                    }
                    
                    query = "UPDATE ops_maintenance_crew SET crew_equipment_list_id = '"+ crewEquipmentListID +"' WHERE  omid = '" + opsMaintenanceCrewID + "'";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    
                    for(int i = 0; i < crewEquipmentList.toList().size(); i++){
                        addOperationCrewEquipment(crewEquipmentList.get(i), crewEquipmentListID);
                    }
                    
                    opsMaintenanceCrewIDs[2] = crewEquipmentListID;
                } else {
                    CrewEquipmentList operationCrewEquipmentList = getRegularActivityOpsCrewEquipmentList(opsMaintenanceCrewID);
                    
                    if(!crewEquipmentList.isEmpty()){
                        
                        for(int i = 0; i < operationCrewEquipmentList.toList().size(); i++){
                            CrewEquipment crewEquipment = operationCrewEquipmentList.get(i);
                            if(!crewEquipmentList.contains(crewEquipment)){
                                deleteOperationCrewEquipment(crewEquipment.getId());
                            } 
                        } 

                        for(int i = 0; i < crewEquipmentList.toList().size(); i++){
                            CrewEquipment crewEquipment = crewEquipmentList.get(i);
                            
                            if(!crewEquipment.getId().equalsIgnoreCase("New")){
                                updateOperationCrewEquipment(crewEquipment);
                            } else {
                                addOperationCrewEquipment(crewEquipment,opsMaintenanceCrewIDs[2]);
                            }
                        }
                        
                    } else {

                        for(int i = 0; i < operationCrewEquipmentList.toList().size(); i++){
                            CrewEquipment crewEquipment = operationCrewEquipmentList.get(i);
                            deleteOperationCrewEquipment(crewEquipment.getId());
                        }

                        opsMaintenanceCrewIDs[2] = "";
                    }
                }
                
                //Finalization for maintenance crew table id's
                if(opsMaintenanceCrewIDs[0].isBlank() && opsMaintenanceCrewIDs[1].isBlank() && opsMaintenanceCrewIDs[2].isBlank()){
                    regularActivity.setOpsMaintenanceCrewID("");
                } else {
                    query = "UPDATE ops_maintenance_crew SET crew_personnel_list_id = ?, crew_materials_list_id = ?, crew_equipment_list_id = ? WHERE omid = ?";

                    preparedStatement = connection.prepareStatement(query);
                    
                    preparedStatement.setString(1, opsMaintenanceCrewIDs[0]);
                    preparedStatement.setString(2, opsMaintenanceCrewIDs[1]);
                    preparedStatement.setString(3, opsMaintenanceCrewIDs[2]);
                    preparedStatement.setString(4, opsMaintenanceCrewID);
                    
                    preparedStatement.executeUpdate();
                }
            } else {
                //Create new maintenance crew set
                if(!crewPersonnelList.isEmpty() || !crewMaterialsList.isEmpty() || !crewEquipmentList.isEmpty()){
                
                    query = "INSERT INTO ops_maintenance_crew (type) VALUES (?)";
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "false");
                    preparedStatement.executeUpdate();

                    query = "UPDATE ops_maintenance_crew SET omid = CONCAT('OM', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                    query = "SELECT omid FROM ops_maintenance_crew WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);

                    result = preparedStatement.executeQuery();
                    while(result.next()){
                        opsMaintenanceCrewID = result.getString(1);
                    }

                    //Crew Personnel
                    if(!crewPersonnelList.isEmpty()){
                        String crewPersonnelListID = "";

                        query = "INSERT INTO crew_personnel_list (type) VALUES (?)";
                        preparedStatement = connection.prepareStatement(query);

                        preparedStatement.setString(1, "false");
                        preparedStatement.executeUpdate();

                        query = "UPDATE crew_personnel_list SET cplid = CONCAT('CPL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                        query = "SELECT cplid FROM crew_personnel_list WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);

                        result = preparedStatement.executeQuery();
                        while(result.next()){
                            crewPersonnelListID = result.getString(1);
                        }

                        query = "UPDATE ops_maintenance_crew SET crew_personnel_list_id = '"+ crewPersonnelListID +"' WHERE omid = '" + opsMaintenanceCrewID + "'";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                        for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                            addOperationCrewPersonnel(crewPersonnelList.get(i), crewPersonnelListID);
                        }
                    }

                    //Crew Materials
                    if(!crewMaterialsList.isEmpty()){
                        String crewMaterialsListID = "";

                        query = "INSERT INTO crew_materials_list (type) VALUES (?)";
                        preparedStatement = connection.prepareStatement(query);

                        preparedStatement.setString(1, "false");
                        preparedStatement.executeUpdate();

                        query = "UPDATE crew_materials_list SET cmlid = CONCAT('CML', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                        query = "SELECT cmlid FROM crew_materials_list WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);

                        result = preparedStatement.executeQuery();
                        while(result.next()){
                            crewMaterialsListID = result.getString(1);
                        }

                        query = "UPDATE ops_maintenance_crew SET crew_materials_list_id = '"+ crewMaterialsListID +"' WHERE  omid = '" + opsMaintenanceCrewID + "'";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                        for(int i = 0; i < crewMaterialsList.toList().size(); i++){
                            addOperationCrewMaterials(crewMaterialsList.get(i), crewMaterialsListID);
                        }
                    }

                    //Crew Equipment
                    if(!crewEquipmentList.isEmpty()){
                        String crewEquipmentListID = "";

                        query = "INSERT INTO crew_equipment_list (type) VALUES (?)";
                        preparedStatement = connection.prepareStatement(query);

                        preparedStatement.setString(1, "false");
                        preparedStatement.executeUpdate();

                        query = "UPDATE crew_equipment_list SET celid = CONCAT('CEL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                        query = "SELECT celid FROM crew_equipment_list WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);

                        result = preparedStatement.executeQuery();
                        while(result.next()){
                            crewEquipmentListID = result.getString(1);
                        }

                        query = "UPDATE ops_maintenance_crew SET crew_equipment_list_id = '"+ crewEquipmentListID +"' WHERE  omid = '" + opsMaintenanceCrewID + "'";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                        for(int i = 0; i < crewEquipmentList.toList().size(); i++){
                            addOperationCrewEquipment(crewEquipmentList.get(i), crewEquipmentListID);
                        }
                    }  
                }
                
                regularActivity.setOpsMaintenanceCrewID(opsMaintenanceCrewID);
            }
            
            // ---------Data----------- 
            
            query = "UPDATE regular_activity SET ops_equipment_list_id = ?, ops_maintenance_crew_id = ? WHERE ralid = ?";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, regularActivity.getOpsEquipmentListID());
            preparedStatement.setString(2, regularActivity.getOpsMaintenanceCrewID());
            preparedStatement.setString(3, regularActivity.getId());
            
            preparedStatement.executeUpdate();     
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
        }
    }   
    
    public void delete(RegularActivity regularActivity){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            //Operation Equipment
            OpsEquipmentList operationEquipmentList = getRegularActivityOpsEquipmentList(regularActivity.getOpsEquipmentListID());
            
            for(int i = 0; i < operationEquipmentList.size(); i++){
                OpsEquipment opsEquipment = operationEquipmentList.get(i);
                deleteOperationEquipment(opsEquipment.getId());
            }
            
            //Crew Personnel
            CrewPersonnelList operationCrewPersonnelList = getRegularActivityOpsCrewPersonnelList(regularActivity.getOpsMaintenanceCrewID());
            
            for(int i = 0; i < operationCrewPersonnelList.toList().size(); i++){
                CrewPersonnel crewPersonnel = operationCrewPersonnelList.get(i);
                deleteOperationCrewPersonnel(crewPersonnel.getId());
            }
            
            //Crew Materials
            CrewMaterialsList operationCrewMaterialsList = getRegularActivityOpsCrewMaterialsList(regularActivity.getOpsMaintenanceCrewID());
            
            for(int i = 0; i < operationCrewMaterialsList.toList().size(); i++){
                CrewMaterials crewMaterials = operationCrewMaterialsList.get(i);
                deleteOperationCrewMaterials(crewMaterials.getId());
            }
            
            //Crew Equipment
            CrewEquipmentList operationCrewEquipmentList = getRegularActivityOpsCrewEquipmentList(regularActivity.getOpsMaintenanceCrewID());
            
            for(int i = 0; i < operationCrewEquipmentList.toList().size(); i++){
                CrewEquipment crewEquipment = operationCrewEquipmentList.get(i);
                deleteOperationCrewEquipment(crewEquipment.getId());
            }
            
            String[] opsMaintenanceCrewIDs = getOpsMaintenanceCrewID(regularActivity.getOpsMaintenanceCrewID());
            
            if(opsMaintenanceCrewIDs[0] != null){
                query = "DELETE FROM crew_personnel_list WHERE cplid = ?";
                connection = Driver.getConnection();
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, opsMaintenanceCrewIDs[0]);
                preparedStatement.executeUpdate();
            }
            
            if(opsMaintenanceCrewIDs[1] != null){
                query = "DELETE FROM crew_materials_list WHERE cmlid = ?";
                connection = Driver.getConnection();
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, opsMaintenanceCrewIDs[1]);
                preparedStatement.executeUpdate();
            }
            
            if(opsMaintenanceCrewIDs[2] != null){
                query = "DELETE FROM crew_equipment_list WHERE celid = ?";
                connection = Driver.getConnection();
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, opsMaintenanceCrewIDs[2]);
                preparedStatement.executeUpdate();
            }
            
            query = "DELETE FROM ops_maintenance_crew WHERE omid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, regularActivity.getOpsMaintenanceCrewID());
            preparedStatement.executeUpdate();
            
            query = "DELETE FROM regular_activity WHERE ralid = ?";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, regularActivity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
}
