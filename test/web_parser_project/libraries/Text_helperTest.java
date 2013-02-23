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
     * Test of remove_punctuation_from_ends method, of class Text_helper.
     */
    @Test
    public void testRemove_punctuation_from_ends() {
        System.out.println("remove_punctuation_from_ends");
        String[] test_strings = {
            "", "hello", "cat!", "but,", "rick*", "phil,ly", "jim;", "!a%", ":&*^%$"
        };
        String[] result_strings = {
            "", "hello", "cat", "but", "rick", "phil,ly", "jim", "a", ""
        };
       
        for(int i = 0; i < test_strings.length; i++){
            System.out.println("\tTesting "+test_strings[i]);
            String result = Text_helper.remove_punctuation_from_ends(test_strings[i]);
            assertEquals("The string at index "+i+" had an issue", result_strings[i], result);
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
            assertEquals("The item at index "+i+"failed", results[i], result);
        }
    }

    /**
     * Test of split_text_to_individual_words method, of class Text_helper.
     */
    @Test
    public void testSplit_text_to_individual_words() {
        System.out.println("Testing the split text into individual words function");
        assertNull(Text_helper.split_text_to_individual_words(null));
        
        String input1 = "Hello,  John; 'I cats-meow frank/john /jim";
        String[] result1 = {"Hello,", "John;", "'I", "cats", "meow", "frank", "john", "", "jim"};
        String[] actual1 = Text_helper.split_text_to_individual_words(input1);
        
        
        String input2 = "---";
        String[] result2 = {};
        String[] actual2 = Text_helper.split_text_to_individual_words(input2);
        
        assertArrayEquals(result2, actual2);
    }

    /**
     * Test of is_email method, of class Text_helper.
     */
    @Test
    public void testIs_email() {
        
        assertEquals(false, Text_helper.is_email(null));
        
        assertEquals(false, Text_helper.is_email(""));
        
        assertEquals(true, Text_helper.is_email("jason.cowan@mso.umt.edu"));
        
        assertEquals(false, Text_helper.is_email("-"));
        
        assertEquals(false, Text_helper.is_email("-hi"));
        
        assertEquals(false, Text_helper.is_email("@mentioningsomething"));
        
        
    }
    
    
    
}
