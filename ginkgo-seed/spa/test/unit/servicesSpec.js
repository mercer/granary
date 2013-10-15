'use strict';

/* jasmine specs for services go here */

describe('service', function () {
    beforeEach(module('userAdmin.services'));


    describe('version', function () {
        it('should return current version', inject(function (version) {
            expect(version).toEqual('0.1');
        }));
    });


    describe('Users factory', function(){

        var users = [{id: 1, name: 'user'}];

        it('can get all users given a success rest call', inject(function(Users, $httpBackend, URL_ALL_USERS){
            var callbacks = jasmine.createSpyObj('callbacks', ['success', 'error']);
            $httpBackend.expectPOST(URL_ALL_USERS).respond(200, users);

            Users.getUsers(callbacks.success, callbacks.error);
            $httpBackend.flush();

            expect(callbacks.success).toHaveBeenCalledWith(users);
        }));


        it('can get all users given a success rest call', inject(function(Users, $httpBackend, URL_ALL_USERS){
            var callbacks = jasmine.createSpyObj('callbacks', ['success', 'error']);
            var error = 'error object';
            $httpBackend.expectPOST(URL_ALL_USERS).respond(400, error);

            Users.getUsers(callbacks.success, callbacks.error);
            $httpBackend.flush();

            expect(callbacks.error).toHaveBeenCalledWith(error);
        }));


    });


    describe('CONSTANTS DEFINITIONS', function(){
        it('URL_ALL_USERS shuold point to "rest/users"', inject(function(URL_ALL_USERS){
            expect(URL_ALL_USERS).toBe('rest/users');
        }));
    });
});
