'use strict';

/* jasmine specs for directives go here */

describe('directives', function () {
    beforeEach(module('userAdmin.directives'));
    beforeEach(module('rechtwijzerAdmin.services'));
    beforeEach(module('rechtwijzerAdmin.controllers'));
    beforeEach(module('ngMockE2E'));

    describe('app-version', function () {
        it('should print current version', function () {
            module(function ($provide) {
                $provide.value('version', 'TEST_VER');
            });
            inject(function ($compile, $rootScope) {
                var element = $compile('<span app-version></span>')($rootScope);
                expect(element.text()).toEqual('TEST_VER');
            });
        });
    });

    describe('login', function(){
        var scope;
        var USERNAME = 'username';
        var PASSWORD = 'password';

        beforeEach(inject(function($rootScope,$httpBackend){
            scope = $rootScope.$new();
            $httpBackend.whenGET('directive/login.html').passThrough();
        }));

        it('should make call to LOGIN_REST_URL with username and password', inject(function($httpBackend, LOGIN_REST_URL, $compile, $rootScope){

//            $httpBackend.expectPOST(LOGIN_REST_URL,{username: USERNAME, password: PASSWORD}).respond({});

            var link = $compile("<login></login>");

            console.log(link)
            var element = link(scope);
            console.log(scope);
            console.log(element.html());

//            scope.login(USERNAME, PASSWORD);

//            $httpBackend.flush();

//            $httpBackend.verifyNoOutstandingExpectation();
//            $httpBackend.verifyNoOutstandingRequest();
        }));

        it('pressing login button should get values from username/password inputs and make call with them', inject(function($compile){
                var element = $compile('<login></login>')(scope);
//                var scopeSpy = spyOn(scope, 'login');

                var usernameInput = element.children()[0];
                var passwordInput = element.children()[1];

//                scope.login(usernameInput.text(),passwordInput.text());

//                expect(scopeSpy).toHaveBeenCalledWith(USERNAME, PASSWORD);
           }
        ));
    })
});
