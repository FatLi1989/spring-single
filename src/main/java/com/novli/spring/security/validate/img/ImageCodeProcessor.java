package com.novli.spring.security.validate.img;

import com.novli.spring.security.model.dto.ImageCode;
import com.novli.spring.security.validate.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author novLi
 * @date 2019年08月14日 12:41
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
