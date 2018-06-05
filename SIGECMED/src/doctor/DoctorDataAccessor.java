/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

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
public class DoctorDataAccessor {
    
    private Connection connection;
    
    public DoctorDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public Doctor getDoctor(int id) {
        try {
            String query = "select * from doctor where ID_DOCTOR = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id_doctor = rs.getInt("ID_DOCTOR");
                int id_especialidad = rs.getInt("ID_ESPECIALIDAD");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Doctor doctor = new Doctor(id_doctor, id_especialidad, nombre, cedula, direccion, telefono, correo);
                return doctor;
            } else {
                return new Doctor();
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public Especialidad getEspecialidad(int id) {
        try {
            String query = "select * from especialidad where ID_ESPECIALIDAD = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id_especialidad = rs.getInt("ID_ESPECIALIDAD");
                String nombre = rs.getString("NOMBRE");
                Especialidad esp = new Especialidad(id_especialidad, nombre);
                return esp;
            } else {
                return new Especialidad();
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Doctor> getDoctorsList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from doctor");) {
            List<Doctor> doctorList = new ArrayList<>();
            while (rs.next()) {
                int id_doctor = rs.getInt("ID_DOCTOR");
                int id_especialidad = rs.getInt("ID_ESPECIALIDAD");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Doctor doctor = new Doctor(id_doctor, id_especialidad, nombre, cedula, direccion, telefono, correo);
                doctorList.add(doctor);
            }
            return doctorList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<String> getDoctorsStringList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from doctor");) {
            List<String> doctorList = new ArrayList<>();
            while (rs.next()) {
                int id_especialidad = rs.getInt("ID_ESPECIALIDAD");
                String especialidad = getEspecialidad(id_especialidad).getNombre();
                String nombre = rs.getString("NOMBRE_SEC");
                String salida = nombre + " - " + especialidad;
                doctorList.add(salida);
            }
            return doctorList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<String> getEspecialidadStringList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from especialidad");) {
            List<String> especialidadList = new ArrayList<>();
            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                especialidadList.add(nombre);
            }
            return especialidadList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Especialidad> getEspecialidadList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from especialidad");) {
            List<Especialidad> especialidadList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ID_ESPECIALIDAD");
                String nombre = rs.getString("NOMBRE");
                Especialidad esp = new Especialidad(id, nombre);
                especialidadList.add(esp);
            }
            return especialidadList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean insertNewDoctor(Doctor doctor) {
        try {
            String query = "INSERT INTO doctor (ID_ESPECIALIDAD, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctor.getId_especialidad());
            ps.setString(2, doctor.getNombre());
            ps.setString(3, doctor.getCedula());
            ps.setString(4, doctor.getDireccion());
            ps.setString(5, doctor.getTelefono());
            ps.setString(6, doctor.getCorreo());

            ps.execute();
            ps.close();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateDoctor(Doctor doctor) {
        try {
            String query = "UPDATE doctor SET ID_ESPECIALIDAD = ?, NOMBRE_SEC = ?, CEDULA_SEC = ?, DIRECCION_SEC = ?, TELEFONO_SEC = ?, CORREO_SEC = ? WHERE ID_DOCTOR = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctor.getId_especialidad());
            ps.setString(2, doctor.getNombre());
            ps.setString(3, doctor.getCedula());
            ps.setString(4, doctor.getDireccion());
            ps.setString(5, doctor.getTelefono());
            ps.setString(6, doctor.getCorreo());
            ps.setInt(7, doctor.getId_doctor());

            ps.executeUpdate();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deleteDoctor(int id) {
        try {
            String query = "DELETE FROM doctor WHERE ID_DOCTOR = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ps.execute();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
        public boolean insertNewEsp(Especialidad especialidad) {
        try {
            String query = "INSERT INTO especialidad (NOMBRE) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, especialidad.getNombre());

            ps.execute();
            ps.close();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateEsp(Especialidad especialidad) {
        try {
            String query = "UPDATE especialidad SET NOMBRE = ? WHERE ID_ESPECIALIDAD = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, especialidad.getNombre());
            ps.setInt(2, especialidad.getId_especialidad());

            ps.executeUpdate();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deleteEspecialidad(int id) {
        try {
            String query = "DELETE FROM especialidad WHERE ID_ESPECIALIDAD = ?";
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
