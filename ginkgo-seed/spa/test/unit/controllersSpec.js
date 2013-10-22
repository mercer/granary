'use strict';

describe('controllers', function () {
    var scope;
    beforeEach(function () {
        module('userAdmin.controllers');

    });

    describe('UsersController', function () {
        var usersMock, passedInSuccesCallback, UsersController;

        beforeEach(function () {
            module(function ($provide) {
                $provide.value('Users', {});
            });

            inject(function ($controller, $rootScope) {
                scope = $rootScope.$new();
                usersMock = jasmine.createSpyObj('Users', ['getUsers']);

                UsersController = $controller('UsersController', {$scope: scope, Users: usersMock});
            });

        });

        it('should get users from User factory', function () {
            expect(usersMock.getUsers).toHaveBeenCalled();
        });

        it('success callback should put users on scope', function () {
            var success = usersMock.getUsers.mostRecentCall.args[0];
            var users = [
                {id: 1, name: 'name'}
            ];

            success(users);

            expect(scope.users).toBe(users);
        });

    });


    describe('LoginDirectiveController', function () {
        var url = 'scope-url-value';
        var callbacks = {
            success: function () {
            },
            error: function () {
            }
        };


        beforeEach(function () {
            module(function ($provide) {
                //jasmine.createSpyObj('Auth', ['authenticate', 'getAuthenticatedUser', 'isAuthorizedToAccess', 'isAuthenticated'])
                $provide.factory('Auth', function () {
                    var credentials = {};

                    function authenticate(credentials, successCallback, errorCallback) {
                        this.credentials = credentials;
                        if (credentials.username != '') {
                            successCallback();
                        } else {
                            errorCallback('ERROR-MESSAGE-FROM-REST');
                        }
                    }

                    return {
                        authenticate: authenticate,
                        credentials: credentials
                    }
                });
            });
            inject(function ($controller, $rootScope) {
                scope = $rootScope.$new();
                scope.url = url;
                scope.afterLogin = function () {
                };
                scope.alerts = [];
                scope.username = 'username';
                scope.password = 'password';

                $controller('LoginDirectiveController', {$scope: scope});
            });
        });

        it('should delegate authentication to Auth', inject(function (Auth) {
            scope.login();
            expect(Auth.credentials).toEqual({username: scope.username, password: scope.password});
        }));

        it('should call afterLogin method given a successful login', inject(function (Auth) {
            spyOn(scope, 'afterLogin');

            scope.login();

            expect(Auth.credentials).toEqual({username: scope.username, password: scope.password});
            expect(scope.afterLogin).toHaveBeenCalled();
        }));

        it('should add error message on scope given a failed login', inject(function ($httpBackend) {
            var responseErrorMessage = 'ERROR-MESSAGE-FROM-REST';
            scope.username = '';

            scope.login();

            expect(scope.alerts).toContain({ type: 'danger', msg: 'Login failed: ' + responseErrorMessage });
        }));
    });

});
