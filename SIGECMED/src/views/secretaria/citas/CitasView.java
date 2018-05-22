/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.secretaria.citas;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import static java.lang.Thread.sleep;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author frani
 */
public class CitasView extends JFXTabPane{

    public CitasView() {
        Calendar cal1 = new Calendar("Appointments");
        
        Tab tab = new Tab("Registrar Cita");
        Tab tab2 = new Tab("Reagendar Cita");
        
        BorderPane pane = new BorderPane();
        tab.setContent(pane);
        
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
        btn.setStyle("-fx-padding: 0.7em 0.57em;-fx-font-size: 16px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-pref-width: 150;-fx-pref-height: 40;-fx-text-fill: WHITE;");
        btn.setTranslateY(10);
        btn.setTranslateX(575);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AppointmentView ap1 = new AppointmentView();
                ap1.getBtn1().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        AppointmentView ap1 = new AppointmentView();
                        ap1.getBtn1().setOnAction(this);

                        Scene secondScene = new Scene(ap1, 600, 400);

                        Stage newWindow = new Stage();
                        newWindow.setTitle("Second Stage");
                        newWindow.setScene(secondScene);

                        newWindow.centerOnScreen();
                        newWindow.show();
                    }
                });

                Scene secondScene = new Scene(ap1, 600, 400);

                Stage newWindow = new Stage();
                newWindow.setTitle("Añadir Cita");
                newWindow.setScene(secondScene);

                newWindow.centerOnScreen();
                newWindow.show();
            }
        });
        
        pane.setCenter(calendarView);
        Pane grid = new Pane();
        grid.setStyle("-fx-background-color:WHITE;");
        grid.setPrefSize(1300, 80);
        grid.getChildren().add(btn);
        pane.setBottom(grid);
        
        getTabs().add(tab);
        getTabs().add(tab2);
    }
    
}
