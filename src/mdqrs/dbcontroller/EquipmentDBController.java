/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import dbcontroller.Driver;
import java.sql.*;
import java.util.*;
import classes.Equipment;

/**
 *
 * @author Vienji
 */
public class EquipmentDBController {
    private String query = "";
    
    public void add(String equipmentNumber, String type){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO equipment (equipment_number, type) VALUES (?, ?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, equipmentNumber);
            preparedStatement.setString(2, type);
            
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
    
    public ArrayList<Equipment> getList(){
        ArrayList<Equipment> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM equipment WHERE is_deleted = 'no'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Equipment equipment = new Equipment();
                
                equipment.setEquipmentNumber(result.getString(1));
                equipment.setType(result.getString(2));
                
                list.add(equipment);
            }
        } catch (SQLException e){
        
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
    
    public Equipment getEquipment(String id){
        Equipment equipment = new Equipment();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM equipment WHERE equipment_number = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
   
                equipment.setEquipmentNumber(result.getString(1));
                equipment.setType(result.getString(2));
                
            }
        } catch (SQLException e){
        
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
        
        return equipment;
    }
    
    public void edit(String equipmentNumber, String newType){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE equipment SET type = '" + newType + "' WHERE equipment_number = '" + equipmentNumber + "'";
            
            statement.execute(query);
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(statement != null){
                try{statement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void delete(String equipmentNumber){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE equipment SET is_deleted = 'yes' WHERE equipment_number = '" + equipmentNumber + "'";
            
            statement.execute(query);
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(statement != null){
                try{statement.close();}catch(SQLException e){}
            }
        }
    }
    
    public boolean isPresent(String equipmentNumber){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        boolean present = false;
        
        try{
            query = "SELECT * FROM equipment WHERE equipment_number = '" + equipmentNumber + "' AND is_deleted = 'no'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            present = result.next();
        } catch (SQLException e){
        
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
        
        return present;
    }
}
