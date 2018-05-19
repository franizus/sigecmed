/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigecmed;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author PAOLITA
 */
public class PrincipalSecretariaController implements Initializable {

    @FXML
    private JFXButton botonPacientes;
    @FXML
    private JFXButton botonDoctor;
    @FXML
    private JFXButton botonCitas;
    @FXML
    private JFXButton botonReportes;

          
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irAPacientes(ActionEvent event) {
    }

    @FXML
    private void irADoctor(ActionEvent event) {
    }

    @FXML
    private void irACitas(ActionEvent event) {
    }

    @FXML
    private void irAReportes(ActionEvent event) {
    }
    
}
