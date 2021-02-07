package com.sie.saaf.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.server.AliOssServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.excel.OperationExcelDocUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ProjectMaster
 *
 * @author YangXiaowei
 * @creteTime 2018/4/25
 */

public class FileExportFiter implements ResponseBodyAdvice {

    private static Logger LOGGER = LoggerFactory.getLogger(FileExportFiter.class);

    @Autowired
    private HttpServletRequest servletRequest;
    @Autowired
    private HttpServletResponse servletResponse;
    @Autowired
    private AliOssServer aliOssServer;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if ("Y".equals(servletRequest.getParameter("exportExcel"))) {
            JSONObject result = JSONObject.parseObject(String.valueOf(o));
            String msg = "";
            if ("S".equals(result.getString("status"))) {
                JSONObject xlsObject = JSONObject.parseObject(servletRequest.getParameter("xlsParams"));
                JSONArray labelNames = xlsObject.getJSONArray("labelName");
                JSONArray names = xlsObject.getJSONArray("name");

                String sheetName = xlsObject.getString("sheetName");
                String[] allAttributeLabels = labelNames.toJavaList(String.class).toArray(new String[labelNames.size()]);
                String[] attributeNames = names.toJavaList(String.class).toArray(new String[names.size()]);
                JSONArray data = result.getJSONArray("data");

                if(data.size() > 10 * 10000){
                    return SToolUtils.convertResultJSONObj("E", "数据量大于10W,请添加检索条件", 0, null).toJSONString();
                }
                List<Map<String, Object>> ts = getListResult(data);

                //创建Excel导出
                Workbook workBook = null;
                try {
                    OperationExcelDocUtils excelDoc = new OperationExcelDocUtils(allAttributeLabels);
                    workBook = excelDoc.createExcel2007Doc(sheetName, ts, attributeNames);
                    LOGGER.info("导出Excel，输出Excel文件成功！");
                    String fullFileName = sheetName + System.currentTimeMillis()  + ".xlsx"; //真实的下载文件名
//                    downloadFile(fullFileName, workBook);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    workBook.write(outputStream);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    ResultFileEntity resultFileEntity = aliOssServer.imageUpload(inputStream, fullFileName);
                    return SToolUtils.convertResultJSONObj("S", "上传成功", 0, resultFileEntity).toJSONString();
                } catch (Exception e) {
                    msg = "导出Excle异常！";
                    LOGGER.error(msg + e);
                    return SToolUtils.convertResultJSONObj("E", msg, 0, null).toJSONString();
                }

            }else {
                msg = result.getString("msg");
            }
            return SToolUtils.convertResultJSONObj("E", msg, 0, null).toJSONString();
        }
        return o;
    }

    private List<Map<String, Object>> getListResult(JSONArray array) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject jSONObject = array.getJSONObject(i);
                Map<String, Object> map = JSON.parseObject(jSONObject.toString(), Map.class);
                list.add(map);
            }
        }
        return list;
    }

//    private void downloadFile(String targetFileName, Workbook workBook){
//        servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859-1");
//        BufferedOutputStream output = null;
//        try {
//            servletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(targetFileName.getBytes("utf-8"), "ISO8859-1"));
//            servletResponse.addHeader("Pargam", "no-cache");
//            servletResponse.addHeader("Cache-Control", "no-cache");
////            servletResponse.setContentLength((int) file.length());
//            output = new BufferedOutputStream(servletResponse.getOutputStream());
//            workBook.write(output);
//            output.flush();
//            servletResponse.flushBuffer();
//        }catch (FileNotFoundException e) {
//
//        } catch (IOException e) {
//
//        } finally {
//            try {
//                //关闭流，不可少
//                if (output != null) {
//                    output.close();
//                }
//                //testFile.deleteOnExit(); //文件下载完之后将原有的文件删除
//            } catch (IOException e) {
//
//            }
//        }
//    }
}

