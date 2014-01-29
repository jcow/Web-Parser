parserApp.factory('dataQuery', function() {

	var has_html_asset = function(url){
		return url.html_asset !== null
	}

	var in_http_search_codes = function(codes, url){
		if(codes instanceof Array === false){
			return false
		}

		for(var i in codes){
			var code = codes[i]
			var str_code = code+""

			// search for int+xx
			if(str_code.indexOf("xx") !== -1 && parseInt(str_code[0]) === parseInt((url.http_code+"")[0])){
				return code
			}
			else if(parseInt(code) === parseInt(url.http_code)){
				return code
			}
		}

		return false
	}

	var has_misspellings = function(url){
		return (has_html_asset(url) && url.html_asset.misspellings.length > 0)
	}

	var has_inputs_no_labels = function(url){
		return (has_html_asset(url) && url.html_asset.inputs_no_labels.length > 0)
	}

	var has_no_alt_text = function(url){
		return (has_html_asset(url) && url.html_asset.no_alt_text.length > 0)
	}

	var has_poor_link_naming = function(url){
		return (has_html_asset(url) && url.html_asset.poor_link_naming.length > 0)
	}

	var has_deprecated_tags = function(url){
		return (has_html_asset(url) && url.html_asset.deprecated_tags.length > 0)
	}

	var get = function(data, attributes){

		var is_http_code_search = attributes.http_codes instanceof Array
		var is_misspelling_search = attributes.misspellings === true
		var is_no_alt_text_search = attributes.no_alt_text === true
		var is_inputs_no_labels_search = attributes.inputs_no_labels === true
		var is_poor_link_naming_search = attributes.poor_link_naming === true
		var is_deprecated_tag_search = attributes.deprecated_tags === true

		var place_into_http_code_bin = function(ret, url, http_code){
			ret.http_codes[http_code].push(url)
		}

		var ret = {}

		if(is_misspelling_search) ret.misspellings = []			
		if(is_no_alt_text_search) ret.no_alt_text = []
		if(is_inputs_no_labels_search) ret.inputs_no_labels = []
		if(is_poor_link_naming_search) ret.poor_link_naming = []
		if(is_deprecated_tag_search) ret.deprecated_tags = []
		if(is_http_code_search){
			ret.http_codes = {}
			for(var i in attributes.http_codes) ret.http_codes[attributes.http_codes[i]] = []
		}


		for(var i in data.urls){
			var url = data.urls[i]

			if(is_misspelling_search && has_misspellings(url))	ret.misspellings.push(url)
			if(is_no_alt_text_search && has_no_alt_text(url)) ret.no_alt_text.push(url)
			if(is_inputs_no_labels_search && has_inputs_no_labels(url)) ret.inputs_no_labels.push(url)
			if(is_poor_link_naming_search && has_poor_link_naming(url)) ret.poor_link_naming.push(url)
			if(is_deprecated_tag_search && has_deprecated_tags(url)) ret.deprecated_tags.push(url)
			if(is_http_code_search){
				var http_code = in_http_search_codes(attributes.http_codes, url)	
				if(http_code !== false) place_into_http_code_bin(ret, url, http_code)
			} 
		}

		return ret
	}

	var search = function(data, attributes){

		var is_url_string_search = typeof(attributes.url) === "string" && attributes.url_search !== ""
		var is_http_code_search = attributes.http_codes instanceof Array

		var ret = []
		for(var i in data.urls){
			var url = data.urls[i]

			if(is_url_string_search && url.url.indexOf(attributes.url) !== -1){
				ret.push(url)
				continue
			}

			if(is_http_code_search){
				var http_code = in_http_search_codes(attributes.http_codes, url)
				if(http_code !== false){
					ret.push(url)
					continue
				}
			}
		}
		return ret
	}


	return {
		get:get,
		search:search
	}
});

parserApp.factory('deprecated_tags', function(dataQuery) {
	var get_list = function(data, array_option){
		var query = dataQuery.get(data,{
			'deprecated_tags':true
		})

		var temp_ret = {}
		for(var i in query.deprecated_tags){
			var html_asset = query.deprecated_tags[i].html_asset
			for(var j in html_asset.deprecated_tags){
				temp_ret[html_asset.deprecated_tags[j]] = html_asset.deprecated_tags[j]
			}
		}

		if(array_option !== true){
			return temp_ret
		}

		var ret = []
		for(var i in temp_ret){
			ret.push(temp_ret[i])
		}

		return ret
	}

	return {
		get_list:get_list
	}
})

parserApp.factory('misspellings', function(dataQuery) {
	var get_list = function(data){
		var query = dataQuery.get(data,{
			'misspellings':true
		})	
		
		var ret = {}
		for(var i in query.misspellings){
			var html_asset = query.misspellings[i].html_asset
			for(var j in html_asset.misspellings){
				ret[html_asset.misspellings[j]] = html_asset.misspellings[j]
			}
		}
		return ret
	}

	var get = function(data){
		var query = dataQuery.get(data, {
			misspellings:true
		})
		return query.misspellings
	}
	
	return {
		get:get,
		get_list:get_list
	}
})

parserApp.factory('helpers', function() {

	var milli_to_date = function(lmod){
		var d = new Date(lmod)
		var month = d.getMonth()+1
		month = month < 10?"0"+month:month
		var day = d.getDate() < 10?"0"+d.getDate():d.getDate()
  		var hours = d.getHours() > 12?d.getHours()-12:d.getHours()
  		var minutes = d.getMinutes() < 10?"0"+d.getMinutes():d.getMinutes()
  		var ampm = d.getHours() >= 12?"pm":"am"
		return d.getFullYear()+"-"+month+"-"+day+" "+hours+":"+minutes+ampm
	}

	var bytesToSize = function(bytes) {
		var bytes = parseInt(bytes)
		var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
		if (bytes == 0) return '0 Bytes';
		var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
		return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
	};

	var milli_to_time = function(milli){
		var hours = parseInt(milli/3600000)
		milli = milli - (hours * 3600000)

		var minutes = parseInt(milli/60000)
		milli = milli - (minutes * 60000)

		var seconds = parseInt(milli/1000)

		var string = ""
		string += (hours > 0)?hours+"h"+" ":""
		string += (minutes !== 0 || hours > 0)?minutes+"m"+" ":""
		string += seconds+"s"
		return string
	}

	return {
		milli_to_date:milli_to_date,
		bytesToSize:bytesToSize,
		milli_to_time:milli_to_time
	}
})
