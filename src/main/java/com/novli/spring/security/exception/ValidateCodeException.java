package com.novli.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author novLi
 * @date 2019年08月13日 14:30
 */
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
