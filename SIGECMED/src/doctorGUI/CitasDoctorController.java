/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorGUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
public class CitasDoctorController implements Initializable {

    @FXML
    private JFXTextField textNombre;
    @FXML
    private JFXButton botonBuscarPaciente;
    @FXML
    private JFXListView<?> listaTratamientos1;
    @FXML
    private JFXButton botonRegistrarPaciente;
    @FXML
    private JFXRadioButton radiioButSI;
    @FXML
    private JFXRadioButton radioButNO;
    @FXML
    private JFXTextField textDiasTratamiento;
    @FXML
    private JFXDatePicker fechaInicio;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonGuardarCita;
    @FXML
    private JFXTextArea comentarioCitaM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarPaciente(ActionEvent event) {
    }

    @FXML
    private void registrarAtencionCita(ActionEvent event) {
    }

    @FXML
    private void cancelarAtencionCita(ActionEvent event) {
    }

    @FXML
    private void guardarAtencionCita(ActionEvent event) {
    }
    
}
