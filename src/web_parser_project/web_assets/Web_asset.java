/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

/**
 *
 * @author Jason
 */
public class Web_asset {
    
    public static boolean is_html_asset(Web_asset w_asset){
        
        if(w_asset != null && w_asset instanceof Html_asset){
            return true;
        }
        else{
            return false;
        }
    }
   
}
