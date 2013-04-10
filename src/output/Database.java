/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import config.Config;
import config.Non_GUI_config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class Database {
    
    private static Database instance;
    
    private Database(){}
    
    public static Database get_instance(){
        if(instance == null){
            instance = new Database();
        }
        
        return instance;
    }
    
    
    public ResultSet get_parsing_entity(){
        
        Connection connect = get_connection();
        
        if(connect != null){
            
            try {
                PreparedStatement prepared_statement= connect.prepareStatement(
                        "SELECT * FROM "+Non_GUI_config.get_database_name()+".parsing_entity"
                );
                
                ResultSet cat = prepared_statement.executeQuery();
                
                while(cat.next()){
                    System.out.println(cat.getString("starting_url"));
                    System.out.println(cat.getString("domain"));
                }
                
                
                System.out.println("here");
            } 
            catch (SQLException ex) {
                return null;
            }
            
        }
        
        
        return null;
    }
    
    public Connection get_connection(){
        try {
            // This will load the MySQL driver, each DB has its own driver
          Class.forName("com.mysql.jdbc.Driver");
          
          // Setup the connection with the DB
          Connection connect = DriverManager.getConnection("jdbc:mysql://"
                  + Non_GUI_config.get_database_host()+"/"
                  + Non_GUI_config.get_database_name()+"?"
                  + "user="+Non_GUI_config.get_database_username()
                  + "&password="+Non_GUI_config.get_database_password()
          );
      
          return connect;
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        catch(ClassNotFoundException ex){
            System.out.println(ex);
            return null;
        }
    }
            
            
}
