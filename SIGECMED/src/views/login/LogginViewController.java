/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class LogginViewController implements Initializable {

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
    private void toUserUI(ActionEvent event) {
        if (textUsuario.getText().equals("admin")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/AdminView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/secretaria/menu/MenuSecretariaView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Stage stage1 = (Stage) botonIngresar.getScene().getWindow();
        stage1.close();
    }
    
}
