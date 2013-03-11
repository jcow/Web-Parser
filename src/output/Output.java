/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import data.Config;
import java.util.HashMap;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Output {
    
    public Output(){
        
    }
    
    public static void do_output(String starting_url, String domain, HashMap<String, Web_url> traveled_sites){
        if(Config.is_output_to_database()){
            Database_dump d = new Database_dump();
            d.dump_to_database(starting_url, domain, traveled_sites);
        }
        else if(Config.is_output_to_json_file()){
            
        }
    }
}
