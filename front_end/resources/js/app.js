'use strict';

/* App Module */
var parserApp = angular.module('parserApp', [
  'ngRoute',
  'parserAppControllers',
  'ui.bootstrap'
]);
 
parserApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/dashboard', {
        templateUrl: 'dashboard_template',
        controller: 'dashboardController'
      }).
      when('/urls', {
        templateUrl: 'urls_template',
        controller: 'urlsController'
      }).
      when('/misspellings', {
        templateUrl: 'misspellings_template',
        controller: 'misspellingsController'
      }).
      when('/accessibility', {
        templateUrl: 'accessibility_template',
        controller: 'accessibilityController'
      }).
      when('/other', {
        templateUrl: 'other_template',
        controller: 'otherController'
      }).
      otherwise({
        redirectTo:'/dashboard'
      });
}]);