/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.Activity;
import classes.SubActivity;
import dbcontroller.Driver;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Vienji
 */
public class SubActivityDBController {
    private String query = "";
    
    public void add(String description, Activity activity){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO sub_activity (description, activity_number) VALUES (?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, activity.getItemNumber());
            
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
    
    public ArrayList<SubActivity> getList(String id){
        ArrayList<SubActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM sub_activity WHERE activity_number = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                SubActivity subActivity = new SubActivity();
                
                subActivity.setId(result.getInt(1));
                subActivity.setDescription(result.getString(2));
                subActivity.setActivity(new ActivityDBController().getActivity(result.getString(3)));
                
                list.add(subActivity);
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
    
    public ArrayList<SubActivity> getList(){
        ArrayList<SubActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM sub_activity";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                SubActivity subActivity = new SubActivity();
                
                subActivity.setId(result.getInt(1));
                subActivity.setDescription(result.getString(2));
                subActivity.setActivity(new ActivityDBController().getActivity(result.getString(3)));
                
                list.add(subActivity);
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
    
    public SubActivity getSubActivity(int id){
        SubActivity subActivity = new SubActivity();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM sub_activity WHERE id = " + id;
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){         
                subActivity.setId(result.getInt(1));
                subActivity.setDescription(result.getString(2));
                subActivity.setActivity(new ActivityDBController().getActivity(result.getString(3)));                
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
        
        return subActivity;
    }
    
    public void edit(SubActivity subActivity){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE sub_activity SET description = '" + 
                    subActivity.getDescription() + 
                    "', activity_number = '" + subActivity.getActivity().getItemNumber() + 
                    "' WHERE id = " + subActivity.getId();
            
            statement.execute(query);
        } catch (SQLException e){
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
    
    public boolean hasWorkActivities(int itemNumber, String month){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        boolean present = false;
        
        try{
            query = "SELECT oalid FROM other_activity WHERE sub_activity_id = ? AND month = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, itemNumber);
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
