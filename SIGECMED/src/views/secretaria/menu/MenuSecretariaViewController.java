/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.secretaria.menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import views.secretaria.citas.AppointmentView;
import views.secretaria.citas.CitasView;

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
            System.out.println("Tile pressed ");
            event.consume();
        });
        
        imageDoctor.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Tile pressed ");
            event.consume();
        });
        
        imageCitas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CitasView ap1 = new CitasView();

            Scene secondScene = new Scene(ap1, 1300, 900);

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
