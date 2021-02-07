package com.sie.saaf.schedule.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.schedule.model.entities.ScheduleJobParametersEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobParametersEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IJobParameters;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("jobParametersServer")
public class JobParametersServer extends BaseCommonServer<ScheduleJobParametersEntity_HI> implements IJobParameters {

    private static Logger log = LoggerFactory.getLogger(JobParametersServer.class);

    @Autowired
    private ViewObject<ScheduleJobParametersEntity_HI> scheduleJobParametersDAO_HI;
    @Autowired
    private BaseViewObject<ScheduleJobParametersEntity_HI_RO> scheduleJobParametersDAO_HI_RO;


    public JobParametersServer() {
        super();
    }

    public Pagination<ScheduleJobParametersEntity_HI_RO> findJobParameters(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        //BaseViewObject vo = this.scheduleJobParametersDAO_HI_RO;
        StringBuffer sqlSB = new StringBuffer(ScheduleJobParametersEntity_HI_RO.QUERY_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<ScheduleJobParametersEntity_HI_RO> rows = null;
        SaafToolUtils.parperParam(parameters, "sjp.PARAM_ID", "paramId", sqlSB, map, "=");
//        if (null != parameters.get("paramId") && !"".equals(parameters.get("paramId"))) {
//            map.put("varParamId", Integer.parseInt(parameters.get("paramId").toString()));
//            where = where + " and sjp.PARAM_ID = :varParamId ";
//        }
        SaafToolUtils.parperParam(parameters, "sjp.JOB_ID", "jobId", sqlSB, map, "=");
//        if (null != parameters.get("jobId") && !"".equals(parameters.get("jobId"))) {
//            map.put("varJobId", Integer.parseInt(parameters.get("jobId").toString()));
//            where = where + " and sjp.JOB_ID = :varJobId ";
//        }
        SaafToolUtils.parperParam(parameters, "sjp.PARAM_SEQ_NUM", "paramSeqNum", sqlSB, map, "=");
//        if (null != parameters.get("paramSeqNum") && !"".equals(parameters.get("paramSeqNum"))) {
//            map.put("varParamSeqNum", Integer.parseInt(parameters.get("paramSeqNum").toString()));
//            where = where + " and sjp.PARAM_SEQ_NUM = :varParamSeqNum ";
//        }
        SaafToolUtils.parperParam(parameters, "sjp.PARAM_NAME", "paramName", sqlSB, map, "=");
//        if (null != parameters.get("paramName") && !"".equals(parameters.get("paramName"))) {
//            map.put("varParamName", parameters.get("paramName").toString());
//            where = where + " and sjp.PARAM_NAME = :varParamName ";
//        }
        SaafToolUtils.parperParam(parameters, "sjp.PARAM_TYPE", "paramType", sqlSB, map, "=");
//        if (null != parameters.get("paramType") && !"".equals(parameters.get("paramType"))) {
//            map.put("varParamType", Integer.parseInt(parameters.get("paramType").toString()));
//            where = where + " and sjp.PARAM_TYPE = :varParamType ";
//        }
        SaafToolUtils.parperParam(parameters, "sjp.PARAM_REGION", "paramRegion", sqlSB, map, "=");
//        if (null != parameters.get("paramRegion") && !"".equals(parameters.get("paramRegion"))) {
//            map.put("varParamRegion", parameters.get("paramRegion").toString());
//            where = where + " and sjp.PARAM_REGION = :varParamRegion ";
//        }

        //当 提交请求 时，查询参数操作 则仅仅查询已启用的参数（即：isEnabled字段标识为‘Y’）
        //isNotSubmitRequest 值为‘Y’时，表示当前查询操作为 新增 或 编辑 job参数
        //                   值为‘N’时，表示当前查询为 提交请求 时的查询参数操作 , 此时 仅仅 查询 ‘已启用’的参数

        if (parameters.get("isNotSubmitRequest") != null && parameters.get("isNotSubmitRequest").toString().equalsIgnoreCase("N")) {
            sqlSB.append(" and sjp.IS_ENABLED = 'Y'");
//            where = where + " and sjp.IS_ENABLED = 'Y' ";
        }
        //结果排序：paramSeqNum 字段 升序
        //where = where + " order by sjp.PARAM_SEQ_NUM asc";
        sqlSB.append(" order by sjp.PARAM_SEQ_NUM asc");
        if (map.size() > 0) {
            rows = scheduleJobParametersDAO_HI_RO.findPagination(sqlSB.toString(), map, pageIndex, pageRows);
        } else {
            rows = scheduleJobParametersDAO_HI_RO.findPagination(sqlSB.toString(), pageIndex, pageRows);
        }
        return rows;
    }

    public String saveParameter(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        //参数所属的jobId
        if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobId为必填!", 0, null);
            return jsonResult.toString();
        }
        //参数序号
        /*if (parameters.get("paramSeqNum") == null || parameters.get("paramSeqNum") == "") {
            jsonResult = SaafToolUtils.convertResultJSONObj("E", "保存失败！参数序号（paramSeqNum）为必填!", 0, null);
            return jsonResult.toString();
        }*/
        //参数名称
        if (parameters.get("paramName") == null || parameters.get("paramName") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！参数名称（paramName）为必填!", 0, null);
            return jsonResult.toString();
        }
        //参数类型
        if (parameters.get("paramType") == null || parameters.get("paramType") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！参数类型（paramType）为必填!", 0, null);
            return jsonResult.toString();
        }

      /*  try {
            Integer.parseInt(parameters.get("paramSeqNum").toString());
        } catch (NumberFormatException e) {
            jsonResult = SaafToolUtils.convertResultJSONObj("E", "保存失败！参数序号（paramSeqNum）必须为数字", 0, null);
            return jsonResult.toString();
        }*/

//        if (isParamRegionValue_BODY_Repeat(parameters)) {
//            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！'BODY'位置已存在参数，不能再添加", 0, null);
//            return jsonResult.toString();
//        }
        //
        StringBuilder msg = new StringBuilder();
        if (isParamSeqNumRepeat(parameters)) {
            msg.append("参数序号（paramSeqNum）重复 ");
        }
        if (isParamNameRepeat(parameters)) {
            msg.append("参数名称（paramName）重复 ");
        }
        log.info("--------------------- createParameters ---------" + msg + "----------------------");
        if (msg.toString().length() > 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！" + msg.toString(), 0, null);
            return jsonResult.toString();
        }
        //        ViewObject vo = (ViewObject)SaafToolUtils.context.getBean("saafJobParametersDAO_HI");
        //ViewObject vo = this.saafJobParametersDAO_HI;
        ScheduleJobParametersEntity_HI row = new ScheduleJobParametersEntity_HI();
        int userId = Integer.parseInt(parameters.get("varUserId").toString());
        row.setCreationDate(new Date());
        row.setCreatedBy(userId);
        row.setLastUpdateDate(new Date());
        row.setLastUpdateLogin(userId);
        row.setLastUpdatedBy(userId);
        row.setJobId(Integer.parseInt(parameters.get("jobId").toString()));
        row.setParamSeqNum(Integer.parseInt(parameters.get("paramSeqNum").toString()));
        row.setParamName(parameters.get("paramName").toString());
        row.setParamType(parameters.get("paramType").toString());
        row.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : null);
        row.setIsRequired(parameters.get("isRequired") != null ? parameters.get("isRequired").toString() : "");
        //默认启用
        row.setIsEnabled(parameters.get("isEnabled") != null ? parameters.get("isEnabled").toString() : "Y");
        row.setDefaultValue(parameters.get("defaultValue") != null ? parameters.get("defaultValue").toString() : "");
        ////记录参数位置：WebService（url、body、head），package（in、out）
        row.setParamRegion((parameters.get("paramRegion") != null ? parameters.get("paramRegion").toString() : ""));
        scheduleJobParametersDAO_HI.save(row);
        jsonResult = SToolUtils.convertResultJSONObj("S", "新增Job参数成功!", 1, null);
        return jsonResult.toString();
    }
    
	public String saveParameterInfo(JSONObject parameters) {
		com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
		// 参数名称
		if (parameters.get("paramName") == null || parameters.get("paramName") == "") {
			jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数名称（paramName）为必填!", 0, null);
			return jsonResult.toString();
		}
		// 参数类型
		if (parameters.get("paramType") == null || parameters.get("paramType") == "") {
			jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数类型（paramType）为必填!", 0, null);
			return jsonResult.toString();
		}
        if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数类型（jobId）为必填!", 0, null);
            return jsonResult.toString();
        }
		
		//
		StringBuilder msg = new StringBuilder();
		if (isParamNameRepeat(parameters)) {
			msg.append("参数名称（paramName）重复 ");
		}
		log.info("--------------------- editParameter ---------" + msg + "----------------------");
		if (msg.toString().length() > 0) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "保存更新失败！" + msg.toString(), 0, null);
			return jsonResult.toString();
		}
		if (parameters.get("paramId") == null || parameters.get("paramId") == "") {
          //新增
//			if (isParamRegionValue_BODY_Repeat(parameters)) {
//				jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！'BODY'位置已存在参数，不能再添加", 0, null);
//				return jsonResult.toString();
//			}
	
			//ViewObject vo = this.scheduleJobParametersDAO_HI;
            ScheduleJobParametersEntity_HI row = new ScheduleJobParametersEntity_HI();
			int userId = Integer.parseInt(parameters.get("varUserId").toString());
			row.setCreationDate(new Date());
			row.setCreatedBy(userId);
			row.setLastUpdateDate(new Date());
			row.setLastUpdateLogin(userId);
			row.setLastUpdatedBy(userId);
			row.setJobId(Integer.parseInt(parameters.get("jobId").toString()));
			int seconds = (int) (System.currentTimeMillis() / 1000);
			row.setParamSeqNum(seconds);//从数字改为jobId 保持唯一性
			row.setParamName(parameters.get("paramName").toString());
			row.setParamType(parameters.get("paramType").toString());
			row.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : null);
			row.setIsRequired(parameters.get("isRequired") != null ? parameters.get("isRequired").toString() : "");
			// 默认启用
			row.setIsEnabled(parameters.get("isEnabled") != null ? parameters.get("isEnabled").toString() : "Y");
			row.setDefaultValue(
					parameters.get("defaultValue") != null ? parameters.get("defaultValue").toString() : "");
			//// 记录参数位置：WebService（url、body、head），package（in、out）
			row.setParamRegion((parameters.get("paramRegion") != null ? parameters.get("paramRegion").toString() : ""));
			//
            scheduleJobParametersDAO_HI.save(row);
			//
			jsonResult = SToolUtils.convertResultJSONObj("S", "新增Job参数成功!", 1, row);
			return jsonResult.toString();
		} else {
			// 更新job参数时： 参数Id、参数序号、参数名称、参数类型 为必填。 jobId 不会被修改。

			// 参数位置
			if (parameters.get("paramRegion") == null || parameters.get("paramRegion") == "") {
				jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数位置（paramRegion）为必填!", 0, null);
				return jsonResult.toString();
			}
			// 参数所属的 jobId
			if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
				jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！jobId为必填!", 0, null);
				return jsonResult.toString();
			}
//			if (isParamRegionValue_BODY_Repeat(parameters)) {
//				jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！'BODY'位置已存在参数，不能再添加!", 0, null);
//				return jsonResult.toString();
//			}
			//ViewObject vo = this.scheduleJobParametersDAO_HI;
            ScheduleJobParametersEntity_HI row = scheduleJobParametersDAO_HI.findByProperty("paramId", Integer.parseInt(parameters.get("paramId").toString())).get(0);

			// 跟踪修改
			row.setLastUpdateDate(new Date());
			row.setLastUpdateLogin(-1);
			row.setLastUpdatedBy(Integer.parseInt(parameters.get("varUserId").toString()));
			//新增的时候 已插入
		   // row.setParamSeqNum(1/*Integer.parseInt(parameters.get("paramSeqNum").toString())*/);
			row.setParamName(parameters.get("paramName") != null ? parameters.get("paramName").toString() : "");
			row.setParamType(parameters.get("paramType") != null ? parameters.get("paramType").toString() : "");
			row.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : null);
			row.setIsRequired(parameters.get("isRequired") != null ? parameters.get("isRequired").toString() : "");
			// 默认启用
			row.setIsEnabled(parameters.get("isEnabled") != null ? parameters.get("isEnabled").toString() : "Y");
			row.setDefaultValue(
					parameters.get("defaultValue") != null ? parameters.get("defaultValue").toString() : "");
			// 记录参数位置：WebService（url、body、head），package（in、out）
			row.setParamRegion((parameters.get("paramRegion") != null ? parameters.get("paramRegion").toString() : ""));
            scheduleJobParametersDAO_HI.saveOrUpdate(row);
			jsonResult = SToolUtils.convertResultJSONObj("S", "编辑成功!", 1, row);
			return jsonResult.toString();

		}
	}

    public String deleteJobParameter(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        if (parameters.get("paramId") == null || parameters.get("paramId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败!未指定 Job参数id（paramId）", 0, null);
            return jsonResult.toString();
        }
        //ViewObject vo = this.scheduleJobParametersDAO_HI;
        ScheduleJobParametersEntity_HI row = (ScheduleJobParametersEntity_HI)scheduleJobParametersDAO_HI.getById(Integer.parseInt(parameters.get("paramId").toString()));
        if (row != null) {
            scheduleJobParametersDAO_HI.delete(row);
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "删除Job参数成功!", 1, null);
        return jsonResult.toString();
    }

    public String updateJobParameter(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        log.info("---------------------- editParameter ------------ " + parameters.toString());
        //更新job参数时： 参数Id、参数序号、参数名称、参数类型 为必填。 jobId 不会被修改。
        //参数Id
        if (parameters.get("paramId") == null || parameters.get("paramId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数Id（paramId）为必填!", 0, null);
            return jsonResult.toString();
        }
        //参数序号
       /*if (parameters.get("paramSeqNum") == null || parameters.get("paramSeqNum") == "") {
            jsonResult = SaafToolUtils.convertResultJSONObj("E", "更新失败！参数序号（paramSeqNum）为必填!", 0, null);
            return jsonResult.toString();
        }*/
        //参数名称
        if (parameters.get("paramName") == null || parameters.get("paramName") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数名称（paramName）为必填!", 0, null);
            return jsonResult.toString();
        }
        //参数类型
        if (parameters.get("paramType") == null || parameters.get("paramType") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数类型（paramType）为必填!", 0, null);
            return jsonResult.toString();
        }
        //参数位置
        if (parameters.get("paramRegion") == null || parameters.get("paramRegion") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！参数位置（paramRegion）为必填!", 0, null);
            return jsonResult.toString();
        }
        //参数所属的 jobId
        if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！jobId为必填!", 0, null);
            return jsonResult.toString();
        }

        /*try {
            Integer.parseInt(parameters.get("paramSeqNum").toString());
        } catch (NumberFormatException e) {
            jsonResult = SaafToolUtils.convertResultJSONObj("E", "更新失败！参数序号（paramSeqNum）必须为数字（整数）!", 0, null);
            return jsonResult.toString();
        }*/

//        if (isParamRegionValue_BODY_Repeat(parameters)) {
//            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！'BODY'位置已存在参数，不能再添加!", 0, null);
//            return jsonResult.toString();
//        }
        StringBuilder msg = new StringBuilder();
        log.info("--------------------- editParameter ---------" + msg + "----------------------");
        if (msg.toString().length() > 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "更新失败！" + msg.toString(), 0, null);
            return jsonResult.toString();
        }
        //ViewObject vo = this.scheduleJobParametersDAO_HI;
        ScheduleJobParametersEntity_HI row = (ScheduleJobParametersEntity_HI)scheduleJobParametersDAO_HI.findByProperty("paramId", Integer.parseInt(parameters.get("paramId").toString())).get(0);

        //跟踪修改
        row.setLastUpdateDate(new Date());
        row.setLastUpdateLogin(-1);
        row.setLastUpdatedBy(Integer.parseInt(parameters.get("varUserId").toString()));
        //
        //row.setParamSeqNum(Integer.parseInt(parameters.get("paramSeqNum").toString()));
        row.setParamName(parameters.get("paramName") != null ? parameters.get("paramName").toString() : "");
        row.setParamType(parameters.get("paramType") != null ? parameters.get("paramType").toString() : "");
        row.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : null);
        row.setIsRequired(parameters.get("isRequired") != null ? parameters.get("isRequired").toString() : "");
        //默认启用
        row.setIsEnabled(parameters.get("isEnabled") != null ? parameters.get("isEnabled").toString() : "Y");
        row.setDefaultValue(parameters.get("defaultValue") != null ? parameters.get("defaultValue").toString() : "");
        //记录参数位置：WebService（url、body、head），package（in、out）
        row.setParamRegion((parameters.get("paramRegion") != null ? parameters.get("paramRegion").toString() : ""));
        scheduleJobParametersDAO_HI.saveOrUpdate(row);
        jsonResult = SToolUtils.convertResultJSONObj("S", "编辑成功!", 1, null);
        return jsonResult.toString();

    }

    private boolean isParamSeqNumRepeat(JSONObject parameters) {
        //ViewObject vo = (ViewObject)SaafToolUtils.context.getBean("saafJobParametersDAO_HI");
        Map<String, Object> map = new HashMap<String, Object>();
        String where = " where 1=1 ";

        Pagination<ScheduleJobParametersEntity_HI> rows = null;

        int paramSeqNum = Integer.parseInt(parameters.get("paramSeqNum").toString());
        where = where + " and paramSeqNum = :varParamSeqNum ";
        map.put("varParamSeqNum", paramSeqNum);

        int jobId = Integer.parseInt(parameters.get("jobId").toString());
        where = where + " and jobId = :varJobId ";
        map.put("varJobId", jobId);

        //paramId 为空时 表示当前操作为 新增参数
        if (null == parameters.get("paramId")) {
            rows = scheduleJobParametersDAO_HI.findPagination("from " + scheduleJobParametersDAO_HI.getEntityClass().getSimpleName() + where, map, 0, -1);
        } else {
            int paramId = Integer.parseInt(parameters.get("paramId").toString());
            where = where + " and paramId != :varParamId ";
            map.put("varParamId", paramId);
            rows = scheduleJobParametersDAO_HI.findPagination("from " + scheduleJobParametersDAO_HI.getEntityClass().getSimpleName() + where, map, 0, -1);
        }

        if (rows != null && rows.getData().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isParamNameRepeat(JSONObject parameters) {
        Map<String, Object> map = new HashMap<String, Object>();
        String where = " where 1=1 ";

        Pagination<ScheduleJobParametersEntity_HI> rows = null;
        String paramName = parameters.get("paramName").toString();
        where = where + " and paramName = :varParamName ";
        map.put("varParamName", paramName);

        int jobId = Integer.parseInt(parameters.get("jobId").toString());
        where = where + " and jobId = :varJobId ";
        map.put("varJobId", jobId);

        //paramId 为空时 表示当前操作为 新增参数
        if (null == parameters.get("paramId")) {
            rows = scheduleJobParametersDAO_HI.findPagination("from " + scheduleJobParametersDAO_HI.getEntityClass().getSimpleName() + where, map, 0, -1);
        } else {
            int paramId = Integer.parseInt(parameters.get("paramId").toString());
            where = where + " and paramId != :varParamId ";
            map.put("varParamId", paramId);
            rows = scheduleJobParametersDAO_HI.findPagination("from " + scheduleJobParametersDAO_HI.getEntityClass().getSimpleName() + where, map, 0, -1);
        }

        if (rows != null && rows.getData().size() > 0) {
            return true;
        } else {
            return false;
        }

    }

//    private boolean isParamRegionValue_BODY_Repeat(JSONObject parameters) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        String where = " where 1=1 ";
//        Pagination<ScheduleJobParametersEntity_HI> rows = null;
//        where = where + " and paramRegion = :varParamRegion ";
//        map.put("varParamRegion", "BODY");
//        int jobId = Integer.parseInt(parameters.get("jobId").toString());
//        where = where + " and jobId = :varJobId ";
//        map.put("varJobId", jobId);
//
//        //paramId 为空时 表示当前操作为 新增参数
//        if (null == parameters.get("paramId")) {
//            rows = scheduleJobParametersDAO_HI.findPagination("from " + scheduleJobParametersDAO_HI.getEntityClass().getSimpleName() + where, map, 0, -1);
//        } else {
//            int paramId = Integer.parseInt(parameters.get("paramId").toString());
//            where = where + " and paramId != :varParamId ";
//            map.put("varParamId", paramId);
//            rows = scheduleJobParametersDAO_HI.findPagination("from " + scheduleJobParametersDAO_HI.getEntityClass().getSimpleName() + where, map, 0, -1);
//        }
//        if (rows != null && rows.getData().size() > 0 && parameters.get("paramRegion").toString().equalsIgnoreCase("BODY")) {
//            return true;
//        } else {
//            return false;
//        }
//    }


}