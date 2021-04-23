/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CavityScreenController implements Initializable {

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
    private JFXButton btnTest;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnSystemCheck;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnGaugeCal;
    @FXML
    private JFXTextField txtCavityReiiefPressure;
    @FXML
    private JFXTextField txtActualCavityReliefPressure;
    @FXML
    private JFXTextField txtResult;
    @FXML
    private JFXButton btnReleasePressure;
    @FXML
    private RadioButton radioHighPressure;
    @FXML
    private RadioButton radioLowPressure;
    @FXML
    private RadioButton radioOff;

    DatabaseHandler dh = new DatabaseHandler();
    Connection conn = dh.MakeConnection();

    //Trend Chart Component
    private final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
//    private XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
    private ExecutorService executor;
    private final ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
//    private ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();

    private final ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();
    private NumberAxis xAxis;
    @FXML
    private JFXDrawer drawer;
    LineChart<Number, Number> lineChart;

    private volatile boolean stop_mode = false;
   
    Thread m_mode;
    
    String current_machine_mode, current_offline_mode, current_actual_pressure;
    String valve_class = "";
    String valve_size = "";
    String valve_type = "";
    String valve_standards = "";
    String type_of_sealing = "";
    
    
    int current_over_all_timer = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        date_time();
        Background_Processes.insert_plc_data("python E:\\E1232\\python_plc\\insert_init_tags.py", false, true);
        machine_mode();
    }

    
    private void machine_mode() {
        stop_mode = false;
        m_mode = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(150);
                    if (stop_mode) {
                        break;
                    }
                    String display = "SELECT * FROM test_tags ORDER BY test_tags_id DESC LIMIT 1";
                    ResultSet rs = dh.getData(display, conn);
                    if (rs.next()) {
//                        System.out.println("hydro_actual_a_pressure" +rs.getString("hydro_actual_a_pressure"));
                        if (rs.getString("offline_online").equals(current_offline_mode)) {
                            //System.out.println("Offline not changed");
                        } else {
                            switch (rs.getString("offline_online")) {
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
                            //System.out.println("Offline changed");
                        }
                        if (stop_mode) {
                            break;
                        }
                        if (rs.getString("machine_mode").equals(current_machine_mode)) {
//                                System.out.println("mode not changed");
                        } else {
                            machine_mode_value(rs.getString("machine_mode"));
//                                System.out.println("mode changed");
                        }
                        if (stop_mode) {
                            break;
                        }
//                        System.out.println("hydro_actual_a_pressure" +rs.getString("hydro_actual_a_pressure"));
                        if (rs.getString("hydro_actual_a_pressure").equals(current_actual_pressure)) {
//                                System.out.println("mode not changed");
                        } else {
                            txtActualCavityReliefPressure.setText(rs.getString("hydro_actual_a_pressure"));
                            current_actual_pressure = rs.getString("hydro_actual_a_pressure");
//                                System.out.println("mode changed");
                        }
                    }

                } catch (InterruptedException | SQLException e) {

                }
                if (stop_mode) {
                    break;
                }
            }
        }, "machineModeThreadCavity");
        m_mode.setDaemon(true);
        m_mode.start();
    }

    private void machine_mode_value(String mode) {
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
//                break;
//            case "0":
//                txtMode.setText("Auto Mode");
//                current_machine_mode = "0";
//                txtDate.setFill(Color.web("#0099FF"));
//                imgEmergency.setVisible(false);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(true);
//                break;
//            case "1":
//                txtMode.setText("Manual Mode");
//                current_machine_mode = "1";
//                txtDate.setFill(Color.web("#0099FF"));
//                imgEmergency.setVisible(false);
//                imgManual.setVisible(true);
//                imgAuto.setVisible(false);
//                break;
//            case "2":
//                txtMode.setText("Emergency Mode");
//                current_machine_mode = "2";
//                txtDate.setFill(Color.web("#C32420"));
//                imgEmergency.setVisible(true);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(false);
//                break;
//            default:
//                txtMode.setText("Something wrong");
//                current_machine_mode = "0";
//                txtDate.setFill(Color.web("#C32420"));
//                imgEmergency.setVisible(true);
//                imgManual.setVisible(false);
//                imgAuto.setVisible(false);
//                break;
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

    final ToggleGroup group_pump = new ToggleGroup();

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

            lineChart.setAnimated(false);
            lineChart.setTitle("");
            lineChart.getData().clear();
            series1.getData().clear();
            series1.getData().removeAll();
            lineChart.setHorizontalGridLinesVisible(true);

            series1.setName("Hydro Pressure");
            // Add Chart Series
            lineChart.getData().addAll(series1, series3, series2);
//            }
            drawer.setMinWidth(400);
            drawer.setSidePane(lineChart);
            drawer.setOverLayVisible(false);

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
            series1.getData().removeAll(dataQ1);
            series2.getData().removeAll(dataQ2);
//                series3.getData().removeAll(dataQ3);

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
    private boolean start_trend = true;

    @FXML
    private void txtCavityReliefPressureAction(ActionEvent event) {
         ToolKit.tagWrite("F8:111", txtCavityReiiefPressure.getText());
    }

    @FXML
    private void txtCavityReliefPressureKeyRelease(KeyEvent event) {
    }

    @FXML
    private void btnReleasePressureAction(ActionEvent event) {
         String get_pressure = "SELECT result FROM test_tags ORDER BY test_tags_id DESC LIMIT 1";

//        ToolKit.tagWrite("B3:12/2", "1");
//        ToolKit.tagWrite("B3:12/2", "0");

        try {
            ResultSet rs = dh.getData(get_pressure, conn);
            if (rs.next()) {
                String result_cavity = ToolKit.tagRead("N7:55");
                String result = "";
                if (result_cavity.equals("1")) {

                } else {

                }
//                txtResult.setText(rs.getString("result"));
                String store_result = "INSERT INTO test_result (`valve_serial_no`, `test_no`, `test_type`, `leakage_type`, `valve_type`,`valve_size`, `valve_class`, `actual_leakage`, `holding_time`,`over_all_time`, `hydro_set_pressure`,`start_pressure_a`,`start_pressure_b`,`stop_pressure_a`,`stop_pressure_b`, `pressure_unit`, `gauge_serial_no`, `guage_calibration_date`, `test_result`, `date_time`) VALUES('" + Session.get("Valve_Serial_No") + "','" + Session.get("test_no") + "','" + Session.get("Test_Type") + "','NONE','" + valve_type + "','" + valve_size + "','" + valve_class + "','NA','NA','NA','" + txtCavityReiiefPressure.getText() + "','" + txtActualCavityReliefPressure.getText() + "','NA','NA','NA','" + Session.get("pressure_unit") + "','" + Session.get("gauge_serial") + "','" + Session.get("gauge_calibration_date") + "','" + result_cavity + "',NOW())";
                dh.execute(store_result, conn);
            }

        } catch (Exception e) {
        }
    }

    @FXML
    private void radioHighPressureAction(ActionEvent event) {
    }

    @FXML
    private void radioLowPressureAction(ActionEvent event) {
    }

    @FXML
    private void radioOffAction(ActionEvent event) {
    }

    private class AddToQueue implements Runnable {

        String query = "SELECT * FROM test_tags ORDER BY test_tags_id DESC LIMIT 1";
        ResultSet rs;

        @Override
        public void run() {
//run after some time 
            Platform.runLater(() -> {
                try {
                    // add a item of random data to queue.
                    if (start_trend) {
                        rs = dh.getData(query, conn);
                        if (rs.next()) {
                            double dq1 = 0.0;
                            double dq2 = 0.0;
                            double dq3 = 0.0;
                            try {
                                dq1 = Double.parseDouble(rs.getString("hydro_actual_a_pressure"));
                            } catch (Exception e) {
                                System.err.println("This is an Error of Trend data where dq1 and dq2 defined: " + e.getMessage());
                            }
//                            System.out.println("dq1 : " + dq1);
                            dataQ1.add(dq1);

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
            series1.getData().add(new XYChart.Data<>(current_over_all_timer, dataQ1.remove()));

        }
        Platform.runLater(() -> {
            xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
            xAxis.setUpperBound(xSeriesData - 1);

        });

    }

    @FXML
    private void btnHomeAction(ActionEvent event) throws IOException {
         dropbox("Login.fxml", false);
   
    }

    @FXML
    private void btnReportAction(ActionEvent event) throws IOException {
        dropbox("ReportScreen.fxml", false);
    }

    @FXML
    private void btnSystemCheckAction(ActionEvent event) {
        try {
            dropbox("AlarmScreen.fxml", false);
        } catch (IOException ex) {
            Logger.getLogger(CavityScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAdminAction(ActionEvent event) throws IOException {
         dropbox("AdminScreen.fxml", false);
    }

    @FXML
    private void btnGaugeCalAction(ActionEvent event) {
    }

     public void dropbox(String a, boolean initial_check) throws IOException {
//        Background_Processes.stop_plc_read();
//        Background_Processes.stop_date_time();

        try {
            Background_Processes.stop_plc_read();
        } catch (Exception e) {
            System.out.println("Exception while stoping insert_plc_thread_Cavity_Screen : " + e.getMessage());
        }
        try {
            Background_Processes.stop_date_time();
        } catch (Exception e) {
            System.out.println("Exception while stoping date_time_thread_Cavity_Screen : " + e.getMessage());
        }
        //Stoping machine mode thread
        stop_mode = true;
        if (initial_check) {
            Background_Processes.Initialize_Initial_Screen();
        }
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(a));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnHome);
    }
}
