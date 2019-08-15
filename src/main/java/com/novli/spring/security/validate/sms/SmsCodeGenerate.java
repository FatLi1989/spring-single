package com.novli.spring.security.validate.sms;

import com.novli.spring.security.model.dto.ValidateCode;
import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.validate.ValidateCodeGenerate;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author novLi
 * @date 2019年08月14日 10:50
 */
@Data
public class SmsCodeGenerate implements ValidateCodeGenerate<ValidateCode> {

    private SecurityProperties securityProperties;

    public SmsCodeGenerate(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(smsCode, securityProperties.getCode().getSms().getExpireIn());
    }
}
