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
public interface Report {
    void setHeaderTitle(String headerTitle);
    void setTimeFrameDetail(Object timeFrame, int year);
    void setOrganizationHeads(ArrayList<String> organizationHeads);
    void setFilePath(String filePath, File file);
    void generateReport();
}
