/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Jason
 */
public class Document_parser {
    
    public Document_parser(){}
    
    public void parse(Document the_document){
        System.out.println(the_document.title());
        
        
    }
}
