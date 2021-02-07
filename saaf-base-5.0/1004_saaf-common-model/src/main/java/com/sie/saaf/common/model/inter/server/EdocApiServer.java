package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.configration.EdocConfigration;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IEdocApi;
import com.sie.saaf.common.util.HttpClientUtil;
import com.yhg.redis.framework.JedisClusterCore;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EdocApiServer extends JedisClusterCore implements IEdocApi {

    private static final Logger log= LoggerFactory.getLogger(EdocApiServer.class);

    @Autowired
    private EdocConfigration edocConfigration;

    @Autowired
    private IBaseAttachment baseAttachmentServer;


    /**
     * 请求accesstoken
     * @return
     * @throws IOException
     */
    @Override
    public String getAccessToken() throws IOException {
        String url=edocConfigration.getRequestUrl()+edocConfigration.getAccessTokenApi();
        JSONObject params=new JSONObject();
        params.put("accessKey",edocConfigration.getAccessKey());
        Map<String,String> header=new HashMap<>();
        header.put("Content-Type","application/json");
        String result= HttpClientUtil.sendJsonRequest(url,params,null,true,null);
        log.info("edoc请求accesstoken结果:{}",result);
        if (StringUtils.isBlank(result))
            throw new IllegalStateException("请求edoc系统accesstoken失败");
        String accessToken= JSON.parseObject(result).getString("d");
        jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,"edocAccessToken",accessToken);
        return accessToken;
    }

    /**
     * 获取用户token
     * @param employeeNumber    员工编号
     * @return
     */
    @Override
    public String  getUserToken(String employeeNumber) throws IOException {
        if (StringUtils.isBlank(employeeNumber)){
            throw new IllegalArgumentException("invalid employeeNumber");
        }
        String accessToken= jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,"edocAccessToken");
        if (StringUtils.isBlank(accessToken))
            throw new IllegalArgumentException("无法获取edoc系统accessToken");
        String token=jedisCluster.hget("EDOC_USER_TOKEN",employeeNumber);
        JSONObject requestParams=new JSONObject()
                .fluentPut("userLoginName",employeeNumber)
                .fluentPut("ipAddress",edocConfigration.getIpAddress())
                .fluentPut("accessToken",accessToken);
        if (StringUtils.isBlank(token)){
            String result= HttpClientUtil.sendJsonRequest(edocConfigration.getRequestUrl()+edocConfigration.getTokenApi(),requestParams,null,true,null);
            if (log.isInfoEnabled())
                log.info("{}请求edoc系统token结果:{}",employeeNumber,result);
            token= JSON.parseObject(result).getJSONObject("d").getString("ResultValue");
            Assert.notNull(token,"获取token失败");
            jedisCluster.hset(CommonConstants.RedisCacheKey.EDOC_USER_TOKEN,employeeNumber,token);
        }
        int n=0;
        while (n++<=3 && !isLogin(token)){
            String result= HttpClientUtil.sendJsonRequest(edocConfigration.getRequestUrl()+edocConfigration.getTokenApi(),requestParams,null,true,null);
            if (log.isInfoEnabled())
                log.info("{}请求edoc系统token结果:{}",employeeNumber,result);
            token= JSON.parseObject(result).getJSONObject("d").getString("ResultValue");
            jedisCluster.hset(CommonConstants.RedisCacheKey.EDOC_USER_TOKEN,employeeNumber,token);
        }
        return token;
    }

    /**
     * 判断token是否有效
     * @param token
     * @return
     */
    public boolean isLogin(String token) throws IOException {
        Assert.notNull(token,"token is required");
        JSONObject requestParam=new JSONObject()
                .fluentPut("ipAddress",edocConfigration.getIpAddress())
                .fluentPut("token",token);
        String result= HttpClientUtil.sendJsonRequest(edocConfigration.getRequestUrl()+edocConfigration.getIsLoginApi(),requestParam,null,true,null);
        requestParam= JSON.parseObject(result);
        return requestParam!=null && requestParam.containsKey("d") && requestParam.getBoolean("d");
    }

    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @param userSessionBean
     * @return
     * @throws IOException
     */
    @Override
    public ResultFileEntity upload(InputStream inputStream, String fileName, UserSessionBean userSessionBean) throws IOException {
        Assert.notNull(userSessionBean,"请重新登录");
        String token=getUserToken(userSessionBean.getEmployeeNumber());
        Assert.notNull(token,"获取edoc用户token失败");
        if (!fileName.contains("."))
            throw new IllegalArgumentException("上传文件必需包含文件后缀");
        long fileSize= ((FileInputStream)inputStream).getChannel().size();
        String url=edocConfigration.getUploadDomain()+edocConfigration.getUploadApi()+token+"/?folderId=6";

        String fileType = fileName.lastIndexOf(".")>fileName.length()-1?fileName:fileName.substring(fileName.lastIndexOf(".")+1);
        String edocFileName=System.currentTimeMillis()+"."+fileType;
        String result= HttpClientUtil.sendFileInputStreamRequest(url,inputStream,edocFileName);
        log.info("{},文件上传结果:{}",url,result);
        Assert.notNull(result,"请求edoc文件上传接口失败");
        result=result.replaceAll("\\\\","");
        result=result.substring(result.indexOf("\"")+1,result.lastIndexOf("\""));
        JSONArray jsonArray= JSONArray.parseArray(result);
        if (jsonArray==null || jsonArray.size()==0)
            throw new IllegalStateException("请求edoc文件上传接口失败:"+jsonArray);
        JSONObject jsonObject=jsonArray.getJSONObject(0);
        if (!"0".equals(jsonObject.getString("Status")))
            throw new IllegalStateException("请求edoc文件上传接口失败:"+jsonArray);

        String fileCode = jsonObject.getString("FileCode");
        ResultFileEntity resultFileEntity = new ResultFileEntity(fileName,fileType, fileCode, fileCode, fileSize,new Date(), null, fileName);

        //保存附件表
        BaseAttachmentEntity_HI instance=new BaseAttachmentEntity_HI();
        instance.setDeleteFlag(0);
        instance.setFileStoreSystem(2);
        instance.setSourceFileName(fileName);
        instance.setFileSize(new BigDecimal(fileSize));
        instance.setFileType(fileType);
        instance.setFilePath(fileCode);
        instance.setPhyFileName(fileCode);
        instance.setOperatorUserId(userSessionBean.getUserId());
        baseAttachmentServer.saveBaseAttachmentInfo(instance);
        resultFileEntity.setFileId(instance.getFileId());
        resultFileEntity.setCreatedBy(String.valueOf(userSessionBean.getUserId()));
        resultFileEntity.setCreatedByUser(userSessionBean.getUserFullName());
        resultFileEntity.setCreationDate(new Date());
        resultFileEntity.setFileStoreSystem(instance.getFileStoreSystem());
        return resultFileEntity;
    }

    /**
     * 删除文件
     * @return
     * @throws IOException
     */
    public void delete(Long fileId, UserSessionBean userSessionBean) throws IOException {
        Assert.notNull(userSessionBean,"请重新登录");
        BaseAttachmentEntity_HI attachmentEntity = baseAttachmentServer.findBaseAttachmentInfo(fileId);
        if (attachmentEntity == null) {
            throw new IllegalArgumentException("文件不存在，删除失败");
        } else if (attachmentEntity.getCreatedBy().equals(String.valueOf(userSessionBean.getUserId()))) {
            throw new IllegalArgumentException("非本人上传的附件，无法删除");
        }
        String url = edocConfigration.getRequestUrl() + edocConfigration.getDeleteFileApi();
        JSONObject requestParams=new JSONObject()
                .fluentPut("token",getUserToken(userSessionBean.getEmployeeNumber()))
                .fluentPut("fileIds", attachmentEntity.getFilePath())
                .fluentPut("intoRecycleBin", false);
        Map<String,String> header=new HashMap<>();
        header.put("Content-Type","application/json");
        String result= HttpClientUtil.sendJsonRequest(url,requestParams,null,true,null);
        log.info("edoc系统删除文件结果:{}",result);
        if (StringUtils.isBlank(result))
            throw new IllegalStateException("edoc系统删除文件失败");
        String status = JSON.parseObject(result).getString("d");
        if (!"0".equals(status))
            throw new IllegalStateException("edoc系统删除文件失败");
        baseAttachmentServer.deleteBaseAttachment(Long.valueOf(fileId));
    }

    /**
     * 获取EDOC文件流
     * @param fileId
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getFileInputStream(String fileId, UserSessionBean userSessionBean) throws IOException {
        Assert.notNull(userSessionBean, "请重新登录");
        String url = edocConfigration.getRequestUrl() +
                String.format(edocConfigration.getDownloadUrl(), fileId, getUserToken(userSessionBean.getEmployeeNumber()));
        InputStream inputStream = HttpClientUtil.getFileInputStreamRequest(url);
        return inputStream;
    }

    /**
     * 文档系统edoc登录
     * @param employeeNumber
     * @return
     * @throws IOException
     */
    @Override
    public String UserLogin(String employeeNumber) throws IOException {
        if (StringUtils.isBlank(employeeNumber)){
            throw new IllegalArgumentException("invalid employeeNumber");
        }
        String accessToken= jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,"edocAccessToken");
        if (StringUtils.isBlank(accessToken))
            throw new IllegalArgumentException("无法获取edoc系统accessToken");

        JSONObject requestParams=new JSONObject()
                .fluentPut("userLoginName",employeeNumber)
                .fluentPut("ipAddress",edocConfigration.getIpAddress())
                .fluentPut("accessToken",accessToken);

        String result= HttpClientUtil.sendJsonRequest(edocConfigration.getRequestUrl()+edocConfigration.getUserLoginApi(),requestParams,null,true,null);
        if (log.isInfoEnabled())
            log.info("{}请求edoc系统单点登录token结果:{}",employeeNumber,result);
        String token= JSON.parseObject(result).getJSONObject("d").getString("ResultValue");
        Assert.notNull(token,"获取edoc系统单点登录token失败");
        return token;
    }


    public static void main(String[] args) {
        String fileName=System.currentTimeMillis()+".docx";
        System.out.println(fileName.length());
        String fileType = fileName.lastIndexOf(".")>fileName.length()-1?fileName:fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println(fileType);
    }

}
