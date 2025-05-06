package com.fiveup.core.events.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.math.MathUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.excel.EasyExcel;
import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.events.model.PictureActivity;
import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import com.fiveup.core.events.model.response.ImgUploadResp;
import com.fiveup.core.events.service.ActivityService;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.events.service.PictureActivityService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BizErrorCodeEnum;
import com.fiveup.core.management.model.excel.TeaExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/11
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class FileController {
    private static final String TENCENT_NODE_UPLOAD_PATH = "http://124.221.88.85:9200/activityFile/actImgs/";
    private static final String FIVEUP_NODE_UPLOAD_PATH = "http://82.157.231.88:9200/activityFile/actImgs/";


    @Resource
    private PictureActivityService pictureActivityService;

    @PostMapping("/upload")
    public CommonResponse<ImgUploadResp> imgUpload(MultipartFile activityPicture, Long activityId) {

        if (activityPicture == null || activityPicture.isEmpty()) {
            return CommonResponse.fail(500088, "上传文件为空");
        }
        String originFileName = activityPicture.getOriginalFilename();
        String suffixName = originFileName.substring(originFileName.lastIndexOf("."));
        String prePath = "/Users/erxuanhe/IdeaProjects/wuyu-server-zcy/actImgs/";
        // 3. 判断路径是否存在，不存在则新建

        File file = new File(prePath);
        if (!file.exists()) {
            file.mkdir();
        }

        String imgName = activityId + "_" + UUID.fastUUID().toString(true) + suffixName;
        File transferPath = new File(prePath + imgName);
        try {
            activityPicture.transferTo(transferPath);
        } catch (Exception e) {
            log.error("|| FILE_tranfer_Exception", e.getMessage(), e);
            return CommonResponse.fail(5000076, "transfer error");
        }

        // 图片地址落库
        PictureActivity pictureActivity = new PictureActivity();
        pictureActivity.setActivityId(activityId);
        pictureActivity.setPictureUrl(FIVEUP_NODE_UPLOAD_PATH + imgName);
        pictureActivityService.insertOne(pictureActivity);
        ImgUploadResp imgUploadResp = new ImgUploadResp(FIVEUP_NODE_UPLOAD_PATH + imgName);
        return CommonResponse.ok(imgUploadResp);
    }
}
