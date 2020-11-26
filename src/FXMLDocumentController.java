/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;

import Proces.NewData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * @author Ranga Rathnayake
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ProgressBar progrus;

    @FXML
    private Button button;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        button.setDisable(true);
        label.setText("Process Started Please Wait");
        progrus.setVisible(true);
        NewData.getData();
        progrus.setVisible(false);
        label.setText("Completed");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        progrus.setVisible(false);
    }

}
