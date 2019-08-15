package com.novli.spring.security.validate.sms;

public interface SmsCodeSender{


    void sendLogin(String mobile, String code);
}
