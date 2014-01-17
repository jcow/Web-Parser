/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Template;

import WebParserProject.Data.ProjectFileReader;
import java.util.LinkedList;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import WebParserProject.Config.Config;
import WebParserProject.Config.NonGUIConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import WebParserProject.WebAssets.ParseAsset;
import WebParserProject.WebAssets.TotalsAsset;
import WebParserProject.WebAssets.Web_url;


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
    
    public void create(String template_location, String output_location, ParseAsset parse_asset){
        
        MustacheFactory mf = new DefaultMustacheFactory();
        try {
            Mustache mustache = mf.compile(new FileReader(template_location), "blllla");
            
            File file = new File(output_location);

            // if file doesnt exists, then create it
            if (!file.exists()) { 
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            mustache.execute(fw, new WebURLTemplate(parse_asset)).flush();
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
