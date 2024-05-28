/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mdqrs.view.regularactivity;

import classes.CrewPersonnel;
import classes.Personnel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mdqrs.classes.DataValidation;
import mdqrs.listeners.MainListener;

/**
 *
 * @author Vienji
 */
public class AddOpsMaintenanceCrew extends javax.swing.JFrame {
    private DataValidation dataValidation = new DataValidation();
    private static AddOpsMaintenanceCrew instance;
    private static MainListener mainListener;
    private static ArrayList<Personnel> personnelList = new ArrayList();
    private static int formType;
    /**
     * Creates new form AddOpsMaintenanceCrew
     */
    private AddOpsMaintenanceCrew() {
        initComponents();
        initPersonnelSelectionBox();
        addWindowListener(new CloseWindow());
    }
    
    public static AddOpsMaintenanceCrew getInstance(){
        if(instance == null){
            instance = new AddOpsMaintenanceCrew();
        }
        
        return instance;
    }
    
    public static void setFormType(int id){
        formType = id;
    }
    
    public static void setListener(MainListener listener){
        mainListener = listener;
    }
    
    public static void setPersonnelList(ArrayList<Personnel> list){
        for(Personnel personnel : list){
            personnelList.add(personnel);
        }     
    }
    
    public void showFrame(){
        if(!isVisible()){
            setVisible(true);
        }
    }
    
    public void initPersonnelSelectionBox(){
        personnelList.forEach((e) -> {
            personnel.addItem(e.getName() + " (" + e.getType() + ")");
        });
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
        personnel = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        numberOfCD = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Operation Maintenance Crew");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Personnel");

        personnel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose maintenance crew..." }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Number of CD");

        add.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add.setText("Add");
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });

        cancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cancel.setText("Cancel");
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(personnel, 0, 283, Short.MAX_VALUE)
                        .addComponent(numberOfCD)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personnel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfCD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add)
                    .addComponent(cancel))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
        instance = null;
        personnelList = new ArrayList();
        dispose();
    }//GEN-LAST:event_cancelMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        if(personnel.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(rootPane, "Please choose a maintenance crew!");
        } else if (!dataValidation.validateDouble(numberOfCD.getText())){
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid number of days!");
        } else {
            CrewPersonnel crewPersonnel = new CrewPersonnel();
            
            double opsNumberOfCD = !numberOfCD.getText().isBlank() ? Double.parseDouble(numberOfCD.getText()) : 0.0;
            double opsRatePerDay = personnelList.get(personnel.getSelectedIndex() - 1).getRatePerDay();
            
            crewPersonnel.setId("New");
            crewPersonnel.setPersonnel(personnelList.get(personnel.getSelectedIndex() - 1));
            crewPersonnel.setNumberOfCd(opsNumberOfCD);
            crewPersonnel.setRatePerDay(opsRatePerDay);
            
            mainListener.addRegularActivityOpsMaintenanceCrew(crewPersonnel, formType);
            instance = null;
            personnelList = new ArrayList();
            dispose();
        }
    }//GEN-LAST:event_addMouseClicked

    private class CloseWindow extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e){
            instance = null;
            personnelList = new ArrayList();
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
            java.util.logging.Logger.getLogger(AddOpsMaintenanceCrew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddOpsMaintenanceCrew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddOpsMaintenanceCrew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddOpsMaintenanceCrew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddOpsMaintenanceCrew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField numberOfCD;
    private javax.swing.JComboBox<String> personnel;
    // End of variables declaration//GEN-END:variables
}
