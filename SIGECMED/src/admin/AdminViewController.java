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
import doctor.DoctorSearchView;
import doctor.Especialidad;
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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.LogginViewController;
import login.PasswordUtility;
import login.User;
import login.UserDataAccessor;
import main.Globals;
import main.Validation;
import org.apache.commons.lang3.StringUtils;
import secretaria.Secretaria;
import secretaria.SecretariaDataAccessor;
import secretaria.SecretariaSearchView;

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
    private boolean especialidadSelected;
    private boolean secretariaSelected;
    private boolean userSelected;
    private Secretaria secretariaGlobal;
    private Doctor doctorGlobal;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXTextField nombreUsuarioText;
    @FXML
    private JFXTextField usuarioText;
    @FXML
    private JFXPasswordField passwordText;
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
    private JFXTextField nombreSecText;
    @FXML
    private JFXTextField cedulaSecText;
    @FXML
    private JFXTextField dirSecText;
    @FXML
    private JFXTextField telfSecText;
    @FXML
    private JFXTextField correoSecText;
    @FXML
    private JFXTextField especialidadText;
    @FXML
    private JFXComboBox<String> especialidadCombo;
    @FXML
    private JFXComboBox<String> rolCombo;
    @FXML
    private JFXTreeTableView<User> tableUsers;
    @FXML
    private JFXTreeTableView<Doctor> docTable;
    @FXML
    private JFXTreeTableView<Secretaria> secTable;
    @FXML
    private JFXTreeTableView<Especialidad> tableEspecialidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doctorSelected = false;
        especialidadSelected = false;
        secretariaSelected = false;
        userSelected = false;
        
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
        btnUsuarioBuscar.setDisable(true);
        loadComboEsp();
        loadUsersTable();
        loadDoctorsTable();
        loadSecretariaTable();
        loadEspecialidadTable();
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
    void handleEspTable(MouseEvent event) {
        int idEsp = tableEspecialidad.getSelectionModel().getSelectedItem().getValue().getId_especialidad();
        Especialidad esp = doctorDataAccessor.getEspecialidad(idEsp);
        especialidadText.setText(esp.getNombre());
        especialidadSelected = true;
    }

    @FXML
    void handleSecTable(MouseEvent event) {
        int idSec = secTable.getSelectionModel().getSelectedItem().getValue().getId_secretaria();
        Secretaria sec = secretariaDataAccessor.getSecretaria(idSec);
        nombreSecText.setText(sec.getNombre());
        cedulaSecText.setText(sec.getCedula());
        dirSecText.setText(sec.getDireccion());
        telfSecText.setText(sec.getTelefono());
        correoSecText.setText(sec.getCorreo());
        secretariaSelected = true;
    }

    @FXML
    void handleUserTable(MouseEvent event) {
        String usuarioAux = tableUsers.getSelectionModel().getSelectedItem().getValue().getUsuario();
        User user = userDataAccessor.getUser(usuarioAux);
        rolCombo.getSelectionModel().select(user.getId_rol() - 1);
        nombreUsuarioText.setText(userDataAccessor.getAssociateUserName(user.getId_asociado(), user.getId_rol()));
        usuarioText.setText(user.getUsuario());
        userSelected = true;
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
        clearDoctor();
        loadDoctorsTable();
    }

    @FXML
    void guardarDoctor(ActionEvent event) {
        if (!validateDoctor()) {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        } else {
            if (Validation.validarCedula(cedulaDocText.getText())) {
                int idDoc = 0;
                if (doctorSelected) {
                    idDoc = docTable.getSelectionModel().getSelectedItem().getValue().getId_doctor();
                }
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
                loadDoctorsTable();
            } else {
                getDialog("Cedula ingresada es incorrecta").show();
            }
        }
    }
    
    @FXML
    void cancelarSecretaria(ActionEvent event) {
        clearSecretaria();
    }
    
    @FXML
    void cancelarEsp(ActionEvent event) {
        clearEsp();
    }

    @FXML
    void cancelarUsuario(ActionEvent event) {
        clearUser();
    }
    
    @FXML
    void eliminarEsp(ActionEvent event) {
        if (especialidadSelected) {
            int idEsp = tableEspecialidad.getSelectionModel().getSelectedItem().getValue().getId_especialidad();
            getDeleteDialog("Seguro desea eliminar esta especialidad?", 4, idEsp).show();
        } else {
            getDialog("Debe seleccionar una Especialidad para poder eliminarla.").show();
        }
        clearEsp();
        loadEspecialidadTable();
        loadComboEsp();
    }
    
    @FXML
    void eliminarUsuario(ActionEvent event) {
        if (userSelected) {
            int idUser = tableUsers.getSelectionModel().getSelectedItem().getValue().getId_user();
            getDeleteDialog("Seguro desea eliminar este usuario?", 3, idUser).show();
        } else {
            getDialog("Debe seleccionar un Usuario para poder eliminarlo.").show();
        }
        clearUser();
        loadUsersTable();
    }

    @FXML
    void eliminarSecretaria(ActionEvent event) {
        if (secretariaSelected) {
            int idSec = secTable.getSelectionModel().getSelectedItem().getValue().getId_secretaria();
            getDeleteDialog("Seguro desea eliminar esta secretaria?", 2, idSec).show();
        } else {
            getDialog("Debe seleccionar una Secretaria para poder eliminarla.").show();
        }
        clearSecretaria();
        loadSecretariaTable();
    }
    
    @FXML
    void guardarEsp(ActionEvent event) {
        if (!validateEsp()) {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        } else {
            int idEsp = 0;
            if (especialidadSelected) {
                idEsp = tableEspecialidad.getSelectionModel().getSelectedItem().getValue().getId_especialidad();
            }
            String nombre = especialidadText.getText();
            Especialidad esp = new Especialidad(idEsp, nombre);
            if (especialidadSelected) {
                doctorDataAccessor.updateEsp(esp);
                getDialog("Especialidad modificada con exito").show();
            } else {
                doctorDataAccessor.insertNewEsp(esp);
                getDialog("Especialidad ingresada al sistema con exito").show();
            }
            clearEsp();
            loadEspecialidadTable();
            loadComboEsp();
        }
    }

    @FXML
    void guardarSecretaria(ActionEvent event) {
        if (!validateSecretaria()) {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        } else {
            if (Validation.validarCedula(cedulaSecText.getText())) {
                int idSec = 0;
                if (secretariaSelected) {
                    idSec = secTable.getSelectionModel().getSelectedItem().getValue().getId_secretaria();
                }
                String nombre = nombreSecText.getText();
                String cedula = cedulaSecText.getText();
                String direccion = dirSecText.getText();
                String telefono = telfSecText.getText();
                String correo = correoSecText.getText();
                Secretaria sec = new Secretaria(idSec, nombre, cedula, direccion, telefono, correo);
                if (secretariaSelected) {
                    secretariaDataAccessor.updateSec(sec);
                    getDialog("Secretaria modificada con exito").show();
                } else {
                    secretariaDataAccessor.insertNewSec(sec);
                    getDialog("Secretaria ingresada al sistema con exito").show();
                }
                clearSecretaria();
                loadSecretariaTable();
            } else {
                getDialog("Cedula ingresada es incorrecta").show();
            }
        }
    }
    
    @FXML
    private void guardarUsuario(ActionEvent event) {
        if (!validateUser()) {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        } else {
            int idUser = 0;
            if (userSelected) {
                idUser = tableUsers.getSelectionModel().getSelectedItem().getValue().getId_user();
            }
            int id_rol = rolCombo.getSelectionModel().getSelectedIndex() + 1;
            String usuario = usuarioText.getText();
            String salt = PasswordUtility.getSalt(30);
            String contrasena = PasswordUtility.generateSecurePassword(passwordText.getText(), salt);
            int id_asociado = 0;
            switch(id_rol) {
                case 2:
                    id_asociado = doctorGlobal.getId_doctor();
                    break;
                case 3:
                    id_asociado = secretariaGlobal.getId_secretaria();
                    break;
            }
            User user = new User(idUser, id_rol, usuario, contrasena, salt, id_asociado);
            if (userSelected) {
                userDataAccessor.updateUser(user);
                getDialog("Usuario modificado con exito").show();
            } else {
                userDataAccessor.insertNewUser(user);
                getDialog("Usuario ingresado al sistema con exito").show();
            }
            clearUser();
            loadUsersTable();
        }
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
                    SecretariaSearchView ssv = new SecretariaSearchView();
                    ssv.getBtnUserCancelar().setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Stage stage1 = (Stage) ssv.getBtnUserCancelar().getScene().getWindow();
                            stage1.close();
                        }
                    });
                    ssv.getBtnUserGuardar().setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            int idSec = ssv.getjFXTreeTableView().getSelectionModel().getSelectedItem().getValue().getId_secretaria();
                            secretariaGlobal = secretariaDataAccessor.getSecretaria(idSec);
                            nombreUsuarioText.setText(secretariaGlobal.getNombre());
                            Stage stage1 = (Stage) ssv.getBtnUserCancelar().getScene().getWindow();
                            stage1.close();
                        }
                    });
                    
                    Scene secondScene = new Scene(ssv, 600, 400);

                    Stage newWindow = new Stage();
                    newWindow.setTitle("Buscar Secretaria");
                    newWindow.getIcons().add(new Image("/images/report.png"));
                    newWindow.setScene(secondScene);

                    newWindow.centerOnScreen();
                    newWindow.show();
                }
            });
        } else {
            btnUsuarioBuscar.setDisable(false);
            btnUsuarioBuscar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
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
                            nombreUsuarioText.setText(doctorGlobal.getNombre());
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
        docTable.getSelectionModel().select(null);
    }
    
    private void clearSecretaria() {
        nombreSecText.setText("");
        cedulaSecText.setText("");
        dirSecText.setText("");
        telfSecText.setText("");
        correoSecText.setText("");
        secretariaSelected = false;
        secTable.getSelectionModel().select(null);
    }
    
    private void clearUser() {
        nombreUsuarioText.setText("");
        usuarioText.setText("");
        passwordText.setText("");
        userSelected = false;
        tableUsers.getSelectionModel().select(null);
    }
    
    private void clearEsp() {
        especialidadText.setText("");
        especialidadSelected = false;
        tableEspecialidad.getSelectionModel().select(null);
    }
    
    private void loadComboEsp() {
        List<String> especialidades = new ArrayList<>();
        especialidades = doctorDataAccessor.getEspecialidadStringList();
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
        List<User> usuarios = userDataAccessor.getUsersList();
        
        for (User usuario : usuarios) {
            users.add(usuario);
        }
        
        final TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);
        tableUsers.getColumns().setAll(idRolCol, usuarioCol, idAsociadoCol);
        tableUsers.setRoot(root);
        tableUsers.setShowRoot(false);
    }
    
    private boolean validateDoctor() {
        boolean aux1 = StringUtils.isEmpty(nombreDocText.getText());
        boolean aux2 = StringUtils.isEmpty(cedulaDocText.getText());
        boolean aux3 = StringUtils.isEmpty(correoDocText.getText());
        boolean aux4 = StringUtils.isEmpty(especialidadCombo.getSelectionModel().getSelectedItem());
        
        return !(aux1 && aux2 && aux3 && aux4);
    }
    
    private boolean validateSecretaria() {
        boolean aux1 = StringUtils.isEmpty(nombreSecText.getText());
        boolean aux2 = StringUtils.isEmpty(cedulaSecText.getText());
        boolean aux3 = StringUtils.isEmpty(correoSecText.getText());
        
        return !(aux1 && aux2 && aux3);
    }
    
    private boolean validateUser() {
        boolean aux1 = StringUtils.isEmpty(usuarioText.getText());
        boolean aux2 = StringUtils.isEmpty(passwordText.getText());
        boolean aux3 = StringUtils.isEmpty(rolCombo.getSelectionModel().getSelectedItem());
        
        return !(aux1 && aux2 && aux3);
    }
    
    private boolean validateEsp() {
        return !StringUtils.isEmpty(especialidadText.getText());
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
    
    private void loadEspecialidadTable() {
        JFXTreeTableColumn<Especialidad, Number> idEspCol = new JFXTreeTableColumn<Especialidad, Number>("ID Especialidad");
        idEspCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Especialidad, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Especialidad, Number> param) {
                return param.getValue().getValue().id_especialidadProperty();
            }
        });
        
        JFXTreeTableColumn<Especialidad, String> nombreCol = new JFXTreeTableColumn<Especialidad, String>("Nombre");
        nombreCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Especialidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Especialidad, String> param) {
                return param.getValue().getValue().getNombreProperty();
            }
        });
        
        ObservableList<Especialidad> especialidad = FXCollections.observableArrayList();
        List<Especialidad> especialidades = doctorDataAccessor.getEspecialidadList();
        
        for (Especialidad esp : especialidades) {
            especialidad.add(esp);
        }
        
        final TreeItem<Especialidad> root = new RecursiveTreeItem<Especialidad>(especialidad, RecursiveTreeObject::getChildren);
        tableEspecialidad.getColumns().setAll(idEspCol, nombreCol);
        tableEspecialidad.setRoot(root);
        tableEspecialidad.setShowRoot(false);
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
                        secretariaDataAccessor.deleteSec(idP);
                        break;
                    case 3:
                        userDataAccessor.deleteUser(idP);
                        break;
                    default:
                        doctorDataAccessor.deleteEspecialidad(idP);
                }
                
            }
        });
        layout.setActions(close, eliminar);
        layout.setBody(new Text(body));
        
        return dialog;
    }
    
}
