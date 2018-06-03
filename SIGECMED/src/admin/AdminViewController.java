/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import doctor.Doctor;
import doctor.DoctorDataAccessor;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.LogginViewController;
import login.User;
import login.UserDataAccessor;
import main.Globals;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class AdminViewController implements Initializable {
    
    private UserDataAccessor userDataAccessor;
    private DoctorDataAccessor doctorDataAccessor;
    @FXML
    private JFXComboBox rolCombo;
    @FXML
    private JFXTextField nombreUsuarioText;
    @FXML
    private JFXTextField usuarioText;
    @FXML
    private JFXPasswordField passwordText;
    @FXML
    private JFXButton btnUserGuardar;
    @FXML
    private JFXButton btnUsuarioBuscar;
    @FXML
    private JFXTextField nombreDocText;
    @FXML
    private JFXTextField cedulaDocText;
    @FXML
    private JFXTextField dirDocText;
    @FXML
    private JFXTextField telfDocText;
    @FXML
    private JFXTextField correoDocText;
    @FXML
    private JFXComboBox<String> especialidadCombo;
    @FXML
    private JFXTreeTableView<User> tableUsers;
    @FXML
    private JFXTreeTableView<Doctor> docTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userDataAccessor = new UserDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rolCombo.getItems().addAll("Administrador", "Secretaria", "Doctor");
        nombreUsuarioText.setEditable(false);
        
        initializeComboEsp();
        initializeUsersTable();
        initializeDoctorsTable();
    }
    
    @FXML
    void handleDocTable(MouseEvent event) {
        try {
            int idDoc = docTable.getSelectionModel().getSelectedItem().getValue().getId_doctor();
            Doctor doctor = doctorDataAccessor.getDoctor(idDoc);
            nombreDocText.setText(doctor.getNombre());
            cedulaDocText.setText(doctor.getCedula());
            dirDocText.setText(doctor.getDireccion());
            telfDocText.setText(doctor.getTelefono());
            correoDocText.setText(doctor.getCorreo());
            especialidadCombo.getSelectionModel().select(doctor.getId_especialidad()-1);
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void cancelarDoctor(ActionEvent event) {
        nombreDocText.setText("");
        cedulaDocText.setText("");
        dirDocText.setText("");
        telfDocText.setText("");
        correoDocText.setText("");
        especialidadCombo.getSelectionModel().select(-1);
    }
    
    @FXML
    private void guardarUsuario(ActionEvent event) {
        
    }
    
    @FXML
    private void comboAction(ActionEvent event) {
        if (rolCombo.getValue().equals("Administrador")) {
            btnUsuarioBuscar.setDisable(true);
        } else if (rolCombo.getValue().equals("Secretaria")) {
            btnUsuarioBuscar.setDisable(false);
            btnUsuarioBuscar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin/SecretariaSearchView.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            btnUsuarioBuscar.setDisable(false);
            btnUsuarioBuscar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin/DoctorSearchView.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }
    
    private void initializeComboEsp() {
        List<String> especialidades = new ArrayList<>();
        try {
            especialidades = doctorDataAccessor.getEspecialidadList();
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        especialidadCombo.setItems(FXCollections.observableArrayList(especialidades));
    }
    
    private void initializeUsersTable() {
        JFXTreeTableColumn<User, Number> idUserCol = new JFXTreeTableColumn<User, Number>("ID Usuario");
        idUserCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<User, Number> param) {
                return param.getValue().getValue().id_userProperty();
            }
        });
        
        JFXTreeTableColumn<User, Number> idRolCol = new JFXTreeTableColumn<User, Number>("ID Rol");
        idRolCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<User, Number> param) {
                return param.getValue().getValue().id_rolProperty();
            }
        });
        
        JFXTreeTableColumn<User, String> usuarioCol = new JFXTreeTableColumn<User, String>("Usuario");
        usuarioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return param.getValue().getValue().usuarioProperty();
            }
        });
        
        JFXTreeTableColumn<User, Number> idAsociadoCol = new JFXTreeTableColumn<User, Number>("ID Asociado");
        idAsociadoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<User, Number> param) {
                return param.getValue().getValue().id_asociadoProperty();
            }
        });
        
        
        ObservableList<User> users = FXCollections.observableArrayList();
        List<User> usuarios = new ArrayList<>();
        try {
            usuarios = userDataAccessor.getUsersList();
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (User usuario : usuarios) {
            users.add(usuario);
        }
        
        final TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);
        tableUsers.getColumns().setAll(idRolCol, usuarioCol, idAsociadoCol);
        tableUsers.setRoot(root);
        tableUsers.setShowRoot(false);
    }
    
    private void initializeDoctorsTable() {
        JFXTreeTableColumn<Doctor, Number> idDoctorCol = new JFXTreeTableColumn<Doctor, Number>("ID Doctor");
        idDoctorCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Doctor, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Doctor, Number> param) {
                return param.getValue().getValue().id_doctorProperty();
            }
        });
        
        JFXTreeTableColumn<Doctor, Number> idEspecCol = new JFXTreeTableColumn<Doctor, Number>("ID Especialidad");
        idEspecCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Doctor, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Doctor, Number> param) {
                return param.getValue().getValue().id_especialidadProperty();
            }
        });
        
        JFXTreeTableColumn<Doctor, String> nombreCol = new JFXTreeTableColumn<Doctor, String>("Nombre");
        nombreCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Doctor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Doctor, String> param) {
                return param.getValue().getValue().getNombreProperty();
            }
        });
        
        JFXTreeTableColumn<Doctor, String> cedulaCol = new JFXTreeTableColumn<Doctor, String>("Cedula");
        cedulaCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Doctor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Doctor, String> param) {
                return param.getValue().getValue().getCedulaProperty();
            }
        });
        
        JFXTreeTableColumn<Doctor, String> correoCol = new JFXTreeTableColumn<Doctor, String>("Correo");
        correoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Doctor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Doctor, String> param) {
                return param.getValue().getValue().getCorreoProperty();
            }
        });
        
        
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        List<Doctor> doctores = new ArrayList<>();
        try {
            doctores = doctorDataAccessor.getDoctorsList();
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Doctor doctor : doctores) {
            doctors.add(doctor);
        }
        
        final TreeItem<Doctor> root = new RecursiveTreeItem<Doctor>(doctors, RecursiveTreeObject::getChildren);
        docTable.getColumns().setAll(idDoctorCol, idEspecCol, nombreCol, cedulaCol, correoCol);
        docTable.setRoot(root);
        docTable.setShowRoot(false);
    }
    
}
