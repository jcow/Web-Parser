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
    
    /**
     * Takes in a word that hasn't been formatted and determines if it's misspelt
     * You should probably run the string through the clean function first
     * @param word
     * @return 
     *      Returns true if the word was misspelt
     */
    public String IsMisspelt(String word){
		
        if(	word == null){return null;}
		
		word = Text_helper.trueTrim(word);
		if(word.length() <= 1){return null;}
		
		if(Text_helper.is_number(word) == true){return null;}
		
		char[] chars = {',','"','\'','!','?'};
		word = Text_helper.remove_char_from_end_of_string(word, chars);
		if(Text_helper.is_acronym(word) == true){return null;}
      	
		word = HTMLHelper.get_instance().remove_html_entities(word);
		word = Text_helper.remove_punctuation_from_ends(word);
		
		if(word.length() <= 1){return null;}
		
		if( Text_helper.is_email(word) == true ||
			HTMLHelper.is_url(word)
		){return null;}
		
		word = word.toLowerCase();
		
        //  otherwise, look in the dictionary
        if(dictionary.containsKey(word)){
            return null;
        }  
        else{
            return word;
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
