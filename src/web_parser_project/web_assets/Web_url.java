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
public class Web_url {
    
    protected int id;
    
    protected String the_url;
    protected String direct_parent;
    protected LinkedList<String> urls_which_reference_asset;
    protected int http_code;
    protected String content_type;
    protected Web_asset asset;
    
    protected boolean io_error;
    protected boolean malformed_url;
    
    public Web_url(String url, String d_parent){
        the_url = url;
        direct_parent = d_parent;
        
        urls_which_reference_asset = new LinkedList();
        add_to_reference(d_parent);
        
        io_error = false;
        malformed_url = false;
        
        id  = 0;
    }
    
    public void set_id(int inc_id){id = inc_id;}
    public int get_id(){return id;}
    
    public void set_url(String inc_url){the_url = inc_url;}
    public String get_url(){return the_url;}
    
    public void set_http_code(int http_cde){http_code = http_cde;}
    public int get_http_code(){return http_code;}
    
    public void set_content_type(String c_type){content_type = c_type;}
    public String get_content_type(){return content_type;}
    
    public LinkedList<String> get_references(){return urls_which_reference_asset;}   
    public final void add_to_reference(String url){ 
        if(url != null){
            urls_which_reference_asset.add(url);
        }
        
    }
    
    public void set_web_asset(Web_asset w_asset){
        asset = w_asset;
    }
    
    public Web_asset get_web_asset(){
        return asset;
    }
    
    public String get_direct_parent(){
        return direct_parent;
    }
    
    public boolean is_404(){
        return Html_helper.is_404(http_code);
    }
    
    public boolean is_200_ok(){
        return Html_helper.is_200(http_code);
    }
    
    public void set_io_error(boolean inc_error){
        io_error = inc_error;
    }
    
    public void set_malformed_url(boolean inc_mal_url){
        malformed_url = inc_mal_url;
    }
    
    public boolean is_io_error(){
        return io_error;
    }
    
    public boolean is_malformed_url(){
        return malformed_url;
    }
    
}
