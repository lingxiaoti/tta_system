package com.sie.saaf.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportChartsTypeEntity_HI;
import com.sie.saaf.base.report.model.inter.IBaseReportChartsType;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
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

import java.util.Objects;

/**
 * @author huangtao
 */

@RestController
@RequestMapping("/baseReportChartsTypeService")
public class BaseReportChartsTypeService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseReportChartsTypeService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @Autowired
    private IBaseReportChartsType baseReportChartsTypeServer;


    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                       @RequestParam(required = false, defaultValue = "-1") String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);

            Pagination<BaseReportChartsTypeEntity_HI> pagination = baseReportChartsTypeServer.findPagination(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = -1;
            JSONObject jsonObject = JSON.parseObject(params);
            BaseReportChartsTypeEntity_HI instance = baseReportChartsTypeServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params {id 主键}
     * @description 删除
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            for (String id : ids) {
                baseReportChartsTypeServer.deleteById(Integer.parseInt(id));
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}
