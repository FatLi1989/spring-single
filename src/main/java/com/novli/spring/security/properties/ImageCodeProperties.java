package com.novli.spring.security.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    @Setter
    @Getter
    private int width;

    @Setter
    @Getter
    private int height;




}
