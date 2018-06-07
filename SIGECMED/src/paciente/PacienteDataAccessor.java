/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paciente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author frani
 */
public class PacienteDataAccessor {
    
        private Connection connection;
    
    public PacienteDataAccessor(String dbURL) throws SQLException {
        connection = DriverManager.getConnection(dbURL);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public Paciente getPaciente(int id) {
        try {
            String query = "select * from paciente where ID_PACIENTE = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id_paciente = rs.getInt("ID_PACIENTE");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Paciente paciente = new Paciente(id_paciente, nombre, cedula, direccion, telefono, correo);
                return paciente;
            } else {
                return new Paciente();
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Paciente> getPacienteList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from paciente");) {
            List<Paciente> pacienteList = new ArrayList<>();
            while (rs.next()) {
                int id_paciente = rs.getInt("ID_PACIENTE");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Paciente paciente = new Paciente(id_paciente, nombre, cedula, direccion, telefono, correo);
                pacienteList.add(paciente);
            }
            return pacienteList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean insertNewPac(Paciente pac) {
        try {
            String query = "INSERT INTO paciente (NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC, TRATAMIENTO) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pac.getNombre());
            ps.setString(2, pac.getCedula());
            ps.setString(3, pac.getDireccion());
            ps.setString(4, pac.getTelefono());
            ps.setString(5, pac.getCorreo());
            ps.setBoolean(6, false);

            ps.execute();
            ps.close();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updatePac(Paciente pac) {
        try {
            String query = "UPDATE paciente SET NOMBRE_SEC = ?, CEDULA_SEC = ?, DIRECCION_SEC = ?, TELEFONO_SEC = ?, CORREO_SEC = ? WHERE ID_PACIENTE = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pac.getNombre());
            ps.setString(2, pac.getCedula());
            ps.setString(3, pac.getDireccion());
            ps.setString(4, pac.getTelefono());
            ps.setString(5, pac.getCorreo());
            ps.setInt(6, pac.getId_paciente());

            ps.executeUpdate();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deletePac(int id) {
        try {
            String query = "DELETE FROM paciente WHERE ID_PACIENTE = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ps.execute();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
