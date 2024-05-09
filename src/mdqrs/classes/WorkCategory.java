/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class WorkCategory {

    public int getWorkCategoryNumber() {
        return workCategoryNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setWorkCategoryNumber(int workCategoryNumber) {
        this.workCategoryNumber = workCategoryNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    private int workCategoryNumber;
    private String description;
}
