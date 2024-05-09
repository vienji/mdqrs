/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbcontroller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import mdqrs.classes.Cryptographer;

/**
 *
 * @author Vienji
 */
public class Driver {
    private static String USER = "";
    private static String PASSWORD = "";
    private static String SERVER = "";
    private static String PORT = "";
    private static String DATABASE = "";
    
    public static void main(String... args){
        Driver.getConnection();
    }
    
    public static Connection getConnection(){       
        try(InputStream input = new FileInputStream("src\\mdqrs\\path\\to\\config.properties")){
            Properties network = new Properties();
            
            network.load(input);
            
            Cryptographer cryptographer = new Cryptographer();
            
            String decryptedPassword = "";
            try {
                decryptedPassword = cryptographer.decrypt(network.getProperty("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            USER = network.getProperty("username");
            PASSWORD = decryptedPassword;
            SERVER = network.getProperty("server");
            PORT = network.getProperty("port");
            DATABASE = network.getProperty("database");
        }
        catch (IOException io) {}
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + 
                    SERVER.concat(":" + PORT + "/" + DATABASE), USER, PASSWORD);

            return connect;
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
