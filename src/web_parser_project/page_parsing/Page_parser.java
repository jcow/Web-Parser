/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.page_parsing;

import java.util.List;
import java.util.ListIterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import web_parser_project.libraries.Html_accessibility_helper;
import web_parser_project.libraries.Html_helper;
import web_parser_project.libraries.Html_meta_helper;
import web_parser_project.libraries.Spell_checker;
import web_parser_project.libraries.Text_helper;
import web_parser_project.web_assets.Html_asset;
import web_parser_project.web_assets.Parse_asset;
import web_parser_project.web_assets.Totals_asset;
import web_parser_project.web_assets.Web_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Page_parser {
    
    private Html_asset current_html_asset;
    private Parse_asset parse_asset;
    private Spell_checker spell_checker;
    private Html_helper html_helper;
    private Html_accessibility_helper accessibility_helper;
    private Labels_to_form_elements label_to_form_element;
    private String[] tags_to_ignore;
    
    public Page_parser(Parse_asset p_asset){
        spell_checker = Spell_checker.getInstance();
        html_helper = Html_helper.get_instance();
        accessibility_helper = Html_accessibility_helper.get_instance();
        parse_asset = p_asset;
    }
    
    public void parse(Web_url w_url){
        
        if(Web_asset.is_html_asset(w_url.get_web_asset())){
            current_html_asset = (Html_asset)w_url.get_web_asset();
            label_to_form_element = new Labels_to_form_elements();
            
            parse_document();
            
            check_labels_to_input();
        }
    }
    
    public void parse_document(){
        Document page_document = current_html_asset.get_contents();
        
        parse_doctype(page_document);
        parse_head(page_document, page_document.head());
        parse_body(page_document.body());
    }
    
    public void parse_doctype(Document page_document){
        
        List<Node> first_nodes = page_document.childNodes();
        
        // loop through highest nodes to find the doctype
        if(first_nodes.isEmpty() == false){
            for (Node node : first_nodes) {
                if (Html_helper.is_node_doctype(node)) {
                    DocumentType document_type = (DocumentType)node;
                    current_html_asset.set_doctype(document_type.toString());
                }
            }
        }
        
        
    }
    
    public void parse_head(Document page_document, Element head){
        
        // set the title
        current_html_asset.set_title(page_document.title());
        
        parse_head_nodes(head.children());
    }
    
    public void parse_head_nodes(Elements nodes){
        for(Element node : nodes){
            if(Html_meta_helper.is_element_meta_description(node)){
               current_html_asset.set_description(Html_meta_helper.get_meta_content(node));
            }
        }
    }
    
    public void parse_body(Element body){
        parse_body_nodes(body.children());
    }
    
    
    /**
     * Recurses a set of nodes and gets their children
     * @param nodes
     * @param tab 
     */
    public void parse_body_nodes(Elements nodes){
        
        if(nodes.isEmpty() == false){
            ListIterator<Element> iterator = nodes.listIterator();
            Element node;
            while(iterator.hasNext()){
                node = iterator.next();
                
                if(is_ignore_node(node) == false){
                
                    // check if the node is deprecated
                    check_if_node_is_deprecated(node);

                    // check if the node contains inline styling
                    check_if_node_contains_inline_styling(node);

                    // if the node is an image
                    if(Html_helper.is_node_image(node)){
                        check_image_node(node);
                    }

                    // if the node is a link
                    if(Html_helper.is_node_anchor(node)){
                        check_anchor(node.text());
                    }

                    // if the node is an form element that should have an associated label
                    if(Html_helper.should_node_have_associated_label(node)){
                        label_to_form_element.add_to_form_element(node.attr("id")); 
                    }
                    // if the node is a label
                    else if(Html_helper.is_node_label(node)){
                        label_to_form_element.add_to_labels(node.attr("for"));
                    }

                    // check the text of the node
                    check_text(Text_helper.split_text_to_individual_words(node.ownText()));

                    // parse out the children of this node
                    parse_body_nodes(node.children());
                }
            }
        }
        
    }
    
    private void check_if_node_contains_inline_styling(Element node){
        if(html_helper.contains_inline_styling(node)){
            current_html_asset.add_to_inline_styling(html_helper.get_tag_name(node), html_helper.get_style_value(node));
            String p = html_helper.get_style_value(node);
            String q = Html_helper.get_tag_name(node);
        }
    }
    
    private void check_if_node_is_deprecated(Element node){
        String node_name = Html_helper.get_tag_name(node);
        if(html_helper.is_tag_deprecated(node_name)){
            current_html_asset.add_to_deprecated_tags(node_name);
        }
    }
    
    private void check_text(String[] words){
        
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
            parse_asset.add_to_total_misspellings(1);
            current_html_asset.add_to_misspellings(clean_text);
        }
    }
    
    private void check_image_node(Element node){
        // check if there is any alt text
        String alt_text = node.attr("alt");
        
        // no alt text
        if(alt_text.equals("")){
           current_html_asset.add_to_no_alt_text(node.attr("abs:src"));
        }
        // spell check alt text
        else{
           check_text(Text_helper.split_text_to_individual_words(alt_text));
        }
    }
    
    private void check_anchor(String text){
        if(Html_accessibility_helper.is_poor_link_name(text)){
            current_html_asset.add_to_poor_link_naming(text);
        }
    }
    
    private void check_labels_to_input(){
        current_html_asset.set_inputs_no_labels(label_to_form_element.get_difference());
    }
    
    private boolean is_ignore_node(Element node){
         String node_name = Html_helper.get_tag_name(node).toLowerCase();
         
         if(node_name.equals("script") || node_name.equals("style") || node_name.equals("code")){
             return true;
         }
         else{
             return false;
         }
    }
}
