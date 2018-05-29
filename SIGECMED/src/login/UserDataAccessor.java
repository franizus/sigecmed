/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.DriverManager;
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
    
    public UserDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public User getUser(String usuarioAux) throws SQLException {
        Statement stmnt = connection.createStatement();
        String query = "select * from usuario where USUARIO = '" + usuarioAux + "'";
        ResultSet rs = stmnt.executeQuery(query);
        
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
    }
    
    public List<User> getUsersList() throws SQLException {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from usuario");
        ){
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
            return userList ;
        } 
    }
    
}
