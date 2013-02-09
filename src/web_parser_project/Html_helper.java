/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

/**
 *
 * @author Jason
 */
public class Html_helper {
    
    private static Html_helper instance;
    
    private Html_helper(){}
    
    
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
    
    
}
