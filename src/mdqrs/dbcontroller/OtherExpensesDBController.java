/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import mdqrs.classes.OtherExpenses;
import java.util.*;
import java.sql.*;
import dbcontroller.Driver;

/**
 *
 * @author Vienji
 */
public class OtherExpensesDBController {
    private String query = "";
    
    public void add(OtherExpenses otherExpenses){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO other_expenses (labor_crew_cost, labor_equipment_cost, light_equipments, heavy_equipments, days_of_operation, implementation_mode, month, year) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setDouble(1, otherExpenses.getLaborCrewCost());
            preparedStatement.setDouble(2, otherExpenses.getLaborEquipmentCost());
            preparedStatement.setDouble(3, otherExpenses.getLightEquipments());
            preparedStatement.setDouble(4, otherExpenses.getHeavyEquipments());
            preparedStatement.setInt(5, otherExpenses.getNumberOfCD());
            preparedStatement.setString(6, otherExpenses.getImplementationMode());
            preparedStatement.setString(7, otherExpenses.getMonth());
            preparedStatement.setInt(8, otherExpenses.getYear());
            
            preparedStatement.executeUpdate();
            
            query = "UPDATE other_expenses SET oexid = CONCAT('OEX', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
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
    
    public ArrayList<OtherExpenses> getList(){
        ArrayList<OtherExpenses> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM other_expenses";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherExpenses otherExpenses = new OtherExpenses();
                
                otherExpenses.setId(result.getString(2));
                otherExpenses.setLaborCrewCost(result.getDouble(3));
                otherExpenses.setLaborEquipmentCost(result.getDouble(4));
                otherExpenses.setLightEquipments(result.getDouble(5));
                otherExpenses.setHeavyEquipments(result.getDouble(6));
                otherExpenses.setNumberOfCD(result.getInt(7));
                otherExpenses.setImplementationMode(result.getString(8));
                otherExpenses.setMonth(result.getString(9));
                otherExpenses.setYear(result.getInt(10));
            
                list.add(otherExpenses);
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
    
    public OtherExpenses getOtherExpenses(String month){
        OtherExpenses otherExpenses = new OtherExpenses();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM other_expenses WHERE month = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, month);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                
                otherExpenses.setId(result.getString(2));
                otherExpenses.setLaborCrewCost(result.getDouble(3));
                otherExpenses.setLaborEquipmentCost(result.getDouble(4));
                otherExpenses.setLightEquipments(result.getDouble(5));
                otherExpenses.setHeavyEquipments(result.getDouble(6));
                otherExpenses.setNumberOfCD(result.getInt(7));
                otherExpenses.setImplementationMode(result.getString(8));
                otherExpenses.setMonth(result.getString(9));
                otherExpenses.setYear(result.getInt(10));
                
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
        
        return otherExpenses;
    }
    
    public void edit(OtherExpenses otherExpenses){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            query = "UPDATE other_expenses SET labor_crew_cost = ?, "
                    + "labor_equipment_cost = ?, "
                    + "light_equipments = ?, "
                    + "heavy_equipments = ?, "
                    + "days_of_operation = ?, "
                    + "implementation_mode = ?, "
                    + "month = ?, "
                    + "year = ? "
                    + "WHERE oexid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setDouble(1, otherExpenses.getLaborCrewCost());
            preparedStatement.setDouble(2, otherExpenses.getLaborEquipmentCost());
            preparedStatement.setDouble(3, otherExpenses.getLightEquipments());
            preparedStatement.setDouble(4, otherExpenses.getHeavyEquipments());
            preparedStatement.setInt(5, otherExpenses.getNumberOfCD());
            preparedStatement.setString(6, otherExpenses.getImplementationMode());
            preparedStatement.setString(7, otherExpenses.getMonth());
            preparedStatement.setInt(8, otherExpenses.getYear());
            preparedStatement.setString(9, otherExpenses.getId());
            
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
