/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Libraries;

import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class HTMLAccessibilityHelper {
    
    private static HTMLAccessibilityHelper instance;
    private static HashMap<String,String> poor_link_names;
    
	// TODO - Add urls to the poor link naming scheme
	
    public HTMLAccessibilityHelper(){
        poor_link_names = new HashMap();
        poor_link_names.put("click here", "click here");
        poor_link_names.put("here", "here");
        poor_link_names.put("more", "more");
        poor_link_names.put("more information", "more information");
        poor_link_names.put("more information here", "more information here");
        poor_link_names.put("continue", "continue");
        poor_link_names.put("this", "this");
    }
    
    public static HTMLAccessibilityHelper get_instance(){
        if(instance == null){
            instance = new HTMLAccessibilityHelper();
        }
        
        return instance;
    }
    
    public static boolean is_poor_link_name(String link_text){
        if(HTMLAccessibilityHelper.poor_link_names.containsKey(link_text.trim().toLowerCase())){
            return true;
        }
        else{
            return false;
        }
    }
}
