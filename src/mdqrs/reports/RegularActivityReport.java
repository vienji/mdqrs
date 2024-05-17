/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.reports;

import classes.RegularActivity;
import java.io.File;
import java.io.FileOutputStream;
import static mdqrs.reports.MonthlyReport.getExtension;
import static mdqrs.reports.MonthlyReport.validateFilePath;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
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
        
        //Merged Regions
        sheet.addMergedRegion(new CellRangeAddress(0,0, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(1,1, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(2,2, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(0,0, 10, 11));
        
        //Cell styles
        CellStyle left = createLeftNoBorderStyle(workbook);
        
        int startingRow = 0;
        
        String roadSection = regularActivity.isIsOtherRoadSection() ? regularActivity.getOtherRoadSection() : regularActivity.getRoadSection().getName();
        String subActivity = regularActivity.getSubActivity().getId() != 0 ? " ( " + regularActivity.getSubActivity().getDescription() + " ) " : "";

        addInfoRow(startingRow++, sheet, left, "Name of Road Section:", roadSection, "Month/Year", regularActivity.getDate());
        addInfoRow(startingRow++, sheet, left, "Location:", regularActivity.getLocation().getLocation() + ", South Cotabato", "", "");
        addInfoRow(startingRow++, sheet, left, "Days of Operation:", regularActivity.getNumberOfCD() + " CD", "", "");
    
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
