'use strict';

/* Services */

angular.module('userAdmin.services', ['ngCookies','http-auth-interceptor'])
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

  .factory('Auth', ['$http', 'REST_URLS', '$cookieStore', function ($http, REST_URLS, $cookieStore) {
    var user = {userId: '', roles: []};

    if ($cookieStore.get('user') !== undefined) {
      angular.extend(user, $cookieStore.get('user'));
    }

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
          transformRequest: function (data) {
            return $.param(data);
          },
          ignoreAuthModule: true
        })
        .success(function (response) {
          angular.copy(response, user);
          $cookieStore.put('user', user);
          successCallback();
        }).error(function (error) {
          errorCallback(error);
        });

    }

    function getAuthenticatedUser() {
      var copiedUser = {};
      angular.copy(user, copiedUser);
      return copiedUser;
    }

    function logout(successCallback, errorCallback) {
      $cookieStore.remove('user');
      $http.post(REST_URLS.LOGOUT,{},{ignoreAuthModule : true}).success(
        function () {
          user.userId = '';
          user.roles = [];
          if (!_.isUndefined(successCallback)) {
            successCallback();
          }
        }).error(function (error) {
          if (!_.isUndefined(errorCallback)) {
            errorCallback(error);
          }
        });
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
    LOGOUT: 'http://localhost:8080/backend/j_spring_security_logout',
    USERS: 'http://localhost:8080/backend/rest/users',
    USER: 'http://localhost:8080/backend/rest/user'
  });

