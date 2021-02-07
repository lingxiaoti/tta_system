package com.sie.saaf.base.cms.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.cms.model.entities.BaseCmsCarouselEntity_HI;
import com.sie.saaf.base.cms.model.entities.readonly.BaseCmsCarouselEntity_HI_RO;
import com.sie.saaf.base.cms.model.inter.IBaseCmsCarousel;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/baseCmsCarouselService")
public class BaseCmsCarouselService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCmsCarouselService.class);
    @Autowired
    private IBaseCmsCarousel baseCmsCarouselServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseCmsCarouselServer;
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
            if (StringUtils.isNotBlank(params)) {
                jsonObject = parseObject(params);
            }
            jsonObject = SaafToolUtils.cleanNull(jsonObject, "displayFlag", "carouselPosition");
            if (StringUtils.isBlank(jsonObject.getString("orgId")) && !Objects.equals(jsonObject.getString("varIsadmin"),"Y")) {
                ProFileBean ouBean = baseAccreditCacheServer.getOrg(jsonObject.getInteger("varUserId"), jsonObject.getInteger("respId"));
                if (ouBean == null) {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
                }
                jsonObject.put("orgId", ouBean.getProfileValue());
            }
            Pagination<BaseCmsCarouselEntity_HI_RO> result = baseCmsCarouselServer.findAllCarousel(jsonObject, pageIndex, pageRows);
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
     *  app loading页面图片维护
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAPPCarousel")
    public String findAPPCarousel(@RequestParam(required = false) String params,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = parseObject(params);
            jsonObject = SaafToolUtils.cleanNull(jsonObject,  "carouselPosition");
            Pagination<BaseCmsCarouselEntity_HI_RO> result = baseCmsCarouselServer.findAPPCarousel(jsonObject, pageIndex, pageRows);
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
     * 查询数据
     *
     * @param params 参数id
     *               {
     *               id:"主键id"
     *               }
     * @return
     * @author yuzhenli
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    @Override
    public String findById(@RequestParam(required = false) String params) {
        return super.findById(params);
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    @Override
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            if (null == jsonObject.getInteger("orgId")) {
                jsonObject.put("orgId", 0);
            }
            SaafToolUtils.validateJsonParms(jsonObject, "displayFlag", "pictureUrl", "carouselName", "orderSequence", "carouselPosition", "carouselType");
            jsonObject.put("startDateActive", new Date());
            BaseCmsCarouselEntity_HI entity = baseCmsCarouselServer.saveOrUpdate(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, new JSONArray().fluentAdd(entity)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * app loading页面维护接口
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAppLoading")
    public String saveOrUpdateAppLoading(@RequestParam(required = true) String params) {
        try {
            if (!Objects.equals(getUserSessionBean().getIsadmin(),"Y"))
                throw new IllegalArgumentException("非管理员不允许修改");
            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "displayFlag", "pictureUrl", "carouselName", "carouselPosition");
            jsonObject.put("carouselType","APP_LOADING");
            jsonObject.put("startDateActive", new Date());
            BaseCmsCarouselEntity_HI entity = baseCmsCarouselServer.saveOrUpdate(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, new JSONArray().fluentAdd(entity)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 删除数据
     *
     * @param params 参数id
     *               {
     *               id:需要删除的数据Id，如果需要删除多个，则用;分隔
     *               }
     * @return
     * @author yuzhenli
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    @Override
    public String delete(@RequestParam(required = false) String params) {
        return super.delete(params);
    }

    @RequestMapping(method = RequestMethod.POST, value = "findList")
    @Override
    public String findList(String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            List<BaseCmsCarouselEntity_HI> list = baseCmsCarouselServer.findList(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }
}
