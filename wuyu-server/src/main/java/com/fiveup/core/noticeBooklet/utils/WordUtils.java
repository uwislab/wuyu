
package com.fiveup.core.noticeBooklet.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

/**
 * @program byh-service-referral
 * @description: word工具类
 * @author: chenmet
 * @create: 2019/01/10 11:33
 */
public class WordUtils {

    /**
     * 根据参数与文件路径生成word文件
     * @param param
     * @param wordPath
     */
    public static void saveWord(Map<String,Object> param, String wordPath) {
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMdd_HHmmssSSS");
        //调用的模板
        String templPath= "templates/wordss.docx";
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(templPath, param);
            FileOutputStream fos = new FileOutputStream(wordPath +param.get("studentName")+dateTime+".docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
