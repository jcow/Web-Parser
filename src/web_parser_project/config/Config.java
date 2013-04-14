/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.config;

import web_parser_project.data.File_reader;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public abstract class Config {
    
    protected static String application_type;
    
    protected static String config_location;
    
    protected static String dictionary_location;
    
    protected static String custom_dictionary_location;
    
    protected static int parse_count_limit;
    
    // timeout in milliseconds on how long to try to connect to a url
    protected static int timeout_limit;
    
    protected static String dictionary_index          = "dictionary_location";
    protected static String custom_dictionary_index   = "custom_dictionary_location";
    
    protected static String parse_count_limit_index = "parse_count_limit";
    
    protected static String timeout_limit_index = "timeout_limit";
    
    
    protected Config(){
        config_location = "C:\\Users\\Jason\\Documents\\NetBeansProjects\\web_parser_project\\src\\web_parser_project\\config.txt";
    }
    
    
    public LinkedList<String> read(String what){
        
        LinkedList<String> config_contents = File_reader.read_file(what);
        
        return config_contents;
    }
    
    public void initialize(){}
    
    
    public boolean is_config_comment_or_empty(String item){
        item = item.trim();
        if(item.length() == 0 || item.charAt(0) == '#'){
            return true;
        }
        else{
            return false;
        }
    }
    
    protected String[] split(String item){
        return item.split("::");
    }
    
    protected void parse_config_contents(LinkedList<String> contents){
        for(String item : contents){
            if(is_config_comment_or_empty(item) == false){
                
                String[] parts = split(item);
                String index = parts[0].trim();
                String value = parts[1].trim();
        
                parse_config_item(index, value);
            }
        }
    }
    
    protected void parse_config_item(String index, String value){
        
        // dictionary location
        if(is_location_of_index(dictionary_index, index)){
            dictionary_location = value;
        }
        // custom dictionary location
        else if(is_location_of_index(custom_dictionary_index, index)){
            custom_dictionary_location = value;
        }
        // parse count limit
        else if(is_location_of_index(parse_count_limit_index, index)){
            parse_count_limit = Integer.parseInt(value);
        }
        // timeout limit
        else if(is_location_of_index(timeout_limit_index, index)){
            timeout_limit = Integer.parseInt(value);
        }
    }
    
    protected boolean is_location_of_index(String index_to_match, String index_read_in){
        if(index_read_in.equals(index_to_match)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static void set_application_type(String app_type){
        application_type = app_type;
    }
    
    public static String get_application_type(){
        return application_type;
    }
    
    public static boolean is_app_type_gui(){
        return (application_type.equals("gui"))?true:false;
    }
    
    public static boolean is_app_type_gui(String app_type){
        return (app_type.equals("gui"))?true:false;
    }
    
    public static String get_dictionary_location(){
        return dictionary_location;
    }
    
    public static String get_custom_dictionary_location(){
        return custom_dictionary_location;
    }
    
    public static int get_parse_count_limit(){
        return parse_count_limit;
    }
    
    public static int get_timeout_limit(){
        return timeout_limit;
    }

   
    
    
}
