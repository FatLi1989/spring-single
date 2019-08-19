package com.novli.spring.security.validate;

import com.novli.spring.security.model.dto.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author novLi
 * @date 2019年08月14日 12:17
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerate> validateCodeGenerates;

    /**
     * 生成验证码
     *
     * @author Liyanpeng
     * @date 2019/8/14 12:32
     **/

    @Override
    public void generate(ServletWebRequest request) throws Exception {
        C validateCode = create(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    private C create(ServletWebRequest request) {
        //调用实现AbstractValidateCodeProcessor 的两个类 smsValidateCodeProcessor   imageValidateCodeProcessor
        String type = StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
        type = ValidateCodeGenerate.class.getSimpleName().replace("Validate", type);
        ValidateCodeGenerate validateCodeGenerate = validateCodeGenerates.get(type);
        return (C) validateCodeGenerate.generate(request.getRequest());
    }

    /**
     * 发送验证码
     *
     * @param request
     * @param validateCode
     * @throws Exception 异常
     * @author Liyanpeng
     * @date 2019/8/14 14:14
     **/
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    protected abstract void save(ServletWebRequest request, C validateCode);



    @Override
    public void validate(ServletWebRequest request) {

    }
}
