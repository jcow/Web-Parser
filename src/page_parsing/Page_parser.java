/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package page_parsing;

import org.jsoup.nodes.Document;

/**
 *
 * @author Jason
 */
public class Page_parser {
    
    Page_parser(){}
    
    public static void parse_document(Document the_document){
        System.out.println(the_document.text());
    }
}
