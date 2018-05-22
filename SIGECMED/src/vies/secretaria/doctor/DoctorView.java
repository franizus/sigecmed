/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vies.secretaria.doctor;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.JFXTabPane;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author frani
 */
public class DoctorView extends JFXTabPane{

    public DoctorView() {
        Calendar cal1 = new Calendar("Appointments");
        
        Tab tab = new Tab("Horarios");
        Tab tab2 = new Tab("Agenda");
        Tab tab3 = new Tab("Historial Citas");
        
        BorderPane pane = new BorderPane();
        tab2.setContent(pane);
        
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
        
        pane.setCenter(calendarView);
        
        getTabs().add(tab);
        getTabs().add(tab2);
        getTabs().add(tab3);
    }
    
}