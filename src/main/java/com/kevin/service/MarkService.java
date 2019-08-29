package com.kevin.service;

import java.awt.*;
import java.io.File;

/**
 * @类名: MarkService
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 10:36
 * @版本：1.0
 * @描述：图片水印业务层接口
 * @实现思路：
 * 1、创建图片缓存对象
 * 2、创建Java绘图工具对象
 * 3、使用绘图工具对象将原图绘制到图片缓存对象中
 * 4、使用绘图工具对象将水印绘制到图片缓存对象中
 * 5、创建图像编码工具类
 * 6、使用图像编码工具类输出图片缓存对象到目标图片文件中
 */
public interface MarkService {

    /** 水印文字内容 */
    String MARK_TEXT = "新华社";
    /** 水印文字类型 */
    String FONT_NAME = "微软雅黑";
    /** 水印文字样式 */
    int FONT_STYLE = Font.BOLD;
    /** 水印文字大小 */
    int FONT_SIZE= 40;// 单位:像素
    /** 水印文字颜色 */
    Color FONT_COLOR= Color.BLACK;
    /** 水印文字位置X轴 */
    int X = 10;
    /** 水印文字位置Y轴 */
    int Y = 10;
    /** 水印文字透明度*/
    float ALPHA = 0.3F;

    /** 水印图片*/
    String  LOGO = "logo.png";

    /**
     * @方法名：watermark
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/3 10:37
     * @描述：为图片添加水印并保存到服务器
     * @param imageFile 源图片
     * @param imageFileName 源图片名称
     * @param uploadPath 图片上传相对路径
     * @param realUploadPath 图片上传绝对路径
     * @return java.lang.String 水印图片的url地址
     * @exception
     */
    String watermark(File imageFile, String imageFileName, String uploadPath, String realUploadPath);
}
