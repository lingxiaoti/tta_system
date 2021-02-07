package com.sie.saaf.base.commmon.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IAliOss;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件上传
 *
 * @author huangtao
 */
@RestController
@RequestMapping("/fileUploadService")
public class FileUploadService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @Autowired
    private IAliOss aliOssServer;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    ISsoServer ssoServer;

    @Autowired
    private IBaseAttachment baseAttachmentServer;

    /**
     * 图片上传
     * @param files Form表单的文件列表
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 图片上传
     */
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile[] files) throws Exception {

        try {
            List<ResultFileEntity> list = new ArrayList<>();
            Assert.notNull(files, "上传内容为空");
            Assert.notEmpty(files, "上传内容为空");
            for (MultipartFile file : files) {
                Assert.notNull(file, "上传内容为空");

                ResultFileEntity fileEntity = fastdfsServer.fileUpload(file.getInputStream(),file.getOriginalFilename());

//                ResultFileEntity fileEntity = aliOssServer.imageUploadAndCloseStream(file.getInputStream(), file.getOriginalFilename());
                list.add(fileEntity);
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 文件上传
     * @param files Form表单的文件列表
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 附件上传
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile[] files){

        try {
            List<ResultFileEntity> list = new ArrayList<>();
            Assert.notNull(files, "上传内容为空");
            Assert.notEmpty(files, "上传内容为空");
            for (MultipartFile file : files) {
                Assert.notNull(file, "上传内容为空");
                try (InputStream in=file.getInputStream()){
                    ResultFileEntity fileEntity = fastdfsServer.fileUpload(in, file.getOriginalFilename());
                    list.add(fileEntity);
                }
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 文件上传
     * @param files Form表单的文件列表
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 文件上传，兼容前端，现在使用fastdfs上传，不使用edoc
     */
    @RequestMapping(value = "/edocUpload", method = RequestMethod.POST)
    public String edocUpload(@RequestParam("file") MultipartFile[] files)  {
        return this.fileUpload(files);
    }

    /**
     * 文件删除
     * @param params {
     *     fileId:文件Id
     * }
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 文件删除，兼容前端，现在使用fastdfs删除文件
     */
    @RequestMapping(value = "/edocDelete", method = RequestMethod.POST)
    public String edocDelete(@RequestParam String params) {
        return this.fileDelete(params);
    }

    /**
     * 图片删除
     * @param params {
     *     filePath:文件的访问路径
     * }
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 图片删除，这里删除只删除文件，不删除表记录，建议使用fileDelete做删除
     */
    @RequestMapping(value = "/imgDelete", method = RequestMethod.POST)
    public String imgDelete(@RequestParam String params) {

        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"filePath");

			ResultFileEntity entity = fastdfsServer.delete(jsonObject.getString("filePath"));
			if(entity==null){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败", 0, null).toJSONString();
			}else{
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "删除成功", 0, entity).toJSONString();
			}
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 图片下载
     * @param fileId 文件id
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 图片下载
     */
    @RequestMapping(value = "/imgDownload", method = RequestMethod.GET)
    public void imgDownload(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Assert.notNull(fileId,"缺少参数 fileId");
        fastdfsServer.download(Long.valueOf(fileId), request, response);
    }

    /**
     * 文件删除
     * @param params {
     *     fileId:文件Id
     * }
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description 文件删除
     */
    @RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
    public String fileDelete(@RequestParam String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "fileId");
            fastdfsServer.delete(jsonObject.getLong("fileId"), getUserSessionBean());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "删除成功", 0, null).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * Base64上传
     * @author ZhangJun
     * @createTime 2018/11/16
     * @description Base64文件上传
     */
    @RequestMapping(value = "/base64ImgUpload", method = RequestMethod.POST)
    public String base64ImgUpload(@RequestParam String params)  {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "base64Str", "fileName");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(jsonObject.getString("base64Str"));
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, jsonObject.getString("fileName"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", 0, fileEntity).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 文件打包下载
     * @param fileIds 文件Id，逗号分隔
     * @param certificate 登录认识码
     * @return
     * @author ZhangJun
     * @modify chenkangliang
     * @createTime 2018/9/10
     * @description 文件打包下载
     */
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST},value="batchDownload")
    public String batchDownload(@RequestParam String fileIds, @RequestParam String certificate,@RequestParam String type){
        try{

            Assert.notNull(fileIds,"缺少参数 fileIds");
            Assert.notNull(certificate,"缺少参数 certificate");
            if (!ssoServer.authCookie(certificate)) {
                throw new RuntimeException("无法打开文件，没有登录"+ certificate );
            }
            String[] fileIdsArr = fileIds.split(",");

            if(fileIdsArr != null && fileIdsArr.length > 0) {
                // 创建临时文件
                File zipFile = File.createTempFile(SaafDateUtils.convertDateToString(new Date(),"yyyyMMddHHmmssSSS"), ".zip");
                FileOutputStream fos = new FileOutputStream(zipFile);
                CheckedOutputStream csum = new CheckedOutputStream(fos, new Adler32());
                // 用于将数据压缩成Zip文件格式
                ZipOutputStream zos = new ZipOutputStream(csum);
                UserSessionBean userSessionBean = null;

                Map<String,Integer> sourceFileNames = new HashMap<>();
                for (int i=0,size=fileIdsArr.length;i<size;i++) {
                    String fileId = fileIdsArr[i];

                    BaseAttachmentEntity_HI atta = baseAttachmentServer.findBaseAttachmentInfo(Long.parseLong(fileId));
                    InputStream inputStream = null;
                    if( SaafToolUtils.isNullOrEmpty(type) && atta.getFileStoreSystem().compareTo(1) == 0) {
                        // OSS
                        inputStream = aliOssServer.getObjectInputStream(atta.getPhyFileName());
                    } else {
                        // fastdfs
                        if (null == userSessionBean)
                        inputStream = fastdfsServer.getInputStream(atta.getBucketName(),atta.getPhyFileName());
                    }
                    /* 2018-10-18 add by SIE_ZHANGJUN 解决批量载文件时，存在同名文件不能压缩问题*/
                    String sourceFileName = atta.getSourceFileName();
                    String newSourceFileName = atta.getSourceFileName();//新文件名，压缩文件夹中使用此文件名

                    if(sourceFileNames.containsKey(sourceFileName)){
                        //已存在一个相同文件名
                        int index = sourceFileNames.get(sourceFileName);
                        String prefix = sourceFileName.substring(0,sourceFileName.lastIndexOf("."));
                        String suffix = sourceFileName.substring(sourceFileName.lastIndexOf("."));
                        newSourceFileName = prefix + "(" + index++ + ")" + suffix;
                        sourceFileNames.put(newSourceFileName,0);
                        sourceFileNames.put(sourceFileName,index);
                    }else{
                        sourceFileNames.put(sourceFileName,0);
                    }
                    /*end*/

                    zos.putNextEntry(new ZipEntry(newSourceFileName));

                    int bytesRead = 0;
                    // 向压缩文件中输出数据
                    while ((bytesRead = inputStream.read()) != -1) {
                        zos.write(bytesRead);
                    }
                    inputStream.close();
                    zos.closeEntry(); // 当前文件写完，定位为写入下一条项目
                }
                zos.close();

                response.reset();
                response.setContentType("text/plain");
                response.setContentType("application/octet-stream; charset=utf-8");
                response.setHeader("Location", zipFile.getName());
                response.setHeader("Cache-Control", "max-age=0");
                response.setHeader("Content-Disposition", "attachment; filename=" + zipFile.getName());

                FileInputStream fis = new FileInputStream(zipFile);
                BufferedInputStream buff = new BufferedInputStream(fis);
                BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
                byte[] bytes=new byte[1024];
                int len = -1;
                while((len = buff.read(bytes)) != -1){
                    out.write(bytes,0,len);
                }
                //关闭流
                fis.close();
                buff.close();
                out.close();

                // 删除临时文件
                zipFile.delete();

                return null;
            }else{
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 fileIds", 0, null).toString();
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }



    /**
     * 返回补货申请行错误结果转excel
     * @author sie_wujigao
     * @createTime 2020/03/14
     * @description 返回错误结果转excel
     */
    @RequestMapping(value = "/plmErrorResultToExcel", method = RequestMethod.POST)
    public String plmErrorResultToExcel(@RequestParam String params)  {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "errors");
            JSONArray array = jsonObject.getJSONArray("errors");
            if(array.isEmpty() || array.size()<1){
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "没有错误文件值", 0, "null").toJSONString();
            }
            InputStream inputStream =fastdfsServer.plmErrorResultToExcel(array);
            if(inputStream==null){
                throw new Exception("没有组装成excel文件");
            }
            ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, jsonObject.getString("fileName"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", 0, fileEntity).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 返回补货基础错误结果转excel
     * @author sie_wujigao
     * @createTime 2020/04/08
     * @description 返回错误结果转excel
     */
    @RequestMapping(value = "/plmSupErrorResultToExcel", method = RequestMethod.POST)
    public String plmSupErrorResultToExcel(@RequestParam String params)  {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "errors");
            JSONArray array = jsonObject.getJSONArray("errors");
            if(array.isEmpty() || array.size()<1){
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "没有错误文件值", 0, "null").toJSONString();
            }
            InputStream inputStream =fastdfsServer.plmSupErrorResultToExcel(array);
            if(inputStream==null){
                throw new Exception("没有组装成excel文件");
            }
            ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, jsonObject.getString("fileName"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", 0, fileEntity).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }


}
