/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project.libraries;

import WebParserProject.Config.GUIConfig;
import WebParserProject.Libraries.SpellChecker;
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
public class SpellCheckerTest {
    
    private SpellChecker spChecker;
    
    public SpellCheckerTest() {
        
        // setup the config
        GUIConfig theConfig = GUIConfig.get_instance();
		theConfig.initialize();
        
		// setup the spell checker
        spChecker = SpellChecker.getInstance();
        
        try{
            spChecker.read();
        }
        catch(IOException e){
            fail("An io exception was thown when reading the spell checker: "+e.getMessage());
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
     * Test of IsMisspelt method, of class Spell_checker.
     */
    @Test
    public void testIs_misspelt() {
		System.out.println("TestIsMisspelt");
        assertNull(spChecker.IsMisspelt("aardvark"));
		assertNull(spChecker.IsMisspelt("AAA"));
		assertNull(spChecker.IsMisspelt("a.m."));
		assertNull(spChecker.IsMisspelt("a.m. "));
		assertTrue(spChecker.IsMisspelt("a.m.&nbsp;") != null);
		
        
        
    }

    /**
     * Test of read method, of class Spell_checker.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        spChecker = SpellChecker.getInstance();
		assertFalse(spChecker == null);
    }
}
