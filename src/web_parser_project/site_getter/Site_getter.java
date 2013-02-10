/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.site_getter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import web_parser_project.libraries.Html_helper;
import web_parser_project.web_assets.Error_asset;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Unknown_asset;
import web_parser_project.web_assets.Web_asset;

/**
 *
 * @author Jason
 */
public class Site_getter {
    
    private String starting_url;
    
    private HashMap<String, Web_asset> traveled_assets;
    private LinkedList<Parsing_url> non_traveled_urls;
    
    public Site_getter(String s_url){
        starting_url = s_url;
        
        non_traveled_urls = new LinkedList();
        traveled_assets = new HashMap();
        
        add_unexplored_url(starting_url, null);
    }
    
    public Web_asset get_next(){
        Parsing_url current_url = non_traveled_urls.remove();
        
        System.out.println("Parsing: "+current_url.get_url());
        
        try{
            
            URL the_url = new URL(current_url.get_url());
            
            HttpURLConnection.setFollowRedirects(true);
            HttpURLConnection connection = (HttpURLConnection) the_url.openConnection();
            
            set_request_method(connection, current_url.get_url());
            
            InputStream in = connection.getInputStream();
            
            
            int http_status = connection.getResponseCode();
            String content_type = connection.getContentType();
            
            // 200 ok
            if(Html_helper.is_200(http_status)){
                
                if(Html_helper.is_content_type_html(content_type)){
                    Document html_page = Jsoup.parse(in, null, current_url.get_url());
                
                    store_links(html_page, current_url.get_url());
                    
                    return add_to_explored(current_url, html_page, http_status, content_type);
                }
                else{
                    return add_to_explored_non_html_asset(current_url, in, http_status);
                }
            }
            // not 200 ok
            else{
                add_to_explored_non_200_status_code(current_url, http_status);
            }
            
        }
        
        // Bad url
        catch(MalformedURLException e){
            
            System.out.println("----------");
            System.out.println("Malformed URL");
            System.out.println(e);
            System.out.println(current_url.get_url());
            System.out.println("----------");
            
            add_to_explored_malformed_url(current_url);
            
        }
        // 404 not found
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.out.println(current_url.get_url());
            
            add_to_explored_non_200_status_code(current_url, 404);
            
        }
        
        // something bad happend :(
        catch(IOException e){
            System.out.println("IO Exception");
            System.out.println(e);
            
            add_to_explored_io_exception(current_url);
        }
        
        return null;
    }
    
    public boolean has_next(){
        if(non_traveled_urls.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public HashMap<String, Web_asset> get_traveled_urls(){
        return traveled_assets;
    }
    
    private boolean has_already_been_seen(String the_url){
        if(traveled_assets.containsKey(the_url)){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void store_links(Document the_document, String current_url){
        
        // the link page must be in the same domain if it is to be parsed also check if it hasn't been checked already
        if(Html_helper.is_same_domain(starting_url, current_url)){
            
            String link_url;
            Elements links = the_document.select("a[href]");
            for (Element link : links) {
                
                link_url = link.attr("abs:href");
                
                // strips out the # anchors
                link_url = Html_helper.strip_page_anchor(link_url);
                
                if(has_already_been_seen(link_url) == false){
                    add_unexplored_url(link_url, current_url);
                }
            }
        }
    }
    
    private void add_unexplored_url(String the_url, String parent_url){
        Parsing_url p_url = new Parsing_url(the_url);
        p_url.set_parent_url(parent_url);
        non_traveled_urls.add(p_url);
    }
    
    private Web_asset add_to_explored(Parsing_url p_url, Document the_document, int status_code, String content_type){
        
        Html_asset html_asset = new Html_asset(p_url.get_url());
        
        html_asset.add_to_reference(p_url.get_parent_url());
        html_asset.set_content_type(content_type);
        html_asset.set_http_code(status_code);
        html_asset.set_contents(the_document);
        
        traveled_assets.put(p_url.get_url(), html_asset);
        
        return html_asset;
    }
    
    private Web_asset add_to_explored_non_200_status_code(Parsing_url p_url, int status_code){
        
        Web_asset web_asset = new Web_asset(p_url.get_url());
        web_asset.add_to_reference(p_url.get_parent_url());
        web_asset.set_http_code(status_code);
        
        traveled_assets.put(p_url.get_url(), web_asset);
        
        return web_asset;
    }
    
    private Web_asset add_to_explored_non_html_asset(Parsing_url p_url, InputStream contents, int status_code){
        
        Unknown_asset unknown_asset = new Unknown_asset(p_url.get_url());
        unknown_asset.add_to_reference(p_url.get_parent_url());
        unknown_asset.set_http_code(status_code);
        unknown_asset.set_content(contents);
        
        traveled_assets.put(p_url.get_url(), unknown_asset);
        
        return unknown_asset;
    }
    
    private Web_asset add_to_explored_malformed_url(Parsing_url p_url){
        Error_asset error_asset = new Error_asset(p_url.get_url());
        error_asset.add_to_reference(p_url.get_parent_url());
        error_asset.set_malformed_url(true);
        
        return error_asset;
    }
    
    private Web_asset add_to_explored_io_exception(Parsing_url p_url){
        Error_asset error_asset = new Error_asset(p_url.get_url());
        error_asset.add_to_reference(p_url.get_parent_url());
        error_asset.set_io_error(true);
        
        return error_asset;
    }
    
    private void set_request_method(HttpURLConnection connection, String the_url){
        // TODO, set head request if the url is an image, css file, jscript file, etc.
       // do this 
    }
    
    
}
