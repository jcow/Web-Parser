html_parser.charts_page = {
    
    page_container_id:'charts_page',
    indented_tree_container_id:'charts_page_indented_tree',
    have_been_built:false,
    
    show:function(){
        if(this.have_been_built == false){
            this._build();
            this.have_been_built = true;
        }
    },
    
    get_page_container_id:function(){
        return this.page_container_id;
    },
    
    _build:function(){
        
        
        //todo - get the system to set the width and height based on the number of nodes and child nodes
        //also, for large sets make sure that the indentation stays 
        
        
        // this is the best
        // http://bl.ocks.org/mbostock/raw/1093025/
        

         
        this._build_indented_tree();
    },
    
    _build_indented_tree:function(){
        
        var left_margin = 30;
        
        var the_data = this._make_url_tree();
        
        var tree_data = the_data.nodes;
        var node_count = the_data.node_count;
        var depth = the_data.depth;
        var html = "<h2>Indented Tree of Site Traversal</h2>";
        html += "<p>The following graph is path that the parser took through the site.</p>";
        var parse = function(node, d){
            html += "<div class='indented_tree_node' style='margin-left:"+(left_margin*d)+"px;'>"+node.name+"</div>";
            for(var i in node.children){
                parse(node.children[i], d+1);
            }
        }
        parse(tree_data, 1)
        this._get_charts_page_indented_tree_container().html(html);
    },
    
    _get_container:function(){
        return $('#'+this.page_container_id);
    },
    
    _get_charts_page_indented_tree_container:function(){
        return $('#'+this.indented_tree_container_id);
    },
    
    
    _make_url_tree:function(){
        var urls = html_parser.urls.get_all_urls();
		
        var node_count = urls.length;
        var depth = 0;
        var result = {};
	var start = '';
        
        for(var i in urls){

            // this current node has not been created yet
            if(typeof result[urls[i].url] === 'undefined'){
                    result[urls[i].url] = {
                            url:urls[i].url,
                            children:[]
                    }
            }

            // do i have a parent			
            if(urls[i].direct_parent !== null && urls[i].direct_parent !== ''){

                    // has my parent been defined
                    if(typeof result[urls[i].direct_parent] === 'undefined'){
                            result[urls[i].direct_parent] = {
                                    url:urls[i].direct_parent,
                                    children:[]
                            }
                    }

                    // add me as a child to the parent
                    result[urls[i].direct_parent].children.push(urls[i].url)
            }
            else{
                    start = urls[i].url
            }			
        }

        var parse = function(node, d){
            if(d > depth){
                depth = d;
            }
            
            var new_obj = {}
            new_obj.name = node.url;
            new_obj.children = [];
            for(var i in node.children){
                new_obj.children.push(parse(result[node.children[i]], d+1));
            }
            
            return new_obj;
        }

        var new_result = parse(result[start], 1);
        
        return {nodes:new_result, node_count:node_count, depth:depth}
        
    }
    
}
