/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import web_parser_project.documents.Document_parser;
import web_parser_project.site_getter.Site_getter;
import web_parser_project.web_assets.Web_asset;

/**
 *
 * @author Jason
 */
public class Job_delineator {
    
    private Site_getter site_reader;
    private int parsed_pages = 0;
    private int read_urls = 0;
    
    public Job_delineator(String starting_url){
        site_reader = new Site_getter(starting_url);
    }
    
    public void run(String starting_url){
        
        Document_parser doc_parser = new Document_parser();
        Web_asset current_site;
        
        int limit = 10;
        
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
        
        
        HashMap<String, Web_asset> traveled_sites = site_reader.get_traveled_urls();
        Iterator it  = traveled_sites.keySet().iterator();
        Web_asset current_it;
        while(it.hasNext()){
            String key = (String)it.next();
            current_it = (Web_asset)traveled_sites.get(key);
            
            if(current_it.is_404()){
                print_fof(current_it.get_url(), current_it.get_references());
            }
            else if(current_it.is_200_ok()){
                System.out.println("OK: "+current_it.get_url());
            }
            
            System.out.println("\t"+current_it.getClass().getName());
            
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
