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
    protected LinkedList<String> no_alt_text;
    
    public Html_asset(Document inc_contents){
        contents = inc_contents;
        misspellings = new LinkedList();
        at_mentions = new LinkedList();
        hash_tags = new LinkedList();
        emails = new LinkedList();
        deprecated_tags = new LinkedList();
        inline_styling = new LinkedList();
        no_alt_text = new LinkedList();
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
    
}
