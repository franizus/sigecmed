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
import com.jfoenix.controls.JFXTabPane;
import doctor.Doctor;
import doctor.DoctorDataAccessor;
import doctor.Horario;
import doctor.HorarioDataAccessor;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import login.LogginViewController;
import main.Globals;

/**
 *
 * @author frani
 */
public class DoctorView extends JFXTabPane{
    
    private Calendar horario;
    private DoctorDataAccessor doctorDataAccessor;
    private HorarioDataAccessor horarioDataAccessor;

    public DoctorView() {
        try {
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            horarioDataAccessor = new HorarioDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.getStylesheets().add("/resources/tabPane.css");
        
        Tab tab = new Tab("Horarios");
        try {
            StackPane newLoadedPane =  FXMLLoader.load(getClass().getResource("/secretaria/HorarioDoctorView.fxml"));
            tab.setContent(newLoadedPane);
        } catch (IOException ex) {
            Logger.getLogger(DoctorView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Tab tab2 = new Tab("Agenda");
        Tab tab3 = new Tab("Historial Citas");
        
        BorderPane pane = new BorderPane();
        tab2.setContent(pane);
        
        horario = new Calendar("Horario Doctores");
        horario.setStyle(Calendar.Style.STYLE1);
        loadHorarioDoctor();
        
        DayPage calendarView = new DayPage();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.getDetailedDayView().setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        calendarView.getDetailedDayView().setVisibleHours(12);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(horario);
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
    
    public void loadHorarioDoctor() {
        List<Horario> horarios = horarioDataAccessor.getHorarioList();
        for (Horario horario : horarios) {
            Doctor doctor = doctorDataAccessor.getDoctor(horario.getIdDoctor());
            Entry entry = new Entry(doctor.getNombre());
            Interval interval = new Interval(horario.getFechaInicio().toLocalDate(), horario.getHoraInicio().toLocalTime(), horario.getFechaInicio().toLocalDate(), horario.getHoraFin().toLocalTime());
            entry.setInterval(interval);
            int days = horario.getNumberOfDays();
            String aux = horario.getStringToCalendar();
            String rule = "RRULE:FREQ=MONTHLY;BYDAY=" + aux + ";COUNT=" + days + ";";
            entry.setRecurrenceRule(rule);
            this.horario.addEntry(entry);
        }
    }
    
}