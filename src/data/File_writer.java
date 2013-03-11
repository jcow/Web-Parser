/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author Jason
 */
public class File_writer {
    
    public File_writer(){
        
    }
    
    public static void write_to_file(String filename, String contents){
        try{
            // Create file 
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(contents);
            //Close the output stream
            out.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
