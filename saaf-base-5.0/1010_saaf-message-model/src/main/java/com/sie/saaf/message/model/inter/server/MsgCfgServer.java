package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.entities.MsgCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgCfgEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgCfg;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("msgCfgServer")
public class MsgCfgServer  extends BaseCommonServer<MsgCfgEntity_HI> implements IMsgCfg {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MsgCfgServer.class);

	@Autowired
	private BaseViewObject<MsgCfgEntity_HI_RO> msgCfgDAO_HI_RO;
	@Autowired
	private ViewObject<MsgCfgEntity_HI> msgCfgDAO_HI;
	public MsgCfgServer() {
		super();
	}

	public List<MsgCfgEntity_HI> findMsgCfgInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<MsgCfgEntity_HI> findListResult = msgCfgDAO_HI.findList("from MsgCfgEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveMsgCfgInfo(JSONObject queryParamJSON) {
		MsgCfgEntity_HI msgCfgEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgCfgEntity_HI.class);
		Object resultData = msgCfgDAO_HI.save(msgCfgEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<MsgCfgEntity_HI_RO> findSourceConfigInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(MsgCfgEntity_HI_RO.QUERY_SELECT_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "mc.org_id", "orgId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.msg_cfg_id", "msgCfgId", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "mc.msg_cfg_name", "msgCfgName", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "mc.msg_type_code", "msgTypeCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.channel_type", "channelType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.msg_source_id", "msgSourceId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.temple_id", "templeId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.enabled_flag", "enableFlag", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.blacklist_flag", "blacklistFlag", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "mc.compensate_flag", "compensateFlag", sql, paramsMap, "=");
		sql.append(" ORDER BY MC.CREATION_DATE DESC");
		Pagination<MsgCfgEntity_HI_RO> findList = msgCfgDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);


		/*//查询数据字典
		JSONArray msgTypeName = CommonServiceUtil.findDictList("MESSAGE_TYPE", "BASE");

		JSONArray enabledFlagName = CommonServiceUtil.findDictList("MESSAGE_FLAG", "BASE");

		JSONArray blacklistFlagName = CommonServiceUtil.findDictList("MESSAGE_BLACKLIST_FLAG", "BASE");

		JSONArray orgName = CommonServiceUtil.findDictList("BASE_OU", "BASE");

		List<MsgCfgEntity_HI_RO> paginatList = findList.getData();

		Iterator<MsgCfgEntity_HI_RO> paginatListiterator = paginatList.iterator();

		while (paginatListiterator.hasNext()) {

			MsgCfgEntity_HI_RO entity = paginatListiterator.next();

			//数据字典配对插入
			dataDictionary(entity, msgTypeName);
			dataDictionaryEnableFlag(entity, enabledFlagName);
			dataDictionaryBlackListFlag(entity,blacklistFlagName);
			dataDictionaryOrgName(entity,orgName);
		}*/

		return findList;
	}

	@Override
	public String deleteMsgCfg(JSONObject queryParamJSON, int userId) {
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
				MsgCfgEntity_HI entity = msgCfgDAO_HI.getById(Integer.valueOf(id));
				if (entity != null) {
					entity.setIsDelete(CommonConstants.DELETE_TRUE);
					entity.setOperatorUserId(userId);
					msgCfgDAO_HI.update(entity);
				}

			}
		}
		jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
		return jsonResult.toString();
	}
}
