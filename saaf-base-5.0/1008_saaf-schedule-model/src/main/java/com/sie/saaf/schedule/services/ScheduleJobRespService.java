package com.sie.saaf.schedule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.schedule.exception.NotLoginException;
import com.sie.saaf.schedule.model.entities.ScheduleJobRespEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsRespEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IScheduleJobResp;
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

//@Component("saafJobRespService")
@RestController
@RequestMapping("/scheduleJobRespService")
public class ScheduleJobRespService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobRespService.class);
    @Autowired
    private IScheduleJobResp scheduleJobRespServer;

    public ScheduleJobRespService() {
        super();
    }

    @Override
    public IBaseCommon<ScheduleJobRespEntity_HI> getBaseCommonServer() {
        return scheduleJobRespServer;
    }

    /**
     * 查询调度JOB职责信息
     *
     * @param params
     * @param curIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSaafJobRespInfo")
    public String findSaafJobRespInfo(@RequestParam("params") String params,
                                      @RequestParam(value = "pageIndex", defaultValue = "1") Integer curIndex,
                                      @RequestParam(value = "pageRows", defaultValue = "10") Integer pageSize) {
        LOGGER.info(params);
        JSONObject paramJSON = JSON.parseObject(params);
        List<ScheduleJobRespEntity_HI> scheduleJobRespList = scheduleJobRespServer.findSaafJobRespInfo(paramJSON);

        return SToolUtils.convertResultJSONObj("S", "查询成功！", scheduleJobRespList.size(), scheduleJobRespList).toString();

    }

    /**
     * 查询调度JOB所有职责（职责分配JOB）
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findJobRespAll")
    public String findJobRespAll(@RequestParam("params") String params) {
        JSONObject paramJSON = JSON.parseObject(params);
        Pagination<ScheduleJobsRespEntity_HI_RO> list = scheduleJobRespServer.findJobRespAll(paramJSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject = (JSONObject) JSON.toJSON(list);
        jsonObject.put(SToolUtils.STATUS, "S");
        jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
        String resultStr = jsonObject.toString();
        LOGGER.info(resultStr);
        return resultStr;
    }

    /**
     * LOV：查询未分配给JOB的所有职责
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRemainderJobResp")
    public String findRemainderJobResp(@RequestParam("params") String params,
                                       @RequestParam("pageIndex") int pageIndex,
                                       @RequestParam("pageRows") int pageRows) throws NotLoginException {
        try {

            String resultStr = "";
            JSONObject jsonParam = JSON.parseObject(params);


            if (jsonParam.get("jobId") == null || jsonParam.get("jobId") == "") {
                resultStr = SToolUtils.convertResultJSONObj("E", "失败！参数（jobId）为必填!", 0, null).toString();

            } else {
                Pagination RemainderUserResp = scheduleJobRespServer.findRemainderJobResp(jsonParam, pageIndex, pageRows);

                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) JSON.toJSON(RemainderUserResp);
                jsonObject.put(SToolUtils.STATUS, "S");
                jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
                resultStr = jsonObject.toString();
            }


            LOGGER.info(resultStr);

            return resultStr;
        } catch (Exception e) {
            LOGGER.error("查询职责失败！" + e);
            return SToolUtils.convertResultJSONObj("E", "查询职责失败!" + e, 0, null).toString();
        }
    }

    /**
     * 保存JOB职责
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveJobResp")
    public String saveJobResp(@RequestParam("params")
                                      String params) {
        try {
            JSONObject jsonParam = JSON.parseObject(params);
            /*if (baseSaafUserRespServer.findExisUserRespName(jsonParam)) {
                return CommonAbstractServices.convertResultJSONObj("E", "此职责存在重复用户,不允许保存请检查", 0, null);
            }*/
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            List usersRespList = scheduleJobRespServer.saveSaafJobResp(jsonParam);
            return SToolUtils.convertResultJSONObj("S", "保存用户职责列表成功!", usersRespList.size(), usersRespList).toString();
        } catch (Exception e) {
            LOGGER.error("保存用户职责列表异常：" + e);
            return SToolUtils.convertResultJSONObj("E", "保存JOB职责列表失败!" + e, 0, null).toString();
        }
    }

    /**
     * 删除Job职责
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteJobResp")
    public String deleteJobResp(@RequestParam("params") String params) {
        try {
            JSONObject jsonParam = JSON.parseObject(params);
            JSONObject jsonData = scheduleJobRespServer.deleteSaafJobResp(jsonParam);
            return SToolUtils.convertResultJSONObj(jsonData.getString("status"), jsonData.getString("msg"), jsonData.getInteger("count"), null).toString();
        } catch (Exception e) {
            LOGGER.error("删除用户职责列表异常：" + e);
            return SToolUtils.convertResultJSONObj("E", "删除JOB职责列表失败!" + e, 0, null).toString();
        }
    }
}