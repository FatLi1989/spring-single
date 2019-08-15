package com.novli.spring.security.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author novLi
 * @date 2019年08月14日 11:26
 */
@ToString
public class SmsCodeProperties {

    @Setter
    @Getter
    private String Type;

    @Setter
    @Getter
    private int length;

    @Setter
    @Getter
    private int expireIn;

    @Setter
    @Getter
    private String url;

    @Setter
    @Getter
    private QiNiuProperties qiniu = new QiNiuProperties();

    public SmsCodeProperties() {
    }
}
