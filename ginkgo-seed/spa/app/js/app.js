'use strict';


// Declare app level module which depends on filters, and services
angular.module('rechtwijzerAdmin', ['ngRoute','rechtwijzerAdmin.filters', 'rechtwijzerAdmin.services', 'rechtwijzerAdmin.directives', 'rechtwijzerAdmin.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
    $routeProvider.when('/users', {templateUrl: 'partials/users.html', controller: 'UsersCtrl'});
    $routeProvider.when('/user/:id', {templateUrl: 'partials/user.html', controller: 'UserCtrl'});
    $routeProvider.otherwise({redirectTo: '/'});
  }]);
