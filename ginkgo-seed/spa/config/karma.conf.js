module.exports = function (config) {
    config.set({
        basePath: '../',

        files: [
            'http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js',
            'app/lib/angular/angular.js',
            'http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.6.0.min.js',
            'http://angular-ui.github.io/ui-utils/build/ui-utils.min.js',
            'app/lib/angular/angular-*.js',
            'test/lib/angular/angular-mocks.js',
            'app/js/**/*.js',
            'app/lib/lodash/lodash.min.js',
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
