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
            controller: LoginDirectiveController,
            templateUrl: 'directive/login.html',
            scope: {
                url: "@restUrl",
                afterLogin: '&afterLogin'
            },
            link: function(scope,elem,attrs){
                var passwordField = $('#password');
                passwordField.keypress(function (event) {
                    if (event.keyCode == 13) {
                        var loginButton = $('#login');
                        loginButton.click();
                    }
                });

            }
        }
    }
);
