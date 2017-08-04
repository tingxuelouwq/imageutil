package com.kevin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @类名: UploadService
 * @包名：com.kevin.service
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 10:27
 * @版本：1.0
 * @描述：图片上传业务层接口
 */
public interface UploadService {

    /**
     * @方法名：uploadImage
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/3 10:28
     * @描述：上传图片
     * @param file 源图片
     * @param uploadPath 图片上传相对路径
     * @param realUploadPath 图片上传绝对路径
     * @return java.lang.String 源图片相对地址
     * @exception
     */
    String uploadImage(MultipartFile file, String uploadPath, String realUploadPath);
}
