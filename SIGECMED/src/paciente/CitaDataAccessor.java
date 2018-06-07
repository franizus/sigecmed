/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paciente;

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
public class CitaDataAccessor {
    
    private Connection connection;
    
    public CitaDataAccessor(String dbURL) throws SQLException {
        connection = DriverManager.getConnection(dbURL);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public List<Cita> getCitasList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from cita");) {
            List<Cita> citaList = new ArrayList<>();
            while (rs.next()) {
                int id_cita = rs.getInt("ID_CITA");
                int id_doctor = rs.getInt("ID_DOCTOR");
                int id_paciente = rs.getInt("ID_PACIENTE");
                Date fecha = rs.getDate("FECHA");
                Time horaInicio = rs.getTime("HORA_INICIO");
                Time horaFin = rs.getTime("HORA_FIN");
                Cita cita = new Cita(id_cita, id_doctor, id_paciente, fecha, horaInicio, horaFin);
                citaList.add(cita);
            }
            return citaList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean insertNewCita(Cita cita) {
        try {
            String query = "INSERT INTO cita (ID_DOCTOR, ID_PACIENTE, FECHA, HORA_INICIO, HORA_FIN) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cita.getIdDoctor());
            ps.setInt(2, cita.getIdPaciente());
            ps.setDate(3, cita.getFecha());
            ps.setTime(4, cita.getHoraInicio());
            ps.setTime(5, cita.getHoraFin());

            ps.execute();
            ps.close();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
