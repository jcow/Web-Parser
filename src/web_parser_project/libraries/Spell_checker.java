/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import data.Config;
import data.File_reader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class Spell_checker {
    
    private HashMap<String,String> dictionary;
    
    private static Spell_checker instance = null;
    private Spell_checker() {
           // Exists only to defeat instantiation.
    }
        
    public static Spell_checker getInstance() {
           if(instance == null) {
              instance = new Spell_checker();
           }
           return instance;
    }
    
    /**
     * Takes in a word that hasn't been formatted and determines if it's misspelt
     * @param text_blob
     * @return 
     *      Returns a linked list of misspelled words or null if none were found
     */
    public boolean is_misspelt(String word){

        word = word.trim();                                     // trim
        word = word.toLowerCase();                              // convert to lowercase
        word = Text_helper.remove_punctuation_from_ends(word);  // remove the punctuation from ends
                
        if(dictionary.containsKey(word)){
            return false;
        }  
        else{
            return true;
        }
    }
    
    
    public void read() throws IOException{
        dictionary = new HashMap();
        
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
            word = word.toLowerCase();
            dictionary.put(word, word);
        }
    }
}
