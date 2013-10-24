package com.cegeka.ginkgo.rest.user;

import com.cegeka.ginkgo.application.security.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ConcurrentHashMap;

@Controller
public class CorsController {
    private static Logger logger = LoggerFactory.getLogger(CorsController.class);
    @Autowired
    LoggedInUser loggedInUser;
    private ConcurrentHashMap<String, MethodCounts> counts = new ConcurrentHashMap<String, MethodCounts>();

    @RequestMapping(value = "/cors/test")
    @ResponseBody
    public ResponseEntity confirmRegistrationGet() {

        MethodCounts counters = getCounters();
        counters.incrementGetCount();

        ResponseEntity<MethodCounts> success = new ResponseEntity<MethodCounts>(counters, addAccessControlAllowOrigin(), HttpStatus.OK);
        return success;
    }

    @RequestMapping(value = "/cors/test", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity confirmRegistrationPost() {

        MethodCounts counters = getCounters();
        counters.incrementPostCount();

        ResponseEntity<MethodCounts> success = new ResponseEntity<MethodCounts>(counters, addAccessControlAllowOrigin(), HttpStatus.OK);
        return success;
    }

    @RequestMapping(value = "/cors/test", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity confirmRegistrationPut() {

        MethodCounts counters = getCounters();
        counters.incrementPutCount();

        ResponseEntity<MethodCounts> success = new ResponseEntity<MethodCounts>(counters, addAccessControlAllowOrigin(), HttpStatus.OK);
        return success;
    }
    @RequestMapping(value = "/cors/test", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity confirmRegistrationDelete() {

        MethodCounts counters = getCounters();
        counters.incrementDeleteCount();

        ResponseEntity<MethodCounts> success = new ResponseEntity<MethodCounts>(counters, addAccessControlAllowOrigin(), HttpStatus.OK);
        return success;
    }

    private MethodCounts getCounters() {
        String userId = loggedInUser.getCustomUserDetails().getUserId();
        if (!counts.contains(userId)) {
            counts.putIfAbsent(userId, new MethodCounts());
        }
        return counts.get(userId);
    }

    private HttpHeaders addAccessControlAllowOrigin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
    }

    public static class MethodCounts {
        private int getCount = 0;
        private int postCount = 0;
        private int putCount = 0;
        private int deleteCount = 0;

        public void incrementGetCount() {
            getCount++;
        }

        public void incrementPostCount() {
            postCount++;
        }

        public void incrementPutCount() {
            putCount++;
        }
        public void incrementDeleteCount() {
            deleteCount++;
        }

        public int getPutCount() {
            return putCount;
        }

        public int getGetCount() {
            return getCount;
        }

        public int getPostCount() {
            return postCount;
        }

        public int getDeleteCount() {
            return deleteCount;
        }
    }


}
