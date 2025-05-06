package com.fiveup.core.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * freemarker
 * 模板word导出，工具类
 * @author zecheng
 * @date 2023/03/30
 */
public class FreemarkerExportWordUtil {
    private static Configuration configuration = null;

    static {
        configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //获取模板路径    setClassForTemplateLoading 这个方法默认路径是webRoot 路径下
        configuration.setClassForTemplateLoading(FreemarkerExportWordUtil.class, "/templates");
    }

    private FreemarkerExportWordUtil() {
        throw new AssertionError();
    }

    /**
     * 根据 /resources/templates 目录下的ftl模板文件生成文件并写到客户端进行下载
     *
     * @param response    HttpServletResponse
     * @param map         数据集合
     * @param fileName    用户下载到的文件名称
     * @param ftlFileName ftl模板文件名称
     * @throws IOException
     */
    public static void exportWord(HttpServletResponse response, Map map, String fileName, String ftlFileName) throws IOException {
        Template freemarkerTemplate = configuration.getTemplate(ftlFileName);
        // 调用工具类的createDoc方法生成Word文档
        File file = createDoc(map, freemarkerTemplate);
        //将word文档写到前端
        download(file.getAbsolutePath(), response, fileName);
    }

    private static File createDoc(Map<?, ?> dataMap, Template template) {
        //临时文件
        String name = "template.doc";
        File f = new File(name);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    //下载文件的公共方法
    public static void download(String filePath, HttpServletResponse response, String fileName) {
        try {
            setAttachmentResponseHeader(response, fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
                // 输出流
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ) {
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bis.read(buff)) > 0) {
                bos.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);
        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue.toString());
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }
}
