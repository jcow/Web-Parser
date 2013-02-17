/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jason
 */
public class Text_helperTest {
    
    public Text_helperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of strip_punctuation_from_end method, of class Text_helper.
     */
    @Test
    public void testStrip_punctuation_from_end() {
        System.out.println("strip_punctuation_from_end");
        String[] test_strings = {
            "", "hello", "cat!", "but,", "rick*", "phil,ly", "jim;"
        };
        String[] result_strings = {
            "", "hello", "cat", "but", "rick*", "phil,ly", "jim"
        };
       
        for(int i = 0; i < test_strings.length; i++){
            String result = Text_helper.strip_punctuation_from_end(test_strings[i]);
            assertEquals(result_strings[i], result);
        }
        
    }
    
    /**
     * Test of is_hash_tag( method, of class Text_helper.
     */
    @Test
    public void testIs_hash_tag() {
        System.out.println("Testing the is hash tag function");
        
        String[] test_strings = {
            "", "#yep", "nope"
        };
        boolean[] results = {
            false, true, false
        };
       
        for(int i = 0; i < test_strings.length; i++){
            boolean result = Text_helper.is_hash_tag(test_strings[i]);
            assertEquals(results[i], result);
        }
    }
    
    
    /**
     * Test of is_at_mention method, of class Text_helper.
     */
    @Test
    public void testIs_at_mention() {
        System.out.println("Testing the is at mention function");
        
        String[] test_strings = {
            "", "@yep", "nope"
        };
        boolean[] results = {
            false, true, false
        };
       
        for(int i = 0; i < test_strings.length; i++){
            boolean result = Text_helper.is_at_mention(test_strings[i]);
            assertEquals(results[i], result);
        }
    }
    
    
    
}
