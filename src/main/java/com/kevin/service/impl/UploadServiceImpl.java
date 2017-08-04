package com.kevin.service.impl;

import com.kevin.service.UploadService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @类名: UploadServiceImpl
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 9:31
 * @版本：1.0
 * @描述：图片上传业务层实现类
 */
@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String uploadPath, String realUploadPath) {
        log.info("相对上传路径: {}", uploadPath);
        log.info("绝对上传路径: {}", realUploadPath);

        try {
            String targetFilePath = realUploadPath + File.separator + file.getOriginalFilename();
            File targetFile = new File(targetFilePath);
            FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadPath + File.separator + file.getOriginalFilename();
    }
}
