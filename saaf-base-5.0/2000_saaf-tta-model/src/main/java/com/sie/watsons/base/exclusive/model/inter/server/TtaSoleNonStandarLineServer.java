package com.sie.watsons.base.exclusive.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleNonStandarLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleNonStandarLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleNonStandarLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSoleNonStandarLineServer")
public class TtaSoleNonStandarLineServer extends BaseCommonServer<TtaSoleNonStandarLineEntity_HI> implements ITtaSoleNonStandarLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarLineServer.class);

	@Autowired
	private ViewObject<TtaSoleNonStandarLineEntity_HI> ttaSoleNonStandarLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSoleNonStandarLineEntity_HI_RO> ttaSoleNonStandarLineDAO_HI_RO;

	public TtaSoleNonStandarLineServer() {
		super();
	}

	/**
	 * 保存
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaSoleNonStandarLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception {
		ArrayList<TtaSoleNonStandarLineEntity_HI> objects = new ArrayList<>();
		JSONArray jsonList = queryParamJSON.getJSONArray("list");
		Integer id = queryParamJSON.getInteger("id");
		if (id != null) {
			for(int i = 0 ;i<jsonList.size();i++){
				JSONObject  json = (JSONObject)jsonList.get(i) ;
				SaafToolUtils.validateJsonParms(json,"proposalOrder","proposalVersion","vendorCode","proposalYear");
					StringBuffer countSql = new StringBuffer("select count(ts.soleNonStandarLineId) from TtaSoleNonStandarLineEntity_HI ts " +
							"where ts.proposalId = :proposalId and ts.soleNonStandarHeaderId =:soleNonStandarHeaderId");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("proposalId",json.getInteger("proposalId")) ;
					map.put("soleNonStandarHeaderId",id) ;
					Integer count = ttaSoleNonStandarLineDAO_HI.count(countSql, map);
					if(count.intValue() != 0){
						String tipMsg = "选择的Proposal合同号为" + json.getString("proposalOrder") + ",版本号"+json.getString("proposalVersion")+",在系统中已存在,不能再次添加";
						throw new IllegalArgumentException(tipMsg);
					}
				TtaSoleNonStandarLineEntity_HI instance = SaafToolUtils.setEntity(TtaSoleNonStandarLineEntity_HI.class, json, ttaSoleNonStandarLineDAO_HI, userId);
				instance.setSoleNonStandarHeaderId(id);
				objects.add(instance);
			}
			ttaSoleNonStandarLineDAO_HI.saveOrUpdateAll(objects);
		}

		return objects;
	}

	@Override
	public Pagination<TtaSoleNonStandarLineEntity_HI_RO> findTtaSoleNonStandarLinePagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSoleNonStandarLineEntity_HI_RO.TTA_LIST);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(TtaSoleNonStandarLineEntity_HI_RO.class,
				queryParamJSON, "tsnsl.sole_non_standar_header_id", "soleNonStandarHeaderId", sql,
				paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tsnsl.sole_non_standar_line_id desc",
				false);
		return ttaSoleNonStandarLineDAO_HI_RO.findPagination(sql,
				SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex,
				pageRows);

	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaSoleNonStandarLineEntity_HI instance = ttaSoleNonStandarLineDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaSoleNonStandarLineDAO_HI.delete(instance);
	}

}
