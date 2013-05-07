html_parser.accessibility_page = {
    
    page_container_id:'accessibility_page',
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
        this._get_container().html(Mustache.render(html_parser.templates.get_template('accessibilities_page_poor_link_naming'),  html_parser.urls.get_all_poor_link_naming()));
        this._get_container().append(Mustache.render(html_parser.templates.get_template('accessibilities_page_inputs_no_labels'), html_parser.urls.get_all_inputs_no_labels()));
        this._get_container().append(Mustache.render(html_parser.templates.get_template('accessibilities_page_no_alt_text'), html_parser.urls.get_all_no_alt_text()));
    },
    
    _get_container:function(){
        return $('#'+this.page_container_id);
    }
    
}
