/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

/**
 *
 * @author Jason
 */
public class Incoming_args {
    
    private int expected_arg_count = 1;
    private String starting_url = "";
    
    public Incoming_args(){}
    
    public void set_incoming_args(String args[]){
        if(expected_arg_count != args.length){
            throw new IllegalArgumentException("Arg counts must match");
        }
        
        if(Html_helper.is_http_address(args[0])){
            throw new IllegalArgumentException("Incoming address must be http protocol");
        }
        
        starting_url = args[0];
        
    }
    
    public String get_starting_url(){
        return starting_url;
    }
}
