/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.WebAssets;

/**
 *
 * @author Jason
 */
public class WebAsset {
    
    public static boolean is_html_asset(WebAsset w_asset){
        
        if(w_asset != null && w_asset instanceof Html_asset){
            return true;
        }
        else{
            return false;
        }
    }
   
}
