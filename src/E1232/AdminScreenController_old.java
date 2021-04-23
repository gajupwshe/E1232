/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import static E1232.ReportScreenController.catStage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Narpat Singh
 */
public class AdminScreenController_old implements Initializable {

    Thread insert_Admin, m_mode;
    String current_machine_mode, current_offline_mode,prev_unit;

//    Columns for user table start
    JFXTreeTableColumn<Users, String> UserName = new JFXTreeTableColumn<>("Username");
    JFXTreeTableColumn<Users, String> Name = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<Users, String> Qualification = new JFXTreeTableColumn<>("Qualification");
    JFXTreeTableColumn<Users, String> UserType = new JFXTreeTableColumn<>("User Type");
//    Columns for user table end

//    Columns for Gauge table start
    JFXTreeTableColumn<Users, String> GS = new JFXTreeTableColumn<>("Serial No");
    JFXTreeTableColumn<Users, String> GD = new JFXTreeTableColumn<>("Gauge Description");
    JFXTreeTableColumn<Users, String> GR = new JFXTreeTableColumn<>("Gauge Range");
//    JFXTreeTableColumn<Users, String> GU = new JFXTreeTableColumn<>("Gauge Unit");
    JFXTreeTableColumn<Users, String> CD = new JFXTreeTableColumn<>("Calibration Date");
    JFXTreeTableColumn<Users, String> CDD = new JFXTreeTableColumn<>("Calibration Due Date");
//    Columns for Gauge table end

//    Columns for Customer table start
    //JFXTreeTableColumn<Users, String> customerCode = new JFXTreeTableColumn<>("Code");
    JFXTreeTableColumn<Users, String> Sl_no = new JFXTreeTableColumn<>("Sl.No");
    //JFXTreeTableColumn<Users, String> customerName = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<Users, String> valveclass = new JFXTreeTableColumn<>("Valve Class");
    JFXTreeTableColumn<Users, String> valve_standards = new JFXTreeTableColumn<>("Description");

    //    Columns for Customer table start
    //JFXTreeTableColumn<Users, String> customerCode = new JFXTreeTableColumn<>("Code");
    JFXTreeTableColumn<Users, String> Sl_no1 = new JFXTreeTableColumn<>("Sl.No");
    //JFXTreeTableColumn<Users, String> customerName = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<Users, String> valve_standards1 = new JFXTreeTableColumn<>("Valve Standard");
//    Columns for Customer table end

//    Columns for Manufacturer table start
    JFXTreeTableColumn<ManufacturerData, String> manufacturerCode = new JFXTreeTableColumn<>("Sl.no");
    JFXTreeTableColumn<ManufacturerData, String> manufacturerName = new JFXTreeTableColumn<>("Valve Size");

    //    Columns for Manufacturer table start
    JFXTreeTableColumn<ManufacturerData, String> manufacturerCode1 = new JFXTreeTableColumn<>("Sl.no");
    JFXTreeTableColumn<ManufacturerData, String> manufacturerName1 = new JFXTreeTableColumn<>("Valve Material");
//    JFXTreeTableColumn<ManufacturerData, String> manufacturerDescription = new JFXTreeTableColumn<>("Description");
//    Columns for Manufacturer table end

//    Columns for Project table start
    JFXTreeTableColumn<ProjectData, String> projectCode = new JFXTreeTableColumn<>("Sl.No");
    JFXTreeTableColumn<ProjectData, String> projectName = new JFXTreeTableColumn<>("valve_type");

    JFXTreeTableColumn<ProjectData, String> projectCode1 = new JFXTreeTableColumn<>("Sl.No");
    JFXTreeTableColumn<ProjectData, String> projectName1 = new JFXTreeTableColumn<>("valve_type");
//    JFXTreeTableColumn<ProjectData, String> projectDescription = new JFXTreeTableColumn<>("Description");
//    Columns for Project table end
//    Columns for Project table end

    ToolKit toolkit = new ToolKit();

    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();

    private Text txtMode;
    private Text txtDate;
    private ImageView imgEmergency;
    private Text txtOffline;
    private JFXButton btnHome;
    private JFXButton btnInitial;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private HBox sectionFooter;
    @FXML
    private JFXTreeTableView<Users> tblUser;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXTextField txtQualification;
    @FXML
    private JFXComboBox<String> cmbUserType;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnAddUpdate;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXTextField txtSearchGauge;

    @FXML
    private JFXTreeTableView<Users> tblGauge;

    @FXML
    private JFXTextField txtGuageSerial;

    @FXML
    private JFXButton btnAddUpdateGauge;

    @FXML
    private JFXButton btnDeleteGauge;

    @FXML
    private JFXButton btnClearGauge;

    @FXML
    private JFXButton btnCloseMessageGauge;

    @FXML
    private HBox BoxMessageGauge;

    @FXML
    private Text txtMessageGuage;

    @FXML
    private JFXDatePicker txtCalibrationDate;

    @FXML
    private JFXDatePicker txtCalibrationDueDate;

    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnCloseMessage;
    @FXML
    private HBox BoxMessage;
    @FXML
    private Text txtMessage;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private Tab tabUser;
    @FXML
    private Tab tabGauge;
    @FXML
    private JFXComboBox<String> cmbGuageDescription;
    @FXML
    private JFXTextField txtGuageRange;

    private ImageView imgAuto;
    private ImageView imgManual;

    @FXML
    private Tab tabCMP;

    @FXML
    private JFXTextField txtSearchCustomer;

    @FXML
    private JFXTreeTableView<Users> tblCMPCustomer;

    @FXML
    private JFXTreeTableView<Users> tblCMPCustomer1;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXButton btnAddUpdateCustomer;

    @FXML
    private JFXButton btnDeleteCustomer;

    @FXML
    private JFXButton btnClearCustomer;

    @FXML
    private HBox BoxMessageCustomer;

    @FXML
    private Text txtMessageCustomer;

    @FXML
    private JFXTextField txtCustomerDescription;

    @FXML
    private JFXTextField txtSearchManufaturer;

    @FXML
    private JFXTreeTableView<ManufacturerData> tblCMPManufaturer;

    @FXML
    private JFXTreeTableView<ManufacturerData> tblCMPManufaturer1;

    @FXML
    private JFXTextField txtManufaturerName;

    @FXML
    private JFXButton btnAddUpdateManufaturer;

    @FXML
    private JFXButton btnDeleteManufaturer;

    @FXML
    private JFXButton btnClearManufaturer;

    @FXML
    private HBox BoxMessageManufaturer;

    @FXML
    private Text txtMessageManufaturer;

    @FXML
    private JFXTextField txtManufaturerDescription;

    @FXML
    private JFXTextField txtSearchProject;

    @FXML
    private JFXTreeTableView<ProjectData> tblCMPProject;
    @FXML
    private JFXTreeTableView<ProjectData> tblCMPProject1;

    @FXML
    private JFXTextField txtProjectName;

    @FXML
    private JFXButton btnAddUpdateProject;

    @FXML
    private JFXButton btnDeleteProject;

    @FXML
    private JFXButton btnClearProject;

    @FXML
    private HBox BoxMessageProject;

    @FXML
    private Text txtMessageProject;

    @FXML
    private JFXTextField txtProjectDescription;
    @FXML
    private JFXTextField txtCustomerCode;
    @FXML
    private JFXTextField txtManufaturerCode;
    @FXML
    private JFXTextField txtProjectCode;
    @FXML
    private Text txtErrorCCode;
    @FXML
    private Text txtErrorCName;
    @FXML
    private Text txtErrorCDescription;
    @FXML
    private Text txtErrorMCode;
    @FXML
    private Text txtErrorMName;
    @FXML
    private Text txtErrorMDescription;
    @FXML
    private Text txtErrorPCode;
    @FXML
    private Text txtErrorPName;
    @FXML
    private Text txtErrorPDescription;

    private volatile boolean stop_mode = false;
    @FXML
    private Text txtdate;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btngauge;
    @FXML
    private JFXButton btnTestScreeng;
    @FXML
    private JFXButton btnAirPurging;
    @FXML
    private Tab tabCMP1;
    @FXML
    private JFXTextField txtSearchCustomer1;
    @FXML
    private HBox BoxMessageCustomer1;
    @FXML
    private Text txtMessageCustomer1;
    @FXML
    private JFXTextField txtCustomerCode1;
    @FXML
    private JFXTextField txtCustomerName1;
    @FXML
    private JFXTextField txtCustomerDescription1;
    @FXML
    private JFXButton btnAddUpdateCustomer1;
    @FXML
    private JFXButton btnDeleteCustomer1;
    @FXML
    private JFXButton btnClearCustomer1;
    @FXML
    private Text txtErrorCCode1;
    @FXML
    private Text txtErrorCName1;
    @FXML
    private Text txtErrorCDescription1;
    @FXML
    private JFXTextField txtSearchManufaturer1;
    @FXML
    private HBox BoxMessageManufaturer1;
    @FXML
    private Text txtMessageManufaturer1;
    @FXML
    private JFXTextField txtManufaturerCode1;
    @FXML
    private JFXTextField txtManufaturerName1;
    @FXML
    private JFXTextField txtManufaturerDescription1;
    @FXML
    private JFXButton btnAddUpdateManufaturer1;
    @FXML
    private JFXButton btnDeleteManufaturer1;
    @FXML
    private JFXButton btnClearManufaturer1;
    @FXML
    private Text txtErrorMCode1;
    @FXML
    private Text txtErrorMName1;
    @FXML
    private Text txtErrorMDescription1;
    @FXML
    private JFXTextField txtSearchProject1;
    @FXML
    private HBox BoxMessageProject1;
    @FXML
    private Text txtMessageProject1;
    @FXML
    private JFXTextField txtProjectCode1;
    @FXML
    private JFXTextField txtProjectName1;
    @FXML
    private JFXTextField txtProjectDescription1;
    @FXML
    private JFXButton btnAddUpdateProject1;
    @FXML
    private JFXButton btnDeleteProject1;
    @FXML
    private JFXButton btnClearProject1;
    @FXML
    private Text txtErrorPCode1;
    @FXML
    private Text txtErrorPName1;
    @FXML
    private Text txtErrorPDescription1;
    @FXML
    private JFXComboBox<String> cmbGuageUnit;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize intial screen: Start
        Thread Initialize_Initial = new Thread(() -> {

            System.out.println("Initialized");
        }, "initializeIntialScreenThread");
        Initialize_Initial.setDaemon(true);
        Initialize_Initial.start();
        Session.set("screen", "AdminScreen");
        //Initialize intial screen: End

//        imgManual.setVisible(false);
//        imgAuto.setVisible(false);
//        imgEmergency.setVisible(false);
//        current_machine_mode = "45";
//        current_offline_mode = "45";
//        btnHome.setFocusTraversable(false);
//        btnInitial.setFocusTraversable(false);
//        btnReport.setFocusTraversable(false);
        //Tab1: User registration: Start
        Initial_User_registration();
        //Tab1: User registration: End

        //Tab2: Gauge Start: Start
        Initial_Guage_table();
        //Tab2: Gauge Start: End

        //Tab2: Master List: Start
        intialCMP();
        //Tab2: Master List: End
        String user_name = Session.get("user");
        if (user_name.equals("ctpl")) {
            btnAirPurging.setVisible(true);

        } else {
            btnAirPurging.setVisible(false);
        }
    }

    private void intialCMP() {

        txtCustomerCode.setDisable(true);
        txtManufaturerCode.setDisable(true);
        txtProjectCode.setDisable(true);
//        Customer Section start
        //VALIDATION
        txtErrorCCode.setVisible(false);
        txtErrorCName.setVisible(false);
        txtErrorCDescription.setVisible(false);
        add_listener_to_textfield(txtCustomerName, txtErrorCName);
        add_listener_to_textfield(txtCustomerDescription, txtErrorCDescription);

        txtCustomerDescription.setVisible(false);
        btnAddUpdateCustomer.setText("Add New");
        txtMessageCustomer.setVisible(false);
        BoxMessageCustomer.setVisible(false);
        btnClearCustomer.setVisible(false);
        txtManufaturerDescription.setVisible(false);
        txtErrorCCode.setVisible(false);
        txtProjectDescription.setVisible(false);
        txtErrorPDescription.setVisible(false);

        Sl_no.setPrefWidth(150);
        Sl_no.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        valveclass.setPrefWidth(150);
        valveclass.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        //valve_standards.setPrefWidth(200);
//        valve_standards.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);
        txtSearchCustomer.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPCustomer.setPredicate((TreeItem<Users> user) -> {
                Boolean flag = user.getValue().one.getValue().contains(newValue) || user.getValue().two.getValue().contains(newValue);
                return flag;
            });
        });

        refreshTableCustomer();

        txtCustomerCode1.setDisable(true);
        txtManufaturerCode1.setDisable(true);
        txtProjectCode1.setDisable(true);
//        Customer Section start
        //VALIDATION
        txtErrorCCode1.setVisible(false);
        txtErrorCName1.setVisible(false);
        txtErrorCDescription1.setVisible(false);
        add_listener_to_textfield(txtCustomerName1, txtErrorCName1);
        add_listener_to_textfield(txtCustomerDescription1, txtErrorCDescription1);

        txtCustomerDescription1.setVisible(false);
        btnAddUpdateCustomer1.setText("Add New");
        txtMessageCustomer1.setVisible(false);
        BoxMessageCustomer1.setVisible(false);
        btnClearCustomer1.setVisible(false);
        txtManufaturerDescription1.setVisible(false);
        txtErrorCCode1.setVisible(false);
        txtProjectDescription1.setVisible(false);
        txtErrorPDescription1.setVisible(false);

        Sl_no1.setPrefWidth(150);
        Sl_no1.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        valve_standards1.setPrefWidth(150);
        valve_standards1.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        //valve_standards.setPrefWidth(200);
//        valve_standards.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);
        txtSearchCustomer1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPCustomer1.setPredicate((TreeItem<Users> user) -> {
                Boolean flag = user.getValue().one.getValue().contains(newValue) || user.getValue().two.getValue().contains(newValue);
                return flag;
            });
        });

        refreshTableCustomer1();

//        Customer Section end
//        Manufacturer Section start
        //VALIDATION START
        txtErrorMCode.setVisible(false);
        txtErrorMName.setVisible(false);
        txtErrorMDescription.setVisible(false);
        add_listener_to_textfield(txtManufaturerName, txtErrorMName);
        add_listener_to_textfield(txtManufaturerDescription, txtErrorMDescription);
        //VALIDATION END
        btnAddUpdateManufaturer.setText("Add New");
        txtMessageManufaturer.setVisible(false);
        BoxMessageManufaturer.setVisible(false);
        btnClearManufaturer.setVisible(false);

        manufacturerCode.setPrefWidth(150);
        manufacturerCode.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().code);

        manufacturerName.setPrefWidth(150);
        manufacturerName.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().name);

//        manufacturerDescription.setPrefWidth(200);
//        manufacturerDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().description);
        txtSearchManufaturer.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPManufaturer.setPredicate((TreeItem<ManufacturerData> user) -> {
                Boolean flag = user.getValue().code.getValue().contains(newValue) || user.getValue().name.getValue().contains(newValue);
                return flag;
            });
        });

        refreshTableManufacturer();

        txtErrorMCode1.setVisible(false);
        txtErrorMName1.setVisible(false);
        txtErrorMDescription1.setVisible(false);
        add_listener_to_textfield(txtManufaturerName1, txtErrorMName1);
        add_listener_to_textfield(txtManufaturerDescription1, txtErrorMDescription1);
        //VALIDATION END
        btnAddUpdateManufaturer1.setText("Add New");
        txtMessageManufaturer1.setVisible(false);
        BoxMessageManufaturer1.setVisible(false);
        btnClearManufaturer1.setVisible(false);

        manufacturerCode1.setPrefWidth(150);
        manufacturerCode1.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().code);

        manufacturerName1.setPrefWidth(150);
        manufacturerName1.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().name);

//        manufacturerDescription.setPrefWidth(200);
//        manufacturerDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().description);
        txtSearchManufaturer1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPManufaturer1.setPredicate((TreeItem<ManufacturerData> user) -> {
                Boolean flag = user.getValue().code.getValue().contains(newValue) || user.getValue().name.getValue().contains(newValue);
                return flag;
            });
        });

        refreshTableManufacturer1();
//        Manufacturer Section end 

//        Project Section start
        //VALIDATION START
        txtErrorPCode.setVisible(false);
        txtErrorPName.setVisible(false);
        txtErrorPDescription.setVisible(false);
        add_listener_to_textfield(txtProjectName, txtErrorPName);
//        add_listener_to_textfield(txtProjectDescription, txtErrorPDescription);
        //VALIDATION END
        btnAddUpdateProject.setText("Add New");
        txtMessageProject.setVisible(false);
        BoxMessageProject.setVisible(false);
        btnClearProject.setVisible(false);

        projectCode.setPrefWidth(150);
        projectCode.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().code);

        projectName.setPrefWidth(150);
        projectName.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().name);

//        projectDescription.setPrefWidth(200);
//        projectDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().description);
        txtSearchProject.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPProject.setPredicate((TreeItem<ProjectData> user) -> {
                Boolean flag = user.getValue().code.getValue().contains(newValue) || user.getValue().name.getValue().contains(newValue);
                return flag;
            });
        });
        refreshTableProject();

        txtErrorPCode1.setVisible(false);
        txtErrorPName1.setVisible(false);
        txtErrorPDescription1.setVisible(false);
        add_listener_to_textfield(txtProjectName1, txtErrorPName1);
//        add_listener_to_textfield(txtProjectDescription, txtErrorPDescription);
        //VALIDATION END
        btnAddUpdateProject1.setText("Add New");
        txtMessageProject1.setVisible(false);
        BoxMessageProject1.setVisible(false);
        btnClearProject1.setVisible(false);

        projectCode1.setPrefWidth(150);
        projectCode1.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().code);

        projectName1.setPrefWidth(150);
        projectName1.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().name);

//        projectDescription.setPrefWidth(200);
//        projectDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().description);
        txtSearchProject1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPProject1.setPredicate((TreeItem<ProjectData> user) -> {
                Boolean flag = user.getValue().code.getValue().contains(newValue) || user.getValue().name.getValue().contains(newValue);
                return flag;
            });
        });
        refreshTableProject1();
//        Project Section end
    }

    /*
    *
     */
    private void refreshTableCustomer() {
        btnAddUpdateCustomer.setText("Add New");
        btnClearCustomer.setVisible(false);

        ObservableList<Users> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM valve_class;";
        System.out.println("query" + query);
        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {

                String id = Integer.toString(rs.getInt("valve_class_id"));
                System.out.println(id);
                users.add(new Users(rs.getString("valve_class_id"), rs.getString("valve_class")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPCustomer.getColumns().setAll(Sl_no, valveclass);
        tblCMPCustomer.setRoot(root);
        tblCMPCustomer.setShowRoot(false);

        tblCMPCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {

                String code = tblCMPCustomer.getSelectionModel().selectedItemProperty().get().getValue().one.get().replaceAll("'", "\\\\'");

                String q = "SELECT * FROM valve_class WHERE valve_class_id = '" + code + "';";

                try {
                    ResultSet rs1 = dh.getData(q, connect);

                    if (rs1.next()) {
                        txtCustomerCode.setText(rs1.getString("valve_class_id"));
                        txtCustomerName.setText(rs1.getString("valve_class"));
//                        txtCustomerDescription.setText(rs1.getString("valve_standards"));
                    }
                    btnAddUpdateCustomer.setText("Update");
                    btnClearCustomer.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    private void refreshTableCustomer1() {
        btnAddUpdateCustomer1.setText("Add New");
        btnClearCustomer1.setVisible(false);

        ObservableList<Users> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM valve_standards;";
        System.out.println("query" + query);
        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {

                String id = Integer.toString(rs.getInt("valve_standards_id"));
                System.out.println(id);
                users.add(new Users(rs.getString("valve_standards_id"), rs.getString("valve_standards")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPCustomer1.getColumns().setAll(Sl_no1, valve_standards1);
        tblCMPCustomer1.setRoot(root);
        tblCMPCustomer1.setShowRoot(false);

        tblCMPCustomer1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {

                String code = tblCMPCustomer1.getSelectionModel().selectedItemProperty().get().getValue().one.get().replaceAll("'", "\\\\'");

                String q = "SELECT * FROM `valve_standards` WHERE `valve_standards_id` = '" + code + "';";
                System.out.println("q...." + q);
                try {
                    ResultSet rs1 = dh.getData(q, connect);

                    if (rs1.next()) {
                        txtCustomerCode1.setText(rs1.getString("valve_standards_id"));
                        txtCustomerName1.setText(rs1.getString("valve_standards"));
//                        txtCustomerDescription.setText(rs1.getString("valve_standards"));
                    }
                    btnAddUpdateCustomer1.setText("Update");
                    btnClearCustomer1.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    /*
    *
     */
    private void refreshTableManufacturer() {
        btnAddUpdateManufaturer.setText("Add New");
        btnClearManufaturer.setVisible(false);

        ObservableList<ManufacturerData> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM valve_size;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new ManufacturerData(rs.getString("valve_size_id"), rs.getString("valve_size")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<ManufacturerData> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPManufaturer.getColumns().setAll(manufacturerCode, manufacturerName);
        tblCMPManufaturer.setRoot(root);
        tblCMPManufaturer.setShowRoot(false);

        tblCMPManufaturer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPManufaturer.getSelectionModel().selectedItemProperty().get().getValue().code.get().replaceAll("'", "\\\\'");

                String q = "SELECT * FROM valve_size WHERE valve_size_id = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtManufaturerCode.setText(rs1.getString("valve_size_id"));
                        txtManufaturerName.setText(rs1.getString("valve_size"));
//                        txtManufaturerDescription.setText(rs1.getString("manufacturer_description"));
                    }
                    btnAddUpdateManufaturer.setText("Update");
                    btnClearManufaturer.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    private void refreshTableManufacturer1() {
        btnAddUpdateManufaturer1.setText("Add New");
        btnClearManufaturer1.setVisible(false);

        ObservableList<ManufacturerData> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM valve_material;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new ManufacturerData(rs.getString("valve_material_id"), rs.getString("valve_material")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<ManufacturerData> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPManufaturer1.getColumns().setAll(manufacturerCode1, manufacturerName1);
        tblCMPManufaturer1.setRoot(root);
        tblCMPManufaturer1.setShowRoot(false);

        tblCMPManufaturer1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPManufaturer1.getSelectionModel().selectedItemProperty().get().getValue().code.get().replaceAll("'", "\\\\'");

                String q = "SELECT * FROM valve_material WHERE valve_material_id = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtManufaturerCode1.setText(rs1.getString("valve_material_id"));
                        txtManufaturerName1.setText(rs1.getString("valve_material"));
//                        txtManufaturerDescription.setText(rs1.getString("manufacturer_description"));
                    }
                    btnAddUpdateManufaturer1.setText("Update");
                    btnClearManufaturer1.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    /*
    *
     */
    private void refreshTableProject() {
        btnAddUpdateProject.setText("Add New");
        btnClearProject.setVisible(false);

        ObservableList<ProjectData> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM valve_type;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                System.out.println();
                users.add(new ProjectData(rs.getString("valve_type_id"), rs.getString("valve_type")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<ProjectData> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPProject.getColumns().setAll(projectCode, projectName);
        tblCMPProject.setRoot(root);
        tblCMPProject.setShowRoot(false);

        tblCMPProject.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPProject.getSelectionModel().selectedItemProperty().get().getValue().code.get().replaceAll("'", "\\\\'");
                String q = "SELECT * FROM valve_type WHERE valve_type_id = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtProjectCode.setText(rs1.getString("valve_type_id"));
                        txtProjectName.setText(rs1.getString("valve_type"));
//                        txtProjectDescription.setText(rs1.getString("project_description"));
                    }
                    btnAddUpdateProject.setText("Update");
                    btnClearProject.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    private void refreshTableProject1() {
        btnAddUpdateProject1.setText("Add New");
        btnClearProject1.setVisible(false);

        ObservableList<ProjectData> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM `test_type`;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                System.out.println();
                users.add(new ProjectData(rs.getString("test_id"), rs.getString("test_type")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<ProjectData> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPProject1.getColumns().setAll(projectCode1, projectName1);
        tblCMPProject1.setRoot(root);
        tblCMPProject1.setShowRoot(false);

        tblCMPProject1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPProject1.getSelectionModel().selectedItemProperty().get().getValue().code.get().replaceAll("'", "\\\\'");
                String q = "SELECT * FROM `test_type` WHERE test_id = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtProjectCode1.setText(rs1.getString("test_id"));
                        txtProjectName1.setText(rs1.getString("test_type"));
//                        txtProjectDescription.setText(rs1.getString("project_description"));
                    }
                    btnAddUpdateProject1.setText("Update");
                    btnClearProject1.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    private void machine_mode_value(String mode) {
        switch (mode) {
            case "0":
                txtMode.setText("Auto Mode");
                current_machine_mode = "0";
                txtDate.setFill(Color.web("#0099FF"));
                imgEmergency.setVisible(false);
                imgManual.setVisible(false);
                imgAuto.setVisible(true);
                break;
            case "1":
                txtMode.setText("Manual Mode");
                current_machine_mode = "1";
                txtDate.setFill(Color.web("#0099FF"));
                imgEmergency.setVisible(false);
                imgManual.setVisible(true);
                imgAuto.setVisible(false);
                break;
            case "2":
                txtMode.setText("Emergency Mode");
                current_machine_mode = "2";
                txtDate.setFill(Color.web("#C32420"));
                imgEmergency.setVisible(true);
                imgManual.setVisible(false);
                imgAuto.setVisible(false);
                break;
            default:
                txtMode.setText("Something wrong");
                current_machine_mode = "0";
                txtDate.setFill(Color.web("#C32420"));
                imgEmergency.setVisible(true);
                imgManual.setVisible(false);
                imgAuto.setVisible(false);
                break;
        }
    }

    public void Initial_User_registration() {

        cmbUserType.getItems().add(0, "operator");
        cmbUserType.getItems().add(1, "admin");
        btnAddUpdate.setText("Add New");
        txtMessage.setVisible(false);
        BoxMessage.setVisible(false);
        btnCloseMessage.setVisible(false);
        btnClear.setVisible(false);

        UserName.setPrefWidth(150);
        UserName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        Name.setPrefWidth(200);
        Name.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        Qualification.setPrefWidth(250);
        Qualification.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);

        UserType.setPrefWidth(150);
        UserType.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().four);
        refresh_table_user_registration();

        txtSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblUser.setPredicate((TreeItem<Users> user) -> {
                Boolean flag = user.getValue().one.getValue().contains(newValue) || user.getValue().two.getValue().contains(newValue) || user.getValue().three.getValue().contains(newValue) || user.getValue().four.getValue().contains(newValue);
                return flag;
            });
        });
    }

    public void refresh_table_user_registration() {
        btnAddUpdate.setText("Add New");
        btnClear.setVisible(false);
        ObservableList<Users> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM user_data WHERE operator_id != 1;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new Users(rs.getString("username"), rs.getString("name"), rs.getString("qualification"), rs.getString("user_type")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblUser.getColumns().setAll(UserName, Name, Qualification, UserType);
        tblUser.setRoot(root);
        tblUser.setShowRoot(false);

        tblUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String username = tblUser.getSelectionModel().selectedItemProperty().get().getValue().one.get();
                String q = "SELECT * FROM user_data WHERE username = '" + username + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtName.setText(rs1.getString("name"));
                        txtUserName.setText(rs1.getString("username"));
                        txtPassword.setText(rs1.getString("password"));
                        txtQualification.setText(rs1.getString("qualification"));
                        if (rs1.getString("user_type").equals("admin")) {
                            cmbUserType.getSelectionModel().select(1);
                        } else {
                            cmbUserType.getSelectionModel().select(0);
                        }
                    }
                    btnAddUpdate.setText("Update");
                    btnClear.setVisible(true);
                    txtUserName.setEditable(false);
                } catch (SQLException e) {
                }
            }
        });

    }

    public void Initial_Guage_table() {
//        txtGuageRange.setEditable(false);
        String qu = "SELECT * FROM `gauge_description` GROUP BY `index` ASC";
        try {
            ResultSet rs = dh.getData(qu, connect);
            while (rs.next()) {
                cmbGuageDescription.getItems().add(Integer.parseInt(rs.getString("index")), rs.getString("gauge_description"));
            }
        } catch (NumberFormatException | SQLException e) {
        }

        cmbGuageUnit.getItems().clear();
        cmbGuageUnit.getItems().addAll(
                "bar",
                "psi",
                "kg/sq.cm"
        );
        btnAddUpdateGauge.setText("Add New");
        txtMessageGuage.setVisible(false);
        BoxMessageGauge.setVisible(false);
        btnCloseMessageGauge.setVisible(false);
        btnClearGauge.setVisible(false);

        GS.setPrefWidth(200);
        GS.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        GD.setPrefWidth(200);
        GD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        CD.setPrefWidth(150);
        CD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);

        CDD.setPrefWidth(150);
        CDD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().four);

        GR.setPrefWidth(200);
        GR.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().five);

        refresh_table_Guage();

        txtSearchGauge.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblGauge.setPredicate((TreeItem<Users> guage) -> {
                Boolean flag = guage.getValue().one.getValue().contains(newValue) || guage.getValue().two.getValue().contains(newValue) || guage.getValue().three.getValue().contains(newValue) || guage.getValue().four.getValue().contains(newValue) || guage.getValue().five.getValue().contains(newValue);
                return flag;
            });
        });
    }

    private void refresh_table_Guage() {
        btnAddUpdateGauge.setText("Add New");
        btnClearGauge.setVisible(false);
        ObservableList<Users> gauge = FXCollections.observableArrayList();
        String query = "SELECT gda.*,gd.gauge_range FROM gauge_data gda LEFT JOIN gauge_description gd ON gd.gauge_description = gda.description;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                gauge.add(new Users(rs.getString("serial"), rs.getString("description"), rs.getString("c_date"), rs.getString("c_due_date"), rs.getString("gauge_range")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(gauge, RecursiveTreeObject::getChildren);
        tblGauge.getColumns().setAll(GS, GD, GR, CD, CDD);
        tblGauge.setRoot(root);
        tblGauge.setShowRoot(false);

        tblGauge.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String serial = tblGauge.getSelectionModel().selectedItemProperty().get().getValue().one.get();
                String q = "SELECT gda.*,gd.index,gd.gauge_range FROM gauge_data gda JOIN gauge_description gd ON gd.gauge_description = gda.description WHERE serial = '" + serial + "';";
//                System.out.println("q"+q);
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    txtCalibrationDate.setValue(null);
                    txtCalibrationDueDate.setValue(null);
                    if (rs1.next()) {
                        txtGuageSerial.setText(rs1.getString("serial"));
//                        System.out.println("......"+rs1.getString("index"));
                        cmbGuageDescription.getSelectionModel().select(Integer.parseInt(rs1.getString("index")));

                        txtGuageRange.setText(rs1.getString("gauge_range").replaceAll("\\s.*", ""));
                        int spacePos = rs1.getString("gauge_range").indexOf(" ");
                        String suffix = rs1.getString("gauge_range").substring(spacePos + 1, rs1.getString("gauge_range").length());
                        cmbGuageUnit.getSelectionModel().select(suffix);
                        prev_unit=suffix;
                        txtCalibrationDate.setValue(LocalDate.parse(rs1.getString("c_date")));
                        txtCalibrationDueDate.setValue(LocalDate.parse(rs1.getString("c_due_date")));
                    }
                    btnAddUpdateGauge.setText("Update");
                    btnClearGauge.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    public void dropbox(String screen, boolean Initial_check) {
        try {
            try {
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
                System.out.println("Exception while stoping insert_plc_thread_Admin_Screen : " + e.getMessage());
            }
            try {
                Background_Processes.stop_date_time();
            } catch (Exception e) {
                System.out.println("Exception while stoping date_time_thread_Admin_Screen : " + e.getMessage());
            }
            stop_mode = true;
//            if (Initial_check) {
//                Background_Processes.Initialize_Initial_Screen();
//            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource(screen));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);

        } catch (Exception ex) {
            Logger.getLogger(AdminScreenController_old.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnHomeAction(ActionEvent event) {
        dropbox("LoginScreen.fxml", false);
    }

    @FXML
    private void btnReportAction(ActionEvent event) {
        dropbox("ReportScreen.fxml", false);
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
//        dropbox("AdminScreen.fxml", false);
    }

    private void btnInitialAction(ActionEvent event) {
        dropbox("InitialScreen.fxml", true);
    }

    @FXML
    private void btnAddUpdateAction(ActionEvent event) {
        String name = txtName.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String qualification = txtQualification.getText();
        String usertype = cmbUserType.getSelectionModel().getSelectedItem();
        if (name == null || name.equals("") || username == null || username.equals("") || password == null || password.equals("") || qualification == null || qualification.equals("") || usertype == null || usertype.equals("")) {
            if (name == null || name.equals("")) {
                txtName.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtName.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (username == null || username.equals("")) {
                txtUserName.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (qualification == null || qualification.equals("")) {
                txtQualification.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (password == null || password.equals("")) {
                txtPassword.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (usertype == null || usertype.equals("")) {
                cmbUserType.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            }

        } else {
            cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            txtName.setUnFocusColor(Paint.valueOf("#000000"));
            txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
            txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
            if (btnAddUpdate.getText().equals("Add New")) {

                //Insert Stored procedure
                String sp = " insert_user_data_sp('" + username + "','" + password + "','" + name + "','" + qualification + "','" + usertype + "')";
                try {
                    if (dh.execute_sp(sp, connect) > 0) {
                        txtName.setText("");
                        txtPassword.setText("");
                        txtUserName.setText("");
                        txtQualification.setText("");
                        cmbUserType.getSelectionModel().clearSelection();
                        refresh_table_user_registration();
                        messageUser("User Added Successfully :)", "#90EE02");
                    } else {
                        messageUser("User not Added, Please check all paramenters !!!!", "#FF0808");
                    }
                } catch (SQLException e) {
                    messageUser(String.valueOf(e.getMessage()), "#FF0808");
                }
            } else {

                //update stored procedure
                String sp = " update_user_data_sp('" + username + "','" + password + "','" + name + "','" + qualification + "','" + usertype + "')";
                try {
                    if (dh.execute_sp(sp, connect) > 0) {
                        txtName.setText("");
                        txtPassword.setText("");
                        txtUserName.setText("");
                        txtQualification.setText("");
                        cmbUserType.getSelectionModel().clearSelection();
                        refresh_table_user_registration();
                        btnAddUpdate.setText("Add New");
                        txtUserName.setEditable(true);
                        btnClear.setVisible(false);
                        messageUser("User Updated Successfully :)", "#90EE02");

                    } else {
                        messageUser("User not Updated, Please check all paramenters", "#FF0808");
                    }

                } catch (SQLException e) {
                    messageUser(String.valueOf(e.getMessage()), "#FF0808");
                }
            }
        }
    }

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        String username = txtUserName.getText();
        if (username == null || username.equals("")) {
            txtUserName.setUnFocusColor(Paint.valueOf("#FF0808"));
            cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            txtName.setUnFocusColor(Paint.valueOf("#000000"));
            txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
        } else {
            cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            txtName.setUnFocusColor(Paint.valueOf("#000000"));
            txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
            txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
            String sp = "DELETE FROM `user_data` WHERE `username` ='" + username + "';";
            try {
                if (dh.execute(sp, connect) > 0) {
                    txtName.setText("");
                    txtPassword.setText("");
                    txtUserName.setText("");
                    txtQualification.setText("");
                    cmbUserType.getSelectionModel().clearSelection();
                    txtUserName.setEditable(true);
                    btnClear.setVisible(false);
                    btnAddUpdate.setText("Add New");
                    messageUser("User Deleted Successfully :)", "#90EE02");
                    refresh_table_user_registration();
                } else {
                    messageUser("User not exist!!!!", "#FF0808");
                }
            } catch (SQLException e) {
                messageUser(String.valueOf(e.getMessage()), "#FF0808");
            }

        }
    }

    @FXML
    private void btnClearAction(ActionEvent event) {

        txtName.setText("");
        txtPassword.setText("");
        txtUserName.setText("");
        txtQualification.setText("");
        cmbUserType.getSelectionModel().clearSelection();
        cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
        txtName.setUnFocusColor(Paint.valueOf("#000000"));
        txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
        txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
        txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
        btnAddUpdate.setText("Add New");
        btnClear.setVisible(false);
        txtUserName.setEditable(true);
        refresh_table_user_registration();
    }

    @FXML
    private void btnCloseMessageAction(ActionEvent event) {
        txtMessage.setVisible(false);
        BoxMessage.setVisible(false);
        btnCloseMessage.setVisible(false);
    }

    @FXML
    private void tabUserAction(Event event) {
        refresh_table_user_registration();
        txtName.setText("");
        txtPassword.setText("");
        txtUserName.setText("");
        txtQualification.setText("");
        cmbUserType.getSelectionModel().clearSelection();
        cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
        txtName.setUnFocusColor(Paint.valueOf("#000000"));
        txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
        txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
        txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
    }

    @FXML
    private void tabGaugeAction(Event event) {
        refresh_table_Guage();
        txtGuageSerial.setText("");
        txtGuageRange.setText("");
        cmbGuageDescription.getSelectionModel().clearSelection();
        cmbGuageUnit.getSelectionModel().clearSelection();
        txtCalibrationDate.getEditor().clear();
        txtCalibrationDueDate.getEditor().clear();
        btnClearGauge.setVisible(false);
        txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
        cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
    }

    @FXML
    void btnDeleteGaugeAction(ActionEvent event) {
        String serial = txtGuageSerial.getText();
        String unit = cmbGuageUnit.getSelectionModel().getSelectedItem();
        if (serial == null || serial.equals("")) {
            txtGuageSerial.setUnFocusColor(Paint.valueOf("#FF0808"));
            cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
        } else {
            txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
            cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
            String sp = "DELETE FROM `gauge_data` WHERE `serial` ='" + serial + "' and `gauge_range` LIKE '" + "%" + unit + "';";
            System.out.println("sp" + sp);
            try {
                if (dh.execute(sp, connect) > 0) {
                    txtGuageSerial.setText("");
                    txtGuageRange.setText("");
                    cmbGuageDescription.getSelectionModel().clearSelection();
                    cmbGuageUnit.getSelectionModel().clearSelection();
                    txtCalibrationDate.getEditor().clear();
                    txtCalibrationDueDate.getEditor().clear();
//                    txtGuageSerial.setEditable(true);
                    btnClearGauge.setVisible(false);
                    btnAddUpdateGauge.setText("Add New");
                    messageGuage("Gauge Deleted Successfully :)", "#90EE02");
                    refresh_table_Guage();
                } else {
                    messageGuage("Gauge not exist!!!!", "#FF0808");
                }
            } catch (Exception e) {
                messageGuage(String.valueOf(e.getMessage()), "#FF0808");
            }

        }
    }

    @FXML
    void btnCloseMessageGaugeAction(ActionEvent event) {
        txtMessageGuage.setVisible(false);
        BoxMessageGauge.setVisible(false);
        btnCloseMessageGauge.setVisible(false);
    }

    @FXML
    void btnClearGaugeAction(ActionEvent event) {
        txtGuageSerial.setText("");
        txtGuageRange.setText("");
        cmbGuageDescription.getSelectionModel().clearSelection();
        cmbGuageUnit.getSelectionModel().clearSelection();
        txtCalibrationDate.getEditor().clear();
        txtCalibrationDueDate.getEditor().clear();
        txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
        cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
        btnAddUpdateGauge.setText("Add New");
        btnClearGauge.setVisible(false);
//        txtGuageSerial.setEditable(true);
        refresh_table_Guage();
    }

    @FXML
    void btnAddUpdateGaugeAction(ActionEvent event) throws SQLException {
        String serial = txtGuageSerial.getText();
        String description = cmbGuageDescription.getSelectionModel().getSelectedItem();
        String range = txtGuageRange.getText().replaceAll(" ", "");
        int i = txtGuageRange.getText().indexOf("-"); // 4
        String min = txtGuageRange.getText().substring(0, i); // from 0 to 3

//        System.out.println("min" + min);
        String unit = cmbGuageUnit.getSelectionModel().getSelectedItem();
        String date = String.valueOf(txtCalibrationDate.getValue());
        String duedate = String.valueOf(txtCalibrationDueDate.getValue());

        double green = 0.0;
        int spacePos = range.indexOf("-");
        String max = range.substring(spacePos + 1, range.length()).replaceAll(" ", "");
//        System.out.println("max...." + max);
//        System.out.println("ddddd" + Double.valueOf(min));
//        System.out.println("ddddd" + Double.valueOf(max));

        green = (Double.valueOf(min) + Double.valueOf(max)) / 2;
//        System.out.println("green" + green);

        if (serial.equals("null") || serial.equals("") || description.equals("null") || description.equals("") || unit.equals("null") || unit.equals("") || date.equals("null") || date.equals("") || duedate.equals("null") || duedate.equals("")) {
            messageGuage("All Felds are mendatory,Please provide appropriate data !!!!!", "#FF0808");

        } else {
            txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
            cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
            cmbGuageUnit.setUnFocusColor(Paint.valueOf("#000000"));
            if (btnAddUpdateGauge.getText().equals("Add New")) {
                String sql = "SELECT * FROM `gauge_data` WHERE`serial`='" + serial + "' and `description`='" + description + "' and `gauge_range` LIKE '" + "%" + unit + "';";
                ResultSet rs_sele = dh.getData(sql, connect);
                if (rs_sele.next()) {
                    messageGuage("Duplicate entry for gauge..", "#FF0808");
                    btnClearGauge.setVisible(true);
                } else {
                    //insert gauge stored procedure
                    String sp = " insert_gauge_data_sp('" + serial + "','" + description + "','" + range.concat(" " + unit) + "','" + date + "','" + duedate + "')";
                    try {
                        if (dh.execute_sp(sp, connect) > 0) {

                            String sql_in = "  INSERT INTO `gauge_range`(`serial`, `description`, `gauge_range`, `minimum`, `green`, `maximum`) VALUES ('" + serial + "','" + description + "','" + range.concat(" " + unit) + "','" + min + "','" + Integer.valueOf((int) green) + "','" + max + "')";
                            System.out.println("sql_in"+sql_in);
                            dh.execute(sql_in, connect);

                            txtGuageSerial.setText("");
                            txtGuageRange.setText("");
                            cmbGuageDescription.getSelectionModel().clearSelection();
                            cmbGuageUnit.getSelectionModel().clearSelection();
                            txtCalibrationDate.getEditor().clear();
                            txtCalibrationDueDate.getEditor().clear();
                            refresh_table_Guage();
                            messageGuage("Guage Added Successfully :)", "#90EE02");

                        } else {
                            messageGuage("Gauge not Added, Please check all paramenters !!!!", "#FF0808");
                        }
                    } catch (SQLException e) {
                        messageGuage(String.valueOf(e.getMessage()), "#FF0808");
                    }
                }
            } else {
                //update gauge stored procedure
//                String sp = " update_gauge_data_sp('" + serial + "','" + description + "','" + range.concat(" " + unit) + "','" + date + "','" + duedate + "','"+unit+"')";
//                System.out.println("sp"+sp);
                  String sp = " UPDATE `gauge_data` SET `serial`='" + serial + "',`description`='" + description + "',`gauge_range`='" + range.concat(" " + unit) + "',`c_date`='" + date + "',`c_due_date`='" + duedate + "'  WHERE `serial`='" + serial + "' and `gauge_range` LIKE '" + "%" + prev_unit + "';";
                  System.out.println("sp"+sp);
                try {
                    if (dh.execute(sp, connect) > 0) {
                        
                      String sql_in = " UPDATE `gauge_range` SET `serial`='" + serial + "',`description`='" + description + "',`gauge_range`='" + range.concat(" " + unit) + "' WHERE `serial`='" + serial + "' and `gauge_range` LIKE '" + "%" + prev_unit + "';";
                            System.out.println("sql_in"+sql_in);
                            dh.execute(sql_in, connect);
                        
                        txtGuageSerial.setText("");
                        txtGuageRange.setText("");
                        cmbGuageDescription.getSelectionModel().clearSelection();
                        cmbGuageUnit.getSelectionModel().clearSelection();
                        txtCalibrationDate.getEditor().clear();
                        txtCalibrationDueDate.getEditor().clear();
                        refresh_table_Guage();
                        btnAddUpdateGauge.setText("Add New");
//                        txtGuageSerial.setEditable(true);
                        btnClearGauge.setVisible(false);
                        messageGuage("Gauge Updated Successfully :)", "#90EE02");

                    } else {
                        messageGuage("Gauge not Updated, Please check all paramenters", "#FF0808");
                    }

                } catch (SQLException e) {
                    messageGuage(String.valueOf(e.getMessage()), "#FF0808");
                }
            }
        }
    }

    @FXML
    private void cmbGuageDescriptionAction(ActionEvent event) {
//        String desc = cmbGuageDescription.getSelectionModel().getSelectedItem();
//        String query = "SELECT gauge_range FROM gauge_description WHERE gauge_description = '" + desc + "';";
//        try {
//            ResultSet rs = dh.getData(query, connect);
//            if (rs.next()) {
//                txtGuageRange.setText(rs.getString("gauge_range"));
//            }
//        } catch (Exception e) {
//            messageGuage(String.valueOf(e.getMessage()), "#FF0808");
//        }
    }

    private void btnSystemCheckAction(ActionEvent event) {
        dropbox("AlarmScreen.fxml", false);
    }

    @FXML
    private void btnAddUpdateCustomerAction(ActionEvent event) {
        String cName = txtCustomerName.getText().replaceAll("'", "\\\\'");
        String cDescription = txtCustomerDescription.getText().replaceAll("'", "\\\\'");
        if (cName == null || cName.equals("")) {
            if (cName == null || cName.equals("")) {
                txtErrorCName.setVisible(true);
            } else {
                txtErrorCName.setVisible(false);
            }
//            if (cDescription == null || cDescription.equals("")) {
//                txtErrorCDescription.setVisible(true);
//            } else {
//                txtErrorCDescription.setVisible(false);
//            }
        } else {
            if (btnAddUpdateCustomer.getText().equals("Add New")) {
                try {
                    //Check if user is exist
                    ResultSet check_rs = dh.getData("SELECT * FROM valve_class ORDER BY `valve_class_id` DESC LIMIT 1;", connect);
                    System.out.println("check_rs" + check_rs);
                    if (check_rs.next()) {

                        String vc_in = "INSERT INTO valve_class VALUES ('0','" + cName + "','0','49','70')";
                        System.out.println("vc_in :" + vc_in);
                        int executed = dh.execute(vc_in, connect);
                        if (executed == 1) {
                            setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Valve Class Added :)", Colors.msgGreen);
                        } else {
                            setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                        }

                    }
                } catch (SQLException ex) {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, ex.getMessage() + ", please contact Admin :)", Colors.msgRed);
                }
            } else {
                try {
                    String cCode = txtCustomerCode.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    String vc_up = "UPDATE valve_class SET valve_class='" + cName + "'  WHERE valve_class_id='" + cCode + "'";
                    System.out.println("update vc: " + vc_up);
                    int executed = dh.execute(vc_up, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Valve Class   :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtCustomerCode, txtCustomerName, txtCustomerDescription, btnAddUpdateCustomer, btnClearCustomer);
            refreshTableCustomer();
            txtErrorCName.setVisible(false);
            txtErrorCDescription.setVisible(false);
        }
    }

    /*
    *@params txtMsg
    *@params msgBox
    *@params msg
    *@params Color_Code
     */
    private void setMessageForResult(Text txtMsg, HBox msgBox, String msg, String Color_Code) {
        txtMsg.setText(msg);
        msgBox.setStyle("-fx-background-color: derive(" + Color_Code + ",50%);");
        txtMsg.setVisible(true);
        msgBox.setVisible(true);
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent ev) -> {
            txtMsg.setVisible(false);
            msgBox.setVisible(false);
        }));
        timer.setCycleCount(1);
        timer.play();
    }

    /*
    *@params txtMsg
    *@params msgBox
    *@params msg
    *@params Color_Code
     */
    private void cleadAndAddNewBtnAction(JFXTextField txtCode, JFXTextField txtName, JFXTextField txtDescription, JFXButton btnAddUpdate, JFXButton btnClear) {
        btnAddUpdate.setText("Add New");
        txtCode.setText("");
        txtName.setText("");
        txtDescription.setText("");
        btnClear.setVisible(false);
    }

    @FXML
    private void btnDeleteCustomerAction(ActionEvent event) {
        String cCode = txtCustomerCode.getText().replaceAll("'", "\\\\'");
        if (cCode == null || cCode.equals("")) {
            setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
//            txtErrorMCode.setVisible(false);
            try {
                //Check if user is exist
                String vc_del = "DELETE FROM valve_class WHERE valve_class_id='" + cCode + "'";
                System.out.println("vc_Del " + vc_del);
                int executed = dh.execute(vc_del, connect);
                System.out.println("vc_Del " + vc_del + " exe : " + executed);
                if (executed == 1) {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Valve class Removed  :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageCustomer, BoxMessageCustomer, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtCustomerCode, txtCustomerName, txtCustomerDescription, btnAddUpdateCustomer, btnClearCustomer);
            refreshTableCustomer();
            txtErrorCName.setVisible(false);
            txtErrorCDescription.setVisible(false);
        }
    }

    @FXML
    private void btnClearCustomerAction(ActionEvent event) {
        cleadAndAddNewBtnAction(txtCustomerCode, txtCustomerName, txtCustomerDescription, btnAddUpdateCustomer, btnClearCustomer);
        refreshTableCustomer();
    }

    @FXML
    private void btnAddUpdateManufaturerAction(ActionEvent event) throws SQLException {
        String mName = txtManufaturerName.getText().replaceAll("'", "\\\\'");
//        String mDescription = txtManufaturerDescription.getText().replaceAll("'", "\\\\'");
        if (mName == null || mName.equals("")) {
            if (mName == null || mName.equals("")) {
                txtErrorMName.setVisible(true);
            } else {
                txtErrorMName.setVisible(false);
            }

        } else {
            if (btnAddUpdateManufaturer.getText().equals("Add New")) {
                try {
                    //Check if user is exist

                    String vs_in = "INSERT INTO valve_size(`valve_size`)VALUES('" + mName + "')";
                    System.out.println("vs_in :" + vs_in);
                    int executed = dh.execute(vs_in, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Valve size Added  :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);

                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }
            } else {
                String mCode = txtManufaturerCode.getText().replaceAll("'", "\\\\'");
                //Check if user is exist
//                    int executed = dh.execute_sp("update_tbl_manufacturer_sp('" + mCode + "','" + mName + "','" + mDescription + "')", connect);                      
                String vs_up = "UPDATE valve_size SET valve_size='" + mName + "'  WHERE valve_size_id='" + mCode + "'";
                int executed = dh.execute(vs_up, connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Valve size Updated  :)", Colors.msgGreen);

                } else {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtManufaturerCode, txtManufaturerName, txtManufaturerDescription, btnAddUpdateManufaturer, btnClearManufaturer);
            refreshTableManufacturer();
            txtErrorMName.setVisible(false);
            txtErrorMDescription.setVisible(false);
        }

    }

    @FXML
    private void btnDeleteManufaturerAction(ActionEvent event) {
        String mCode = txtManufaturerCode.getText().replaceAll("'", "\\\\'");
        if (mCode == null || mCode.equals("")) {
            setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
//            txtErrorMCode.setVisible(false);
            try {
                //Check if user is exist
                String vs_del = "DELETE FROM valve_size WHERE valve_size_id='" + mCode + "'";
                int executed = dh.execute(vs_del, connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "valve_size Removed  :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtManufaturerCode, txtManufaturerName, txtManufaturerDescription, btnAddUpdateManufaturer, btnClearManufaturer);
            refreshTableManufacturer();
            txtErrorMName.setVisible(false);
            txtErrorMDescription.setVisible(false);
        }
    }

    @FXML
    private void btnClearManufaturerAction(ActionEvent event) {
        cleadAndAddNewBtnAction(txtManufaturerCode, txtManufaturerName, txtManufaturerDescription, btnAddUpdateManufaturer, btnClearCustomer);
        refreshTableManufacturer();
    }

    @FXML
    private void btnAddUpdateProjectAction(ActionEvent event) {
        String pName = txtProjectName.getText().replaceAll("'", "\\\\'");
        String pDescription = txtProjectDescription.getText().replaceAll("'", "\\\\'");
        if (pName == null || pName.equals("")) {
            if (pName == null || pName.equals("")) {
                txtErrorPName.setVisible(true);
            } else {
                txtErrorPName.setVisible(false);
            }

        } else {
            if (btnAddUpdateProject.getText().equals("Add New")) {
                try {
                    //Check if user is exist

                    String vt_in = "INSERT INTO valve_type(`valve_type`)VALUES('" + pName + "')";
                    System.out.println("vt_in : " + vt_in);
                    int executed = dh.execute(vt_in, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageProject, BoxMessageProject, "valve_type Added  :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageProject, BoxMessageProject, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            } else {
                try {
                    String pCode = txtProjectCode.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    String vs_up = "UPDATE valve_type SET valve_type='" + pName + "'  WHERE valve_type_id='" + pCode + "'";
                    int executed = dh.execute(vs_up, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageProject, BoxMessageProject, "valve_type Updated  :)", Colors.msgGreen);

                    } else {
                        setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtProjectCode, txtProjectName, txtProjectDescription, btnAddUpdateProject, btnClearProject);
            refreshTableProject();
            txtErrorPName.setVisible(false);
            txtErrorPDescription.setVisible(false);
        }
    }

    @FXML
    private void btnDeleteProjectAction(ActionEvent event) {
        String pCode = txtProjectCode.getText().replaceAll("'", "\\\\'");
        if (pCode == null || pCode.equals("")) {
            setMessageForResult(txtMessageProject, BoxMessageProject, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
            try {
                //Check if user is exist
                String vs_del = "DELETE FROM valve_type WHERE valve_type_id='" + pCode + "'";
                int executed = dh.execute(vs_del, connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageProject, BoxMessageProject, "valve_type Removed  :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageProject, BoxMessageProject, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtProjectCode, txtProjectName, txtProjectDescription, btnAddUpdateProject, btnClearProject);
            refreshTableProject();
            txtErrorPName.setVisible(false);
            txtErrorPDescription.setVisible(false);
        }
    }

    @FXML
    private void btnClearProjectAction(ActionEvent event
    ) {
        cleadAndAddNewBtnAction(txtProjectCode, txtProjectName, txtProjectDescription, btnAddUpdateProject, btnClearProject);
        refreshTableProject();
    }

    @FXML
    private void tabCMPAction(Event event
    ) {
    }

    @FXML
    private void btnLoginAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }

    public static Stage catStage;

    @FXML
    private void btngaugeAction(ActionEvent event) throws IOException {
        //Open Gauge calibration window
//        Parent root = FXMLLoader.load(getClass().getResource("gaugecalibration.fxml"));
//        Platform.runLater(() -> {
////                        cmbTestStandards.getSelectionModel().select(0);
//            catStage = new Stage(StageStyle.UNDECORATED);
//            catStage.setAlwaysOnTop(true);
//            Scene scene = new Scene(root, 1330, 250);
//
//            catStage.setScene(scene);
//            catStage.show();
//        });
    }

    private void btnTestScreenAction(MouseEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);

    }

    @FXML
    private void btnTestScreenAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }

    @FXML
    private void btnAirPurgingAction(ActionEvent event) {

        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AirPurgingScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);

    }

    @FXML
    private void tabCMP1Action(Event event) {
    }

    @FXML
    private void btnAddUpdateCustomer1Action(ActionEvent event) {
        String cName = txtCustomerName1.getText().replaceAll("'", "\\\\'");
//        String cDescription = txtCustomerDescription.getText().replaceAll("'", "\\\\'");
        if (cName == null || cName.equals("")) {
            if (cName == null || cName.equals("")) {
                txtErrorCName1.setVisible(true);
            } else {
                txtErrorCName1.setVisible(false);
            }
//            if (cDescription == null || cDescription.equals("")) {
//                txtErrorCDescription.setVisible(true);
//            } else {
//                txtErrorCDescription.setVisible(false);
//            }
        } else {
            if (btnAddUpdateCustomer1.getText().equals("Add New")) {
                try {
                    //Check if user is exist
                    ResultSet check_rs = dh.getData("SELECT * FROM `valve_standards` ORDER BY `valve_standards_id`  DESC LIMIT 1;", connect);
                    System.out.println("check_rs" + check_rs);
                    if (check_rs.next()) {

                        String vc_in = "INSERT INTO valve_standards VALUES ('0','" + cName + "')";
                        System.out.println("vc_in :" + vc_in);
                        int executed = dh.execute(vc_in, connect);
                        if (executed == 1) {
                            setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "Valve standard Added :)", Colors.msgGreen);
                        } else {
                            setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                        }

                    }
                } catch (SQLException ex) {
                    setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, ex.getMessage() + ", please contact Admin :)", Colors.msgRed);
                }
            } else {
                try {
                    String cCode = txtCustomerCode1.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    String vc_up = "UPDATE valve_standards SET valve_standards='" + cName + "'  WHERE valve_standards_id='" + cCode + "'";
                    System.out.println("update vc: " + vc_up);
                    int executed = dh.execute(vc_up, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "Valve Dtandard   :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtCustomerCode1, txtCustomerName1, txtCustomerDescription1, btnAddUpdateCustomer1, btnClearCustomer1);
            refreshTableCustomer1();
            txtErrorCName1.setVisible(false);
            txtErrorCDescription1.setVisible(false);
        }

    }

    @FXML
    private void btnDeleteCustomer1Action(ActionEvent event) {
        String cCode = txtCustomerCode1.getText().replaceAll("'", "\\\\'");
        if (cCode == null || cCode.equals("")) {
            setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
//            txtErrorMCode.setVisible(false);
            try {
                //Check if user is exist
                String vc_del = "DELETE FROM `valve_standards` WHERE `valve_standards_id`='" + cCode + "'";
                System.out.println("vc_Del " + vc_del);
                int executed = dh.execute(vc_del, connect);
                System.out.println("vc_Del " + vc_del + " exe : " + executed);
                if (executed == 1) {
                    setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "Valve standard Removed  :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageCustomer1, BoxMessageCustomer1, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtCustomerCode1, txtCustomerName1, txtCustomerDescription1, btnAddUpdateCustomer1, btnClearCustomer1);
            refreshTableCustomer1();
            txtErrorCName1.setVisible(false);
            txtErrorCDescription1.setVisible(false);
        }
    }

    @FXML
    private void btnAddUpdateManufaturer1Action(ActionEvent event) throws SQLException {
        String mName = txtManufaturerName1.getText().replaceAll("'", "\\\\'");
//        String mDescription = txtManufaturerDescription.getText().replaceAll("'", "\\\\'");
        if (mName == null || mName.equals("")) {
            if (mName == null || mName.equals("")) {
                txtErrorMName1.setVisible(true);
            } else {
                txtErrorMName1.setVisible(false);
            }

        } else {
            if (btnAddUpdateManufaturer1.getText().equals("Add New")) {
                try {
                    //Check if user is exist

                    String vs_in = "INSERT INTO valve_material(`valve_material`)VALUES('" + mName + "')";
                    System.out.println("vs_in :" + vs_in);
                    int executed = dh.execute(vs_in, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Valve Material Added  :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Something went wrong, please contact Admin :)", Colors.msgRed);

                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }
            } else {
                String mCode = txtManufaturerCode1.getText().replaceAll("'", "\\\\'");
                //Check if user is exist
//                    int executed = dh.execute_sp("update_tbl_manufacturer_sp('" + mCode + "','" + mName + "','" + mDescription + "')", connect);                      
                String vs_up = "UPDATE valve_material SET valve_material='" + mName + "'  WHERE valve_material_id='" + mCode + "'";
                int executed = dh.execute(vs_up, connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Valve Material Updated  :)", Colors.msgGreen);

                } else {
                    setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtManufaturerCode1, txtManufaturerName1, txtManufaturerDescription1, btnAddUpdateManufaturer1, btnClearManufaturer1);
            refreshTableManufacturer1();
            txtErrorMName1.setVisible(false);
            txtErrorMDescription1.setVisible(false);
        }
    }

    @FXML
    private void btnDeleteManufaturer1Action(ActionEvent event) {
        String mCode = txtManufaturerCode1.getText().replaceAll("'", "\\\\'");
        if (mCode == null || mCode.equals("")) {
            setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
//            txtErrorMCode.setVisible(false);
            try {
                //Check if user is exist
                String vs_del = "DELETE FROM valve_material WHERE valve_material_id='" + mCode + "'";
                int executed = dh.execute(vs_del, connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Valve Material Removed  :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtManufaturerCode1, txtManufaturerName1, txtManufaturerDescription1, btnAddUpdateManufaturer1, btnClearManufaturer1);
            refreshTableManufacturer1();
            txtErrorMName1.setVisible(false);
            txtErrorMDescription1.setVisible(false);
        }
    }

    @FXML
    private void btnAddUpdateProject1Action(ActionEvent event) {

        String pName = txtProjectName1.getText().replaceAll("'", "\\\\'");
//        String pDescription = txtProjectDescription1.getText().replaceAll("'", "\\\\'");
        if (pName == null || pName.equals("")) {
            if (pName == null || pName.equals("")) {
                txtErrorPName1.setVisible(true);
            } else {
                txtErrorPName1.setVisible(false);
            }

        } else {
            if (btnAddUpdateProject1.getText().equals("Add New")) {
                try {
                    //Check if user is exist

                    String vt_in = "INSERT INTO test_type(`test_type`,`reference_type`)VALUES('" + pName + "','" + pName + "')";
                    System.out.println("vt_in : " + vt_in);
                    int executed = dh.execute(vt_in, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageProject1, BoxMessageProject1, "Test Type Added  :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageProject1, BoxMessageProject1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageProject1, BoxMessageProject1, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            } else {
                try {
                    String pCode = txtProjectCode1.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    String vs_up = "UPDATE test_type SET test_type='" + pName + "'  WHERE test_id='" + pCode + "'";
                    int executed = dh.execute(vs_up, connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageProject1, BoxMessageProject1, "Test Type Updated  :)", Colors.msgGreen);

                    } else {
                        setMessageForResult(txtMessageProject1, BoxMessageProject1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer1, BoxMessageManufaturer1, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtProjectCode1, txtProjectName1, txtProjectDescription1, btnAddUpdateProject1, btnClearProject1);
            refreshTableProject1();
            txtErrorPName1.setVisible(false);
            txtErrorPDescription1.setVisible(false);
        }
    }

    @FXML
    private void btnDeleteProject1Action(ActionEvent event) {
        String pCode = txtProjectCode1.getText().replaceAll("'", "\\\\'");
        if (pCode == null || pCode.equals("")) {
            setMessageForResult(txtMessageProject1, BoxMessageProject1, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
            try {
                //Check if user is exist
                String vs_del = "DELETE FROM test_type WHERE test_id='" + pCode + "'";
                int executed = dh.execute(vs_del, connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageProject1, BoxMessageProject1, "Test Type  Removed  :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageProject1, BoxMessageProject1, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageProject1, BoxMessageProject1, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtProjectCode1, txtProjectName1, txtProjectDescription1, btnAddUpdateProject1, btnClearProject1);
            refreshTableProject1();
            txtErrorPName1.setVisible(false);
            txtErrorPDescription1.setVisible(false);
        }
    }

    @FXML
    private void btnClearCustomer1Action(ActionEvent event) {
        cleadAndAddNewBtnAction(txtCustomerCode1, txtCustomerName1, txtCustomerDescription1, btnAddUpdateCustomer1, btnClearCustomer1);
        refreshTableCustomer();
    }

    @FXML
    private void btnClearManufaturer1Action(ActionEvent event) {
        cleadAndAddNewBtnAction(txtManufaturerCode1, txtManufaturerName1, txtManufaturerDescription1, btnAddUpdateManufaturer1, btnClearCustomer1);
        refreshTableManufacturer1();
    }

    @FXML
    private void btnClearProject1Action(ActionEvent event) {
        cleadAndAddNewBtnAction(txtProjectCode1, txtProjectName1, txtProjectDescription1, btnAddUpdateProject1, btnClearProject1);
        refreshTableProject1();
    }

    @FXML
    private void cmbGuageUnitAction(ActionEvent event) {
    }

    class Users extends RecursiveTreeObject<Users> {

        StringProperty one;
        StringProperty two;
        StringProperty three;
        StringProperty four;
        StringProperty five;

        public Users(String one, String two) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);

        }

        public Users(String one, String two, String three) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
            this.three = new SimpleStringProperty(three);
        }

        public Users(String one, String two, String three, String four) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
            this.three = new SimpleStringProperty(three);
            this.four = new SimpleStringProperty(four);
        }

        public Users(String one, String two, String three, String four, String five) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
            this.three = new SimpleStringProperty(three);
            this.four = new SimpleStringProperty(four);
            this.five = new SimpleStringProperty(five);
        }

    }

    class ManufacturerData extends RecursiveTreeObject<ManufacturerData> {

        StringProperty code;
        StringProperty name;
        StringProperty description;

        public ManufacturerData(String one, String two) {
            this.code = new SimpleStringProperty(one);
            this.name = new SimpleStringProperty(two);
//            this.description = new SimpleStringProperty(three);
        }

    }

    class ProjectData extends RecursiveTreeObject<ProjectData> {

        StringProperty code;
        StringProperty name;
        StringProperty description;

        public ProjectData(String one, String two) {
            this.code = new SimpleStringProperty(one);
            this.name = new SimpleStringProperty(two);
//            this.description = new SimpleStringProperty(three);
        }

    }

    public void messageGuage(String message, String Color_Code) {
        txtMessageGuage.setText(message);
        BoxMessageGauge.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        btnCloseMessageGauge.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        txtMessageGuage.setVisible(true);
        BoxMessageGauge.setVisible(true);
        btnCloseMessageGauge.setVisible(true);
    }

    public void messageUser(String message, String Color_Code) {
        txtMessage.setText(message);
        BoxMessage.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        btnCloseMessage.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        txtMessage.setVisible(true);
        BoxMessage.setVisible(true);
        btnCloseMessage.setVisible(true);
    }

    //VALIDATION CHECK
    private void add_listener_to_textfield(JFXTextField textField, Text text) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            check_empty_fields(text, newValue);
        });
    }

    private void check_empty_fields(Text field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setVisible(true);
            } else {
                field.setVisible(false);
            }
        } else {
            field.setVisible(true);
        }
    }

}
