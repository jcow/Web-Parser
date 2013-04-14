/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import data.File_reader;
import java.util.LinkedList;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import config.Config;
import config.Non_GUI_config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import web_parser_project.web_assets.Totals_asset;
import web_parser_project.web_assets.Web_url;


/**
 *
 * @author Jason
 */
public class Template {
    
    private String output_location;
    
    private LinkedList<String> template;
    private String output;
    
    public Template(){
        
    }
    
    public void create(String template_location, String output_location, String starting_url, String domain, HashMap<String, Web_url> urls, Totals_asset totals){
        
        MustacheFactory mf = new DefaultMustacheFactory();
        try {
            
            Mustache mustache = mf.compile(new FileReader(template_location), "blllla");
            
            File file = new File(output_location);

            // if file doesnt exists, then create it
            if (!file.exists()) { 
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            mustache.execute(fw, new Web_url_template(starting_url, domain, urls, totals)).flush();
        } 
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
