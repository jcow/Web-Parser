/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Config;

/**
 *
 * @author Jason
 */
public class Config_factory {
    
    public Config_factory(){}
    
    public static Config get_config(String application_type){
        if(Config.is_app_type_gui(application_type)){
            GUI_config.set_application_type(application_type);
            return GUI_config.get_instance();
        }
        else{
            Non_GUI_config.set_application_type(application_type);
            return Non_GUI_config.get_instance();
        }
    }
}
