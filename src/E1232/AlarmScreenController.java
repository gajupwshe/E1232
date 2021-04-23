/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import static E1232.ReportScreenController.catStage;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hydro
 */
public class AlarmScreenController implements Initializable {

    Thread mode, read, insert_alarm;
    private Service<Void> TimeBackground_alarm;
    String current_machine_mode, previous_hydro_set_pressure, current_offline_mode, current_oil_level, current_air_inlet, current_temperature, current_5hp, current_2hp, current_m3, current_m4, current_m5, current_pt1, current_pt2, current_hm, current_pm, current_vd;
    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text txtMode;
    private Text txtDate;
    private ImageView imgEmergency;
    private Text txtOffline;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnInitial;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnOilLevel;
    private JFXButton btnAirInlet;
    private JFXButton btnTemperature;
    private JFXButton btnHM5Hp;
    private JFXButton btnHM2Hp;
    private JFXButton btnMotor3;
    private JFXButton btnMotor4;
    private JFXButton btnMotor5;
    private JFXButton btnAlarm;

    public static volatile boolean stop_mode = false;
    private ImageView imgAuto;
    private ImageView imgManual;
    @FXML
    private Text txtdate;
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnPresTrans1;
    @FXML
    private JFXButton btnPresTrans2;
    @FXML
    private JFXButton btnHydraMotor;
    @FXML
    private JFXButton btnPreMotor;

    @FXML
    private JFXButton btnValveDrain;
    private JFXButton btnAirPurging;
    @FXML
    private JFXButton btnEmr;
    @FXML
    private HBox sectionHeader;
    @FXML
    private JFXButton btnTestScreen;
    @FXML
    private JFXButton btnSystemCheck;
    @FXML
    private JFXButton btnGaugeCal;

    private void machine_mode() {
        System.out.println("in machine mode");
        stop_mode = false;
        mode = new Thread(() -> {
            while (true) {
                try {
                    String cmd1 = "python E:\\E1257\\python_plc\\insert_alarm_tags.py";
                    System.out.println("cmd1" + cmd1);
                    Process child1 = Runtime.getRuntime().exec(cmd1);
                    child1.waitFor();
                    System.out.println("");
                    Thread.sleep(150);
                    if (stop_mode) {
                        break;
                    }

                    String display = "SELECT * FROM alarm_tags ORDER BY alarm_tags_id DESC LIMIT 1";
                    ResultSet rs = dh.getData(display, connect);
                    if (rs.next()) {
                        if (rs.getString("oil_level").equals("1")) {
//                            btnOilLevel.setText("LOW");
                            btnOilLevel.setStyle("-fx-background-color:RED");

                        } else {
//                            btnOilLevel.setText("HIGH");
                            btnOilLevel.setStyle("-fx-background-color:GREEN");

                        }
                        if (rs.getString("machine_mode").equals("1") || rs.getString("machine_mode").equals("0")) {
//                            btnEmr.setText("ON");
                            btnEmr.setStyle("-fx-background-color:RED");
                            
                        } else {
//                             btnEmr.setText("OFF");
                            btnEmr.setStyle("-fx-background-color:GREEN");
                            
                        }
                        if (rs.getString("motor_4").equals("1")) {
//                              btnPresTrans1.setText("NOT CONNECTED");
                            btnPresTrans1.setStyle("-fx-background-color:RED");
                          
                        }else{
//                            btnPresTrans1.setText("CONNECTED");
                            btnPresTrans1.setStyle("-fx-background-color:GREEN");
                        }
                        if (rs.getString("motor_5").equals("1")) {
//                            btnPresTrans2.setText("NOT CONNECTED");
                            btnPresTrans2.setStyle("-fx-background-color:RED");
                        }else{
//                            btnPresTrans2.setText(" CONNECTED");
                            btnPresTrans2.setStyle("-fx-background-color:GREEN");
                        }
                        if (rs.getString("hydraulic_motor_5").equals("0")) {
//                            btnHydraMotor.setText("OFF");
                            btnHydraMotor.setStyle("-fx-background-color:RED");
                        }else if (rs.getString("hydraulic_motor_5").equals("1")){
//                            btnHydraMotor.setText("ON");
                            btnHydraMotor.setStyle("-fx-background-color:YELLOW");
                        }else{
                            btnHydraMotor.setStyle("-fx-background-color:GREEN");
                        }
                        if (rs.getString("hydraulic_motor_2").equals("0")) {
//                            btnPreMotor.setText("OFF");
                            btnPreMotor.setStyle("-fx-background-color:RED");
                        }else if (rs.getString("hydraulic_motor_2").equals("1")) {
                            btnPreMotor.setStyle("-fx-background-color:YELLOW");
                        }else{
//                            btnPreMotor.setText("ON");
                             btnPreMotor.setStyle("-fx-background-color:GREEN");
                        }
                        if (rs.getString("motor_3").equals("0")) {
//                            btnValveDrain.setText("OFF");
                            btnValveDrain.setStyle("-fx-background-color:RED");
                        }else if(rs.getString("motor_3").equals("1")){
                         btnValveDrain.setStyle("-fx-background-color:YELLOW");   
                        }
                        else{
//                            btnValveDrain.setText("ON");
                            btnValveDrain.setStyle("-fx-background-color:GREEN");
                        }
                        

                    }

                } catch (InterruptedException | SQLException e) {

                } catch (IOException ex) {
                    Logger.getLogger(AlarmScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (stop_mode) {
                    break;
                }
            }
        }, "machineModeThreadAlarmScreen");
        mode.setDaemon(true);
        mode.start();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize intial screen
//        Thread Initialize_Initial = new Thread(() -> {
//            Background_Processes.Initialize_Initial_Screen();
//            System.out.println("Initialized");
//        }, "initializeIntialScreenThread");
//        Initialize_Initial.setDaemon(true);
//        Initialize_Initial.start();
        // TODO
//        imgEmergency.setVisible(false);
//        imgManual.setVisible(false);
//        imgAuto.setVisible(false);
        Session.set("screen", "AlarmScreen");
        String user_name = Session.get("user");

        try {
//            System.out.println(".......................");
            current_pt1 = "2131";
            current_pt2 = "2121";
            current_hm = "2121";
            current_pm = "2121";
            current_vd = "2121";
//            current_m5 = "2121";
            current_machine_mode = "2121";
            Date dateInstance = new Date();
            txtdate.setText("" + (dateInstance.getDate() + "/" + (dateInstance.getMonth() + 1) + "/" + (dateInstance.getYear() + 1900)));
            System.out.println(".......................");
            machine_mode();

        } catch (Exception e) {
        }

    }

    @FXML
    private void btnHomeAction(ActionEvent event) throws IOException {

        if (ToolKit.readpy("python D:/SCADA_soft/E1024/python_plc/read_plc_bool.py 14 1").replace("/", "").equals("True")) {
            System.out.println("Alert:" + ToolKit.readpy("python D:/SCADA_soft/E1024/python_plc/read_plc_int.py 14 1").replace("/", ""));
            Dialog.showForSometime("Alert", "Error Occured", "alert", 450, 3);

        } else {
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

    }

    @FXML
    private void btnReportAction(ActionEvent event) throws IOException, SQLException {
       mode.stop();
        stop_mode = true;
        String truncat_query = "TRUNCATE TABLE alarm_tags";
        dh.execute(truncat_query, connect);
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);

        
    }

    @FXML
    private void btnAdminAction(ActionEvent event) throws IOException, SQLException {
       mode.stop();
        stop_mode = true;
        String truncat_query = "TRUNCATE TABLE alarm_tags";
        dh.execute(truncat_query, connect);
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);

        
    }

    @FXML
    private void btnInitialAction(ActionEvent event) throws IOException {
        
        try {
            mode.stop();
            stop_mode = true;
            String truncat_query = "TRUNCATE TABLE alarm_tags";
            dh.execute(truncat_query, connect);
            
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("InitialScreen.fxml"));
                    ToolKit.loadScreen(root);
                    stop_mode = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } catch (SQLException ex) {
            Logger.getLogger(AlarmScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    /**
     * Oil Level selector
     */
    private void oil_level(String olevel) {

        switch (olevel) {
            case "0":
                btnOilLevel.setText("Low");
                btnOilLevel.setStyle("-fx-background-color:#ac0800;");
                current_oil_level = "0";
                break;
            case "1":
                btnOilLevel.setText("Medium");
                btnOilLevel.setStyle("-fx-background-color:#4b636e;");
                current_oil_level = "1";
                break;
            case "2":
                btnOilLevel.setText("High");
                btnOilLevel.setStyle("-fx-background-color:#388e3c;");
                current_oil_level = "2";
                break;
            default:
                current_oil_level = "0";
                break;
        }
    }

    /**
     * Oil Level selector
     */
    private void air_inlet(String alevel) {
        switch (alevel) {
            case "0":
                btnAirInlet.setText("Low");
                btnAirInlet.setStyle("-fx-background-color:#ac0800;");
                current_air_inlet = "0";
                break;
            //as per requirement
            //if required uncomment below code to indicate medium air range
//            case "1":
//                btnAirInlet.setText("Medium");
//                btnAirInlet.setStyle("-fx-background-color:#4b636e;");
//                current_air_inlet = "1";
//                break;
            case "1":
                btnAirInlet.setText("High");
                btnAirInlet.setStyle("-fx-background-color:#388e3c");
                current_air_inlet = "1";
                break;
            default:
                current_air_inlet = "0";
                break;
        }
    }

    /**
     * Temperature selector
     */
    private void temperature(String Tlevel) {
        switch (Tlevel) {
            case "0":
                btnTemperature.setText("Low");
                btnTemperature.setStyle("-fx-background-color:#388e3c;");
                current_temperature = "0";
                break;
            case "1":
                btnTemperature.setText("Medium");
                btnTemperature.setStyle("-fx-background-color:#4b636e;");
                current_temperature = "1";
                break;
            case "2":
                btnTemperature.setText("High");
                btnTemperature.setStyle("-fx-background-color:#ac0800;");
                current_temperature = "2";
                break;
            default:
                current_temperature = "0";
                break;
        }
    }

    /**
     * Motor status
     */
    private void motor_status(String status, JFXButton button, String value_changes) {
        switch (status) {
            case "0":
                button.setText("OFF");
                button.setStyle("-fx-background-color:#4b636e;");
                value_changes = "0";
                break;
            case "1":
                button.setText("ON");
                button.setStyle("-fx-background-color:#388e3c;");
                value_changes = "1";
                break;
            case "2":
                button.setText("TRIP");
                button.setStyle("-fx-background-color:#ac0800;");
                value_changes = "2";
                break;
            default:
                value_changes = "0";
                break;
        }
    }

    /**
     * offline online mode selector
     */
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

    /**
     * pressure transmitter
     */
    private void pressure_status(String status, JFXButton button, String value_changes) {
        switch (status) {
            case "0":
                button.setText("DISCONNECTED");
                button.setStyle("-fx-background-color:#ac0800;");
                value_changes = "0";
                break;
            case "1":
                button.setText("CONNECTED");
                button.setStyle("-fx-background-color:#388e3c;");
                value_changes = "1";
                break;

            default:
                value_changes = "0";
                break;
        }
    }

    /**
     * machine mode selector
     */
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

    /**
     * change screen
     *
     * @param a
     * @param initial_check
     * @throws java.io.IOException
     */
    public void dropbox(String a, boolean initial_check) throws IOException {
        try {
            Background_Processes.stop_plc_read();
        } catch (Exception e) {
            System.out.println("Exception while stoping insert_plc_thread_Alram_Screen : " + e.getMessage());
        }
        try {
            Background_Processes.stop_date_time();
        } catch (Exception e) {
            System.out.println("Exception while stoping date_time_thread_Alram_Screen : " + e.getMessage());
        }
        //Stoping machine mode thread
        stop_mode = true;
//        if (initial_check) {
//            Background_Processes.Initialize_Initial_Screen();
//        }
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(a));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnAlarm);

    }

    private void btnLoginAction(ActionEvent event) throws SQLException {

        mode.stop();
        stop_mode = true;
        String truncat_query = "TRUNCATE TABLE alarm_tags";
        dh.execute(truncat_query, connect);
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

    private void btngaugeAction(ActionEvent event) throws IOException {

        if (ToolKit.readpy("python D:/SCADA_soft/E1024/python_plc/read_plc_int.py 14 1").replace("/", "").equals("True")) {
            System.out.println("Alert:" + ToolKit.readpy("python D:/SCADA_soft/E1024/python_plc/read_plc_int.py 14 1").replace("/", ""));
            Dialog.showForSometime("Alert", "Error Occured", "alert", 450, 3);

        } else {
            //Open Gauge calibration window
            Parent root = FXMLLoader.load(getClass().getResource("gaugecalibration.fxml"));
            Platform.runLater(() -> {
//                        cmbTestStandards.getSelectionModel().select(0);
                catStage = new Stage(StageStyle.UNDECORATED);
                catStage.setAlwaysOnTop(true);
                Scene scene = new Scene(root, 1330, 250);

                catStage.setScene(scene);
                catStage.show();
            });

        }

    }


    private void btnAirPurgingAction(ActionEvent event) {

        if (ToolKit.readpy("python D:/SCADA_soft/E1024/python_plc/read_plc_int.py 14 1").replace("/", "").equals("True")) {
            System.out.println("Alert:" + ToolKit.readpy("python D:/SCADA_soft/E1024/python_plc/read_plc_int.py 14 1").replace("/", ""));
            Dialog.showForSometime("Alert", "Error Occured", "alert", 450, 3);

        } else {
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

    }

    @FXML
    private void btnTestScreenAction(ActionEvent event) {
    }

    @FXML
    private void btnSystemCheckAction(ActionEvent event) {
        
    }

    @FXML
    private void btnGaugeCalAction(ActionEvent event) {
    }

}
