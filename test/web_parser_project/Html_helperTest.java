/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_parser_project;

import WebParserProject.Libraries.HTMLHelper;
import java.net.URLConnection;
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
public class Html_helperTest {
    
    public Html_helperTest() {
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
     * Test of get_instance method, of class Html_helper.
     */
    @Test
    public void testGet_instance() {
        System.out.println("get_instance");
        HTMLHelper expResult = null;
        HTMLHelper result = HTMLHelper.get_instance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of is_http_address method, of class Html_helper.
     */
    @Test
    public void testIs_http_address() {
        System.out.println("is_http_address");
        String address = "";
        boolean expResult = false;
        boolean result = HTMLHelper.is_http_address(address);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of is_same_domain method, of class Html_helper.
     */
    @Test
    public void testIs_same_domain() {
        System.out.println("is_same_domain");
        String base_url = "";
        String test_url = "";
        boolean expResult = false;
        boolean result = HTMLHelper.is_same_domain(base_url, test_url);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of strip_page_anchor method, of class Html_helper.
     */
    @Test
    public void testStrip_page_anchor() {
        System.out.println("strip_page_anchor");

        assertEquals("http://life.umt.edu", HTMLHelper.strip_page_anchor("http://life.umt.edu"));
        
        assertEquals("http://life.umt.edu", HTMLHelper.strip_page_anchor("http://life.umt.edu#happy_happy_joy_joy"));
        
        assertEquals(null, HTMLHelper.strip_page_anchor(null));
        
    }
    
    /**
     * Test of strip_end_slash method, of class Html_helper.
     */
    @Test
    public void testStrip_end_slash() {
        System.out.println("strip_end_slash");

        assertEquals(null, HTMLHelper.strip_end_slash(null));
        
        assertEquals("", HTMLHelper.strip_end_slash(""));
        
        assertEquals("http://life.umt.edu", HTMLHelper.strip_end_slash("http://life.umt.edu"));
        
        assertEquals("http://life.umt.edu", HTMLHelper.strip_end_slash("http://life.umt.edu/"));
        
        
        
    }
    
    

    /**
     * Test of is_404 method, of class Html_helper.
     */
    @Test
    public void testIs_404() {
        System.out.println("is_404");
        int status = 0;
        boolean expResult = false;
        boolean result = HTMLHelper.is_404(status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of is_200 method, of class Html_helper.
     */
    @Test
    public void testIs_200() {
        System.out.println("is_200");
        int status = 0;
        boolean expResult = false;
        boolean result = HTMLHelper.is_200(status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Tests whether or not a url is a valid url
     */
    @Test
    public void testGet_extension(){
        
        String com = "http://mysite.com";
        assertEquals("com", HTMLHelper.get_extension(com));
        
        String com2 = "http://mysite.com/noextension";
        assertEquals("com", HTMLHelper.get_extension(com2));
        
        String image = "http://mysite.com/some_picture.png";
        assertEquals("png", HTMLHelper.get_extension(image));
        
        String nada = "asdfasdfadsfdas";
        assertEquals(null, HTMLHelper.get_extension(nada));
        
    }
    
    
    
    /**
     * Tests whether or not a url is a valid url
     */
    @Test
    public void testIs_non_html_url(){
        
        String normal = "http://mysite.com";
        assertEquals(false, HTMLHelper.is_non_html_url(normal));
        
        String image = "http://mysite.com/some_picture.png";
        assertEquals(true, HTMLHelper.is_non_html_url(image));
        
        String video = "http://mysite.com/some_video.mp3";
        assertEquals(true, HTMLHelper.is_non_html_url(video));
        
    }
}
