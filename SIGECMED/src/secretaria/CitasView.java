/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.JFXButton;
import static java.lang.Thread.sleep;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author frani
 */
public class CitasView extends BorderPane{

    public CitasView() {
        Calendar cal1 = new Calendar("Appointments");
        
        DayPage calendarView = new DayPage();
        
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.getDetailedDayView().setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        calendarView.getDetailedDayView().setVisibleHours(12);
                
        Entry entry = new Entry("Dentista 1");
        Interval interval = new Interval(LocalDate.now(), LocalTime.of(9, 15), LocalDate.now(), LocalTime.of(11, 15));
        entry.setInterval(interval);
        entry.setRecurrenceRule("RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;COUNT=20;");
                
        cal1.setStyle(Calendar.Style.STYLE2);
                
        Calendar birthdays = new Calendar("Doctors");
        birthdays.addEntry(entry);
        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(birthdays, cal1);
        calendarView.getCalendarSources().addAll(myCalendarSource);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        JFXButton btn = new JFXButton();
        btn.textProperty().set("Nueva Cita");
        btn.setStyle("-fx-padding: 0.7em 0.57em;-fx-font-size: 16px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-pref-width: 150;-fx-pref-height: 30;-fx-text-fill: WHITE;");
        btn.setTranslateX(250);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AppointmentView ap1 = new AppointmentView();
                LocalDate date = calendarView.getYearMonthView().getDate();
                ap1.getJFXDatePicker().setValue(date);
                //ap1.getjFXTextField().setEditable(false);
                ap1.getBtn1().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Entry entry = new Entry(ap1.getjFXTextField().getText());
                        Interval interval = new Interval(ap1.getJFXDatePicker().getValue(), ap1.getjFXTimePicker0().getValue(), ap1.getJFXDatePicker().getValue(), ap1.getjFXTimePicker().getValue());
                        entry.setInterval(interval);
                        cal1.addEntry(entry);
                        Stage stage1 = (Stage) ap1.getBtn1().getScene().getWindow();
                        stage1.close();
                    }
                });

                Scene secondScene = new Scene(ap1, 600, 400);

                Stage newWindow = new Stage();
                newWindow.setTitle("Nueva Cita");
                newWindow.setScene(secondScene);

                newWindow.centerOnScreen();
                newWindow.show();
            }
        });
        
        JFXButton btn1 = new JFXButton();
        btn1.textProperty().set("Reagendar Cita");
        btn1.setStyle("-fx-padding: 0.7em 0.57em;-fx-font-size: 16px;-jfx-button-type: RAISED;-fx-background-color: #669966;-fx-pref-width: 150;-fx-pref-height: 30;-fx-text-fill: WHITE;");
        btn1.setTranslateX(500);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
            }
        });
        
        Pane grid = new Pane();
        grid.setStyle("-fx-background-color:WHITE;");
        grid.setPrefSize(900, 60);
        grid.getChildren().add(btn);
        grid.getChildren().add(btn1);
        
        this.setCenter(calendarView);
        this.setBottom(grid);
    }
    
}
