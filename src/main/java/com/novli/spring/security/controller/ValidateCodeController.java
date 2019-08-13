package com.novli.spring.security.controller;

import com.novli.spring.security.model.dto.CodeImage;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author novLi
 * @date 2019年08月13日 12:39
 */
@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/img")
    public void validateImgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码图片
        CodeImage codeImage = createImage(request);
        //codeImage存到session中  * 同一个请求
        sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY, codeImage);
        //图片写入到输出流
        ImageIO.write(codeImage.getBufferedImage(), "JPEG", response.getOutputStream());
    }

    private CodeImage createImage(HttpServletRequest request) {
        //生成图片宽度
        int width = 80;
        //生成图片高度
        int height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("宋体", Font.BOLD, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder verifyCode = new StringBuilder(5);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            char c = VERIFY_CODES.charAt(rand.nextInt(VERIFY_CODES.length() - 1));
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(c), 13 * i + 6, 16);
            verifyCode.append(c);
        }
        g.dispose();
        return new CodeImage(image, verifyCode.toString(), 60);
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
