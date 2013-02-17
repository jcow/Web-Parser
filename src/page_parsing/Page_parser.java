/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package page_parsing;

import java.util.ListIterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Page_parser {
    
    public Page_parser(){}
    
    public void parse(Web_url w_url){
        
        if(Web_asset.is_html_asset(w_url.get_web_asset())){
            Html_asset asset = (Html_asset)w_url.get_web_asset();
            parse_document(asset);
        }
    }
    
    public void parse_document(Html_asset html_asset){
        Document page_document = html_asset.get_contents();
        
        parse_body(page_document.body());
    }
    
    public void parse_body(Element body){
        parse_nodes(body.children(), 0);
    }
    
    
    public void parse_nodes(Elements nodes, int tab){
        
        if(nodes.isEmpty() == false){
            ListIterator<Element> iterator = nodes.listIterator();
            Element node;
            while(iterator.hasNext()){
                node = iterator.next();
                
                for(int p = 0; p < tab; p++){
                    System.out.print("\t");
                }
                
                System.out.println(node.ownText());
                parse_nodes(node.children(), tab+1);
            }
        }
        
        
        
    }
    
}
