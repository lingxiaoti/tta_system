package com.sie.watsons.base.withdrawal.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.yhg.base.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.List;

public class MergePdf {

    /*
    public static void main(String[] args) throws Exception {
        long start=new Date().getTime();
        List<byte[]> list=new ArrayList<>();
        list.add(StreamUtils.copyToByteArray(new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\1.pdf")));
        list.add(StreamUtils.copyToByteArray(new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\2.pdf")));
        list.add(StreamUtils.copyToByteArray(new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\3.pdf")));

        String savepath = "C:\\Users\\Administrator\\Desktop\\temp.pdf";
        mergePdfFiles(list);
        long end=new Date().getTime();
        System.out.println(("耗時：" + (end-start)));
    }
    */

    //合并pdf文件
    public static boolean mergePdfFiles(List<String> files, String saveToPath) {
        boolean retValue = false;
        Document document=null;
        try {
            document=new Document(new PdfReader(files.get(0)).getPageSize(1));
            PdfCopy copy=new PdfCopy(document,new FileOutputStream(saveToPath));
            document.open();
            int pagesNum=0;//总页码
            int pages=0;//当前页码
            for (int i = 0; i <files.size() ; i++) {
                PdfReader reader=new PdfReader(files.get(i));
                pagesNum+=reader.getNumberOfPages();
            }
            for (int i = 0; i <files.size() ; i++) {
                PdfReader reader=new PdfReader(files.get(i));
                PdfCopy.PageStamp stamp;//插入页码所需  不要页码可删除
                int n=reader.getNumberOfPages();
                for (int j = 1; j <=n ; j++) {
                    pages++;
                    document.newPage();
                    PdfImportedPage page=copy.getImportedPage(reader,j);
                    stamp=copy.createPageStamp(page);//插入页码所需  不要页码可删除
                    ColumnText.showTextAligned(stamp.getUnderContent(), Element.ALIGN_CENTER, new Phrase(addFont(String.format("第%d页，共%d页", pages, pagesNum))),300f,16f,0f);//插入页码所需  不要页码可删除
                    stamp.alterContents();//插入页码所需  不要页码可删除
                    copy.addPage(page);
                }
                reader.close();
            }
            copy.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            document.close();
        }
        return retValue;
    }

    /**合并pdf文件(读取PDF文档的页，然后一页一页追加到新的文档)
     * @param files
     * @return
     */
    public static byte[] mergePdfFiles(List<byte[]> files) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] retValue = null;
        Document document=null;
        try {
            document=new Document(new PdfReader(files.get(0)).getPageSize(1));
            PdfCopy copy=new PdfCopy(document, out);
            document.open();
            int pagesNum=0;//总页码
            int pages=0;//当前页码
            /*for (int i = 0; i <files.size() ; i++) {
                PdfReader reader=new PdfReader(files.get(i));
                pagesNum+=reader.getNumberOfPages();
            }*/
            for (int i = 0; i <files.size() ; i++) {
                PdfReader reader=new PdfReader(files.get(i));
                //PdfCopy.PageStamp stamp;//插入页码所需  不要页码可删除
                int n =reader.getNumberOfPages();//获得总页码
                for (int j = 1; j <= n ; j++) {
                    pages++;
                    document.newPage();
                    PdfImportedPage page=copy.getImportedPage(reader,j);
                    //stamp=copy.createPageStamp(page);//插入页码所需  不要页码可删除
                    //ColumnText.showTextAligned(stamp.getUnderContent(), Element.ALIGN_CENTER, new Phrase(addFont(String.format("第%d页，共%d页", pages, pagesNum))),300f,16f,0f);//插入页码所需  不要页码可删除
                    //ColumnText.showTextAligned(stamp.getUnderContent(), Element.ALIGN_CENTER, new Phrase(addFont(String.format("%d/%d", pages, pagesNum))),300f,16f,0f);//插入页码所需  不要页码可删除
                    //stamp.alterContents();//插入页码所需  不要页码可删除
                    copy.addPage(page);
                }
                reader.close();
            }
            copy.close();
            retValue = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retValue;
    }

    /**
     * 合并PDF文件(读取所有文档然后追加到新的文档)
     * @param files
     * @return
     */
    public static byte[] mergepdf(List<byte[]> files) {
        Document document = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] retValue = null;
        try {
            document = new Document(new PdfReader(files.get(0)).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, out);
            document.open();

            for (int i = 0; i < files.size(); i++) {
                PdfReader reader = new PdfReader(files.get(i));
                copy.addDocument(reader);
                reader.close();
            }
            copy.close();
            retValue = out.toByteArray();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retValue;
    }

    public static byte[] mergePdfFilesAndDelWhitePage(List<byte[]> files) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] retValue = null;
        Document document=null;
        try {
            document=new Document(new PdfReader(files.get(0)).getPageSize(1));
            PdfCopy copy=new PdfCopy(document, out);
            document.open();
            for (int i = 0; i <files.size() ; i++) {
                PdfReader reader=new PdfReader(files.get(i));
                //RandomAccessFileOrArray raf = new RandomAccessFileOrArray(files.get(i));
                //PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                //TextExtractionStrategy strategy;
                int n =reader.getNumberOfPages();//获得总页码

                for (int j = 1; j <= n ; j++) {
                    //第一种方式:获取页面内容
                    String textFromPage = PdfTextExtractor.getTextFromPage(reader, j);
                    //System.out.println(textFromPage);
                    //页面内容不为空,添加到pdf文件中
                    if (!StringUtils.isBlank(textFromPage)){
                        document.newPage();
                        PdfImportedPage page=copy.getImportedPage(reader,j);
                        copy.addPage(page);
                    }
                    //第二种方式:获取页面内容
                    /*StringBuffer buff = new StringBuffer();
                    strategy = parser.processContent(j,
                            new SimpleTextExtractionStrategy());
                    buff.append(strategy.getResultantText());
                    if (!StringUtils.isBlank(buff.toString())) {
                        document.newPage();
                        PdfImportedPage page=copy.getImportedPage(reader,j);
                        copy.addPage(page);
                    }*/

                    //第三种方式:从某一页获取页面内容(容量)
                   /* byte[] pageContent = reader.getPageContent(j, raf);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bs.write(pageContent);
                    System.out.println("page content length of page "+j+" = " + bs.size());
                    //如果页面内容大于20,证明它不是空白页,添加到pdf中
                    if (bs.size() > 20) {
                        document.newPage();
                        PdfImportedPage page=copy.getImportedPage(reader,j);
                        copy.addPage(page);
                    }
                    bs.close();*/
                }
                //raf.close();
                reader.close();
            }

            copy.close();
            retValue = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retValue;
    }

    protected static Paragraph addFont(String content) {
        BaseFont baseFont=null;
        try {
            try {
                baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
                //baseFont=BaseFont.createFont("D:\\workspace_tta\\chinses\\simsun.ttc,0",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//D:/z/simsun.ttc,0是所下字体的路径
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        Font font=null;
        font=new Font(baseFont,8f,Font.NORMAL);//设置字体
        return addText(content, font);
    }
    private static Paragraph addText(String content, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }


}