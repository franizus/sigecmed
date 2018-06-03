/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import login.LogginViewController;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class MenuSecretariaViewController implements Initializable {

    @FXML
    private ImageView imagePaciente;
    @FXML
    private ImageView imageDoctor;
    @FXML
    private ImageView imageCitas;
    @FXML
    private ImageView imageReportes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagePaciente.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/secretaria/paciente/PacienteView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        imageDoctor.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            DoctorView ap1 = new DoctorView();

            Scene secondScene = new Scene(ap1, 1000, 700);

            Stage newWindow = new Stage();
            newWindow.setTitle("Menu Doctor");
            newWindow.setScene(secondScene);

            newWindow.centerOnScreen();
            newWindow.show();
        });
        
        imageCitas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CitasView ap1 = new CitasView();

            Scene secondScene = new Scene(ap1, 1000, 700);

            Stage newWindow = new Stage();
            newWindow.setTitle("Menu Citas");
            newWindow.setScene(secondScene);

            newWindow.centerOnScreen();
            newWindow.show();
        });
        
        imageReportes.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Tile pressed ");
            event.consume();
        });
    }    
    
}
