/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package page_parsing;

import java.util.LinkedList;

/**
 * Used to find inputs with no associated label
 * @author Jason
 */
public class Labels_to_form_elements {
    
    private LinkedList<String> form_elements;
    private LinkedList<String> labels;
    
    public Labels_to_form_elements(){
        form_elements = new LinkedList();
        labels = new LinkedList(); 
   }
    
    
    public void add_to_form_element(String i){
        form_elements.add(i);
    }
    
    public void add_to_labels(String i){
        labels.add(i);
    }
    
    /**
     * Loop though the found inputs and found labels and make sure each input has a label
     * @return LinkedList<String>
     */
    public LinkedList<String> get_difference(){
        
        LinkedList<String> not_found_input_labels = new LinkedList();
        
        boolean found;
        for(String form_element : form_elements){
            System.out.println("-----------------------------");
            System.out.println(form_element);
            found = false;
            for(String label : labels){
                System.out.println("\t"+label);
                if(form_element.equals(label)){
                    found = true;
                    break;
                }
            }
            
            if(found == false){
                not_found_input_labels.add(form_element);
            }
            System.out.println("-----------------------------");
        }
        
        return not_found_input_labels;
    }
}
