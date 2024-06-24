/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mdqrs.view;

import classes.Activity;
import classes.CrewEquipment;
import classes.CrewEquipmentList;
import classes.CrewMaterials;
import classes.CrewMaterialsList;
import classes.CrewPersonnel;
import classes.CrewPersonnelList;
import classes.Equipment;
import classes.Location;
import classes.OpsEquipment;
import classes.OpsEquipmentList;
import classes.OtherActivity;
import classes.Personnel;
import classes.RegularActivity;
import classes.RoadSection;
import classes.SubActivity;
import classes.WorkCategory;
import dbcontroller.Driver;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import mdqrs.classes.Cryptographer;
import mdqrs.classes.DriversForEngineers;
import mdqrs.classes.ImageManipulator;
import mdqrs.reports.MonthlyReport;
import mdqrs.reports.MonthlyReportBuilder;
import mdqrs.classes.OtherExpenses;
import mdqrs.classes.Program;
import mdqrs.classes.Project;
import mdqrs.reports.ReportFactory;
import mdqrs.classes.DataValidation;
import mdqrs.classes.JarDirectory;
import mdqrs.reports.QuarterlyReport;
import mdqrs.reports.QuarterlyReportBuilder;
import mdqrs.dbcontroller.ActivityDBController;
import mdqrs.dbcontroller.ActivityListDBController;
import mdqrs.dbcontroller.DriversForEngineersDBController;
import mdqrs.dbcontroller.EquipmentDBController;
import mdqrs.dbcontroller.GeneralDBController;
import mdqrs.dbcontroller.LocationDBController;
import mdqrs.dbcontroller.OtherActivityListDBController;
import mdqrs.dbcontroller.OtherExpensesDBController;
import mdqrs.dbcontroller.PersonnelDBController;
import mdqrs.dbcontroller.ProgramDBController;
import mdqrs.dbcontroller.RoadSectionDBController;
import mdqrs.dbcontroller.SubActivityDBController;
import mdqrs.dbcontroller.WorkCategoryDBController;
import mdqrs.listeners.MainListener;
import mdqrs.reports.OtherActivityReport;
import mdqrs.reports.RegularActivityReport;
import mdqrs.view.equipment.AddEquipment;
import mdqrs.view.equipment.EditEquipment;
import mdqrs.view.personnel.AddPersonnel;
import mdqrs.view.personnel.EditPersonnel;
import mdqrs.view.personnel.PersonnelSetting;
import mdqrs.view.program.AddProject;
import mdqrs.view.program.EditProject;
import mdqrs.view.regularactivity.AddOpsCrewEquipment;
import mdqrs.view.regularactivity.AddOpsCrewMaterials;
import mdqrs.view.regularactivity.AddOpsEquipment;
import mdqrs.view.regularactivity.AddOpsMaintenanceCrew;
import mdqrs.view.regularactivity.EditOpsCrewEquipment;
import mdqrs.view.regularactivity.EditOpsCrewMaterials;
import mdqrs.view.regularactivity.EditOpsEquipment;
import mdqrs.view.regularactivity.EditOpsMaintenanceCrew;
import mdqrs.view.workcategory.AddActivity;
import mdqrs.view.workcategory.AddSubActivity;
import mdqrs.view.workcategory.AddWorkCategory;
import mdqrs.view.workcategory.EditActivity;
import mdqrs.view.workcategory.EditSubActivity;
import mdqrs.view.workcategory.EditWorkCategory;

/**
 *
 * @author Vienji
 */
public class Main extends javax.swing.JFrame implements MainListener {
    private DataValidation dataValidation = new DataValidation();
    private JFileChooser fileChooser = new JFileChooser();
    private int currentSection = 0;
    private ArrayList<Activity> activityList;
    private ArrayList<SubActivity> subActivityList;
    private ArrayList<SubActivity> subActivityTempList;
    private ArrayList<SubActivity> subActivityOtherList;
    private ArrayList<Personnel> personnelList;
    private ArrayList<Equipment> equipmentList;
    private ArrayList<Location> locationList;
    private ArrayList<RoadSection> roadSectionList; 
    private ArrayList<WorkCategory> workCategoryList;
    private ArrayList<OtherActivity> otherActivityList;
    private ArrayList<OtherExpenses> otherExpensesList;
    private ArrayList<DriversForEngineers> driversForEngineersList;
    private ArrayList<Program> programList;
    
    //Data for Regular activity forms
    private OpsEquipmentList opsEquipmentList = new OpsEquipmentList();
    private CrewPersonnelList crewPersonnelList = new CrewPersonnelList();
    private CrewMaterialsList crewMaterialsList = new CrewMaterialsList();
    private CrewEquipmentList crewEquipmentList = new CrewEquipmentList();
    
    private ArrayList<Project> projectList = new ArrayList();

    //Search List
    ArrayList<Personnel> searchedPersonnel;
    ArrayList<Equipment> searchedEquipment;
    ArrayList<WorkCategory> searchedWorkCategory;
    ArrayList<Activity> searchedActivity;
    ArrayList<SubActivity> searchedSubActivity;
    ArrayList<OtherActivity> searchedOtherActivity;
    ArrayList<OtherExpenses> searchedOtherExpenses;
    ArrayList<DriversForEngineers> searchedDFE;
    ArrayList<Program> searchedProgram;
    
    //Temp
    RegularActivity regularActivityForEdit = new RegularActivity();
    RegularActivity regularActivityToView = new RegularActivity();
    OtherActivity otherActivityForEdit = new OtherActivity();
    OtherActivity otherActivityToView = new OtherActivity();
    OtherExpenses otherExpensesForEdit = new OtherExpenses();
    DriversForEngineers driversForEngineersForEdit = new DriversForEngineers();
    Program programForEdit = new Program();
    
    //Pagination
    private ArrayList<RegularActivity> regularActivityList;
    ArrayList<RegularActivity> searchedRegularActivity;
    private int currentPage = 1;
    private double fullPage = 1;
    private final int PAGE_LIMIT = 21;
    
    private void updateRegularActivity(){
        regularActivityList = new ActivityListDBController().fetchData(currentPage, PAGE_LIMIT);
        searchedRegularActivity = regularActivityList;
        
        populateMainRegularActivity(regularActivityList);
        
        pageLabel.setText(currentPage + " / " + (int) fullPage);
        prevRAPage.setEnabled(currentPage > 1);
        nextRAPage.setEnabled(currentPage != fullPage);
    }   
    
    private void updateSearchedRegularActivity(String value){                                       
        regularActivityList = new ActivityListDBController().searchData(currentPage, PAGE_LIMIT, value);
        searchedRegularActivity = regularActivityList;
        
        populateMainRegularActivity(regularActivityList);
  
        pageLabel.setText(currentPage + " / " + (int) fullPage);
        prevRAPage.setEnabled(currentPage > 1);
        nextRAPage.setEnabled(currentPage != fullPage);
    }   
    
    
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        /*initIcons();
        initNetworkSettings();
        initReportSettings();
        initSearchFieldListener();
        initDate();*/
        addWindowListener(new CloseWindow());
        /*
        if (Driver.getConnection() != null) {
            initData();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Setup Your Network!");
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navigationPanel = new javax.swing.JPanel();
        menuLogo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        navActivityList = new javax.swing.JPanel();
        navLabelActivityList = new javax.swing.JLabel();
        iconActivityList = new javax.swing.JLabel();
        navWorkCategory = new javax.swing.JPanel();
        navLabelWorkCategory = new javax.swing.JLabel();
        iconWorkCategory = new javax.swing.JLabel();
        navEquipment = new javax.swing.JPanel();
        navLabelEquipment = new javax.swing.JLabel();
        iconEquipment = new javax.swing.JLabel();
        navPersonnel = new javax.swing.JPanel();
        navLabelPersonnel = new javax.swing.JLabel();
        iconPersonnel = new javax.swing.JLabel();
        navReport = new javax.swing.JPanel();
        navLabelReport = new javax.swing.JLabel();
        iconReport = new javax.swing.JLabel();
        navSettings = new javax.swing.JPanel();
        navLabelSettings = new javax.swing.JLabel();
        iconSettings = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        welcomePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        welcomeLogo = new javax.swing.JLabel();
        jLabel250 = new javax.swing.JLabel();
        activityListPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchActivity = new javax.swing.JLabel();
        activitySearchValue = new javax.swing.JTextField();
        sortActivity = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        activityTabbedPane = new javax.swing.JTabbedPane();
        regularActivityTab = new javax.swing.JPanel();
        mainRegularActivityPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMainRegularActivity = new javax.swing.JTable();
        deleteRegularActivity = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        editRegularActivity = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        viewRegularActivity = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        addNewRegularActivity = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        prevRAPage = new javax.swing.JButton();
        nextRAPage = new javax.swing.JButton();
        pageLabel = new javax.swing.JLabel();
        addNewRegularActivityPanel = new javax.swing.JPanel();
        cancelNewRegularActivity = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        saveNewRegularActivity = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        addNewRegularActivityScrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        regularActivityFormActivity = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        regularActivityFormRoadSection = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        regularActivityFormLocation = new javax.swing.JComboBox<>();
        regularActivityFormOtherRoadSection = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableRegularActivityOperationEquipment = new javax.swing.JTable();
        addOperationEquipment = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableRegularActivityMaintenanceCrew = new javax.swing.JTable();
        addMaintenanceCrew = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableRegularActivityMaterials = new javax.swing.JTable();
        addCrewMaterials = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        addCrewEquipment = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableRegularActivityEquipment = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        regularActivityFormDaysOfOperation = new javax.swing.JTextField();
        regularActivityFormImplementationMode = new javax.swing.JTextField();
        isOperationEquipmentTableSelected = new javax.swing.JCheckBox();
        isMaintenanceCrewTableSelected = new javax.swing.JCheckBox();
        isMaterialsTableSelected = new javax.swing.JCheckBox();
        isEquipmentTableSelected = new javax.swing.JCheckBox();
        removeOperationEquipment = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        editOperationEquipment = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        editMaintenanceCrew = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        removeMaintenanceCrew = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        editCrewMaterials = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        removeCrewMaterials = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        editCrewEquipment = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        removeCrewEquipment = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        regularActivityFormYear = new javax.swing.JComboBox<>();
        regularActivityFormMonth = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        regularActivitySubActivitySelectionBox = new javax.swing.JComboBox<>();
        regularActivitySubActivityTitle = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        viewRegularActivityPanel = new javax.swing.JPanel();
        backViewRegularActivity = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        viewRegularActivityScrollPane = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        regularActivityViewActivityName = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        regularActivityViewRoadSectionName = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        regularActivityViewDaysOfOperation = new javax.swing.JLabel();
        regularActivityViewLocationName = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        regularActivityViewDate = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        regularActivityViewImplementationMode = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableRegularActivityViewOperationEquipment = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tableRegularActivityViewMaintenanceCrew = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tableRegularActivityViewMaterials = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tableRegularActivityViewEquipment = new javax.swing.JTable();
        jLabel67 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        regularActivityViewOpsEquipmentTotalExpenses = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        regularActivityViewMaintenanceCrewSubTotalExpenses = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        regularActivityViewEquipmentSubTotalExpenses = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        regularActivityViewOpsMaintenanceCrewTotalExpenses = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        regularActivityGrandTotalExpenses = new javax.swing.JLabel();
        exportActivity = new javax.swing.JPanel();
        jLabel226 = new javax.swing.JLabel();
        editRegularActivityPanel = new javax.swing.JPanel();
        cancelEditRegularActivity = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        saveEditRegularActivity = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        editRegularActivityScrollPane = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        regularActivityEditActivity = new javax.swing.JComboBox<>();
        jLabel87 = new javax.swing.JLabel();
        regularActivityEditRoadSection = new javax.swing.JComboBox<>();
        jLabel88 = new javax.swing.JLabel();
        regularActivityEditLocation = new javax.swing.JComboBox<>();
        regularActivityEditOtherRoadSection = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        tableRegularActivityEditOperationEquipment = new javax.swing.JTable();
        addOperationEquipmentEdit = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        tableRegularActivityEditMaintenanceCrew = new javax.swing.JTable();
        addMaintenanceCrewEdit = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        tableRegularActivityEditMaterials = new javax.swing.JTable();
        addCrewMaterialsEdit = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        addCrewEquipmentEdit = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tableRegularActivityEditEquipment = new javax.swing.JTable();
        jLabel97 = new javax.swing.JLabel();
        regularActivityEditDaysOfOperation = new javax.swing.JTextField();
        regularActivityEditImplementationMode = new javax.swing.JTextField();
        isEditOperationEquipmentTableSelected = new javax.swing.JCheckBox();
        isEditMaintenanceCrewTableSelected = new javax.swing.JCheckBox();
        isEditMaterialsTableSelected = new javax.swing.JCheckBox();
        isEditEquipmentTableSelected = new javax.swing.JCheckBox();
        removeOperationEquipmentEdit = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        editOperationEquipmentEdit = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        editMaintenanceCrewEdit = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        removeMaintenanceCrewEdit = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        editCrewMaterialsEdit = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        removeCrewMaterialsEdit = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        editCrewEquipmentEdit = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        removeCrewEquipmentEdit = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        regularActivityEditYear = new javax.swing.JComboBox<>();
        regularActivityEditMonth = new javax.swing.JComboBox<>();
        jLabel106 = new javax.swing.JLabel();
        regularActivityEditSubActivitySelectionBox = new javax.swing.JComboBox<>();
        regularActivitySubActivityTitle1 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        otherActivityTab = new javax.swing.JPanel();
        mainOtherActivityPanel = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        tableMainOtherActivity = new javax.swing.JTable();
        deleteOtherActivity = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        editOtherActivity = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        viewOtherActivity = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        addNewOtherActivity = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        addNewOtherActivityPanel = new javax.swing.JPanel();
        cancelNewOtherActivity = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        saveNewOtherActivity = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        addNewOtherActivityScrollPane = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        otherActivityFormDescription = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        tableOtherActivityMaintenanceCrew = new javax.swing.JTable();
        addOtherMaintenanceCrew = new javax.swing.JPanel();
        jLabel122 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        otherActivityFormDaysOfOperation = new javax.swing.JTextField();
        otherActivityFormImplementationMode = new javax.swing.JTextField();
        editOtherMaintenanceCrew = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        removeOtherMaintenanceCrew = new javax.swing.JPanel();
        jLabel129 = new javax.swing.JLabel();
        otherActivityFormYear = new javax.swing.JComboBox<>();
        otherActivityFormMonth = new javax.swing.JComboBox<>();
        jLabel134 = new javax.swing.JLabel();
        otherActivityFormSubActivitySelectionBox = new javax.swing.JComboBox<>();
        regularActivitySubActivityTitle2 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        viewOtherActivityPanel = new javax.swing.JPanel();
        backViewRegularActivity1 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        viewOtherActivityScrollPane = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        otherActivityViewSubActivityName = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        otherActivityViewDescription = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        otherActivityViewDaysOfOperation = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        otherActivityViewDate = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        otherActivityViewImplementationMode = new javax.swing.JLabel();
        jScrollPane28 = new javax.swing.JScrollPane();
        tableOtherActivityViewPersonnel = new javax.swing.JTable();
        jLabel130 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        otherActivityViewPersonnelTotalExpenses = new javax.swing.JLabel();
        exportOtherActivity = new javax.swing.JPanel();
        jLabel249 = new javax.swing.JLabel();
        editOtherActivityPanel = new javax.swing.JPanel();
        cancelNewOtherActivity1 = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        saveEditedOtherActivity = new javax.swing.JPanel();
        jLabel126 = new javax.swing.JLabel();
        editOtherActivityScrollPane = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        otherActivityEditDescription = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jScrollPane29 = new javax.swing.JScrollPane();
        tableOtherActivityEditPersonnel = new javax.swing.JTable();
        addOtherMaintenanceCrewEdit = new javax.swing.JPanel();
        jLabel133 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        otherActivityEditDaysOfOperation = new javax.swing.JTextField();
        otherActivityEditImplementationMode = new javax.swing.JTextField();
        editOtherMaintenanceCrewEdit = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        removeOtherMaintenanceCrewEdit = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        otherActivityEditYear = new javax.swing.JComboBox<>();
        otherActivityEditMonth = new javax.swing.JComboBox<>();
        jLabel140 = new javax.swing.JLabel();
        otherActivityEditSubActivitySelectionBox = new javax.swing.JComboBox<>();
        regularActivitySubActivityTitle3 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        otherExpensesTab = new javax.swing.JPanel();
        mainOtherExpensesPanel = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        tableMainOtherExpenses = new javax.swing.JTable();
        deleteOtherExpenses = new javax.swing.JPanel();
        jLabel142 = new javax.swing.JLabel();
        editOtherExpenses = new javax.swing.JPanel();
        jLabel143 = new javax.swing.JLabel();
        viewOtherExpenses = new javax.swing.JPanel();
        jLabel144 = new javax.swing.JLabel();
        addNewOtherExpenses = new javax.swing.JPanel();
        jLabel145 = new javax.swing.JLabel();
        addNewOtherExpensesPanel = new javax.swing.JPanel();
        cancelNewOtherExpenses = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        saveNewOtherExpenses = new javax.swing.JPanel();
        jLabel147 = new javax.swing.JLabel();
        addNewOtherExpensesScrollPane = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        otherExpensesFormDaysOfOperation = new javax.swing.JTextField();
        otherExpensesFormImplementationMode = new javax.swing.JTextField();
        otherExpensesFormYear = new javax.swing.JComboBox<>();
        otherExpensesFormMonth = new javax.swing.JComboBox<>();
        jLabel155 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        otherExpensesFormLaborCrewCost = new javax.swing.JTextField();
        otherExpensesFormLaborEquipmentCost = new javax.swing.JTextField();
        jLabel158 = new javax.swing.JLabel();
        otherExpensesFormLightEquipments = new javax.swing.JTextField();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        otherExpensesFormHeavyEquipments = new javax.swing.JTextField();
        jLabel162 = new javax.swing.JLabel();
        otherExpensesFormDescription = new javax.swing.JLabel();
        viewOtherExpensesPanel = new javax.swing.JPanel();
        backViewOtherExpenses = new javax.swing.JPanel();
        jLabel175 = new javax.swing.JLabel();
        viewOtherExpensesScrollPane = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        jLabel176 = new javax.swing.JLabel();
        otherExpensesViewDate = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        otherActivityViewDescription1 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        otherExpensesViewLaborCrewCost = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        otherExpensesViewLaborEquipmentCost = new javax.swing.JLabel();
        otherExpensesViewImplementationMode = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        otherExpensesViewDaysOfOperation = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        otherExpensesViewLightEquipments = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        otherExpensesViewHeavyEquipments = new javax.swing.JLabel();
        editOtherExpensesPanel = new javax.swing.JPanel();
        cancelEditOtherExpenses = new javax.swing.JPanel();
        jLabel186 = new javax.swing.JLabel();
        saveEditOtherExpenses = new javax.swing.JPanel();
        jLabel187 = new javax.swing.JLabel();
        editOtherExpensesScrollPane = new javax.swing.JScrollPane();
        jPanel12 = new javax.swing.JPanel();
        jLabel188 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        otherExpensesFormEditDaysOfOperation = new javax.swing.JTextField();
        otherExpensesFormEditImplementationMode = new javax.swing.JTextField();
        otherExpensesFormEditYear = new javax.swing.JComboBox<>();
        otherExpensesFormEditMonth = new javax.swing.JComboBox<>();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        otherExpensesFormEditLaborCrewCost = new javax.swing.JTextField();
        otherExpensesFormEditLaborEquipmentCost = new javax.swing.JTextField();
        jLabel194 = new javax.swing.JLabel();
        otherExpensesFormEditLightEquipments = new javax.swing.JTextField();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        otherExpensesFormEditHeavyEquipments = new javax.swing.JTextField();
        jLabel198 = new javax.swing.JLabel();
        otherExpensesFormDescription1 = new javax.swing.JLabel();
        driversForEngineersTab = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        driversForEngineersContainer = new javax.swing.JPanel();
        mainDriversForEngineersPanel = new javax.swing.JPanel();
        deleteDriversForEngineers1 = new javax.swing.JPanel();
        jLabel165 = new javax.swing.JLabel();
        editDriversForEngineers1 = new javax.swing.JPanel();
        jLabel170 = new javax.swing.JLabel();
        addNewDriversForEngineers1 = new javax.swing.JPanel();
        jLabel171 = new javax.swing.JLabel();
        jScrollPane33 = new javax.swing.JScrollPane();
        tableMainDriversForEngineers = new javax.swing.JTable();
        addNewDriversForEngineersPanel = new javax.swing.JPanel();
        addNewDFEScrollPane = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel172 = new javax.swing.JLabel();
        driversForEngineersFormLaborEquipmentCost = new javax.swing.JTextField();
        jLabel173 = new javax.swing.JLabel();
        driversForEngineersFormEquipmentFuelCost = new javax.swing.JTextField();
        driversForEngineersFormLubricantCost = new javax.swing.JTextField();
        jLabel174 = new javax.swing.JLabel();
        driversForEngineersFormMonth = new javax.swing.JComboBox<>();
        jLabel199 = new javax.swing.JLabel();
        driversForEngineersFormYear = new javax.swing.JComboBox<>();
        jLabel200 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        driversForEngineersFormImplementationMode = new javax.swing.JTextField();
        jLabel202 = new javax.swing.JLabel();
        driversForEngineersFormDaysOfOperation = new javax.swing.JTextField();
        cancelNewOtherExpenses1 = new javax.swing.JPanel();
        jLabel203 = new javax.swing.JLabel();
        saveNewOtherExpenses1 = new javax.swing.JPanel();
        jLabel204 = new javax.swing.JLabel();
        editDriversForEngineersPanel = new javax.swing.JPanel();
        editDFEScrollPane = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel205 = new javax.swing.JLabel();
        driversForEngineersFormEditLaborEquipmentCost = new javax.swing.JTextField();
        jLabel206 = new javax.swing.JLabel();
        driversForEngineersFormEditEquipmentFuelCost = new javax.swing.JTextField();
        driversForEngineersFormEditLubricantCost = new javax.swing.JTextField();
        jLabel207 = new javax.swing.JLabel();
        driversForEngineersFormEditMonth = new javax.swing.JComboBox<>();
        jLabel208 = new javax.swing.JLabel();
        driversForEngineersFormEditYear = new javax.swing.JComboBox<>();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        driversForEngineersFormEditImplementationMode = new javax.swing.JTextField();
        jLabel211 = new javax.swing.JLabel();
        driversForEngineersFormEditDaysOfOperation = new javax.swing.JTextField();
        cancelNewOtherExpenses2 = new javax.swing.JPanel();
        jLabel212 = new javax.swing.JLabel();
        saveDFE = new javax.swing.JPanel();
        jLabel213 = new javax.swing.JLabel();
        totalCostBreakdownPanel = new javax.swing.JPanel();
        jLabel151 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        laborCrewCost = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        laborEquipmentCost = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        equipmentFuelCost = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        lubricantCost = new javax.swing.JLabel();
        timeframeDetail = new javax.swing.JComboBox<>();
        timeRange = new javax.swing.JComboBox<>();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        grandTotalCost = new javax.swing.JLabel();
        projectsOfWorksTab = new javax.swing.JPanel();
        mainProjectsPanel = new javax.swing.JPanel();
        jScrollPane37 = new javax.swing.JScrollPane();
        tableMainPrograms = new javax.swing.JTable();
        deleteProjects = new javax.swing.JPanel();
        jLabel154 = new javax.swing.JLabel();
        editProjects = new javax.swing.JPanel();
        jLabel163 = new javax.swing.JLabel();
        viewProjects = new javax.swing.JPanel();
        jLabel167 = new javax.swing.JLabel();
        addNewProjects = new javax.swing.JPanel();
        jLabel214 = new javax.swing.JLabel();
        addNewProjectsPanel = new javax.swing.JPanel();
        cancelNewOtherActivity2 = new javax.swing.JPanel();
        jLabel252 = new javax.swing.JLabel();
        saveNewOtherActivity1 = new javax.swing.JPanel();
        jLabel253 = new javax.swing.JLabel();
        addNewProjectsScrollPane = new javax.swing.JScrollPane();
        jPanel17 = new javax.swing.JPanel();
        projectsFormSourceOfFund = new javax.swing.JTextField();
        jLabel254 = new javax.swing.JLabel();
        jLabel256 = new javax.swing.JLabel();
        jScrollPane42 = new javax.swing.JScrollPane();
        tableProjects = new javax.swing.JTable();
        addProjectsItem = new javax.swing.JPanel();
        jLabel257 = new javax.swing.JLabel();
        editProjectsItem = new javax.swing.JPanel();
        jLabel259 = new javax.swing.JLabel();
        removeProjectsItem = new javax.swing.JPanel();
        jLabel260 = new javax.swing.JLabel();
        projectsFormYear = new javax.swing.JComboBox<>();
        projectsFormMonth = new javax.swing.JComboBox<>();
        jLabel261 = new javax.swing.JLabel();
        jLabel262 = new javax.swing.JLabel();
        viewProjectsPanel = new javax.swing.JPanel();
        backViewProgram = new javax.swing.JPanel();
        jLabel215 = new javax.swing.JLabel();
        viewProjectsScrollPane = new javax.swing.JScrollPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel216 = new javax.swing.JLabel();
        projectsViewSourceOfFund = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        projectsViewDate = new javax.swing.JLabel();
        jScrollPane39 = new javax.swing.JScrollPane();
        tableViewProjects = new javax.swing.JTable();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        projectsViewProjectTotal = new javax.swing.JLabel();
        editProjectsPanel = new javax.swing.JPanel();
        cancelNewOtherActivity3 = new javax.swing.JPanel();
        jLabel255 = new javax.swing.JLabel();
        saveNewOtherActivity2 = new javax.swing.JPanel();
        jLabel258 = new javax.swing.JLabel();
        editProjectsScrollPane = new javax.swing.JScrollPane();
        jPanel18 = new javax.swing.JPanel();
        projectsFormEditSourceOfFund = new javax.swing.JTextField();
        jLabel263 = new javax.swing.JLabel();
        jLabel264 = new javax.swing.JLabel();
        jScrollPane44 = new javax.swing.JScrollPane();
        tableEditProjects = new javax.swing.JTable();
        addProjectsItemEdit = new javax.swing.JPanel();
        jLabel265 = new javax.swing.JLabel();
        editProjectsItemEdit = new javax.swing.JPanel();
        jLabel266 = new javax.swing.JLabel();
        removeProjectsItemEdit = new javax.swing.JPanel();
        jLabel267 = new javax.swing.JLabel();
        projectsFormEditYear = new javax.swing.JComboBox<>();
        projectsFormEditMonth = new javax.swing.JComboBox<>();
        jLabel268 = new javax.swing.JLabel();
        jLabel269 = new javax.swing.JLabel();
        workCategoryPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        workCategoryTabbedPane = new javax.swing.JTabbedPane();
        workCategoryTab = new javax.swing.JPanel();
        mainWorkCategoryPanel = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableWorkCategory = new javax.swing.JTable();
        deleteWorkCategory = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        editWorkCategory = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        addNewWorkCategory = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        activityTab = new javax.swing.JPanel();
        mainWorkCategoryPanel1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableActivity = new javax.swing.JTable();
        deleteActivity = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        editActivity = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        addNewActivity = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        subActivityTab = new javax.swing.JPanel();
        mainWorkCategoryPanel3 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tableSubActivity = new javax.swing.JTable();
        deleteSubActivity = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        editSubActivity = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        addNewSubActivity = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        workCategorySearchValue = new javax.swing.JTextField();
        searchWorkCategory = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        sortWorkCategory = new javax.swing.JComboBox<>();
        equipmentPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        equipmentSearchValue = new javax.swing.JTextField();
        searchEquipment = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        sortEquipment = new javax.swing.JComboBox<>();
        workCategoryTab1 = new javax.swing.JPanel();
        mainWorkCategoryPanel2 = new javax.swing.JPanel();
        deleteEquipment = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        editEquipment = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        addNewEquipment = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableEquipment = new javax.swing.JTable();
        personnelPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        searchPersonnelValue = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablePersonnel = new javax.swing.JTable();
        searchPersonnel = new javax.swing.JLabel();
        sortPersonnel = new javax.swing.JComboBox<>();
        jLabel75 = new javax.swing.JLabel();
        deletePersonnel = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        editPersonnel = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        addNewPersonnel = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        personnelSettingsIcon = new javax.swing.JLabel();
        reportPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel217 = new javax.swing.JLabel();
        jLabel218 = new javax.swing.JLabel();
        monthlyHeaderTitle = new javax.swing.JTextField();
        monthlyYear = new javax.swing.JComboBox<>();
        monthlyMonth = new javax.swing.JComboBox<>();
        exportMonthlyReport = new javax.swing.JButton();
        jLabel220 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        quarterlyYear = new javax.swing.JComboBox<>();
        jLabel227 = new javax.swing.JLabel();
        exportQuarterlyReport = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        regularActivityWorkbookMonth = new javax.swing.JComboBox<>();
        exportWorkbook = new javax.swing.JButton();
        regularActivityWorkbookYear = new javax.swing.JComboBox<>();
        jLabel231 = new javax.swing.JLabel();
        settingsPanel = new javax.swing.JPanel();
        settingsScrollPane = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        networkUsername = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        networkServer = new javax.swing.JTextField();
        networkPort = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        networkDatabase = new javax.swing.JTextField();
        saveNetwork = new javax.swing.JButton();
        networkPassword = new javax.swing.JPasswordField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel230 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        preparedBy1Name = new javax.swing.JTextField();
        preparedBy1Position = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jLabel236 = new javax.swing.JLabel();
        preparedBy2Name = new javax.swing.JTextField();
        jLabel237 = new javax.swing.JLabel();
        preparedBy2Position = new javax.swing.JTextField();
        jLabel238 = new javax.swing.JLabel();
        jLabel239 = new javax.swing.JLabel();
        jLabel240 = new javax.swing.JLabel();
        submittedByPosition = new javax.swing.JTextField();
        submittedByName = new javax.swing.JTextField();
        jLabel241 = new javax.swing.JLabel();
        approvedByPosition = new javax.swing.JTextField();
        approvedByName = new javax.swing.JTextField();
        jLabel242 = new javax.swing.JLabel();
        jLabel243 = new javax.swing.JLabel();
        saveReport = new javax.swing.JButton();
        jLabel244 = new javax.swing.JLabel();
        jLabel245 = new javax.swing.JLabel();
        jLabel246 = new javax.swing.JLabel();
        totalLengthOfProvincialRoads = new javax.swing.JTextField();
        jLabel247 = new javax.swing.JLabel();
        totalLengthOfProvincialRoadsInFairToGood = new javax.swing.JTextField();
        jLabel248 = new javax.swing.JLabel();
        totalBudget = new javax.swing.JTextField();
        saveQuarterlyReportDetails = new javax.swing.JButton();
        testNetworkConnection = new javax.swing.JButton();
        systemRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Maintenance Division Quarterly Report System");
        setMinimumSize(new java.awt.Dimension(1400, 800));
        setResizable(false);

        navigationPanel.setBackground(new java.awt.Color(0, 102, 255));
        navigationPanel.setPreferredSize(new java.awt.Dimension(250, 800));
        navigationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!tableMainRegularActivity.getBounds().contains(e.getPoint())) {
                    tableMainRegularActivity.clearSelection();
                }
            }
        });

        menuLogo.setMaximumSize(new java.awt.Dimension(100, 100));
        menuLogo.setMinimumSize(new java.awt.Dimension(100, 100));
        menuLogo.setPreferredSize(new java.awt.Dimension(100, 100));
        menuLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLogoMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("MDQRS");

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));
        jPanel2.setLayout(new java.awt.GridLayout(8, 0, 0, 8));

        navActivityList.setBackground(new java.awt.Color(0, 102, 255));
        navActivityList.setPreferredSize(new java.awt.Dimension(75, 75));
        navActivityList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navActivityListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navActivityListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navActivityListMouseExited(evt);
            }
        });

        navLabelActivityList.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        navLabelActivityList.setForeground(new java.awt.Color(255, 255, 255));
        navLabelActivityList.setText("Activity List");

        javax.swing.GroupLayout navActivityListLayout = new javax.swing.GroupLayout(navActivityList);
        navActivityList.setLayout(navActivityListLayout);
        navActivityListLayout.setHorizontalGroup(
            navActivityListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navActivityListLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(iconActivityList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLabelActivityList, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navActivityListLayout.setVerticalGroup(
            navActivityListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navActivityListLayout.createSequentialGroup()
                .addComponent(navLabelActivityList, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navActivityListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconActivityList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(navActivityList);

        navWorkCategory.setBackground(new java.awt.Color(0, 102, 255));
        navWorkCategory.setPreferredSize(new java.awt.Dimension(75, 75));
        navWorkCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navWorkCategoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navWorkCategoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navWorkCategoryMouseExited(evt);
            }
        });

        navLabelWorkCategory.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        navLabelWorkCategory.setForeground(new java.awt.Color(255, 255, 255));
        navLabelWorkCategory.setText("Work Category");

        javax.swing.GroupLayout navWorkCategoryLayout = new javax.swing.GroupLayout(navWorkCategory);
        navWorkCategory.setLayout(navWorkCategoryLayout);
        navWorkCategoryLayout.setHorizontalGroup(
            navWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navWorkCategoryLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(iconWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLabelWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navWorkCategoryLayout.setVerticalGroup(
            navWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navWorkCategoryLayout.createSequentialGroup()
                .addComponent(navLabelWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navWorkCategoryLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(navWorkCategory);

        navEquipment.setBackground(new java.awt.Color(0, 102, 255));
        navEquipment.setPreferredSize(new java.awt.Dimension(75, 75));
        navEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navEquipmentMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navEquipmentMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navEquipmentMouseExited(evt);
            }
        });

        navLabelEquipment.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        navLabelEquipment.setForeground(new java.awt.Color(255, 255, 255));
        navLabelEquipment.setText("Equipment");

        javax.swing.GroupLayout navEquipmentLayout = new javax.swing.GroupLayout(navEquipment);
        navEquipment.setLayout(navEquipmentLayout);
        navEquipmentLayout.setHorizontalGroup(
            navEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navEquipmentLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(iconEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLabelEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navEquipmentLayout.setVerticalGroup(
            navEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navEquipmentLayout.createSequentialGroup()
                .addComponent(navLabelEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navEquipmentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(navEquipment);

        navPersonnel.setBackground(new java.awt.Color(0, 102, 255));
        navPersonnel.setPreferredSize(new java.awt.Dimension(75, 75));
        navPersonnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPersonnelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navPersonnelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navPersonnelMouseExited(evt);
            }
        });

        navLabelPersonnel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        navLabelPersonnel.setForeground(new java.awt.Color(255, 255, 255));
        navLabelPersonnel.setText("Personnel");

        javax.swing.GroupLayout navPersonnelLayout = new javax.swing.GroupLayout(navPersonnel);
        navPersonnel.setLayout(navPersonnelLayout);
        navPersonnelLayout.setHorizontalGroup(
            navPersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navPersonnelLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(iconPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLabelPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navPersonnelLayout.setVerticalGroup(
            navPersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPersonnelLayout.createSequentialGroup()
                .addComponent(navLabelPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navPersonnelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(navPersonnel);

        navReport.setBackground(new java.awt.Color(0, 102, 255));
        navReport.setPreferredSize(new java.awt.Dimension(75, 75));
        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navReportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navReportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navReportMouseExited(evt);
            }
        });

        navLabelReport.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        navLabelReport.setForeground(new java.awt.Color(255, 255, 255));
        navLabelReport.setText("Report");

        javax.swing.GroupLayout navReportLayout = new javax.swing.GroupLayout(navReport);
        navReport.setLayout(navReportLayout);
        navReportLayout.setHorizontalGroup(
            navReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navReportLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(iconReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLabelReport, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navReportLayout.setVerticalGroup(
            navReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navReportLayout.createSequentialGroup()
                .addComponent(navLabelReport, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navReportLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(navReport);

        navSettings.setBackground(new java.awt.Color(0, 102, 255));
        navSettings.setPreferredSize(new java.awt.Dimension(75, 75));
        navSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navSettingsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navSettingsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navSettingsMouseExited(evt);
            }
        });

        navLabelSettings.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        navLabelSettings.setForeground(new java.awt.Color(255, 255, 255));
        navLabelSettings.setText("Settings");

        javax.swing.GroupLayout navSettingsLayout = new javax.swing.GroupLayout(navSettings);
        navSettings.setLayout(navSettingsLayout);
        navSettingsLayout.setHorizontalGroup(
            navSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navSettingsLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(iconSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLabelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navSettingsLayout.setVerticalGroup(
            navSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navSettingsLayout.createSequentialGroup()
                .addComponent(navLabelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navSettingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(navSettings);

        javax.swing.GroupLayout navigationPanelLayout = new javax.swing.GroupLayout(navigationPanel);
        navigationPanel.setLayout(navigationPanelLayout);
        navigationPanelLayout.setHorizontalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navigationPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7))
                    .addComponent(menuLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        navigationPanelLayout.setVerticalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(menuLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!tableMainRegularActivity.getBounds().contains(e.getPoint())) {
                    tableMainRegularActivity.clearSelection();
                }
            }
        });
        mainPanel.setLayout(new java.awt.CardLayout());

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel8.setText("Welcome to");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel9.setText("Maintenance Division Quarterly Report System");

        jLabel250.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel250.setForeground(new java.awt.Color(102, 102, 102));
        jLabel250.setText("Developed by Vienji");

        javax.swing.GroupLayout welcomePanelLayout = new javax.swing.GroupLayout(welcomePanel);
        welcomePanel.setLayout(welcomePanelLayout);
        welcomePanelLayout.setHorizontalGroup(
            welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomePanelLayout.createSequentialGroup()
                .addGroup(welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(welcomePanelLayout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addGroup(welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(welcomePanelLayout.createSequentialGroup()
                                .addGap(227, 227, 227)
                                .addGroup(welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(welcomeLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))))
                    .addGroup(welcomePanelLayout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(jLabel250)))
                .addContainerGap(271, Short.MAX_VALUE))
        );
        welcomePanelLayout.setVerticalGroup(
            welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomePanelLayout.createSequentialGroup()
                .addContainerGap(248, Short.MAX_VALUE)
                .addComponent(welcomeLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel250)
                .addGap(229, 229, 229))
        );

        mainPanel.add(welcomePanel, "card2");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Activity");

        searchActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchActivityMouseClicked(evt);
            }
        });

        activitySearchValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                activitySearchValueKeyPressed(evt);
            }
        });

        sortActivity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id", "activity", "date", "implementation mode" }));
        sortActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortActivityActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Sort");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Search");

        activityTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activityTabbedPaneMouseClicked(evt);
            }
        });

        regularActivityTab.setLayout(new java.awt.CardLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tableMainRegularActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Activity", "Road Section", "Location", "No. of CD", "Date", "Implementation Mode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMainRegularActivity.setRowHeight(24);
        tableMainRegularActivity.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tableMainRegularActivity);
        if (tableMainRegularActivity.getColumnModel().getColumnCount() > 0) {
            tableMainRegularActivity.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableMainRegularActivity.getColumnModel().getColumn(3).setHeaderValue("Location");
            tableMainRegularActivity.getColumnModel().getColumn(4).setPreferredWidth(70);
        }

        deleteRegularActivity.setBackground(new java.awt.Color(255, 51, 51));
        deleteRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteRegularActivityMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Delete");

        javax.swing.GroupLayout deleteRegularActivityLayout = new javax.swing.GroupLayout(deleteRegularActivity);
        deleteRegularActivity.setLayout(deleteRegularActivityLayout);
        deleteRegularActivityLayout.setHorizontalGroup(
            deleteRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteRegularActivityLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(31, 31, 31))
        );
        deleteRegularActivityLayout.setVerticalGroup(
            deleteRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteRegularActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editRegularActivity.setBackground(new java.awt.Color(0, 102, 204));
        editRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editRegularActivityMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Edit");

        javax.swing.GroupLayout editRegularActivityLayout = new javax.swing.GroupLayout(editRegularActivity);
        editRegularActivity.setLayout(editRegularActivityLayout);
        editRegularActivityLayout.setHorizontalGroup(
            editRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editRegularActivityLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel16)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editRegularActivityLayout.setVerticalGroup(
            editRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editRegularActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addContainerGap())
        );

        viewRegularActivity.setBackground(new java.awt.Color(255, 102, 0));
        viewRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewRegularActivityMouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("View");

        javax.swing.GroupLayout viewRegularActivityLayout = new javax.swing.GroupLayout(viewRegularActivity);
        viewRegularActivity.setLayout(viewRegularActivityLayout);
        viewRegularActivityLayout.setHorizontalGroup(
            viewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewRegularActivityLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel17)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        viewRegularActivityLayout.setVerticalGroup(
            viewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewRegularActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        addNewRegularActivity.setBackground(new java.awt.Color(0, 204, 0));
        addNewRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewRegularActivityMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Add New");

        javax.swing.GroupLayout addNewRegularActivityLayout = new javax.swing.GroupLayout(addNewRegularActivity);
        addNewRegularActivity.setLayout(addNewRegularActivityLayout);
        addNewRegularActivityLayout.setHorizontalGroup(
            addNewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewRegularActivityLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel12)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        addNewRegularActivityLayout.setVerticalGroup(
            addNewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewRegularActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        prevRAPage.setText("Prev");
        prevRAPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevRAPageActionPerformed(evt);
            }
        });

        nextRAPage.setText("Next");
        nextRAPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextRAPageActionPerformed(evt);
            }
        });

        pageLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pageLabel.setText("1 / 1");

        javax.swing.GroupLayout mainRegularActivityPanelLayout = new javax.swing.GroupLayout(mainRegularActivityPanel);
        mainRegularActivityPanel.setLayout(mainRegularActivityPanelLayout);
        mainRegularActivityPanelLayout.setHorizontalGroup(
            mainRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainRegularActivityPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(prevRAPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nextRAPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 392, Short.MAX_VALUE)
                .addComponent(deleteRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainRegularActivityPanelLayout.setVerticalGroup(
            mainRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainRegularActivityPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addGroup(mainRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainRegularActivityPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addNewRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainRegularActivityPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(mainRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(prevRAPage)
                            .addComponent(nextRAPage)
                            .addComponent(pageLabel))))
                .addContainerGap())
        );

        regularActivityTab.add(mainRegularActivityPanel, "card2");

        cancelNewRegularActivity.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewRegularActivityMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Cancel");

        javax.swing.GroupLayout cancelNewRegularActivityLayout = new javax.swing.GroupLayout(cancelNewRegularActivity);
        cancelNewRegularActivity.setLayout(cancelNewRegularActivityLayout);
        cancelNewRegularActivityLayout.setHorizontalGroup(
            cancelNewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewRegularActivityLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(24, 24, 24))
        );
        cancelNewRegularActivityLayout.setVerticalGroup(
            cancelNewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewRegularActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveNewRegularActivity.setBackground(new java.awt.Color(0, 204, 0));
        saveNewRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNewRegularActivityMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Save");

        javax.swing.GroupLayout saveNewRegularActivityLayout = new javax.swing.GroupLayout(saveNewRegularActivity);
        saveNewRegularActivity.setLayout(saveNewRegularActivityLayout);
        saveNewRegularActivityLayout.setHorizontalGroup(
            saveNewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewRegularActivityLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel21)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveNewRegularActivityLayout.setVerticalGroup(
            saveNewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewRegularActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addNewRegularActivityScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        addNewRegularActivityScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(241, 241, 182));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Activity");

        regularActivityFormActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActivityFormActivityActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Name of Road Section");

        regularActivityFormRoadSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActivityFormRoadSectionActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Location");

        regularActivityFormLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActivityFormLocationActionPerformed(evt);
            }
        });

        regularActivityFormOtherRoadSection.setEditable(false);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Month");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Implementation Mode");

        tableRegularActivityOperationEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "Type", "Operator Name", "Rate Per Day", "No. of CD", "Total Wages", "Fuel Consumption (L)", "Cost/L", "Amount", "Lubricant", "Total Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityOperationEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tableRegularActivityOperationEquipment);
        if (tableRegularActivityOperationEquipment.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityOperationEquipment.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableRegularActivityOperationEquipment.getColumnModel().getColumn(6).setPreferredWidth(140);
        }

        addOperationEquipment.setVisible(false);
        addOperationEquipment.setBackground(new java.awt.Color(255, 255, 255));
        addOperationEquipment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addOperationEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addOperationEquipmentMouseClicked(evt);
            }
        });

        jLabel33.setBackground(new java.awt.Color(51, 51, 51));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Add");

        javax.swing.GroupLayout addOperationEquipmentLayout = new javax.swing.GroupLayout(addOperationEquipment);
        addOperationEquipment.setLayout(addOperationEquipmentLayout);
        addOperationEquipmentLayout.setHorizontalGroup(
            addOperationEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOperationEquipmentLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel33)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addOperationEquipmentLayout.setVerticalGroup(
            addOperationEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOperationEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("If Other Road Section Please Specify:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("B. Operation Maintenance Crew");

        tableRegularActivityMaintenanceCrew.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityMaintenanceCrew.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tableRegularActivityMaintenanceCrew);

        addMaintenanceCrew.setVisible(false);
        addMaintenanceCrew.setBackground(new java.awt.Color(255, 255, 255));
        addMaintenanceCrew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addMaintenanceCrew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMaintenanceCrewMouseClicked(evt);
            }
        });

        jLabel35.setBackground(new java.awt.Color(51, 51, 51));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 51, 51));
        jLabel35.setText("Add");

        javax.swing.GroupLayout addMaintenanceCrewLayout = new javax.swing.GroupLayout(addMaintenanceCrew);
        addMaintenanceCrew.setLayout(addMaintenanceCrewLayout);
        addMaintenanceCrewLayout.setHorizontalGroup(
            addMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addMaintenanceCrewLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel35)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addMaintenanceCrewLayout.setVerticalGroup(
            addMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableRegularActivityMaterials.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Quantity", "Unit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityMaterials.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tableRegularActivityMaterials);

        addCrewMaterials.setVisible(false);
        addCrewMaterials.setBackground(new java.awt.Color(255, 255, 255));
        addCrewMaterials.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addCrewMaterials.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCrewMaterialsMouseClicked(evt);
            }
        });

        jLabel38.setBackground(new java.awt.Color(51, 51, 51));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(51, 51, 51));
        jLabel38.setText("Add");

        javax.swing.GroupLayout addCrewMaterialsLayout = new javax.swing.GroupLayout(addCrewMaterials);
        addCrewMaterials.setLayout(addCrewMaterialsLayout);
        addCrewMaterialsLayout.setHorizontalGroup(
            addCrewMaterialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewMaterialsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel38)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addCrewMaterialsLayout.setVerticalGroup(
            addCrewMaterialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewMaterialsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addCrewEquipment.setVisible(false);
        addCrewEquipment.setBackground(new java.awt.Color(255, 255, 255));
        addCrewEquipment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addCrewEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCrewEquipmentMouseClicked(evt);
            }
        });

        jLabel39.setBackground(new java.awt.Color(51, 51, 51));
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(51, 51, 51));
        jLabel39.setText("Add");

        javax.swing.GroupLayout addCrewEquipmentLayout = new javax.swing.GroupLayout(addCrewEquipment);
        addCrewEquipment.setLayout(addCrewEquipmentLayout);
        addCrewEquipmentLayout.setHorizontalGroup(
            addCrewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewEquipmentLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel39)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addCrewEquipmentLayout.setVerticalGroup(
            addCrewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableRegularActivityEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "No. of CD", "Rate Per Day", "Total Wages", "Fuel Consumption (L)", "Cost/L", "Amount", "Lubricant", "Total Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tableRegularActivityEquipment);
        if (tableRegularActivityEquipment.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityEquipment.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableRegularActivityEquipment.getColumnModel().getColumn(4).setPreferredWidth(140);
        }

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel53.setText("Days of Operation");

        isOperationEquipmentTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isOperationEquipmentTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isOperationEquipmentTableSelected.setText("A. Operation Equipment");
        isOperationEquipmentTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isOperationEquipmentTableSelectedActionPerformed(evt);
            }
        });

        isMaintenanceCrewTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isMaintenanceCrewTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isMaintenanceCrewTableSelected.setText("B.1 Maintenance Crew");
        isMaintenanceCrewTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isMaintenanceCrewTableSelectedActionPerformed(evt);
            }
        });

        isMaterialsTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isMaterialsTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isMaterialsTableSelected.setText("B.2 Materials");
        isMaterialsTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isMaterialsTableSelectedActionPerformed(evt);
            }
        });

        isEquipmentTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isEquipmentTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isEquipmentTableSelected.setText("B.3 Equipment");
        isEquipmentTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isEquipmentTableSelectedActionPerformed(evt);
            }
        });

        removeOperationEquipment.setVisible(false);
        removeOperationEquipment.setBackground(new java.awt.Color(255, 255, 255));
        removeOperationEquipment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeOperationEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeOperationEquipmentMouseClicked(evt);
            }
        });

        jLabel36.setBackground(new java.awt.Color(51, 51, 51));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 51));
        jLabel36.setText("Remove");

        javax.swing.GroupLayout removeOperationEquipmentLayout = new javax.swing.GroupLayout(removeOperationEquipment);
        removeOperationEquipment.setLayout(removeOperationEquipmentLayout);
        removeOperationEquipmentLayout.setHorizontalGroup(
            removeOperationEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOperationEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeOperationEquipmentLayout.setVerticalGroup(
            removeOperationEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOperationEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editOperationEquipment.setVisible(false);
        editOperationEquipment.setBackground(new java.awt.Color(255, 255, 255));
        editOperationEquipment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editOperationEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editOperationEquipmentMouseClicked(evt);
            }
        });

        jLabel56.setBackground(new java.awt.Color(51, 51, 51));
        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(51, 51, 51));
        jLabel56.setText("Edit");

        javax.swing.GroupLayout editOperationEquipmentLayout = new javax.swing.GroupLayout(editOperationEquipment);
        editOperationEquipment.setLayout(editOperationEquipmentLayout);
        editOperationEquipmentLayout.setHorizontalGroup(
            editOperationEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOperationEquipmentLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel56)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editOperationEquipmentLayout.setVerticalGroup(
            editOperationEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOperationEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editMaintenanceCrew.setVisible(false);
        editMaintenanceCrew.setBackground(new java.awt.Color(255, 255, 255));
        editMaintenanceCrew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editMaintenanceCrew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMaintenanceCrewMouseClicked(evt);
            }
        });

        jLabel57.setBackground(new java.awt.Color(51, 51, 51));
        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(51, 51, 51));
        jLabel57.setText("Edit");

        javax.swing.GroupLayout editMaintenanceCrewLayout = new javax.swing.GroupLayout(editMaintenanceCrew);
        editMaintenanceCrew.setLayout(editMaintenanceCrewLayout);
        editMaintenanceCrewLayout.setHorizontalGroup(
            editMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editMaintenanceCrewLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel57)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editMaintenanceCrewLayout.setVerticalGroup(
            editMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeMaintenanceCrew.setVisible(false);
        removeMaintenanceCrew.setBackground(new java.awt.Color(255, 255, 255));
        removeMaintenanceCrew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeMaintenanceCrew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeMaintenanceCrewMouseClicked(evt);
            }
        });

        jLabel58.setBackground(new java.awt.Color(51, 51, 51));
        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 51, 51));
        jLabel58.setText("Remove");

        javax.swing.GroupLayout removeMaintenanceCrewLayout = new javax.swing.GroupLayout(removeMaintenanceCrew);
        removeMaintenanceCrew.setLayout(removeMaintenanceCrewLayout);
        removeMaintenanceCrewLayout.setHorizontalGroup(
            removeMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeMaintenanceCrewLayout.setVerticalGroup(
            removeMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editCrewMaterials.setVisible(false);
        editCrewMaterials.setBackground(new java.awt.Color(255, 255, 255));
        editCrewMaterials.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editCrewMaterials.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editCrewMaterialsMouseClicked(evt);
            }
        });

        jLabel63.setBackground(new java.awt.Color(51, 51, 51));
        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(51, 51, 51));
        jLabel63.setText("Edit");

        javax.swing.GroupLayout editCrewMaterialsLayout = new javax.swing.GroupLayout(editCrewMaterials);
        editCrewMaterials.setLayout(editCrewMaterialsLayout);
        editCrewMaterialsLayout.setHorizontalGroup(
            editCrewMaterialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewMaterialsLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel63)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editCrewMaterialsLayout.setVerticalGroup(
            editCrewMaterialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewMaterialsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeCrewMaterials.setVisible(false);
        removeCrewMaterials.setBackground(new java.awt.Color(255, 255, 255));
        removeCrewMaterials.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeCrewMaterials.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeCrewMaterialsMouseClicked(evt);
            }
        });

        jLabel64.setBackground(new java.awt.Color(51, 51, 51));
        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(51, 51, 51));
        jLabel64.setText("Remove");

        javax.swing.GroupLayout removeCrewMaterialsLayout = new javax.swing.GroupLayout(removeCrewMaterials);
        removeCrewMaterials.setLayout(removeCrewMaterialsLayout);
        removeCrewMaterialsLayout.setHorizontalGroup(
            removeCrewMaterialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewMaterialsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeCrewMaterialsLayout.setVerticalGroup(
            removeCrewMaterialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewMaterialsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editCrewEquipment.setVisible(false);
        editCrewEquipment.setBackground(new java.awt.Color(255, 255, 255));
        editCrewEquipment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editCrewEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editCrewEquipmentMouseClicked(evt);
            }
        });

        jLabel65.setBackground(new java.awt.Color(51, 51, 51));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(51, 51, 51));
        jLabel65.setText("Edit");

        javax.swing.GroupLayout editCrewEquipmentLayout = new javax.swing.GroupLayout(editCrewEquipment);
        editCrewEquipment.setLayout(editCrewEquipmentLayout);
        editCrewEquipmentLayout.setHorizontalGroup(
            editCrewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewEquipmentLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel65)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editCrewEquipmentLayout.setVerticalGroup(
            editCrewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeCrewEquipment.setVisible(false);
        removeCrewEquipment.setBackground(new java.awt.Color(255, 255, 255));
        removeCrewEquipment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeCrewEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeCrewEquipmentMouseClicked(evt);
            }
        });

        jLabel66.setBackground(new java.awt.Color(51, 51, 51));
        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(51, 51, 51));
        jLabel66.setText("Remove");

        javax.swing.GroupLayout removeCrewEquipmentLayout = new javax.swing.GroupLayout(removeCrewEquipment);
        removeCrewEquipment.setLayout(removeCrewEquipmentLayout);
        removeCrewEquipmentLayout.setHorizontalGroup(
            removeCrewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeCrewEquipmentLayout.setVerticalGroup(
            removeCrewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        regularActivityFormMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setText("Year");

        regularActivitySubActivitySelectionBox.setEnabled(false);
        regularActivitySubActivitySelectionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose sub activity..." }));

        regularActivitySubActivityTitle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        regularActivitySubActivityTitle.setText("Sub Activity");

        jLabel83.setForeground(new java.awt.Color(0, 0, 204));
        jLabel83.setText("Note: Please remember to select the checkbox on the left side of the table title if using the table, and uncheck it if not.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel83)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(isOperationEquipmentTableSelected))
                        .addGap(694, 694, 694))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 168, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(isMaintenanceCrewTableSelected)
                                        .addComponent(isMaterialsTableSelected)
                                        .addComponent(isEquipmentTableSelected))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(editMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(editCrewMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeCrewMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addCrewMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(editCrewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeCrewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addCrewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(editOperationEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeOperationEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addOperationEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(regularActivityFormActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(regularActivitySubActivityTitle)
                                    .addComponent(regularActivitySubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel19)
                                        .addComponent(regularActivityFormRoadSection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel30)
                                        .addComponent(regularActivityFormLocation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(regularActivityFormOtherRoadSection, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(regularActivityFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel31))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel37)
                                            .addComponent(regularActivityFormYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel53)
                                    .addComponent(regularActivityFormDaysOfOperation, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(regularActivityFormImplementationMode, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                        .addGap(36, 36, 36))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel31)
                        .addComponent(jLabel37))
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regularActivityFormActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regularActivityFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regularActivityFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(regularActivitySubActivityTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(regularActivitySubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityFormLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityFormImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityFormDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(regularActivityFormRoadSection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(10, 10, 10)
                .addComponent(regularActivityFormOtherRoadSection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel83)
                .addGap(18, 18, 18)
                .addComponent(isOperationEquipmentTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addOperationEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editOperationEquipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(removeOperationEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jLabel34)
                .addGap(30, 30, 30)
                .addComponent(isMaintenanceCrewTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(isMaterialsTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCrewMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editCrewMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeCrewMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(isEquipmentTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCrewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editCrewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeCrewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );

        addNewRegularActivityScrollPane.setViewportView(jPanel1);

        javax.swing.GroupLayout addNewRegularActivityPanelLayout = new javax.swing.GroupLayout(addNewRegularActivityPanel);
        addNewRegularActivityPanel.setLayout(addNewRegularActivityPanelLayout);
        addNewRegularActivityPanelLayout.setHorizontalGroup(
            addNewRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewRegularActivityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveNewRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(addNewRegularActivityScrollPane)
        );
        addNewRegularActivityPanelLayout.setVerticalGroup(
            addNewRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewRegularActivityPanelLayout.createSequentialGroup()
                .addComponent(addNewRegularActivityScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveNewRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        regularActivityTab.add(addNewRegularActivityPanel, "card2");

        backViewRegularActivity.setBackground(new java.awt.Color(0, 102, 204));
        backViewRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backViewRegularActivityMouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Back");

        javax.swing.GroupLayout backViewRegularActivityLayout = new javax.swing.GroupLayout(backViewRegularActivity);
        backViewRegularActivity.setLayout(backViewRegularActivityLayout);
        backViewRegularActivityLayout.setHorizontalGroup(
            backViewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backViewRegularActivityLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel25)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        backViewRegularActivityLayout.setVerticalGroup(
            backViewRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backViewRegularActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap())
        );

        viewRegularActivityScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Activity");

        regularActivityViewActivityName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        regularActivityViewActivityName.setText("### - Activity Name");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Name of Road Section");

        regularActivityViewRoadSectionName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        regularActivityViewRoadSectionName.setText("Road Section Name");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Days of Operation");

        regularActivityViewDaysOfOperation.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        regularActivityViewDaysOfOperation.setText("# CD");

        regularActivityViewLocationName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        regularActivityViewLocationName.setText("Location Name");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Location");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setText("Date");

        regularActivityViewDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        regularActivityViewDate.setText("Month Year");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setText("Mode of Implementation");

        regularActivityViewImplementationMode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        regularActivityViewImplementationMode.setText("implementation");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("A. Operation Equipment");

        tableRegularActivityViewOperationEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "Type", "Operator Name", "Rate Per Day", "No. of CD", "Total Wages", "Fuel Consumption (L)", "Cost / L", "Amount", "Lubricant", "Total Cost"
            }
        ));
        tableRegularActivityViewOperationEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tableRegularActivityViewOperationEquipment);
        if (tableRegularActivityViewOperationEquipment.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityViewOperationEquipment.getColumnModel().getColumn(1).setHeaderValue("Type");
            tableRegularActivityViewOperationEquipment.getColumnModel().getColumn(2).setHeaderValue("Operator Name");
        }

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("B. Operation Maintenance Crew");

        tableRegularActivityViewMaintenanceCrew.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ));
        tableRegularActivityViewMaintenanceCrew.getTableHeader().setReorderingAllowed(false);
        jScrollPane14.setViewportView(tableRegularActivityViewMaintenanceCrew);
        if (tableRegularActivityViewMaintenanceCrew.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityViewMaintenanceCrew.getColumnModel().getColumn(1).setHeaderValue("Type");
            tableRegularActivityViewMaintenanceCrew.getColumnModel().getColumn(2).setHeaderValue("Operator Name");
        }

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("B.1 Maintenance Crew");

        tableRegularActivityViewMaterials.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Quantity", "Unit"
            }
        ));
        tableRegularActivityViewMaterials.getTableHeader().setReorderingAllowed(false);
        jScrollPane15.setViewportView(tableRegularActivityViewMaterials);
        if (tableRegularActivityViewMaterials.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityViewMaterials.getColumnModel().getColumn(1).setHeaderValue("Type");
            tableRegularActivityViewMaterials.getColumnModel().getColumn(2).setHeaderValue("Operator Name");
        }

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("B.2 Materials");

        tableRegularActivityViewEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "No. of CD", "Rate Per Day", "Total Wages", "Fuel Consumption (L)", "Cost / L", "Amount", "Lubricant", "Total Cost"
            }
        ));
        tableRegularActivityViewEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane16.setViewportView(tableRegularActivityViewEquipment);

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel67.setText("B.3 Equipment");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setText("Total Expenses:");

        regularActivityViewOpsEquipmentTotalExpenses.setText("0.00");

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel76.setText("B.1 Sub Total Expenses:");

        regularActivityViewMaintenanceCrewSubTotalExpenses.setText("0.00");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel77.setText("B.3 Sub Total Expenses:");

        regularActivityViewEquipmentSubTotalExpenses.setText("0.00");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel78.setText(" Total Expenses:");

        regularActivityViewOpsMaintenanceCrewTotalExpenses.setText("0.00");

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel79.setText(" Grand Total Expenses:");

        regularActivityGrandTotalExpenses.setText("0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(regularActivityViewActivityName)
                            .addComponent(jLabel23)
                            .addComponent(regularActivityViewRoadSectionName)
                            .addComponent(jLabel26)
                            .addComponent(regularActivityViewDaysOfOperation)
                            .addComponent(regularActivityViewLocationName)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(regularActivityViewDate)
                            .addComponent(jLabel68)
                            .addComponent(regularActivityViewImplementationMode))
                        .addGap(51, 51, 51))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel24))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 89, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28)
                                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel67))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(regularActivityViewOpsEquipmentTotalExpenses)
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel76)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(regularActivityViewMaintenanceCrewSubTotalExpenses)
                                .addGap(26, 26, 26))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityViewOpsMaintenanceCrewTotalExpenses))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityViewEquipmentSubTotalExpenses))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel79)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityGrandTotalExpenses)))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regularActivityViewActivityName)
                    .addComponent(regularActivityViewDate))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityViewRoadSectionName))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityViewImplementationMode)))
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(regularActivityViewLocationName)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(regularActivityViewDaysOfOperation)
                .addGap(52, 52, 52)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(regularActivityViewOpsEquipmentTotalExpenses))
                .addGap(47, 47, 47)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(regularActivityViewMaintenanceCrewSubTotalExpenses))
                .addGap(27, 27, 27)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(regularActivityViewEquipmentSubTotalExpenses))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(regularActivityViewOpsMaintenanceCrewTotalExpenses))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(regularActivityGrandTotalExpenses))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        viewRegularActivityScrollPane.setViewportView(jPanel4);

        exportActivity.setBackground(new java.awt.Color(0, 102, 102));
        exportActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportActivityMouseClicked(evt);
            }
        });

        jLabel226.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel226.setForeground(new java.awt.Color(255, 255, 255));
        jLabel226.setText("Export");

        javax.swing.GroupLayout exportActivityLayout = new javax.swing.GroupLayout(exportActivity);
        exportActivity.setLayout(exportActivityLayout);
        exportActivityLayout.setHorizontalGroup(
            exportActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportActivityLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel226)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        exportActivityLayout.setVerticalGroup(
            exportActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel226)
                .addContainerGap())
        );

        javax.swing.GroupLayout viewRegularActivityPanelLayout = new javax.swing.GroupLayout(viewRegularActivityPanel);
        viewRegularActivityPanel.setLayout(viewRegularActivityPanelLayout);
        viewRegularActivityPanelLayout.setHorizontalGroup(
            viewRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewRegularActivityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exportActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backViewRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(viewRegularActivityScrollPane)
        );
        viewRegularActivityPanelLayout.setVerticalGroup(
            viewRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewRegularActivityPanelLayout.createSequentialGroup()
                .addComponent(viewRegularActivityScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(viewRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(backViewRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        regularActivityTab.add(viewRegularActivityPanel, "card2");

        cancelEditRegularActivity.setBackground(new java.awt.Color(255, 102, 0));
        cancelEditRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelEditRegularActivityMouseClicked(evt);
            }
        });

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Cancel");

        javax.swing.GroupLayout cancelEditRegularActivityLayout = new javax.swing.GroupLayout(cancelEditRegularActivity);
        cancelEditRegularActivity.setLayout(cancelEditRegularActivityLayout);
        cancelEditRegularActivityLayout.setHorizontalGroup(
            cancelEditRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelEditRegularActivityLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel84)
                .addGap(24, 24, 24))
        );
        cancelEditRegularActivityLayout.setVerticalGroup(
            cancelEditRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelEditRegularActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel84)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveEditRegularActivity.setBackground(new java.awt.Color(0, 204, 0));
        saveEditRegularActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveEditRegularActivityMouseClicked(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Save");

        javax.swing.GroupLayout saveEditRegularActivityLayout = new javax.swing.GroupLayout(saveEditRegularActivity);
        saveEditRegularActivity.setLayout(saveEditRegularActivityLayout);
        saveEditRegularActivityLayout.setHorizontalGroup(
            saveEditRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveEditRegularActivityLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel85)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveEditRegularActivityLayout.setVerticalGroup(
            saveEditRegularActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveEditRegularActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel85)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editRegularActivityScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        editRegularActivityScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel5.setBackground(new java.awt.Color(241, 241, 182));

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel86.setText("Activity");

        regularActivityEditActivity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose activity..." }));
        regularActivityEditActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActivityEditActivityActionPerformed(evt);
            }
        });

        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel87.setText("Name of Road Section");

        regularActivityEditRoadSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActivityEditRoadSectionActionPerformed(evt);
            }
        });

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel88.setText("Location");

        regularActivityEditLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActivityEditLocationActionPerformed(evt);
            }
        });

        regularActivityFormOtherRoadSection.setEditable(false);

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel89.setText("Month");

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel90.setText("Implementation Mode");

        tableRegularActivityEditOperationEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "Type", "Operator Name", "Rate Per Day", "No. of CD", "Total Wages", "Fuel Consumption (L)", "Cost/L", "Amount", "Lubricant", "Total Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityEditOperationEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane19.setViewportView(tableRegularActivityEditOperationEquipment);
        if (tableRegularActivityEditOperationEquipment.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityEditOperationEquipment.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableRegularActivityEditOperationEquipment.getColumnModel().getColumn(6).setPreferredWidth(140);
        }

        addOperationEquipment.setVisible(false);
        addOperationEquipmentEdit.setBackground(new java.awt.Color(255, 255, 255));
        addOperationEquipmentEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addOperationEquipmentEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addOperationEquipmentEditMouseClicked(evt);
            }
        });

        jLabel91.setBackground(new java.awt.Color(51, 51, 51));
        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(51, 51, 51));
        jLabel91.setText("Add");

        javax.swing.GroupLayout addOperationEquipmentEditLayout = new javax.swing.GroupLayout(addOperationEquipmentEdit);
        addOperationEquipmentEdit.setLayout(addOperationEquipmentEditLayout);
        addOperationEquipmentEditLayout.setHorizontalGroup(
            addOperationEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOperationEquipmentEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel91)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addOperationEquipmentEditLayout.setVerticalGroup(
            addOperationEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOperationEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel91)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel92.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel92.setText("If Other Road Section Please Specify:");

        jLabel93.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel93.setText("B. Operation Maintenance Crew");

        tableRegularActivityEditMaintenanceCrew.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityEditMaintenanceCrew.getTableHeader().setReorderingAllowed(false);
        jScrollPane20.setViewportView(tableRegularActivityEditMaintenanceCrew);

        addMaintenanceCrew.setVisible(false);
        addMaintenanceCrewEdit.setBackground(new java.awt.Color(255, 255, 255));
        addMaintenanceCrewEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addMaintenanceCrewEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMaintenanceCrewEditMouseClicked(evt);
            }
        });

        jLabel94.setBackground(new java.awt.Color(51, 51, 51));
        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(51, 51, 51));
        jLabel94.setText("Add");

        javax.swing.GroupLayout addMaintenanceCrewEditLayout = new javax.swing.GroupLayout(addMaintenanceCrewEdit);
        addMaintenanceCrewEdit.setLayout(addMaintenanceCrewEditLayout);
        addMaintenanceCrewEditLayout.setHorizontalGroup(
            addMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addMaintenanceCrewEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel94)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addMaintenanceCrewEditLayout.setVerticalGroup(
            addMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel94)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableRegularActivityEditMaterials.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Quantity", "Unit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityEditMaterials.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(tableRegularActivityEditMaterials);

        addCrewMaterials.setVisible(false);
        addCrewMaterialsEdit.setBackground(new java.awt.Color(255, 255, 255));
        addCrewMaterialsEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addCrewMaterialsEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCrewMaterialsEditMouseClicked(evt);
            }
        });

        jLabel95.setBackground(new java.awt.Color(51, 51, 51));
        jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(51, 51, 51));
        jLabel95.setText("Add");

        javax.swing.GroupLayout addCrewMaterialsEditLayout = new javax.swing.GroupLayout(addCrewMaterialsEdit);
        addCrewMaterialsEdit.setLayout(addCrewMaterialsEditLayout);
        addCrewMaterialsEditLayout.setHorizontalGroup(
            addCrewMaterialsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewMaterialsEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel95)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addCrewMaterialsEditLayout.setVerticalGroup(
            addCrewMaterialsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewMaterialsEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel95)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addCrewEquipment.setVisible(false);
        addCrewEquipmentEdit.setBackground(new java.awt.Color(255, 255, 255));
        addCrewEquipmentEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addCrewEquipmentEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCrewEquipmentEditMouseClicked(evt);
            }
        });

        jLabel96.setBackground(new java.awt.Color(51, 51, 51));
        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(51, 51, 51));
        jLabel96.setText("Add");

        javax.swing.GroupLayout addCrewEquipmentEditLayout = new javax.swing.GroupLayout(addCrewEquipmentEdit);
        addCrewEquipmentEdit.setLayout(addCrewEquipmentEditLayout);
        addCrewEquipmentEditLayout.setHorizontalGroup(
            addCrewEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewEquipmentEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel96)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addCrewEquipmentEditLayout.setVerticalGroup(
            addCrewEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCrewEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel96)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableRegularActivityEditEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "No. of CD", "Rate Per Day", "Total Wages", "Fuel Consumption (L)", "Cost/L", "Amount", "Lubricant", "Total Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableRegularActivityEditEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane22.setViewportView(tableRegularActivityEditEquipment);
        if (tableRegularActivityEditEquipment.getColumnModel().getColumnCount() > 0) {
            tableRegularActivityEditEquipment.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableRegularActivityEditEquipment.getColumnModel().getColumn(4).setPreferredWidth(140);
        }

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel97.setText("Days of Operation");

        isEditOperationEquipmentTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isEditOperationEquipmentTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isEditOperationEquipmentTableSelected.setText("A. Operation Equipment");
        isEditOperationEquipmentTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isEditOperationEquipmentTableSelectedActionPerformed(evt);
            }
        });

        isEditMaintenanceCrewTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isEditMaintenanceCrewTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isEditMaintenanceCrewTableSelected.setText("B.1 Maintenance Crew");
        isEditMaintenanceCrewTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isEditMaintenanceCrewTableSelectedActionPerformed(evt);
            }
        });

        isEditMaterialsTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isEditMaterialsTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isEditMaterialsTableSelected.setText("B.2 Materials");
        isEditMaterialsTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isEditMaterialsTableSelectedActionPerformed(evt);
            }
        });

        isEditEquipmentTableSelected.setBackground(new java.awt.Color(241, 241, 182));
        isEditEquipmentTableSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        isEditEquipmentTableSelected.setText("B.3 Equipment");
        isEditEquipmentTableSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isEditEquipmentTableSelectedActionPerformed(evt);
            }
        });

        removeOperationEquipment.setVisible(false);
        removeOperationEquipmentEdit.setBackground(new java.awt.Color(255, 255, 255));
        removeOperationEquipmentEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeOperationEquipmentEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeOperationEquipmentEditMouseClicked(evt);
            }
        });

        jLabel98.setBackground(new java.awt.Color(51, 51, 51));
        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(51, 51, 51));
        jLabel98.setText("Remove");

        javax.swing.GroupLayout removeOperationEquipmentEditLayout = new javax.swing.GroupLayout(removeOperationEquipmentEdit);
        removeOperationEquipmentEdit.setLayout(removeOperationEquipmentEditLayout);
        removeOperationEquipmentEditLayout.setHorizontalGroup(
            removeOperationEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOperationEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel98)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeOperationEquipmentEditLayout.setVerticalGroup(
            removeOperationEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOperationEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel98)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editOperationEquipment.setVisible(false);
        editOperationEquipmentEdit.setBackground(new java.awt.Color(255, 255, 255));
        editOperationEquipmentEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editOperationEquipmentEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editOperationEquipmentEditMouseClicked(evt);
            }
        });

        jLabel99.setBackground(new java.awt.Color(51, 51, 51));
        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(51, 51, 51));
        jLabel99.setText("Edit");

        javax.swing.GroupLayout editOperationEquipmentEditLayout = new javax.swing.GroupLayout(editOperationEquipmentEdit);
        editOperationEquipmentEdit.setLayout(editOperationEquipmentEditLayout);
        editOperationEquipmentEditLayout.setHorizontalGroup(
            editOperationEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOperationEquipmentEditLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel99)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editOperationEquipmentEditLayout.setVerticalGroup(
            editOperationEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOperationEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel99)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editMaintenanceCrew.setVisible(false);
        editMaintenanceCrewEdit.setBackground(new java.awt.Color(255, 255, 255));
        editMaintenanceCrewEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editMaintenanceCrewEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMaintenanceCrewEditMouseClicked(evt);
            }
        });

        jLabel100.setBackground(new java.awt.Color(51, 51, 51));
        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(51, 51, 51));
        jLabel100.setText("Edit");

        javax.swing.GroupLayout editMaintenanceCrewEditLayout = new javax.swing.GroupLayout(editMaintenanceCrewEdit);
        editMaintenanceCrewEdit.setLayout(editMaintenanceCrewEditLayout);
        editMaintenanceCrewEditLayout.setHorizontalGroup(
            editMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editMaintenanceCrewEditLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel100)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editMaintenanceCrewEditLayout.setVerticalGroup(
            editMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeMaintenanceCrew.setVisible(false);
        removeMaintenanceCrewEdit.setBackground(new java.awt.Color(255, 255, 255));
        removeMaintenanceCrewEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeMaintenanceCrewEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeMaintenanceCrewEditMouseClicked(evt);
            }
        });

        jLabel101.setBackground(new java.awt.Color(51, 51, 51));
        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(51, 51, 51));
        jLabel101.setText("Remove");

        javax.swing.GroupLayout removeMaintenanceCrewEditLayout = new javax.swing.GroupLayout(removeMaintenanceCrewEdit);
        removeMaintenanceCrewEdit.setLayout(removeMaintenanceCrewEditLayout);
        removeMaintenanceCrewEditLayout.setHorizontalGroup(
            removeMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel101)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeMaintenanceCrewEditLayout.setVerticalGroup(
            removeMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel101)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editCrewMaterials.setVisible(false);
        editCrewMaterialsEdit.setBackground(new java.awt.Color(255, 255, 255));
        editCrewMaterialsEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editCrewMaterialsEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editCrewMaterialsEditMouseClicked(evt);
            }
        });

        jLabel102.setBackground(new java.awt.Color(51, 51, 51));
        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(51, 51, 51));
        jLabel102.setText("Edit");

        javax.swing.GroupLayout editCrewMaterialsEditLayout = new javax.swing.GroupLayout(editCrewMaterialsEdit);
        editCrewMaterialsEdit.setLayout(editCrewMaterialsEditLayout);
        editCrewMaterialsEditLayout.setHorizontalGroup(
            editCrewMaterialsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewMaterialsEditLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel102)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editCrewMaterialsEditLayout.setVerticalGroup(
            editCrewMaterialsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewMaterialsEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel102)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeCrewMaterials.setVisible(false);
        removeCrewMaterialsEdit.setBackground(new java.awt.Color(255, 255, 255));
        removeCrewMaterialsEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeCrewMaterialsEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeCrewMaterialsEditMouseClicked(evt);
            }
        });

        jLabel103.setBackground(new java.awt.Color(51, 51, 51));
        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(51, 51, 51));
        jLabel103.setText("Remove");

        javax.swing.GroupLayout removeCrewMaterialsEditLayout = new javax.swing.GroupLayout(removeCrewMaterialsEdit);
        removeCrewMaterialsEdit.setLayout(removeCrewMaterialsEditLayout);
        removeCrewMaterialsEditLayout.setHorizontalGroup(
            removeCrewMaterialsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewMaterialsEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeCrewMaterialsEditLayout.setVerticalGroup(
            removeCrewMaterialsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewMaterialsEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editCrewEquipment.setVisible(false);
        editCrewEquipmentEdit.setBackground(new java.awt.Color(255, 255, 255));
        editCrewEquipmentEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editCrewEquipmentEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editCrewEquipmentEditMouseClicked(evt);
            }
        });

        jLabel104.setBackground(new java.awt.Color(51, 51, 51));
        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(51, 51, 51));
        jLabel104.setText("Edit");

        javax.swing.GroupLayout editCrewEquipmentEditLayout = new javax.swing.GroupLayout(editCrewEquipmentEdit);
        editCrewEquipmentEdit.setLayout(editCrewEquipmentEditLayout);
        editCrewEquipmentEditLayout.setHorizontalGroup(
            editCrewEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewEquipmentEditLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel104)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editCrewEquipmentEditLayout.setVerticalGroup(
            editCrewEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCrewEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel104)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeCrewEquipment.setVisible(false);
        removeCrewEquipmentEdit.setBackground(new java.awt.Color(255, 255, 255));
        removeCrewEquipmentEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeCrewEquipmentEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeCrewEquipmentEditMouseClicked(evt);
            }
        });

        jLabel105.setBackground(new java.awt.Color(51, 51, 51));
        jLabel105.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(51, 51, 51));
        jLabel105.setText("Remove");

        javax.swing.GroupLayout removeCrewEquipmentEditLayout = new javax.swing.GroupLayout(removeCrewEquipmentEdit);
        removeCrewEquipmentEdit.setLayout(removeCrewEquipmentEditLayout);
        removeCrewEquipmentEditLayout.setHorizontalGroup(
            removeCrewEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel105)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeCrewEquipmentEditLayout.setVerticalGroup(
            removeCrewEquipmentEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeCrewEquipmentEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel105)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        regularActivityEditMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel106.setText("Year");

        regularActivitySubActivitySelectionBox.setEnabled(false);
        regularActivityEditSubActivitySelectionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose sub activity..." }));

        regularActivitySubActivityTitle1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        regularActivitySubActivityTitle1.setText("Sub Activity");

        jLabel107.setForeground(new java.awt.Color(0, 0, 204));
        jLabel107.setText("Note: Please remember to select the checkbox on the left side of the table title if using the table, and uncheck it if not.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel107)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel93)
                            .addComponent(isEditOperationEquipmentTableSelected))
                        .addGap(694, 694, 694))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane19)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 168, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(isEditMaintenanceCrewTableSelected)
                                        .addComponent(isEditMaterialsTableSelected)
                                        .addComponent(isEditEquipmentTableSelected))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(editMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(editCrewMaterialsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeCrewMaterialsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addCrewMaterialsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(editCrewEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeCrewEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addCrewEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(editOperationEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeOperationEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addOperationEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel86)
                                    .addComponent(regularActivityEditActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(regularActivitySubActivityTitle1)
                                    .addComponent(regularActivityEditSubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel92)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel87)
                                        .addComponent(regularActivityEditRoadSection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel88)
                                        .addComponent(regularActivityEditLocation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(regularActivityEditOtherRoadSection, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(regularActivityEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel89))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel106)
                                            .addComponent(regularActivityEditYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel97)
                                    .addComponent(regularActivityEditDaysOfOperation, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(regularActivityEditImplementationMode, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                        .addGap(36, 36, 36))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel89)
                        .addComponent(jLabel106))
                    .addComponent(jLabel86))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regularActivityEditActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regularActivityEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regularActivityEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(regularActivitySubActivityTitle1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(regularActivityEditSubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel88)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityEditLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel90)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityEditImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel97)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regularActivityEditDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(regularActivityEditRoadSection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel92)
                .addGap(10, 10, 10)
                .addComponent(regularActivityEditOtherRoadSection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel107)
                .addGap(18, 18, 18)
                .addComponent(isEditOperationEquipmentTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addOperationEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editOperationEquipmentEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(removeOperationEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jLabel93)
                .addGap(30, 30, 30)
                .addComponent(isEditMaintenanceCrewTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(isEditMaterialsTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCrewMaterialsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editCrewMaterialsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeCrewMaterialsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(isEditEquipmentTableSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCrewEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editCrewEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeCrewEquipmentEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );

        editRegularActivityScrollPane.setViewportView(jPanel5);

        javax.swing.GroupLayout editRegularActivityPanelLayout = new javax.swing.GroupLayout(editRegularActivityPanel);
        editRegularActivityPanel.setLayout(editRegularActivityPanelLayout);
        editRegularActivityPanelLayout.setHorizontalGroup(
            editRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editRegularActivityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelEditRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveEditRegularActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(editRegularActivityScrollPane)
        );
        editRegularActivityPanelLayout.setVerticalGroup(
            editRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editRegularActivityPanelLayout.createSequentialGroup()
                .addComponent(editRegularActivityScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editRegularActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveEditRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelEditRegularActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        regularActivityTab.add(editRegularActivityPanel, "card2");

        activityTabbedPane.addTab("Regular Activity", regularActivityTab);

        otherActivityTab.setLayout(new java.awt.CardLayout());

        tableMainOtherActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Sub Activity", "Description", "No. of CD", "Date", "Implementation Mode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMainOtherActivity.setRowHeight(24);
        tableMainOtherActivity.setShowVerticalLines(false);
        tableMainOtherActivity.getTableHeader().setReorderingAllowed(false);
        jScrollPane23.setViewportView(tableMainOtherActivity);
        if (tableMainOtherActivity.getColumnModel().getColumnCount() > 0) {
            tableMainOtherActivity.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableMainOtherActivity.getColumnModel().getColumn(3).setPreferredWidth(70);
        }

        deleteOtherActivity.setBackground(new java.awt.Color(255, 51, 51));
        deleteOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteOtherActivityMouseClicked(evt);
            }
        });

        jLabel108.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 255, 255));
        jLabel108.setText("Delete");

        javax.swing.GroupLayout deleteOtherActivityLayout = new javax.swing.GroupLayout(deleteOtherActivity);
        deleteOtherActivity.setLayout(deleteOtherActivityLayout);
        deleteOtherActivityLayout.setHorizontalGroup(
            deleteOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteOtherActivityLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel108)
                .addGap(31, 31, 31))
        );
        deleteOtherActivityLayout.setVerticalGroup(
            deleteOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteOtherActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel108)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editOtherActivity.setBackground(new java.awt.Color(0, 102, 204));
        editOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editOtherActivityMouseClicked(evt);
            }
        });

        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setText("Edit");

        javax.swing.GroupLayout editOtherActivityLayout = new javax.swing.GroupLayout(editOtherActivity);
        editOtherActivity.setLayout(editOtherActivityLayout);
        editOtherActivityLayout.setHorizontalGroup(
            editOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherActivityLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel109)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editOtherActivityLayout.setVerticalGroup(
            editOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editOtherActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel109)
                .addContainerGap())
        );

        viewOtherActivity.setBackground(new java.awt.Color(255, 102, 0));
        viewOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewOtherActivityMouseClicked(evt);
            }
        });

        jLabel110.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setText("View");

        javax.swing.GroupLayout viewOtherActivityLayout = new javax.swing.GroupLayout(viewOtherActivity);
        viewOtherActivity.setLayout(viewOtherActivityLayout);
        viewOtherActivityLayout.setHorizontalGroup(
            viewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewOtherActivityLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel110)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        viewOtherActivityLayout.setVerticalGroup(
            viewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewOtherActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel110)
                .addContainerGap())
        );

        addNewOtherActivity.setBackground(new java.awt.Color(0, 204, 0));
        addNewOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewOtherActivityMouseClicked(evt);
            }
        });

        jLabel111.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setText("Add New");

        javax.swing.GroupLayout addNewOtherActivityLayout = new javax.swing.GroupLayout(addNewOtherActivity);
        addNewOtherActivity.setLayout(addNewOtherActivityLayout);
        addNewOtherActivityLayout.setHorizontalGroup(
            addNewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewOtherActivityLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel111)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        addNewOtherActivityLayout.setVerticalGroup(
            addNewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewOtherActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel111)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainOtherActivityPanelLayout = new javax.swing.GroupLayout(mainOtherActivityPanel);
        mainOtherActivityPanel.setLayout(mainOtherActivityPanelLayout);
        mainOtherActivityPanelLayout.setHorizontalGroup(
            mainOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainOtherActivityPanelLayout.createSequentialGroup()
                .addContainerGap(575, Short.MAX_VALUE)
                .addComponent(deleteOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainOtherActivityPanelLayout.setVerticalGroup(
            mainOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainOtherActivityPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherActivityTab.add(mainOtherActivityPanel, "card2");

        cancelNewOtherActivity.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherActivityMouseClicked(evt);
            }
        });

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 255, 255));
        jLabel112.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherActivityLayout = new javax.swing.GroupLayout(cancelNewOtherActivity);
        cancelNewOtherActivity.setLayout(cancelNewOtherActivityLayout);
        cancelNewOtherActivityLayout.setHorizontalGroup(
            cancelNewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherActivityLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel112)
                .addGap(24, 24, 24))
        );
        cancelNewOtherActivityLayout.setVerticalGroup(
            cancelNewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel112)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveNewOtherActivity.setBackground(new java.awt.Color(0, 204, 0));
        saveNewOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNewOtherActivityMouseClicked(evt);
            }
        });

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 255, 255));
        jLabel113.setText("Save");

        javax.swing.GroupLayout saveNewOtherActivityLayout = new javax.swing.GroupLayout(saveNewOtherActivity);
        saveNewOtherActivity.setLayout(saveNewOtherActivityLayout);
        saveNewOtherActivityLayout.setHorizontalGroup(
            saveNewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherActivityLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel113)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveNewOtherActivityLayout.setVerticalGroup(
            saveNewOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel113)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addNewOtherActivityScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        addNewOtherActivityScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel6.setBackground(new java.awt.Color(241, 241, 182));

        jLabel117.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel117.setText("Month");

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel118.setText("Implementation Mode");

        jLabel120.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel120.setText("Description");

        tableOtherActivityMaintenanceCrew.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableOtherActivityMaintenanceCrew.getTableHeader().setReorderingAllowed(false);
        jScrollPane26.setViewportView(tableOtherActivityMaintenanceCrew);
        if (tableOtherActivityMaintenanceCrew.getColumnModel().getColumnCount() > 0) {
            tableOtherActivityMaintenanceCrew.getColumnModel().getColumn(3).setHeaderValue("Total Wages");
        }

        addMaintenanceCrew.setVisible(false);
        addOtherMaintenanceCrew.setBackground(new java.awt.Color(255, 255, 255));
        addOtherMaintenanceCrew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addOtherMaintenanceCrew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addOtherMaintenanceCrewMouseClicked(evt);
            }
        });

        jLabel122.setBackground(new java.awt.Color(51, 51, 51));
        jLabel122.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(51, 51, 51));
        jLabel122.setText("Add");

        javax.swing.GroupLayout addOtherMaintenanceCrewLayout = new javax.swing.GroupLayout(addOtherMaintenanceCrew);
        addOtherMaintenanceCrew.setLayout(addOtherMaintenanceCrewLayout);
        addOtherMaintenanceCrewLayout.setHorizontalGroup(
            addOtherMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOtherMaintenanceCrewLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel122)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addOtherMaintenanceCrewLayout.setVerticalGroup(
            addOtherMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOtherMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel122)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel125.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel125.setText("Days of Operation");

        otherActivityFormDaysOfOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherActivityFormDaysOfOperationActionPerformed(evt);
            }
        });

        editMaintenanceCrew.setVisible(false);
        editOtherMaintenanceCrew.setBackground(new java.awt.Color(255, 255, 255));
        editOtherMaintenanceCrew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editOtherMaintenanceCrew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editOtherMaintenanceCrewMouseClicked(evt);
            }
        });

        jLabel128.setBackground(new java.awt.Color(51, 51, 51));
        jLabel128.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(51, 51, 51));
        jLabel128.setText("Edit");

        javax.swing.GroupLayout editOtherMaintenanceCrewLayout = new javax.swing.GroupLayout(editOtherMaintenanceCrew);
        editOtherMaintenanceCrew.setLayout(editOtherMaintenanceCrewLayout);
        editOtherMaintenanceCrewLayout.setHorizontalGroup(
            editOtherMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherMaintenanceCrewLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel128)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editOtherMaintenanceCrewLayout.setVerticalGroup(
            editOtherMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel128)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeMaintenanceCrew.setVisible(false);
        removeOtherMaintenanceCrew.setBackground(new java.awt.Color(255, 255, 255));
        removeOtherMaintenanceCrew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeOtherMaintenanceCrew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeOtherMaintenanceCrewMouseClicked(evt);
            }
        });

        jLabel129.setBackground(new java.awt.Color(51, 51, 51));
        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(51, 51, 51));
        jLabel129.setText("Remove");

        javax.swing.GroupLayout removeOtherMaintenanceCrewLayout = new javax.swing.GroupLayout(removeOtherMaintenanceCrew);
        removeOtherMaintenanceCrew.setLayout(removeOtherMaintenanceCrewLayout);
        removeOtherMaintenanceCrewLayout.setHorizontalGroup(
            removeOtherMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOtherMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel129)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeOtherMaintenanceCrewLayout.setVerticalGroup(
            removeOtherMaintenanceCrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOtherMaintenanceCrewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel129)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        otherActivityFormMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel134.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel134.setText("Year");

        regularActivitySubActivitySelectionBox.setEnabled(false);
        otherActivityFormSubActivitySelectionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose sub activity..." }));

        regularActivitySubActivityTitle2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        regularActivitySubActivityTitle2.setText("Sub Activity");

        jLabel136.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel136.setText("Personnel");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane26)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editOtherMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeOtherMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addOtherMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regularActivitySubActivityTitle2)
                                    .addComponent(otherActivityFormSubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel120)
                                    .addComponent(otherActivityFormDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 472, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(otherActivityFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel117))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel134)
                                            .addComponent(otherActivityFormYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel125)
                                    .addComponent(otherActivityFormDaysOfOperation, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(otherActivityFormImplementationMode, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel136)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel117)
                            .addComponent(jLabel134))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(otherActivityFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(otherActivityFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel118)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherActivityFormImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(regularActivitySubActivityTitle2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(otherActivityFormSubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel120)
                        .addGap(10, 10, 10)
                        .addComponent(otherActivityFormDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel125)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherActivityFormDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jLabel136)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addOtherMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editOtherMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeOtherMaintenanceCrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );

        addNewOtherActivityScrollPane.setViewportView(jPanel6);

        javax.swing.GroupLayout addNewOtherActivityPanelLayout = new javax.swing.GroupLayout(addNewOtherActivityPanel);
        addNewOtherActivityPanel.setLayout(addNewOtherActivityPanelLayout);
        addNewOtherActivityPanelLayout.setHorizontalGroup(
            addNewOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewOtherActivityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveNewOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(addNewOtherActivityScrollPane)
        );
        addNewOtherActivityPanelLayout.setVerticalGroup(
            addNewOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewOtherActivityPanelLayout.createSequentialGroup()
                .addComponent(addNewOtherActivityScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveNewOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherActivityTab.add(addNewOtherActivityPanel, "card2");

        viewOtherActivityScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        backViewRegularActivity1.setBackground(new java.awt.Color(0, 102, 204));
        backViewRegularActivity1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backViewRegularActivity1MouseClicked(evt);
            }
        });

        jLabel114.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setText("Back");

        javax.swing.GroupLayout backViewRegularActivity1Layout = new javax.swing.GroupLayout(backViewRegularActivity1);
        backViewRegularActivity1.setLayout(backViewRegularActivity1Layout);
        backViewRegularActivity1Layout.setHorizontalGroup(
            backViewRegularActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backViewRegularActivity1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel114)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        backViewRegularActivity1Layout.setVerticalGroup(
            backViewRegularActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backViewRegularActivity1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel114)
                .addContainerGap())
        );

        jLabel115.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel115.setText("Sub Activity");

        otherActivityViewSubActivityName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherActivityViewSubActivityName.setText("Sub Activity Name");

        jLabel116.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel116.setText("Description");

        otherActivityViewDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherActivityViewDescription.setText("Description");

        jLabel119.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel119.setText("Days of Operation");

        otherActivityViewDaysOfOperation.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherActivityViewDaysOfOperation.setText("# CD");

        jLabel123.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel123.setText("Date");

        otherActivityViewDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherActivityViewDate.setText("Month Year");

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel124.setText("Mode of Implementation");

        otherActivityViewImplementationMode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherActivityViewImplementationMode.setText("implementation");

        tableOtherActivityViewPersonnel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ));
        tableOtherActivityViewPersonnel.getTableHeader().setReorderingAllowed(false);
        jScrollPane28.setViewportView(tableOtherActivityViewPersonnel);
        if (tableOtherActivityViewPersonnel.getColumnModel().getColumnCount() > 0) {
            tableOtherActivityViewPersonnel.getColumnModel().getColumn(1).setHeaderValue("Type");
            tableOtherActivityViewPersonnel.getColumnModel().getColumn(2).setHeaderValue("Operator Name");
        }

        jLabel130.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel130.setText("Personnel");

        jLabel135.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel135.setText("Total Expenses:");

        otherActivityViewPersonnelTotalExpenses.setText("0.00");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel115)
                            .addComponent(otherActivityViewSubActivityName)
                            .addComponent(jLabel116)
                            .addComponent(otherActivityViewDescription))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123)
                            .addComponent(otherActivityViewDate)
                            .addComponent(jLabel124)
                            .addComponent(otherActivityViewImplementationMode))
                        .addGap(51, 51, 51))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel119)
                            .addComponent(otherActivityViewDaysOfOperation)
                            .addComponent(jLabel130))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel135)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otherActivityViewPersonnelTotalExpenses))
                            .addComponent(jScrollPane28, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel115)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherActivityViewSubActivityName)
                    .addComponent(otherActivityViewDate))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel116)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherActivityViewDescription))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel124)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherActivityViewImplementationMode)))
                .addGap(18, 18, 18)
                .addComponent(jLabel119)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherActivityViewDaysOfOperation)
                .addGap(55, 55, 55)
                .addComponent(jLabel130)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel135)
                    .addComponent(otherActivityViewPersonnelTotalExpenses))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        viewOtherActivityScrollPane.setViewportView(jPanel7);

        exportOtherActivity.setBackground(new java.awt.Color(0, 102, 102));
        exportOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportOtherActivityMouseClicked(evt);
            }
        });

        jLabel249.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel249.setForeground(new java.awt.Color(255, 255, 255));
        jLabel249.setText("Export");

        javax.swing.GroupLayout exportOtherActivityLayout = new javax.swing.GroupLayout(exportOtherActivity);
        exportOtherActivity.setLayout(exportOtherActivityLayout);
        exportOtherActivityLayout.setHorizontalGroup(
            exportOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportOtherActivityLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel249)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        exportOtherActivityLayout.setVerticalGroup(
            exportOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportOtherActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel249)
                .addContainerGap())
        );

        javax.swing.GroupLayout viewOtherActivityPanelLayout = new javax.swing.GroupLayout(viewOtherActivityPanel);
        viewOtherActivityPanel.setLayout(viewOtherActivityPanelLayout);
        viewOtherActivityPanelLayout.setHorizontalGroup(
            viewOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewOtherActivityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exportOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backViewRegularActivity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(viewOtherActivityScrollPane)
        );
        viewOtherActivityPanelLayout.setVerticalGroup(
            viewOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewOtherActivityPanelLayout.createSequentialGroup()
                .addComponent(viewOtherActivityScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(viewOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backViewRegularActivity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherActivityTab.add(viewOtherActivityPanel, "card2");

        editOtherActivityScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        cancelNewOtherActivity1.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherActivity1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherActivity1MouseClicked(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(255, 255, 255));
        jLabel121.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherActivity1Layout = new javax.swing.GroupLayout(cancelNewOtherActivity1);
        cancelNewOtherActivity1.setLayout(cancelNewOtherActivity1Layout);
        cancelNewOtherActivity1Layout.setHorizontalGroup(
            cancelNewOtherActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherActivity1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel121)
                .addGap(24, 24, 24))
        );
        cancelNewOtherActivity1Layout.setVerticalGroup(
            cancelNewOtherActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherActivity1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel121)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveEditedOtherActivity.setBackground(new java.awt.Color(0, 204, 0));
        saveEditedOtherActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveEditedOtherActivityMouseClicked(evt);
            }
        });

        jLabel126.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setText("Save");

        javax.swing.GroupLayout saveEditedOtherActivityLayout = new javax.swing.GroupLayout(saveEditedOtherActivity);
        saveEditedOtherActivity.setLayout(saveEditedOtherActivityLayout);
        saveEditedOtherActivityLayout.setHorizontalGroup(
            saveEditedOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveEditedOtherActivityLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel126)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveEditedOtherActivityLayout.setVerticalGroup(
            saveEditedOtherActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveEditedOtherActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel126)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editOtherActivityScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel8.setBackground(new java.awt.Color(241, 241, 182));

        jLabel127.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel127.setText("Month");

        jLabel131.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel131.setText("Implementation Mode");

        jLabel132.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel132.setText("Description");

        tableOtherActivityEditPersonnel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableOtherActivityEditPersonnel.getTableHeader().setReorderingAllowed(false);
        jScrollPane29.setViewportView(tableOtherActivityEditPersonnel);

        addMaintenanceCrew.setVisible(false);
        addOtherMaintenanceCrewEdit.setBackground(new java.awt.Color(255, 255, 255));
        addOtherMaintenanceCrewEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addOtherMaintenanceCrewEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addOtherMaintenanceCrewEditMouseClicked(evt);
            }
        });

        jLabel133.setBackground(new java.awt.Color(51, 51, 51));
        jLabel133.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(51, 51, 51));
        jLabel133.setText("Add");

        javax.swing.GroupLayout addOtherMaintenanceCrewEditLayout = new javax.swing.GroupLayout(addOtherMaintenanceCrewEdit);
        addOtherMaintenanceCrewEdit.setLayout(addOtherMaintenanceCrewEditLayout);
        addOtherMaintenanceCrewEditLayout.setHorizontalGroup(
            addOtherMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOtherMaintenanceCrewEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel133)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addOtherMaintenanceCrewEditLayout.setVerticalGroup(
            addOtherMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addOtherMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel133)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel137.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel137.setText("Days of Operation");

        otherActivityEditDaysOfOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherActivityEditDaysOfOperationActionPerformed(evt);
            }
        });

        editMaintenanceCrew.setVisible(false);
        editOtherMaintenanceCrewEdit.setBackground(new java.awt.Color(255, 255, 255));
        editOtherMaintenanceCrewEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editOtherMaintenanceCrewEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editOtherMaintenanceCrewEditMouseClicked(evt);
            }
        });

        jLabel138.setBackground(new java.awt.Color(51, 51, 51));
        jLabel138.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(51, 51, 51));
        jLabel138.setText("Edit");

        javax.swing.GroupLayout editOtherMaintenanceCrewEditLayout = new javax.swing.GroupLayout(editOtherMaintenanceCrewEdit);
        editOtherMaintenanceCrewEdit.setLayout(editOtherMaintenanceCrewEditLayout);
        editOtherMaintenanceCrewEditLayout.setHorizontalGroup(
            editOtherMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherMaintenanceCrewEditLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel138)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editOtherMaintenanceCrewEditLayout.setVerticalGroup(
            editOtherMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel138)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeMaintenanceCrew.setVisible(false);
        removeOtherMaintenanceCrewEdit.setBackground(new java.awt.Color(255, 255, 255));
        removeOtherMaintenanceCrewEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeOtherMaintenanceCrewEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeOtherMaintenanceCrewEditMouseClicked(evt);
            }
        });

        jLabel139.setBackground(new java.awt.Color(51, 51, 51));
        jLabel139.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(51, 51, 51));
        jLabel139.setText("Remove");

        javax.swing.GroupLayout removeOtherMaintenanceCrewEditLayout = new javax.swing.GroupLayout(removeOtherMaintenanceCrewEdit);
        removeOtherMaintenanceCrewEdit.setLayout(removeOtherMaintenanceCrewEditLayout);
        removeOtherMaintenanceCrewEditLayout.setHorizontalGroup(
            removeOtherMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOtherMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel139)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeOtherMaintenanceCrewEditLayout.setVerticalGroup(
            removeOtherMaintenanceCrewEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeOtherMaintenanceCrewEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel139)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        otherActivityEditMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel140.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel140.setText("Year");

        regularActivitySubActivitySelectionBox.setEnabled(false);
        otherActivityEditSubActivitySelectionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose sub activity..." }));

        regularActivitySubActivityTitle3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        regularActivitySubActivityTitle3.setText("Sub Activity");

        jLabel141.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel141.setText("Personnel");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane29)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editOtherMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeOtherMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addOtherMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regularActivitySubActivityTitle3)
                                    .addComponent(otherActivityEditSubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel132)
                                    .addComponent(otherActivityEditDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 472, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(otherActivityEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel127))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel140)
                                            .addComponent(otherActivityEditYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel137)
                                    .addComponent(otherActivityEditDaysOfOperation, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(otherActivityEditImplementationMode, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel141)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel127)
                            .addComponent(jLabel140))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(otherActivityEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(otherActivityEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel131)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherActivityEditImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(regularActivitySubActivityTitle3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(otherActivityEditSubActivitySelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel132)
                        .addGap(10, 10, 10)
                        .addComponent(otherActivityEditDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel137)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherActivityEditDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jLabel141)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addOtherMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editOtherMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeOtherMaintenanceCrewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );

        editOtherActivityScrollPane.setViewportView(jPanel8);

        javax.swing.GroupLayout editOtherActivityPanelLayout = new javax.swing.GroupLayout(editOtherActivityPanel);
        editOtherActivityPanel.setLayout(editOtherActivityPanelLayout);
        editOtherActivityPanelLayout.setHorizontalGroup(
            editOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editOtherActivityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherActivity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveEditedOtherActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(editOtherActivityScrollPane)
        );
        editOtherActivityPanelLayout.setVerticalGroup(
            editOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherActivityPanelLayout.createSequentialGroup()
                .addComponent(editOtherActivityScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editOtherActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveEditedOtherActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherActivity1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherActivityTab.add(editOtherActivityPanel, "card2");

        activityTabbedPane.addTab("Other Activity", otherActivityTab);

        otherExpensesTab.setLayout(new java.awt.CardLayout());

        tableMainOtherExpenses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Description", "Labor Crew Cost", "Labor Equipment Cost", "No. of CD", "Date", "Implementation Mode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMainOtherExpenses.setRowHeight(24);
        tableMainOtherExpenses.setShowVerticalLines(false);
        jScrollPane30.setViewportView(tableMainOtherExpenses);
        if (tableMainOtherExpenses.getColumnModel().getColumnCount() > 0) {
            tableMainOtherExpenses.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableMainOtherExpenses.getColumnModel().getColumn(4).setPreferredWidth(70);
            tableMainOtherExpenses.getColumnModel().getColumn(4).setHeaderValue("No. of CD");
            tableMainOtherExpenses.getColumnModel().getColumn(5).setHeaderValue("Date");
            tableMainOtherExpenses.getColumnModel().getColumn(6).setHeaderValue("Implementation Mode");
        }

        deleteOtherExpenses.setBackground(new java.awt.Color(255, 51, 51));
        deleteOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteOtherExpensesMouseClicked(evt);
            }
        });

        jLabel142.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setText("Delete");

        javax.swing.GroupLayout deleteOtherExpensesLayout = new javax.swing.GroupLayout(deleteOtherExpenses);
        deleteOtherExpenses.setLayout(deleteOtherExpensesLayout);
        deleteOtherExpensesLayout.setHorizontalGroup(
            deleteOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel142)
                .addGap(31, 31, 31))
        );
        deleteOtherExpensesLayout.setVerticalGroup(
            deleteOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteOtherExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel142)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editOtherExpenses.setBackground(new java.awt.Color(0, 102, 204));
        editOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editOtherExpensesMouseClicked(evt);
            }
        });

        jLabel143.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setText("Edit");

        javax.swing.GroupLayout editOtherExpensesLayout = new javax.swing.GroupLayout(editOtherExpenses);
        editOtherExpenses.setLayout(editOtherExpensesLayout);
        editOtherExpensesLayout.setHorizontalGroup(
            editOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherExpensesLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel143)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editOtherExpensesLayout.setVerticalGroup(
            editOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel143)
                .addContainerGap())
        );

        viewOtherExpenses.setBackground(new java.awt.Color(255, 102, 0));
        viewOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewOtherExpensesMouseClicked(evt);
            }
        });

        jLabel144.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setText("View");

        javax.swing.GroupLayout viewOtherExpensesLayout = new javax.swing.GroupLayout(viewOtherExpenses);
        viewOtherExpenses.setLayout(viewOtherExpensesLayout);
        viewOtherExpensesLayout.setHorizontalGroup(
            viewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewOtherExpensesLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel144)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        viewOtherExpensesLayout.setVerticalGroup(
            viewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel144)
                .addContainerGap())
        );

        addNewOtherExpenses.setBackground(new java.awt.Color(0, 204, 0));
        addNewOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewOtherExpensesMouseClicked(evt);
            }
        });

        jLabel145.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("Add New");

        javax.swing.GroupLayout addNewOtherExpensesLayout = new javax.swing.GroupLayout(addNewOtherExpenses);
        addNewOtherExpenses.setLayout(addNewOtherExpensesLayout);
        addNewOtherExpensesLayout.setHorizontalGroup(
            addNewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewOtherExpensesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel145)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        addNewOtherExpensesLayout.setVerticalGroup(
            addNewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel145)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainOtherExpensesPanelLayout = new javax.swing.GroupLayout(mainOtherExpensesPanel);
        mainOtherExpensesPanel.setLayout(mainOtherExpensesPanelLayout);
        mainOtherExpensesPanelLayout.setHorizontalGroup(
            mainOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainOtherExpensesPanelLayout.createSequentialGroup()
                .addContainerGap(575, Short.MAX_VALUE)
                .addComponent(deleteOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane30)
        );
        mainOtherExpensesPanelLayout.setVerticalGroup(
            mainOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainOtherExpensesPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherExpensesTab.add(mainOtherExpensesPanel, "card2");

        cancelNewOtherExpenses.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherExpensesMouseClicked(evt);
            }
        });

        jLabel146.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherExpensesLayout = new javax.swing.GroupLayout(cancelNewOtherExpenses);
        cancelNewOtherExpenses.setLayout(cancelNewOtherExpensesLayout);
        cancelNewOtherExpensesLayout.setHorizontalGroup(
            cancelNewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel146)
                .addGap(24, 24, 24))
        );
        cancelNewOtherExpensesLayout.setVerticalGroup(
            cancelNewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel146)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveNewOtherExpenses.setBackground(new java.awt.Color(0, 204, 0));
        saveNewOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNewOtherExpensesMouseClicked(evt);
            }
        });

        jLabel147.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setText("Save");

        javax.swing.GroupLayout saveNewOtherExpensesLayout = new javax.swing.GroupLayout(saveNewOtherExpenses);
        saveNewOtherExpenses.setLayout(saveNewOtherExpensesLayout);
        saveNewOtherExpensesLayout.setHorizontalGroup(
            saveNewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherExpensesLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel147)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveNewOtherExpensesLayout.setVerticalGroup(
            saveNewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel147)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addNewOtherExpensesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel9.setBackground(new java.awt.Color(241, 241, 182));

        jLabel148.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel148.setText("Month");

        jLabel149.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel149.setText("Implementation Mode");

        jLabel150.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel150.setText("Description");

        jLabel152.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel152.setText("Days of Operation");

        otherExpensesFormDaysOfOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherExpensesFormDaysOfOperationActionPerformed(evt);
            }
        });

        otherExpensesFormMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel155.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel155.setText("Year");

        jLabel157.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel157.setText("Labor Crew Cost");

        jLabel158.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel158.setText("Labor Equipment Cost");

        jLabel159.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel159.setText("Repair & Maintenance of Transportation Equipment");

        jLabel160.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel160.setText("Indirect Cost Work Expenses");

        jLabel161.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel161.setText("Light Equipments");

        jLabel162.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel162.setText("Heavy Equipments");

        otherExpensesFormDescription.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        otherExpensesFormDescription.setText("Meal Allowance");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel152))
                        .addGap(686, 686, 686))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(otherExpensesFormDaysOfOperation, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(otherExpensesFormImplementationMode, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel150, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel157, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(otherExpensesFormLaborCrewCost, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(otherExpensesFormLaborEquipmentCost, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel158, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                            .addComponent(otherExpensesFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                            .addComponent(jLabel148)
                                            .addGap(165, 165, 165)))
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel155)
                                        .addComponent(otherExpensesFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(otherExpensesFormDescription))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(otherExpensesFormLightEquipments, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel161, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otherExpensesFormHeavyEquipments, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel162, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel160, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel159, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(136, 136, 136))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel148)
                            .addComponent(jLabel155))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(otherExpensesFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(otherExpensesFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel150)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otherExpensesFormDescription)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel157)
                        .addGap(10, 10, 10)
                        .addComponent(otherExpensesFormLaborCrewCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel158))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel160)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel159)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel161)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otherExpensesFormLightEquipments, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel162)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otherExpensesFormHeavyEquipments, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(otherExpensesFormLaborEquipmentCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel149)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherExpensesFormImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel152)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherExpensesFormDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        addNewOtherExpensesScrollPane.setViewportView(jPanel9);

        javax.swing.GroupLayout addNewOtherExpensesPanelLayout = new javax.swing.GroupLayout(addNewOtherExpensesPanel);
        addNewOtherExpensesPanel.setLayout(addNewOtherExpensesPanelLayout);
        addNewOtherExpensesPanelLayout.setHorizontalGroup(
            addNewOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewOtherExpensesPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveNewOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(addNewOtherExpensesScrollPane)
        );
        addNewOtherExpensesPanelLayout.setVerticalGroup(
            addNewOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewOtherExpensesPanelLayout.createSequentialGroup()
                .addComponent(addNewOtherExpensesScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveNewOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherExpensesTab.add(addNewOtherExpensesPanel, "card2");

        backViewOtherExpenses.setBackground(new java.awt.Color(0, 102, 204));
        backViewOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backViewOtherExpensesMouseClicked(evt);
            }
        });

        jLabel175.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel175.setForeground(new java.awt.Color(255, 255, 255));
        jLabel175.setText("Back");

        javax.swing.GroupLayout backViewOtherExpensesLayout = new javax.swing.GroupLayout(backViewOtherExpenses);
        backViewOtherExpenses.setLayout(backViewOtherExpensesLayout);
        backViewOtherExpensesLayout.setHorizontalGroup(
            backViewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backViewOtherExpensesLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel175)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        backViewOtherExpensesLayout.setVerticalGroup(
            backViewOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backViewOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel175)
                .addContainerGap())
        );

        viewOtherExpensesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        viewOtherExpensesScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jLabel176.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel176.setText("Date");

        otherExpensesViewDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewDate.setText("Month Year");

        jLabel177.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel177.setText("Description");

        otherActivityViewDescription1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherActivityViewDescription1.setText("Meal Allowance");

        jLabel178.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel178.setText("Labor Crew Cost");

        otherExpensesViewLaborCrewCost.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewLaborCrewCost.setText("₱ 0.00");

        jLabel181.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel181.setText("Labor Equipment Cost");

        otherExpensesViewLaborEquipmentCost.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewLaborEquipmentCost.setText("₱ 0.00");

        otherExpensesViewImplementationMode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewImplementationMode.setText("implementation");

        jLabel182.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel182.setText("Implementation Mode");

        otherExpensesViewDaysOfOperation.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewDaysOfOperation.setText("# CD");

        jLabel183.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel183.setText("Days of Operation");

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel179.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel179.setText("Indirect Cost Work Expenses");

        jLabel180.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel180.setText("Repair and Maintenance of Transportation Equipment");

        jLabel184.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel184.setText("Light Equipments");

        otherExpensesViewLightEquipments.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewLightEquipments.setText("₱ 0.00");

        jLabel185.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel185.setText("Heavy Equipments");

        otherExpensesViewHeavyEquipments.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherExpensesViewHeavyEquipments.setText("₱ 0.00");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(otherExpensesViewDaysOfOperation)
                    .addComponent(jLabel183)
                    .addComponent(jLabel181)
                    .addComponent(otherExpensesViewLaborEquipmentCost)
                    .addComponent(otherExpensesViewImplementationMode)
                    .addComponent(jLabel182)
                    .addComponent(jLabel176)
                    .addComponent(otherExpensesViewDate)
                    .addComponent(jLabel177)
                    .addComponent(otherActivityViewDescription1)
                    .addComponent(jLabel178)
                    .addComponent(otherExpensesViewLaborCrewCost))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(413, 413, 413)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel179)
                            .addComponent(otherExpensesViewLightEquipments)
                            .addComponent(otherExpensesViewHeavyEquipments)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addComponent(jLabel180))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addComponent(jLabel184))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addComponent(jLabel185)))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel176)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherExpensesViewDate)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel177)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherActivityViewDescription1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel178)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherExpensesViewLaborCrewCost)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel181)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherExpensesViewLaborEquipmentCost)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel182)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherExpensesViewImplementationMode)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel183)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otherExpensesViewDaysOfOperation))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel179)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel180)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel184)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otherExpensesViewLightEquipments)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel185)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otherExpensesViewHeavyEquipments)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        viewOtherExpensesScrollPane.setViewportView(jPanel11);

        javax.swing.GroupLayout viewOtherExpensesPanelLayout = new javax.swing.GroupLayout(viewOtherExpensesPanel);
        viewOtherExpensesPanel.setLayout(viewOtherExpensesPanelLayout);
        viewOtherExpensesPanelLayout.setHorizontalGroup(
            viewOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewOtherExpensesPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backViewOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(viewOtherExpensesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE)
        );
        viewOtherExpensesPanelLayout.setVerticalGroup(
            viewOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewOtherExpensesPanelLayout.createSequentialGroup()
                .addComponent(viewOtherExpensesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backViewOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        otherExpensesTab.add(viewOtherExpensesPanel, "card2");

        cancelEditOtherExpenses.setBackground(new java.awt.Color(255, 102, 0));
        cancelEditOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelEditOtherExpensesMouseClicked(evt);
            }
        });

        jLabel186.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel186.setForeground(new java.awt.Color(255, 255, 255));
        jLabel186.setText("Cancel");

        javax.swing.GroupLayout cancelEditOtherExpensesLayout = new javax.swing.GroupLayout(cancelEditOtherExpenses);
        cancelEditOtherExpenses.setLayout(cancelEditOtherExpensesLayout);
        cancelEditOtherExpensesLayout.setHorizontalGroup(
            cancelEditOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelEditOtherExpensesLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel186)
                .addGap(24, 24, 24))
        );
        cancelEditOtherExpensesLayout.setVerticalGroup(
            cancelEditOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelEditOtherExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel186)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveEditOtherExpenses.setBackground(new java.awt.Color(0, 204, 0));
        saveEditOtherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveEditOtherExpensesMouseClicked(evt);
            }
        });

        jLabel187.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel187.setForeground(new java.awt.Color(255, 255, 255));
        jLabel187.setText("Save");

        javax.swing.GroupLayout saveEditOtherExpensesLayout = new javax.swing.GroupLayout(saveEditOtherExpenses);
        saveEditOtherExpenses.setLayout(saveEditOtherExpensesLayout);
        saveEditOtherExpensesLayout.setHorizontalGroup(
            saveEditOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveEditOtherExpensesLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel187)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveEditOtherExpensesLayout.setVerticalGroup(
            saveEditOtherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveEditOtherExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel187)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editOtherExpensesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel12.setBackground(new java.awt.Color(241, 241, 182));

        jLabel188.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel188.setText("Month");

        jLabel189.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel189.setText("Implementation Mode");

        jLabel190.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel190.setText("Description");

        jLabel191.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel191.setText("Days of Operation");

        otherExpensesFormEditDaysOfOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherExpensesFormEditDaysOfOperationActionPerformed(evt);
            }
        });

        otherExpensesFormEditMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel192.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel192.setText("Year");

        jLabel193.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel193.setText("Labor Crew Cost");

        jLabel194.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel194.setText("Labor Equipment Cost");

        jLabel195.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel195.setText("Repair & Maintenance of Transportation Equipment");

        jLabel196.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel196.setText("Indirect Cost Work Expenses");

        jLabel197.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel197.setText("Light Equipments");

        jLabel198.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel198.setText("Heavy Equipments");

        otherExpensesFormDescription1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        otherExpensesFormDescription1.setText("Meal Allowance");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel189, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel191))
                        .addGap(686, 686, 686))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(otherExpensesFormEditDaysOfOperation, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(otherExpensesFormEditImplementationMode, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel190, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel193, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(otherExpensesFormEditLaborCrewCost, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(otherExpensesFormEditLaborEquipmentCost, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel194, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel12Layout.createSequentialGroup()
                                            .addComponent(otherExpensesFormEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                            .addComponent(jLabel188)
                                            .addGap(165, 165, 165)))
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel192)
                                        .addComponent(otherExpensesFormEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(otherExpensesFormDescription1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(otherExpensesFormEditLightEquipments, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel197, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otherExpensesFormEditHeavyEquipments, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel198, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel196, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel195, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(136, 136, 136))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel188)
                            .addComponent(jLabel192))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(otherExpensesFormEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(otherExpensesFormEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel190)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otherExpensesFormDescription1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel193)
                        .addGap(10, 10, 10)
                        .addComponent(otherExpensesFormEditLaborCrewCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel194))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel196)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel195)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel197)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otherExpensesFormEditLightEquipments, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel198)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otherExpensesFormEditHeavyEquipments, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(otherExpensesFormEditLaborEquipmentCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel189)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherExpensesFormEditImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel191)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otherExpensesFormEditDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        editOtherExpensesScrollPane.setViewportView(jPanel12);

        javax.swing.GroupLayout editOtherExpensesPanelLayout = new javax.swing.GroupLayout(editOtherExpensesPanel);
        editOtherExpensesPanel.setLayout(editOtherExpensesPanelLayout);
        editOtherExpensesPanelLayout.setHorizontalGroup(
            editOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editOtherExpensesPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelEditOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveEditOtherExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(editOtherExpensesScrollPane)
        );
        editOtherExpensesPanelLayout.setVerticalGroup(
            editOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editOtherExpensesPanelLayout.createSequentialGroup()
                .addComponent(editOtherExpensesScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editOtherExpensesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveEditOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelEditOtherExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        otherExpensesTab.add(editOtherExpensesPanel, "card2");

        activityTabbedPane.addTab("Other Expenses", otherExpensesTab);

        jSplitPane1.setMaximumSize(new java.awt.Dimension(1052, 2147483647));

        driversForEngineersContainer.setMinimumSize(new java.awt.Dimension(707, 0));
        driversForEngineersContainer.setLayout(new java.awt.CardLayout());

        deleteDriversForEngineers1.setBackground(new java.awt.Color(255, 51, 51));
        deleteDriversForEngineers1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteDriversForEngineers1MouseClicked(evt);
            }
        });

        jLabel165.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(255, 255, 255));
        jLabel165.setText("Delete");

        javax.swing.GroupLayout deleteDriversForEngineers1Layout = new javax.swing.GroupLayout(deleteDriversForEngineers1);
        deleteDriversForEngineers1.setLayout(deleteDriversForEngineers1Layout);
        deleteDriversForEngineers1Layout.setHorizontalGroup(
            deleteDriversForEngineers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteDriversForEngineers1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel165)
                .addGap(31, 31, 31))
        );
        deleteDriversForEngineers1Layout.setVerticalGroup(
            deleteDriversForEngineers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteDriversForEngineers1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel165)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editDriversForEngineers1.setBackground(new java.awt.Color(0, 102, 204));
        editDriversForEngineers1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editDriversForEngineers1MouseClicked(evt);
            }
        });

        jLabel170.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel170.setForeground(new java.awt.Color(255, 255, 255));
        jLabel170.setText("Edit");

        javax.swing.GroupLayout editDriversForEngineers1Layout = new javax.swing.GroupLayout(editDriversForEngineers1);
        editDriversForEngineers1.setLayout(editDriversForEngineers1Layout);
        editDriversForEngineers1Layout.setHorizontalGroup(
            editDriversForEngineers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editDriversForEngineers1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel170)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editDriversForEngineers1Layout.setVerticalGroup(
            editDriversForEngineers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editDriversForEngineers1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel170)
                .addContainerGap())
        );

        addNewDriversForEngineers1.setBackground(new java.awt.Color(0, 204, 0));
        addNewDriversForEngineers1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewDriversForEngineers1MouseClicked(evt);
            }
        });

        jLabel171.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(255, 255, 255));
        jLabel171.setText("Add New");

        javax.swing.GroupLayout addNewDriversForEngineers1Layout = new javax.swing.GroupLayout(addNewDriversForEngineers1);
        addNewDriversForEngineers1.setLayout(addNewDriversForEngineers1Layout);
        addNewDriversForEngineers1Layout.setHorizontalGroup(
            addNewDriversForEngineers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewDriversForEngineers1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel171)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        addNewDriversForEngineers1Layout.setVerticalGroup(
            addNewDriversForEngineers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewDriversForEngineers1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel171)
                .addContainerGap())
        );

        tableMainDriversForEngineers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Labor Equipment", "Equipment Fuel", "Lubricant", "No. of CD", "Implementation Mode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMainDriversForEngineers.setRowHeight(24);
        tableMainDriversForEngineers.getTableHeader().setReorderingAllowed(false);
        jScrollPane33.setViewportView(tableMainDriversForEngineers);

        javax.swing.GroupLayout mainDriversForEngineersPanelLayout = new javax.swing.GroupLayout(mainDriversForEngineersPanel);
        mainDriversForEngineersPanel.setLayout(mainDriversForEngineersPanelLayout);
        mainDriversForEngineersPanelLayout.setHorizontalGroup(
            mainDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainDriversForEngineersPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteDriversForEngineers1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editDriversForEngineers1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewDriversForEngineers1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addComponent(jScrollPane33, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
        );
        mainDriversForEngineersPanelLayout.setVerticalGroup(
            mainDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainDriversForEngineersPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane33, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewDriversForEngineers1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editDriversForEngineers1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteDriversForEngineers1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        driversForEngineersContainer.add(mainDriversForEngineersPanel, "card2");

        addNewDFEScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addNewDFEScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel10.setBackground(new java.awt.Color(241, 241, 182));

        jLabel172.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel172.setText("Labor-equipment cost");

        driversForEngineersFormLaborEquipmentCost.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel173.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel173.setText("Equipment Fuel cost");

        driversForEngineersFormEquipmentFuelCost.setPreferredSize(new java.awt.Dimension(7, 30));

        driversForEngineersFormLubricantCost.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel174.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel174.setText("Lubricant cost");

        driversForEngineersFormMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel199.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel199.setText("Month");

        jLabel200.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel200.setText("Year");

        jLabel201.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel201.setText("Implementation Mode");

        driversForEngineersFormImplementationMode.setText("Administration");
        driversForEngineersFormImplementationMode.setEnabled(false);
        driversForEngineersFormImplementationMode.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel202.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel202.setText("Days of Operation");

        driversForEngineersFormDaysOfOperation.setPreferredSize(new java.awt.Dimension(7, 30));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(driversForEngineersFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel199)
                                .addGap(165, 165, 165)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel200)
                            .addComponent(driversForEngineersFormYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel172)
                    .addComponent(jLabel173)
                    .addComponent(jLabel174)
                    .addComponent(driversForEngineersFormLaborEquipmentCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(driversForEngineersFormEquipmentFuelCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(driversForEngineersFormLubricantCost, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel201)
                    .addComponent(driversForEngineersFormImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel202)
                    .addComponent(driversForEngineersFormDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel199)
                    .addComponent(jLabel200))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driversForEngineersFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(driversForEngineersFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel172)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormLaborEquipmentCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel173)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormEquipmentFuelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel174)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormLubricantCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel201)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel202)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        addNewDFEScrollPane.setViewportView(jPanel10);

        cancelNewOtherExpenses1.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherExpenses1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherExpenses1MouseClicked(evt);
            }
        });

        jLabel203.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel203.setForeground(new java.awt.Color(255, 255, 255));
        jLabel203.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherExpenses1Layout = new javax.swing.GroupLayout(cancelNewOtherExpenses1);
        cancelNewOtherExpenses1.setLayout(cancelNewOtherExpenses1Layout);
        cancelNewOtherExpenses1Layout.setHorizontalGroup(
            cancelNewOtherExpenses1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherExpenses1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel203)
                .addGap(24, 24, 24))
        );
        cancelNewOtherExpenses1Layout.setVerticalGroup(
            cancelNewOtherExpenses1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherExpenses1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel203)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveNewOtherExpenses1.setBackground(new java.awt.Color(0, 204, 0));
        saveNewOtherExpenses1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNewOtherExpenses1MouseClicked(evt);
            }
        });

        jLabel204.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel204.setForeground(new java.awt.Color(255, 255, 255));
        jLabel204.setText("Save");

        javax.swing.GroupLayout saveNewOtherExpenses1Layout = new javax.swing.GroupLayout(saveNewOtherExpenses1);
        saveNewOtherExpenses1.setLayout(saveNewOtherExpenses1Layout);
        saveNewOtherExpenses1Layout.setHorizontalGroup(
            saveNewOtherExpenses1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherExpenses1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel204)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveNewOtherExpenses1Layout.setVerticalGroup(
            saveNewOtherExpenses1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherExpenses1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel204)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout addNewDriversForEngineersPanelLayout = new javax.swing.GroupLayout(addNewDriversForEngineersPanel);
        addNewDriversForEngineersPanel.setLayout(addNewDriversForEngineersPanelLayout);
        addNewDriversForEngineersPanelLayout.setHorizontalGroup(
            addNewDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addNewDFEScrollPane)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewDriversForEngineersPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherExpenses1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveNewOtherExpenses1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        addNewDriversForEngineersPanelLayout.setVerticalGroup(
            addNewDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewDriversForEngineersPanelLayout.createSequentialGroup()
                .addComponent(addNewDFEScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveNewOtherExpenses1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherExpenses1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        driversForEngineersContainer.add(addNewDriversForEngineersPanel, "card2");

        editDFEScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        editDFEScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel13.setBackground(new java.awt.Color(241, 241, 182));

        jLabel205.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel205.setText("Labor-equipment cost");

        driversForEngineersFormEditLaborEquipmentCost.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel206.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel206.setText("Equipment Fuel cost");

        driversForEngineersFormEditEquipmentFuelCost.setPreferredSize(new java.awt.Dimension(7, 30));

        driversForEngineersFormEditLubricantCost.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel207.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel207.setText("Lubricant cost");

        driversForEngineersFormEditMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel208.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel208.setText("Month");

        jLabel209.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel209.setText("Year");

        jLabel210.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel210.setText("Implementation Mode");

        driversForEngineersFormEditImplementationMode.setEnabled(false);
        driversForEngineersFormEditImplementationMode.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel211.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel211.setText("Days of Operation");

        driversForEngineersFormEditDaysOfOperation.setPreferredSize(new java.awt.Dimension(7, 30));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(driversForEngineersFormEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel208)
                                .addGap(165, 165, 165)))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel209)
                            .addComponent(driversForEngineersFormEditYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel205)
                    .addComponent(jLabel206)
                    .addComponent(jLabel207)
                    .addComponent(driversForEngineersFormEditLaborEquipmentCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(driversForEngineersFormEditEquipmentFuelCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(driversForEngineersFormEditLubricantCost, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel210)
                    .addComponent(driversForEngineersFormEditImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel211)
                    .addComponent(driversForEngineersFormEditDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel208)
                    .addComponent(jLabel209))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driversForEngineersFormEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(driversForEngineersFormEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel205)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormEditLaborEquipmentCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel206)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormEditEquipmentFuelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel207)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormEditLubricantCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel210)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormEditImplementationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel211)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversForEngineersFormEditDaysOfOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        editDFEScrollPane.setViewportView(jPanel13);

        cancelNewOtherExpenses2.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherExpenses2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherExpenses2MouseClicked(evt);
            }
        });

        jLabel212.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel212.setForeground(new java.awt.Color(255, 255, 255));
        jLabel212.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherExpenses2Layout = new javax.swing.GroupLayout(cancelNewOtherExpenses2);
        cancelNewOtherExpenses2.setLayout(cancelNewOtherExpenses2Layout);
        cancelNewOtherExpenses2Layout.setHorizontalGroup(
            cancelNewOtherExpenses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherExpenses2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel212)
                .addGap(24, 24, 24))
        );
        cancelNewOtherExpenses2Layout.setVerticalGroup(
            cancelNewOtherExpenses2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherExpenses2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel212)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveDFE.setBackground(new java.awt.Color(0, 204, 0));
        saveDFE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveDFEMouseClicked(evt);
            }
        });

        jLabel213.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel213.setForeground(new java.awt.Color(255, 255, 255));
        jLabel213.setText("Save");

        javax.swing.GroupLayout saveDFELayout = new javax.swing.GroupLayout(saveDFE);
        saveDFE.setLayout(saveDFELayout);
        saveDFELayout.setHorizontalGroup(
            saveDFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveDFELayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel213)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveDFELayout.setVerticalGroup(
            saveDFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveDFELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel213)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout editDriversForEngineersPanelLayout = new javax.swing.GroupLayout(editDriversForEngineersPanel);
        editDriversForEngineersPanel.setLayout(editDriversForEngineersPanelLayout);
        editDriversForEngineersPanelLayout.setHorizontalGroup(
            editDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editDFEScrollPane)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editDriversForEngineersPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherExpenses2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveDFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        editDriversForEngineersPanelLayout.setVerticalGroup(
            editDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editDriversForEngineersPanelLayout.createSequentialGroup()
                .addComponent(editDFEScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editDriversForEngineersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveDFE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherExpenses2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        driversForEngineersContainer.add(editDriversForEngineersPanel, "card2");

        jSplitPane1.setLeftComponent(driversForEngineersContainer);

        totalCostBreakdownPanel.setMaximumSize(new java.awt.Dimension(400, 32767));
        totalCostBreakdownPanel.setOpaque(false);

        jLabel151.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel151.setText("Total Cost Breakdown");

        jLabel153.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel153.setText("Labor-Crew Cost ");

        laborCrewCost.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        laborCrewCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        laborCrewCost.setText("₱ 0.00");

        jLabel156.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel156.setText("Labor-Equipment Cost ");

        laborEquipmentCost.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        laborEquipmentCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        laborEquipmentCost.setText("₱ 0.00");

        jLabel164.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel164.setText("Equipment Fuel Cost ");

        equipmentFuelCost.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        equipmentFuelCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        equipmentFuelCost.setText("₱ 0.00");

        jLabel166.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel166.setText("Lubricant Cost ");

        lubricantCost.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lubricantCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lubricantCost.setText("₱ 0.00");

        timeframeDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeframeDetailActionPerformed(evt);
            }
        });

        timeRange.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monthly", "Quarterly", "Annually" }));
        timeRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeRangeActionPerformed(evt);
            }
        });

        jLabel168.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel168.setText("Time range");

        jLabel169.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel169.setText("Timeframe detail");

        jLabel225.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel225.setText("Grand Total Cost ");

        grandTotalCost.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        grandTotalCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        grandTotalCost.setText("₱ 0.00");

        javax.swing.GroupLayout totalCostBreakdownPanelLayout = new javax.swing.GroupLayout(totalCostBreakdownPanel);
        totalCostBreakdownPanel.setLayout(totalCostBreakdownPanelLayout);
        totalCostBreakdownPanelLayout.setHorizontalGroup(
            totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalCostBreakdownPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(totalCostBreakdownPanelLayout.createSequentialGroup()
                        .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(timeRange, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel168))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(totalCostBreakdownPanelLayout.createSequentialGroup()
                                .addComponent(jLabel169)
                                .addGap(0, 47, Short.MAX_VALUE))
                            .addComponent(timeframeDetail, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(totalCostBreakdownPanelLayout.createSequentialGroup()
                        .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel166)
                            .addComponent(jLabel164)
                            .addComponent(jLabel153)
                            .addComponent(jLabel156)
                            .addComponent(jLabel225))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(laborCrewCost)
                            .addComponent(laborEquipmentCost)
                            .addComponent(equipmentFuelCost)
                            .addComponent(lubricantCost)
                            .addComponent(grandTotalCost))
                        .addGap(20, 20, 20))))
        );
        totalCostBreakdownPanelLayout.setVerticalGroup(
            totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalCostBreakdownPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel151)
                .addGap(20, 20, 20)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel168)
                    .addComponent(jLabel169))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeframeDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeRange, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel153)
                    .addComponent(laborCrewCost))
                .addGap(25, 25, 25)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel156)
                    .addComponent(laborEquipmentCost))
                .addGap(25, 25, 25)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel164)
                    .addComponent(equipmentFuelCost))
                .addGap(25, 25, 25)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel166)
                    .addComponent(lubricantCost))
                .addGap(25, 25, 25)
                .addGroup(totalCostBreakdownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel225)
                    .addComponent(grandTotalCost))
                .addContainerGap(223, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(totalCostBreakdownPanel);

        javax.swing.GroupLayout driversForEngineersTabLayout = new javax.swing.GroupLayout(driversForEngineersTab);
        driversForEngineersTab.setLayout(driversForEngineersTabLayout);
        driversForEngineersTabLayout.setHorizontalGroup(
            driversForEngineersTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        driversForEngineersTabLayout.setVerticalGroup(
            driversForEngineersTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        activityTabbedPane.addTab("Drivers for Engineers", driversForEngineersTab);

        projectsOfWorksTab.setLayout(new java.awt.CardLayout());

        tableMainPrograms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Source of Fund", "Total Project Cost", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMainPrograms.setRowHeight(24);
        tableMainPrograms.setShowVerticalLines(false);
        jScrollPane37.setViewportView(tableMainPrograms);
        if (tableMainPrograms.getColumnModel().getColumnCount() > 0) {
            tableMainPrograms.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        deleteProjects.setBackground(new java.awt.Color(255, 51, 51));
        deleteProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteProjectsMouseClicked(evt);
            }
        });

        jLabel154.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel154.setForeground(new java.awt.Color(255, 255, 255));
        jLabel154.setText("Delete");

        javax.swing.GroupLayout deleteProjectsLayout = new javax.swing.GroupLayout(deleteProjects);
        deleteProjects.setLayout(deleteProjectsLayout);
        deleteProjectsLayout.setHorizontalGroup(
            deleteProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteProjectsLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel154)
                .addGap(31, 31, 31))
        );
        deleteProjectsLayout.setVerticalGroup(
            deleteProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteProjectsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel154)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editProjects.setBackground(new java.awt.Color(0, 102, 204));
        editProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editProjectsMouseClicked(evt);
            }
        });

        jLabel163.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(255, 255, 255));
        jLabel163.setText("Edit");

        javax.swing.GroupLayout editProjectsLayout = new javax.swing.GroupLayout(editProjects);
        editProjects.setLayout(editProjectsLayout);
        editProjectsLayout.setHorizontalGroup(
            editProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProjectsLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel163)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editProjectsLayout.setVerticalGroup(
            editProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editProjectsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel163)
                .addContainerGap())
        );

        viewProjects.setBackground(new java.awt.Color(255, 102, 0));
        viewProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewProjectsMouseClicked(evt);
            }
        });

        jLabel167.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel167.setForeground(new java.awt.Color(255, 255, 255));
        jLabel167.setText("View");

        javax.swing.GroupLayout viewProjectsLayout = new javax.swing.GroupLayout(viewProjects);
        viewProjects.setLayout(viewProjectsLayout);
        viewProjectsLayout.setHorizontalGroup(
            viewProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewProjectsLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel167)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        viewProjectsLayout.setVerticalGroup(
            viewProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewProjectsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel167)
                .addContainerGap())
        );

        addNewProjects.setBackground(new java.awt.Color(0, 204, 0));
        addNewProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewProjectsMouseClicked(evt);
            }
        });

        jLabel214.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel214.setForeground(new java.awt.Color(255, 255, 255));
        jLabel214.setText("Add New");

        javax.swing.GroupLayout addNewProjectsLayout = new javax.swing.GroupLayout(addNewProjects);
        addNewProjects.setLayout(addNewProjectsLayout);
        addNewProjectsLayout.setHorizontalGroup(
            addNewProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewProjectsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel214)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        addNewProjectsLayout.setVerticalGroup(
            addNewProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewProjectsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel214)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainProjectsPanelLayout = new javax.swing.GroupLayout(mainProjectsPanel);
        mainProjectsPanel.setLayout(mainProjectsPanelLayout);
        mainProjectsPanelLayout.setHorizontalGroup(
            mainProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainProjectsPanelLayout.createSequentialGroup()
                .addContainerGap(575, Short.MAX_VALUE)
                .addComponent(deleteProjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editProjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewProjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewProjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane37)
        );
        mainProjectsPanelLayout.setVerticalGroup(
            mainProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainProjectsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane37, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewProjects, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewProjects, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editProjects, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteProjects, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        projectsOfWorksTab.add(mainProjectsPanel, "card2");

        cancelNewOtherActivity2.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherActivity2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherActivity2MouseClicked(evt);
            }
        });

        jLabel252.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel252.setForeground(new java.awt.Color(255, 255, 255));
        jLabel252.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherActivity2Layout = new javax.swing.GroupLayout(cancelNewOtherActivity2);
        cancelNewOtherActivity2.setLayout(cancelNewOtherActivity2Layout);
        cancelNewOtherActivity2Layout.setHorizontalGroup(
            cancelNewOtherActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherActivity2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel252)
                .addGap(24, 24, 24))
        );
        cancelNewOtherActivity2Layout.setVerticalGroup(
            cancelNewOtherActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherActivity2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel252)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveNewOtherActivity1.setBackground(new java.awt.Color(0, 204, 0));
        saveNewOtherActivity1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNewOtherActivity1MouseClicked(evt);
            }
        });

        jLabel253.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel253.setForeground(new java.awt.Color(255, 255, 255));
        jLabel253.setText("Save");

        javax.swing.GroupLayout saveNewOtherActivity1Layout = new javax.swing.GroupLayout(saveNewOtherActivity1);
        saveNewOtherActivity1.setLayout(saveNewOtherActivity1Layout);
        saveNewOtherActivity1Layout.setHorizontalGroup(
            saveNewOtherActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherActivity1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel253)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveNewOtherActivity1Layout.setVerticalGroup(
            saveNewOtherActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherActivity1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel253)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addNewProjectsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel17.setBackground(new java.awt.Color(241, 241, 182));

        jLabel254.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel254.setText("Month");

        jLabel256.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel256.setText("Source of Fund");

        tableProjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Project Cost", "Implementation Mode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProjects.getTableHeader().setReorderingAllowed(false);
        jScrollPane42.setViewportView(tableProjects);

        addMaintenanceCrew.setVisible(false);
        addProjectsItem.setBackground(new java.awt.Color(255, 255, 255));
        addProjectsItem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addProjectsItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addProjectsItemMouseClicked(evt);
            }
        });

        jLabel257.setBackground(new java.awt.Color(51, 51, 51));
        jLabel257.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel257.setForeground(new java.awt.Color(51, 51, 51));
        jLabel257.setText("Add");

        javax.swing.GroupLayout addProjectsItemLayout = new javax.swing.GroupLayout(addProjectsItem);
        addProjectsItem.setLayout(addProjectsItemLayout);
        addProjectsItemLayout.setHorizontalGroup(
            addProjectsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProjectsItemLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel257)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addProjectsItemLayout.setVerticalGroup(
            addProjectsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProjectsItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel257)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editMaintenanceCrew.setVisible(false);
        editProjectsItem.setBackground(new java.awt.Color(255, 255, 255));
        editProjectsItem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editProjectsItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editProjectsItemMouseClicked(evt);
            }
        });

        jLabel259.setBackground(new java.awt.Color(51, 51, 51));
        jLabel259.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel259.setForeground(new java.awt.Color(51, 51, 51));
        jLabel259.setText("Edit");

        javax.swing.GroupLayout editProjectsItemLayout = new javax.swing.GroupLayout(editProjectsItem);
        editProjectsItem.setLayout(editProjectsItemLayout);
        editProjectsItemLayout.setHorizontalGroup(
            editProjectsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProjectsItemLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel259)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editProjectsItemLayout.setVerticalGroup(
            editProjectsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProjectsItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel259)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeMaintenanceCrew.setVisible(false);
        removeProjectsItem.setBackground(new java.awt.Color(255, 255, 255));
        removeProjectsItem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeProjectsItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeProjectsItemMouseClicked(evt);
            }
        });

        jLabel260.setBackground(new java.awt.Color(51, 51, 51));
        jLabel260.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel260.setForeground(new java.awt.Color(51, 51, 51));
        jLabel260.setText("Remove");

        javax.swing.GroupLayout removeProjectsItemLayout = new javax.swing.GroupLayout(removeProjectsItem);
        removeProjectsItem.setLayout(removeProjectsItemLayout);
        removeProjectsItemLayout.setHorizontalGroup(
            removeProjectsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeProjectsItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel260)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeProjectsItemLayout.setVerticalGroup(
            removeProjectsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeProjectsItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel260)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        projectsFormMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel261.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel261.setText("Year");

        jLabel262.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel262.setText("Projects");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel262)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane42, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editProjectsItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeProjectsItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addProjectsItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel256)
                                    .addComponent(projectsFormSourceOfFund, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 489, Short.MAX_VALUE)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(projectsFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel254))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel261)
                                    .addComponent(projectsFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(36, 36, 36))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel254)
                            .addComponent(jLabel261))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(projectsFormYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(projectsFormMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel256)
                        .addGap(10, 10, 10)
                        .addComponent(projectsFormSourceOfFund, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addComponent(jLabel262)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane42, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addProjectsItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editProjectsItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeProjectsItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        addNewProjectsScrollPane.setViewportView(jPanel17);

        javax.swing.GroupLayout addNewProjectsPanelLayout = new javax.swing.GroupLayout(addNewProjectsPanel);
        addNewProjectsPanel.setLayout(addNewProjectsPanelLayout);
        addNewProjectsPanelLayout.setHorizontalGroup(
            addNewProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewProjectsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherActivity2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveNewOtherActivity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(addNewProjectsScrollPane)
        );
        addNewProjectsPanelLayout.setVerticalGroup(
            addNewProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewProjectsPanelLayout.createSequentialGroup()
                .addComponent(addNewProjectsScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveNewOtherActivity1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherActivity2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        projectsOfWorksTab.add(addNewProjectsPanel, "card2");

        backViewProgram.setBackground(new java.awt.Color(0, 102, 204));
        backViewProgram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backViewProgramMouseClicked(evt);
            }
        });

        jLabel215.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel215.setForeground(new java.awt.Color(255, 255, 255));
        jLabel215.setText("Back");

        javax.swing.GroupLayout backViewProgramLayout = new javax.swing.GroupLayout(backViewProgram);
        backViewProgram.setLayout(backViewProgramLayout);
        backViewProgramLayout.setHorizontalGroup(
            backViewProgramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backViewProgramLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel215)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        backViewProgramLayout.setVerticalGroup(
            backViewProgramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backViewProgramLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel215)
                .addContainerGap())
        );

        jLabel216.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel216.setText("Source of Fund");

        projectsViewSourceOfFund.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        projectsViewSourceOfFund.setText("source of fund");

        jLabel219.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel219.setText("Date");

        projectsViewDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        projectsViewDate.setText("Month Year");

        tableViewProjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name of Personnel", "No. of CD", "Rate Per Day", "Total Wages"
            }
        ));
        tableViewProjects.getTableHeader().setReorderingAllowed(false);
        jScrollPane39.setViewportView(tableViewProjects);
        if (tableViewProjects.getColumnModel().getColumnCount() > 0) {
            tableViewProjects.getColumnModel().getColumn(1).setHeaderValue("Type");
            tableViewProjects.getColumnModel().getColumn(2).setHeaderValue("Operator Name");
        }

        jLabel221.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel221.setText("Projects");

        jLabel222.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel222.setText("Grand Total:");

        projectsViewProjectTotal.setText("0.00");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel216)
                            .addComponent(projectsViewSourceOfFund))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel219)
                            .addComponent(projectsViewDate))
                        .addGap(118, 118, 118))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel221)
                        .addGap(0, 991, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane39, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel222)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(projectsViewProjectTotal)
                                .addGap(9, 9, 9)))
                        .addGap(26, 26, 26))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel216)
                    .addComponent(jLabel219))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectsViewSourceOfFund)
                    .addComponent(projectsViewDate))
                .addGap(61, 61, 61)
                .addComponent(jLabel221)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel222)
                    .addComponent(projectsViewProjectTotal))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        viewProjectsScrollPane.setViewportView(jPanel14);

        javax.swing.GroupLayout viewProjectsPanelLayout = new javax.swing.GroupLayout(viewProjectsPanel);
        viewProjectsPanel.setLayout(viewProjectsPanelLayout);
        viewProjectsPanelLayout.setHorizontalGroup(
            viewProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewProjectsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backViewProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(viewProjectsScrollPane)
        );
        viewProjectsPanelLayout.setVerticalGroup(
            viewProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewProjectsPanelLayout.createSequentialGroup()
                .addComponent(viewProjectsScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backViewProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        projectsOfWorksTab.add(viewProjectsPanel, "card2");

        cancelNewOtherActivity3.setBackground(new java.awt.Color(255, 102, 0));
        cancelNewOtherActivity3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNewOtherActivity3MouseClicked(evt);
            }
        });

        jLabel255.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel255.setForeground(new java.awt.Color(255, 255, 255));
        jLabel255.setText("Cancel");

        javax.swing.GroupLayout cancelNewOtherActivity3Layout = new javax.swing.GroupLayout(cancelNewOtherActivity3);
        cancelNewOtherActivity3.setLayout(cancelNewOtherActivity3Layout);
        cancelNewOtherActivity3Layout.setHorizontalGroup(
            cancelNewOtherActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelNewOtherActivity3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel255)
                .addGap(24, 24, 24))
        );
        cancelNewOtherActivity3Layout.setVerticalGroup(
            cancelNewOtherActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelNewOtherActivity3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel255)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveNewOtherActivity2.setBackground(new java.awt.Color(0, 204, 0));
        saveNewOtherActivity2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNewOtherActivity2MouseClicked(evt);
            }
        });

        jLabel258.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel258.setForeground(new java.awt.Color(255, 255, 255));
        jLabel258.setText("Save");

        javax.swing.GroupLayout saveNewOtherActivity2Layout = new javax.swing.GroupLayout(saveNewOtherActivity2);
        saveNewOtherActivity2.setLayout(saveNewOtherActivity2Layout);
        saveNewOtherActivity2Layout.setHorizontalGroup(
            saveNewOtherActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherActivity2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel258)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        saveNewOtherActivity2Layout.setVerticalGroup(
            saveNewOtherActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveNewOtherActivity2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel258)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editProjectsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel18.setBackground(new java.awt.Color(241, 241, 182));

        jLabel263.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel263.setText("Month");

        jLabel264.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel264.setText("Source of Fund");

        tableEditProjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Project Cost", "Implementation Mode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableEditProjects.getTableHeader().setReorderingAllowed(false);
        jScrollPane44.setViewportView(tableEditProjects);

        addMaintenanceCrew.setVisible(false);
        addProjectsItemEdit.setBackground(new java.awt.Color(255, 255, 255));
        addProjectsItemEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addProjectsItemEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addProjectsItemEditMouseClicked(evt);
            }
        });

        jLabel265.setBackground(new java.awt.Color(51, 51, 51));
        jLabel265.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel265.setForeground(new java.awt.Color(51, 51, 51));
        jLabel265.setText("Add");

        javax.swing.GroupLayout addProjectsItemEditLayout = new javax.swing.GroupLayout(addProjectsItemEdit);
        addProjectsItemEdit.setLayout(addProjectsItemEditLayout);
        addProjectsItemEditLayout.setHorizontalGroup(
            addProjectsItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProjectsItemEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel265)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        addProjectsItemEditLayout.setVerticalGroup(
            addProjectsItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProjectsItemEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel265)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editMaintenanceCrew.setVisible(false);
        editProjectsItemEdit.setBackground(new java.awt.Color(255, 255, 255));
        editProjectsItemEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editProjectsItemEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editProjectsItemEditMouseClicked(evt);
            }
        });

        jLabel266.setBackground(new java.awt.Color(51, 51, 51));
        jLabel266.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel266.setForeground(new java.awt.Color(51, 51, 51));
        jLabel266.setText("Edit");

        javax.swing.GroupLayout editProjectsItemEditLayout = new javax.swing.GroupLayout(editProjectsItemEdit);
        editProjectsItemEdit.setLayout(editProjectsItemEditLayout);
        editProjectsItemEditLayout.setHorizontalGroup(
            editProjectsItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProjectsItemEditLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel266)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        editProjectsItemEditLayout.setVerticalGroup(
            editProjectsItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProjectsItemEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel266)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        removeMaintenanceCrew.setVisible(false);
        removeProjectsItemEdit.setBackground(new java.awt.Color(255, 255, 255));
        removeProjectsItemEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeProjectsItemEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeProjectsItemEditMouseClicked(evt);
            }
        });

        jLabel267.setBackground(new java.awt.Color(51, 51, 51));
        jLabel267.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel267.setForeground(new java.awt.Color(51, 51, 51));
        jLabel267.setText("Remove");

        javax.swing.GroupLayout removeProjectsItemEditLayout = new javax.swing.GroupLayout(removeProjectsItemEdit);
        removeProjectsItemEdit.setLayout(removeProjectsItemEditLayout);
        removeProjectsItemEditLayout.setHorizontalGroup(
            removeProjectsItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeProjectsItemEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel267)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        removeProjectsItemEditLayout.setVerticalGroup(
            removeProjectsItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeProjectsItemEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel267)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        projectsFormEditMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel268.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel268.setText("Year");

        jLabel269.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel269.setText("Projects");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel269)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane44, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editProjectsItemEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeProjectsItemEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addProjectsItemEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel264)
                                    .addComponent(projectsFormEditSourceOfFund, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 489, Short.MAX_VALUE)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(projectsFormEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel263))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel268)
                                    .addComponent(projectsFormEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(36, 36, 36))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel263)
                            .addComponent(jLabel268))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(projectsFormEditYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(projectsFormEditMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel264)
                        .addGap(10, 10, 10)
                        .addComponent(projectsFormEditSourceOfFund, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addComponent(jLabel269)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane44, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addProjectsItemEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editProjectsItemEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeProjectsItemEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        editProjectsScrollPane.setViewportView(jPanel18);

        javax.swing.GroupLayout editProjectsPanelLayout = new javax.swing.GroupLayout(editProjectsPanel);
        editProjectsPanel.setLayout(editProjectsPanelLayout);
        editProjectsPanelLayout.setHorizontalGroup(
            editProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editProjectsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewOtherActivity3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveNewOtherActivity2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(editProjectsScrollPane)
        );
        editProjectsPanelLayout.setVerticalGroup(
            editProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProjectsPanelLayout.createSequentialGroup()
                .addComponent(editProjectsScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editProjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveNewOtherActivity2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelNewOtherActivity3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        projectsOfWorksTab.add(editProjectsPanel, "card2");

        activityTabbedPane.addTab("Projects/Program of Works", projectsOfWorksTab);

        javax.swing.GroupLayout activityListPanelLayout = new javax.swing.GroupLayout(activityListPanel);
        activityListPanel.setLayout(activityListPanelLayout);
        activityListPanelLayout.setHorizontalGroup(
            activityListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(activityListPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(activityListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(activityTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(activityListPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(activitySearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(494, 494, 494)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sortActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        activityListPanelLayout.setVerticalGroup(
            activityListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, activityListPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(activityListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(activityListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sortActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(activityListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(activitySearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)))
                .addGap(18, 18, 18)
                .addComponent(activityTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        mainPanel.add(activityListPanel, "card2");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Work Category");

        workCategoryTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                workCategoryTabbedPaneMouseClicked(evt);
            }
        });

        workCategoryTab.setLayout(new java.awt.CardLayout());

        tableWorkCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Category No.", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableWorkCategory.setRowHeight(24);
        tableWorkCategory.setShowVerticalLines(false);
        tableWorkCategory.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tableWorkCategory);
        if (tableWorkCategory.getColumnModel().getColumnCount() > 0) {
            tableWorkCategory.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableWorkCategory.getColumnModel().getColumn(1).setPreferredWidth(800);
        }

        deleteWorkCategory.setBackground(new java.awt.Color(255, 51, 51));
        deleteWorkCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteWorkCategoryMouseClicked(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Delete");

        javax.swing.GroupLayout deleteWorkCategoryLayout = new javax.swing.GroupLayout(deleteWorkCategory);
        deleteWorkCategory.setLayout(deleteWorkCategoryLayout);
        deleteWorkCategoryLayout.setHorizontalGroup(
            deleteWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteWorkCategoryLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(31, 31, 31))
        );
        deleteWorkCategoryLayout.setVerticalGroup(
            deleteWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteWorkCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editWorkCategory.setBackground(new java.awt.Color(0, 102, 204));
        editWorkCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editWorkCategoryMouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Edit");

        javax.swing.GroupLayout editWorkCategoryLayout = new javax.swing.GroupLayout(editWorkCategory);
        editWorkCategory.setLayout(editWorkCategoryLayout);
        editWorkCategoryLayout.setHorizontalGroup(
            editWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editWorkCategoryLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel42)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editWorkCategoryLayout.setVerticalGroup(
            editWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editWorkCategoryLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addContainerGap())
        );

        addNewWorkCategory.setBackground(new java.awt.Color(0, 204, 0));
        addNewWorkCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewWorkCategoryMouseClicked(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Add New");

        javax.swing.GroupLayout addNewWorkCategoryLayout = new javax.swing.GroupLayout(addNewWorkCategory);
        addNewWorkCategory.setLayout(addNewWorkCategoryLayout);
        addNewWorkCategoryLayout.setHorizontalGroup(
            addNewWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewWorkCategoryLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel44)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        addNewWorkCategoryLayout.setVerticalGroup(
            addNewWorkCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewWorkCategoryLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainWorkCategoryPanelLayout = new javax.swing.GroupLayout(mainWorkCategoryPanel);
        mainWorkCategoryPanel.setLayout(mainWorkCategoryPanelLayout);
        mainWorkCategoryPanelLayout.setHorizontalGroup(
            mainWorkCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainWorkCategoryPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainWorkCategoryPanelLayout.setVerticalGroup(
            mainWorkCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainWorkCategoryPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainWorkCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewWorkCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editWorkCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteWorkCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        workCategoryTab.add(mainWorkCategoryPanel, "card2");

        workCategoryTabbedPane.addTab("Work Category", workCategoryTab);

        activityTab.setLayout(new java.awt.CardLayout());

        tableActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item No.", "Description", "Work Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableActivity.setRowHeight(24);
        tableActivity.setShowVerticalLines(false);
        jScrollPane10.setViewportView(tableActivity);
        if (tableActivity.getColumnModel().getColumnCount() > 0) {
            tableActivity.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableActivity.getColumnModel().getColumn(0).setHeaderValue("Item No.");
            tableActivity.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        deleteActivity.setBackground(new java.awt.Color(255, 51, 51));
        deleteActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteActivityMouseClicked(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Delete");

        javax.swing.GroupLayout deleteActivityLayout = new javax.swing.GroupLayout(deleteActivity);
        deleteActivity.setLayout(deleteActivityLayout);
        deleteActivityLayout.setHorizontalGroup(
            deleteActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteActivityLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addGap(31, 31, 31))
        );
        deleteActivityLayout.setVerticalGroup(
            deleteActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editActivity.setBackground(new java.awt.Color(0, 102, 204));
        editActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editActivityMouseClicked(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Edit");

        javax.swing.GroupLayout editActivityLayout = new javax.swing.GroupLayout(editActivity);
        editActivity.setLayout(editActivityLayout);
        editActivityLayout.setHorizontalGroup(
            editActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editActivityLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel45)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editActivityLayout.setVerticalGroup(
            editActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addContainerGap())
        );

        addNewActivity.setBackground(new java.awt.Color(0, 204, 0));
        addNewActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewActivityMouseClicked(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Add New");

        javax.swing.GroupLayout addNewActivityLayout = new javax.swing.GroupLayout(addNewActivity);
        addNewActivity.setLayout(addNewActivityLayout);
        addNewActivityLayout.setHorizontalGroup(
            addNewActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewActivityLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addGap(21, 21, 21))
        );
        addNewActivityLayout.setVerticalGroup(
            addNewActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainWorkCategoryPanel1Layout = new javax.swing.GroupLayout(mainWorkCategoryPanel1);
        mainWorkCategoryPanel1.setLayout(mainWorkCategoryPanel1Layout);
        mainWorkCategoryPanel1Layout.setHorizontalGroup(
            mainWorkCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainWorkCategoryPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainWorkCategoryPanel1Layout.setVerticalGroup(
            mainWorkCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainWorkCategoryPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainWorkCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        activityTab.add(mainWorkCategoryPanel1, "card2");

        workCategoryTabbedPane.addTab("Activity", activityTab);

        subActivityTab.setLayout(new java.awt.CardLayout());

        tableSubActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Activity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSubActivity.setRowHeight(24);
        tableSubActivity.setShowVerticalLines(false);
        jScrollPane17.setViewportView(tableSubActivity);
        if (tableSubActivity.getColumnModel().getColumnCount() > 0) {
            tableSubActivity.getColumnModel().getColumn(0).setPreferredWidth(500);
        }

        deleteSubActivity.setBackground(new java.awt.Color(255, 51, 51));
        deleteSubActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteSubActivityMouseClicked(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Delete");

        javax.swing.GroupLayout deleteSubActivityLayout = new javax.swing.GroupLayout(deleteSubActivity);
        deleteSubActivity.setLayout(deleteSubActivityLayout);
        deleteSubActivityLayout.setHorizontalGroup(
            deleteSubActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteSubActivityLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel80)
                .addGap(31, 31, 31))
        );
        deleteSubActivityLayout.setVerticalGroup(
            deleteSubActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteSubActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editSubActivity.setBackground(new java.awt.Color(0, 102, 204));
        editSubActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editSubActivityMouseClicked(evt);
            }
        });

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Edit");

        javax.swing.GroupLayout editSubActivityLayout = new javax.swing.GroupLayout(editSubActivity);
        editSubActivity.setLayout(editSubActivityLayout);
        editSubActivityLayout.setHorizontalGroup(
            editSubActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editSubActivityLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel81)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editSubActivityLayout.setVerticalGroup(
            editSubActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editSubActivityLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel81)
                .addContainerGap())
        );

        addNewSubActivity.setBackground(new java.awt.Color(0, 204, 0));
        addNewSubActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewSubActivityMouseClicked(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Add New");

        javax.swing.GroupLayout addNewSubActivityLayout = new javax.swing.GroupLayout(addNewSubActivity);
        addNewSubActivity.setLayout(addNewSubActivityLayout);
        addNewSubActivityLayout.setHorizontalGroup(
            addNewSubActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewSubActivityLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel82)
                .addGap(21, 21, 21))
        );
        addNewSubActivityLayout.setVerticalGroup(
            addNewSubActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewSubActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel82)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainWorkCategoryPanel3Layout = new javax.swing.GroupLayout(mainWorkCategoryPanel3);
        mainWorkCategoryPanel3.setLayout(mainWorkCategoryPanel3Layout);
        mainWorkCategoryPanel3Layout.setHorizontalGroup(
            mainWorkCategoryPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainWorkCategoryPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteSubActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editSubActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewSubActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainWorkCategoryPanel3Layout.setVerticalGroup(
            mainWorkCategoryPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainWorkCategoryPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainWorkCategoryPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewSubActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editSubActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteSubActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        subActivityTab.add(mainWorkCategoryPanel3, "card2");

        workCategoryTabbedPane.addTab("Sub Activity", subActivityTab);

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel70.setText("Search");

        workCategorySearchValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                workCategorySearchValueKeyPressed(evt);
            }
        });

        searchWorkCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchWorkCategoryMouseClicked(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel71.setText("Sort");

        sortWorkCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "category no.", "description" }));
        sortWorkCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortWorkCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout workCategoryPanelLayout = new javax.swing.GroupLayout(workCategoryPanel);
        workCategoryPanel.setLayout(workCategoryPanelLayout);
        workCategoryPanelLayout.setHorizontalGroup(
            workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workCategoryPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(workCategoryTabbedPane)
                        .addGroup(workCategoryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel70)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(workCategorySearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(searchWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(528, 528, 528)
                            .addComponent(jLabel71)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sortWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        workCategoryPanelLayout.setVerticalGroup(
            workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workCategoryPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addGroup(workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sortWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel71))
                    .addGroup(workCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(workCategorySearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel70))
                    .addComponent(searchWorkCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(workCategoryTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        mainPanel.add(workCategoryPanel, "card2");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Equipment");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel72.setText("Search");

        equipmentSearchValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                equipmentSearchValueKeyPressed(evt);
            }
        });

        searchEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchEquipmentMouseClicked(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel73.setText("Sort");

        sortEquipment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "equipment no.", "type" }));
        sortEquipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortEquipmentActionPerformed(evt);
            }
        });

        workCategoryTab1.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout mainWorkCategoryPanel2Layout = new javax.swing.GroupLayout(mainWorkCategoryPanel2);
        mainWorkCategoryPanel2.setLayout(mainWorkCategoryPanel2Layout);
        mainWorkCategoryPanel2Layout.setHorizontalGroup(
            mainWorkCategoryPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1045, Short.MAX_VALUE)
        );
        mainWorkCategoryPanel2Layout.setVerticalGroup(
            mainWorkCategoryPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );

        workCategoryTab1.add(mainWorkCategoryPanel2, "card2");

        deleteEquipment.setBackground(new java.awt.Color(255, 51, 51));
        deleteEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteEquipmentMouseClicked(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Delete");

        javax.swing.GroupLayout deleteEquipmentLayout = new javax.swing.GroupLayout(deleteEquipment);
        deleteEquipment.setLayout(deleteEquipmentLayout);
        deleteEquipmentLayout.setHorizontalGroup(
            deleteEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteEquipmentLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel47)
                .addGap(31, 31, 31))
        );
        deleteEquipmentLayout.setVerticalGroup(
            deleteEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteEquipmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editEquipment.setBackground(new java.awt.Color(0, 102, 204));
        editEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editEquipmentMouseClicked(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Edit");

        javax.swing.GroupLayout editEquipmentLayout = new javax.swing.GroupLayout(editEquipment);
        editEquipment.setLayout(editEquipmentLayout);
        editEquipmentLayout.setHorizontalGroup(
            editEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editEquipmentLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel48)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editEquipmentLayout.setVerticalGroup(
            editEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editEquipmentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addContainerGap())
        );

        addNewEquipment.setBackground(new java.awt.Color(0, 204, 0));
        addNewEquipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewEquipmentMouseClicked(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Add New");

        javax.swing.GroupLayout addNewEquipmentLayout = new javax.swing.GroupLayout(addNewEquipment);
        addNewEquipment.setLayout(addNewEquipmentLayout);
        addNewEquipmentLayout.setHorizontalGroup(
            addNewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewEquipmentLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addGap(22, 22, 22))
        );
        addNewEquipmentLayout.setVerticalGroup(
            addNewEquipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewEquipmentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addContainerGap())
        );

        tableEquipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipment No.", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableEquipment.setRowHeight(24);
        tableEquipment.setShowVerticalLines(false);
        tableEquipment.getTableHeader().setReorderingAllowed(false);
        jScrollPane11.setViewportView(tableEquipment);
        if (tableEquipment.getColumnModel().getColumnCount() > 0) {
            tableEquipment.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableEquipment.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        javax.swing.GroupLayout equipmentPanelLayout = new javax.swing.GroupLayout(equipmentPanel);
        equipmentPanel.setLayout(equipmentPanelLayout);
        equipmentPanelLayout.setHorizontalGroup(
            equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equipmentPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(equipmentPanelLayout.createSequentialGroup()
                        .addComponent(deleteEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addNewEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane11)
                        .addGroup(equipmentPanelLayout.createSequentialGroup()
                            .addComponent(jLabel72)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(equipmentSearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(searchEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(517, 517, 517)
                            .addComponent(jLabel73)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sortEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel3)))
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(equipmentPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(workCategoryTab1, javax.swing.GroupLayout.PREFERRED_SIZE, 1045, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        equipmentPanelLayout.setVerticalGroup(
            equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equipmentPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addGap(41, 41, 41)
                .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sortEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel73))
                    .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(equipmentSearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel72))
                    .addComponent(searchEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewEquipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editEquipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteEquipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
            .addGroup(equipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(equipmentPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(workCategoryTab1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        mainPanel.add(equipmentPanel, "card2");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Personnel");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel74.setText("Search");

        searchPersonnelValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchPersonnelValueKeyPressed(evt);
            }
        });

        tablePersonnel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Personnel ID", "Name", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePersonnel.setRowHeight(24);
        tablePersonnel.setShowVerticalLines(false);
        tablePersonnel.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(tablePersonnel);
        if (tablePersonnel.getColumnModel().getColumnCount() > 0) {
            tablePersonnel.getColumnModel().getColumn(0).setPreferredWidth(20);
            tablePersonnel.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        searchPersonnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchPersonnelMouseClicked(evt);
            }
        });

        sortPersonnel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id", "name", "type" }));
        sortPersonnel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortPersonnelActionPerformed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel75.setText("Sort");

        deletePersonnel.setBackground(new java.awt.Color(255, 51, 51));
        deletePersonnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletePersonnelMouseClicked(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Delete");

        javax.swing.GroupLayout deletePersonnelLayout = new javax.swing.GroupLayout(deletePersonnel);
        deletePersonnel.setLayout(deletePersonnelLayout);
        deletePersonnelLayout.setHorizontalGroup(
            deletePersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deletePersonnelLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel50)
                .addGap(31, 31, 31))
        );
        deletePersonnelLayout.setVerticalGroup(
            deletePersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deletePersonnelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editPersonnel.setBackground(new java.awt.Color(0, 102, 204));
        editPersonnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editPersonnelMouseClicked(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Edit");

        javax.swing.GroupLayout editPersonnelLayout = new javax.swing.GroupLayout(editPersonnel);
        editPersonnel.setLayout(editPersonnelLayout);
        editPersonnelLayout.setHorizontalGroup(
            editPersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPersonnelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel51)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        editPersonnelLayout.setVerticalGroup(
            editPersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editPersonnelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addContainerGap())
        );

        addNewPersonnel.setBackground(new java.awt.Color(0, 204, 0));
        addNewPersonnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewPersonnelMouseClicked(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Add New");

        javax.swing.GroupLayout addNewPersonnelLayout = new javax.swing.GroupLayout(addNewPersonnel);
        addNewPersonnel.setLayout(addNewPersonnelLayout);
        addNewPersonnelLayout.setHorizontalGroup(
            addNewPersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewPersonnelLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addGap(22, 22, 22))
        );
        addNewPersonnelLayout.setVerticalGroup(
            addNewPersonnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewPersonnelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addContainerGap())
        );

        personnelSettingsIcon.setToolTipText("Personnel Settings");
        personnelSettingsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                personnelSettingsIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout personnelPanelLayout = new javax.swing.GroupLayout(personnelPanel);
        personnelPanel.setLayout(personnelPanelLayout);
        personnelPanelLayout.setHorizontalGroup(
            personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personnelPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(personnelPanelLayout.createSequentialGroup()
                            .addComponent(deletePersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(editPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(addNewPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane12)
                            .addGroup(personnelPanelLayout.createSequentialGroup()
                                .addComponent(jLabel74)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchPersonnelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(517, 517, 517)
                                .addComponent(jLabel75)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sortPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(personnelPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(personnelSettingsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        personnelPanelLayout.setVerticalGroup(
            personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personnelPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(personnelSettingsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sortPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel75))
                    .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchPersonnelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel74))
                    .addComponent(searchPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(personnelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addNewPersonnel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editPersonnel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletePersonnel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );

        mainPanel.add(personnelPanel, "card2");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Report");

        jLabel217.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel217.setText("Monthly Report");

        jLabel218.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel218.setText("Header Title");

        monthlyMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        exportMonthlyReport.setText("Export Monthly Report");
        exportMonthlyReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMonthlyReportActionPerformed(evt);
            }
        });

        jLabel220.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel220.setText("Year");

        jLabel223.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel223.setText("Month");

        jLabel224.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel224.setText("Quarterly Report");

        jLabel227.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel227.setText("Year");

        exportQuarterlyReport.setText("Export Quarterly Report");
        exportQuarterlyReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportQuarterlyReportMouseClicked(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));

        jLabel228.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel228.setText("Regular Activity Workbook");

        jLabel229.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel229.setText("Month");

        regularActivityWorkbookMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        exportWorkbook.setText("Export Workbook");
        exportWorkbook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportWorkbookActionPerformed(evt);
            }
        });

        jLabel231.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel231.setText("Year");

        javax.swing.GroupLayout reportPanelLayout = new javax.swing.GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setHorizontalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel224)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanelLayout.createSequentialGroup()
                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(reportPanelLayout.createSequentialGroup()
                                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(monthlyHeaderTitle)
                                    .addGroup(reportPanelLayout.createSequentialGroup()
                                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel218)
                                            .addComponent(jLabel217)
                                            .addComponent(jLabel5))
                                        .addGap(0, 555, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(monthlyMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel223))
                                .addGap(18, 18, 18)
                                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel220)
                                    .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(exportMonthlyReport, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(monthlyYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(56, 56, 56))
                    .addGroup(reportPanelLayout.createSequentialGroup()
                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(reportPanelLayout.createSequentialGroup()
                                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regularActivityWorkbookMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel229))
                                .addGap(18, 18, 18)
                                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel231)
                                    .addGroup(reportPanelLayout.createSequentialGroup()
                                        .addComponent(regularActivityWorkbookYear, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(exportWorkbook, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel228)
                            .addGroup(reportPanelLayout.createSequentialGroup()
                                .addComponent(quarterlyYear, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(exportQuarterlyReport, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel227))
                        .addGap(0, 542, Short.MAX_VALUE))))
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(reportPanelLayout.createSequentialGroup()
                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel220)
                            .addComponent(jLabel223))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthlyMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthlyYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(reportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel217)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel218)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthlyHeaderTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(exportMonthlyReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel224)
                .addGap(18, 18, 18)
                .addComponent(jLabel227)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quarterlyYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportQuarterlyReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel228)
                .addGap(18, 18, 18)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel231)
                    .addComponent(jLabel229))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regularActivityWorkbookMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regularActivityWorkbookYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportWorkbook, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(215, Short.MAX_VALUE))
        );

        mainPanel.add(reportPanel, "card2");

        settingsPanel.setPreferredSize(new java.awt.Dimension(1133, 813));

        settingsScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        settingsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(153, 153, 153));
        jLabel54.setText("Settings");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("Network ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Username");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("Password");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel60.setText("Server");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setText("Port");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel62.setText("Database");

        saveNetwork.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        saveNetwork.setText("Save");
        saveNetwork.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNetworkMouseClicked(evt);
            }
        });

        jLabel230.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel230.setText("Report");

        jLabel232.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel232.setText("Prepared by 1");

        jLabel233.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel233.setText("Name");

        jLabel234.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel234.setText("Position");

        jLabel235.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel235.setText("Prepared by 2");

        jLabel236.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel236.setText("Name");

        jLabel237.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel237.setText("Position");

        jLabel238.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel238.setText("Submitted by");

        jLabel239.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel239.setText("Name");

        jLabel240.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel240.setText("Position");

        jLabel241.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel241.setText("Position");

        jLabel242.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel242.setText("Name");

        jLabel243.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel243.setText("Approved by");

        saveReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        saveReport.setText("Save");
        saveReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveReportMouseClicked(evt);
            }
        });

        jLabel244.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel244.setForeground(new java.awt.Color(102, 102, 102));
        jLabel244.setText("Name for Notes");

        jLabel245.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel245.setForeground(new java.awt.Color(102, 102, 102));
        jLabel245.setText("Quarterly Report Details");

        jLabel246.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel246.setText("Total Length of Provincial Roads (Km)");

        jLabel247.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel247.setText("Total Length of Provincial Roads In Fair-to-Good Condition (Km)");

        jLabel248.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel248.setText("Total Budget (Php)");

        saveQuarterlyReportDetails.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        saveQuarterlyReportDetails.setText("Save");
        saveQuarterlyReportDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveQuarterlyReportDetailsMouseClicked(evt);
            }
        });

        testNetworkConnection.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        testNetworkConnection.setText("Test Connection");
        testNetworkConnection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                testNetworkConnectionMouseClicked(evt);
            }
        });

        systemRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        systemRefresh.setText("System Refresh");
        systemRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                systemRefreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalLengthOfProvincialRoads, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel245)
                    .addComponent(jLabel244)
                    .addComponent(jLabel232)
                    .addComponent(jLabel230)
                    .addComponent(jLabel55)
                    .addComponent(jLabel54)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel246)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel235)
                            .addComponent(jLabel238)
                            .addComponent(jLabel243)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(saveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel236)
                                        .addComponent(jLabel237)
                                        .addComponent(jLabel239)
                                        .addComponent(jLabel240)
                                        .addComponent(jLabel241)
                                        .addComponent(jLabel242))
                                    .addGap(33, 33, 33)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(preparedBy2Name)
                                        .addComponent(preparedBy2Position)
                                        .addComponent(submittedByPosition)
                                        .addComponent(submittedByName)
                                        .addComponent(approvedByPosition)
                                        .addComponent(approvedByName, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel233)
                                .addComponent(jLabel234))
                            .addGap(33, 33, 33)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(preparedBy1Name)
                                .addComponent(preparedBy1Position, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel247)
                    .addComponent(totalLengthOfProvincialRoadsInFairToGood, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel248)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(saveQuarterlyReportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(systemRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(testNetworkConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(saveNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel59)
                                .addComponent(jLabel60)
                                .addComponent(jLabel61)
                                .addComponent(jLabel62))
                            .addGap(52, 52, 52)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(networkPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(networkUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(networkServer, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(networkPort, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(networkDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel54)
                .addGap(41, 41, 41)
                .addComponent(jLabel55)
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(networkUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(networkPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(networkServer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(networkPort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(networkDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(testNetworkConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(systemRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel230)
                .addGap(34, 34, 34)
                .addComponent(jLabel244)
                .addGap(18, 18, 18)
                .addComponent(jLabel232)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel233)
                    .addComponent(preparedBy1Name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel234)
                    .addComponent(preparedBy1Position, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel235)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel236)
                    .addComponent(preparedBy2Name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel237)
                    .addComponent(preparedBy2Position, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel238)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel239)
                    .addComponent(submittedByName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel240)
                    .addComponent(submittedByPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel243)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel242)
                    .addComponent(approvedByName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel241)
                    .addComponent(approvedByPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel245)
                .addGap(37, 37, 37)
                .addComponent(jLabel246)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalLengthOfProvincialRoads, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel247)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalLengthOfProvincialRoadsInFairToGood, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel248)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveQuarterlyReportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        settingsScrollPane.setViewportView(jPanel3);

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(settingsScrollPane)
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(settingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
        );

        mainPanel.add(settingsPanel, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navigationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void navActivityListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navActivityListMouseEntered
        navActivityList.setBackground(new Color(78, 150, 215));
    }//GEN-LAST:event_navActivityListMouseEntered

    private void navWorkCategoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navWorkCategoryMouseEntered
        navWorkCategory.setBackground(new Color(78, 150, 215));
    }//GEN-LAST:event_navWorkCategoryMouseEntered

    private void navEquipmentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navEquipmentMouseEntered
        navEquipment.setBackground(new Color(78, 150, 215));
    }//GEN-LAST:event_navEquipmentMouseEntered

    private void navPersonnelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPersonnelMouseEntered
        navPersonnel.setBackground(new Color(78, 150, 215));
    }//GEN-LAST:event_navPersonnelMouseEntered

    private void navReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navReportMouseEntered
        navReport.setBackground(new Color(78, 150, 215));
    }//GEN-LAST:event_navReportMouseEntered

    private void navSettingsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navSettingsMouseEntered
        navSettings.setBackground(new Color(78, 150, 215));
    }//GEN-LAST:event_navSettingsMouseEntered

    private void navActivityListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navActivityListMouseExited
        if (currentSection != 1)
            navActivityList.setBackground(new Color(0, 102, 255));
    }//GEN-LAST:event_navActivityListMouseExited

    private void navWorkCategoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navWorkCategoryMouseExited
        if (currentSection != 2)
            navWorkCategory.setBackground(new Color(0, 102, 255));
    }//GEN-LAST:event_navWorkCategoryMouseExited

    private void navEquipmentMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navEquipmentMouseExited
        if (currentSection != 3)
            navEquipment.setBackground(new Color(0, 102, 255));
    }//GEN-LAST:event_navEquipmentMouseExited

    private void navPersonnelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPersonnelMouseExited
        if (currentSection != 4)
            navPersonnel.setBackground(new Color(0, 102, 255));
    }//GEN-LAST:event_navPersonnelMouseExited

    private void navReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navReportMouseExited
        if (currentSection != 5)
            navReport.setBackground(new Color(0, 102, 255));
    }//GEN-LAST:event_navReportMouseExited

    private void navSettingsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navSettingsMouseExited
        if (currentSection != 6)
            navSettings.setBackground(new Color(0, 102, 255));
    }//GEN-LAST:event_navSettingsMouseExited

    private void navActivityListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navActivityListMouseClicked
        mainPanel.removeAll();
        mainPanel.add(activityListPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 1);
        currentSection = 1;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_navActivityListMouseClicked

    private void navWorkCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navWorkCategoryMouseClicked
        mainPanel.removeAll();
        mainPanel.add(workCategoryPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 2);
        currentSection = 2;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_navWorkCategoryMouseClicked

    private void navEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navEquipmentMouseClicked
        mainPanel.removeAll();
        mainPanel.add(equipmentPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 3);
        currentSection = 3;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_navEquipmentMouseClicked

    private void navPersonnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPersonnelMouseClicked
        mainPanel.removeAll();
        mainPanel.add(personnelPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 4);
        currentSection = 4;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_navPersonnelMouseClicked

    private void navReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navReportMouseClicked
        mainPanel.removeAll();
        mainPanel.add(reportPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 5);
        currentSection = 5;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_navReportMouseClicked

    private void navSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navSettingsMouseClicked
        mainPanel.removeAll();
        mainPanel.add(settingsPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 6);
        currentSection = 6;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_navSettingsMouseClicked

    private void menuLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLogoMouseClicked
        mainPanel.removeAll();
        mainPanel.add(welcomePanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        unselectSectionBefore(currentSection, 0);
        currentSection = 0;
        settingsScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_menuLogoMouseClicked

    private void addNewRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewRegularActivityMouseClicked
        regularActivityTab.removeAll();
        regularActivityTab.add(addNewRegularActivityPanel);
        regularActivityTab.repaint();
        regularActivityTab.revalidate();
    }//GEN-LAST:event_addNewRegularActivityMouseClicked

    private void cancelNewRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewRegularActivityMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            regularActivityTab.removeAll();
            regularActivityTab.add(mainRegularActivityPanel);
            regularActivityTab.repaint();
            regularActivityTab.revalidate();
            resetRegularActivityForm();
            fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);                    
            currentPage = 1;
            updateRegularActivity();
        }
    }//GEN-LAST:event_cancelNewRegularActivityMouseClicked

    private void regularActivityFormRoadSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regularActivityFormRoadSectionActionPerformed
        boolean isOther = regularActivityFormRoadSection.getSelectedItem() == "Other...";
        regularActivityFormOtherRoadSection.setEditable(isOther);
    }//GEN-LAST:event_regularActivityFormRoadSectionActionPerformed

    private void addNewWorkCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewWorkCategoryMouseClicked
        if(Driver.getConnection() != null){
            AddWorkCategory.setListener(this);
            AddWorkCategory.getInstance().showFrame();
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_addNewWorkCategoryMouseClicked

    private void addNewActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewActivityMouseClicked
        if(Driver.getConnection() != null){
            AddActivity.setListener(this);
            AddActivity.getInstance().showFrame();
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_addNewActivityMouseClicked

    private void addNewEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewEquipmentMouseClicked
        if(Driver.getConnection() != null){
            AddEquipment.setListener(this);
            AddEquipment.getInstance().showFrame();
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message, "Error", 0);
        }  
    }//GEN-LAST:event_addNewEquipmentMouseClicked

    private void editActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editActivityMouseClicked
        int selectedRow = tableActivity.getSelectedRow();
        tableActivity.clearSelection();
        
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                Activity activity = searchedActivity.get(selectedRow);
                EditActivity.setListener(this);
                EditActivity.setData(activity.getItemNumber(),
                        activity.getDescription(),
                        activity.getWorkCategory().getDescription());
                EditActivity.getInstance().showFrame();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to be edited!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editActivityMouseClicked

    private void editWorkCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editWorkCategoryMouseClicked
        int selectedRow = tableWorkCategory.getSelectedRow();
        tableWorkCategory.clearSelection();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                WorkCategory workCategory = searchedWorkCategory.get(selectedRow);
                EditWorkCategory.setListener(this);
                EditWorkCategory.setData(String.valueOf(workCategory.getWorkCategoryNumber()),
                        workCategory.getDescription());
                EditWorkCategory.getInstance().showFrame();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to be edited!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editWorkCategoryMouseClicked

    private void editEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editEquipmentMouseClicked
        int selectedRow = tableEquipment.getSelectedRow();
        tableEquipment.clearSelection();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                Equipment equipment = searchedEquipment.get(selectedRow);
                EditEquipment.setData(equipment.getEquipmentNumber(),
                        equipment.getType());
                EditEquipment.setListener(this);
                EditEquipment.getInstance().showFrame();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to be edited!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message, "Error", 0);
        }
    }//GEN-LAST:event_editEquipmentMouseClicked

    private void editPersonnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editPersonnelMouseClicked
        int selectedRow = tablePersonnel.getSelectedRow();
        tablePersonnel.clearSelection();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                EditPersonnel.setListener(this);
                EditPersonnel.setData(searchedPersonnel.get(selectedRow));
                EditPersonnel.getInstance().showFrame();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to be edited!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        }  
    }//GEN-LAST:event_editPersonnelMouseClicked

    private void addNewPersonnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewPersonnelMouseClicked
        if(Driver.getConnection() != null){
            AddPersonnel.setListener(this);
            AddPersonnel.getInstance().showFrame();
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        }  
    }//GEN-LAST:event_addNewPersonnelMouseClicked

    private void regularActivityFormLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regularActivityFormLocationActionPerformed
        regularActivityFormRoadSection.removeAllItems();
        initAddRoadSectionSelectionBox(
                locationList.get(
                        regularActivityFormLocation.getSelectedIndex()
                ).getId()
        );
    }//GEN-LAST:event_regularActivityFormLocationActionPerformed

    private void addOperationEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addOperationEquipmentMouseClicked
        AddOpsEquipment.setPersonnelList(personnelList);
        AddOpsEquipment.setEquipmentList(equipmentList);
        AddOpsEquipment.setFormType(1);
        AddOpsEquipment.setListener(this);
        AddOpsEquipment.getInstance().showFrame();
    }//GEN-LAST:event_addOperationEquipmentMouseClicked

    private void addMaintenanceCrewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMaintenanceCrewMouseClicked
        AddOpsMaintenanceCrew.setPersonnelList(personnelList);
        AddOpsMaintenanceCrew.setFormType(1);
        AddOpsMaintenanceCrew.setListener(this);
        AddOpsMaintenanceCrew.getInstance().showFrame();
    }//GEN-LAST:event_addMaintenanceCrewMouseClicked

    private void addCrewMaterialsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCrewMaterialsMouseClicked
        AddOpsCrewMaterials.setListener(this);
        AddOpsCrewMaterials.setFormType(1);
        AddOpsCrewMaterials.getInstance().showFrame();
    }//GEN-LAST:event_addCrewMaterialsMouseClicked

    private void addCrewEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCrewEquipmentMouseClicked
        AddOpsCrewEquipment.setListener(this);
        AddOpsCrewEquipment.setEquipmentList(equipmentList);
        AddOpsCrewEquipment.setFormType(1);
        AddOpsCrewEquipment.getInstance().showFrame();
    }//GEN-LAST:event_addCrewEquipmentMouseClicked

    private void saveNetworkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNetworkMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to save these changes?");
        File jarDir = null;
        
        try{
            jarDir = JarDirectory.getJarDir(Main.class);
        } catch (URISyntaxException | IOException e){}
        
        File parentDir = jarDir.getParentFile();
        final String NETWORK_FILE = "src/mdqrs/path/to/config.properties";
        File file = new File(parentDir,NETWORK_FILE);
        if (n == 0) {
            try (OutputStream output = new FileOutputStream(file)) {
                Properties network = new Properties();

                Cryptographer cryptographer = new Cryptographer();

                String encryptedPassword = "";

                try {
                    encryptedPassword = cryptographer.encrypt(networkPassword.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage());
                    e.printStackTrace();
                }

                network.setProperty("username", networkUsername.getText());
                network.setProperty("password", encryptedPassword);
                network.setProperty("server", networkServer.getText());
                network.setProperty("port", networkPort.getText());
                network.setProperty("database", networkDatabase.getText());

                network.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            }
            
            networkUsername.enableInputMethods(false);
            networkPassword.enableInputMethods(false);
            networkServer.enableInputMethods(false);
            networkPort.enableInputMethods(false);
            networkDatabase.enableInputMethods(false);
        } else {
            try (FileInputStream input = new FileInputStream(file)) {
                Properties network = new Properties();
                network.load(input);

                Cryptographer cryptographer = new Cryptographer();

                String decryptedPassword = "";

                try {
                    decryptedPassword = cryptographer.decrypt(network.getProperty("password"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage());
                    e.printStackTrace();
                }

                networkUsername.setText(network.getProperty("username"));
                networkPassword.setText(decryptedPassword);
                networkServer.setText(network.getProperty("server"));
                networkPort.setText(network.getProperty("port"));
                networkDatabase.setText(network.getProperty("database"));
            } catch (IOException io) {
                io.printStackTrace();
            }

            networkUsername.enableInputMethods(false);
            networkPassword.enableInputMethods(false);
            networkServer.enableInputMethods(false);
            networkPort.enableInputMethods(false);
            networkDatabase.enableInputMethods(false);
        }
    }//GEN-LAST:event_saveNetworkMouseClicked

    private void isOperationEquipmentTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isOperationEquipmentTableSelectedActionPerformed
        if (isOperationEquipmentTableSelected.isSelected()) {
            addOperationEquipment.setVisible(true);
            removeOperationEquipment.setVisible(true);
            editOperationEquipment.setVisible(true);
        } else {
            addOperationEquipment.setVisible(false);
            removeOperationEquipment.setVisible(false);
            editOperationEquipment.setVisible(false);
        }
    }//GEN-LAST:event_isOperationEquipmentTableSelectedActionPerformed

    private void isMaintenanceCrewTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isMaintenanceCrewTableSelectedActionPerformed
        if (isMaintenanceCrewTableSelected.isSelected()) {
            addMaintenanceCrew.setVisible(true);
            editMaintenanceCrew.setVisible(true);
            removeMaintenanceCrew.setVisible(true);
        } else {
            addMaintenanceCrew.setVisible(false);
            removeMaintenanceCrew.setVisible(false);
            editMaintenanceCrew.setVisible(false);
        }
    }//GEN-LAST:event_isMaintenanceCrewTableSelectedActionPerformed

    private void isMaterialsTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isMaterialsTableSelectedActionPerformed
        if (isMaterialsTableSelected.isSelected()) {
            addCrewMaterials.setVisible(true);
            removeCrewMaterials.setVisible(true);
            editCrewMaterials.setVisible(true);
        } else {
            addCrewMaterials.setVisible(false);
            removeCrewMaterials.setVisible(false);
            editCrewMaterials.setVisible(false);
        }
    }//GEN-LAST:event_isMaterialsTableSelectedActionPerformed

    private void isEquipmentTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isEquipmentTableSelectedActionPerformed
        if (isEquipmentTableSelected.isSelected()) {
            addCrewEquipment.setVisible(true);
            removeCrewEquipment.setVisible(true);
            editCrewEquipment.setVisible(true);
        } else {
            addCrewEquipment.setVisible(false);
            removeCrewEquipment.setVisible(false);
            editCrewEquipment.setVisible(false);
        }
    }//GEN-LAST:event_isEquipmentTableSelectedActionPerformed

    private void removeOperationEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeOperationEquipmentMouseClicked
        int n = tableRegularActivityOperationEquipment.getSelectedRow();

        if (n > -1) {
            opsEquipmentList.removeEquipment(n);
            populateRegularActivityOpsEquipment(opsEquipmentList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeOperationEquipmentMouseClicked

    private void editOperationEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editOperationEquipmentMouseClicked
        int n = tableRegularActivityOperationEquipment.getSelectedRow();

        if (n > -1) {
            EditOpsEquipment.setPersonnelList(personnelList);
            EditOpsEquipment.setEquipmentList(equipmentList);
            EditOpsEquipment.setListener(this);
            EditOpsEquipment.setFormType(1);
            EditOpsEquipment.setData(n, opsEquipmentList.get(n));
            EditOpsEquipment.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editOperationEquipmentMouseClicked

    private void editMaintenanceCrewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMaintenanceCrewMouseClicked
        int n = tableRegularActivityMaintenanceCrew.getSelectedRow();
        tableRegularActivityMaintenanceCrew.clearSelection();
        if (n > -1) {
            EditOpsMaintenanceCrew.setPersonnelList(personnelList);
            EditOpsMaintenanceCrew.setListener(this);
            EditOpsMaintenanceCrew.setFormType(1);
            EditOpsMaintenanceCrew.setData(n, crewPersonnelList.get(n));
            EditOpsMaintenanceCrew.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editMaintenanceCrewMouseClicked

    private void removeMaintenanceCrewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeMaintenanceCrewMouseClicked
        int n = tableRegularActivityMaintenanceCrew.getSelectedRow();

        if (n > -1) {
            crewPersonnelList.removeCrew(n);
            populateRegularActivityOpsMaintenanceCrew(crewPersonnelList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeMaintenanceCrewMouseClicked

    private void editCrewMaterialsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editCrewMaterialsMouseClicked
        int n = tableRegularActivityMaterials.getSelectedRow();

        if (n > -1) {
            EditOpsCrewMaterials.setListener(this);
            EditOpsCrewMaterials.setData(n, crewMaterialsList.get(n));
            EditOpsCrewMaterials.setFormType(1);
            EditOpsCrewMaterials.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editCrewMaterialsMouseClicked

    private void removeCrewMaterialsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeCrewMaterialsMouseClicked
        int n = tableRegularActivityMaterials.getSelectedRow();

        if (n > -1) {
            crewMaterialsList.removeMaterial(n);
            populateRegularActivityMaterials(crewMaterialsList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeCrewMaterialsMouseClicked

    private void editCrewEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editCrewEquipmentMouseClicked
        int n = tableRegularActivityEquipment.getSelectedRow();

        if (n > -1) {
            EditOpsCrewEquipment.setEquipmentList(equipmentList);
            EditOpsCrewEquipment.setData(n, crewEquipmentList.get(n));
            EditOpsCrewEquipment.setFormType(1);
            EditOpsCrewEquipment.setListener(this);
            EditOpsCrewEquipment.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editCrewEquipmentMouseClicked

    private void removeCrewEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeCrewEquipmentMouseClicked
        int n = tableRegularActivityEquipment.getSelectedRow();

        if (n > -1) {
            crewEquipmentList.removeCrewEquipment(n);
            populateRegularActivityCrewEquipment(crewEquipmentList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeCrewEquipmentMouseClicked

    private void saveNewRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNewRegularActivityMouseClicked
        int selectedActivity = regularActivityFormActivity.getSelectedIndex();
        if(Driver.getConnection() != null){
            if (selectedActivity == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select an activity!");
            } else if (regularActivitySubActivitySelectionBox.isEnabled() && regularActivitySubActivitySelectionBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(rootPane, "If 504, please specify sub-activity!");
            } else if (!regularActivityFormOtherRoadSection.isEnabled() && regularActivityFormOtherRoadSection.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "If other road section, please specify!");
            } else if (regularActivityFormImplementationMode.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please specify the implementation mode!");
            } else if (regularActivityFormDaysOfOperation.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please specify the days of operation!");
            } else if (!dataValidation.validateDouble(regularActivityFormDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please specify a valid days of operation!");
            } else if (isOperationEquipmentTableSelected.isSelected() && opsEquipmentList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Operation Equipment table if empty or not used!");
            } else if (isMaintenanceCrewTableSelected.isSelected() && crewPersonnelList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Maintenance Crew table if empty or not used!");
            } else if (isMaterialsTableSelected.isSelected() && crewMaterialsList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Materials table if empty or not used!");
            } else if (isEquipmentTableSelected.isSelected() && crewEquipmentList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Equipment table if empty or not used!");
            } else {
                ActivityListDBController activityListDBController = new ActivityListDBController();
                SubActivity subActivity = regularActivitySubActivitySelectionBox.isEnabled() ? subActivityTempList.get(regularActivitySubActivitySelectionBox.getSelectedIndex() - 1) : new SubActivity();

                if (!isOperationEquipmentTableSelected.isSelected()) {
                    opsEquipmentList.toList().clear();
                }
                if (!isMaintenanceCrewTableSelected.isSelected()) {
                    crewPersonnelList.toList().clear();
                }
                if (!isMaterialsTableSelected.isSelected()) {
                    crewMaterialsList.toList().clear();
                }
                if (!isEquipmentTableSelected.isSelected()) {
                    crewEquipmentList.toList().clear();
                }

                if (regularActivityFormOtherRoadSection.isEditable()) {

                    activityListDBController.add(activityList.get(selectedActivity - 1), locationList.get(regularActivityFormLocation.getSelectedIndex()),
                            regularActivityFormOtherRoadSection.getText(), true, String.valueOf(regularActivityFormMonth.getSelectedItem()),
                             Integer.parseInt(String.valueOf(regularActivityFormYear.getSelectedItem())), regularActivityFormImplementationMode.getText(),
                            Double.parseDouble(regularActivityFormDaysOfOperation.getText()), opsEquipmentList, crewPersonnelList, crewMaterialsList, crewEquipmentList, subActivity);

                    JOptionPane.showMessageDialog(rootPane, "Activity added!");
                    regularActivityTab.removeAll();
                    regularActivityTab.add(mainRegularActivityPanel);
                    regularActivityTab.repaint();
                    regularActivityTab.revalidate();
                    fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);                    
                    currentPage = 1;
                    updateRegularActivity();
                    resetRegularActivityForm();
                    timeframeDetailActionPerformed(null);
                } else {
                    activityListDBController.add(activityList.get(selectedActivity - 1), locationList.get(regularActivityFormLocation.getSelectedIndex()),
                            roadSectionList.get(regularActivityFormRoadSection.getSelectedIndex()), false, String.valueOf(regularActivityFormMonth.getSelectedItem()),
                             Integer.parseInt(String.valueOf(regularActivityFormYear.getSelectedItem())), regularActivityFormImplementationMode.getText(),
                            Double.parseDouble(regularActivityFormDaysOfOperation.getText()), opsEquipmentList, crewPersonnelList, crewMaterialsList, crewEquipmentList, subActivity);

                    JOptionPane.showMessageDialog(rootPane, "Activity added!");
                    regularActivityTab.removeAll();
                    regularActivityTab.add(mainRegularActivityPanel);
                    regularActivityTab.repaint();
                    regularActivityTab.revalidate();
                    fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);                    
                    currentPage = 1;
                    updateRegularActivity();
                    resetRegularActivityForm();
                    timeframeDetailActionPerformed(null);
                }
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveNewRegularActivityMouseClicked

    private void viewRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewRegularActivityMouseClicked
        int selectedRow = tableMainRegularActivity.getSelectedRow();
        tableMainRegularActivity.clearSelection();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedRegularActivity.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainRegularActivity.clearSelection();
                } else {
                    regularActivityTab.removeAll();
                    regularActivityTab.add(viewRegularActivityPanel);
                    regularActivityTab.repaint();
                    regularActivityTab.revalidate();
                    setRegularActivityDataView(selectedRow);
                    tableMainRegularActivity.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to view!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_viewRegularActivityMouseClicked

    private void backViewRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backViewRegularActivityMouseClicked
        regularActivityTab.removeAll();
        regularActivityTab.add(mainRegularActivityPanel);
        regularActivityTab.repaint();
        regularActivityTab.revalidate();
        viewRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_backViewRegularActivityMouseClicked

    private void editSubActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editSubActivityMouseClicked
        int selectedRow = tableSubActivity.getSelectedRow();
        tableSubActivity.clearSelection();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                EditSubActivity.setData(searchedSubActivity.get(selectedRow));
                EditSubActivity.setListener(this);
                EditSubActivity.getInstance().showFrame();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editSubActivityMouseClicked

    private void addNewSubActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewSubActivityMouseClicked
        if(Driver.getConnection() != null){
            AddSubActivity.setListener(this);
            AddSubActivity.getInstance().showFrame();
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_addNewSubActivityMouseClicked

    private void regularActivityFormActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regularActivityFormActivityActionPerformed
        boolean isOther = false;
        if (regularActivityFormActivity.getSelectedIndex() > 0) {
            isOther = activityList.get(regularActivityFormActivity.getSelectedIndex() - 1).getItemNumber().equals("504");
        }
        regularActivitySubActivitySelectionBox.removeAllItems();
        regularActivitySubActivitySelectionBox.setEnabled(isOther);
        if (regularActivityFormActivity.getSelectedIndex() > 0) {
            initAddRegularActivitySubActivitySelectionBox(activityList.get(regularActivityFormActivity.getSelectedIndex() - 1).getItemNumber());
        }
    }//GEN-LAST:event_regularActivityFormActivityActionPerformed

    private void cancelEditRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelEditRegularActivityMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            regularActivityTab.removeAll();
            regularActivityTab.add(mainRegularActivityPanel);
            regularActivityTab.repaint();
            regularActivityTab.revalidate();
            resetRegularActivityEditForm();
            fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);                    
            currentPage = 1;
            updateRegularActivity();
        }
    }//GEN-LAST:event_cancelEditRegularActivityMouseClicked

    private void saveEditRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveEditRegularActivityMouseClicked
        int selectedActivity = regularActivityEditActivity.getSelectedIndex();
        if(Driver.getConnection() != null){
            if (selectedActivity == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select an activity!");
            } else if (regularActivityEditSubActivitySelectionBox.isEnabled() && regularActivityEditSubActivitySelectionBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(rootPane, "If 504, please specify sub-activity!");
            } else if (!regularActivityEditOtherRoadSection.isEnabled() && regularActivityEditOtherRoadSection.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "If other road section, please specify!");
            } else if (regularActivityEditImplementationMode.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please specify the implementation mode!");
            } else if (regularActivityEditDaysOfOperation.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please specify the days of operation!");
            } else if (!dataValidation.validateDouble(regularActivityEditDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please specify a valid days of operation!");
            } else if (isEditOperationEquipmentTableSelected.isSelected() && opsEquipmentList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Operation Equipment table if empty or not used!");
            } else if (isEditMaintenanceCrewTableSelected.isSelected() && crewPersonnelList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Maintenance Crew table if empty or not used!");
            } else if (isEditMaterialsTableSelected.isSelected() && crewMaterialsList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Materials table if empty or not used!");
            } else if (isEditEquipmentTableSelected.isSelected() && crewEquipmentList.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please unselect Equipment table if empty or not used!");
            } else {
                ActivityListDBController activityListDBController = new ActivityListDBController();
                SubActivity subActivity = regularActivityEditSubActivitySelectionBox.isEnabled() ? subActivityTempList.get(regularActivityEditSubActivitySelectionBox.getSelectedIndex() - 1) : new SubActivity();

                regularActivityForEdit.setActivity(activityList.get(selectedActivity - 1));
                regularActivityForEdit.setLocation(locationList.get(regularActivityEditLocation.getSelectedIndex()));
                if (regularActivityEditOtherRoadSection.isEditable()) {
                    regularActivityForEdit.setOtherRoadSection(regularActivityEditOtherRoadSection.getText());
                    regularActivityForEdit.setIsOtherRoadSection(true);
                } else {
                    regularActivityForEdit.setRoadSection(roadSectionList.get(regularActivityEditRoadSection.getSelectedIndex()));
                    regularActivityForEdit.setIsOtherRoadSection(false);
                }
                regularActivityForEdit.setMonth(String.valueOf(regularActivityEditMonth.getSelectedItem()));
                regularActivityForEdit.setYear(Integer.parseInt(String.valueOf(regularActivityEditYear.getSelectedItem())));
                regularActivityForEdit.setImplementationMode(regularActivityEditImplementationMode.getText());
                regularActivityForEdit.setNumberOfCD(Double.parseDouble(regularActivityEditDaysOfOperation.getText()));
                regularActivityForEdit.setSubActivity(subActivity);

                activityListDBController.edit(regularActivityForEdit, opsEquipmentList, crewPersonnelList, crewMaterialsList, crewEquipmentList);

                JOptionPane.showMessageDialog(rootPane, "Changes Saved!");
                regularActivityTab.removeAll();
                regularActivityTab.add(mainRegularActivityPanel);
                regularActivityTab.repaint();
                regularActivityTab.revalidate();
                fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);                    
                currentPage = 1;
                updateRegularActivity();
                resetRegularActivityEditForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveEditRegularActivityMouseClicked

    private void regularActivityEditActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regularActivityEditActivityActionPerformed
        boolean isOther = false;
        if (regularActivityEditActivity.getSelectedIndex() > 0) {
            isOther = activityList.get(regularActivityEditActivity.getSelectedIndex() - 1).getItemNumber().equals("504");
        }
        regularActivityEditSubActivitySelectionBox.removeAllItems();
        regularActivityEditSubActivitySelectionBox.setEnabled(isOther);
        if (regularActivityEditActivity.getSelectedIndex() > 0){
            initEditRegularActivitySubActivitySelectionBox(activityList.get(regularActivityEditActivity.getSelectedIndex() - 1).getItemNumber());
        }
    }//GEN-LAST:event_regularActivityEditActivityActionPerformed

    private void regularActivityEditRoadSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regularActivityEditRoadSectionActionPerformed
        boolean isOther = regularActivityEditRoadSection.getSelectedItem() == "Other...";
        regularActivityEditOtherRoadSection.setEditable(isOther);
    }//GEN-LAST:event_regularActivityEditRoadSectionActionPerformed

    private void regularActivityEditLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regularActivityEditLocationActionPerformed
        regularActivityEditRoadSection.removeAllItems();
        initEditRoadSectionSelectionBox(
                locationList.get(
                        regularActivityEditLocation.getSelectedIndex()
                ).getId()
        );
    }//GEN-LAST:event_regularActivityEditLocationActionPerformed

    private void addOperationEquipmentEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addOperationEquipmentEditMouseClicked
        AddOpsEquipment.setPersonnelList(personnelList);
        AddOpsEquipment.setEquipmentList(equipmentList);
        AddOpsEquipment.setFormType(2);
        AddOpsEquipment.setListener(this);
        AddOpsEquipment.getInstance().showFrame();
    }//GEN-LAST:event_addOperationEquipmentEditMouseClicked

    private void addMaintenanceCrewEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMaintenanceCrewEditMouseClicked
        AddOpsMaintenanceCrew.setPersonnelList(personnelList);
        AddOpsMaintenanceCrew.setFormType(2);
        AddOpsMaintenanceCrew.setListener(this);
        AddOpsMaintenanceCrew.getInstance().showFrame();
    }//GEN-LAST:event_addMaintenanceCrewEditMouseClicked

    private void addCrewMaterialsEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCrewMaterialsEditMouseClicked
        AddOpsCrewMaterials.setListener(this);
        AddOpsCrewMaterials.setFormType(2);
        AddOpsCrewMaterials.getInstance().showFrame();
    }//GEN-LAST:event_addCrewMaterialsEditMouseClicked

    private void addCrewEquipmentEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCrewEquipmentEditMouseClicked
        AddOpsCrewEquipment.setListener(this);
        AddOpsCrewEquipment.setEquipmentList(equipmentList);
        AddOpsCrewEquipment.setFormType(2);
        AddOpsCrewEquipment.getInstance().showFrame();
    }//GEN-LAST:event_addCrewEquipmentEditMouseClicked

    private void isEditOperationEquipmentTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isEditOperationEquipmentTableSelectedActionPerformed
        if (isEditOperationEquipmentTableSelected.isSelected()) {
            addOperationEquipmentEdit.setVisible(true);
            removeOperationEquipmentEdit.setVisible(true);
            editOperationEquipmentEdit.setVisible(true);
        } else {
            addOperationEquipmentEdit.setVisible(false);
            removeOperationEquipmentEdit.setVisible(false);
            editOperationEquipmentEdit.setVisible(false);
        }
    }//GEN-LAST:event_isEditOperationEquipmentTableSelectedActionPerformed

    private void isEditMaintenanceCrewTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isEditMaintenanceCrewTableSelectedActionPerformed
        if (isEditMaintenanceCrewTableSelected.isSelected()) {
            addMaintenanceCrewEdit.setVisible(true);
            editMaintenanceCrewEdit.setVisible(true);
            removeMaintenanceCrewEdit.setVisible(true);
        } else {
            addMaintenanceCrewEdit.setVisible(false);
            removeMaintenanceCrewEdit.setVisible(false);
            editMaintenanceCrewEdit.setVisible(false);
        }
    }//GEN-LAST:event_isEditMaintenanceCrewTableSelectedActionPerformed

    private void isEditMaterialsTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isEditMaterialsTableSelectedActionPerformed
        if (isEditMaterialsTableSelected.isSelected()) {
            addCrewMaterialsEdit.setVisible(true);
            removeCrewMaterialsEdit.setVisible(true);
            editCrewMaterialsEdit.setVisible(true);
        } else {
            addCrewMaterialsEdit.setVisible(false);
            removeCrewMaterialsEdit.setVisible(false);
            editCrewMaterialsEdit.setVisible(false);
        }
    }//GEN-LAST:event_isEditMaterialsTableSelectedActionPerformed

    private void isEditEquipmentTableSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isEditEquipmentTableSelectedActionPerformed
        if (isEditEquipmentTableSelected.isSelected()) {
            addCrewEquipmentEdit.setVisible(true);
            removeCrewEquipmentEdit.setVisible(true);
            editCrewEquipmentEdit.setVisible(true);
        } else {
            addCrewEquipmentEdit.setVisible(false);
            removeCrewEquipmentEdit.setVisible(false);
            editCrewEquipmentEdit.setVisible(false);
        }
    }//GEN-LAST:event_isEditEquipmentTableSelectedActionPerformed

    private void removeOperationEquipmentEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeOperationEquipmentEditMouseClicked
        int n = tableRegularActivityEditOperationEquipment.getSelectedRow();

        if (n > -1) {
            opsEquipmentList.removeEquipment(n);
            populateRegularActivityEditOpsEquipment(opsEquipmentList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeOperationEquipmentEditMouseClicked

    private void editOperationEquipmentEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editOperationEquipmentEditMouseClicked
        int n = tableRegularActivityEditOperationEquipment.getSelectedRow();

        if (n > -1) {
            EditOpsEquipment.setPersonnelList(personnelList);
            EditOpsEquipment.setEquipmentList(equipmentList);
            EditOpsEquipment.setListener(this);
            EditOpsEquipment.setFormType(2);
            EditOpsEquipment.setData(n, opsEquipmentList.get(n));
            EditOpsEquipment.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editOperationEquipmentEditMouseClicked

    private void editMaintenanceCrewEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMaintenanceCrewEditMouseClicked
        int n = tableRegularActivityEditMaintenanceCrew.getSelectedRow();
        tableRegularActivityEditMaintenanceCrew.clearSelection();
        if (n > -1) {
            EditOpsMaintenanceCrew.setPersonnelList(personnelList);
            EditOpsMaintenanceCrew.setListener(this);
            EditOpsMaintenanceCrew.setFormType(2);
            EditOpsMaintenanceCrew.setData(n, crewPersonnelList.get(n));
            EditOpsMaintenanceCrew.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editMaintenanceCrewEditMouseClicked

    private void removeMaintenanceCrewEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeMaintenanceCrewEditMouseClicked
        int n = tableRegularActivityEditMaintenanceCrew.getSelectedRow();

        if (n > -1) {
            crewPersonnelList.removeCrew(n);
            populateRegularActivityEditOpsMaintenanceCrew(crewPersonnelList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeMaintenanceCrewEditMouseClicked

    private void editCrewMaterialsEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editCrewMaterialsEditMouseClicked
        int n = tableRegularActivityEditMaterials.getSelectedRow();

        if (n > -1) {
            EditOpsCrewMaterials.setListener(this);
            EditOpsCrewMaterials.setData(n, crewMaterialsList.get(n));
            EditOpsCrewMaterials.setFormType(2);
            EditOpsCrewMaterials.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editCrewMaterialsEditMouseClicked

    private void removeCrewMaterialsEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeCrewMaterialsEditMouseClicked
        int n = tableRegularActivityEditMaterials.getSelectedRow();

        if (n > -1) {
            crewMaterialsList.removeMaterial(n);
            populateRegularActivityEditOpsCrewMaterials(crewMaterialsList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeCrewMaterialsEditMouseClicked

    private void editCrewEquipmentEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editCrewEquipmentEditMouseClicked
        int n = tableRegularActivityEditEquipment.getSelectedRow();

        if (n > -1) {
            EditOpsCrewEquipment.setEquipmentList(equipmentList);
            EditOpsCrewEquipment.setData(n, crewEquipmentList.get(n));
            EditOpsCrewEquipment.setFormType(2);
            EditOpsCrewEquipment.setListener(this);
            EditOpsCrewEquipment.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editCrewEquipmentEditMouseClicked

    private void removeCrewEquipmentEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeCrewEquipmentEditMouseClicked
        int n = tableRegularActivityEditEquipment.getSelectedRow();

        if (n > -1) {
            crewEquipmentList.removeCrewEquipment(n);
            populateRegularActivityEditOpsCrewEquipment(crewEquipmentList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeCrewEquipmentEditMouseClicked

    private void editRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editRegularActivityMouseClicked
        int selectedRow = tableMainRegularActivity.getSelectedRow();
        
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedRegularActivity.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainRegularActivity.clearSelection();
                } else {
                    regularActivityTab.removeAll();
                    regularActivityTab.add(editRegularActivityPanel);
                    regularActivityTab.repaint();
                    regularActivityTab.revalidate();
                    setRegularActivityDataEdit(selectedRow);
                    tableMainRegularActivity.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editRegularActivityMouseClicked

    private void editOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editOtherActivityMouseClicked
        int selectedRow = tableMainOtherActivity.getSelectedRow();

        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedOtherActivity.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainOtherActivity.clearSelection();
                } else {
                    otherActivityTab.removeAll();
                    otherActivityTab.add(editOtherActivityPanel);
                    otherActivityTab.repaint();
                    otherActivityTab.revalidate();
                    setOtherActivityDataEdit(selectedRow);
                    tableMainOtherActivity.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editOtherActivityMouseClicked

    private void viewOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewOtherActivityMouseClicked
        int selectedRow = tableMainOtherActivity.getSelectedRow();

        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedOtherActivity.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainOtherActivity.clearSelection();
                } else {
                    otherActivityTab.removeAll();
                    otherActivityTab.add(viewOtherActivityPanel);
                    otherActivityTab.repaint();
                    otherActivityTab.revalidate();
                    setOtherActivityDataView(selectedRow);
                    tableMainOtherActivity.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to view!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_viewOtherActivityMouseClicked

    private void addNewOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewOtherActivityMouseClicked
        otherActivityTab.removeAll();
        otherActivityTab.add(addNewOtherActivityPanel);
        otherActivityTab.repaint();
        otherActivityTab.revalidate();
    }//GEN-LAST:event_addNewOtherActivityMouseClicked

    private void cancelNewOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherActivityMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            otherActivityTab.removeAll();
            otherActivityTab.add(mainOtherActivityPanel);
            otherActivityTab.repaint();
            otherActivityTab.revalidate();
            resetOtherActivityForm();
        }
    }//GEN-LAST:event_cancelNewOtherActivityMouseClicked

    private void saveNewOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNewOtherActivityMouseClicked
        int selectedSubActivity = otherActivityFormSubActivitySelectionBox.getSelectedIndex();
        
        if(Driver.getConnection() != null){
            if (selectedSubActivity == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select a sub activity!");
            } else if (otherActivityFormDescription.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write a description!");
            } else if (otherActivityFormImplementationMode.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write an implementation mode!");
            } else if (otherActivityFormDaysOfOperation.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write the days of operation!");
            } else if (!dataValidation.validateDouble(otherActivityFormDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please specify a valid days of operation!");
            } else {
                OtherActivity otherActivity = new OtherActivity();

                otherActivity.setSubActivity(subActivityOtherList.get(otherActivityFormSubActivitySelectionBox.getSelectedIndex() - 1));
                otherActivity.setDescription(otherActivityFormDescription.getText());
                otherActivity.setMonth(String.valueOf(otherActivityFormMonth.getSelectedItem()));
                otherActivity.setYear(Integer.parseInt(String.valueOf(otherActivityFormYear.getSelectedItem())));
                otherActivity.setImplementationMode(otherActivityFormImplementationMode.getText());
                otherActivity.setNumberOfCD(Double.parseDouble(otherActivityFormDaysOfOperation.getText()));

                new OtherActivityListDBController().add(otherActivity, crewPersonnelList);

                JOptionPane.showMessageDialog(rootPane, "Changes Saved!");
                otherActivityTab.removeAll();
                otherActivityTab.add(mainOtherActivityPanel);
                otherActivityTab.repaint();
                otherActivityTab.revalidate();
                otherActivityList = new OtherActivityListDBController().getList();
                searchedOtherActivity = otherActivityList;
                populateMainOtherActivity(otherActivityList);
                resetOtherActivityForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveNewOtherActivityMouseClicked

    private void addOtherMaintenanceCrewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addOtherMaintenanceCrewMouseClicked
        AddOpsMaintenanceCrew.setPersonnelList(personnelList);
        AddOpsMaintenanceCrew.setFormType(3);
        AddOpsMaintenanceCrew.setListener(this);
        AddOpsMaintenanceCrew.getInstance().showFrame();
    }//GEN-LAST:event_addOtherMaintenanceCrewMouseClicked

    private void editOtherMaintenanceCrewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editOtherMaintenanceCrewMouseClicked
        int n = tableOtherActivityMaintenanceCrew.getSelectedRow();
        tableOtherActivityMaintenanceCrew.clearSelection();
        if (n > -1) {
            EditOpsMaintenanceCrew.setPersonnelList(personnelList);
            EditOpsMaintenanceCrew.setListener(this);
            EditOpsMaintenanceCrew.setFormType(3);
            EditOpsMaintenanceCrew.setData(n, crewPersonnelList.get(n));
            EditOpsMaintenanceCrew.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editOtherMaintenanceCrewMouseClicked

    private void removeOtherMaintenanceCrewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeOtherMaintenanceCrewMouseClicked
        int n = tableOtherActivityMaintenanceCrew.getSelectedRow();

        if (n > -1) {
            crewPersonnelList.removeCrew(n);
            populateOtherActivityOpsMaintenanceCrew(crewPersonnelList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeOtherMaintenanceCrewMouseClicked

    private void otherActivityFormDaysOfOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherActivityFormDaysOfOperationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherActivityFormDaysOfOperationActionPerformed

    private void backViewRegularActivity1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backViewRegularActivity1MouseClicked
        otherActivityTab.removeAll();
        otherActivityTab.add(mainOtherActivityPanel);
        otherActivityTab.repaint();
        otherActivityTab.revalidate();
        viewOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
    }//GEN-LAST:event_backViewRegularActivity1MouseClicked

    private void cancelNewOtherActivity1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherActivity1MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");        
        if(n == 0){
            otherActivityTab.removeAll();
            otherActivityTab.add(mainOtherActivityPanel);
            otherActivityTab.repaint();
            otherActivityTab.revalidate();
            resetOtherActivityEditForm();
        }
    }//GEN-LAST:event_cancelNewOtherActivity1MouseClicked

    private void saveEditedOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveEditedOtherActivityMouseClicked
        int selectedSubActivity = otherActivityEditSubActivitySelectionBox.getSelectedIndex();
        
        if(Driver.getConnection() != null){
            if (selectedSubActivity == 0) {
                JOptionPane.showMessageDialog(rootPane, "Please select a sub activity!");
            } else if (otherActivityEditDescription.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write a description!");
            } else if (otherActivityEditImplementationMode.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write an implementation mode!");
            } else if (otherActivityEditDaysOfOperation.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write the days of operation!");
            } else if (!dataValidation.validateDouble(otherActivityEditDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please specify a valid days of operation!");
            } else {

                otherActivityForEdit.setSubActivity(subActivityOtherList.get(otherActivityEditSubActivitySelectionBox.getSelectedIndex() - 1));
                otherActivityForEdit.setDescription(otherActivityEditDescription.getText());
                otherActivityForEdit.setMonth(String.valueOf(otherActivityEditMonth.getSelectedItem()));
                otherActivityForEdit.setYear(Integer.parseInt(String.valueOf(otherActivityEditYear.getSelectedItem())));
                otherActivityForEdit.setImplementationMode(otherActivityEditImplementationMode.getText());
                otherActivityForEdit.setNumberOfCD(Double.parseDouble(otherActivityEditDaysOfOperation.getText()));

                new OtherActivityListDBController().edit(otherActivityForEdit, crewPersonnelList);

                JOptionPane.showMessageDialog(rootPane, "Changes Saved!");
                otherActivityTab.removeAll();
                otherActivityTab.add(mainOtherActivityPanel);
                otherActivityTab.repaint();
                otherActivityTab.revalidate();
                otherActivityList = new OtherActivityListDBController().getList();
                searchedOtherActivity = otherActivityList;
                populateMainOtherActivity(otherActivityList);
                resetOtherActivityEditForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveEditedOtherActivityMouseClicked

    private void addOtherMaintenanceCrewEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addOtherMaintenanceCrewEditMouseClicked
        AddOpsMaintenanceCrew.setPersonnelList(personnelList);
        AddOpsMaintenanceCrew.setFormType(4);
        AddOpsMaintenanceCrew.setListener(this);
        AddOpsMaintenanceCrew.getInstance().showFrame();
    }//GEN-LAST:event_addOtherMaintenanceCrewEditMouseClicked

    private void otherActivityEditDaysOfOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherActivityEditDaysOfOperationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherActivityEditDaysOfOperationActionPerformed

    private void editOtherMaintenanceCrewEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editOtherMaintenanceCrewEditMouseClicked
        int n = tableOtherActivityEditPersonnel.getSelectedRow();
        tableOtherActivityEditPersonnel.clearSelection();
        if (n > -1) {
            EditOpsMaintenanceCrew.setPersonnelList(personnelList);
            EditOpsMaintenanceCrew.setListener(this);
            EditOpsMaintenanceCrew.setFormType(4);
            EditOpsMaintenanceCrew.setData(n, crewPersonnelList.get(n));
            EditOpsMaintenanceCrew.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editOtherMaintenanceCrewEditMouseClicked

    private void removeOtherMaintenanceCrewEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeOtherMaintenanceCrewEditMouseClicked
        int n = tableOtherActivityEditPersonnel.getSelectedRow();

        if (n > -1) {
            crewPersonnelList.removeCrew(n);
            populateOtherActivityEditOpsMaintenanceCrew(crewPersonnelList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeOtherMaintenanceCrewEditMouseClicked

    private void editOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editOtherExpensesMouseClicked
        int selectedOtherExpenses = tableMainOtherExpenses.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedOtherExpenses > -1) {
                if(searchedOtherExpenses.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainOtherExpenses.clearSelection();
                } else {
                    otherExpensesTab.removeAll();
                    otherExpensesTab.add(editOtherExpensesPanel);
                    otherExpensesTab.repaint();
                    otherExpensesTab.revalidate();
                    setOtherExpensesDataEdit(selectedOtherExpenses);
                    tableMainOtherExpenses.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editOtherExpensesMouseClicked

    private void viewOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewOtherExpensesMouseClicked
        int selectedOtherExpenses = tableMainOtherExpenses.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedOtherExpenses > -1) {
                if(searchedOtherExpenses.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainOtherExpenses.clearSelection();
                } else {         
                    otherExpensesTab.removeAll();
                    otherExpensesTab.add(viewOtherExpensesPanel);
                    otherExpensesTab.repaint();
                    otherExpensesTab.revalidate();
                    setOtherExpensesDataView(selectedOtherExpenses);
                    tableMainOtherExpenses.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to view!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_viewOtherExpensesMouseClicked

    private void addNewOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewOtherExpensesMouseClicked
        otherExpensesTab.removeAll();
        otherExpensesTab.add(addNewOtherExpensesPanel);
        otherExpensesTab.repaint();
        otherExpensesTab.revalidate();
    }//GEN-LAST:event_addNewOtherExpensesMouseClicked

    private void cancelNewOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherExpensesMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            otherExpensesTab.removeAll();
            otherExpensesTab.add(mainOtherExpensesPanel);
            otherExpensesTab.repaint();
            otherExpensesTab.revalidate();
            resetOtherExpensesForm();
        }
    }//GEN-LAST:event_cancelNewOtherExpensesMouseClicked

    private void saveNewOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNewOtherExpensesMouseClicked
        if(Driver.getConnection() != null){
            if (otherExpensesFormLaborCrewCost.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a labor crew cost!");
            } else if (otherExpensesFormLaborEquipmentCost.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a labor equipment cost!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormLaborEquipmentCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid labor equipment cost!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormLaborCrewCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid labor Crew cost!");
            } else if (otherExpensesFormImplementationMode.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write an implementation mode!");
            } else if (otherExpensesFormDaysOfOperation.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write the days of operation!");
            } else if (!dataValidation.validateDouble(otherExpensesFormDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please specify a valid days of operation!");
            } else if (otherExpensesFormLightEquipments.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter light equipments expenses!");
            } else if (otherExpensesFormHeavyEquipments.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter heavy equipments expenses!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormLightEquipments.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid light equipment cost!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormHeavyEquipments.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid heavy equipment cost!");
            } else {
                OtherExpenses otherExpenses = new OtherExpenses();

                otherExpenses.setLaborCrewCost(Double.parseDouble(otherExpensesFormLaborCrewCost.getText()));
                otherExpenses.setLaborEquipmentCost(Double.parseDouble(otherExpensesFormLaborEquipmentCost.getText()));
                otherExpenses.setImplementationMode(otherExpensesFormImplementationMode.getText());
                otherExpenses.setNumberOfCD(Double.parseDouble(otherExpensesFormDaysOfOperation.getText()));
                otherExpenses.setLightEquipments(Double.parseDouble(otherExpensesFormLightEquipments.getText()));
                otherExpenses.setHeavyEquipments(Double.parseDouble(otherExpensesFormHeavyEquipments.getText()));
                otherExpenses.setMonth(String.valueOf(otherExpensesFormMonth.getSelectedItem()));
                otherExpenses.setYear(Integer.parseInt(String.valueOf(otherExpensesFormYear.getSelectedItem())));

                new OtherExpensesDBController().add(otherExpenses);

                JOptionPane.showMessageDialog(rootPane, "Successfully Added!");
                otherExpensesTab.removeAll();
                otherExpensesTab.add(mainOtherExpensesPanel);
                otherExpensesTab.repaint();
                otherExpensesTab.revalidate();
                otherExpensesList = new OtherExpensesDBController().getList();
                searchedOtherExpenses = otherExpensesList;
                populateMainOtherExpenses(otherExpensesList);
                resetOtherExpensesForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveNewOtherExpensesMouseClicked

    private void otherExpensesFormDaysOfOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherExpensesFormDaysOfOperationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherExpensesFormDaysOfOperationActionPerformed

    private void activityTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activityTabbedPaneMouseClicked
        if (activityTabbedPane.getSelectedIndex() == 0) {
            String[] sortingItems = {"id", "activity", "date", "implementation mode"};
            
            sortActivity.removeAllItems();
            for(String item : sortingItems){
                sortActivity.addItem(item);
            } 
            
            driversForEngineersContainer.removeAll();
            driversForEngineersContainer.add(mainDriversForEngineersPanel);
            driversForEngineersContainer.repaint();
            driversForEngineersContainer.revalidate();
            resetDriversForEngineersForm();
            resetDriversForEngineersEditForm();
            otherExpensesTab.removeAll();
            otherExpensesTab.add(mainOtherExpensesPanel);
            otherExpensesTab.repaint();
            otherExpensesTab.revalidate();
            resetOtherExpensesForm();
            resetOtherExpensesEditForm();
            viewOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
            otherActivityTab.removeAll();
            otherActivityTab.add(mainOtherActivityPanel);
            otherActivityTab.repaint();
            otherActivityTab.revalidate();
            resetOtherActivityEditForm();
            resetOtherActivityForm();
            viewOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
            projectsOfWorksTab.removeAll();
            projectsOfWorksTab.add(mainProjectsPanel);
            projectsOfWorksTab.repaint();
            projectsOfWorksTab.revalidate();   
            resetProjectsForm();
            viewProjectsScrollPane.getVerticalScrollBar().setValue(0);         
        } else if (activityTabbedPane.getSelectedIndex() == 1) {
            String[] sortingItems = {"id", "sub activity", "description", "date", "implementation mode"};
            
            sortActivity.removeAllItems();
            for(String item : sortingItems){
                sortActivity.addItem(item);
            }
            
            driversForEngineersContainer.removeAll();
            driversForEngineersContainer.add(mainDriversForEngineersPanel);
            driversForEngineersContainer.repaint();
            driversForEngineersContainer.revalidate();
            resetDriversForEngineersForm();
            resetDriversForEngineersEditForm();
            otherExpensesTab.removeAll();
            otherExpensesTab.add(mainOtherExpensesPanel);
            otherExpensesTab.repaint();
            otherExpensesTab.revalidate();
            resetOtherExpensesForm();
            resetOtherExpensesEditForm();
            viewOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
            regularActivityTab.removeAll();
            regularActivityTab.add(mainRegularActivityPanel);
            regularActivityTab.repaint();
            regularActivityTab.revalidate();
            resetRegularActivityEditForm();
            resetRegularActivityForm();
            viewRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
            projectsOfWorksTab.removeAll();
            projectsOfWorksTab.add(mainProjectsPanel);
            projectsOfWorksTab.repaint();
            projectsOfWorksTab.revalidate();   
            resetProjectsForm();
            viewProjectsScrollPane.getVerticalScrollBar().setValue(0);
        } else if (activityTabbedPane.getSelectedIndex() == 2) {
            String[] sortingItems = {"id", "date"};
            
            sortActivity.removeAllItems();
            for(String item : sortingItems){
                sortActivity.addItem(item);
            } 
            
            driversForEngineersContainer.removeAll();
            driversForEngineersContainer.add(mainDriversForEngineersPanel);
            driversForEngineersContainer.repaint();
            driversForEngineersContainer.revalidate();
            resetDriversForEngineersForm();
            resetDriversForEngineersEditForm();
            regularActivityTab.removeAll();
            regularActivityTab.add(mainRegularActivityPanel);
            regularActivityTab.repaint();
            regularActivityTab.revalidate();
            resetRegularActivityEditForm();
            resetRegularActivityForm();
            viewRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
            otherActivityTab.removeAll();
            otherActivityTab.add(mainOtherActivityPanel);
            otherActivityTab.repaint();
            otherActivityTab.revalidate();
            resetOtherActivityEditForm();
            resetOtherActivityForm();
            viewOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
            projectsOfWorksTab.removeAll();
            projectsOfWorksTab.add(mainProjectsPanel);
            projectsOfWorksTab.repaint();
            projectsOfWorksTab.revalidate();   
            resetProjectsForm();
            viewProjectsScrollPane.getVerticalScrollBar().setValue(0);                  
        } else if (activityTabbedPane.getSelectedIndex() == 3) {
            String[] sortingItems = {"id", "date"};
            
            sortActivity.removeAllItems();
            for(String item : sortingItems){
                sortActivity.addItem(item);
            }
            
            regularActivityTab.removeAll();
            regularActivityTab.add(mainRegularActivityPanel);
            regularActivityTab.repaint();
            regularActivityTab.revalidate();
            resetRegularActivityEditForm();
            resetRegularActivityForm();
            viewRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
            otherActivityTab.removeAll();
            otherActivityTab.add(mainOtherActivityPanel);
            otherActivityTab.repaint();
            otherActivityTab.revalidate();
            resetOtherActivityEditForm();
            resetOtherActivityForm();
            viewOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
            otherExpensesTab.removeAll();
            otherExpensesTab.add(mainOtherExpensesPanel);
            otherExpensesTab.repaint();
            otherExpensesTab.revalidate();
            resetOtherExpensesForm();
            resetOtherExpensesEditForm();
            viewOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
            projectsOfWorksTab.removeAll();
            projectsOfWorksTab.add(mainProjectsPanel);
            projectsOfWorksTab.repaint();
            projectsOfWorksTab.revalidate();   
            resetProjectsForm();
            viewProjectsScrollPane.getVerticalScrollBar().setValue(0);
            
            timeframeDetailActionPerformed(null);
        } else if (activityTabbedPane.getSelectedIndex() == 4){
            String[] sortingItems = {"id", "source of fund","date"};
            
            sortActivity.removeAllItems();
            for(String item : sortingItems){
                sortActivity.addItem(item);
            }
            
            regularActivityTab.removeAll();
            regularActivityTab.add(mainRegularActivityPanel);
            regularActivityTab.repaint();
            regularActivityTab.revalidate();
            resetRegularActivityEditForm();
            resetRegularActivityForm();
            viewRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
            otherActivityTab.removeAll();
            otherActivityTab.add(mainOtherActivityPanel);
            otherActivityTab.repaint();
            otherActivityTab.revalidate();
            resetOtherActivityEditForm();
            resetOtherActivityForm();
            viewOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
            otherExpensesTab.removeAll();
            otherExpensesTab.add(mainOtherExpensesPanel);
            otherExpensesTab.repaint();
            otherExpensesTab.revalidate();
            resetOtherExpensesForm();
            resetOtherExpensesEditForm();
            viewOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
            driversForEngineersContainer.removeAll();
            driversForEngineersContainer.add(mainDriversForEngineersPanel);
            driversForEngineersContainer.repaint();
            driversForEngineersContainer.revalidate();
            resetDriversForEngineersForm();
            resetDriversForEngineersEditForm();
        }
    }//GEN-LAST:event_activityTabbedPaneMouseClicked

    private void timeRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeRangeActionPerformed
        String selectedTimeRange = String.valueOf(timeRange.getSelectedItem());
        initTimeframeDetail(selectedTimeRange);
    }//GEN-LAST:event_timeRangeActionPerformed

    private void timeframeDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeframeDetailActionPerformed
        if(Driver.getConnection() != null){
            if(timeframeDetail.getItemCount() > 0){
                GeneralDBController gdbc = new GeneralDBController();
                double totalLaborCrewCost, totalLaborEquipmentCost, totalEquipmentFuelCost, totalLubricantCost, totalGrandCost;

                String selectedTimeRange = String.valueOf(timeRange.getSelectedItem());
                Object selectedTimeFrame = timeframeDetail.getSelectedItem();

                switch (selectedTimeRange) {
                    case "Monthly":
                        totalLaborCrewCost = gdbc.getTotalLaborCrewCost(
                                gdbc.getMonthlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getMonthlyOtherActivityList(String.valueOf(selectedTimeFrame)), 
                                gdbc.getMonthlyOtherExpensesList(String.valueOf(selectedTimeFrame)));
                        laborCrewCost.setText("₱ " + setDecimalFormat(totalLaborCrewCost));

                        totalLaborEquipmentCost = gdbc.getTotalLaborEquipmentCost(
                                gdbc.getMonthlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getMonthlyOtherExpensesList(String.valueOf(selectedTimeFrame)),
                                gdbc.getMonthlyDriversForEngineersList(String.valueOf(selectedTimeFrame)));
                        laborEquipmentCost.setText("₱ " + setDecimalFormat(totalLaborEquipmentCost));

                        totalEquipmentFuelCost = gdbc.getTotalEquipmentFuelCost(
                                gdbc.getMonthlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getMonthlyDriversForEngineersList(String.valueOf(selectedTimeFrame)));
                        equipmentFuelCost.setText("₱ " + setDecimalFormat(totalEquipmentFuelCost));

                        totalLubricantCost = gdbc.getTotalLubricantCost(
                                gdbc.getMonthlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getMonthlyDriversForEngineersList(String.valueOf(selectedTimeFrame)));
                        lubricantCost.setText("₱ " + setDecimalFormat(totalLubricantCost));
                        
                        totalGrandCost = totalLaborCrewCost + totalLaborEquipmentCost + totalEquipmentFuelCost + totalLubricantCost;
                        grandTotalCost.setText("₱ " + setDecimalFormat(totalGrandCost));
                        break;
                    case "Quarterly":  
                        totalLaborCrewCost = gdbc.getTotalLaborCrewCost(
                                gdbc.getQuarterlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getQuarterlyOtherActivityList(String.valueOf(selectedTimeFrame)), 
                                gdbc.getQuarterlyOtherExpensesList(String.valueOf(selectedTimeFrame)));
                        laborCrewCost.setText("₱ " + setDecimalFormat(totalLaborCrewCost));

                        totalLaborEquipmentCost = gdbc.getTotalLaborEquipmentCost(
                                gdbc.getQuarterlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getQuarterlyOtherExpensesList(String.valueOf(selectedTimeFrame)),
                                gdbc.getQuarterlyDriversForEngineersList(String.valueOf(selectedTimeFrame)));
                        laborEquipmentCost.setText("₱ " + setDecimalFormat(totalLaborEquipmentCost));

                        totalEquipmentFuelCost = gdbc.getTotalEquipmentFuelCost(
                                gdbc.getQuarterlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getQuarterlyDriversForEngineersList(String.valueOf(selectedTimeFrame)));
                        equipmentFuelCost.setText("₱ " + setDecimalFormat(totalEquipmentFuelCost));

                        totalLubricantCost = gdbc.getTotalLubricantCost(
                                gdbc.getQuarterlyRegularActivityList(String.valueOf(selectedTimeFrame)),
                                gdbc.getQuarterlyDriversForEngineersList(String.valueOf(selectedTimeFrame)));
                        lubricantCost.setText("₱ " + setDecimalFormat(totalLubricantCost));
                        
                        totalGrandCost = totalLaborCrewCost + totalLaborEquipmentCost + totalEquipmentFuelCost + totalLubricantCost;
                        grandTotalCost.setText("₱ " + setDecimalFormat(totalGrandCost));
                        break;
                    case "Annually":    
                        totalLaborCrewCost = gdbc.getTotalLaborCrewCost(
                                gdbc.getAnnualRegularActivityList(Integer.parseInt(String.valueOf(selectedTimeFrame))),
                                gdbc.getAnnualOtherActivityList(Integer.parseInt(String.valueOf(selectedTimeFrame))), 
                                gdbc.getAnnualOtherExpensesList(Integer.parseInt(String.valueOf(selectedTimeFrame))));
                        laborCrewCost.setText("₱ " + setDecimalFormat(totalLaborCrewCost));

                        totalLaborEquipmentCost = gdbc.getTotalLaborEquipmentCost(
                                gdbc.getAnnualRegularActivityList(Integer.parseInt(String.valueOf(selectedTimeFrame))),
                                gdbc.getAnnualOtherExpensesList(Integer.parseInt(String.valueOf(selectedTimeFrame))),
                                gdbc.getAnnualDriversForEngineersList(Integer.parseInt(String.valueOf(selectedTimeFrame))));
                        laborEquipmentCost.setText("₱ " + setDecimalFormat(totalLaborEquipmentCost));

                        totalEquipmentFuelCost = gdbc.getTotalEquipmentFuelCost(
                                gdbc.getAnnualRegularActivityList(Integer.parseInt(String.valueOf(selectedTimeFrame))),
                                gdbc.getAnnualDriversForEngineersList(Integer.parseInt(String.valueOf(selectedTimeFrame))));
                        equipmentFuelCost.setText("₱ " + setDecimalFormat(totalEquipmentFuelCost));

                        totalLubricantCost = gdbc.getTotalLubricantCost(
                                gdbc.getAnnualRegularActivityList(Integer.parseInt(String.valueOf(selectedTimeFrame))),
                                gdbc.getAnnualDriversForEngineersList(Integer.parseInt(String.valueOf(selectedTimeFrame))));
                        lubricantCost.setText("₱ " + setDecimalFormat(totalLubricantCost));
                        
                        totalGrandCost = totalLaborCrewCost + totalLaborEquipmentCost + totalEquipmentFuelCost + totalLubricantCost;
                        grandTotalCost.setText("₱ " + setDecimalFormat(totalGrandCost));
                        break;
                }
            }
        } else {
            laborCrewCost.setText("₱ " + setDecimalFormat(0.0));
            laborEquipmentCost.setText("₱ " + setDecimalFormat(0.0));
            equipmentFuelCost.setText("₱ " + setDecimalFormat(0.0));
            lubricantCost.setText("₱ " + setDecimalFormat(0.0));
            grandTotalCost.setText("₱ " + setDecimalFormat(0.0));
        }
    }//GEN-LAST:event_timeframeDetailActionPerformed

    private void editDriversForEngineers1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editDriversForEngineers1MouseClicked
        int selectedDriversForEngineers = tableMainDriversForEngineers.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedDriversForEngineers > -1) {
                if(searchedDFE.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainDriversForEngineers.clearSelection();
                } else {
                    driversForEngineersContainer.removeAll();
                    driversForEngineersContainer.add(editDriversForEngineersPanel);
                    driversForEngineersContainer.repaint();
                    driversForEngineersContainer.revalidate();
                    setDriversForEngineersDataEdit(selectedDriversForEngineers);
                    tableMainDriversForEngineers.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editDriversForEngineers1MouseClicked

    private void addNewDriversForEngineers1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewDriversForEngineers1MouseClicked
        driversForEngineersContainer.removeAll();
        driversForEngineersContainer.add(addNewDriversForEngineersPanel);
        driversForEngineersContainer.repaint();
        driversForEngineersContainer.revalidate();
    }//GEN-LAST:event_addNewDriversForEngineers1MouseClicked

    private void backViewOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backViewOtherExpensesMouseClicked
        viewOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
        otherExpensesTab.removeAll();
        otherExpensesTab.add(mainOtherExpensesPanel);
        otherExpensesTab.repaint();
        otherExpensesTab.revalidate();
    }//GEN-LAST:event_backViewOtherExpensesMouseClicked

    private void cancelEditOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelEditOtherExpensesMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            otherExpensesTab.removeAll();
            otherExpensesTab.add(mainOtherExpensesPanel);
            otherExpensesTab.repaint();
            otherExpensesTab.revalidate();
            resetOtherExpensesEditForm();
        }
    }//GEN-LAST:event_cancelEditOtherExpensesMouseClicked

    private void saveEditOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveEditOtherExpensesMouseClicked
        if(Driver.getConnection() != null){
            if (otherExpensesFormEditLaborCrewCost.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a labor crew cost!");
            } else if (otherExpensesFormEditLaborEquipmentCost.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a labor equipment cost!");
            }  else if (!dataValidation.validateCurrency(otherExpensesFormLaborEquipmentCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid labor equipment cost!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormLaborCrewCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid labor crew cost!");
            } else if (otherExpensesFormEditImplementationMode.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write an implementation mode!");
            } else if (otherExpensesFormEditDaysOfOperation.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please write the days of operation!");
            } else if (!dataValidation.validateDouble(otherExpensesFormEditDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid days of operation!");
            } else if (otherExpensesFormEditLightEquipments.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter light equipments expenses!");
            } else if (otherExpensesFormEditHeavyEquipments.getText().isBlank()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter heavy equipments expenses!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormEditLightEquipments.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid light equipment cost!");
            } else if (!dataValidation.validateCurrency(otherExpensesFormEditHeavyEquipments.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid heavy equipment cost!");
            } else {
                otherExpensesForEdit.setLaborCrewCost(Double.parseDouble(otherExpensesFormEditLaborCrewCost.getText()));
                otherExpensesForEdit.setLaborEquipmentCost(Double.parseDouble(otherExpensesFormEditLaborEquipmentCost.getText()));
                otherExpensesForEdit.setImplementationMode(otherExpensesFormEditImplementationMode.getText());
                otherExpensesForEdit.setNumberOfCD(Double.parseDouble(otherExpensesFormEditDaysOfOperation.getText()));
                otherExpensesForEdit.setLightEquipments(Double.parseDouble(otherExpensesFormEditLightEquipments.getText()));
                otherExpensesForEdit.setHeavyEquipments(Double.parseDouble(otherExpensesFormEditHeavyEquipments.getText()));
                otherExpensesForEdit.setMonth(String.valueOf(otherExpensesFormEditMonth.getSelectedItem()));
                otherExpensesForEdit.setYear(Integer.parseInt(String.valueOf(otherExpensesFormEditYear.getSelectedItem())));

                new OtherExpensesDBController().edit(otherExpensesForEdit);

                JOptionPane.showMessageDialog(rootPane, "Changes Saved!");
                otherExpensesTab.removeAll();
                otherExpensesTab.add(mainOtherExpensesPanel);
                otherExpensesTab.repaint();
                otherExpensesTab.revalidate();
                otherExpensesList = new OtherExpensesDBController().getList();
                searchedOtherExpenses = otherExpensesList;
                populateMainOtherExpenses(otherExpensesList);
                resetOtherExpensesEditForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveEditOtherExpensesMouseClicked

    private void otherExpensesFormEditDaysOfOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherExpensesFormEditDaysOfOperationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherExpensesFormEditDaysOfOperationActionPerformed

    private void cancelNewOtherExpenses1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherExpenses1MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            driversForEngineersContainer.removeAll();
            driversForEngineersContainer.add(mainDriversForEngineersPanel);
            driversForEngineersContainer.repaint();
            driversForEngineersContainer.revalidate();
            resetDriversForEngineersForm();
        }
    }//GEN-LAST:event_cancelNewOtherExpenses1MouseClicked

    private void saveNewOtherExpenses1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNewOtherExpenses1MouseClicked
        if(Driver.getConnection() != null) {
            if(driversForEngineersFormLaborEquipmentCost.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the labor equipment cost!");
            } else if(driversForEngineersFormEquipmentFuelCost.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the equipment fuel cost!");
            } else if(driversForEngineersFormLubricantCost.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the lubricant cost!");
            } else if(driversForEngineersFormDaysOfOperation.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the days of operation!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid days of operation!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormLaborEquipmentCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid labor equipment cost!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormEquipmentFuelCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid equipment fuel cost!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormLubricantCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid lubricant cost!");
            } else {
                DriversForEngineers driversForEngineers = new DriversForEngineers();

                driversForEngineers.setLaborEquipmentCost(Double.parseDouble(driversForEngineersFormLaborEquipmentCost.getText()));
                driversForEngineers.setEquipmentFuelCost(Double.parseDouble(driversForEngineersFormEquipmentFuelCost.getText()));
                driversForEngineers.setLubricantCost(Double.parseDouble(driversForEngineersFormLubricantCost.getText()));
                driversForEngineers.setImplementationMode(driversForEngineersFormImplementationMode.getText());
                driversForEngineers.setNumberOfCD(Double.parseDouble(driversForEngineersFormDaysOfOperation.getText()));
                driversForEngineers.setMonth(String.valueOf(driversForEngineersFormMonth.getSelectedItem()));
                driversForEngineers.setYear(Integer.parseInt(String.valueOf(driversForEngineersFormYear.getSelectedItem())));

                new DriversForEngineersDBController().add(driversForEngineers);
                JOptionPane.showMessageDialog(rootPane, "Successfully Added!");
                driversForEngineersList = new DriversForEngineersDBController().getList();
                searchedDFE = driversForEngineersList;
                populateDriversForEngineersTable(driversForEngineersList);
                driversForEngineersContainer.removeAll();
                driversForEngineersContainer.add(mainDriversForEngineersPanel);
                driversForEngineersContainer.repaint();
                driversForEngineersContainer.revalidate();
                resetDriversForEngineersForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveNewOtherExpenses1MouseClicked

    private void cancelNewOtherExpenses2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherExpenses2MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            driversForEngineersContainer.removeAll();
            driversForEngineersContainer.add(mainDriversForEngineersPanel);
            driversForEngineersContainer.repaint();
            driversForEngineersContainer.revalidate();
            resetDriversForEngineersEditForm();
        }
    }//GEN-LAST:event_cancelNewOtherExpenses2MouseClicked

    private void saveDFEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveDFEMouseClicked
        if(Driver.getConnection() != null) {
            if(driversForEngineersFormEditLaborEquipmentCost.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the labor equipment cost!");
            } else if(driversForEngineersFormEditEquipmentFuelCost.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the equipment fuel cost!");
            } else if(driversForEngineersFormEditLubricantCost.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the lubricant cost!");
            } else if(driversForEngineersFormEditDaysOfOperation.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the days of operation!");
            }   else if(!dataValidation.validateDouble(driversForEngineersFormEditDaysOfOperation.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid days of operation!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormEditLaborEquipmentCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid labor equipment cost!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormEditEquipmentFuelCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid equipment fuel cost!");
            } else if(!dataValidation.validateDouble(driversForEngineersFormEditLubricantCost.getText())){
                JOptionPane.showMessageDialog(rootPane, "Please enter the valid lubricant cost!");
            } else {

                driversForEngineersForEdit.setLaborEquipmentCost(Double.parseDouble(driversForEngineersFormEditLaborEquipmentCost.getText()));
                driversForEngineersForEdit.setEquipmentFuelCost(Double.parseDouble(driversForEngineersFormEditEquipmentFuelCost.getText()));
                driversForEngineersForEdit.setLubricantCost(Double.parseDouble(driversForEngineersFormEditLubricantCost.getText()));
                driversForEngineersForEdit.setImplementationMode(driversForEngineersFormEditImplementationMode.getText());
                driversForEngineersForEdit.setNumberOfCD(Double.parseDouble(driversForEngineersFormEditDaysOfOperation.getText()));
                driversForEngineersForEdit.setMonth(String.valueOf(driversForEngineersFormEditMonth.getSelectedItem()));
                driversForEngineersForEdit.setYear(Integer.parseInt(String.valueOf(driversForEngineersFormEditYear.getSelectedItem())));

                new DriversForEngineersDBController().edit(driversForEngineersForEdit);
                JOptionPane.showMessageDialog(rootPane, "Successfully Edited!");
                driversForEngineersList = new DriversForEngineersDBController().getList();
                searchedDFE = driversForEngineersList;
                populateDriversForEngineersTable(driversForEngineersList);
                driversForEngineersContainer.removeAll();
                driversForEngineersContainer.add(mainDriversForEngineersPanel);
                driversForEngineersContainer.repaint();
                driversForEngineersContainer.revalidate();
                resetDriversForEngineersEditForm();
                timeframeDetailActionPerformed(null);
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveDFEMouseClicked

    private void editProjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProjectsMouseClicked
        int selectedProgram = tableMainPrograms.getSelectedRow();
        if(Driver.getConnection() != null){
            if(selectedProgram > -1){
                if(searchedProgram.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainDriversForEngineers.clearSelection();
                } else {
                    projectsOfWorksTab.removeAll();
                    projectsOfWorksTab.add(editProjectsPanel);
                    projectsOfWorksTab.repaint();
                    projectsOfWorksTab.revalidate();
                    setProgramDataEdit(selectedProgram);
                    tableMainPrograms.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_editProjectsMouseClicked

    private void viewProjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewProjectsMouseClicked
        int selectedProgram = tableMainPrograms.getSelectedRow();
        if(Driver.getConnection() != null){
            if(selectedProgram > -1){
                if(searchedProgram.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainDriversForEngineers.clearSelection();
                } else {
                    projectsOfWorksTab.removeAll();
                    projectsOfWorksTab.add(viewProjectsPanel);
                    projectsOfWorksTab.repaint();
                    projectsOfWorksTab.revalidate();
                    setProgramDataView(selectedProgram);
                    tableMainPrograms.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to view!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_viewProjectsMouseClicked

    private void addNewProjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewProjectsMouseClicked
        projectsOfWorksTab.removeAll();
        projectsOfWorksTab.add(addNewProjectsPanel);
        projectsOfWorksTab.repaint();
        projectsOfWorksTab.revalidate();
    }//GEN-LAST:event_addNewProjectsMouseClicked

    private void cancelNewOtherActivity2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherActivity2MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            projectsOfWorksTab.removeAll();
            projectsOfWorksTab.add(mainProjectsPanel);
            projectsOfWorksTab.repaint();
            projectsOfWorksTab.revalidate();    
            resetProjectsForm();
        }
    }//GEN-LAST:event_cancelNewOtherActivity2MouseClicked

    private void saveNewOtherActivity1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNewOtherActivity1MouseClicked
        if(Driver.getConnection() != null) {
            if(projectsFormSourceOfFund.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please indicate the source of fund!");
            } else if (projectList.isEmpty()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the projects related to the fund!");
            } else {
                Program program = new Program();

                program.setSourceOfFund(projectsFormSourceOfFund.getText());
                program.setMonth(String.valueOf(projectsFormMonth.getSelectedItem()));
                program.setYear(Integer.parseInt(String.valueOf(projectsFormYear.getSelectedItem())));

                new ProgramDBController().add(program, projectList);
                JOptionPane.showMessageDialog(rootPane, "Successfully Added!");
                programList = new ProgramDBController().getList();
                searchedProgram = programList;
                populateMainProgram(programList);
                projectsOfWorksTab.removeAll();
                projectsOfWorksTab.add(mainProjectsPanel);
                projectsOfWorksTab.repaint();
                projectsOfWorksTab.revalidate();    
                resetProjectsForm();
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveNewOtherActivity1MouseClicked

    private void addProjectsItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addProjectsItemMouseClicked
        AddProject.setListener(this);
        AddProject.setFormType(1);
        AddProject.getInstance().showFrame();
    }//GEN-LAST:event_addProjectsItemMouseClicked

    private void editProjectsItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProjectsItemMouseClicked
        int selectedProject = tableProjects.getSelectedRow();

        if (selectedProject > -1) {
            EditProject.setData(selectedProject, projectList.get(selectedProject));
            EditProject.setFormType(1);
            EditProject.setListener(this);
            EditProject.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editProjectsItemMouseClicked

    private void removeProjectsItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeProjectsItemMouseClicked
        int n = tableProjects.getSelectedRow();

        if (n > -1) {
            projectList.remove(n);
            populateProjects(projectList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeProjectsItemMouseClicked

    private void cancelNewOtherActivity3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNewOtherActivity3MouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to cancel this without saving?");
        if(n == 0){
            projectsOfWorksTab.removeAll();
            projectsOfWorksTab.add(mainProjectsPanel);
            projectsOfWorksTab.repaint();
            projectsOfWorksTab.revalidate();    
            resetProjectsFormEdit();
        }
    }//GEN-LAST:event_cancelNewOtherActivity3MouseClicked

    private void saveNewOtherActivity2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNewOtherActivity2MouseClicked
        if(Driver.getConnection() != null){
            if(projectsFormEditSourceOfFund.getText().isBlank()){
                JOptionPane.showMessageDialog(rootPane, "Please indicate the source of fund!");
            } else if (projectList.isEmpty()){
                JOptionPane.showMessageDialog(rootPane, "Please enter the projects related to the fund!");
            } else {           
                programForEdit.setSourceOfFund(projectsFormEditSourceOfFund.getText());
                programForEdit.setMonth(String.valueOf(projectsFormEditMonth.getSelectedItem()));
                programForEdit.setYear(Integer.parseInt(String.valueOf(projectsFormEditYear.getSelectedItem())));

                new ProgramDBController().edit(programForEdit, projectList);
                JOptionPane.showMessageDialog(rootPane, "Changes Saved!");
                programList = new ProgramDBController().getList();
                searchedProgram = programList;
                populateMainProgram(programList);
                projectsOfWorksTab.removeAll();
                projectsOfWorksTab.add(mainProjectsPanel);
                projectsOfWorksTab.repaint();
                projectsOfWorksTab.revalidate();    
                resetProjectsFormEdit();
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_saveNewOtherActivity2MouseClicked

    private void addProjectsItemEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addProjectsItemEditMouseClicked
        AddProject.setListener(this);
        AddProject.setFormType(2);
        AddProject.getInstance().showFrame();
    }//GEN-LAST:event_addProjectsItemEditMouseClicked

    private void editProjectsItemEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProjectsItemEditMouseClicked
        int selectedProject = tableEditProjects.getSelectedRow();

        if (selectedProject > -1) {
            EditProject.setData(selectedProject, projectList.get(selectedProject));
            EditProject.setFormType(2);
            EditProject.setListener(this);
            EditProject.getInstance().showFrame();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to edit!");
        }
    }//GEN-LAST:event_editProjectsItemEditMouseClicked

    private void removeProjectsItemEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeProjectsItemEditMouseClicked
        int n = tableEditProjects.getSelectedRow();

        if (n > -1) {
            projectList.remove(n);
            populateEditProjects(projectList);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to be remove!");
        }
    }//GEN-LAST:event_removeProjectsItemEditMouseClicked

    private void backViewProgramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backViewProgramMouseClicked
        viewProjectsScrollPane.getVerticalScrollBar().setValue(0);
        projectsOfWorksTab.removeAll();
        projectsOfWorksTab.add(mainProjectsPanel);
        projectsOfWorksTab.repaint();
        projectsOfWorksTab.revalidate(); 
    }//GEN-LAST:event_backViewProgramMouseClicked

    private void exportMonthlyReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMonthlyReportActionPerformed
        if(Driver.getConnection() != null){
            if(!monthlyHeaderTitle.getText().isBlank()){
                if(evt.getSource() == exportMonthlyReport){
                    int returnVal = fileChooser.showSaveDialog(Main.this);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getCurrentDirectory();
                        File fileName = fileChooser.getSelectedFile();

                        MonthlyReportBuilder builder = new ReportFactory().createMonthlyReportBuilder();
                        MonthlyReport report = builder
                                                    .setHeaderTitle(monthlyHeaderTitle.getText())
                                                    .setTimeFrameDetail(monthlyMonth.getSelectedItem(), Integer.parseInt(String.valueOf(monthlyYear.getSelectedItem())))
                                                    .setFilePath(file.getAbsolutePath() + "\\", fileName)
                                                    .build();

                        ExportLoadScreen exportLoadScreen = new ExportLoadScreen();
                        exportLoadScreen.setVisible(true);

                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
                            @Override
                            protected Void doInBackground() throws Exception {
                                report.generateReport();
                                
                                File jarDir = JarDirectory.getJarDir(Main.class);
                                File parentDir = jarDir.getParentFile();
                                final String REPORT_FILE = "src/mdqrs/path/to/report_header_config.properties";
                                File reportFile = new File(parentDir, REPORT_FILE);
                                try (OutputStream output = new FileOutputStream(reportFile)) {
                                    Properties headerTitle = new Properties();

                                    headerTitle.setProperty("header_title", monthlyHeaderTitle.getText());
                                    headerTitle.store(output, null);

                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void done(){
                                exportLoadScreen.dispose();
                                int n = JOptionPane.showConfirmDialog(rootPane, "Report successfully exported and saved at " + report.getFileDirectory() + ". Do you want to open this file?");
                            
                                if(n == 0){
                                    report.openReport();
                                }
                            }
                        };

                        worker.execute();
                    }
                }  
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please write a header title for the report!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message);
        } 
    }//GEN-LAST:event_exportMonthlyReportActionPerformed

    private void saveReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveReportMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to save these changes?");

        File jarDir = null;
        
        try{
            jarDir = JarDirectory.getJarDir(Main.class);
        } catch (URISyntaxException | IOException e){}
        
        File parentDir = jarDir.getParentFile();
        
        final String REPORT_FILE_1 = "src/mdqrs/path/to/report_config.properties";
        File file1 = new File(parentDir, REPORT_FILE_1);
        final String REPORT_FILE_2 = "src/mdqrs/path/to/quarterly_report_config.properties";
        File file2 = new File(parentDir,REPORT_FILE_2);
        
        if (n == 0) {
            try (OutputStream output = new FileOutputStream(file1)) {
                Properties report = new Properties();

                report.setProperty("prepared_by_1_name", preparedBy1Name.getText());
                report.setProperty("prepared_by_1_position", preparedBy1Position.getText());
                report.setProperty("prepared_by_2_name", preparedBy2Name.getText());
                report.setProperty("prepared_by_2_position", preparedBy2Position.getText());
                report.setProperty("submitted_by_name", submittedByName.getText());
                report.setProperty("submitted_by_position", submittedByPosition.getText());
                report.setProperty("approved_by_name", approvedByName.getText());
                report.setProperty("approved_by_position", approvedByPosition.getText());
                
                report.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } else {
            try (FileInputStream input = new FileInputStream(file1)) {
                Properties report = new Properties();
                report.load(input);
                
                preparedBy1Name.setText(report.getProperty("prepared_by_1_name"));
                preparedBy1Position.setText(report.getProperty("prepared_by_1_position"));
                preparedBy2Name.setText(report.getProperty("prepared_by_2_name"));
                preparedBy2Position.setText(report.getProperty("prepared_by_2_position"));
                submittedByName.setText(report.getProperty("submitted_by_name"));
                submittedByPosition.setText(report.getProperty("submitted_by_position"));
                approvedByName.setText(report.getProperty("approved_by_name"));
                approvedByPosition.setText(report.getProperty("approved_by_position"));
                
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }//GEN-LAST:event_saveReportMouseClicked

    private void sortPersonnelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortPersonnelActionPerformed
        String sort = String.valueOf(sortPersonnel.getSelectedItem());
        
        switch(sort){
            case "id":
                Collections.sort(searchedPersonnel, Comparator.comparing(Personnel::getId));
                populatePersonnelTable(searchedPersonnel);
                break;
            
            case "name":
                Collections.sort(searchedPersonnel, Comparator.comparing(Personnel::getName));
                populatePersonnelTable(searchedPersonnel);
                break;
                
            case "type":
                Collections.sort(searchedPersonnel, Comparator.comparing(Personnel::getType));
                populatePersonnelTable(searchedPersonnel);
        }
    }//GEN-LAST:event_sortPersonnelActionPerformed

    private void sortEquipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortEquipmentActionPerformed
        String sort = String.valueOf(sortEquipment.getSelectedItem());
        
        switch(sort){
            case "equipment no.":
                Collections.sort(searchedEquipment, Comparator.comparing(Equipment::getEquipmentNumber));
                populateEquipmentTable(searchedEquipment);
                break;
                
            case "type":                
                Collections.sort(searchedEquipment, Comparator.comparing(Equipment::getType));
                populateEquipmentTable(searchedEquipment);
        }
    }//GEN-LAST:event_sortEquipmentActionPerformed

    private void workCategoryTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workCategoryTabbedPaneMouseClicked
        int selectedTab = workCategoryTabbedPane.getSelectedIndex();
        
        switch(selectedTab){
            case 0:
                String[] items = {"category no.", "description"};
                sortWorkCategory.removeAllItems();
                for(String item : items){
                    sortWorkCategory.addItem(item);
                }
                break;
            case 1:
                String[] items1 = {"item no.", "description", "work category"};
                sortWorkCategory.removeAllItems();
                for(String item : items1){
                    sortWorkCategory.addItem(item);
                }
                break;
            case 2:
                String[] items2 = {"description", "activity"};
                sortWorkCategory.removeAllItems();
                for(String item : items2){
                    sortWorkCategory.addItem(item);
                }
                break;
            default:
                String[] items3 = {"category no.", "description"};
                sortWorkCategory.removeAllItems();
                for(String item : items3){
                    sortWorkCategory.addItem(item);
                }
                break;    
        }
    }//GEN-LAST:event_workCategoryTabbedPaneMouseClicked

    private void sortWorkCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortWorkCategoryActionPerformed
        int selectedTab = workCategoryTabbedPane.getSelectedIndex();
        String sort = String.valueOf(sortWorkCategory.getSelectedItem());
        
        switch(selectedTab){
            case 0:
                switch(sort){
                    case "category no.":
                        Collections.sort(searchedWorkCategory, Comparator.comparingInt(WorkCategory::getWorkCategoryNumber));
                        populateWorkCategoryTable(searchedWorkCategory);
                        break;
                    case "description":
                        Collections.sort(searchedWorkCategory, Comparator.comparing(WorkCategory::getDescription));
                        populateWorkCategoryTable(searchedWorkCategory);
                        break;
                }
                break;
                
            case 1:
                switch(sort){
                    case "item no.":
                        Collections.sort(searchedActivity, Comparator.comparing(Activity::getItemNumber));
                        initAddActivitySelectionBox();
                        initEditActivitySelectionBox();
                        populateActivityTable(searchedActivity);
                        break;
                    case "description":
                        Collections.sort(searchedActivity, Comparator.comparing(Activity::getDescription));
                        initAddActivitySelectionBox();
                        initEditActivitySelectionBox();
                        populateActivityTable(searchedActivity);
                        break;
                    case "work category":
                        Collections.sort(searchedActivity, new ActivityComparator());
                        initAddActivitySelectionBox();
                        initEditActivitySelectionBox();
                        populateActivityTable(searchedActivity);
                        break;
                }
                break;
                
            case 2:
                switch(sort){
                    case "description":
                        Collections.sort(searchedSubActivity, Comparator.comparing(SubActivity::getDescription));
                        populateSubActivityTable(searchedSubActivity);
                        break;
                    case "activity":
                        Collections.sort(searchedSubActivity, new SubActivityComparator());
                        populateSubActivityTable(searchedSubActivity);
                        break;
                }
                break;
        }
    }//GEN-LAST:event_sortWorkCategoryActionPerformed

    private void sortActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortActivityActionPerformed
        int selectedTab = activityTabbedPane.getSelectedIndex();
        String sort = String.valueOf(sortActivity.getSelectedItem());
        
        switch(selectedTab){
            case 0:
                switch(sort){
                    case "id":
                        Collections.sort(searchedRegularActivity, Comparator.comparing(RegularActivity::getId));
                        Collections.reverse(searchedRegularActivity);
                        populateMainRegularActivity(searchedRegularActivity);
                        break;

                    case "activity":
                        Collections.sort(searchedRegularActivity, new RegularActivityComparator());
                        populateMainRegularActivity(searchedRegularActivity);
                        break;

                    case "date":
                        Collections.sort(searchedRegularActivity, new DateComparator());
                        Collections.reverse(searchedRegularActivity);
                        populateMainRegularActivity(searchedRegularActivity);
                        break;

                    case "implementation mode":
                        Collections.sort(searchedRegularActivity, Comparator.comparing(RegularActivity::getImplementationMode));
                        populateMainRegularActivity(searchedRegularActivity);
                        break;                       
                }
                break;
                
            case 1:      
                switch(sort){
                    case "id":
                        Collections.sort(searchedOtherActivity, Comparator.comparing(OtherActivity::getId));
                        Collections.reverse(searchedOtherActivity);
                        populateMainOtherActivity(searchedOtherActivity);
                        break;

                    case "sub activity":
                        Collections.sort(searchedOtherActivity, new OASubActivityComparator());
                        populateMainOtherActivity(searchedOtherActivity);
                        break;

                    case "description":
                        Collections.sort(searchedOtherActivity, Comparator.comparing(OtherActivity::getDescription));
                        populateMainOtherActivity(searchedOtherActivity);
                        break;

                    case "date":
                        Collections.sort(searchedOtherActivity, Comparator.comparing(OtherActivity::getDate));
                        populateMainOtherActivity(searchedOtherActivity);
                        break;

                    case "implementation mode":
                        Collections.sort(searchedOtherActivity, Comparator.comparing(OtherActivity::getImplementationMode));
                        populateMainOtherActivity(searchedOtherActivity);
                        break;
                }
                break;
                
            case 2:
                switch(sort){
                    case "id":
                        Collections.sort(searchedOtherExpenses, Comparator.comparing(OtherExpenses::getId));
                        Collections.reverse(searchedOtherExpenses);
                        populateMainOtherExpenses(searchedOtherExpenses);
                        break;

                    case "date":
                        Collections.sort(searchedOtherExpenses, Comparator.comparing(OtherExpenses::getDate));
                        populateMainOtherExpenses(searchedOtherExpenses);
                        break;
                }
                break;
            
            case 3:
                switch(sort){
                    case "id":
                        Collections.sort(searchedDFE, Comparator.comparing(DriversForEngineers::getId));
                        Collections.reverse(searchedDFE);
                        populateDriversForEngineersTable(searchedDFE);
                        break;

                    case "date":
                        Collections.sort(searchedDFE, Comparator.comparing(DriversForEngineers::getDate));
                        populateDriversForEngineersTable(searchedDFE);
                        break;
                }
                break;
                
            case 4:
                switch(sort){
                    case "id":
                        Collections.sort(searchedProgram, Comparator.comparing(Program::getId));
                        Collections.reverse(searchedProgram);
                        populateMainProgram(searchedProgram);
                        break;

                    case "source of fund":
                        Collections.sort(searchedProgram, Comparator.comparing(Program::getSourceOfFund));
                        populateMainProgram(searchedProgram);
                        break;
                        
                    case "date":
                        Collections.sort(searchedProgram, Comparator.comparing(Program::getDate));
                        populateMainProgram(searchedProgram);
                        break;
                }
                break;       
        }
    }//GEN-LAST:event_sortActivityActionPerformed

    private void searchPersonnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchPersonnelMouseClicked
        searchedPersonnel = personnelList
                                                    .stream()
                                                    .filter(personnel -> personnel.getName()
                                                                                  .toLowerCase()
                                                                                  .contains(searchPersonnelValue.getText().toLowerCase()) 
                                                            || personnel.getId().equals(searchPersonnelValue.getText()) 
                                                            || personnel.getType()
                                                                        .toLowerCase()
                                                                        .contains(searchPersonnelValue.getText().toLowerCase()))
                                                    .collect(Collectors.toCollection(ArrayList::new));
                
        if(!searchPersonnelValue.getText().isBlank()){
            populatePersonnelTable(searchedPersonnel);
        } else {
            populatePersonnelTable(personnelList);
        }   
    }//GEN-LAST:event_searchPersonnelMouseClicked

    private void searchEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchEquipmentMouseClicked
        searchedEquipment = equipmentList
                                                        .stream()
                                                        .filter(equipment -> equipment.getEquipmentNumber().equals(equipmentSearchValue.getText()) 
                                                                || equipment.getType().toLowerCase().contains(equipmentSearchValue.getText().toLowerCase()))
                                                        .collect(Collectors.toCollection(ArrayList::new));
        
        if(!equipmentSearchValue.getText().isBlank()){
            populateEquipmentTable(searchedEquipment);
        } else {
            populateEquipmentTable(equipmentList);
        } 
    }//GEN-LAST:event_searchEquipmentMouseClicked

    private void searchWorkCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchWorkCategoryMouseClicked
        int selectedTab = workCategoryTabbedPane.getSelectedIndex();
        
        switch(selectedTab){
            case 0:
                searchedWorkCategory = workCategoryList
                                                                        .stream()
                                                                        .filter(workCategory -> String.valueOf(workCategory.getWorkCategoryNumber()).equals(workCategorySearchValue.getText())
                                                                                || workCategory.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase()))
                                                                        .collect(Collectors.toCollection(ArrayList::new));
                if(!workCategorySearchValue.getText().isBlank()){
                    populateWorkCategoryTable(searchedWorkCategory);
                } else {
                    populateWorkCategoryTable(workCategoryList);
                }
                break;
            
            case 1:
                searchedActivity = activityList
                                                                        .stream()
                                                                        .filter(activity -> activity.getItemNumber().equals(workCategorySearchValue.getText())
                                                                                || activity.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                || activity.getWorkCategory().getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase()))
                                                                        .collect(Collectors.toCollection(ArrayList::new));
                if(!workCategorySearchValue.getText().isBlank()){
                    populateActivityTable(searchedActivity);
                } else {
                    populateActivityTable(activityList);
                }
                break; 
                
            case 2:
                searchedSubActivity = subActivityList
                                                                        .stream()
                                                                        .filter(subActivity -> subActivity.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                || subActivity.getActivity().getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                || subActivity.getActivity().getItemNumber().equals(workCategorySearchValue.getText()))
                                                                        .collect(Collectors.toCollection(ArrayList::new));
                if(!workCategorySearchValue.getText().isBlank()){
                    populateSubActivityTable(searchedSubActivity);
                } else {
                    populateSubActivityTable(subActivityList);
                }
                break;  
        }
    }//GEN-LAST:event_searchWorkCategoryMouseClicked

    private void searchActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchActivityMouseClicked
        int selectedTab = activityTabbedPane.getSelectedIndex();
        
        switch(selectedTab){
            case 0:
                /*
                searchedRegularActivity = regularActivityList
                                                                                    .stream()
                                                                                    .filter(regularActivity -> regularActivity.getId().equals(activitySearchValue.getText()) 
                                                                                            || regularActivity.getActivity().getItemNumber().equals(activitySearchValue.getText())
                                                                                            || regularActivity.getActivity().getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || regularActivity.getLocation().getLocation().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || regularActivity.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || regularActivity.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                    .collect(Collectors.toCollection(ArrayList::new));
                */
                fullPage = Math.ceil((double) new ActivityListDBController().getSearchCount(activitySearchValue.getText()) / (double) PAGE_LIMIT);  
                currentPage = 1;
                updateSearchedRegularActivity(activitySearchValue.getText());   
                /*
                if(!activitySearchValue.getText().isBlank()){
                    populateMainRegularActivity(searchedRegularActivity);
                } else {
                    populateMainRegularActivity(regularActivityList);
                }
                */
                break;
            
            case 1:
                searchedOtherActivity = otherActivityList
                                                                                .stream()
                                                                                .filter(otherActivity -> otherActivity.getId().equals(activitySearchValue.getText()) 
                                                                                        || otherActivity.getSubActivity().getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                        || otherActivity.getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                        || otherActivity.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                        || otherActivity.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                .collect(Collectors.toCollection(ArrayList::new));
                if(!activitySearchValue.getText().isBlank()){
                    populateMainOtherActivity(searchedOtherActivity);
                } else {
                    populateMainOtherActivity(otherActivityList);
                }
                break;
                
            case 2:
                searchedOtherExpenses = otherExpensesList
                                                                                .stream()
                                                                                .filter(otherExpenses -> otherExpenses.getId().equals(activitySearchValue.getText()) 
                                                                                        || otherExpenses.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                        || otherExpenses.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                .collect(Collectors.toCollection(ArrayList::new));
                if(!activitySearchValue.getText().isBlank()){
                    populateMainOtherExpenses(searchedOtherExpenses);
                } else {
                    populateMainOtherExpenses(otherExpensesList);
                }
                break;
            
            case 3:
                searchedDFE = driversForEngineersList
                                                                                .stream()
                                                                                .filter(dfe -> dfe.getId().equals(activitySearchValue.getText()) 
                                                                                        || dfe.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                        || dfe.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                .collect(Collectors.toCollection(ArrayList::new));
                if(!activitySearchValue.getText().isBlank()){
                    populateDriversForEngineersTable(searchedDFE);
                } else {
                    populateDriversForEngineersTable(driversForEngineersList);
                }
                break;
            
            case 4:
                searchedProgram = programList
                                                            .stream()
                                                            .filter(program -> program.getId().equals(activitySearchValue.getText()) 
                                                                    || program.getSourceOfFund().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                    || program.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                            .collect(Collectors.toCollection(ArrayList::new));
                if(!activitySearchValue.getText().isBlank()){
                    populateMainProgram(searchedProgram);
                } else {
                    populateMainProgram(programList);
                }
                break;
        }
    }//GEN-LAST:event_searchActivityMouseClicked

    private void deleteRegularActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteRegularActivityMouseClicked
        int selectedRow = tableMainRegularActivity.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedRegularActivity.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainRegularActivity.clearSelection();
                } else {
                    RegularActivity regularActivity = searchedRegularActivity.get(selectedRow);
                    int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                    if(n == 0){
                        new ActivityListDBController().delete(regularActivity);
                        fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);                    
                        currentPage = 1;
                        updateRegularActivity();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteRegularActivityMouseClicked

    private void deleteOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteOtherActivityMouseClicked
        int selectedRow = tableMainOtherActivity.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedOtherActivity.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainOtherActivity.clearSelection();
                } else {
                    OtherActivity otherActivity = searchedOtherActivity.get(selectedRow);
                    int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                    if(n == 0){
                        new OtherActivityListDBController().delete(otherActivity);
                        otherActivityList = new OtherActivityListDBController().getList();
                        searchedOtherActivity = otherActivityList;
                        populateMainOtherActivity(otherActivityList);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteOtherActivityMouseClicked

    private void deleteOtherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteOtherExpensesMouseClicked
        int selectedRow = tableMainOtherExpenses.getSelectedRow();
        
        if(Driver.getConnection() != null) {
            if (selectedRow > -1) {
                if(searchedOtherExpenses.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainOtherExpenses.clearSelection();
                } else {
                    OtherExpenses otherExpenses = searchedOtherExpenses.get(selectedRow);
                    int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                    if(n == 0){
                        new OtherExpensesDBController().delete(otherExpenses);
                        otherExpensesList = new OtherExpensesDBController().getList();
                        searchedOtherExpenses = otherExpensesList;
                        populateMainOtherExpenses(otherExpensesList);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteOtherExpensesMouseClicked

    private void deleteDriversForEngineers1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteDriversForEngineers1MouseClicked
        int selectedRow = tableMainDriversForEngineers.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedDFE.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainDriversForEngineers.clearSelection();
                } else {
                    DriversForEngineers driversForEngineers = searchedDFE.get(selectedRow);
                    int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                    if(n == 0){
                        new DriversForEngineersDBController().delete(driversForEngineers);
                        driversForEngineersList = new DriversForEngineersDBController().getList();
                        searchedDFE = driversForEngineersList;
                        populateDriversForEngineersTable(driversForEngineersList);
                        timeframeDetailActionPerformed(null);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteDriversForEngineers1MouseClicked

    private void deleteProjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteProjectsMouseClicked
        int selectedRow = tableMainPrograms.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                if(searchedProgram.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "There's no data!");
                    tableMainDriversForEngineers.clearSelection();
                } else {
                    Program program = searchedProgram.get(selectedRow);
                    int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                    if(n == 0){
                        new ProgramDBController().delete(program);
                        programList = new ProgramDBController().getList();
                        searchedProgram = programList;
                        populateMainProgram(programList);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteProjectsMouseClicked

    private void deletePersonnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletePersonnelMouseClicked
        int selectedRow = tablePersonnel.getSelectedRow();
        
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                Personnel personnel = searchedPersonnel.get(selectedRow);
                int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                if(n == 0){
                    new PersonnelDBController().delete(personnel.getId());
                    personnelList = new PersonnelDBController().getList();
                    searchedPersonnel = personnelList;
                    populatePersonnelTable(personnelList);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        }  
    }//GEN-LAST:event_deletePersonnelMouseClicked

    private void deleteEquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteEquipmentMouseClicked
        int selectedRow = tableEquipment.getSelectedRow();
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                Equipment equipment = searchedEquipment.get(selectedRow);
                int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                if(n == 0){
                    new EquipmentDBController().delete(equipment.getEquipmentNumber());
                    equipmentList = new EquipmentDBController().getList();
                    searchedEquipment = equipmentList;
                    populateEquipmentTable(equipmentList);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        }
    }//GEN-LAST:event_deleteEquipmentMouseClicked

    private void deleteWorkCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteWorkCategoryMouseClicked
        int selectedRow = tableWorkCategory.getSelectedRow();
        
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                WorkCategory workCategory = searchedWorkCategory.get(selectedRow);
                int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                if(n == 0){
                    new WorkCategoryDBController().delete(workCategory.getWorkCategoryNumber());
                    workCategoryList = new WorkCategoryDBController().getList();
                    searchedWorkCategory = workCategoryList;
                    populateWorkCategoryTable(workCategoryList);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteWorkCategoryMouseClicked

    private void deleteActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteActivityMouseClicked
        int selectedRow = tableActivity.getSelectedRow();
        
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                Activity activity = searchedActivity.get(selectedRow);
                
                if(!activity.getItemNumber().equals("504")){
                    int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                    if(n == 0){
                        new ActivityDBController().delete(activity.getItemNumber());
                        activityList = new ActivityDBController().getList();
                        searchedActivity = activityList;
                        populateActivityTable(activityList);
                    } else {
                        tableActivity.clearSelection();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Forbidden Action! Removing 504 will cause unwanted system behaviour.", "Warning!", 2);
                    tableActivity.clearSelection();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteActivityMouseClicked

    private void deleteSubActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteSubActivityMouseClicked
        int selectedRow = tableSubActivity.getSelectedRow();
        
        if(Driver.getConnection() != null){
            if (selectedRow > -1) {
                SubActivity subActivity = searchedSubActivity.get(selectedRow);
                int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to delete this selected item? Warning: This action can't be undone!");
                if(n == 0){
                    new SubActivityDBController().delete(subActivity);
                    subActivityList = new SubActivityDBController().getList();
                    searchedSubActivity = subActivityList;
                    populateSubActivityTable(subActivityList);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a row to delete!");
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
        } 
    }//GEN-LAST:event_deleteSubActivityMouseClicked

    private void saveQuarterlyReportDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveQuarterlyReportDetailsMouseClicked
        int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to save these changes?");

        File jarDir = null;
        
        try{
            jarDir = JarDirectory.getJarDir(Main.class);
        } catch (URISyntaxException | IOException e){}
        
        File parentDir = jarDir.getParentFile();
        
        final String REPORT_FILE_2 = "src/mdqrs/path/to/quarterly_report_config.properties";
        File file2 = new File(parentDir,REPORT_FILE_2);
        
        if (n == 0) {
            try (OutputStream output = new FileOutputStream(file2)) {
                Properties report = new Properties();

                report.setProperty("total_provincial_roads", totalLengthOfProvincialRoads.getText());
                report.setProperty("total_provincial_roads_in_fair_to_good", totalLengthOfProvincialRoadsInFairToGood.getText());
                report.setProperty("total_budget", totalBudget.getText());
                
                report.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } else {
            try (FileInputStream input = new FileInputStream(file2)) {
                Properties report = new Properties();
                report.load(input);
                
                totalLengthOfProvincialRoads.setText(report.getProperty("total_provincial_roads"));
                totalLengthOfProvincialRoadsInFairToGood.setText(report.getProperty("total_provincial_roads_in_fair_to_good"));
                totalBudget.setText(report.getProperty("total_budget"));
                
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }//GEN-LAST:event_saveQuarterlyReportDetailsMouseClicked

    private void exportQuarterlyReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportQuarterlyReportMouseClicked
        if(Driver.getConnection() != null) {
            if(evt.getSource() == exportQuarterlyReport){
                int returnVal = fileChooser.showSaveDialog(Main.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getCurrentDirectory();
                    File fileName = fileChooser.getSelectedFile();

                    QuarterlyReportBuilder quarterlyBuilder = new ReportFactory().createQuarterlyReportBuilder();
                    QuarterlyReport quarterlyReport = quarterlyBuilder
                                                                    .setTimeFrameDetail("", Integer.parseInt(String.valueOf(quarterlyYear.getSelectedItem())))
                                                                    .setFilePath(file.getAbsolutePath() + "\\", fileName)
                                                                    .build();

                    ExportLoadScreen exportLoadScreen = new ExportLoadScreen();
                    exportLoadScreen.setVisible(true);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
                        @Override
                        protected Void doInBackground() throws Exception {
                            quarterlyReport.generateReport();
                            return null;
                        }

                        @Override
                        protected void done(){
                            exportLoadScreen.dispose();
                            int n = JOptionPane.showConfirmDialog(rootPane, "Report successfully exported and saved at " + quarterlyReport.getFileDirectory() + ". Do you want to open this file?");
                            
                            if(n == 0){
                                quarterlyReport.openReport();
                            }
                        }
                    };

                    worker.execute();
                }
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message);
        } 
    }//GEN-LAST:event_exportQuarterlyReportMouseClicked

    private void exportActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportActivityMouseClicked
        if(evt.getSource() == exportActivity){
            if(Driver.getConnection() != null){
                int returnVal = fileChooser.showSaveDialog(Main.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getCurrentDirectory();
                    File fileName = fileChooser.getSelectedFile();

                    RegularActivityReport regularReport = new RegularActivityReport(regularActivityToView);

                    regularReport.setFilePath(file.getAbsolutePath() + "\\", fileName);

                    ExportLoadScreen exportLoadScreen = new ExportLoadScreen();
                    exportLoadScreen.setVisible(true);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
                        @Override
                        protected Void doInBackground() throws Exception {
                            regularReport.generateReport();
                            return null;
                        }

                        @Override
                        protected void done(){
                            exportLoadScreen.dispose();
                            int n = JOptionPane.showConfirmDialog(rootPane, "Report successfully exported and saved at " + regularReport.getFileDirectory() + ". Do you want to open this file?");
                            
                            if(n == 0){
                                regularReport.openReport();
                            }
                        }
                    };

                    worker.execute();
                }
            } else {
                String message = "Error 59: An unexpected network error occurred.";
                JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
            } 
        } 
    }//GEN-LAST:event_exportActivityMouseClicked

    private void exportWorkbookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportWorkbookActionPerformed
        if(Driver.getConnection() != null) {
            if(evt.getSource() == exportWorkbook){
                int returnVal = fileChooser.showSaveDialog(Main.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getCurrentDirectory();
                    File fileName = fileChooser.getSelectedFile();

                    RegularActivityReport regularReport = new RegularActivityReport();

                    regularReport.setFilePath(file.getAbsolutePath() + "\\", fileName);

                    ExportLoadScreen exportLoadScreen = new ExportLoadScreen();
                    exportLoadScreen.setVisible(true);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
                        @Override
                        protected Void doInBackground() throws Exception {
                            regularReport.generateWorkbook(String.valueOf(regularActivityWorkbookMonth.getSelectedItem()), 
                                    Integer.parseInt(String.valueOf(regularActivityWorkbookYear.getSelectedItem())));
                            return null;
                        }

                        @Override
                        protected void done(){
                            exportLoadScreen.dispose();
                            int n = JOptionPane.showConfirmDialog(rootPane, "Report successfully exported and saved at " + regularReport.getFileDirectory() + ". Do you want to open this file?");
                            
                            if(n == 0){
                                regularReport.openReport();
                            }
                        }
                    };

                    worker.execute();
                }
            }
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message);
        } 
    }//GEN-LAST:event_exportWorkbookActionPerformed

    private void exportOtherActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportOtherActivityMouseClicked
        if(evt.getSource() == exportOtherActivity){
            if(Driver.getConnection() != null){
                int returnVal = fileChooser.showSaveDialog(Main.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getCurrentDirectory();
                    File fileName = fileChooser.getSelectedFile();

                    OtherActivityReport otherReport = new OtherActivityReport(otherActivityToView);

                    otherReport.setFilePath(file.getAbsolutePath() + "\\", fileName);

                    ExportLoadScreen exportLoadScreen = new ExportLoadScreen();
                    exportLoadScreen.setVisible(true);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
                        @Override
                        protected Void doInBackground() throws Exception {
                            otherReport.generateReport();
                            return null;
                        }

                        @Override
                        protected void done(){
                            exportLoadScreen.dispose();
                            int n = JOptionPane.showConfirmDialog(rootPane, "Report successfully exported and saved at " + otherReport.getFileDirectory() + ". Do you want to open this file?");
                            
                            if(n == 0){
                                otherReport.openReport();
                            }
                        }
                    };

                    worker.execute();
                }
            } else {
                String message = "Error 59: An unexpected network error occurred.";
                JOptionPane.showMessageDialog(rootPane, message,"Error", 0);
            } 
        } 
    }//GEN-LAST:event_exportOtherActivityMouseClicked

    private void personnelSettingsIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_personnelSettingsIconMouseClicked
        if(Driver.getConnection() != null){
        PersonnelSetting.setListener(this);
        PersonnelSetting.getInstance().showFrame();
        } else {
            String message = "Error 59: An unexpected network error occurred.";
            JOptionPane.showMessageDialog(rootPane, message, "Error", 0);
        }  
    }//GEN-LAST:event_personnelSettingsIconMouseClicked

    private void testNetworkConnectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testNetworkConnectionMouseClicked
        if(Driver.getConnection() != null){
            JOptionPane.showMessageDialog(rootPane, "You're connected to the server!", "Message", 1);
        } else {
            //JOptionPane.showMessageDialog(rootPane, "Can't connect to the server. Please check your network credentials and try again.", "Error", 0);
            new ConnectionErrorMessage().setVisible(true);
        }
    }//GEN-LAST:event_testNetworkConnectionMouseClicked

    private void systemRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_systemRefreshMouseClicked
        RefreshLoadScreen refreshLoadScreen = new RefreshLoadScreen();
        refreshLoadScreen.setVisible(true);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                initIcons();
                initNetworkSettings();
                initReportSettings();
                initDate();
                initSearchFieldListener();  
                initData();
                timeframeDetailActionPerformed(null);
                return null;
            }

            @Override
            protected void done(){
                refreshLoadScreen.dispose();
            }
        };

        worker.execute();
    }//GEN-LAST:event_systemRefreshMouseClicked

    private void activitySearchValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_activitySearchValueKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ){
            int selectedTab = activityTabbedPane.getSelectedIndex();
        
            switch(selectedTab){
                case 0:    
                    /*
                    searchedRegularActivity = regularActivityList
                                                                                        .stream()
                                                                                        .filter(regularActivity -> regularActivity.getId().equals(activitySearchValue.getText()) 
                                                                                                || regularActivity.getActivity().getItemNumber().equals(activitySearchValue.getText())
                                                                                                || regularActivity.getActivity().getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || regularActivity.getLocation().getLocation().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || regularActivity.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || regularActivity.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || regularActivity.getOtherRoadSection().toLowerCase().contains(activitySearchValue.getText())
                                                                                                || regularActivity.getRoadSection().getName().toLowerCase().contains(activitySearchValue.getText()))
                                                                                        .collect(Collectors.toCollection(ArrayList::new));
                    */  
                    fullPage = Math.ceil((double) new ActivityListDBController().getSearchCount(activitySearchValue.getText()) / (double) PAGE_LIMIT);  
                    currentPage = 1;
                    updateSearchedRegularActivity(activitySearchValue.getText());               
                    /*                   
                    if(!activitySearchValue.getText().isBlank()){
                        populateMainRegularActivity(searchedRegularActivity);
                    } else {
                        populateMainRegularActivity(regularActivityList);
                    }
                    */
                    break;

                case 1:
                    searchedOtherActivity = otherActivityList
                                                                                    .stream()
                                                                                    .filter(otherActivity -> otherActivity.getId().equals(activitySearchValue.getText()) 
                                                                                            || otherActivity.getSubActivity().getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || otherActivity.getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || otherActivity.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || otherActivity.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                    .collect(Collectors.toCollection(ArrayList::new));
                    if(!activitySearchValue.getText().isBlank()){
                        populateMainOtherActivity(searchedOtherActivity);
                    } else {
                        populateMainOtherActivity(otherActivityList);
                    }
                    break;

                case 2:
                    searchedOtherExpenses = otherExpensesList
                                                                                    .stream()
                                                                                    .filter(otherExpenses -> otherExpenses.getId().equals(activitySearchValue.getText()) 
                                                                                            || otherExpenses.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || otherExpenses.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                    .collect(Collectors.toCollection(ArrayList::new));
                    if(!activitySearchValue.getText().isBlank()){
                        populateMainOtherExpenses(searchedOtherExpenses);
                    } else {
                        populateMainOtherExpenses(otherExpensesList);
                    }
                    break;

                case 3:
                    searchedDFE = driversForEngineersList
                                                                                    .stream()
                                                                                    .filter(dfe -> dfe.getId().equals(activitySearchValue.getText()) 
                                                                                            || dfe.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                            || dfe.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                    .collect(Collectors.toCollection(ArrayList::new));
                    if(!activitySearchValue.getText().isBlank()){
                        populateDriversForEngineersTable(searchedDFE);
                    } else {
                        populateDriversForEngineersTable(driversForEngineersList);
                    }
                    break;

                case 4:
                    searchedProgram = programList
                                                                .stream()
                                                                .filter(program -> program.getId().equals(activitySearchValue.getText()) 
                                                                        || program.getSourceOfFund().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                        || program.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                .collect(Collectors.toCollection(ArrayList::new));
                    if(!activitySearchValue.getText().isBlank()){
                        populateMainProgram(searchedProgram);
                    } else {
                        populateMainProgram(programList);
                    }
                    break;
            }
        }
    }//GEN-LAST:event_activitySearchValueKeyPressed

    private void workCategorySearchValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_workCategorySearchValueKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            int selectedTab = workCategoryTabbedPane.getSelectedIndex();

            switch(selectedTab){
                case 0:
                    searchedWorkCategory = workCategoryList
                                                                            .stream()
                                                                            .filter(workCategory -> String.valueOf(workCategory.getWorkCategoryNumber()).equals(workCategorySearchValue.getText())
                                                                                    || workCategory.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase()))
                                                                            .collect(Collectors.toCollection(ArrayList::new));
                    if(!workCategorySearchValue.getText().isBlank()){
                        populateWorkCategoryTable(searchedWorkCategory);
                    } else {
                        populateWorkCategoryTable(workCategoryList);
                    }
                    break;

                case 1:
                    searchedActivity = activityList
                                                                            .stream()
                                                                            .filter(activity -> activity.getItemNumber().equals(workCategorySearchValue.getText())
                                                                                    || activity.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                    || activity.getWorkCategory().getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase()))
                                                                            .collect(Collectors.toCollection(ArrayList::new));
                    if(!workCategorySearchValue.getText().isBlank()){
                        populateActivityTable(searchedActivity);
                    } else {
                        populateActivityTable(activityList);
                    }
                    break; 

                case 2:
                    searchedSubActivity = subActivityList
                                                                            .stream()
                                                                            .filter(subActivity -> subActivity.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                    || subActivity.getActivity().getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                    || subActivity.getActivity().getItemNumber().equals(workCategorySearchValue.getText()))
                                                                            .collect(Collectors.toCollection(ArrayList::new));
                    if(!workCategorySearchValue.getText().isBlank()){
                        populateSubActivityTable(searchedSubActivity);
                    } else {
                        populateSubActivityTable(subActivityList);
                    }
                    break;  
            }
        }
    }//GEN-LAST:event_workCategorySearchValueKeyPressed

    private void equipmentSearchValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_equipmentSearchValueKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            searchedEquipment = equipmentList
                                                            .stream()
                                                            .filter(equipment -> equipment.getEquipmentNumber().equals(equipmentSearchValue.getText()) 
                                                                    || equipment.getType().toLowerCase().contains(equipmentSearchValue.getText().toLowerCase()))
                                                            .collect(Collectors.toCollection(ArrayList::new));

            if(!equipmentSearchValue.getText().isBlank()){
                populateEquipmentTable(searchedEquipment);
            } else {
                populateEquipmentTable(equipmentList);
            } 
         }
    }//GEN-LAST:event_equipmentSearchValueKeyPressed

    private void searchPersonnelValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchPersonnelValueKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            searchedPersonnel = personnelList
                                                .stream()
                                                .filter(personnel -> personnel.getName()
                                                                              .toLowerCase()
                                                                              .contains(searchPersonnelValue.getText().toLowerCase()) 
                                                        || personnel.getId().equals(searchPersonnelValue.getText()) 
                                                        || personnel.getType()
                                                                    .toLowerCase()
                                                                    .contains(searchPersonnelValue.getText().toLowerCase()))
                                                .collect(Collectors.toCollection(ArrayList::new));

            if(!searchPersonnelValue.getText().isBlank()){
                populatePersonnelTable(searchedPersonnel);
            } else {
                populatePersonnelTable(personnelList);
            }  
        }
    }//GEN-LAST:event_searchPersonnelValueKeyPressed

    private void prevRAPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevRAPageActionPerformed
        if(currentPage > 1){
            currentPage--;
            if(activitySearchValue.getText().isBlank()){
                updateRegularActivity();
            } else {
                updateSearchedRegularActivity(activitySearchValue.getText());
            }
        }
    }//GEN-LAST:event_prevRAPageActionPerformed

    private void nextRAPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextRAPageActionPerformed
        currentPage++;
        if(activitySearchValue.getText().isBlank()){
                updateRegularActivity();
        } else {
            updateSearchedRegularActivity(activitySearchValue.getText());
        }
    }//GEN-LAST:event_nextRAPageActionPerformed

    // Table populators
    private void populateViewProjects(ArrayList<Project> projectCollection){
        tableViewProjects.setModel(new DefaultTableModel(null, new String[]{"Description", "Project Cost", "Implementation Mode"}));
        DefaultTableModel projectsTableModel = (DefaultTableModel) tableViewProjects.getModel();
        
        Iterator i = (Iterator) projectCollection.iterator();
        
        while(i.hasNext()){
            Project project = (Project) i.next();
            
            String[] projectData = {project.getDescription(), "₱ " + setDecimalFormat(project.getProjectCost()), project.getImplementationMode()};
            
            projectsTableModel.addRow(projectData);
        }
    }
    
    private void populateEditProjects(ArrayList<Project> projectCollection){
        tableEditProjects.setModel(new DefaultTableModel(null, new String[]{"Description", "Project Cost", "Implementation Mode"}));
        DefaultTableModel projectsTableModel = (DefaultTableModel) tableEditProjects.getModel();
        
        Iterator i = (Iterator) projectCollection.iterator();
        
        while(i.hasNext()){
            Project project = (Project) i.next();
            
            String[] projectData = {project.getDescription(), "₱ " + setDecimalFormat(project.getProjectCost()), project.getImplementationMode()};
            
            projectsTableModel.addRow(projectData);
        }
    }
    
    private void populateProjects(ArrayList<Project> projectCollection){
        tableProjects.setModel(new DefaultTableModel(null, new String[]{"Description", "Project Cost", "Implementation Mode"}));
        DefaultTableModel projectsTableModel = (DefaultTableModel) tableProjects.getModel();
        
        Iterator i = (Iterator) projectCollection.iterator();
        
        while(i.hasNext()){
            Project project = (Project) i.next();
            
            String[] projectData = {project.getDescription(), "₱ " + setDecimalFormat(project.getProjectCost()), project.getImplementationMode()};
            
            projectsTableModel.addRow(projectData);
        }
    }
    
    private void populateMainProgram(ArrayList<Program> programCollection){
        tableMainPrograms.setModel(new DefaultTableModel(null, new String[]{"ID", "Source of Fund", "Total Project Cost", "Date"}));
        DefaultTableModel programsTableModel = (DefaultTableModel) tableMainPrograms.getModel();
        
        if(programCollection.isEmpty()){
            String[] programData = {"No Data", "No Data", "No Data", "No Data"};
            programsTableModel.addRow(programData);
        } else {
        
            Iterator i = (Iterator) programCollection.iterator();

            while(i.hasNext()){
                Program program = (Program) i.next();

                String[] programData = {program.getId(), program.getSourceOfFund(), 
                    "₱ " + setDecimalFormat(program.getTotalProjectCost()), program.getDate()};

                programsTableModel.addRow(programData);
            }
        }
    }
    
    private void populateMainOtherExpenses(ArrayList<OtherExpenses> otherExpensesCollection) {
        tableMainOtherExpenses.setModel(new DefaultTableModel(null, new String[]{"ID",
            "Description",
            "Labor Crew Cost",
            "Labor Equipment Cost",
            "No. of CD",
            "Date",
            "Implementation Mode"}));
        DefaultTableModel otherExpensesTableModel = (DefaultTableModel) tableMainOtherExpenses.getModel();

        if(otherExpensesCollection.isEmpty()){
            String[] otherExpensesData = {"No Data", "No Data", "No Data", "No Data", "No Data", "No Data", "No Data"};
            otherExpensesTableModel.addRow(otherExpensesData);
        } else {
            Iterator i = (Iterator) otherExpensesCollection.iterator();

            while (i.hasNext()) {
                OtherExpenses otherExpenses = (OtherExpenses) i.next();

                String[] otherExpensesData = {otherExpenses.getId(), otherExpenses.DESCRIPTION, "₱ " + setDecimalFormat(otherExpenses.getLaborCrewCost()),
                    "₱ " + setDecimalFormat(otherExpenses.getLaborEquipmentCost()), String.valueOf(otherExpenses.getNumberOfCD()), otherExpenses.getDate(),
                    otherExpenses.getImplementationMode()};

                otherExpensesTableModel.addRow(otherExpensesData);
            }
        }
    }

    private void populateWorkCategoryTable(ArrayList<WorkCategory> workCategoryCollection) {
        tableWorkCategory.setModel(new DefaultTableModel(null, new String[]{"Category No.", "Description"}));
        DefaultTableModel workCategoryTableModel = (DefaultTableModel) tableWorkCategory.getModel();

        if (workCategoryCollection.size() == 0) {
            String[] data = {"No Data", "No Data"};
            workCategoryTableModel.addRow(data);
        } else {
            Iterator i = (Iterator) workCategoryCollection.iterator();

            while (i.hasNext()) {
                WorkCategory workCategory = (WorkCategory) i.next();
                String[] workCategoryData = {String.valueOf(workCategory.getWorkCategoryNumber()), workCategory.getDescription()};
                workCategoryTableModel.addRow(workCategoryData);
            }
        }
    }

    private void populateActivityTable(ArrayList<Activity> activityCollection) {
        tableActivity.setModel(new DefaultTableModel(null, new String[]{"Item No.", "Description", "Work Category"}));
        DefaultTableModel activityTableModel = (DefaultTableModel) tableActivity.getModel();

        if (activityCollection.size() == 0) {
            String[] data = {"No Data", "No Data", "No Data"};
            activityTableModel.addRow(data);
        } else {
            Iterator i = (Iterator) activityCollection.iterator();

            while (i.hasNext()) {
                Activity activity = (Activity) i.next();
                String[] activityData = {activity.getItemNumber(), activity.getDescription(), activity.getWorkCategory().getDescription()};
                activityTableModel.addRow(activityData);
            }
        }
    }

    private void populateSubActivityTable(ArrayList<SubActivity> subActivityCollection) {
        tableSubActivity.setModel(new DefaultTableModel(null, new String[]{"Description", "Activtity"}));
        DefaultTableModel subActivityTableModel = (DefaultTableModel) tableSubActivity.getModel();

        if (subActivityCollection.size() == 0) {
            String[] data = {"No Data", "No Data"};
            subActivityTableModel.addRow(data);
        } else {
            Iterator i = subActivityCollection.iterator();

            while (i.hasNext()) {
                SubActivity subActivity = (SubActivity) i.next();
                String[] subActivityData = {subActivity.getDescription(), subActivity.getActivity().getItemNumber() + " - " + subActivity.getActivity().getDescription()};
                subActivityTableModel.addRow(subActivityData);
            }
        }
    }

    private void populateEquipmentTable(ArrayList<Equipment> equipmentCollection) {
        tableEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.", "Type"}));
        DefaultTableModel equipmentTableModel = (DefaultTableModel) tableEquipment.getModel();

        if (equipmentCollection.size() == 0) {
            String[] data = {"No Data", "No Data"};
            equipmentTableModel.addRow(data);
        } else {
            Iterator i = (Iterator) equipmentCollection.iterator();

            while (i.hasNext()) {
                Equipment equipment = (Equipment) i.next();
                String[] equipmentData = {equipment.getEquipmentNumber(), equipment.getType()};
                equipmentTableModel.addRow(equipmentData);
            }
        }
    }

    private void populatePersonnelTable(ArrayList<Personnel> personnelCollection) {
        tablePersonnel.setModel(new DefaultTableModel(null, new String[]{"Personnel ID", "Name", "Type", "Rate Per Day"}));
        DefaultTableModel personnelTableModel = (DefaultTableModel) tablePersonnel.getModel();

        if (personnelCollection.size() == 0) {
            String[] data = {"No Data", "No Data", "No Data", "No Data"};
            personnelTableModel.addRow(data);
        } else {
            Iterator i = (Iterator) personnelCollection.iterator();

            while (i.hasNext()) {
                Personnel personnel = (Personnel) i.next();
                String[] personnelData = {personnel.getId(), personnel.getName(), personnel.getType(), "₱ " + setDecimalFormat(personnel.getRatePerDay())};
                personnelTableModel.addRow(personnelData);
            }
        }
    }

    private void populateRegularActivityOpsEquipment(OpsEquipmentList opsEquipmentCollection) {
        tableRegularActivityOperationEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.",
            "Type",
            "Operator Name",
            "Rate Per Day",
            "No. of CD",
            "Total Wages",
            "Fuel Consumption (L)",
            "Cost//L",
            "Amount",
            "Lubricant",
            "Total Cost"}));
        DefaultTableModel opsEquipmentTableModel = (DefaultTableModel) tableRegularActivityOperationEquipment.getModel();

        Iterator i = (Iterator) opsEquipmentCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            OpsEquipment opsEquipment = (OpsEquipment) i.next();

            String type = opsEquipment.getPersonnel()
                    .getType()
                    .equalsIgnoreCase("Operator") ? opsEquipment.getEquipment().getType() : opsEquipment.getPersonnel().getType();

            String[] opsEquipmentData = {opsEquipment.getEquipment().getEquipmentNumber(), type, opsEquipment.getPersonnel().getName(),
                "₱ " + setDecimalFormat(opsEquipment.getRatePerDay()), String.valueOf(opsEquipment.getNumberOfCd()),
                "₱ " + setDecimalFormat(opsEquipment.getTotalWages()), String.valueOf(opsEquipment.getFuelConsumption()),
                "₱ " + setDecimalFormat(opsEquipment.getFuelCost()), "₱ " + setDecimalFormat(opsEquipment.getFuelAmount()),
                "₱ " + setDecimalFormat(opsEquipment.getLubricantAmount()), "₱ " + setDecimalFormat(opsEquipment.getTotalCost())};

            totalExpenses += opsEquipment.getTotalCost();
            
            opsEquipmentTableModel.addRow(opsEquipmentData);
        }
        
        if(!opsEquipmentCollection.isEmpty()){
            String[] totalRow = {"", "", "","","","", "", "", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            opsEquipmentTableModel.addRow(totalRow);
        }
    }

    private void populateRegularActivityOpsMaintenanceCrew(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableRegularActivityMaintenanceCrew.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableRegularActivityMaintenanceCrew.getModel();

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }
        
        if(!opsMaintenanceCrewCollection.isEmpty()){
            String[] totalRow = {"", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            maintenanceCrewTableModel.addRow(totalRow);
        }
    }

    private void populateOtherActivityOpsMaintenanceCrew(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableOtherActivityMaintenanceCrew.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableOtherActivityMaintenanceCrew.getModel();

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }
        
        if(!opsMaintenanceCrewCollection.isEmpty()){
            String[] totalRow = {"", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            maintenanceCrewTableModel.addRow(totalRow);
        }
    }

    private void populateOtherActivityEditOpsMaintenanceCrew(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableOtherActivityEditPersonnel.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableOtherActivityEditPersonnel.getModel();

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }
        
        if(!opsMaintenanceCrewCollection.isEmpty()){
            String[] totalRow = {"", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            maintenanceCrewTableModel.addRow(totalRow);
        }
    }

    private void populateRegularActivityMaterials(CrewMaterialsList crewMaterialsCollection) {
        tableRegularActivityMaterials.setModel(new DefaultTableModel(null, new String[]{"Description", "Quantity", "Unit"}));
        DefaultTableModel materialsTableModel = (DefaultTableModel) tableRegularActivityMaterials.getModel();

        Iterator i = (Iterator) crewMaterialsCollection.toList().iterator();

        while (i.hasNext()) {
            CrewMaterials crewMaterials = (CrewMaterials) i.next();

            String[] crewMaterialsData = {crewMaterials.getDescription(), String.valueOf(crewMaterials.getQuantity()), crewMaterials.getUnit()};
            materialsTableModel.addRow(crewMaterialsData);
        }
    }

    private void populateRegularActivityCrewEquipment(CrewEquipmentList crewEquipmentCollection) {
        tableRegularActivityEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.",
            "Rate Per Day",
            "No. of CD",
            "Total Wages",
            "Fuel Consumption (L)",
            "Cost//L",
            "Amount",
            "Lubricant",
            "Total Cost"}));
        DefaultTableModel crewEquipmentTableModel = (DefaultTableModel) tableRegularActivityEquipment.getModel();

        Iterator i = (Iterator) crewEquipmentCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            CrewEquipment crewEquipment = (CrewEquipment) i.next();

            String[] crewEquipmentData = {crewEquipment.getEquipment().getEquipmentNumber(),
                "₱ " + setDecimalFormat(crewEquipment.getRatePerDay()), String.valueOf(crewEquipment.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewEquipment.getTotalWages()), String.valueOf(crewEquipment.getFuelConsumption()),
                "₱ " + setDecimalFormat(crewEquipment.getFuelCost()), "₱ " + setDecimalFormat(crewEquipment.getFuelAmount()),
                "₱ " + setDecimalFormat(crewEquipment.getLubricantAmount()), "₱ " + setDecimalFormat(crewEquipment.getTotalCost())};

            totalExpenses += crewEquipment.getTotalCost();
            
            crewEquipmentTableModel.addRow(crewEquipmentData);
        }
        
        
        if(!crewEquipmentCollection.isEmpty()){
            String[] totalRow = {"", "", "", "", "", "", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            crewEquipmentTableModel.addRow(totalRow);
        }
    }

    private void populateMainRegularActivity(ArrayList<RegularActivity> regularActivityCollection) {
        tableMainRegularActivity.setModel(new DefaultTableModel(null, new String[]{"ID",
            "Activity",
            "Road Section",
            "Location",
            "No. of CD",
            "Date",
            "Implementation Mode"}));
        DefaultTableModel regularActivityTableModel = (DefaultTableModel) tableMainRegularActivity.getModel();

        tableMainRegularActivity.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableMainRegularActivity.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableMainRegularActivity.getColumnModel().getColumn(2).setPreferredWidth(250);
        tableMainRegularActivity.getColumnModel().getColumn(3).setPreferredWidth(50);
        tableMainRegularActivity.getColumnModel().getColumn(4).setPreferredWidth(5);
        tableMainRegularActivity.getColumnModel().getColumn(5).setPreferredWidth(20);
        tableMainRegularActivity.getColumnModel().getColumn(5).setPreferredWidth(50);

        if(regularActivityCollection.isEmpty()){
            String[] regularActivityData = {"No Data", "No Data", "No Data", "No Data", "No Data", "No Data", "No Data"};
            regularActivityTableModel.addRow(regularActivityData);
        } else {
            Iterator i = (Iterator) regularActivityCollection.iterator();

            while (i.hasNext()) {
                RegularActivity regularActivity = (RegularActivity) i.next();

                String roadSection = regularActivity.isIsOtherRoadSection() ? regularActivity.getOtherRoadSection() : regularActivity.getRoadSection().getName();

                String[] regularActivityData = {regularActivity.getId(), regularActivity.getActivity().getItemNumber() + " - " + regularActivity.getActivity().getDescription(),
                    roadSection, regularActivity.getLocation().getLocation(),
                    String.valueOf(regularActivity.getNumberOfCD()),
                    regularActivity.getDate(), regularActivity.getImplementationMode()};

                regularActivityTableModel.addRow(regularActivityData);
            }
        }
    }

    private void populateMainOtherActivity(ArrayList<OtherActivity> otherActivityCollection) {
        tableMainOtherActivity.setModel(new DefaultTableModel(null, new String[]{"ID",
            "Sub Activity",
            "Description",
            "No. of CD",
            "Date",
            "Implementation Mode"}));
        DefaultTableModel otherActivityTableModel = (DefaultTableModel) tableMainOtherActivity.getModel();

        if(otherActivityCollection.isEmpty()){
            String[] otherActivityData = {"No Data", "No Data", "No Data", "No Data", "No Data", "No Data"};
            otherActivityTableModel.addRow(otherActivityData);
        } else {
            Iterator i = (Iterator) otherActivityCollection.iterator();

            while (i.hasNext()) {
                OtherActivity otherActivity = (OtherActivity) i.next();

                String[] otherActivityData = {otherActivity.getId(), otherActivity.getSubActivity().getDescription(), otherActivity.getDescription(),
                    String.valueOf(otherActivity.getNumberOfCD()), otherActivity.getDate(), otherActivity.getImplementationMode()};

                otherActivityTableModel.addRow(otherActivityData);
            }
        }
    }

    private void populateDriversForEngineersTable(ArrayList<DriversForEngineers> driversForEngineersCollection){
        tableMainDriversForEngineers.setModel(new DefaultTableModel(null, new String[]{"ID", "Date", "Labor Equipment", "Equipment Fuel", "Lubricant", "No. of CD", "Implementation Mode"}));
        DefaultTableModel driversForEngineersTableModel = (DefaultTableModel) tableMainDriversForEngineers.getModel();
        
        if(driversForEngineersCollection.isEmpty()){
            String[] driversForEngineersData = {"No Data", "No Data", "No Data", "No Data", "No Data", "No Data", "No Data"};
            driversForEngineersTableModel.addRow(driversForEngineersData);
        } else {       
            Iterator i = (Iterator) driversForEngineersCollection.iterator();

            while(i.hasNext()){
                DriversForEngineers driversForEngineers = (DriversForEngineers) i.next();

                String[] driversForEngineersData = {driversForEngineers.getId(), driversForEngineers.getDate(), "₱ " + setDecimalFormat(driversForEngineers.getLaborEquipmentCost()),
                                                    "₱ " + setDecimalFormat(driversForEngineers.getEquipmentFuelCost()), "₱ " + setDecimalFormat(driversForEngineers.getLubricantCost()),
                                                    String.valueOf(driversForEngineers.getNumberOfCD()), driversForEngineers.getImplementationMode()};
                driversForEngineersTableModel.addRow(driversForEngineersData);
            }
        }
    }
    
    //Table populators for activity view
    //"Edit" Tables
    private void populateRegularActivityEditOpsEquipment(OpsEquipmentList opsEquipmentCollection) {
        tableRegularActivityEditOperationEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.",
            "Type",
            "Operator Name",
            "Rate Per Day",
            "No. of CD",
            "Total Wages",
            "Fuel Consumption (L)",
            "Cost//L",
            "Amount",
            "Lubricant",
            "Total Cost"}));
        DefaultTableModel opsEquipmentTableModel = (DefaultTableModel) tableRegularActivityEditOperationEquipment.getModel();

        Iterator i = (Iterator) opsEquipmentCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            OpsEquipment opsEquipment = (OpsEquipment) i.next();

            String type = opsEquipment.getPersonnel()
                    .getType()
                    .equalsIgnoreCase("Operator") ? opsEquipment.getEquipment().getType() : opsEquipment.getPersonnel().getType();

            String[] opsEquipmentData = {opsEquipment.getEquipment().getEquipmentNumber(), type, opsEquipment.getPersonnel().getName(),
                "₱ " + setDecimalFormat(opsEquipment.getRatePerDay()), String.valueOf(opsEquipment.getNumberOfCd()),
                "₱ " + setDecimalFormat(opsEquipment.getTotalWages()), String.valueOf(opsEquipment.getFuelConsumption()),
                "₱ " + setDecimalFormat(opsEquipment.getFuelCost()), "₱ " + setDecimalFormat(opsEquipment.getFuelAmount()),
                "₱ " + setDecimalFormat(opsEquipment.getLubricantAmount()), "₱ " + setDecimalFormat(opsEquipment.getTotalCost())};

            totalExpenses += opsEquipment.getTotalCost();
            
            opsEquipmentTableModel.addRow(opsEquipmentData);
        }
        
        if(!opsEquipmentCollection.isEmpty()){
            String[] totalRow = {"", "", "","","","", "", "", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            opsEquipmentTableModel.addRow(totalRow);
        }
    }

    private void populateRegularActivityEditOpsMaintenanceCrew(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableRegularActivityEditMaintenanceCrew.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableRegularActivityEditMaintenanceCrew.getModel();

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }
        
        if(!opsMaintenanceCrewCollection.isEmpty()){
            String[] totalRow = {"", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            maintenanceCrewTableModel.addRow(totalRow);
        }
    }

    private void populateRegularActivityEditOpsCrewMaterials(CrewMaterialsList crewMaterialsCollection) {
        tableRegularActivityEditMaterials.setModel(new DefaultTableModel(null, new String[]{"Description", "Quantity", "Unit"}));
        DefaultTableModel materialsTableModel = (DefaultTableModel) tableRegularActivityEditMaterials.getModel();

        Iterator i = (Iterator) crewMaterialsCollection.toList().iterator();

        while (i.hasNext()) {
            CrewMaterials crewMaterials = (CrewMaterials) i.next();

            String[] crewMaterialsData = {crewMaterials.getDescription(), String.valueOf(crewMaterials.getQuantity()), crewMaterials.getUnit()};
            materialsTableModel.addRow(crewMaterialsData);
        }
    }

    private void populateRegularActivityEditOpsCrewEquipment(CrewEquipmentList crewEquipmentCollection) {
        tableRegularActivityEditEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.",
            "Rate Per Day",
            "No. of CD",
            "Total Wages",
            "Fuel Consumption (L)",
            "Cost//L",
            "Amount",
            "Lubricant",
            "Total Cost"}));
        DefaultTableModel crewEquipmentTableModel = (DefaultTableModel) tableRegularActivityEditEquipment.getModel();

        Iterator i = (Iterator) crewEquipmentCollection.toList().iterator();

        Double totalExpenses = 0.0;
        
        while (i.hasNext()) {
            CrewEquipment crewEquipment = (CrewEquipment) i.next();

            String[] crewEquipmentData = {crewEquipment.getEquipment().getEquipmentNumber(),
                "₱ " + setDecimalFormat(crewEquipment.getRatePerDay()), String.valueOf(crewEquipment.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewEquipment.getTotalWages()), String.valueOf(crewEquipment.getFuelConsumption()),
                "₱ " + setDecimalFormat(crewEquipment.getFuelCost()), "₱ " + setDecimalFormat(crewEquipment.getFuelAmount()),
                "₱ " + setDecimalFormat(crewEquipment.getLubricantAmount()), "₱ " + setDecimalFormat(crewEquipment.getTotalCost())};

            totalExpenses += crewEquipment.getTotalCost();
            
            crewEquipmentTableModel.addRow(crewEquipmentData);
        }
        
        if(!crewEquipmentCollection.isEmpty()){
            String[] totalRow = {"", "", "", "", "", "", "", "Total Expenses", "₱ " + setDecimalFormat(totalExpenses)};
            crewEquipmentTableModel.addRow(totalRow);
        }
    }

    //"View" Tables
    private void populateRegularActivityViewOpsEquipment(OpsEquipmentList opsEquipmentCollection) {
        tableRegularActivityViewOperationEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.",
            "Type",
            "Operator Name",
            "Rate Per Day",
            "No. of CD",
            "Total Wages",
            "Fuel Consumption (L)",
            "Cost//L",
            "Amount",
            "Lubricant",
            "Total Cost"}));
        DefaultTableModel opsEquipmentTableModel = (DefaultTableModel) tableRegularActivityViewOperationEquipment.getModel();

        Double totalExpenses = 0.00;

        Iterator i = (Iterator) opsEquipmentCollection.toList().iterator();

        while (i.hasNext()) {
            OpsEquipment opsEquipment = (OpsEquipment) i.next();

            String type = opsEquipment.getPersonnel()
                    .getType()
                    .equalsIgnoreCase("Operator") ? opsEquipment.getEquipment().getType() : opsEquipment.getPersonnel().getType();

            String[] opsEquipmentData = {opsEquipment.getEquipment().getEquipmentNumber(), type, opsEquipment.getPersonnel().getName(),
                "₱ " + setDecimalFormat(opsEquipment.getRatePerDay()), String.valueOf(opsEquipment.getNumberOfCd()),
                "₱ " + setDecimalFormat(opsEquipment.getTotalWages()), String.valueOf(opsEquipment.getFuelConsumption()),
                "₱ " + setDecimalFormat(opsEquipment.getFuelCost()), "₱ " + setDecimalFormat(opsEquipment.getFuelAmount()),
                "₱ " + setDecimalFormat(opsEquipment.getLubricantAmount()), "₱ " + setDecimalFormat(opsEquipment.getTotalCost())};

            totalExpenses += opsEquipment.getTotalCost();
            opsEquipmentTableModel.addRow(opsEquipmentData);
        }

        regularActivityViewOpsEquipmentTotalExpenses.setText("₱ " + setDecimalFormat(totalExpenses));
    }

    private void populateRegularActivityViewOpsMaintenanceCrew(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableRegularActivityViewMaintenanceCrew.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableRegularActivityViewMaintenanceCrew.getModel();

        Double totalExpenses = 0.00;

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }

        regularActivityViewMaintenanceCrewSubTotalExpenses.setText("₱ " + setDecimalFormat(totalExpenses));
    }

    private void populateOtherActivityViewOpsPersonnel(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableOtherActivityViewPersonnel.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableOtherActivityViewPersonnel.getModel();

        Double totalExpenses = 0.00;

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }

        otherActivityViewPersonnelTotalExpenses.setText("₱ " + setDecimalFormat(totalExpenses));
    }

    private void populateOtherActivityEditOpsPersonnel(CrewPersonnelList opsMaintenanceCrewCollection) {
        tableOtherActivityEditPersonnel.setModel(new DefaultTableModel(null, new String[]{"Name of Personnel",
            "No. of CD",
            "Rate Per Day",
            "Total Wages"}));
        DefaultTableModel maintenanceCrewTableModel = (DefaultTableModel) tableOtherActivityEditPersonnel.getModel();

        Double totalExpenses = 0.00;

        Iterator i = (Iterator) opsMaintenanceCrewCollection.toList().iterator();

        while (i.hasNext()) {
            CrewPersonnel crewPersonnel = (CrewPersonnel) i.next();

            String[] maintenanceCrewData = {crewPersonnel.getPersonnel().getName(), String.valueOf(crewPersonnel.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewPersonnel.getRatePerDay()),
                "₱ " + setDecimalFormat(crewPersonnel.getTotalWages())};

            totalExpenses += crewPersonnel.getTotalWages();
            maintenanceCrewTableModel.addRow(maintenanceCrewData);
        }

        otherActivityViewPersonnelTotalExpenses.setText("₱ " + setDecimalFormat(totalExpenses));
    }

    private void populateRegularActivityViewOpsCrewMaterials(CrewMaterialsList crewMaterialsCollection) {
        tableRegularActivityViewMaterials.setModel(new DefaultTableModel(null, new String[]{"Description", "Quantity", "Unit"}));
        DefaultTableModel materialsTableModel = (DefaultTableModel) tableRegularActivityViewMaterials.getModel();

        Iterator i = (Iterator) crewMaterialsCollection.toList().iterator();

        while (i.hasNext()) {
            CrewMaterials crewMaterials = (CrewMaterials) i.next();

            String[] crewMaterialsData = {crewMaterials.getDescription(), String.valueOf(crewMaterials.getQuantity()), crewMaterials.getUnit()};
            materialsTableModel.addRow(crewMaterialsData);
        }
    }

    private void populateRegularActivityViewOpsCrewEquipment(CrewEquipmentList crewEquipmentCollection) {
        tableRegularActivityViewEquipment.setModel(new DefaultTableModel(null, new String[]{"Equipment No.",
            "Rate Per Day",
            "No. of CD",
            "Total Wages",
            "Fuel Consumption (L)",
            "Cost//L",
            "Amount",
            "Lubricant",
            "Total Cost"}));
        DefaultTableModel crewEquipmentTableModel = (DefaultTableModel) tableRegularActivityViewEquipment.getModel();

        Double totalExpenses = 0.00;
        Iterator i = (Iterator) crewEquipmentCollection.toList().iterator();

        while (i.hasNext()) {
            CrewEquipment crewEquipment = (CrewEquipment) i.next();

            String[] crewEquipmentData = {crewEquipment.getEquipment().getEquipmentNumber(),
                "₱ " + setDecimalFormat(crewEquipment.getRatePerDay()), String.valueOf(crewEquipment.getNumberOfCd()),
                "₱ " + setDecimalFormat(crewEquipment.getTotalWages()), String.valueOf(crewEquipment.getFuelConsumption()),
                "₱ " + setDecimalFormat(crewEquipment.getFuelCost()), "₱ " + setDecimalFormat(crewEquipment.getFuelAmount()),
                "₱ " + setDecimalFormat(crewEquipment.getLubricantAmount()), "₱ " + setDecimalFormat(crewEquipment.getTotalCost())};

            totalExpenses += crewEquipment.getTotalCost();
            crewEquipmentTableModel.addRow(crewEquipmentData);
        }

        regularActivityViewEquipmentSubTotalExpenses.setText("₱ " + setDecimalFormat(totalExpenses));
    }

    // Custom Methods
    private void setRegularActivityDataEdit(int i) {
        regularActivityForEdit = searchedRegularActivity.get(i);

        String roadSection = regularActivityForEdit.isIsOtherRoadSection() ? regularActivityForEdit.getOtherRoadSection() : regularActivityForEdit.getRoadSection().getName();

        regularActivityEditActivity.setSelectedItem(regularActivityForEdit.getActivity().getItemNumber() + " - " + regularActivityForEdit.getActivity().getDescription());
        
        boolean isOther = false;
        
        isOther = regularActivityForEdit.getActivity().getItemNumber().equals("504");
        regularActivityEditSubActivitySelectionBox.removeAllItems();
        regularActivityEditSubActivitySelectionBox.setEnabled(isOther);
        initEditRegularActivitySubActivitySelectionBox(activityList.get(regularActivityEditActivity.getSelectedIndex() - 1).getItemNumber());

        regularActivityEditSubActivitySelectionBox.setSelectedItem(regularActivityForEdit.getSubActivity().getDescription());
        
        regularActivityEditLocation.setSelectedItem(regularActivityForEdit.getLocation().getLocation());

        if (regularActivityForEdit.isIsOtherRoadSection()) {
            regularActivityEditRoadSection.setSelectedItem("Other...");
            regularActivityEditOtherRoadSection.setText(roadSection);
            regularActivityEditOtherRoadSection.setEditable(true);
        } else {
            regularActivityEditRoadSection.setSelectedItem(roadSection);
            regularActivityEditOtherRoadSection.setEditable(false);
        }

        regularActivityEditMonth.setSelectedItem(regularActivityForEdit.getMonth());
        regularActivityEditYear.setSelectedItem(String.valueOf(regularActivityForEdit.getYear()));
        regularActivityEditImplementationMode.setText(regularActivityForEdit.getImplementationMode());
        regularActivityEditDaysOfOperation.setText(String.valueOf(regularActivityForEdit.getNumberOfCD()));

        //Tables 
        //Initialization of lists
        opsEquipmentList = new ActivityListDBController()
                .getRegularActivityOpsEquipmentList(
                        regularActivityForEdit.getOpsEquipmentListID());
        crewPersonnelList = new ActivityListDBController()
                .getRegularActivityOpsCrewPersonnelList(
                        regularActivityForEdit.getOpsMaintenanceCrewID());
        crewMaterialsList = new ActivityListDBController()
                .getRegularActivityOpsCrewMaterialsList(
                        regularActivityForEdit.getOpsMaintenanceCrewID());
        crewEquipmentList = new ActivityListDBController()
                .getRegularActivityOpsCrewEquipmentList(
                        regularActivityForEdit.getOpsMaintenanceCrewID());

        //To Load Operation Equipment Table
        if (!opsEquipmentList.isEmpty()) {
            isEditOperationEquipmentTableSelected.setSelected(true);
            populateRegularActivityEditOpsEquipment(opsEquipmentList);
            editOperationEquipmentEdit.setVisible(true);
            removeOperationEquipmentEdit.setVisible(true);
            addOperationEquipmentEdit.setVisible(true);
        } else {
            isEditOperationEquipmentTableSelected.setSelected(false);
            editOperationEquipmentEdit.setVisible(false);
            removeOperationEquipmentEdit.setVisible(false);
            addOperationEquipmentEdit.setVisible(false);
        }

        //To Load Operation Maintenance Crew Table
        if (!crewPersonnelList.isEmpty()) {
            isEditMaintenanceCrewTableSelected.setSelected(true);
            populateRegularActivityEditOpsMaintenanceCrew(crewPersonnelList);
            editMaintenanceCrewEdit.setVisible(true);
            removeMaintenanceCrewEdit.setVisible(true);
            addMaintenanceCrewEdit.setVisible(true);
        } else {
            isEditMaintenanceCrewTableSelected.setSelected(false);
            editMaintenanceCrewEdit.setVisible(false);
            removeMaintenanceCrewEdit.setVisible(false);
            addMaintenanceCrewEdit.setVisible(false);
        }

        //To Load Operation Crew Materials Table
        if (!crewMaterialsList.isEmpty()) {
            isEditMaterialsTableSelected.setSelected(true);
            populateRegularActivityEditOpsCrewMaterials(crewMaterialsList);
            editCrewMaterialsEdit.setVisible(true);
            removeCrewMaterialsEdit.setVisible(true);
            addCrewMaterialsEdit.setVisible(true);
        } else {
            isEditMaterialsTableSelected.setSelected(false);
            editCrewMaterialsEdit.setVisible(false);
            removeCrewMaterialsEdit.setVisible(false);
            addCrewMaterialsEdit.setVisible(false);
        }

        //To Load Operation Crew Equipment   
        if (!crewEquipmentList.isEmpty()) {
            isEditEquipmentTableSelected.setSelected(true);
            populateRegularActivityEditOpsCrewEquipment(crewEquipmentList);
            editCrewEquipmentEdit.setVisible(true);
            removeCrewEquipmentEdit.setVisible(true);
            addCrewEquipmentEdit.setVisible(true);
        } else {
            isEditEquipmentTableSelected.setSelected(false);
            editCrewEquipmentEdit.setVisible(false);
            removeCrewEquipmentEdit.setVisible(false);
            addCrewEquipmentEdit.setVisible(false);
        }
    }

    private void setRegularActivityDataView(int i) {
        regularActivityToView = searchedRegularActivity.get(i);

        String roadSection = regularActivityToView.isIsOtherRoadSection() ? regularActivityToView.getOtherRoadSection() : regularActivityToView.getRoadSection().getName();
        String subActivity = regularActivityToView.getSubActivity().getId() != 0 ? " ( " + regularActivityToView.getSubActivity().getDescription() + " ) " : "";
        
        regularActivityViewActivityName.setText(regularActivityToView.getActivity().getItemNumber() + " - " + regularActivityToView.getActivity().getDescription() + subActivity);
        regularActivityViewRoadSectionName.setText(roadSection);
        regularActivityViewLocationName.setText(regularActivityToView.getLocation().getLocation());
        regularActivityViewDaysOfOperation.setText(String.valueOf(regularActivityToView.getNumberOfCD()) + " CD");
        regularActivityViewDate.setText(regularActivityToView.getDate());
        regularActivityViewImplementationMode.setText(regularActivityToView.getImplementationMode());

        //To Load Operation Equipment Table
        populateRegularActivityViewOpsEquipment(
                new ActivityListDBController()
                        .getRegularActivityOpsEquipmentList(regularActivityToView.getOpsEquipmentListID()));

        //To Load Operation Maintenance Crew Table
        populateRegularActivityViewOpsMaintenanceCrew(
                new ActivityListDBController()
                        .getRegularActivityOpsCrewPersonnelList(regularActivityToView.getOpsMaintenanceCrewID()));

        //To Load Operation Crew Materials Table
        populateRegularActivityViewOpsCrewMaterials(
                new ActivityListDBController()
                        .getRegularActivityOpsCrewMaterialsList(regularActivityToView.getOpsMaintenanceCrewID()));

        //To Load Operation Crew Equipment      
        populateRegularActivityViewOpsCrewEquipment(
                new ActivityListDBController()
                        .getRegularActivityOpsCrewEquipmentList(regularActivityToView.getOpsMaintenanceCrewID()));

        //Calculation for total expenses
        Double opsEquipmentTotalExpenses = 0.00;
        Double opsMaintenanceCrewSubtotal = 0.00;
        Double opsCrewEquipmentSubtotal = 0.00;
        Double opsMaintenanceCrewTotalExpenses = 0.00;
        Double grandTotalExpenses = 0.00;

        opsEquipmentTotalExpenses = new ActivityListDBController()
                .getRegularActivityOpsEquipmentList(regularActivityToView.getOpsEquipmentListID()).getTotal();
        opsMaintenanceCrewSubtotal = new ActivityListDBController()
                .getRegularActivityOpsCrewPersonnelList(regularActivityToView.getOpsMaintenanceCrewID()).getTotal();
        opsCrewEquipmentSubtotal = new ActivityListDBController()
                .getRegularActivityOpsCrewEquipmentList(regularActivityToView.getOpsMaintenanceCrewID()).getTotal();

        opsMaintenanceCrewTotalExpenses = opsMaintenanceCrewSubtotal + opsCrewEquipmentSubtotal;
        grandTotalExpenses = opsEquipmentTotalExpenses + opsMaintenanceCrewTotalExpenses;

        regularActivityViewOpsMaintenanceCrewTotalExpenses.setText("₱ " + setDecimalFormat(opsMaintenanceCrewTotalExpenses));
        regularActivityGrandTotalExpenses.setText("₱ " + setDecimalFormat(grandTotalExpenses));
    }

    private void setProgramDataView(int i){
        Program program = searchedProgram.get(i);
        ProgramDBController programDBController = new ProgramDBController();
        Double grandTotal = programDBController.getTotalProjectCost(program.getId());
        
        projectsViewSourceOfFund.setText(program.getSourceOfFund());
        projectsViewDate.setText(program.getDate());
        projectsViewProjectTotal.setText("₱ " + setDecimalFormat(grandTotal));
        
        ArrayList<Project> projectCollection = programDBController.getProjectList(program.getId());
        
        populateViewProjects(projectCollection);
    }
    
    private void setProgramDataEdit(int i){
        programForEdit = searchedProgram.get(i);
        
        projectsFormEditSourceOfFund.setText(programForEdit.getSourceOfFund());
        projectsFormEditMonth.setSelectedItem(programForEdit.getMonth());
        projectsFormEditYear.setSelectedItem(String.valueOf(programForEdit.getYear()));
        
        projectList = new ProgramDBController().getProjectList(programForEdit.getId());
        populateEditProjects(projectList);
    }
    
    private void setOtherActivityDataEdit(int i) {
        otherActivityForEdit = searchedOtherActivity.get(i);

        otherActivityEditSubActivitySelectionBox.setSelectedItem(otherActivityForEdit.getSubActivity().getDescription());
        otherActivityEditDescription.setText(otherActivityForEdit.getDescription());
        otherActivityEditMonth.setSelectedItem(otherActivityForEdit.getMonth());
        otherActivityEditYear.setSelectedItem(String.valueOf(otherActivityForEdit.getYear()));
        otherActivityEditImplementationMode.setText(otherActivityForEdit.getImplementationMode());
        otherActivityEditDaysOfOperation.setText(String.valueOf(otherActivityForEdit.getNumberOfCD()));

        crewPersonnelList = new OtherActivityListDBController()
                .getOtherActivityOpsCrewPersonnelList(otherActivityForEdit.getId());
        populateOtherActivityEditOpsPersonnel(crewPersonnelList);
    }

    private void setOtherActivityDataView(int i) {
        otherActivityToView = searchedOtherActivity.get(i);

        otherActivityViewSubActivityName.setText(otherActivityToView.getSubActivity().getDescription());
        otherActivityViewDescription.setText(otherActivityToView.getDescription());
        otherActivityViewDaysOfOperation.setText(otherActivityToView.getNumberOfCD() + " CD");
        otherActivityViewDate.setText(otherActivityToView.getDate());
        otherActivityViewImplementationMode.setText(otherActivityToView.getImplementationMode());

        populateOtherActivityViewOpsPersonnel(
                new OtherActivityListDBController()
                        .getOtherActivityOpsCrewPersonnelList(otherActivityToView.getId()));

    }

    private void setOtherExpensesDataView(int i) {
        OtherExpenses otherExpenses = searchedOtherExpenses.get(i);

        otherExpensesViewDate.setText(otherExpenses.getDate());
        otherExpensesViewLaborCrewCost.setText("₱ " + setDecimalFormat(otherExpenses.getLaborCrewCost()));
        otherExpensesViewLaborEquipmentCost.setText("₱ " + setDecimalFormat(otherExpenses.getLaborEquipmentCost()));
        otherExpensesViewImplementationMode.setText(otherExpenses.getImplementationMode());
        otherExpensesViewDaysOfOperation.setText(otherExpenses.getNumberOfCD() + " CD");
        otherExpensesViewLightEquipments.setText("₱ " + setDecimalFormat(otherExpenses.getLightEquipments()));
        otherExpensesViewHeavyEquipments.setText("₱ " + setDecimalFormat(otherExpenses.getHeavyEquipments()));
    }

    private void setDriversForEngineersDataEdit(int i){
        driversForEngineersForEdit = searchedDFE.get(i);
        
        driversForEngineersFormEditMonth.setSelectedItem(driversForEngineersForEdit.getMonth());
        driversForEngineersFormEditYear.setSelectedItem(String.valueOf(driversForEngineersForEdit.getYear()));
        driversForEngineersFormEditLaborEquipmentCost.setText(String.valueOf(driversForEngineersForEdit.getLaborEquipmentCost()));
        driversForEngineersFormEditEquipmentFuelCost.setText(String.valueOf(driversForEngineersForEdit.getEquipmentFuelCost()));
        driversForEngineersFormEditLubricantCost.setText(String.valueOf(driversForEngineersForEdit.getLubricantCost()));
        driversForEngineersFormEditImplementationMode.setText(driversForEngineersForEdit.getImplementationMode());
        driversForEngineersFormEditDaysOfOperation.setText(String.valueOf(driversForEngineersForEdit.getNumberOfCD()));
    }
    
    private void setOtherExpensesDataEdit(int i){
        otherExpensesForEdit = searchedOtherExpenses.get(i);

        otherExpensesFormEditMonth.setSelectedItem(otherExpensesForEdit.getMonth());
        otherExpensesFormEditYear.setSelectedItem(String.valueOf(otherExpensesForEdit.getYear()));
        otherExpensesFormEditLaborCrewCost.setText(String.valueOf(otherExpensesForEdit.getLaborCrewCost()));
        otherExpensesFormEditLaborEquipmentCost.setText(String.valueOf(otherExpensesForEdit.getLaborEquipmentCost()));
        otherExpensesFormEditImplementationMode.setText(otherExpensesForEdit.getImplementationMode());
        otherExpensesFormEditDaysOfOperation.setText(String.valueOf(otherExpensesForEdit.getNumberOfCD()));
        otherExpensesFormEditLightEquipments.setText(String.valueOf(otherExpensesForEdit.getLightEquipments()));
        otherExpensesFormEditHeavyEquipments.setText(String.valueOf(otherExpensesForEdit.getHeavyEquipments()));
    }
    
    private String setDecimalFormat(Double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        return decimalFormat.format(amount);
    }
    
    private void unselectSectionBefore(int currentSection, int section) {
        if (currentSection != section) {
            switch (currentSection) {
                case 1:
                    navActivityList.setBackground(new Color(0, 102, 255));
                    break;
                case 2:
                    navWorkCategory.setBackground(new Color(0, 102, 255));
                    break;
                case 3:
                    navEquipment.setBackground(new Color(0, 102, 255));
                    break;
                case 4:
                    navPersonnel.setBackground(new Color(0, 102, 255));
                    break;
                case 5:
                    navReport.setBackground(new Color(0, 102, 255));
                    break;
                case 6:
                    navSettings.setBackground(new Color(0, 102, 255));
                    break;
            }
        }
    }

    public void initData() {
        if(Driver.getConnection() != null){
            fullPage = Math.ceil((double) new ActivityListDBController().getCount() / (double) PAGE_LIMIT);
            currentPage = 1;
            
            activityList = new ActivityDBController().getList();
            searchedActivity = activityList;
            subActivityList = new SubActivityDBController().getList();
            searchedSubActivity = subActivityList;
            workCategoryList = new WorkCategoryDBController().getList();
            searchedWorkCategory = workCategoryList;
            personnelList = new PersonnelDBController().getList();
            searchedPersonnel = personnelList;
            equipmentList = new EquipmentDBController().getList();
            searchedEquipment = equipmentList;
            regularActivityList = new ActivityListDBController().fetchData(currentPage, PAGE_LIMIT);
            searchedRegularActivity = regularActivityList;        
            otherActivityList = new OtherActivityListDBController().getList();
            searchedOtherActivity = otherActivityList;
            otherExpensesList = new OtherExpensesDBController().getList();
            searchedOtherExpenses = otherExpensesList;
            driversForEngineersList = new DriversForEngineersDBController().getList();
            searchedDFE = driversForEngineersList;
            programList = new ProgramDBController().getList();
            searchedProgram = programList;
        } else {
            activityList = new ArrayList();
            searchedActivity = new ArrayList();
            subActivityList = new ArrayList();
            searchedSubActivity = new ArrayList();
            workCategoryList = new ArrayList();
            searchedWorkCategory = new ArrayList();
            personnelList = new ArrayList();
            searchedPersonnel = new ArrayList();
            equipmentList = new ArrayList();
            searchedEquipment = new ArrayList();
            regularActivityList = new ArrayList();
            searchedRegularActivity = new ArrayList();
            otherActivityList = new ArrayList();
            searchedOtherActivity = new ArrayList();
            otherExpensesList = new ArrayList();
            searchedOtherExpenses = new ArrayList();
            driversForEngineersList = new ArrayList();
            searchedDFE = new ArrayList();
            programList = new ArrayList();
            searchedProgram = new ArrayList();
        }
        
        populateMainProgram(programList);
        populateWorkCategoryTable(workCategoryList);
        populateActivityTable(activityList);
        populateSubActivityTable(subActivityList);
        populateMainRegularActivity(regularActivityList);
        populateMainOtherActivity(otherActivityList);
        populateMainOtherExpenses(otherExpensesList);
        populateEquipmentTable(equipmentList);
        populatePersonnelTable(personnelList);
        populateDriversForEngineersTable(driversForEngineersList);
        
        initAddActivitySelectionBox();
        initAddLocationSelectionBox();
        initAddRoadSectionSelectionBox(locationList.get(0).getId());
        initEditActivitySelectionBox();
        initEditLocationSelectionBox();
        initEditRoadSectionSelectionBox(locationList.get(0).getId());
        initAddOtherActivitySubActivitySelectionBox();
        initEditOtherActivitySubActivitySelectionBox();

        pageLabel.setText(currentPage + " / " + (int) fullPage);
        prevRAPage.setEnabled(currentPage > 1);
        nextRAPage.setEnabled(currentPage != fullPage);
    }

    private void initAddActivitySelectionBox() {
        regularActivityFormActivity.removeAllItems();
        regularActivityFormActivity.addItem("Choose Activity...");
        activityList.forEach((e) -> {
            regularActivityFormActivity.addItem(e.getItemNumber() + " - " + e.getDescription());
        });
    }

    private void initEditActivitySelectionBox() {
        regularActivityEditActivity.removeAllItems();
        regularActivityEditActivity.addItem("Choose Activity...");
        activityList.forEach((e) -> {
            regularActivityEditActivity.addItem(e.getItemNumber() + " - " + e.getDescription());
        });
    }

    private void initAddRegularActivitySubActivitySelectionBox(String id) {
        regularActivitySubActivitySelectionBox.addItem("Choose sub activity...");
        subActivityTempList = new SubActivityDBController().getList(id);
        subActivityTempList.forEach((e) -> {
            regularActivitySubActivitySelectionBox.addItem(e.getDescription());
        });
    }

    private void initAddOtherActivitySubActivitySelectionBox() {
        otherActivityFormSubActivitySelectionBox.removeAllItems();
        otherActivityFormSubActivitySelectionBox.addItem("Choose sub activity...");
        subActivityOtherList = new SubActivityDBController().getList("504");
        subActivityOtherList.forEach((e) -> {
            otherActivityFormSubActivitySelectionBox.addItem(e.getDescription());
        });
    }

    private void initEditOtherActivitySubActivitySelectionBox() {
        otherActivityEditSubActivitySelectionBox.removeAllItems();
        otherActivityEditSubActivitySelectionBox.addItem("Choose sub activity...");
        subActivityOtherList = new SubActivityDBController().getList("504");
        subActivityOtherList.forEach((e) -> {
            otherActivityEditSubActivitySelectionBox.addItem(e.getDescription());
        });
    }

    private void initEditRegularActivitySubActivitySelectionBox(String id) {
        regularActivityEditSubActivitySelectionBox.addItem("Choose sub activity...");
        subActivityTempList = new SubActivityDBController().getList(id);
        subActivityTempList.forEach((e) -> {
            regularActivityEditSubActivitySelectionBox.addItem(e.getDescription());
        });
    }

    private void initAddLocationSelectionBox() {
        locationList = new LocationDBController().getList();

        locationList.forEach((e) -> {
            regularActivityFormLocation.addItem(e.getLocation());
        });
    }

    private void initEditLocationSelectionBox() {
        locationList = new LocationDBController().getList();

        locationList.forEach((e) -> {
            regularActivityEditLocation.addItem(e.getLocation());
        });
    }

    private void initAddRoadSectionSelectionBox(String location) {
        roadSectionList = new RoadSectionDBController().getList(location);

        roadSectionList.forEach((e) -> {
            regularActivityFormRoadSection.addItem(e.getName());
        });

        regularActivityFormRoadSection.addItem("Other...");
    }

    private void initEditRoadSectionSelectionBox(String location) {
        roadSectionList = new RoadSectionDBController().getList(location);

        roadSectionList.forEach((e) -> {
            regularActivityEditRoadSection.addItem(e.getName());
        });

        regularActivityEditRoadSection.addItem("Other...");
    }

    private ArrayList<Integer> getYears() {
        ArrayList<Integer> years = new ArrayList();

        for (int i = 1975; i <= 2080; i++) {
            years.add(i);
        }

        return years;
    }

    private void initTimeframeDetail(String timeRange) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] quarters = {"1st Quarter", "2nd Quarter", "3rd Quarter", "4th Quarter"};
        timeframeDetail.removeAllItems();
        switch (timeRange) {
            case "Monthly":
                Arrays.asList(months).forEach((m) -> {
                    timeframeDetail.addItem(m);
                });
                timeframeDetail.setSelectedIndex(new Date().getMonth());
                break;
            case "Quarterly":
                Arrays.asList(quarters).forEach((q) -> {
                    timeframeDetail.addItem(q);
                });

                int index = 0;
                int month = new Date().getMonth();

                if (month < 3) {
                    index = 0;
                } else if (month >= 3 && month < 6) {
                    index = 1;
                } else if (month >= 6 && month < 9) {
                    index = 2;
                } else {
                    index = 3;
                }

                timeframeDetail.setSelectedIndex(index);
                break;
            case "Annually":
                getYears().forEach((y) -> {
                    timeframeDetail.addItem(String.valueOf(y));
                });
                timeframeDetail.setSelectedIndex(new Date().getYear() - 75);
                break;
        }
    }

    public void initDate() {
        getYears().forEach((y) -> {
            regularActivityFormYear.addItem(String.valueOf(y));
        });

        regularActivityFormMonth.setSelectedIndex(new Date().getMonth());
        regularActivityFormYear.setSelectedIndex(new Date().getYear() - 75);

        getYears().forEach((y) -> {
            otherActivityFormYear.addItem(String.valueOf(y));
        });

        otherActivityFormMonth.setSelectedIndex(new Date().getMonth());
        otherActivityFormYear.setSelectedIndex(new Date().getYear() - 75);

        getYears().forEach((y) -> {
            otherExpensesFormYear.addItem(String.valueOf(y));
        });

        otherExpensesFormMonth.setSelectedIndex(new Date().getMonth());
        otherExpensesFormYear.setSelectedIndex(new Date().getYear() - 75);
        
        getYears().forEach((y) -> {
            driversForEngineersFormYear.addItem(String.valueOf(y));
        });

        driversForEngineersFormMonth.setSelectedIndex(new Date().getMonth());
        driversForEngineersFormYear.setSelectedIndex(new Date().getYear() - 75);
        
        getYears().forEach((y) -> {
            projectsFormYear.addItem(String.valueOf(y));
        });

        projectsFormMonth.setSelectedIndex(new Date().getMonth());
        projectsFormYear.setSelectedIndex(new Date().getYear() - 75);
        
        getYears().forEach((y) -> {
            monthlyYear.addItem(String.valueOf(y));
        });

        monthlyMonth.setSelectedIndex(new Date().getMonth());
        monthlyYear.setSelectedIndex(new Date().getYear() - 75);
        
        getYears().forEach((y) -> {
            quarterlyYear.addItem(String.valueOf(y));
        });
        
        quarterlyYear.setSelectedIndex(new Date().getYear() - 75);
        
        getYears().forEach((y) -> {
            regularActivityWorkbookYear.addItem(String.valueOf(y));
        });

        regularActivityWorkbookMonth.setSelectedIndex(new Date().getMonth());
        regularActivityWorkbookYear.setSelectedIndex(new Date().getYear() - 75);

        //Edit
        getYears().forEach((y) -> {
            regularActivityEditYear.addItem(String.valueOf(y));
        });

        getYears().forEach((y) -> {
            otherActivityEditYear.addItem(String.valueOf(y));
        });
        
        getYears().forEach((y) -> {
            otherExpensesFormEditYear.addItem(String.valueOf(y));
        });
        
        getYears().forEach((y) -> {
            driversForEngineersFormEditYear.addItem(String.valueOf(y));
        });
        
        getYears().forEach((y) -> {
            projectsFormEditYear.addItem(String.valueOf(y));
        });

        initTimeframeDetail("Monthly");
    }

    private void resetRegularActivityForm() {
        addNewRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
        regularActivityFormActivity.setSelectedIndex(0);
        regularActivityFormLocation.setSelectedIndex(0);
        initAddRoadSectionSelectionBox(locationList.get(0).getId());
        regularActivityFormOtherRoadSection.setText("");
        regularActivityFormOtherRoadSection.setEditable(false);
        initAddRegularActivitySubActivitySelectionBox("");
        regularActivitySubActivitySelectionBox.setEnabled(false);
        regularActivityFormMonth.setSelectedIndex(new Date().getMonth());
        regularActivityFormYear.setSelectedIndex(new Date().getYear() - 75);
        regularActivityFormImplementationMode.setText("");
        regularActivityFormDaysOfOperation.setText("");

        opsEquipmentList = new OpsEquipmentList();
        populateRegularActivityOpsEquipment(opsEquipmentList);

        crewPersonnelList = new CrewPersonnelList();
        populateRegularActivityOpsMaintenanceCrew(crewPersonnelList);

        crewMaterialsList = new CrewMaterialsList();
        populateRegularActivityMaterials(crewMaterialsList);

        crewEquipmentList = new CrewEquipmentList();
        populateRegularActivityCrewEquipment(crewEquipmentList);

        isOperationEquipmentTableSelected.setSelected(false);
        addOperationEquipment.setVisible(false);
        removeOperationEquipment.setVisible(false);
        editOperationEquipment.setVisible(false);

        isMaintenanceCrewTableSelected.setSelected(false);
        addMaintenanceCrew.setVisible(false);
        editMaintenanceCrew.setVisible(false);
        removeMaintenanceCrew.setVisible(false);

        isMaterialsTableSelected.setSelected(false);
        addCrewMaterials.setVisible(false);
        removeCrewMaterials.setVisible(false);
        editCrewMaterials.setVisible(false);

        isEquipmentTableSelected.setSelected(false);
        addCrewEquipment.setVisible(false);
        removeCrewEquipment.setVisible(false);
        editCrewEquipment.setVisible(false);

    }

    private void resetProjectsFormEdit(){
        editProjectsScrollPane.getVerticalScrollBar().setValue(0);
        projectsFormEditSourceOfFund.setText("");
        projectsFormEditMonth.setSelectedItem(new Date().getMonth());
        projectsFormEditYear.setSelectedItem(new Date().getYear() - 75);
        projectList = new ArrayList();
        populateEditProjects(projectList);
    }
    
    private void resetProjectsForm(){
        addNewProjectsScrollPane.getVerticalScrollBar().setValue(0);
        projectsFormSourceOfFund.setText("");
        projectsFormMonth.setSelectedItem(new Date().getMonth());
        projectsFormYear.setSelectedItem(new Date().getYear() - 75);
        projectList = new ArrayList();
        populateProjects(projectList);
    }
    
    private void resetDriversForEngineersForm(){
        addNewDFEScrollPane.getVerticalScrollBar().setValue(0);
        driversForEngineersFormMonth.setSelectedIndex(new Date().getMonth());
        driversForEngineersFormYear.setSelectedIndex(new Date().getYear() - 75);
        driversForEngineersFormLaborEquipmentCost.setText("");
        driversForEngineersFormEquipmentFuelCost.setText("");
        driversForEngineersFormLubricantCost.setText("");
        driversForEngineersFormDaysOfOperation.setText("");
    }
    
    private void resetDriversForEngineersEditForm(){
        editDFEScrollPane.getVerticalScrollBar().setValue(0);
        driversForEngineersFormEditMonth.setSelectedIndex(new Date().getMonth());
        driversForEngineersFormEditYear.setSelectedIndex(new Date().getYear() - 75);
        driversForEngineersFormEditLaborEquipmentCost.setText("");
        driversForEngineersFormEditEquipmentFuelCost.setText("");
        driversForEngineersFormEditLubricantCost.setText("");
        driversForEngineersFormEditDaysOfOperation.setText("");
    }
    
    private void resetOtherExpensesEditForm(){
        editOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
        otherExpensesFormEditMonth.setSelectedIndex(new Date().getMonth());
        otherExpensesFormEditYear.setSelectedIndex(new Date().getYear() - 75);
        otherExpensesFormEditLaborCrewCost.setText("");
        otherExpensesFormEditLaborEquipmentCost.setText("");
        otherExpensesFormEditImplementationMode.setText("");
        otherExpensesFormEditDaysOfOperation.setText("");
        otherExpensesFormEditLightEquipments.setText("");
        otherExpensesFormEditHeavyEquipments.setText("");
    }
    
    private void resetOtherExpensesForm() {
        addNewOtherExpensesScrollPane.getVerticalScrollBar().setValue(0);
        otherExpensesFormMonth.setSelectedIndex(new Date().getMonth());
        otherExpensesFormYear.setSelectedIndex(new Date().getYear() - 75);
        otherExpensesFormLaborCrewCost.setText("");
        otherExpensesFormLaborEquipmentCost.setText("");
        otherExpensesFormImplementationMode.setText("");
        otherExpensesFormDaysOfOperation.setText("");
        otherExpensesFormLightEquipments.setText("");
        otherExpensesFormHeavyEquipments.setText("");
    }

    private void resetOtherActivityForm() {
        addNewOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
        otherActivityFormDescription.setText("");
        initAddOtherActivitySubActivitySelectionBox();
        otherActivityFormMonth.setSelectedIndex(new Date().getMonth());
        otherActivityFormYear.setSelectedIndex(new Date().getYear() - 75);
        otherActivityFormImplementationMode.setText("");
        otherActivityFormDaysOfOperation.setText("");

        crewPersonnelList = new CrewPersonnelList();
        populateOtherActivityOpsMaintenanceCrew(crewPersonnelList);
    }

    private void resetOtherActivityEditForm() {
        editOtherActivityScrollPane.getVerticalScrollBar().setValue(0);
        otherActivityEditDescription.setText("");
        initEditOtherActivitySubActivitySelectionBox();
        otherActivityEditMonth.setSelectedIndex(new Date().getMonth());
        otherActivityEditYear.setSelectedIndex(new Date().getYear() - 75);
        otherActivityEditImplementationMode.setText("");
        otherActivityEditDaysOfOperation.setText("");

        crewPersonnelList = new CrewPersonnelList();
        populateOtherActivityEditOpsMaintenanceCrew(crewPersonnelList);
    }

    private void resetRegularActivityEditForm() {
        editRegularActivityScrollPane.getVerticalScrollBar().setValue(0);
        regularActivityEditActivity.setSelectedIndex(0);
        regularActivityEditLocation.setSelectedIndex(0);
        initEditRoadSectionSelectionBox(locationList.get(0).getId());
        regularActivityEditOtherRoadSection.setText("");
        regularActivityEditOtherRoadSection.setEditable(false);
        initEditRegularActivitySubActivitySelectionBox("");
        regularActivityEditSubActivitySelectionBox.setEnabled(false);
        //regularActivityEditMonth.setSelectedIndex(new Date().getMonth());
        //regularActivityEditYear.setSelectedIndex(new Date().getYear() - 75);
        regularActivityEditImplementationMode.setText("");
        regularActivityEditDaysOfOperation.setText("");

        opsEquipmentList = new OpsEquipmentList();
        populateRegularActivityEditOpsEquipment(opsEquipmentList);

        crewPersonnelList = new CrewPersonnelList();
        populateRegularActivityEditOpsMaintenanceCrew(crewPersonnelList);

        crewMaterialsList = new CrewMaterialsList();
        populateRegularActivityEditOpsCrewMaterials(crewMaterialsList);

        crewEquipmentList = new CrewEquipmentList();
        populateRegularActivityEditOpsCrewEquipment(crewEquipmentList);

        isEditOperationEquipmentTableSelected.setSelected(false);
        addOperationEquipmentEdit.setVisible(false);
        removeOperationEquipmentEdit.setVisible(false);
        editOperationEquipmentEdit.setVisible(false);

        isEditMaintenanceCrewTableSelected.setSelected(false);
        addMaintenanceCrewEdit.setVisible(false);
        editMaintenanceCrewEdit.setVisible(false);
        removeMaintenanceCrewEdit.setVisible(false);

        isEditMaterialsTableSelected.setSelected(false);
        addCrewMaterialsEdit.setVisible(false);
        removeCrewMaterialsEdit.setVisible(false);
        editCrewMaterialsEdit.setVisible(false);

        isEditEquipmentTableSelected.setSelected(false);
        addCrewEquipmentEdit.setVisible(false);
        removeCrewEquipmentEdit.setVisible(false);
        editCrewEquipmentEdit.setVisible(false);
    }

    public void initIcons() {
        java.net.URL imgURL = getClass().getResource("/mdqrs/assets/socot_seal_bgrmvd.png");
        ImageIcon icons = new ImageIcon(imgURL);
        setIconImage(icons.getImage());
        
        ImageManipulator imageManipulator = new ImageManipulator();
        imageManipulator.setIcon("/mdqrs/assets/socot_seal_bgrmvd.png", menuLogo);
        imageManipulator.setIcon("/mdqrs/assets/socot_seal_bgrmvd.png", welcomeLogo);
        imageManipulator.setIcon("/mdqrs/assets/icon_list.png", iconActivityList);
        imageManipulator.setIcon("/mdqrs/assets/icon_category.png", iconWorkCategory);
        imageManipulator.setIcon("/mdqrs/assets/icon_equipment.png", iconEquipment);
        imageManipulator.setIcon("/mdqrs/assets/icon_personnel.png", iconPersonnel);
        imageManipulator.setIcon("/mdqrs/assets/icon_report.png", iconReport);
        imageManipulator.setIcon("/mdqrs/assets/icon_settings.png", iconSettings);

        imageManipulator.setIcon("/mdqrs/assets/icon_search.png", searchActivity);
        imageManipulator.setIcon("/mdqrs/assets/icon_search.png", searchWorkCategory);
        imageManipulator.setIcon("/mdqrs/assets/icon_search.png", searchEquipment);
        imageManipulator.setIcon("/mdqrs/assets/icon_search.png", searchPersonnel);
        
        imageManipulator.setIcon("/mdqrs/assets/icon_personnel_settings.png", personnelSettingsIcon);
    }

    public void initNetworkSettings() throws URISyntaxException, IOException {
        File jarDir = JarDirectory.getJarDir(Main.class);
        File parentDir = jarDir.getParentFile();
        final String NETWORK_FILE = "src/mdqrs/path/to/config.properties";
        File file = new File(parentDir,NETWORK_FILE);

        if(file.exists()){
            try (FileInputStream input = new FileInputStream(file)) {
                Properties network = new Properties();
                network.load(input);

                Cryptographer cryptographer = new Cryptographer();

                String decryptedPassword = "";

                try {
                    decryptedPassword = cryptographer.decrypt(network.getProperty("password"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage());
                    e.printStackTrace();
                }

                networkUsername.setText(network.getProperty("username"));
                networkPassword.setText(decryptedPassword);
                networkServer.setText(network.getProperty("server"));
                networkPort.setText(network.getProperty("port"));
                networkDatabase.setText(network.getProperty("database"));
            } catch (IOException io) {
                io.printStackTrace();
            }

            networkUsername.enableInputMethods(false);
            networkPassword.enableInputMethods(false);
            networkServer.enableInputMethods(false);
            networkPort.enableInputMethods(false);
            networkDatabase.enableInputMethods(false);
        } else {
            try (OutputStream output = new FileOutputStream(file)) {
                Properties network = new Properties();

                network.setProperty("username", "admin");
                network.setProperty("password", "admin");
                network.setProperty("server", "localhost");
                network.setProperty("port", "3306");
                network.setProperty("database", "mdqrs");

                network.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            }

            try (FileInputStream input = new FileInputStream(file)) {
                Properties network = new Properties();
                network.load(input);

                Cryptographer cryptographer = new Cryptographer();

                String decryptedPassword = "";

                try {
                    decryptedPassword = cryptographer.decrypt(network.getProperty("password"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage());
                    e.printStackTrace();
                }

                networkUsername.setText(network.getProperty("username"));
                networkPassword.setText(decryptedPassword);
                networkServer.setText(network.getProperty("server"));
                networkPort.setText(network.getProperty("port"));
                networkDatabase.setText(network.getProperty("database"));
            } catch (IOException io) {
                io.printStackTrace();
            }
            
            networkUsername.enableInputMethods(false);
            networkPassword.enableInputMethods(false);
            networkServer.enableInputMethods(false);
            networkPort.enableInputMethods(false);
            networkDatabase.enableInputMethods(false);
        }    
    }
    
    public void initReportSettings() throws URISyntaxException, IOException {
        File jarDir = JarDirectory.getJarDir(Main.class);
        File parentDir = jarDir.getParentFile();
        
        final String REPORT_FILE_1 = "src/mdqrs/path/to/report_config.properties";
        final String REPORT_FILE_2 = "src/mdqrs/path/to/quarterly_report_config.properties";
        File file1 = new File(parentDir, REPORT_FILE_1);
        File file2 = new File(parentDir,REPORT_FILE_2);
        
        if(file1.exists()){
            try (FileInputStream input = new FileInputStream(file1)) {
                Properties report = new Properties();
                report.load(input);

                preparedBy1Name.setText(report.getProperty("prepared_by_1_name"));
                preparedBy1Position.setText(report.getProperty("prepared_by_1_position"));
                preparedBy2Name.setText(report.getProperty("prepared_by_2_name"));
                preparedBy2Position.setText(report.getProperty("prepared_by_2_position"));
                submittedByName.setText(report.getProperty("submitted_by_name"));
                submittedByPosition.setText(report.getProperty("submitted_by_position"));
                approvedByName.setText(report.getProperty("approved_by_name"));
                approvedByPosition.setText(report.getProperty("approved_by_position"));

            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        
        if(file2.exists()){
            try (FileInputStream input = new FileInputStream(file2)) {
                Properties report = new Properties();
                report.load(input);

                totalLengthOfProvincialRoads.setText(report.getProperty("total_provincial_roads"));
                totalLengthOfProvincialRoadsInFairToGood.setText(report.getProperty("total_provincial_roads_in_fair_to_good"));
                totalBudget.setText(report.getProperty("total_budget"));

            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
    
    public void initHeaderTitle() throws URISyntaxException, IOException{
        File jarDir = JarDirectory.getJarDir(Main.class);
        File parentDir = jarDir.getParentFile();
        
        final String REPORT_FILE = "src/mdqrs/path/to/report_header_config.properties";
        File file = new File(parentDir, REPORT_FILE);
        
        if(file.exists()){
            try (FileInputStream input = new FileInputStream(file)) {
                Properties report = new Properties();
                report.load(input);

                monthlyHeaderTitle.setText(report.getProperty("header_title"));

            } catch (IOException io) {
                io.printStackTrace();
            }
        } else {
            try (OutputStream output = new FileOutputStream(file)) {
                Properties headerTitle = new Properties();

                headerTitle.setProperty("header_title", "");
                headerTitle.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            }
            
            
            try (FileInputStream input = new FileInputStream(file)) {
                Properties report = new Properties();
                report.load(input);

                monthlyHeaderTitle.setText(report.getProperty("header_title"));

            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
    
    //Listeners
    @Override
    public void updateWorkCategory() {
        workCategoryList = new WorkCategoryDBController().getList();
        searchedWorkCategory = workCategoryList;
        populateWorkCategoryTable(workCategoryList);
    }

    @Override
    public void updateActivity() {
        activityList = new ActivityDBController().getList();
        searchedActivity = activityList;
        populateActivityTable(activityList);
        regularActivityFormActivity.removeAllItems();
        regularActivityEditActivity.removeAllItems();
        initAddActivitySelectionBox();
        initEditActivitySelectionBox();
    }

    @Override
    public void updateSubActivity() {
        subActivityList = new SubActivityDBController().getList();
        searchedSubActivity = subActivityList;
        populateSubActivityTable(subActivityList);
    }

    @Override
    public void updateEquipment() {
        equipmentList = new EquipmentDBController().getList();
        searchedEquipment = equipmentList;
        populateEquipmentTable(equipmentList);
    }

    @Override
    public void updatePersonnel() {
        personnelList = new PersonnelDBController().getList();
        searchedPersonnel = personnelList;
        populatePersonnelTable(personnelList);
    }

    @Override
    public void addProject(Project project, int formType){
        projectList.add(project);
        
        switch(formType){
            case 1:
                populateProjects(projectList);
                break;
            case 2:
                populateEditProjects(projectList);
                break;
        }       
    }
    
    @Override
    public void addRegularActivityOpsEquipment(OpsEquipment opsEquipment, int formType) {
        opsEquipmentList.addEquipment(opsEquipment);

        switch (formType) {
            case 1:
                populateRegularActivityOpsEquipment(opsEquipmentList);
                break;
            case 2:
                populateRegularActivityEditOpsEquipment(opsEquipmentList);
                break;
        }
    }

    @Override
    public void editProject(int i, Project project, int formType){
        projectList.set(i, project);
        
        switch(formType){
            case 1:
                populateProjects(projectList);
                break;
            case 2:
                populateEditProjects(projectList);
                break;
        }   
    }
    
    @Override
    public void editRegularActivityOpsEquipment(int i, OpsEquipment opsEquipment, int formType) {
        opsEquipmentList.editEquipment(i, opsEquipment);

        switch (formType) {
            case 1:
                populateRegularActivityOpsEquipment(opsEquipmentList);
                break;
            case 2:
                populateRegularActivityEditOpsEquipment(opsEquipmentList);
                break;
        }
    }

    @Override
    public void addRegularActivityOpsMaintenanceCrew(CrewPersonnel crewPersonnel, int formType) {
        crewPersonnelList.addCrew(crewPersonnel);

        switch (formType) {
            case 1:
                populateRegularActivityOpsMaintenanceCrew(crewPersonnelList);
                break;
            case 2:
                populateRegularActivityEditOpsMaintenanceCrew(crewPersonnelList);
                break;
            case 3:
                populateOtherActivityOpsMaintenanceCrew(crewPersonnelList);
                break;
            case 4:
                populateOtherActivityEditOpsMaintenanceCrew(crewPersonnelList);
                break;
        }
    }

    @Override
    public void editRegularActivityOpsMaintenanceCrew(int i, CrewPersonnel crewPersonnel, int formType) {
        crewPersonnelList.editCrewPersonnel(i, crewPersonnel);

        switch (formType) {
            case 1:
                populateRegularActivityOpsMaintenanceCrew(crewPersonnelList);
                break;
            case 2:
                populateRegularActivityEditOpsMaintenanceCrew(crewPersonnelList);
                break;
            case 3:
                populateOtherActivityOpsMaintenanceCrew(crewPersonnelList);
                break;
            case 4:
                populateOtherActivityEditOpsMaintenanceCrew(crewPersonnelList);
                break;
        }
    }

    @Override
    public void addRegularActivityOpsCrewMaterials(CrewMaterials crewMaterials, int formType) {
        crewMaterialsList.addMaterial(crewMaterials);

        switch (formType) {
            case 1:
                populateRegularActivityMaterials(crewMaterialsList);
                break;
            case 2:
                populateRegularActivityEditOpsCrewMaterials(crewMaterialsList);
                break;
        }
    }

    @Override
    public void editRegularActivityOpsCrewMaterials(int i, CrewMaterials crewMaterials, int formType) {
        crewMaterialsList.editMaterial(i, crewMaterials);

        switch (formType) {
            case 1:
                populateRegularActivityMaterials(crewMaterialsList);
                break;
            case 2:
                populateRegularActivityEditOpsCrewMaterials(crewMaterialsList);
                break;
        }
    }

    @Override
    public void addRegularActivityOpsCrewEquipment(CrewEquipment crewEquipment, int formType) {
        crewEquipmentList.addCrewEquipment(crewEquipment);

        switch (formType) {
            case 1:
                populateRegularActivityCrewEquipment(crewEquipmentList);
                break;
            case 2:
                populateRegularActivityEditOpsCrewEquipment(crewEquipmentList);
                break;
        }
    }

    @Override
    public void editRegularActivityOpsCrewEquipment(int i, CrewEquipment crewEquipment, int formType) {
        crewEquipmentList.editCrewEquipment(i, crewEquipment);

        switch (formType) {
            case 1:
                populateRegularActivityCrewEquipment(crewEquipmentList);
                break;
            case 2:
                populateRegularActivityEditOpsCrewEquipment(crewEquipmentList);
                break;
        }
    }

    private class CloseWindow extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            int n = JOptionPane.showConfirmDialog(rootPane, "Are you sure you wanted to close this application?");
            if (n == 0) {
                System.exit(0);
            }
        }
    }

    public class ActivityComparator implements Comparator<Activity> {
        @Override
        public int compare(Activity activity1, Activity activity2) {
            
            String description1 = activity1.getWorkCategory().getDescription();
            String description2 = activity2.getWorkCategory().getDescription();

            return description1.compareTo(description2);
        }
    }
    
    public class DateComparator implements Comparator<RegularActivity>{
        @Override
        public int compare(final RegularActivity d1, final RegularActivity d2){
            Date date1 = new Date(), date2 = new Date();
            
            try {
                date1 = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).parse(d1.getDate());
                date2 = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).parse(d2.getDate());
            } catch (Exception e){
                e.printStackTrace();
            }
            
            return date1.compareTo(date2);
         }
    }
    
    public class RegularActivityComparator implements Comparator<RegularActivity>{
        @Override
        public int compare(RegularActivity regularActivity1, RegularActivity regularActivity2){
            
            String description1 = regularActivity1.getActivity().getDescription();
            String description2 = regularActivity2.getActivity().getDescription();

            return description1.compareTo(description2);
        }
    }
    
    public class SubActivityComparator implements Comparator<SubActivity> {
        @Override
        public int compare(SubActivity subActivity1, SubActivity subActivity2) {
            
            String itemNumber1 = subActivity1.getActivity().getItemNumber();
            String itemNumber2 = subActivity2.getActivity().getItemNumber();

            return itemNumber1.compareTo(itemNumber2);
        }
    }
    
    public class RARoadSectionComparator implements Comparator<RegularActivity> {
        @Override
        public int compare(RegularActivity roadSection1, RegularActivity roadSection2) {
            
            String itemNumber1 = roadSection1.getRoadSection().getName();
            String itemNumber2 = roadSection2.getRoadSection().getName();

            return itemNumber1.compareTo(itemNumber2);
        }
    }
    
    public class RALocationComparator implements Comparator<RegularActivity>{
        @Override
        public int compare(RegularActivity location1, RegularActivity location2){
            
            String locationName1 = location1.getRoadSection().getName();
            String locationName2 = location2.getRoadSection().getName();
            
            return locationName1.compareToIgnoreCase(locationName2);
        }
    }
    
    public class OASubActivityComparator implements Comparator<OtherActivity>{
        @Override
        public int compare(OtherActivity otherActivity1, OtherActivity otherActivity2){
            
            String description1 = otherActivity1.getSubActivity().getDescription();
            String description2 = otherActivity2.getSubActivity().getDescription();
            
            return description1.compareToIgnoreCase(description2);
        }
    }
    
    public void initSearchFieldListener(){
//        personnelSearchFieldListener();
//        equipmentSearchFieldListener();
//        workCategorySearchFieldListener();
//        activitySearchFieldListener();
    }
  /*  
    private void activitySearchFieldListener(){
        activitySearchValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateActivity();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateActivity();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateActivity();
            }
        
            private void updateActivity(){
                int selectedTab = activityTabbedPane.getSelectedIndex();
        
                switch(selectedTab){
                    case 0:
                        searchedRegularActivity = regularActivityList
                                                                                            .stream()
                                                                                            .filter(regularActivity -> regularActivity.getId().equals(activitySearchValue.getText()) 
                                                                                                    || regularActivity.getActivity().getItemNumber().equals(activitySearchValue.getText())
                                                                                                    || regularActivity.getActivity().getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                    || regularActivity.getLocation().getLocation().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                    || regularActivity.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                    || regularActivity.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                            .collect(Collectors.toCollection(ArrayList::new));
                        if(!activitySearchValue.getText().isBlank()){
                            populateMainRegularActivity(searchedRegularActivity);
                        } else {
                            populateMainRegularActivity(regularActivityList);
                        }
                        break;

                    case 1:
                        searchedOtherActivity = otherActivityList
                                                                                        .stream()
                                                                                        .filter(otherActivity -> otherActivity.getId().equals(activitySearchValue.getText()) 
                                                                                                || otherActivity.getSubActivity().getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || otherActivity.getDescription().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || otherActivity.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || otherActivity.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                        .collect(Collectors.toCollection(ArrayList::new));
                        if(!activitySearchValue.getText().isBlank()){
                            populateMainOtherActivity(searchedOtherActivity);
                        } else {
                            populateMainOtherActivity(otherActivityList);
                        }
                        break;

                    case 2:
                        searchedOtherExpenses = otherExpensesList
                                                                                        .stream()
                                                                                        .filter(otherExpenses -> otherExpenses.getId().equals(activitySearchValue.getText()) 
                                                                                                || otherExpenses.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || otherExpenses.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                        .collect(Collectors.toCollection(ArrayList::new));
                        if(!activitySearchValue.getText().isBlank()){
                            populateMainOtherExpenses(searchedOtherExpenses);
                        } else {
                            populateMainOtherExpenses(otherExpensesList);
                        }
                        break;

                    case 3:
                        searchedDFE = driversForEngineersList
                                                                                        .stream()
                                                                                        .filter(dfe -> dfe.getId().equals(activitySearchValue.getText()) 
                                                                                                || dfe.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                                                || dfe.getImplementationMode().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                                        .collect(Collectors.toCollection(ArrayList::new));
                        if(!activitySearchValue.getText().isBlank()){
                            populateDriversForEngineersTable(searchedDFE);
                        } else {
                            populateDriversForEngineersTable(driversForEngineersList);
                        }
                        break;

                    case 4:
                        searchedProgram = programList
                                                                    .stream()
                                                                    .filter(program -> program.getId().equals(activitySearchValue.getText()) 
                                                                            || program.getSourceOfFund().toLowerCase().contains(activitySearchValue.getText().toLowerCase())
                                                                            || program.getMonth().toLowerCase().contains(activitySearchValue.getText().toLowerCase()))
                                                                    .collect(Collectors.toCollection(ArrayList::new));
                        if(!activitySearchValue.getText().isBlank()){
                            populateMainProgram(searchedProgram);
                        } else {
                            populateMainProgram(programList);
                        }
                        break;
                }
            }
        });
    }
    
    private void workCategorySearchFieldListener(){
        workCategorySearchValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWorkCategory();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWorkCategory();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateWorkCategory();
            }
            
            private void updateWorkCategory(){
                int selectedTab = workCategoryTabbedPane.getSelectedIndex();

                switch(selectedTab){
                    case 0:
                        searchedWorkCategory = workCategoryList
                                                                                .stream()
                                                                                .filter(workCategory -> String.valueOf(workCategory.getWorkCategoryNumber()).equals(workCategorySearchValue.getText())
                                                                                        || workCategory.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase()))
                                                                                .collect(Collectors.toCollection(ArrayList::new));
                        if(!workCategorySearchValue.getText().isBlank()){
                            populateWorkCategoryTable(searchedWorkCategory);
                        } else {
                            populateWorkCategoryTable(workCategoryList);
                        }
                        break;

                    case 1:
                        searchedActivity = activityList
                                                                                .stream()
                                                                                .filter(activity -> activity.getItemNumber().equals(workCategorySearchValue.getText())
                                                                                        || activity.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                        || activity.getWorkCategory().getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase()))
                                                                                .collect(Collectors.toCollection(ArrayList::new));
                        if(!workCategorySearchValue.getText().isBlank()){
                            populateActivityTable(searchedActivity);
                        } else {
                            populateActivityTable(activityList);
                        }
                        break; 

                    case 2:
                        searchedSubActivity = subActivityList
                                                                                .stream()
                                                                                .filter(subActivity -> subActivity.getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                        || subActivity.getActivity().getDescription().toLowerCase().contains(workCategorySearchValue.getText().toLowerCase())
                                                                                        || subActivity.getActivity().getItemNumber().equals(workCategorySearchValue.getText()))
                                                                                .collect(Collectors.toCollection(ArrayList::new));
                        if(!workCategorySearchValue.getText().isBlank()){
                            populateSubActivityTable(searchedSubActivity);
                        } else {
                            populateSubActivityTable(subActivityList);
                        }
                        break;  
                }
            }
        });
    }
    
    private void equipmentSearchFieldListener(){
        equipmentSearchValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateEquipment();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {             
                updateEquipment();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateEquipment();
            }
            
            private void updateEquipment(){
                searchedEquipment = equipmentList
                                                        .stream()
                                                        .filter(equipment -> equipment.getEquipmentNumber().equals(equipmentSearchValue.getText()) 
                                                                || equipment.getType().toLowerCase().contains(equipmentSearchValue.getText().toLowerCase()))
                                                        .collect(Collectors.toCollection(ArrayList::new));
        
                if(!equipmentSearchValue.getText().isBlank()){
                    populateEquipmentTable(searchedEquipment);
                } else {
                    populateEquipmentTable(equipmentList);
                } 
            }
        });
    }
    
    private void personnelSearchFieldListener(){
        searchPersonnelValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePersonnel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePersonnel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePersonnel();
            }
            
            private void updatePersonnel(){
                searchedPersonnel = personnelList
                                                    .stream()
                                                    .filter(personnel -> personnel.getName()
                                                                                  .toLowerCase()
                                                                                  .contains(searchPersonnelValue.getText().toLowerCase()) 
                                                            || personnel.getId().equals(searchPersonnelValue.getText()) 
                                                            || personnel.getType()
                                                                        .toLowerCase()
                                                                        .contains(searchPersonnelValue.getText().toLowerCase()))
                                                    .collect(Collectors.toCollection(ArrayList::new));
                
                if(!searchPersonnelValue.getText().isBlank()){
                    populatePersonnelTable(searchedPersonnel);
                } else {
                    populatePersonnelTable(personnelList);
                } 
            }
        });
    }
    */
    
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel activityListPanel;
    private javax.swing.JTextField activitySearchValue;
    private javax.swing.JPanel activityTab;
    private javax.swing.JTabbedPane activityTabbedPane;
    private javax.swing.JPanel addCrewEquipment;
    private javax.swing.JPanel addCrewEquipmentEdit;
    private javax.swing.JPanel addCrewMaterials;
    private javax.swing.JPanel addCrewMaterialsEdit;
    private javax.swing.JPanel addMaintenanceCrew;
    private javax.swing.JPanel addMaintenanceCrewEdit;
    private javax.swing.JPanel addNewActivity;
    private javax.swing.JScrollPane addNewDFEScrollPane;
    private javax.swing.JPanel addNewDriversForEngineers1;
    private javax.swing.JPanel addNewDriversForEngineersPanel;
    private javax.swing.JPanel addNewEquipment;
    private javax.swing.JPanel addNewOtherActivity;
    private javax.swing.JPanel addNewOtherActivityPanel;
    private javax.swing.JScrollPane addNewOtherActivityScrollPane;
    private javax.swing.JPanel addNewOtherExpenses;
    private javax.swing.JPanel addNewOtherExpensesPanel;
    private javax.swing.JScrollPane addNewOtherExpensesScrollPane;
    private javax.swing.JPanel addNewPersonnel;
    private javax.swing.JPanel addNewProjects;
    private javax.swing.JPanel addNewProjectsPanel;
    private javax.swing.JScrollPane addNewProjectsScrollPane;
    private javax.swing.JPanel addNewRegularActivity;
    private javax.swing.JPanel addNewRegularActivityPanel;
    private javax.swing.JScrollPane addNewRegularActivityScrollPane;
    private javax.swing.JPanel addNewSubActivity;
    private javax.swing.JPanel addNewWorkCategory;
    private javax.swing.JPanel addOperationEquipment;
    private javax.swing.JPanel addOperationEquipmentEdit;
    private javax.swing.JPanel addOtherMaintenanceCrew;
    private javax.swing.JPanel addOtherMaintenanceCrewEdit;
    private javax.swing.JPanel addProjectsItem;
    private javax.swing.JPanel addProjectsItemEdit;
    private javax.swing.JTextField approvedByName;
    private javax.swing.JTextField approvedByPosition;
    private javax.swing.JPanel backViewOtherExpenses;
    private javax.swing.JPanel backViewProgram;
    private javax.swing.JPanel backViewRegularActivity;
    private javax.swing.JPanel backViewRegularActivity1;
    private javax.swing.JPanel cancelEditOtherExpenses;
    private javax.swing.JPanel cancelEditRegularActivity;
    private javax.swing.JPanel cancelNewOtherActivity;
    private javax.swing.JPanel cancelNewOtherActivity1;
    private javax.swing.JPanel cancelNewOtherActivity2;
    private javax.swing.JPanel cancelNewOtherActivity3;
    private javax.swing.JPanel cancelNewOtherExpenses;
    private javax.swing.JPanel cancelNewOtherExpenses1;
    private javax.swing.JPanel cancelNewOtherExpenses2;
    private javax.swing.JPanel cancelNewRegularActivity;
    private javax.swing.JPanel deleteActivity;
    private javax.swing.JPanel deleteDriversForEngineers1;
    private javax.swing.JPanel deleteEquipment;
    private javax.swing.JPanel deleteOtherActivity;
    private javax.swing.JPanel deleteOtherExpenses;
    private javax.swing.JPanel deletePersonnel;
    private javax.swing.JPanel deleteProjects;
    private javax.swing.JPanel deleteRegularActivity;
    private javax.swing.JPanel deleteSubActivity;
    private javax.swing.JPanel deleteWorkCategory;
    private javax.swing.JPanel driversForEngineersContainer;
    private javax.swing.JTextField driversForEngineersFormDaysOfOperation;
    private javax.swing.JTextField driversForEngineersFormEditDaysOfOperation;
    private javax.swing.JTextField driversForEngineersFormEditEquipmentFuelCost;
    private javax.swing.JTextField driversForEngineersFormEditImplementationMode;
    private javax.swing.JTextField driversForEngineersFormEditLaborEquipmentCost;
    private javax.swing.JTextField driversForEngineersFormEditLubricantCost;
    private javax.swing.JComboBox<String> driversForEngineersFormEditMonth;
    private javax.swing.JComboBox<String> driversForEngineersFormEditYear;
    private javax.swing.JTextField driversForEngineersFormEquipmentFuelCost;
    private javax.swing.JTextField driversForEngineersFormImplementationMode;
    private javax.swing.JTextField driversForEngineersFormLaborEquipmentCost;
    private javax.swing.JTextField driversForEngineersFormLubricantCost;
    private javax.swing.JComboBox<String> driversForEngineersFormMonth;
    private javax.swing.JComboBox<String> driversForEngineersFormYear;
    private javax.swing.JPanel driversForEngineersTab;
    private javax.swing.JPanel editActivity;
    private javax.swing.JPanel editCrewEquipment;
    private javax.swing.JPanel editCrewEquipmentEdit;
    private javax.swing.JPanel editCrewMaterials;
    private javax.swing.JPanel editCrewMaterialsEdit;
    private javax.swing.JScrollPane editDFEScrollPane;
    private javax.swing.JPanel editDriversForEngineers1;
    private javax.swing.JPanel editDriversForEngineersPanel;
    private javax.swing.JPanel editEquipment;
    private javax.swing.JPanel editMaintenanceCrew;
    private javax.swing.JPanel editMaintenanceCrewEdit;
    private javax.swing.JPanel editOperationEquipment;
    private javax.swing.JPanel editOperationEquipmentEdit;
    private javax.swing.JPanel editOtherActivity;
    private javax.swing.JPanel editOtherActivityPanel;
    private javax.swing.JScrollPane editOtherActivityScrollPane;
    private javax.swing.JPanel editOtherExpenses;
    private javax.swing.JPanel editOtherExpensesPanel;
    private javax.swing.JScrollPane editOtherExpensesScrollPane;
    private javax.swing.JPanel editOtherMaintenanceCrew;
    private javax.swing.JPanel editOtherMaintenanceCrewEdit;
    private javax.swing.JPanel editPersonnel;
    private javax.swing.JPanel editProjects;
    private javax.swing.JPanel editProjectsItem;
    private javax.swing.JPanel editProjectsItemEdit;
    private javax.swing.JPanel editProjectsPanel;
    private javax.swing.JScrollPane editProjectsScrollPane;
    private javax.swing.JPanel editRegularActivity;
    private javax.swing.JPanel editRegularActivityPanel;
    private javax.swing.JScrollPane editRegularActivityScrollPane;
    private javax.swing.JPanel editSubActivity;
    private javax.swing.JPanel editWorkCategory;
    private javax.swing.JLabel equipmentFuelCost;
    private javax.swing.JPanel equipmentPanel;
    private javax.swing.JTextField equipmentSearchValue;
    private javax.swing.JPanel exportActivity;
    private javax.swing.JButton exportMonthlyReport;
    private javax.swing.JPanel exportOtherActivity;
    private javax.swing.JButton exportQuarterlyReport;
    private javax.swing.JButton exportWorkbook;
    private javax.swing.JLabel grandTotalCost;
    private javax.swing.JLabel iconActivityList;
    private javax.swing.JLabel iconEquipment;
    private javax.swing.JLabel iconPersonnel;
    private javax.swing.JLabel iconReport;
    private javax.swing.JLabel iconSettings;
    private javax.swing.JLabel iconWorkCategory;
    private javax.swing.JCheckBox isEditEquipmentTableSelected;
    private javax.swing.JCheckBox isEditMaintenanceCrewTableSelected;
    private javax.swing.JCheckBox isEditMaterialsTableSelected;
    private javax.swing.JCheckBox isEditOperationEquipmentTableSelected;
    private javax.swing.JCheckBox isEquipmentTableSelected;
    private javax.swing.JCheckBox isMaintenanceCrewTableSelected;
    private javax.swing.JCheckBox isMaterialsTableSelected;
    private javax.swing.JCheckBox isOperationEquipmentTableSelected;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel243;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel245;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel247;
    private javax.swing.JLabel jLabel248;
    private javax.swing.JLabel jLabel249;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel250;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel254;
    private javax.swing.JLabel jLabel255;
    private javax.swing.JLabel jLabel256;
    private javax.swing.JLabel jLabel257;
    private javax.swing.JLabel jLabel258;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel260;
    private javax.swing.JLabel jLabel261;
    private javax.swing.JLabel jLabel262;
    private javax.swing.JLabel jLabel263;
    private javax.swing.JLabel jLabel264;
    private javax.swing.JLabel jLabel265;
    private javax.swing.JLabel jLabel266;
    private javax.swing.JLabel jLabel267;
    private javax.swing.JLabel jLabel268;
    private javax.swing.JLabel jLabel269;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane42;
    private javax.swing.JScrollPane jScrollPane44;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel laborCrewCost;
    private javax.swing.JLabel laborEquipmentCost;
    private javax.swing.JLabel lubricantCost;
    private javax.swing.JPanel mainDriversForEngineersPanel;
    private javax.swing.JPanel mainOtherActivityPanel;
    private javax.swing.JPanel mainOtherExpensesPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainProjectsPanel;
    private javax.swing.JPanel mainRegularActivityPanel;
    private javax.swing.JPanel mainWorkCategoryPanel;
    private javax.swing.JPanel mainWorkCategoryPanel1;
    private javax.swing.JPanel mainWorkCategoryPanel2;
    private javax.swing.JPanel mainWorkCategoryPanel3;
    private javax.swing.JLabel menuLogo;
    private javax.swing.JTextField monthlyHeaderTitle;
    private javax.swing.JComboBox<String> monthlyMonth;
    private javax.swing.JComboBox<String> monthlyYear;
    private javax.swing.JPanel navActivityList;
    private javax.swing.JPanel navEquipment;
    private javax.swing.JLabel navLabelActivityList;
    private javax.swing.JLabel navLabelEquipment;
    private javax.swing.JLabel navLabelPersonnel;
    private javax.swing.JLabel navLabelReport;
    private javax.swing.JLabel navLabelSettings;
    private javax.swing.JLabel navLabelWorkCategory;
    private javax.swing.JPanel navPersonnel;
    private javax.swing.JPanel navReport;
    private javax.swing.JPanel navSettings;
    private javax.swing.JPanel navWorkCategory;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JTextField networkDatabase;
    private javax.swing.JPasswordField networkPassword;
    private javax.swing.JTextField networkPort;
    private javax.swing.JTextField networkServer;
    private javax.swing.JTextField networkUsername;
    private javax.swing.JButton nextRAPage;
    private javax.swing.JTextField otherActivityEditDaysOfOperation;
    private javax.swing.JTextField otherActivityEditDescription;
    private javax.swing.JTextField otherActivityEditImplementationMode;
    private javax.swing.JComboBox<String> otherActivityEditMonth;
    private javax.swing.JComboBox<String> otherActivityEditSubActivitySelectionBox;
    private javax.swing.JComboBox<String> otherActivityEditYear;
    private javax.swing.JTextField otherActivityFormDaysOfOperation;
    private javax.swing.JTextField otherActivityFormDescription;
    private javax.swing.JTextField otherActivityFormImplementationMode;
    private javax.swing.JComboBox<String> otherActivityFormMonth;
    private javax.swing.JComboBox<String> otherActivityFormSubActivitySelectionBox;
    private javax.swing.JComboBox<String> otherActivityFormYear;
    private javax.swing.JPanel otherActivityTab;
    private javax.swing.JLabel otherActivityViewDate;
    private javax.swing.JLabel otherActivityViewDaysOfOperation;
    private javax.swing.JLabel otherActivityViewDescription;
    private javax.swing.JLabel otherActivityViewDescription1;
    private javax.swing.JLabel otherActivityViewImplementationMode;
    private javax.swing.JLabel otherActivityViewPersonnelTotalExpenses;
    private javax.swing.JLabel otherActivityViewSubActivityName;
    private javax.swing.JTextField otherExpensesFormDaysOfOperation;
    private javax.swing.JLabel otherExpensesFormDescription;
    private javax.swing.JLabel otherExpensesFormDescription1;
    private javax.swing.JTextField otherExpensesFormEditDaysOfOperation;
    private javax.swing.JTextField otherExpensesFormEditHeavyEquipments;
    private javax.swing.JTextField otherExpensesFormEditImplementationMode;
    private javax.swing.JTextField otherExpensesFormEditLaborCrewCost;
    private javax.swing.JTextField otherExpensesFormEditLaborEquipmentCost;
    private javax.swing.JTextField otherExpensesFormEditLightEquipments;
    private javax.swing.JComboBox<String> otherExpensesFormEditMonth;
    private javax.swing.JComboBox<String> otherExpensesFormEditYear;
    private javax.swing.JTextField otherExpensesFormHeavyEquipments;
    private javax.swing.JTextField otherExpensesFormImplementationMode;
    private javax.swing.JTextField otherExpensesFormLaborCrewCost;
    private javax.swing.JTextField otherExpensesFormLaborEquipmentCost;
    private javax.swing.JTextField otherExpensesFormLightEquipments;
    private javax.swing.JComboBox<String> otherExpensesFormMonth;
    private javax.swing.JComboBox<String> otherExpensesFormYear;
    private javax.swing.JPanel otherExpensesTab;
    private javax.swing.JLabel otherExpensesViewDate;
    private javax.swing.JLabel otherExpensesViewDaysOfOperation;
    private javax.swing.JLabel otherExpensesViewHeavyEquipments;
    private javax.swing.JLabel otherExpensesViewImplementationMode;
    private javax.swing.JLabel otherExpensesViewLaborCrewCost;
    private javax.swing.JLabel otherExpensesViewLaborEquipmentCost;
    private javax.swing.JLabel otherExpensesViewLightEquipments;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JPanel personnelPanel;
    private javax.swing.JLabel personnelSettingsIcon;
    private javax.swing.JTextField preparedBy1Name;
    private javax.swing.JTextField preparedBy1Position;
    private javax.swing.JTextField preparedBy2Name;
    private javax.swing.JTextField preparedBy2Position;
    private javax.swing.JButton prevRAPage;
    private javax.swing.JComboBox<String> projectsFormEditMonth;
    private javax.swing.JTextField projectsFormEditSourceOfFund;
    private javax.swing.JComboBox<String> projectsFormEditYear;
    private javax.swing.JComboBox<String> projectsFormMonth;
    private javax.swing.JTextField projectsFormSourceOfFund;
    private javax.swing.JComboBox<String> projectsFormYear;
    private javax.swing.JPanel projectsOfWorksTab;
    private javax.swing.JLabel projectsViewDate;
    private javax.swing.JLabel projectsViewProjectTotal;
    private javax.swing.JLabel projectsViewSourceOfFund;
    private javax.swing.JComboBox<String> quarterlyYear;
    private javax.swing.JComboBox<String> regularActivityEditActivity;
    private javax.swing.JTextField regularActivityEditDaysOfOperation;
    private javax.swing.JTextField regularActivityEditImplementationMode;
    private javax.swing.JComboBox<String> regularActivityEditLocation;
    private javax.swing.JComboBox<String> regularActivityEditMonth;
    private javax.swing.JTextField regularActivityEditOtherRoadSection;
    private javax.swing.JComboBox<String> regularActivityEditRoadSection;
    private javax.swing.JComboBox<String> regularActivityEditSubActivitySelectionBox;
    private javax.swing.JComboBox<String> regularActivityEditYear;
    private javax.swing.JComboBox<String> regularActivityFormActivity;
    private javax.swing.JTextField regularActivityFormDaysOfOperation;
    private javax.swing.JTextField regularActivityFormImplementationMode;
    private javax.swing.JComboBox<String> regularActivityFormLocation;
    private javax.swing.JComboBox<String> regularActivityFormMonth;
    private javax.swing.JTextField regularActivityFormOtherRoadSection;
    private javax.swing.JComboBox<String> regularActivityFormRoadSection;
    private javax.swing.JComboBox<String> regularActivityFormYear;
    private javax.swing.JLabel regularActivityGrandTotalExpenses;
    private javax.swing.JComboBox<String> regularActivitySubActivitySelectionBox;
    private javax.swing.JLabel regularActivitySubActivityTitle;
    private javax.swing.JLabel regularActivitySubActivityTitle1;
    private javax.swing.JLabel regularActivitySubActivityTitle2;
    private javax.swing.JLabel regularActivitySubActivityTitle3;
    private javax.swing.JPanel regularActivityTab;
    private javax.swing.JLabel regularActivityViewActivityName;
    private javax.swing.JLabel regularActivityViewDate;
    private javax.swing.JLabel regularActivityViewDaysOfOperation;
    private javax.swing.JLabel regularActivityViewEquipmentSubTotalExpenses;
    private javax.swing.JLabel regularActivityViewImplementationMode;
    private javax.swing.JLabel regularActivityViewLocationName;
    private javax.swing.JLabel regularActivityViewMaintenanceCrewSubTotalExpenses;
    private javax.swing.JLabel regularActivityViewOpsEquipmentTotalExpenses;
    private javax.swing.JLabel regularActivityViewOpsMaintenanceCrewTotalExpenses;
    private javax.swing.JLabel regularActivityViewRoadSectionName;
    private javax.swing.JComboBox<String> regularActivityWorkbookMonth;
    private javax.swing.JComboBox<String> regularActivityWorkbookYear;
    private javax.swing.JPanel removeCrewEquipment;
    private javax.swing.JPanel removeCrewEquipmentEdit;
    private javax.swing.JPanel removeCrewMaterials;
    private javax.swing.JPanel removeCrewMaterialsEdit;
    private javax.swing.JPanel removeMaintenanceCrew;
    private javax.swing.JPanel removeMaintenanceCrewEdit;
    private javax.swing.JPanel removeOperationEquipment;
    private javax.swing.JPanel removeOperationEquipmentEdit;
    private javax.swing.JPanel removeOtherMaintenanceCrew;
    private javax.swing.JPanel removeOtherMaintenanceCrewEdit;
    private javax.swing.JPanel removeProjectsItem;
    private javax.swing.JPanel removeProjectsItemEdit;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JPanel saveDFE;
    private javax.swing.JPanel saveEditOtherExpenses;
    private javax.swing.JPanel saveEditRegularActivity;
    private javax.swing.JPanel saveEditedOtherActivity;
    private javax.swing.JButton saveNetwork;
    private javax.swing.JPanel saveNewOtherActivity;
    private javax.swing.JPanel saveNewOtherActivity1;
    private javax.swing.JPanel saveNewOtherActivity2;
    private javax.swing.JPanel saveNewOtherExpenses;
    private javax.swing.JPanel saveNewOtherExpenses1;
    private javax.swing.JPanel saveNewRegularActivity;
    private javax.swing.JButton saveQuarterlyReportDetails;
    private javax.swing.JButton saveReport;
    private javax.swing.JLabel searchActivity;
    private javax.swing.JLabel searchEquipment;
    private javax.swing.JLabel searchPersonnel;
    private javax.swing.JTextField searchPersonnelValue;
    private javax.swing.JLabel searchWorkCategory;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JScrollPane settingsScrollPane;
    private javax.swing.JComboBox<String> sortActivity;
    private javax.swing.JComboBox<String> sortEquipment;
    private javax.swing.JComboBox<String> sortPersonnel;
    private javax.swing.JComboBox<String> sortWorkCategory;
    private javax.swing.JPanel subActivityTab;
    private javax.swing.JTextField submittedByName;
    private javax.swing.JTextField submittedByPosition;
    private javax.swing.JButton systemRefresh;
    private javax.swing.JTable tableActivity;
    private javax.swing.JTable tableEditProjects;
    private javax.swing.JTable tableEquipment;
    private javax.swing.JTable tableMainDriversForEngineers;
    private javax.swing.JTable tableMainOtherActivity;
    private javax.swing.JTable tableMainOtherExpenses;
    private javax.swing.JTable tableMainPrograms;
    private javax.swing.JTable tableMainRegularActivity;
    private javax.swing.JTable tableOtherActivityEditPersonnel;
    private javax.swing.JTable tableOtherActivityMaintenanceCrew;
    private javax.swing.JTable tableOtherActivityViewPersonnel;
    private javax.swing.JTable tablePersonnel;
    private javax.swing.JTable tableProjects;
    private javax.swing.JTable tableRegularActivityEditEquipment;
    private javax.swing.JTable tableRegularActivityEditMaintenanceCrew;
    private javax.swing.JTable tableRegularActivityEditMaterials;
    private javax.swing.JTable tableRegularActivityEditOperationEquipment;
    private javax.swing.JTable tableRegularActivityEquipment;
    private javax.swing.JTable tableRegularActivityMaintenanceCrew;
    private javax.swing.JTable tableRegularActivityMaterials;
    private javax.swing.JTable tableRegularActivityOperationEquipment;
    private javax.swing.JTable tableRegularActivityViewEquipment;
    private javax.swing.JTable tableRegularActivityViewMaintenanceCrew;
    private javax.swing.JTable tableRegularActivityViewMaterials;
    private javax.swing.JTable tableRegularActivityViewOperationEquipment;
    private javax.swing.JTable tableSubActivity;
    private javax.swing.JTable tableViewProjects;
    private javax.swing.JTable tableWorkCategory;
    private javax.swing.JButton testNetworkConnection;
    private javax.swing.JComboBox<String> timeRange;
    private javax.swing.JComboBox<String> timeframeDetail;
    private javax.swing.JTextField totalBudget;
    private javax.swing.JPanel totalCostBreakdownPanel;
    private javax.swing.JTextField totalLengthOfProvincialRoads;
    private javax.swing.JTextField totalLengthOfProvincialRoadsInFairToGood;
    private javax.swing.JPanel viewOtherActivity;
    private javax.swing.JPanel viewOtherActivityPanel;
    private javax.swing.JScrollPane viewOtherActivityScrollPane;
    private javax.swing.JPanel viewOtherExpenses;
    private javax.swing.JPanel viewOtherExpensesPanel;
    private javax.swing.JScrollPane viewOtherExpensesScrollPane;
    private javax.swing.JPanel viewProjects;
    private javax.swing.JPanel viewProjectsPanel;
    private javax.swing.JScrollPane viewProjectsScrollPane;
    private javax.swing.JPanel viewRegularActivity;
    private javax.swing.JPanel viewRegularActivityPanel;
    private javax.swing.JScrollPane viewRegularActivityScrollPane;
    private javax.swing.JLabel welcomeLogo;
    private javax.swing.JPanel welcomePanel;
    private javax.swing.JPanel workCategoryPanel;
    private javax.swing.JTextField workCategorySearchValue;
    private javax.swing.JPanel workCategoryTab;
    private javax.swing.JPanel workCategoryTab1;
    private javax.swing.JTabbedPane workCategoryTabbedPane;
    // End of variables declaration//GEN-END:variables
}
