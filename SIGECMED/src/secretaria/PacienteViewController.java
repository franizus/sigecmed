/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import login.LogginViewController;
import main.Globals;
import main.Validation;
import org.apache.commons.lang3.StringUtils;
import paciente.Paciente;
import paciente.PacienteDataAccessor;

/**
 * FXML Controller class
 *
 * @author frani
 */
public class PacienteViewController implements Initializable {

    private PacienteDataAccessor pacienteDataAccessor;
    private boolean pacienteSelected;
    private Paciente pacienteGlobal;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXTextField nombrePacText;
    @FXML
    private JFXTextField cedulaPacText;
    @FXML
    private JFXTextField dirPacText;
    @FXML
    private JFXTextField telfPacText;
    @FXML
    private JFXTextField correoPacText;
    @FXML
    private JFXTreeTableView<Paciente> pacTable;
    @FXML
    private JFXButton btnSecCancelar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnEliminar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pacienteSelected = false;
        
        try {
            pacienteDataAccessor = new PacienteDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadPacienteTable();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        clear();
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        if (pacienteSelected) {
            int idPac = pacTable.getSelectionModel().getSelectedItem().getValue().getId_paciente();
            getDeleteDialog("Seguro desea eliminar este paciente?", idPac).show();
        } else {
            getDialog("Debe seleccionar un Paciente para poder eliminarlo.").show();
        }
        clear();
        loadPacienteTable();
    }

    @FXML
    void handleGuardar(ActionEvent event) {
        if (!validate()) {
            getDialog("Campos obligatorios* no pueden estar vacios").show();
        } else {
            if (Validation.validarCedula(cedulaPacText.getText())) {
                int idPac = 0;
                if (pacienteSelected) {
                    idPac = pacTable.getSelectionModel().getSelectedItem().getValue().getId_paciente();
                }
                String nombre = nombrePacText.getText();
                String cedula = cedulaPacText.getText();
                String direccion = dirPacText.getText();
                String telefono = telfPacText.getText();
                String correo = correoPacText.getText();
                Paciente pac = new Paciente(idPac, nombre, cedula, direccion, telefono, correo);
                if (pacienteSelected) {
                    pacienteDataAccessor.updatePac(pac);
                    getDialog("Paciente modificado con exito").show();
                } else {
                    pacienteDataAccessor.insertNewPac(pac);
                    getDialog("Paciente ingresado al sistema con exito").show();
                }
                clear();
                loadPacienteTable();
            } else {
                getDialog("Cedula ingresada es incorrecta").show();
            }
        }
    }

    @FXML
    void handlePacTable(MouseEvent event) {
        int idPac = pacTable.getSelectionModel().getSelectedItem().getValue().getId_paciente();
        Paciente pac = pacienteDataAccessor.getPaciente(idPac);
        nombrePacText.setText(pac.getNombre());
        cedulaPacText.setText(pac.getCedula());
        dirPacText.setText(pac.getDireccion());
        telfPacText.setText(pac.getTelefono());
        correoPacText.setText(pac.getCorreo());
        pacienteSelected = true;
    }
    
    private void loadPacienteTable() {
        JFXTreeTableColumn<Paciente, Number> idPacCol = new JFXTreeTableColumn<Paciente, Number>("ID Paciente");
        idPacCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paciente, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Paciente, Number> param) {
                return param.getValue().getValue().id_pacienteProperty();
            }
        });
        
        JFXTreeTableColumn<Paciente, String> nombreCol = new JFXTreeTableColumn<Paciente, String>("Nombre");
        nombreCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paciente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paciente, String> param) {
                return param.getValue().getValue().getNombreProperty();
            }
        });
        
        JFXTreeTableColumn<Paciente, String> cedulaCol = new JFXTreeTableColumn<Paciente, String>("Cedula");
        cedulaCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paciente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paciente, String> param) {
                return param.getValue().getValue().getCedulaProperty();
            }
        });
        
        JFXTreeTableColumn<Paciente, String> correoCol = new JFXTreeTableColumn<Paciente, String>("Correo");
        correoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paciente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paciente, String> param) {
                return param.getValue().getValue().getCorreoProperty();
            }
        });
        
        
        ObservableList<Paciente> pacient = FXCollections.observableArrayList();
        List<Paciente> pacientes = pacienteDataAccessor.getPacienteList();
        
        for (Paciente paciente : pacientes) {
            pacient.add(paciente);
        }
        
        final TreeItem<Paciente> root = new RecursiveTreeItem<Paciente>(pacient, RecursiveTreeObject::getChildren);
        pacTable.getColumns().setAll(idPacCol, nombreCol, cedulaCol, correoCol);
        pacTable.setRoot(root);
        pacTable.setShowRoot(false);
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
    
    private JFXDialog getDeleteDialog(String body, int idP) {
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
                pacienteDataAccessor.deletePac(idP);
            }
        });
        layout.setActions(close, eliminar);
        layout.setBody(new Text(body));
        
        return dialog;
    }

    private void clear() {
        nombrePacText.setText("");
        cedulaPacText.setText("");
        dirPacText.setText("");
        telfPacText.setText("");
        correoPacText.setText("");
        pacienteSelected = false;
        pacTable.getSelectionModel().select(null);
    }

    private boolean validate() {
        boolean aux1 = StringUtils.isEmpty(nombrePacText.getText());
        boolean aux2 = StringUtils.isEmpty(cedulaPacText.getText());
        boolean aux3 = StringUtils.isEmpty(correoPacText.getText());
        
        return !(aux1 && aux2 && aux3);
    }
    
}
