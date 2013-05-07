html_parser.urls_page = {
    
    page_container_id:'urls_page',
    where_to_place_table_id:'urls_results',
    table_id:'url_search_table',
    
    has_been_built:false,
    
    show:function(){
      if(this.has_been_built == false){
          this._initial_build();
          this.has_been_built = true;
      }  
    },
    
    get_page_container_id:function(){
        return this.page_container_id;
    },
    
    _initial_build:function(){
        this._bind_search();
        
        this._handle_search();
    },
    
    _bind_search:function(){
        var self = this;
        $('#search_button').click(function(e){
            self._handle_search();
        });
        $('.url_search_box').keypress(function(event) {
        if (event.keyCode == 13) {
            self._handle_search();
        }
    });
    },
    
    _handle_search:function(){
        var urls = $('#search_urls').val();
        var http_codes = $('#search_http_codes').val();
        var content_types = $('#search_content_types').val();
        var document_types = $('#search_document_types').val();
        var errors = $('#search_errors').is(':checked');
        var inline_styling = $('#search_inline_styling').is(':checked');
        var deprecated_tags = $('#search_deprecated_tags').is(':checked');
        
        if(http_codes !== ''){
            http_codes = http_codes.split(',');
            for(var i in http_codes){
                // trim white space
                http_codes[i] = http_codes[i].replace(/^\s+|\s+$/g,'');
                
                // convert to int
                http_codes[i] = parseInt(http_codes[i]);
            }
        }
        else{
            http_codes = [];
        }
        
        
        var results = html_parser.urls.search(urls, http_codes, content_types, document_types, errors, inline_styling, deprecated_tags);
        results = this._search_to_human(results);
        this._get_where_to_place_table().html(Mustache.render(html_parser.templates.get_template('url_table'), results));
        this._get_table().footable();
    },
    
    _search_to_human:function(urls){
        for(var i in urls){
            urls[i].human_io_error = (urls[i].io_error === true)?'Yes':'No';
            urls[i].human_malformed_url = (urls[i].malformed_url === true)?'Yes':'No';
            urls[i].human_timeout_error = (urls[i].timeout_error === true)?'Yes':'No';
            urls[i].human_other_error = (urls[i].casting_error === true)?'Yes':'No';
            urls[i].human_content_length = html_parser.urls.split_content_length(urls[i].content_length); // makes an object of size and units
            
            var last_mod_date = (urls[i].last_modified == 0)?'No Value Specified':new Date(urls[i].last_modified);
            urls[i].human_last_modified = (urls[i].last_modified == 0)?last_mod_date:last_mod_date.getMonth()+1+'/'+last_mod_date.getDate()+'/'+last_mod_date.getFullYear();
        }
        
        var cat = {
                    "url":"http://lesica.com/libor-drama.html",
                    "http_code":200,
		    "direct_parent":"http://lesica.com",
                    "content_type":"text/html",
                    "retrieval_time":79,
                    "io_error":false,
                    "malformed_url":false,
                    "timeout_error":false,
                    "casting_error":false,
                    "content_length":3685,
                    "last_modified":1361906870000,
                    "referencing_urls":[
                                "http://lesica.com",
                                "http://lesica.com/"
                    ],
                    "html_asset":{
                            "document_type":"&lt;!DOCTYPE html&gt;",
                            "title":"Reaction to the LIBOR Drama",
                            "description":"",
                            "misspellings":["Wishlist","LIBOR","LIBOR","LIBOR"],
                            "at_mentions":[],
                            "hash_tags":[],
                            "emails":[],
                            "deprecated_tags":[],
                            "poor_link_naming":[],
                            "inputs_no_labels":[]
                        }

                }
        
        return urls;
    },
    
    _bind_table_sorter:function(){
        this._get_table().tablesorter();
    },
    
    _get_page_container:function(){
        return $('#'+this.page_container_id);
    },
    
    _get_where_to_place_table:function(){
        return $('#'+this.where_to_place_table_id);
    },
    
    _get_table:function(){
        return $('#'+this.table_id);
    },
    
    _humanize_results:function(){
        
    }
}

