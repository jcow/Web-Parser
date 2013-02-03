/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import org.jsoup.nodes.Document;

/**
 *
 * @author Jason
 */
public class Site_parser {
    
    private Site_reader site_reader;
    private int parsed_pages = 0;
    private int read_urls = 0;
    
    public Site_parser(String starting_url){
        site_reader = new Site_reader(starting_url);
    }
    
    public void run(String starting_url){
        
        Document_parser doc_parser = new Document_parser();
        Document current_site;
        
        
        int limit = 10;
        
        int counter = 0;
        while(site_reader.has_next() && counter < limit){
            current_site = site_reader.get_next();
            
            if(current_site != null){
                doc_parser.parse(current_site);
                parsed_pages++;
            }
            
            read_urls++;
            counter++;
        }
        
        System.out.println("done");
    }
}
