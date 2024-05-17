/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.reports;

import classes.Activity;
import classes.RegularActivity;
import classes.SubActivity;
import classes.OtherActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import mdqrs.classes.DriversForEngineers;
import mdqrs.classes.OtherExpenses;
import mdqrs.classes.Program;
import mdqrs.classes.Project;
import mdqrs.dbcontroller.ActivityDBController;
import mdqrs.dbcontroller.DriversForEngineersDBController;
import mdqrs.dbcontroller.GeneralDBController;
import mdqrs.dbcontroller.OtherExpensesDBController;
import mdqrs.dbcontroller.ProgramDBController;
import mdqrs.dbcontroller.SubActivityDBController;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFHeaderFooterProperties;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Vienji
 */
public class MonthlyReport implements Report {
    private static final String xlsx = "xlsx";
    private File file;
    private String headerTitle;
    private String timeFrame;
    private String filePath;
    private int year;
    private ArrayList<String> organizationHeads;
    
    @Override
    public void setFilePath(String filePath, File file){
        this.filePath = filePath;
        this.file = file;
    }
    
    @Override
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    @Override
    public void setTimeFrameDetail(Object timeFrame, int year) {
        this.timeFrame = String.valueOf(timeFrame);
        this.year = year;
    }

    @Override
    public void setOrganizationHeads(ArrayList<String> organizationHeads) {
        this.organizationHeads = organizationHeads;
    }

    @Override
    public void generateReport() {
        Workbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet();
        
        //Header
        Header header = sheet.getHeader();
        header.setCenter(headerTitle + "\nFY " + year + " Road Maintenance Program");
        header.setLeft(HSSFHeader.fontSize((short) 9) + "\n\n\nRegion: Region XII\nProvince: South Cotabato");
        
        XSSFHeaderFooterProperties prop = sheet.getHeaderFooterProperties();
        prop.setAlignWithMargins(true);
        
        //Cell Styles
        CellStyle columnHeaderStyle = createColumnHeaderStyle(workbook);
        CellStyle right = createRightStyle(workbook);
        CellStyle timeFrameStyle = createTimeFrameStyle(workbook);
        CellStyle activityHeaderStyle = createActivityHeaderStyle(workbook);
        CellStyle activityTitleStyle = createActivityTitleStyle(workbook);
        CellStyle numberStyle = createNumberStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);
        CellStyle currencySubTotalStyle = createSubTotalCurrencyStyle(workbook);
        CellStyle currencyTotalStyle = createTotalCurrencyStyle(workbook);
        CellStyle centerSubTotal = createCenteredSubTotalStyle(workbook);
        CellStyle centerTotal = createCenteredTotalStyle(workbook);
        CellStyle center = createCenteredStyle(workbook);
        CellStyle left = createLeftStyle(workbook);
        CellStyle left10 = createLeft10Style(workbook);
        CellStyle subTotalStyle = createSubTotalStyle(workbook);
        CellStyle totalStyle = createTotalStyle(workbook);
        CellStyle centeredCurrencyStyle = createCenteredCurrencyStyle(workbook);
        CellStyle sidelessSubTotalCurrencyStyle = createSidelessSubTotalCurrencyStyle(workbook);
        CellStyle sidelessTotalCurrencyStyle = createSidelessTotalCurrencyStyle(workbook);
        
        //Print Setup
        PrintSetup printSetup =  sheet.getPrintSetup();
        printSetup.setLandscape(true);
        
        //Paper Layout
        sheet.getPrintSetup().setPaperSize(PrintSetup.LEGAL_PAPERSIZE);
        sheet.setMargin(Sheet.LeftMargin, 0.3); 
        sheet.setMargin(Sheet.RightMargin, 0.3); 
        sheet.setMargin(Sheet.TopMargin, 1.2); 
        sheet.setMargin(Sheet.BottomMargin, 0.5); 
        
        //Merged Regions
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

        //Column Width
        sheet.setColumnWidth(0, 0);
        sheet.setColumnWidth(1, 800);
        sheet.setColumnWidth(2, 5200);
        sheet.setColumnWidth(3, 5200);
        sheet.setColumnWidth(4, 2000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 2500);
        sheet.setColumnWidth(8, 2200);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 2000);
        sheet.setColumnWidth(11, 3000);
        sheet.setColumnWidth(12, 3300);
        sheet.setColumnWidth(13, 3300);
        sheet.setColumnWidth(14, 3000);
        sheet.setColumnWidth(15, 3000);
        
        //Column Header
        Row columnHeader = sheet.createRow(0);
        
        String[] headerTitle = {
            "ROAD MAINTENANCE", 
            "", 
            "ACTIVITIES",
            "LENGTH (Km)",
            "LABOR-CREW COST",
            "LABOR-EQUIPMENT COST",
            "EQUIPMENT FUEL COST",
            "LUBRICANT COST",
            "MATERIAL COST",
            "OTHER EXPENSES",
            "ESTIMATED COST",
            "PHYSICAL ACCOMPLISHMENT",
            "FINANCIAL ACCOMPLISHMENT",
            "ACTUAL COST",
            "MODE OF IMPLEMENTATION"};
        
        for(int i = 0; i < headerTitle.length; i++){
            Cell cell = columnHeader.createCell(i + 1);
            cell.setCellValue(headerTitle[i]);
            cell.setCellStyle(columnHeaderStyle);   
        }
        
        //Month
        Row month = sheet.createRow(1);
        Cell monthCell = month.createCell(1);
        monthCell.setCellValue(timeFrame);
        monthCell.setCellStyle(timeFrameStyle);
        
        for(int i = 2; i <= headerTitle.length; i++){
            Cell cell = month.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(timeFrameStyle);
        }
        
        int startingRow = 2;
        
        //Regular Activities
        ActivityDBController activityDBController = new ActivityDBController();
        GeneralDBController gdbc = new GeneralDBController();
        ArrayList<Activity> activityList = activityDBController.getList();
        
        Double totalRALaborCrewExpenses = 0.0, totalRALaborEquipmentExpenses = 0.00, totalRAEquipmentFuelExpenses = 0.00, totalRALubricantExpenses = 0.00, grandRASubTotal = 0.00;
        
        for(Activity activity : activityList){
            if( activityDBController.hasWorkActivities(activity.getItemNumber(), timeFrame) && !activity.getItemNumber().equals("504")){
                Double totalLaborCrewExpenses = 0.00, totalLaborEquipmentExpenses = 0.00, totalEquipmentFuelExpenses = 0.00, totalLubricantExpenses = 0.00, grandSubTotal = 0.00;
                
                ArrayList<RegularActivity> list = gdbc.getSpecifiedMonthlyRegularActivityList(timeFrame, activity.getItemNumber(), year);
                sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
                Row activityTitle = sheet.createRow(startingRow++);
                Cell activityTitleCell = activityTitle.createCell(1);
                activityTitleCell.setCellValue("Activity #" + activity.getItemNumber() + " - " + activity.getDescription());
                activityTitleCell.setCellStyle(activityHeaderStyle);
                
                for(int i = 2; i <= headerTitle.length; i++){
                    Cell cell = activityTitle.createCell(i);
                    cell.setCellValue("");
                    cell.setCellStyle(activityHeaderStyle);
                }
                
                //Work items
                for(int i = 0; i < list.size(); i++){
                    RegularActivity regularActivity = list.get(i);
                    
                    Double laborCrewExpense = gdbc.getTotalRegularActivityLaborCrewCost(regularActivity.getOpsMaintenanceCrewID()),
                           laborEquipmentExpense = gdbc.getTotalRegularActivityLaborEquipmentCost(regularActivity.getOpsEquipmentListID()),
                           equipmentFuelExpense = gdbc.getTotalRegularActivityEquipmentFuelCost(regularActivity.getOpsMaintenanceCrewID(), regularActivity.getOpsEquipmentListID()),
                            lubricantExpense = gdbc.getTotalRegularActivityLubricantCost(regularActivity.getOpsMaintenanceCrewID(), regularActivity.getOpsEquipmentListID());
                    
                    Double total = laborCrewExpense + laborEquipmentExpense + equipmentFuelExpense + lubricantExpense;
                    
                    totalLaborCrewExpenses += laborCrewExpense;
                    totalLaborEquipmentExpenses += laborEquipmentExpense;
                    totalEquipmentFuelExpenses += equipmentFuelExpense;
                    totalLubricantExpenses += lubricantExpense;
                    grandSubTotal += total;
                                      
                    String roadSection = regularActivity.isIsOtherRoadSection() ? regularActivity.getOtherRoadSection() : regularActivity.getRoadSection().getName();
                    addItemRow(i + 1, startingRow++, sheet, new CellStyle[]{numberStyle, activityTitleStyle, activityTitleStyle, 
                            numberStyle, currencyStyle, currencyStyle, currencyStyle, 
                            currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
                            center, currencyStyle, currencyStyle, center}, roadSection, regularActivity.getImplementationMode(),
                            laborCrewExpense, laborEquipmentExpense, equipmentFuelExpense, lubricantExpense, 
                            total);  
                }
                
                //Sub Total
                addSubtotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                centerSubTotal, currencySubTotalStyle, currencySubTotalStyle, centerSubTotal}, 
                    totalLaborCrewExpenses, totalLaborEquipmentExpenses, 
                    totalEquipmentFuelExpenses, totalLubricantExpenses, grandSubTotal);
                
                totalRALaborCrewExpenses += totalLaborCrewExpenses;
                totalRALaborEquipmentExpenses += totalLaborEquipmentExpenses;
                totalRAEquipmentFuelExpenses += totalEquipmentFuelExpenses;
                totalRALubricantExpenses += totalLubricantExpenses;
                grandRASubTotal += grandSubTotal;
            }
        }
        
        //504
        Double total504LaborCrewExpenses = 0.0, total504LaborEquipmentExpenses = 0.00, total504EquipmentFuelExpenses = 0.00, total504LubricantExpenses = 0.00, grand504SubTotal = 0.00;
        for(Activity activity : activityList){
            if(activityDBController.hasWorkActivities("504", timeFrame) && activity.getItemNumber().equals("504")){
                Double totalLaborCrewExpenses = 0.00, totalLaborEquipmentExpenses = 0.00, totalEquipmentFuelExpenses = 0.00, totalLubricantExpenses = 0.00, grandSubTotal = 0.00;
                
                ArrayList<RegularActivity> list = gdbc.getSpecifiedMonthlyRegularActivityList(timeFrame, activity.getItemNumber(), year);
                sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
                Row activityTitle = sheet.createRow(startingRow++);
                Cell activityTitleCell = activityTitle.createCell(1);
                activityTitleCell.setCellValue("Activity #" + activity.getItemNumber() + " - " + activity.getDescription() + " (Work for Other Agencies or Individuals)");
                activityTitleCell.setCellStyle(activityHeaderStyle);
                
                for(int i = 2; i <= headerTitle.length; i++){
                    Cell cell = activityTitle.createCell(i);
                    cell.setCellValue("");
                    cell.setCellStyle(activityHeaderStyle);
                }
                
                //Work items
                for(int i = 0; i < list.size(); i++){
                    RegularActivity regularActivity = list.get(i);
                    
                    if(regularActivity.getSubActivity().getId() == 2){

                        Double laborCrewExpense = gdbc.getTotalRegularActivityLaborCrewCost(regularActivity.getOpsMaintenanceCrewID()),
                               laborEquipmentExpense = gdbc.getTotalRegularActivityLaborEquipmentCost(regularActivity.getOpsEquipmentListID()),
                               equipmentFuelExpense = gdbc.getTotalRegularActivityEquipmentFuelCost(regularActivity.getOpsMaintenanceCrewID(), regularActivity.getOpsEquipmentListID()),
                                lubricantExpense = gdbc.getTotalRegularActivityLubricantCost(regularActivity.getOpsMaintenanceCrewID(), regularActivity.getOpsEquipmentListID());

                        Double total = laborCrewExpense + laborEquipmentExpense + equipmentFuelExpense + lubricantExpense;

                        totalLaborCrewExpenses += laborCrewExpense;
                        totalLaborEquipmentExpenses += laborEquipmentExpense;
                        totalEquipmentFuelExpenses += equipmentFuelExpense;
                        totalLubricantExpenses += lubricantExpense;
                        grandSubTotal += total;

                        String roadSection = regularActivity.isIsOtherRoadSection() ? regularActivity.getOtherRoadSection() : regularActivity.getRoadSection().getName();
                        
                        addItemRow(i + 1, startingRow++, sheet, new CellStyle[]{numberStyle, activityTitleStyle, activityTitleStyle, 
                            numberStyle, currencyStyle, currencyStyle, currencyStyle, 
                            currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
                            center, currencyStyle, currencyStyle, center}, roadSection, regularActivity.getImplementationMode(),
                            laborCrewExpense, laborEquipmentExpense, equipmentFuelExpense, lubricantExpense, 
                            total);                      
                    }
                }
                
                //Sub Total
                addSubtotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                centerSubTotal, currencySubTotalStyle, currencySubTotalStyle, centerSubTotal}, 
                    totalLaborCrewExpenses, totalLaborEquipmentExpenses, 
                    totalEquipmentFuelExpenses, totalLubricantExpenses, grandSubTotal);
                
                total504LaborCrewExpenses += totalLaborCrewExpenses;
                total504LaborEquipmentExpenses += totalLaborEquipmentExpenses;
                total504EquipmentFuelExpenses += totalEquipmentFuelExpenses;
                total504LubricantExpenses += totalLubricantExpenses;
                grand504SubTotal += grandSubTotal;
            }
        }
        
        //Other Activity
        SubActivityDBController subActivityDBController = new SubActivityDBController();
        ArrayList<SubActivity> subActivityList = subActivityDBController.getList();
        
        Double totalOALaborCrewExpenses = 0.00;
        
        for(SubActivity subActivity : subActivityList){    
            if(subActivityDBController.hasWorkActivities(subActivity.getId(), timeFrame, year) && subActivity.getId() != 2){
                Double grandSubTotal = 0.00;

                ArrayList<OtherActivity> list = gdbc.getSpecifiedMonthlyOtherActivityList(timeFrame, subActivity.getId(), year);
                sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
                Row activityTitle = sheet.createRow(startingRow++);
                Cell activityTitleCell = activityTitle.createCell(1);
                activityTitleCell.setCellValue("Activity #504 - Other Work or Expenses (" + subActivity.getDescription() + ")");
                activityTitleCell.setCellStyle(activityHeaderStyle);

                for(int i = 2; i <= headerTitle.length; i++){
                    Cell cell = activityTitle.createCell(i);
                    cell.setCellValue("");
                    cell.setCellStyle(activityHeaderStyle);
                }

                //Work items
                for(int i = 0; i < list.size(); i++){
                    OtherActivity otherActivity = list.get(i);

                    Double laborCrewExpense = gdbc.getTotalOtherActivityLaborCrewCost(otherActivity.getId());

                    grandSubTotal += laborCrewExpense;

                    addItemRow(i + 1, startingRow++, sheet, new CellStyle[]{numberStyle, activityTitleStyle, activityTitleStyle, 
                        numberStyle, currencyStyle, currencyStyle, currencyStyle, 
                        currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
                        center, currencyStyle, currencyStyle, center}, otherActivity.getDescription(), otherActivity.getImplementationMode(),
                        laborCrewExpense, 0.0, 0.0, 0.0, 
                        laborCrewExpense);
                }

                //Sub Total
                addSubtotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                centerSubTotal, currencySubTotalStyle, currencySubTotalStyle, centerSubTotal}, 
                    grandSubTotal, 0.0, 
                    0.0, 0.0, grandSubTotal);
                
                totalOALaborCrewExpenses += grandSubTotal;
            }
        }
        
        //Drivers for Engineers 
        ArrayList<DriversForEngineers> driversForEngineersList = new DriversForEngineersDBController().getList(timeFrame, year);
        Double totalDFELaborEquipmentExpenses = 0.00, totalDFEEquipmentFuelExpenses = 0.00, totalDFELubricantExpenses = 0.00, grandDFESubTotal = 0.00;
        
        if(!driversForEngineersList.isEmpty()){             
            sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
            Row driversForEngineers = sheet.createRow(startingRow++);

            Cell driversForEngineersCell = driversForEngineers.createCell(1);
            driversForEngineersCell.setCellValue("Activity #504 - Other Work or Expenses");
            driversForEngineersCell.setCellStyle(activityHeaderStyle);

            for(int i = 2; i <= headerTitle.length; i++){
                Cell cell = driversForEngineers.createCell(i);
                cell.setCellValue("");
                cell.setCellStyle(activityHeaderStyle);
            }

            for(int i = 0; i < driversForEngineersList.size() ; i++){
                DriversForEngineers driversForEngineerData = driversForEngineersList.get(i);

                Double laborEquipmentExpense = driversForEngineerData.getLaborEquipmentCost(),
                       equipmentFuelExpense = driversForEngineerData.getEquipmentFuelCost(),
                       lubricantExpense = driversForEngineerData.getLubricantCost(),
                       total = 0.0;

                total = laborEquipmentExpense + equipmentFuelExpense + lubricantExpense;

                totalDFELaborEquipmentExpenses += laborEquipmentExpense;
                totalDFEEquipmentFuelExpenses += equipmentFuelExpense;
                totalDFELubricantExpenses += lubricantExpense;
                grandDFESubTotal += total;

                addItemRow(i + 1, startingRow++, sheet, new CellStyle[]{numberStyle, activityTitleStyle, activityTitleStyle, 
                numberStyle, currencyStyle, currencyStyle, currencyStyle, 
                currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
                center, currencyStyle, currencyStyle, center}, "Drivers For Engineer Sample", driversForEngineerData.getImplementationMode(),
                0.0, laborEquipmentExpense, equipmentFuelExpense, lubricantExpense, 
                total);
            }

            //Sub Total
            addSubtotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
                centerSubTotal, currencySubTotalStyle, currencySubTotalStyle, centerSubTotal}, 
                    0.0, totalDFELaborEquipmentExpenses, 
                    totalDFEEquipmentFuelExpenses, totalDFELubricantExpenses, grandDFESubTotal);

        }
        
        //Other Expenses
        OtherExpenses otherExpensesData = new OtherExpensesDBController().getOtherExpenses(timeFrame, year);
        Double totalOELaborCrewExpenses = otherExpensesData.getLaborCrewCost(), totalOELaborEquipmentExpenses = otherExpensesData.getLaborEquipmentCost(), 
                grandOESubTotal = 0.00, grandOEEquipmentsSubTotal = otherExpensesData.getLightEquipments() + otherExpensesData.getHeavyEquipments();
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
        Row otherExpensesRow = sheet.createRow(startingRow++);
        
        Cell otherExpensesCell = otherExpensesRow.createCell(1);
        otherExpensesCell.setCellValue("Activity #504 - Other Work or Expenses (Travelling Expenses)");
        otherExpensesCell.setCellStyle(left);

        for(int i = 2; i <= headerTitle.length; i++){
            Cell cell = otherExpensesRow.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(activityHeaderStyle);
        }        
        
        grandOESubTotal = totalOELaborCrewExpenses + totalOELaborEquipmentExpenses;

        addItemRow(1, startingRow++, sheet, new CellStyle[]{numberStyle, activityTitleStyle, activityTitleStyle, 
            numberStyle, currencyStyle, currencyStyle, currencyStyle, 
            currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
            center, currencyStyle, currencyStyle, center}, "Meal Allowance", otherExpensesData.getImplementationMode(),
            totalOELaborCrewExpenses, totalOELaborEquipmentExpenses, 0.0, 0.0, 
            grandOESubTotal);
        
        //Sub Total
        addSubtotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
            sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
            sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
            centerSubTotal, currencySubTotalStyle, currencySubTotalStyle, centerSubTotal}, 
                totalOELaborCrewExpenses, totalOELaborEquipmentExpenses, 
                0.0, 0.0, grandOESubTotal);
        
        
        //Indirect Cost Work Expenses
        addHeaderRow( startingRow++, sheet, left, "Indirect Cost Work Expenses");
        addHeaderRow( startingRow++, sheet, center, "Repair & Maint. Transportation Equipment");
        addItemRow(1, startingRow++, sheet, new CellStyle[]{numberStyle, right, right, 
            numberStyle, currencyStyle, currencyStyle, currencyStyle, 
            currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
            center, currencyStyle, currencyStyle, center}, "Light Equipments", otherExpensesData.getImplementationMode(),
            0.0, 0.0, 0.0, 0.0, 
            otherExpensesData.getLightEquipments());
        addHeaderRow( startingRow++, sheet, center, "Repair & Maint. Transportation Equipment");
        addItemRow(1, startingRow++, sheet, new CellStyle[]{numberStyle, right, right, 
            numberStyle, currencyStyle, currencyStyle, currencyStyle, 
            currencyStyle, currencyStyle, currencyStyle, currencyStyle, 
            center, currencyStyle, currencyStyle, center}, "Heavy Equipments", otherExpensesData.getImplementationMode(),
            0.0, 0.0, 0.0, 0.0, 
            otherExpensesData.getHeavyEquipments());
        sheet.setRepeatingRows(CellRangeAddress.valueOf("1:1"));
        
        //Sub Total
        addSubtotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
            sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
            sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, sidelessSubTotalCurrencyStyle, 
            centerSubTotal, currencySubTotalStyle, currencySubTotalStyle, centerSubTotal}, 
                0.0, 0.0, 
                0.0, 0.0, grandOEEquipmentsSubTotal);
        
        Double laborCrewCostGrandTotal = totalRALaborCrewExpenses + total504LaborCrewExpenses + totalOALaborCrewExpenses + totalOELaborCrewExpenses,
               laborEquipmentCostGrandTotal = totalRALaborEquipmentExpenses + total504LaborEquipmentExpenses + totalDFELaborEquipmentExpenses + totalOELaborEquipmentExpenses,
               equipmentFuelCostGrandTotal = totalRAEquipmentFuelExpenses + total504EquipmentFuelExpenses + totalDFEEquipmentFuelExpenses,
               lubricantCostGrandTotal = totalRALubricantExpenses + total504LubricantExpenses + totalDFELubricantExpenses,
               grandTotal = 0.0;
        
        grandTotal = grandRASubTotal + grand504SubTotal + grandDFESubTotal + grandOESubTotal + totalOALaborCrewExpenses + grandOEEquipmentsSubTotal;
        
        addGrandTotalRow(startingRow++, sheet, new CellStyle[]{totalStyle, totalStyle, totalStyle, 
            sidelessTotalCurrencyStyle, sidelessTotalCurrencyStyle, sidelessTotalCurrencyStyle, sidelessTotalCurrencyStyle, 
            sidelessTotalCurrencyStyle, sidelessTotalCurrencyStyle, sidelessTotalCurrencyStyle, sidelessTotalCurrencyStyle, 
            centerTotal, currencyTotalStyle, currencyTotalStyle, centerTotal}, 
                laborCrewCostGrandTotal, laborEquipmentCostGrandTotal, 
                equipmentFuelCostGrandTotal, lubricantCostGrandTotal, 
                grandTotal);
        
        //Program
        addBlankRow(startingRow++, sheet);
        addProgramTitle(startingRow++, sheet, workbook);
        
        Double totalProgramExpenses = 0.00;
        ArrayList<Program> programList = new ProgramDBController().getList(timeFrame, year);
        
        for(Program program : programList){
            addSourceOfFundName(startingRow++, sheet, left10, program.getSourceOfFund());
            
            ArrayList<Project> projectList = new ProgramDBController().getProjectList(program.getId());
            
            for(int i = 0; i < projectList.size(); i++){
                Project project = projectList.get(i);
                addProjectName(i+1, startingRow++, sheet, new CellStyle[]{numberStyle, left, centeredCurrencyStyle, center}, 
                        project.getDescription(), project.getImplementationMode(), project.getProjectCost());
                
                totalProgramExpenses += project.getProjectCost();
            }
        }
        
        addProgramGrandTotal(startingRow++, sheet, workbook, totalProgramExpenses);
        addBlankRow(startingRow++, sheet);
        addReportTotal(startingRow++, sheet, workbook, totalProgramExpenses + grandTotal);
        
        Double prevGrandTotal = 0.0;
        int placeOfMonth = 0;
        
        switch(checkQuarter(timeFrame)){
            case "1st Quarter":
                String[] firstQuarterMonths = {"January", "February", "March"};
              
                outerloop:
                for(int i = 0; i < 3; i++){
                    if(timeFrame.equalsIgnoreCase(firstQuarterMonths[i])){
                        placeOfMonth = i;
                        break outerloop;
                    }
                }
                break;
            case "2nd Quarter":
                String[] secondQuarterMonths = {"April", "May", "June"};
              
                outerloop:
                for(int i = 0; i < 3; i++){
                    if(timeFrame.equalsIgnoreCase(secondQuarterMonths[i])){
                        placeOfMonth = i;
                        break outerloop;
                    }
                }
                break;
            case "3rd Quarter":
                String[] thirdQuarterMonths = {"July", "August", "Septempber"};
              
                outerloop:
                for(int i = 0; i < 3; i++){
                    if(timeFrame.equalsIgnoreCase(thirdQuarterMonths[i])){
                        placeOfMonth = i;
                        break outerloop;
                    }
                }
                break;
            case "4th Quarter":
                String[] fourthQuarterMonths = {"October", "November", "December"};
              
                outerloop:
                for(int i = 0; i < 3; i++){
                    if(timeFrame.equalsIgnoreCase(fourthQuarterMonths[i])){
                        placeOfMonth = i;
                        break outerloop;
                    }
                }
                break;
        }
        
        if(placeOfMonth != 0){
            ArrayList<RegularActivity> raList;
            ArrayList<OtherActivity> oaList;
            ArrayList<DriversForEngineers> dfeList;
            ArrayList<OtherExpenses> oeList;

            switch(checkQuarter(timeFrame)){
                case "1st Quarter":
                    Double laborCrew = 0.0, laborEquipment = 0.0, equipmentFuel = 0.0, lubricant = 0.0;
                    String[] firstQuarterMonths = {"January", "February", "March"};
                    
                    for(int i = 0; i < placeOfMonth; i++){
                        raList = gdbc.getMonthlyRegularActivityList(firstQuarterMonths[i], year);
                        oaList = gdbc.getMonthlyOtherActivityList(firstQuarterMonths[i], year);
                        dfeList = gdbc.getMonthlyDriversForEngineersList(firstQuarterMonths[i], year);
                        oeList = gdbc.getMonthlyOtherExpensesList(firstQuarterMonths[i], year);

                        laborCrew = gdbc.getTotalLaborCrewCost(raList, oaList, oeList);
                        laborEquipment = gdbc.getTotalLaborEquipmentCost(raList, oeList, dfeList);
                        equipmentFuel = gdbc.getTotalEquipmentFuelCost(raList, dfeList);
                        lubricant = gdbc.getTotalLubricantCost(raList, dfeList);

                        OtherExpenses oeData = new OtherExpensesDBController().getOtherExpenses(firstQuarterMonths[i], year);
                        
                        Double totalPE = 0.00;
                        
                        ArrayList<Program> pList = new ProgramDBController().getList(firstQuarterMonths[i], year);

                        for(Program program : pList){
                            ArrayList<Project> projectList = new ProgramDBController().getProjectList(program.getId());

                            for(int j = 0; j < projectList.size(); j++){
                                Project project = projectList.get(j);
                                totalPE += project.getProjectCost();
                            }
                        }
                        
                        prevGrandTotal += laborCrew + laborEquipment + equipmentFuel + lubricant + oeData.getLightEquipments() + oeData.getHeavyEquipments() + totalPE;
                    }

                    break;
                    
                case "2nd Quarter":
                    Double secondlaborCrew = 0.0, secondlaborEquipment = 0.0, secondequipmentFuel = 0.0, secondlubricant = 0.0;
                    String[] secondQuarterMonths = {"April", "May", "June"};

                    for(int i = 0; i < placeOfMonth; i++){
                        raList = gdbc.getMonthlyRegularActivityList(secondQuarterMonths[i], year);
                        oaList = gdbc.getMonthlyOtherActivityList(secondQuarterMonths[i], year);
                        dfeList = gdbc.getMonthlyDriversForEngineersList(secondQuarterMonths[i], year);
                        oeList = gdbc.getMonthlyOtherExpensesList(secondQuarterMonths[i], year);

                        secondlaborCrew = gdbc.getTotalLaborCrewCost(raList, oaList, oeList);
                        secondlaborEquipment = gdbc.getTotalLaborEquipmentCost(raList, oeList, dfeList);
                        secondequipmentFuel = gdbc.getTotalEquipmentFuelCost(raList, dfeList);
                        secondlubricant = gdbc.getTotalLubricantCost(raList, dfeList);

                        OtherExpenses oeData = new OtherExpensesDBController().getOtherExpenses(secondQuarterMonths[i], year);
                        
                        Double totalPE = 0.00;
                        
                        ArrayList<Program> pList = new ProgramDBController().getList(secondQuarterMonths[i], year);

                        for(Program program : pList){
                            ArrayList<Project> projectList = new ProgramDBController().getProjectList(program.getId());

                            for(int j = 0; j < projectList.size(); j++){
                                Project project = projectList.get(j);
                                totalPE += project.getProjectCost();
                            }
                        }
                        
                        prevGrandTotal += secondlaborCrew + secondlaborEquipment + secondequipmentFuel + secondlubricant + oeData.getLightEquipments() + oeData.getHeavyEquipments() + totalPE;
                    }

                    break;
                    
                case "3rd Quarter":
                    Double thirdlaborCrew = 0.0, thirdlaborEquipment = 0.0, thirdequipmentFuel = 0.0, thirdlubricant = 0.0;
                    String[] thirdQuarterMonths = {"July", "August", "Septempber"};
                    
                    for(int i = 0; i < placeOfMonth; i++){
                        raList = gdbc.getMonthlyRegularActivityList(thirdQuarterMonths[i], year);
                        oaList = gdbc.getMonthlyOtherActivityList(thirdQuarterMonths[i], year);
                        dfeList = gdbc.getMonthlyDriversForEngineersList(thirdQuarterMonths[i], year);
                        oeList = gdbc.getMonthlyOtherExpensesList(thirdQuarterMonths[i], year);

                        thirdlaborCrew = gdbc.getTotalLaborCrewCost(raList, oaList, oeList);
                        thirdlaborEquipment = gdbc.getTotalLaborEquipmentCost(raList, oeList, dfeList);
                        thirdequipmentFuel = gdbc.getTotalEquipmentFuelCost(raList, dfeList);
                        thirdlubricant = gdbc.getTotalLubricantCost(raList, dfeList);

                        OtherExpenses oeData = new OtherExpensesDBController().getOtherExpenses(thirdQuarterMonths[i], year);
                        
                        Double totalPE = 0.00;
                        
                        ArrayList<Program> pList = new ProgramDBController().getList(thirdQuarterMonths[i], year);

                        for(Program program : pList){
                            ArrayList<Project> projectList = new ProgramDBController().getProjectList(program.getId());

                            for(int j = 0; j < projectList.size(); j++){
                                Project project = projectList.get(j);
                                totalPE += project.getProjectCost();
                            }
                        }
                        
                        prevGrandTotal += thirdlaborCrew + thirdlaborEquipment + thirdequipmentFuel + thirdlubricant + oeData.getLightEquipments() + oeData.getHeavyEquipments() + totalPE;
                    }

                    break;
                    
                case "4th Quarter":
                    Double fourthlaborCrew = 0.0, fourthlaborEquipment = 0.0, fourthequipmentFuel = 0.0, fourthlubricant = 0.0;
                    String[] fourthQuarterMonths = {"July", "August", "Septempber"};

                    for(int i = 0; i < placeOfMonth; i++){
                        raList = gdbc.getMonthlyRegularActivityList(fourthQuarterMonths[i], year);
                        oaList = gdbc.getMonthlyOtherActivityList(fourthQuarterMonths[i], year);
                        dfeList = gdbc.getMonthlyDriversForEngineersList(fourthQuarterMonths[i], year);
                        oeList = gdbc.getMonthlyOtherExpensesList(fourthQuarterMonths[i], year);

                        fourthlaborCrew = gdbc.getTotalLaborCrewCost(raList, oaList, oeList);
                        fourthlaborEquipment = gdbc.getTotalLaborEquipmentCost(raList, oeList, dfeList);
                        fourthequipmentFuel = gdbc.getTotalEquipmentFuelCost(raList, dfeList);
                        fourthlubricant = gdbc.getTotalLubricantCost(raList, dfeList);

                        OtherExpenses oeData = new OtherExpensesDBController().getOtherExpenses(fourthQuarterMonths[i], year);
                        
                        Double totalPE = 0.00;
                        
                        ArrayList<Program> pList = new ProgramDBController().getList(fourthQuarterMonths[i], year);

                        for(Program program : pList){
                            ArrayList<Project> projectList = new ProgramDBController().getProjectList(program.getId());

                            for(int j = 0; j < projectList.size(); j++){
                                Project project = projectList.get(j);
                                totalPE += project.getProjectCost();
                            }
                        }
                        
                        prevGrandTotal += fourthlaborCrew + fourthlaborEquipment + fourthequipmentFuel + fourthlubricant + oeData.getLightEquipments() + oeData.getHeavyEquipments() + totalPE;
                    }

                    break;
            }
        }
        
        addReportGrandTotal(startingRow++, sheet, workbook, prevGrandTotal + totalProgramExpenses + grandTotal, timeFrame);
        
        
        addBlankRow(startingRow++, sheet);
        addNotes(startingRow++, sheet, workbook);
        
        //Output
        String fileName = getExtension(file.getName()).toLowerCase().equals(xlsx) ? file.getName() : file.getName() + "." + xlsx;
        String fileDirectory = validateFilePath(filePath) ;
        
        try(FileOutputStream outputStream = new FileOutputStream(fileDirectory + fileName)){
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }  
    
    private static String checkQuarter(String month){
        String quarter = "";
        
        switch(month){
            case "January":
                quarter = "1st Quarter";
                break;
            case "February":
                quarter = "1st Quarter";
                break;
            case "March":
                quarter = "1st Quarter";
                break;
            case "April":
                quarter = "2nd Quarter";
                break;
            case "May":
                quarter = "2nd Quarter";
                break;
            case "June":
                quarter = "2nd Quarter";
                break;
            case "July":
                quarter = "3rd Quarter";
                break;
            case "August":
                quarter = "3rd Quarter";
                break;
            case "September":
                quarter = "3rd Quarter";
                break;
            case "October":
                quarter = "4th Quarter";
                break;
            case "November":
                quarter = "4th Quarter";
                break;
            case "December":
                quarter = "4th Quarter";
                break;
            default:
                quarter = "Error";
                break;
        }
        
        return quarter;
    }
    
    private static void addNotes(int startingRow, XSSFSheet sheet, Workbook workbook){
        String preparedBy1Name = "", preparedBy1Position = "", preparedBy2Name = "", preparedBy2Position = "", 
                submittedByName = "", submittedByPosition = "", approvedByName = "", approvedByPosition = "";
        try (InputStream input = new FileInputStream("src\\mdqrs\\path\\to\\report_config.properties")) {
            Properties report = new Properties();
            report.load(input);

            preparedBy1Name = report.getProperty("prepared_by_1_name");
            preparedBy1Position = report.getProperty("prepared_by_1_position");
            preparedBy2Name = report.getProperty("prepared_by_2_name");
            preparedBy2Position = report.getProperty("prepared_by_2_position");
            submittedByName = report.getProperty("submitted_by_name");
            submittedByPosition = report.getProperty("submitted_by_position");
            approvedByName = report.getProperty("approved_by_name");
            approvedByPosition = report.getProperty("approved_by_position");

        } catch (IOException io) {
            io.printStackTrace();
        }
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 6));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 7, 11));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 12, 15));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 7, 11));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 12, 15));
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 7, 11));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 12, 15));
        
        Row row1 = sheet.createRow(startingRow);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        
        font1.setFontHeightInPoints((short) 10);
        style1.setFont(font1);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setAlignment(HorizontalAlignment.LEFT);
        
        Cell tag = row1.createCell(1);
        tag.setCellValue("Prepared By: ");
        tag.setCellStyle(style1);
        
        for(int i = 2; i <= 6; i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style1);
        }
        
        tag = row1.createCell(7);
        tag.setCellValue("Submitted By:");
        tag.setCellStyle(style1);
        
        for(int i = 8; i <= 11; i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style1);
        }
        
        tag = row1.createCell(12);
        tag.setCellValue("Approved By:");
        tag.setCellStyle(style1);
        
        for(int i = 13; i <= 15; i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style1);
        }
        
        addBlankRow(startingRow + 1, sheet);
        addBlankRow(startingRow + 2, sheet);
        
        Row row2 = sheet.createRow(startingRow + 3);
        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 12);
        font2.setUnderline(Font.U_SINGLE);
        style2.setFont(font2);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setAlignment(HorizontalAlignment.CENTER);
        
        Cell name = row2.createCell(1);
        name.setCellValue(preparedBy1Name);
        name.setCellStyle(style2);
        
        for(int i = 2; i <= 3; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        name = row2.createCell(4);
        name.setCellValue(preparedBy2Name);
        name.setCellStyle(style2);
        
        for(int i = 5; i <= 6; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        name = row2.createCell(7);
        name.setCellValue(submittedByName);
        name.setCellStyle(style2);
        
        for(int i = 8; i <= 11; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        name = row2.createCell(12);
        name.setCellValue(approvedByName);
        name.setCellStyle(style2);
        
        for(int i = 13; i <= 15; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        Row row3 = sheet.createRow(startingRow + 4);
        CellStyle style3 = workbook.createCellStyle();
        Font font3 = workbook.createFont();
        
        font3.setFontHeightInPoints((short) 9);
        style3.setFont(font3);
        style3.setVerticalAlignment(VerticalAlignment.CENTER);
        style3.setAlignment(HorizontalAlignment.CENTER);
        
        Cell position = row3.createCell(1);
        position.setCellValue(preparedBy1Position);
        position.setCellStyle(style3);
        
        for(int i = 2; i <= 3; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
        
        position = row3.createCell(4);
        position.setCellValue(preparedBy2Position);
        position.setCellStyle(style3);
        
        for(int i = 5; i <= 6; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
        
        position = row3.createCell(7);
        position.setCellValue(submittedByPosition);
        position.setCellStyle(style3);
        
        for(int i = 8; i <= 11; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
        
        position = row3.createCell(12);
        position.setCellValue(approvedByPosition);
        position.setCellStyle(style3);
        
        for(int i = 13; i <= 15; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
    }
    
    private static void addReportGrandTotal(int startingRow, XSSFSheet sheet, Workbook workbook, Double grandTotal, String month){
        String quarter =  checkQuarter(month);       
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 15));
        Row row = sheet.createRow(startingRow);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle totalStyle = workbook.createCellStyle();
        Font totalFont = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        totalStyle.setDataFormat(format.getFormat("#,##0.00"));
        
        totalFont.setBold(true);
        totalFont.setFontHeightInPoints((short) 11);
        totalStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalStyle.setFont(totalFont);
        totalStyle.setWrapText(true);
        totalStyle.setBorderLeft(BorderStyle.THIN);
        totalStyle.setBorderTop(BorderStyle.THIN);
        totalStyle.setBorderRight(BorderStyle.THIN);
        totalStyle.setBorderBottom(BorderStyle.THIN);
        totalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        totalStyle.setAlignment(HorizontalAlignment.RIGHT);
        
        Cell programTitleCell = row.createCell(1);
        programTitleCell.setCellValue("GRAND TOTAL (" + quarter + ")");
        programTitleCell.setCellStyle(style);

        for(int i = 2; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style);
        }
        
        Cell projectCostCell = row.createCell(11);
        projectCostCell.setCellValue(grandTotal);
        projectCostCell.setCellStyle(totalStyle);

        for(int i = 12; i <= 15; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(totalStyle);
        }
    }
    
    private static void addReportTotal(int startingRow, XSSFSheet sheet, Workbook workbook, Double grandTotal){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 15));
        Row row = sheet.createRow(startingRow);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle totalStyle = workbook.createCellStyle();
        Font totalFont = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        totalStyle.setDataFormat(format.getFormat("#,##0.00"));
        
        totalFont.setBold(true);
        totalFont.setFontHeightInPoints((short) 11);
        totalStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalStyle.setFont(totalFont);
        totalStyle.setWrapText(true);
        totalStyle.setBorderLeft(BorderStyle.THIN);
        totalStyle.setBorderTop(BorderStyle.THIN);
        totalStyle.setBorderRight(BorderStyle.THIN);
        totalStyle.setBorderBottom(BorderStyle.THIN);
        totalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        totalStyle.setAlignment(HorizontalAlignment.RIGHT);
        
        Cell programTitleCell = row.createCell(1);
        programTitleCell.setCellValue("TOTAL");
        programTitleCell.setCellStyle(style);

        for(int i = 2; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style);
        }
        
        Cell projectCostCell = row.createCell(11);
        projectCostCell.setCellValue(grandTotal);
        projectCostCell.setCellStyle(totalStyle);

        for(int i = 12; i <= 15; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(totalStyle);
        }
    }
    
    private static void addBlankRow(int startingRow, XSSFSheet sheet){
        Row row = sheet.createRow(startingRow);

        for(int i = 1; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
        }
        
    }
    
    private static void addProjectName(int number, int startingRow, XSSFSheet sheet, CellStyle[] style, String projectName, String implementationMode,Double projectCost){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 2, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 14));
        Row row = sheet.createRow(startingRow++);
        
        
        Cell numberCell = row.createCell(1);
        numberCell.setCellValue(number);
        numberCell.setCellStyle(style[0]);
        
        Cell nameCell = row.createCell(2);
        nameCell.setCellValue(projectName);
        nameCell.setCellStyle(style[1]);
        
        for(int i = 3; i <= 10; i++){
            Cell cell = row.createCell(i);
            
            cell.setCellValue("");
            cell.setCellStyle(style[1]);  
        }   
        
        Cell projectCostCell = row.createCell(11);
        projectCostCell.setCellValue(projectCost);
        projectCostCell.setCellStyle(style[2]);
        
        for(int i = 12; i <= 14; i++){
            Cell cell = row.createCell(i);
            
            cell.setCellValue("");
            cell.setCellStyle(style[2]);  
        }   
        
        Cell implementationModeCell = row.createCell(15);
        implementationModeCell.setCellValue( "By " + implementationMode);
        implementationModeCell.setCellStyle(style[3]);
    }
   
    private static void addSourceOfFundName(int startingRow, XSSFSheet sheet, CellStyle style, String sourceOfFund){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 14));
        Row row = sheet.createRow(startingRow++);
        row.setHeight((short) 600);
        Cell name = row.createCell(1);
        name.setCellValue("            Source of Fund:  " + sourceOfFund);
        name.setCellStyle(style);
        
        for(int i = 2; i <= 15; i++){
            Cell cell = row.createCell(i);
            
            cell.setCellValue("");
            cell.setCellStyle(style);  
        }   
    }
    
    private static void addProgramGrandTotal(int startingRow, XSSFSheet sheet, Workbook workbook, Double grandTotal){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 14));
        Row row = sheet.createRow(startingRow);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        CellStyle currencyStyle = workbook.createCellStyle();
        Font currencyFont = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        currencyStyle.setDataFormat(format.getFormat("#,##0.00"));
        
        currencyFont.setBold(true);
        currencyFont.setFontHeightInPoints((short) 10);
        currencyStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        currencyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        currencyStyle.setFont(currencyFont);
        currencyStyle.setWrapText(true);
        currencyStyle.setBorderLeft(BorderStyle.THIN);
        currencyStyle.setBorderTop(BorderStyle.THIN);
        currencyStyle.setBorderRight(BorderStyle.THIN);
        currencyStyle.setBorderBottom(BorderStyle.THIN);
        currencyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        currencyStyle.setAlignment(HorizontalAlignment.CENTER);
        
        
        Cell programTitleCell = row.createCell(1);
        programTitleCell.setCellValue("Total");
        programTitleCell.setCellStyle(style);

        for(int i = 2; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style);
        }
        
        Cell projectCostCell = row.createCell(11);
        projectCostCell.setCellValue(grandTotal);
        projectCostCell.setCellStyle(currencyStyle);

        for(int i = 12; i <= 15; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(currencyStyle);
        }
    }
    
    private static void addProgramTitle(int startingRow, XSSFSheet sheet, Workbook workbook){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 14));
        Row row = sheet.createRow(startingRow);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        Cell programTitleCell = row.createCell(1);
        programTitleCell.setCellValue("PROJECTS/PROGRAM OF WORKS");
        programTitleCell.setCellStyle(style);

        for(int i = 2; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style);
        }
        
        Cell projectCostCell = row.createCell(11);
        projectCostCell.setCellValue("PROJECT COST");
        projectCostCell.setCellStyle(style);

        for(int i = 12; i <= 15; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style);
        }
    }
    
    private static void addHeaderRow(int startingRow, XSSFSheet sheet, CellStyle style,String workTitle){     
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
        Row otherExpensesRow = sheet.createRow(startingRow++);
        
        Cell otherExpensesCell = otherExpensesRow.createCell(1);
        otherExpensesCell.setCellValue(workTitle);
        otherExpensesCell.setCellStyle(style);

        for(int i = 2; i <= 15; i++){
            Cell cell = otherExpensesRow.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style);
        }   
    }
    
    private static void addItemRow(int i, int startingRow, XSSFSheet sheet, CellStyle[] styles,String workTitle, String implementationMode,
            Double totalLaborCrewExpenses, Double totalLaborEquipmentExpenses, 
            Double totalEquipmentFuelExpenses, Double totalLubricantExpenses, 
            Double grandTotalExpenses){
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 2, 3));
        Row itemRow = sheet.createRow(startingRow++);

        Cell number = itemRow.createCell(1);
        number.setCellValue(i);
        number.setCellStyle(styles[0]);

        Cell activityTitleColumn = itemRow.createCell(2);
        activityTitleColumn.setCellValue(workTitle);
        activityTitleColumn.setCellStyle(styles[1]);

        Cell blankCell = itemRow.createCell(3);
        blankCell.setCellValue("");
        blankCell.setCellStyle(styles[2]);      

        Cell length = itemRow.createCell(4);
        length.setCellValue("");
        length.setCellStyle(styles[3]);

        Cell laborCrewCost = itemRow.createCell(5);
        laborCrewCost.setCellValue(totalLaborCrewExpenses);
        laborCrewCost.setCellStyle(styles[4]);

        Cell laborEquipmentCost = itemRow.createCell(6);
        laborEquipmentCost.setCellValue(totalLaborEquipmentExpenses);
        laborEquipmentCost.setCellStyle(styles[5]);

        Cell equipmentFuelCost = itemRow.createCell(7);
        equipmentFuelCost.setCellValue(totalEquipmentFuelExpenses);
        equipmentFuelCost.setCellStyle(styles[6]);

        Cell lubricantCost = itemRow.createCell(8);
        lubricantCost.setCellValue(totalLubricantExpenses);
        lubricantCost.setCellStyle(styles[7]);

        Cell materialCost = itemRow.createCell(9);
        materialCost.setCellValue("");
        materialCost.setCellStyle(styles[8]);

        Cell otherExpenses = itemRow.createCell(10);
        otherExpenses.setCellValue("");
        otherExpenses.setCellStyle(styles[9]);

        Cell estimatedCost = itemRow.createCell(11);
        estimatedCost.setCellValue(grandTotalExpenses);
        estimatedCost.setCellStyle(styles[10]);

        Cell physicalAccomplishment1 = itemRow.createCell(12);
        physicalAccomplishment1.setCellValue("100%");
        physicalAccomplishment1.setCellStyle(styles[11]);

        Cell financialAccomplishment1 = itemRow.createCell(13);
        financialAccomplishment1.setCellValue(grandTotalExpenses);
        financialAccomplishment1.setCellStyle(styles[12]);

        Cell actualCost = itemRow.createCell(14);
        actualCost.setCellValue(grandTotalExpenses);
        actualCost.setCellStyle(styles[13]);

        Cell modeOfImplementation = itemRow.createCell(15);
        modeOfImplementation.setCellValue("By " + implementationMode);
        modeOfImplementation.setCellStyle(styles[14]);
    }
    
    private static void addGrandTotalRow(int startingRow, XSSFSheet sheet, CellStyle[] styles,
            Double totalLaborCrewExpenses, Double totalLaborEquipmentExpenses, 
            Double totalEquipmentFuelExpenses, Double totalLubricantExpenses, 
            Double grandTotalExpenses){
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
        Row subTotalRow = sheet.createRow(startingRow);

        Cell subTotalTitle = subTotalRow.createCell(1);
        subTotalTitle.setCellValue("Total");
        subTotalTitle.setCellStyle(styles[0]);

        Cell blankCell1 = subTotalRow.createCell(2);
        blankCell1.setCellValue("");
        blankCell1.setCellStyle(styles[1]);

        Cell blankCell2 = subTotalRow.createCell(3);
        blankCell2.setCellValue("");
        blankCell2.setCellStyle(styles[2]);

        Cell blankCell3 = subTotalRow.createCell(4);
        blankCell3.setCellValue("");
        blankCell3.setCellStyle(styles[3]);

        Cell laborCrewSubTotal = subTotalRow.createCell(5);
        laborCrewSubTotal.setCellValue(totalLaborCrewExpenses);
        laborCrewSubTotal.setCellStyle(styles[4]);

        Cell laborEquipmentSubTotal = subTotalRow.createCell(6);
        laborEquipmentSubTotal.setCellValue(totalLaborEquipmentExpenses);
        laborEquipmentSubTotal.setCellStyle(styles[5]);

        Cell equipmentFuelSubTotal = subTotalRow.createCell(7);
        equipmentFuelSubTotal.setCellValue(totalEquipmentFuelExpenses);
        equipmentFuelSubTotal.setCellStyle(styles[6]);

        Cell lubricantSubTotal = subTotalRow.createCell(8);
        lubricantSubTotal.setCellValue(totalLubricantExpenses);
        lubricantSubTotal.setCellStyle(styles[7]);

        Cell materialCostSubTotal1 = subTotalRow.createCell(9);
        materialCostSubTotal1.setCellValue("");
        materialCostSubTotal1.setCellStyle(styles[8]);

        Cell otherExpensesSubTotal1 = subTotalRow.createCell(10);
        otherExpensesSubTotal1.setCellValue("");
        otherExpensesSubTotal1.setCellStyle(styles[9]);

        Cell estimatedExpenses1 = subTotalRow.createCell(11);
        estimatedExpenses1.setCellValue(grandTotalExpenses);
        estimatedExpenses1.setCellStyle(styles[10]);

        Cell physicalAccomplishment11 = subTotalRow.createCell(12);
        physicalAccomplishment11.setCellValue("");
        physicalAccomplishment11.setCellStyle(styles[11]);  

        Cell financialAccomplishment11 = subTotalRow.createCell(13);
        financialAccomplishment11.setCellValue(grandTotalExpenses);
        financialAccomplishment11.setCellStyle(styles[12]);  

        Cell actualExpenses1 = subTotalRow.createCell(14);
        actualExpenses1.setCellValue(grandTotalExpenses);
        actualExpenses1.setCellStyle(styles[13]);  

        Cell implementationMode1 = subTotalRow.createCell(15);
        implementationMode1.setCellValue("");
        implementationMode1.setCellStyle(styles[14]);
    }
    
    private static void addSubtotalRow(int startingRow, XSSFSheet sheet, CellStyle[] styles,
            Double totalLaborCrewExpenses, Double totalLaborEquipmentExpenses, 
            Double totalEquipmentFuelExpenses, Double totalLubricantExpenses, 
            Double grandTotalExpenses){
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 3));
        Row subTotalRow = sheet.createRow(startingRow);
        
        Cell subTotalTitle = subTotalRow.createCell(1);
        subTotalTitle.setCellValue("Sub-Total");
        subTotalTitle.setCellStyle(styles[0]);

        Cell blankCell1 = subTotalRow.createCell(2);
        blankCell1.setCellValue("");
        blankCell1.setCellStyle(styles[1]);

        Cell blankCell2 = subTotalRow.createCell(3);
        blankCell2.setCellValue("");
        blankCell2.setCellStyle(styles[2]);

        Cell blankCell3 = subTotalRow.createCell(4);
        blankCell3.setCellValue("");
        blankCell3.setCellStyle(styles[3]);

        Cell laborCrewSubTotal = subTotalRow.createCell(5);
        laborCrewSubTotal.setCellValue(totalLaborCrewExpenses);
        laborCrewSubTotal.setCellStyle(styles[4]);

        Cell laborEquipmentSubTotal = subTotalRow.createCell(6);
        laborEquipmentSubTotal.setCellValue(totalLaborEquipmentExpenses);
        laborEquipmentSubTotal.setCellStyle(styles[5]);

        Cell equipmentFuelSubTotal = subTotalRow.createCell(7);
        equipmentFuelSubTotal.setCellValue(totalEquipmentFuelExpenses);
        equipmentFuelSubTotal.setCellStyle(styles[6]);

        Cell lubricantSubTotal = subTotalRow.createCell(8);
        lubricantSubTotal.setCellValue(totalLubricantExpenses);
        lubricantSubTotal.setCellStyle(styles[7]);

        Cell materialCostSubTotal1 = subTotalRow.createCell(9);
        materialCostSubTotal1.setCellValue("");
        materialCostSubTotal1.setCellStyle(styles[8]);

        Cell otherExpensesSubTotal1 = subTotalRow.createCell(10);
        otherExpensesSubTotal1.setCellValue("");
        otherExpensesSubTotal1.setCellStyle(styles[9]);

        Cell estimatedExpenses1 = subTotalRow.createCell(11);
        estimatedExpenses1.setCellValue(grandTotalExpenses);
        estimatedExpenses1.setCellStyle(styles[10]);

        Cell physicalAccomplishment11 = subTotalRow.createCell(12);
        physicalAccomplishment11.setCellValue("");
        physicalAccomplishment11.setCellStyle(styles[11]);  

        Cell financialAccomplishment11 = subTotalRow.createCell(13);
        financialAccomplishment11.setCellValue(grandTotalExpenses);
        financialAccomplishment11.setCellStyle(styles[12]);  

        Cell actualExpenses1 = subTotalRow.createCell(14);
        actualExpenses1.setCellValue(grandTotalExpenses);
        actualExpenses1.setCellStyle(styles[13]);  

        Cell implementationMode1 = subTotalRow.createCell(15);
        implementationMode1.setCellValue("");
        implementationMode1.setCellStyle(styles[14]);
    }
    
    public static CellStyle createTotalStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createSubTotalStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createColumnHeaderStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        
        return style;
    }
    
    public static CellStyle createTagStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 10);
        font.setUnderline((byte) 1);
        style.setFont(font);
        style.setWrapText(true);
        
        return style;
    }
    
    public static CellStyle createTimeFrameStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    public static CellStyle createActivityHeaderStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 6);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createActivityTitleStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        
        return style;
    }
    
    public static CellStyle createNumberStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createSidelessTotalCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createSidelessSubTotalCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createSidelessCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createTotalCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createSubTotalCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static CellStyle createCenteredCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    public static CellStyle createCenteredTotalStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    public static CellStyle createCenteredSubTotalStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    public static CellStyle createCenteredStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    public static CellStyle createLeft10Style(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        
        return style;
    }
    
    public static CellStyle createLeftStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 6);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        
        return style;
    }
    
    public static CellStyle createRightStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 6);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.RIGHT);
        
        return style;
    }
    
    public static String validateFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return filePath;
        }

        StringBuilder validatedFilePath = new StringBuilder();
        for (int i = 0; i < filePath.length(); i++) {
            char currentChar = filePath.charAt(i);
            validatedFilePath.append(currentChar);
            if (currentChar == '\\') {
                validatedFilePath.append(currentChar);
            }
        }

        return validatedFilePath.toString();
    }
    
    public static String getExtension(String s) {
        String ext = s;
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
