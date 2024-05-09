/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;
import classes.Location;
import dbcontroller.Driver;
import java.util.*;
import java.sql.*;
/**
 *
 * @author Vienji
 */
public class LocationDBController {
    private String query = "";
    
    public ArrayList<Location> getList(){
        ArrayList<Location> list = new ArrayList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM location "
                    + "ORDER BY location";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Location location = new Location();
                
                location.setId(result.getString(2));
                location.setLocation(result.getString(3));
                
                list.add(location);
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
    
    public Location getLocation(String id){
        Location location = new Location();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM location WHERE lid = '" + id + "'";
                    
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                
                location.setId(result.getString(2));
                location.setLocation(result.getString(3));
                
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
        
        return location;
    }
}
