'use strict';

/* Services */

angular.module('userAdmin.services', [])
    .value('version', '0.1')

    .factory('Users', ['$http', 'REST_URLS', function ($http, REST_URLS) {
        function getUsers(successCallback, errorCallback) {
            $http.post(REST_URLS.USERS)
                .success(function (data) {
                    successCallback(data);
                })
                .error(function (error) {
                    errorCallback(error);
                });
        }

        //TODO: test me pls
        function getUser(userId, successCallback, errorCallback) {
            $http.get(REST_URLS.USER + '/' + userId)
                .success(function (data) {
                    successCallback(data);
                })
                .error(function (error) {
                    errorCallback(error);
                });
        }

        //TODO: test me pls
        function updateUser(user) {
            return $http.post(REST_URLS.USER, user);
        }

        return {
            getUsers: getUsers,
            getUser: getUser,
            updateUser: updateUser
        };
    }])

    .factory('Auth', ['$http', 'REST_URLS', function ($http, REST_URLS) {
        var user = {userId: '', roles: []};

        function isAuthorizedToAccess(route) {
            return _.indexOf(user.roles, route.role) !== -1;
        }

        function isAuthenticated() {
            return !_.isEmpty(user.userId.trim());
        }

        function authenticate(credentials, successCallback, errorCallback) {
            $http.post(REST_URLS.LOGIN, credentials,
                {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    transformRequest: function(data){
                        return $.param(data);
                    }
                })
                .success(function (response) {
                    angular.copy(response, user);
                    successCallback();
                }).error(function(error){
                    errorCallback(error);
                });
        }

        function getAuthenticatedUser() {
            var copiedUser = {};
            angular.copy(user, copiedUser);
            return copiedUser;
        }

        function logout(){
            user.userId = '';
        }

        return {
            authenticate: authenticate,
            getAuthenticatedUser: getAuthenticatedUser,
            isAuthorizedToAccess: isAuthorizedToAccess,
            isAuthenticated: isAuthenticated,
            logout: logout
        };
    }])


    .constant('REST_URLS', {
        LOGIN: 'http://localhost:8080/backend/j_spring_security_check',
        USERS: 'http://localhost:8080/backend/rest/users',
        USER: 'http://localhost:8080/backend/rest/user'
    });

