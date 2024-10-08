/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mdqrs.view.regularactivity;

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
public class EditOpsEquipment extends javax.swing.JFrame {
    private DataValidation dataValidation = new DataValidation();
    private static EditOpsEquipment instance;
    private static MainListener mainListener;
    private static ArrayList<Personnel> personnelList = new ArrayList();
    private static ArrayList<Equipment> equipmentList;
    private static OpsEquipment operationEquipment;
    private static int index;
    private static int formType;
    /**
     * Creates new form AddOpsEquipment
     */
    private EditOpsEquipment() {
        initComponents();
        initPersonnelSelectionBox();
        initData();
        addWindowListener(new CloseWindow());
    }
    
    public static EditOpsEquipment getInstance(){
        if(instance == null){
            instance = new EditOpsEquipment();
        }
        
        return instance;
    }

    public static void setListener(MainListener listener){
        mainListener = listener;
    }
    
    public static void setData(int i, OpsEquipment opsEquipment){
        operationEquipment = opsEquipment;
        index = i;
    }
    
    public static void setFormType(int id){
        formType = id;
    }
    
    private void initData(){
        personnel.setSelectedItem(operationEquipment
                                                .getPersonnel()
                                                .getName()
                                                .concat("   ( " + operationEquipment
                                                                            .getPersonnel()
                                                                            .getType() + " )"));  
        
        if(personnel.getSelectedIndex() != 0){
            String type = personnelList.get(personnel.getSelectedIndex() - 1).getType();
            role.setText(type);
            if(type.equalsIgnoreCase("Operator")){
                equipmentType.setEnabled(true);
                equipmentType.setSelectedItem(operationEquipment
                                                        .getEquipment()
                                                        .getType());
                
                initEquipmentNumberSelectionBox(operationEquipment.getEquipment().getType());
                
                equipmentNumber.setEnabled(true);
                equipmentNumber.setSelectedItem(operationEquipment
                                                            .getEquipment()
                                                            .getEquipmentNumber());
            } else {
                equipmentType.setEnabled(false);
                equipmentNumber.setEnabled(false);
            }
        } else {
            role.setText("");
        }
        
        numberOfCD.setText(String.valueOf(operationEquipment.getNumberOfCd()));
        fuelConsumption.setText(String.valueOf(operationEquipment.getFuelConsumption()));
        fuelCost.setText(String.valueOf(operationEquipment.getFuelCost()));
        lubricant.setText(String.valueOf(operationEquipment.getLubricantAmount()));
    }
    
    public static void setPersonnelList(ArrayList<Personnel> list){
        for(Personnel personnel : list){
            if(!personnel.getType().equalsIgnoreCase("maintenance crew")){
                personnelList.add(personnel);
            }
        }
    }
    
    public static void setEquipmentList(ArrayList<Equipment> list){
        equipmentList = list;
    }
    
    public void showFrame(){
        if(!isVisible()){
            setVisible(true);
        }
    }
    
    public void initPersonnelSelectionBox(){
        personnelList.forEach((e) -> {
            personnel.addItem(e.getName().concat("   ( " + e.getType() + " )"));
        });
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
        jLabel1 = new javax.swing.JLabel();
        personnel = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        role = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        equipmentType = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        numberOfCD = new javax.swing.JTextField();
        fuelConsumption = new javax.swing.JTextField();
        fuelCost = new javax.swing.JTextField();
        lubricant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        equipmentNumber = new javax.swing.JComboBox<>();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Operation Equipment");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Personnel");

        personnel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose personnel..." }));
        personnel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personnelActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Role");

        role.setEditable(false);
        role.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Equipment");

        equipmentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose equipment type...", "Backhoe", "Boom Truck", "Bulldozer", "Dump Truck", "Loader", "Motor Grader", "Prime Mover", "Self Loading", "Skid Steel Roller", "Steel Roller", "Utility Vehicle" }));
        equipmentType.setEnabled(false);
        equipmentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentTypeActionPerformed(evt);
            }
        });

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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Equipment Number");

        equipmentNumber.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose equipment number... " }));
        equipmentNumber.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(equipmentNumber, 0, 260, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(equipmentType, 0, 260, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(role)
                            .addComponent(jLabel2)
                            .addComponent(personnel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(numberOfCD)
                            .addComponent(fuelConsumption)
                            .addComponent(fuelCost)
                            .addComponent(lubricant))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personnel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(role, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(equipmentType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(equipmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        instance = null;
        personnelList = new ArrayList();
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void personnelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personnelActionPerformed
        if(personnel.getSelectedIndex() != 0){
            String type = personnelList.get(personnel.getSelectedIndex() - 1).getType();
            role.setText(type);
            if(type.equalsIgnoreCase("Operator")){
                equipmentType.setEnabled(true);
                equipmentNumber.setEnabled(true);
            } else {
                equipmentType.setEnabled(false);
                equipmentNumber.setEnabled(false);
            }
        } else {
            role.setText("");
        }
    }//GEN-LAST:event_personnelActionPerformed

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
      
        if(personnel.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(rootPane, "Please choose a personnel!");
        } else if (equipmentType.isEnabled() && equipmentType.getSelectedIndex() == 0) {
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
            
            Personnel opsPersonnel = personnelList.get(personnel.getSelectedIndex() - 1);
            Equipment opsEquipment = equipmentNumber.isEnabled() ? getEquipment(String.valueOf(equipmentNumber.getSelectedItem())) : new Equipment();
            double opsRatePerDay = personnelList.get(personnel.getSelectedIndex() - 1).getRatePerDay();
            double opsNumberOfCD = !numberOfCD.getText().isBlank() ? Double.parseDouble(numberOfCD.getText()) : 0.0;
            double opsTotalWages = opsRatePerDay * opsNumberOfCD;
            int opsFuelConsumption = !fuelConsumption.getText().isBlank() ? Integer.parseInt(fuelConsumption.getText()) : 0;
            double opsFuelCost = !fuelCost.getText().isBlank() ? Double.parseDouble(fuelCost.getText()) : 0.00;
            double opsFuelAmount = opsFuelConsumption * opsFuelCost;
            double opsLubricantAmount = !lubricant.getText().isBlank() ? Double.parseDouble(lubricant.getText()) : 0.00;
            double opsTotalCost = opsTotalWages + opsFuelAmount + opsLubricantAmount;
            
            operationEquipment.setPersonnel(opsPersonnel);
            operationEquipment.setEquipment(opsEquipment);
            operationEquipment.setNumberOfCd(opsNumberOfCD);
            operationEquipment.setTotalWages(opsTotalWages);   
            operationEquipment.setFuelConsumption(opsFuelConsumption);
            operationEquipment.setFuelCost(opsFuelCost);           
            operationEquipment.setFuelAmount(opsFuelAmount);           
            operationEquipment.setLubricantAmount(opsLubricantAmount);
            operationEquipment.setTotalCost(opsTotalCost);

            mainListener.editRegularActivityOpsEquipment(index, operationEquipment, formType);
            instance = null;
            personnelList = new ArrayList();
            dispose();   
        }    
    }//GEN-LAST:event_saveMouseClicked

    private void lubricantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lubricantKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){  
            if(personnel.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(rootPane, "Please choose a personnel!");
            } else if (equipmentType.isEnabled() && equipmentType.getSelectedIndex() == 0) {
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

                Personnel opsPersonnel = personnelList.get(personnel.getSelectedIndex() - 1);
                Equipment opsEquipment = equipmentNumber.isEnabled() ? getEquipment(String.valueOf(equipmentNumber.getSelectedItem())) : new Equipment();
                double opsRatePerDay = personnelList.get(personnel.getSelectedIndex() - 1).getRatePerDay();
                double opsNumberOfCD = !numberOfCD.getText().isBlank() ? Double.parseDouble(numberOfCD.getText()) : 0.0;
                double opsTotalWages = opsRatePerDay * opsNumberOfCD;
                int opsFuelConsumption = !fuelConsumption.getText().isBlank() ? Integer.parseInt(fuelConsumption.getText()) : 0;
                double opsFuelCost = !fuelCost.getText().isBlank() ? Double.parseDouble(fuelCost.getText()) : 0.00;
                double opsFuelAmount = opsFuelConsumption * opsFuelCost;
                double opsLubricantAmount = !lubricant.getText().isBlank() ? Double.parseDouble(lubricant.getText()) : 0.00;
                double opsTotalCost = opsTotalWages + opsFuelAmount + opsLubricantAmount;

                operationEquipment.setPersonnel(opsPersonnel);
                operationEquipment.setEquipment(opsEquipment);
                operationEquipment.setNumberOfCd(opsNumberOfCD);
                operationEquipment.setTotalWages(opsTotalWages);   
                operationEquipment.setFuelConsumption(opsFuelConsumption);
                operationEquipment.setFuelCost(opsFuelCost);           
                operationEquipment.setFuelAmount(opsFuelAmount);           
                operationEquipment.setLubricantAmount(opsLubricantAmount);
                operationEquipment.setTotalCost(opsTotalCost);

                mainListener.editRegularActivityOpsEquipment(index, operationEquipment, formType);
                instance = null;
                personnelList = new ArrayList();
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
            java.util.logging.Logger.getLogger(EditOpsEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditOpsEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditOpsEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditOpsEquipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditOpsEquipment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> equipmentNumber;
    private javax.swing.JComboBox<String> equipmentType;
    private javax.swing.JTextField fuelConsumption;
    private javax.swing.JTextField fuelCost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField lubricant;
    private javax.swing.JTextField numberOfCD;
    private javax.swing.JComboBox<String> personnel;
    private javax.swing.JTextField role;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
