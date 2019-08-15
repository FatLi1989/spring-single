package com.novli.spring.security.validate.sms.qiniu;

import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.validate.sms.SmsCodeSender;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novLi
 * @date 2019年08月15日 17:03
 */
@Component("qiNiuSmsCodeSender")
public class QiNiuSmsCodeSender implements SmsCodeSender {

    @Autowired
    private SecurityProperties securityProperties;

 /*   public QiNiuSmsCodeSender(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }*/

    @Override
    public void sendLogin(String mobile, String code) {
        // 设置需要操作的账号的AK和SK
        try {
            Auth auth = Auth.create(securityProperties.getCode().getSms().getQiniu().getAccessKey(), securityProperties.getCode().getSms().getQiniu().getSecretKey());
            Map<String, String> map = new HashMap<>(1);
            map.put("code", code);
            // 实例化一个SmsManager对象
            SmsManager smsManager = new SmsManager(auth);
            Response resp = smsManager.sendMessage(securityProperties.getCode().getSms().getQiniu().getTemplateId(), new String[]{"18701429557"}, map);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
