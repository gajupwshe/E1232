/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gajanan
 */
public class TestScreenController implements Initializable {

    @FXML
    private Text txtMode;
    @FXML
    private Text txtdate;
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
    private JFXTextField txtValveType;
    @FXML
    private JFXTextField txtValveSize;
    @FXML
    private JFXTextField txtValveClass;
    @FXML
    private JFXTextField txtTypeOfSealing;
    @FXML
    private JFXTextField txtTestType;
    @FXML
    private JFXTextField txtTypeOfLeakage;
    @FXML
    private HBox hboxgauge;
    @FXML
    private VBox vboxgauge;
    @FXML
    private GridPane gridviewgauge;
    @FXML
    private Gauge GaugeSetHydraulic;
    @FXML
    private Gauge GaugeActualHydraulic;
    @FXML
    private Text txtLabelSetGauge;
    @FXML
    private Gauge GaugeSetHydro;
    @FXML
    private Text txtLabelgaugeAside;
    @FXML
    private Gauge GaugeActualHydro_A;
    @FXML
    private Text textHydrobside;
    @FXML
    private Gauge GaugeActualHydro_B;
    @FXML
    private HBox hboxtimers;
    @FXML
    private VBox vboxtimers;
    @FXML
    private JFXTextField txtActualLeakage;
    @FXML
    private JFXTextField txtCycleStatus;
    @FXML
    private JFXTextField txtDrainTimer;
    @FXML
    private JFXTextField txtOverAllTime;
    @FXML
    private JFXTextField txtHoldingTimer;
    @FXML
    private JFXTextField txtStabilizationTimer;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private HBox sectionFooter;

    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();

    //Trend Chart Component
    private final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
    private ExecutorService executor;
    private final ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();

    private final ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();
    private NumberAxis xAxis;
    Thread mode, cycleStatus;
    public static volatile boolean stop_mode = false, stopCycleStatusThread;
    private boolean start_trend = false;

    Boolean first_pop_lock = false, second_pop_lock = false, third_pop_lock = false, you_can_change = true, stop_pressure_get = true;
    ;

    String mac_mode = "4545", off_mode = "4545", cycl_status = "0", s_time = "0", h_time = "0", d_d = "0", d_time = "0", over_all_time = "0", clampingActualPressure = "0", pressure_a = "0", pressure_b = "0", popUps = "0", result = "4545", bubble_counter = "0", current_machine_mode, current_offline_mode, current_over_all_timer, start_pressure_a, start_pressure_b, start_pressure_c, start_pressure_d, start_pressure_e, stop_pressure_a, stop_pressure_b, stop_pressure_c, stop_pressure_d, stop_pressure_e, current_cycle_status, test_type = "1", test_no, holding_time, current_stabilization_timer, current_holding_timer, current_drain_delay, current_drain_timer, pressure_a_side = "0", pressure_b_side = "0", vsn, oat;

    DoubleProperty ClampingAct, HydroActA, HydroActB;

    int delete_count = 0, test_result_count = 0, test_result_by_type_check = 0, count_result = 0, gauge_clamping_red, gauge_hydro_red;

    @FXML
    private Text txtOffline;
    @FXML
    private JFXTextField txtResult;
    Colors color = new Colors();
    public static Stage catStage;
    LineChart<Number, Number> lineChart;
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

            if (Session.get("user_type").equals("admin")) {
                btnAdmin.setVisible(true);
            } else {
                btnAdmin.setVisible(false);
            }

            date_time();
            Background_Processes.insert_plc_data("python E:\\E1232\\python_plc\\insert_test_tags.py", false, true);
            machine_mode();
            cycleStatusThread();
            trend_initialize();

            guage_initialize(0, Integer.parseInt(Session.get("max")), Integer.parseInt(Session.get("green")), Session.get("pu"));

            ResultSet rs_get = dh.getData("SELECT * FROM `valve_data` ORDER BY `id` DESC LIMIT 1", connect);
            if (rs_get.next()) {
                test_no = rs_get.getString("test_no");
                int no = Integer.parseInt(test_no);
                test_no = Integer.toString(no);
                vsn = rs_get.getString("vsn");
            }
            ResultSet rs = dh.getData("SELECT * FROM `initial_init` ORDER BY `id` DESC LIMIT 1", connect);
            if (rs.next()) {
                switch (rs.getString("test_type")) {
                    case "0":
                        txtTestType.setText("HYDROSTATIC SHELL");
                        break;
                    case "1":
                        txtTestType.setText("HYDROSTATIC SEAT A SIDE");
                        break;
                    case "2":
                        txtTestType.setText("HYDROSTATIC SEAT B SIDE");
                        break;
                    case "3":
                        txtTestType.setText("PNEUMATIC SEAT A SIDE");
                        break;
                    case "4":
                        txtTestType.setText("PNEUMATIC SEAT B SIDE");
                        break;
                    case "5":
                        txtTestType.setText("BACKSEAT");
                        break;
                    case "6":
                        txtTestType.setText("SIMULTANEOUS SEAT TEST HYDRO");
                        break;
                    case "7":
                        txtTestType.setText("SIMULTANEOUS SEAT AIR HYDRO");
                        break;

                }

                switch (rs.getString("valve_type")) {
                    case "0":
                        txtValveType.setText("GATE VALVE");
                        break;
                    case "1":
                        txtValveType.setText("GLOBE VALVE");
                        break;
                    case "2":
                        txtValveType.setText("BUTTERFLY VALVE");
                        break;
                    case "3":
                        txtValveType.setText("BALL VALVE");
                        break;
                    case "4":
                        txtValveType.setText("CHECK VALVE");
                        break;
                }
            }
            txtValveClass.setText(Session.get("vc"));
            txtTypeOfLeakage.setText(Session.get("tos"));
            txtTypeOfSealing.setText(Session.get("lt"));
            txtValveSize.setText(Session.get("vs"));
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
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
            txtdate.setText("Date:" + dtf.format(now));

        }
    };

    public void guage_initialize(int min, int max, int green, String unit) throws SQLException {

//        switch (test_type) {
//
//            case "3":
//                txtLabelgaugeAside.setText("PNEUMATIC SEAT A SIDE");
//                break;
//            case "4":
//                textHydrobside.setText("PNEUMATIC SEAT B SIDE");
//                break;
//            case "5":
//                txtLabelgaugeAside.setText("NITROGEN SEAT A SIDE");
//                break;
//            case "6":
//                textHydrobside.setText("NITROGEN SEAT b SIDE");
//                break;
//            case "7":
//                txtLabelgaugeAside.setText("NITROGEN SHELL");
//                break;
//            default:
//                break;
//        }
//        }
        GaugeActualHydro_A.clearSections();
        GaugeActualHydro_A.clearTickMarkSections();
        GaugeActualHydro_A.clearMarkers();
        GaugeActualHydro_A.clearCustomTickLabels();
        GaugeActualHydro_B.clearSections();
        GaugeActualHydro_B.clearTickMarkSections();
        GaugeActualHydro_B.clearMarkers();
        GaugeActualHydro_B.clearCustomTickLabels();
        GaugeActualHydro_A.setDecimals(1);
        GaugeActualHydro_B.setDecimals(1);
        GaugeSetHydro.setDecimals(1);
        GaugeSetHydraulic.setDecimals(1);
        GaugeActualHydraulic.setDecimals(1);

        GaugeSetHydro.setValue(Double.parseDouble(Session.get("hsp")));
        GaugeSetHydraulic.setValue(Double.parseDouble(Session.get("hlsp")));
        System.out.println(Session.get("tt"));
        if (Session.get("tt").equals("HYDROSTATIC SHELL") || Session.get("tt").equals("HYDROSTATIC SEAT A SIDE") || Session.get("tt").equals("PNEUMATIC SEAT A SIDE")) {
            System.out.println(Session.get("test_type"));
            GaugeActualHydro_A.setVisible(true);
            txtLabelgaugeAside.setVisible(true);
            GaugeActualHydro_B.setVisible(false);
            textHydrobside.setVisible(false);
        } else if (Session.get("tt").equals("HYDROSTATIC SEAT B SIDE") || Session.get("tt").equals("PNEUMATIC SEAT B SIDE")) {
            GaugeActualHydro_A.setVisible(false);
            GaugeActualHydro_B.setVisible(true);
            textHydrobside.setVisible(true);
            txtLabelgaugeAside.setVisible(false);
        } else if (Session.get("tt").equals("SIMULTANEOUS SEAT TEST HYDRO") || Session.get("tt").equals("SIMULTANEOUS SEAT TEST AIR")) {
            GaugeActualHydro_A.setVisible(true);
            GaugeActualHydro_B.setVisible(true);
            textHydrobside.setVisible(true);
            txtLabelgaugeAside.setVisible(true);
        } else {
            GaugeActualHydro_B.setVisible(false);
            textHydrobside.setVisible(false);
        }
        GaugeSetHydro.setUnit(unit);
        GaugeActualHydro_A.setUnit(unit);
        GaugeActualHydro_B.setUnit(unit);
        GaugeActualHydraulic.setUnit(unit);
        try {
            //Hydro gauge visible property
            GaugeSetHydro.sectionsVisibleProperty().set(true);  //Hydro gauge max value
            GaugeSetHydro.setMaxValue(max);

            GaugeActualHydro_A.sectionsVisibleProperty().set(true);  //Hydro gauge max value
            GaugeActualHydro_A.setMaxValue(max);

            GaugeActualHydro_B.sectionsVisibleProperty().set(true);  //Hydro gauge max value
            GaugeActualHydro_B.setMaxValue(max);

            //Hydro gauge major tick space
//            if (max < 100) {
//                if (max % 10 == 0) {
//                    GaugeSetHydro.setMajorTickSpace(10);
//                    GaugeSetHydro.setMinorTickSpace(5);
//
//                    GaugeActualHydro_A.setMajorTickSpace(10);
//                    GaugeActualHydro_A.setMinorTickSpace(5);
//
//                    GaugeActualHydro_B.setMajorTickSpace(10);
//                    GaugeActualHydro_B.setMinorTickSpace(5);
//                } else {
//                    if (max < 10) {
//                    } else {
//                        int space = max / 3;
//                        GaugeSetHydro.setMajorTickSpace(max / space);
//                        GaugeSetHydro.setMinorTickSpace(1);
//
//                        GaugeActualHydro_A.setMajorTickSpace(max / space);
//                        GaugeActualHydro_A.setMinorTickSpace(1);
//
//                        GaugeActualHydro_B.setMajorTickSpace(max / space);
//                        GaugeActualHydro_B.setMinorTickSpace(1);
//                    }
//                }
//            } else if (max > 1500) {
//                GaugeSetHydro.setMajorTickSpace(max / 1000);
//                GaugeSetHydro.setMinorTickSpace(500);
//
//                GaugeActualHydro_A.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_A.setMinorTickSpace(500);
//
//                GaugeActualHydro_B.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_B.setMinorTickSpace(500);
//            } else if (max > 1000 && max < 1500) {
//                GaugeSetHydro.setMajorTickSpace(max / 1000);
//                GaugeSetHydro.setMinorTickSpace(250);
//
//                GaugeActualHydro_A.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_A.setMinorTickSpace(250);
//
//                GaugeActualHydro_B.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_B.setMinorTickSpace(250);
//            } else if (max > 500 && max < 1000) {
//                GaugeSetHydro.setMajorTickSpace(max / 1000);
//                GaugeSetHydro.setMinorTickSpace(500);
//
//                GaugeActualHydro_A.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_A.setMinorTickSpace(500);
//
//                GaugeActualHydro_B.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_B.setMinorTickSpace(500);
//            } else if (max < 500 && max > 100) {
//                GaugeSetHydro.setMajorTickSpace(max / 1000);
//                GaugeSetHydro.setMinorTickSpace(500);
//
//                GaugeActualHydro_A.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_A.setMinorTickSpace(500);
//
//                GaugeActualHydro_B.setMajorTickSpace(max / 1000);
//                GaugeActualHydro_B.setMinorTickSpace(500);
//            }
if (max < 100) {
                if (max % 10 == 0) {
                    GaugeSetHydro.setMajorTickSpace(10);
                    GaugeActualHydro_B.setMajorTickSpace(10);
                    GaugeActualHydro_A.setMajorTickSpace(10);
                    GaugeSetHydro.setMinorTickSpace(5);
                    GaugeActualHydro_B.setMinorTickSpace(5);
                    GaugeActualHydro_A.setMinorTickSpace(5);
                } else {
                    if (max < 10) {
                    } else {
                        int space = max / 10;
                        GaugeSetHydro.setMajorTickSpace(max / space);
                        GaugeActualHydro_B.setMajorTickSpace(max / space);
                        GaugeActualHydro_A.setMajorTickSpace(max / space);
                        GaugeSetHydro.setMinorTickSpace(1);
                        GaugeActualHydro_B.setMinorTickSpace(1);
                        GaugeActualHydro_A.setMinorTickSpace(1);
                    }

                }
            } else if (max > 1500) {
                GaugeSetHydro.setMajorTickSpace(max / 1200);
                GaugeActualHydro_B.setMajorTickSpace(max / 1200);
                GaugeActualHydro_A.setMajorTickSpace(max / 1200);
                GaugeSetHydro.setMinorTickSpace(250);
                GaugeActualHydro_B.setMinorTickSpace(250);
                GaugeActualHydro_A.setMinorTickSpace(250);
            } else if (max > 1000 && max < 1500) {
                GaugeSetHydro.setMajorTickSpace(max / 200);
                GaugeActualHydro_B.setMajorTickSpace(max / 200);
                GaugeActualHydro_A.setMajorTickSpace(max / 200);
                GaugeSetHydro.setMinorTickSpace(50);
                GaugeActualHydro_B.setMinorTickSpace(50);
                GaugeActualHydro_A.setMinorTickSpace(50);
            } else if (max > 500 && max < 1000) {
                GaugeSetHydro.setMajorTickSpace(max / 100);
                GaugeActualHydro_B.setMajorTickSpace(max / 100);
                GaugeActualHydro_A.setMajorTickSpace(max / 100);
                GaugeSetHydro.setMinorTickSpace(25);
                GaugeActualHydro_B.setMinorTickSpace(25);
                GaugeActualHydro_A.setMinorTickSpace(25);
            } else if (max < 500 && max > 100) {
                GaugeSetHydro.setMajorTickSpace(max / 50);
                GaugeActualHydro_B.setMajorTickSpace(max / 50);
                GaugeActualHydro_A.setMajorTickSpace(max / 50);
                GaugeSetHydro.setMinorTickSpace(15);
                GaugeActualHydro_B.setMinorTickSpace(15);
                GaugeActualHydro_A.setMinorTickSpace(15);
            }

            //Hydro gauge green zone range
            GaugeSetHydro.addSection(new Section(0, green + 1, color.GaugeGreen));

            //Hydro gauge red zone range
            GaugeSetHydro.addSection(new Section(green + 1, max, color.GaugeRed));

            //Hydro gauge green zone range
            GaugeActualHydro_A.addSection(new Section(0, green + 1, color.GaugeGreen));

            //Hydro gauge red zone range
            GaugeActualHydro_A.addSection(new Section(green + 1, max, color.GaugeRed));

            //Hydro gauge green zone range
            GaugeActualHydro_B.addSection(new Section(0, green + 1, color.GaugeGreen));

            //Hydro gauge red zone range
            GaugeActualHydro_B.addSection(new Section(green + 1, max, color.GaugeRed));

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

        GaugeSetHydraulic.setMaxValue(max);
        GaugeActualHydraulic.setMaxValue(max);
//        GaugeSetHydraulic.setMaxValue(max);
        if (max < 100) {
            if (max % 10 == 0) {
                Platform.runLater(() -> {
                    GaugeSetHydraulic.setMajorTickSpace(10);
                    GaugeActualHydraulic.setMajorTickSpace(10);
                    GaugeSetHydraulic.setMinorTickSpace(5);
                    GaugeActualHydraulic.setMinorTickSpace(5);
                });
            } else {
                int space = max / 10;
                Platform.runLater(() -> {
                    GaugeSetHydraulic.setMajorTickSpace(max / space);
                    GaugeActualHydraulic.setMajorTickSpace(max / space);
                });
            }
        } else if (max > 1000) {
            Platform.runLater(() -> {
                GaugeSetHydraulic.setMajorTickSpace(max / 1000);
                GaugeActualHydraulic.setMajorTickSpace(max / 1000);
                GaugeSetHydraulic.setMinorTickSpace(250);
                GaugeActualHydraulic.setMinorTickSpace(250);
            });
        } else {
            Platform.runLater(() -> {
                GaugeSetHydraulic.setMajorTickSpace(max / 100);
                GaugeActualHydraulic.setMajorTickSpace(max / 100);
                GaugeSetHydraulic.setMinorTickSpace(50);
                GaugeActualHydraulic.setMinorTickSpace(50);
            });
        }

        //Clamping gauge visible properties
        GaugeSetHydraulic.sectionsVisibleProperty().set(true);
        GaugeActualHydraulic.sectionsVisibleProperty().set(true);
        //Clamping gauge green zone range
        Platform.runLater(() -> {
            GaugeSetHydraulic.addSection(new Section(0, green, color.GaugeGreen));
            GaugeActualHydraulic.addSection(new Section(0, green, color.GaugeGreen));
            //Clamping gauge red zone range
            GaugeSetHydraulic.addSection(new Section(green + 1, max, color.GaugeRed));
            GaugeActualHydraulic.addSection(new Section(green + 1, max, color.GaugeRed));
            //Clamping gauge needle color
            GaugeActualHydraulic.needleColorProperty().setValue(color.black);
            GaugeSetHydraulic.needleColorProperty().setValue(color.black);
        });
    }

    NumberAxis yAxis;

    private void trend_initialize() throws IOException {
        //            AnchorPane trend = FXMLLoader.load(getClass().getResource("TrendScreen.fxml"));
        try {
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
            String test_type = txtTestType.getText();
            lineChart.setAnimated(false);
            lineChart.setTitle("");
            lineChart.getData().clear();
//            if (!lineChart.getData().isEmpty()) {
//                System.out.println("Remove Series");
//        lineChart.getData().remove(/*(lineChart.getData().size()-1)*/0);
//    }
            series1.getData().clear();
            series2.getData().clear();
            series3.getData().clear();
            series1.getData().removeAll();
            series2.getData().removeAll();
            series3.getData().removeAll();
            lineChart.setHorizontalGridLinesVisible(true);
            if (test_type.equals("HYDROSTATIC SHELL") || test_type.equals("BACK SEAT TEST")) {
                series1.setName("Hydro Pressure");
                series3.setName("Hydraulic Pressure");

                lineChart.getData().addAll(series1, series3);

            } else {
                // Set Name for Series
                series1.setName("Hydro Pressure A Side");
                series2.setName("Hydro Pressure B Side");
                series3.setName("Hydraulic Pressure");
                // Add Chart Series
                lineChart.getData().addAll(series1, series3, series2);
            }
            drawer.setMinWidth(400);
            drawer.setSidePane(lineChart);
            drawer.setOverLayVisible(false);

        } catch (Exception e) {
        }

//        HamburgerBackArrowBasicTransition burgermove = new HamburgerBackArrowBasicTransition(burger);
//        burgermove.setRate(-1);
//        burger.addEventHandler(MouseEvent.MOUSE_PRESSED, (evt) -> {
////            burgermove.setRate(burgermove.getRate() * -1);
////            burgermove.play();
//            if (drawer.isShown()) {
//                drawer.close();
//            } else {
//                drawer.open();
//            }
//
//        });
//        AnchorPane FinePane = FXMLLoader.load(getClass().getResource("FineTuning.fxml"));
//        finedrawer.setSidePane(FinePane);
//        finedrawer.setOverLayVisible(false);
    }
    int count_executer_status = 0;
    boolean initial_start_trend = true;

    private void start_trend() {
        //For UI updation
//        Platform.runLater(() -> {
        if (initial_start_trend) {
            initial_start_trend = false;
        } else {
            xSeriesData = 0;
            System.out.println("Clearing dataQue");
            dataQ1.clear();
            dataQ2.clear();
            dataQ3.clear();
//            Platform.runLater(() -> {
            series1.getData().clear();
            series2.getData().clear();
            series3.getData().clear();
            series1.getData().removeAll(dataQ1);
            series2.getData().removeAll(dataQ2);
            series3.getData().removeAll(dataQ3);

//            });
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

    @FXML
    private void toggleAsideAction(ActionEvent event) throws SQLException {
        if (toggleAside.isSelected()) {
            dh.execute("UPDATE pop_value set toggelA='1' WHERE id='1'", connect);

        } else {
            dh.execute("UPDATE pop_value set toggelA='0' WHERE id='1'", connect);
        }
    }

    @FXML
    private void toggleBsideAction(ActionEvent event) throws SQLException {
        if (toggleBside.isSelected()) {
            dh.execute("UPDATE pop_value set toggelB='1' WHERE id='1'", connect);

        } else {
            dh.execute("UPDATE pop_value set toggelB='0' WHERE id='1'", connect);
        }
    }

    private class AddToQueue implements Runnable {

        String query = "SELECT * FROM test_tags ORDER BY id DESC LIMIT 1";
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
                            double dq3 = 0.0;
                            try {
                                dq1 = Double.parseDouble(rs.getString("hydro_actual_a"));
                                dq2 = Double.parseDouble(rs.getString("hydro_actual_b"));
                                dq3 = Double.parseDouble(rs.getString("hydraulic_actual"));
                            } catch (Exception e) {
                                System.err.println("This is an Error of Trend data where dq1 and dq2 defined: " + e.getMessage());
                            }
//                            System.out.println("dq1 : " + dq1);
                            dataQ1.add(dq1);
                            dataQ2.add(dq2);
                            dataQ3.add(dq3);
                        }

                        Thread.sleep(100);
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

    //-- Timeline gets called in the JavaFX Main thread
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
//            Platform.runLater(() -> {
            series1.getData().add(new XYChart.Data<>(Integer.parseInt(current_over_all_timer), dataQ1.remove()));
            series2.getData().add(new XYChart.Data<>(Integer.parseInt(current_over_all_timer), dataQ2.remove()));
            series3.getData().add(new XYChart.Data<>(Integer.parseInt(current_over_all_timer), dataQ3.remove()));
//            });
        }
//            Iterator<Float> iterator = avgWeighingScaleReading.iterator();
//            if(iterator.hasNext()){
//                Float adArr = iterator.next();
//                iterator.remove();
//            }
//            Iterator<Number> iterator1=dataQ1.iterator();
//            if(iterator1.hasNext()){
//                Number dat1=iterator1.next();
//                iterator.remove();
//            }
//            Iterator<Number> iterator2=dataQ2.iterator();
//            if(iterator2.hasNext()){
//                Number dat2=iterator1.next();
//                iterator.remove();
//            }
//            

//        // update
        Platform.runLater(() -> {
            xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
            xAxis.setUpperBound(xSeriesData - 1);

        });

    }

    private void machine_mode() {
        mode = new Thread(() -> {
            String display = "SELECT * FROM `test_tags` ORDER BY id DESC LIMIT 1";
            ResultSet rs;

            while (true) {
                try {
                    if (stop_mode) {
                        mode.stop();
                        break;
                    }

                    mode.sleep(200);
//                    rs = dh.getData("SELECT * FROM `all_tag_read` ORDER BY id DESC", connect);
                    rs = dh.getData(display, connect);
//                    System.out.println("query : SELECT * FROM `all_tag_read` ORDER BY id DESC");
                    if (rs.next()) {
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
                    }
                    if (mac_mode.equals(current_machine_mode)) {
                    } else {
                        mode(mac_mode);
                    }

                    if (off_mode.equals(current_offline_mode)) {
                    } else {
                        offline_online(off_mode);
                    }
                    if (ToolKit.isNull(rs.getString("cycle_status"))) {
                        System.out.println("NULL cycle_status");
                    } else {
                        cycl_status = rs.getString("cycle_status");
                    }
                    if (ToolKit.isNull(rs.getString("stabilization"))) {
                        System.out.println("NULL stabilization");
                    } else {
                        s_time = rs.getString("stabilization");
                    }
                    if (ToolKit.isNull(rs.getString("holding"))) {
                        System.out.println("NULL holding");
                    } else {
                        h_time = rs.getString("holding");
                    }
                    if (ToolKit.isNull(rs.getString("drain_delay"))) {
                        System.out.println("NULL drain_delay");
                    } else {
                        d_d = rs.getString("drain_delay");
                    }
                    if (ToolKit.isNull(rs.getString("drain"))) {
                        System.out.println("NULL drain_timer");
                    } else {
                        d_time = rs.getString("drain");
                    }
                    if (ToolKit.isNull(rs.getString("overall_time"))) {
                        System.out.println("NULL over_all_time");
                    } else {
                        over_all_time = rs.getString("overall_time");
                    }
                    if (ToolKit.isNull(rs.getString("hydraulic_actual"))) {
                        System.out.println("NULL hydraulic_actual");
                    } else {
                        clampingActualPressure = rs.getString("hydraulic_actual");
                    }
                    if (ToolKit.isNull(rs.getString("hydro_actual_a"))) {
                        System.out.println("NULL hydro_actual_a");
                    } else {
                        pressure_a = rs.getString("hydro_actual_a");
                    }
                    if (ToolKit.isNull(rs.getString("hydro_actual_b"))) {
                        System.out.println("NULL hydro_actual_b");
                    } else {
                        pressure_b = rs.getString("hydro_actual_b");
                    }
                    if (ToolKit.isNull(rs.getString("pop_ups"))) {
                        System.out.println("NULL pop_up");
                    } else {
                        popUps = rs.getString("pop_ups");
                    }
                    if (ToolKit.isNull(rs.getString("result"))) {
                        System.out.println("NULL result");
                    } else {
                        result = rs.getString("result");
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

//                        Platform.runLater(() -> {
                        GaugeActualHydraulic.valueProperty().bind(ClampingAct);
                        GaugeActualHydro_A.valueProperty().bind(HydroActA);
                        GaugeActualHydro_B.valueProperty().bind(HydroActB);

//                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION IN SETTING VALUE OF GAUGES IN DATA_UPDATE_THREAD : " + e.getMessage());
//                            GaugeActualHydraulic.setValue(hydroB);
                    }

                    if (ToolKit.isNull(over_all_time)) {
                    } else {

                        if (over_all_time.equals(current_over_all_timer)) {
                        } else {
                            Platform.runLater(() -> {
                                txtOverAllTime.setText(over_all_time);
                            });
                            current_over_all_timer = over_all_time;
//                            dh.execute("INSERT INTO trend_data(`hydro_pressure`,`test_timer`,`test_no`,`test_type`)VALUES('"+pressure_a+"','"+over_all_time+"','"+ Session.get("test_no") + "','"+txtTestType.getText()+"')", connect);
//                            System.out.println("INSERT INTO graph_generation (`test_no`,`component_serial_no`,`time`,`pressure`,`unit`,`hold_start`,`date_time`)VALUES('" + Session.get("test_no") + "','" + Session.get("csn") + "','" + over_all_time + "','" + pressure_a + "','" + Session.get("pu") + "','" + h_time + "',NOW())");
                            if (txtTestType.getText().equals("HYDROSTATIC SHELL") || txtTestType.getText().equals("HYDROSTATIC SEAT A SIDE") || txtTestType.getText().equals("PNEUMATIC SEAT A SIDE")) {
//                                System.out.println("equal b");
                                dh.execute("INSERT INTO graph_generation (`test_no`,`component_serial_no`,`time`,`pressure`,`unit`,`hold_start`,`test_type`,`date_time`)VALUES('" + test_no + "','" + vsn + "','" + over_all_time + "','" + pressure_a + "','" + Session.get("pu") + "','" + h_time + "','" + txtTestType.getText() + "',NOW())", connect);
                            } else {
//                                System.out.println("equal nt b");
                                dh.execute("INSERT INTO graph_generation (`test_no`,`component_serial_no`,`time`,`pressure`,`unit`,`hold_start`,`test_type`,`date_time`)VALUES('" + test_no + "','" + vsn + "','" + over_all_time + "','" + pressure_b + "','" + Session.get("pu") + "','" + h_time + "','" + txtTestType.getText() + "',NOW())", connect);
                            }

                        }
                    }

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
                                    holding_time = Integer.toString(Integer.parseInt(txtHoldingTimer.getText()) + 1);

//                                        imgDrainDelay.setVisible(false);
                                    pop_up_timer("Start Drain Timer", 300, 0, "N7:9");
                                    third_pop_lock = true;
//                                    third_pop_lock = true;
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
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        mode.start();
    }

    private void pop_up_start(String message, int width, int yes, int no, String tag) {
        Platform.runLater(() -> {
            Optional<ButtonType> option = Dialog.ConfirmationDialog("CONFIRMATION", message, width);
            if (option.get() == ButtonType.YES) {
                try {
                    System.out.println("YES PRESSED");
                    //                    mode.sleep(50);
//                    ToolKit.tagWrite(tag, Integer.toString(yes));
                    dh.execute("UPDATE pop_value set cycle_start='0' WHERE id='1' ", connect);
                    delete_count = 0;
                    test_result_count = 0;
                    stop_pressure_get = true;
                    test_result_by_type_check++;
//                    start_trend = true;
//                    stopCycleStatusThread = false;
//                    trend_initialize();
//                    start_trend();
                    Session.set("Trend_Status", "Running");
                    trend_initialize();
                    start_trend();
                    start_trend = true;
                } catch (SQLException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (option.get() == ButtonType.NO) {
                try {
                    System.out.println("NO PRESSED");
                    you_can_change = true;
                    mode.sleep(50);
//                    ToolKit.tagWrite(tag, Integer.toString(no));
                    dh.execute("UPDATE pop_value set cycle_start='4' WHERE id='1' ", connect);
                    Session.set("Trend_Status", "Stopped");
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void pop_up_timer(String message, int width, int ok, String tag) {
        Platform.runLater(() -> {
            Optional<ButtonType> option = Dialog.ConfirmationDialog_Single_button("CONFIRMATION", message, width);
            if (option.get() == ButtonType.OK) {
                //                    mode.sleep(50);
//                ToolKit.tagWrite(tag, Integer.toString(ok));
                if (message.equals("Start Hodling Timer")) {
                    try {
                        dh.execute("UPDATE pop_value set holding='0' WHERE id='1' ", connect);
                    } catch (SQLException ex) {
                        Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (txtTestType.getText().equals("HYDROSTATIC SEAT C SIDE")) {
                        start_pressure_a = pressure_b;
                        start_pressure_b = pressure_b;
                        start_pressure_c = pressure_a;
                        start_pressure_d = pressure_b;
                        start_pressure_e = pressure_b;
                    } else if (txtTestType.getText().equals("HYDROSTATIC SEAT D SIDE")) {
                        start_pressure_a = pressure_a;
                        start_pressure_b = pressure_a;
                        start_pressure_c = pressure_a;
                        start_pressure_d = pressure_b;
                        start_pressure_e = pressure_a;
                    } else if (txtTestType.getText().equals("HYDROSTATIC SEAT E SIDE")) {
                        start_pressure_a = pressure_b;
                        start_pressure_b = pressure_b;
                        start_pressure_c = pressure_b;
                        start_pressure_d = pressure_b;
                        start_pressure_e = pressure_a;
                    } else {

                        start_pressure_a = pressure_a;
                        start_pressure_b = pressure_b;
                        start_pressure_c = "0";
                        start_pressure_d = "0";
                        start_pressure_e = "0";
//                        leakageCheck();
                    }
//                } else if (message.equals("Click YES To Restart the holding Time Or Click NO to Start Drain Timer")) {
//                    try {
//                        dh.execute("UPDATE pop_value set drain='0' WHERE id='1' ", connect);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
                if (message.equals("Start Drain Timer")) {
                    try {
                        dh.execute("UPDATE pop_value set drain='0' WHERE id='1' ", connect);
                    } catch (SQLException ex) {
                        Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Session.set("Trend_Status", "Running");
            }
        });
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
                current_offline_mode = "2";
                break;
        }
    }

    private void mode(String mode) {
        switch (mode) {
//            case "0":
//                txtMode.setText("Auto Mode");
//                current_machine_mode = "0";
//                txtdate.setFill(Color.web("#0099FF"));
//
//                break;
//            case "1":
//                txtMode.setText("Manual Mode");
//                current_machine_mode = "1";
//                txtdate.setFill(Color.web("#0099FF"));
//
//                break;
//            case "2":
//                txtMode.setText("Emergency Mode");
//                current_machine_mode = "2";
//                txtdate.setFill(Color.web("#C32420"));
//                break;
//            default:
//                txtMode.setText("Something wrong");
//                current_machine_mode = "0";
//                txtdate.setFill(Color.web("#C32420"));
//
//                break;
            case "0":
                txtMode.setText("Emergency Mode");
                current_machine_mode = "0";
                txtdate.setFill(Color.web("#C32420"));
                toggleAside.setDisable(true);
                toggleBside.setDisable(true);
                break;
            case "1":
                txtMode.setText("PT Error");
                current_machine_mode = "1";
                txtdate.setFill(Color.web("#C32420"));
                toggleAside.setDisable(true);
                toggleBside.setDisable(true);
                break;
            case "2":
                txtMode.setText("Manual Mode");
                current_machine_mode = "2";
                txtdate.setFill(Color.web("#0099FF"));
                toggleAside.setDisable(false);
                toggleBside.setDisable(false);
                break;
            case "3":
                txtMode.setText("Auto Mode");
                current_machine_mode = "3";
                txtdate.setFill(Color.web("#0099FF"));
                toggleAside.setDisable(false);
                toggleBside.setDisable(false);
                break;
            default:
                txtMode.setText("Something wrong");
                current_machine_mode = "4";
                txtdate.setFill(Color.web("#C32420"));
                toggleAside.setDisable(true);
                toggleBside.setDisable(true);
                break;
        }
    }

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
                                            txtActualLeakage.setText(bubble_counter);
                                        });
                                        try {
                                            pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(pressure_a));
                                            pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(pressure_b));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //Test_result_by_test_type

                                        if (test_result_by_type_check == 1) {
                                            String query = "INSERT INTO test_result_by_type (`valve_serial_no`,`test_no`,`test_type`,`hydro_pressure_a_side`,`hydro_pressure_b_side`,`date_time`,`test_count`) VALUES('" + vsn + "','" + test_no + "','" + txtTestType.getText() + "','" + pressure_a_side + "','" + pressure_b_side + "',NOW(),'" + test_result_by_type_check + "')";
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
                                            oat = over_all_time;
//                                            overall_time_end = txtOverAllTime.getText();
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
//                                       

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
                            case "5":
                                System.out.println("in case 5");
                                try {
                                    count_result = 0;
//                                    String lt = txtTypeOfLeakage.getText();

                                    String psu;

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

                                        String query = "INSERT INTO test_result(`valve_serial_no`, `test_no`, `test_type`, `leakage_type`, `valve_type`, `valve_size`, `valve_class`, `actual_leakage`, `holding_time`, `over_all_time`, `hydro_set_pressure`, `start_pressure_a`, `start_pressure_b`, `stop_pressure_a`, `stop_pressure_b`, `pressure_unit`, `gauge_serial_no`, `guage_calibration_date`, `test_result`, `date_time`) VALUES('" + vsn + "','" + test_no + "','" + txtTestType.getText() + "','" + txtTypeOfLeakage.getText() + "','" + txtValveType.getText() + "','" + Session.get("vs") + "','" + Session.get("vc") + "','" + txtActualLeakage.getText() + "','" + holding_time + "','" + oat + "','" + Session.get("hsp") + "','" + start_pressure_a_side + "','" + start_pressure_b_side + "','" + stop_pressure_a_side + "','" + stop_pressure_b_side + "','" + Session.get("pu") + "','NA','NA','" + result + "',NOW())";
                                        System.out.println("query test result : " + query);
                                        try {
                                            dh.execute(query, connect);
                                            test_result_count++;
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                            System.out.println(String.valueOf("TEST RESULT : " + e.getMessage()));
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
//                        System.out.println("current_cycle_status : " +current_cycle_status +"cycle_statusc : " +cycl_status);
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

    private void get_result(String result) {
        if (result.equals("1")) {

//            txtResult.setStyle("-fx-background-color: derive(#388e3c,50%); -fx-text-inner-color:black; -fx-font-size: 20px;");
            Platform.runLater(() -> {
                txtResult.setText("TEST OK");
            });
//            final_result = "1";

        } else {
//            txtResult.setStyle("-fx-background-color: derive(#ac0800,50%); -fx-text-inner-color:white; -fx-font-size: 20px;");
            Platform.runLater(() -> {
                txtResult.setText("TEST NOT OK");
            });
//            final_result = "2";
        }
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

                Platform.runLater(() -> {
                    txtCycleStatus.setText("CYCLE COMPLETE");
                });
                you_can_change = true;

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
//                stopCycleStatusThread = true;
//                try {
//                    cycleStatus.stop();
//                } catch (Exception e) {
//                }
                start_trend = false;
                break;
            case "6":
//                enable_field();
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
//                stopCycleStatusThread = true;
//                try {
//                    cycleStatus.stop();
//                } catch (Exception e) {
//                }
                start_trend = false;
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
//                stopCycleStatusThread = true;
//                try {
//                    cycleStatus.stop();
//                } catch (Exception e) {
//                }
                start_trend = false;

                break;
            default:
                System.out.println("default");
                txtCycleStatus.setText("");
                you_can_change = true;
                current_cycle_status = "0";
//                stopCycleStatusThread = true;
//                try {
//                    cycleStatus.stop();
//                } catch (Exception e) {
//                }
                start_trend = false;
                break;
        }
        System.out.println("default");
    }

    private void empty_timers() {
        txtStabilizationTimer.setText("");
        txtHoldingTimer.setText("");
//        txtDrainDelay.setText("");
        txtDrainTimer.setText("");
        txtOverAllTime.setText("");
        txtResult.setText("");
        txtActualLeakage.setText("");

    }

    @FXML
    private void btnHomeAction(ActionEvent event) {
        if (you_can_change) {
            Background_Processes.stop_plc_read();
            mode.stop();
            cycleStatus.stop();
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnHome);
        } else {
            Dialog.showForSometime("Alert", "Cycle running You can't Go", "Alert", 450, 5);
        }
    }

    @FXML
    private void btnTestScreenAction(ActionEvent event) {

    }

    @FXML
    private void btnGaugeCalAction(ActionEvent event) {
    }

    @FXML
    private void btnReportAction(ActionEvent event) {
        if (you_can_change) {
            Background_Processes.stop_plc_read();
            mode.stop();
            cycleStatus.stop();
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnHome);
        } else {
            Dialog.showForSometime("Alert", "Cycle running You can't Go", "Alert", 450, 5);
        }
    }

    @FXML
    private void btnSystemCheckAction(ActionEvent event) {
        if (you_can_change) {
            Background_Processes.stop_plc_read();
            mode.stop();
            cycleStatus.stop();
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnHome);
        } else {
            Dialog.showForSometime("Alert", "Cycle running You can't Go", "Alert", 450, 5);
        }
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
        if (you_can_change) {
            Background_Processes.stop_plc_read();
            mode.stop();
            cycleStatus.stop();
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnHome);
        } else {
            Dialog.showForSometime("Alert", "Cycle running You can't Go", "Alert", 450, 5);
        }
    }

    @FXML
    private void btnInitialAction(ActionEvent event) {
        if (you_can_change) {
            Background_Processes.stop_plc_read();
            mode.stop();
            cycleStatus.stop();
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("InitialScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnHome);
        } else {
            Dialog.showForSometime("Alert", "Cycle running You can't Go", "Alert", 450, 5);
        }
    }

}
