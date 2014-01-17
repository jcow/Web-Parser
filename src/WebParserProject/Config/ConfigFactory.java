/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.Config;

/**
 *
 * @author Jason
 */
public class ConfigFactory {
    
    public ConfigFactory(){}
    
    public static Config get_config(String application_type){
        if(Config.is_app_type_gui(application_type)){
            GUIConfig.set_application_type(application_type);
            return GUIConfig.get_instance();
        }
        else{
            NonGUIConfig.set_application_type(application_type);
            return NonGUIConfig.get_instance();
        }
    }
}
