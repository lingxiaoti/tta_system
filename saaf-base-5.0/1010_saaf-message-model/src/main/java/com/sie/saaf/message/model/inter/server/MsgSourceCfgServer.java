package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.dao.readonly.MsgSourceCfgDAO_HI_RO;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgSourceCfgEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgSourceCfg;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component("msgSourceCfgServer")
public class MsgSourceCfgServer extends BaseCommonServer<MsgSourceCfgEntity_HI> implements IMsgSourceCfg {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MsgSourceCfgServer.class);
	@Autowired
	private ViewObject<MsgSourceCfgEntity_HI> msgSourceCfgDAO_HI;
	@Autowired
	private MsgSourceCfgDAO_HI_RO msgSourceCfgDAO_HI_RO;
	public MsgSourceCfgServer() {
		super();
	}

	public List<MsgSourceCfgEntity_HI> findMsgSourceCfgInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<MsgSourceCfgEntity_HI> findListResult = msgSourceCfgDAO_HI.findList("from MsgSourceCfgEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveMsgSourceCfgInfo(JSONObject queryParamJSON) {
		MsgSourceCfgEntity_HI msgSourceCfgEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgSourceCfgEntity_HI.class);
		Object resultData = msgSourceCfgDAO_HI.save(msgSourceCfgEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<MsgSourceCfgEntity_HI_RO> findSourceConfigInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(MsgSourceCfgEntity_HI_RO.QUERY_SELECT_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "msc.org_id", "orgId", sql, paramsMap, "=");
		SaafToolUtils.parperHbmParam(MsgSourceCfgEntity_HI_RO.class,queryParamJSON, "msc.source_name", "sourceName", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "msc.msg_type_code", "msgTypeCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "msc.source_user", "sourceUser", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "msc.enabled_flag", "enabledFlag", sql, paramsMap, "=");

		sql.append(" order by msc.CREATION_DATE desc ");
		Pagination<MsgSourceCfgEntity_HI_RO> findList = msgSourceCfgDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);


		return findList;
	}
	@Override
	public String deleteMsgCfgSource(JSONObject queryParamJSON, int userId) {
		JSONObject jsonResult = new JSONObject();
		if (queryParamJSON.get("idDetails") == null ) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
			return jsonResult.toString();
		}
		JSONArray msgSourceCfgIdDetails = queryParamJSON.getJSONArray("idDetails");
		if(msgSourceCfgIdDetails!=null && !msgSourceCfgIdDetails.isEmpty()){
			for(int i=0;i<msgSourceCfgIdDetails.size();i++){
				JSONObject object = msgSourceCfgIdDetails.getJSONObject(i);
				String id=object.getString("id");
				MsgSourceCfgEntity_HI entity = msgSourceCfgDAO_HI.getById(Integer.valueOf(id));
				if (entity != null) {
					entity.setIsDelete(CommonConstants.DELETE_TRUE);
					entity.setOperatorUserId(userId);
					msgSourceCfgDAO_HI.update(entity);
				}
			}
		}
		jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
		return jsonResult.toString();
	}

	@Override
	public Object saveOrUpdateMsgSource(JSONObject queryParamJSON) {
		JSONObject result=new JSONObject();
		//判断消息源名称名是否存在,如果存在不允许新增
		StringBuffer sql = new StringBuffer();
		sql.append(MsgSourceCfgEntity_HI_RO.QUERY_TEMPLENAME_EXIST_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "msc.source_name", "sourceName", sql, paramsMap, "=");

		String msgSourceId = queryParamJSON.getString("msgSourceId");
		if (!StringUtils.isEmpty(msgSourceId)) {
			SaafToolUtils.parperParam(queryParamJSON, "msc.msg_source_id", "msgSourceId", sql, paramsMap, "!=");
		}

		List<MsgSourceCfgEntity_HI_RO> findList = msgSourceCfgDAO_HI_RO.findList(sql, paramsMap);
		if (findList != null && findList.size() > 0) {
			throw new IllegalArgumentException("源名称重复!请修改!");
		}

		if (StringUtils.isEmpty(msgSourceId)){
			if (queryParamJSON.getString("isDelete") == null) {
				queryParamJSON.put("isDelete", "0");
			}
		}
		MsgSourceCfgEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), MsgSourceCfgEntity_HI.class);
		msgSourceCfgDAO_HI.saveOrUpdate(entity);
		result.put("title",  "消息源编号-" +entity.getMsgSourceId());
		return result;
	}

}
