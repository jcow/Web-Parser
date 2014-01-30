
var parserAppControllers = angular.module('parserAppControllers', []);

parserAppControllers.controller('mainController', function($scope, $location){
	$scope.data = site_data
})

parserAppControllers.controller('navController', function($scope, $location){
	$scope.is_active = function(name){
		if($location.path() === name){
			return true
		}
		else{
			return false
		}
	}
})

parserAppControllers.controller('dashboardController', function($scope, dataQuery, helpers, deprecated_tags, misspellings) {
	$scope.milli_to_time = function(milli){
		return helpers.milli_to_time(milli)
	}

	var query = dataQuery.get($scope.data, {
		'inputs_no_labels':true,
		'no_alt_text':true,
		'poor_link_naming':true,
		'http_codes':['2xx','3xx','4xx','5xx']
	})


	console.log(misspellings.get_list($scope.data))

	$scope.http_two_hundreds = query.http_codes['2xx']
	$scope.http_three_hundreds = query.http_codes['3xx']
	$scope.http_four_hundreds = query.http_codes['4xx']
	$scope.http_five_hundreds = query.http_codes['5xx']
	$scope.accessibility_issues = query.inputs_no_labels.length+query.no_alt_text.length+query.poor_link_naming.length
	$scope.deprecated_tags = deprecated_tags.get_list($scope.data, true).length
	$scope.unique_misspellings = misspellings.get_list($scope.data,true).length
});

parserAppControllers.controller('urlsController', function($scope,helpers,dataQuery) {

  $scope.bytesToSize = function(amount){
  	return helpers.bytesToSize(amount)
  }

  $scope.last_modified = function(lmod){
  	return (lmod === 0)?"No Last Modified Specified":helpers.milli_to_date(lmod)
  }

  $scope.has_html_asset = function(url){
  	return url.html_asset !== null
  }

  $scope.filtered = []


  $scope.search = function(){

  		var search_obj = {
			'http_codes':[]
		}

		if($scope.url_query !== '') 
			search_obj.url = $scope.url_query

  		if($scope.http_codes_query !== undefined && $scope.http_codes_query !== '')
  			search_obj.http_codes = $scope.http_codes_query.split(",")
  		
  		
  		$scope.filtered = dataQuery.search($scope.data, search_obj)
  }

  
});

parserAppControllers.controller('misspellingsController', function($scope, dataQuery, misspellings) {

	$scope.options = [{
	   name: 'Show by URL',
	   value: 'by_url'
	}, 
	{
	   name: 'List All',
	   value: 'by_list'
	}];

	$scope.show_type = $scope.options[0].value
	$scope.change_show_type = function(){
		$scope.show_type = $scope.options_query.value
	}

	
	$scope.misspellings = misspellings.get($scope.data)
	$scope.misspellings_list = misspellings.get_list($scope.data)
})

parserAppControllers.controller('accessibilityController', function($scope, dataQuery) {

	var query = dataQuery.get($scope.data, {
		'inputs_no_labels':true,
		'no_alt_text':true,
		'poor_link_naming':true,
	})

	$scope.inputs_no_labels = query.inputs_no_labels
	$scope.no_alt_text = query.no_alt_text
	$scope.poor_link_naming = query.poor_link_naming
});


parserAppControllers.controller('otherController', function($scope, deprecated_tags) {
	$scope.deprecated_tags = deprecated_tags.get_list($scope.data, true)
})
