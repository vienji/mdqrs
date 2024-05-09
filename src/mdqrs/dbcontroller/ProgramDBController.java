/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import java.util.*;
import java.sql.*;
import mdqrs.classes.Program;
import mdqrs.classes.Project;
import dbcontroller.Driver;

/**
 *
 * @author Vienji
 */
public class ProgramDBController {
    private String query = "";
    
    public void add(Program program, ArrayList<Project> projectList){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            String programID = "";
            query = "INSERT INTO program (source_of_fund, month, year) VALUES (?,?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, program.getSourceOfFund());
            preparedStatement.setString(2, program.getMonth());
            preparedStatement.setInt(3, program.getYear());
            
            preparedStatement.executeUpdate();
            
            query = "UPDATE program SET prid = CONCAT('PR', YEAR(CURDATE()), MONTH(CURDATE()), LPAD(LAST_INSERT_ID(), 3, '0')) WHERE id = last_insert_id()";
                        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
            query = "SELECT prid FROM program WHERE id = last_insert_id()";

            preparedStatement = connection.prepareStatement(query);

            result = preparedStatement.executeQuery();
            while(result.next()){
                programID = result.getString(1);
            }
            
            for(Project project : projectList){
                addProject(project, programID);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
    }
    
    public void addProject(Project project, String programID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "INSERT INTO project (description, project_cost, implementation_mode, source_of_fund_id) VALUES (?,?,?,?)";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, project.getDescription());
            preparedStatement.setDouble(2, project.getProjectCost());
            preparedStatement.setString(3, project.getImplementationMode());
            preparedStatement.setString(4, programID);
            
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
    
    public void updateProject(Project project){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE project SET description = ?, project_cost = ?, implementation_mode = ? WHERE id = ? AND source_of_fund_id = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, project.getDescription());
            preparedStatement.setDouble(2, project.getProjectCost());
            preparedStatement.setString(3, project.getImplementationMode());
            preparedStatement.setInt(4, project.getId());
            preparedStatement.setString(5, project.getSourceOfFundID());
            
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
    
    public void deleteProject(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "DELETE FROM project WHERE id = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, id);
            
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
    
    public ArrayList<Program> getList(){
        ArrayList<Program> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM program";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Program program = new Program();
                
                program.setId(result.getString(2));
                program.setSourceOfFund(result.getString(3));
                program.setMonth(result.getString(4));
                program.setYear(result.getInt(5));
                program.setTotalProjectCost(getTotalProjectCost(program.getId()));
                
                list.add(program);              
            }           
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
        
        return list;
    }
    
    public ArrayList<Program> getList(String month, int year){
        ArrayList<Program> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM program WHERE month = ? AND year = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, month);
            preparedStatement.setInt(2, year);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Program program = new Program();
                
                program.setId(result.getString(2));
                program.setSourceOfFund(result.getString(3));
                program.setMonth(result.getString(4));
                program.setYear(result.getInt(5));
                program.setTotalProjectCost(getTotalProjectCost(program.getId()));
                
                list.add(program);              
            }           
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
        
        return list;
    }
    
    public ArrayList<Project> getProjectList(String programID){
        ArrayList<Project> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT * FROM project WHERE source_of_fund_id = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, programID);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                Project project = new Project();
                
                project.setId(result.getInt(1));
                project.setDescription(result.getString(2));
                project.setProjectCost(result.getDouble(3));
                project.setImplementationMode(result.getString(4));
                project.setSourceOfFundID(result.getString(5));
                
                list.add(project);
            }           
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
        
        return list;
    }
    
    public double getTotalProjectCost(String programID){
        double total = 0.0;
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try{
            query = "SELECT project_cost FROM project WHERE source_of_fund_id = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, programID);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                total += result.getDouble(1);
            }           
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try{connection.close();}catch(SQLException e){}
            }
            if(result != null){
                try{result.close();}catch(SQLException e){}
            }
            if(preparedStatement != null){
                try{preparedStatement.close();}catch(SQLException e){}
            }
        }
        
        return total;
    }
    
    public void edit(Program program, ArrayList<Project> projectList){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            query = "UPDATE program SET source_of_fund = ?, "
                    + "month = ?, year = ? "
                    + "WHERE prid = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, program.getSourceOfFund());
            preparedStatement.setString(2, program.getMonth());
            preparedStatement.setInt(3, program.getYear());
            preparedStatement.setString(4, program.getId());
            
            preparedStatement.executeUpdate();
            
            ArrayList<Project> projectCollection = getProjectList(program.getId());
            
            if(projectCollection.isEmpty()){
                for(Project project : projectList){
                    addProject(project, program.getId());
                }
            } else {
                if(!projectList.isEmpty()){
                    for(int i = 0; i < projectCollection.size(); i++){
                        Project project = projectCollection.get(i);
                        if(!contains(projectList, project)){
                            deleteProject(project.getId());
                        }
                    }

                    for(int i = 0; i < projectList.size(); i++){
                        Project project = projectList.get(i);

                        if(project.getId() != -1){
                            updateProject(project);
                        } else {
                            addProject(project, program.getId());
                        }
                    }
                } else {
                    for(int i = 0; i < projectCollection.size(); i++){
                        Project project = projectCollection.get(i);
                        deleteProject(project.getId());
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
    
    public boolean contains(ArrayList<Project> projectList, Project project){
        for(Project projectData : projectList){
            if(project.getId() == projectData.getId()){
                return true;
            }
        }
        return false;
    }
}
