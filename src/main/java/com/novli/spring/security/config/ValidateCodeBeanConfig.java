package com.novli.spring.security.config;

import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.util.code.sms.DefaultSmsCodeSender;
import com.novli.spring.security.util.code.sms.SmsCodeSender;
import com.novli.spring.security.validate.ImageCodeGenerate;
import com.novli.spring.security.validate.ValidateCodeGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    SecurityProperties securityProperties;

    /**
     * 不存在imageCodeGenerate的时候加载
     **/
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerate")
    public ValidateCodeGenerate imageCodeGenerate() {
        ValidateCodeGenerate validateCodeGenerate = new ImageCodeGenerate(securityProperties);
        return validateCodeGenerate;
    }


    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
