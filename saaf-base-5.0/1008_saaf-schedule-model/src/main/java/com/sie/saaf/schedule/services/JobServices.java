package com.sie.saaf.schedule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.schedule.model.entities.ScheduleJobsEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IJobs;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;


@RestController
@RequestMapping("/jobServices")
public class JobServices extends CommonAbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServices.class);
    @Autowired
    private IJobs jobsServer;
    public JobServices() {
        super();
    }

    @Override
    public IBaseCommon<ScheduleJobsEntity_HI> getBaseCommonServer() {
        return jobsServer;
    }

    /**
     *
     * @param params
     * @param pageIndex
     *              页码
     * @param pageRows
     *              每页查询记录数
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findJobs")
    public String findJobs(@RequestParam("params") String params,
                           @RequestParam(value="pageIndex", defaultValue="1") Integer pageIndex,
                           @RequestParam(value = "pageRows", defaultValue ="10") Integer pageRows) {
        try {
            JSONObject jsonParam = parseObject(params);
            // 去除空值
            Iterator<String> iterator = jsonParam.keySet().iterator();
            while (iterator.hasNext()){
                if(StringUtils.isBlank(jsonParam.getString(iterator.next()))){
                    iterator.remove();
                }
            }
            Pagination<ScheduleJobsEntity_HI_RO> result = jobsServer.findJobs(jsonParam, pageIndex, pageRows);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error("findJobs 参数，" + params.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "查询Job失败", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteJob")
    public String deleteJob(@RequestParam("params")
    String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return jobsServer.deleteJob(jsonParam);
        } catch (Exception e) {
            LOGGER.error("deleteJob 参数，" + params.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "删除Job失败", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveJob")
    public String saveJob(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
          //  jsonParam.put("varUserId", -9999);
            return jobsServer.saveJobInfo(jsonParam);
        } catch (Exception e) {
            LOGGER.error("saveJob 参数，" + params.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "保存Job失败", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateJob")
    public String updateJob(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            //jsonParam.put("varUserId", -9999);
            return jobsServer.updateJob(jsonParam);
        } catch (Exception e) {
            LOGGER.error("updateJob 参数，" + params.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "更新Job失败", 0, null).toString();
        }
    }
}
