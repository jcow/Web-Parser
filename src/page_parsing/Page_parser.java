/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package page_parsing;

import java.util.ListIterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import web_parser_project.libraries.Html_helper;
import web_parser_project.libraries.Spell_checker;
import web_parser_project.libraries.Text_helper;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Web_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Page_parser {
    
    private Html_asset current_html_asset;
    private Spell_checker spell_checker;
    
    public Page_parser(){
        spell_checker = Spell_checker.getInstance();
    }
    
    public void parse(Web_url w_url){
        
        if(Web_asset.is_html_asset(w_url.get_web_asset())){
            current_html_asset = (Html_asset)w_url.get_web_asset();
            parse_document();
        }
    }
    
    public void parse_document(){
        Document page_document = current_html_asset.get_contents();
        
        parse_body(page_document.body());
    }
    
    public void parse_body(Element body){
        parse_nodes(body.children(), 0);
    }
    
    
    /**
     * Recurses a set of nodes and gets their children
     * @param nodes
     * @param tab 
     */
    public void parse_nodes(Elements nodes, int tab){
        
        if(nodes.isEmpty() == false){
            ListIterator<Element> iterator = nodes.listIterator();
            Element node;
            while(iterator.hasNext()){
                node = iterator.next();
                
                for(int p = 0; p < tab; p++){
                    System.out.print("\t");
                }
                
                System.out.println(node.tagName().length());
                
                // check if the node is deprecated
                check_if_node_is_deprecated(node);
                
                // check the text of the node
                check_nodes_text(node);
                
                // parse out the children of this node
                parse_nodes(node.children(), tab+1);
            }
        }
        
    }
    
    private void check_if_node_is_deprecated(Element node){
        String node_name = node.tagName().trim().toLowerCase();
        if(Html_helper.is_tag_deprecated(node_name)){
            current_html_asset.add_to_deprecated_tags(node_name);
        }
    }
    
    private void check_nodes_text(Element node){
        String[] words = Text_helper.split_text_to_individual_words(node.ownText());
        
        for(int i = 0; i < words.length; i++){
            
            // check if it contains a number leave it alone
            if(Text_helper.contains_number(words[i]) == false){
                
                // log if it's an @mention
                if(Text_helper.is_at_mention(words[i])){
                    current_html_asset.add_to_at_mensions(words[i]);
                }
                // log if it's a hash tag
                else if(Text_helper.is_hash_tag(words[i])){
                    current_html_asset.add_to_hash_tags(words[i]);
                }
                // log if it's an email address
                else if(Text_helper.is_email(words[i])){
                    current_html_asset.add_to_emails(words[i]);
                }
                // spell check it if it didn't hit any of the others
                else{
                    spell_check_text(words[i]);
                }
                
            }
            
        }
        
    }
    
    private void spell_check_text(String text){
        String clean_text = spell_checker.clean(text);
        if(spell_checker.is_misspelt(clean_text)){
            current_html_asset.add_to_misspellings(clean_text);
        }
    }
}
