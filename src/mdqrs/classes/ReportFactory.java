/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

/**
 *
 * @author Vienji
 */
public class ReportFactory {
    public MonthlyReportBuilder createMonthlyReportBuilder(){
        return new MonthlyReportBuilderImplementation();
    }
    
    public QuarterlyReportBuilder createQuarterlyReportBuilder(){
        return new QuarterlyReportBuilderImplementation();
    }
}
