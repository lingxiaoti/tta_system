package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.dao.readonly.MsgHistoryDAO_HI_RO;
import com.sie.saaf.message.model.entities.MsgHistoryEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgHistoryEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgHistory;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component("msgHistoryServer")
public class MsgHistoryServer extends BaseCommonServer<MsgHistoryEntity_HI> implements IMsgHistory {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MsgHistoryServer.class);
	@Autowired
	private ViewObject<MsgHistoryEntity_HI> msgHistoryDAO_HI;

	@Autowired
	private MsgHistoryDAO_HI_RO msgHistoryDAO_HI_RO;
	public MsgHistoryServer() {
		super();
	}

	public List<MsgHistoryEntity_HI> findMsgHistoryInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<MsgHistoryEntity_HI> findListResult = msgHistoryDAO_HI.findList("from MsgHistoryEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveMsgHistoryInfo(JSONObject queryParamJSON) {
		MsgHistoryEntity_HI msgHistoryEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgHistoryEntity_HI.class);
		Object resultData = msgHistoryDAO_HI.save(msgHistoryEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<MsgHistoryEntity_HI_RO> findHistoy(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(MsgHistoryEntity_HI_RO.QUERY_SELECT_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("msgTransactionDateStart"))){
			sql.append(" and msg_history.msg_transaction_date >= to_date(:msgTransactionDateStart,'YYYY-MM-DD')");
			paramsMap.put("msgTransactionDateStart",queryParamJSON.getString("msgTransactionDateStart"));
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("msgTransactionDateEnd"))){
			sql.append(" and msg_history.msg_transaction_date <= to_date(:msgTransactionDateEnd,'YYYY-MM-DD hh24:mi:ss')");
			paramsMap.put("msgTransactionDateEnd",queryParamJSON.getString("msgTransactionDateEnd"));
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("sendDateStart"))){
			sql.append(" and msg_history.send_date >= to_date(:sendDateStart,'YYYY-MM-DD')");
			paramsMap.put("sendDateStart",queryParamJSON.getString("sendDateStart"));
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("sendDateEnd"))){
			sql.append(" and msg_history.send_date <= to_date(:sendDateEnd,'YYYY-MM-DD hh24:mi:ss')");
			paramsMap.put("sendDateEnd",queryParamJSON.getString("sendDateEnd"));
		}
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.channel_type", "channelType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.msg_type_code", "msgTypeCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.send_status", "sendStatus", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.org_id", "orgId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.receive_code", "receiveCode", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.source_type", "sourceType", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "msg_history.msg_subject", "msgSubject", sql, paramsMap, "fulllike");
		//SaafToolUtils.parperParam(queryParamJSON, "msg_history.msg_transaction_date", "msgTransactionDateStart", sql, paramsMap, ">=");
		//SaafToolUtils.parperParam(queryParamJSON, "msg_history.msg_transaction_date", "msgTransactionDateEnd", sql, paramsMap, "<=");
		//SaafToolUtils.parperParam(queryParamJSON, "msg_history.send_date", "sendDateStart", sql, paramsMap, ">=");
		//SaafToolUtils.parperParam(queryParamJSON, "msg_history.send_date", "sendDateEnd", sql, paramsMap, "<=");
		sql.append(" ORDER BY msg_history.msg_transaction_date DESC");
		Pagination<MsgHistoryEntity_HI_RO> findList = msgHistoryDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public String deleteMsgHistory(JSONObject queryParamJSON, int userId) {
		JSONObject jsonResult = new JSONObject();
		if (queryParamJSON.get("idDetails") == null ) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
			return jsonResult.toString();
		}
		JSONArray msgHistoryIdDetails = queryParamJSON.getJSONArray("idDetails");
		if(msgHistoryIdDetails!=null && !msgHistoryIdDetails.isEmpty()){
			for(int i=0;i<msgHistoryIdDetails.size();i++){
				JSONObject object = msgHistoryIdDetails.getJSONObject(i);
				String id=object.getString("id");
				MsgHistoryEntity_HI entity = msgHistoryDAO_HI.getById(Integer.valueOf(id));
				if (entity != null) {
					entity.setIsDelete(CommonConstants.DELETE_TRUE);
					entity.setOperatorUserId(userId);
					msgHistoryDAO_HI.update(entity);
				}
			}
		}
		jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
		return jsonResult.toString();
	}
}
