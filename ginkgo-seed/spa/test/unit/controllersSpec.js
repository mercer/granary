'use strict';

describe('controllers', function () {
    beforeEach(module('userAdmin.controllers'));

    describe('UsersController', function () {
        var scope, usersMock, passedInSuccesCallback,UsersController;

        beforeEach(function () {
            module(function ($provide) {
                $provide.value('Users', {});
            });

            inject(function ($controller, $rootScope) {
                scope = $rootScope.$new();
                usersMock = jasmine.createSpyObj('Users',['getUsers']);

                UsersController = $controller('UsersController', {$scope: scope, Users: usersMock});
            });

        });

        it('should get users from User factory', function () {
            expect(usersMock.getUsers).toHaveBeenCalled();
        });

        it('success callback should put users on scope', function(){
            var success = usersMock.getUsers.mostRecentCall.args[0];
            var users = [{id: 1, name: 'name'}];

            success(users);

            expect(scope.users).toBe(users);
        });

    });

});
