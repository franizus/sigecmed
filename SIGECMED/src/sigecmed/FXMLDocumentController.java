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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 *
 * @author frani
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private JFXButton botonIngresar;
    @FXML
    private JFXTextField usuario = new JFXTextField();
    @FXML
    private JFXPasswordField contrasenia;
    @FXML
    private JFXButton botonCerrar;
    @FXML
    private Hyperlink linkContraseniaOlvidada;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    private void handleCerrar(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ingresarAVistaDelUsuario(ActionEvent event) {
        //temporal: redigir FXML con usuario
        
        if(usuario.getText().equalsIgnoreCase("doctor")){ 
            
        }else if(usuario.getText().equalsIgnoreCase("secretaria")){ 
            
        }
    }

    @FXML
    private void recuperarContrasenia(ActionEvent event) {
    }

    @FXML
    private void cerrarPrincipal(ActionEvent event) {
        System.exit(0);
    }
    
}
