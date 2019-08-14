package com.novli.spring.security.controller;

import com.novli.spring.security.model.dto.ImageCode;
import com.novli.spring.security.model.dto.ValidateCode;
import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.util.code.sms.SmsCodeSender;
import com.novli.spring.security.validate.ValidateCodeGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author novLi
 * @date 2019年08月13日 12:39
 */
@RestController
public class ValidateCodeController {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    ValidateCodeGenerate imageCodeGenerate;

    @Autowired
    ValidateCodeGenerate smsCodeGenerate;

    @Autowired
    SmsCodeSender smsCodeSender;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/img")
    public void validateImgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码图片
        ImageCode imageCode = imageCodeGenerate.createImage(request);
        //codeImage存到session中  * 同一个请求
        sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY, imageCode);
        //图片写入到输出流
        ImageIO.write(imageCode.getBufferedImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/sms/img")
    public void validateSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        //生成验证码图片
        ValidateCode smsCode = smsCodeGenerate.createImage(request);
        //codeImage存到session中  * 同一个请求
        sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY, smsCode);
        //请求中获取手机验证码
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        //发送手机验证码
        smsCodeSender.send(mobile, smsCode.getCode());
    }
}
