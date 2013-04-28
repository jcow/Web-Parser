/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.config;

import web_parser_project.data.File_reader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import web_parser_project.libraries.Html_helper;

/**
 *
 * @author Jason
 */
public abstract class Config {
    
    protected static String domain = "";
    
    protected static String application_type;
    
    protected static String config_location;
    
    protected static String dictionary_location;
    
    protected static String custom_dictionary_location;
    
    protected static int parse_count_limit;
    
    // timeout in milliseconds on how long to try to connect to a url
    protected static int timeout_limit;
    
    protected static boolean respect_robots_txt;
    protected static String robots_txt_location = "";
    protected static LinkedList<String> robots_txt_directory_ignores;
    
    protected static String dictionary_index          = "dictionary_location";
    protected static String custom_dictionary_index   = "custom_dictionary_location";
    
    protected static String parse_count_limit_index = "parse_count_limit";
    
    protected static String timeout_limit_index = "timeout_limit";
    
    protected static String respect_robots_txt_index = "respect_robots.txt";
    
    
    protected Config(){
        config_location = "C:\\Users\\Jason\\Documents\\NetBeansProjects\\web_parser_project\\src\\web_parser_project\\resources\\configs\\config.txt";
    }
    
    
    public LinkedList<String> read(String what){
        
        LinkedList<String> config_contents = File_reader.read_file(what);
        
        return config_contents;
    }
    
    public void initialize(){}
    
    
    public boolean is_config_comment_or_empty(String item){
        item = item.trim();
        if(item.length() == 0 || item.charAt(0) == '#'){
            return true;
        }
        else{
            return false;
        }
    }
    
    protected String[] split(String item){
        return item.split("::");
    }
    
    protected void parse_config_contents(LinkedList<String> contents){
        for(String item : contents){
            if(is_config_comment_or_empty(item) == false){
                
                String[] parts = split(item);
                String index = parts[0].trim();
                String value = parts[1].trim();
        
                parse_config_item(index, value);
            }
        }
    }
    
    protected void parse_config_item(String index, String value){
        
        // dictionary location
        if(is_location_of_index(dictionary_index, index)){
            dictionary_location = value;
        }
        // custom dictionary location
        else if(is_location_of_index(custom_dictionary_index, index)){
            custom_dictionary_location = value;
        }
        // parse count limit
        else if(is_location_of_index(parse_count_limit_index, index)){
            parse_count_limit = Integer.parseInt(value);
        }
        // timeout limit
        else if(is_location_of_index(timeout_limit_index, index)){
            timeout_limit = Integer.parseInt(value);
        }
        // is respect_robots.txt
        else if(is_location_of_index(respect_robots_txt_index, index)){
            if(value.equals("true")){
                respect_robots_txt = true;
                set_robots_txt_location();
            }
            else{
                respect_robots_txt = false;
            }
        }
        
    }
    
    protected boolean is_location_of_index(String index_to_match, String index_read_in){
        if(index_read_in.equals(index_to_match)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static void set_application_type(String app_type){
        application_type = app_type;
    }
    
    public static String get_application_type(){
        return application_type;
    }
    
    public static void set_domain(String d){
        domain = d;
    }
    
    public static String get_domain(){
        return domain;
    }
    
    public static boolean is_app_type_gui(){
        return (application_type.equals("gui"))?true:false;
    }
    
    public static boolean is_app_type_gui(String app_type){
        return (app_type.equals("gui"))?true:false;
    }
    
    public static String get_dictionary_location(){
        return dictionary_location;
    }
    
    public static String get_custom_dictionary_location(){
        return custom_dictionary_location;
    }
    
    public static int get_parse_count_limit(){
        return parse_count_limit;
    }
    
    public static int get_timeout_limit(){
        return timeout_limit;
    }
    
    public static boolean is_url_in_ignore_directory(String url){
        boolean is_in_ignore = false;
        for(String robots_txt_directory: robots_txt_directory_ignores){
            if(url.contains(robots_txt_directory)){
                is_in_ignore = true;
                break;
            }
        }
        return is_in_ignore;
    }
    
    private void set_robots_txt_location(){
        String dom = domain;
        if(domain.length() >= 2 && domain.charAt(domain.length()-1) == '/'){
            dom = domain.substring(0, domain.length()-1);
        }
        
        robots_txt_directory_ignores = new LinkedList();
        
        URL the_url;
        try {
            the_url = new URL(dom+"/robots.txt");
            
            System.out.println("\t\t"+the_url);

            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = (HttpURLConnection) the_url.openConnection();

            // set the timeout
            connection.setConnectTimeout(Config.get_timeout_limit());

            InputStream in = connection.getInputStream();

            int http_status = connection.getResponseCode();
            
            

            // 200 ok, get content, go forth
            if(Html_helper.is_200(http_status)){
                String r_txt = IOUtils.toString(in, "UTF-8");
                String[] parts = r_txt.split("\\r?\\n");
                
                // right now we don't specify the useragent so only look for the * useragent disallows
                boolean in_right_disallows = false;
                for(int i = 0; i < parts.length; i++){
                    if(parts[i].trim().contains("User-agent: *")){
                        in_right_disallows = true;
                    }
                    else if(in_right_disallows == true){
                        if(parts[i].trim().contains("Disallow")){
                            String[] disallow_parts = parts[i].split(":");
                            if(disallow_parts.length == 2){
                                robots_txt_directory_ignores.add(disallow_parts[1].trim());
                            }
                        }
                        else{
                            in_right_disallows = false;
                        }
                    }
                }
                
                
                System.out.println("yo");
            }
            
        } 
        catch (MalformedURLException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception ex){
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

   
    
    
}
