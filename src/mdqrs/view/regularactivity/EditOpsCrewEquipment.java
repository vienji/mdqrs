/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mdqrs.view.regularactivity;

import classes.CrewEquipment;
import classes.CrewMaterials;
import classes.Equipment;
import classes.OpsEquipment;
import classes.Personnel;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import mdqrs.classes.DataValidation;
import mdqrs.listeners.MainListener;

/**
 *
 * @author Vienji
 */
public class EditOpsCrewEquipment extends javax.swing.JFrame {
    private DataValidation dataValidation = new DataValidation();
    private static EditOpsCrewEquipment instance;
    private static MainListener mainListener;
    private static ArrayList<Equipment> equipmentList;
    private static int index;
    private static CrewEquipment operationCrewEquipment;
    private static int formType;
    
    /**
     * Creates new form AddOpsEquipment
     */
    private EditOpsCrewEquipment() {
        initComponents();
        initData();
        addWindowListener(new CloseWindow());
    }
    
    public static void setFormType(int id){
        formType = id;
    }
    
    public static EditOpsCrewEquipment getInstance(){
        if(instance == null){
            instance = new EditOpsCrewEquipment();
        }
        
        return instance;
    }

    public static void setListener(MainListener listener){
        mainListener = listener;
    }   
    
    private void initData(){
        equipmentType.setSelectedItem(operationCrewEquipment
                                                    .getEquipment()
                                                    .getType());
        
        initEquipmentNumberSelectionBox(operationCrewEquipment.getEquipment().getType());
        
        equipmentNumber.setSelectedItem(operationCrewEquipment
                                                            .getEquipment()
                                                            .getEquipmentNumber());
        
        ratePerDay.setText(String.valueOf(operationCrewEquipment.getRatePerDay()));
        numberOfCD.setText(String.valueOf(operationCrewEquipment.getNumberOfCd()));
        fuelConsumption.setText(String.valueOf(operationCrewEquipment.getFuelConsumption()));
        fuelCost.setText(String.valueOf(operationCrewEquipment.getFuelCost()));
        lubricant.setText(String.valueOf(operationCrewEquipment.getLubricantAmount()));
    }
    
    public static void setData(int i, CrewEquipment crewEquipment){
        operationCrewEquipment = crewEquipment;
        index = i;
    }
    
    public static void setEquipmentList(ArrayList<Equipment> list){
        equipmentList = list;
    }
    
    public void showFrame(){
        if(!isVisible()){
            setVisible(true);
        }
    }   

    public void initEquipmentNumberSelectionBox(String type){
        equipmentNumber.removeAllItems();
        equipmentNumber.addItem("Choose equipment number...");
        equipmentList.stream().filter(e -> e.getType().equalsIgnoreCase(type)).forEach((eq) -> {
            equipmentNumber.addItem(eq.getEquipmentNumber());
        });
    }
    
    public Equipment getEquipment(String number){
        Equipment equipmentData = equipmentList
                                        .stream()
                                        .filter(e -> e.getEquipmentNumber().equalsIgnoreCase(number))
                                        .collect(Collectors.toCollection(ArrayList::new)).get(0);
        return equipmentData;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        equipmentType = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        ratePerDay = new javax.swing.JTextField();
        numberOfCD = new javax.swing.JTextField();
        fuelConsumption = new javax.swing.JTextField();
        fuelCost = new javax.swing.JTextField();
        lubricant = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        equipmentNumber = new javax.swing.JComboBox<>();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Operation Crew Equipment");
        setResizable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Equipment");

        equipmentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose equipment type...", "Backhoe", "Boom Truck", "Bulldozer", "Dump Truck", "Loader", "Motor Grader", "Prime Mover", "Self Loading", "Skid Steel Roller", "Steel Roller", "Utility Vehicle" }));
        equipmentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentTypeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Rate Per Day");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Fuel Consumption (L)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Fuel Cost / L");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Lubricant Cost");

        save.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        save.setText("Save");
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
        });

        cancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Number of CD");

        lubricant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lubricantKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Equipment Number");

        equipmentNumber.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose equipment number... " }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(equipmentNumber, 0, 260, Short.MAX_VALUE)
                            .addComponent(jLabel9))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(equipmentType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(ratePerDay)
                            .addComponent(numberOfCD)
                            .addComponent(fuelConsumption)
                            .addComponent(fuelCost)
                            .addComponent(lubricant, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(equipmentType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(equipmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ratePerDay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfCD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fuelConsumption, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fuelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lubricant, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save)
                    .addComponent(cancel))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        instance = null;
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
        if (equipmentType.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Please choose an equipment!");
        } else if (equipmentNumber.isEnabled() && equipmentNumber.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please choose an equipment number!");
        } else if (!dataValidation.validateDouble(numberOfCD.getText())){
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid number of days!");
        } else if (!dataValidation.validateCurrency(fuelConsumption.getText())){
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid fuel consumption!");
        } else if (!dataValidation.validateCurrency(fuelCost.getText())){
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid fuel cost!");
        } else if (!dataValidation.validateCurrency(lubricant.getText())){
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid lubricant cost!");
        } else {         
            Equipment opsEquipment = getEquipment(String.valueOf(equipmentNumber.getSelectedItem()));
            double opsRatePerDay = !ratePerDay.getText().isBlank() ? Double.parseDouble(ratePerDay.getText()) : 0.00;
            double opsNumberOfCD = !numberOfCD.getText().isBlank() ? Double.parseDouble(numberOfCD.getText()) : 0.0;
            double opsTotalWages = opsRatePerDay * opsNumberOfCD;
            double opsFuelConsumption = !fuelConsumption.getText().isBlank() ? Double.parseDouble(fuelConsumption.getText()) : 0;
            double opsFuelCost = !fuelCost.getText().isBlank() ? Double.parseDouble(fuelCost.getText()) : 0.00;
            double opsFuelAmount = opsFuelConsumption * opsFuelCost;
            double opsLubricantAmount = !lubricant.getText().isBlank() ? Double.parseDouble(lubricant.getText()) : 0.00;
            double opsTotalCost = opsTotalWages + opsFuelAmount + opsLubricantAmount;
            
            operationCrewEquipment.setEquipment(opsEquipment);
            operationCrewEquipment.setRatePerDay(opsRatePerDay);
            operationCrewEquipment.setNumberOfCd(opsNumberOfCD);
            operationCrewEquipment.setTotalWages(opsTotalWages);
            operationCrewEquipment.setFuelConsumption(opsFuelConsumption);
            operationCrewEquipment.setFuelCost(opsFuelCost);
            operationCrewEquipment.setFuelAmount(opsFuelAmount);
            operationCrewEquipment.setLubricantAmount(opsLubricantAmount);
            operationCrewEquipment.setTotalCost(opsTotalCost);
            
            mainListener.editRegularActivityOpsCrewEquipment(index, operationCrewEquipment, formType);
            instance = null;
            dispose();   
        } 
    }//GEN-LAST:event_saveMouseClicked

    private void lubricantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lubricantKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){    
            if (equipmentType.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please choose an equipment!");
            } else if (equipmentNumber.isEnabled() && equipmentNumber.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please choose an equipment number!");
            } else if (!dataValidation.validateDouble(numberOfCD.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid number of days!");
            } else if (!dataValidation.validateCurrency(fuelConsumption.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid fuel consumption!");
            } else if (!dataValidation.validateCurrency(fuelCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid fuel cost!");
            } else if (!dataValidation.validateCurrency(lubricant.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid lubricant cost!");
            } else {         
                Equipment opsEquipment = getEquipment(String.valueOf(equipmentNumber.getSelectedItem()));
                double opsRatePerDay = !ratePerDay.getText().isBlank() ? Double.parseDouble(ratePerDay.getText()) : 0.00;
                double opsNumberOfCD = !numberOfCD.getText().isBlank() ? Double.parseDouble(numberOfCD.getText()) : 0.0;
                double opsTotalWages = opsRatePerDay * opsNumberOfCD;
                double opsFuelConsumption = !fuelConsumption.getText().isBlank() ? Double.parseDouble(fuelConsumption.getText()) : 0;
                double opsFuelCost = !fuelCost.getText().isBlank() ? Double.parseDouble(fuelCost.getText()) : 0.00;
                double opsFuelAmount = opsFuelConsumption * opsFuelCost;
                double opsLubricantAmount = !lubricant.getText().isBlank() ? Double.parseDouble(lubricant.getText()) : 0.00;
                double opsTotalCost = opsTotalWages + opsFuelAmount + opsLubricantAmount;

                operationCrewEquipment.setEquipment(opsEquipment);
                operationCrewEquipment.setRatePerDay(opsRatePerDay);
                operationCrewEquipment.setNumberOfCd(opsNumberOfCD);
                operationCrewEquipment.setTotalWages(opsTotalWages);
                operationCrewEquipment.setFuelConsumption(opsFuelConsumption);
                operationCrewEquipment.setFuelCost(opsFuelCost);
                operationCrewEquipment.setFuelAmount(opsFuelAmount);
                operationCrewEquipment.setLubricantAmount(opsLubricantAmount);
                operationCrewEquipment.setTotalCost(opsTotalCost);

                mainListener.editRegularActivityOpsCrewEquipment(index, operationCrewEquipment, formType);
                instance = null;
                dispose();   
            } 
        }
    }//GEN-LAST:event_lubricantKeyPressed

    private void equipmentTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentTypeActionPerformed
        initEquipmentNumberSelectionBox(String.valueOf(equipmentType.getSelectedItem()));
    }//GEN-LAST:event_equipmentTypeActionPerformed

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
            java.util.logging.Logger.getLogger(EditOpsCrewEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditOpsCrewEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditOpsCrewEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditOpsCrewEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditOpsCrewEquipment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> equipmentNumber;
    private javax.swing.JComboBox<String> equipmentType;
    private javax.swing.JTextField fuelConsumption;
    private javax.swing.JTextField fuelCost;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField lubricant;
    private javax.swing.JTextField numberOfCD;
    private javax.swing.JTextField ratePerDay;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
