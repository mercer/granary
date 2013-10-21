'use strict';

/* jasmine specs for services go here */

describe('service', function () {
    beforeEach(module('userAdmin.services'));


    describe('version', function () {
        it('should return current version', inject(function (version) {
            expect(version).toEqual('0.1');
        }));
    });


    describe('Users factory', function () {

        var users = [
            {id: 1, name: 'user'}
        ];

        it('can get all users given a success rest call', inject(function (Users, $httpBackend, URL_ALL_USERS) {
            var callbacks = jasmine.createSpyObj('callbacks', ['success', 'error']);
            $httpBackend.expectPOST(URL_ALL_USERS).respond(200, users);

            Users.getUsers(callbacks.success, callbacks.error);
            $httpBackend.flush();

            expect(callbacks.success).toHaveBeenCalledWith(users);
        }));


        it('can get all users given a success rest call', inject(function (Users, $httpBackend, URL_ALL_USERS) {
            var callbacks = jasmine.createSpyObj('callbacks', ['success', 'error']);
            var error = 'error object';
            $httpBackend.expectPOST(URL_ALL_USERS).respond(400, error);

            Users.getUsers(callbacks.success, callbacks.error);
            $httpBackend.flush();

            expect(callbacks.error).toHaveBeenCalledWith(error);
        }));


    });


    describe('Auth service', function () {
        var credentials = {
            username: 'username',
            password: 'password'
        };
        var authService;

        beforeEach(function () {
            module('userAdmin.services');
            inject(function (Auth) {
                authService = Auth;
            })
        });

        it('user from Auth should be initialized; otherwise values used in templates before http calls are finished will not be bounded to scope', inject(function ($httpBackend, LOGIN_REST_URL) {
            expect(authService.user).not.toBeUndefined();
            expect(authService.user.name).not.toBeUndefined();
            expect(authService.user.roles).not.toBeUndefined();
        }));

        it('should call login url', inject(function ($httpBackend, LOGIN_REST_URL) {
            $httpBackend.expectPOST(LOGIN_REST_URL, credentials).respond(200, '');

            authService.authenticate(credentials);

            $httpBackend.verifyNoOutstandingExpectation();
        }));


        it('should call login url', inject(function ($httpBackend, LOGIN_REST_URL) {
            var user = {name: 'name', roles: ['USER']};
            $httpBackend.expectPOST(LOGIN_REST_URL, credentials).respond(200, user);

            authService.authenticate(credentials);
            $httpBackend.flush();

            expect(authService.user).not.toBeUndefined();
            expect(authService.user).toEqual(user);
        }));

        it('should return false if user does not have role for given route', function () {
            expect(authService.isAuthorizedToAccess({role: 'ADMIN'})).toBeFalsy();
        });


        it('should return true if user has role for given route', function () {
            authService.user.roles.push('ADMIN');

            expect(authService.isAuthorizedToAccess({role: 'ADMIN'})).toBeTruthy();
        });

    });


    describe('CONSTANTS DEFINITIONS', function () {
        it('URL_ALL_USERS shuold point to "rest/users"', inject(function (URL_ALL_USERS) {
            expect(URL_ALL_USERS).toBe('../rest/users');
        }));
    });
});
