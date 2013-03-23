/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class Config {
    private static String config_location;
    
    private static String dictionary_location;
    
    private static String custom_dictionary_location;
    
    private static int parse_count_limit;
    
    private static String database_host;
    private static String database_port;
    private static String database_name;
    private static String database_username;
    private static String database_password;
    
    private static String output_file_name;
    private static String output_file_location;
    private static String output_file_template_location;
    
    private static String output_type;
    
    private static String dictionary_index          = "dictionary_location";
    private static String custom_dictionary_index   = "custom_dictionary_location";
    
    private static String database_host_index       = "database_host"; 
    private static String database_port_index       = "database_port";
    private static String database_name_index       = "database_name";
    private static String database_username_index   = "database_username";
    private static String database_password_index   = "database_password";
    
    private static String output_file_name_index = "output_file_name";
    private static String output_file_location_index = "output_file_location";
    private static String output_file_template_location_index = "output_file_template_location";
    
    private static String output_type_index = "output_type";
    
    private static String parse_count_limit_index = "parse_count_limit";
    
    
    public Config(){
        config_location = "C:\\Users\\Jason\\Documents\\NetBeansProjects\\web_parser_project\\src\\web_parser_project\\config.txt";
    }
    
    public void read() throws IOException{
        
        LinkedList<String> config_contents = File_reader.read_file(config_location);
        
        if(config_contents == null){
            throw new IOException("Config cannot be null");
        }
        else{
            parse_config_contents(config_contents);
        }
    }
    
    
    public boolean is_config_comment_or_empty(String item){
        item = item.trim();
        if(item.length() == 0 || item.charAt(0) == '#'){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void parse_config_contents(LinkedList<String> contents){
        for(String item : contents){
            if(is_config_comment_or_empty(item) == false){
                parse_item(item);
            }
        }
    }
    
    private void parse_item(String item){
        String[] parts = item.split("::");
       
        if(parts.length == 2){
            String index = parts[0].trim();
            String value = parts[1].trim();
        
            // dictionary location
            if(is_location_of_index(dictionary_index, index)){
                dictionary_location = value;
            }
            // custom dictionary location
            else if(is_location_of_index(custom_dictionary_index, index)){
                custom_dictionary_location = value;
            }
            // database_host
            else if(is_location_of_index(database_host_index, index)){
                database_host = value;
            }
            // database port index
            else if(is_location_of_index(database_port_index, index)){
                database_port = value;
            }
            // database name index
            else if(is_location_of_index(database_name_index, index)){
                database_name = value;
            }
            // database username index
            else if(is_location_of_index(database_username_index, index)){
                database_username = value;
            }
            // database password index
            else if(is_location_of_index(database_password_index, index)){
                database_password = value;
            }
            // output file name index
            else if(is_location_of_index(output_file_name_index, index)){
                output_file_name = value;
            }
            // output file location index
            else if(is_location_of_index(output_file_location_index, index)){
                output_file_location = value;
            }
            // output type
            else if(is_location_of_index(output_type_index, index)){
                output_type = value;
            }
            // output file location output_file_template_location
            else if(is_location_of_index(output_file_template_location_index, index)){
                output_file_template_location = value;
            }
            // parse count limit
            else if(is_location_of_index(parse_count_limit_index, index)){
                parse_count_limit = Integer.parseInt(value);
            }
        }
    }
    
    public boolean is_location_of_index(String index_to_match, String index_read_in){
        if(index_read_in.equals(index_to_match)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static String get_dictionary_location(){
        return dictionary_location;
    }
    
    public static String get_custom_dictionary_location(){
        return custom_dictionary_location;
    }
    
    public static String get_database_host(){
        return database_host;
    }
    
    public static String get_database_port(){
        return database_port;
    }
    
    public static String get_database_name(){
        return database_name;
    }
    
    public static String get_database_username(){
        return database_username;
    }
    
    public static String get_database_password(){
        return database_password;
    }
    
    public static String get_output_type(){
        return output_type;
    }
    
    public static String get_output_file_name(){
        return output_file_name;
    }
    
    public static String get_output_file_location(){
        return output_file_location;
    }
    
    public static String get_output_file_template_location(){
        return output_file_template_location;
    }
    
    public static int get_parse_count_limit(){
        return parse_count_limit;
    }
    
    /**
     * Returns the file location and the file name appended together
     * @return 
     */
    public static String get_output_file_string(){
        
        if(output_file_location.charAt(0) == '/'){
            return output_file_location+output_file_name;
        }
        else{
            return output_file_location+"/"+output_file_name;
        }
        
        
    }
    
    public static boolean is_output_to_database(){
        return (output_type.compareTo("database") == 0)?true:false;
    }
    
    public static boolean is_output_to_json_file(){
        return (output_type.compareTo("json") == 0)?true:false;
    }
    
    public static boolean is_output_to_html_file(){
        return (output_type.compareTo("html") == 0)?true:false;
    }
}
