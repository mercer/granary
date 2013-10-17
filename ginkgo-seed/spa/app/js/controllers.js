'use strict';

/* Controllers */

angular.module('userAdmin.controllers', [])
    .controller('HomeCtrl', ['$scope', function ($scope) {
    }])
    .controller('LoginController', ['$scope', LoginController])
    .controller('LoginDirectiveController', ['$http', '$scope', 'LOGIN_REST_URL', LoginDirectiveController])
    .controller('UsersController', ['Users', '$scope', UsersController])
    .controller('UserCtrl', [function () {
    }]);


function LoginController($scope, $location) {
    $scope.redirectTo = function (route) {
        $location.path(route);
    }
}

function LoginDirectiveController($http, $scope) {
    $scope.login = function () {
        //TODO: should we do rest calls from controller?
        $http.post($scope.url, {username: $scope.username, password: $scope.password})
            .success($scope.redirectTo)
            .error(function(error){
                $scope.loginErrorMessage = error;
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

    $scope.updateUser = function(){
         Users.updateUser($scope.user)
         .then(function(){

         }, function error(){

         })
    }
}


