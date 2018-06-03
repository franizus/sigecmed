/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.LogginViewController;
import login.User;
import login.UserDataAccessor;
import main.Globals;
import secretaria.Secretaria;
import secretaria.SecretariaDataAccessor;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class AdminViewController implements Initializable {
    
    private UserDataAccessor userDataAccessor;
    private DoctorDataAccessor doctorDataAccessor;
    private SecretariaDataAccessor secretariaDataAccessor;
    private boolean doctorSelected;
    @FXML
    private StackPane stackPane;
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
    @FXML
    private JFXTreeTableView<Secretaria> secTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doctorSelected = false;
        
        try {
            userDataAccessor = new UserDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            secretariaDataAccessor = new SecretariaDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rolCombo.getItems().addAll("Administrador", "Doctor", "Secretaria");
        nombreUsuarioText.setEditable(false);
        
        loadComboEsp();
        loadUsersTable();
        loadDoctorsTable();
        loadSecretariaTable();
    }
    
    @FXML
    void handleDocTable(MouseEvent event) {
        int idDoc = docTable.getSelectionModel().getSelectedItem().getValue().getId_doctor();
        Doctor doctor = doctorDataAccessor.getDoctor(idDoc);
        nombreDocText.setText(doctor.getNombre());
        cedulaDocText.setText(doctor.getCedula());
        dirDocText.setText(doctor.getDireccion());
        telfDocText.setText(doctor.getTelefono());
        correoDocText.setText(doctor.getCorreo());
        especialidadCombo.getSelectionModel().select(doctor.getId_especialidad() - 1);
        doctorSelected = true;
    }
    
    @FXML
    void cancelarDoctor(ActionEvent event) {
        clearDoctor();
    }
    
    
    @FXML
    void eliminarDoctor(ActionEvent event) {
        if (doctorSelected) {
            int idDoc = docTable.getSelectionModel().getSelectedItem().getValue().getId_doctor();
            getDeleteDialog("Seguro desea eliminar este doctor?", 1, idDoc).show();
        } else {
            getDialog("Debe seleccionar un Doctor para poder eliminarlo.").show();
        }
    }

    @FXML
    void guardarDoctor(ActionEvent event) {
        int idDoc = docTable.getSelectionModel().getSelectedItem().getValue().getId_doctor();
        int idEsp = especialidadCombo.getSelectionModel().getSelectedIndex() + 1;
        String nombre = nombreDocText.getText();
        String cedula = cedulaDocText.getText();
        String direccion = dirDocText.getText();
        String telefono = telfDocText.getText();
        String correo = correoDocText.getText();
        Doctor doctor = new Doctor(idDoc, idEsp, nombre, cedula, direccion, telefono, correo);
        if (doctorSelected) {
            doctorDataAccessor.updateDoctor(doctor);
            getDialog("Doctor modificado con exito").show();
        } else {
            doctorDataAccessor.insertNewDoctor(doctor);
            getDialog("Doctor ingresado al sistema con exito").show();
        }
        clearDoctor();
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
    
    private void clearDoctor() {
        nombreDocText.setText("");
        cedulaDocText.setText("");
        dirDocText.setText("");
        telfDocText.setText("");
        correoDocText.setText("");
        especialidadCombo.getSelectionModel().select(-1);
        doctorSelected = false;
    }
    
    private void loadComboEsp() {
        List<String> especialidades = new ArrayList<>();
        especialidades = doctorDataAccessor.getEspecialidadList();
        especialidadCombo.setItems(FXCollections.observableArrayList(especialidades));
    }
    
    private void loadUsersTable() {
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
    
    private void loadDoctorsTable() {
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
        List<Doctor> doctores = doctorDataAccessor.getDoctorsList();
        
        for (Doctor doctor : doctores) {
            doctors.add(doctor);
        }
        
        final TreeItem<Doctor> root = new RecursiveTreeItem<Doctor>(doctors, RecursiveTreeObject::getChildren);
        docTable.getColumns().setAll(idDoctorCol, idEspecCol, nombreCol, cedulaCol, correoCol);
        docTable.setRoot(root);
        docTable.setShowRoot(false);
    }
    
    private void loadSecretariaTable() {
        JFXTreeTableColumn<Secretaria, Number> idSecCol = new JFXTreeTableColumn<Secretaria, Number>("ID Secretaria");
        idSecCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Secretaria, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Secretaria, Number> param) {
                return param.getValue().getValue().id_secretariaProperty();
            }
        });
        
        JFXTreeTableColumn<Secretaria, String> nombreCol = new JFXTreeTableColumn<Secretaria, String>("Nombre");
        nombreCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Secretaria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Secretaria, String> param) {
                return param.getValue().getValue().getNombreProperty();
            }
        });
        
        JFXTreeTableColumn<Secretaria, String> cedulaCol = new JFXTreeTableColumn<Secretaria, String>("Cedula");
        cedulaCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Secretaria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Secretaria, String> param) {
                return param.getValue().getValue().getCedulaProperty();
            }
        });
        
        JFXTreeTableColumn<Secretaria, String> correoCol = new JFXTreeTableColumn<Secretaria, String>("Correo");
        correoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Secretaria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Secretaria, String> param) {
                return param.getValue().getValue().getCorreoProperty();
            }
        });
        
        
        ObservableList<Secretaria> secretary = FXCollections.observableArrayList();
        List<Secretaria> secretarias = secretariaDataAccessor.getSecretariaList();
        
        for (Secretaria secretaria : secretarias) {
            secretary.add(secretaria);
        }
        
        final TreeItem<Secretaria> root = new RecursiveTreeItem<Secretaria>(secretary, RecursiveTreeObject::getChildren);
        secTable.getColumns().setAll(idSecCol, nombreCol, cedulaCol, correoCol);
        secTable.setRoot(root);
        secTable.setShowRoot(false);
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
    
    private JFXDialog getDeleteDialog(String body, int id, int idP) {
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
        layout.setHeading(new Text("Advertencia"));
        JFXButton close = new JFXButton("Cancelar");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        JFXButton eliminar = new JFXButton("Eliminar");
        eliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch(id) {
                    case 1:
                        doctorDataAccessor.deleteDoctor(idP);
                        break;
                    case 2:
                        //secretaria
                        break;
                    default:
                        
                }
                
            }
        });
        layout.setActions(close, eliminar);
        layout.setBody(new Text(body));
        
        return dialog;
    }
    
}
