/**
* Object in charge of managing templates
*/
html_parser.templates = {

	loaded_count:0,
	
	/**
	* Templates for the application
        * Simply add another attribute that is unique
        * If you need to use it, call it in the get_template function that html_parser.templates provides
	*/
	temps:{
		general_statistics:'<h2>General Information</h2><dl>\n\
					<dt>Starting URL</dt>\n\
					<dd>{{starting_url}}</dd>\n\
					<dt>Domain</dt>\n\
					<dd>{{domain}}</dd>\n\
                                        <dt>Total URLs Visited</dt>\n\
                                        <dd>{{total_urls}}</dd>\n\
                                        <dt>Total URLs Visited in Your Domain</dt>\n\
                                        <dd>{{total_same_domain_urls}}</dd>\n\
                                        <dt>Total HTML Pages in Your Domain</dt>\n\
                                        <dd>{{total_same_domain_pages}}</dd>\n\
                                        <dt>Total Images</dt>\n\
                                        <dd>{{total_images}}</dd>\n\
                                        <dt>Total Misspellings</dt>\n\
                                        <dd>{{total_misspellings}}</dd>\n\
                                        <dt>Total Time to Retrieve URLs</dt>\n\
                                        <dd>{{#total_time}}{{minutes}} minutes {{seconds}} seconds{{/total_time}}</dd>\n\
                                </dl>',
                general_statistics_emails:'<h2>Emails</h2>{{#.}}<dt>{{email}}</h3></dt><ul>{{#urls}} <li>{{.}}</li> {{/urls}}</ul>{{/.}}{{^.}}<p>No Emails were found</p>{{/.}}',
                general_statistics_hash_tags:'<h2>#HashTags</h2>{{#.}}<dt>{{hash_tag}}</h3></dt><ul>{{#urls}} <li>{{.}}</li> {{/urls}}</ul>{{/.}}{{^.}}<p>No #Hashtags were found</p>{{/.}}',
                general_statistics_at_mentions:'<h2>@Mentions</h2>{{#.}}<dt>{{at_mention}}</h3></dt><ul>{{#urls}} <li>{{.}}</li> {{/urls}}</ul>{{/.}} {{^.}}<p>No @Mentions were found</p>{{/.}}',
		url_table:'<table id="url_search_table" class="footable">\n\
                                <thead>\n\
                                        <th data-class="expand">URL</th>\n\
                                        <th data-hide="tablet">HTTP Code</th>\n\
                                        <th data-hide="tablet">Retrieval Time</th>\n\
                                        <th data-hide="phone,tablet,max">URL</th>\n\
                                        <th data-hide="phone,tablet,max">Parent URL</th>\n\
                                        <th data-hide="phone,tablet,max">Content Type</th>\n\
                                        <th data-hide="phone,tablet,max">I/O Error</th>\n\
                                        <th data-hide="phone,tablet,max">Malformed URL</th>\n\
                                        <th data-hide="phone,tablet,max">Timeout Error</th>\n\
                                        <th data-hide="phone,tablet,max">Other Error</th>\n\
                                        <th data-hide="phone,tablet,max">Content Length</th>\n\
                                        <th data-hide="phone,tablet,max">Last Modified</th>\n\
                                        <th data-hide="phone,tablet,max">Document Type</th>\n\
                                        <th data-hide="phone,tablet,max">Title</th>\n\
                                        <th data-hide="phone,tablet,max">Description</th>\n\
                                </thead>\n\
                                <tbody>\n\
                                        {{#.}}\n\
                                                <tr>\n\
                                                        <td>{{short_url}}</td>\n\
                                                        <td>{{http_code}}</td>\n\
                                                        <td>{{retrieval_time}}</td>\n\
                                                        <td>{{url}}</td>\n\
                                                        <td>{{direct_parent}}</td>\n\
                                                        <td>{{content_type}}</td>\n\
                                                        <td>{{human_io_error}}</td>\n\
                                                        <td>{{human_malformed_url}}</td>\n\
                                                        <td>{{human_timeout_error}}</td>\n\
                                                        <td>{{human_other_error}}</td>\n\
                                                        <td>{{#human_content_length}}{{value}} {{units}} {{/human_content_length}}</td>\n\
                                                        <td>{{human_last_modified}}</td>\n\
                                                        <td>{{#html_asset}}{{document_type}}{{/html_asset}}</td>\n\
                                                        <td>{{#html_asset}}{{title}}{{/html_asset}}</td>\n\
                                                        <td>{{#html_asset}}{{description}}{{/html_asset}}</td>\n\
                                                </tr>\n\
                                        {{/.}}\n\
                                </tbody>\n\
                        </table>',
		misspelling_items:'<h2 class="pad_lr">Misspelt words by Occurrence</h2><div class="main_misspelling_container">{{#result}}<div class="misspelling_container">\n\
							<h3>{{name}}</h3>\n\
							<dl>\n\
								<dt>Number of occurrences</dt>\n\
								<dd>{{occurrences}}</dd>\n\
								<dt>URLs that the misspelling appears</dt>\n\
								<dd><ul>{{#urls}}<li>{{occurrences}} - {{url}}</li>{{/urls}}</ul></dd>\n\
							</dl>\n\
							</div>{{/result}}</div>',
		all_misspelt_words:'<h2>All Misspelt Words</h2><ul>{{#.}}<li>{{.}}</li>{{/.}}</ul>',
                accessibilities_page_poor_link_naming:'<div class="accessibility_container"><h2>Poor Link Naming</h2>\n\
                                                        {{#.}} <h3>{{url}}</h3><ul>{{#html_asset.poor_link_naming}} <li>{{.}}</li> {{/html_asset.poor_link_naming}}</ul>  {{/.}}\n\
                                                        {{^.}}None were found{{/.}}\n\
                                                       </div>',
                accessibilities_page_inputs_no_labels:'<div class="accessibility_container"><h2>Inputs No Labels</h2>\n\
                                                        {{#.}}<h3>{{url}}</h3> {{#html_asset.inputs_no_labels}}<dl class="dl-horizontal"><dt>Form Tag</dt><dd>{{tag_name}}</dd><dt>ID Value</dt><dd>{{id_value}}</dd><dt>For Value</dt><dd>{{for_value}}</dd></dl>{{/html_asset.inputs_no_labels}}{{/.}}\n\
                                                        {{^.}}None were found{{/.}}\n\
                                                       </div>',
                accessibilities_page_no_alt_text:'<div class="accessibility_container"><h2>Images with no Alternative Text</h2>\n\
                                                        {{#.}} <h3>{{url}}</h3><ul>{{#html_asset.no_alt_text}} <li>{{.}}</li> {{/html_asset.no_alt_text}}</ul>  {{/.}}\n\
                                                        {{^.}}None were found{{/.}}\n\
                                                       </div>'                                       
	},
	
	/**
	* Gets a template based off the templates name
	*/
	get_template:function(which){
		return this.temps[which];
	}
}
