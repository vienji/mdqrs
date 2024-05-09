/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import mdqrs.classes.DriversForEngineers;
import java.util.*;
import java.sql.*;
import dbcontroller.Driver;

/**
 *
 * @author Vienji
 */
public class DriversForEngineersDBController {
    private String query = "";
    
    public void add(DriversForEngineers driversForEngineers){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO drivers_for_engineers (labor_equipment_cost, equipment_fuel_cost, lubricant_cost, days_of_operation, implementation_mode, month, year) "
                    + "VALUES (?,?,?,?,?,?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setDouble(1, driversForEngineers.getLaborEquipmentCost());
            preparedStatement.setDouble(2, driversForEngineers.getEquipmentFuelCost());
            preparedStatement.setDouble(3, driversForEngineers.getLubricantCost());
            preparedStatement.setInt(4, driversForEngineers.getNumberOfCD());
            preparedStatement.setString(5, driversForEngineers.getImplementationMode());
            preparedStatement.setString(6, driversForEngineers.getMonth());
            preparedStatement.setInt(7, driversForEngineers.getYear());
            
            preparedStatement.executeUpdate();
            
            query = "UPDATE drivers_for_engineers SET deid = CONCAT('DE', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
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
    
    public ArrayList<DriversForEngineers> getList(){
        ArrayList<DriversForEngineers> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM drivers_for_engineers";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                DriversForEngineers driversForEngineers = new DriversForEngineers();
                
                driversForEngineers.setId(result.getString(2));
                driversForEngineers.setLaborEquipmentCost(result.getDouble(3));
                driversForEngineers.setEquipmentFuelCost(result.getDouble(4));
                driversForEngineers.setLubricantCost(result.getDouble(5));
                driversForEngineers.setNumberOfCD(result.getInt(6));
                driversForEngineers.setImplementationMode(result.getString(7));
                driversForEngineers.setMonth(result.getString(8));
                driversForEngineers.setYear(result.getInt(9));
                
                list.add(driversForEngineers);
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
    
    public ArrayList<DriversForEngineers> getList(String month, int year){
        ArrayList<DriversForEngineers> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM drivers_for_engineers WHERE month = ? AND year = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, month);
            preparedStatement.setInt(2, year);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                DriversForEngineers driversForEngineers = new DriversForEngineers();
                
                driversForEngineers.setId(result.getString(2));
                driversForEngineers.setLaborEquipmentCost(result.getDouble(3));
                driversForEngineers.setEquipmentFuelCost(result.getDouble(4));
                driversForEngineers.setLubricantCost(result.getDouble(5));
                driversForEngineers.setNumberOfCD(result.getInt(6));
                driversForEngineers.setImplementationMode(result.getString(7));
                driversForEngineers.setMonth(result.getString(8));
                driversForEngineers.setYear(result.getInt(9));
                
                list.add(driversForEngineers);
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
    
    public void edit(DriversForEngineers driversForEngineers){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE drivers_for_engineers SET labor_equipment_cost = ?, "
                    + "equipment_fuel_cost = ?, "
                    + "lubricant_cost = ?, "
                    + "days_of_operation = ?, "
                    + "implementation_mode = ?, "
                    + "month = ?, "
                    + "year = ? "
                    + "WHERE deid = ?";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setDouble(1, driversForEngineers.getLaborEquipmentCost());
            preparedStatement.setDouble(2, driversForEngineers.getEquipmentFuelCost());
            preparedStatement.setDouble(3, driversForEngineers.getLubricantCost());
            preparedStatement.setInt(4, driversForEngineers.getNumberOfCD());
            preparedStatement.setString(5, driversForEngineers.getImplementationMode());
            preparedStatement.setString(6, driversForEngineers.getMonth());
            preparedStatement.setInt(7, driversForEngineers.getYear());
            preparedStatement.setString(6, driversForEngineers.getId());
            
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
