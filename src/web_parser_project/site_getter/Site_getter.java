/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.site_getter;


import web_parser_project.config.Config;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import web_parser_project.libraries.Html_helper;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Other_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Site_getter {
    
    private String starting_url;
    private String domain;
    
    private HashMap<String, Web_url> traveled_assets;
    private LinkedList<Web_url> non_traveled_urls;
    
    
    public Site_getter(String s_url, String s_domain){
        starting_url = s_url;
        domain = s_domain;
        
        non_traveled_urls = new LinkedList();
        traveled_assets = new HashMap();
        
        add_unexplored_url(starting_url, null);
    }
    
    public Web_url get_next(){
        Web_url current_url = non_traveled_urls.remove();
        
        //System.out.println("Parsing: "+current_url.get_url());
        
        try{
            
            long start_time = System.currentTimeMillis();
            
            URL the_url = new URL(current_url.get_url());
            
            System.out.println("\t\t"+the_url);
            
            HttpURLConnection.setFollowRedirects(true);
            HttpURLConnection connection = (HttpURLConnection) the_url.openConnection();
            
            // set the timeout
            connection.setConnectTimeout(Config.get_timeout_limit());
            
            set_request_method(connection, current_url.get_url());
            
            InputStream in = connection.getInputStream();
            
            long end_time = System.currentTimeMillis();
            
            current_url.set_retrieval_time(start_time, end_time);
            
            int http_status = connection.getResponseCode();
            String content_type = connection.getContentType();
            
            // set some header values
            current_url.set_content_length(connection.getContentLengthLong());
            current_url.set_last_modified(connection.getLastModified());
            current_url.set_content_type(content_type);
            
            // 200 ok
            if(Html_helper.is_200(http_status)){
                        
                if(Html_helper.is_content_type_html(content_type)){
                    Document html_page = Jsoup.parse(in, null, current_url.get_url());
                
                    store_things(html_page, current_url.get_url());
                    
                    return add_to_explored(current_url, html_page, http_status);
                }
                else{
                    return add_to_explored_non_html_asset(current_url, in, http_status, content_type);
                }
            }
            // not 200 ok
            else{
                add_to_explored_non_200_status_code(current_url, http_status);
            }
            
        }
        
        // Bad url
        catch(MalformedURLException e){
            add_to_explored_malformed_url(current_url);
        }
        // 404 not found
        catch(FileNotFoundException e){
            add_to_explored_non_200_status_code(current_url, 404);   
        }
        catch(ClassCastException e){
            add_to_explored_set_casting_error(current_url);
        }
        // timeout
        catch(SocketTimeoutException e){
            add_to_explored_set_timeout_error(current_url);
        }
        // something bad happend :(
        catch(IOException e){
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
    
    public HashMap<String, Web_url> get_traveled_urls(){
        return traveled_assets;
    }
    
    private Web_url url_has_already_been_seen(String the_url){
        
        // does it exist in the hash map traveled
        if(traveled_assets.containsKey(the_url)){
            return traveled_assets.get(the_url);
        }
        else{
            // loop through and check if it's in the non traveled
            for(Web_url w_url : non_traveled_urls){
                if(the_url.equals(w_url.get_url())){
                    return w_url;
                }
            }
            return null;
        }
    }
    
    private void store_things(Document the_document, String current_url){
        
        //System.out.println("\tChecking Domain");
//        System.out.println("\t\t"+starting_url);
        
        // the current page must be in the same domain if it is to be parsed also check if it hasn't been checked already
        if(Html_helper.is_same_domain(domain, current_url)){
            //System.out.println("\t\tPassed");
            //System.out.println("\tLinks");
            
            String url;
            Elements links = the_document.select("a[href]");
            for (Element link : links) {
                url = link.attr("abs:href");
                store_item(current_url, url);
            }
            
            Elements imgs = the_document.select("img");
            for(Element img : imgs){
                url = img.attr("abs:src");
                store_item(current_url, url);
            }
            
            Elements scripts = the_document.select("script");
            for(Element script : scripts){
                url = script.attr("abs:src");
                if(!url.equals("")){
                    store_item(current_url, url);
                }
                
            }
            
            Elements link_tags = the_document.select("link");
            for(Element link_tag : link_tags){
                url = link_tag.attr("abs:src");
                if(!url.equals("")){
                    store_item(current_url, url);
                }
                
            }
        }
    }
    
    /**
     * Called by store things... stores an item that is passed from it
     */
    private void store_item(String current_url, String link_url){
        System.out.println("\t\t\t"+link_url);
                
        // strips out the # anchors
        link_url = Html_helper.strip_page_anchor(link_url);

        // strips off the end / if there is one
        link_url = Html_helper.strip_end_slash(link_url);

        // if the page is not linking to itself, continue on
        if(link_url.compareTo(current_url) != 0){

            // if the page has not been seen already, and it is not in the queue, then store a reference
            Web_url seen_url = url_has_already_been_seen(link_url);
            if(seen_url == null){

                //System.out.println(" - added to unexplored");

                add_unexplored_url(link_url, current_url);
            }
            // if a page has been seen, store a reference to it
            else{
                //System.out.println(" - added to reference");
                seen_url.add_to_reference(current_url);
            }
        }
        else{
            //System.out.println(" - not added to anything");
        }
    }
    
    private void add_unexplored_url(String the_url, String parent_url){
        Web_url url_to_add = new Web_url(the_url, parent_url);
        non_traveled_urls.add(url_to_add);
    }
    
    private Web_url add_to_explored(Web_url w_url, Document the_document, int status_code){
        
        w_url.set_web_asset(new Html_asset(the_document));
        w_url.set_http_code(status_code);
        
        traveled_assets.put(w_url.get_url(), w_url);
        
        return w_url;
    }
    
    private Web_url add_to_explored_non_200_status_code(Web_url w_url, int status_code){
        
        w_url.set_http_code(status_code);
        traveled_assets.put(w_url.get_url(), w_url);
        
        return w_url;
    }
    
    private Web_url add_to_explored_non_html_asset(Web_url w_url, InputStream contents, int status_code, String content_type){
        
        w_url.set_web_asset(new Other_asset(contents));
        w_url.set_content_type(content_type);
        w_url.set_http_code(status_code);
        
        traveled_assets.put(w_url.get_url(), w_url);
        
        return w_url;
    }
    
    private Web_url add_to_explored_malformed_url(Web_url w_url){
        w_url.set_malformed_url(true);
        return w_url;
    }
    
    private Web_url add_to_explored_io_exception(Web_url w_url){
        w_url.set_io_error(true);
        return w_url;
    }
    
    private Web_url add_to_explored_set_timeout_error(Web_url w_url){
        w_url.set_timeout_error(true);
        return w_url;
    }
    
    private Web_url add_to_explored_set_casting_error(Web_url w_url){
        w_url.set_casting_error(true);
        return w_url;
    }
    
    private void set_request_method(HttpURLConnection connection, String the_url){
        // TODO, set head request if the url is an image, css file, jscript file, etc.
       // do this 
    }
    
    
}
