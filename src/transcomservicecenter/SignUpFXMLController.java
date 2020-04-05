/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transcomservicecenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import transcomservicecenter.Main;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author faiya
 */
public class SignUpFXMLController implements Initializable {

    String usermail;
    String userpass;
    String userf;
    String userl;
    String userdb;
    String userph;
    String usercb;
    int ln;
    File file = new File("D:\\NETBEANS\\TranscomServiceCenter\\src\\text file");

    void createFolder() {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    void readFile() {
        try {
            FileReader fr = new FileReader(file + "\\data.txt");
            System.out.println("file exists");
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(file + "\\data.txt");
                System.out.println("File Created");
            } catch (IOException ex1) {
                Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    void addData(String sgnmail, String fname, String lname, String db, String sgnpass, String sgnconpass, String sgnphone, String combob) {
        try {
            RandomAccessFile raf = new RandomAccessFile(file + "\\data.txt", "rw");
            for (int i = 0; i < ln; i++) {
                raf.readLine();
            }
            if (sgnmail.isEmpty() || fname.isEmpty() || db.isEmpty() || sgnpass.isEmpty() || sgnconpass.isEmpty() || sgnphone.isEmpty() || combob.isEmpty()) {
                txtlbl2.setText("Please fill up all the fields.");
            } else {
                if (sgnpass.equals(sgnconpass)) {
                    txtlbl2.setText("");
                    txtlbl3.setText("Registration Successful.");
                    raf.writeBytes("Email: " + sgnmail + "\r\n");
                    raf.writeBytes("FirstName: " + fname + "\r\n");
                    raf.writeBytes("LastName: " + lname + "\r\n");
                    raf.writeBytes("DateofBirth: " + db + "\r\n");
                    raf.writeBytes("Password: " + sgnpass + "\r\n");
                    raf.writeBytes("Phone: " + sgnphone + "\r\n");
                    raf.writeBytes("Designation: " + combob +"\r\n");
                } else {
                    txtlbl2.setText("Passwords do not match.");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void countLines() {
        try {
            ln = 1;
            RandomAccessFile raf = new RandomAccessFile(file + "\\data.txt", "rw");
            for (int i = 0; raf.readLine() != null; i++) {
                ln++;
            }
            System.out.println("number of lines: " + ln);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String manage_combobox() {
        String combovalue;
        if (combobox.getSelectionModel().getSelectedIndex() == -1) {
            combovalue = "";
        } else {
            combovalue = combobox.getSelectionModel().getSelectedItem();
        }
        return combovalue;
    }

    public String manage_date() {
        String birthday;
        if (db.getValue() == null) {
            birthday = "";
        } else {
            birthday = db.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return birthday;
    }

    public void register_account(ActionEvent event) {
        createFolder();
        readFile();
        countLines();
        addData(sgnmail.getText(), fname.getText(), lname.getText(), manage_date(),
                sgnpass.getText(), sgnconpass.getText(), sgnphone.getText(), manage_combobox());
    }
    @FXML
    public Label txtlbl2;
    @FXML
    public Label txtlbl3;
    @FXML
    public Button button;
    @FXML
    public AnchorPane parent;
    @FXML
    public Pane content_area;
    @FXML
    public ComboBox<String> combobox;
    ObservableList<String> list = FXCollections.observableArrayList("Customer", "Manager", "Accountant", "Service Representative", "Admin");

    private static double xOffSet = 0;
    private static double yOffSet = 0;
    @FXML
    private TextField sgnmail;
    @FXML
    private PasswordField sgnpass;
    @FXML
    private Button register;
    @FXML
    private TextField fname;
    @FXML
    private TextField sgnphone;
    @FXML
    private TextField lname;
    @FXML
    private PasswordField sgnconpass;
    @FXML
    private DatePicker db;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combobox.setItems(list);
        make_draggable();
        parent.setOpacity(0);
        fadeIn();
    }

    public void make_draggable() {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> {
            Main.stage.setX(event.getScreenX() - xOffSet);
            Main.stage.setY(event.getScreenY() - yOffSet);
            Main.stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) -> {
            Main.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) -> {
            Main.stage.setOpacity(1.0f);
        });
    }

    @FXML
    public void close_app(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void minimize_app(ActionEvent event) {
        Main.stage.setIconified(true);
    }

    public void fadeIn() {
        FadeTransition fout = new FadeTransition();
        fout.setDuration(Duration.millis(300));
        fout.setNode(parent);
        fout.setFromValue(0);
        fout.setToValue(1);

        fout.play();
    }

    @FXML
    public void onClick(ActionEvent event) {
        fadeOut();
    }

    public void fadeOut() {
        FadeTransition fout = new FadeTransition();
        fout.setDuration(Duration.millis(300));
        fout.setNode(parent);
        fout.setFromValue(1);
        fout.setToValue(0);

        fout.setOnFinished((ActionEvent event) -> {
            back_to_menu();
        });
        fout.play();
    }

    @FXML
    public void back_to_menu() {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            Scene newScene = new Scene(root);
            Stage curStage = (Stage) parent.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
