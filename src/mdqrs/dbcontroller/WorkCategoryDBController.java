/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.WorkCategory;
import java.util.*;
import java.sql.*;
import dbcontroller.Driver;

/**
 *
 * @author Vienji
 */
public class WorkCategoryDBController {
    private String query = "";
    
    public void add(int categoryNumber, String description){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO work_category (category_number, description) VALUES (?, ?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, categoryNumber);
            preparedStatement.setString(2, description);
            
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
    
    public ArrayList<WorkCategory> getList(){
        ArrayList<WorkCategory> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM work_category WHERE is_deleted = 'no'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                WorkCategory workCategory = new WorkCategory();
                
                workCategory.setWorkCategoryNumber(result.getInt(1));
                workCategory.setDescription(result.getString(2));
                
                list.add(workCategory);
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
    
    public WorkCategory getCategory(int categoryNumber){
        WorkCategory workCategory = new WorkCategory();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM work_category WHERE category_number = " + categoryNumber;
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){              
                workCategory.setWorkCategoryNumber(result.getInt(1));
                workCategory.setDescription(result.getString(2));
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
        
        return workCategory;
    }
    
    public void edit(String categoryNumber, String newDescription){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE work_category SET description = '" + newDescription + "' WHERE category_number = " + categoryNumber;
            
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
    
    public void delete(int categoryNumber){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE work_category SET is_deleted = 'yes' WHERE category_number = " + categoryNumber;
            
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
    
    public boolean isPresent(int categoryNumber){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        boolean present = false;
        
        try{
            query = "SELECT * FROM work_category WHERE category_number = " + categoryNumber + " AND is_deleted = 'no'";
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
