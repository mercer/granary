'use strict';

describe('auth and user management modules', function () {
    var rootScope;

    beforeEach(function () {
        module('userAdmin');

    });

    describe('$onRouteChangeStart', function () {
        var authServiceMock;
        var locationMock;
        var nextRoute = {roles: ""}, currentRoute;

        beforeEach(function () {
            module(function ($provide) {
                authServiceMock = jasmine.createSpyObj('Auth', ['isAuthorizedToAccess', 'isAuthenticated']);
                locationMock = jasmine.createSpyObj('$location', ['path']);

                $provide.value('Auth', authServiceMock);
                $provide.value('$location', locationMock);
            })

            inject(function ($rootScope) {
                rootScope = $rootScope;
            })
        })

        it('should do nothing when user is authorized to access route', function () {
            authServiceMock.isAuthorizedToAccess.andCallFake(function () {return true; });

            rootScope.$broadcast('$routeChangeStart');

            expect(locationMock.path).not.toHaveBeenCalled();
        })

        it('should redirect to home when user is not authorized to access route but is logged in', function () {
            authServiceMock.isAuthorizedToAccess.andCallFake(function () {return false;});
            authServiceMock.isAuthenticated.andCallFake(function(){ return true; });

            rootScope.$broadcast('$routeChangeStart', nextRoute, currentRoute);

            expect(locationMock.path).toHaveBeenCalledWith('/');
        })

        it('should redirect to login when user is not authorized and is not logged in', function () {
            authServiceMock.isAuthorizedToAccess.andCallFake(function () {return false;});
            authServiceMock.isAuthenticated.andCallFake(function(){ return false; });

            rootScope.$broadcast('$routeChangeStart', nextRoute, currentRoute);

            expect(locationMock.path).toHaveBeenCalledWith('login');
        })
    });
});