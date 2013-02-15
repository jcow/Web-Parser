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
    
    
    private static String dictionary_index = "dictionary_location";
    
    public Config(){
        config_location = "C:\\Users\\Jason\\Documents\\NetBeansProjects\\web_parser_project\\src\\web_parser_project\\config.txt";
    }
    
    public static String get_dictionary_location(){
        return dictionary_location;
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
        
            // check the values
            if(is_location_of_dictionary_index(index)){
                dictionary_location = value;
            }
        }
    }
    
    public boolean is_location_of_dictionary_index(String index){
        if(index.equals(dictionary_index)){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    
}
