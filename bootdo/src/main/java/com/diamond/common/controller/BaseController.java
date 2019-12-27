package com.diamond.common.controller;

import org.springframework.stereotype.Controller;
import com.diamond.common.utils.ShiroUtils;
import com.diamond.system.entity.User;

@Controller
public class BaseController {

    public User getUser() {
        return ShiroUtils.getUser();
    }

    public Long getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

}