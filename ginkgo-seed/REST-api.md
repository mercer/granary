Login Service
=============

### Example of login

``curl -d "username=admin@mailinator.com&password=test" --dump-header -  http://localhost:8080/rest/j_spring_security_check
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9; Path=/rest/; HttpOnly
Content-Length: 88
Date: Tue, 22 Oct 2013 12:47:17 GMT

{"userId":"2c9e12f341e029150141e0291f4e000a","roles":["ADMIN","USER"],"userLocale":null}``


### Listing all the users (if you are logged in as an administrator)

``
curl --cookie "JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9"  --dump-header -  http://localhost:8080/rest/rest/users
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 22 Oct 2013 12:55:05 GMT

[
    {"id": "2c9e12f341e029150141e0291ea50006", "password": null, "email": "romeo@mailinator.com", "firstName": "Romeo", "lastName": "Zeldenthuis", "retypePassword": null, "roles": ["USER"], "confirmed": true, "locale": "en"},
    {"id": "2c9e12f341e029150141e0291ef90008", "password": null, "email": "juliet@mailinator.com", "firstName": "Juliet", "lastName": "Zeldenthuis", "retypePassword": null, "roles": ["USER"], "confirmed": true, "locale": "en"},
    {"id": "2c9e12f341e029150141e0291f4e000a", "password": null, "email": "admin@mailinator.com", "firstName": "Juliet", "lastName": "Zeldenthuis", "retypePassword": null, "roles": ["ADMIN", "USER"], "confirmed": true, "locale": "en"}
]
``

### Get the user details (based on userId)

``
curl --cookie "JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9"  --dump-header -  http://localhost:8080/rest/rest/user/2c9e12f341e029150141e0291f4e000a
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 22 Oct 2013 13:01:43 GMT

{"id":"2c9e12f341e029150141e0291f4e000a","password":null,"email":"admin@mailinator.com","firstName":"Juliet","lastName":"Zeldenthuis","retypePassword":null,"roles":["ADMIN","USER"],"confirmed":true,"locale":"en"}
``

### Do we still need this:

``
curl --cookie "JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9"  --dump-header -  http://localhost:8080/rest/rest/user/info
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 22 Oct 2013 13:04:30 GMT

{"userId":"2c9e12f341e029150141e0291f4e000a","roles":["ADMIN","USER"],"userLocale":null}
``

### Obtain profile information (should we change it to /user/{userId}/profile ?)

``
curl --cookie "JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9"  --dump-header -  http://localhost:8080/rest/rest/user/profile/2c9e12f341e029150141e0291f4e000a
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 22 Oct 2013 13:06:14 GMT

{"pictureUrl":"juliet","firstName":"Juliet","lastName":"Zeldenthuis"}
``

### Edit user

``
curl -v -H "Content-Type: application/json" --cookie "JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9" -X POST  -data '{"id":"2c9e12f341e029150141e0291f4e000a","password":null,"email":"admin@mailinator.com","firstName":"Juliet","lastName":"Zeldenthuis","retypePassword":null,"roles":["ADMIN","USER"],"confirmed":true,"locale":"en"}'  --dump-header -  http://localhost:8080/rest/rest/user
``

### Register user

``
curl -H "Content-Type: application/json" --cookie "JSESSIONID=DFA817D7EFCFAB86CD81CC543E781EA9"  -data '{"id":"2c9e12f341e029150141e0291f4e000a","password":null,"email":"admin@mailinator.com","firstName":"Juliet","lastName":"Zeldenthuis","retypePassword":null,"roles":["ADMIN","USER"],"confirmed":true,"locale":"en"}'  --dump-header -  http://localhost:8080/rest/rest/register_user


15:34:52,401  INFO mvc.method.annotation.RequestMappingHandlerMapping: 179 - Mapped "{[/register_user],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public org.springframework.http.ResponseEntity com.cegeka.ginkgo.rest.user.RegisterUserRestService.doRegisterUser(com.cegeka.ginkgo.application.UserTo)
15:34:52,402  INFO mvc.method.annotation.RequestMappingHandlerMapping: 179 - Mapped "{[/confirm_token/{token}],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public org.springframework.http.ResponseEntity com.cegeka.ginkgo.rest.user.RegisterUserRestService.confirmRegistration(java.lang.String)
15:34:52,404  INFO mvc.method.annotation.RequestMappingHandlerMapping: 179 - Mapped "{[/user],methods=[POST],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public org.springframework.http.ResponseEntity com.cegeka.ginkgo.rest.user.UserRestService.saveUser(com.cegeka.ginkgo.application.UserTo)
``
