package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.entities.MsgReceiveSqlEntity_HI;
import com.sie.saaf.message.model.inter.IMsgReceiveSql;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("msgReceiveSqlServer")
public class MsgReceiveSqlServer extends BaseCommonServer<MsgReceiveSqlEntity_HI> implements IMsgReceiveSql {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MsgReceiveSqlServer.class);
	@Autowired
	private ViewObject<MsgReceiveSqlEntity_HI> msgReceiveSqlDAO_HI;
	public MsgReceiveSqlServer() {
		super();
	}

	public List<MsgReceiveSqlEntity_HI> findMsgReceiveSqlInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<MsgReceiveSqlEntity_HI> findListResult = msgReceiveSqlDAO_HI.findList("from MsgReceiveSqlEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveMsgReceiveSqlInfo(JSONObject queryParamJSON) {
		MsgReceiveSqlEntity_HI msgReceiveSqlEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgReceiveSqlEntity_HI.class);
		Object resultData = msgReceiveSqlDAO_HI.save(msgReceiveSqlEntity_HI);
		return resultData;
	}

	@Override
	public Pagination<MsgReceiveSqlEntity_HI> findReceiveSqlInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append("from MsgReceiveSqlEntity_HI where is_delete = 0 ");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(MsgReceiveSqlEntity_HI.class, queryParamJSON, sql, paramsMap);
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "creationDate", false);
		Pagination<MsgReceiveSqlEntity_HI> findList = msgReceiveSqlDAO_HI.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public String deleteMsgReceiveSql(JSONObject queryParamJSON, int userId) {
		JSONObject jsonResult = new JSONObject();
		if (queryParamJSON.get("idDetails") == null ) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
			return jsonResult.toString();
		}
		JSONArray msgReceiveSqlIdDetails = queryParamJSON.getJSONArray("idDetails");
		if(msgReceiveSqlIdDetails!=null && !msgReceiveSqlIdDetails.isEmpty()){
			for(int i=0;i<msgReceiveSqlIdDetails.size();i++){
				JSONObject object = msgReceiveSqlIdDetails.getJSONObject(i);
				String id=object.getString("id");
				MsgReceiveSqlEntity_HI entity = msgReceiveSqlDAO_HI.getById(Integer.valueOf(id));
				if (entity != null) {
					entity.setIsDelete(CommonConstants.DELETE_TRUE);
					entity.setOperatorUserId(userId);
					msgReceiveSqlDAO_HI.update(entity);
				}
			}
		}
		jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
		return jsonResult.toString();
	}
}
