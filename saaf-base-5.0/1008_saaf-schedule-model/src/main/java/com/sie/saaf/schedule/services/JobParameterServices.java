package com.sie.saaf.schedule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobParametersEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IJobParameters;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/jobParameterServices")
public class JobParameterServices extends CommonAbstractService {
    @Autowired
    private IJobParameters jobParametersServer;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobParameterServices.class);

    public JobParameterServices() {
        super();
    }

    @Override
    public IBaseCommon getBaseCommonServer() {
        return jobParametersServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteJobParameter")
    public String deleteJobParameter(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return jobParametersServer.deleteJobParameter(jsonParam);
        } catch (Exception e) {
            LOGGER.error("deleteJobParameter 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "删除Job参数失败", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findJobParameters")
    public String findJobParameters(@RequestParam("params") String params,
                                    @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                    @RequestParam(value = "pageRows", defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);

            JSONObject jsonObject = new JSONObject();
            Pagination<ScheduleJobParametersEntity_HI_RO> result = jobParametersServer.findJobParameters(jsonParam, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();

        } catch (Exception e) {
            LOGGER.error("findJobParameters 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "查询Job参数失败", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveJobParameter")
    public String saveJobParameter(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            //jsonParam.put("varUserId", -9999);
            return jobParametersServer.saveParameterInfo(jsonParam);
        } catch (Exception e) {
            LOGGER.error("saveJobParameter 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "保存Job参数失败", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateJobParameter")
    public String updateJobParameter(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            //jsonParam.put("varUserId", -9999);
            return jobParametersServer.updateJobParameter(jsonParam);
        } catch (Exception e) {
            LOGGER.error("updateJobParameter 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "更新Job参数失败", 0, null).toString();
        }
    }
}