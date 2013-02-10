/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.web_assets;

/**
 *
 * @author Jason
 */
public class Error_asset extends Web_asset{
    
    protected boolean io_error;
    protected boolean malformed_url;
    
    public Error_asset(String url){
        super(url);
        
        io_error = false;
        malformed_url = false;
    }
    
    public void set_io_error(boolean inc_error){
        io_error = inc_error;
    }
    
    public void set_malformed_url(boolean inc_mal_url){
        malformed_url = inc_mal_url;
    }
    
    public boolean is_io_error(){
        return io_error;
    }
    
    public boolean is_malformed_url(){
        return malformed_url;
    }
}
