/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class Dictionary {
    
    private HashMap<String,String> dictionary;
    
    public Dictionary(){
        dictionary = new HashMap();
    }

    public void read() throws IOException{
            
        LinkedList<String> read_in_dictionary = File_reader.read_file(Config.get_dictionary_location());
       
        if(read_in_dictionary == null){
            throw new IOException("Dictionary cannot be null");
        }
        else{
            convert_linked_list_to_hash_map(read_in_dictionary);
        }
    }
    
    private void convert_linked_list_to_hash_map(LinkedList<String> read_in_dictionary){
        for(String word : read_in_dictionary){
            dictionary.put(word, word);
        }
    }
}
