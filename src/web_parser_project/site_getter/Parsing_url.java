/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.site_getter;

/**
 *
 * @author Jason
 */
public class Parsing_url {
    
    private String url;
    private String parent_url;
    
    public Parsing_url(String inc_url){
        url = inc_url;
    }
    
    public void set_url(String inc_url){
        url = inc_url;
    }
    
    public void set_parent_url(String inc_parent_url){
        parent_url = inc_parent_url;
    }
    
    public String get_url(){
        return url;
    }
    
    public String get_parent_url(){
        return parent_url;
    }
}
