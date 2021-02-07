package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.report.model.entities.TtaIrTermsEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaIrTermsEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaIrTerms;
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

/**
 * Created by Administrator on 2019/8/2/002.
 */
@RestController
@RequestMapping("/ttaIrTermsService")
public class TtaIrTermsService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaIrTermsService.class);

    @Autowired
    private ITtaIrTerms ttaIrTermsServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.ttaIrTermsServer;
    }

    /**
     * @param params    {
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 查询列表（带分页）
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {

            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            Pagination<TtaIrTermsEntity_HI_RO> result = ttaIrTermsServer.find(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "dataImport")
    public String dataImport(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaIrTermsEntity_HI instance = ttaIrTermsServer.saveOrUpdate(jsonObject, userId);
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
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaIrTermsEntity_HI instance = ttaIrTermsServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}