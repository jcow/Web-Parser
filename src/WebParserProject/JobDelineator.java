/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject;

import java.util.HashMap;
import java.util.Iterator;
import WebParserProject.Config.Config;
import WebParserProject.Libraries.HTMLHelper;
import WebParserProject.Output.Output;
import WebParserProject.PageParsing.PageParser;
import WebParserProject.SiteGetter.SiteGetter;
import WebParserProject.WebAssets.Html_asset;
import WebParserProject.WebAssets.ParseAsset;
import WebParserProject.WebAssets.Web_url;

/**
 *
 * @author Jason
 */
public class JobDelineator {
    
    private SiteGetter site_reader;
    private String starting_url;
    private String domain;
    
    public JobDelineator(String s_url, String dom){
        
        starting_url = s_url;
        domain = dom;
        
        site_reader = new SiteGetter(s_url, dom);
    }
    
    public void run(){
        
        Web_url current_site;
        ParseAsset parse_asset = new ParseAsset(starting_url, domain);
        
        int limit = Config.get_parse_count_limit();
        
        int counter = 0;
        long start_time = System.currentTimeMillis();
        System.out.println("Getting URLs");
        while(site_reader.has_next() && counter < limit){
            current_site = site_reader.get_next();
            counter++;
        }
        System.out.println("Done Getting URLs\n");
        
        long end_time = System.currentTimeMillis();
        
        parse_asset.add_to_total_time(end_time - start_time);
        
        HashMap<String, Web_url> traveled_sites = site_reader.get_traveled_urls();
        
        parse_asset.add_to_total_urls(traveled_sites.size());
        PageParser page_parser = new PageParser(parse_asset);
        
        Iterator it  = traveled_sites.keySet().iterator();
        Web_url current_it;
        
        System.out.println("Starting Parse of Obtained URLs");
        while(it.hasNext()){
            String key = (String)it.next();
            current_it = (Web_url)traveled_sites.get(key);
            
            if(current_it.is_404() == false){
                
                // must be in the same domain to get checked in-depth
                if(HTMLHelper.is_same_domain(domain, current_it.get_url())){ 

                    parse_asset.add_to_total_same_domain_urls(1);
                
                    if(current_it.get_web_asset() instanceof Html_asset && current_it.get_web_asset() != null){

                        // add to total pages
                        parse_asset.add_to_total_pages(1);

                        // parse the page
                        page_parser.parse(current_it);
                    }
                }

                if(HTMLHelper.is_content_type_image(current_it.get_content_type())){
                    parse_asset.add_to_total_images(1);
                }
            }
            
        }
        
        // set the urls
        parse_asset.set_urls(traveled_sites);
        
        Output.do_output(parse_asset);
        
        System.out.println("Finished");
    }
}
