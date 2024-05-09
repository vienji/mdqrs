/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Vienji
 */
public class SubActivity {
    
    public SubActivity(){
        this.activity = new Activity();
        this.description = "";
        this.id = 0;
    }
    
    public void setActivity(Activity activity){
        this.activity = activity;
    }
    
    public Activity getActivity(){
        return activity;
    }
            
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    private int id;
    private String description;
    private Activity activity;
}
