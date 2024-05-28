/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class RegularActivity {

    public SubActivity getSubActivity(){
        return subActivity;
    }
    
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Activity getActivity() {
        return activity;
    }

    public RoadSection getRoadSection() {
        return roadSection;
    }

    public String getOtherRoadSection() {
        return otherRoadSection;
    }

    public boolean isIsOtherRoadSection() {
        return isOtherRoadSection;
    }

    public double getNumberOfCD() {
        return numberOfCD;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getOpsEquipmentListID() {
        return opsEquipmentListID;
    }

    public String getOpsMaintenanceCrewID() {
        return opsMaintenanceCrewID;
    }

    public String getImplementationMode() {
        return implementationMode;
    }

    public String getDate() {
        return month + " " + year;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setRoadSection(RoadSection roadSection) {
        this.roadSection = roadSection;
    }

    public void setOtherRoadSection(String otherRoadSection) {
        this.otherRoadSection = otherRoadSection;
    }

    public void setIsOtherRoadSection(boolean isOtherRoadSection) {
        this.isOtherRoadSection = isOtherRoadSection;
    }

    public void setNumberOfCD(double numberOfCD) {
        this.numberOfCD = numberOfCD;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setOpsEquipmentListID(String opsEquipmentListID) {
        this.opsEquipmentListID = opsEquipmentListID;
    }

    public void setOpsMaintenanceCrewID(String opsMaintenanceCrewID) {
        this.opsMaintenanceCrewID = opsMaintenanceCrewID;
    }

    public void setImplementationMode(String implementationMode) {
        this.implementationMode = implementationMode;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public void setSubActivity(SubActivity subActivity){
        this.subActivity = subActivity;
    }
    
    private String id;
    private SubActivity subActivity;
    private Activity activity;
    private RoadSection roadSection;
    private String otherRoadSection;
    private boolean isOtherRoadSection;
    private Location location;
    private double numberOfCD;
    private String month;
    private int year;
    private String opsEquipmentListID;
    private String opsMaintenanceCrewID;
    private String implementationMode;
    private String date;
}
