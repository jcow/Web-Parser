/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Libraries;

import java.util.HashMap;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/**
 *
 * @author Jason
 */
public class HTMLHelper {
    
    private static HTMLHelper instance;
    
    // TODO, this list needs to be larger and include videos, etc
	private static final String regex_for_urls = "/((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)/";
    private static String image_extensions = "png,jpg,gif";
    private static String video_extensions = "mp3";
    private HashMap<String,String> deprecated_tags;
	private HashMap<String,String> html_entities;
	
    
    
    private HTMLHelper(){
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
		
		html_entities = new HashMap();
		html_entities.put("&nbsp;", "&nbsp;");
		html_entities.put("&lt;", "&lt;");
		html_entities.put("&gt;", "&gt;");
		html_entities.put("&amp;", "&amp;");
		html_entities.put("&cent;", "&cent;");
		html_entities.put("&pound;", "&pound;");
		html_entities.put("&yen;", "&yen;");
		html_entities.put("&euro;", "&euro;");
		html_entities.put("&copy;", "&copy;");
		html_entities.put("&reg;", "&reg;");
		
		html_entities.put("&#160;", "&#160;");
		html_entities.put("&#60;", "&#60;");
		html_entities.put("&#62;", "&#62;");
		html_entities.put("&#38;", "&#38;");
		html_entities.put("&#162;", "&#162;");
		html_entities.put("&#163;", "&#163;");
		html_entities.put("&#165;", "&#165;");
		html_entities.put("&#8364;", "&#8364;");
		html_entities.put("&#169;", "&#169;");
		html_entities.put("&#174;", "&#174;");
		html_entities.put("&#39;", "&#39;");
		
		
		
		
    }
    
    public static HTMLHelper get_instance(){
        if(instance == null){
            instance = new HTMLHelper();
        }
        
        return instance;
    }
	
	public boolean is_html_entity(String word){
		return html_entities.containsKey(word);
	}
	
	public String remove_html_entities(String word){
		for (String key : html_entities.keySet()){
			if(word.contains(key)){
				word = word.replace(key, "");
			}
		}
		return word;
	}
	
	public String contains_html_entity(String word){
		for (String key : html_entities.keySet()){
			if(word.contains(key)){
				return key;
			}
		}
		return null;
	}
    
    public static boolean is_http_address(String address){
        if(address.indexOf("http") == -1 && address.indexOf("https") == -1){
            return false;
        }
        else{
            return true;
        }
    }
	
	public static boolean is_url(String s){
		return s.matches(HTMLHelper.regex_for_urls);
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
    
    public static boolean is_3xx_redirect(int status){
        if(300 <= status && status <= 399){
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
        String extension = HTMLHelper.get_extension(url);
        
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
        if(content_type != null){
            if(content_type.contains("text/html")){
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean is_content_type_image(String content_type){
        if(content_type != null){
            if(content_type.contains("image")){
                return true;
            }
        }
        
        return false;
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
    
    public static String get_tag_name(Element node){
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
    
    public static boolean is_node_image(Element node){
        if(HTMLHelper.get_tag_name(node).equals("img")) {
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_node_anchor(Element node){
        if(HTMLHelper.get_tag_name(node).equals("a")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_node_label(Element node){
        return (HTMLHelper.get_tag_name(node).equals("label"))?true:false;
    }
    
    public static boolean is_node_input(Element node){
        return (HTMLHelper.get_tag_name(node).equals("input"))?true:false;
    }
    
    public static boolean is_node_textarea(Element node){
        return (HTMLHelper.get_tag_name(node).equals("textarea"))?true:false;
    }
    
    public static boolean is_node_select(Element node){
        return (HTMLHelper.get_tag_name(node).equals("select"))?true:false;
    }
    
    public static boolean is_node_ignored_input_for_labels(Element node){
        if(HTMLHelper.is_node_input(node) && (node.attr("type").equals("submit") || node.attr("type").equals("hidden") || node.attr("type").equals("button") || node.attr("type").equals("image")) ){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Determines if a node is a form input, select, or text area and ignore <input type="submit"> fields
     * http://www.w3.org/TR/2008/WD-WCAG20-TECHS-20080430/H44.html
     * @param node
     * @return 
     */
    public static boolean should_node_have_associated_label(Element node){
        if(HTMLHelper.is_node_ignored_input_for_labels(node) == false && (HTMLHelper.is_node_input(node) || HTMLHelper.is_node_select(node)|| HTMLHelper.is_node_textarea(node))){
            return true;
        }
        else{
            return false;
        }
    }
    
    
}
