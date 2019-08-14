package com.novli.spring.security.properties;

import lombok.Data;

@Data
public class BrowserProperties {
    private String loginPage;

    private int rememberMeSeconds;

}
