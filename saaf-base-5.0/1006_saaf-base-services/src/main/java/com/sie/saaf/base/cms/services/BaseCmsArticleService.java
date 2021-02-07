package com.sie.saaf.base.cms.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.cms.model.entities.BaseCmsArticleEntity_HI;
import com.sie.saaf.base.cms.model.entities.readonly.BaseCmsArticleEntity_HI_RO;
import com.sie.saaf.base.cms.model.inter.IBaseCmsArticle;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/baseCmsArticleService")
public class BaseCmsArticleService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCmsArticleService.class);
    @Autowired
    private IBaseCmsArticle baseCmsArticleServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseCmsArticleServer;
    }

    /**
     * @param params    {
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 查询文章列表（带分页）
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {

            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            jsonObject=SaafToolUtils.cleanNull(jsonObject,"articleType");
            String isAdmin=jsonObject.getString("isAdmin");
            String systemCode=jsonObject.getString("systemCode");
            if (!"Y".equals(isAdmin)) {
                SaafToolUtils.validateJsonParms(jsonObject, "systemCode");
            }

            Set<String> systemCodes = new HashSet<>();
            if (systemCode != null && !systemCode.isEmpty()) {
                String[] systemCodeArr = jsonObject.getString("systemCode").split(";");
                for (String s : systemCodeArr) {
                    systemCodes.add(s);
                }
            }
            if (!"Y".equals(isAdmin)) {
                jsonObject.put("systemCode_in", StringUtils.join(systemCodes, ","));
            } else {
                if (systemCode != null && !systemCode.isEmpty()) {
                    jsonObject.put("systemCodeadd", StringUtils.join(systemCodes, ","));
                }
            }
            Pagination<BaseCmsArticleEntity_HI_RO> result = baseCmsArticleServer.findBaseCmsArticles(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params ｛
     *               articleTitle       标题
     *               articleContent     内容
     *               articleDescription 描述
     *               articleType        文章类型
     *               articleStatus      文章状态
     *               ｝
     * @description 文章保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            BaseCmsArticleEntity_HI instance = baseCmsArticleServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params-articleId 主键
     * @description 删除文章数据
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("articleId").split(",");
            for (String articleId : ids) {
                baseCmsArticleServer.delete(Integer.parseInt(articleId));
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    /**
     * 查询详情
     *
     * @param params
     *                  {
     *                  id:'文章id'
     *                  }
     * @return BaseCmsArticleEntity_HI
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    @Override
    public String findById(String params) {
        try{
            return super.findById(params);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
    /**
     *Portal
     * @param
     * @description 企业新闻查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "findNews")
    public String findNews(@RequestParam(required = false) String params) {
        try {
            UserSessionBean userSessionBean=getUserSessionBean();
            Assert.notNull(userSessionBean,"请重新登录");
            JSONObject paramJson = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(paramJson,"systemCode");
            String isAdmin=paramJson.getString("isAdmin");
            Set<String> systemCodes = new HashSet<>();
            String[] systemCodeArr = paramJson.getString("systemCode").split(";");
            for(String s : systemCodeArr){
                systemCodes.add(s);
            }
            if (!"Y".equals(isAdmin)) {
                paramJson.put("systemCode_in", StringUtils.join(systemCodes, ","));
            }
            List<BaseCmsArticleEntity_HI_RO> result = baseCmsArticleServer.findNews(paramJson.fluentPut("userId",getSessionUserId()));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, result.size(), result).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    /**
     *Portal
     * @param
     * @description 通知公告查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "findNotice")
    public String findNotice(@RequestParam(required = false) String params) {
        try {
            UserSessionBean userSessionBean=getUserSessionBean();
            Assert.notNull(userSessionBean,"请重新登录");
            JSONObject paramJson = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(paramJson,"systemCode");
            String isAdmin=paramJson.getString("isAdmin");
            Set<String> systemCodes = new HashSet<>();
            String[] systemCodeArr = paramJson.getString("systemCode").split(";");
            for(String s : systemCodeArr){
                systemCodes.add(s);
            }
            if (!"Y".equals(isAdmin)) {
                paramJson.put("systemCode_in", StringUtils.join(systemCodes, ","));
            }
            List<BaseCmsArticleEntity_HI_RO> result = baseCmsArticleServer.findNotice(paramJson.fluentPut("userId",getSessionUserId()));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, result.size(), result).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}
