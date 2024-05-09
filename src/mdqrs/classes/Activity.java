/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class Activity {

    public String getItemNumber() {
        return itemNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getWorkCategoryNumber() {
        return workCategoryNumber;
    }
    
    public WorkCategory getWorkCategory(){
        return workCategory;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkCategoryNumber(int workCategoryNumber) {
        this.workCategoryNumber = workCategoryNumber;
    }
    
    public void setWorkCategory(WorkCategory workCategory){
        this.workCategory = workCategory;
    }
    
    private String itemNumber;
    private String description;
    private int workCategoryNumber;
    private WorkCategory workCategory;
}
