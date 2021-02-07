package com.sie.watsons.base.feedept.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptDEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptDEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.feedept.model.inter.ITtaFeeDeptD;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaFeeDeptDServer")
public class TtaFeeDeptDServer extends BaseCommonServer<TtaFeeDeptDEntity_HI> implements ITtaFeeDeptD{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptDServer.class);

	@Autowired
	private ViewObject<TtaFeeDeptDEntity_HI> ttaFeeDeptDDAO_HI;
	@Autowired
	private BaseViewObject<TtaFeeDeptDEntity_HI_RO> ttaFeeDeptDDAO_HI_RO;

	public TtaFeeDeptDServer() {
		super();
	}

	@Override
	public Pagination<TtaFeeDeptDEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaFeeDeptDEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.feedept_Line_Id", "feedeptLineId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.dept_Code", "deptCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.dept_Desc", "deptDesc", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.feedept_Detail_Id desc", false);
		Pagination<TtaFeeDeptDEntity_HI_RO> findList = ttaFeeDeptDDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaFeeDeptDEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		SaafToolUtils.validateJsonParms(paramsJSON,"feedeptLineId","deptCode","unit");
		Map hashMap = new HashMap<String,Object>();
		hashMap.put("feedeptLineId",paramsJSON.getInteger("feedeptLineId"));
		hashMap.put("deptCode",paramsJSON.getString("deptCode"));
		hashMap.put("unit",paramsJSON.getString("unit"));
		List<TtaFeeDeptDEntity_HI> byProperty = ttaFeeDeptDDAO_HI.findByProperty(hashMap);
		if(byProperty.size()>0){
			if(SaafToolUtils.isNullOrEmpty(paramsJSON.getInteger("feedeptDetailId"))){
				throw new IllegalArgumentException("部门,单位必须唯一 " );
			}else{
				if(!paramsJSON.getInteger("feedeptDetailId").equals(byProperty.get(0).getFeedeptDetailId())){
					throw new IllegalArgumentException("部门,单位必须唯一 " );
				}
			}

		}
		TtaFeeDeptDEntity_HI instance = SaafToolUtils.setEntity(TtaFeeDeptDEntity_HI.class, paramsJSON, ttaFeeDeptDDAO_HI, userId);


		ttaFeeDeptDDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaFeeDeptDEntity_HI instance = ttaFeeDeptDDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaFeeDeptDDAO_HI.delete(instance);
	}


	@Override
	public TtaFeeDeptDEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaFeeDeptDEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.feedept_Detail_Id", "feedeptDetailId", sql, paramsMap, "=");
		return (TtaFeeDeptDEntity_HI_RO)ttaFeeDeptDDAO_HI_RO.get(sql,paramsMap);
	}

}
