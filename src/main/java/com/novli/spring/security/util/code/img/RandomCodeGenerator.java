package com.novli.spring.security.util.code.img;

import com.novli.spring.security.model.dto.CodeImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author novLi
 * @date 2019年08月13日 13:03
 */
@Data
@Builder
@AllArgsConstructor
public class RandomCodeGenerator {




    private static final char[] CHARS = "abcdefghijkmnpqrstovwxyz0123456789".toCharArray();
    private int length;
    private int fontSize = 14;
    private String randomCode = null;

    public RandomCodeGenerator(int length) {
        this.length = length;
    }

    public RandomCodeGenerator() {
        this(4);
    }

    public String getRandomCode() {
        if (randomCode == null) {
            // 生成验证码
            this.randomCode = initRandomCode();
        }
        return randomCode;
    }

    /**
     * 初始化验证码
     **/
    private String initRandomCode() {
        Set<Character> chars = new HashSet<>();
        Random r = new Random();
        while(chars.size() <= 5) {
            chars.add(CHARS[r.nextInt(CHARS.length)]);
        }

        char[] cs = new char[6];
        int index = 0;
        for (char c : chars) {
            cs[index++] = c;
        }
        return new String(cs).toUpperCase();
    }

    public  CodeImage generator(HttpServletRequest request) throws IOException {
        int width = length * (fontSize + 2) + 10;
        int height = fontSize + 10;
        //创建一个缓存图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //得到画笔对象
        Graphics2D g = (Graphics2D)image.getGraphics();
        //设置前景色
        g.setColor(Color.PINK);
        //使用前景色填充距形
        g.fillRect(0, 0, width, height);
        Random r = new Random();
        //绘制干扰线
        for(int i = 0; i < 3; i++){
            //生成随机颜色
            Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            g.setColor(c);
            //画线
            g.drawLine(r.nextInt(width / 2), r.nextInt(height), r.nextInt(width / 2) + width / 2, r.nextInt(height));
        }

        g.setColor(Color.WHITE);
        Font font = new Font("Cooper Std", Font.PLAIN, fontSize);
        //设置字体
        g.setFont(font);
        if(randomCode == null || ("").equals(randomCode)) {
            this.randomCode = getRandomCode();
        }
        for(int i = 0; i < randomCode.length(); i++){
            //画字符
            g.drawString(randomCode.substring(i, i+1), 8 + fontSize * i, height / 2 + fontSize / 2);
        }
        //输出
        return new CodeImage(image, randomCode, 60);

    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
