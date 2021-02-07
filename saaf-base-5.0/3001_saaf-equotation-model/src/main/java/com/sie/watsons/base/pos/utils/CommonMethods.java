package com.sie.watsons.base.pos.utils;

import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CommonMethods extends CommonAbstractService {

    @Autowired
    private IFastdfs fastDfsServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    public static final String FILE_TYPE = "JPEG、PSD、PDD、JPEG、SVG、DOC、DOCX、XLS、MSG、RAR、ZIP、PPTX、XLSX、XML、TXT、BMP、GIF、PDF、JPG、PNG、XLS、XLSX、RAR、CSV";


    /**
     * 通用上传功能(用户只需传入业务标识id和调用的service类名即可)
     *
     * @param functionId 业务模块id(生成附件存放路径用)
     * @param className  用于日志打印
     */
    public String fileUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response, String functionId, String className,Integer userId) {

        Logger logger = LoggerFactory.getLogger(className.getClass());
        try {
            request.setCharacterEncoding(CommonEnum.UTF8.getValue());
            List<ResultFileEntity> list = new ArrayList<>();
            //1.将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //2.获取业务主键id
            String businessId = multiRequest.getParameter("businessId");
            Iterator iterator = multiRequest.getFileNames();
            while (iterator.hasNext()) {
                //3.这里的name为fileItem的alias属性值，相当于form表单中name
                String name = (String) iterator.next();
                //4.根据name值拿取文件
                MultipartFile file1 = multiRequest.getFile(name);
                Assert.notNull(file1, "上传内容为空");
                //String fileName = file1.getOriginalFilename(); //文件名 不用这种方式获取文件名,有乱码问题
                String fileName = request.getParameter("fileName");
                String str2 = fileName.substring(fileName.lastIndexOf(".")+1);
                if (FILE_TYPE.indexOf(str2.toUpperCase())==-1){
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无法上传此格式附件", 0, null).toJSONString();
                }
                try (
                        InputStream inputStream = file.getInputStream();
                ) {
                    //5.保存附件到文件系统和base_attachment中
                    ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(businessId),userId);
                    String filePath = resultFileEntity.getFilePath();
                    resultFileEntity.setFilePath(filePath+"?attname="+fileName);
                    list.add(resultFileEntity);
                }
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toJSONString();
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e1.getMessage(), 0, null).toJSONString();
        }
    }
}
