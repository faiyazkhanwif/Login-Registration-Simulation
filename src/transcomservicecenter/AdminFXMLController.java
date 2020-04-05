/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transcomservicecenter;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author faiya
 */
public class AdminFXMLController implements Initializable {

    @FXML
    public Button button;
    @FXML
    public AnchorPane parent;
    @FXML
    public Pane content_area;

    private static double xOffSet = 0;
    private static double yOffSet = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    @FXML
    public void fadeIn() {
        FadeTransition fout = new FadeTransition();
        fout.setDuration(Duration.millis(800));
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
