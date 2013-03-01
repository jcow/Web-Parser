/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import data.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class Database_dump{
    
    
    private String parsing_entity_table_name = "parsing_entity";
    private String[] parsing_entity_columns = {"starting_url", "domain"};
    
    public void dump_to_database(String starting_url, String domain, Iterator traveled_sites_iterator){
        
        Database database = Database.get_instance();
        Connection connection = database.get_connection();
        
        if(connection != null){
            
            
            int parsing_entity_id = write_to_parsing_entity(connection, starting_url, domain);
            
            System.out.println(parsing_entity_id);
//            while(traveled_sites_iterator.hasNext()){
//            
//            }
        }
    }
    
    private int write_to_parsing_entity(Connection connection, String starting_url, String domain){
        
        String[] values = {starting_url, domain};
        PreparedStatement prep_state = create_insert(connection, parsing_entity_table_name, parsing_entity_columns, values);
        
        try {
            
            int id = prep_state.executeUpdate();
            
            // this gets you the id of the inserted item
            ResultSet res = prep_state.getGeneratedKeys();
            while(res.next()){
                System.out.println("Generated key: " + res.getInt(1));
            }
            
            return id;
        } 
        catch (SQLException ex) {
            Logger.getLogger(Database_dump.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    private PreparedStatement create_insert(Connection connection, String table_name, String[] column_names, String[] values){
        
        if(connection == null){
            return null;
        }
        
        
        String sql = "INSERT INTO "+Config.get_database_name()+"."+table_name+" ";
        
        if(column_names.length == values.length){
            sql += create_columns_string(column_names);
            sql += " VALUES ";
            sql += create_values_string_for_prepared_statement(values);
                    
            try {
                PreparedStatement prep_state = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                set_strings(prep_state, values);

                return prep_state;
            } 
            catch (SQLException ex) {
                System.out.println(ex);
                Logger.getLogger(Database_dump.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            return null;
        }
        
        return null;
    }
    
    private String create_columns_string(String[] column_names){
        String sql = "";
        sql += "(";
        for(int i = 0; i < column_names.length; i++){
            sql += column_names[i];

            if(i != column_names.length-1){
                sql += ", ";
            }
        }
        sql += ")";
        return sql;
    }
    
    private String create_values_string_for_prepared_statement(String[] values){
        String sql = "";
        sql += "(";
        for(int i = 0; i < values.length; i++){
            sql += "?";

            if(i != values.length-1){
                sql += ", ";
            }
        }
        sql += ")";
        return sql;
    }
    
    private void set_strings(PreparedStatement prep_state, String[] values) throws SQLException{
        if(prep_state != null){
            for(int i = 1; i <= values.length; i++){
                prep_state.setString(i, values[i-1]);
            }
        }
        else{
            throw new SQLException("In set strings, the prepared statement was empty");
        }
    }
}
