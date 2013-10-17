package com.cegeka.ginkgo.rest.user;

import com.cegeka.ginkgo.application.UserTo;
import com.cegeka.ginkgo.application.security.LoggedInUser;
import com.cegeka.ginkgo.application.security.UserDetailsTO;
import com.cegeka.ginkgo.application.UserFacade;
import com.cegeka.ginkgo.application.UserProfileTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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

    @RequestMapping("/users")
    @ResponseBody
    public List<UserTo> getUsers(){
        return userFacade.getUsers();
    }
}
