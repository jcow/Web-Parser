/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jason
 */
public class File_writer {
    
    public File_writer(){
        
    }
    
    /**
     * Either creates and writes to a file, or appends to a file
     * @param filename
     * @param content 
     */
    public static void write_to_file(String filename, String content){
        try {
            
            File file = new File(filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
