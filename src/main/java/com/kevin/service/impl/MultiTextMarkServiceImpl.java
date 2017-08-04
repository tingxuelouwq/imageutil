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
 * @类名: MultiTextMarkServiceImpl
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 14:41
 * @版本：1.0
 * @描述：多个文字水印业务层实现类
 */
@Service("MultiTextMarkService")
public class MultiTextMarkServiceImpl implements MarkService {
    @Override
    public String watermark(File imageFile, String imageFileName, String uploadPath, String realUploadPath) {
        String logoFileName = "logo_" + imageFileName;
        OutputStream os = null;

        try {
            Image image = ImageIO.read(imageFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            // 创建缓存图片对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建绘图工具对象
            Graphics2D g = bufferedImage.createGraphics();
            // 使用绘制工具对象将原图绘制到缓存图片对象中
            g.drawImage(image, 0, 0, null);

            // 设置水印文字字体信息
            g.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            // 设置水印文字颜色
            g.setColor(FONT_COLOR);

            // 获取水印文字的宽度和高度
            int markWidth = FONT_SIZE * getTextLength(MARK_TEXT);
            int markHeight = FONT_SIZE;

            // 设置水印透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));

            // 旋转图片
            g.rotate(Math.toRadians(30), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

            int x = -width / 2;
            int y = -height / 2;

            // 水印之间的间隔
            int xmove = 40;
            int ymove = 40;

            // 循环添加
            while (x < width * 1.5) {
                y = -height / 2;
                while (y < height * 1.5) {
                    g.drawString(MARK_TEXT, x, y);
                    y += markHeight + ymove;
                }
                x += markWidth + xmove;
            }

            g.dispose();

            os = new FileOutputStream(realUploadPath + File.separator + logoFileName);
            // 创建图像编码工具类
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            // 使用图像编码工具类将缓存图片对象输出到目标图片文件中
            en.encode(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
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
