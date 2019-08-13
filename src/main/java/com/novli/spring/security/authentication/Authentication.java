package com.novli.spring.security.authentication;

import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
public class Authentication {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    SecurityProperties securityProperties;

    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) {
        //request请求会存放到requestCache中
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            //获取请求目标地址
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是: {}", targetUrl);
            //如果请求中包含.html
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                try {
                    redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return SimpleResponse.builder().data("访问的服务器需要身份认证, 请引导用户到登录页").build();
    }

}
