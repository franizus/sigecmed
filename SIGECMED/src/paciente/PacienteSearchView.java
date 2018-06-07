/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paciente;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import login.LogginViewController;
import main.Globals;

/**
 *
 * @author frani
 */
public class PacienteSearchView extends AnchorPane {

    private PacienteDataAccessor pacienteDataAccessor;
    protected JFXTreeTableView<Paciente> jFXTreeTableView;
    protected JFXButton btnUserGuardar;
    protected JFXButton btnUserCancelar;

    public PacienteSearchView() {
        
        try {
            pacienteDataAccessor = new PacienteDataAccessor(Globals.dbURL);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        jFXTreeTableView = new JFXTreeTableView();
        btnUserGuardar = new JFXButton("Seleccionar");
        btnUserCancelar = new JFXButton("Cancelar");

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: WHITE;");

        jFXTreeTableView.setLayoutX(27.0);
        jFXTreeTableView.setLayoutY(23.0);
        jFXTreeTableView.setPrefWidth(546);
        jFXTreeTableView.setPrefHeight(316);

        btnUserGuardar.setLayoutX(470.0);
        btnUserGuardar.setLayoutY(355.0);
        btnUserGuardar.setPrefWidth(100);
        btnUserGuardar.setPrefHeight(30);
        btnUserGuardar.setStyle("-fx-background-color: #689F38;-fx-text-fill: WHITE;");
        btnUserGuardar.setFont(new Font("System Bold", 15.0));

        btnUserCancelar.setLayoutX(342.0);
        btnUserCancelar.setLayoutY(355.0);
        btnUserCancelar.setPrefWidth(100);
        btnUserCancelar.setPrefHeight(30);
        btnUserCancelar.setFont(new Font("System Bold", 15.0));

        getChildren().add(jFXTreeTableView);
        getChildren().add(btnUserGuardar);
        getChildren().add(btnUserCancelar);
        
        loadPacienteTable();
    }
    
    public JFXTreeTableView<Paciente> getjFXTreeTableView() {
        return jFXTreeTableView;
    }

    public JFXButton getBtnUserGuardar() {
        return btnUserGuardar;
    }

    public JFXButton getBtnUserCancelar() {
        return btnUserCancelar;
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
        jFXTreeTableView.getColumns().setAll(idPacCol, nombreCol, cedulaCol, correoCol);
        jFXTreeTableView.setRoot(root);
        jFXTreeTableView.setShowRoot(false);
    }
    
}
