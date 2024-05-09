/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.Location;
import classes.RoadSection;
import dbcontroller.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Vienji
 */
public class RoadSectionDBController {
    private String query = "";
    
    public void add(String roadSection, String location){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO road_section (name, location) VALUES (?, ?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, roadSection);
            preparedStatement.setString(2, location);
            
            preparedStatement.executeUpdate();     
            
            query = "UPDATE road_section SET rid = CONCAT('R', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
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
    
    public ArrayList<RoadSection> getList(String location){
        ArrayList<RoadSection> list = new ArrayList();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM road_section "
                    + "WHERE location = '" + location + "' "
                    + "ORDER BY name";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                RoadSection roadSection = new RoadSection();
                
                roadSection.setId(result.getString(2));
                roadSection.setName(result.getString(3));
                roadSection.setLocationId(result.getString(4));
                
                list.add(roadSection);
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
    
    public RoadSection getRoadSection(String id){
        RoadSection roadSection = new RoadSection();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM road_section "
                    + "WHERE rid = '" + id + "' ";
                    
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                
                roadSection.setId(result.getString(2));
                roadSection.setName(result.getString(3));
                roadSection.setLocationId(result.getString(4));
                
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
        
        return roadSection;
    }
}
