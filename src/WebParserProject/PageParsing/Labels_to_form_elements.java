/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.PageParsing;

import java.util.LinkedList;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import WebParserProject.Libraries.Html_helper;
import WebParserProject.WebAssets.WebAssetsHTML.Form_element;

/**
 * Used to find inputs with no associated label
 * @author Jason
 */
public class Labels_to_form_elements {
    
    private LinkedList<Element> form_elements;
    private LinkedList<String> labels;
    
    public Labels_to_form_elements(){
        form_elements = new LinkedList();
        labels = new LinkedList(); 
   }
    
    
    public void add_to_form_element(Element n){
        form_elements.add(n);
    }
    
    public void add_to_labels(String i){
        labels.add(i);
    }
    
    /**
     * Loop though the found inputs and found labels and make sure each input has a label
     * @return LinkedList<String>
     */
    public LinkedList<Form_element> get_difference(){
        
        LinkedList<Form_element> not_found_input_labels = new LinkedList();
        
        boolean found;
        for(Element form_element : form_elements){
            
            String element_tag = Html_helper.get_tag_name(form_element);
            String element_name = form_element.attr("name");
            String element_id = form_element.attr("id");
            
            found = false;
            for(String label : labels){
                if(element_id.equals(label)){
                    found = true;
                    break;
                }
            }
            
            if(found == false){
                not_found_input_labels.add(new Form_element(element_tag, element_name, element_id));
            }
        }
        
        return not_found_input_labels;
    }
}
