/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import java.awt.Toolkit;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.RED;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.management.remote.JMXConnectorFactory.connect;
import static javax.swing.Spring.width;
import org.python.apache.xerces.util.DOMUtil;
import static org.python.core.Py.True;
import org.python.modules.time.Time;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class TestScreenControllerold implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTestType;
    private Text ttxErr_Valveclass;
    private Text txtErr_Valvetype;
    @FXML
    private JFXTextField txtHydroSetPressure;
    @FXML
    private JFXTextField txtHoldingTime;

    private LineChart<Number, Number> lineChart;
//    int count_executer_status = 0;
//    boolean initial_start_trend = true;

    String current_overall_timer = "2";

    public static boolean start_trend_application = false;
    private boolean start_trend = false;
    //Trend Chart Component
    private static final int MAX_DATA_POINTS = 25;
    private int xSeriesData = 0;
    private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private ExecutorService executor;
    private final ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();
    private NumberAxis xAxis;
    DatabaseHandler dh = new DatabaseHandler();

    Connection connect = dh.MakeConnection();
    Colors colors = new Colors();
    private JFXButton btnStart;
    @FXML
    private JFXComboBox<String> cmbValveType;
    @FXML
    private JFXComboBox<String> cmbValveClass;
    @FXML
    private JFXComboBox<String> cmbValveSize;
    @FXML
    private JFXComboBox<String> cmbTestStd;
    @FXML
    private JFXTextField txtValveSrNo;
    private JFXTextField txtType;
    @FXML
    private JFXTextField txtCustom;
    @FXML
    private JFXTextField txtproject;

    String tt, vt, vc, vs, hp, hold, vsn, type, p_gauge, uom, oper, cust, proj, job, procedr, tstd, vendr, prange, indnt;
    String pernsno, vstd, remark = "";
    @FXML
    private JFXTextField txtCycleStatus;
    @FXML
    private JFXTextField txtHoldingTimer;
    @FXML
    private Gauge Gaugehydro;

    final ToggleGroup group_select = new ToggleGroup();
    final ToggleGroup group_select_time = new ToggleGroup();

    Colors color = new Colors();
    @FXML
    private Text textHydro;
    @FXML
    private Text textTrend;
    @FXML
    private HBox hboxtrend;
    private HBox hboxTrendText;
    private VBox vboxStatus;

    String h_time, pressure_a, result, overall_time, bubble = "";
    @FXML
    private JFXTextField txtOverAllTime;
    @FXML
    private JFXTextField txtOperator;
    @FXML
    private VBox vboxtrend;
    private JFXTextField txtvType;
//    @FXML
//    private JFXTextField txtRoomTemp1;
    @FXML
    private Text txtdate;
    @FXML
    private JFXButton btnLogin;

    boolean you_can = true;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXRadioButton radiobar;
    @FXML
    private JFXRadioButton radiopsi;
    @FXML
    private Text txtb;
    @FXML
    private JFXButton btngauge;
    private JFXTextField txtTestStd;
    private JFXTextField txtpermno;
    private JFXTextField txtgrn;
    private JFXTextField txtindent;
    @FXML
    private JFXTextField txtvendr;
    @FXML
    private JFXDrawer drawer1;
    @FXML
    private JFXComboBox<String> cmbPressuregage;
    @FXML
    private JFXTextField txtrange;
    private JFXButton btnAirPurging;
    @FXML
    private VBox vboxGauge;
    @FXML
    private VBox vboxTxt;
    private JFXDatePicker dateGrn;
    private JFXComboBox<String> cmbValveMat;
    @FXML
    private JFXRadioButton radiokg;
//    private JFXTextField txtremark;
    private JFXButton btnSubmit;

    @FXML
    private JFXTextField txtHydraulicSetPressure;
    @FXML
    private JFXTextField txtStabilization;
    @FXML
    private JFXTextField txtDrainTime;
    @FXML
    private JFXTextField txtheatno;
    @FXML
    private JFXTextField txtpono;
    @FXML
    private JFXTextField txtjobno;
    @FXML
    private Text textHydro1;
    @FXML
    private Text txtMode;
    private Text txtOffline;

    String current_machine_mode = "33", mac_mode = "22", off_mode = "22", clampingActualPressure = "33", s_time = "0", d_d = "0", d_time = "0", pressure_b = "0", popUps = "0", bubble_counter = "0", cycl_status, current_offline_mode, ts, operator_name, st, ht, dt, hsp, csp, pro, cus, heat;
    Boolean first_pop_lock = false, second_pop_lock = false, third_pop_lock = false, you_can_change = true;
    DoubleProperty ClampingAct;
    DoubleProperty HydroActA;
    DoubleProperty HydroActB;
    int firstBindingGauge = 0;
    String start_pressure_a, start_pressure_b, start_pressure_c, start_pressure_d, start_pressure_e, stop_pressure_a, stop_pressure_b, stop_pressure_c, stop_pressure_d, stop_pressure_e, oat, al, current_stabilization_timer, current_holding_timer, current_drain_delay, current_drain_timer, holding_time, overall_time_end;
    int test_result_by_type_check = 0;
    private int gauge_clamping_red, gauge_hydro_red;
    @FXML
    private Gauge GaugeActualHydraulic;
    @FXML
    private JFXTextField txtResult;
    @FXML
    private JFXTextField txtStabilizationTimer;
//    @FXML
//    private JFXTextField txtHoldingTimer;
    @FXML
    private JFXTextField txtDrainTimer;
    @FXML
    private HBox hboxTest;
    @FXML
    private ImageView imgAuto;
    @FXML
    private ImageView imgManual;
    @FXML
    private ImageView imgEmergency;
    @FXML
    private JFXComboBox<String> cmbTypeOfSealing;
    @FXML
    private Text txtunithydro;
    @FXML
    private Text txtunithydraulic;
    @FXML
    private JFXComboBox<String> cmbLeakageType;
    @FXML
    private JFXTextField txtAllowable;
    @FXML
    private Text txtinvalid;
    @FXML
    private JFXTextField txtAtcualBubble;
    @FXML
    private Text txtbubble;
    @FXML
    private JFXToggleButton toggleOnlineOffline;
    @FXML
    private Text txtOnlineOffline;
    @FXML
    private JFXButton btnAlarm;
    @FXML
    private JFXTextField txtGearboxWekno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        radiobar.setToggleGroup(group_select);
        GaugeActualHydraulic.setVisible(false);
        radiobar.setSelected(true);
        radiokg.setToggleGroup(group_select);
        radiopsi.setToggleGroup(group_select);
        Session.set("screen", "TestScreen");
        txtAllowable.setVisible(false);
        btngauge.setVisible(false);
        imgEmergency.setVisible(false);
        imgManual.setVisible(false);
        imgAuto.setVisible(false);
        //set Editable false some testing parameter
        txtCycleStatus.setEditable(false);
        txtStabilizationTimer.setEditable(false);
        txtHoldingTimer.setEditable(false);
        txtDrainTimer.setEditable(false);
        txtOverAllTime.setEditable(false);
        txtResult.setEditable(false);
        txtAtcualBubble.setEditable(false);
        txtAtcualBubble.setVisible(false);
        txtbubble.setVisible(false);
        String user_type = Session.get("user_type");
        if (user_type.equals("admin")) {
            btnAdmin.setVisible(true);

        } else {
            btnAdmin.setVisible(false);
        }
        String user_name = Session.get("user");
        try {
            System.out.println("fsfklskdflksdfkorif47445y787.?a/sd/dL31");
//            if (Session.get("p_data").equals("1")) {
//            String initial_query = "SELECT * FROM `initial_valve_data`  ORDER BY id DESC LIMIT 1";
//            ResultSet rs_in = dh.getData(initial_query, connect);
//            txtresult.setVisible(false);
//            txtremark.setVisible(false);
//            btnBubble.setVisible(false);
//            txtb.setVisible(false);
//            if (rs_in.next()) {
            if (5 > 9) {
                // Initial data
                Combo_data();
                int test, v_type, v_class, v_size, t_std;
                try {
                    test = Integer.parseInt(Session.get("test"));
                    v_class = Integer.parseInt(Session.get("v_type"));
                    v_type = Integer.parseInt(Session.get("v_clas"));
                    v_size = Integer.parseInt(Session.get("v_size"));
                    t_std = Integer.parseInt(Session.get("t_std"));
                    cmbTestType.getSelectionModel().select(test);
                    cmbValveType.getSelectionModel().select(v_class);
                    cmbValveClass.getSelectionModel().select(v_type);
                    cmbValveSize.getSelectionModel().select(v_size);
                    cmbTestStd.getSelectionModel().select(t_std);

                } catch (Exception e) {
                }

                txtHydroSetPressure.setText("0");
//                txtValveSrNo.setText(rs_in.getString("valve_sr_no"));
//                txtheatno.setText(rs_in.getString("heat_no"));
//                txtvendr.setText(rs_in.getString("vendor"));
//                txtpono.setText(rs_in.getString("po_no"));
//                txtjobno.setText(rs_in.getString("job_no"));
//                txtCustom.setText(rs_in.getString("customer"));
//                txtproject.setText(rs_in.getString("project"));
//                txtOperator.setText(rs_in.getString("operator"));

//                System.out.println("........." + rs_in.getString("uom"));
//                if (rs_in.getString("uom").equals("bar")) {
//                    System.out.println("bar");
//                    radiobar.setSelected(true);
//                    String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 1";
//                    System.out.println("cmd1" + cmd1);
//                    Process child1 = Runtime.getRuntime().exec(cmd1);
//                    child1.waitFor();
//                    String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//                    Process child2 = Runtime.getRuntime().exec(cmd2);
//                    child2.waitFor();
//                    String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//                    Process child3 = Runtime.getRuntime().exec(cmd3);
//                    child3.waitFor();
//                    uom = "bar";
//                    radiopsi.setSelected(false);
//                    radiokg.setSelected(false);
//                    
//                } else if (rs_in.getString("uom").equals("kg/sq.cm")) {
//                    System.out.println("bar");
//                    radiokg.setSelected(true);
//                    String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 1";
//                    System.out.println("cmd1" + cmd1);
//                    Process child1 = Runtime.getRuntime().exec(cmd1);
//                    child1.waitFor();
//                    String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//                    Process child2 = Runtime.getRuntime().exec(cmd2);
//                    child2.waitFor();
//                    String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//                    Process child3 = Runtime.getRuntime().exec(cmd3);
//                    child3.waitFor();
//                    uom = "kg/sq.cm";
//                    radiobar.setSelected(false);
//                    radiopsi.setSelected(false);
//                    
//                } else {
//                    radiopsi.setSelected(true);
//                    String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 1";
//                    Process child1 = Runtime.getRuntime().exec(cmd1);
//                    child1.waitFor();
//                    String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//                    Process child2 = Runtime.getRuntime().exec(cmd2);
//                    child2.waitFor();
//                    String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//                    Process child3 = Runtime.getRuntime().exec(cmd3);
//                    child3.waitFor();
//                    uom = "psi";
//                    radiobar.setSelected(false);
//                    radiokg.setSelected(false);
//                }
            } else {
                try {
                    // Initial data
                    Combo_data();
                } catch (SQLException ex) {
                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                }

                InitialDataLoad();

                txtCycleStatus.setEditable(true);
                txtHoldingTimer.setEditable(true);

                radiobar.setToggleGroup(group_select);

                set_data_plc();

//                if (radiopsi.isSelected()) {
//                    try {
//
//                        String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 1";
//                        Process child1 = Runtime.getRuntime().exec(cmd1);
//                        child1.waitFor();
//                        String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//                        Process child2 = Runtime.getRuntime().exec(cmd2);
//                        child2.waitFor();
//                        String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//                        Process child3 = Runtime.getRuntime().exec(cmd3);
//                        child3.waitFor();
//                        String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
//
//                        uom = "psi";
//                        txtHydroSetPressure.setText(cmd);
//                        radiobar.setSelected(false);
//                        radiokg.setSelected(false);
//
//                        String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
//                        System.out.println("gauge_data" + gauge_data);
//                        ResultSet rs_gdata;
//
//                        rs_gdata = dh.getData(gauge_data, connect);
//
//                        cmbPressuregage.getSelectionModel().clearSelection();
//                        cmbPressuregage.getItems().clear();
//                        while (rs_gdata.next()) {
//                            cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
//                        }
//                    } catch (IOException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else if (radiokg.isSelected()) {
//                    try {
//                        String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 1";
//                        Process child1 = Runtime.getRuntime().exec(cmd1);
//                        child1.waitFor();
//                        String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//                        Process child2 = Runtime.getRuntime().exec(cmd2);
//                        child2.waitFor();
//                        String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//                        Process child3 = Runtime.getRuntime().exec(cmd3);
//                        child3.waitFor();
//                        String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
//
//                        uom = "kg/sq.cm";
//                        txtHydroSetPressure.setText(cmd);
//
//                        radiobar.setSelected(false);
//                        radiopsi.setSelected(false);
//                        String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
//                        System.out.println("gauge_data" + gauge_data);
//                        ResultSet rs_gdata = dh.getData(gauge_data, connect);
//                        cmbPressuregage.getSelectionModel().clearSelection();
//                        cmbPressuregage.getItems().clear();
//                        while (rs_gdata.next()) {
//                            cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
//                        }
//                    } catch (IOException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else if (radiobar.isSelected()) {
//                    try {
//                        System.out.println("bar");
//                        String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 1";
//                        System.out.println("cmd1" + cmd1);
//                        Process child1 = Runtime.getRuntime().exec(cmd1);
//                        child1.waitFor();
//                        String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//                        Process child2 = Runtime.getRuntime().exec(cmd2);
//                        child2.waitFor();
//                        String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//                        Process child3 = Runtime.getRuntime().exec(cmd3);
//                        child3.waitFor();
//                        String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
//
//                        uom = "bar";
//                        txtHydroSetPressure.setText(cmd);
//                        radiopsi.setSelected(false);
//                        radiokg.setSelected(false);
//
//                    } catch (IOException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
            }

            cmbTypeOfSealing.getItems().addAll("Face Sealing");
            cmbTypeOfSealing.getSelectionModel().selectFirst();

            txtCycleStatus.setEditable(true);
            txtHoldingTimer.setEditable(true);

//            txtremark.setDisable(false);
            Gaugehydro.setVisible(false);
            textHydro.setVisible(false);
            hboxtrend.setVisible(false);
            textTrend.setVisible(false);
            Gaugehydro.setVisible(false);
            textHydro1.setVisible(false);
            hboxtrend.setVisible(false);
            textTrend.setVisible(false);
            Date dateInstance = new Date();
            txtdate.setText("" + (dateInstance.getDate() + "/" + (dateInstance.getMonth() + 1) + "/" + (dateInstance.getYear() + 1900)));

        } catch (SQLException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
        machine_mode();

    }
    String pu;

    private void set_data_plc() {
        try {
            String[] split_data = ToolKit.readpy("python E:\\E1257\\python_plc\\read_intial_data.py").split("/");

            txtHydroSetPressure.setText(Float.toString(Math.round(Float.parseFloat(split_data[1]))));
            txtHydraulicSetPressure.setText(Float.toString(Math.round(Float.parseFloat(split_data[2]))));
            txtStabilization.setText(split_data[3]);
            txtHoldingTime.setText(split_data[4]);
            txtDrainTime.setText(split_data[5]);

            String vc_query = "SELECT valve_class FROM valve_class WHERE write_value = '" + split_data[7] + "';";
            ResultSet vc_rs = dh.getData(vc_query, connect);
            if (vc_rs.next()) {
                cmbValveClass.getSelectionModel().select(vc_rs.getString("valve_class"));
            }
            String vs_query = "SELECT valve_size FROM valve_size WHERE write_value = '" + split_data[8] + "';";
            ResultSet vs_rs = dh.getData(vs_query, connect);
            if (vs_rs.next()) {
                cmbValveSize.getSelectionModel().select(vs_rs.getString("valve_size"));
            }

            String tt_query = "SELECT test_type FROM test_type WHERE write_value = '" + split_data[6] + "';";
            ResultSet tt_rs = dh.getData(tt_query, connect);
            if (tt_rs.next()) {
                cmbTestType.getSelectionModel().select(tt_rs.getString("test_type"));
            }
            String vt_query = "SELECT valve_type FROM valve_type WHERE write_value = '" + split_data[9] + "';";
            ResultSet vt_rs = dh.getData(vt_query, connect);
            if (vt_rs.next()) {
                cmbValveType.getSelectionModel().select(vt_rs.getString("valve_type"));
            }
            String tSTD_query = "SELECT test_standards FROM test_standards WHERE write_value = '" + split_data[10] + "';";
            ResultSet tSTD_rs = dh.getData(tSTD_query, connect);
            if (tSTD_rs.next()) {
                cmbTestStd.getSelectionModel().select(tSTD_rs.getString("test_standards"));
                if (cmbTestStd.getSelectionModel().getSelectedItem().equals("API-598")) {
                    txtHoldingTime.setEditable(false);
                    txtHydraulicSetPressure.setEditable(false);
                    txtHydroSetPressure.setEditable(false);
                    txtStabilization.setEditable(false);
                    txtDrainTime.setEditable(false);
                }

            }
            ResultSet rs_lt = dh.getData("SELECT leakage_type FROM leakage_type WHERE write_value_leakage_type='" + split_data[12] + "'", connect);
            if (rs_lt.next()) {
                cmbLeakageType.getSelectionModel().select(rs_lt.getString("leakage_type"));
            }
            switch (split_data[11]) {
                case "0":
                    radiobar.setSelected(true);
                    txtunithydraulic.setText("bar");
                    txtunithydro.setText("bar");
                    pu = "bar";
                    break;
                case "1":
                    radiopsi.setSelected(true);
                    txtunithydraulic.setText("psi");
                    txtunithydro.setText("psi");
                    pu = "psi";
                    break;
                case "2":
                    radiokg.setSelected(true);
                    txtunithydraulic.setText("kg/sqcm");
                    txtunithydro.setText("kg/sqcm");
                    pu = "kg/sqcm";
                    break;

            }
            String onlineOffline = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_bool.py 0 7");
            if (onlineOffline.equals("/True")) {
                toggleOnlineOffline.setSelected(true);
                txtOnlineOffline.setFill(Color.web("#0099FF"));
                txtOnlineOffline.setText("OFFLINE");
            } else {
                toggleOnlineOffline.setSelected(false);
                txtOnlineOffline.setFill(Color.web("#212120"));
                txtOnlineOffline.setText("ONLINE");

            }

            String lt = cmbLeakageType.getSelectionModel().getSelectedItem();
            System.out.println("lt value :" + lt);
//        Thread.sleep(50);
            if (lt.equals("NONE")) {
                txtAllowable.setVisible(false);
                txtAtcualBubble.setVisible(false);
                txtbubble.setVisible(false);
//                String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 6 0";
//                Process child = Runtime.getRuntime().exec(cmd);
//                child.waitFor();
            } else {
//            String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 6 1";
//            Process child = Runtime.getRuntime().exec(cmd);
//            child.waitFor();
                txtAllowable.setVisible(true);
                txtAtcualBubble.setVisible(true);
                txtbubble.setVisible(true);
            }

//                c
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }

        String tt_d = cmbTestType.getSelectionModel().getSelectedItem();
        if (tt_d.contains("PNEUMATIC")) {
            txtHydroSetPressure.promptTextProperty().set("PNEUMATIC TEST SET PRESSURE");
            textHydro.setText("PNEUMATIC TEST SET PRESSURE");
        } else {
            txtHydroSetPressure.promptTextProperty().set("HYDRO TEST SET PRESSURE");
            textHydro.setText("HYDROadmin   admin"
                    + " TEST SET PRESSURE");
        }

    }

    private void Combo_data() throws SQLException {
        System.out.println("in combo function");
        //test type
        String test_ty = "SELECT * FROM test_type WHERE test_type like '%SEAT TEST'";
        ResultSet rs_tt = dh.getData(test_ty, connect);
        cmbTestType.getItems().clear();
        cmbTestType.getSelectionModel().clearSelection();
        while (rs_tt.next()) {
            cmbTestType.getItems().add(rs_tt.getString("test_type"));
        }
        //valve class 
        String valve_c = "SELECT * FROM `valve_class` ORDER BY `valve_class_id` ASC";
        ResultSet rs_vc = dh.getData(valve_c, connect);
        cmbValveClass.getItems().clear();
        cmbValveClass.getSelectionModel().clearSelection();
        while (rs_vc.next()) {
            cmbValveClass.getItems().add(rs_vc.getString("valve_class"));
        }
        //valve size
        String valve_s = "SELECT * FROM `valve_size` ORDER BY `valve_size_id` ASC";
        ResultSet rs_vs = dh.getData(valve_s, connect);
        cmbValveSize.getSelectionModel().clearSelection();
        cmbValveSize.getItems().clear();
        while (rs_vs.next()) {
            cmbValveSize.getItems().add(rs_vs.getString("valve_size"));
        }
        //valve type
        String valve_t = "SELECT * FROM `valve_type` ORDER BY `valve_type_id` ASC";
        ResultSet rs_vt = dh.getData(valve_t, connect);
        cmbValveType.getSelectionModel().clearSelection();
        cmbValveType.getItems().clear();
        while (rs_vt.next()) {
            cmbValveType.getItems().add(rs_vt.getString("valve_type"));
        }

        //valve Standard
        String valve_std = "SELECT * FROM `test_standards`";
        ResultSet rs_vstd = dh.getData(valve_std, connect);
        cmbTestStd.getSelectionModel().clearSelection();
        cmbTestStd.getItems().clear();
//        cmbTestStd.getItems().add("");
        while (rs_vstd.next()) {
            cmbTestStd.getItems().add(rs_vstd.getString("test_standards"));
        }

        //Pressuregauge
//        if (cmbTestType.getSelectionModel().getSelectedItem().contains("PNEUMATIC  SEAT TEST")) {
//            ResultSet rs_p = dh.getData("SELECT serial FROM gauge_data WHERE description='Air Guage'", connect);
//            System.out.println("SELECT serial FROM gauge_data WHERE description='Air Guage'");
//            cmbPressuregage.getItems().clear();
//            while (rs_p.next()) {
//                cmbPressuregage.getItems().addAll(rs_p.getString("serial"));
//            }
//        } else {
            ResultSet rs_g = dh.getData("SELECT * FROM `gauge_data` WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'", connect);
            cmbPressuregage.getItems().clear();
            System.out.println("rs_g : SELECT * FROM `gauge_data` WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'");
            while (rs_g.next()) {
                cmbPressuregage.getItems().addAll(rs_g.getString("description"));
            }
//        }

        ResultSet rs_lt = dh.getData("SELECT leakage_type FROM leakage_type GROUP BY leakage_type ORDER BY leakage_type Desc", connect);
        cmbLeakageType.getItems().clear();
        while (rs_lt.next()) {
            cmbLeakageType.getItems().addAll(rs_lt.getString("leakage_type"));
        }

    }

    public void InitialDataLoad() {

//        trend_initialize();
    }

    private void disable_field() {
        cmbTestType.setDisable(true);
//        radioA.setEditable(true);
//        radioB.setEditable(true);
        cmbValveClass.setDisable(true);
        cmbValveSize.setDisable(true);
        cmbTestStd.setDisable(true);
        cmbValveType.setDisable(true);
        cmbPressuregage.setDisable(true);
//        cmbValveMat.setDisable(true);
        txtHoldingTime.setEditable(false);
        txtHydroSetPressure.setEditable(false);

        txtValveSrNo.setEditable(false);
        txtheatno.setEditable(false);
        txtvendr.setEditable(true);
        txtpono.setEditable(true);
        txtjobno.setEditable(true);
//        txtwater.setEditable(true);
        txtOperator.setEditable(false);
        txtCustom.setEditable(false);
        txtproject.setEditable(false);

        radiobar.setDisable(true);
        radiopsi.setDisable(true);
//        radiompas.setEditable(true);
        txtvendr.setEditable(false);

    }

    private void enable_field() {
        cmbTestType.setDisable(false);
//        radioA.setEditable(true);
//        radioB.setEditable(true);
        cmbValveClass.setDisable(false);
        cmbValveSize.setDisable(false);
        cmbTestStd.setDisable(false);
        cmbValveType.setDisable(false);
        cmbPressuregage.setDisable(false);
//        cmbValveMat.setDisable(true);
        txtHoldingTime.setEditable(true);
        txtHydroSetPressure.setEditable(true);

        txtValveSrNo.setEditable(true);
        txtheatno.setEditable(true);
        txtvendr.setEditable(true);
        txtpono.setEditable(false);
        txtjobno.setEditable(false);
//        txtwater.setEditable(true);
        txtOperator.setEditable(true);
        txtCustom.setEditable(true);
        txtproject.setEditable(true);

        radiobar.setDisable(false);
        radiopsi.setDisable(false);
//        radiompas.setEditable(true);
        txtvendr.setEditable(true);
    }

    private void is_empty() {

        tt = cmbTestType.getSelectionModel().getSelectedItem();
        vt = cmbValveType.getSelectionModel().getSelectedItem();
        vc = cmbValveClass.getSelectionModel().getSelectedItem();
        vs = cmbValveSize.getSelectionModel().getSelectedItem();
//        vstd = cmbTestStd.getSelectionModel().getSelectedItem();

        hp = txtHydroSetPressure.getText();
        vsn = txtValveSrNo.getText();
//        p_gauge = cmbPressuregage.getSelectionModel().getSelectedItem();
//        ts = txtTestStd.getText();
        ts = cmbTestStd.getSelectionModel().getSelectedItem();
        oper = txtOperator.getText();
        cust = txtCustom.getText();
        proj = txtproject.getText();
        heat = txtheatno.getText();
        hsp = txtHydroSetPressure.getText();

//        tstd = txtTestStd.getText();
//        vendr = txtvendr.getText();
//        pernsno = txtpermno.getText();
//        indnt = txtindent.getText();
//        prange = txtrange.getText();
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

    private void check_text_empty_fields(JFXTextField field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setUnFocusColor(RED);
                field.setFocusColor(RED);
            } else {
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
            }
        } else {
            field.setUnFocusColor(RED);
            field.setFocusColor(RED);
        }
    }

    //set empty fields on cycle compleat and cycle stop
    public void set_empty() {
        txtCycleStatus.setText("");
        txtHoldingTimer.setText("");

//        txtremark.setText("");
        txtOverAllTime.setText("");
    }

    private void machine_mode() {
        mode = new Thread(() -> {

            try {

                String display = "SELECT * FROM all_tag_read ORDER BY id DESC LIMIT 1";
                ResultSet rs;
                try {
                    rs = dh.getData(display, connect);
                    if (rs.next()) {
                        if (ToolKit.isNull(rs.getString("overall_time"))) {
                            System.out.println("NULL overall_time");
                        } else {
                            current_overall_timer = rs.getString("overall_time");
                        }
                        txtOverAllTime.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                }

                while (true) {
                    String cmd_insert = "python E:\\E1257\\python_plc\\test_tag_new.py " + DatabaseHandler.DB_HOST + " " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " insert_all_tag_sp insert_init_tags_sp";
//                    System.out.println("cmd_insert..." + cmd_insert);
                    Process child = Runtime.getRuntime().exec(cmd_insert);

                    child.waitFor();
//                    if (cmbTestStd.getSelectionModel().getSelectedItem().equals("Customized")) {
//                    } else {
//                        read_test_data();
//                    }

//                current_overall_timer=null;
//                txtStabilization=null;
                    try {
                        long start = System.currentTimeMillis();
                        //Sleeping thread for 250 miliseconds: Starts
                        try {
//                            Thread.sleep(250);
                            mode.sleep(100);
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
                            if (ToolKit.isNull(rs.getString("cycle_status"))) {
                                System.out.println("NULL cycle_status");
                            } else {
                                cycl_status = rs.getString("cycle_status");

                            }
                            if (ToolKit.isNull(rs.getString("stabilization"))) {
                                System.out.println("NULL stabilization_timer");
                            } else {
                                s_time = rs.getString("stabilization");
                            }
                            if (ToolKit.isNull(rs.getString("holding"))) {
                                System.out.println("NULL holding_timer");
                            } else {
                                h_time = rs.getString("holding");
                            }
//                        if (ToolKit.isNull(rs.getString("drain_delay"))) {
//                            System.out.println("NULL drain_delay");
//                        } else {
//                            d_d = rs.getString("drain_delay");
//                        }
                            if (ToolKit.isNull(rs.getString("drain"))) {
                                System.out.println("NULL drain_timer");
                            } else {
                                d_time = rs.getString("drain");
                            }
                            if (ToolKit.isNull(rs.getString("overall_time"))) {
                                System.out.println("NULL overall_time");
                            } else {
                                overall_time = rs.getString("overall_time");
                            }
                            if (ToolKit.isNull(rs.getString("hydraulic_actual"))) {
                                System.out.println("NULL hydraulic_actual_pressure");
                            } else {
//                                System.out.println("rs.getString(\"hydraulic_actual\")" + rs.getString("hydraulic_actual"));
                                clampingActualPressure = rs.getString("hydraulic_actual");
                            }
                            if (ToolKit.isNull(rs.getString("hydro_actual_a"))) {
                                System.out.println("NULL hydro_actual_a_pressure");
                            } else {
                                pressure_a = rs.getString("hydro_actual_a");
                            }
                            if (ToolKit.isNull(rs.getString("hydro_actual_b"))) {
                                System.out.println("NULL hydro_actual_b_pressure");
                            } else {
                                pressure_b = rs.getString("hydro_actual_b");
                            }
                            if (ToolKit.isNull(rs.getString("pop_up"))) {
                                System.out.println("NULL pop_ups");
                            } else {
                                popUps = rs.getString("pop_up");
                            }
                            if (ToolKit.isNull(rs.getString("result"))) {
                                System.out.println("NULL result");
                            } else {
                                result = rs.getString("result");
                            }
                            if (rs.getString("invalid").equals("1")) {
                                txtinvalid.setVisible(true);
                            } else {
                                txtinvalid.setVisible(false);
                            }

                            if (ToolKit.isNull(rs.getString("pop_up"))) {
                                System.out.println("NULL pop_ups");
                            } else {
                                popUps = rs.getString("pop_up");
                            }
                            if (ToolKit.isNull(rs.getString("bubble_count"))) {
                                System.out.println("NULL bubble_counter");
                            } else {
                                bubble_counter = rs.getString("bubble_count");
                            }

//Storing Value's of Machine Parameters: End
                            try {

                                //Updating Gauge's Value: Start
                                double clampingActual = Double.parseDouble(clampingActualPressure);
                                double hydroA = Double.parseDouble(pressure_a);
                                double hydroB = Double.parseDouble(pressure_b);
                                ClampingAct = new SimpleDoubleProperty(clampingActual);
                                HydroActA = new SimpleDoubleProperty(hydroA);
                                HydroActB = new SimpleDoubleProperty(hydroB);
                                if (hydroB > 1) {
                                    HydroActA = new SimpleDoubleProperty(hydroB);
                                }
                                Platform.runLater(() -> {
                                    GaugeActualHydraulic.valueProperty().bind(ClampingAct);
                                    Gaugehydro.valueProperty().bind(HydroActA);
                                    // GaugeActualHydro_B.valueProperty().bind(HydroActB);

//                            GaugeActualHydraulic.valueProperty().bind(ClampingAct);
//                            Gaugehydro.valueProperty().bind(HydroActA);
//                            // GaugeActualHydro_B.valueProperty().bind(HydroActB);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION IN SETTING VALUE OF GAUGES IN DATA_UPDATE_THREAD : " + e.getMessage());
//                            GaugeActualHydraulic.setValue(hydroB);
                            }

//                        System.out.println(clampingActual + ": " + hydroA + ": " + hydroB);
//Updating Gauge's Valu: End
                            try {
                                try {
                                    //Updating Overall Time Value: Start
//                                if (ToolKit.isNull(overall_time)) {
//
//                                } else {
//                                    if (overall_time.equals(current_overall_timer)) {
//                                    } else {
//                                        txtOverAllTime.setText(overall_time);
//                                        current_overall_timer = overall_time;
//                                    }
//                                }

                                    if (ToolKit.isNull(overall_time)) {
                                    } else {

                                        if (overall_time.equals(current_overall_timer)) {
                                        } else {
//                                                System.out.println("overall_time : " + overall_time);
                                            Platform.runLater(() -> {
                                                txtOverAllTime.setText(overall_time);
                                            });
                                            current_overall_timer = overall_time;
                                        }
                                    }

//Updating Overall Time Value: End
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();

                                    System.out.println("EXCEPTION IN UPDATE OVERALL TIME DATA_UPDATE_THREAD : " + e.getMessage());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
//                                try {
//                                    //Updating Off_Online Mode Value: Start
//                                    if (off_mode.equals(current_offline_mode)) {
//                                    } else {
//                                        offline_online(off_mode);
//                                    }
//                                    //Updating Off_Online Mode Value: End
//                                } catch (ArrayIndexOutOfBoundsException e) {
//                                    e.printStackTrace();
//                                    System.out.println("EXCEPTION IN Updating Off_Online Mode DATA_UPDATE_THREAD : " + e.getMessage());
//                                }
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

                            try {
                                //getting first pop up start the cycle: Start
                                switch (popUps) {
                                    case "0":
                                        first_pop_lock = false;
                                        second_pop_lock = false;
                                        third_pop_lock = false;
                                        break;
                                    case "1":
                                        if (first_pop_lock) {
                                        } else {

                                            first_pop_lock = true;
                                            you_can_change = false;
//                                        txtStabilization.setText();
//                                            txtStabilization.setText("");
//                                            txtHoldingTime.setText("");
//                                        txtDrainDelay.setText("");
//                                            txtDrainTime.setText("");
//                                            txtOverAllTime.setText("");
//                                        txtResult.setText("");
                                            pop_up_start("Please Confirm Valve Type, Valve Class and Valve Size.", 450, 0, 4, "N7:9");
                                        }
                                        break;
                                    case "2":
                                        if (second_pop_lock) {
                                        } else {

                                            pop_up_timer("Start Hodling Timer", 300, 0, "N7:9");

                                            second_pop_lock = true;
//                                        start_pressure_a = pressure_a;
//                                        start_pressure_b = pressure_b;
                                        }
                                        break;
                                    case "3":
                                        if (third_pop_lock) {
                                        } else {
                                            stop_pressure_a = pressure_a;
                                            stop_pressure_b = pressure_b;
                                            holding_time = txtHoldingTimer.getText();

//                                        imgDrainDelay.setVisible(false);
                                            pop_up_timer("Start Drain Timer", 300, 0, "N7:9");
                                            third_pop_lock = true;
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                //getting first pop up start the cycle: Start
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION IN Pop up Logic DATA_UPDATE_THREAD : " + e.getMessage());
                            }

                        }
                        long stop = System.currentTimeMillis();
                        long res = stop - start;
//                    System.out.println("mode offline pop cycle combine time : " + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Exception In Mode Thread : " + e);
//                            Thread.sleep(250);
                    }
                    if (stop_mode) {
                        break;
                    }
//                    dh.execute("TRUNCATE TABLE all_tag_read", connect);
                }
            } catch (IOException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, "Data_Update_Thread_Mode");
//        mode.setDaemon(true);
        mode.start();
    }

    private void mode(String mode) {

        switch (mode) {
            case "0":
                Platform.runLater(() -> {
                    txtMode.setText("Emergency Mode");
                    current_machine_mode = "0";
                    txtdate.setFill(Color.web("#0099FF"));
                });
//                imgEmergency.setVisible(false);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(false);
                break;
            case "1":
                Platform.runLater(() -> {
                    txtMode.setText("Alarm Mode");
//                Platform.runLater(()->{
                    current_machine_mode = "1";
                    txtdate.setFill(Color.web("Red"));
//                imgEmergency.setVisible(false);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(false);
                });
                break;
            case "2":
                Platform.runLater(() -> {
                    txtMode.setText("Manual Mode");

                    current_machine_mode = "2";
                    txtdate.setFill(Color.web("Blue"));
                });
//                imgEmergency.setVisible(false);
//                imgAuto.setVisible(false);
//                imgManual.setVisible(false);

                break;
            case "3":
                Platform.runLater(() -> {
                    txtMode.setText("Auto Mode");
                    current_machine_mode = "3";
                    txtdate.setFill(Color.web("Blue"));
//                imgEmergency.setVisible(false);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(false);
                });
                break;
            default:
                Platform.runLater(() -> {
                    txtMode.setText("Something wrong");
                    current_machine_mode = "0";
                    txtdate.setFill(Color.web("#C32420"));
                });
//                imgEmergency.setVisible(false);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(false);
                break;
        }
    }

    private void offline_online(String off_on) {
        switch (off_on) {
            case "0":
                txtOffline.setText("Online");
                current_offline_mode = "0";
                break;
            case "1":
                txtOffline.setText("Offline");
                current_offline_mode = "1";
                break;
            default:
                txtOffline.setText("Something went wrong");
                current_offline_mode = "0";
                break;
        }
    }

//    private void btnStartAction(ActionEvent event) throws SQLException, InterruptedException, IOException {
//
//        String Truncate = "TRUNCATE test_tags";
//        dh.execute(Truncate, connect);
//
//        if (ToolKit.isNull(vt)
//                || ToolKit.isNull(tt)
//                || ToolKit.isNull(vc)
//                || ToolKit.isNull(vs)
//                || ToolKit.isNull(oper)
//                || ToolKit.isNull(hp)
//                || ToolKit.isNull(hold)
//                || ToolKit.isNull(vsn)
//                || ToolKit.isNull(p_gauge)
//                || ToolKit.isNull(tstd)
//                || ToolKit.isNull(type)
//                || ToolKit.isNull(vendr)
//                || ToolKit.isNull(indnt)
//                || ToolKit.isNull(prange)) {
//            Dialog.showForSometime("", "Please Fill All Required Data", "Alert", 450, 10);
//            check_combo_empty_fields(cmbValveType, vt);
//            check_combo_empty_fields(cmbValveSize, vs);
//            check_combo_empty_fields(cmbValveClass, vc);
//            check_combo_empty_fields(cmbTestType, tt);
//            check_text_empty_fields(txtHoldingTime, hold);
//            check_text_empty_fields(txtHydroSetPressure, hp);
//            check_combo_empty_fields(cmbPressuregage, p_gauge);
//            check_text_empty_fields(txtValveSrNo, vsn);
//            check_text_empty_fields(txtTestStd, type);
//            check_text_empty_fields(txtOperator, oper);
//            check_text_empty_fields(txtCustom, cust);
//            check_text_empty_fields(txtproject, proj);
//            check_text_empty_fields(txtvendr, vendr);
//            check_text_empty_fields(txtindent, indnt);
//            check_text_empty_fields(txtrange, prange);
//
//        } else {
//
//            Session.set("p_data", "1");
//            pop_up_start("Please Confirm Valve Type, Valve Class and Valve Size.", 450, 0, 4, "N7:9");
//
//            Thread.sleep(250);
//        }
//
//    }
    int test_no = 1;

    private void check_date_empty_fields(JFXDatePicker field, String value) {
        if (value != null && !value.isEmpty() && !field.isFocused()) {
            if (value.equals("null") || value.equals("")) {
                field.setStyle("-fx-border-width: 0");
                field.setStyle("-fx-border-color: #ed0739;");
            } else {
            }
        }
    }

    private void pop_up_start(String message, int width, int yes, int no, String tag) throws IOException {
        String[] split = ToolKit.readpy("python E:\\E1257\\python_plc\\gauge_setup_hydro.py").split("/");
        System.out.println(split[1] + "','" + split[2] + "','" + split[3]);
        if (split[3].equals("False")) {
            is_empty();
            if (ToolKit.isNull(tt)
                    //                        || ToolKit.isNull(lt)
                    || ToolKit.isNull(vt)
                    || ToolKit.isNull(vc)
                    || ToolKit.isNull(vs)
                    //                        || ToolKit.isNull(tos)
                    || ToolKit.isNull(hp)
                    //                        || ToolKit.isNull(shift)
                    || ToolKit.isNull(vsn)
                    || ToolKit.isNull(ts)
                    || ToolKit.isNull(oper)
                    //                            || ToolKit.isNull(dd)
                    || ToolKit.isNull(cust)
                    || ToolKit.isNull(proj)
                    || ToolKit.isNull(heat) //|| ToolKit.isNull(vsn)
                    //                            || ToolKit.isNull(vtn)
                    //                            || ToolKit.isNull(mnfr)
                    //                            || ToolKit.isNull(wtr)
                    // || ToolKit.isNull(pro)//project
                    // || ToolKit.isNull(cus) //customer     
                    //|| ToolKit.isNull(vst)
                    //                        || ToolKit.isNull(imir)
                    ) {

                Dialog.showForSometime("", "Please provide appropriate data", "Alert", 450, 10);
                String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 18 0 4 ";
                System.out.println("cmd..." + cmd);
                Process child = Runtime.getRuntime().exec(cmd);
                try {
                    child.waitFor();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                Platform.runLater(() -> {
                    Optional<ButtonType> option = Dialog.ConfirmationDialog("CONFIRMATION", message, width);
                    if (option.get() == ButtonType.YES) {
                        try {
                            disable_field();

                            cycleStatusThread();
                            you_can = false;
                            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 18 0 0 ";
                            System.out.println("cmd..." + cmd);
                            Process child = Runtime.getRuntime().exec(cmd);
                            try {
                                child.waitFor();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            String select_data = "SELECT * FROM `valve_data` ORDER BY valve_data_id DESC LIMIT 1";
                            ResultSet rs_sele = dh.getData(select_data, connect);
                            if (rs_sele.next()) {
                                test_no = Integer.parseInt(rs_sele.getString("test_no"));
                                test_no++;

                                //insert data into initia_vala_data
                                String sp = " insert_test_init_sp('" + tt + "','NA','" + vt + "','" + vs + "','" + vc + "','" + cmbTypeOfSealing.getSelectionModel().getSelectedItem() + "','" + ts + "','" + st + "','" + ht + "','NA','" + dt + "','" + hsp + "','" + csp + "','" + pu + "','0','" + txtHydroSetPressure.getText() + "','" + txtHydraulicSetPressure.getText() + "','" + vsn + "','NA','NA','NA','" + txtproject.getText() + "','" + cust + "','" + txtOperator.getText() + "','NA'," + test_no + ",'NA','NA','" + txtheatno.getText() + "','" + cmbPressuregage.getSelectionModel().getSelectedItem() + "','" + txtvendr.getText() + "','" + txtpono.getText() + "','" + txtjobno.getText() + "','" + txtGearboxWekno.getText() + "','" + cmbTestStd.getSelectionModel().getSelectedItem() + "')";
                                System.out.println("sp_insert : " + sp);
                                dh.execute_sp(sp, connect);

                            } else {
                                //insert data into initia_vala_data
                                String sp = " insert_test_init_sp('" + tt + "','NA','" + vt + "','" + vs + "','" + vc + "','" + cmbTypeOfSealing.getSelectionModel().getSelectedItem() + "','" + ts + "','" + st + "','" + ht + "','NA','" + dt + "','" + hsp + "','" + csp + "','" + pu + "','0','" + txtHydroSetPressure.getText() + "','" + txtHydraulicSetPressure.getText() + "','" + vsn + "','NA','NA','NA','" + pro + "','" + cust + "','" + txtOperator.getText() + "','NA'," + test_no + ",'NA','NA','" + txtheatno.getText() + "','" + cmbPressuregage.getSelectionModel().getSelectedItem() + "','" + txtvendr.getText() + "','" + txtpono.getText() + "','" + txtjobno.getText() + "','" + txtGearboxWekno.getText() + "')";
                                dh.execute_sp(sp, connect);
                            }

//                            trend_initialize();
                            String[] split_gauge = ToolKit.readpy("python E:\\E1257\\python_plc\\gauge_setup_hydro.py").split("/");
                            Thread.sleep(50);
                            String psu = "";
                            if (radiobar.isSelected()) {
                                psu = "bar";
                            } else if (radiopsi.isSelected()) {
                                psu = "psi";
                            } else {
                                psu = "kg/sqcm";
                            }
                            guage_initialize(0, Integer.parseInt(split_gauge[1]), Integer.parseInt(split_gauge[2]), psu);

//                    Background_Processes.insert_plc_data("python E:\\E1257\\python_plc\\inser_tag.py localhost root abc E1024 truncate_test_tags_sp insert_test_tags_sp", false, true);
//                            start_trend = true;
//                            disable_field();
                            //Trend Start
//                            start_trend();
//                            textTrend.setVisible(true);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (option.get() == ButtonType.NO) {
                        try {
                            System.out.println("NO PRESSED");

                            Thread.sleep(100);
                            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 18 0 4 ";
                            System.out.println("cmd..." + cmd);
                            Process child = Runtime.getRuntime().exec(cmd);
                            try {
                                child.waitFor();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                            }
//                    Session.set("Trend_Status", "Stopped");
//                    Session.set("Trend_Status", "Stopped");
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            }
        } else {
            Dialog.showAndWait("INVALID SETTINGS.....");
        }

    }

    private void pop_up_timer(String message, int width, int ok, String tag) {
        Platform.runLater(() -> {
            Optional<ButtonType> option = Dialog.ConfirmationDialog_Single_button("CONFIRMATION", message, width);
            if (option.get() == ButtonType.OK) {
                try {

                    System.out.println("OK PRESSED");
                    String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 18 0 0 ";
                    System.out.println("cmd..." + cmd);
                    Process child = Runtime.getRuntime().exec(cmd);
                    try {
                        child.waitFor();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Thread.sleep(100);
                    if (message.equals("Start Hodling Timer")) {

//                        if (Session.get("Test_Type").equals("HYDROSTATIC SEAT C SIDE")) {
//                            start_pressure_a = pressure_b;
//                            start_pressure_b = pressure_b;
//                            start_pressure_c = pressure_a;
//                            start_pressure_d = pressure_b;
//                            start_pressure_e = pressure_b;
//                        } else if (Session.get("Test_Type").equals("HYDROSTATIC SEAT D SIDE")) {
//                            start_pressure_a = pressure_a;
//                            start_pressure_b = pressure_a;
//                            start_pressure_c = pressure_a;
//                            start_pressure_d = pressure_b;
//                            start_pressure_e = pressure_a;
//                        } else if (Session.get("Test_Type").equals("HYDROSTATIC SEAT E SIDE")) {
//                            start_pressure_a = pressure_b;
//                            start_pressure_b = pressure_b;
//                            start_pressure_c = pressure_b;
//                            start_pressure_d = pressure_b;
//                            start_pressure_e = pressure_a;
//                        } else {
                        start_pressure_a = pressure_a;
                        start_pressure_b = pressure_b;
                        start_pressure_c = "0";
                        start_pressure_d = "0";
                        start_pressure_e = "0";
//                        leakageCheck();
//                        }
                    }
                    Session.set("Trend_Status", "Running");
                    check_pop_up();
                } catch (InterruptedException ex) {
                    System.err.println("Exception in Holding time pop up respond" + ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void check_pop_up() throws IOException {
        if (!"0".equals(popUps)) {
            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 18 0 0 ";
            System.out.println("cmd..." + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
        }
    }
    volatile boolean stopCycleStatusThread;
    Thread cycleStatus;
    String current_cycle_status = "45";
    String pressure_a_side = "0";
    String pressure_b_side = "0";
    int delete_count = 0;
    int count_result = 0;
    int test_result_count = 0;
    boolean stop_pressure_get = true;

    private void cycleStatusThread() {

        stopCycleStatusThread = false;
        cycleStatus = new Thread(() -> {

            while (true) {
//                 Runtime.getRuntime().gc();
                try {
                    Thread.sleep(250);
                    try {
                        try {
                            //cycle status update

                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();

                            //Thread.interrupted();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION IN CYCLE STATUS UPDATE cycleStatusThread : " + e.getLocalizedMessage());
                    }

                    try {
                        //getting All timers
                        switch (cycl_status) {
                            case "2":
                                try {
                                    if (s_time.equals(current_stabilization_timer)) {
                                    } else {
                                        try {
                                            Platform.runLater(() -> {
                                                txtStabilizationTimer.setText(s_time);
                                            });
                                            current_stabilization_timer = s_time;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "3":
                                try {
                                    if (h_time.equals(current_holding_timer)) {
                                    } else {
                                        try {
                                            Platform.runLater(() -> {
                                                txtHoldingTimer.setText(h_time);
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (h_time.equals(1) || h_time.equals(2) || h_time.equals(3) || h_time.equals(4) || h_time.equals(5)) {
                                            test_result_count = 0;
                                        }
                                        Platform.runLater(() -> {
                                            txtAtcualBubble.setText(bubble_counter);
                                        });
                                        try {
                                            pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(pressure_a));
                                            pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(pressure_b));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //Test_result_by_test_type

                                        if (test_result_by_type_check == 1) {
                                            String query = "INSERT INTO test_result_by_type (`valve_serial_no`,`test_no`,`test_type`,`hydro_pressure_a_side`,`hydro_pressure_b_side`,`date_time`,`test_count`) VALUES('" + vsn + "','" + test_no + "','" + tt + "','" + pressure_a_side + "','" + pressure_b_side + "',NOW(),'" + test_result_by_type_check + "')";
                                            try {
                                                dh.execute(query, connect);
                                            } catch (SQLException e) {
                                                System.out.println(String.valueOf("Test result by type:  " + e.getMessage()));
                                            }
                                        } else {
                                            if (delete_count == 0) {
                                                String delete_query = "DELETE FROM test_result_by_type WHERE valve_serial_no = '" + Session.get("Valve_Serial_No") + "' AND test_type = '" + Session.get("Test_Type") + "' AND test_no = '" + Session.get("test_no") + "';";
                                                try {
                                                    dh.execute(delete_query, connect);
                                                    delete_count++;
                                                } catch (SQLException e) {
                                                    System.out.println(String.valueOf("Test result by type:  " + e.getMessage()));
                                                }
                                            }
                                            String query = "INSERT INTO test_result_by_type (`valve_serial_no`,`test_no`,`test_type`,`hydro_pressure_a_side`,`hydro_pressure_b_side`,`date_time`,`test_count`) VALUES('" + Session.get("Valve_Serial_No") + "','" + Session.get("test_no") + "','" + Session.get("Test_Type") + "','" + pressure_a_side + "','" + pressure_b_side + "',NOW(),'" + test_result_by_type_check + "')";
                                            try {
                                                dh.execute(query, connect);
                                            } catch (SQLException e) {
                                                System.out.println(String.valueOf("Test result by type:  " + e.getMessage()));
                                            }
                                        }
                                        current_holding_timer = h_time;
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                            case "4":
                                try {

                                    if (d_time.equals("0") || d_time.equals("1") || d_time.equals("2")) {
                                        try {
                                            get_result(result);
                                            overall_time_end = txtOverAllTime.getText();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    if (d_time.equals(current_drain_timer)) {
                                    } else {
                                        try {
                                            Platform.runLater(() -> {
                                                txtDrainTimer.setText(d_time);
                                            });
                                            current_drain_timer = d_time;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    if (stop_pressure_get) {
//                                        if (Session.get("Test_Type").equals("HYDROSTATIC SEAT C SIDE")) {
//                                            stop_pressure_a = pressure_b;
//                                            stop_pressure_b = pressure_b;
//                                            stop_pressure_c = pressure_a;
//                                            stop_pressure_d = pressure_b;
//                                            stop_pressure_e = pressure_b;
//                                        } else if (Session.get("Test_Type").equals("HYDROSTATIC SEAT D SIDE")) {
//                                            stop_pressure_a = pressure_a;
//                                            stop_pressure_b = pressure_a;
//                                            stop_pressure_c = pressure_a;
//                                            stop_pressure_d = pressure_b;
//                                            stop_pressure_e = pressure_a;
//                                        } else if (Session.get("Test_Type").equals("HYDROSTATIC SEAT E SIDE")) {
//                                            stop_pressure_a = pressure_b;
//                                            stop_pressure_b = pressure_b;
//                                            stop_pressure_c = pressure_b;
//                                            stop_pressure_d = pressure_b;
//                                            stop_pressure_e = pressure_a;
//                                        } else {

                                        stop_pressure_a = pressure_a;
                                        stop_pressure_b = pressure_b;
                                        stop_pressure_c = "0";
                                        stop_pressure_d = "0";
                                        stop_pressure_e = "0";
                                        stop_pressure_get = false;
//                                        }
                                    }

//                                    if (d_d.equals(current_drain_delay)) {
//                                    } else {
//                                        try {
//                                            Platform.runLater(()->{
//                                            txtDrainDelay.setText(d_d);
//                                            });
//                                            current_drain_delay = d_d;
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
//                            case "5":
//                                try {
//                                    if (d_time.equals(current_drain_timer)) {
//                                    } else {
//                                        try {
//                                            Platform.runLater(() -> {
//                                                txtDrainTimer.setText(d_time);
//                                            });
//                                            current_drain_timer = d_time;
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                                break;
                            case "5":
                                System.out.println("in case 5");
                                try {
                                    count_result = 0;
//                                    String lt = txtTypeOfLeakage.getText();
                                    String vt = cmbValveType.getSelectionModel().getSelectedItem();
                                    String vc = cmbValveClass.getSelectionModel().getSelectedItem();
                                    String vs = cmbValveSize.getSelectionModel().getSelectedItem();
                                    String hsp = txtHydroSetPressure.getText();
                                    String psu;
                                    if (radiobar.isSelected()) {
                                        psu = "bar";
                                    } else if (radiopsi.isSelected()) {
                                        psu = "psi";
                                    } else {
                                        psu = "kg/sqcm";
                                    }

                                    if (test_result_count == 0) {
                                        String check_data = "SELECT test_result_id,test_no FROM test_result WHERE test_no = '" + Session.get("test_no") + "';";
                                        ResultSet check = dh.getData(check_data, connect);
                                        if (check.next()) {
                                            String delete_query = "DELETE FROM test_result WHERE test_no = '" + Session.get("test_no") + "';";
                                            try {
                                                dh.execute(delete_query, connect);
                                                delete_count++;
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                System.out.println(String.valueOf(e.getMessage()));
                                            }
                                        }
                                        Thread.sleep(15);

                                        String start_pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_a));
                                        String start_pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_b));
                                        String stop_pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_a));
                                        String stop_pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_b));
                                        // for dbb block
                                        String start_pressure_c_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_c));
                                        String start_pressure_d_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_d));
                                        String start_pressure_e_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_e));
                                        String stop_pressure_c_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_c));
                                        String stop_pressure_d_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_d));
                                        String stop_pressure_e_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_e));
                                        String actLeak = " ";

                                        //for dbb valve
                                        if (vt.equals("DOUBLE BLOCK BLEED VALVE")) {
                                            String query1 = "INSERT INTO dbb_test_result (`valve_serial_no`, `test_no`, `test_type`, `leakage_type`, `valve_type`,`valve_size`, `valve_class`, `actual_leakage`, `holding_time`,`overall_time`, `hydro_set_pressure`,`start_pressure_a`,`start_pressure_b`,`start_pressure_c`,`start_pressure_d`,`start_pressure_e`,`stop_pressure_a`,`stop_pressure_b`,`stop_pressure_c`,`stop_pressure_d`,`stop_pressure_e`, `pressure_unit`, `gauge_serial_no`, `guage_calibration_date`, `test_result`, `date_time`) VALUES('" + Session.get("Valve_Serial_No") + "','" + Session.get("test_no") + "','" + Session.get("Test_Type") + "','NA','" + vt + "','" + vs + "','" + vc + "','" + actLeak + "','" + Session.get("holding_time") + "','" + oat + "','" + hsp + "','" + start_pressure_a_side + "','" + start_pressure_b_side + "','" + start_pressure_c_side + "','" + start_pressure_d_side + "','" + start_pressure_e_side + "','" + stop_pressure_a_side + "','" + stop_pressure_b_side + "','" + stop_pressure_c_side + "','" + stop_pressure_d_side + "','" + stop_pressure_e_side + "','" + psu + "','" + Session.get("gauge_serial") + "','" + Session.get("gauge_calibration_date") + "','" + result + "',NOW())";
                                            try {
                                                dh.execute(query1, connect);
                                                test_result_count++;
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                System.out.println(String.valueOf("TEST RESULT DBB : " + e.getMessage()));
                                            }

                                        } else {
                                            String query = "INSERT INTO test_result (`valve_serial_no`, `test_no`, `test_type`, `leakage_type`, `valve_type`,`valve_size`, `valve_class`, `actual_leakage`, `holding_time`,`over_all_time`, `hydro_set_pressure`,`start_pressure_a`,`start_pressure_b`,`stop_pressure_a`,`stop_pressure_b`, `pressure_unit`, `gauge_serial_no`, `guage_calibration_date`, `test_result`, `date_time`) VALUES('" + vsn + "','" + test_no + "','" + cmbTestType.getSelectionModel().getSelectedItem() + "','" + cmbLeakageType.getSelectionModel().getSelectedItem() + "','" + vt + "','" + vs + "','" + vc + "','" + txtAtcualBubble.getText() + "','" + holding_time + "','" + overall_time_end + "','" + hsp + "','" + start_pressure_a_side + "','" + start_pressure_b_side + "','" + stop_pressure_a_side + "','" + stop_pressure_b_side + "','" + psu + "','" + cmbPressuregage.getSelectionModel().getSelectedItem() + "','NA','" + result + "',NOW())";
                                            System.out.println("query test result : " + query);
                                            try {
                                                dh.execute(query, connect);
                                                test_result_count++;
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                System.out.println(String.valueOf("TEST RESULT : " + e.getMessage()));
                                            }

                                        }

                                    }
//                                    screen_reopen();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                dropbox("TestScreen.fxml", false);
                                break;
                            default:
                                break;

                        }
                        if (cycl_status.equals(current_cycle_status)) {
                        } else {
                            try {
                                cycle_status(cycl_status);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        if (stopCycleStatusThread) {
                            cycleStatus.stop();
                            break;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION IN getting All timers cycleStatusThread : " + e.getLocalizedMessage());
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException intex) {
                            System.err.println("Interupt in cycleStatusThread Exception : " + intex);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Exception in cycleStatusThread: " + e.getMessage());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException intex) {
                        System.err.println("Interupt in cycleStatusThread Exception : " + intex);
                    }
                }

                //to clear garbage data
            }

        }, "cycleStatusThread");
        cycleStatus.start();
//       
//                  updateTime=null;
//                  cycleStatus=null;

    }

    private void cycle_status(String status) {
        switch (status) {
            case "0":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("");
                });
//                txtCycleStatus.setText("");
                current_cycle_status = "0";
                break;
            case "1":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("CYCLE RUNNING");
                });
                current_cycle_status = "1";
                break;
            case "2":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("STABILIZATION TIMER RUNNING");
                });

                current_cycle_status = "2";
                break;
            case "3":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("HOLDING TIMER  RUNNING");
                });

                current_cycle_status = "3";
                break;
            case "4":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("DRAIN TIMER RUNNING");
                });

                current_cycle_status = "4";
                break;
            case "5":
                enable_field();
                Platform.runLater(() -> {
                    txtCycleStatus.setText("CYCLE COMPLETE");
                });
                you_can_change = true;
                oat = txtOverAllTime.getText();
//                al = txtActualLeakage.getText();
//                current_cycle_status = "5";
                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");

//                stop_mode = true;
//                stopCycleStatusThread = true;
//                ();
//                screen_reopen();
                System.out.println("start_trend LOW");

                current_cycle_status = "5";
                //cycle status stop
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }

                break;
            case "6":
                enable_field();
                System.out.println("force Stop");
                count_result = 0;
                Platform.runLater(() -> {
                    txtCycleStatus.setText("FORCE STOP");
                });
                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");

                start_trend = false;
                you_can_change = true;
                System.out.println("start_trend LOW");
                current_cycle_status = "6";
                //cycle status stop
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                break;

//            case "7":
//                count_result = 0;
//                Platform.runLater(() -> {
//                    txtCycleStatus.setText("FORCE STOP");
//                });
////                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");
//
//                start_trend = false;
//                you_can_change = true;
//                System.out.println("start_trend LOW");
//                current_cycle_status = "7";
//                break;
            case "7":
                enable_field();
                count_result = 0;
                Platform.runLater(() -> {
                    txtCycleStatus.setText("EMERGENCY");
                });
                you_can_change = true;
                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");

                start_trend = false;
//                stop_read();
                System.out.println("start_trend LOW");
                current_cycle_status = "7";
                //cycle status stop
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                break;
            default:
                txtCycleStatus.setText("");
                you_can_change = true;
                current_cycle_status = "0";
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                break;
        }
    }

    private void empty_timers() {
        txtCycleStatus.setText("");
        txtStabilizationTimer.setText("");
        txtHoldingTimer.setText("");
        txtDrainTimer.setText("");
        txtOverAllTime.setText("");
        txtResult.setText("");
        txtAtcualBubble.setText("");
        test_result_by_type_check = 0;
    }

    private void get_result(String result) {
        try {
            if (result.equals("1")) {

                txtResult.setStyle(" -fx-text-inner-color:GREEN; -fx-font-size: 20px;");
                Platform.runLater(() -> {
                    txtResult.setText("TEST OK");
                });
            } else {
                txtResult.setStyle(" -fx-text-inner-color:RED; -fx-font-size: 20px;");
                Platform.runLater(() -> {
                    txtResult.setText("TEST NOT OK");
                });
            }

            dh.execute("TRUNCATE TABLE all_tag_read", connect);
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void pop_up_start_hold(String message, int width, int yes, int no) {
//        Platform.runLater(() -> {
//            Optional<ButtonType> option = Dialog.ConfirmationDialog("CONFIRMATION", message, width);
//            if (option.get() == ButtonType.YES) {
//                try {
////                    hold_flag = 1;
//                    //cycle Start.
//                    String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 105 1 1";
//                    System.out.println("cmd..." + cmd);
//                    Process child = Runtime.getRuntime().exec(cmd);
//                    child.waitFor();
//
//                    String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 105 1 0";
//                    Process child1 = Runtime.getRuntime().exec(cmd1);
//                    child1.waitFor();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//
//    }
    NumberAxis yAxis;

    private void trend_initialize() throws IOException {
        //            AnchorPane trend = FXMLLoader.load(getClass().getResource("TrendScreen.fxml"));
        try {

            System.out.println("in");
            hboxtrend.setVisible(true);
//              hboxTrendText.setVisible(true);
//              vboxStatus.setVisible(true);
//              lineChart.setVisible(true);
            System.out.println("in function");
            xAxis = new NumberAxis(0, 1000, 1);
            xAxis.setForceZeroInRange(true);
            xAxis.setAutoRanging(true);
            xAxis.setTickLabelsVisible(true);
            xAxis.setTickMarkVisible(true);
            xAxis.setMinorTickVisible(true);
            yAxis = new NumberAxis();
            // Create a LineChart
            lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
                // Override to remove symbols on each data point
                @Override
                protected void dataItemAdded(XYChart.Series<Number, Number> series, int itemIndex, XYChart.Data<Number, Number> item) {
                }
            };
//
//            String test_type = txtTestType.getText();
            lineChart.setAnimated(false);
            lineChart.setTitle("");
            lineChart.setHorizontalGridLinesVisible(true);
            series1.setName("Hydro Pressure ");
            lineChart.getData().addAll(series1);
            drawer1.setSidePane(lineChart);
            drawer1.setOverLayVisible(false);

        } catch (Exception e) {
        }

    }
    int count_executer_status = 0;
    boolean initial_start_trend = true;

    private void start_trend() {

        if (initial_start_trend) {
            initial_start_trend = false;
        } else {
            xSeriesData = 0;
            System.out.println("Clearing dataQue");
            dataQ1.clear();
            dataQ2.clear();
            series1.getData().clear();
            series2.getData().clear();

        }

        xAxis.setLowerBound(0);
        count_executer_status++;
        //upate ui
        Platform.runLater(() -> {
            //to clear garbage data

            System.out.println("Cleared dataQue");
            executor = Executors.newCachedThreadPool((Runnable r) -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });
            count_executer_status = 0;
            AddToQueue addToQueue = new AddToQueue();
            executor.execute(addToQueue);
            //-- Prepare Timeline
            prepareTimeline();
        });
//         Runtime.getRuntime().gc();
    }

    private void btnBubbleAction(ActionEvent event) {

        ToolKit.tagWrite("B3:37/1", "1");
        ToolKit.tagWrite("B3:37/2", "1");
    }

    @FXML
    private void radiobarAction(ActionEvent event) throws IOException, InterruptedException, SQLException {
//        if (radiobar.isSelected()) {
//            System.out.println("bar");
//            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 1";
//            System.out.println("cmd1" + cmd1);
//            Process child1 = Runtime.getRuntime().exec(cmd1);
//            child1.waitFor();
//            String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//            Process child2 = Runtime.getRuntime().exec(cmd2);
//            child2.waitFor();
//            String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//            Process child3 = Runtime.getRuntime().exec(cmd3);
//            child3.waitFor();
//            String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
////               txtHydroSetPressure.setText(cmd.substring(0,cmd.indexOf(".")));
//            uom = "bar";
//            txtHydroSetPressure.setText(cmd);
//            radiopsi.setSelected(false);
//            radiokg.setSelected(false);
////            String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
////            System.out.println("gauge_data" + gauge_data);
////            ResultSet rs_gdata = dh.getData(gauge_data, connect);
////            cmbPressuregage.getSelectionModel().clearSelection();
////            cmbPressuregage.getItems().clear();
////            while (rs_gdata.next()) {
////                cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
////            }
//
//        }
        String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 16 0 0";
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();
        read_test_data();
        txtunithydraulic.setText("bar");
        txtunithydro.setText("bar");
        pu = "bar";
    }

    @FXML
    private void radiopsiAction(ActionEvent event) throws IOException, InterruptedException, SQLException {
//        if (radiopsi.isSelected()) {
//            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 1";
//            Process child1 = Runtime.getRuntime().exec(cmd1);
//            child1.waitFor();
//            String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//            Process child2 = Runtime.getRuntime().exec(cmd2);
//            child2.waitFor();
//            String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//            Process child3 = Runtime.getRuntime().exec(cmd3);
//            child3.waitFor();
//            String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
////              txtHydroSetPressure.setText(cmd.substring(0,cmd.indexOf(".")));
//            uom = "psi";
//            txtHydroSetPressure.setText(cmd);
//
//            radiobar.setSelected(false);
//            radiokg.setSelected(false);
//
////            String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
////            System.out.println("gauge_data" + gauge_data);
////            ResultSet rs_gdata = dh.getData(gauge_data, connect);
////            cmbPressuregage.getSelectionModel().clearSelection();
////            cmbPressuregage.getItems().clear();
////            while (rs_gdata.next()) {
////                cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
////            }
//        }
        String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 16 0 1";
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();
        read_test_data();
        txtunithydraulic.setText("psi");
        txtunithydro.setText("psi");
        pu = "psi";
    }

    @FXML
    private void radiokgAction(ActionEvent event) throws IOException, InterruptedException, SQLException {
//        if (radiokg.isSelected()) {
//            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 1";
//            Process child1 = Runtime.getRuntime().exec(cmd1);
//            child1.waitFor();
//            String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//            Process child2 = Runtime.getRuntime().exec(cmd2);
//            child2.waitFor();
//            String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//            Process child3 = Runtime.getRuntime().exec(cmd3);
//            child3.waitFor();
//            String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
////              txtHydroSetPressure.setText(cmd.substring(0,cmd.indexOf(".")));
//            uom = "kg/sq.cm";
//            txtHydroSetPressure.setText(cmd);
//
//            radiobar.setSelected(false);
//            radiopsi.setSelected(false);
////            String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
////            System.out.println("gauge_data" + gauge_data);
////            ResultSet rs_gdata = dh.getData(gauge_data, connect);
////            cmbPressuregage.getSelectionModel().clearSelection();
////            cmbPressuregage.getItems().clear();
////            while (rs_gdata.next()) {
////                cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
////            }
//        }
        String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 16 0 2";
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();
        read_test_data();
        txtunithydraulic.setText("kg/sqcm");
        txtunithydro.setText("kg/sqcm");
        pu = "kg/sqcm";

    }
    public static Stage catStage;

    @FXML
    private void btngaugeAction(ActionEvent event) throws IOException {
        //Open Gauge calibration window
        Parent root = FXMLLoader.load(getClass().getResource("gaugecalibration.fxml"));
        Platform.runLater(() -> {
//                        cmbTestStd.getSelectionModel().select(0);
            catStage = new Stage(StageStyle.UNDECORATED);
            catStage.setAlwaysOnTop(true);
            Scene scene = new Scene(root, 1330, 350);

            catStage.setScene(scene);
            catStage.show();
        });

    }

    @FXML
    private void cmbPressuregageAction(ActionEvent event) throws SQLException {
        String serial = cmbPressuregage.getSelectionModel().getSelectedItem();
        System.out.println("(cmbTestType.getSelectionModel().getSelectedItem().equals(\"PNEUMATIC  SEAT TEST\"))" +cmbTestType.getSelectionModel().getSelectedItem());
        if (cmbTestType.getSelectionModel().getSelectedItem().equals("PNEUMATIC  SEAT TEST")) {
            String range = "SELECT gauge_range FROM `gauge_description` WHERE gauge_description='Air Guage' ";
        System.out.println("range " + range);
        ResultSet rs_rang = dh.getData(range, connect);
        if (rs_rang.next()) {
            txtrange.setText(rs_rang.getString("gauge_range"));
        }
        }else{
            System.out.println("else part");
        String range = "SELECT gauge_range FROM `gauge_description` WHERE gauge_description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "' ";
        System.out.println("range " + range);
        ResultSet rs_rang = dh.getData(range, connect);
        if (rs_rang.next()) {
            txtrange.setText(rs_rang.getString("gauge_range"));
        }
        }
    }

    @FXML
    private void txtHydraulicSetPressureAction(ActionEvent event) throws InterruptedException {
        try {
            
            String test = txtHydraulicSetPressure.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc2.py 40 0 " + test;
            System.out.println("cmd_hydro : " + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtStabilizationTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String s_time1 = txtStabilization.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_Dword.py 52 0 " + s_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtDrainTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String d_time1 = txtStabilization.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_Dword.py 62 0 " + d_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbTypeOfSealingAction(ActionEvent event) {
        try {
            int index = cmbTypeOfSealing.getSelectionModel().getSelectedIndex();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 12 0 " + index;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbLeakageTypeAction(ActionEvent event) throws IOException, InterruptedException {

        String lt = cmbLeakageType.getSelectionModel().getSelectedItem();
        System.out.println("lt value :" + lt);
        Thread.sleep(50);
        if (lt.equals("NONE")) {
            try {
                txtAllowable.setVisible(false);
                txtAtcualBubble.setVisible(false);
                txtbubble.setVisible(false);
                String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 6 0";
                Process child = Runtime.getRuntime().exec(cmd);
                child.waitFor();
            } catch (IOException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 6 1";
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
            txtAllowable.setVisible(true);
            txtAtcualBubble.setVisible(true);
            txtbubble.setVisible(true);
        }

    }

    @FXML
    private void txtAllowableAction(ActionEvent event) {
        try {
            ToolKit.validateNumberField(txtAllowable);
            String count = txtAllowable.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 104 0 " + count;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtheatnoKeyPressed(KeyEvent event) {
        try {
            if (cmbTestType.getSelectionModel().getSelectedItem().equals("PNEUMATIC  SEAT TEST")) {
                ResultSet rs_p = dh.getData("SELECT serial FROM gauge_data WHERE description='Air Guage'", connect);
                System.out.println("SELECT serial FROM gauge_data WHERE description='Air Guage'");
                cmbPressuregage.getItems().clear();
                while (rs_p.next()) {
                    cmbPressuregage.getItems().addAll(rs_p.getString("serial"));
                }
            } else {
                ResultSet rs_p = dh.getData("SELECT serial FROM gauge_data WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'", connect);
                System.out.println("SELECT serial FROM gauge_data WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'");
                cmbPressuregage.getItems().clear();
                while (rs_p.next()) {
                    cmbPressuregage.getItems().addAll(rs_p.getString("serial"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void toggleOnlineOfflineAction(ActionEvent event) throws IOException, InterruptedException {
        if (toggleOnlineOffline.isSelected()) {
            txtOnlineOffline.setFill(Color.web("#0099FF"));
            txtOnlineOffline.setText("OFFLINE");
            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 7 1";
            Process child1 = Runtime.getRuntime().exec(cmd1);
            child1.waitFor();
//            read_onlline();
            read_test_data();
        } else {
            txtOnlineOffline.setFill(Color.web("#0099FF"));
            txtOnlineOffline.setText("ONLINE");
            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 7 0";
            Process child1 = Runtime.getRuntime().exec(cmd1);
            child1.waitFor();
            read_test_data();
        }
    }

    @FXML
    private void btnAlarmAction(ActionEvent event) {
        if (you_can_change) {
            try {
                dh.execute("TRUNCATE TABLE all_tag_read", connect);
                try {
                    mode.stop();
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                Platform.runLater(() -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                        ToolKit.loadScreen(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                ToolKit.unloadScreen(btnLogin);
            } catch (SQLException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 2);
        }
    }

    private class AddToQueue implements Runnable {

        String query = "SELECT * FROM `trend_data` WHERE `test_no`='" + test_no + "' ORDER BY id DESC LIMIT 1";

        ResultSet rs;

        @Override
        public void run() {
//run after some time 
            Platform.runLater(() -> {
                try {
                    // add a item of random data to queue.
                    if (start_trend) {
                        rs = dh.getData(query, connect);
                        if (rs.next()) {
                            double dq1 = 0.0;
                            double dq2 = 0.0;
                            try {
                                dq1 = Double.parseDouble(rs.getString("pressure"));
                                dq2 = Double.parseDouble(rs.getString("pressure"));
                            } catch (Exception e) {
                                System.err.println("This is an Error of Trend data where dq1 and dq2 defined: " + e.getMessage());
                            }

                            dataQ1.add(dq1);
                            dataQ2.add(dq2);
                        }

                        Thread.sleep(1000);
                        //System.out.println("running");
                        executor.execute(this);
                    } else {
                        if (count_executer_status > 1) {
                            //System.out.println("Stopping");
                            executor.shutdown();
                        } else {

                        }

                    }

                } catch (Exception ex) {
                    System.err.println("This is an Error of Trend data in second try below dq1 and dq2 defined: " + ex.getMessage());

                    executor.shutdown();
                    //kill the thread
//                 Thread.interrupted();
                }
            });
        }

    }

    private void prepareTimeline() {

        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();

    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            //-- add 20 numbers to the plot+
            if (dataQ1.isEmpty()) {
                break;
            }
            series1.getData().add(new XYChart.Data<>(Integer.parseInt(current_overall_timer), dataQ1.remove()));
            series2.getData().add(new XYChart.Data<>(Integer.parseInt(current_overall_timer), dataQ2.remove()));

        }

//        // update
        Platform.runLater(() -> {
            xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
            xAxis.setUpperBound(xSeriesData - 1);

        });

    }

    public void guage_initialize(int min, int max, int green, String unit) throws SQLException {
        String tt_d = cmbTestType.getSelectionModel().getSelectedItem();
        if (tt_d.contains("PNEUMATIC")) {
//            txtHydroSetPressure.promptTextProperty().set("PNEUMATIC TEST SET PRESSURE");
            textHydro.setText("PNEUMATIC TEST SET PRESSURE");
        } else {
//            txtHydroSetPressure.promptTextProperty().set("HYDRO TEST SET PRESSURE");
            textHydro.setText("HYDRO TEST SET PRESSURE");
        }
        Gaugehydro.clearAreas();
        Gaugehydro.clearTickMarkSections();
        Gaugehydro.clearTickLabelSections();
        Gaugehydro.clearSections();
        Gaugehydro.clearCustomTickLabels();
        textHydro.setVisible(true);
        textHydro1.setVisible(true);
        Gaugehydro.setVisible(true);

        GaugeActualHydraulic.clearAreas();
        GaugeActualHydraulic.clearTickMarkSections();
        GaugeActualHydraulic.clearTickLabelSections();
        GaugeActualHydraulic.clearSections();
        GaugeActualHydraulic.clearCustomTickLabels();
        GaugeActualHydraulic.setVisible(true);
        GaugeActualHydraulic.setUnit(unit);
        Gaugehydro.setUnit(unit);

//        String v_class = cmbValveClass.getSelectionModel().getSelectedItem();
//        String select_data = "SELECT minimum,green,maximum FROM gauge_range WHERE serial = '" + cmbPressuregage.getSelectionModel().getSelectedItem() + "' AND description='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND gauge_range LIKE '" + "%" + uom + "'; ";
//        System.out.println("select_data : " + select_data);
//        ResultSet result = dh.getData(select_data, connect);
//        if (result.next()) {
//
//            Gaugehydro.setUnit(uom.toUpperCase());
//
//            min = Integer.parseInt(result.getString("minimum"));
//            green = Integer.parseInt(result.getString("green"));
//            max = Integer.parseInt(result.getString("maximum"));
////            }
//        } else {
//            min = 0;
//            max = 0;
//            green = 0;
//        }
        try {
            //Hydro gauge visible property
            Gaugehydro.sectionsVisibleProperty().set(true);  //Hydro gauge max value
            Gaugehydro.setMaxValue(max);

            //Hydro gauge major tick space
            if (max < 100) {
                if (max % 10 == 0) {
                    Gaugehydro.setMajorTickSpace(10);

                    Gaugehydro.setMinorTickSpace(5);

                } else {
                    if (max < 10) {
                    } else {
                        int space = max / 10;
                        Gaugehydro.setMajorTickSpace(max / space);
                        Gaugehydro.setMinorTickSpace(1);
                    }
                }
            } else if (max > 1500) {
                Gaugehydro.setMajorTickSpace(max / 1000);
                Gaugehydro.setMinorTickSpace(250);
            } else if (max > 1000 && max < 1500) {
                Gaugehydro.setMajorTickSpace(max / 200);
                Gaugehydro.setMinorTickSpace(50);
            } else if (max > 500 && max < 1000) {
                Gaugehydro.setMajorTickSpace(max / 100);
                Gaugehydro.setMinorTickSpace(25);
            } else if (max < 500 && max > 100) {
                Gaugehydro.setMajorTickSpace(max / 50);
                Gaugehydro.setMinorTickSpace(15);
            }
            //Hydro gauge green zone range
            Gaugehydro.addSection(new Section(0, green, color.GaugeGreen));

            //Hydro gauge red zone range
            Gaugehydro.addSection(new Section(green + 1, max, color.GaugeRed));

            switch (unit) {
                case "bar":
                    gauge_clamping_red = 420;
                    bar_psi(420, 600);
                    break;
                case "psi":
                    gauge_clamping_red = 6090;
                    bar_psi(6090, 8700);
                    break;
                case "kg/sqcm":
                    gauge_clamping_red = 425;
                    bar_psi(425, 620);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            System.out.println("Error in gauge initialization " + e.getMessage());
        }

    }

    public void bar_psi(int green, int max) {

        GaugeActualHydraulic.setMaxValue(max);
//        GaugeSetHydraulic.setMaxValue(max);
        if (max < 100) {
            if (max % 10 == 0) {
                Platform.runLater(() -> {
                    GaugeActualHydraulic.setMajorTickSpace(10);
//                GaugeSetHydraulic.setMajorTickSpace(10);
                    GaugeActualHydraulic.setMinorTickSpace(5);
//                GaugeSetHydraulic.setMinorTickSpace(5);
                });
            } else {
                int space = max / 10;
                Platform.runLater(() -> {
                    GaugeActualHydraulic.setMajorTickSpace(max / space);
//                GaugeSetHydraulic.setMajorTickSpace(max / space);
                });
            }
        } else if (max > 1000) {
            Platform.runLater(() -> {
                GaugeActualHydraulic.setMajorTickSpace(max / 1000);
//            GaugeSetHydraulic.setMajorTickSpace(max / 1000);
                GaugeActualHydraulic.setMinorTickSpace(250);
//            GaugeSetHydraulic.setMinorTickSpace(250);
            });
        } else {
            Platform.runLater(() -> {
                GaugeActualHydraulic.setMajorTickSpace(max / 100);
//            GaugeSetHydraulic.setMajorTickSpace(max / 100);
                GaugeActualHydraulic.setMinorTickSpace(50);
//            GaugeSetHydraulic.setMinorTickSpace(50);
            });
        }

        //Clamping gauge visible properties
        GaugeActualHydraulic.sectionsVisibleProperty().set(true);
//        GaugeSetHydraulic.sectionsVisibleProperty().set(true);
        //Clamping gauge green zone range
        Platform.runLater(() -> {
            GaugeActualHydraulic.addSection(new Section(0, green, colors.GaugeGreen));
//        GaugeSetHydraulic.addSection(new Section(0, green, colors.GaugeGreen));
            //Clamping gauge red zone range
            GaugeActualHydraulic.addSection(new Section(green + 1, max, colors.GaugeRed));
//        GaugeSetHydraulic.addSection(new Section(green + 1, max, colors.GaugeRed));
            //Clamping gauge needle color
//        GaugeSetHydraulic.needleColorProperty().setValue(colors.black);
            GaugeActualHydraulic.needleColorProperty().setValue(colors.black);
        });
    }

    Thread mode;
    public static volatile boolean stop_mode = false;
    String over_all = "0";
    DoubleProperty HydroAct;
//    int firstBindingGauge = 0, hold_flag = 0, hold_flag_pop = 0;

    public String flag_test = "0";
    int check_drain = 0;

    private void screen_reopen() {
        try {

            mode.stop();
            cycleStatus.stop();
//            Thread.sleep(100);
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (IOException ex) {
                    Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
                }
                ToolKit.unloadScreen(btnAdmin);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        });

    }

    private void btnStopAction(ActionEvent event) throws InterruptedException, IOException {
        String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 1 7 1";
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();

        String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 1 7 0";
        Process child1 = Runtime.getRuntime().exec(cmd1);
        child1.waitFor();

        set_empty();
        enable_field();
        btnStart.setDisable(false);
        Gaugehydro.clearSections();
        Gaugehydro.setVisible(false);
        start_trend = false;
        textTrend.setVisible(false);
        textHydro.setVisible(false);
        textHydro1.setVisible(false);
        hboxtrend.setVisible(false);
//        hboxTrendText.setVisible(false);
//        vboxStatus.setVisible(false);
        stop_mode = true;
        you_can = true;
        System.out.println("Trend Low");
//        Background_Processes.stop_plc_read();
        txtCycleStatus.setText("");
        txtOverAllTime.setText("");
        txtHoldingTime.setText("");

//        txtremark.setText("");
        screen_reopen();
    }

    @FXML
    private void btnLoginAction(ActionEvent event) throws SQLException {
        if (you_can_change) {
            dh.execute("TRUNCATE TABLE all_tag_read", connect);
            try {
                mode.stop();
                cycleStatus.stop();
            } catch (Exception e) {
            }

            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 3);
        }
    }

    @FXML
    private void cmbValveTypeAction(ActionEvent event) {
        try {
            String v_type = Integer.toString(cmbValveType.getSelectionModel().getSelectedIndex());
            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_word.py 10 0 " + v_type;
            Process child1 = Runtime.getRuntime().exec(cmd1);
            child1.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbValveClassAction(ActionEvent event) throws SQLException {

//        Session.set("v_clas", v_clas);
        try {
            String v_clas = Integer.toString(cmbValveClass.getSelectionModel().getSelectedIndex());
            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_word.py 6 0 " + v_clas;
            Process child1 = Runtime.getRuntime().exec(cmd1);
            child1.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("cmbTestType.getSelectionModel().getSelectedItem() : " +cmbTestType.getSelectionModel().getSelectedItem());
        if (cmbTestType.getSelectionModel().getSelectedItem().equals("PNEUMATIC  SEAT TEST")) {
            
            ResultSet rs_p = dh.getData("SELECT serial FROM gauge_data WHERE description='Air Guage'", connect);
            System.out.println("SELECT serial FROM gauge_data WHERE description='Air Guage'");
            cmbPressuregage.getItems().clear();
            while (rs_p.next()) {
                cmbPressuregage.getItems().addAll(rs_p.getString("serial"));
            }
        } else {
            ResultSet rs_p = dh.getData("SELECT serial FROM gauge_data WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'", connect);
            System.out.println("SELECT serial FROM gauge_data WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'");
            cmbPressuregage.getItems().clear();
            while (rs_p.next()) {
                cmbPressuregage.getItems().addAll(rs_p.getString("serial"));
            }
        }
        cmbPressuregage.getSelectionModel().selectFirst();
        read_test_data();
    }

    @FXML
    private void cmbValveSizeAction(ActionEvent event) throws SQLException {

//        if (radiopsi.isSelected()) {
//            try {
//                String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 1";
//                Process child1 = Runtime.getRuntime().exec(cmd1);
//                child1.waitFor();
//                String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//                Process child2 = Runtime.getRuntime().exec(cmd2);
//                child2.waitFor();
//                String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//                Process child3 = Runtime.getRuntime().exec(cmd3);
//                child3.waitFor();
//                String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
////              txtHydroSetPressure.setText(cmd.substring(0,cmd.indexOf(".")));
//                uom = "psi";
//                txtHydroSetPressure.setText(cmd);
//
//                radiobar.setSelected(false);
//                radiokg.setSelected(false);
//
////                String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
////                System.out.println("gauge_data" + gauge_data);
////                ResultSet rs_gdata;
////
////                rs_gdata = dh.getData(gauge_data, connect);
////
////                cmbPressuregage.getSelectionModel().clearSelection();
////                cmbPressuregage.getItems().clear();
////                while (rs_gdata.next()) {
////                    cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
////                }
//            } catch (IOException ex) {
//                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (radiokg.isSelected()) {
//            try {
//                String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 1";
//                Process child1 = Runtime.getRuntime().exec(cmd1);
//                child1.waitFor();
//                String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 0";
//                Process child2 = Runtime.getRuntime().exec(cmd2);
//                child2.waitFor();
//                String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//                Process child3 = Runtime.getRuntime().exec(cmd3);
//                child3.waitFor();
//                String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
////              txtHydroSetPressure.setText(cmd.substring(0,cmd.indexOf(".")));
//                uom = "kg/sq.cm";
//                txtHydroSetPressure.setText(cmd);
//
//                radiobar.setSelected(false);
//                radiopsi.setSelected(false);
////                String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
////                System.out.println("gauge_data" + gauge_data);
////                ResultSet rs_gdata = dh.getData(gauge_data, connect);
////                cmbPressuregage.getSelectionModel().clearSelection();
////                cmbPressuregage.getItems().clear();
////                while (rs_gdata.next()) {
////                    cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
////                }
//            } catch (IOException ex) {
//                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (radiobar.isSelected()) {
//            try {
//                System.out.println("bar");
//                String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 0 1";
//                System.out.println("cmd1" + cmd1);
//                Process child1 = Runtime.getRuntime().exec(cmd1);
//                child1.waitFor();
//                String cmd2 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 1 0";
//                Process child2 = Runtime.getRuntime().exec(cmd2);
//                child2.waitFor();
//                String cmd3 = "python E:\\E1257\\python_plc\\write_plc_bool.py 0 2 0";
//                Process child3 = Runtime.getRuntime().exec(cmd3);
//                child3.waitFor();
//                String cmd = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_int.py 52 0").replace("/", "");
////               txtHydroSetPressure.setText(cmd.substring(0,cmd.indexOf(".")));
//                uom = "bar";
//                txtHydroSetPressure.setText(cmd);
//                radiopsi.setSelected(false);
//                radiokg.setSelected(false);
//                String gauge_data = "SELECT * FROM `gauge_range` where `description`='" + cmbValveSize.getSelectionModel().getSelectedItem() + "' AND `gauge_range`LIKE '" + "%" + uom + "'";
//                System.out.println("gauge_data" + gauge_data);
////                ResultSet rs_gdata = dh.getData(gauge_data, connect);
////                cmbPressuregage.getSelectionModel().clearSelection();
////                cmbPressuregage.getItems().clear();
////                while (rs_gdata.next()) {
////                    cmbPressuregage.getItems().add(rs_gdata.getString("serial"));
////                }
//            } catch (IOException ex) {
//                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//        Session.set("v_size", v_size);
        try {
            String v_size = Integer.toString(cmbValveSize.getSelectionModel().getSelectedIndex());

            String cmd1 = "python E:\\E1257\\python_plc\\write_plc_word.py 8 0 " + v_size;
            Process child1 = Runtime.getRuntime().exec(cmd1);
            child1.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
        read_test_data();

    }

    private void read_test_data() {
        try {
            String[] split1 = ToolKit.readpy("python E:\\E1257\\python_plc\\read_plc_5.py").split("/");
            Platform.runLater(() -> {
                txtHydroSetPressure.setText(Float.toString(Math.round(Float.parseFloat(split1[1]))));
                txtHydraulicSetPressure.setText(Float.toString(Math.round(Float.parseFloat(split1[2]))));
                txtStabilization.setText(split1[3]);
                txtHoldingTime.setText(split1[4]);
                txtDrainTime.setText(split1[5]);
//                if(split1[6].equals("True")){
//                    Dialog.showForSometime("alert", "INVALID SELECTION!!!", "WRONG SELECTION", 450, 5);
//                }
            });
        } catch (ArrayIndexOutOfBoundsException exception) {

        }

    }

    @FXML
    private void cmbTestStdAction(ActionEvent event) {
        int index = cmbTestStd.getSelectionModel().getSelectedIndex();
        if (cmbTestStd.getSelectionModel().getSelectedItem().equals("Customized")) {
            try {
                if (Session.get("catAccess").equals("granted")) {
                    String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 14 0 1";
                    Process child = Runtime.getRuntime().exec(cmd);
                    child.waitFor();
                    txtHoldingTime.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtHydroSetPressure.setEditable(true);
                    txtStabilization.setEditable(true);
                    txtDrainTime.setEditable(true);
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("CategoryLogin.fxml"));
                    Platform.runLater(() -> {
                        cmbTestStd.getSelectionModel().select(0);
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
                                        System.out.println("granted");
//                                        Thread.sleep(200);
//                                        write_node_value(index, "N7:6");
                                        Platform.runLater(() -> {
                                            cmbTestStd.getSelectionModel().select(1);
                                        });
                                        break;
                                    } else if (Session.get("catAccess").equals("not granted")) {
                                        System.out.println("not granted");
//                                        Thread.sleep(200);
                                        Platform.runLater(() -> {
                                            cmbTestStd.getSelectionModel().select(0);
                                        });
                                        break;
                                    } else {
                                        System.out.println("not");
                                        Thread.sleep(200);
                                    }
                                } catch (InterruptedException e) {
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
            try {
                String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 14 0 0";
                Process child = Runtime.getRuntime().exec(cmd);
                child.waitFor();
                txtHoldingTime.setEditable(false);
                txtHydraulicSetPressure.setEditable(false);
                txtHydroSetPressure.setEditable(false);
                txtStabilization.setEditable(false);
                txtDrainTime.setDisable(true);
            } catch (IOException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnReportAction(ActionEvent event) throws SQLException {
        if (you_can_change) {
            dh.execute("TRUNCATE TABLE all_tag_read", connect);
            try {
                mode.stop();
                cycleStatus.stop();
            } catch (Exception e) {
            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 2);
        }
    }

    @FXML
    private void btnAdminAction(ActionEvent event) throws SQLException {
        if (you_can_change) {
            dh.execute("TRUNCATE TABLE all_tag_read", connect);
            try {
                mode.stop();
                cycleStatus.stop();
            } catch (Exception e) {
            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 2);
        }
    }

    @FXML
    private void cmbTestTypeAction(ActionEvent event) throws InterruptedException {
        try {
//            String test = 
            String test = "";
            ResultSet rs_tt = dh.getData("SELECT write_value FROM test_type WHERE test_type ='" + cmbTestType.getSelectionModel().getSelectedItem() + "' ", connect);
            if (rs_tt.next()) {
                test = Integer.toString(rs_tt.getInt("write_value"));
            }

            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 4 0 " + test;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();

//            leackage_type();
            read_test_data();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tt_d = cmbTestType.getSelectionModel().getSelectedItem();
        if (tt_d.contains("PNEUMATIC")) {
            txtHydroSetPressure.promptTextProperty().set("PNEUMATIC TEST SET PRESSURE");
            textHydro.setText("PNEUMATIC TEST SET PRESSURE");
        } else {
            txtHydroSetPressure.promptTextProperty().set("HYDRO TEST SET PRESSURE");
            textHydro.setText("HYDROadmin   admin"
                    + " TEST SET PRESSURE");
        }
    }

    private void leackage_type() {
        try {
            String test_type = cmbTestType.getSelectionModel().getSelectedItem();
            ResultSet rs_lt = dh.getData("SELECT leakage_type FROM leakage_type WHERE test_type='" + test_type + "' order by leakage_type DESC ", connect);
            cmbLeakageType.getItems().clear();
            while (rs_lt.next()) {
                cmbLeakageType.getItems().addAll(rs_lt.getString("leakage_type"));
            }
            cmbLeakageType.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void txtHydroSetPressureAction(ActionEvent event) throws InterruptedException {

        try {
            String test = txtHydroSetPressure.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc2.py 44 0 " + test;
            System.out.println("cmd_hydro : " + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtHydroPressureKeyRelease(KeyEvent event) {
//        ToolKit.validateNumberField(txtHydroSetPressure);
//        String pressure = txtHydroSetPressure.getText();
//        try {
//            if (pressure.equals("")) {
//
//            } else {
//                String cmd = "python E:\\E1257\\python_plc\\write_tag_int.py 52 0 " + pressure;
//                Process child = Runtime.getRuntime().exec(cmd);
//                child.waitFor();
//                System.out.println("cmd id = " + cmd);
//            }
//        } catch (Exception e) {
//        }

    }

    @FXML
    private void txtHoldingTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String h_time1 = txtHoldingTime.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_Dword.py 56 0 " + h_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenControllerold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtHoldingTimeKeyRelease(KeyEvent event) {
        ToolKit.validateNumberField(txtHoldingTime);
        String hoding = txtHoldingTime.getText();

        if (hoding.equals("")) {

        } else {

        }
    }
}
