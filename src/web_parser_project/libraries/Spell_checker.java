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
    
    public Spell_checker(){
        dictionary = new HashMap();
    }
    
    /**
     * Takes in a sentence, paragraph, section of text and checks if any words are misspelt 
     * @param text_blob
     * @return 
     *      Returns a linked list of misspelled words
     */
    public LinkedList<String> find_spelling_errors(String text_blob){
        
        
        
        return null;
        
    }
    
    /**
     * Parses a section of text based off of the white space and or hyphens between words
     * @param text_blob
     * @return 
     */
    private LinkedList<String> get_blobs_individual_words(String text_blob){
        
        String[] parts = text_blob.split(" ");
        if(parts.length > 0){
            LinkedList<String> misspelt_words;
            for(int i = 0; i < parts.length; i++){
                
                
            }
        }
        
        
        return null;
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
