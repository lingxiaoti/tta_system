package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.entities.MsgTempleCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgTempleCfgEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgTempleCfg;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("msgTempleCfgServer")
public class MsgTempleCfgServer extends BaseCommonServer<MsgTempleCfgEntity_HI> implements IMsgTempleCfg {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MsgTempleCfgServer.class);
	@Autowired
	private ViewObject<MsgTempleCfgEntity_HI> msgTempleCfgDAO_HI;

	@Autowired
	private BaseViewObject<MsgTempleCfgEntity_HI_RO> msgTempleCfgDAO_HI_RO;
	public MsgTempleCfgServer() {
		super();
	}

	public List<MsgTempleCfgEntity_HI> findMsgTempleCfgInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<MsgTempleCfgEntity_HI> findListResult = msgTempleCfgDAO_HI.findList("from MsgTempleCfgEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveMsgTempleCfgInfo(JSONObject queryParamJSON) {
		MsgTempleCfgEntity_HI msgTempleCfgEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgTempleCfgEntity_HI.class);
		Object resultData = msgTempleCfgDAO_HI.save(msgTempleCfgEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<MsgTempleCfgEntity_HI_RO> findTempleConfigInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(MsgTempleCfgEntity_HI_RO.QUERY_SELECT_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "mtc.org_id", "orgId", sql, paramsMap, "=");
		SaafToolUtils.parperHbmParam(MsgTempleCfgEntity_HI_RO.class,queryParamJSON, "mtc.temple_name", "templeName", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "mtc.msg_type", "msgType", sql, paramsMap, "=");
		sql.append(" order by mtc.creation_date desc");
		Pagination<MsgTempleCfgEntity_HI_RO> findList = msgTempleCfgDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public String deleteMsgTempleCfg(JSONObject queryParamJSON, int userId) {
		JSONObject jsonResult = new JSONObject();
		if (queryParamJSON.get("idDetails") == null ) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
			return jsonResult.toString();
		}
		JSONArray idDetails = queryParamJSON.getJSONArray("idDetails");
		if(idDetails!=null && !idDetails.isEmpty()){
			for(int i=0;i<idDetails.size();i++){
				JSONObject object = idDetails.getJSONObject(i);
				String id=object.getString("id");
				MsgTempleCfgEntity_HI entity = msgTempleCfgDAO_HI.getById(Integer.valueOf(id));
				if (entity != null) {
					entity.setIsDelete(CommonConstants.DELETE_TRUE);
					entity.setOperatorUserId(userId);
					msgTempleCfgDAO_HI.update(entity);
				}
			}
		}
		jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
		return jsonResult.toString();
	}

	@Override
	public Object saveOrUpdateMsgTemple(JSONObject queryParamJSON) {
		JSONObject result=new JSONObject();
		//判断模板名是否存在,如果存在不允许新增
		StringBuffer sql = new StringBuffer();
		sql.append(MsgTempleCfgEntity_HI_RO.QUERY_TEMPLENAME_EXIST_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "mtc.temple_name", "templeName", sql, paramsMap, "=");

		String templeId = queryParamJSON.getString("templeId");
		if (!StringUtils.isEmpty(templeId)) {
			SaafToolUtils.parperParam(queryParamJSON, "mtc.temple_id", "templeId", sql, paramsMap, "!=");
		}

		List<MsgTempleCfgEntity_HI_RO> findList = msgTempleCfgDAO_HI_RO.findList(sql, paramsMap);
		if (findList != null && findList.size() > 0) {
			throw new IllegalArgumentException("模板名重复!请修改!");
		}

		if (StringUtils.isEmpty(templeId)){
			if (queryParamJSON.getString("isDelete") == null) {
				queryParamJSON.put("isDelete", "0");
			}
		}
		MsgTempleCfgEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), MsgTempleCfgEntity_HI.class);
		msgTempleCfgDAO_HI.saveOrUpdate(entity);
		result.put("title",  "模板编号-" +entity.getTempleId());
		return result;
	}

}
