package com.sie.saaf.common.util;

import java.io.*;
import java.net.URLDecoder;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * @version $Id$
 * @since
 * @see
 */
public class Word2PdfUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(Word2PdfUtil.class);
    public static boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            is = Test.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }
        return result;
    }

    public static void doc2pdf(String inPath, String outPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        FileOutputStream os = null;
        try {
            File file = new File(outPath); // 新建一个空白pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {

                }
            }
        }
    }




    public static String getDocToPdfAndGetPath(String inPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return "";
        }
        FileOutputStream os = null;
        try {
            String name = URLDecoder.decode(Test.class.getResource("/").getPath(), "utf-8") + "temp" + (int) (Math.random() * 100000) + ".pdf";

            File file = new File(name);
            os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            return name;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return "";
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {

                }
            }
        }
    }

    //将PDF转成流
    public static ByteArrayOutputStream getDocToPdfOutputStream(InputStream fileInputStream) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            Document doc = new Document(fileInputStream);
            doc.acceptAllRevisions();
            doc.save(byteArrayOutputStream, SaveFormat.PDF);
            return byteArrayOutputStream;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

    public static void main(String[] args) {
        String sql = "http://10.82.24.117:8888/group1/M00/00/0B/ClIYdV89BqeAcNKNABIjdTqALSU947.pdf";
        System.out.println(sql.substring(sql.indexOf("group1") + "group1".length() + 1));
    }
}
