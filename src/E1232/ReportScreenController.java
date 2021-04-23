/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package E1232;

import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import static javafx.scene.input.DataFormat.HTML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class ReportScreenController implements Initializable {

    @FXML
    private WebView webReport;

    /**
     * Initializes the controller class.
     */
    WebEngine webEngine;
    @FXML
    private Text txtdate;
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnInitial;

    private JFXButton btnAirPurging;
    @FXML
    private HBox sectionHeader;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnTestScreen;
    @FXML
    private JFXButton btnSystemCheck;
    @FXML
    private JFXButton btnGaugeCal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String user_type = Session.get("user_type");
        System.out.println("user_type" + user_type);
        if (user_type.equals("admin")) {
            System.out.println("YES........");
            btnAdmin.setVisible(true);

        } else {
            System.out.println("NO........");
            btnAdmin.setVisible(false);
        }
        String user_name = Session.get("user");
       
        Session.set("screen", "ReportScreen");

        Date dateInstance = new Date();
        txtdate.setText("" + (dateInstance.getDate() + "/" + (dateInstance.getMonth() + 1) + "/" + (dateInstance.getYear() + 1900)));
        webEngine = webReport.getEngine();
        webEngine.load("http://localhost/Report_1232/index.php?db_user=root&db_pass=hydro&db_name=E1257");
        webEngine.setJavaScriptEnabled(true);
        System.out.println("http://localhost/Report_1232/index.php?db_user=root&db_pass=hydro&db_name=E1257");
        //Adding Custom Context menu - Start
        webReport.setContextMenuEnabled(false);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("Reload");
        reload.setOnAction(e -> webReport.getEngine().reload());
        MenuItem goBack = new MenuItem("Go back");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    webReport.getEngine().getHistory().go(-1);
                } catch (IndexOutOfBoundsException exc) {
                    Dialog.showForSometime("", "You are on last page", "", 550, 2);
                }
            }
        });
        contextMenu.getItems().addAll(reload, goBack);
        //Adding Custom Context menu - End

        webReport.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webReport, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

        //Initialize intial screen
        Thread Initialize_Initial = new Thread(() -> {
            Background_Processes.Initialize_Initial_Screen();
            System.out.println("Initialized");
        }, "Initialize_Initial_Screen");
        Initialize_Initial.setDaemon(true);
        Initialize_Initial.start();

//        LocalDate today = LocalDate.now();
//        System.out.println(today);
//        double start_pressure_a_side = Double.parseDouble("12.0135454");
//        String df = new DecimalFormat("#").format(Double.parseDouble("12.0135454"));
//        System.out.println(df);
//        //Get hardqoute text from database: Start
//        try {
//            String get_hardqouteAdd = "SELECT java_hardqoutes_Name,java_hardqoutes_code FROM tbl_java_hardqoutes WHERE java_hardqoutes_screen ='REPORT';";
//            ResultSet get_rs = dh.getData(get_hardqouteAdd, connect);
//        } catch (SQLException e) {
//            System.out.println("Exception in getting harqoute form DB : " + e.getMessage());
//        }
/*
*THIS IS QUERY TO CREATE TABLE HARDQOUTE
*CREATE TABLE `tbl_java_hardqoutes` (
  `java_hardqoutes_id` int(11) NOT NULL AUTO_INCREMENT,
  `java_hardqoutes_Name` varchar(1000) NOT NULL,
  `java_hardqoutes_code` varchar(100) NOT NULL,
  `java_hardqoutes_screen` varchar(100) NOT NULL,
  `java_hardqoutes_description` varchar(255) NOT NULL,
  `java_hardqoutes_date_time` datetime NOT NULL,
  PRIMARY KEY (`java_hardqoutes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1
         */
//        //Get hardqoute text from database: End
//        
        webReport.getEngine().setOnAlert((WebEvent<String> arg0) -> {
            System.out.println(webEngine.locationProperty().getValue());
            String[] pdf_split = arg0.getData().split("_split_");
            if (pdf_split[0].equals("PDF SAVED SUCCESSFULLY")) {
                try {
                    System.out.println(" saved");
                    Dialog.showAndWait(arg0.getData());
                    //Transfer Folder to Desktop
                    Runtime run = Runtime.getRuntime();
//                    Process child = run.exec("rm -r /home/hydro-380/Desktop/Reports/Pdf/");
//                    child.waitFor();
//                    Process child1 = run.exec("cp -r /opt/lampp/htdocs/Scuff_1079/reports/ /home/ctpl/Desktop/Report/");
//                    child1.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(ReportScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (arg0.getData().equals("testing test no")) {
                String[] split = webEngine.locationProperty().getValue().split("test_no=");
                String[] split2 = split[1].split("&");
                System.out.println(split2[0]);
                String testNo = split2[0];
                Runtime run = Runtime.getRuntime();
                try {
//                    Process child = run.exec("python /opt/share/E948/TestWare/include/plot.py " + DatabaseHandler.DB_HOST + " " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " " + testNo);
//                    System.out.println("python /opt/share/E948/TestWare/include/plot.py " + DatabaseHandler.DB_HOST + " " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " " + testNo);
//                    child.waitFor(5, TimeUnit.SECONDS);
                } catch (Exception e) {

                }
//                Dialog.showAndWait("graph");

            }

        });
    }

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

    private void btnReportAction(ActionEvent event) {
        Background_Processes.stop_plc_read();
//        machine_mode.stop();
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
    private void btnAdminAction(ActionEvent event) {
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
    private void btnInitialAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("InitialScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }
    public static Stage catStage;


    private void btnAirPurgingAction(ActionEvent event) {

       

    }

    @FXML
    private void btnHomeAction(ActionEvent event) {
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

    @FXML
    private void btnTestScreenAction(ActionEvent event) {
        
    }

    @FXML
    private void btnSystemCheckAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }

    @FXML
    private void btnGaugeCalAction(ActionEvent event) {
    }
}
