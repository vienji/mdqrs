/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mdqrs.classes;

import java.util.ArrayList;

/**
 *
 * @author Vienji
 */
public interface Report {
    void setHeaderTitle(String headerTitle);
    void setTimeFrameDetail(Object timeFrame, int year);
    void setOrganizationHeads(ArrayList<String> organizationHeads);
    void generateReport();
}
