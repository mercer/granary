'use strict';

/* jasmine specs for directives go here */

describe('directives', function () {
    beforeEach(module('userAdmin.directives'));
    beforeEach(module('userAdmin.services'));

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
        var $compile, scope, template, formElement, compiledElement;
        var loginUrl = 'login-url-parameter';

        beforeEach(module('app/directive/login.html'));
        beforeEach(inject(function ($templateCache, _$compile_, _$rootScope_) {
            //assign the template to the expected url called by the directive and put it in the cache
            template = $templateCache.get('app/directive/login.html');
            $templateCache.put('directive/login.html', template);

            $compile = _$compile_;
            scope = _$rootScope_.$new();

            formElement = angular.element("<login url='" + loginUrl + "' after-login=\"doAfterLogin()\"></login>");
            compiledElement = $compile(formElement)(scope);
            scope = compiledElement.scope();
            scope.$digest();
        }));


        it('shuld be compiled to right html', function () {

            expect(compiledElement.attr('url')).toEqual(loginUrl);
            expect(compiledElement.attr('after-login')).toEqual('doAfterLogin()');
            expect($(compiledElement.children()[0]).attr('ng-model')).toEqual('username');
            expect($(compiledElement.children()[1]).attr('ng-model')).toEqual('password');
            expect($(compiledElement.children()[2]).attr('ng-click')).toEqual('login()');

        });
    })
});
