package com.novli.spring.security.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author novLi
 * @date 2019年08月15日 17:16
 */
@ToString
public class QiNiuProperties {

    @Setter
    @Getter
    private String templateId;

    @Setter
    @Getter
    private String accessKey;

    @Setter
    @Getter
    private String secretKey;

    public QiNiuProperties() {
    }
}
