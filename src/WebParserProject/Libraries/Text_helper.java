/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Libraries;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;


/**
 *
 * @author Jason
 */
public class Text_helper {
    
    private static final String regex_for_text_splitting = "[ ]+|-|/";
    
    public Text_helper(){}
    
    /**
     * Splits a string into individual words based on a specific set of characters    private static EmailValidator email_validator = EmailValidator.getInstance();

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
    
    public static String remove_punctuation_from_ends(String the_string){
        
        if(the_string == null || the_string.length() == 0){
            return the_string;
        }
        
        int new_start = 0;
        for(int i = 0; i < the_string.length(); i++){
            if(Character.isLetterOrDigit(the_string.charAt(i)) == false){
                new_start = i+1;
            }
            else{
                break;
            }
        }
        
        // it's all puncuation
        if(new_start == the_string.length()){
            return "";
        }
        
        int new_end = the_string.length();
        for(int p = the_string.length()-1; p >= 0; p--){
            if(Character.isLetterOrDigit(the_string.charAt(p)) == false){
                new_end = p;
            }
            else{
                break;
            }
        }
        
        return the_string.substring(new_start, new_end);
    }
    
    public static boolean is_hash_tag(String the_string){
        if(the_string != null && the_string.length() > 1 && the_string.charAt(0) == '#' && StringUtils.isAlphanumeric(the_string.substring(1))){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_at_mention(String the_string){
        if(the_string != null && the_string.length() > 0 && the_string.charAt(0) == '@'){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_email(String the_string){
        if(the_string != null && the_string.length() > 1){
            
            if(Text_helper.get_email_validator().isValid(the_string)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    private static EmailValidator get_email_validator(){
        return EmailValidator.getInstance();
    }
    
    public static boolean is_acronym(String word){
        if(StringUtils.isAllUpperCase(word)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean is_number(String word){
        try  
        {  
           double d = Double.parseDouble(word);  
           return true;
        }  
        catch(NumberFormatException nfe){
            return false;
        } 
    }
    
    /**
     * Looks at a word and determines if it contains a number or not
     * @return 
     */
    public static boolean contains_number(String word){
        
        for(int i = 0; i < word.length(); i++){
            if(Character.isDigit(word.charAt(i))){
                return true;
            }
        }
        
        return false;
    }
}
