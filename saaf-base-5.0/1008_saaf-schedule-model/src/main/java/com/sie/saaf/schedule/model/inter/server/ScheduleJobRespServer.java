package com.sie.saaf.schedule.model.inter.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.schedule.model.entities.ScheduleJobRespEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsRespEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IScheduleJobResp;

@Component("scheduleJobRespServer")
public class ScheduleJobRespServer extends BaseCommonServer<ScheduleJobRespEntity_HI> implements IScheduleJobResp {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobRespServer.class);
	@Autowired
	private ViewObject<ScheduleJobRespEntity_HI> scheduleJobRespDAO_HI;
	@Autowired
	DynamicViewObjectImpl<ScheduleJobsRespEntity_HI_RO> scheduleJobsRespDAO_HI_RO;
	
	public ScheduleJobRespServer() {
		super();
	}

	public List<ScheduleJobRespEntity_HI> findSaafJobRespInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<ScheduleJobRespEntity_HI> findListResult = scheduleJobRespDAO_HI.findList("from SaafJobRespEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveSaafJobRespInfo(JSONObject queryParamJSON) {
        ScheduleJobRespEntity_HI saafJobRespEntity_HI = JSON.parseObject(queryParamJSON.toString(), ScheduleJobRespEntity_HI.class);
		Object resultData = scheduleJobRespDAO_HI.save(saafJobRespEntity_HI);
		return resultData;
	}

	public Pagination<ScheduleJobsRespEntity_HI_RO> findJobRespAll(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer();
        querySql.append(ScheduleJobsRespEntity_HI_RO.QUERY_JOBS_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobId", queryParamJSON.getInteger("jobId"));
        Pagination<ScheduleJobsRespEntity_HI_RO> rowSet = scheduleJobsRespDAO_HI_RO.findPagination(querySql, map, 1, 20);
        return rowSet;
    }
	 

    /**
     * LOV:查询未分配给用户的所有职责
     * @param parameters
     * @return
     */
    public Pagination findRemainderJobResp(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        try {
            StringBuffer querySql = new StringBuffer();
            querySql.append(ScheduleJobsRespEntity_HI_RO.QUERY_ASSIGNED_RESP_TO_JOB_SQL);
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("jobId", SToolUtils.object2Int(parameters.getInteger("jobId")));
            SaafToolUtils.parperParam(parameters, "sr.RESPONSIBILITY_NAME", "responsibilityName", querySql, map, "like");
            //按平台控制分配权限
            SaafToolUtils.parperParam(parameters, "sr.platform_code", "platformCode", querySql, map, "=");
            Pagination<ScheduleJobsRespEntity_HI_RO> rowSet = scheduleJobsRespDAO_HI_RO.findPagination(querySql, map, pageIndex, pageRows);
            return rowSet;
        } catch (Exception e) {
        	LOGGER.info("saaf.commmon.fmw.schedule.model.inter.server.SaafJobRespServer.findRemainderJobResp(JSONObject, Integer, Integer) error:"+e.getMessage());
        }
        
        return null;
    }

    /**
     * 编辑或创建用户职责
     * @param parameters
     * @return
     */
    public List saveSaafJobResp(JSONObject parameters)  {
        ScheduleJobRespEntity_HI row = null;
        List userRespList = new ArrayList();
        //获取页面数据
        int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));
        try {
            JSONArray valuesArray = parameters.getJSONArray("jobRespData");
            for (int i = 0; i < valuesArray.size(); i++) {
                JSONObject valuesJson = valuesArray.getJSONObject(i);
                //判断是新增还是修改
                row = new ScheduleJobRespEntity_HI();
                row.setCreatedBy(varUserId); // 用户登录的userId，从session里面获取
                row.setCreationDate(new Date());
                row.setLastUpdatedBy(varUserId);
                row.setLastUpdateDate(new Date());
                row.setLastUpdateLogin(varUserId);
                row.setStartDateActive(new Date());
                row.setPlatformCode(SToolUtils.object2String(valuesJson.get("varPlatformCode") == null ? parameters.get("varPlatformCode") : valuesJson.get("varPlatformCode")));
                row.setJobId(SToolUtils.object2Int(valuesJson.get("jobId")));
                row.setResponsibilityId(SToolUtils.object2Int(valuesJson.get("respId") == null ? parameters.get("respId") : valuesJson.get("respId")));
                row.setJobRespName(SToolUtils.object2String(valuesJson.get("JobRespName")));
                userRespList.add(row);
            }
            scheduleJobRespDAO_HI.saveOrUpdateAll(userRespList);
            return userRespList;
        } catch (Exception e) {
        	LOGGER.info("saaf.commmon.fmw.schedule.model.inter.server.SaafJobRespServer.saveSaafJobResp(JSONObject) error:"+e.getMessage());
        }
		return null;
    }

    /**
     * 删除JOB职责
     * @param parameters
     * @return
     */
    public JSONObject deleteSaafJobResp(JSONObject parameters) {
        try {
            Integer jobRespId = null;
            jobRespId = parameters.getInteger("jobRespId");
            ScheduleJobRespEntity_HI row = scheduleJobRespDAO_HI.getById(SToolUtils.object2Int(jobRespId));
            scheduleJobRespDAO_HI.delete(row);
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null);
        } catch (Exception e) {
        	LOGGER.info("saaf.commmon.fmw.schedule.model.inter.server.SaafJobRespServer.deleteSaafJobResp(JSONObject) error:"+e.getMessage());
        }
        return null;
    }

}
