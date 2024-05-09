/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

import java.util.ArrayList;

/**
 *
 * @author Vienji
 */
public class MonthlyReportBuilderImplementation implements MonthlyReportBuilder {
    MonthlyReport report;
    
    public MonthlyReportBuilderImplementation(){
        this.report = new MonthlyReport();
    }
    
    @Override
    public MonthlyReportBuilder setHeaderTitle(String headerTitle) {
        report.setHeaderTitle(headerTitle);
        return this;
    }

    @Override
    public MonthlyReportBuilder setTimeFrameDetail(Object timeFrame, int year) {
        report.setTimeFrameDetail(timeFrame, year);
        return this;
    }

    @Override
    public MonthlyReportBuilder setOrganizationHeads(ArrayList<String> organizationHeads) {
        report.setOrganizationHeads(organizationHeads);
        return this;
    }

    @Override
    public MonthlyReport build() {
        return report;
    } 
}
