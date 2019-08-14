package com.novli.spring.security.properties;

import lombok.Data;

@Data
public class ImageCodeProperties {

    private int width;

    private int height;

    private int length;

    private int expireIn;

    private String url;


}
