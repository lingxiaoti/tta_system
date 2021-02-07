package com.sie.saaf.schedule.model.inter.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.schedule.model.entities.ScheduleJobAccessOrgsEntity_HI;
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

import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobInstEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IScheduleJobAccessOrgs;

@Component("scheduleJobAccessOrgsServer")
public class ScheduleJobAccessOrgsServer extends BaseCommonServer<ScheduleJobAccessOrgsEntity_HI> implements IScheduleJobAccessOrgs {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobAccessOrgsServer.class);
	@Autowired
	private ViewObject<ScheduleJobAccessOrgsEntity_HI> scheduleJobAccessOrgsDAO_HI;

	@Autowired
	private DynamicViewObjectImpl<ScheduleJobInstEntity_HI_RO> scheduleJobInstDAO_HI_RO;
	public ScheduleJobAccessOrgsServer() {
		super();
	}

	public List<ScheduleJobAccessOrgsEntity_HI> findSaafJobAccessOrgsInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		queryParamMap.put("jobId", queryParamJSON.getString("jobId"));
		List<ScheduleJobAccessOrgsEntity_HI> findListResult = scheduleJobAccessOrgsDAO_HI.findList("from SaafJobAccessOrgsEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveSaafJobAccessOrgsInfo(JSONObject queryParamJSON) {
        ScheduleJobAccessOrgsEntity_HI saafJobAccessOrgsEntity_HI = JSON.parseObject(queryParamJSON.toString(), ScheduleJobAccessOrgsEntity_HI.class);
		Object resultData = scheduleJobAccessOrgsDAO_HI.save(saafJobAccessOrgsEntity_HI);
		return resultData;
	}
	
    /**
     * 查询JOB属于所有组织树结构
     * @param parameters
     * @return
     */
    public List findJobsInstTree(JSONObject parameters) {
        try {
            StringBuffer querySql = new StringBuffer();
            querySql.append(ScheduleJobInstEntity_HI_RO.QUERY_JOB_INST_TREE_SQL);
            querySql.append(" AND sua.job_id =:jobId");
//            querySql.append(" AND sua.platform_code =:platformCode");
            List list = new ArrayList();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("jobId", parameters.getInteger("jobId"));
//            map.put("platformCode", parameters.getString("platformCode"));
            list = scheduleJobInstDAO_HI_RO.findList(querySql, map);
            return list;
        } catch (Exception e) {
            LOGGER.info("saaf.commmon.fmw.schedule.model.inter.server.SaafJobAccessOrgsServer.findJobsInstTree(JSONObject) error:"+e.getMessage());
        }
        return null;
    }
    
    /**
     * 保存用户组织关系
     * @param parameters
     * @return
     */
    public JSONObject saveJobInsts(JSONObject parameters){
        ScheduleJobAccessOrgsEntity_HI row = null;
        List userInstList = new ArrayList();
        deleteJobInst(parameters);
        //获取页面数据
        int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));
        JSONArray valuesArray = JSON.parseArray(parameters.getString("jobInstData"));
        try {
            for (int i = 0; i < valuesArray.size(); i++) {
                JSONObject valuesJson = valuesArray.getJSONObject(i);
                //判断是新增还是修改
                if (null == valuesJson.get("accessOrgId") || "".equals(SToolUtils.object2String(valuesJson.get("accessOrgId")).trim())) {
                    row = new ScheduleJobAccessOrgsEntity_HI();
                } else {
                    row = scheduleJobAccessOrgsDAO_HI.findByProperty("accessOrgId", SToolUtils.object2Int(valuesJson.get("accessOrgId"))).get(0);
                }
                row.setPlatformCode(SToolUtils.object2String(parameters.get("varPlatformCode")));
                row.setJobId(SToolUtils.object2Int(valuesJson.get("jobId")));
                row.setOrgId(SToolUtils.object2Int(valuesJson.get("orgId")));
                row.setOperatorUserId(varUserId);
                String startDate = SToolUtils.object2String(valuesJson.get("startDate"));
                if (null != valuesJson.get("startDate") && !"".equals(startDate.trim())) {
                    Date startDateActive = SToolUtils.string2DateTime(startDate, "yyyy-MM-dd");
                    row.setStartDate(startDateActive);
                }
                String endDate = SToolUtils.object2String(valuesJson.get("endDate"));
                if (null != valuesJson.get("endDate") && !"".equals(endDate.trim())) {
                    Date endDateActive = SToolUtils.string2DateTime(endDate, "yyyy-MM-dd");
                    row.setEndDate(endDateActive);
                }
                userInstList.add(row);
            }
            scheduleJobAccessOrgsDAO_HI.saveOrUpdateAll(userInstList);
            return SToolUtils.convertResultJSONObj("S", "保存成功！", 0, userInstList);
        } catch (Exception e) {
        	LOGGER.info("saaf.commmon.fmw.schedule.model.inter.server.SaafJobAccessOrgsServer.saveJobInsts(JSONObject) error:"+e.getMessage());
        }
        return null;
    }
    /**
     * 删除JOB组织关系
     * @param parameters
     * @return
     */
    public Boolean deleteJobInst(JSONObject parameters) {
        try {
        	JSONObject jsonObject =new JSONObject();
        	jsonObject.put("jobId", parameters.getInteger("jobId"));
            List<ScheduleJobAccessOrgsEntity_HI> list = findSaafJobAccessOrgsInfo(jsonObject);
            scheduleJobAccessOrgsDAO_HI.deleteAll(list);
            return true;
        } catch (Exception e) {
        	LOGGER.info("saaf.commmon.fmw.schedule.model.inter.server.SaafJobAccessOrgsServer.deleteJobInst(JSONObject) error:"+e.getMessage());
        }
        return null;
    }
}
