'use strict';

/* Controllers */

angular.module('userAdmin.controllers', [])
    .controller('HomeCtrl', ['$scope', function ($scope) {

    }])

    .controller('LoginController', ['$scope', function ($scope, $location) {
        $scope.redirectTo = function (route) {
            $location.path(route);
        }
    }])

    .controller('LoginDirectiveController', ['$http', '$scope', LoginDirectiveController])

    .controller('UsersController', ['Users', '$scope', function (Users, $scope) {
        Users.getUsers(
            function success(responseData) {
                $scope.users = responseData;
            },
            function error(error) {
                var x=0;
                // for now do nothing. feel free to add here error messages on scope if you want/need to
            });
    }])
    .controller('UserCtrl', [function () {
    }]);


function LoginDirectiveController($http, $scope) {
    $scope.login = function () {
        $http.post($scope.url, {username: $scope.username, password: $scope.password})
            .then(function(){
                $scope.afterLogin();
            });
    };
};



