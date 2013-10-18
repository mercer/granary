'use strict';

/* Services */

angular.module('userAdmin.services', [])
    .value('version', '0.1')

    .factory('Users', ['$http', 'URL_ALL_USERS', 'URL_USER', function ($http, URL_ALL_USERS, URL_USER) {
        function getUsers(successCallback, errorCallback) {
            $http.post(URL_ALL_USERS)
                .success(function (data) {
                    successCallback(data);
                })
                .error(function (error) {
                    errorCallback(error);
                });
        }

        function getUser(userId, successCallback, errorCallback) {
            $http.get(URL_USER + '/' + userId)
                .success(function (data) {
                    successCallback(data);
                })
                .error(function (error) {
                    errorCallback(error);
                });
        }

        function updateUser(user) {
            return $http.post(URL_USER, user);
        }

        return {
            getUsers: getUsers,
            getUser: getUser,
            updateUser: updateUser
        };
    }])

    .factory('Auth', ['$http', 'LOGIN_REST_URL', function ($http, LOGIN_REST_URL) {
        var user = {name : '', roles : []};

        function authenticate(credentials) {
            $http.post(LOGIN_REST_URL, credentials)
                .success(function (response) {
                    angular.copy(response, user);
                });
        }

        return {
            authenticate: authenticate,
            user: user
        };
    }])

    .constant('URL_ALL_USERS', '../rest/users')
    .constant('URL_USER', '../rest/user')
    .constant('LOGIN_REST_URL', 'j_spring_security_check');
;

