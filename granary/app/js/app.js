'use strict';


// Declare app level module which depends on filters, and services
angular.module('granary', ['granary.filters', 'granary.services', 'granary.directives', 'granary.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
    $routeProvider.otherwise({redirectTo: '/'});
  }]);
