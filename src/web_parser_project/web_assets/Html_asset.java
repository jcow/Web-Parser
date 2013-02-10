/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

import org.jsoup.nodes.Document;

/**
 *
 * @author Jason
 */
public class Html_asset extends Web_asset{
    
    protected Document contents;
    
    public Html_asset(String url){
        super(url);
    }
    
    public void set_contents(Document inc_contents){
        contents = inc_contents;
    }
    
    public Document get_contents(){
        return contents;
    }
    
}
