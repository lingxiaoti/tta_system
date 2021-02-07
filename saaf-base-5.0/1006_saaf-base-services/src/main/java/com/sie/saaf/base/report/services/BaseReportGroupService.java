package com.sie.saaf.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportGroupEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportHeaerDatasourceEntity_HI_RO;
import com.sie.saaf.base.report.model.inter.IBaseReportGroup;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */
@RestController
@RequestMapping("/baseReportGroupService")
public class BaseReportGroupService extends CommonAbstractService {

    private static final Logger log = LoggerFactory.getLogger(BaseReportGroupService.class);

    @Autowired
    private IBaseReportGroup baseReportGroupServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return baseReportGroupServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        return super.findPagination(params, pageIndex, pageRows);
    }


    /**
     * 查询报表组详情
     *
     * @param params    JSON参数，查询条件的JSON格式
     *                  {
     *                  reportGroupId:'报表组id'
     *                  }
     * @return BaseReportHeaderEntity_HI
     */
    @RequestMapping(method = RequestMethod.POST, value = "findGroupDetails")
    public String findGroupDetails(@RequestParam String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "reportGroupId");
            List<BaseReportHeaerDatasourceEntity_HI_RO> list = baseReportGroupServer.deleteAndFindReportGroupDetail(jsonObject.getInteger("reportGroupId"));
            BaseReportGroupEntity_HI entity = baseReportGroupServer.getById(jsonObject.getInteger("reportGroupId"));
            JSONObject returnValue = JSONObject.parseObject(JSON.toJSONString(entity));
            returnValue.put("groupDetail",list);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), new JSONArray().fluentAdd(returnValue)).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    /**
     * 保存或更新数据
     *
     * @param params JSON参数 {
     *               group:{报表组信息},
     *               header:[{报表头信息}]
     *               }
     * @return BaseReportGroupEntity_HI
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            BaseReportGroupEntity_HI group = baseReportGroupServer.saveOrUpdateReportGroup(jsonObject.getJSONObject("group"), jsonObject.getJSONArray("header"), getUserSessionBean());
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, group).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    /**
     * 删除报表中的报表
     * @param params {
     *     reportGroupId:报表组Id,
     *     reportHeaderId:报表Id，多个报表用,逗号隔开
     * }
     * @author ZhangJun
     * @createTime 2018/2/1
     * @description 删除报表中的报表
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteReportHeaderInGroup")
    public String deleteReportHeaderInGroup(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"reportGroupId","reportHeaderId");

            String[] ids = jsonObject.getString("reportHeaderId").split(",");
            baseReportGroupServer.deleteReportHeaderInGroup(jsonObject.getInteger("reportGroupId"),ids);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, ids.length, ids).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
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
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam() String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            baseReportGroupServer.delete(ids);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, ids.length, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败," + e.getMessage(), 0, null).toString();
        }
    }


}
