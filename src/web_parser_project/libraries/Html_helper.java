/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import java.util.HashMap;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/**
 *
 * @author Jason
 */
public class Html_helper {
    
    private static Html_helper instance;
    
    // TODO, this list needs to be larger and include videos, etc
    private static String image_extensions = "png,jpg,gif";
    private static String video_extensions = "mp3";
    private static HashMap<String,String> deprecated_tags;
    
    
    private Html_helper(){
        deprecated_tags = new HashMap();
        deprecated_tags.put("applet", "applet");
        deprecated_tags.put("basefont", "basefont");
        deprecated_tags.put("center", "center");
        deprecated_tags.put("dir", "dir");
        deprecated_tags.put("font", "font");
        deprecated_tags.put("strike", "strike");
        deprecated_tags.put("u", "u");
        deprecated_tags.put("isindex", "isindex");
        deprecated_tags.put("xmp", "xmp");
        deprecated_tags.put("plaintext", "plaintext");
        deprecated_tags.put("listing", "listing");
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
    
    public static String strip_page_anchor(String the_string){
        
        
        if(the_string != null ){
            int pound_index = the_string.indexOf('#');
            
            if(pound_index == -1){
                return the_string;
            }
            else{
                return the_string.substring(0, pound_index);
            }  
        }
        else{
            return null;
        }
    }
    
    
    public static String strip_end_slash(String the_string){
        
        if(the_string != null && the_string.length() > 0 && the_string.charAt(the_string.length()-1) == '/'){
             return the_string.substring(0, the_string.length()-1);
        }
        else{
            return the_string;
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
    
    
    public static boolean is_content_type_html(String content_type){
        if(content_type.contains("text/html")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean is_tag_deprecated(String tag){
        
        if(tag != null){
            if(deprecated_tags.containsKey(tag)){
                return true;
            }
        }
        return false;
    }
    
    public boolean contains_inline_styling(Element tag){
        return tag.hasAttr("style");
    }
    
    public String get_tag_name(Element node){
        return node.tagName().trim().toLowerCase();
    }
    
    public String get_style_value(Element node){
        return node.attr("style");
    }
    
    public static boolean is_node_doctype(Node node){
        if(node != null){
            if(node instanceof DocumentType){
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean is_node_head(Element node){
        if(node != null){
            if(node.tagName().trim().toLowerCase().compareTo("head") == 0){
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean is_node_body(Element node){
        if(node != null){
            if(node.tagName().trim().toLowerCase().compareTo("body") == 0){
                return true;
            }
        }
        
        return false;
    }
    
}
