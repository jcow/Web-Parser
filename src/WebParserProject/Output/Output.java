/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Output;

import WebParserProject.Config.Config;
import WebParserProject.Config.GUIConfig;
import WebParserProject.Config.NonGUIConfig;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import WebParserProject.Template.Template;
import WebParserProject.WebAssets.Parse_asset;
import WebParserProject.WebAssets.Totals_asset;
import WebParserProject.WebAssets.Web_url;

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
    
    public static void do_output(Parse_asset parse_asset){
        
        if(Config.is_app_type_gui()){
            // make and write to the file
            Template template = new Template();
            template.create(GUIConfig.get_front_end_template_location(), GUIConfig.get_front_end_dump_location(), parse_asset);
            
            // open up the html file
            open_gui();
        }
        else{
//            if(Non_GUI_config.is_output_to_database()){
//                Database_dump d = new Database_dump();
//                d.dump_to_database(starting_url, domain, traveled_sites);
//            }
//            else if(Non_GUI_config.is_output_to_json_file()){
//
//            }
//            else if(Non_GUI_config.is_output_to_html_file()){
//                Template template = new Template();
//                //template.create(starting_url, domain, traveled_sites, totals);
//            }
        }
    }
    
    private static void open_gui(){
        try {
            File the_file = new File("front_end/main.html");
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
