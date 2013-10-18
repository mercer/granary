'use strict';

// Declare app level module which depends on filters, and services
angular.module('userAdmin', ['ngRoute', 'ui.bootstrap', 'userAdmin.filters', 'userAdmin.services', 'userAdmin.directives', 'userAdmin.controllers'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
        $routeProvider.when('/users', {templateUrl: 'partials/users.html', controller: 'UsersController', role: 'ROLE_ADMIN'});
        $routeProvider.when('/user/:id', {templateUrl: 'partials/user.html', controller: 'UserController', role: 'ROLE_ADMIN'});
        $routeProvider.otherwise({redirectTo: '/'});
    }])
    .run(function ($rootScope, $location, Auth) {
        $rootScope.alerts = [];
        $rootScope.closeAlert = function (index) {
            $rootScope.alerts.splice(index, 1);
        };

        $rootScope.$on('$routeChangeStart', function (event, next, current) {
            if (!Auth.authorize(next)) {
                if(!Auth.isLoggedIn()){
                    $location.path('login');
                }
                $location.path('/')
            }
        })
    });
