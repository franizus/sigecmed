/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frani
 */
public class Globals {
    
    public final static String driverClassName;
    public final static String dbURL;
    public final static String dbUSER;
    public final static String dbPassword;
    
    static {
        FileInputStream in = null;
        Properties props = null;
        try {
            props = new Properties();
            in = new FileInputStream("database.properties");
            props.load(in);
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        driverClassName = props.getProperty("jdbc.driver");
        dbURL = props.getProperty("jdbc.url");
        dbUSER = props.getProperty("jdbc.username");
        dbPassword = props.getProperty("jdbc.password");
    }
    
}
