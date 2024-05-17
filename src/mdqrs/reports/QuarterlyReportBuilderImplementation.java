/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.reports;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Vienji
 */
public class QuarterlyReportBuilderImplementation implements QuarterlyReportBuilder {
    QuarterlyReport report;
    
    public QuarterlyReportBuilderImplementation(){
        this.report = new QuarterlyReport();
    }
    
    @Override
    public QuarterlyReportBuilder setHeaderTitle(String headerTitle) {
        report.setHeaderTitle(headerTitle);
        return this;
    }

    @Override
    public QuarterlyReportBuilder setTimeFrameDetail(Object timeFrame, int year) {
        report.setTimeFrameDetail(timeFrame, year);
        return this;
    }

    @Override
    public QuarterlyReportBuilder setOrganizationHeads(ArrayList<String> organizationHeads) {
        report.setOrganizationHeads(organizationHeads);
        return this;
    }

    @Override
    public QuarterlyReportBuilder setFilePath(String filePath, File file) {
        report.setFilePath(filePath, file);
        return this;
    }

    @Override
    public QuarterlyReport build() {
        return report;
    }
    
}
