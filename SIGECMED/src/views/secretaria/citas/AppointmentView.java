package views.secretaria.citas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class AppointmentView extends AnchorPane {

    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final ColumnConstraints columnConstraints2;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final RowConstraints rowConstraints2;
    protected final RowConstraints rowConstraints3;
    protected final RowConstraints rowConstraints4;
    protected final RowConstraints rowConstraints5;
    protected final JFXButton btn1;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final JFXTimePicker jFXTimePicker;
    protected final JFXTimePicker jFXTimePicker0;
    protected final JFXDatePicker jFXDatePicker;
    protected final JFXTextField jFXTextField;

    public AppointmentView() {
        
        this.setStyle("-fx-background-color:WHITE;");

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        columnConstraints2 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        rowConstraints2 = new RowConstraints();
        rowConstraints3 = new RowConstraints();
        rowConstraints4 = new RowConstraints();
        rowConstraints5 = new RowConstraints();
        btn1 = new JFXButton();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        jFXTimePicker = new JFXTimePicker();
        jFXTimePicker0 = new JFXTimePicker();
        jFXDatePicker = new JFXDatePicker();
        jFXTextField = new JFXTextField();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        gridPane.setPrefHeight(400.0);
        gridPane.setPrefWidth(600.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMaxWidth(195.0);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(82.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(368.0);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(149.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(277.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(266.0);

        columnConstraints2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints2.setMaxWidth(181.0);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPrefWidth(181.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(30.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setPrefHeight(30.0);
        rowConstraints3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints4.setMinHeight(10.0);
        rowConstraints4.setPrefHeight(30.0);
        rowConstraints4.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints5.setMinHeight(10.0);
        rowConstraints5.setPrefHeight(10.0);
        rowConstraints5.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        btn1.setText("Guardar");
        btn1.setStyle("-fx-padding: 0.7em 0.57em;-fx-font-size: 16px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-pref-width: 100;-fx-pref-height: 30;-fx-text-fill: WHITE;");
        
        GridPane.setColumnIndex(btn1, 3);
        GridPane.setRowIndex(btn1, 5);

        GridPane.setColumnIndex(label, 1);
        GridPane.setRowIndex(label, 4);
        label.setText("Hora Fin:");
        label.setFont(new Font(18.0));

        GridPane.setColumnIndex(label0, 1);
        GridPane.setRowIndex(label0, 3);
        label0.setText("Hora Inicio:");
        label0.setFont(new Font(18.0));

        GridPane.setColumnIndex(label1, 1);
        GridPane.setRowIndex(label1, 2);
        label1.setText("Fecha:");
        label1.setFont(new Font(18.0));

        GridPane.setColumnIndex(label2, 1);
        GridPane.setRowIndex(label2, 1);
        label2.setText("Paciente:");
        label2.setFont(new Font(18.0));

        GridPane.setColumnIndex(jFXTimePicker, 2);
        GridPane.setRowIndex(jFXTimePicker, 4);

        GridPane.setColumnIndex(jFXTimePicker0, 2);
        GridPane.setRowIndex(jFXTimePicker0, 3);

        GridPane.setColumnIndex(jFXDatePicker, 2);
        GridPane.setRowIndex(jFXDatePicker, 2);

        GridPane.setColumnIndex(jFXTextField, 2);
        GridPane.setRowIndex(jFXTextField, 1);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getColumnConstraints().add(columnConstraints2);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getRowConstraints().add(rowConstraints2);
        gridPane.getRowConstraints().add(rowConstraints3);
        gridPane.getRowConstraints().add(rowConstraints4);
        gridPane.getRowConstraints().add(rowConstraints5);
        gridPane.getChildren().add(btn1);
        gridPane.getChildren().add(label);
        gridPane.getChildren().add(label0);
        gridPane.getChildren().add(label1);
        gridPane.getChildren().add(label2);
        gridPane.getChildren().add(jFXTimePicker);
        gridPane.getChildren().add(jFXTimePicker0);
        gridPane.getChildren().add(jFXDatePicker);
        gridPane.getChildren().add(jFXTextField);
        getChildren().add(gridPane);

    }
    
    public JFXButton getBtn1() {
        return btn1;
    }
}
