package com.sie.watsons.base.withdrawal.utils;

import com.deepoove.poi.XWPFTemplate;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * <pre>
 * Created by Binary Wang on 2017-01-05.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class QrcodeUtils {
    private static final int DEFAULT_LENGTH = 400;// 生成二维码的默认边长，因为是正方形的，所以高度和宽度一致
    private static final String FORMAT = "jpg";// 生成二维码的格式

    private static Logger logger = LoggerFactory.getLogger(QrcodeUtils.class);

    /**
     * 根据内容生成二维码数据
     *
     * @param content 二维码文字内容[为了信息安全性，一般都要先进行数据加密]
     * @param length  二维码图片宽度和高度
     */
    private static BitMatrix createQrcodeMatrix(String content, int length) {
        Map<EncodeHintType, Object> hints = Maps.newEnumMap(EncodeHintType.class);
        // 设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        try {
            return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, length, length, hints);
        } catch (Exception e) {
            logger.warn("内容为：【" + content + "】的二维码生成失败！", e);
            return null;
        }

    }

    /**
     * 根据指定边长创建生成的二维码，允许配置logo属性
     *
     * @param content    二维码内容
     * @param length     二维码的高度和宽度
     * @param logoFile   logo 文件对象，可以为空
     * @param logoConfig logo配置，可设置logo展示长宽，边框颜色
     * @return 二维码图片的字节数组
     */
    public static byte[] createQrcode(String content, int length, File logoFile, MatrixToLogoImageConfig logoConfig) {
        if (logoFile != null && !logoFile.exists()) {
            throw new IllegalArgumentException("请提供正确的logo文件！");
        }

        BitMatrix qrCodeMatrix = createQrcodeMatrix(content, length);
        if (qrCodeMatrix == null) {
            return null;
        }
        try {
            File file = Files.createTempFile("qrcode_", "." + FORMAT).toFile();
            logger.debug(file.getAbsolutePath());
            MatrixToImageWriter.writeToFile(qrCodeMatrix, FORMAT, file);
            if (logoFile != null) {
                // 添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
                BufferedImage img = ImageIO.read(file);
                overlapImage(img, FORMAT, file.getAbsolutePath(), logoFile, logoConfig);
            }
            return toByteArray(file);
        } catch (Exception e) {
            logger.warn("内容为：【" + content + "】的二维码生成失败！", e);
            return null;
        }
    }

    /**
     * 根据指定边长创建生成的二维码
     *
     * @param content  二维码内容
     * @param length   二维码的高度和宽度
     * @param logoFile logo 文件对象，可以为空
     * @return 二维码图片的字节数组
     */
    public static byte[] createQrcode(String content, int length, File logoFile) {
        return createQrcode(content, length, logoFile, new MatrixToLogoImageConfig());
    }

    /**
     * 创建生成默认高度(400)的二维码图片
     * 可以指定是否贷logo
     *
     * @param content  二维码内容
     * @param logoFile logo 文件对象，可以为空
     * @return 二维码图片的字节数组
     */
    public static byte[] createQrcode(String content, File logoFile) {
        return createQrcode(content, DEFAULT_LENGTH, logoFile);
    }

    /**
     * 将文件转换为字节数组，
     * 使用MappedByteBuffer，可以在处理大文件时，提升性能
     *
     * @param file 文件
     * @return 二维码图片的字节数组
     */
    private static byte[] toByteArray(File file) {
        try (FileChannel fc = new RandomAccessFile(file, "r").getChannel();) {
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (Exception e) {
            logger.warn("文件转换成byte[]发生异常！", e);
            return null;
        }
    }

    /**
     * 将logo添加到二维码中间
     *
     * @param image     生成的二维码图片对象
     * @param imagePath 图片保存路径
     * @param logoFile  logo文件对象
     * @param format    图片格式
     */
    private static void overlapImage(BufferedImage image, String format, String imagePath, File logoFile,
                                     MatrixToLogoImageConfig logoConfig) throws IOException {
        try {
            BufferedImage logo = ImageIO.read(logoFile);
            Graphics2D g = image.createGraphics();
            // 考虑到logo图片贴到二维码中，建议大小不要超过二维码的1/5;
            int width = image.getWidth() / logoConfig.getLogoPart();
            int height = image.getHeight() / logoConfig.getLogoPart();
            // logo起始位置，此目的是为logo居中显示
            int x = (image.getWidth() - width) / 2;
            int y = (image.getHeight() - height) / 2;
            // 绘制图
            g.drawImage(logo, x, y, width, height, null);

            // 给logo画边框
            // 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, width, height);

            g.dispose();
            // 写入logo图片到二维码
            ImageIO.write(image, format, new File(imagePath));
        } catch (Exception e) {
            throw new IOException("二维码添加logo时发生异常！", e);
        }
    }

    /**
     * 解析二维码
     *
     * @param file 二维码文件内容
     * @return 二维码的内容
     */
    public static String decodeQrcode(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = Maps.newEnumMap(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        return new MultiFormatReader().decode(binaryBitmap, hints).getText();
    }

    public static String getImagePath(){
        ClassPathResource resource = new ClassPathResource("/static/image/watsons.png");
        String imagePath = "";
        try {
            imagePath = resource.getURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return imagePath;
    }

    /**
     * 生成水印的方法
     */
    public static File createWatermark(String inFilePath, String waterMarkName) throws  Exception {
        File outFile = Files.createTempFile("qrcode_", ".pdf").toFile();
        //File outFile = new File(ResourceUtils.getUploadTempPath());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
        PdfReader reader = new PdfReader(inFilePath);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        PdfGState gs = new PdfGState();
        for (int i = 1; i < total; i++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            gs.setFillOpacity(0.8f);
            content.setGState(gs);
            content.beginText();
            content.setColorFill(BaseColor.LIGHT_GRAY);//Color.LIGHT_GRAY  new BaseColor(192, 192, 192)
            content.setFontAndSize(base, 50);
            content.setTextMatrix(70, 200);
            //public void ShowTextAligned(int alignment, String text, float x,float y, float rotation)
            content.showTextAligned(Element.ALIGN_CENTER, "WATSONS", 445, 280, 360);
            //Image image = Image.getInstance("D:\\pdf\\img.png");
            Image image = Image.getInstance(QrcodeUtils.createQrcode(waterMarkName, 200,null));
            image.setAbsolutePosition(755, 5); // image of the absolute 720,5
            image.scaleToFit(80, 80); //设置图片大小
            content.addImage(image);
            content.setColorFill(BaseColor.BLACK);//Color.BLACK
            content.setFontAndSize(base, 8);
            //content.showTextAligned(Element.ALIGN_CENTER, "下载时间：" + waterMarkName + "", 300, 10, 0);
            content.endText();
        }
        stamper.close();
        bos.close();
        reader.close();
        System.out.println(outFile);
        return outFile;
    }

    private File createWatermark(File file, String waterMarkName) throws  Exception {
        File outFile = Files.createTempFile("qrcode_", ".pdf").toFile();
        //File outFile = new File(ResourceUtils.getUploadTempPath());
        outFile = new File("D:\\program\\watsons2\\upload\\qrcode.pdf");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
        PdfReader reader = new PdfReader(new FileInputStream(file));
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;//华文彩云
        //BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        //ClassPathResource resource = new ClassPathResource("fonts/STCAIYUN.TTF");
        //使用华文彩云字体,通过加载resource资源目录下的字体文件
        BaseFont base = BaseFont.createFont("E:\\sie_project\\watsonsTTA\\01工作区\\02项目代码\\saaf-base-5.0\\2000_saaf-tta-model\\src\\main\\resources\\fonts\\STCAIYUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        PdfGState gs = new PdfGState();
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        for (int i = 1; i < total; i++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            gs.setFillOpacity(0.8f);
            content.setGState(gs);
            content.beginText();
            if(i == 1){
                //文字加粗
                //设置文本描边宽度
                content.setLineWidth(0.5f);
                //设置文本为描边模式
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
                content.setFontAndSize(baseFont, 13);
                content.showTextAligned(Element.ALIGN_CENTER,"EB2020080021",470,753,0);
            }
            content.setTextRenderingMode(0);
            content.setColorFill(BaseColor.LIGHT_GRAY);//Color.LIGHT_GRAY  new BaseColor(192, 192, 192)
            content.setFontAndSize(base, 50);
            //content.setTextMatrix(70, 200);
            //public void ShowTextAligned(int alignment, String text, float x,float y, float rotation)
            content.showTextAligned(Element.ALIGN_CENTER, "WATSONS",300 , 400, 360);
            //Image image = Image.getInstance("D:\\pdf\\img.png");
            Image image = Image.getInstance(QrcodeUtils.createQrcode(waterMarkName, 200,null));
            image.setAbsolutePosition(510, 20); // image of the absolute 720,5
            image.scaleToFit(80, 80); //设置图片大小
            content.addImage(image);
            content.setColorFill(BaseColor.BLACK);//Color.BLACK
            content.setFontAndSize(base, 8);
            //content.showTextAligned(Element.ALIGN_CENTER, "下载时间：" + waterMarkName + "", 300, 10, 0);
            content.endText();
        }
        stamper.close();
        bos.close();
        reader.close();
        System.out.println(outFile);
        return outFile;
    }

    /**
     * 将文件转换成byte数组
     * @return
     */
    public static byte[] File2byte(File tradeFile){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }


    public static byte[] word2PdfAndReplace(Map<String, Object> replaceParams, InputStream inputStream) throws Exception {
        XWPFTemplate template = null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ByteArrayOutputStream byteOutputStream = null;
        try {
            template = XWPFTemplate.compile(inputStream).render(replaceParams);
            out = new ByteArrayOutputStream();
            template.write(out);
            byteArrayInputStream =  new ByteArrayInputStream(out.toByteArray());
            byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream(byteArrayInputStream);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                template.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                byteOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  byteOutputStream.toByteArray();
    }

    /**
     * 加水印，含有图片
     */
    public static byte[] createWatermark(String qCode, InputStream byteInput, Float absoluteX, Float absoluteY) throws Exception {
        byte[] arr = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(byteInput);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        //BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        ClassPathResource resource = new ClassPathResource("/fonts/STCAIYUN.TTF");
        //使用华文彩云字体,通过加载resource资源目录下的字体文件
        BaseFont base = BaseFont.createFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        PdfGState gs = new PdfGState();
        PdfGState gs1 = new PdfGState();
        PdfContentByte content;
        for (int i = 1; i < total; i++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            gs.setFillOpacity(0.8f);
            content.setGState(gs);
            content.beginText();
            /*
            if(i == 1){
                //文字加粗，第一页设置文字标记
                //设置文本描边宽度
                content.setLineWidth(0.5f);
                //设置文本为描边模式
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
                content.setFontAndSize(baseFont, 13);
                content.showTextAligned(Element.ALIGN_CENTER, code,470,753,0);
            }
            */

            //************添加文本水印start***************
/*			content.setTextRenderingMode(0);
			content.setColorFill(BaseColor.LIGHT_GRAY);//Color.LIGHT_GRAY  new BaseColor(192, 192, 192)
			content.setFontAndSize(base, 50);
			content.showTextAligned(Element.ALIGN_CENTER, "WATSONS",300 , 400, 45);*/
            //************添加文本水印end**************

            //************************添加水印图片start*********************************
            Image imageLogo = Image.getInstance(QrcodeUtils.getImagePath());
            // 设置坐标 绝对位置 X Y
            imageLogo.setAbsolutePosition(425,795);
            //imageLogo.setRotation(45);// 旋转 弧度
            // 设置旋转角度
            imageLogo.setRotationDegrees(0);// 旋转 角度
            // 设置等比缩放
            imageLogo.scalePercent(15);// 依照比例缩放
            //imageLogo.scaleAbsolute(50,100);//自定义大小
            // 设置透明度
            //gs.setFillOpacity(0.3f);
            //content.setGState(gs);
            // 添加水印图片
            content.addImage(imageLogo);
            //************************添加水印图片end*********************************

            //**********************添加二维码start********************************
            Image image = Image.getInstance(QrcodeUtils.createQrcode(qCode, 200,null));
            image.setAbsolutePosition(absoluteX != null? absoluteX : 510, absoluteY != null? absoluteY : 20); // image of the absolute 755,5 （510，20）
            image.scaleToFit(80, 80); //设置图片大小
            gs1.setFillOpacity(0.8f);
            content.setGState(gs1);
            content.addImage(image);
            content.setColorFill(BaseColor.BLACK);//Color.BLACK
            content.setFontAndSize(base, 8);
            //content.showTextAligned(Element.ALIGN_CENTER, "下载时间：" + waterMarkName + "", 300, 10, 0);
            //**********************添加二维码end********************************
            content.endText();
        }
        stamper.close();
        reader.close();
        arr = bos.toByteArray();
        bos.close();
        return arr;
    }

    /**
     * 插入二维码和图片水印
     * @param cvCode
     * @param is
     * @return
     * @throws Exception
     */
    public static InputStream insertWatermarkAndLogo(String cvCode, InputStream is) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(is);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        ClassPathResource resource = new ClassPathResource("/fonts/STCAIYUN.TTF");
        //使用华文彩云字体,通过加载resource资源目录下的字体文件
        BaseFont base = BaseFont.createFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        PdfGState gs = new PdfGState();
        PdfGState gs1 = new PdfGState();
        PdfContentByte content;
        for (int i = 1; i < total; i++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            gs.setFillOpacity(0.8f);
            content.setGState(gs);
            content.beginText();
            if(i == 1){
                //文字加粗
                //设置文本描边宽度
                content.setLineWidth(0.5f);
                //设置文本为描边模式
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
                content.setFontAndSize(baseFont, 13);
                content.showTextAligned(Element.ALIGN_CENTER,cvCode,470,770,0);
            }

            //************************添加水印图片start*********************************
            Image imageLogo = Image.getInstance(QrcodeUtils.getImagePath());
            // 设置坐标 绝对位置 X Y
            imageLogo.setAbsolutePosition(425,795);
            //imageLogo.setRotation(45);// 旋转 弧度
            // 设置旋转角度
            imageLogo.setRotationDegrees(0);// 旋转 角度
            // 设置等比缩放
            imageLogo.scalePercent(15);// 依照比例缩放
            //imageLogo.scaleAbsolute(50,100);//自定义大小
            // 设置透明度
            //gs.setFillOpacity(0.1f);
            //content.setGState(gs);
            // 添加水印图片
            content.addImage(imageLogo);
            //************************添加水印图片end*********************************

            //********************添加二维码水印start******************
            Image image = Image.getInstance(QrcodeUtils.createQrcode(cvCode, 200,null));
            image.setAbsolutePosition(510, 20); // image of the absolute 720,5
            image.scaleToFit(80, 80); //设置图片大小
            gs1.setFillOpacity(0.8f);
            content.setGState(gs1);
            content.addImage(image);
            content.setColorFill(BaseColor.BLACK);//Color.BLACK
            content.setFontAndSize(base, 8);
            //content.showTextAligned(Element.ALIGN_CENTER, "下载时间：" + waterMarkName + "", 300, 10, 0);
            //*********************添加二维码水印end***********************
            content.endText();
        }
        stamper.close();
        reader.close();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bos.toByteArray());
        bos.close();
        byteArrayInputStream.close();
        return byteArrayInputStream;
    }

    public static void main(String[] args) throws Exception {
        //createWatermark("http://10.82.24.117:8888/group1/M00/00/03/ClIYdV7CC-WAellHAARrUYS-b9s834.pdf", "654545458");
        //File inFile = new File("E:\\健与美渠道，药房，大卖场和超市独家B5.pdf");
        //createWatermark(inFile,"EB202008001");
        byte[] watermark = createWatermark("4544", new FileInputStream("C:\\Users\\Administrator\\Downloads\\AAA.pdf"), 730f, 5f);
        Files.write(Paths.get("d:/a.pdf"), watermark);
    }

}
