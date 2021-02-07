package com.sie.saaf.common.model.inter.server;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.configration.OssConfigration;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IAliOss;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author huangtao
 */
@Component
public class AliOssServer extends FileUploadServer implements IAliOss {
    private static final Logger log = LoggerFactory.getLogger(AliOssServer.class);

    @Autowired
    private OssConfigration ossConfigration;

    @Autowired
    private IBaseAttachment baseAttachmentServer;

//    @Autowired(required = false)
//    private JedisCluster jedisCluster;



    private ThreadLocal<OSSClient> ossClient = new ThreadLocal<OSSClient>() {
        @Override
        protected OSSClient initialValue() {
            return new OSSClient(ossConfigration.getEndpoint(), ossConfigration.getAccessKeyId(), ossConfigration.getAccessKeySecret());
        }
    };


    /**
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return
     */
    @Override
    public ResultFileEntity imageUploadAndCloseStream(InputStream inputStream, String fileName) {
        try (InputStream in = inputStream) {
            return imageUpload(in, fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    /**
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return
     */
    @Override
    public ResultFileEntity imageUpload(InputStream inputStream, String fileName) throws IOException {
        return fileUpload(inputStream,fileName,RemoteDirectory.Imgfile);
    }


    @Override
    public ResultFileEntity fileUpload(InputStream inputStream, String fileName,RemoteDirectory remoteDirectory) throws IOException {
        Long start=System.currentTimeMillis();
        long fileSize= inputStream.available();
        String postfix = "";
        if (StringUtils.isNotBlank(fileName) && fileName.lastIndexOf(".") >= 0) {
            postfix = fileName.substring(fileName.lastIndexOf("."));
        }
        OSSClient client = ossClient.get();
        String remoteFileName = UUID.randomUUID().toString().replace("-", "") + postfix;
        String path = remoteDirectory.dir + remoteFileName;
        String accessPath = ossConfigration.getAccessProtocol() + ossConfigration.getBucketName() + ossConfigration.getEndpoint().replace("http://", ".") + "/" + path;
        client.putObject(ossConfigration.getBucketName(), path, inputStream);
        log.info("文件上传用时{}s，大小:{}MB",(System.currentTimeMillis()-start)/1000D,fileSize/1024D/1024D);
        start=System.currentTimeMillis();
        client.setObjectAcl(ossConfigration.getBucketName(),path,CannedAccessControlList.PublicRead);
        log.info("设置文件可访问传用时{}s",System.currentTimeMillis()-start/1000D);
        ResultFileEntity resultFileEntity = new ResultFileEntity(fileName,postfix, accessPath, accessPath, fileSize ,new Date(), ossConfigration.getBucketName(), remoteFileName);
        //保存附件表
        BaseAttachmentEntity_HI instance=new BaseAttachmentEntity_HI();
        instance.setDeleteFlag(0);
        instance.setFileStoreSystem(1);
        instance.setSourceFileName(resultFileEntity.getSourceFileName());
        instance.setFileSize(new BigDecimal(fileSize));
        if (StringUtils.isNotBlank(postfix)){
            postfix=postfix.replace(".","");
            instance.setFileType(postfix.toLowerCase());
        }
        instance.setFilePath(accessPath);
        instance.setPhyFileName(path);
        instance.setOperatorUserId(getUserId());
        baseAttachmentServer.saveBaseAttachmentInfo(instance);
        resultFileEntity.setFileId(instance.getFileId());
        resultFileEntity.setCreatedBy(String.valueOf(getUserId()));
        resultFileEntity.setCreatedByUser(getUserFullName());
        resultFileEntity.setCreationDate(new Date());
        resultFileEntity.setFileStoreSystem(instance.getFileStoreSystem());
        return resultFileEntity;
    }



    /**
     * @param file 文件
     * @return
     */
    @Override
    public ResultFileEntity imageUpload(File file) {
        if (file == null || file.isDirectory() || file.exists() == false) {
            throw new IllegalArgumentException("文件读取失败");
        }
        OSSClient client = ossClient.get();
        String postfix = "";
        String fileName = file.getName();
        if (StringUtils.isNotBlank(fileName) && fileName.lastIndexOf(".") >= 0) {
            postfix = fileName.substring(fileName.lastIndexOf("."));
        }
        String remoteFileName = UUID.randomUUID().toString().replace("-", "") + postfix;
        String path = RemoteDirectory.Imgfile.dir + remoteFileName;
        String accessPath = ossConfigration.getAccessProtocol() + ossConfigration.getBucketName() + ossConfigration.getEndpoint().replace("http://", ".") + "/" + path;
        client.putObject(ossConfigration.getBucketName(), path, file);
        client.setObjectAcl(ossConfigration.getBucketName(), path, CannedAccessControlList.PublicRead);
        ResultFileEntity resultFileEntity = new ResultFileEntity(fileName, accessPath, accessPath, new Date(), ossConfigration.getBucketName(), remoteFileName);

        //保存附件表
        BaseAttachmentEntity_HI instance=new BaseAttachmentEntity_HI();
        instance.setDeleteFlag(0);
        instance.setFileStoreSystem(1);
        instance.setSourceFileName(resultFileEntity.getSourceFileName());
        instance.setFileSize(new BigDecimal(file.getTotalSpace()).divide(new BigDecimal(1024*1024)));
        if (StringUtils.isNotBlank(postfix)){
            postfix=postfix.replace(".","");
            instance.setFileType(postfix.toLowerCase());
        }
        instance.setFilePath(accessPath);
        instance.setPhyFileName(path);
        instance.setOperatorUserId(getUserId());
        baseAttachmentServer.saveBaseAttachmentInfo(instance);
        resultFileEntity.setFileId(instance.getFileId());
        resultFileEntity.setCreatedBy(String.valueOf(getUserId()));
        resultFileEntity.setCreatedByUser(getUserFullName());
        resultFileEntity.setCreationDate(new Date());
        resultFileEntity.setFileStoreSystem(instance.getFileStoreSystem());
        return resultFileEntity;
    }

    /**
     * @param filePath resultFileEntity中返回的filePath
     */
    @Override
    public void delete(String filePath) {
        if (StringUtils.isNotBlank(filePath))
            ossClient.get().deleteObject(ossConfigration.getBucketName(), filePath);
    }

    @Override
    public void download(Long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseAttachmentEntity_HI obj = baseAttachmentServer.findBaseAttachmentInfo(fileId);
        if (null == obj) {
            throw new IllegalArgumentException("文件不存在，下载失败");
        }

        String fileName = obj.getSourceFileName(); // 文件的默认保存名
        String userAgent = request.getHeader("User-Agent");

        // 针对IE或者以IE为内核的浏览器（针对中文名图片下载后文件名乱码）
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        // 读到流中
        InputStream inStream = getObjectInputStream(obj.getPhyFileName());
        // 设置输出的格式
        response.reset();
        response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void delete(Long fileId, UserSessionBean userSessionBean) {
        BaseAttachmentEntity_HI obj = baseAttachmentServer.findBaseAttachmentInfo(fileId);
        if (null == obj) {
            throw new IllegalArgumentException("文件不存在，删除失败");
        } else if (obj.getCreatedBy().equals(String.valueOf(userSessionBean.getUserId()))) {
            throw new IllegalArgumentException("非本人上传的附件，无法删除");
        }
        delete(obj.getPhyFileName());
        baseAttachmentServer.deleteBaseAttachment(fileId);
    }

    /**
     * 获取文件输入流
     * @param ossfile OSS文件路径
     * @return {@link InputStream}
     * @author ZhangJun
     * @createTime 2018/9/10
     * @description 获取文件输入流
     */
    @Override
    public InputStream getObjectInputStream(String ossfile){
        OSSObject ossObject = ossClient.get().getObject(ossConfigration.getBucketName(),ossfile);
        // 读取Object内容  返回
        InputStream inputStream = ossObject.getObjectContent();
        return inputStream;
    }

    public enum RemoteDirectory {
        /**
         * oss图片 保存路径
         */
        Imgfile("img/"),
        /**
         * oss 附件保存路径
         */
        AttachFile("file/updata/");

        private String dir;

        RemoteDirectory(String s) {
            this.dir=s;
        }

        @Override
        public String toString() {
            return dir;
        }
    }

}
