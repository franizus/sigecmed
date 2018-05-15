package views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.view.page.DayPage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;

public class CalendarApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            
            BorderPane pane = new BorderPane();

            DayPage calendarView = new DayPage();

                calendarView.setRequestedTime(LocalTime.now());
                calendarView.setShowLayoutButton(false);
                
                Entry entry = new Entry("Dentista 1");
                Interval interval = new Interval(LocalDate.now(), LocalTime.of(9, 15), LocalDate.now(), LocalTime.of(11, 15));
                entry.setInterval(interval);
                entry.setRecurrenceRule("RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;COUNT=20;");
                
                Calendar birthdays = new Calendar("Doctors");
                birthdays.addEntry(entry);
                CalendarSource myCalendarSource = new CalendarSource("My Calendars");
                myCalendarSource.getCalendars().addAll(birthdays);
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
                                                // update every 10 seconds
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
                btn.textProperty().set("Bienvenido");
                btn.buttonTypeProperty().set(JFXButton.ButtonType.RAISED);
                pane.setCenter(calendarView);
                pane.setBottom(btn);

                Scene scene = new Scene(pane);
                primaryStage.setTitle("Calendar");
                primaryStage.setScene(scene);
                primaryStage.setWidth(1300);
                primaryStage.setHeight(1000);
                primaryStage.centerOnScreen();
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}