'use strict';

/* jasmine specs for directives go here */

describe('directives', function () {
    beforeEach(module('userAdmin.directives'));
    beforeEach(module('userAdmin.services'));
    beforeEach(module('userAdmin.controllers'));
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

    describe('login directive', function () {

        var $compile, $rootScope, template;

        beforeEach(module('app/directive/login.html'));
        beforeEach(inject(function ($templateCache, _$compile_, _$rootScope_) {
            //assign the template to the expected url called by the directive and put it in the cache
            template = $templateCache.get('app/directive/login.html');
            $templateCache.put('directive/login.html', template);

            $compile = _$compile_;
            $rootScope = _$rootScope_;
        }));


        it('should make call to LOGIN_REST_URL with username and password', inject(function ($httpBackend, LOGIN_REST_URL, $compile, $rootScope) {

            var formElement = angular.element("<login url='login-url-parameter'></login>");
            var element = $compile(formElement)($rootScope);
            $rootScope.$digest();
            console.log(element);


        }));
    })
});
