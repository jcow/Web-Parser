/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Parse_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Web_url_template {
    
    Parse_results parse_result = new Parse_results();
    
    public Web_url_template(Parse_asset parse_asset){
        
        parse_result.starting_url = parse_asset.get_starting_url();
        parse_result.domain = parse_asset.get_domain();
        parse_result.total_urls = parse_asset.get_total_urls();
        parse_result.total_misspellings = parse_asset.get_total_misspellings();
        parse_result.total_same_domain_urls = parse_asset.get_total_same_domain_urls();
        parse_result.total_time = parse_asset.get_total_time();
        parse_result.total_same_domain_pages = parse_asset.get_total_pages();
        parse_result.total_images = parse_asset.get_total_images();
        
        Web_url current_url;
        Html_asset current_asset;
        HashMap<String, Web_url> traveled_sites = parse_asset.get_urls();
        Iterator it = traveled_sites.keySet().iterator();
        
        int counter = 0;
        int last_item = traveled_sites.size()-1;
        
        while(it.hasNext()){
            
            String key = (String)it.next();
            current_url = (Web_url)traveled_sites.get(key);
            W_url w_url = new W_url();
            w_url.url = current_url.get_url();
            w_url.http_code = current_url.get_http_code();
            w_url.direct_parent = current_url.get_direct_parent();
            w_url.content_type = current_url.get_content_type();
            w_url.retrieval_time = current_url.get_retrieval_time();
            w_url.content_length = current_url.get_content_length();
            w_url.last_modified = current_url.get_last_modified();
            
            w_url.io_error = current_url.is_io_error();
            w_url.malformed_url = current_url.is_malformed_url();
            w_url.timeout_error = current_url.is_timeout_error();
            w_url.casting_error = current_url.is_casting_error();
            
            w_url.referencing_urls = convert_string_to_str(current_url.get_references());
            
            if(counter == last_item){
                w_url.is_not_last = false;
            }
            else{
                w_url.is_not_last = true;
            }
            
            System.out.println(w_url.is_not_last);
            
            
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
                w_url.h_asset.no_alt_text = convert_string_to_str(current_asset.get_no_alt_text());
                w_url.h_asset.poor_link_naming = convert_string_to_str(current_asset.get_poor_link_naming());
                w_url.h_asset.inputs_no_labels = convert_string_to_str(current_asset.get_inputs_no_labels());
            }
            
            parse_result.url_list.add(w_url);
            counter++;
        }
    }
    
    public LinkedList<W_url> urls(){
        return parse_result.url_list;
    }
    
    
    private LinkedList<Str> convert_string_to_str(LinkedList<String> strings){
        LinkedList<Str> str = new LinkedList();
        
        int counter = 0;
        int last_index = strings.size() - 1;
        for(String string : strings){
            if(counter == last_index){
                str.add(new Str(string, false));
            }
            else{
                str.add(new Str(string, true));
            }
            counter++;
        }
        
        return str;
    }
    
    
    class LItem{
        boolean is_not_last;
    }
    
    class W_url extends LItem{
        
        String url;
        String direct_parent;
        int http_code;
        String content_type;
        long retrieval_time;
        boolean io_error;
        boolean malformed_url;
        boolean timeout_error;
        boolean casting_error;
        LinkedList<Str> referencing_urls;
        boolean last_url;
        long content_length;
        long last_modified;
        

        H_asset h_asset;
        
        public W_url(){}
    }
    
    class Str extends LItem{
        String value;
        
        Str(String v, boolean i){
            value = v;
            is_not_last = i;
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
        LinkedList<Str> no_alt_text;
        LinkedList<Str> poor_link_naming;
        LinkedList<Str> inputs_no_labels;
        
        H_asset(){
            
        }
    }
    
    class Parse_results{
        
        String starting_url;
        String domain;
        
        long total_time;
        int total_misspellings;
        int total_same_domain_pages;
        int total_images;
        int total_urls;
        int total_same_domain_urls;
        LinkedList<W_url> url_list;
        
        public Parse_results(){
            url_list = new LinkedList();
        }
    }
        
}
