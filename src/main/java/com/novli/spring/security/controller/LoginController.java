package com.novli.spring.security.controller;

import com.novli.spring.security.support.SimpleResponse;
import io.swagger.annotations.Api;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@Api(value = "login", tags = "登录接口")
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String SignIn() {
        System.out.println("登录");
        System.out.println("");
        return "登录嘿嘿";
    }


    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public SimpleResponse me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return SimpleResponse.builder().data(authentication).build();
    }

    @RequestMapping(value = "/me1", method = RequestMethod.GET)
    public SimpleResponse me1(Authentication authentication) {
        return SimpleResponse.builder().data(authentication).build();
    }

    @RequestMapping(value = "/me2", method = RequestMethod.GET)
    public SimpleResponse me2(@AuthenticationPrincipal UserDetails userDetails) {
        return SimpleResponse.builder().data(userDetails).build();
    }
}
