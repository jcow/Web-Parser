/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.WebAssets;

import java.util.LinkedList;
import WebParserProject.Libraries.HTMLHelper;

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
    protected WebAsset asset;
    
    // number of milliseconds to retrieve an item
    protected long retrieval_time;
    
    // gets the content's length in bytes
    protected long content_length;
    
    // last modified date in seconds since the unix epoch
    protected long last_modified;
    
    protected boolean io_error;
    protected boolean malformed_url;
    protected boolean timeout_error;
    protected boolean casting_error;
    
    public Web_url(String url, String d_parent){
        the_url = url;
        direct_parent = d_parent;
        
        urls_which_reference_asset = new LinkedList();
        add_to_reference(d_parent);
        
        io_error = false;
        malformed_url = false;
        
        id  = 0;
        
        retrieval_time = 0;
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
    
    public void set_content_length(long clength){
        content_length = clength;
    }
    
    public void set_web_asset(WebAsset w_asset){
        asset = w_asset;
    }
    
    public void set_last_modified(long lmodified){
        last_modified = lmodified;
    }
    
    public WebAsset get_web_asset(){
        return asset;
    }
    
    public String get_direct_parent(){
        return direct_parent;
    }
    
    public boolean is_404(){
        return HTMLHelper.is_404(http_code);
    }
    
    public boolean is_200_ok(){
        return HTMLHelper.is_200(http_code);
    }
    
    public void set_io_error(boolean inc_error){
        io_error = inc_error;
    }
    
    public void set_malformed_url(boolean inc_mal_url){
        malformed_url = inc_mal_url;
    }
    
    public void set_timeout_error(boolean err){
        timeout_error = err;
    }
    
    public void set_casting_error(boolean err){
        casting_error = err;
    }
    
    public void set_retrieval_time(long start, long stop){
        retrieval_time = stop-start;
    }
    
    public long get_retrieval_time(){
        return retrieval_time;
    }
    
    public boolean is_io_error(){
        return io_error;
    }
    
    public boolean is_malformed_url(){
        return malformed_url;
    }
    
    public boolean is_timeout_error(){
        return timeout_error;
    }
    
    public boolean is_casting_error(){
        return casting_error;
    }
    
    public int get_io_error(){
        if(io_error){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    public int get_malformed_url(){
        if(malformed_url){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    public long get_content_length(){
        return content_length;
    }
    
    public long get_last_modified(){
        return last_modified;
    }
    
}
