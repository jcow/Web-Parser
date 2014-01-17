/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject;

import java.io.IOException;
import WebParserProject.Config.Config;
import WebParserProject.Config.ConfigFactory;
import WebParserProject.Libraries.IncomingArgs;
import WebParserProject.Libraries.SpellChecker;
import WebParserProject.RunTypes.GUI;

/**
 *
 * @author Jason
 */
public class WebParserProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        IncomingArgs inc_args = new IncomingArgs();
        
        /*
        boolean args_ok = check_inc_args(inc_args, args);
        if(args_ok){
            Site_parser site_parser = new Site_parser(inc_args.get_starting_url());
            
            site_parser.run(inc_args.get_starting_url());
        }
        */
        
        String starting_url = "http://localhost/html_files_for_testing/index.html";
        String domain = "http://localhost/html_files_for_testing/";
        
        
        
         // this should be an incoming argument
        String application_type = "gui";
        
        // setup the config
        Config the_config = ConfigFactory.get_config(application_type);
        Config.set_domain(domain);
        the_config.initialize();
        
        
        // setup the dictionary
        SpellChecker sp_chk = SpellChecker.getInstance();
        try{
            sp_chk.read();
        }
        catch(IOException e){
            System.out.println("Reading the dictionary failed");
            System.exit(0);
        }
        
        
        
        if(application_type.equals("gui")){
            GUI the_gui = new GUI();
            the_gui.run();
        }
        else{
            JobDelineator site_parser = new JobDelineator(starting_url, domain);
            site_parser.run();
        }
            
        
        
        
        
        
        
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
    
    public static boolean check_inc_args(IncomingArgs inc_args, String args[]){
        
        try{
            inc_args.set_incoming_args(args);
            return true;
        }
        catch(IllegalArgumentException e){
            return false;
        }
    }
    
}
