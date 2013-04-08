/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import config.Config;
import data.File_writer;
import java.util.HashMap;
import java.util.Iterator;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Output_html extends Output{
    
    public Output_html(){
        
    }
    
    public void out(HashMap<String, Web_url> traveled_sites){
        add_head_info();
        iterate_urls(traveled_sites);
        add_foot_info();
        
        File_writer.write_to_file(Config.get_output_file_string(), out);
    }
    
    public void iterate_urls(HashMap<String, Web_url> traveled_sites){
        
        Iterator traveled_sites_iterator = traveled_sites.keySet().iterator();
        
        String key;
        Web_url current_url;
        while(traveled_sites_iterator.hasNext()){ 
            key = (String)traveled_sites_iterator.next();
            current_url = (Web_url)traveled_sites.get(key);
            if(current_url != null){
                create_line("<div>", 2);
                
                    dump_url_data(current_url);
                    
                    if(Web_asset.is_html_asset(current_url.get_web_asset())){
                        dump_html_asset_data((Html_asset)current_url.get_web_asset());
                    }
                    
                create_line("</div>", 2);
            }
        }
    }
    
    private void dump_html_asset_data(Html_asset html_asset){
       create_line("<dl>", 4);  
       
       create_dt_item("Html General", 5);
       dump_html_asset_head_data(html_asset);
       
       create_line("</dl>", 4);
    }
    
    private void dump_html_asset_head_data(Html_asset html_asset){
        create_line("<dl>", 6);
        
        create_dt_item("Document Type", 7);
        create_dd_item(html_asset.get_doctype(), 7);
        
        create_dt_item("Title", 7);
        create_dd_item(html_asset.get_title(), 7);
        
        create_dt_item("Description", 7);
        create_dd_item(html_asset.get_description(), 7);
        
        create_line("</dl>", 6);
    }
    
    
    private void dump_url_data(Web_url current_url){
            
            create_line("<dl>", 3);
            
            create_line("<dt>", "Url", "</dt>", 4);
            create_line("<dd>", current_url.get_url(), "</dd>", 4);
            
            create_line("<dt>", "Direct Parent", "</dt>", 4);
            create_line("<dd>", current_url.get_direct_parent(), "</dd>", 4);
            
            create_line("<dt>", "HTTP Status", "</dt>", 4);
            create_line("<dd>", Integer.toString(current_url.get_http_code()), "</dd>", 4);
            
            create_line("<dt>", "Content Type", "</dt>", 4);
            create_line("<dd>", current_url.get_content_type(), "</dd>", 4);
            
            create_line("<dt>", "IO Error", "</dt>", 4);
            create_line("<dd>", Integer.toString(current_url.get_io_error()), "</dd>", 4);
            
            create_line("<dt>", "Malformed URL", "</dt>", 4);
            create_line("<dd>", Integer.toString(current_url.get_malformed_url()), "</dd>", 4);
            
            create_line("</dl>", 3);
       
    }
    
    
    private void create_dd_item(String content, int t){
        create_line("<dt>", content, "</dt>", t);
    }
    
    private void create_dt_item(String content, int t){
        create_line("<dt>", content, "</dt>", t);
    }
    
    private void add_head_info(){
        create_line("<!DOCTYPE html>");
        create_line("<html>");
        create_line("<head>", 1);
        create_line("<title>Output</title>", 2);
        create_line("</head>", 1);
        create_line("<body>", 1);
    }
    
    private void add_foot_info(){
        create_line("</body>", 1);
        create_line("</html>");
    }
}
