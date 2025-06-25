
package com.fiveup.core.noticeBooklet.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        // 生成word文件名
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMdd_HHmmssSSS");
        //调用的模板wordss文件
        String templPath= "templates/wordss.docx";
        try {
            //读取模板文件
            XWPFDocument doc = WordExportUtil.exportWord07(templPath, param);
            //保存文件
            FileOutputStream fos = new FileOutputStream(wordPath +param.get("studentName")+dateTime+".docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成word文件
     * @param data
     *
     */
    public static ByteArrayOutputStream createWord(Map<String, Object> data) throws IOException {
        // 创建一个Word文件
        XWPFDocument doc = new XWPFDocument();
        //创建段落
        XWPFParagraph para = doc.createParagraph();
        //创建段落样式
        XWPFRun run = para.createRun();
        //设置段落文本
        run.setText("示例内容：" + data.get("name"));
        //创建输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        return out;
    }
}
