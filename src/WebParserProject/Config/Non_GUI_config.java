/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Config;

import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class Non_GUI_config extends Config{
    
    private static Non_GUI_config instance;
    
    private static String database_host;
    private static String database_port;
    private static String database_name;
    private static String database_username;
    private static String database_password;
    
    private static String output_file_name;
    private static String output_file_location;
    private static String output_file_template_location;
    
    private static String output_type;
    
    private static String database_host_index       = "database_host"; 
    private static String database_port_index       = "database_port";
    private static String database_name_index       = "database_name";
    private static String database_username_index   = "database_username";
    private static String database_password_index   = "database_password";
    
    private static String output_file_name_index = "output_file_name";
    private static String output_file_location_index = "output_file_location";
    private static String output_file_template_location_index = "output_file_template_location";
    
    private static String output_type_index = "output_type";
    
    private static String non_gui_config_location;
    
    public Non_GUI_config(){
        non_gui_config_location = "resources/configs/non_gui_config.txt";
    }
    
    public static Non_GUI_config get_instance(){
        if(instance == null){
            instance = new Non_GUI_config();
        }
        
        return instance;
    }
    
    public void initialize(){
       super.parse_config_contents(super.read(config_location));
       parse_non_gui_config_contents(read(non_gui_config_location));
    }
    
    protected void parse_non_gui_config_contents(LinkedList<String> contents){
        if(contents != null){
            for(String item : contents){
                if(is_config_comment_or_empty(item) == false){

                    String[] parts = split(item);
                    String index = parts[0].trim();
                    String value = parts[1].trim();

                    parse_non_gui_config_item(index, value);
                }
            }
        }
    }
    
    private void parse_non_gui_config_item(String index, String value){
         // database_host
        if(is_location_of_index(database_host_index, index)){
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
