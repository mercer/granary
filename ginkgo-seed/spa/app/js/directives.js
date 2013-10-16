'use strict';

/* Directives */


angular.module('userAdmin.directives', []).
    directive('appVersion', ['version', function (version) {
        return function (scope, elm, attrs) {
            elm.text(version);
        };
    }])

    .directive('login', function () {
        return {
            restrict: 'E',
            replace : true,
            templateUrl: 'directive/login.html',

            scope : {
                url : "@"
            },

            controller: ['$http', '$scope', function ($http, $scope) {
                $scope.login = function(){
                    $http.post($scope.url ,{username: $scope.username, password: $scope.password});
                };
            }]
        }
    }
);
