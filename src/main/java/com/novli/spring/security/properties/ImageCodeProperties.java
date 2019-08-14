package com.novli.spring.security.properties;

import lombok.Data;

@Data
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }
    private int width;

    private int height;



}
