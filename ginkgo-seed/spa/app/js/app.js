'use strict';


// Declare app level module which depends on filters, and services
angular.module('userAdmin', ['ngRoute','userAdmin.filters', 'userAdmin.services', 'userAdmin.directives', 'userAdmin.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
    $routeProvider.when('/users', {templateUrl: 'partials/users.html', controller: 'UsersController'});
    $routeProvider.when('/user/:id', {templateUrl: 'partials/user.html', controller: 'UserCtrl'});
    $routeProvider.otherwise({redirectTo: '/'});
  }]);
