/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class OtherActivity {

    public String getDescription() {
        return description;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getImplementationMode() {
        return implementationMode;
    }

    public double getNumberOfCD() {
        return numberOfCD;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImplementationMode(String implementationMode) {
        this.implementationMode = implementationMode;
    }

    public void setNumberOfCD(double numberOfCD) {
        this.numberOfCD = numberOfCD;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setSubActivity(SubActivity subActivity){
        this.subActivity = subActivity;
    }
    
    public SubActivity getSubActivity(){
        return subActivity;
    }
    
    public String getDate(){
        return month + " " + year;
    }
    
    private String id;
    private SubActivity subActivity;
    private String description;
    private String month;
    private int year;
    private String implementationMode;
    private double numberOfCD;
    private String date;
}
