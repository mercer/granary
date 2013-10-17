'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('userAdmin.services', []).
    value('version', '0.1')
    .factory('Users', function ($http, URL_ALL_USERS,URL_USER) {
        function getUsers(successCallback, errorCallback) {
            $http.post(URL_ALL_USERS)
                .success(function (data) {
                    successCallback(data);
                })
                .error(function (error) {
                    errorCallback(error);
                });
        }
        function getUser(userId,successCallback, errorCallback) {
            $http.get(URL_USER+ '/' + userId)
                .success(function (data) {
                    successCallback(data);
                })
                .error(function (error) {
                    errorCallback(error);
                });
        }

        return {
            getUsers: getUsers,
            getUser: getUser
        };
    })

    .constant('URL_ALL_USERS', '../rest/users')
    .constant('URL_USER', '../rest/user')
    .constant('LOGIN_REST_URL','j_spring_security_check');
;

