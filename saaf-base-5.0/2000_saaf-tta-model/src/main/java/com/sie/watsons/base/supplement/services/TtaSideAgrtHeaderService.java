package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaAttachmentDownload;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtHeader;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ttaSideAgrtHeaderService")
public class TtaSideAgrtHeaderService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtHeaderService.class);

    @Autowired
    private ITtaSideAgrtHeader ttaSideAgrtHeaderServer;

    @Autowired
    private IBaseAttachment baseAttachmentServer;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    private ITtaAttachmentDownload ttaAttachmentDownload;

    @Autowired
    ISsoServer ssoServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.ttaSideAgrtHeaderServer;
    }

    /**
     * 保存补充协议拆分头主表信息
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("saveTtaSideAgrtHeader")
    public String saveTtaSideAgrtHeader(@RequestParam(required = true) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            int userId = this.getSessionUserId();
            TtaSideAgrtHeaderEntity_HI instance = ttaSideAgrtHeaderServer.editTtaSideAgrtHeader(parameters, userId);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, instance).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "保存失败", 0, null).toJSONString();
        }
    }

    /**
     * 补充协议信息提交
     *
     * @param params
     * @return
     */
    @PostMapping("submitTtaSideAgrtHeader")
    public String submitTtaSideAgrtHeader(@RequestParam(required = true) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            int userId = this.getSessionUserId();
            TtaSideAgrtHeaderEntity_HI instance = ttaSideAgrtHeaderServer.submitTtaSideAgrtHeader(parameters, userId);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, instance).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "保存失败", 0, null).toJSONString();
        }
    }

    /**
     * 更新单据状态
     * @param params
     * @return
     */
    @PostMapping("updateStatus")
    public String updateStatus(@RequestParam(required = true) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            int userId = this.getSessionUserId();
            ttaSideAgrtHeaderServer.updateStatus(parameters, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常:" + e.getMessage(), 0, null).toJSONString();
        }
    }
    /**
     * 作废
     *
     * @param params
     * @return
     */
    @PostMapping("disSicradTtaSideAgrtHeader")
    public String disSicradTtaSideAgrtHeader(@RequestParam(required = true) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            int userId = this.getSessionUserId();
            TtaSideAgrtHeaderEntity_HI instance = ttaSideAgrtHeaderServer.disSicradTtaSideAgrtHeader(parameters, userId);
            return SToolUtils.convertResultJSONObj("S", "作废成功", 1, instance).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "作废失败", 0, null).toJSONString();
        }
    }

    /**
     * 查询补充协议拆分头表
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("findSideAgrtHeaderList")
    public String findTtaSupplierItemHeaderEntityHIPage(@RequestParam(required = false) String params,
                                                        @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                        @RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
            JSONObject parameters = this.parseObject(params);
            Pagination<TtaSideAgrtHeaderEntity_HI_RO> page = ttaSideAgrtHeaderServer.findTtaSideAgrtHeaderEntity(parameters, pageIndex, pageRows);
            JSONObject results = JSONObject
                    .parseObject(JSON.toJSONString(page));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
                    .toJSONString();
        }
    }


    /**
     * 查询供应商
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("findTtaSupplierEntity_HI_RO")
    public String findTtaSupplierEntity_HI_RO(@RequestParam(required = false) String params,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                              @RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
            JSONObject parameters = this.parseObject(params);
            Pagination<TtaSupplierEntity_HI_RO> ttaSupplierEntity = ttaSideAgrtHeaderServer.findTtaSupplierEntity(parameters, pageIndex, pageRows);
            JSONObject results = JSONObject
                    .parseObject(JSON.toJSONString(ttaSupplierEntity));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
                    .toJSONString();
        }
    }

    /**
     * 查询附件
     *
     * @param params
     * @return
     * @throws Exception
     * @Date 2019.7.19
     */
    @PostMapping("findBaseAttachmentList")
    public String findBaseAttachmentEntity(@RequestParam(required = false) String params,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
            JSONObject parameters = this.parseObject(params);
            Pagination<BaseAttachmentEntity_HI_RO> baseAttachmentEntity = ttaSideAgrtHeaderServer.findBaseAttachmentEntity(parameters, pageIndex, pageRows);

            JSONObject results = JSONObject.parseObject(JSON.toJSONString(baseAttachmentEntity));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
                    .toJSONString();
        }
    }

    /**
     * 文件上传
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "ttaSideAgrtHeaderUploadFile", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        try {

            LOGGER.info("#############################文件上传开始 userId" + this.getSessionUserId() + "#########################################");
            List<ResultFileEntity> list = new ArrayList<>();
            request.setCharacterEncoding("utf-8");
            //1.//业务模块 "tta_side_agrt_header"
            String functionId = request.getParameter("functionId");
            //1.获取业务主键id
            String bussnessId = request.getParameter("sideAgrtHId");
            String fileName = request.getParameter("fileName");
            LOGGER.info("业务主键 bussnessId: {},functionId:{}", bussnessId, functionId);
            Assert.notNull(file, "上传内容为空");
            Assert.notNull(bussnessId, "业务id 不能为空！");
            Assert.notNull(functionId, "functionId 不能为空!");
          /*  if ((file.getSize() / 1024*1024) > 15D) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "上传文件不能超过15M ", 0, null).toJSONString();
            }*/
            try (
                    InputStream inputStream = file.getInputStream();
            ) {
                //5.保存附件到文件系统和base_attachment中
                ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(bussnessId), this.getSessionUserId());
                list.add(resultFileEntity);
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
        } catch (Exception e) {
            LOGGER.error("#############################文件上传失败:{}", e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 ", 0, null).toJSONString();
        }
    }

    /**
     * 附件删除
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/ttaSideAgrtHeaderFileDelete", method = RequestMethod.POST)
    public String fileDelete(@RequestParam String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "fileId");
            fastdfsServer.delete(jsonObject.getLong("fileId"), getUserSessionBean());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "删除成功", 0, null).toJSONString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 文件下载
     *
     * @param fileId
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "fileDownload", method = {RequestMethod.GET, RequestMethod.POST})
    public String FileDownload(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Assert.notNull(fileId, "缺少参数 fileId");
            int userId = this.getSessionUserId();

            //下载文件
            this.download_file(Long.valueOf(fileId),userId, request, response);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "下载成功", 1, null).toJSONString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     *
     * @param functionId
     * @param id
     * @param Certificate
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "fileDownloadByBIC", method = {RequestMethod.GET, RequestMethod.POST})
    public String fileDownloadByBIC(@RequestParam String functionId,@RequestParam String id,@RequestParam String Certificate, HttpServletRequest request, HttpServletResponse response) {
        try {
            Assert.notNull(functionId, "缺少参数 functionId");
            Assert.notNull(id, "缺少参数 id");

            if (!ssoServer.authCookie(Certificate)) {
                throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +functionId);
            }
            String key = "cookie_" + Certificate;
            String result = jedisCluster.hget(key, "sessionInfo");
            if (StringUtils.isBlank(result))
                throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +result);
            UserSessionBean userSessionBean = JSON.parseObject(result,
                    UserSessionBean.class);
            if (SaafToolUtils.isNullOrEmpty(userSessionBean.getUserType()) || (!("45".equals(userSessionBean.getUserType()))) ) {
                throw new RuntimeException("无法打开文件，非BIC用户");
            }
            int userId = this.getSessionUserId();
            //下载文件
            this.download_file_function_id(Long.valueOf(id),functionId,userId, request, response);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "下载成功", 1, null).toJSONString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }


    /**
     *
     * @param functionId
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "fileDownloadType", method = {RequestMethod.GET, RequestMethod.POST})
    public String FileDownloadType(@RequestParam String functionId,@RequestParam String id,@RequestParam String Certificate, HttpServletRequest request, HttpServletResponse response) {
        try {
            Assert.notNull(functionId, "缺少参数 functionId");
            Assert.notNull(id, "缺少参数 id");
            int userId = this.getSessionUserId();
            if (!ssoServer.authCookie(Certificate)) {
                throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +functionId);
            }
            //下载文件
            this.download_file_function_id(Long.valueOf(id),functionId,userId, request, response);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "下载成功", 1, null).toJSONString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 下载文件
     * @param fileId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void download_file(Long fileId,int userId, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        InputStream inStream = fastdfsServer.getInputStream(obj.getBucketName(),obj.getPhyFileName());
        // 设置输出的格式
        response.reset();
        response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Content-Length",String.valueOf(inStream.available()));
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();


        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        //插入数据
        ttaAttachmentDownload.ttaAchmentDownload(obj,userId);
    }


    /**
     *
     * @param businessId
     * @param functionId
     * @param userId
     * @param request
     * @param response
     * @throws Exception
     */
    public void download_file_function_id(Long businessId, String functionId,int userId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseAttachmentEntity_HI obj = baseAttachmentServer.findBaseAttachmentInfoByFun(businessId,functionId);
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
        InputStream inStream = fastdfsServer.getInputStream(obj.getBucketName(),obj.getPhyFileName());
        // 设置输出的格式
        response.reset();
        response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Content-Length",String.valueOf(inStream.available()));
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();


        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        //插入数据
        ttaAttachmentDownload.ttaAchmentDownload(obj,userId);
    }


}