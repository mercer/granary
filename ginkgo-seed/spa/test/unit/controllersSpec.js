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

        beforeEach(function () {
            module(function ($provide) {
                $provide.value('LOGIN_REST_URL', 'LOGIN_REST_URL');
            });
            inject(function ($controller, $rootScope, $httpBackend) {
                scope = $rootScope.$new();
                scope.redirectTo = function (route) {}
                scope.url = url
                scope.username = 'username';
                scope.password = 'password';

                $controller('LoginDirectiveController', {$scope: scope});
            });
        });

        it('should post username/password to url given in scope', inject(function ($httpBackend) {
            $httpBackend.expectPOST(url, {username: scope.username, password: scope.password}).respond(200, '');
            scope.login();
            $httpBackend.verifyNoOutstandingExpectation();
        }));

        it('should call redirectTo method given a successful login', inject(function ($httpBackend) {
            $httpBackend.expectPOST(url, {username: scope.username, password: scope.password}).respond(200, '');
            spyOn(scope, 'redirectTo');

            scope.login();
            $httpBackend.flush();

            expect(scope.redirectTo).toHaveBeenCalled();
        }));

        it('should add error message on scope given a failed login', inject(function ($httpBackend) {
            var responseErrorMessage = 'ERROR-MESSAGE-FROM-REST';
            $httpBackend.expectPOST(url, {username: scope.username, password: scope.password})
                .respond(401, responseErrorMessage);

            scope.login();
            $httpBackend.flush();

            expect(scope.loginErrorMessage).toEqual(responseErrorMessage);
        }));
    });

});
