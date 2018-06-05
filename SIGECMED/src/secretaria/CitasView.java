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
import doctor.Doctor;
import doctor.DoctorDataAccessor;
import doctor.Horario;
import doctor.HorarioDataAccessor;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.LogginViewController;
import main.Globals;
import paciente.Cita;
import paciente.CitaDataAccessor;
import paciente.Paciente;
import paciente.PacienteDataAccessor;

/**
 *
 * @author frani
 */
public class CitasView extends BorderPane{

    private Calendar horario;
    private Calendar citas;
    private DoctorDataAccessor doctorDataAccessor;
    private HorarioDataAccessor horarioDataAccessor;
    private CitaDataAccessor citaDataAccessor;
    private PacienteDataAccessor pacienteDataAccessor;
    
    public CitasView() {
        try {
            doctorDataAccessor = new DoctorDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            horarioDataAccessor = new HorarioDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            citaDataAccessor = new CitaDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
            pacienteDataAccessor = new PacienteDataAccessor(Globals.driverClassName, Globals.dbURL, Globals.dbUSER, Globals.dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        horario = new Calendar("Horario Doctores");
        horario.setStyle(Calendar.Style.STYLE1);
        loadHorarioDoctor();
        
        citas = new Calendar("Citas");
        citas.setStyle(Calendar.Style.STYLE2);
        loadCitas();
        
        DayPage calendarView = new DayPage();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.getDetailedDayView().setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        calendarView.getDetailedDayView().setVisibleHours(12);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(horario, citas);
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
        btn.setStyle("-fx-font-size: 13pt;-fx-font-weight: bold;-jfx-button-type: RAISED;-fx-background-color: #689F38;-fx-pref-width: 150;-fx-pref-height: 30;-fx-text-fill: WHITE;");
        btn.setTranslateX(830);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/secretaria/CitaView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Nueva Cita");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LogginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        });
        
        JFXButton btn1 = new JFXButton();
        btn1.textProperty().set("Reagendar Cita");
        btn1.setStyle("-fx-font-size: 13pt;-fx-font-weight: bold;-fx-pref-width: 150;-fx-pref-height: 30;");
        btn1.setTranslateX(660);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
            }
        });
        
        Pane grid = new Pane();
        grid.setStyle("-fx-background-color:WHITE;");
        grid.setPrefSize(1000, 60);
        grid.getChildren().add(btn);
        grid.getChildren().add(btn1);
        
        this.setCenter(calendarView);
        this.setBottom(grid);
    }
    
    private void loadHorarioDoctor() {
        List<Horario> horarios = horarioDataAccessor.getHorarioList();
        for (Horario horario : horarios) {
            Doctor doctor = doctorDataAccessor.getDoctor(horario.getIdDoctor());
            Entry entry = new Entry("Doctor: " + doctor.getNombre());
            Interval interval = new Interval(horario.getFechaInicio().toLocalDate(), horario.getHoraInicio().toLocalTime(), horario.getFechaInicio().toLocalDate(), horario.getHoraFin().toLocalTime());
            entry.setInterval(interval);
            int days = horario.getNumberOfDays();
            String aux = horario.getStringToCalendar();
            String rule = "RRULE:FREQ=MONTHLY;BYDAY=" + aux + ";COUNT=" + days + ";";
            entry.setRecurrenceRule(rule);
            this.horario.addEntry(entry);
        }
    }
    
    private void loadCitas() {
        List<Cita> citas = citaDataAccessor.getCitasList();
        for (Cita cita : citas) {
            Doctor doctor = doctorDataAccessor.getDoctor(cita.getIdDoctor());
            Paciente paciente = pacienteDataAccessor.getPaciente(cita.getIdPaciente());
            Entry entry = new Entry("Paciente: " + paciente.getNombre() + " - Doctor: " + doctor.getNombre());
            Interval interval = new Interval(cita.getFecha().toLocalDate(), cita.getHoraInicio().toLocalTime(), cita.getFecha().toLocalDate(), cita.getHoraFin().toLocalTime());
            entry.setInterval(interval);
            this.citas.addEntry(entry);
        }
    }
    
}
