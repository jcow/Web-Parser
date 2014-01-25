/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Libraries;

import WebParserProject.Config.Config;
import WebParserProject.Data.ProjectFileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class SpellChecker {
    
	/*
	 *	Other dictionary sources
	 *  http://www.gutenberg.org/files/29765/29765-8.txt
	 */
    private HashMap<String,String> dictionary;
    
    private static SpellChecker instance = null;
    private SpellChecker() {
           // Exists only to defeat instantiation.
    }
        
    public static SpellChecker getInstance() {
           if(instance == null) {
              instance = new SpellChecker();
           }
           return instance;
    }
    
    public String clean(String word){
        word = word.trim();                                     // trim
        word = Text_helper.remove_punctuation_from_ends(word);  // remove the punctuation from ends
        
        return word;
    }
    
    /**
     * Takes in a word that hasn't been formatted and determines if it's misspelt
     * You should probably run the string through the clean function first
     * @param word
     * @return 
     *      Returns true if the word was misspelt
     */
    public boolean IsMisspelt(String word){

        // empty, leave it
        if(word == null || word.length() == 0){return false;}
      
        // make it lower case
        word = word.toLowerCase();
        
        // all puncuation, now empty
        // or it's a number
        // or if it's all upper-case
        if(word.length() == 0 || Text_helper.is_number(word) || Text_helper.is_acronym(word)){
            return false;
        }
        
        //  otherwise, look in the dictionary
        if(dictionary.containsKey(word)){
            return false;
        }  
        else{
            return true;
        }
    }
    
    
    public void read() throws IOException{
        dictionary = new HashMap();
        
        LinkedList<String> read_in_dictionary = ProjectFileReader.read_file(Config.get_dictionary_location());
        
        
        
        if(read_in_dictionary == null){
            throw new IOException("Dictionary cannot be null");
        }
        else{
            ConvertLinkedListToHashMap(read_in_dictionary);
            
            // check and set custom dictionary
            if(Config.get_custom_dictionary_location() != null){
                LinkedList<String> custom_dict = ProjectFileReader.read_file(Config.get_custom_dictionary_location());
                ConvertLinkedListToHashMap(custom_dict);
            }
        }
    }
    
    
    private void ConvertLinkedListToHashMap(LinkedList<String> read_in_dictionary){
        for(String word : read_in_dictionary){
            word = word.trim().toLowerCase();
            dictionary.put(word, word);
        }
    }
}
