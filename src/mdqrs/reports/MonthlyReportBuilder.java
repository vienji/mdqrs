/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mdqrs.reports;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Vienji
 */
public interface MonthlyReportBuilder {
    MonthlyReportBuilder setHeaderTitle(String headerTitle);
    MonthlyReportBuilder setTimeFrameDetail(Object timeFrame, int year);
    MonthlyReportBuilder setOrganizationHeads(ArrayList<String> organizationHeads);
    MonthlyReportBuilder setFilePath(String filePath, File file);
    MonthlyReport build();
}
