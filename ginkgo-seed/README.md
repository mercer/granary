The Ginkgo Seed
===============

This seed contains AngularJS, consuming a REST service build on top of SpringMVC, secured by Spring Security.


### Setup guide

You'll need to have these installed:
* jdk 1.6
* maven 3
* node and npm ~0.10.*
* postgresql
* system variable CHROME_BIN set to chrome executable

Take these steps
* git clone this repository
* mvn clean install
* mvn tomcat:run from /rest
* mvn tomcat:run -Dmaven.tomcat.port=8081 from /spa
