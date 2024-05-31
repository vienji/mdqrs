/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mdqrs.view.workcategory;

import dbcontroller.Driver;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import mdqrs.dbcontroller.WorkCategoryDBController;
import mdqrs.listeners.MainListener;

/**
 *
 * @author Vienji
 */
public class AddWorkCategory extends javax.swing.JFrame {
    private static MainListener mainListener;
    private static AddWorkCategory instance;
    /**
     * Creates new form AddWorkCategory
     */
    public AddWorkCategory() {
        initComponents();
        addWindowListener(new CloseWindow());
    }

    public static AddWorkCategory getInstance(){
        if(instance == null){
            instance = new AddWorkCategory();
        }
        
        return instance;
    }
    
    public static void setListener(MainListener listener){
        mainListener = listener;
    }
    
    public void showFrame(){
        if(!isVisible()){
            setVisible(true);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        categoryNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Work Category");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Category No.");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Description");

        description.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descriptionKeyPressed(evt);
            }
        });

        add.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        cancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(categoryNumber)
                            .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(categoryNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add)
                    .addComponent(cancel))
                .addContainerGap())
        );

        categoryNumber.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        instance = null;
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        if(Driver.getConnection() != null){
            if(categoryNumber.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please put a category number!");
            } else if (isNaN(categoryNumber.getText())){
                JOptionPane.showMessageDialog(rootPane, "\"" + categoryNumber.getText() + "\" is not a number!");
            } else if(description.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please put a description!");
            } else if (new WorkCategoryDBController().isPresent(Integer.parseInt(categoryNumber.getText()))){
                JOptionPane.showMessageDialog(rootPane, 
                        String.format("Work Category %s number %s was already added!", description.getText(), categoryNumber.getText()));
            } else {
                new WorkCategoryDBController().add(Integer.parseInt(String.valueOf(categoryNumber.getText())), description.getText());
                mainListener.updateWorkCategory();
                JOptionPane.showMessageDialog(rootPane, "New Work Category was successfully added!");
                int n = JOptionPane.showConfirmDialog(rootPane, "Work Category was added! Do you want to add another one?");

                if(n != 0){
                    instance = null;
                    dispose();
                } else {
                    categoryNumber.setText("");
                    description.setText("");
                }
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_addActionPerformed

    private void descriptionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descriptionKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(Driver.getConnection() != null){
                if(categoryNumber.getText().isBlank()){
                    JOptionPane.showMessageDialog(rootPane, "Please put a category number!");
                } else if (isNaN(categoryNumber.getText())){
                    JOptionPane.showMessageDialog(rootPane, "\"" + categoryNumber.getText() + "\" is not a number!");
                } else if(description.getText().isBlank()){
                    JOptionPane.showMessageDialog(rootPane, "Please put a description!");
                } else if (new WorkCategoryDBController().isPresent(Integer.parseInt(categoryNumber.getText()))){
                    JOptionPane.showMessageDialog(rootPane, 
                            String.format("Work Category %s number %s was already added!", description.getText(), categoryNumber.getText()));
                } else {
                    new WorkCategoryDBController().add(Integer.parseInt(String.valueOf(categoryNumber.getText())), description.getText());
                    mainListener.updateWorkCategory();
                    JOptionPane.showMessageDialog(rootPane, "New Work Category was successfully added!");
                    int n = JOptionPane.showConfirmDialog(rootPane, "Work Category was added! Do you want to add another one?");

                    if(n != 0){
                        instance = null;
                        dispose();
                    } else {
                        categoryNumber.setText("");
                        description.setText("");
                    }
                }
            } else {
                String message = "Error 59: An unexpected network error occurred.";
                JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
            } 
        }
    }//GEN-LAST:event_descriptionKeyPressed

    private boolean isNaN(String value){
        try{
            Integer.parseInt(value);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }
    
    private class CloseWindow extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e){
            instance = null;
            dispose();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddWorkCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddWorkCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddWorkCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddWorkCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddWorkCategory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton cancel;
    private javax.swing.JTextField categoryNumber;
    private javax.swing.JTextField description;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
