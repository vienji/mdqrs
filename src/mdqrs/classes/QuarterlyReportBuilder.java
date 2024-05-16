/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mdqrs.classes;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Vienji
 */
public interface QuarterlyReportBuilder {
    QuarterlyReportBuilder setHeaderTitle(String headerTitle);
    QuarterlyReportBuilder setTimeFrameDetail(Object timeFrame, int year);
    QuarterlyReportBuilder setOrganizationHeads(ArrayList<String> organizationHeads);
    QuarterlyReportBuilder setFilePath(String filePath, File file);
    QuarterlyReport build();
}
