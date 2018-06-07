/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author frani
 */
public class UserDataAccessor {
    
    private Connection connection;
    
    public UserDataAccessor(String dbURL) throws SQLException {
        connection = DriverManager.getConnection(dbURL);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public User getUser(String usuarioAux) {
        try {
            String query = "select * from usuario where USUARIO = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, usuarioAux);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id_user = rs.getInt("ID_USUARIO");
                int id_rol = rs.getInt("ID_ROL");
                String usuario = rs.getString("USUARIO");
                String contrasena = rs.getString("CONTRASENA");
                String salt = rs.getString("SALT");
                int id_asociado = rs.getInt("ID_ASOCIADO");
                User user = new User(id_user, id_rol, usuario, contrasena, salt, id_asociado);
                return user;
            } else {
                return new User();
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public String getAssociateUserName(int idA, int idRol) {
        try {
            String query = "";
            switch(idRol) {
                case 2:
                    query = "select * from doctor where ID_DOCTOR = ?";
                    break;
                case 3:
                    query = "select * from secretaria where ID_SECRETARIA = ?";
                    break;
            }
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idA);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String nombre = rs.getString("NOMBRE_SEC");
                return nombre;
            } else {
                return "";
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<User> getUsersList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from usuario");) {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                int id_user = rs.getInt("ID_USUARIO");
                int id_rol = rs.getInt("ID_ROL");
                String usuario = rs.getString("USUARIO");
                String contrasena = rs.getString("CONTRASENA");
                String salt = rs.getString("SALT");
                int id_asociado = rs.getInt("ID_ASOCIADO");
                User person = new User(id_user, id_rol, usuario, contrasena, salt, id_asociado);
                userList.add(person);
            }
            return userList;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean insertNewUser(User user) {
        try {
            String query = "INSERT INTO usuario (ID_ROL, USUARIO, CONTRASENA, ID_ASOCIADO, SALT) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId_rol());
            ps.setString(2, user.getUsuario());
            ps.setString(3, user.getContrasena());
            ps.setInt(4, user.getId_asociado());
            ps.setString(5, user.getSalt());

            ps.execute();
            ps.close();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateUser(User user) {
        try {
            String query = "UPDATE usuario SET ID_ROL = ?, USUARIO = ?, CONTRASENA = ?, ID_ASOCIADO = ?, SALT = ? WHERE ID_USUARIO = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId_rol());
            ps.setString(2, user.getUsuario());
            ps.setString(3, user.getContrasena());
            ps.setInt(4, user.getId_asociado());
            ps.setString(5, user.getSalt());
            ps.setInt(6, user.getId_user());

            ps.executeUpdate();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deleteUser(int id) {
        try {
            String query = "DELETE FROM usuario WHERE ID_USUARIO = ?";
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
