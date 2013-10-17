'use strict';

angular.module('userAdmin.directives', []).
    directive('appVersion', ['version', function (version) {
        return function (scope, elm, attrs) {
            elm.text(version);
        };
    }])

    .directive('login', function () {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: 'directive/login.html',
            scope: {
                url: "@restUrl",
                afterLogin: '&afterLogin'
            }
        }
    }
);
