package com.novli.spring.security.model.dto;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author novLi
 * @date 2019年08月13日 12:25
 */
@Data
public class ImageCode extends ValidateCode{

    private BufferedImage bufferedImage;

    public ImageCode(BufferedImage bufferedImage, String code, int expireTime) {
        super(code, expireTime);
        this.bufferedImage = bufferedImage;
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(super.expireTime);
    }
}
