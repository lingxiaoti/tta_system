package com.sie.watsons.base.ttasastdtpl.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaTplTabLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaTplTabLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSaTplTabLineServer")
public class TtaSaTplTabLineServer extends BaseCommonServer<TtaSaTplTabLineEntity_HI> implements ITtaSaTplTabLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTplTabLineServer.class);

	@Autowired
	private ViewObject<TtaSaTplTabLineEntity_HI> ttaSaTplTabLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSaTplTabLineEntity_HI_RO> ttaSaTplTabLineDAO_HI_RO;

	public TtaSaTplTabLineServer() {
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
	public List<TtaSaTplTabLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception {
		ArrayList<TtaSaTplTabLineEntity_HI> objects = new ArrayList<>();
		JSONArray jsonList = queryParamJSON.getJSONArray("list");
		Integer id = queryParamJSON.getInteger("id");
		JSONObject paramJSON = new JSONObject();
		paramJSON.put("saStdTplDefHeaderId",id);
		List<TtaSaTplTabLineEntity_HI> ttaSaTplTabLineSList = findTtaSaTplTabLineSList(paramJSON);
		if (id != null) {
			for(int i = 0 ; i<jsonList.size(); i++){
				JSONArray jsonArray = jsonList.getJSONArray(i);
				for (int j = 0; j < jsonArray.size(); j++ ) {
					JSONObject  json = (JSONObject)jsonArray.get(j) ;
					TtaSaTplTabLineEntity_HI instance = SaafToolUtils.setEntity(TtaSaTplTabLineEntity_HI.class, json, ttaSaTplTabLineDAO_HI, userId);
					instance.setSaStdTplDefHeaderId(id);
					objects.add(instance);

					if ( null != instance.getSaTplTabLineId()) {
						List<TtaSaTplTabLineEntity_HI> returnList = ttaSaTplTabLineSList.stream().filter(en -> instance.getSaTplTabLineId().intValue() == en.getSaTplTabLineId().intValue()).collect(Collectors.toList());
						if ( returnList.size() > 0) {
							ttaSaTplTabLineSList.remove(returnList.get(0));
						}
					}
				}

			}
			ttaSaTplTabLineDAO_HI.delete(ttaSaTplTabLineSList);
			ttaSaTplTabLineDAO_HI.saveOrUpdateAll(objects);
		}

		return objects;
	}

	@Override
	public List<ArrayList<TtaSaTplTabLineEntity_HI_RO>> findTtaSaTplTabLineList(JSONObject queryParamJSON) {
		
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(TtaSaTplTabLineEntity_HI_RO.QUERY_LIST);
		SaafToolUtils.parperParam(queryParamJSON, "tsttl.sa_std_tpl_def_header_id", "saStdTplDefHeaderId", querySql, queryParamMap, "=");
		querySql.append("  order by tsttl.y_point asc, tsttl.x_point asc");
		List<TtaSaTplTabLineEntity_HI_RO> list = ttaSaTplTabLineDAO_HI_RO.findList(querySql, queryParamMap);
		
		List<ArrayList<TtaSaTplTabLineEntity_HI_RO>>  returnList = new ArrayList<ArrayList<TtaSaTplTabLineEntity_HI_RO>>();
		int num = 0;
		ArrayList<TtaSaTplTabLineEntity_HI_RO> cur = new ArrayList<TtaSaTplTabLineEntity_HI_RO>();;
		for (TtaSaTplTabLineEntity_HI_RO tttl : list) {
			if (tttl.getYPoint().intValue() != num) {
				num = tttl.getYPoint().intValue();
				cur = new ArrayList<TtaSaTplTabLineEntity_HI_RO>();
				returnList.add(cur);
			}
			cur.add(tttl);
		}
		
		return returnList ;
	}

	@Override
	public List<TtaSaTplTabLineEntity_HI> findTtaSaTplTabLineSList(JSONObject queryParamJSON) {
		List<TtaSaTplTabLineEntity_HI> byProperty = ttaSaTplTabLineDAO_HI.findByProperty("saStdTplDefHeaderId", queryParamJSON.getInteger("saStdTplDefHeaderId"));
		return byProperty ;
	}

}
