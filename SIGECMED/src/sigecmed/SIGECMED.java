/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigecmed;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author frani
 */
public class SIGECMED extends Application {
    Scene scene, scene2;
    Stage thestage;
    
    @Override
    public void start(Stage stage) throws Exception {
        thestage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
  
    /*public void abrirVistaDoctor(Stage stage) throws Exception{
        //File file = new File ("../doctorGUI/citasDoctor.fxml");
        //String ruta = file.getAbsolutePath();
        Parent root = FXMLLoader.load(getClass().getResource("citasDoctor.fxml"));      
        Scene scene = new Scene(root);// indicamos qué scene contendrá el Parent de cada ventana
        stage.setScene(scene);//declaras la escena de la ventana, en este caso sera la principal
        stage.show();
    }*/

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    /*private void ButtonClicked(ActionEvent e) {
    
    if (e.getSource()== 1)
        thestage.setScene(scene2);
    else
        thestage.setScene(scene);
    }*/
    
}
