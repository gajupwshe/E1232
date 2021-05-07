/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.RED;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Gajanan
 */
public class InitialScreenController implements Initializable {

    @FXML
    private HBox sectionHeader1;
    @FXML
    private Text txtMode;
    @FXML
    private ImageView imgEmergency;
    @FXML
    private Text txtOffline;
    @FXML
    private ImageView imgAuto;
    @FXML
    private ImageView imgManual;
    @FXML
    private Text txtDate;
    @FXML
    private HBox sectionHeader;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnInitial;
    @FXML
    private JFXButton btnTestScreen;
    @FXML
    private JFXButton btnGaugeCal;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnSystemCheck;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXComboBox<String> cmbTestType;
    @FXML
    private JFXComboBox<String> cmbLeakageType;
    @FXML
    private JFXComboBox<String> cmbValveType;
    @FXML
    private JFXComboBox<String> cmbValveClass;
    @FXML
    private JFXComboBox<String> cmbValveSize;
    @FXML
    private JFXComboBox<String> cmbTypeOfSealing;
    @FXML
    private JFXComboBox<String> cmbTestStandards;
    @FXML
    private JFXComboBox<String> cmbValveStandards;
    @FXML
    private JFXTextField txtStabilizationTime;
    @FXML
    private JFXTextField txtHoldingTime;
    @FXML
    private JFXTextField txtDrainDelay;
    @FXML
    private JFXTextField txtDrainTime;
    @FXML
    private JFXTextField txtHydroSetPressure;
    @FXML
    private JFXTextField txtHydraulicSetPressure;
    @FXML
    private JFXRadioButton radioBar;
    @FXML
    private JFXRadioButton radioPsi;
    @FXML
    private JFXRadioButton radioKgcm;
    @FXML
    private JFXRadioButton radioPascal;
    @FXML
    private JFXRadioButton radioKpa;
    @FXML
    private JFXRadioButton radioMpa;
    @FXML
    private JFXTextField txtAllowableLeakage;
    @FXML
    private Text txtActualUnit;
    @FXML
    private Text txtWrongSelection;
    @FXML
    private JFXTextField txtValveSrNo;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private JFXTextField txtGADrawNo;
    @FXML
    private JFXTextField txtEndType;
    @FXML
    private JFXTextField txtModelNo;
    @FXML
    private JFXTextField txtMaterialGrade;
    @FXML
    private JFXTextField txtQtyTest;
    @FXML
    private JFXTextField txtCustomer;
    @FXML
    private JFXTextField txtPORef;
    @FXML
    private JFXTextField txtBOM;
    @FXML
    private JFXTextField txtAssyProcedure;
    @FXML
    private JFXTextField txtTestProcedure;
    @FXML
    private JFXTextField txtWORef;
    @FXML
    private JFXTextField txtProcedureRef;
    @FXML
    private JFXTextField txtTCRef;
    @FXML
    private JFXTextField txtBodySR;
    @FXML
    private JFXTextField txtBodyHeat;
    @FXML
    private JFXTextField txtBodyMPI;
    @FXML
    private JFXTextField txtBodyRT;
    @FXML
    private JFXTextField txtBonnetSR;
    @FXML
    private JFXTextField txtBonnetHeat;
    @FXML
    private JFXTextField txtBonnetMPI;
    @FXML
    private JFXTextField txtBonnetRT;
    @FXML
    private JFXTextField txtPlugSR;
    @FXML
    private JFXTextField txtPlugHeat;
    @FXML
    private JFXTextField txtPlugMPI;
    @FXML
    private JFXTextField txtPlugRT;
    @FXML
    private JFXTextField txtSlipSR;
    @FXML
    private JFXTextField txtSlipHeat;
    @FXML
    private JFXTextField txtSlipMPI;
    @FXML
    private JFXTextField txtSlipRT;
    @FXML
    private JFXTextField txtTrunnionSR;
    @FXML
    private JFXTextField txtTrunnionHeat;
    @FXML
    private JFXTextField txtTrunnionMPI;
    @FXML
    private JFXTextField txtTrunnionRT;
    @FXML
    private JFXTextField txtGlandSR;
    @FXML
    private JFXTextField txtGlandHeat;
    @FXML
    private JFXTextField txtGlandMPI;
    @FXML
    private JFXTextField txtGlandRT;
    @FXML
    private JFXComboBox<String> comboMake;
    @FXML
    private JFXComboBox<String> comboGaugeId;
    private JFXComboBox<String> comboRange;
    private JFXComboBox<String> comboDOCal;
    private JFXComboBox<String> comboValidupto;
    @FXML
    private JFXComboBox<String> comboCalStatus;
    @FXML
    private JFXComboBox<String> comboRemark;
    @FXML
    private HBox sectionFooter;
    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();
    public static Stage catStage;
    int leak_no, cust_flag = 1, test_no = 1;
    Thread machine_mode;
    Boolean stop_insert = false, stop_mode = false, stop_pressure_get = true;
    String hydraulic, hydro, drain, stabi, hold, mac_mode, off_mode, current_offline_mode, current_machine_mode, max, green, invalid, pu;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    private JFXTextField txtPressureAsPer;
    @FXML
    private JFXTextField txtMake;
    @FXML
    private JFXTextField txtRange;
    @FXML
    private JFXTextField txtCalibration;
    @FXML
    private JFXTextField txtValid;
    @FXML
    private Text txtGaugeError;
    @FXML
    private JFXTextField txtRemarks;
    @FXML
    private JFXTextField txtproDuct;
    @FXML
    private JFXToggleButton toggleAside;
    @FXML
    private JFXToggleButton toggleBside;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            date_time();
            Background_Processes.insert_plc_data("python E:\\E1232\\python_plc\\insert_init_tags.py", false, true);
            machine_mode();
            comboMake.setVisible(false);
            txtGaugeError.setVisible(false);
            comboRemark.setVisible(false);

            //UI
            txtAllowableLeakage.setVisible(false);
            txtWrongSelection.setVisible(false);
            txtDrainDelay.setVisible(false);
            radioMpa.setVisible(false);
            radioPascal.setVisible(false);
            radioKpa.setVisible(false);
            radioBar.setToggleGroup(group);
            radioBar.setSelected(true);
            radioPsi.setToggleGroup(group);
            radioKgcm.setSelected(true);
            radioKgcm.setToggleGroup(group);
            radioKpa.setToggleGroup(group);
            radioMpa.setToggleGroup(group);
            radioPascal.setToggleGroup(group);

            if (Session.get("user_type").equals("admin")) {
                btnAdmin.setVisible(true);
                Session.set("catAccess", "granted");
            } else {
                Session.set("catAccess", "not");
                btnAdmin.setVisible(false);
            }

            txtRange.setEditable(false);
            txtCalibration.setEditable(false);
            txtValid.setEditable(false);

            InitialDataLoad();
        } catch (SQLException ex) {
            Logger.getLogger(InitialScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void date_time() {
        time.scheduleAtFixedRate(date, 0, 1000);
    }

    Timer time = new Timer();

    TimerTask date = new TimerTask() {
        @Override
        public void run() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            txtDate.setText("Date:" + dtf.format(now));

        }
    };

    private void machine_mode() {
//        String display = "SELECT machine_mode,offline_online FROM init_tags ORDER BY ID DESC LIMIT 1";
        machine_mode = new Thread(() -> {
//            String display = "SELECT * FROM test_tags ORDER BY test_tags_id DESC LIMIT 1";
            String display = "SELECT * FROM initialinitmain ORDER BY id DESC LIMIT 1";
            ResultSet rs;
            while (true) {
                try {

                    long start = System.currentTimeMillis();
//  
                    try {

                        machine_mode.sleep(150);
                    } catch (InterruptedException intex) {
                        intex.printStackTrace();
                        System.err.println("Interupt in mode thread : " + intex);
                    }
                    //Sleeping thread for 250 miliseconds: End

                    if (stop_mode) {
                        break;
                    }
//                    System.out.println("This is select  query  : " + display);

                    rs = dh.getData(display, connect);
                    if (rs.next()) {
                        //Storing Value's of Machine Parameters: Start
                        if (ToolKit.isNull(rs.getString("machine_mode"))) {
                            System.out.println("NULL machine_mode");
                        } else {

//                                mac_mode=rs.getString("ma")
                            mac_mode = rs.getString("machine_mode");
                        }
                        if (ToolKit.isNull(rs.getString("offline_online"))) {
                            System.out.println("NULL offline_online");
                        } else {
                            off_mode = rs.getString("offline_online");
                        }
                        max = rs.getString("max_gauge");
                        green = rs.getString("green_gauge");
                        invalid = rs.getString("invalid");
//                       
//                        System.out.println(clampingActual + ": " + hydroA + ": " + hydroB);
                        //Updating Gauge's Valu: End
                        try {
                            try {
                                if (cust_flag == 1) {
//                                    if (ToolKit.isNull(rs.getString("hydraulic_pressure_set"))) {
//                                        System.out.println("NULL hydraulic_pressure_set");
//                                    } else {
                                    hydraulic = rs.getString("hydraulic_pressure_set");
                                    hydro = rs.getString("hydro_pressure_set");
                                    drain = rs.getString("drain_timer_set");
                                    stabi = rs.getString("stabilization_timer_set");
                                    hold = rs.getString("holding_timer_set");
                                    Platform.runLater(() -> {
                                        txtHydraulicSetPressure.setText(Float.toString(Math.round(Float.parseFloat(hydraulic))));
                                    });
                                    Platform.runLater(() -> {
                                        txtHydroSetPressure.setText(Float.toString(Math.round(Float.parseFloat(hydro))));
                                    });

                                    Platform.runLater(() -> {
                                        txtHoldingTime.setText(hold);
                                    });
                                    Platform.runLater(() -> {
                                        txtDrainTime.setText(drain);
                                    });
                                    Platform.runLater(() -> {
                                        txtStabilizationTime.setText(stabi);
                                    });

//                                    }
                                } else {

                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();

                                System.out.println("EXCEPTION IN UPDATE OVERALL TIME DATA_UPDATE_THREAD : " + e.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            try {
                                //Updating Off_Online Mode Value: Start
                                if (off_mode.equals(current_offline_mode)) {
                                } else {
                                    offline_online(off_mode);
                                }
                                //Updating Off_Online Mode Value: End
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION IN Updating Off_Online Mode DATA_UPDATE_THREAD : " + e.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //break loop and stoping process: Start
                        if (stop_mode) {
                            break;
                        }
                        //break loop and stoping process: Start

                        try {
                            try {
                                //Updating Operating Mode Value: Start
                                if (mac_mode.equals(current_machine_mode)) {
                                } else {
                                    mode(mac_mode);
                                }
                                //Updating Operating Mode Value: End
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION IN Updating Operating Mode DATA_UPDATE_THREAD : " + e.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    long stop = System.currentTimeMillis();
                    long res = stop - start;
//                    System.out.println("mode offline pop cycle combine time : " + res);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Exception In Mode Thread : " + e);
                    try {
                        machine_mode.sleep(250);
                    } catch (InterruptedException intex) {
                        System.err.println("Interupt in mode thread : " + intex);
                    }
                }
                if (stop_mode) {
                    machine_mode.stop();
                    break;
                }
            }
        });
        machine_mode.start();

    }

    private void offline_online(String off_on) {
        switch (off_on) {
            case "0":
                Platform.runLater(() -> {
                    txtOffline.setText("Online");
                });
                current_offline_mode = "0";
                break;
            case "1":
                Platform.runLater(() -> {
                    txtOffline.setText("Offline-1");
                });
                current_offline_mode = "1";
                break;
            case "2":
                Platform.runLater(() -> {
                    txtOffline.setText("Offline-2");
                });
                current_offline_mode = "1";
                break;
            default:
                Platform.runLater(() -> {
                    txtOffline.setText("Something went wrong");
                });
                current_offline_mode = "0";
                break;
        }
    }

    private void mode(String mode) {
        switch (mode) {
            case "0":
                txtMode.setText("Emergency Mode");
                current_machine_mode = "0";
                txtDate.setFill(Color.web("#C32420"));
                break;
            case "1":
                txtMode.setText("PT Error");
                current_machine_mode = "1";
                txtDate.setFill(Color.web("#C32420"));
                break;
            case "2":
                txtMode.setText("Manual Mode");
                current_machine_mode = "2";
                txtDate.setFill(Color.web("#0099FF"));
                break;
            case "3":
                txtMode.setText("Auto Mode");
                current_machine_mode = "3";
                txtDate.setFill(Color.web("#0099FF"));
                break;
            default:
                txtMode.setText("Something wrong");
                current_machine_mode = "4";
                txtDate.setFill(Color.web("#C32420"));
                break;
        }
    }

    private void InitialDataLoad() throws SQLException {
        //test type 
        String query = "SELECT test_type,write_value FROM test_type GROUP BY write_value";
        cmbTestType.getSelectionModel().clearSelection();
        cmbTestType.getItems().clear();
        try {
            int count = 0;
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {

                cmbTestType.getItems().add(count, rs.getString("test_type"));
                count++;
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        String valve_s = "";
        String q2 = "SELECT valve_standards FROM Initial_init ORDER BY ID DESC LIMIT 1;";
        ResultSet rs0;

        rs0 = dh.getData(q2, connect);
        if (rs0.next()) {
            String q1 = "SELECT valve_standards FROM valve_standards WHERE write_value = " + rs0.getString("valve_standards") + ";";
            ResultSet rs1 = dh.getData(q1, connect);
            if (rs1.next()) {
                valve_s = rs1.getString("valve_standards");
            }
        }

        Initial_Dropdowns_Data();
        //VALVE CLASS SIZE
        valve_class_size(valve_s);

        //if previous data exist
        //setting data
        String query1 = "SELECT * FROM Initial_init ORDER BY ID DESC LIMIT 1;";

        try {
            ResultSet rs1 = dh.getData(query1, connect);

            while (rs1.next()) {
                cmbTestType.getSelectionModel().select(Integer.parseInt(rs1.getString("test_type")));
                System.out.println("--------------!!!!!!--------" + rs1.getString("leakage_type"));
                cmbLeakageType.getSelectionModel().select(Integer.parseInt(rs1.getString("leakage_type")));
                leak_no = Integer.parseInt(rs1.getString("leakage_type"));
                String vc_query = "SELECT valve_class FROM valve_class WHERE write_value = '" + rs1.getString("valve_class") + "';";

                ResultSet vc_rs = dh.getData(vc_query, connect);
                if (vc_rs.next()) {
                    cmbValveClass.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_class")));
                }
                String vs_query = "SELECT valve_size FROM valve_size WHERE write_value = '" + rs1.getString("valve_size") + "';";
                ResultSet vs_rs = dh.getData(vs_query, connect);
                if (vs_rs.next()) {
                    cmbValveSize.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_size")));
                }
//                cmbValveClass.getSelectionModel().select(Integer.parseInt(rs.getString("valve_class")));
//                cmbValveSize.getSelectionModel().select(Integer.parseInt(rs.getString("valve_size")));
                cmbValveType.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_type")));
                cmbTypeOfSealing.getSelectionModel().select(Integer.parseInt(rs1.getString("type_of_sealing")));
                cmbTestStandards.getSelectionModel().select(Integer.parseInt(rs1.getString("test_standards")));

                if (rs1.getString("test_standards").equals("1")) {
                    cust_flag = 0;
                    txtHydroSetPressure.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtStabilizationTime.setEditable(true);
                    txtHoldingTime.setEditable(true);
                    txtDrainTime.setEditable(true);
                } else {
                    txtHydroSetPressure.setEditable(false);
                    txtHydraulicSetPressure.setEditable(false);
                    txtStabilizationTime.setEditable(false);
                    txtHoldingTime.setEditable(false);
                    txtDrainTime.setEditable(false);
                }
                cmbValveStandards.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_standards")));
                txtStabilizationTime.setText(rs1.getString("stabilization_time"));
                txtHoldingTime.setText(rs1.getString("holding_time"));
//                txtDrainDelay.setText(rs.getString("drain_delay"));
                txtDrainTime.setText(rs1.getString("drain_time"));
                txtHydroSetPressure.setText(rs1.getString("hydro_set_pressure"));
                txtHydraulicSetPressure.setText(rs1.getString("hydraulic_set_pressure"));

                bar_psi_kg(rs1.getString("bar_psi_kgcm"));

            }
            leakage_type();
            try {

                if (cmbTestType.getSelectionModel().getSelectedItem().equals("PNEUMATIC SEAT A SIDE") || cmbTestType.getSelectionModel().getSelectedItem().equals("PNEUMATIC SEAT B SIDE")) {
                    Gauge_details("Air Guage");
                } else {
                    Gauge_details(cmbValveClass.getSelectionModel().getSelectedItem());
                }
            } catch (Exception e) {
            }
            //display value is checked for set automatic data when comming to initial screen from test screen
            try {
                if (Session.get("display_valve_data").equals("1")) {
                    String q = "SELECT * FROM valve_data ORDER BY id DESC LIMIT 1";
                    ResultSet rs_1 = null;
                    try {
                        rs1 = dh.getData(q, connect);
                        if (rs1.next()) {

                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(InitialScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (Exception e) {
            }

            ResultSet rs_data = dh.getData("SELECT * FROM valve_data ORDER BY id DESC LIMIT 1", connect);
            if (rs_data.next()) {

                txtproDuct.setText(rs_data.getString("productDetial"));
                txtValveSrNo.setText(rs_data.getString("vsn"));
                txtQty.setText(rs_data.getString("qty"));
                txtGADrawNo.setText(rs_data.getString("gaDrawingNo"));
                txtEndType.setText(rs_data.getString("endType"));
                txtModelNo.setText(rs_data.getString("modelNo"));
                txtMaterialGrade.setText(rs_data.getString("materialGrade"));
                txtQtyTest.setText(rs_data.getString("qtyTested"));
                txtCustomer.setText(rs_data.getString("customer"));
                txtPORef.setText(rs_data.getString("poRef"));
                txtBOM.setText(rs_data.getString("bom"));
                txtAssyProcedure.setText(rs_data.getString("assyProcedure"));
                txtTestProcedure.setText(rs_data.getString("testingProcedure"));
                txtWORef.setText(rs_data.getString("woRef"));
                txtProcedureRef.setText(rs_data.getString("productRef"));
                txtTCRef.setText(rs_data.getString("tcRef"));

                txtBodySR.setText(rs_data.getString("bodySlNo"));
                txtBodyHeat.setText(rs_data.getString("bodyHeatNo"));
                txtBodyMPI.setText(rs_data.getString("bodyMipNo"));
                txtBodyRT.setText(rs_data.getString("bodyRtNo"));

                txtBonnetHeat.setText(rs_data.getString("bonnetHeatNo"));
                txtBonnetMPI.setText(rs_data.getString("bonnetMipNo"));
                txtBonnetRT.setText(rs_data.getString("bonnetRtNo"));
                txtBonnetSR.setText(rs_data.getString("bonnetSlNo"));

                txtPlugHeat.setText(rs_data.getString("plugHeatNo"));
                txtPlugMPI.setText(rs_data.getString("plugMpiNo"));
                txtPlugRT.setText(rs_data.getString("plugRtNo"));
                txtPlugSR.setText(rs_data.getString("plugSlNo"));

                txtSlipHeat.setText(rs_data.getString("slipSlno"));
                txtSlipMPI.setText(rs_data.getString("slipMpino"));
                txtSlipRT.setText(rs_data.getString("slipRtNo"));
                txtSlipSR.setText(rs_data.getString("slipHeatNo"));

                txtTrunnionHeat.setText(rs_data.getString("trunnionSlno"));
                txtTrunnionMPI.setText(rs_data.getString("trunnionMpiNo"));
                txtTrunnionRT.setText(rs_data.getString("trunnionRtNo"));
                txtTrunnionSR.setText(rs_data.getString("trunnionHeatNo"));

                txtGlandHeat.setText(rs_data.getString("glandSlNo"));
                txtGlandMPI.setText(rs_data.getString("glandHeatNo"));
                txtGlandRT.setText(rs_data.getString("glandMpiNo"));
                txtGlandSR.setText(rs_data.getString("glandRtNo"));

                txtPressureAsPer.setText(rs_data.getString("pressureAsPer"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void Initial_Dropdowns_Data() {
        String q = "SELECT vt.valve_type,vt.write_value AS type_write,tos.type_of_sealing,tos.write_value AS sealing_write,ts.test_standards,ts.write_value AS standard_write,vst.valve_standards,vst.write_value AS valve_standard_write FROM valve_type vt LEFT JOIN type_of_sealing tos ON tos.type_of_sealing_id = vt.valve_type_id LEFT JOIN test_standards ts ON ts.test_standards_id = vt.valve_type_id LEFT JOIN valve_standards vst ON vst.valve_standards_id = vt.valve_type_id;";
        try {
            ResultSet rs = dh.getData(q, connect);
            while (rs.next()) {
                if (rs.getString("valve_type") == null || rs.getString("valve_type").equals("")) {
                } else {
                    cmbValveType.getItems().add(rs.getString("valve_type"));
                }
                if (rs.getString("type_of_sealing") == null || rs.getString("type_of_sealing").equals("")) {
                } else {
                    cmbTypeOfSealing.getItems().add(Integer.parseInt(rs.getString("sealing_write")), rs.getString("type_of_sealing"));
                }
                if (rs.getString("test_standards") == null || rs.getString("test_standards").equals("")) {
                } else {
                    cmbTestStandards.getItems().add(Integer.parseInt(rs.getString("standard_write")), rs.getString("test_standards"));
                }
                if (rs.getString("valve_standards") == null || rs.getString("valve_standards").equals("")) {
                } else {
                    cmbValveStandards.getItems().add(Integer.parseInt(rs.getString("valve_standard_write")), rs.getString("valve_standards"));
                }
            }

            ResultSet rs_gd = dh.getData("SELECT * FROM `gauge_data`", connect);
            comboGaugeId.getItems().clear();
            while (rs_gd.next()) {
                comboGaugeId.getItems().add(rs_gd.getString("serial"));
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void valve_class_size(String Valve_Standard) throws SQLException {
        cmbValveClass.getSelectionModel().clearSelection();
        cmbValveSize.getSelectionModel().clearSelection();
        cmbValveClass.getItems().clear();
        cmbValveSize.getItems().clear();
        //Valve_class
        String vc = "SELECT vc.valve_class FROM valve_class vc WHERE vc.valve_standards = '" + Valve_Standard + "';";
        System.out.println(vc);
        ResultSet rs_vc = dh.getData(vc, connect);
        while (rs_vc.next()) {
            if (rs_vc.getString("valve_class") == null || rs_vc.getString("valve_class").equals("")) {
            } else {
                cmbValveClass.getItems().add(rs_vc.getString("valve_class"));
            }
        }
        //Valve_size
        String vs = "SELECT vs.valve_size FROM valve_size vs WHERE vs.valve_standards = '" + Valve_Standard + "';";
        System.out.println(vs);
        ResultSet rs_vs = dh.getData(vs, connect);
        while (rs_vs.next()) {
            if (rs_vs.getString("valve_size") == null || rs_vs.getString("valve_size").equals("")) {
            } else {

                cmbValveSize.getItems().add(rs_vs.getString("valve_size"));

            }
        }
    }

    private void bar_psi_kg(String bar_psi_kg) {
        switch (bar_psi_kg) {
            case "0":
                radioBar.setSelected(true);
                pu = "bar";
                setPressureUnit("bar");
                break;
            case "1":
                radioPsi.setSelected(true);
                pu = "psi";
                setPressureUnit("psi");
                break;
            case "2":
                radioKgcm.setSelected(true);
                pu = "kg/sqcm";
                setPressureUnit("kg/sqcm");
                break;
            case "3":

                radioKpa.setSelected(true);
                pu = "KPa";
                setPressureUnit("kg/sqcm");
                break;
            case "4":
                radioMpa.setSelected(true);
                pu = "MPa";
                setPressureUnit("kg/sqcm");
                break;
            case "5":
                radioPascal.setSelected(true);
                pu = "Pascal";
                setPressureUnit("kg/sqcm");
                break;
            default:
                radioBar.setSelected(true);
                pu = "bar";
                break;
        }
    }

    private void setPressureUnit(String unit) {
        txtHydroSetPressure.setPromptText("Hydro Set Pressure(" + unit + ")");
        txtHydraulicSetPressure.setPromptText("Hydraulic Set Pressure(" + unit + ")");
    }

    @FXML
    private void btnHomeAction(ActionEvent event) {
        Background_Processes.stop_plc_read();
        machine_mode.stop();

        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnHome);
    }

    @FXML
    private void btnTestScreenAction(ActionEvent event) throws SQLException, ParseException {
        if (invalid.equals("0")) {
            String vsn = txtValveSrNo.getText();
            String tt = cmbTestType.getSelectionModel().getSelectedItem();
            String lt = cmbLeakageType.getSelectionModel().getSelectedItem();
            String vt = cmbValveType.getSelectionModel().getSelectedItem();
            String vc = cmbValveClass.getSelectionModel().getSelectedItem();
            String vs = cmbValveSize.getSelectionModel().getSelectedItem();
            String tos = cmbTypeOfSealing.getSelectionModel().getSelectedItem();
            String ts = cmbTestStandards.getSelectionModel().getSelectedItem();
            String st = txtStabilizationTime.getText();
            String ht = txtHoldingTime.getText();
            String vstd = cmbValveStandards.getSelectionModel().getSelectedItem();
//                String dd = txtDrainDelay.getText();
            String dt = txtDrainTime.getText();
            String hsp = txtHydroSetPressure.getText();
            String qty = txtQty.getText();
            String gaDrawingNo = txtGADrawNo.getText();
            String endType = txtEndType.getText();
            String modelNo = txtModelNo.getText();
            String materialGrade = txtMaterialGrade.getText();
            String qtyTested = txtQtyTest.getText();
            String customer = txtCustomer.getText();
            String poRef = txtPORef.getText();
            String bom = txtBOM.getText();
            String assyProcedure = txtAssyProcedure.getText();
            String testingProcedure = txtTestProcedure.getText();
            String woRef = txtWORef.getText();
            String productRef = txtProcedureRef.getText();
            String tcRef = txtTCRef.getText();
            String bodySlNo = txtBodySR.getText();
            String bodyHeatNo = txtBodyHeat.getText();
            String bodyMipNo = txtBonnetMPI.getText();
            String bodyRtNo = txtBodyRT.getText();
            String bonnetSlNo = txtBonnetSR.getText();
            String bonnetHeatNo = txtBonnetHeat.getText();
            String bonnetMipNo = txtBonnetMPI.getText();
            String bonnetRtNo = txtBonnetRT.getText();
            String plugSlNo = txtPlugSR.getText();
            String plugHeatNo = txtPlugHeat.getText();
            String plugMpiNo = txtPlugMPI.getText();
            String plugRtNo = txtPlugRT.getText();
            String slipSlno = txtSlipSR.getText();
            String slipHeatNo = txtSlipHeat.getText();
            String slipMpino = txtSlipMPI.getText();
            String slipRtNo = txtSlipRT.getText();
            String trunnionSlno = txtTrunnionSR.getText();
            String trunnionHeatNo = txtTrunnionHeat.getText();
            String trunnionMpiNo = txtTrunnionMPI.getText();
            String trunnionRtNo = txtTrunnionRT.getText();
            String glandSlNo = txtGlandSR.getText();
            String glandHeatNo = txtGlandHeat.getText();
            String glandMpiNo = txtGlandMPI.getText();
            String glandRtNo = txtGlandRT.getText();
            String digiPressMake = txtMake.getText();
            String digiPressGaugeId = comboGaugeId.getSelectionModel().getSelectedItem();
            String digiPressRange = txtRange.getText();
            String digiPressCalibration = txtCalibration.getText();
            String digiPressDue = txtValid.getText();
            String digiPressCalibrationStatus = comboCalStatus.getSelectionModel().getSelectedItem();
            String digiPressRemarks = txtRemarks.getText();//comboRemark.getSelectionModel().getSelectedItem();
            String allow = txtAllowableLeakage.getText();
            String PressureAsPer = txtPressureAsPer.getText();
            if (ToolKit.isNull(vsn)
                    || ToolKit.isNull(PressureAsPer)
                    || ToolKit.isNull(lt)
                    || ToolKit.isNull(digiPressGaugeId)) {
                Dialog.showForSometime("", "Please provide appropriate data", "Alert", 450, 10);
                check_text_empty_fields(txtValveSrNo, vsn);
                check_text_empty_fields(txtPressureAsPer, vsn);
                check_combo_empty_fields(cmbLeakageType, lt);
                check_combo_empty_fields(comboGaugeId, digiPressGaugeId);
            } else {

                if (tt.equals("CAVITY")) {
                    Session.set("vsn", vsn);
                    Session.set("tt", tt);
                    Session.set("vt", vt);
                    Session.set("vc", vc);
                    Session.set("vs", vs);
                    Session.set("lt", lt);
                    Session.set("tos", tos);
                    Session.set("green", green);
                    Session.set("max", max);
                    Session.set("pu", pu);
                    Session.set("hsp", txtHydroSetPressure.getText());
                    Session.set("hlsp", txtHydraulicSetPressure.getText());
                    Session.set("gaugeSlNo",digiPressGaugeId);

                    ResultSet rs = dh.getData("SELECT test_no FROM valve_data ORDER BY id DESC LIMIT 1", connect);
                    if (rs.next()) {
                        test_no = Integer.parseInt(rs.getString("test_no"));
                        test_no++;
                    } else {
                        test_no = 1;
                    }

                    String insert_data = "INSERT INTO `valve_data`(`test_no`, `test_type`, `valve_standards`, `hydro_set_pressure`, `holding_set`, `allowable_leakage`, `vsn`, `qty`, `gaDrawingNo`, `endType`, `modelNo`, `materialGrade`, `qtyTested`, `customer`, `poRef`, `bom`, `assyProcedure`, `testingProcedure`, `woRef`, `productRef`, `tcRef`, `bodySlNo`, `bodyHeatNo`, `bodyMipNo`, `bodyRtNo`, `bonnetSlNo`, `bonnetHeatNo`, `bonnetMipNo`, `bonnetRtNo`, `plugSlNo`, `plugHeatNo`, `plugMpiNo`, `plugRtNo`, `slipSlno`, `slipHeatNo`, `slipMpino`, `slipRtNo`, `trunnionSlno`, `trunnionHeatNo`, `trunnionMpiNo`, `trunnionRtNo`, `glandSlNo`, `glandHeatNo`, `glandMpiNo`, `glandRtNo`, `digiPressMake`, `digiPressGaugeId`, `digiPressRange`, `digiPressCalibration`, `digiPressDue`, `digiPressCalibrationStatus`, `digiPressRemarks`, `date`,`pressureAsPer`,`productDetial`) VALUES ('" + test_no + "','" + tt + "','" + vstd + "','" + hsp + "','" + ht + "','" + allow + "','" + vsn + "','" + qty + "','" + gaDrawingNo + "', '" + endType + "', '" + modelNo + "', '" + materialGrade + "', '" + qtyTested + "', '" + customer + "', '" + poRef + "', '" + bom + "', '" + assyProcedure + "', '" + testingProcedure + "', '" + woRef + "', '" + productRef + "', '" + tcRef + "', '" + bodySlNo + "', '" + bodyHeatNo + "', '" + bodyMipNo + "', '" + bodyRtNo + "', '" + bonnetSlNo + "', '" + bonnetHeatNo + "', '" + bonnetMipNo + "', '" + bonnetRtNo + "', '" + plugSlNo + "', '" + plugHeatNo + "', '" + plugMpiNo + "', '" + plugRtNo + "', '" + slipSlno + "', '" + slipHeatNo + "', '" + slipMpino + "', '" + slipRtNo + "', '" + trunnionSlno + "', '" + trunnionHeatNo + "', '" + trunnionMpiNo + "', '" + trunnionRtNo + "', '" + glandSlNo + "', '" + glandHeatNo + "', '" + glandMpiNo + "', '" + glandRtNo + "', '" + digiPressMake + "', '" + digiPressGaugeId + "', '" + digiPressRange + "', '" + digiPressCalibration + "', '" + digiPressDue + "', '" + digiPressCalibrationStatus + "', '" + digiPressRemarks + "',NOW(),'" + PressureAsPer + "','" + txtproDuct.getText() + "')";

                    System.out.println("insert_data : " + insert_data);
                    dh.execute(insert_data, connect);
                    Background_Processes.stop_plc_read();
                    machine_mode.stop();
                    Background_Processes.Initialize_Initial_Screen();

                    Platform.runLater(() -> {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("CavityScreen.fxml"));
                            ToolKit.loadScreen(root);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    ToolKit.unloadScreen(btnHome);
                } else {

                    Session.set("vsn", vsn);
                    Session.set("tt", tt);
                    Session.set("vt", vt);
                    Session.set("vc", vc);
                    Session.set("vs", vs);
                    Session.set("lt", lt);
                    Session.set("tos", tos);
                    Session.set("green", green);
                    Session.set("max", max);
                    Session.set("pu", pu);
                    Session.set("hsp", txtHydroSetPressure.getText());
                    Session.set("hlsp", txtHydraulicSetPressure.getText());
                    Session.set("gaugeSlNo",digiPressGaugeId);

                    ResultSet rs = dh.getData("SELECT test_no FROM valve_data ORDER BY id DESC LIMIT 1", connect);
                    if (rs.next()) {
                        test_no = Integer.parseInt(rs.getString("test_no"));
                        test_no++;
                    } else {
                        test_no = 1;
                    }

                    String insert_data = "INSERT INTO `valve_data`(`test_no`, `test_type`, `valve_standards`, `hydro_set_pressure`, `holding_set`, `allowable_leakage`, `vsn`, `qty`, `gaDrawingNo`, `endType`, `modelNo`, `materialGrade`, `qtyTested`, `customer`, `poRef`, `bom`, `assyProcedure`, `testingProcedure`, `woRef`, `productRef`, `tcRef`, `bodySlNo`, `bodyHeatNo`, `bodyMipNo`, `bodyRtNo`, `bonnetSlNo`, `bonnetHeatNo`, `bonnetMipNo`, `bonnetRtNo`, `plugSlNo`, `plugHeatNo`, `plugMpiNo`, `plugRtNo`, `slipSlno`, `slipHeatNo`, `slipMpino`, `slipRtNo`, `trunnionSlno`, `trunnionHeatNo`, `trunnionMpiNo`, `trunnionRtNo`, `glandSlNo`, `glandHeatNo`, `glandMpiNo`, `glandRtNo`, `digiPressMake`, `digiPressGaugeId`, `digiPressRange`, `digiPressCalibration`, `digiPressDue`, `digiPressCalibrationStatus`, `digiPressRemarks`, `date`,`pressureAsPer`,`productDetial`) VALUES ('" + test_no + "','" + tt + "','" + vstd + "','" + hsp + "','" + ht + "','" + allow + "','" + vsn + "','" + qty + "','" + gaDrawingNo + "', '" + endType + "', '" + modelNo + "', '" + materialGrade + "', '" + qtyTested + "', '" + customer + "', '" + poRef + "', '" + bom + "', '" + assyProcedure + "', '" + testingProcedure + "', '" + woRef + "', '" + productRef + "', '" + tcRef + "', '" + bodySlNo + "', '" + bodyHeatNo + "', '" + bodyMipNo + "', '" + bodyRtNo + "', '" + bonnetSlNo + "', '" + bonnetHeatNo + "', '" + bonnetMipNo + "', '" + bonnetRtNo + "', '" + plugSlNo + "', '" + plugHeatNo + "', '" + plugMpiNo + "', '" + plugRtNo + "', '" + slipSlno + "', '" + slipHeatNo + "', '" + slipMpino + "', '" + slipRtNo + "', '" + trunnionSlno + "', '" + trunnionHeatNo + "', '" + trunnionMpiNo + "', '" + trunnionRtNo + "', '" + glandSlNo + "', '" + glandHeatNo + "', '" + glandMpiNo + "', '" + glandRtNo + "', '" + digiPressMake + "', '" + digiPressGaugeId + "', '" + digiPressRange + "', '" + digiPressCalibration + "', '" + digiPressDue + "', '" + digiPressCalibrationStatus + "', '" + digiPressRemarks + "',NOW(),'" + PressureAsPer + "','" + txtproDuct.getText() + "')";

                    System.out.println("insert_data : " + insert_data);
                    dh.execute(insert_data, connect);
                    Background_Processes.stop_plc_read();
                    machine_mode.stop();
                    Background_Processes.Initialize_Initial_Screen();

                    Platform.runLater(() -> {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                            ToolKit.loadScreen(root);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    ToolKit.unloadScreen(btnHome);
                }
            }
        } else {
            Dialog.showAndWait("Invalid Selection Please Refer Clamping Chart");
        }
    }

    private void check_text_empty_fields(JFXTextField field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setUnFocusColor(RED);
                field.setFocusColor(RED);
            } else {
//                field.setFocusColor(false);
            }
        } else {
            field.setUnFocusColor(RED);
            field.setFocusColor(RED);
        }
    }

    private void check_combo_empty_fields(JFXComboBox field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setUnFocusColor(RED);
                field.setFocusColor(RED);
            } else {
//                field.setVisible(false);
            }
        } else {
            field.setUnFocusColor(RED);
            field.setFocusColor(RED);
        }
    }

    @FXML
    private void btnGaugeCalAction(ActionEvent event) {
        Background_Processes.stop_plc_read();
        machine_mode.stop();
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("gaugecalibration.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnHome);
    }

    @FXML
    private void btnReportAction(ActionEvent event) {
        Background_Processes.stop_plc_read();
        machine_mode.stop();
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnHome);
    }

    @FXML
    private void btnSystemCheckAction(ActionEvent event) {
        Background_Processes.stop_plc_read();
        machine_mode.stop();
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnHome);
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
        Background_Processes.stop_plc_read();
        machine_mode.stop();
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnHome);
    }

    @FXML
    private void cmbTestTypeAction(ActionEvent event) throws SQLException {
        int index = cmbTestType.getSelectionModel().getSelectedIndex();
        System.out.println("UPDATE writedropdownplc set testType='" + index + "'");
        dh.execute("UPDATE writedropdownplc set testType='" + index + "'", connect);
        leakage_type();
    }

    private void leakage_type() {
        String tst_type = cmbTestType.getSelectionModel().getSelectedItem();
        cmbLeakageType.getSelectionModel().clearSelection();
        cmbLeakageType.getItems().clear();
//        txtActualUnit.setVisible(false);
//        txtAllowableLeakage.setVisible(false);
        String q = "SELECT series,leakage_type,write_value_test_type FROM leakage_type WHERE test_type = '" + tst_type + "' GROUP BY series;";
        System.out.println("q: " + q);
        try {
            ResultSet rs = dh.getData(q, connect);
            String write_value = "0";
            while (rs.next()) {
                write_value = rs.getString("write_value_test_type");
                int index = Integer.parseInt(rs.getString("series"));
//                cmbLeakageType.getItems().add(index, rs.getString("leakage_type"));
                cmbLeakageType.getItems().add(rs.getString("leakage_type"));
            }
//            cmbLeakageType.getSelectionModel().select(leak_no);

            txtActualUnit.setText("");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void cmbLeakageTypeAction(ActionEvent event) {

    }

    @FXML
    private void cmbValveTypeAction(ActionEvent event) throws SQLException {
        int index = cmbValveType.getSelectionModel().getSelectedIndex();
        System.out.println("UPDATE writedropdownplc set valveType='" + index + "'");
        dh.execute("UPDATE writedropdownplc set valveType='" + index + "'", connect);
    }

    @FXML
    private void cmbValveClassAction(ActionEvent event) throws SQLException {
        int index = cmbValveClass.getSelectionModel().getSelectedIndex();

        dh.execute("UPDATE writedropdownplc set valveClass='" + index + "'", connect);

        Gauge_details(cmbValveClass.getSelectionModel().getSelectedItem());
    }

    public void Gauge_details(String description) {

    }

    @FXML
    private void cmbValveSizeAction(ActionEvent event) throws SQLException {
        int index = cmbValveSize.getSelectionModel().getSelectedIndex();

        dh.execute("UPDATE writedropdownplc set valveSize='" + index + "'", connect);
    }

    @FXML
    private void cmbTypeOfSealingAction(ActionEvent event) throws SQLException {
        int index = cmbTypeOfSealing.getSelectionModel().getSelectedIndex();
        dh.execute("UPDATE writedropdownplc set typeSealing='" + index + "'", connect);
    }

    @FXML
    private void cmbTestStandardsAction(ActionEvent event) throws SQLException {
        int index = cmbTestStandards.getSelectionModel().getSelectedIndex();
        dh.execute("UPDATE writedropdownplc set testStd='" + index + "'", connect);
        int index1 = cmbTestStandards.getSelectionModel().getSelectedIndex();
        if (cmbTestStandards.getSelectionModel().getSelectedItem().equals("Customized")) {
            try {
                if (Session.get("catAccess").equals("granted")) {
                    cust_flag = 0;
                    txtHydroSetPressure.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtStabilizationTime.setEditable(true);
                    txtHoldingTime.setEditable(true);
                    txtDrainTime.setEditable(true);
                    dh.execute("UPDATE writedropdownplc set testStd='" + index + "'", connect);
                    txtHoldingTime.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtHydroSetPressure.setEditable(true);
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("CategoryLogin.fxml"));
                    Platform.runLater(() -> {
                        cmbTestStandards.getSelectionModel().select(0);
                        catStage = new Stage(StageStyle.UNDECORATED);
                        catStage.setAlwaysOnTop(true);
                        Scene scene = new Scene(root, 600, 250);

                        catStage.setScene(scene);
                        catStage.show();
                    });
                    Thread catThread = new Thread() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    if (Session.get("catAccess").equals("granted")) {
                                        cust_flag = 0;
                                        txtHydroSetPressure.setEditable(true);
                                        txtHydraulicSetPressure.setEditable(true);
                                        txtStabilizationTime.setEditable(true);
                                        txtHoldingTime.setEditable(true);
                                        txtDrainTime.setEditable(true);
                                        System.out.println("granted");
                                        Platform.runLater(() -> {
                                            cmbTestStandards.getSelectionModel().select(1);
                                        });
                                        break;
                                    } else if (Session.get("catAccess").equals("not granted")) {
                                        System.out.println("not granted");
                                        Platform.runLater(() -> {
                                            cmbTestStandards.getSelectionModel().select(0);
                                        });
                                        break;
                                    } else {
                                        System.out.println("not");
                                        Thread.sleep(200);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    catThread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            cust_flag = 1;
            dh.execute("UPDATE writedropdownplc set testStd='" + index + "'", connect);
            txtHoldingTime.setEditable(false);
            txtHydraulicSetPressure.setEditable(false);
            txtHydroSetPressure.setEditable(false);
        }
    }

    @FXML
    private void cmbValveStandardsAction(ActionEvent event) {
        try {
            String valve_std = cmbValveStandards.getSelectionModel().getSelectedItem();
            String Query = "SELECT valve_class From valve_class WHERE valve_standards='" + valve_std + "' ORDER BY valve_class_id ASC";
            ResultSet rs_vstd = dh.getData(Query, connect);
            cmbValveClass.getItems().clear();

            while (rs_vstd.next()) {
                cmbValveClass.getItems().add(rs_vstd.getString("valve_class"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(InitialScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Valve_size
            String Valve_Standard = cmbValveStandards.getSelectionModel().getSelectedItem();
            String vs = "SELECT vs.valve_size FROM valve_size vs WHERE vs.valve_standards = '" + Valve_Standard + "';";
            System.out.println(vs);
            ResultSet rs_vs = dh.getData(vs, connect);
            cmbValveSize.getItems().clear();
            while (rs_vs.next()) {
                if (rs_vs.getString("valve_size") == null || rs_vs.getString("valve_size").equals("")) {
                } else {
                    cmbValveSize.getItems().add(rs_vs.getString("valve_size"));
                }
            }

//            int index = cmbValveStandards.getSelectionModel().getSelectedIndex();
            if (cmbValveStandards.getSelectionModel().getSelectedItem().equals("API-6A")) {
//                ToolKit.tagWrite("N7:7", "0");
                dh.execute("UPDATE writedropdownplc set valveStd='0'", connect);
//                Process child = Runtime.getRuntime().exec(py_insert_cmd);
//                child.waitFor();
            } else {
//                ToolKit.tagWrite("N7:7", "1");
                dh.execute("UPDATE writedropdownplc set valveStd='1'", connect);
//                Process child = Runtime.getRuntime().exec(py_insert_cmd);
//                child.waitFor();
            }

        } catch (SQLException ex) {
            Logger.getLogger(InitialScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtStabilizationTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String s_time1 = txtStabilizationTime.getText();
            String cmd = "python E:\\E1232\\python_plc\\write_plc_Dword.py 52 0 " + s_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtStabilizationTimeKeyRelease(KeyEvent event) {
    }

    @FXML
    private void txtHoldingTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String h_time1 = txtHoldingTime.getText();
            String cmd = "python E:\\E1232\\python_plc\\write_plc_Dword.py 56 0 " + h_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtHoldingTimeKeyRelease(KeyEvent event) {
    }

    @FXML
    private void txtDrainDelayAction(ActionEvent event) {
    }

    @FXML
    private void txtDrainDelayKeyRelease(KeyEvent event) {
    }

    @FXML
    private void txtDrainTimeAction(ActionEvent event) {

    }

    @FXML
    private void txtDrainTimeKeyRelease(KeyEvent event) {
    }

    @FXML
    private void txtHydroSetPressureAction(ActionEvent event) throws IOException, InterruptedException {
        String h_time1 = txtHydroSetPressure.getText();
        String cmd = "python E:\\E1232\\python_plc\\write_plc_real.py 44 0 " + h_time1;
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();

    }

    @FXML
    private void txtHydroPressureKeyRelease(KeyEvent event) {
    }

    @FXML
    private void txtHydraulicSetPressureAction(ActionEvent event) throws InterruptedException, IOException {
        String h_time1 = txtHydraulicSetPressure.getText();
        String cmd = "python E:\\E1232\\python_plc\\write_plc_real.py 40 0 " + h_time1;
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();
    }

    @FXML
    private void txtClampingPressureKeyRelease(KeyEvent event) {
    }

    @FXML
    private void radioBarAction(ActionEvent event) throws SQLException {
        pu = "bar";
        dh.execute("UPDATE writedropdownplc set unit='0'", connect);
    }

    @FXML
    private void radioPsiAction(ActionEvent event) throws SQLException {
        pu = "psi";
        dh.execute("UPDATE writedropdownplc set unit='1'", connect);
    }

    @FXML
    private void radioKgcmAction(ActionEvent event) throws SQLException {
        pu = "kg/Cm2";
        dh.execute("UPDATE writedropdownplc set unit='2'", connect);
    }

    @FXML
    private void radioPascalAction(ActionEvent event) {
    }

    @FXML
    private void radioKpaAction(ActionEvent event) {
    }

    @FXML
    private void radioMpaAction(ActionEvent event) {
    }

    @FXML
    private void txtAllowableLeakageAction(ActionEvent event) {
    }

    @FXML
    private void txtAllowableLeakageKeyRelease(KeyEvent event) {
    }

    @FXML
    private void txtValveSrNoAction(ActionEvent event) {
    }

    @FXML
    private void txtQtyAction(ActionEvent event) {
    }

    @FXML
    private void txtGADrawNoAction(ActionEvent event) {
    }

    @FXML
    private void txtEndTypeAction(ActionEvent event) {
    }

    @FXML
    private void txtMaterialGradeAction(ActionEvent event) {
    }

    @FXML
    private void txtQtyTestAction(ActionEvent event) {
    }

    @FXML
    private void txtCustomerAction(ActionEvent event) {
    }

    @FXML
    private void txtPORefAction(ActionEvent event) {
    }

    @FXML
    private void txtBOMAction(ActionEvent event) {
    }

    @FXML
    private void txtAssyProcedureAction(ActionEvent event) {
    }

    @FXML
    private void txtWORefAction(ActionEvent event) {
    }

    @FXML
    private void txtProcedureRefAction(ActionEvent event) {
    }

    @FXML
    private void txtTCRefAction(ActionEvent event) {
    }

    @FXML
    private void comboMakeAction(ActionEvent event) {
    }

    @FXML
    private void comboGaugeIdAction(ActionEvent event) throws SQLException, ParseException {
        String g_id = comboGaugeId.getSelectionModel().getSelectedItem();
        System.out.println("SELECT `gauge_range` FROM `gauge_description` WHERE  gauge_description =(SELECT description  FROM `gauge_data` WHERE `serial` = '" + g_id + "')");
        ResultSet rs_gr = dh.getData("SELECT `gauge_range` FROM `gauge_description` WHERE  gauge_description =(SELECT description  FROM `gauge_data` WHERE `serial` = '" + g_id + "')", connect);
        if (rs_gr.next()) {
            txtRange.setText(rs_gr.getString("gauge_range"));
        }
        ResultSet rs_date = dh.getData("SELECT c_date ,c_due_date FROM `gauge_data` WHERE `serial` = '" + g_id + "'", connect);
        if (rs_date.next()) {
            txtCalibration.setText(rs_date.getString("c_date"));
            txtValid.setText(rs_date.getString("c_due_date"));
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String sy_date = dtf.format(now);
        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sy_date);
        Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(txtValid.getText());

        if (date1.compareTo(date2) > 0) {
            txtGaugeError.setVisible(true);
            btnTestScreen.setDisable(true);
        } else {
            btnTestScreen.setDisable(false);
        }
    }

    @FXML
    private void comboCalStatusAction(ActionEvent event) {
    }

    @FXML
    private void comboRemarkAction(ActionEvent event) {
    }

    @FXML
    private void toggleAsideAction(ActionEvent event) throws SQLException {
        if (toggleAside.isSelected()) {
            dh.execute("UPDATE writedropdownplc set toggelA='1' WHERE id='1'", connect);

        } else {
            dh.execute("UPDATE writedropdownplc set toggelA='0' WHERE id='1'", connect);
        }
    }

    @FXML
    private void toggleBsideAction(ActionEvent event) throws SQLException {
        if (toggleBside.isSelected()) {
            dh.execute("UPDATE writedropdownplc set toggelB='1' WHERE id='1'", connect);

        } else {
            dh.execute("UPDATE writedropdownplc set toggelB='0' WHERE id='1'", connect);
        }
    }

}
