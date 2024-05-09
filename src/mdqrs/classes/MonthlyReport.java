/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

import classes.Activity;
import classes.RegularActivity;
import classes.SubActivity;
import classes.OtherActivity;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import mdqrs.dbcontroller.ActivityDBController;
import mdqrs.dbcontroller.DriversForEngineersDBController;
import mdqrs.dbcontroller.GeneralDBController;
import mdqrs.dbcontroller.OtherExpensesDBController;
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
    private String headerTitle;
    private String timeFrame;
    private int year;
    private ArrayList<String> organizationHeads;
    
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
        CellStyle center = createCenteredStyle(workbook);
        CellStyle left = createLeftStyle(workbook);
        CellStyle subTotalStyle = createSubTotalStyle(workbook);
        CellStyle sidelessCurrencyStyle = createSidelessCurrencyStyle(workbook);
        
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
                
                ArrayList<RegularActivity> list = gdbc.getSpecifiedMonthlyRegularActivityList(timeFrame, activity.getItemNumber());
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
                sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
                sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
                center, currencyStyle, currencyStyle, center}, 
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
                
                ArrayList<RegularActivity> list = gdbc.getSpecifiedMonthlyRegularActivityList(timeFrame, activity.getItemNumber());
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
                sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
                sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
                center, currencyStyle, currencyStyle, center}, 
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
            if(subActivityDBController.hasWorkActivities(subActivity.getId(), timeFrame) && subActivity.getId() != 2){
                Double grandSubTotal = 0.00;

                ArrayList<OtherActivity> list = gdbc.getSpecifiedMonthlyOtherActivityList(timeFrame, subActivity.getId());
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
                sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
                sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
                center, currencyStyle, currencyStyle, center}, 
                    grandSubTotal, 0.0, 
                    0.0, 0.0, grandSubTotal);
                
                totalOALaborCrewExpenses += grandSubTotal;
            }
        }
        
        //Drivers for Engineers 
        ArrayList<DriversForEngineers> driversForEngineersList = new DriversForEngineersDBController().getList(timeFrame, year);
        Double totalDFELaborEquipmentExpenses = 0.00, totalDFEEquipmentFuelExpenses = 0.00, totalDFELubricantExpenses = 0.00, grandDFESubTotal = 0.00;
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
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            center, currencyStyle, currencyStyle, center}, 
                0.0, totalDFELaborEquipmentExpenses, 
                totalDFEEquipmentFuelExpenses, totalDFELubricantExpenses, grandDFESubTotal);
        
        //Other Expenses
        OtherExpenses otherExpensesData = new OtherExpensesDBController().getOtherExpenses(timeFrame);
        Double totalOELaborEquipmentExpenses = otherExpensesData.getLaborCrewCost(), totalOELaborCrewExpenses = otherExpensesData.getLaborEquipmentCost(), 
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
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            center, currencyStyle, currencyStyle, center}, 
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
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            center, currencyStyle, currencyStyle, center}, 
                0.0, 0.0, 
                0.0, 0.0, grandOEEquipmentsSubTotal);
        
        Double laborCrewCostGrandTotal = totalRALaborCrewExpenses + total504LaborCrewExpenses + totalOALaborCrewExpenses + totalOELaborCrewExpenses,
               laborEquipmentCostGrandTotal = totalRALaborEquipmentExpenses + total504LaborEquipmentExpenses + totalDFELaborEquipmentExpenses + totalOELaborEquipmentExpenses,
               equipmentFuelCostGrandTotal = totalRAEquipmentFuelExpenses + total504EquipmentFuelExpenses + totalDFEEquipmentFuelExpenses,
               lubricantCostGrandTotal = totalRALubricantExpenses + total504LubricantExpenses + totalDFELubricantExpenses,
               grandTotal = 0.0;
        
        grandTotal = grandRASubTotal + grand504SubTotal + grandDFESubTotal + grandOESubTotal + totalOALaborCrewExpenses + grandOEEquipmentsSubTotal;
        
        addGrandTotalRow(startingRow++, sheet, new CellStyle[]{subTotalStyle, subTotalStyle, subTotalStyle, 
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, sidelessCurrencyStyle, 
            center, currencyStyle, currencyStyle, center}, 
                laborCrewCostGrandTotal, laborEquipmentCostGrandTotal, 
                equipmentFuelCostGrandTotal, lubricantCostGrandTotal, 
                grandTotal);
        
        //Output
        try(FileOutputStream outputStream = new FileOutputStream("C:\\Users\\userpc\\Desktop\\sampleReport.xlsx")){
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
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
    
    public static CellStyle createSubTotalStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
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
}
