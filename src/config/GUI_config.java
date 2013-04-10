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
    
    private GUI_config(){}
    
    public static GUI_config get_instance(){
        if(instance == null){
            instance = new GUI_config();
        }
        
        return instance;
    }
    
    public void initialize(){
        super.parse_config_contents(super.read(config_location));
    }
    
  
    private void parse_item(String index, String value){}
    
    private void parse_gui_config_contents(LinkedList<String> contents){
        for(String item : contents){
            if(is_config_comment_or_empty(item) == false){
                
                String[] parts = super.split(item);
                String index = parts[0].trim();
                String value = parts[1].trim();
        
                parse_item(index, value);
            }
        }
    }
}
