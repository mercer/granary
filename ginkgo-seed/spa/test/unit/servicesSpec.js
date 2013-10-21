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

        it('can get all users given a success rest call', inject(function (Users, $httpBackend, REST_URLS) {
            var callbacks = jasmine.createSpyObj('callbacks', ['success', 'error']);
            $httpBackend.expectPOST(REST_URLS.USERS).respond(200, users);

            Users.getUsers(callbacks.success, callbacks.error);
            $httpBackend.flush();

            expect(callbacks.success).toHaveBeenCalledWith(users);
        }));


        it('can get all users given a success rest call', inject(function (Users, $httpBackend, REST_URLS) {
            var callbacks = jasmine.createSpyObj('callbacks', ['success', 'error']);
            var error = 'error object';
            $httpBackend.expectPOST(REST_URLS.USERS).respond(400, error);

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

        it('user from Auth should be initialized; otherwise values used in templates before http calls are finished will not be bounded to scope', function () {
            expect(authService.getAuthenticatedUser()).not.toBeUndefined();
            expect(authService.getAuthenticatedUser().name).not.toBeUndefined();
            expect(authService.getAuthenticatedUser().roles).not.toBeUndefined();
        });

        it('should call login url', inject(function ($httpBackend, REST_URLS) {
            $httpBackend.expectPOST(REST_URLS.LOGIN, credentials).respond(200, '');

            authService.authenticate(credentials);

            $httpBackend.verifyNoOutstandingExpectation();
        }));


        it('should call login url', inject(function ($httpBackend, REST_URLS) {
            var user = {name: 'name', roles: ['USER']};
            $httpBackend.expectPOST(REST_URLS.LOGIN, credentials).respond(200, user);

            authService.authenticate(credentials);
            $httpBackend.flush();

            expect(authService.getAuthenticatedUser()).not.toBeUndefined();
            expect(authService.getAuthenticatedUser()).not.toBe(user);
            expect(authService.getAuthenticatedUser()).toEqual(user);
        }));

        it('should return false if user does not have role for given route', function () {
            expect(authService.isAuthorizedToAccess({role: 'ADMIN'})).toBeFalsy();
        });

        it('should return true if user has role for given route', inject(function ($httpBackend, REST_URLS) {
            var user = {name: 'name', roles: ['ADMIN']};
            $httpBackend.expectPOST(REST_URLS.LOGIN, credentials).respond(200, user);
            authService.authenticate(credentials);
            $httpBackend.flush();

            expect(authService.isAuthorizedToAccess({role: 'ADMIN'})).toBeTruthy();
        }));

        it('should return a copy of user when getAuthenticatedUser is called', inject(function ($httpBackend, REST_URLS) {
            var authenticatedUserBeforeStateChanges = authService.getAuthenticatedUser();
            var user = {name: 'name', roles: ['USER']};
            $httpBackend.expectPOST(REST_URLS.LOGIN, credentials).respond(200, user);
            authService.authenticate(credentials);
            $httpBackend.flush();

            var authenticatedUserAfterStateChanges = authService.getAuthenticatedUser();

            expect(authenticatedUserBeforeStateChanges).not.toEqual(authenticatedUserAfterStateChanges);
        }));


        it('should return false if the user is not authenticated', function () {
            expect(authService.isAuthenticated()).toEqual(false);
        });

        it('should return true if the user is authenticated', inject(function ($httpBackend, REST_URLS) {
            var user = {name: 'name', roles: ['USER']};
            $httpBackend.expectPOST(REST_URLS.LOGIN, credentials).respond(200, user);
            authService.authenticate(credentials);
            $httpBackend.flush();

            expect(authService.isAuthenticated()).toEqual(true);
        }));

    });


    describe('CONSTANTS DEFINITIONS', function () {
        it('REST_URLS should have a certain structure', inject(function (REST_URLS) {
            expect(REST_URLS).toEqual({
                LOGIN: 'http://localhost:8080/rest/j_spring_security_check',
                USERS: 'http://localhost:8080/rest/users',
                USER: 'http://localhost:8080/rest/user'
            });
        }));
    });
});
