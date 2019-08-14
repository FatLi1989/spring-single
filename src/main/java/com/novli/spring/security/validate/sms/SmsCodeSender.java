package com.novli.spring.security.validate.sms;

public interface SmsCodeSender {


    void send(String mobile, String code);
}
