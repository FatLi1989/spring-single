package com.novli.spring.security.validate;

import com.novli.spring.security.model.dto.ImageCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerate {

    ImageCode generate(HttpServletRequest request);
}
