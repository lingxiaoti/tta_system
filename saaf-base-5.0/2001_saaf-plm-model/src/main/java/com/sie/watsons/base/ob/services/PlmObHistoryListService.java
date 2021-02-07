package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmObHistoryListEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmObHistoryListEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmObHistoryList;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plmObHistoryListService")
public class PlmObHistoryListService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmObHistoryListService.class);
    @Autowired
    private IPlmObHistoryList plmObHistoryListServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.plmObHistoryListServer;
    }

    /**
     * 查询
     *
     * @param params    {
     *                  teamFrameworkId:框架ID
     *                  }
     * @param pageIndex 当前页码
     * @param pageRows  页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmObHistoryListInfo")
    public String findPlmObHistoryListInfo(@RequestParam(required = false) String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmObHistoryListEntity_HI_RO> plmObHistoryListList = plmObHistoryListServer.findPlmObHistoryListInfo(queryParamJSON, pageIndex, pageRows);
            queryParamJSON = (JSONObject) JSON.toJSON(plmObHistoryListList);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();

        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @description 保存
     **/
    @RequestMapping(method = RequestMethod.POST, value = "savePlmObHistoryListInfo")
    public String savePlmObHistoryListInfo(@RequestParam(required = true) String params) {
        try {
            JSONObject parseObject = parseObject(params);
            int userId = parseObject.getInteger("varUserId");
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(plmObHistoryListServer.savePlmObHistoryListInfo(parseObject));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();

        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (IllegalStateException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @description 保存
     **/
    @RequestMapping(method = RequestMethod.POST, value = "saveAndTransferObHistoryList")
    public String saveAndTransferObHistoryList(@RequestParam(required = true) String params) {
        try {
            JSONObject parseObject = parseObject(params);
            int userId = parseObject.getInteger("varUserId");
            JSONObject jsonObject = plmObHistoryListServer.importAndTransferObHistoryList();
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (IllegalStateException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}