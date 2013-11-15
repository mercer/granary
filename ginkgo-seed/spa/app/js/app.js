'use strict';

// Declare app level module which depends on filters, and services
angular.module('userAdmin', ['ngRoute', 'ui.bootstrap', 'userAdmin.filters', 'userAdmin.services', 'userAdmin.directives', 'userAdmin.controllers'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
            .when('/users', {templateUrl: 'partials/users.html', controller: 'UsersController', role: 'ADMIN'})
            .when('/user/:id', {templateUrl: 'partials/user.html', controller: 'UserController', role: 'ADMIN'})
            .otherwise({redirectTo: '/'});
    }])
    .config(['$httpProvider', function($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        $httpProvider.defaults.withCredentials = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }
    ])
    .run(function ($rootScope, $location, Auth) {
        $rootScope.alerts = [];
        $rootScope.closeAlert = function (index) {
            $rootScope.alerts.splice(index, 1);
        };

        $rootScope.$on('$routeChangeStart', function (event, next, current) {
            if (!Auth.isAuthorizedToAccess(next)) {
                if (!Auth.isAuthenticated()) {
                    $location.path('login');
                }
                $location.path('/')
            }
        })
    });
