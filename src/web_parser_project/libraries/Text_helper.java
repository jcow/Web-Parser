/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;
import org.apache.commons.validator.routines.EmailValidator;


/**
 *
 * @author Jason
 */
public class Text_helper {
    
    private static final String regex_for_text_splitting = "[ ]+";
    private static EmailValidator email_validator = EmailValidator.getInstance();
    
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
    
    public static String remove_punctuation_from_ends(String the_string){
        
        if(the_string == null || the_string.length() == 0){
            return the_string;
        }
        
        int new_start = 0;
        int new_end = the_string.length();
        for(int i = 0; i < the_string.length(); i++){
            if(Character.isLetterOrDigit(the_string.charAt(i)) == true){
                new_start = i;
                break;
            }
        }
        
        for(int p = the_string.length()-1; p >= 0; p--){
            if(Character.isLetterOrDigit(the_string.charAt(p)) == true){
                new_end = p+1;
                break;
            }
        }
        
        return the_string.substring(new_start, new_end);
    }
    
    public static boolean is_hash_tag(String the_string){
        if(the_string != null && the_string.length() > 0 && the_string.charAt(0) == '#'){
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
    
    public static EmailValidator get_email_validator(){
        return Text_helper.email_validator;
    }
}
