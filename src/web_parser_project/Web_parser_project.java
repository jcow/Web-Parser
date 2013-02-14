/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

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
        
        // args won't be ok because I'm in dev mode, so here is something to kick this off
        String starting_url = "http://localhost/html_files_for_testing/index.html";
        String domain = "http://localhost/html_files_for_testing";
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
