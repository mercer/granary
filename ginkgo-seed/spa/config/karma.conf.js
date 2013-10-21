module.exports = function (config) {
    config.set({
        basePath: '../',

        files: [
            'app/bower_components/jquery/jquery.min.js',
            'app/bower_components/angular/angular.js',
            'app/bower_components/angular-ui-bootstrap-bower/ui-bootstrap.min.js',
            'app/bower_components/angular-ui-utils/modules/utils.js',
            'app/lib/angular/angular-*.js',
            'test/lib/angular/angular-mocks.js',
            'app/js/**/*.js',
            'app/bower_components/lodash/dist/lodash.min.js',
            'test/unit/**/*.js',

            //include directives templates
            'app/directive/**/*.html'
        ],

        preprocessors: {
            'app/directive/**/*.html': ['ng-html2js']
        },

        autoWatch: true,

        frameworks: ['jasmine'],

        browsers: ['Chrome'],

        plugins: [
            'karma-junit-reporter',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine',
            'karma-ng-html2js-preprocessor'
        ],

        junitReporter: {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }
    })
}
