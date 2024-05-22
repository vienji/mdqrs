/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.reports;

import classes.OtherActivity;
import classes.RegularActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;
import mdqrs.classes.DriversForEngineers;
import mdqrs.classes.JarDirectory;
import mdqrs.classes.OtherExpenses;
import mdqrs.classes.Program;
import mdqrs.classes.Project;
import mdqrs.dbcontroller.GeneralDBController;
import mdqrs.dbcontroller.OtherExpensesDBController;
import mdqrs.dbcontroller.ProgramDBController;
import mdqrs.view.Main;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFHeaderFooterProperties;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Vienji
 */
public class QuarterlyReport implements Report {
    private static final String xlsx = "xlsx";
    private File file;
    private String headerTitle;
    private String timeFrame;
    private String filePath;
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
    public void setFilePath(String filePath, File file) {
        this.filePath = filePath;
        this.file = file;
    }

    @Override
    public void generateReport() {
        Workbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet();
        
        //Header
        Header header = sheet.getHeader();
        header.setLeft(HSSFHeader.fontSize((short) 10) + "Consolidated Road Maintenance Accomplishment Report\nFY " + year);
        
        XSSFHeaderFooterProperties prop = sheet.getHeaderFooterProperties();
        prop.setAlignWithMargins(true);
        
        //Cell Styles
        CellStyle columnHeaderStyle = createColumnHeaderStyle(workbook);
        CellStyle column8HeaderStyle = create8ColumnHeaderStyle(workbook);
        CellStyle center = createCenteredStyle(workbook);
        CellStyle left = createLeftStyle(workbook);
        CellStyle right = createRightStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);
        
        //Print Setup
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        
        //Paper Layout
        sheet.getPrintSetup().setPaperSize(PrintSetup.LEGAL_PAPERSIZE);
        sheet.setMargin(Sheet.LeftMargin, 0.3); 
        sheet.setMargin(Sheet.RightMargin, 0.3); 
        sheet.setMargin(Sheet.TopMargin, 1.2); 
        sheet.setMargin(Sheet.BottomMargin, 0.5); 
        
        //Merged Regions
        
        //Column Width
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4800);
        sheet.setColumnWidth(3, 4800);
        sheet.setColumnWidth(4, 1400);
        sheet.setColumnWidth(5, 2500);
        sheet.setColumnWidth(6, 3400);
        sheet.setColumnWidth(7, 1800);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 1800);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 1800);
        sheet.setColumnWidth(12, 3000);
        sheet.setColumnWidth(13, 1800);
        sheet.setColumnWidth(14, 3000);
        
        int startingRow = 0;
                
        Double totalLengthOfRoads = 0.0, totalFairToGoodRoads = 0.0, totalBudget = 0.0, firstQuarter = 0.0, secondQuarter = 0.0, thirdQuarter = 0.0, fourthQuarter = 0.0;
        
        File jarDir = null;
        
        try{
            jarDir = JarDirectory.getJarDir(Main.class);
        } catch (URISyntaxException | IOException e){}
        
        File parentDir = jarDir.getParentFile();
        
        final String REPORT_FILE_2 = "src/mdqrs/path/to/quarterly_report_config.properties";
        File file2 = new File(parentDir,REPORT_FILE_2);
        
        try (FileInputStream input = new FileInputStream(file2)) {
            Properties report = new Properties();
            report.load(input);

            totalLengthOfRoads = Double.parseDouble(report.getProperty("total_provincial_roads"));
            totalFairToGoodRoads = Double.parseDouble(report.getProperty("total_provincial_roads_in_fair_to_good"));
            totalBudget = Double.parseDouble(report.getProperty("total_budget"));

        } catch (IOException io) {
            io.printStackTrace();
        }
        
        firstQuarter = totalQuarterExpenses("1st Quarter", year);
        secondQuarter = totalQuarterExpenses("2nd Quarter", year);
        thirdQuarter = totalQuarterExpenses("3rd Quarter", year);
        fourthQuarter = totalQuarterExpenses("4th Quarter", year);
        
        addTitleReportHeader(startingRow++, sheet, center, year);
        addQuarterColumnHeader(startingRow++, sheet, center);
        addColumnHeader(startingRow++, sheet, new CellStyle[]{columnHeaderStyle, column8HeaderStyle});
        addContent(startingRow++, sheet, new CellStyle[]{center, left, right, currencyStyle}, totalLengthOfRoads, totalFairToGoodRoads, totalBudget, firstQuarter, secondQuarter, thirdQuarter, fourthQuarter);
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
    
    private static void addBlankRow(int startingRow, XSSFSheet sheet){
        Row row = sheet.createRow(startingRow);

        for(int i = 1; i <= 10; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue("");
        }
    }
    
    private Double totalQuarterExpenses(String quarter, int year){
        Double prevGrandTotal = 0.0;
        
        GeneralDBController gdbc = new GeneralDBController();
        
        ArrayList<RegularActivity> raList;
            ArrayList<OtherActivity> oaList;
            ArrayList<DriversForEngineers> dfeList;
            ArrayList<OtherExpenses> oeList;

            switch(quarter){
                case "1st Quarter":
                    Double laborCrew = 0.0, laborEquipment = 0.0, equipmentFuel = 0.0, lubricant = 0.0;
                    String[] firstQuarterMonths = {"January", "February", "March"};
                    
                    for(int i = 0; i < 3; i++){
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

                    for(int i = 0; i < 3; i++){
                        raList = gdbc.getMonthlyRegularActivityList(secondQuarterMonths[i], year);
                        oaList = gdbc.getMonthlyOtherActivityList(secondQuarterMonths[i], year);
                        dfeList = gdbc.getMonthlyDriversForEngineersList(secondQuarterMonths[i],year);
                        oeList = gdbc.getMonthlyOtherExpensesList(secondQuarterMonths[i],year);

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
                    
                    for(int i = 0; i < 3; i++){
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

                    for(int i = 0; i < 3; i++){
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
        
        return prevGrandTotal;
    }
    
    public InputStream getConfig(String network_file_in){
        return getClass().getResourceAsStream(network_file_in);
    }
    
    private static void addNotes(int startingRow, XSSFSheet sheet, Workbook workbook){
        String preparedBy1Name = "", preparedBy1Position = "", preparedBy2Name = "", preparedBy2Position = "", 
                submittedByName = "", submittedByPosition = "", approvedByName = "", approvedByPosition = "";
        
        File jarDir = null;
        
        try{
            jarDir = JarDirectory.getJarDir(Main.class);
        } catch (URISyntaxException | IOException e){}
        
        File parentDir = jarDir.getParentFile();
        
        final String REPORT_FILE_1 = "src/mdqrs/path/to/report_config.properties";
        File file1 = new File(parentDir, REPORT_FILE_1);
        
        try (FileInputStream input = new FileInputStream(file1)) {
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
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 6, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow, startingRow, 11, 14));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 6, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 3, startingRow + 3, 11, 14));
        
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 6, 10));
        sheet.addMergedRegion(new CellRangeAddress(startingRow + 4, startingRow + 4, 11, 14));
        
        Row row1 = sheet.createRow(startingRow);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        
        font1.setFontHeightInPoints((short) 10);
        style1.setFont(font1);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setAlignment(HorizontalAlignment.LEFT);
        
        Cell tag = row1.createCell(0);
        tag.setCellValue("Prepared By: ");
        tag.setCellStyle(style1);
        
        for(int i = 1; i <= 5; i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style1);
        }
        
        tag = row1.createCell(6);
        tag.setCellValue("Submitted By:");
        tag.setCellStyle(style1);
        
        for(int i = 7; i <= 10; i++){
            Cell cell = row1.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style1);
        }
        
        tag = row1.createCell(11);
        tag.setCellValue("Approved By:");
        tag.setCellStyle(style1);
        
        for(int i = 12; i <= 14; i++){
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
        
        Cell name = row2.createCell(0);
        name.setCellValue(preparedBy1Name);
        name.setCellStyle(style2);
        
        for(int i = 1; i <= 2; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        name = row2.createCell(3);
        name.setCellValue(preparedBy2Name);
        name.setCellStyle(style2);
        
        for(int i = 4; i <= 5; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        name = row2.createCell(6);
        name.setCellValue(submittedByName);
        name.setCellStyle(style2);
        
        for(int i = 7; i <= 10; i++){
            Cell cell = row2.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style2);
        }
        
        name = row2.createCell(11);
        name.setCellValue(approvedByName);
        name.setCellStyle(style2);
        
        for(int i = 12; i <= 14; i++){
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
        
        Cell position = row3.createCell(0);
        position.setCellValue(preparedBy1Position);
        position.setCellStyle(style3);
        
        for(int i = 1; i <= 2; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
        
        position = row3.createCell(3);
        position.setCellValue(preparedBy2Position);
        position.setCellStyle(style3);
        
        for(int i = 4; i <= 5; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
        
        position = row3.createCell(6);
        position.setCellValue(submittedByPosition);
        position.setCellStyle(style3);
        
        for(int i = 7; i <= 10; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
        
        position = row3.createCell(11);
        position.setCellValue(approvedByPosition);
        position.setCellStyle(style3);
        
        for(int i = 12; i <= 14; i++){
            Cell cell = row3.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(style3);
        }
    }
    
    private String setDecimalFormat(Double number){
        DecimalFormat decimal = new DecimalFormat("#.##");
        return decimal.format(number);
    }
    
    private void addContent(int startingRow, XSSFSheet sheet, CellStyle[] style, Double totalLengthOfRoads, Double totalFairToGoodRoads, Double totalBudget, 
            Double firstQuarter, Double secondQuarter, Double thirdQuarter, Double fourthQuarter){
        Row row = sheet.createRow(startingRow);
        
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("XII");
        cell0.setCellStyle(style[0]);
        
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("South Cotabato");
        cell1.setCellStyle(style[1]);
        
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(totalLengthOfRoads);
        cell2.setCellStyle(style[2]);
        
        Cell cell3 = row.createCell(3);
        cell3.setCellValue(totalFairToGoodRoads);
        cell3.setCellStyle(style[2]);
        
        Cell cell4 = row.createCell(4);
        cell4.setCellValue("");
        cell4.setCellStyle(style[2]);
        
        Cell cell5 = row.createCell(5);
        cell5.setCellValue("");
        cell5.setCellStyle(style[2]);
        
        Cell cell6 = row.createCell(6);
        cell6.setCellValue(totalBudget);
        cell6.setCellStyle(style[3]);
        
        Cell cell7 = row.createCell(7);
        Double firstPer = (firstQuarter / totalBudget) * 100;
        cell7.setCellValue( setDecimalFormat(firstPer) + "%");
        cell7.setCellStyle(style[0]);
        
        Cell cell8 = row.createCell(8);
        cell8.setCellValue(firstQuarter);
        cell8.setCellStyle(style[3]);
        
        Cell cell9 = row.createCell(9);
        Double secondPer = (secondQuarter / totalBudget) * 100;
        cell9.setCellValue(setDecimalFormat(secondPer) + "%");
        cell9.setCellStyle(style[0]);
        
        Cell cell10 = row.createCell(10);
        cell10.setCellValue(secondQuarter);
        cell10.setCellStyle(style[3]);
        
        Cell cell11 = row.createCell(11);
        Double thirdPer = (thirdQuarter / totalBudget) * 100;
        cell11.setCellValue(setDecimalFormat(thirdPer) + "%");
        cell11.setCellStyle(style[0]);
        
        Cell cell12 = row.createCell(12);
        cell12.setCellValue(thirdQuarter);
        cell12.setCellStyle(style[3]);
                
        Cell cell13 = row.createCell(13);
        Double fourthPer = (fourthQuarter / totalBudget) * 100;
        cell13.setCellValue(setDecimalFormat(fourthPer) + "%");
        cell13.setCellStyle(style[0]);
        
        Cell cell14 = row.createCell(14);
        cell14.setCellValue(fourthQuarter);
        cell14.setCellStyle(style[3]);
    }
    
    private void addColumnHeader(int startingRow, XSSFSheet sheet, CellStyle[] style){
        String[] headerTitle = {
            "Region",
            "Province",
            "Total Length of Provincial Roads (Km)",
            "Total Length of Provincial Roads in Fair-to-Good Condition (Km)",
            "% of Total",
            "Total Length of Programmed for Maintenance",
            "Total Budget (Php)",
            "Physical",
            "Financial",
            "Physical",
            "Financial",
            "Physical",
            "Financial",
            "Physical",
            "Financial"
        };
        
        Row row = sheet.createRow(startingRow);
        
         for(int i = 0; i < headerTitle.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(headerTitle[i]);
            if(i == 5){
                cell.setCellStyle(style[1]);
            } else {
                cell.setCellStyle(style[0]);  
            }
        }
    }
    
    private void addQuarterColumnHeader(int startingRow, XSSFSheet sheet, CellStyle style){
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 10));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 12));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 13, 14));
        Row row = sheet.createRow(startingRow);
        Cell blankCell;
        
        for(int i = 0; i <= 6; i++){
            blankCell = row.createCell(i);
            blankCell.setCellValue(""); 
        }  
        
        Cell cell1 = row.createCell(7);
        cell1.setCellValue("1st Quarter");
        cell1.setCellStyle(style);
        
        blankCell = row.createCell(8);
        blankCell.setCellValue(""); 
        blankCell.setCellStyle(style); 
        
        Cell cell2 = row.createCell(9);
        cell2.setCellValue("2nd Quarter");
        cell2.setCellStyle(style);
        
        blankCell = row.createCell(10);
        blankCell.setCellValue(""); 
        blankCell.setCellStyle(style);
        
        Cell cell3 = row.createCell(11);
        cell3.setCellValue("3rd Quarter");
        cell3.setCellStyle(style);
        
        blankCell = row.createCell(12);
        blankCell.setCellValue(""); 
        blankCell.setCellStyle(style);
        
        Cell cell4 = row.createCell(13);
        cell4.setCellValue("4th Quarter");
        cell4.setCellStyle(style);
        
        blankCell = row.createCell(14);
        blankCell.setCellValue(""); 
        blankCell.setCellStyle(style);
    }
    
    private void addTitleReportHeader(int startingRow, XSSFSheet sheet, CellStyle style, int year){
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 14));
        Row row = sheet.createRow(startingRow);
        
        for(int i = 0; i <= 6; i++){
            Cell blankCell = row.createCell(i);
            blankCell.setCellValue(""); 
        }  
        
        Cell cell = row.createCell(7);
        cell.setCellValue("FY " + year + " Quarterly Accomplishment Report");
        cell.setCellStyle(style);
        
        for(int i = 8; i <= 14; i++){
            Cell blankCell = row.createCell(i);
            
            blankCell.setCellValue("");
            blankCell.setCellStyle(style);  
        }  
    }
    
    public static CellStyle create8ColumnHeaderStyle(Workbook workbook){
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
    
    public static CellStyle createColumnHeaderStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
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
    
    public static CellStyle createCenteredStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
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
    
    public static CellStyle createLeftStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
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
    
    public static CellStyle createRightStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontHeightInPoints((short) 10);
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
        
        font.setFontHeightInPoints((short) 10);
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
