/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Output;

import WebParserProject.Config.Config;
import WebParserProject.Config.Non_GUI_config;
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
import WebParserProject.WebAssets.Html_asset;
import WebParserProject.WebAssets.Web_asset;
import WebParserProject.WebAssets.Web_url;

/**
 *
 * @author Jason
 */
public class Database_dump{
    
    // the amount of queries allowed to be added to a execution batch before it's fired off
    int batch_limit = 2;
    
    private String parsing_entity_table_name = "parsing_entity";
    private String[] parsing_entity_columns = {"starting_url", "domain"};
    
    private String url_table_name = "url";
    private String[] url_columns = {"parsing_entity_id", "url", "direct_parent", "http_code", "content_type", "io_error", "malformed_url_error"};
    
    private String misspelling_table_name = "misspelling";
    private String[] misspelling_columns = {"url_id", "value"};
    
    private String at_mention_table_name = "at_mention";
    private String[] at_mention_columns = {"url_id", "value"};
    
    private String email_table_name = "email";
    private String[] email_columns = {"url_id", "value"};
    
    private String hash_tag_table_name = "hash_tag";
    private String[] hash_tag_columns = {"url_id", "value"};
    
    public void dump_to_database(String starting_url, String domain, HashMap<String, Web_url> traveled_urls){
        
        Database database = Database.get_instance();
        Connection connection = database.get_connection();
        
        if(connection != null){
            try{
                int parsing_entity_id = write_to_parsing_entity(connection, starting_url, domain);
                
                if(parsing_entity_id != 0){
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
        
        int counter = 1;
        
        // used to keep a small subset of urls that need to be saved
        Web_url[] batch_urls = new Web_url[batch_limit];
        
        while(traveled_sites_iterator.hasNext()){
            key = (String)traveled_sites_iterator.next();
            current_url = (Web_url)traveled_sites.get(key);
            
            // add the url to the batch to be executed
            batch_urls[counter-1] = current_url;
            
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
            
            if(counter % batch_limit == 0){
                prep_state.executeBatch();
                
                ResultSet result_set = prep_state.getGeneratedKeys();
                save_web_url_sub_data(connection, batch_urls, result_set);
                
                close_prepared_statement(prep_state);
                close_result_set(result_set);
                
                prep_state = create_prepared_insert(connection, url_table_name, url_columns);
                
                batch_urls = new Web_url[batch_limit];
                counter = 1;
            }
            else{
                counter++;
            }
        }
        
        prep_state.executeBatch();
        ResultSet result_set = prep_state.getGeneratedKeys();
        save_web_url_sub_data(connection, batch_urls, result_set);
        close_prepared_statement(prep_state);
        close_result_set(result_set);
    }
    
    private void save_web_url_sub_data(Connection connection, Web_url[] web_urls, ResultSet res) throws SQLException{
        int counter = 0;
        while(res.next()){
            if(web_urls[counter] != null && web_urls[counter].get_web_asset() != null){
                int web_url_id = res.getInt(1);
                
                if(Web_asset.is_html_asset(web_urls[counter].get_web_asset())){
                    Html_asset html_asset = (Html_asset)web_urls[counter].get_web_asset();
                    
                    save_misspellings(connection, web_url_id, html_asset.get_misspellings());
                    save_at_mentions(connection, web_url_id, html_asset.get_at_mentions());
                    save_emails(connection, web_url_id, html_asset.get_emails());
                    save_hash_tags(connection, web_url_id, html_asset.get_hash_tags());
                }
                // other...
                else{
                    
                }
                
            }
            counter++;
        }
    }
    
    /**
     * Takes similar value items associated with the url and stores them in their respective table
     */
    private void save_items(Connection connection, String table_name, String[] columns,
                                        int url_id, LinkedList<String> values) throws SQLException{
        
        PreparedStatement prep_state = create_prepared_insert(connection, table_name, columns);
        Iterator values_it = values.iterator();
        
        int counter = 1;
        while(values_it.hasNext()){
            String word = (String) values_it.next();
            String[] storing_values = {Integer.toString(url_id), word};
            set_strings(prep_state, storing_values);
            
            prep_state.addBatch();
            
            if(counter % batch_limit == 0){
                prep_state.executeBatch();
            }
        }
        
        prep_state.executeBatch();
        
        close_prepared_statement(prep_state);
    }
    
    private void save_misspellings(Connection connection, int url_id, LinkedList<String> misspellings) throws SQLException{
        save_items(connection, misspelling_table_name, misspelling_columns, url_id, misspellings);
    }
    
    private void save_at_mentions(Connection connection, int url_id,  LinkedList<String> at_mentions) throws SQLException{
        save_items(connection, at_mention_table_name, at_mention_columns, url_id, at_mentions);
    }
    
    private void save_emails(Connection connection, int url_id,  LinkedList<String> emails) throws SQLException{
        save_items(connection, email_table_name, email_columns, url_id, emails);
    }
    
    private void save_hash_tags(Connection connection, int url_id,  LinkedList<String> hashtags) throws SQLException{
        save_items(connection, hash_tag_table_name, hash_tag_columns, url_id, hashtags);
    }
    
    private void close_result_set(ResultSet res) throws SQLException{
        if(res != null && res.isClosed() == false){
            res.close();
        }
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
                close_result_set(res);
            }
        }
        
        
        return 0;
    }
    
    private PreparedStatement create_prepared_insert(Connection connection, String table_name, String[] column_names) throws SQLException{
        
        if(connection == null){
            return null;
        }
        
        String sql = "INSERT INTO "+Non_GUI_config.get_database_name()+"."+table_name+" ";
        
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
