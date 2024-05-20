/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.reports;

import classes.CrewEquipment;
import classes.CrewEquipmentList;
import classes.CrewMaterials;
import classes.CrewMaterialsList;
import classes.CrewPersonnel;
import classes.CrewPersonnelList;
import classes.OpsEquipment;
import classes.OpsEquipmentList;
import classes.RegularActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import mdqrs.dbcontroller.ActivityListDBController;
import static mdqrs.reports.MonthlyReport.getExtension;
import static mdqrs.reports.MonthlyReport.validateFilePath;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Vienji
 */
public class RegularActivityReport {
    private static final String xlsx = "xlsx";
    private RegularActivity regularActivity;
    private String filePath;
    private File file;
    
    public RegularActivityReport(RegularActivity regularActivity){
        this.regularActivity = regularActivity;
    }
    
    public void setFilePath(String filePath, File file){
        this.filePath = filePath;
        this.file = file;
    }
    
    public void generateReport(){
        Workbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet();
        
        //Paper Layout
        sheet.getPrintSetup().setPaperSize(PrintSetup.LEGAL_PAPERSIZE);
        sheet.setMargin(Sheet.LeftMargin, 0.3); 
        sheet.setMargin(Sheet.RightMargin, 0.3); 
        sheet.setMargin(Sheet.TopMargin, 0.5); 
        sheet.setMargin(Sheet.BottomMargin, 0.5); 
        
        //Print Setup
        PrintSetup printSetup =  sheet.getPrintSetup();
        printSetup.setLandscape(true);
        
        //Merged Regions
        sheet.addMergedRegion(new CellRangeAddress(0,0, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(0,0, 2, 5));
        sheet.addMergedRegion(new CellRangeAddress(1,1, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(2,2, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(0,0, 10, 11));
        sheet.addMergedRegion(new CellRangeAddress(3,3, 0, 12));
        
        //Column width
        sheet.setColumnWidth(0, 2800);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 2000);
        sheet.setColumnWidth(4, 2500);
        sheet.setColumnWidth(5, 3200);
        sheet.setColumnWidth(6, 3200);
        sheet.setColumnWidth(7, 2500);
        sheet.setColumnWidth(8, 3200);
        sheet.setColumnWidth(9, 3200);
        sheet.setColumnWidth(10, 2500);
        sheet.setColumnWidth(11, 3200);
        sheet.setColumnWidth(12, 3700);
        
        //Cell styles
        CellStyle leftNoBorder = createLeftNoBorderStyle(workbook);
        CellStyle rightNoBorder = createRightNoBorderStyle(workbook);
        CellStyle centerNoBorder = createCenterNoBorderStyle(workbook);
        CellStyle center = createCenterStyle(workbook);
        CellStyle centeredCurrency = createCenterCurrencyStyle(workbook);
        CellStyle centeredBotBorderCurrency = createCenterBotBorderCurrencyStyle(workbook);
        
        int startingRow = 0;
        
        String roadSection = regularActivity.isIsOtherRoadSection() ? regularActivity.getOtherRoadSection() : regularActivity.getRoadSection().getName();
        String subActivity = regularActivity.getSubActivity().getId() != 0 ? " ( " + regularActivity.getSubActivity().getDescription() + " ) " : "";

        addInfoRow(startingRow++, sheet, leftNoBorder, "Name of Road Section:", roadSection, "Month/Year", regularActivity.getDate());
        addInfoRow(startingRow++, sheet, leftNoBorder, "Location:", regularActivity.getLocation().getLocation() + ", South Cotabato", "", "");
        addInfoRow(startingRow++, sheet, leftNoBorder, "Days of Operation:", regularActivity.getNumberOfCD() + " CD", "", "");
        addActivityTitle(startingRow++, sheet, centerNoBorder, "Activity " + regularActivity.getActivity().getItemNumber() + " - " + regularActivity.getActivity().getDescription() + subActivity);
        addOpsEquipmentColumnHeader(startingRow++, sheet, center);
        
        Double totalEquipmentWages = 0.0, totalEquipmentFuel = 0.0, totalEquipmentLubricant = 0.0, opsEquipmentTotalExpenses = 0.0;
        
        OpsEquipmentList opsEquipmentList = new ActivityListDBController()
                        .getRegularActivityOpsEquipmentList(regularActivity.getOpsEquipmentListID());
        
        for(OpsEquipment opsEquipment : opsEquipmentList.toList()){
            addOpsEquipment(startingRow++, sheet, new CellStyle[]{center, centeredCurrency}, opsEquipment);
            totalEquipmentWages += opsEquipment.getTotalWages();
            totalEquipmentFuel += opsEquipment.getFuelAmount();
            totalEquipmentLubricant += opsEquipment.getLubricantAmount();
        }
        
        opsEquipmentTotalExpenses = totalEquipmentWages + totalEquipmentFuel + totalEquipmentLubricant;
        
        addEquipmentSubTotal(startingRow++, sheet, new CellStyle[]{leftNoBorder, centeredBotBorderCurrency}, totalEquipmentWages, totalEquipmentFuel, totalEquipmentLubricant);
        addEquipmentTotalExpenses(startingRow++, sheet, new CellStyle[]{rightNoBorder, centeredBotBorderCurrency}, opsEquipmentTotalExpenses);
        
        //Maintenance Crew
        
        Row bMaintenanceCrew = sheet.createRow(++startingRow);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 0, 1));
        
        Cell bcell1 = bMaintenanceCrew.createCell(0);
        bcell1.setCellValue("B.) Maintenance Crew");
        bcell1.setCellStyle(leftNoBorder);
        
        Row b1MaintenanceCrew = sheet.createRow(++startingRow);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 2));
        
        Cell b1cell2 = b1MaintenanceCrew.createCell(1);
        b1cell2.setCellValue("B.1. Maintenance Crew");
        b1cell2.setCellStyle(leftNoBorder);
        
        //Personnel
        addPersonnelColumnHeader(++startingRow, sheet, center);
        
        Double totalPersonnelWages = 0.0;
        
        CrewPersonnelList crewPersonnelList = new ActivityListDBController()
                        .getRegularActivityOpsCrewPersonnelList(regularActivity.getOpsMaintenanceCrewID());
        
        for(CrewPersonnel crewPersonnel : crewPersonnelList.toList()){
            addPersonnel(++startingRow, sheet, new CellStyle[]{center, centeredCurrency}, crewPersonnel);
            totalPersonnelWages += crewPersonnel.getTotalWages();
        }
        
        addPersonnelSubTotal(++startingRow, sheet, new CellStyle[]{leftNoBorder, centeredBotBorderCurrency}, totalPersonnelWages);
        
        Row b2Materials = sheet.createRow(++startingRow);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 2));
        
        Cell b2cell2 = b2Materials.createCell(1);
        b2cell2.setCellValue("B.2. Materials");
        b2cell2.setCellStyle(leftNoBorder);
        
        //Materials
        addMaterialsColumnHeader(++startingRow, sheet, center);
        
        CrewMaterialsList crewMaterialsList = new ActivityListDBController()
                        .getRegularActivityOpsCrewMaterialsList(regularActivity.getOpsMaintenanceCrewID());
        
        for(CrewMaterials crewMaterials : crewMaterialsList.toList()){
            addMaterials(++startingRow, sheet, new CellStyle[]{center, centeredCurrency}, crewMaterials);
        }
        
        addMaterialsSubTotal(++startingRow, sheet, new CellStyle[]{leftNoBorder, centeredBotBorderCurrency}, 0.0);
        
        Row b3Equipment = sheet.createRow(++startingRow);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 1, 2));
        
        Cell b3cell2 = b3Equipment.createCell(1);
        b3cell2.setCellValue("B.3. Equipment");
        b3cell2.setCellStyle(leftNoBorder);
        
        //Equipment
        addEquipmentColumnHeader(++startingRow, sheet, center);
        
        Double totalCrewEquipmentWages = 0.0, totalCrewEquipmentFuel = 0.0, totalCrewEquipmentLubricant = 0.0, crewEquipmentTotalExpenses = 0.0;
        
        CrewEquipmentList crewEquipmentList = new ActivityListDBController()
                        .getRegularActivityOpsCrewEquipmentList(regularActivity.getOpsMaintenanceCrewID());
        
        for(CrewEquipment crewEquipment : crewEquipmentList.toList()){
            addEquipment(++startingRow, sheet, new CellStyle[]{center, centeredCurrency}, crewEquipment);
            totalCrewEquipmentWages += crewEquipment.getTotalWages();
            totalCrewEquipmentFuel += crewEquipment.getFuelAmount();
            totalCrewEquipmentLubricant += crewEquipment.getLubricantAmount();
        }
        
        crewEquipmentTotalExpenses = totalCrewEquipmentWages + totalCrewEquipmentFuel + totalCrewEquipmentLubricant;
        
        addEquipmentSubTotal(++startingRow, sheet, new CellStyle[]{leftNoBorder, centeredBotBorderCurrency}, totalCrewEquipmentWages, totalCrewEquipmentFuel, totalCrewEquipmentLubricant);
        addEquipmentTotalExpenses(++startingRow, sheet, new CellStyle[]{rightNoBorder, centeredBotBorderCurrency}, crewEquipmentTotalExpenses + totalPersonnelWages);
        
        addGrandTotalExpenses(++startingRow, sheet, new CellStyle[]{rightNoBorder, centeredBotBorderCurrency}, crewEquipmentTotalExpenses + totalPersonnelWages + opsEquipmentTotalExpenses);
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
    
    private void addEquipmentTotalExpenses(int startingRow, XSSFSheet sheet, CellStyle[] style, Double totalExpenses){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 9, 11));
        Row row = sheet.createRow(startingRow);
        
        for(int i = 0; i <= 8; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style[0]);
        }    
        
        Cell cell1 = row.createCell(9);
        cell1.setCellValue("Total Expenses:");
        cell1.setCellStyle(style[0]);
        
        Cell cell2 = row.createCell(10);
        cell2.setCellValue("");
        cell2.setCellStyle(style[0]);
        
        Cell cell4 = row.createCell(11);
        cell4.setCellValue("");
        cell4.setCellStyle(style[0]);
        
        Cell cell3 = row.createCell(12);
        cell3.setCellValue(totalExpenses);
        cell3.setCellStyle(style[1]);
    }
    
    private void addGrandTotalExpenses(int startingRow, XSSFSheet sheet, CellStyle[] style, Double totalExpenses){
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 9, 11));
        Row row = sheet.createRow(startingRow);
        
        for(int i = 0; i <= 9; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style[0]);
        }    
        
        Cell cell1 = row.createCell(9);
        cell1.setCellValue("Grand Total Expenses:");
        cell1.setCellStyle(style[0]);
        
        Cell cell2 = row.createCell(10);
        cell2.setCellValue("");
        cell2.setCellStyle(style[0]);
        
        Cell cell4 = row.createCell(11);
        cell4.setCellValue("");
        cell4.setCellStyle(style[0]);
        
        Cell cell3 = row.createCell(12);
        cell3.setCellValue(totalExpenses);
        cell3.setCellStyle(style[1]);
    }
    
    private void addEquipmentSubTotal(int startingRow, XSSFSheet sheet, CellStyle[] style, 
            Double wagesSubTotal, Double fuelSubTotal, Double lubricantSubTotal){
        Row row = sheet.createRow(startingRow);
        
        for(int i = 0; i <= 3; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style[0]);
        }
        
        Cell cell1 = row.createCell(4);
        cell1.setCellValue("Sub-total:");
        cell1.setCellStyle(style[0]);
        
        Cell cell2 = row.createCell(5);
        cell2.setCellValue(wagesSubTotal);
        cell2.setCellStyle(style[1]);
        
        Cell cell3 = row.createCell(6);
        cell3.setCellValue("");
        cell3.setCellStyle(style[0]);
        
        Cell cell4 = row.createCell(7);
        cell4.setCellValue("Sub-total:");
        cell4.setCellStyle(style[0]);
        
        Cell cell5 = row.createCell(8);
        cell5.setCellValue(fuelSubTotal);
        cell5.setCellStyle(style[1]);
        
        Cell cell6 = row.createCell(9);
        cell6.setCellValue("");
        cell6.setCellStyle(style[0]);
        
        Cell cell7 = row.createCell(10);
        cell7.setCellValue("Sub-total:");
        cell7.setCellStyle(style[0]);
        
        Cell cell8 = row.createCell(11);
        cell8.setCellValue(lubricantSubTotal);
        cell8.setCellStyle(style[1]);
    }
    
    private void addPersonnelSubTotal(int startingRow, XSSFSheet sheet, CellStyle[] style, 
            Double wagesSubTotal){
        Row row = sheet.createRow(startingRow);
        
        for(int i = 0; i <= 3; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style[0]);
        }
        
        Cell cell1 = row.createCell(4);
        cell1.setCellValue("Sub-total");
        cell1.setCellStyle(style[0]);
        
        Cell cell2 = row.createCell(5);
        cell2.setCellValue(wagesSubTotal);
        cell2.setCellStyle(style[1]);
    }
    
    private void addMaterialsSubTotal(int startingRow, XSSFSheet sheet, CellStyle[] style, 
            Double materialsSubTotal){
        Row row = sheet.createRow(startingRow);
        
        for(int i = 0; i <= 4; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style[0]);
        }
        
        Cell cell1 = row.createCell(5);
        cell1.setCellValue("Sub-total");
        cell1.setCellStyle(style[0]);
        
        Cell cell2 = row.createCell(6);
        cell2.setCellValue(materialsSubTotal);
        cell2.setCellStyle(style[1]);
    }
    
    private void addOpsEquipment(int startingRow, XSSFSheet sheet, CellStyle[] style, OpsEquipment opsEquipment){
        String type = opsEquipment.getPersonnel()
                    .getType()
                    .equalsIgnoreCase("Operator") ? opsEquipment.getEquipment().getType() : opsEquipment.getPersonnel().getType();
        
        Row row = sheet.createRow(startingRow);
        
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(opsEquipment.getEquipment().getEquipmentNumber());
        cell1.setCellStyle(style[0]);
        
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(type);
        cell2.setCellStyle(style[0]);
        
        Cell cell3 = row.createCell(2);
        cell3.setCellValue(opsEquipment.getPersonnel().getName());
        cell3.setCellStyle(style[0]);
        
        Cell cell4 = row.createCell(3);
        cell4.setCellValue(opsEquipment.getNumberOfCd());
        cell4.setCellStyle(style[0]);  
        
        Cell cell5 = row.createCell(4);
        cell5.setCellValue(opsEquipment.getRatePerDay());
        cell5.setCellStyle(style[1]);  
        
        Cell cell6 = row.createCell(5);
        cell6.setCellValue(opsEquipment.getTotalWages());
        cell6.setCellStyle(style[1]);  
        
        Cell cell7 = row.createCell(6);
        cell7.setCellValue(opsEquipment.getFuelConsumption());
        cell7.setCellStyle(style[0]);  
        
        Cell cell8 = row.createCell(7);
        cell8.setCellValue(opsEquipment.getFuelCost());
        cell8.setCellStyle(style[1]);
        
        Cell cell9 = row.createCell(8);
        cell9.setCellValue(opsEquipment.getFuelAmount());
        cell9.setCellStyle(style[1]);  
        
        Cell cell10 = row.createCell(9);
        cell10.setCellValue("");
        cell10.setCellStyle(style[1]);  
        
        Cell cell11 = row.createCell(10);
        cell11.setCellValue("");
        cell11.setCellStyle(style[1]);  
        
        Cell cell12 = row.createCell(11);
        cell12.setCellValue(opsEquipment.getLubricantAmount());
        cell12.setCellStyle(style[1]);          
        
        Cell cell13 = row.createCell(12);
        cell13.setCellValue(opsEquipment.getTotalCost());
        cell13.setCellStyle(style[1]);          
    }
    
    private void addEquipment(int startingRow, XSSFSheet sheet, CellStyle[] style, CrewEquipment crewEquipment){      
        Row row = sheet.createRow(startingRow);
        
        Cell cell3 = row.createCell(2);
        cell3.setCellValue(crewEquipment.getEquipment().getEquipmentNumber());
        cell3.setCellStyle(style[0]);
        
        Cell cell4 = row.createCell(3);
        cell4.setCellValue(crewEquipment.getNumberOfCd());
        cell4.setCellStyle(style[0]);  
        
        Cell cell5 = row.createCell(4);
        cell5.setCellValue(crewEquipment.getRatePerDay());
        cell5.setCellStyle(style[1]);  
        
        Cell cell6 = row.createCell(5);
        cell6.setCellValue(crewEquipment.getTotalWages());
        cell6.setCellStyle(style[1]);  
        
        Cell cell7 = row.createCell(6);
        cell7.setCellValue(crewEquipment.getFuelConsumption());
        cell7.setCellStyle(style[0]);  
        
        Cell cell8 = row.createCell(7);
        cell8.setCellValue(crewEquipment.getFuelCost());
        cell8.setCellStyle(style[1]);
        
        Cell cell9 = row.createCell(8);
        cell9.setCellValue(crewEquipment.getFuelAmount());
        cell9.setCellStyle(style[1]);  
        
        Cell cell10 = row.createCell(9);
        cell10.setCellValue("");
        cell10.setCellStyle(style[1]);  
        
        Cell cell11 = row.createCell(10);
        cell11.setCellValue("");
        cell11.setCellStyle(style[1]);  
        
        Cell cell12 = row.createCell(11);
        cell12.setCellValue(crewEquipment.getLubricantAmount());
        cell12.setCellStyle(style[1]);          
        
        Cell cell13 = row.createCell(12);
        cell13.setCellValue(crewEquipment.getTotalCost());
        cell13.setCellStyle(style[1]);          
    }
    
    private void addPersonnel(int startingRow, XSSFSheet sheet, CellStyle[] style, CrewPersonnel crewPersonnel){
        Row row = sheet.createRow(startingRow);
              
        Cell cell1 = row.createCell(2);
        cell1.setCellValue(crewPersonnel.getPersonnel().getName());
        cell1.setCellStyle(style[0]);
              
        Cell cell2 = row.createCell(3);
        cell2.setCellValue(crewPersonnel.getNumberOfCd());
        cell2.setCellStyle(style[0]);
              
        Cell cell3 = row.createCell(4);
        cell3.setCellValue(crewPersonnel.getRatePerDay());
        cell3.setCellStyle(style[1]);
              
        Cell cell4 = row.createCell(5);
        cell4.setCellValue(crewPersonnel.getTotalWages());
        cell4.setCellStyle(style[1]);
    }
    
    private void addMaterials(int startingRow, XSSFSheet sheet, CellStyle[] style, CrewMaterials crewEquipment){
        Row row = sheet.createRow(startingRow);
              
        Cell cell1 = row.createCell(2);
        cell1.setCellValue(crewEquipment.getDescription());
        cell1.setCellStyle(style[0]);
              
        Cell cell2 = row.createCell(3);
        cell2.setCellValue(crewEquipment.getQuantity());
        cell2.setCellStyle(style[0]);
              
        Cell cell3 = row.createCell(4);
        cell3.setCellValue(crewEquipment.getUnit());
        cell3.setCellStyle(style[0]);
              
        Cell cell4 = row.createCell(5);
        cell4.setCellValue("");
        cell4.setCellStyle(style[1]);
              
        Cell cell5 = row.createCell(6);
        cell5.setCellValue("");
        cell5.setCellStyle(style[1]);
    }
    
    private void addActivityTitle(int startingRow, XSSFSheet sheet, CellStyle style, String value){
        Row row = sheet.createRow(startingRow);
        
        Cell activityTitle = row.createCell(0);
        activityTitle.setCellValue(value);
        activityTitle.setCellStyle(style);
    }
    
    private void addOpsEquipmentColumnHeader(int startingRow, XSSFSheet sheet, CellStyle style){
        Row row = sheet.createRow(startingRow);
        
        String[] columnHeaders = {"Equipment no.", "Type", "Name of Operator", "No. of CD", "Rate per day", "Total Wages", 
                                "Fuel Consumption(L)", "Cost/L", "Amount", "Lubricants/Oil", "Cost", "Amount", "Total Cost"};
        
        for(int i = 0; i < columnHeaders.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(style);
        }
    }
    
    private void addEquipmentColumnHeader(int startingRow, XSSFSheet sheet, CellStyle style){
        Row row = sheet.createRow(startingRow);
        
        String[] columnHeaders = {"Name of Equipment", "No. of CD", "Rate per day", "Total Wages", 
                                "Fuel Consumption(L)", "Cost/L", "Amount", "Lubricants/Oil", "Cost", "Amount", "Total Cost"};
        
        for(int i = 0; i < columnHeaders.length; i++){
            Cell cell = row.createCell(i + 2);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(style);
        }
    }
    
    private void addPersonnelColumnHeader(int startingRow, XSSFSheet sheet, CellStyle style){
        Row row = sheet.createRow(startingRow);
        
        String[] columnHeaders = {"Name of Personnel", "No. of CD", "Rate per day", "Total Wages"};
        
        for(int i = 0; i < columnHeaders.length; i++){
            Cell cell = row.createCell(i + 2);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(style);
        }
    }
    
    private void addMaterialsColumnHeader(int startingRow, XSSFSheet sheet, CellStyle style){
        Row row = sheet.createRow(startingRow);
        
        String[] columnHeaders = {"Description", "Quantity", "Unit", "Unit Cost", "Amount"};
        
        for(int i = 0; i < columnHeaders.length; i++){
            Cell cell = row.createCell(i + 2);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(style);
        }
    }
    
    private void addInfoRow(int startingRow, XSSFSheet sheet, CellStyle style, String leftLabel, String leftValue, String rightLabel, String rightValue){
        Row row = sheet.createRow(startingRow);
        
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(leftLabel);
        cell1.setCellStyle(style);
        
        Cell cell6 = row.createCell(1);
        cell6.setCellValue("");
        cell6.setCellStyle(style);
        
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(leftValue);
        cell2.setCellStyle(style);
        
        for(int i = 4; i <= 9; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
        }
        
        Cell cell3 = row.createCell(10);
        cell3.setCellValue(rightLabel);
        cell3.setCellStyle(style);
        
        Cell cell4 = row.createCell(11);
        cell4.setCellValue("");
        cell4.setCellStyle(style);
        
        Cell cell5 = row.createCell(12);
        cell5.setCellValue(rightValue);
        cell5.setCellStyle(style);
    }
    
    public static CellStyle createCenterNoBorderStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    public static CellStyle createCenterStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    private static CellStyle createLeftNoBorderStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        
        return style;
    }
    
    private static CellStyle createRightNoBorderStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
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
    
    public static CellStyle createCenterCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
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
    
    public static CellStyle createCenterBotBorderCurrencyStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
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
