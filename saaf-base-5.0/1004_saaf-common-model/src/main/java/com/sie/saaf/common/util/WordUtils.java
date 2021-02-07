package com.sie.saaf.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordUtils.class);
    private WordUtils() {
        throw new AssertionError();
    }

    /**
     * ftl文件转Word，并将文件返回respose给前台下载
     * @param freemarkerTemplate
     * @param request
     * @param response
     * @param map
     * @throws IOException
     */
    public static void exportMillCertificateWord(Template freemarkerTemplate,HttpServletRequest request, HttpServletResponse response, Map map) throws IOException {
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {

            // 调用工具类的createDoc方法生成Word文档
            file = createDoc(map,freemarkerTemplate);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件名
            String fileName = "tmp"+SaafDateUtils.getDateSeq()+ ".doc";
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

            out = response.getOutputStream();
            byte[] buffer = new byte[512];  // 缓冲区
            int bytesToRead = -1;

            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } finally {
            if(fin != null) fin.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }

    /**
     *ftl文件转Word，word转pdf 并将文件返回respose给前台下载
     * @param freemarkerTemplate
     * @param request
     * @param response
     * @param map
     * @throws IOException
     */
    public static void exportMillCertificatePdf(Template freemarkerTemplate,HttpServletRequest request, HttpServletResponse response, Map map) throws IOException {
        ByteArrayInputStream fileInputStream = null;
        response.setHeader("Content-Type","application/pdf;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        try {
            // 调用工具类的createDoc方法生成Word文档
            fileInputStream = createDocStream(map,freemarkerTemplate);
            //转pdf
            ByteArrayOutputStream byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream(fileInputStream)/*Word2PdfUtil.getDocToPdfAndGetPath(file.getPath())*/;
            out.write(byteOutputStream.toByteArray());

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf;charset=UTF-8");
            // 设置浏览器以下载的方式处理该文件名
            String fileName = "tmp"+SaafDateUtils.getDateSeq()+ ".pdf";
            response.addHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

        } finally {
            if(out != null) out.close();
            if(fileInputStream != null) fileInputStream.close();
        }
    }

    /**
     * ftl转word
     * @param dataMap
     * @param template
     * @return
     */
    private static File createDoc(Map<?, ?> dataMap, Template template) {
        String name = "tmp"+SaafDateUtils.getDateSeq()+ ".doc";
        File f = new File(name);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(),ex);
            throw new RuntimeException(ex);
        }
        return f;
    }


    /**
     * ftl转word
     * @param dataMap
     * @param template
     * @return
     */
    private static ByteArrayInputStream createDocStream(Map<?, ?> dataMap, Template template) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = null;
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(byteOutputStream, "UTF-8");
            t.process(dataMap, w);
            w.close();

            byteArrayInputStream =  new ByteArrayInputStream(byteOutputStream.toByteArray());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(),ex);
            throw new RuntimeException(ex);
        }

        return byteArrayInputStream;
    }

}

