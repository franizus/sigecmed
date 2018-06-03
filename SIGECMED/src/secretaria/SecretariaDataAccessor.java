/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

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
public class SecretariaDataAccessor {
    
    private Connection connection;
    
    public SecretariaDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public Secretaria getSecretaria(int id) {
        try {
            Statement stmnt = connection.createStatement();
            String query = "select * from secretaria where ID_SECRETARIA = '" + id + "'";
            ResultSet rs = stmnt.executeQuery(query);
            
            if (rs.next()) {
                int id_secretaria = rs.getInt("ID_SECRETARIA");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Secretaria secretaria = new Secretaria(id_secretaria, nombre, cedula, direccion, telefono, correo);
                return secretaria;
            } else {
                return new Secretaria();
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Secretaria> getSecretariaList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from secretaria");) {
            List<Secretaria> secretariaList = new ArrayList<>();
            while (rs.next()) {
                int id_secretaria = rs.getInt("ID_SECRETARIA");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Secretaria secretaria = new Secretaria(id_secretaria, nombre, cedula, direccion, telefono, correo);
                secretariaList.add(secretaria);
            }
            return secretariaList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
