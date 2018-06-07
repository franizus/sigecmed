/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import doctor.Doctor;
import doctor.DoctorDataAccessor;
import doctor.DoctorSearchView;
import doctor.Horario;
import doctor.HorarioDataAccessor;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.LogginViewController;
import main.Globals;
import org.apache.commons.lang3.StringUtils;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class HorarioDoctorViewController implements Initializable {

    private DoctorDataAccessor doctorDataAccessor;
    private HorarioDataAccessor horarioDataAccessor;
    private Doctor doctorGlobal;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXCheckBox checkLunes;
    @FXML
    private JFXCheckBox checkMartes;
    @FXML
    private JFXCheckBox checkMiercoles;
    @FXML
    private JFXCheckBox checkJueves;
    @FXML
    private JFXCheckBox checkViernes;
    @FXML
    private JFXTextField txtDoctor;
    @FXML
    private JFXDatePicker fechaInicio;
    @FXML
    private JFXDatePicker fechaFin;
    @FXML
    private JFXTimePicker horaInicio;
    @FXML
    private JFXTimePicker horaFin;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            horarioDataAccessor = new HorarioDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtDoctor.setEditable(false);
        fechaInicio.setValue(LocalDate.now());
        fechaFin.setValue(LocalDate.now());
        horaInicio.setValue(LocalTime.now());
        horaFin.setValue(LocalTime.now());
    }
    
    @FXML
    void handleBuscar(ActionEvent event) {
        DoctorSearchView dsv = new DoctorSearchView();
        dsv.getBtnUserCancelar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage stage1 = (Stage) dsv.getBtnUserCancelar().getScene().getWindow();
                stage1.close();
            }
        });
        dsv.getBtnUserGuardar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int idDoc = dsv.getjFXTreeTableView().getSelectionModel().getSelectedItem().getValue().getId_doctor();
                doctorGlobal = doctorDataAccessor.getDoctor(idDoc);
                txtDoctor.setText(doctorGlobal.getNombre());
                Stage stage1 = (Stage) dsv.getBtnUserCancelar().getScene().getWindow();
                stage1.close();
            }
        });

        Scene secondScene = new Scene(dsv, 600, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("Buscar Doctor");
        newWindow.getIcons().add(new Image("/images/report.png"));
        newWindow.setScene(secondScene);

        newWindow.centerOnScreen();
        newWindow.show();
    }
    
    @FXML
    void handleCancelar(ActionEvent event) {
        clear();
    }

    @FXML
    void handleGuardar(ActionEvent event) {
        if (validate()) {
            int idDoctor = doctorGlobal.getId_doctor();
            String lun = (checkLunes.selectedProperty().get() ? "1" : "0");
            String mar = (checkMartes.selectedProperty().get() ? "1" : "0");
            String mier = (checkMiercoles.selectedProperty().get() ? "1" : "0");
            String juev = (checkJueves.selectedProperty().get() ? "1" : "0");
            String vier = (checkViernes.selectedProperty().get() ? "1" : "0");
            String intervalo = lun + mar + mier + juev + vier;
            Time horaI = Time.valueOf(horaInicio.getValue());
            Time horaF = Time.valueOf(horaFin.getValue());
            Date fechaI = Date.valueOf(fechaInicio.getValue());
            Date fechaF = Date.valueOf(fechaFin.getValue());
            
            Horario horario = new Horario(0, idDoctor, intervalo, horaI, horaF, fechaI, fechaF);
            horarioDataAccessor.insertNewHorario(horario);
            getDialog("Horario ingresado al sistema con exito").show();
            clear();
        } else {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        }
    }
    
    private void clear() {
        txtDoctor.setText("");
        checkLunes.selectedProperty().set(false);
        checkMartes.selectedProperty().set(false);
        checkMiercoles.selectedProperty().set(false);
        checkJueves.selectedProperty().set(false);
        checkViernes.selectedProperty().set(false);
        fechaInicio.setValue(LocalDate.now());
        fechaFin.setValue(LocalDate.now());
        horaInicio.setValue(LocalTime.now());
        horaFin.setValue(LocalTime.now());
    }
    
    private boolean validate() {
        boolean aux1 = StringUtils.isEmpty(txtDoctor.getText());
        boolean lun = checkLunes.selectedProperty().get();
        boolean mar = checkMartes.selectedProperty().get();
        boolean mier = checkMiercoles.selectedProperty().get();
        boolean juev = checkJueves.selectedProperty().get();
        boolean vier = checkViernes.selectedProperty().get();
        boolean aux2 = lun || mar || mier || juev || vier;
        boolean aux3 = StringUtils.isEmpty(fechaInicio.getValue().toString());
        boolean aux4 = StringUtils.isEmpty(fechaFin.getValue().toString());
        boolean aux5 = StringUtils.isEmpty(horaInicio.getValue().toString());
        boolean aux6 = StringUtils.isEmpty(horaFin.getValue().toString());
        
        return !(aux1 && aux2 && aux3 && aux4 && aux5 && aux6);
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
