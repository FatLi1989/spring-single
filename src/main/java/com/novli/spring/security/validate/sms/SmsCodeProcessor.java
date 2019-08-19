package com.novli.spring.security.validate.sms;

import com.novli.spring.security.model.dto.ValidateCode;
import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.validate.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author novLi
 * @date 2019年08月14日 12:41
 */
@Slf4j
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, SmsCodeSender> map;

    @Autowired
    SecurityProperties securityProperties;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        SmsCodeSender smsCodeSender =   map.get(securityProperties.getCode().getSms().getType() + SmsCodeSender.class.getSimpleName());
        smsCodeSender.sendLogin(mobile, validateCode.getCode());
    }

    @Override
    protected void save(ServletWebRequest request, ValidateCode validateCode) {
        sessionStrategy.setAttribute(new ServletRequestAttributes(request.getRequest()), SESSION_SMS_KEY, validateCode);

    }
}
