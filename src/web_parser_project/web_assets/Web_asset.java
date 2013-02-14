/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

import java.util.LinkedList;
import web_parser_project.libraries.Html_helper;

/**
 *
 * @author Jason
 */
public class Web_asset {
    
    protected String the_url;
    protected LinkedList<String> urls_which_reference_asset;
    protected int http_code;
    protected String content_type;
    
    public Web_asset(String url){
        the_url = url;
        urls_which_reference_asset = new LinkedList();
    }
    
    public void set_url(String inc_url){the_url = inc_url;}
    public String get_url(){return the_url;}
    
    public void set_http_code(int http_cde){http_code = http_cde;}
    public int get_http_code(){return http_code;}
    
    public void set_content_type(String c_type){content_type = c_type;}
    public String get_content_type(){return content_type;}
    
    public LinkedList<String> get_references(){return urls_which_reference_asset;}   
    public void add_to_reference(String url){ 
        if(url != null){
            urls_which_reference_asset.add(url);
        }
        
    }    
    
    public boolean is_404(){
        return Html_helper.is_404(http_code);
    }
    
    public boolean is_200_ok(){
        return Html_helper.is_200(http_code);
    }
}
