package com.novli.spring.security.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author novLi
 * @date 2019年08月15日 15:53
 */
@Data
public class Sms implements Serializable {
    String ACCESS_KEY;
    String SECRET_KEY;
    String templateId;
}
