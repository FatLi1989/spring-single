package com.novli.spring.security.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author novLi
 * @date 2019年08月13日 12:25
 */
@Data
@Builder
@NoArgsConstructor
public class CodeImage {

    private BufferedImage bufferedImage;

    private String code;

    private LocalDateTime expireTime;

    public CodeImage(BufferedImage bufferedImage, String code, LocalDateTime expireTime) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = expireTime;
    }

    public CodeImage(BufferedImage bufferedImage, String code, int expireTime) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
