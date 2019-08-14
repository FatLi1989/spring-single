package com.novli.spring.security.validate;


import com.novli.spring.security.model.dto.ValidateCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerate<C extends ValidateCode> {
    /**
     * 生成验证码
     *
     * @author Liyanpeng
     * @date 2019/8/14 13:02
     * @param request
     * @return C
     **/
    C generate(HttpServletRequest request);
}
