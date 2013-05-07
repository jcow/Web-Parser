html_parser.general_statistics_page = {
    
    page_container_id:'general_statistics_page',
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
			var general_stats = html_parser.urls.get_general_stats();
			general_stats.total_time = this._process_general_stats_with_time(general_stats.total_time);			

            this._get_container().html(Mustache.render(html_parser.templates.get_template('general_statistics'), general_stats));
            this._get_container().append(Mustache.render(html_parser.templates.get_template('general_statistics_emails'), html_parser.urls.get_all_emails()));
            this._get_container().append(Mustache.render(html_parser.templates.get_template('general_statistics_hash_tags'), html_parser.urls.get_all_hash_tags()));
            this._get_container().append(Mustache.render(html_parser.templates.get_template('general_statistics_at_mentions'), html_parser.urls.get_all_at_mentions()));
            
            
    },
    
    _get_container:function(){
        return $('#'+this.page_container_id);
    },
    // total time is in milliseconds
	_process_general_stats_with_time:function(total_time){
		var seconds = Math.ceil(total_time/1000);
		
		var minutes = Math.floor(seconds/60);
		seconds = seconds - (minutes * 60);
		return {
			seconds:seconds,
			minutes:minutes
		}
	}
}


