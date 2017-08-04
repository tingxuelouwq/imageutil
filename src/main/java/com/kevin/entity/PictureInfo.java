package com.kevin.entity;

import java.io.Serializable;

/**
 * @类名: PictureInfo
 * @包名：com.kevin.entity
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 9:44
 * @版本：1.0
 * @描述：图片信息实体类
 */
public class PictureInfo implements Serializable {
    private static final long serialVersionUID = 6532383994916497463L;

    private String imageUrl;    // 图片的返回路径
    private String logoImageUrl;    // 添加水印的图片的返回路径

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }
}
