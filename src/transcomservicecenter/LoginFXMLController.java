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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author faiya
 */
public class LoginFXMLController implements Initializable {

    String usermail;
    String userpass;
    String userf;
    String userl;
    String userdb;
    String userph;
    String usercb;
    String space;
    int ln;

    @FXML
    public AnchorPane parent;
    @FXML
    public Button button;
    @FXML
    public Pane content_area;
    @FXML
    public Label txtlbl;
    @FXML
    public TextField txtmail;
    @FXML
    public TextField txtpass;
    @FXML
    public ComboBox<String> combobox;
    ObservableList<String> list = FXCollections.observableArrayList("Customer", "Manager", "Accountant", "Service Representative", "Admin");

    private static double xOffSet = 0;
    private static double yOffSet = 0;

    File file = new File("D:\\NETBEANS\\TranscomServiceCenter\\src\\text file");

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

    void createFolder() {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

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

    void check_data(String sgnmail, String sgnpass, String combob) {
        try {
            RandomAccessFile raf = new RandomAccessFile(file + "\\data.txt", "rw");
            if (sgnmail.isEmpty() || sgnpass.isEmpty() || combob.isEmpty()) {
                txtlbl.setText("Please fill up all the fields.");
            } else {
                for (int i = 0; i < ln; i += 7) {
                    usermail = raf.readLine().substring(7);
                    userf = raf.readLine().substring(11);
                    userl = raf.readLine().substring(10);
                    userdb = raf.readLine().substring(13);
                    userpass = raf.readLine().substring(10);
                    userph = raf.readLine().substring(7);
                    usercb = raf.readLine().substring(13);
                    if (sgnmail.equals(usermail) && sgnpass.equals(userpass) && combob.equals(usercb)) {
                        txtlbl.setText("\t        Login Successful.");
                        Stage stage = new Stage();
                        Parent root;
                        root = FXMLLoader.load(getClass().getResource("AdminFXML.fxml"));
                        Scene newScene = new Scene(root);
                        Stage curStage = (Stage) parent.getScene().getWindow();
                        curStage.setScene(newScene);
                        break;
                    } else {
                        txtlbl.setText("\t\t       Login Failed.");

                    }
                    if (i != 0) {
                        i = i + 1;
                    }

                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void Login(ActionEvent event) {
        createFolder();
        readFile();
        countLines();
        check_data(txtmail.getText(), txtpass.getText(), manage_combobox());
    }

    @FXML
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

    @FXML
    public void fadeOut() {
        FadeTransition fout = new FadeTransition();
        fout.setDuration(Duration.millis(300));
        fout.setNode(parent);
        fout.setFromValue(1);
        fout.setToValue(0);

        fout.setOnFinished((ActionEvent event) -> {
            open_signup();
        });
        fout.play();
    }

    @FXML
    public void open_signup() {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("SignUpFXML.fxml"));
            Scene newScene = new Scene(root);
            Stage curStage = (Stage) parent.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
