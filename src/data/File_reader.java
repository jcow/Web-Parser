/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Jason
 */
public class File_reader {
    
    public File_reader(){}
    
    public static LinkedList<String> read_file(String file_to_read){
        
        if(file_to_read == null){
            return null;
        }
        
        LinkedList<String> read_in_lines = new LinkedList();
        BufferedReader br = null;
        
        try{
 
            String current_line;

            br = new BufferedReader(new FileReader(file_to_read));

            while ((current_line = br.readLine()) != null) {
                    read_in_lines.add(current_line);
            }
            
            return read_in_lines;

        } 
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        } 
        finally {
            try{
                if (br != null){
                    br.close();
                }
            } 
            catch (IOException ex) {}
        }
        
        return null;
    }
}
