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
 * @类名: ImageMarkServiceImpl
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 14:23
 * @版本：1.0
 * @描述：图片水印业务层实现类
 */
@Service("imageMarkService")
public class ImageMarkServiceImpl implements MarkService {
    @Override
    public String watermark(File imageFile, String imageFileName, String uploadPath, String realUploadPath) {
        String logoFileName = "logo_" + imageFileName;
        OutputStream os = null;

        // 图片地址
        String logoPath = realUploadPath + File.separator + LOGO;

        try {
            Image image = ImageIO.read(imageFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            // 创建缓存图片对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建绘图工具对象
            Graphics2D g = bufferedImage.createGraphics();
            // 使用绘图工具对象将原图绘制到图片缓存对象中
            g.drawImage(image, 0, 0, null);

            // 读取logo图片
            File logoFile = new File(logoPath);
            Image logoImage = ImageIO.read(logoFile);

            // 获取logo图片的宽度和高度
            int markWidth = logoImage.getWidth(null);
            int markHeight = logoImage.getHeight(null);

            // 原图和logo图片的高度和宽度之差
            int widthDif = width - markWidth;
            int heightDif = height - markHeight;

            int x = X;
            int y = Y;

            // 判断设置的值是否大于图片大小
            if (x > widthDif) {
                x = widthDif;
            }
            if (y > heightDif) {
                y = heightDif;
            }

            // 设置水印的透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));

            // 添加水印
            g.drawImage(logoImage, x, y, null);

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
}
