package web_parser_project.web_assets;

/**
 *
 * @author Jason
 */
public class Totals_asset {
    
    private long total_time;
    private int total_misspellings;
    private int total_same_domain_pages;
    private int total_images;
    private int total_urls;
    private int total_same_domain_urls;
    
    
    public Totals_asset(){
        total_time = 0;
        total_images = 0;
        total_urls = 0;
        total_misspellings = 0;
        total_same_domain_pages = 0;
        total_same_domain_urls = 0;
    }
    
    public void add_to_total_time(long t){
        total_time += t;
    }
    
    public void add_to_total_misspellings(int m){
        total_misspellings += m;
    }
    
    public void add_to_total_pages(int t){
        total_same_domain_pages += t;
    }
    
    public void add_to_total_images(int i){
        total_images += i;
    }
    
    public void add_to_total_urls(int u){
        total_urls += u;
    }
    
    public void add_to_total_same_domain_urls(int u){
        total_same_domain_urls += u;
    }
    
    public long get_total_time(){
        return total_time;
    }
    
    public int get_total_misspellings(){
        return total_misspellings;
    }
    
    public int get_total_pages(){
        return total_same_domain_pages;
    }
    
    public int get_total_images(){
        return total_images;
    }
    
    public int get_total_urls(){
        return total_urls;
    }
    
    public int get_total_same_domain_urls(){
        return total_same_domain_urls;
    }
    
}
