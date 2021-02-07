package com.sie.watsons.base.exclusive.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/5/7 下午5:06
 */
public class WordUtils {


    public static void main(String[] args) throws Exception {

        OperateDTO dto = new  OperateDTO();

        DynamicTableDTO dtd = new DynamicTableDTO();
        dtd.setFontSize(10);
        dtd.setTableIndex(0);
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> list = new ArrayList<>();
            list.add("A" + i);
            list.add("B" + i);
            list.add("C" + i);
            lists.add(list);
        }
        dtd.setRows(lists);
        List<DynamicTableDTO> dynamicTableDTOS = new ArrayList<>();
        dynamicTableDTOS.add(dtd);
        dto.setDynamicRows(dynamicTableDTOS);

        //排序 防止替换前面的列后 后面的列索引变换
        Collections.sort(dto.getDynamicRows());
        XWPFDocument document = new XWPFDocument(new FileInputStream("D:\\word\\a.docx"));
        WordUtils.dynamicTableReplace(document,dto.getDynamicRows());
        document.write(new FileOutputStream("D:\\word\\b.docx"));
    }

    /**
     * 表格动态添加内容
     * @param xwpfDocument
     * @param dynamicTableDTOS
     */
    public static void dynamicTableReplace(XWPFDocument xwpfDocument, List<DynamicTableDTO> dynamicTableDTOS){
        try {
            //获取word文档中所有的表格
            List<XWPFTable> tables = xwpfDocument.getTables();
            if(tables !=null && !tables.isEmpty()) {
                //遍历操作的表单元格
                for (DynamicTableDTO tableDTO : dynamicTableDTOS) {
                    //获取操作的table
                    XWPFTable table = tables.get(tableDTO.getTableIndex());
                    //获取需要在哪一行下面插入数据
                    XWPFTableRow row1 = table.getRow(tableDTO.getRowIndex());
                    //得到需要填充的内容
                    List<List<String>> words = tableDTO.getRows();
                    int i = 0;
                    for(List<String> rowWord : words){
                        //直接将上一行的样式作为新的行的样式
                        table.addRow(row1,tableDTO.getRowIndex()+i);
                        //得到要添加的行
                        XWPFTableRow row = table.getRow(tableDTO.getRowIndex() + i);
                        //这里设置一个累加的变量是为了区分列的索引下标
                        int m = 0;
                        for (String value : rowWord) {
                            //获取需要填充的单元格
                            XWPFTableCell cell = row.getCell(m);
                            //设置单元格内容
                            setTableCell(cell,value,tableDTO);
                            m++;
                        }
                        i++;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 设置单元格的值
     * @param cell
     * @param word
     * @param dynamicTableDTO
     */
    private static void setTableCell(XWPFTableCell cell, String word, DynamicTableDTO dynamicTableDTO){
        cell.getParagraphs().get(0).removeRun(0);
        XWPFRun cellR = cell.getParagraphs().get(0).createRun();
        //单元格内容
        cellR.setText(word);
        //字体颜色
        cellR.setColor(dynamicTableDTO.getColor());
        //字体大小
        cellR.setFontSize(dynamicTableDTO.getFontSize());
        cellR.setFontFamily("宋体");
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        //默认单元格上下居中
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
    }
}
