/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import dbcontroller.Driver;
import java.sql.*;
import java.util.*;
import classes.Personnel;
import mdqrs.classes.JobType;
/**
 *
 * @author Vienji
 */
public class PersonnelDBController {
    private String query = "";
    
    public void addJobType(String type, Double ratePerDay){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO job_type (type, rate_per_day) VALUES (?, ?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, type);
            preparedStatement.setDouble(2, ratePerDay);
            
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
    
    public void add(String name, String type, boolean isOtherType, Double ratePerDay){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO personnel (name, type, rate_per_day, is_other_type) VALUES (?, ?, ?, ?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setDouble(3, ratePerDay);
            preparedStatement.setString(4, String.valueOf(isOtherType));
            
            preparedStatement.executeUpdate();   
            
            query = "UPDATE personnel SET pid = CONCAT('P', YEAR(CURDATE()), "
                    + "MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = LAST_INSERT_ID()";
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
    
    public ArrayList<JobType> getJobTypes(){
        ArrayList<JobType> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM job_type";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                JobType jobType = new JobType();
                
                jobType.setId(result.getInt(1));
                jobType.setType(result.getString(2));
                jobType.setRatePerDay(result.getDouble(3));
                
                list.add(jobType);
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
    
    public JobType getJobType(int id){
        JobType jobType = new JobType();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM job_type WHERE id = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, id);
            
            result = preparedStatement.executeQuery();
            while(result.next()){             
                jobType.setId(result.getInt(1));
                jobType.setType(result.getString(2));
                jobType.setRatePerDay(result.getDouble(3));
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
        
        return jobType;
    }
    
    public ArrayList<Personnel> getList(){
        ArrayList<Personnel> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM personnel WHERE is_deleted = 'no'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                boolean isOtherType = Boolean.valueOf(result.getString(7));
                Personnel personnel = new Personnel();
                personnel.setId(result.getString(2));
                personnel.setName(result.getString(3));
                
                if(isOtherType){
                    personnel.setType(result.getString(4));
                    personnel.setRatePerDay(result.getDouble(5));
                } else {
                    JobType jobType = getJobType(Integer.parseInt(result.getString(4)));
                    personnel.setType(jobType.getType());
                    personnel.setRatePerDay(jobType.getRatePerDay());
                }
                
                personnel.setIsOtherType(String.valueOf(isOtherType));
                
                list.add(personnel);
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
    
    public ArrayList<Personnel> getList(String value){
        ArrayList<Personnel> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM personnel WHERE pid = ? OR name LIKE ? OR type LIKE ? AND is_deleted = 'no'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, "%" + value + "%");
            preparedStatement.setString(3, "%" + value + "%");
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Personnel personnel = new Personnel();
                
                personnel.setId(result.getString(2));
                personnel.setName(result.getString(3));
                personnel.setType(result.getString(4));
                personnel.setRatePerDay(result.getDouble(5));
                
                list.add(personnel);
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
    
    public Personnel getPersonnel(String id){
        Personnel personnel = new Personnel();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM personnel WHERE pid = '" + id + "'";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                             
                personnel.setId(result.getString(2));
                personnel.setName(result.getString(3));
                personnel.setType(result.getString(4));
                personnel.setRatePerDay(result.getDouble(5));
                
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
        
        return personnel;
    }
    
    public void edit(String pid, String newName, String newType, boolean isOtherType, Double newRatePerDay){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE personnel SET name = '" + newName + "', type = '"+ newType +"', rate_per_day = "+ newRatePerDay +", is_other_type = '" + String.valueOf(isOtherType) + "' WHERE pid = '" + pid + "'";
            
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
    
    public void delete(String pid){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = Driver.getConnection();
            statement = connection.createStatement();
            query = "UPDATE personnel SET is_deleted = 'yes' WHERE pid = '" + pid + "'";
            
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
    
    public boolean isPresent(String name, String type){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        boolean present = false;
        
        try{
            query = "SELECT * FROM personnel WHERE name = '" + name + "' AND type = '" + type + "'";
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
    
    public void updateRatePerDay(ArrayList<JobType> jobTypes){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = Driver.getConnection();
            query = "UPDATE job_type SET rate_per_day = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            
            for(int i = 0; i < jobTypes.size(); i++){
                JobType jobType = jobTypes.get(i);
                preparedStatement.setDouble(1, jobType.getRatePerDay());
                preparedStatement.setInt(2, jobType.getId());
                
                preparedStatement.executeUpdate();
            }
        } catch(SQLException e){
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
