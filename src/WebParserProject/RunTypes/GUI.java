/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebParserProject.RunTypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import WebParserProject.JobDelineator;

/**
 *
 * @author Jason
 */
public class GUI {
    
    private String starting_url;
    private String domain;
    
    
    public GUI(){}
    
    public void run(){
        
        boolean is_good = false;
        
        System.out.println("Good "+get_salutation()+"!\n");
        while(is_good == false){
        
            Scanner in = new Scanner(System.in);

            System.out.println("Please enter in the domain in which you would like to explore - e.g. http://www.example.com");
            domain = in.nextLine();

            System.out.println("Please enter in the URL in which you would like to start from - e.g. http://www.example.com");
            starting_url = in.nextLine();
            
            is_good = check_input();
        }
        
        JobDelineator site_parser = new JobDelineator(starting_url, domain);
        site_parser.run();
        
    }
    
    private boolean check_input(){
        if(domain.contains("http") && starting_url.contains("http")){
            if(starting_url.contains(domain) == false){
                System.out.println("The starting url must be equal to or contain the domain");
                System.out.println("VALID - Domain: http://www.example.com Starting URL: http://example.com");
                System.out.println("VALID - Domain: http://www.example.com Starting URL: http://example.com/mypage.html");
                System.out.println("INVALID - Domain: http://www.example.com/examples Starting URL: http://example.com");
                return false;
            }
            else{
                return true;
            }
        }
        else{
            System.out.println("The Domain and Starting URL must contain a valid URL, like http://....");
        }
        
        return false;
    }
    
    private String get_salutation(){
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        
        int hours = Integer.parseInt(dateFormat.format(date));
        
        if(hours < 12){
            return "morning";
        }
        else if(hours < 18){
            return "afternoon";
        }
        else{
            return "evening";
        }
    }
    
    
}