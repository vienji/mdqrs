
import classes.OtherActivity;
import classes.RegularActivity;
import java.io.File;
import java.util.ArrayList;
import mdqrs.dbcontroller.ActivityListDBController;
import mdqrs.dbcontroller.OtherActivityListDBController;
import mdqrs.reports.MonthlyReport;
import mdqrs.reports.MonthlyReportBuilder;
import mdqrs.reports.OtherActivityReport;
import mdqrs.reports.QuarterlyReport;
import mdqrs.reports.QuarterlyReportBuilder;
import mdqrs.reports.RegularActivityReport;
import mdqrs.reports.Report;
import mdqrs.reports.ReportFactory;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Vienji
 */
public class Test {
    public static void main(String... args){
//        ArrayList<String> list = new ArrayList();
//        
//        MonthlyReportBuilder builder = new ReportFactory().createMonthlyReportBuilder();
//        MonthlyReport report = builder
//                                    .setOrganizationHeads(list)
//                                    .setHeaderTitle("Conditional Matching Grant to Provinces (CMGP)")
//                                    .setTimeFrameDetail("April", 2024)
//                                    .build();
//        
//        report.generateReport();
       
//        QuarterlyReportBuilder quarterlyBuilder = new ReportFactory().createQuarterlyReportBuilder();
//        QuarterlyReport quarterlyReport = quarterlyBuilder
//                                                        .setTimeFrameDetail("2nd Quarter", 2024)
//                                                        .build();
//        
//        quarterlyReport.generateReport();

//          ArrayList<RegularActivity> list = new ActivityListDBController().getList();
//
//          RegularActivity regularActivity = list.get(0);
//          
//          RegularActivityReport regularReport = new RegularActivityReport(regularActivity);
//          
//          File file = new File("sample_data");
//          String filePath = "C:\\Users\\userpc\\Desktop\\";
//          
//          regularReport.setFilePath(filePath, file);
//          
//          regularReport.generateWorkbook("May", 2024);

            ArrayList<OtherActivity> list = new OtherActivityListDBController().getList();
            
            OtherActivity otherActivity = list.get(0);
            
            OtherActivityReport otherActivityReport = new OtherActivityReport(otherActivity);
            
            File file = new File("sample_data");
            String filePath = "C:\\Users\\userpc\\Desktop\\";
            
            otherActivityReport.setFilePath(filePath, file);
            
            otherActivityReport.generateReport();
    }
}
