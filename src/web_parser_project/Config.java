/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

/**
 *
 * @author Jason
 */
public class Config {
 
    private String location;
    
    public Config(){
        setupDefaults();
    }
    
    public String get_config_location(){
        return location;
    }
    
    private void setupDefaults(){
        location = "config.txt";
    }
  
    private void read(){
        
    }
}
