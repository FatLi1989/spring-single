package com.novli.spring.security.validate.img;

import com.novli.spring.security.model.dto.ImageCode;
import com.novli.spring.security.properties.SecurityProperties;
import com.novli.spring.security.validate.ValidateCodeGenerate;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCodeGenerate implements ValidateCodeGenerate {


    public ImageCodeGenerate(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    private SecurityProperties securityProperties;

    public static final String VERIFY_CODES = "1234567890abcdefghjklmnpqrstuvwxyz";

    @Override
    public ImageCode generate(HttpServletRequest request) {
        //生成图片宽度
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());
        //生成图片高度
        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());

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
        for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
            char c = VERIFY_CODES.charAt(rand.nextInt(VERIFY_CODES.length() - 1));
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(c), 13 * i + 6, 16);
            verifyCode.append(c);
        }
        g.dispose();
        return new ImageCode(image, verifyCode.toString(), securityProperties.getCode().getImage().getExpireIn());
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
