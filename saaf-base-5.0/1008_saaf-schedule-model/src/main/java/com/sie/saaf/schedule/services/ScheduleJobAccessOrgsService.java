package com.sie.saaf.schedule.services;

import java.util.List;


import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.schedule.model.entities.ScheduleJobAccessOrgsEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.sie.saaf.schedule.model.inter.IScheduleJobAccessOrgs;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Component("scheduleJobAccessOrgsService")
@RestController
@RequestMapping("/scheduleJobAccessOrgsService")
public class ScheduleJobAccessOrgsService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobAccessOrgsService.class);
	@Autowired
	private IScheduleJobAccessOrgs scheduleJobAccessOrgsServer;

	public ScheduleJobAccessOrgsService() {
		super();
	}

    @Override
    public IBaseCommon<ScheduleJobAccessOrgsEntity_HI> getBaseCommonServer() {
        return scheduleJobAccessOrgsServer;
    }

    /**
	 * 
	 * @param params
	 * @param curIndex
	 * @param pageSize
	 * @return
	 */
    @RequestMapping(method = RequestMethod.POST, value = "findSaafJobAccessOrgsInfo")
	public String findSaafJobAccessOrgsInfo(@RequestParam("params") String params,
                                            @RequestParam(value="pageIndex", defaultValue= "1") Integer curIndex,
                                            @RequestParam(value = "pageRows", defaultValue = "10") Integer pageSize) {
		LOGGER.info(params);
		JSONObject paramJSON = JSON.parseObject(params);

        JSONObject jsonObject = new JSONObject();
        List<ScheduleJobAccessOrgsEntity_HI> result = scheduleJobAccessOrgsServer.findSaafJobAccessOrgsInfo(paramJSON);
        jsonObject = (JSONObject) JSON.toJSON(result);
        jsonObject.put(SToolUtils.STATUS, "S");
        jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);

		String resultStr = jsonObject.toJSONString();
		LOGGER.info(resultStr);
		return resultStr;
	}
	
	 /**
     * 查询JOB属于所有组织树结构
     * @param params
     * @return
     */
     @RequestMapping(method = RequestMethod.POST, value = "findJobsInstTree")
    public String findJobsInstTree(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSON.parseObject(params);
            List instUserlist = scheduleJobAccessOrgsServer.findJobsInstTree(jsonParam);
            return SToolUtils.convertResultJSONObj("S", "查询组织机构树成功！", instUserlist.size(), instUserlist).toString();
        }  catch (Exception e) {
            LOGGER.error("查询组织机构树失败！" + e);
            return SToolUtils.convertResultJSONObj("E", "查询组织机构树失败!" + e, 0, null).toString();
        }
    }
    /**
     * 保存JOB和组织关系
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveJobsInst")
    public String saveJobsInst(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSON.parseObject(params);
            jsonParam.put("varUserId", this.getSessionUserId());
            JSONObject instUserIson = scheduleJobAccessOrgsServer.saveJobInsts(jsonParam);
            return SToolUtils.convertResultJSONObj(instUserIson.getString("status"), instUserIson.getString("msg"), instUserIson.getInteger("count"),
                                                               instUserIson.get("data")).toString();
        } catch (Exception e) {
            LOGGER.error("保存用户组织关系失败！" + e);
            return SToolUtils.convertResultJSONObj("E", "保存用户组织关系失败!" + e, 0, null).toString();
        }
    }
    
   /* public static void main(String[] args) {
             String params="{ \"varUserName\" : \"sysadmin\",\n" +
            		 " \"varUserId\" : 1,\n" +
            		 " \"varPlatformCode\" : \"SAAF\",\n" +
            		 " \"jobId\" : \"37\",\n" +
            		 " \"varIsAdmin\" : \"Y\",\n" +
            		 " \"jobInstData\" :[{ \"jobId\" : \"37\",\n" +
            		 " \"varPlatformCode\" : \"SAAF\",\n" +
            		 " \"instId\" : 66 },{ \"jobId\" : \"37\",\n" +
            		 " \"varPlatformCode\" : \"SAAF\",\n" +
            		 " \"instId\" : 67 }]}";
            JSONObject jsonParam = JSON.parseObject(params);
    		ISaafJobAccessOrgs saafJobAccessOrgsServer=(ISaafJobAccessOrgs)SaafToolUtils.context.getBean("saafJobAccessOrgsServer");
            jsonParam.put("varUserId", 1);
            JSONObject instUserIson = saafJobAccessOrgsServer.saveJobInsts(jsonParam);
         
       
    }*/
}
