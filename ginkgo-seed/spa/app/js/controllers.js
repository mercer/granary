'use strict';

/* Controllers */

angular.module('userAdmin.controllers', [])
    .controller('HomeCtrl', [function () {
    }])
    .controller('UsersController', ['Users', '$scope', function (Users, $scope) {
        Users.getUsers(
            function success(responseData) {
                $scope.users =  responseData;
            },
            function error(error) {
                // for now do nothing. feel free to add here error messages on scope if you want/need to
            });
    }])
    .controller('UserCtrl', [function () {
    }])
;
