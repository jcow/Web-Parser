/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import page_parsing.Page_parser;
import web_parser_project.site_getter.Site_getter;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Job_delineator {
    
    private Site_getter site_reader;
    private int parsed_pages = 0;
    private int read_urls = 0;
    
    public Job_delineator(String starting_url, String domain){
        site_reader = new Site_getter(starting_url, domain);
    }
    
    public void run(String starting_url){
        
        Web_url current_site;
        
        int limit = 1;
        
        int counter = 0;
        while(site_reader.has_next() && counter < limit){
            current_site = site_reader.get_next();
            
            /*
            if(current_site != null){
                doc_parser.parse(current_site);
                parsed_pages++;
            }
            
            read_urls++;
            */
            counter++;
        }
        
        
        System.out.println("\n\n-----------------Parsed Pages----------------\n");
        
        Page_parser page_parser = new Page_parser();
        
        HashMap<String, Web_url> traveled_sites = site_reader.get_traveled_urls();
        Iterator it  = traveled_sites.keySet().iterator();
        Web_url current_it;
        while(it.hasNext()){
            String key = (String)it.next();
            current_it = (Web_url)traveled_sites.get(key);
            
            if(current_it.is_404()){
                print_fof(current_it.get_url(), current_it.get_references());
            }
            else if(current_it.is_200_ok()){
                System.out.println("OK: "+current_it.get_url());
            }
            
            Iterator referred_to = current_it.get_references().iterator();
            
            System.out.println("\tReferred By:");
            while(referred_to.hasNext()){
                System.out.println("\t\t"+referred_to.next());
            }
            
            System.out.println("\t"+current_it.getClass().getName());
            
            if(current_it.get_web_asset() instanceof Html_asset && current_it.get_web_asset() != null){
                System.out.println("printing web asset");
                page_parser.parse(current_it);
            }
            
        }
        
        
        
        System.out.println("done");
    }
    
    public void print_fof(String fof_url, LinkedList<String> requested_by){
        
        String r_by = "";
        Iterator it = requested_by.iterator();
        while(it.hasNext()){
            r_by += it.next();
        }
        
        System.out.println("404: "+fof_url+" Used by "+r_by);
    }
}
