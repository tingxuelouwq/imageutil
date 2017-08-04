package com.kevin.service.impl;

import com.kevin.service.MarkService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @类名: MarkServiceImpl
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 9:59
 * @版本：1.0
 * @描述：文字水印业务层实现类
 */
@Service("textMarkService")
public class TextMarkServiceImpl implements MarkService {

    @Override
    public String watermark(File imageFile, String imageFileName, String uploadPath, String realUploadPath) {
        String logoFileName = "logo_" + imageFileName;
        OutputStream os = null;

        try {
            Image image = ImageIO.read(imageFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            // 创建图片缓存对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建绘图工具对象
            Graphics2D g = bufferedImage.createGraphics();
            // 使用绘图工具将原图绘制到图片缓存对象
            g.drawImage(image, 0, 0, null);

            // 设置水印文字字体信息
            g.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            // 设置水印文字言责
            g.setColor(FONT_COLOR);

            int markWidth = FONT_SIZE * getTextLength(MARK_TEXT);
            int markHeight = FONT_SIZE;

            // 水印的高度和宽度之差
            int widthDif = width - markWidth;
            int heightDir = height - markHeight;

            int x = X;
            int y = Y;

            // 判断设置的值是否大于图片大小
            if (x > widthDif) {
                x = widthDif;
            }
            if (y > heightDir) {
                y = heightDir;
            }

            // 设置水印文字的透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));

            // 使用绘图工具将水印绘制到图片缓存对象
            g.drawString(MARK_TEXT, x, y + FONT_SIZE);

            g.dispose();

            os = new FileOutputStream(realUploadPath + File.separator + logoFileName);
            // 创建图像编码工具类
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            // 使用图片编码工具类，输出图片缓存对象到目标图片文件
            en.encode(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return uploadPath + File.separator + logoFileName;
    }

    /**
     * @方法名：getTextLength
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/3 10:47
     * @描述：获取文本长度，汉字为1:1，英文和数字为2:1
     * @param text
     * @return int
     * @exception
     */
    private int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = ((length % 2 == 0) ? (length / 2) : (length / 2 + 1));
        return length;
    }
}
