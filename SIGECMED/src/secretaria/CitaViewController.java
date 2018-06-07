/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import doctor.Doctor;
import doctor.DoctorDataAccessor;
import doctor.Horario;
import doctor.HorarioDataAccessor;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.LogginViewController;
import main.Globals;
import org.apache.commons.lang3.StringUtils;
import paciente.Cita;
import paciente.CitaDataAccessor;
import paciente.Paciente;
import paciente.PacienteDataAccessor;
import paciente.PacienteSearchView;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class CitaViewController implements Initializable {

    private CitaDataAccessor citaDataAccessor;
    private PacienteDataAccessor pacienteDataAccessor;
    private DoctorDataAccessor doctorDataAccessor;
    private HorarioDataAccessor horarioDataAccessor;
    private Paciente pacienteGlobal;
    private Doctor doctorGlobal;
    private Horario horarioGlobal;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXComboBox<String> comboDoctor;
    @FXML
    private JFXTextField txtPaciente;
    @FXML
    private JFXTimePicker timeHoraInicio;
    @FXML
    private JFXTimePicker timeHoraFin;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private Label labelHoraInicio;
    @FXML
    private Label labelHoraFin;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXDatePicker datePick;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            horarioDataAccessor = new HorarioDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            pacienteDataAccessor = new PacienteDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            citaDataAccessor = new CitaDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtPaciente.setEditable(false);
        labelHoraInicio.setText("");
        labelHoraFin.setText("");
        
        loadCombo();
    }    
    
    @FXML
    void handleBuscar(ActionEvent event) {
        PacienteSearchView psv = new PacienteSearchView();
        psv.getBtnUserCancelar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage stage1 = (Stage) psv.getBtnUserCancelar().getScene().getWindow();
                stage1.close();
            }
        });
        psv.getBtnUserGuardar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int idPac = psv.getjFXTreeTableView().getSelectionModel().getSelectedItem().getValue().getId_paciente();
                pacienteGlobal = pacienteDataAccessor.getPaciente(idPac);
                txtPaciente.setText(pacienteGlobal.getNombre());
                Stage stage1 = (Stage) psv.getBtnUserCancelar().getScene().getWindow();
                stage1.close();
            }
        });

        Scene secondScene = new Scene(psv, 600, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("Buscar Paciente");
        newWindow.getIcons().add(new Image("/images/report.png"));
        newWindow.setScene(secondScene);

        newWindow.centerOnScreen();
        newWindow.show();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        Stage stage1 = (Stage) btnBuscar.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void handleGuardar(ActionEvent event) {
        if (validate()) {
            if (validateHours()) {
                int idDoc = doctorGlobal.getId_doctor();
                int idPac = pacienteGlobal.getId_paciente();
                Date fecha = Date.valueOf(datePick.getValue());
                Time horaI = Time.valueOf(timeHoraInicio.getValue());
                Time horaF = Time.valueOf(timeHoraFin.getValue());
                Cita cita = new Cita(0, idDoc, idPac, fecha, horaI, horaF);
                
                citaDataAccessor.insertNewCita(cita);
                getDialog("Cita ingresada al sistema con exito").show();
                Stage stage1 = (Stage) btnBuscar.getScene().getWindow();
                stage1.close();
            } else {
                getDialog("El tiempo de la cita no concuerda con el horario del doctor.").show();
            }
        } else {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        }
    }
    
    private boolean validateHours() {
        boolean first = isBetween(timeHoraInicio.getValue(), horarioGlobal.getHoraInicio().toLocalTime(), horarioGlobal.getHoraFin().toLocalTime());
        boolean second = isBetween(timeHoraFin.getValue(), horarioGlobal.getHoraInicio().toLocalTime(), horarioGlobal.getHoraFin().toLocalTime());
        return first && second;
    }
    
    private boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);
    }
    
    private boolean validate() {
        boolean aux1 = StringUtils.isEmpty(comboDoctor.getSelectionModel().getSelectedItem());
        boolean aux2 = StringUtils.isEmpty(txtPaciente.getText());
        boolean aux3 = StringUtils.isEmpty(datePick.getValue().toString());
        boolean aux4 = StringUtils.isEmpty(timeHoraInicio.getValue().toString());
        boolean aux5 = StringUtils.isEmpty(timeHoraFin.getValue().toString());
        
        return !(aux1 && aux2 && aux3 && aux4 && aux5);
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
    
    @FXML
    void comboAction(ActionEvent event) {
        int idDoc = comboDoctor.getSelectionModel().getSelectedIndex() + 1;
        doctorGlobal = doctorDataAccessor.getDoctor(idDoc);
        horarioGlobal = horarioDataAccessor.getHorarioByDoctor(idDoc);
        labelHoraInicio.setText(horarioGlobal.getHoraInicio().toString().substring(0, 5));
        labelHoraFin.setText(horarioGlobal.getHoraFin().toString().substring(0, 5));
    }
    
    private void loadCombo() {
        List<String> doctores = new ArrayList<>();
        doctores = doctorDataAccessor.getDoctorsStringList();
        comboDoctor.setItems(FXCollections.observableArrayList(doctores));
    }
    
}
