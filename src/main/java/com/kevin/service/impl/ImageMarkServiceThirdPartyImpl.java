package com.kevin.service.impl;

import com.kevin.service.MarkService;
import com.kevin.util.ImageUtils;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @类名: ImageMarkServiceThirdPartyImpl
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2018/2/24 10:14
 * @版本：1.0
 * @描述：
 */
@Service("ImageMarkServiceThirdParty")
public class ImageMarkServiceThirdPartyImpl implements MarkService {
    @Override
    public String watermark(File imageFile, String imageFileName, String uploadPath, String realUploadPath) {
        String logoPath = realUploadPath + File.separator + LOGO;
        String logoFileName = "logo_" + imageFileName;
        String des = realUploadPath + File.separator + logoFileName;
        String src = imageFile.getAbsolutePath();
        ImageUtils.watermark(src, des, 1.0, 1, logoPath, 1.0f);
        return uploadPath + File.separator + logoFileName;
    }
}
