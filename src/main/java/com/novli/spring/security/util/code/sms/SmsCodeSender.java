package com.novli.spring.security.util.code.sms;

public interface SmsCodeSender {


    void send(String mobile, String code);
}
