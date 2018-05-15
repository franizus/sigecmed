package views;

import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.view.page.DayPage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarApp extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {

            DayPage calendarView = new DayPage();

                calendarView.setRequestedTime(LocalTime.now());
                calendarView.setShowLayoutButton(false);

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

                Scene scene = new Scene(calendarView);
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