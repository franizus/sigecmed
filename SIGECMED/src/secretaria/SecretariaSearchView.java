/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

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
public class SecretariaSearchView extends AnchorPane {

    private SecretariaDataAccessor secretariaDataAccessor;
    protected final JFXTreeTableView<Secretaria> jFXTreeTableView;
    protected final JFXButton btnUserGuardar;
    protected final JFXButton btnUserCancelar;

    public SecretariaSearchView() {
        
        try {
            secretariaDataAccessor = new SecretariaDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
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

        btnUserGuardar.setLayoutX(450.0);
        btnUserGuardar.setLayoutY(355.0);
        btnUserGuardar.setPrefWidth(100);
        btnUserGuardar.setPrefHeight(30);
        btnUserGuardar.setStyle("-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        btnUserGuardar.setFont(new Font("System Bold", 15.0));

        btnUserCancelar.setLayoutX(322.0);
        btnUserCancelar.setLayoutY(355.0);
        btnUserCancelar.setPrefWidth(100);
        btnUserCancelar.setPrefHeight(30);
        btnUserCancelar.setFont(new Font("System Bold", 15.0));

        getChildren().add(jFXTreeTableView);
        getChildren().add(btnUserGuardar);
        getChildren().add(btnUserCancelar);
        
        loadSecretariaTable();
    }
    
    public JFXTreeTableView<Secretaria> getjFXTreeTableView() {
        return jFXTreeTableView;
    }

    public JFXButton getBtnUserGuardar() {
        return btnUserGuardar;
    }

    public JFXButton getBtnUserCancelar() {
        return btnUserCancelar;
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
        jFXTreeTableView.getColumns().setAll(idSecCol, nombreCol, cedulaCol, correoCol);
        jFXTreeTableView.setRoot(root);
        jFXTreeTableView.setShowRoot(false);
    }
    
}
