/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.reports;

import classes.CrewPersonnel;
import classes.CrewPersonnelList;
import classes.OtherActivity;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import mdqrs.dbcontroller.OtherActivityListDBController;
import static mdqrs.reports.RegularActivityReport.createCenterBotBorderCurrencyStyle;
import static mdqrs.reports.RegularActivityReport.createCenterCurrencyStyle;
import static mdqrs.reports.RegularActivityReport.createCenterNoBorderStyle;
import static mdqrs.reports.RegularActivityReport.createCenterStyle;
import static mdqrs.reports.RegularActivityReport.getExtension;
import static mdqrs.reports.RegularActivityReport.validateFilePath;
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
public class OtherActivityReport {
    private static final String xlsx = "xlsx";
    private OtherActivity otherActivity;
    private String filePath;
    private File file;
    
    public OtherActivityReport(){

    }
    
    public OtherActivityReport(OtherActivity otherActivity){
        this.otherActivity = otherActivity;
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
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
        sheet.addMergedRegion(new CellRangeAddress(1,1,2,9));
        sheet.addMergedRegion(new CellRangeAddress(2,2,0,1));       
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,9));
        sheet.addMergedRegion(new CellRangeAddress(0,0,10,11));
        
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
        
        addInfoRow(startingRow++, sheet, leftNoBorder, "Activity:", otherActivity.getSubActivity().getActivity().getItemNumber() + " - " + otherActivity.getSubActivity().getActivity().getDescription() + " (" + otherActivity.getSubActivity().getDescription() + ")", "Month/Year", otherActivity.getDate());
        addInfoRow(startingRow++, sheet, leftNoBorder, "Description:", otherActivity.getDescription(), "", "");
        addInfoRow(startingRow++, sheet, leftNoBorder, "Days of Operation:", otherActivity.getNumberOfCD() + " CD", "", "");
        addBlankRow(startingRow++, sheet);
        addBlankRow(startingRow++, sheet);
        
        addPersonnelColumnHeader(startingRow++, sheet, center);
        
        Double totalPersonnelWages = 0.0;
        
        CrewPersonnelList crewPersonnelList = new OtherActivityListDBController().getOtherActivityOpsCrewPersonnelList(otherActivity.getId());
        
        for(CrewPersonnel crewPersonnel : crewPersonnelList.toList()){
            addPersonnel(startingRow++, sheet, new CellStyle[]{center, centeredCurrency}, crewPersonnel);
            totalPersonnelWages += crewPersonnel.getTotalWages();
        }
        
        addPersonnelSubTotal(startingRow++, sheet, new CellStyle[]{leftNoBorder, centeredBotBorderCurrency}, totalPersonnelWages);
        
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
    
    private void addPersonnelColumnHeader(int startingRow, XSSFSheet sheet, CellStyle style){
        Row row = sheet.createRow(startingRow);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 0, 2));
        String[] columnHeaders = {"Name of Personnel", "No. of CD", "Rate per day", "Total Wages"};
        
        Cell nameCell = row.createCell(0);
        nameCell.setCellValue(columnHeaders[0]);
        nameCell.setCellStyle(style);
        
        Cell nameCell1 = row.createCell(1);
        nameCell1.setCellValue("");
        nameCell1.setCellStyle(style);
        
        Cell nameCell2 = row.createCell(2);
        nameCell2.setCellValue("");
        nameCell2.setCellStyle(style);
        
        for(int i = 1; i < columnHeaders.length; i++){
            Cell cell = row.createCell(i + 2);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(style);
        }
    }
    
    public String getFileDirectory(){
        String fileName = getExtension(file.getName()).toLowerCase().equals(xlsx) ? file.getName() : file.getName() + "." + xlsx;
        String fileDirectory = validateFilePath(filePath) ;
        
        return fileDirectory + fileName;
    }
    
    public void openReport(){
        String fileName = getExtension(file.getName()).toLowerCase().equals(xlsx) ? file.getName() : file.getName() + "." + xlsx;
        String fileDirectory = validateFilePath(filePath) ;
        File reportFile = new File(fileDirectory + fileName);
                
        try{
            Desktop.getDesktop().open(reportFile);
        } catch (Exception e){
            e.printStackTrace();
        }
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
    
    private void addPersonnel(int startingRow, XSSFSheet sheet, CellStyle[] style, CrewPersonnel crewPersonnel){
        Row row = sheet.createRow(startingRow);
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 0, 2));     
        
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(crewPersonnel.getPersonnel().getName());
        cell1.setCellStyle(style[0]);
        
        Cell cell5 = row.createCell(1);
        cell5.setCellValue("");
        cell5.setCellStyle(style[0]);
        
        Cell cell6 = row.createCell(2);
        cell6.setCellValue("");
        cell6.setCellStyle(style[0]);
              
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
    
    private static void addBlankRow(int startingRow, XSSFSheet sheet){
        Row row = sheet.createRow(startingRow);

        for(int i = 1; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
        }
        
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
