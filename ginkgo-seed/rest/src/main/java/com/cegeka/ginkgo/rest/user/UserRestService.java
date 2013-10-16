package com.cegeka.ginkgo.rest.user;

import com.cegeka.ginkgo.infrastructure.security.LoggedInUser;
import com.cegeka.ginkgo.infrastructure.security.UserDetailsTO;
import com.ginkgo.application.facade.UserFacade;
import com.ginkgo.application.facade.UserProfileTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserRestService {

    @Resource
    private LoggedInUser loggedInUser;

    @Autowired
    private UserFacade userFacade;

    @RequestMapping("/user/info")
    @ResponseBody
    public UserDetailsTO getLoggedInUser() {
        return loggedInUser.getLoggedInUserToBeSentToGUI();
    }

    @RequestMapping("/user/profile/{userId}")
    @ResponseBody
    public UserProfileTo getProfile(@PathVariable("userId") Long userId) {
        return userFacade.getProfile(userId);
    }
}
