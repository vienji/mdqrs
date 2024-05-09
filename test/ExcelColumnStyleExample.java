/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Vienji
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelColumnStyleExample {
    public static void main(String[] args) {
        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create a cell style for the column headers
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true); // Make the font bold
            headerStyle.setFont(headerFont);
            headerFont.setFontHeightInPoints((short) 8);
            headerStyle.setAlignment(HorizontalAlignment.CENTER); // Center align the text
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center align vertically
            headerStyle.setBorderBottom(BorderStyle.THIN); // Add a thin border at the bottom
            headerStyle.setBorderLeft(BorderStyle.THIN); // Add a thin border at the left
            headerStyle.setBorderRight(BorderStyle.THIN); // Add a thin border at the right
            headerStyle.setBorderTop(BorderStyle.THIN); // Add a thin border at the top

            // Create the row for column titles
            Row titleRow = sheet.createRow(0);

            // Column titles
            String[] columnTitles = {
                    "ROAD MAINTENANCE ACTIVITIES",
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
                    "MODE OF IMPLEMENTATION"
            };

            // Create and style the cells for column titles
            for (int i = 0; i < columnTitles.length; i++) {
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnTitles[i]);
                cell.setCellStyle(headerStyle);
            }

            // Auto-size columns
            for (int i = 0; i < columnTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Manual adjustment for specific columns based on A4 landscape paper size
            sheet.setColumnWidth(0, 8000); // Adjust width of the first column (merged 1st and 2nd)
            sheet.setColumnWidth(1, 5000); // Adjust width of the second column
            // Adjust width of the rest of the columns as needed
            for (int i = 2; i < columnTitles.length; i++) {
                sheet.setColumnWidth(i, 3000);
            }

            // Write the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\userpc\\Desktop\\workbook.xlsx")) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

