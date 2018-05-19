/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigecmed;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
public class LogginController implements Initializable {

    @FXML
    private JFXButton botonCerrar;
    @FXML
    private JFXTextField textUsuario;
    @FXML
    private JFXPasswordField textContrasena;
    @FXML
    private JFXButton botonIngresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrarPrincipal(ActionEvent event) {
    }

    @FXML
    private void irAPantallaUsuario(ActionEvent event) {
    }
    
}
