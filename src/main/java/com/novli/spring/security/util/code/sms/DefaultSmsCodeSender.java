package com.novli.spring.security.util.code.sms;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("像手机发  {} 发送验证码 {} ", mobile, code);
    }
}
