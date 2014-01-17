/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.WebAssets;

import java.io.InputStream;

/**
 *
 * @author Jason
 */
public class OtherAsset extends WebAsset{
    
    protected InputStream content;
    
    public OtherAsset(InputStream inc_content){
        content = inc_content;
    }
    
    public InputStream get_content(){
        return content;
    }
}
