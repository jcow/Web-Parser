/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets.web_assets_html;

/**
 *
 * @author Jason
 */
public class Form_element {
    
    private String tag_name;
    private String for_value;
    private String id_value;
    
    public Form_element(String t_name, String f_value, String i_value){
        tag_name = t_name;
        for_value = f_value;
        id_value = i_value;
    }
    
    public void set_tag_name(String t){
        tag_name = t;
    }
    
    public void set_for_value(String f){
        for_value = f;
    }
    
    public void set_id_value(String i){
        id_value = i;
    }
    
    public String get_tag_name(){
        return tag_name;
    }
    
    public String get_for_value(){
        return for_value;
    }
    
    public String get_id_value(){
        return id_value;
    }
    
}
