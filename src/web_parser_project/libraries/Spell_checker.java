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
     * Takes in a sentence, paragraph, section of text and checks if any words are misspelt 
     * @param text_blob
     * @return 
     *      Returns a linked list of misspelled words or null if none were found
     */
    public LinkedList<String> find_spelling_errors(String text_blob){

        LinkedList<String> misspellings = null;
        String[] words = Text_helper.split_text_to_individual_words(text_blob);
        
        if(words.length != 0){
            
            for(int i = 0; i < words.length; i++){
                if(is_misspelt(words[i])){
                    
                    // lazy instantiation
                    if(misspellings == null){
                        misspellings = new LinkedList();
                    }
                    
                    misspellings.add(words[i]);
                }
            }
            
            return misspellings;
        }
        else{
            return null;
        }
    }
    
    public boolean is_misspelt(String word){
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
            dictionary.put(word, word);
        }
    }
}
