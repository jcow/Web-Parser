/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.WebAssets;

/**
 *
 * @author Jason
 */
public class Inline_style {
    
    private String tag;
    private String style;
    
    public Inline_style(String inc_tag, String inc_style){
        tag = inc_tag; 
        inc_style = style;
    }
    
    public String get_tag(){
        return tag;
    }
    
    public String get_style(){
        return style;
    }
}
