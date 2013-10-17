'use strict';

/* Controllers */

angular.module('userAdmin.controllers', [])
    .controller('HomeCtrl', ['$scope', function ($scope) {
    }])
    .controller('LoginController', ['$scope', LoginController])
    .controller('LoginDirectiveController', ['$http', '$scope', 'LOGIN_REST_URL', LoginDirectiveController])
    .controller('UsersController', ['Users', '$scope', UsersController]);


function LoginController($scope, $location) {
    $scope.redirectTo = function (route) {
        $location.path(route);
    }
}

function LoginDirectiveController($http, $scope, LOGIN_REST_URL) {
    $scope.login = function () {
        $http.post(LOGIN_REST_URL, {username: $scope.username, password: $scope.password})
            .then(function () {
                $scope.afterLogin();
            });
    };
};

function UsersController(Users, $scope) {
    Users.getUsers(
        function success(responseData) {
            $scope.users = responseData;
        },
        function error(error) {
            var x = 0;
            // for now do nothing. feel free to add here error messages on scope if you want/need to
        });
}


function UserController(Users, $scope, $routeParams) {
    Users.getUser($routeParams.id,
        function success(responseData) {
            $scope.user = responseData;
        },
        function error(error) {
            var x = 0;
            // for now do nothing. feel free to add here error messages on scope if you want/need to
        });
}


