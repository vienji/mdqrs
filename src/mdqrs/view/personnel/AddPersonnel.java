/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mdqrs.view.personnel;

import dbcontroller.Driver;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mdqrs.classes.DataValidation;
import mdqrs.classes.JobType;
import mdqrs.dbcontroller.ActivityDBController;
import mdqrs.dbcontroller.PersonnelDBController;
import mdqrs.listeners.MainListener;
/**
 *
 * @author Vienji
 */
public class AddPersonnel extends javax.swing.JFrame {
    private DataValidation dataValidation = new DataValidation();
    private static MainListener mainListener;
    private static AddPersonnel instance;
    private ArrayList<JobType> types = new PersonnelDBController().getJobTypes();
    /**
     * Creates new form AddPersonnel
     */
    private AddPersonnel() {
        initComponents();
        name.setText("");
        initType();
        type.setSelectedIndex(0);
        addWindowListener(new CloseWindow());
    }
    
    private void initType(){      
        type.addItem("Choose Type...");
        types.forEach((e) -> {
           type.addItem(e.getType());
        });
        type.addItem("Other");
    }
    
    public static AddPersonnel getInstance(){
        if(instance == null){
            instance = new AddPersonnel();
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
        name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        type = new javax.swing.JComboBox<>();
        cancel = new javax.swing.JButton();
        add = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ratePerDay = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        otherType = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Personnel");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Type");

        type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeActionPerformed(evt);
            }
        });

        cancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        add.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Rate Per Day");

        ratePerDay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        ratePerDay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ratePerDayKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Other Type");

        otherType.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(31, 31, 31))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(41, 41, 41)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otherType, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ratePerDay, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(otherType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ratePerDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        instance = null;
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        if(Driver.getConnection() != null){
            if(name.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please put a name!");
            } else if (new PersonnelDBController().isPresent(name.getText(), String.valueOf(type.getSelectedItem()))){
                JOptionPane.showMessageDialog(rootPane,
                    String.format("Personnel %s type %s was already added!", name.getText(), String.valueOf(type.getSelectedItem())));
            } else if (type.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(rootPane, "Please select a type!");
            } else if (String.valueOf(type.getSelectedItem()).equalsIgnoreCase("Other") && otherType.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "If other type, please specify!");
            } else if (!dataValidation.validateCurrency(ratePerDay.getText())) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid numeric input!");
            } else if (ratePerDay.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter a rate per day!");
            } else {
                boolean isOtherType = String.valueOf(type.getSelectedItem()).equalsIgnoreCase("Other");
                String jobType = String.valueOf(type.getSelectedItem()).equalsIgnoreCase("Other") ? otherType.getText() : String.valueOf(types.get(type.getSelectedIndex() - 1).getId());
                new PersonnelDBController().add(name.getText(), jobType, isOtherType, dataValidation.convertToDouble(ratePerDay.getText()));
                mainListener.updatePersonnel();
                int n = JOptionPane.showConfirmDialog(rootPane, "Personnel was added! Do you want to add another one?");
                if(n != 0){
                    instance = null;
                    dispose();
                } else {
                    name.setText("");
                    type.setSelectedIndex(0);
                    otherType.setText("");
                    otherType.setEnabled(false);
                    ratePerDay.setText("");
                }
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        }
    }//GEN-LAST:event_addActionPerformed

    private void typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeActionPerformed
        String selected = String.valueOf(type.getSelectedItem());
        ratePerDay.setText("");
        if(selected.equalsIgnoreCase("Other")){
            otherType.setEnabled(true);
        } else {
            otherType.setEnabled(false);
            if(type.getSelectedIndex() > 0){
                ratePerDay.setText(String.valueOf(types.get(type.getSelectedIndex() - 1).getRatePerDay()));
            }  
        }
    }//GEN-LAST:event_typeActionPerformed

    private void ratePerDayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ratePerDayKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(Driver.getConnection() != null){
                if(name.getText().isBlank()){
                    JOptionPane.showMessageDialog(rootPane, "Please put a name!");
                } else if (new PersonnelDBController().isPresent(name.getText(), String.valueOf(type.getSelectedItem()))){
                    JOptionPane.showMessageDialog(rootPane,
                        String.format("Personnel %s type %s was already added!", name.getText(), String.valueOf(type.getSelectedItem())));
                } else if (type.getSelectedIndex() == 0){
                    JOptionPane.showMessageDialog(rootPane, "Please select a type!");
                } else if (String.valueOf(type.getSelectedItem()).equalsIgnoreCase("Other") && otherType.getText().isBlank()){
                    JOptionPane.showMessageDialog(rootPane, "If other type, please specify!");
                } else if (!dataValidation.validateCurrency(ratePerDay.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a valid numeric input!");
                } else if (ratePerDay.getText().isBlank()){
                    JOptionPane.showMessageDialog(rootPane, "Please enter a rate per day!");
                } else {
                    boolean isOtherType = String.valueOf(type.getSelectedItem()).equalsIgnoreCase("Other");
                    String jobType = String.valueOf(type.getSelectedItem()).equalsIgnoreCase("Other") ? otherType.getText() : String.valueOf(type.getSelectedItem());
                    new PersonnelDBController().add(name.getText(), jobType, isOtherType, dataValidation.convertToDouble(ratePerDay.getText()));
                    mainListener.updatePersonnel();
                    int n = JOptionPane.showConfirmDialog(rootPane, "Personnel was added! Do you want to add another one?");
                    if(n != 0){
                        instance = null;
                        dispose();
                    } else {
                        name.setText("");
                        type.setSelectedIndex(0);
                        otherType.setText("");
                        otherType.setEnabled(false);
                        ratePerDay.setText("");
                    }
                }
            } else {
                String message = "Error 59: An unexpected network error occurred.";
                JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
            } 
        } 
    }//GEN-LAST:event_ratePerDayKeyPressed

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
            java.util.logging.Logger.getLogger(AddPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddPersonnel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField name;
    private javax.swing.JTextField otherType;
    private javax.swing.JFormattedTextField ratePerDay;
    private javax.swing.JComboBox<String> type;
    // End of variables declaration//GEN-END:variables
}
