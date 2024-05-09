
import java.io.File;
import java.util.ArrayList;
import mdqrs.classes.MonthlyReport;
import mdqrs.classes.MonthlyReportBuilder;
import mdqrs.classes.Report;
import mdqrs.classes.ReportFactory;

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
        ArrayList<String> list = new ArrayList();
        
        MonthlyReportBuilder builder = new ReportFactory().createMonthlyReportBuilder();
        MonthlyReport report = builder
                                    .setOrganizationHeads(list)
                                    .setHeaderTitle("Conditional Matching Grant to Provinces (CMGP)")
                                    .setTimeFrameDetail("April", 2024)
                                    .build();
        
        report.generateReport();
    }
}
