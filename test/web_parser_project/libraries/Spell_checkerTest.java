/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import WebParserProject.Libraries.Spell_checker;
import config.Config;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jason
 */
public class Spell_checkerTest {
    
    private Spell_checker instance;
    
    public Spell_checkerTest() {
        
        // setup the config
        Config the_config = new Config();
        try{
            the_config.read();
        }
        catch(IOException e){
            fail("An io exception was thown when reading the config: "+e.getMessage());
        }
        
        
        instance = Spell_checker.getInstance();
        
        try{
            instance.read();
        }
        catch(IOException e){
            fail("An io exception was thown when reading: "+e.getMessage());
        }
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
     * Test of getInstance method, of class Spell_checker.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Spell_checker expResult = null;
        Spell_checker result = Spell_checker.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of is_misspelt method, of class Spell_checker.
     */
    @Test
    public void testIs_misspelt() {
        System.out.println("is_misspelt");
        
        assertEquals(false, instance.is_misspelt("aardvark"));
        
        
    }

    /**
     * Test of read method, of class Spell_checker.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        Spell_checker instance = null;
        instance.read();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
