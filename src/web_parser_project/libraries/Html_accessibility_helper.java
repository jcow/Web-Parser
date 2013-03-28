/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class Html_accessibility_helper {
    
    private static Html_accessibility_helper instance;
    private static HashMap<String,String> poor_link_names;
    
    public Html_accessibility_helper(){
        poor_link_names = new HashMap();
        poor_link_names.put("click here", "click here");
        poor_link_names.put("here", "here");
        poor_link_names.put("more", "more");
        poor_link_names.put("more information", "more information");
        poor_link_names.put("more information here", "more information here");
        poor_link_names.put("continue", "continue");
    }
    
    public static Html_accessibility_helper get_instance(){
        if(instance == null){
            instance = new Html_accessibility_helper();
        }
        
        return instance;
    }
    
    public static boolean is_poor_link_name(String link_text){
        if(Html_accessibility_helper.poor_link_names.containsKey(link_text.trim().toLowerCase())){
            return true;
        }
        else{
            return false;
        }
    }
}
