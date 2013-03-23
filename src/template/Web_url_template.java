/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Web_url_template {
    
    LinkedList<W_url> url_list;
    
    public Web_url_template(String starting_url, String domain, HashMap<String, Web_url>traveled_sites){
        url_list = new LinkedList();
        Web_url current_url;
        Html_asset current_asset;
        Iterator it = traveled_sites.keySet().iterator();
        
        while(it.hasNext()){
            String key = (String)it.next();
            current_url = (Web_url)traveled_sites.get(key);
            W_url w_url = new W_url();
            w_url.url = current_url.get_url();
            w_url.http_code = current_url.get_http_code();
            w_url.direct_parent = current_url.get_direct_parent();
            w_url.content_type = current_url.get_content_type();
            w_url.io_error = current_url.is_io_error();
            w_url.malformed_url = current_url.is_malformed_url();
            
            w_url.referencing_urls = convert_string_to_str(current_url.get_references());
            
            if(current_url.get_web_asset() instanceof Html_asset){
                current_asset = (Html_asset)current_url.get_web_asset();
                w_url.h_asset = new H_asset();
                w_url.h_asset.doc_type = current_asset.get_doctype();
                w_url.h_asset.title = current_asset.get_title();
                w_url.h_asset.description = current_asset.get_description();
                w_url.h_asset.misspellings = convert_string_to_str(current_asset.get_misspellings());
                w_url.h_asset.hash_tags = convert_string_to_str(current_asset.get_hash_tags());
                w_url.h_asset.emails = convert_string_to_str(current_asset.get_emails());
                w_url.h_asset.deprecated_tags = convert_string_to_str(current_asset.get_deprecated_tags());
                
            }
            
            url_list.add(w_url);
        }
    }
    
    public LinkedList<W_url> urls(){
        return url_list;
    }
    
    private LinkedList<Str> convert_string_to_str(LinkedList<String> strings){
        LinkedList<Str> str = new LinkedList();
        
        for(String string : strings){
            str.add(new Str(string));
        }
        
        return str;
    }
    
    class W_url{
        
        String url;
        String direct_parent;
        int http_code;
        String content_type;
        boolean io_error;
        boolean malformed_url;
        LinkedList<Str> referencing_urls;

        H_asset h_asset;
        
        public W_url(){}
    }
    
    class Str{
        String value;
        
        Str(String v){
            value = v;
        }
    }
    
    class H_asset{
        
        String doc_type;
        String title;
        String description;
        LinkedList<Str> misspellings;
        LinkedList<Str> at_mentions;
        LinkedList<Str> hash_tags;
        LinkedList<Str> emails;
        LinkedList<Str> deprecated_tags;
        
        H_asset(){
            
        }
    }
        
}
