/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;


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

/**
 *
 * @author Jason
 */
public class Site_reader {
    
    private String starting_url;
    
    private HashMap<String,String> traveled_urls;
    private LinkedList<String> non_traveled_urls;
    
    private LinkedList<String> error_urls;
    private LinkedList<String> four_o_four_urls;
    
    
    
    public Site_reader(String s_url){
        starting_url = s_url;
        
        non_traveled_urls = new LinkedList();
        error_urls = new LinkedList();
        four_o_four_urls = new LinkedList();
        
        traveled_urls = new HashMap();
        
        add_unexplored_url(starting_url);
    }
    
    public Document get_next(){
        String current_url = non_traveled_urls.remove();
        
        try{
            URL the_url = new URL(current_url);
            
            HttpURLConnection connection = (HttpURLConnection) the_url.openConnection();
            
            InputStream in = connection.getInputStream();
            
            int http_status = connection.getResponseCode();
            
            if(Html_validator.is_200(http_status)){
                
                // only parse web pages
                if(is_web_page(connection.getContentType())){
                    System.out.println("ok");
                    Document html_page = Jsoup.parse(in, null, starting_url);
                    store_links(html_page, current_url);
                    return html_page;
                }
                else{
                    System.out.println("Error, not a good content type");
                    System.out.println(connection.getContentType());
                }
                
            }
            else if(Html_validator.is_404(http_status)){
                four_o_four_urls.add(current_url);
            }
            else{
                System.out.println("poor http status");
                System.out.println(current_url);
                System.out.println(http_status);
            }
            
        }
        
        // TODO, log these exceptions
        catch(MalformedURLException e){
            
            // TODO, log this exception
            System.out.println("----------");
            System.out.println("Malformed URL");
            System.out.println(e);
            System.out.println(current_url);
            error_urls.add(current_url);
            System.out.println("----------");
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.out.println(current_url);
            four_o_four_urls.add(current_url);
        }
        catch(IOException e){
            System.out.println("IO Exception");
            System.out.println(e);
            error_urls.add(current_url);
        }
        
        return null;
    }
    
    public LinkedList<String> get_error_urls(){
        return error_urls;
    }
    
    public boolean has_next(){
        if(non_traveled_urls.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
   
    
    private boolean is_web_page(String content_type){
        if(content_type.contains("text/html")){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void store_links(Document the_document, String current_url){
        // the link page must be in the same domain if it is to be parsed also check if it hasn't been checked already
        if(Html_validator.is_same_domain(starting_url, current_url) && has_already_been_seen(current_url) == false){
            Elements links = the_document.select("a[href]");
        
            for (Element link : links) {
                add_unexplored_url(link.attr("abs:href"));
            }
        }
    }
    
    private void add_unexplored_url(String the_url){
        non_traveled_urls.add(the_url);
    }
    
    private boolean has_already_been_seen(String the_url){
        if(traveled_urls.containsKey(the_url)){
            return true;
        }
        else{
            return false;
        }
    }
    
}
