package doctor;

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

public class DoctorSearchView extends AnchorPane {

    private DoctorDataAccessor doctorDataAccessor;
    protected final JFXTreeTableView<Doctor> jFXTreeTableView;
    protected final JFXButton btnUserGuardar;
    protected final JFXButton btnUserCancelar;

    public DoctorSearchView() {
        
        try {
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
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

        loadDoctorsTable();
    }

    public JFXTreeTableView<Doctor> getjFXTreeTableView() {
        return jFXTreeTableView;
    }

    public JFXButton getBtnUserGuardar() {
        return btnUserGuardar;
    }

    public JFXButton getBtnUserCancelar() {
        return btnUserCancelar;
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
        jFXTreeTableView.getColumns().setAll(idDoctorCol, idEspecCol, nombreCol, cedulaCol, correoCol);
        jFXTreeTableView.setRoot(root);
        jFXTreeTableView.setShowRoot(false);
    }
    
}
