package com.novli.spring.security.filter;

import com.novli.spring.security.authentication.SecurityFailureHandler;
import com.novli.spring.security.exception.ValidateCodeException;
import com.novli.spring.security.model.dto.ValidateCode;
import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.validate.ValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author novLi
 * @date 2019年08月13日 14:32
 */
@Slf4j
@Component
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    SecurityFailureHandler securityFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //加载全部配置文件中需要拦截校验的url,并放到集合中
        String[] configUrls = StringUtils.split(securityProperties.getCode().getImage().getUrl(), ",");
        Stream.of(configUrls).forEach(t -> urls.add(t));
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI());
        //判断请求的url是否和配置的拦截路径匹配，匹配则进行校验
        boolean action = false;
        for (String url :urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }
        //校验验证码,此过滤器只会执行一次
        if (action) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                securityFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ValidateCode validateCode = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_SMS_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空！");
        }
        if (validateCode == null) {
            throw new ValidateCodeException("验证码不存在！");
        }
        if (validateCode.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_SMS_KEY);
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(validateCode.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不正确！");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_SMS_KEY);
    }
}
