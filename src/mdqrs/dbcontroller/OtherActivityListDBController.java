/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.CrewPersonnel;
import classes.CrewPersonnelList;
import classes.OtherActivity;
import dbcontroller.Driver;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Vienji
 */
public class OtherActivityListDBController {
    private String query = "";
    
    public void add(OtherActivity otherActivity, CrewPersonnelList crewPersonnelList){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            String otherActivityID = "";
            query = "INSERT INTO other_activity (description, sub_activity_id, month, year, implementation_mode, days_of_operation) VALUES (?,?,?,?,?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, otherActivity.getDescription());
            preparedStatement.setInt(2, otherActivity.getSubActivity().getId());
            preparedStatement.setString(3, otherActivity.getMonth());
            preparedStatement.setInt(4, otherActivity.getYear());
            preparedStatement.setString(5, otherActivity.getImplementationMode());
            preparedStatement.setInt(6, otherActivity.getNumberOfCD());
            
            preparedStatement.executeUpdate();
            
            query = "UPDATE other_activity SET oalid = CONCAT('OAL', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
            query = "SELECT oalid FROM other_activity WHERE id = last_insert_id()";

            preparedStatement = connection.prepareStatement(query);

            result = preparedStatement.executeQuery();
            while(result.next()){
                otherActivityID = result.getString(1);
            }
            
            for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                try {
                    CrewPersonnel crewPersonnel = crewPersonnelList.get(i);

                    query = "INSERT INTO other_activity_crew_personnel (personnel_id, number_of_cd, rate_per_day, other_activity_id) "
                            + "VALUES (?,?,?,?)";

                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, crewPersonnel.getPersonnel().getId());
                    preparedStatement.setInt(2, crewPersonnel.getNumberOfCd());
                    preparedStatement.setDouble(3, crewPersonnel.getRatePerDay());
                    preparedStatement.setString(4, otherActivityID);                            

                    preparedStatement.executeUpdate();

                    query = "UPDATE other_activity_crew_personnel SET cpid = CONCAT('OACP', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    }
    
    public ArrayList<OtherActivity> getList(){
        ArrayList<OtherActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM other_activity "
                    + "ORDER BY oalid DESC";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherActivity otherActivity = new OtherActivity();
                
                otherActivity.setId(result.getString(2));
                otherActivity.setDescription(result.getString(3));
                otherActivity.setSubActivity(new SubActivityDBController().getSubActivity(result.getInt(4)));
                otherActivity.setMonth(result.getString(5));
                otherActivity.setYear(result.getInt(6));
                otherActivity.setImplementationMode(result.getString(7));
                otherActivity.setNumberOfCD(result.getInt(8));
                
                list.add(otherActivity);
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
    
    public CrewPersonnelList getOtherActivityOpsCrewPersonnelList(String id){
        CrewPersonnelList list = new CrewPersonnelList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM other_activity_crew_personnel WHERE other_activity_id = '" + id + "'";
            connection = Driver.getConnection();
            
            preparedStatement = connection.prepareStatement(query);
        
            result = preparedStatement.executeQuery();
            while(result.next()){
               CrewPersonnel crewPersonnel = new CrewPersonnel();
               
               crewPersonnel.setId(result.getString(2));
               crewPersonnel.setPersonnel(new PersonnelDBController().getPersonnel(result.getString(3)));
               crewPersonnel.setNumberOfCd(result.getInt(4));
               crewPersonnel.setRatePerDay(result.getDouble(5));
               
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
    
    public void edit(OtherActivity otherActivity, CrewPersonnelList crewPersonnelList){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            query = "UPDATE other_activity SET description = ?, "
                    + "sub_activity_id = ?, "
                    + "month = ?, year = ?, "
                    + "implementation_mode = ?, "
                    + "days_of_operation = ? "
                    + "WHERE oalid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, otherActivity.getDescription());
            preparedStatement.setInt(2, otherActivity.getSubActivity().getId());
            preparedStatement.setString(3, otherActivity.getMonth());
            preparedStatement.setInt(4, otherActivity.getYear());
            preparedStatement.setString(5, otherActivity.getImplementationMode());
            preparedStatement.setInt(6, otherActivity.getNumberOfCD());
            preparedStatement.setString(7, otherActivity.getId());
            
            preparedStatement.executeUpdate();
            
            if(getOtherActivityOpsCrewPersonnelList(otherActivity.getId()).toList().isEmpty()){
                for(int i = 0; i < crewPersonnelList.toList().size(); i++){
                    try {
                        CrewPersonnel crewPersonnel = crewPersonnelList.get(i);

                        query = "INSERT INTO other_activity_crew_personnel (personnel_id, number_of_cd, rate_per_day, other_activity_id) "
                                + "VALUES (?,?,?,?)";

                        preparedStatement = connection.prepareStatement(query);

                        preparedStatement.setString(1, crewPersonnel.getPersonnel().getId());
                        preparedStatement.setInt(2, crewPersonnel.getNumberOfCd());
                        preparedStatement.setDouble(3, crewPersonnel.getRatePerDay());
                        preparedStatement.setString(4, otherActivity.getId());                            

                        preparedStatement.executeUpdate();

                        query = "UPDATE other_activity_crew_personnel SET cpid = CONCAT('OACP', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                CrewPersonnelList operationCrewPersonnelList = getOtherActivityOpsCrewPersonnelList(otherActivity.getId());
                    
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
                                addOperationCrewPersonnel(crewPersonnel, otherActivity.getId());
                            }
                        }
                        
                    } else {

                        for(int i = 0; i < operationCrewPersonnelList.toList().size(); i++){
                            CrewPersonnel crewPersonnel = operationCrewPersonnelList.get(i);
                            deleteOperationCrewPersonnel(crewPersonnel.getId());
                        }
                    }
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
        } 
    }
    
    public void deleteOperationCrewPersonnel(String id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "DELETE FROM other_activity_crew_personnel WHERE cpid = '" + id + "'";
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
    
    public void updateOperationCrewPersonnel(CrewPersonnel crewPersonnel){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE other_activity_crew_personnel SET personnel_id = ?, number_of_cd = ?, rate_per_day = ? WHERE cpid = ?";
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
    
    public void addOperationCrewPersonnel(CrewPersonnel crewPersonnel, String otherActivityID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = Driver.getConnection();
                            
            query = "INSERT INTO other_activity_crew_personnel (personnel_id, number_of_cd, rate_per_day, other_activity_id) "
                    + "VALUES (?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, crewPersonnel.getPersonnel().getId());
            preparedStatement.setInt(2, crewPersonnel.getNumberOfCd());
            preparedStatement.setDouble(3, crewPersonnel.getRatePerDay());
            preparedStatement.setString(4, otherActivityID);                            

            preparedStatement.executeUpdate();

            query = "UPDATE other_activity_crew_personnel SET cpid = CONCAT('CP', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";

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
    
    public void delete(OtherActivity otherActivity){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            connection = Driver.getConnection();
            
            CrewPersonnelList operationCrewPersonnelList = getOtherActivityOpsCrewPersonnelList(otherActivity.getId());
            
            for(int i = 0; i < operationCrewPersonnelList.toList().size(); i++){
                CrewPersonnel crewPersonnel = operationCrewPersonnelList.get(i);
                deleteOperationCrewPersonnel(crewPersonnel.getId());
            }
            
            query = "DELETE FROM other_activity WHERE oalid = ?";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, otherActivity.getId());
            
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
