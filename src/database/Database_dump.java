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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Database_dump{
    
    
    private String parsing_entity_table_name = "parsing_entity";
    private String[] parsing_entity_columns = {"starting_url", "domain"};
    
    private String url_table_name = "url";
    private String[] url_columns = {"parsing_entity_id", "url", "direct_parent", "http_code", "content_type", "io_error", "malformed_url_error"};
    
    private String html_asset_table_name = "html_asset";
    private String[] html_asset_columns = {"url_id"};
    
    private String misspelling_table_name = "misspelling";
    private String[] misspelling_columns = {"html_asset_id", "value"};
    
    private String at_mention_table_name = "at_mention";
    private String[] at_mention_columns = {"html_asset_id", "value"};
    
    public void dump_to_database(String starting_url, String domain, HashMap<String, Web_url> traveled_urls){
        
        Database database = Database.get_instance();
        Connection connection = database.get_connection();
        
        if(connection != null){
            try{
                int parsing_entity_id = write_to_parsing_entity(connection, starting_url, domain);
                
                if(parsing_entity_id != 0){
                    Iterator traveled_sites_iterator = traveled_urls.keySet().iterator();
                    save_urls(connection, traveled_urls, parsing_entity_id);
                }
            }
            catch(SQLException e){
                System.out.println(e);
                Logger.getLogger(Database_dump.class.getName()).log(Level.SEVERE, null, e);
            }
            finally{
                try{connection.close();}catch(SQLException e){
                    System.out.println(e);
                    Logger.getLogger(Database_dump.class.getName()).log(Level.SEVERE, null, e);
                };
            }
        }
    }
    
    private int write_to_parsing_entity(Connection connection, String starting_url, String domain) throws SQLException{
        String[] values = {starting_url, domain};
        
        PreparedStatement prep_state = create_prepared_insert(connection, parsing_entity_table_name, parsing_entity_columns);
        set_strings(prep_state, values);
        
        prep_state.executeUpdate();
        
        int inserted_row = get_last_updated_id(prep_state);
        
        close_prepared_statement(prep_state);
        
        return inserted_row;
    }
    
    private void save_urls(Connection connection, HashMap<String, Web_url> traveled_sites, int parsing_entity_id) throws SQLException{
        
        Iterator traveled_sites_iterator = traveled_sites.keySet().iterator();
        PreparedStatement prep_state = create_prepared_insert(connection, url_table_name, url_columns);
        String key;
        Web_url current_url;
        
        int counter = 0;
        while(traveled_sites_iterator.hasNext()){
            key = (String)traveled_sites_iterator.next();
            current_url = (Web_url)traveled_sites.get(key);
            
            if(current_url != null){
                String[] values = {
                    Integer.toString(parsing_entity_id),
                    current_url.get_url(),
                    current_url.get_direct_parent(),
                    Integer.toString(current_url.get_http_code()),
                    current_url.get_content_type(),
                    Integer.toString(current_url.get_io_error()),
                    Integer.toString(current_url.get_malformed_url())
                };
                
                set_strings(prep_state, values);
                prep_state.addBatch();
            }
            
            if(counter == 1000){
                prep_state.executeBatch();
            }
            
            counter += 1;
        }
        
        prep_state.executeBatch();
    }
    
    private void save_html_asset_info(Html_asset h_asset){
        
    }
    
    private void save_misspellings(int html_asset_id, LinkedList<String> misspellings){
        
    }
    
    private void save_at_mentions(int html_asset_id,  LinkedList<String> at_mentions){
        
    }
    
    private void save_emails(int html_asset_id,  LinkedList<String> emails){
        
    }
    
    private void save_hashtags(int html_asset_id,  LinkedList<String> hashtags){
        
    }
    
    private void close_prepared_statement(PreparedStatement prep_state) throws SQLException{
        if(prep_state != null){
            prep_state.close();
        }
    }
    
    private int get_last_updated_id(PreparedStatement prep_state) throws SQLException{
        
        if(prep_state != null){
            
            ResultSet res = null;
            
            try{
                res = prep_state.getGeneratedKeys();

                if(res.next()){
                    return res.getInt(1);
                }
            }
            finally{
                if(res != null){
                    res.close();
                }
            }
        }
        
        
        return 0;
    }
    
    private PreparedStatement create_prepared_insert(Connection connection, String table_name, String[] column_names) throws SQLException{
        
        if(connection == null){
            return null;
        }
        
        String sql = "INSERT INTO "+Config.get_database_name()+"."+table_name+" ";
        
        sql += create_columns_string_for_prepared_statement(column_names);
        sql += " VALUES ";
        sql += create_values_string_for_prepared_statement(column_names);

        PreparedStatement prep_state = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        return prep_state;
    }
    
    private String create_columns_string_for_prepared_statement(String[] column_names){
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
    
    private String create_values_string_for_prepared_statement(String[] columns){
        String sql = "";
        sql += "(";
        for(int i = 0; i < columns.length; i++){
            sql += "?";

            if(i != columns.length-1){
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
            throw new SQLException("In set strings, the prepared statement was null");
        }
    }
}
