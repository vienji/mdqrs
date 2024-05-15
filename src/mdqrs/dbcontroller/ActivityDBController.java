/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.Activity;
import dbcontroller.Driver;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Vienji
 */
public class ActivityDBController {
    private String query = "";
    
    public void add(String itemNumber, String description, int workCategory){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO activity (item_number, description, work_category_number) VALUES (?, ?, ?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, itemNumber);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, workCategory);
            
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
    
    public ArrayList<Activity> getList(){
        ArrayList<Activity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM activity WHERE is_deleted = 'no'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Activity activity = new Activity();
                
                activity.setItemNumber(result.getString(1));
                activity.setDescription(result.getString(2));
                activity.setWorkCategory(new WorkCategoryDBController().getCategory(result.getInt(3)));
                
                list.add(activity);
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
    
    public Activity getActivity(String id){
        Activity activity = new Activity();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM activity WHERE item_number = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                
                activity.setItemNumber(result.getString(1));
                activity.setDescription(result.getString(2));
                activity.setWorkCategory(new WorkCategoryDBController().getCategory(result.getInt(3)));
                
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
        
        return activity;
    }
    
    public void edit(String itemNumber, String newDescription, int newWorkCategory){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE activity SET description = '" + newDescription + "', work_category_number = " + newWorkCategory + " WHERE item_number = '" + itemNumber + "'";
            
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
    
    public void delete(String itemNumber){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE activity SET is_deleted = 'yes' WHERE item_number = '" + itemNumber + "'";
            
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
    
    public boolean isPresent(String itemNumber){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        boolean present = false;
        
        try{
            query = "SELECT * FROM activity WHERE item_number = '" + itemNumber + "' AND is_deleted = 'no'";
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
    
    public boolean hasWorkActivities(String itemNumber, String month){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        boolean present = false;
        
        try{
            query = "SELECT activity_number FROM regular_activity WHERE activity_number = ? AND month = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, itemNumber);
            preparedStatement.setString(2, month);
            
            result = preparedStatement.executeQuery();
            present = result.next();
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
        
        return present;
    }
}
