package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.dao.readonly.MsgLogDAO_HI_RO;
import com.sie.saaf.message.model.entities.MsgLogEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgLogEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgLog;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component("msgLogServer")
public class MsgLogServer extends BaseCommonServer<MsgLogEntity_HI> implements IMsgLog {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MsgLogServer.class);
	@Autowired
	private ViewObject<MsgLogEntity_HI> msgLogDAO_HI;

	@Autowired
	private MsgLogDAO_HI_RO msgLogDAO_HI_RO;

	public MsgLogServer() {
		super();
	}

	public List<MsgLogEntity_HI> findMsgLogInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<MsgLogEntity_HI> findListResult = msgLogDAO_HI.findList("from MsgLogEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveMsgLogInfo(JSONObject queryParamJSON) {
		MsgLogEntity_HI msgLogEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgLogEntity_HI.class);
		Object resultData = msgLogDAO_HI.save(msgLogEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<MsgLogEntity_HI_RO> findMsgLogList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(MsgLogEntity_HI_RO.QUERY_SELECT_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();

			if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDateStart"))){
				sql.append(" and ml.creation_date >= to_date(:creationDateStart,'YYYY-MM-DD')");
				paramsMap.put("creationDateStart",queryParamJSON.getString("creationDateStart"));
			}

			if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDateEnd"))){
				sql.append(" and ml.creation_date <= to_date(:creationDateEnd,'YYYY-MM-DD hh24:mi:ss')");
				paramsMap.put("creationDateEnd",queryParamJSON.getString("creationDateEnd"));
			}
		SaafToolUtils.parperParam(queryParamJSON, "ml.org_id", "orgId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "ml.job_id", "jobId", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "ml.request_id", "requestId", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "ml.user_name", "userName", sql, paramsMap, "fulllike");
		//SaafToolUtils.parperParam(queryParamJSON, "ml.creation_date", "creationDateStart", sql, paramsMap, ">=");
		//SaafToolUtils.parperParam(queryParamJSON, "ml.creation_date", "creationDateEnd", sql, paramsMap, "<=");
		sql.append(" order by ml.CREATION_DATE desc");
		Pagination<MsgLogEntity_HI_RO> findList = msgLogDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql) ,paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public String deleteMsgLog(JSONObject queryParamJSON, int userId) {
		JSONObject jsonResult = new JSONObject();
		if (queryParamJSON.get("idDetails") == null ) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
			return jsonResult.toString();
		}
		JSONArray msgCfgIdDetails = queryParamJSON.getJSONArray("idDetails");
		if(msgCfgIdDetails!=null && !msgCfgIdDetails.isEmpty()){
			for(int i=0;i<msgCfgIdDetails.size();i++){
				JSONObject object = msgCfgIdDetails.getJSONObject(i);
				String id=object.getString("id");
				MsgLogEntity_HI entity = msgLogDAO_HI.getById(Integer.valueOf(id));
				if (entity != null) {
					entity.setIsDelete(CommonConstants.DELETE_TRUE);
					entity.setOperatorUserId(userId);
					msgLogDAO_HI.update(entity);
				}
			}
		}
		jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
		return jsonResult.toString();
	}
}
