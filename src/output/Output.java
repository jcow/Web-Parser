/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import config.Config;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import template.Template;
import web_parser_project.web_assets.Totals_asset;
import web_parser_project.web_assets.Web_url;

/**
 *
 * @author Jason
 */
public class Output {
    
    protected String new_line;
    protected String out;
    
    public Output(){
        out = "";
        new_line = System.getProperty("line.separator");
    }
    
    public static void do_output(String starting_url, String domain, HashMap<String, Web_url> traveled_sites, Totals_asset totals){
        
        if(Config.is_app_type_gui()){
            // make the file
            Template template = new Template();
            template.create(starting_url, domain, traveled_sites, totals);
            
            // open it
            open_gui();
        }
        else{
            if(Config.is_output_to_database()){
                Database_dump d = new Database_dump();
                d.dump_to_database(starting_url, domain, traveled_sites);
            }
            else if(Config.is_output_to_json_file()){

            }
            else if(Config.is_output_to_html_file()){
                Template template = new Template();
                template.create(starting_url, domain, traveled_sites, totals);
            }
        }
    }
    
    private static void open_gui(){
        try {
            File the_file = new File(Config.get_output_file_string());
            Desktop.getDesktop().browse(the_file.toURI());
        } catch (IOException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected void create_line(String content){
        create_line(content, 0);
    }
    
    protected void create_line(String content, int t){
        out += tab(t)+content+new_line;
    }
    
    protected void create_line(String open, String content, String close, int t){
        out += tab(t)+open+content+close+new_line;
    }
    
    protected static String tab(int how_much){
        String t = "";
        for(int i = 0; i < how_much; i++){
            t += "\t";
        }
        return t;
    }
}
