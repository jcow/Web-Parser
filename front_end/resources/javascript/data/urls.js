/**
* Object in charge of setting and searching the url data
*/
html_parser.urls = {
	
    // this gets set in data.js
	data:null,
	
	/**
	* Sets the url data
	*/
	set_data:function(data){
		this.data = data;
	},
	
	/**
	* Gets the data for the general statistics results page
	*/
	get_general_stats:function(){
		return {
			starting_url: this.data.starting_url,
			domain: this.data.domain,
			total_urls: this.data.total_urls,
			total_same_domain_urls: this.data.total_same_domain_urls,
			total_misspellings: this.data.total_misspellings,
			total_same_domain_pages: this.data.total_same_domain_pages,
			total_time: this.data.total_time,
			total_images: this.data.total_images
		}
	},
	
	/**
	 *	Gets the data
	 */
	get_data:function(){
		return this.data;
	},
	
	/**
	*  Returns all of the urls
	*/
	get_all_urls:function(){
		return this.data.urls;
	},
        
        get_starting_url:function(){
                return this.data.starting_url;
        },
	
	/**
	*	Returns any url that contained a misspelling
	*/
	get_all_misspellings:function(){
		var result = [];
		var urls = this.get_urls()
		for(var i in urls){
			if(urls[i].html_asset != null && urls[i].html_asset.misspellings.length > 0){
				result.push(urls[i]);
			}
		}
		return result;
	},

	/**
	* Returns an array of all the words that were misspelt
	*/
	get_all_misspelled_words:function(){
		var result = {};
		var urls = this.get_all_urls();
                
                // make the object of all the misspellings
		for(var i in urls){
			if(urls[i].html_asset != null && urls[i].html_asset.misspellings.length > 0){
				for(var j in urls[i].html_asset.misspellings){
					result[urls[i].html_asset.misspellings[j]] = urls[i].html_asset.misspellings[j];
				}
			}
		}
                
                // convert it into an array
                var new_result = [];
                for(var i in result){
                    new_result.push(result[i]);
                }
                
                // sort it
                new_result.sort();

		return new_result;
	},	
	
	/**
	 * Returns all urls that have a misspelling, ordered by the occurence value, high to low
	 */
	get_all_misspellings_by_occurrence:function(){
		var result = {};
		var urls = this.get_all_urls();

				// get the misspellings into an object of objects
		for(var i in urls){
			if(urls[i].html_asset != null && urls[i].html_asset.misspellings.length > 0){

				for(var j in urls[i].html_asset.misspellings){
					
					// does the misspelling exist
					if(typeof result[urls[i].html_asset.misspellings[j]] === 'undefined'){
						result[urls[i].html_asset.misspellings[j]] = {
							name:urls[i].html_asset.misspellings[j],
							occurrences:1,
							urls:{}
						}
                                                
                                                // create a url within the misspelling
                                                result[urls[i].html_asset.misspellings[j]].urls[urls[i].url] = {
                                                    url:urls[i].url,
                                                    occurrences:1
                                                };
						
					}
					// the misspelling already exists
					else{
                                            
                                            // increment the occurrence of the misspelling
                                            result[urls[i].html_asset.misspellings[j]].occurrences += 1;
                                            
                                            // does the url already exist for that misspelling
                                            // no, create a url within the misspelling
                                            if(typeof result[urls[i].html_asset.misspellings[j]].urls[urls[i].url] === 'undefined'){
                                                result[urls[i].html_asset.misspellings[j]].urls[urls[i].url] = {
                                                    url:urls[i].url,
                                                    occurrences:1
                                                };
                                            }
                                            // yes, increment the occurrence of a misspelling at the url
                                            else{
                                                result[urls[i].html_asset.misspellings[j]].urls[urls[i].url].occurrences += 1;
                                            }
                                            
					}
					console.log(result)
				}
			}
		}
                
                // sorting function
		var sort_function = function(obj1, obj2){
			if(obj1.occurrences > obj2.occurrences){
				return -1;
			}
			else if(obj1.occurrences < obj2.occurrences){
				return 1;
			}
			else{
				return 0;
			}
		}
                
                // make into array
		var new_result = [];
		for(var i in result){
			var url_result = [];
			for(var j in result[i].urls){
				url_result.push(result[i].urls[j]);
			}
			
			result[i].urls = url_result;
			new_result.push(result[i]);
		}
		
		// sort
		new_result.sort(sort_function);
		
		return new_result;
	},


        /**
	 * Returns all urls that have a misspelling, ordered by the occurence value, high to low
	 */
	fake_get_all_misspellings_by_occurrence:function(){
		var result = {};
		var urls = this.get_all_urls();

		// get the misspellings into an object of objects
		for(var i in urls){
			if(urls[i].html_asset != null && urls[i].html_asset.misspellings.length > 0){

				for(var j in urls[i].html_asset.misspellings){
					
					// does the misspelling exist
					if(typeof result[urls[i].html_asset.misspellings[j]] === 'undefined'){
						result[urls[i].html_asset.misspellings[j]] = {
							name:urls[i].html_asset.misspellings[j],
							occurrences:1,
							urls:{}
						}
                                                
                                                // create a url within the misspelling
                                                result[urls[i].html_asset.misspellings[j]].urls[urls[i].url] = {
                                                    url:urls[i].url,
                                                    occurrences:1
                                                };
						
					}
					// the misspelling already exists
					else{
                                            
                                            // increment the occurrence of the misspelling
                                            result[urls[i].html_asset.misspellings[j]].occurrences += 1;
                                            
                                            // does the url already exist for that misspelling
                                            // no, create a url within the misspelling
                                            if(typeof result[urls[i].html_asset.misspellings[j]].urls[urls[i].url] === 'undefined'){
                                                result[urls[i].html_asset.misspellings[j]].urls[urls[i].url] = {
                                                    url:urls[i].url,
                                                    occurrences:1
                                                };
                                            }
                                            // yes, increment the occurrence of a misspelling at the url
                                            else{
                                                result[urls[i].html_asset.misspellings[j]].urls[urls[i].url].occurrences += 1;
                                            }
                                            
					}
				}
			}
		}
                
                // sorting function
		var sort_function = function(obj1, obj2){
			if(obj1.occurrences > obj2.occurrences){
				return -1;
			}
			else if(obj1.occurrences < obj2.occurrences){
				return 1;
			}
			else{
				return 0;
			}
		}
                
                // make into array
		var new_result = [];
		for(var i in result){
			var url_result = [];
			for(var j in result[i].urls){
				url_result.push(result[i].urls[j]);
			}
			
			result[i].urls = url_result;
			new_result.push(result[i]);
		}
		
		// sort
		new_result.sort(sort_function);
		
		return new_result;
        },
        
        /**
         * Returns all urls that have poorly-linked names
         */
        get_all_poor_link_naming:function(){
            var result = [];
            var urls = this.get_all_urls();
            
            for(var i in urls){
                if(urls[i].html_asset != null && urls[i].html_asset.poor_link_naming.length > 0){
                    result.push(urls[i]);
                }
            }
            return result;
        },
        
        get_all_inputs_no_labels:function(){
            var result = [];
            var urls = this.get_all_urls();
            
            for(var i in urls){
                if(urls[i].html_asset != null && urls[i].html_asset.inputs_no_labels.length > 0){
                    result.push(urls[i]);
                }
            }
            return result;
        },
        
        get_all_no_alt_text:function(){
            var result = [];
            var urls = this.get_all_urls();
            
            for(var i in urls){
                if(urls[i].html_asset != null && urls[i].html_asset.no_alt_text.length > 0){
                    result.push(urls[i]);
                }
            }
            return result;
        },
        
        
        get_all_emails:function(){
            return this._get_email_hashtag_or_at_mentions("emails");
        },
        
        get_all_hash_tags:function(){
            return this._get_email_hashtag_or_at_mentions("hash_tags");
        },
        
        get_all_at_mentions:function(){
            return this._get_email_hashtag_or_at_mentions("at_mentions");
        },

        /**
         * Shortens a url by trimming the end of the url at a specific length and adding an end string to show it was cut
         */
        short_url:function(url, length, end){
            
            if(typeof end === 'undefined'){
                end = "......";
            }
            
            // catch case to make sure the end is longer than th
            if(end.length > url.length){
                return url;
            }
            
            if(url.length-end.length > length){
                url = url.substring(0, length-6);
                url += end;
            }
            
            return url;
        },

        /*
         * Shortens a URL by truncating its middle.
         * Retains beginning and end.
         */
        short_url_mid:function(url, l){
            var shortString = function(s, l, reverse){
                var stop_chars = [' ','/', '&'];
                var acceptable_shortness = l * 0.80; // When to start looking for stop characters
                var reverse = typeof(reverse) != "undefined" ? reverse : false;
                var s = reverse ? s.split("").reverse().join("") : s;
                var short_s = "";

                for(var i=0; i < l-1; i++){
                    short_s += s[i];
                    if(i >= acceptable_shortness && stop_chars.indexOf(s[i]) >= 0){
                        break;
                    }
                }
                if(reverse){ return short_s.split("").reverse().join(""); }
                return short_s;
            }

            var l = typeof(l) != "undefined" ? l : 50;
            var chunk_l = (l/2);
            var url = url.replace("http://","").replace("https://","");

            if(url.length <= l){ return url; }

            var start_chunk = shortString(url, chunk_l, false);
            var end_chunk = shortString(url, chunk_l, true);
            return start_chunk + ".." + end_chunk;
        },

        /**
         *  @param which: A string of what you want to get from urls.js in the html_asset object
         *  Returns the value in an array with the structure of 
         *  [
         *      {which(minus the plurality of it, so emails becomes email):'', urls:['', '', ...]}
         *  ]
         */
        _get_email_hashtag_or_at_mentions:function(which){
            
            var urls_con_emails = [];
            var urls = this.get_all_urls();
            
            // get all the urls that have emails
            for(var i in urls){
                if(urls[i].html_asset != null && urls[i].html_asset[which].length > 0){
                    urls_con_emails.push(urls[i]);
                }
            }
            
            // make a data structure that does email:{email:'', urls:[]}
            var results = {};
            for(var i in urls_con_emails){
                for(var j in urls_con_emails[i].html_asset[which]){
                    if(typeof results[urls_con_emails[i].html_asset[which][j]] === 'undefined'){
                        results[urls_con_emails[i].html_asset[which][j]] = {}
                        results[urls_con_emails[i].html_asset[which][j]][which.substring(0, which.length-1)] = urls_con_emails[i].html_asset[which][j];
                        results[urls_con_emails[i].html_asset[which][j]].urls = [urls_con_emails[i].url]
                            
                    }
                    else{
                        results[urls_con_emails[i].html_asset[which][j]].urls.push(urls_con_emails[i].url);
                    }
                }
            }
            
            // make into array
            var new_result = [];
            for(var i in results){
                new_result.push(results[i]);
            }
            
            return new_result;
        },
		
        strip_domain_from_url:function(url){
                return url.replace(this.data.starting_url,"");
        },
        
        search:function(url_text, http_codes, content_type, document_type, error, inline_styling, deprecated_tags){
            var result = [];
            
            var urls = this.get_all_urls();
            for(var i in urls){
                    
                var include_url = true;
                
                var function_outcomes = [
                    this._search_url_string(url_text, urls[i]),
                    this._search_http_codes(http_codes, urls[i]),
                    this._search_content_types(content_type, urls[i]),
                    this._search_document_types(document_type, urls[i]),
                    this._search_errors(error, urls[i]),
                    this._search_deprecated_tags(deprecated_tags, urls[i])
                ];
                
                for(var j in function_outcomes){
                    if(function_outcomes[j] === false){
                        include_url = false;
                        break;
                    }
                }
               
               if(include_url == true){
                   urls[i].short_url = html_parser.urls.short_url(urls[i].url,100);
                   result.push(urls[i]);
               }
            }
            
            return result;
        },
        
        
        _search_url_string:function(url_string, url){
            var ok = true;
            if(url_string !== ''){
                if(url.url.indexOf(url_string) === -1){
                    ok = false;
                }
            }
            return ok;
        },
        
        _search_http_codes:function(http_codes, url){
            var ok = true
            if(http_codes.length > 0){
                ok = false;
                for(var j in http_codes){
                    if(url.http_code == http_codes[j]){
                        ok = true;
                        break;
                    }
                }
            }
            return ok;
        },
        
        _search_content_types:function(content_type, url){
            var ok = true;
            if(content_type !== ''){
                if(content_type === 'HTML'){
                    if(url.content_type.indexOf('html') === -1){
                        ok = false;
                    }
                }
                else if(content_type === 'IMAGE'){
                    if(url.content_type.indexOf('image') === -1){
                        ok = false;
                    }
                }
                else if(content_type === 'OTHER'){
                    if(url.content_type.indexOf('image') !== -1 || url.content_type.indexOf('html') !== -1){
                        ok = false;
                    }
                }
            }
            return ok;
        },
        
        _search_document_types:function(document_type, url){
            var ok = true;
            if(document_type !== ''){
                if(url.html_asset === null || url.html_asset.document_type.toLowerCase().indexOf(document_type.toLowerCase()) === -1){
                    ok = false;
                }
            }
            return ok;
        },
        
        _search_errors:function(error, url){
            var ok = true;
            if(error !== false){
                if(url.io_error === false && url.malformed_url === false && url.timeout_error === false && url.casting_error === false ){
                    ok = false;
                }
            }
            return ok;
        },
        
        _search_inline_styling:function(){
            // todo
        },
        
        _search_deprecated_tags:function(deprecated_tags, url){
            var ok = true;
            if(deprecated_tags !== false){
                if(url.html_asset === null || url.html_asset.deprecated_tags.length === 0){
                    ok = false;
                }
            }
            return ok;
        },
        
        /**
         * Converts a number into an amount, and an associated prefix up to GB
         */
        split_content_length:function(content_length){
            
            var units = ['GB', 'MB', 'KB'];
            var byte_size = [1024*1024*1024, 1024*1024, 1024];
            
            for(var i in units){
                if(byte_size[i] <= content_length){
                    content_length = content_length/byte_size[i];
                    content_length = Math.round(content_length*1000)/1000  //limit decimal spaces to 3
                    return {
                        value:content_length,
                        units:units[i]
                    }
                }
            }
            
            return {
                value:content_length,
                units:'B'
            }
        }
        
        
                        
        
        
        
} 
