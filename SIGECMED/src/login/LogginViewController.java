/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Globals;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class LogginViewController implements Initializable {

    private UserDataAccessor dataAccessor;
    @FXML
    private JFXTextField textUsuario;
    @FXML
    private JFXPasswordField textContrasena;
    @FXML
    private JFXButton botonIngresar;
    @FXML
    private StackPane stackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dataAccessor = new UserDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        botonIngresar.setStyle("-fx-padding: 0.7em 0.57em;-fx-font-size: 16px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-pref-width: 94;-fx-pref-height: 44;-fx-text-fill: WHITE;");
    }
    
    @FXML
    void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            botonIngresarAction();
        }
    }

    @FXML
    private void toUserUI(ActionEvent event) {
        botonIngresarAction();
    }
    
    private void botonIngresarAction() {
        String user = textUsuario.getText();
        String password = textContrasena.getText();
        Stage stage1 = (Stage) botonIngresar.getScene().getWindow();
        
        if (user.isEmpty() || password.isEmpty()) {
            getDialog("Los datos de ingreso no pueden estar vacios.").show();
        } else {
            User userSignedIn = new User();
            try {
                userSignedIn = dataAccessor.getUser(user);
            } catch (SQLException ex) {
                Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            switch(userSignedIn.getId_rol()) {
                case 1:
                    if (PasswordUtility.verifyUserPassword(password, userSignedIn.getContrasena(), userSignedIn.getSalt())) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin/AdminView.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        stage1.close();
                    } else {
                        getDialog("Datos de ingreso no son correctos.").show();
                    }
                    break;
                case 2:
                    break;
                case 3:
                    if (PasswordUtility.verifyUserPassword(password, userSignedIn.getContrasena(), userSignedIn.getSalt())) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/secretaria/MenuSecretariaView.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        stage1.close();
                    } else {
                        getDialog("Datos de ingreso no son correctos.").show();
                    }
                    break;
                default:
                    getDialog("Usuario no encontrado en el sistema.").show();
            }
        }
    }
    
    private JFXDialog getDialog(String body) {
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
        layout.setHeading(new Text("Advertencia"));
        JFXButton close = new JFXButton("Cerrar");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        layout.setActions(close);
        layout.setBody(new Text(body));
        
        return dialog;
    }
    
}
