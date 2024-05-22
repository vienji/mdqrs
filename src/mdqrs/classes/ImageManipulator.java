/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdqrs.classes;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Vienji
 */
public class ImageManipulator {   
    public void setIcon(String filePath, JLabel label){
        java.net.URL imgURL = getClass().getResource(filePath);
        ImageIcon icon = new ImageIcon(imgURL);
        Image image = icon.getImage().getScaledInstance(label.getWidth(), 
                label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));
    }
}
