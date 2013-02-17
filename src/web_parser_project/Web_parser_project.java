/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import data.Config;
import web_parser_project.libraries.Spell_checker;
import java.io.IOException;
import web_parser_project.libraries.Incoming_args;

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
        
        
        // setup the config
        Config the_config = new Config();
        try{
            the_config.read();
        }
        catch(IOException e){
            System.out.println("Reading the config file failed");
            System.exit(0);
        }
        
        // setup the dictionary
        Spell_checker the_dictionary = Spell_checker.getInstance();
        try{
            the_dictionary.read();
        }
        catch(IOException e){
            System.out.println("Reading the dictionary failed");
            System.exit(0);
        }
        
        
        // args won't be ok because I'm in dev mode, so here is something to kick this off
//        String starting_url = "http://localhost/html_files_for_testing/index.html";
//        String domain = "http://localhost/html_files_for_testing";
        
        String starting_url = "http://lesica.com";
        String domain = "http://lesica.com";
        
        Job_delineator site_parser = new Job_delineator(starting_url, domain);
            
        site_parser.run(starting_url);
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
