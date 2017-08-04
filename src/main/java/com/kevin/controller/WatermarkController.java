package com.kevin.controller;

import com.kevin.entity.PictureInfo;
import com.kevin.service.MarkService;
import com.kevin.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @类名: WatermarkController
 * @包名：com.kevin.controller
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/3 9:29
 * @版本：1.0
 * @描述：水印控制器
 */
@Controller
public class WatermarkController {

    private static final Logger log = LoggerFactory.getLogger(WatermarkController.class);

    @Autowired
    private UploadService uploadService;

    @Autowired
//    @Qualifier("textMarkService")
//    @Qualifier("imageMarkService")
//    @Qualifier("MultiTextMarkService")
//    @Qualifier("MultiImageMarkService")
    @Qualifier("ImageMarkServiceThirdPart")
    private MarkService markService;

    @RequestMapping("/watermark")
    public ModelAndView watermark(@RequestParam("image") MultipartFile file, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("page/watermark");
        PictureInfo pictureInfo = new PictureInfo();

        String uploadPath = "images";
        String realUploadPath = request.getServletContext().getRealPath(uploadPath);
        String imageUrl = uploadService.uploadImage(file, uploadPath, realUploadPath);
        File imageFile = new File(realUploadPath + File.separator + file.getOriginalFilename());
        String logoImageUrl = markService.watermark(imageFile, file.getOriginalFilename(), uploadPath, realUploadPath);

        pictureInfo.setImageUrl(imageUrl);
        pictureInfo.setLogoImageUrl(logoImageUrl);
        modelAndView.addObject("pictureInfo", pictureInfo);

        return modelAndView;
    }

    @RequestMapping("/multi-watermark")
    public ModelAndView watermark(List<MultipartFile> images, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("page/multi-watermark");

        String uploadPath = "images";
        String realUploadPath = request.getServletContext().getRealPath(uploadPath);

        if (images != null && images.size() > 0) {
            List<PictureInfo> pictureInfos = new ArrayList<>();
            for (MultipartFile image : images) {
                if (image == null || image.getSize() <= 0) {
                    continue;
                }

                PictureInfo pictureInfo = new PictureInfo();
                String imageUrl = uploadService.uploadImage(image, uploadPath, realUploadPath);
                File imageFile = new File(realUploadPath + File.separator + image.getOriginalFilename());
                String logoImageUrl = markService.watermark(imageFile, image.getOriginalFilename(), uploadPath, realUploadPath);

                pictureInfo.setImageUrl(imageUrl);
                pictureInfo.setLogoImageUrl(logoImageUrl);
                pictureInfos.add(pictureInfo);
            }
            modelAndView.addObject("pictureInfos", pictureInfos);
        }

        return modelAndView;
    }
}
