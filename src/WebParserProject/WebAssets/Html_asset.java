/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.WebAssets;

import java.util.LinkedList;
import org.jsoup.nodes.Document;
import WebParserProject.WebAssets.WebAssetsHTML.Form_element;

/**
 *
 * @author Jason
 */
public class Html_asset extends Web_asset{
    
    protected String doc_type;
    protected String title;
    protected String description;
    protected Document contents;
    protected LinkedList<String> misspellings;
    protected LinkedList<String> at_mentions;
    protected LinkedList<String> hash_tags;
    protected LinkedList<String> emails;
    protected LinkedList<String> deprecated_tags;
    protected LinkedList<Inline_style> inline_styling;
    
    // accessibility
    protected LinkedList<String> no_alt_text;
    protected LinkedList<String> poor_link_naming;
    protected LinkedList<Form_element> inputs_no_labels;
    
    public Html_asset(Document inc_contents){
        contents = inc_contents;
        misspellings = new LinkedList();
        at_mentions = new LinkedList();
        hash_tags = new LinkedList();
        emails = new LinkedList();
        deprecated_tags = new LinkedList();
        inline_styling = new LinkedList();
        no_alt_text = new LinkedList();
        poor_link_naming = new LinkedList();
        inputs_no_labels = new LinkedList();
    }
    
    public void set_doctype(String doc){
        doc_type = doc;
    }
    
    public String get_doctype(){
        return doc_type;
    }
    
    public void set_title(String t){
        title = t;
    }
    
    public String get_title(){
        return title;
    }
    
    public String get_description(){
        return description;
    }
    
    public Document get_contents(){
        return contents;
    }
    
    public void set_description(String desc){
        description = desc;
    }
    
    public void add_to_no_alt_text(String n){
        no_alt_text.add(n);
    }
    
    public void add_to_poor_link_naming(String n){
        poor_link_naming.add(n);
    }
    
    public void add_to_inline_styling(String tag_name, String style_value){
        inline_styling.add(new Inline_style(tag_name, style_value));
    }
    
    public void add_to_at_mensions(String at_mention){
        if(at_mention != null){
            at_mentions.add(at_mention);
        }
    }
    
    public void add_to_hash_tags(String hashtag){
        if(hashtag != null){
            hash_tags.add(hashtag);
        }
    }
    
    public void add_to_emails(String email){
        if(email != null){
            emails.add(email);
        }
    }
    
    public void add_to_misspellings(String misspelling){
        if(misspelling != null){
            misspellings.add(misspelling);
        }
    }
    
    public void add_to_deprecated_tags(String tag){
        if(tag != null){
            deprecated_tags.add(tag);
        }
    }
    
    public void add_to_misspellings(LinkedList<String> incoming_misspellings){
        if(incoming_misspellings != null){
            misspellings.addAll(incoming_misspellings);
        }
    }
    
    public void set_inputs_no_labels(LinkedList<Form_element> inc_inputs_no_labels){
        inputs_no_labels = inc_inputs_no_labels;
    }
    
    public LinkedList<String> get_misspellings(){
        return misspellings;
    }
    
    public LinkedList<String> get_at_mentions(){
        return at_mentions;
    }
    
    public LinkedList<String> get_emails(){
        return emails;
    }
    
    public LinkedList<String> get_hash_tags(){
        return hash_tags;
    }
    
    public LinkedList<String> get_deprecated_tags(){
        return deprecated_tags;
    }
    
    public LinkedList<String> get_no_alt_text(){
        return no_alt_text;
    }
    
    public LinkedList<String> get_poor_link_naming(){
        return poor_link_naming;
    }
    
    public LinkedList<Form_element> get_inputs_no_labels(){
        return inputs_no_labels;
    }
    
}
