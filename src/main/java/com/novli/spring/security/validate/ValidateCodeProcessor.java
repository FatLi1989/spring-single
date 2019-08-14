package com.novli.spring.security.validate;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 验证码处理器
 *
 * @author Liyanpeng
 * @date 2019/8/14 11:37
 **/
public interface ValidateCodeProcessor {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 生成验证码
     **/
    void generate(ServletWebRequest request) throws Exception;

    void validate(ServletWebRequest request);

}
