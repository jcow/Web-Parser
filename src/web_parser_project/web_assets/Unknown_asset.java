/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

import java.io.InputStream;

/**
 *
 * @author Jason
 */
public class Unknown_asset extends Web_asset{
    
    protected InputStream content;
    
    public Unknown_asset(String url){
        super(url);
    }
    
    public void set_content(InputStream inc_content){
        content = inc_content;
    }
    
    public InputStream get_content(){
        return content;
    }
}
