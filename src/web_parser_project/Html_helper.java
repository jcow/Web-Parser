/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import java.net.URLConnection;

/**
 *
 * @author Jason
 */
public class Html_helper {
    
    private static Html_helper instance;
    
    // TODO, this list needs to be larger and include videos, etc
    private static String image_extensions = "png,jpg,gif";
    private static String video_extensions = "mp3";
    
    
    
    
    
    private Html_helper(){
        
    }
    
    
    public static Html_helper get_instance(){
        if(instance == null){
            instance = new Html_helper();
        }
        
        return instance;
    }
    
    public static boolean is_http_address(String address){
        if(address.indexOf("http") == -1 && address.indexOf("https") == -1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public static boolean is_same_domain(String base_url, String test_url){
        if(test_url.contains(base_url)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static String strip_page_anchor(String url){
        
        int pound_index = url.indexOf('#');
        
        if(pound_index == -1){
            return url;
        }
        else{
            return url.substring(0, pound_index);
        }
    }
    
    public static boolean is_404(int status){
        if(status == 404){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_200(int status){
        if(status == 200){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static String get_extension(String url){
        int last_index_of = url.lastIndexOf('.');
        
        if(last_index_of != -1 && last_index_of != url.length()){
            String ret = "";
            for(int i = last_index_of + 1; i < url.length(); i++){
                if(Character.isLetterOrDigit(url.charAt(i))){
                    ret += url.charAt(i);
                }
                else{
                    break;
                }
            }
            
            return ret;
        }
        else{
            return null;
        }
    }
    
    /**
     * Looks at known non-html file types and sees if the url provided is one of them
     * @param url 
     */
    public static boolean is_non_html_url(String url){
        String extension = Html_helper.get_extension(url);
        
        if(extension != null){
            if(image_extensions.contains(extension)){
                return true;
            }
            else if(video_extensions.contains(extension)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    
}
