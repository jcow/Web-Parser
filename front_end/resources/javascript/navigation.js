/**
*   Object in charge of managing the navigation
*/
html_parser.navigation = {

	// the result page that gets shown on load
	default_page:'general_statistics_page',
        ids:{
            general_statistics:{
                button:'general_statistics_navigation_button',
                page:html_parser.general_statistics_page.get_page_container_id()
            },
            urls:{
                button:'urls_navigation_button',
                page:html_parser.urls_page.get_page_container_id()
            },
	    misspellings:{
		buttons:{
                    misspellings_by_occurrence:'misspellings_by_occurrence_button',
                    all_misspelt_words:'all_misspelt_words_button'
                },
                pages:{
                    misspellings_by_occurrence:html_parser.misspellings_page.get_misspellings_by_occurrence_id(),
                    all_misspelt_words:html_parser.misspellings_page.get_all_misspelt_words_id()
                }
            },
            accessibility:{
		button:'accessibility_navigation_button',
                page:html_parser.accessibility_page.get_page_container_id()
            },
            charts:{
                button:'charts_navigation_button',
                page:html_parser.charts_page.get_page_container_id()
            }
        },
	
	init:function(){
		this._build();
		this._bind();
	},
        
        // show only the page that was clicked
	transition:function(button_to_be_highlighted, page_id_to_be_shown){
		
                console.log(button_to_be_highlighted, page_id_to_be_shown);
                
                if(page_id_to_be_shown == this.ids.general_statistics.page){
                    html_parser.general_statistics_page.show();
                }
                else if(page_id_to_be_shown == this.ids.urls.page){
                    html_parser.urls_page.show();
                }
		else if(page_id_to_be_shown == this.ids.misspellings.pages.misspellings_by_occurrence){
                    html_parser.misspellings_page.show();
                }
                else if(page_id_to_be_shown == this.ids.misspellings.pages.all_misspelt_words){
                    html_parser.misspellings_page.show();
                }
                else if(page_id_to_be_shown == this.ids.accessibility.page){
                    html_parser.accessibility_page.show();
                }
                else if(page_id_to_be_shown == this.ids.charts.page){
                    html_parser.charts_page.show();
                }
                
		// loop though the buttons, show the one that needs to be shown
		$.each($('.navigation_button'), function(index, value){
			value = $(value)
			if(value.attr('id') == button_to_be_highlighted){
				value.addClass('current_navigation_button');
			}
			else{
				value.removeClass('current_navigation_button');
			}
		});
        
        
                // loop though the pages, hide others, show the one we want
                $.each($('.page_container'), function(index, value){
			value = $(value);
			if(value.attr('id') == page_id_to_be_shown){
				value.show();
			}
			else{
				value.hide();
			}
		});
                
	},
	
	_build:function(){
                
		if(this.default_page == 'general_statistics_page'){
                    this.transition(this.ids.general_statistics.button, this.ids.general_statistics.page);	
		}
	},
	
	_bind:function(){
            
		var self = this;
                // stats
		this._get_general_statistics_button().click(function(){
                        self.transition(self.ids.general_statistics.button, self.ids.general_statistics.page);
		});
                
                // urls
		this._get_urls_button().click(function(){
                        self.transition(self.ids.urls.button, self.ids.urls.page);
		});
                
                // misspellings
		this._get_misspellings_by_occurrence_button().click(function(){
                        self.transition(self.ids.misspellings.buttons.misspellings_by_occurrence, self.ids.misspellings.pages.misspellings_by_occurrence);
		});
                
                this._get_all_misspelt_words_button().click(function(){
                        self.transition(self.ids.misspellings.buttons.all_misspelt_words, self.ids.misspellings.pages.all_misspelt_words);
                });
                
                // accessibility
		this._get_accessibility_button().click(function(){
                        self.transition(self.ids.accessibility.button, self.ids.accessibility.page);
		});
                
                // charts
                this._get_charts_button().click(function(){
                        self.transition(self.ids.charts.button, self.ids.charts.page);
		});
	},
        
	_get_general_statistics_button:function(){
		return $('#'+this.ids.general_statistics.button);
	},

	_get_urls_button:function(){
		return $('#'+this.ids.urls.button);
	},
	
	_get_misspellings_by_occurrence_button:function(){
		return $('#'+this.ids.misspellings.buttons.misspellings_by_occurrence);
	},
        
        _get_all_misspelt_words_button:function(){
                return $('#'+this.ids.misspellings.buttons.all_misspelt_words);
        },
        
	_get_accessibility_button:function(){
		return $('#'+this.ids.accessibility.button);
	},
        
        _get_charts_button:function(){
		return $('#'+this.ids.charts.button);
	}

        
        
        
}
