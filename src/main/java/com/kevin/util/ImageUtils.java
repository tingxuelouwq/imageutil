package com.kevin.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @类名：ImageUtils
 * @包名：com.kevin.demo
 * @作者：kevin
 * @时间：2017/8/3 21:38
 * @版本：1.0
 * @描述：图片处理工具类
 */
public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 缩放类型
     */
    private static final int SCALE_TYPE_WIDTH = 1;
    private static final int SCALE_TYPE_HEIGHT = 2;
    private static final int SCALE_TYPE_MIN = 3;
    private static final int SCALE_TYPE_MAX = 4;

    /** 随机数生成器 **/
    private static final Random rnd = new Random();

    /**
     * @方法名：getSize
     * @作者：kevin
     * @时间：2017/8/3 22:03
     * @描述：获取图片的宽度和高度
     * @param src
     * @return int[]
     */
    public static int[] getSize(String src) {
        File image = null;
        BufferedImage bufferedImage = null;
        try {
            image = new File(src);
            if (!image.exists()) {
                return null;
            }
            bufferedImage = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] size = {width, height};
        return size;
    }

    /**
     * @方法名：scale
     * @作者：kevin
     * @时间：2017/8/3 22:57
     * @描述：按照指定的长宽缩放图片，维持原长宽比不变
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param width 缩放后的图片宽度
     * @param height 缩放后的图片高度
     * @param scaleType 缩放类型，取值为SCALE_TYPE_WIDTH(按宽度缩放)、SCALE_TYPE_HEIGHT(按高度缩放)、SCALE_TYPE_MIN(按比例小的缩放)、SCALE_TYPE_MAX(按比例大的缩放)
     * @return void
     */
    public static void scale(String src, String des, int width, int height, int scaleType) {
        try {
            int[] size = getSize(src);
            int srcWidth = size[0];
            int srcHeight = size[1];
            double widthScale = getScale(width, srcWidth);
            double heightScale = getScale(height, srcHeight);
            double scale = 1.0;

            switch (scaleType) {
                case SCALE_TYPE_WIDTH:
                    scale = widthScale;
                    break;
                case SCALE_TYPE_HEIGHT:
                    scale = heightScale;
                    break;
                case SCALE_TYPE_MIN:
                    scale = Math.min(widthScale, heightScale);
                    break;
                case SCALE_TYPE_MAX:
                    scale = Math.max(widthScale, heightScale);
                    break;
                default:
                    scale = Math.min(widthScale, heightScale);
                    break;
            }

            Thumbnails.of(src).scale(scale).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：scale
     * @作者：kevin
     * @时间：2017/8/3 23:44
     * @描述：按照指定的长宽缩放图片，不维持原长宽比不变
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param width 缩放后的图片宽度
     * @param height 缩放后的图片高度
     * @return void
     */
    public static void scale(String src, String des, int width, int height) {
        try {
            Thumbnails.of(src).size(width, height).keepAspectRatio(false).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：scale
     * @作者：kevin
     * @时间：2017/8/3 23:49
     * @描述：按指定的缩放比例缩放图片，维持原长宽比不变
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param scale 缩放比例
     * @return void
     */
    public static void scale(String src, String des, double scale) {
        try {
            Thumbnails.of(src).scale(scale).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：clip
     * @作者：kevin
     * @时间：2017/8/4 0:03
     * @描述：裁剪图片
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param position 裁剪位置
     * @param width 裁剪区域宽度
     * @param height 裁剪区域高度
     * @param desWidth 目标图片宽度
     * @param desHeight 目标图片高度
     * @return void
     */
    public static void clip(String src, String des, Position position, int width, int height, int desWidth, int desHeight) {
        try {
            Thumbnails.of(src).sourceRegion(position, width, height).size(desWidth, desHeight).keepAspectRatio(false).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：clip
     * @作者：kevin
     * @时间：2017/8/4 0:09
     * @描述：裁剪图片
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param x 裁剪区域左上角横坐标
     * @param y 裁剪区域左上角纵坐标
     * @param width 裁剪区域宽度
     * @param height 裁剪区域高度
     * @param width 裁剪区域宽度
     * @param height 裁剪区域高度
     * @param desWidth 目标图片宽度
     * @param desHeight 目标图片高度
     * @return void
     */
    public static void clip(String src, String des, int x, int y, int width, int height, int desWidth, int desHeight) {
        try {
            Thumbnails.of(src).sourceRegion(x, y, width, height).size(desWidth, desHeight).keepAspectRatio(false).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：rotate
     * @作者：kevin
     * @时间：2017/8/4 0:17
     * @描述：缩放并旋转图片
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param scale 缩放比例
     * @param angle 旋转角度，正数表示顺时针，负数表示逆时针
     * @return void
     */
    public static void rotate(String src, String des, double scale, double angle) {
        try {
            Thumbnails.of(src).scale(scale).rotate(angle).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @方法名：watermark
     * @作者：kevin
     * @时间：2017/8/4 0:42
     * @描述：打图片水印
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param scale 缩放比例
     * @param position 水印位置
     * @param watermark 水印图片路径
     * @param opacity 透明度
     * @return void
     */
    public static void watermark(String src, String des, double scale,
                                 Position position, String watermark, float opacity) {
        try {
            Thumbnails.of(src).scale(scale).watermark(position, ImageIO.read(new File(watermark)), opacity).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：watermark
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/4 10:41
     * @描述：打文字水印
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param scale 缩放比例
     * @param position 水印图片位置，1-9分别表示TOP_LEFT、TOP_CENTER、TOP_RIGHT、CENTER_LEFT、CENTER_CENTER、CENTER_RIGHT、BOTTOM_LEFT、BOTTOM_CENTER、BOTTOM_RIGHT，0表示随机位置
     * @param text 水印文字
     * @param fontName 字体
     * @param fontStyle 字体样式
     * @param fontSize 字体大小
     * @param fontColor 字体颜色
     * @param outlineSize 边框大小
     * @param outlineColor 边框颜色
     * @param opacity 水印透明度
     * @return void
     * @exception
     */
    public static void watermark(String src, String des, double scale, int position,
                                 String text, String fontName, int fontStyle, int fontSize, String fontColor,
                                 int outlineSize, String outlineColor,
                                 float opacity) {
        File imageFile = new File(src);
        if (!imageFile.exists()) {
            log.error("目标路径文件不存在, des: {}", des);
            return;
        }

        if (position < 0 || position > 9) {
            log.error("水印文字位置取值为[0-9], position: {}", position);
            return;
        }

        OutputStream os = null;
        try {
            Image image = ImageIO.read(imageFile);
            // 原图的尺寸
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            // 水印文字的尺寸
            int markWidth = fontSize * getTextLength(text);
            int markHeight = fontSize;

            // 1、创建缓存图片对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 2、创建绘图工具对象
            Graphics2D g = bufferedImage.createGraphics();
            // 3、使用绘图工具对象将原图绘制到缓存图片对象
            g.drawImage(image, 0, 0, (int)(width * scale), (int)(height * scale), null);

            // 4、设置文字水印信息
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 去锯齿
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);    // 不改变几何结构
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacity));   // 设置透明度

            int[] coordinates = getCoordinates(width, height, text, markWidth, markHeight, position);   // 获取水印文字位置
            FontRenderContext fontRenderContext = g.getFontRenderContext(); // 获取文字渲染上下文
            TextLayout textLayout = new TextLayout(text, new Font(fontName, fontStyle, fontSize), fontRenderContext);   // 设置水印文字样式
            Shape shape = textLayout.getOutline(AffineTransform.getTranslateInstance(coordinates[0], coordinates[1] + fontSize));   // 获取水印文字轮廓信息
            if (outlineSize >= 0 && !StringUtils.isEmpty(outlineColor)) {  // 设置轮廓
                g.setStroke(new BasicStroke(outlineSize));
                g.setColor(Color.decode(outlineColor));
            }
            g.draw(shape);  // 绘制轮廓
            g.setColor(Color.decode(fontColor));    // 设置字体颜色
            g.fill(shape);  // 绘制水印文字
            // 5、销毁绘图工具对象
            g.dispose();

            os = new FileOutputStream(des);
            // 6、创建图像编码工具类
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            ///7、使用图片编码工具类，输出图片缓存对象到目标图片文件
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
    }

    /**
     * @方法名：convert
     * @作者：kevin
     * @时间：2017/8/4 0:42
     * @描述：转换图片格式
     * @param src 原图片路径
     * @param des 目标图片路径，后缀需要与目标图片格式一致，否则会自动添加目标图片格式作为后缀
     * @param scale 缩放比例
     * @param format 目标图片格式
     * @return void
     */
    public static void convert(String src, String des, double scale, String format) {
        try {
            Thumbnails.of(src).scale(scale).outputFormat(format).toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法名：getOutputStream
     * @作者：kevin
     * @时间：2017/8/4 0:51
     * @描述：输出图片到OutputStream
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param scale 缩放比例
     * @return java.io.OutputStream
     */
    public static OutputStream getOutputStream(String src, String des, double scale) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(des);
            Thumbnails.of(src).scale(scale).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * @方法名：getBufferedImage
     * @作者：kevin
     * @时间：2017/8/4 0:53
     * @描述：输出图片到BufferedImage
     * @param src 原图片路径
     * @param des 目标图片路径
     * @param scale 缩放比例
     * @return java.awt.image.BufferedImage
     */
    public static BufferedImage getBufferedImage(String src, String des, double scale) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = Thumbnails.of(src).scale(scale).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }


    /**
     * @方法名：getScale
     * @作者：kevin
     * @时间：2017/8/3 22:48
     * @描述：获取num1/num2的比例
     * @param num1
     * @param num2
     * @return double
     */
    private static double getScale(double num1, double num2) {
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        double result = b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
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
    private static int getTextLength(String text) {
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

    /**
     * @方法名：getCoordinates
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/4 10:23
     * @描述：获取水印文字在原图中的位置
     * @param width 原图宽度
     * @param height 原图高度
     * @param text 水印文字
     * @param markWidth 水印文字宽度
     * @param markHeight 水印文字高度
     * @param position 水印位置值
     * @return int[] 水印位置坐标
     * @exception
     */
    private static int[] getCoordinates(int width, int height, String text, int markWidth, int markHeight, int position) {
        if (position == 0) {
            position = rnd.nextInt(9) + 1;
        }

        // 获取水印文字在原图中的左上角坐标：分别将原图的width和height均分为3份，然后按照position进行相应移动
        int x = (width / 3) * ((position - 1) % 3);
        int y = (height / 3) * ((position - 1) / 3);

        // 计算水印文字的尺寸和原图尺寸的差距
        int widthDif = width - markWidth;
        int heightDif = height - markHeight;

        // 判断原图打上水印文字后，是否会超出原图大小
        if (x > widthDif) {
            x = widthDif;
        }
        if (y > heightDif) {
            y = heightDif;
        }

        return new int[]{x, y};
    }

    public static void main(String[] args) {
        String src = "C:\\Users\\kevin\\Desktop\\images\\国家大剧院.jpg";
        String des = "C:\\Users\\kevin\\Desktop\\images\\国家大剧院_修.jpg";
        String watermark = "C:\\Users\\kevin\\Desktop\\images\\logo.png";
        System.out.println(ImageUtils.getSize(src)[0]);  // 640
        System.out.println(ImageUtils.getSize(src)[1]);  // 320
//        watermark(src, des, 1.0, Positions.CENTER_LEFT, watermark, 0.8f);
//        convert(src, des, 1.0, "png");
        watermark(src, des, 1.0, 9, "hello world", "Console", Font.BOLD, 40, "#B22222", 5, "#ADFF2F", 0.3f);
    }
}
