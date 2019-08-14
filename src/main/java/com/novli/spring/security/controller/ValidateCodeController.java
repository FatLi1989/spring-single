package com.novli.spring.security.controller;

import com.novli.spring.security.validate.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author novLi
 * @date 2019年08月13日 12:39
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;


    @GetMapping("/code/{type}")
    public void validateImgCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type) throws Exception {
        //根据不同类型调用验证码处理方式
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(name);
        validateCodeProcessor.generate(new ServletWebRequest(request, response));
    }
}
