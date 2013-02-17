/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

import java.util.LinkedList;
import org.jsoup.nodes.Document;

/**
 *
 * @author Jason
 */
public class Html_asset extends Web_asset{
    
    protected Document contents;
    protected LinkedList<String> misspellings;
    
    public Html_asset(Document inc_contents){
        contents = inc_contents;
        misspellings = new LinkedList();
    }
    
    public Document get_contents(){
        return contents;
    }
    
    public void add_to_misspellings(String misspelling){
        if(misspelling != null){
            misspellings.add(misspelling);
        }
    }
    
    public void add_to_misspellings(LinkedList<String> incoming_misspellings){
        if(incoming_misspellings != null){
            misspellings.addAll(incoming_misspellings);
        }
    }
    
}
