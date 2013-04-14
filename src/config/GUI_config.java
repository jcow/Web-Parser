/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class GUI_config extends Config{
    
    private static GUI_config instance;
    
    private static String front_end_template_location;
    private static String front_end_template_location_index = "front_end_template_location";
    
    private static String front_end_dump_location;
    private static String front_end_dump_location_index = "front_end_dump_location";
    
    private static String gui_config_location = "C:\\Users\\Jason\\Documents\\NetBeansProjects\\web_parser_project\\src\\web_parser_project\\gui_config.txt";
    
    private GUI_config(){}
    
    public static GUI_config get_instance(){
        if(instance == null){
            instance = new GUI_config();
        }
        
        return instance;
    }
    
    public void initialize(){
        super.parse_config_contents(super.read(config_location));
        parse_gui_config_contents(super.read(gui_config_location));
    }
    
  
    private void parse_gui_config_contents(LinkedList<String> contents){
        for(String item : contents){
            if(is_config_comment_or_empty(item) == false){
                
                String[] parts = super.split(item);
                String index = parts[0].trim();
                String value = parts[1].trim();
        
                parse_gui_item(index, value);
            }
        }
    }
    
    private void parse_gui_item(String index, String value){
        if(is_location_of_index(front_end_template_location_index, index)){
            front_end_template_location = value;
        }
        else if(is_location_of_index(front_end_dump_location_index, index)){
            front_end_dump_location = value;
        }
    }
    
    public static String get_front_end_template_location(){
        return front_end_template_location;
    }
    
    public static String get_front_end_dump_location(){
        return front_end_dump_location;
    }
    
}
