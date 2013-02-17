/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

/**
 *
 * @author Jason
 */
public class Text_helper {
    
    private static final String regex_for_text_splitting = "[ ]+";
    private static final char[] end_of_word_punctuation_chars = {';', ',', '.', '?', '!', '\'', '"', ')', '(', ':'};
    
    public Text_helper(){}
    
    /**
     * Splits a string into individual words based on a specific set of characters
     * @return 
     */
    public static String[] split_text_to_individual_words(String the_string){
        if(the_string != null){
            return the_string.split(regex_for_text_splitting);
        }
        else{
            return null;
        }
    }
    
    public static String strip_punctuation_from_end(String the_string){
        if(the_string.length() > 0){
            
            for(char character : end_of_word_punctuation_chars){
                if(the_string.charAt(the_string.length()-1) == character){
                    return the_string.substring(0, the_string.length()-1);
                }
            }
            
            return the_string;
        }
        else{
            return the_string;
        }
    }
    
    public static boolean is_hash_tag(String the_string){
        if(the_string.length() > 0 && the_string.charAt(0) == '#'){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_at_mention(String the_string){
        if(the_string.length() > 0 && the_string.charAt(0) == '@'){
            return true;
        }
        else{
            return false;
        }
    }
}
