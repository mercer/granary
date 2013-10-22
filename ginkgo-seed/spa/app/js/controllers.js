'use strict';

/* Controllers */

angular.module('userAdmin.controllers', [])
    .controller('HomeCtrl', ['$scope', function ($scope) {
    }])
    .controller('LoginController', ['$scope', '$location', LoginController])
    .controller('LoginDirectiveController', ['$scope', 'Auth', LoginDirectiveController])
    .controller('UsersController', ['Users', '$scope', UsersController])
    .controller('UserCtrl', [function () {
    }]);


function LoginController($scope, $location) {
    $scope.redirectTo = function (route) {
        $location.path(route);
    }
}

function LoginDirectiveController($scope, Auth) {
    $scope.login = function () {
        var credentials = {username: $scope.username, password: $scope.password};
        Auth.authenticate(credentials, function () {
                $scope.afterLogin();
            }, function (error) {
                $scope.alerts.push({ type: 'danger', msg: 'Login failed: ' + error });
            });
    };
}

function UsersController(Users, $scope) {
    Users.getUsers(
        function success(responseData) {
            $scope.users = responseData;
        },
        function error(error) {
            $scope.alerts.push({ type: 'danger', msg: 'There was an error: ' + error });
        });
}


function UserController(Users, $scope, $routeParams) {

    $scope.rolesModel = {'ROLE_ADMIN': false, 'ROLE_USER': false};
    function updateRolesViewModelFromUser(model, user) {
        user.roles.forEach(function (role) {
            model[role] = true;
        });

    }

    function updateUserFromRolesViewModel(user, model) {
        var roles = [];
        _.keys(model).forEach(function (role) {
            if (model[role] === true) {
                roles.push(role);
            }
        });
        user.roles = roles;

    }

    Users.getUser($routeParams.id,
        function success(responseData) {
            $scope.user = responseData;
            updateRolesViewModelFromUser($scope.rolesModel, $scope.user);
        },
        function error(error) {
            var x = 0;
            // for now do nothing. feel free to add here error messages on scope if you want/need to
        });

    $scope.updateUser = function () {
        updateUserFromRolesViewModel($scope.user, $scope.rolesModel);
        Users.updateUser($scope.user)
            .then(function () {

            }, function error() {

            })
    }
}


