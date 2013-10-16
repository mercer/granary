'use strict';

/* Directives */


angular.module('rechtwijzerAdmin.directives', []).
    directive('appVersion', ['version', function (version) {
        return function (scope, elm, attrs) {
            elm.text(version);
        };
    }])
    .directive('login', function () {
        return {
            restrict: 'E',
            replace : true,
            controller: ['$http', '$scope', 'LOGIN_REST_URL', function ($http, $scope, LOGIN_REST_URL) {
                $scope.login = function(){
                    $http.post(LOGIN_REST_URL,{username: $scope.username, password: $scope.password});
                };

                console.log('controller: ' + $scope);
            }],
            templateUrl: 'directive/login.html',
            link : function(scope, elem, attr, controller){
                console.log('Link: ' + scope);
                scope.test = function (){
                    console.log(elem);
                }
            }
        }
    }
);
