/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.dbcontroller;

import classes.CrewEquipment;
import classes.CrewEquipmentList;
import classes.CrewPersonnel;
import classes.CrewPersonnelList;
import classes.OpsEquipment;
import classes.OpsEquipmentList;
import classes.OtherActivity;
import classes.RegularActivity;
import classes.SubActivity;
import java.util.*;
import java.sql.*;
import dbcontroller.Driver;
import mdqrs.classes.DriversForEngineers;
import mdqrs.classes.OtherExpenses;

/**
 *
 * @author Vienji
 */
public class GeneralDBController {
    private String query = "";
    
    // Labor Crew Cost
    public double getTotalRegularActivityLaborCrewCost(String crewPersonnelListID){
        double total = 0.0;
        CrewPersonnelList crewPersonnelList = new ActivityListDBController().getRegularActivityOpsCrewPersonnelList(crewPersonnelListID);
        
        for(CrewPersonnel crewPersonnel : crewPersonnelList.toList()){
            total += crewPersonnel.getTotalWages();
        }

        return total;
    }
    
    public double getTotalOtherActivityLaborCrewCost(String id){
        double total = 0.0;
        CrewPersonnelList list = new OtherActivityListDBController().getOtherActivityOpsCrewPersonnelList(id);
        
        for(CrewPersonnel crewPersonnel : list.toList()){
            total += crewPersonnel.getTotalWages();
        }
        return total;
    }
    
    // Labor Equipment Cost
    public double getTotalRegularActivityLaborEquipmentCost(String id){
        double total = 0.0;
        OpsEquipmentList list = new ActivityListDBController().getRegularActivityOpsEquipmentList(id);
        
        for(OpsEquipment opsEquipment : list.toList()){
            total += opsEquipment.getTotalWages();
        }
        return total;
    }
    
    // Equipment Fuel Cost
    public double getTotalRegularActivityEquipmentFuelCost(String crewPersonnelListID, String opsEquipmentListID){
        double total = 0.0;
        OpsEquipmentList opsEquipmentList = new ActivityListDBController().getRegularActivityOpsEquipmentList(opsEquipmentListID);
        CrewEquipmentList crewEquipmentList = new ActivityListDBController().getRegularActivityOpsCrewEquipmentList(crewPersonnelListID);
        
        for(OpsEquipment opsEquipment : opsEquipmentList.toList()){
            total += opsEquipment.getFuelAmount();
        }
        
        for(CrewEquipment crewEquipment : crewEquipmentList.toList()){
            total += crewEquipment.getFuelAmount();
        }

        return total;
    }
    
    // Lubricant Cost
    public double getTotalRegularActivityLubricantCost(String crewPersonnelListID, String opsEquipmentListID){
        double total = 0.0;
        OpsEquipmentList opsEquipmentList = new ActivityListDBController().getRegularActivityOpsEquipmentList(opsEquipmentListID);
        CrewEquipmentList crewEquipmentList = new ActivityListDBController().getRegularActivityOpsCrewEquipmentList(crewPersonnelListID);
        
        for(OpsEquipment opsEquipment : opsEquipmentList.toList()){
            total += opsEquipment.getLubricantAmount();
        }
        
        for(CrewEquipment crewEquipment : crewEquipmentList.toList()){
            total += crewEquipment.getLubricantAmount();
        }

        return total;
    }
    
    
    //Summary
    
    //Monthly
    public ArrayList<RegularActivity> getSpecifiedMonthlyRegularActivityList(String month, String activityID){
        ArrayList<RegularActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM regular_activity WHERE activity_number = ? AND month = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, activityID);
            preparedStatement.setString(2, month);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                RegularActivity regularActivity = new RegularActivity();
                
                boolean isOtherRoadSection = Boolean.valueOf(result.getString(5));
                
                regularActivity.setId(result.getString(2));
                regularActivity.setActivity(new ActivityDBController().getActivity(result.getString(3)));
                
                if(isOtherRoadSection){
                    regularActivity.setOtherRoadSection(result.getString(4));
                } else {
                    regularActivity.setRoadSection(new RoadSectionDBController().getRoadSection(result.getString(4)));
                }
                
                regularActivity.setIsOtherRoadSection(isOtherRoadSection);
                regularActivity.setLocation(new LocationDBController().getLocation(result.getString(6)));
                regularActivity.setNumberOfCD(result.getInt(7));
                regularActivity.setMonth(result.getString(8));
                regularActivity.setYear(result.getInt(9));
                regularActivity.setOpsEquipmentListID(result.getString(10));
                regularActivity.setOpsMaintenanceCrewID(result.getString(11));
                regularActivity.setImplementationMode(result.getString(12));
                
                SubActivity subActivity = result.getInt(13) != 0 ? 
                        new SubActivityDBController().getSubActivity(result.getInt(13)) : new SubActivity();
                regularActivity.setSubActivity(subActivity);
                
                list.add(regularActivity);
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
    
     public ArrayList<RegularActivity> getMonthlyRegularActivityList(String month){
        ArrayList<RegularActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM regular_activity WHERE month = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, month);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                RegularActivity regularActivity = new RegularActivity();
                
                regularActivity.setOpsEquipmentListID(result.getString(10));
                regularActivity.setOpsMaintenanceCrewID(result.getString(11));
                
                list.add(regularActivity);
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
     
     public ArrayList<OtherActivity> getSpecifiedMonthlyOtherActivityList(String month, int id){
        ArrayList<OtherActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM other_activity WHERE month = ? AND sub_activity_id = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, month);
            preparedStatement.setInt(2, id);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherActivity otherActivity = new OtherActivity();
                
                otherActivity.setId(result.getString(2));
                otherActivity.setDescription(result.getString(3));
                otherActivity.setSubActivity(new SubActivityDBController().getSubActivity(result.getInt(4)));
                otherActivity.setMonth(result.getString(5));
                otherActivity.setYear(result.getInt(6));
                otherActivity.setImplementationMode(result.getString(7));
                otherActivity.setNumberOfCD(result.getInt(8));
                
                list.add(otherActivity);
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
     
     public ArrayList<OtherActivity> getMonthlyOtherActivityList(String month){
        ArrayList<OtherActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT oalid FROM other_activity WHERE month = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, month);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherActivity otherActivity = new OtherActivity();
                
                otherActivity.setId(result.getString(1));
                
                list.add(otherActivity);
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
     
     public ArrayList<OtherExpenses> getMonthlyOtherExpensesList(String month){
        ArrayList<OtherExpenses> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT oexid, labor_crew_cost, labor_equipment_cost, light_equipments, heavy_equipments FROM other_expenses WHERE month = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, month);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherExpenses otherExpenses = new OtherExpenses();
                
                otherExpenses.setId(result.getString(1));
                otherExpenses.setLaborCrewCost(result.getDouble(2));
                otherExpenses.setLaborEquipmentCost(result.getDouble(3));
                otherExpenses.setLightEquipments(result.getDouble(4));
                otherExpenses.setHeavyEquipments(result.getDouble(5));
                
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
     
     public ArrayList<DriversForEngineers> getMonthlyDriversForEngineersList(String month){
        ArrayList<DriversForEngineers> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT deid, labor_equipment_cost, equipment_fuel_cost, lubricant_cost FROM drivers_for_engineers WHERE month = ? AND year = YEAR(CURRENT_DATE())";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, month);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                DriversForEngineers driversForEngineers = new DriversForEngineers();
                
                driversForEngineers.setId(result.getString(1));
                driversForEngineers.setLaborEquipmentCost(result.getDouble(2));
                driversForEngineers.setEquipmentFuelCost(result.getDouble(3));
                driversForEngineers.setLubricantCost(result.getDouble(4));
                
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
     
     //Quarterly
     public ArrayList<RegularActivity> getQuarterlyRegularActivityList(String quarter){
        ArrayList<RegularActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM regular_activity "
                    + "WHERE "
                    + "     year = YEAR(CURRENT_DATE()) "
                    + "     AND "
                    + "     CASE "
                    + "         WHEN month IN ('January', 'February', 'March') THEN '1st Quarter' "
                    + "         WHEN month IN ('April', 'May', 'June') THEN '2nd Quarter' "
                    + "         WHEN month IN ('July', 'August', 'September') THEN '3rd Quarter' "
                    + "         WHEN month IN ('October', 'November', 'December') THEN '4th Quarter' "
                    + "END = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, quarter);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                RegularActivity regularActivity = new RegularActivity();
                
                regularActivity.setOpsEquipmentListID(result.getString(10));
                regularActivity.setOpsMaintenanceCrewID(result.getString(11));
                
                list.add(regularActivity);
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
     
     public ArrayList<OtherActivity> getQuarterlyOtherActivityList(String quarter){
        ArrayList<OtherActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT oalid FROM other_activity "
                    + "WHERE "
                    + "     year = YEAR(CURRENT_DATE()) "
                    + "     AND "
                    + "     CASE "
                    + "         WHEN month IN ('January', 'February', 'March') THEN '1st Quarter' "
                    + "         WHEN month IN ('April', 'May', 'June') THEN '2nd Quarter' "
                    + "         WHEN month IN ('July', 'August', 'September') THEN '3rd Quarter' "
                    + "         WHEN month IN ('October', 'November', 'December') THEN '4th Quarter' "
                    + "END = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, quarter);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherActivity otherActivity = new OtherActivity();
                
                otherActivity.setId(result.getString(1));
                
                list.add(otherActivity);
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
     
     public ArrayList<OtherExpenses> getQuarterlyOtherExpensesList(String quarterly){
        ArrayList<OtherExpenses> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT oexid, labor_crew_cost, labor_equipment_cost, light_equipments, heavy_equipments FROM other_expenses "
                    + "WHERE "
                    + "     year = YEAR(CURRENT_DATE()) "
                    + "     AND "
                    + "     CASE "
                    + "         WHEN month IN ('January', 'February', 'March') THEN '1st Quarter' "
                    + "         WHEN month IN ('April', 'May', 'June') THEN '2nd Quarter' "
                    + "         WHEN month IN ('July', 'August', 'September') THEN '3rd Quarter' "
                    + "         WHEN month IN ('October', 'November', 'December') THEN '4th Quarter' "
                    + "END = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, quarterly);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherExpenses otherExpenses = new OtherExpenses();
                
                otherExpenses.setId(result.getString(1));
                otherExpenses.setLaborCrewCost(result.getDouble(2));
                otherExpenses.setLaborEquipmentCost(result.getDouble(3));
                otherExpenses.setLightEquipments(result.getDouble(4));
                otherExpenses.setHeavyEquipments(result.getDouble(5));
                
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
     
     public ArrayList<DriversForEngineers> getQuarterlyDriversForEngineersList(String quarterly){
        ArrayList<DriversForEngineers> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT deid, labor_equipment_cost, equipment_fuel_cost, lubricant_cost FROM drivers_for_engineers "
                    + "WHERE "
                    + "     year = YEAR(CURRENT_DATE()) "
                    + "     AND "
                    + "     CASE "
                    + "         WHEN month IN ('January', 'February', 'March') THEN '1st Quarter' "
                    + "         WHEN month IN ('April', 'May', 'June') THEN '2nd Quarter' "
                    + "         WHEN month IN ('July', 'August', 'September') THEN '3rd Quarter' "
                    + "         WHEN month IN ('October', 'November', 'December') THEN '4th Quarter' "
                    + "END = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setString(1, quarterly);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                DriversForEngineers driversForEngineers = new DriversForEngineers();
                
                driversForEngineers.setId(result.getString(1));
                driversForEngineers.setLaborEquipmentCost(result.getDouble(2));
                driversForEngineers.setEquipmentFuelCost(result.getDouble(3));
                driversForEngineers.setLubricantCost(result.getDouble(4));
                
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
     
     //Annual
     public ArrayList<RegularActivity> getAnnualRegularActivityList(int year){
        ArrayList<RegularActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT * FROM regular_activity WHERE year = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setInt(1, year);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                RegularActivity regularActivity = new RegularActivity();
                
                regularActivity.setOpsEquipmentListID(result.getString(10));
                regularActivity.setOpsMaintenanceCrewID(result.getString(11));
                
                list.add(regularActivity);
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
     
     public ArrayList<OtherActivity> getAnnualOtherActivityList(int year){
        ArrayList<OtherActivity> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT oalid FROM other_activity WHERE year = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setInt(1, year);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherActivity otherActivity = new OtherActivity();
                
                otherActivity.setId(result.getString(1));
                
                list.add(otherActivity);
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
     
     public ArrayList<OtherExpenses> getAnnualOtherExpensesList(int year){
        ArrayList<OtherExpenses> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT oexid, labor_crew_cost, labor_equipment_cost, light_equipments, heavy_equipments FROM other_expenses WHERE year = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setInt(1, year);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                OtherExpenses otherExpenses = new OtherExpenses();
                
                otherExpenses.setId(result.getString(1));
                otherExpenses.setLaborCrewCost(result.getDouble(2));
                otherExpenses.setLaborEquipmentCost(result.getDouble(3));
                otherExpenses.setLightEquipments(result.getDouble(4));
                otherExpenses.setHeavyEquipments(result.getDouble(5));
                
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
     
     public ArrayList<DriversForEngineers> getAnnualDriversForEngineersList(int year){
        ArrayList<DriversForEngineers> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            query = "SELECT deid, labor_equipment_cost, equipment_fuel_cost, lubricant_cost FROM drivers_for_engineers WHERE year = ?";
            connection = Driver.getConnection();
            preparedStatement = connection.prepareStatement(query);
        
            preparedStatement.setInt(1, year);
            
            result = preparedStatement.executeQuery();
            while(result.next()){
                DriversForEngineers driversForEngineers = new DriversForEngineers();
                
                driversForEngineers.setId(result.getString(1));
                driversForEngineers.setLaborEquipmentCost(result.getDouble(2));
                driversForEngineers.setEquipmentFuelCost(result.getDouble(3));
                driversForEngineers.setLubricantCost(result.getDouble(4));
                
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
     
     //Getting the total expenses
     public double getTotalLaborCrewCost(ArrayList<RegularActivity> regularList, 
             ArrayList<OtherActivity> otherList, ArrayList<OtherExpenses> otherExpensesList){
         
         double total = 0.0;
         
         for(RegularActivity e : regularList){
             total += getTotalRegularActivityLaborCrewCost(e.getOpsMaintenanceCrewID());
         }
         
         for(OtherActivity e : otherList){
             total += getTotalOtherActivityLaborCrewCost(e.getId());
         }

         for(OtherExpenses e : otherExpensesList){
             total += e.getLaborCrewCost();
         }
         
         return total;
     }
     
     public double getTotalLaborEquipmentCost(ArrayList<RegularActivity> regularList, 
             ArrayList<OtherExpenses> otherExpensesList, ArrayList<DriversForEngineers> driversForEngineersList){
         double total = 0.0;
         
         for(RegularActivity e : regularList){
             total += getTotalRegularActivityLaborEquipmentCost(e.getOpsEquipmentListID());
         }
         
         for(OtherExpenses e : otherExpensesList){
             total += e.getLaborEquipmentCost();
         }
         
         for(DriversForEngineers e : driversForEngineersList){
             total += e.getLaborEquipmentCost();
         }
         
         return total;
     }
     
     public double getTotalEquipmentFuelCost(ArrayList<RegularActivity> regularList,
             ArrayList<DriversForEngineers> driversForEngineersList){
         double total = 0.0;
         
         for(RegularActivity e : regularList){
             total += getTotalRegularActivityEquipmentFuelCost(
                     e.getOpsMaintenanceCrewID(), 
                     e.getOpsEquipmentListID());
         }
         
         for(DriversForEngineers e : driversForEngineersList){
             total += e.getEquipmentFuelCost();
         }
         
         return total;
     }
     
     public double getTotalLubricantCost(ArrayList<RegularActivity> regularList,
             ArrayList<DriversForEngineers> driversForEngineersList){
         double total = 0.0;
         
         for(RegularActivity e : regularList){
             total += getTotalRegularActivityLubricantCost(
                     e.getOpsMaintenanceCrewID(),
                     e.getOpsEquipmentListID());
         }
         
         for(DriversForEngineers e : driversForEngineersList){
             total += e.getLubricantCost();
         }
         
         return total;
     }
}
