package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplement.model.inter.ITtaSaNonStandarLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSaNonStandarLineServer")
public class TtaSaNonStandarLineServer extends BaseCommonServer<TtaSaNonStandarLineEntity_HI> implements ITtaSaNonStandarLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaNonStandarLineServer.class);

	@Autowired
	private ViewObject<TtaSaNonStandarLineEntity_HI> ttaSaNonStandarLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSaNonStandarLineEntity_HI_RO> ttaSaNonStandarLineDAO_HI_RO;

	public TtaSaNonStandarLineServer() {
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
	public List<TtaSaNonStandarLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception {
		ArrayList<TtaSaNonStandarLineEntity_HI> objects = new ArrayList<>();
		JSONArray jsonList = queryParamJSON.getJSONArray("list");
		Integer id = queryParamJSON.getInteger("id");
		if (id != null) {
			for(int i = 0 ;i<jsonList.size();i++){
				JSONObject  json = (JSONObject)jsonList.get(i) ;
				SaafToolUtils.validateJsonParms(json,"proposalOrder","proposalVersion","vendorCode","proposalYear");
				StringBuffer countSql = new StringBuffer("select count(ts.saNonStandarLineId) from TtaSaNonStandarLineEntity_HI ts " +
						"where ts.proposalId = :proposalId and ts.saNonStandarHeaderId = :saNonStandarHeaderId");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("proposalId",json.getInteger("proposalId")) ;
				map.put("saNonStandarHeaderId",id) ;
				Integer count = ttaSaNonStandarLineDAO_HI.count(countSql, map);
				if(count.intValue() != 0){
					String tipMsg = "选择的Proposal合同号为" + json.getString("proposalOrder") + ",版本号"+json.getString("proposalVersion")+",在系统中已存在,不能再次添加";
					throw new IllegalArgumentException(tipMsg);
				}
				TtaSaNonStandarLineEntity_HI instance = SaafToolUtils.setEntity(TtaSaNonStandarLineEntity_HI.class, json, ttaSaNonStandarLineDAO_HI, userId);
				instance.setSaNonStandarHeaderId(id);
				objects.add(instance);
			}
			ttaSaNonStandarLineDAO_HI.saveOrUpdateAll(objects);
		}

		return objects;
	}

	@Override
	public Pagination<TtaSaNonStandarLineEntity_HI_RO> findTtaSaNonStandarLinePagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSaNonStandarLineEntity_HI_RO.TTA_LIST);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(TtaSaNonStandarLineEntity_HI_RO.class,
				queryParamJSON, "tssl.sa_non_standar_header_id", "saNonStandarHeaderId", sql,
				paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tssl.sa_non_standar_line_id desc",
				false);
		return ttaSaNonStandarLineDAO_HI_RO.findPagination(sql,
				SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex,
				pageRows);

	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaSaNonStandarLineEntity_HI instance = ttaSaNonStandarLineDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaSaNonStandarLineDAO_HI.delete(instance);
	}

}
