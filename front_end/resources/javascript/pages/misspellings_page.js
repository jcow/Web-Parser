html_parser.misspellings_page = {
    
    page_container_id:'misspellings_page',
    misspellings_by_occurrence_id:'misspellings_page_misspellings_by_occurrence',
    all_misspelt_words_id:'misspellings_page_all_misspelt_words',
    have_been_built:false,
    
    show:function(){
        if(this.have_been_built == false){
            this._build();
            this.have_been_built = true;
        }
    },
    
    get_all_misspelt_words_id:function(){
        return this.all_misspelt_words_id;
    },
    
    get_misspellings_by_occurrence_id:function(){
        return this.misspellings_by_occurrence_id;
    },

    _build:function(){	
            this._build_words_by_occurrence();
            this._build_all_misspelt_words();
    },
    
    _build_words_by_occurrence:function(){
        var res = {};
        res.result = html_parser.urls.get_all_misspellings_by_occurrence();
        this._get_misspellings_by_occurrence_container().html(Mustache.render(html_parser.templates.get_template('misspelling_items'), res));
    },

    _build_all_misspelt_words:function(){
        this._get_all_misspelt_words_container().html(Mustache.render(html_parser.templates.get_template('all_misspelt_words'), html_parser.urls.get_all_misspelled_words()));
    },

    _get_dialog_button:function(){
            return $('#'+this.dialog_button_id);
    },

    _get_dialog_container:function(){
            return $('#'+this.dialog_id);
    },

    _get_all_misspelt_words_container:function(){
        return $("#"+this.all_misspelt_words_id);
    },

    _get_misspellings_by_occurrence_container:function(){
            return $('#'+this.misspellings_by_occurrence_id);
    }

	
}
	
