/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author frani
 */
public class HorarioDataAccessor {
    
    private Connection connection;
    
    public HorarioDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public List<Horario> getHorarioList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from horario");) {
            List<Horario> horarioList = new ArrayList<>();
            while (rs.next()) {
                int id_horario = rs.getInt("ID_HORIARIO");
                int id_doctor = rs.getInt("ID_DOCTOR");
                String intervalo = rs.getString("INTERVALO_DIAS");
                Time horaInicio = rs.getTime("HORA_INICIO");
                Time horaFin = rs.getTime("HORA_FIN");
                Date fechaInicio = rs.getDate("FECHA_INICIO");
                Date fechaFin = rs.getDate("FECHA_FIN");
                Horario horario = new Horario(id_horario, id_doctor, intervalo, horaInicio, horaFin, fechaInicio, fechaFin);
                horarioList.add(horario);
            }
            return horarioList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean insertNewHorario(Horario horario) {
        try {
            String query = "INSERT INTO horario (ID_DOCTOR, INTERVALO_DIAS, HORA_INICIO, HORA_FIN, FECHA_INICIO, FECHA_FIN) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, horario.getIdDoctor());
            ps.setString(2, horario.getIntervaloDias());
            ps.setTime(3, horario.getHoraInicio());
            ps.setTime(4, horario.getHoraFin());
            ps.setDate(5, horario.getFechaInicio());
            ps.setDate(6, horario.getFechaFin());

            ps.execute();
            ps.close();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
