/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import config.Config;
import config.Config_factory;
import java.awt.Desktop;
import java.io.File;
import output.Database;
import output.Database_dump;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import web_parser_project.libraries.Incoming_args;
import web_parser_project.libraries.Spell_checker;

/**
 *
 * @author Jason
 */
public class Web_parser_project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Incoming_args inc_args = new Incoming_args();
        
        /*
        boolean args_ok = check_inc_args(inc_args, args);
        if(args_ok){
            Site_parser site_parser = new Site_parser(inc_args.get_starting_url());
            
            site_parser.run(inc_args.get_starting_url());
        }
        */
        
        
        // this should be an incoming argument
        String application_type = "dog";
        
        // setup the config
        Config the_config = Config_factory.get_config(application_type);
        the_config.initialize();
        
        
        // setup the dictionary
        Spell_checker sp_chk = Spell_checker.getInstance();
        try{
            sp_chk.read();
        }
        catch(IOException e){
            System.out.println("Reading the dictionary failed");
            System.exit(0);
        }
        
//        Database_dump d = new Database_dump();
//        d.dump_to_database("cat", "dog", null);
        
        
        
        
        
        
        // args won't be ok because I'm in dev mode, so here is something to kick this off
        String starting_url = "http://localhost/html_files_for_testing/";
        String domain = "http://localhost/html_files_for_testing";
        
//        String starting_url = "http://lesica.com";
//        String domain = "http://lesica.com";
        
//        String starting_url = "http://life.umt.edu/curry/";
//        String domain = "http://life.umt.edu/curry";
        
        Job_delineator site_parser = new Job_delineator(starting_url, domain);
            
        site_parser.run();
        
        
        /*
         
         * CODE TO OPEN UP APPLICATION IN A USERS BROWSER!
         
         
         */
        
        
//        String htmlFilePath = "C:/wamp/www/html_files_for_testing/index.html"; // path to your new file
//        File htmlFile = new File(htmlFilePath);
//        try {
//            // open the default web browser for the HTML page
//            Desktop.getDesktop().browse(htmlFile.toURI());
        
//            http://stackoverflow.com/questions/602032/getting-java-gui-to-open-a-webpage-in-web-browser
//            // if a web browser is the default HTML handler, this might work too
////            Desktop.getDesktop().open(htmlFile);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Web_parser_project.class.getName()).log(Level.SEVERE, null, ex);
//        }

        
        
        
    }
    
    public static boolean check_inc_args(Incoming_args inc_args, String args[]){
        
        try{
            inc_args.set_incoming_args(args);
            return true;
        }
        catch(IllegalArgumentException e){
            return false;
        }
    }
}
