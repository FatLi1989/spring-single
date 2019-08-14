package com.novli.spring.security.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novLi
 * @date 2019年08月14日 11:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeProperties {

    private int length;

    private int expireIn;

    private String url;
}
