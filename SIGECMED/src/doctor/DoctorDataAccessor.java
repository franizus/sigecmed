/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import java.sql.Connection;
import java.sql.DriverManager;
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
    
    public Doctor getDoctor(int id) throws SQLException {
        Statement stmnt = connection.createStatement();
        String query = "select * from doctor where ID_DOCTOR = '" + id + "'";
        ResultSet rs = stmnt.executeQuery(query);
        
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
    }
    
    public List<Doctor> getDoctorsList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from doctor");
        ){
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
            return doctorList ;
        } 
    }
    
    public List<String> getEspecialidadList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from especialidad");
        ){
            List<String> especialidadList = new ArrayList<>();
            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                especialidadList.add(nombre);
            }
            return especialidadList;
        } 
    }
    
}
