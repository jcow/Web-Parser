/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import org.jsoup.nodes.Element;

/**
 *
 * @author Jason
 */
public class Html_meta_helper {
    
    public Html_meta_helper(){
        
    }
    
    public static boolean is_element_meta(Element node){
        if(node.tagName().equals("meta")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_element_meta_description(Element node){
        if(Html_meta_helper.is_element_meta(node)){
            String name = node.attr("name");
            
            if(name.equals("description")){
                return true;
            }
        }
        
        return false;
    }
    
    public static String get_meta_content(Element node){
        return node.attr("content");
    }
    
}
