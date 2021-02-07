package com.sie.watsons.base.feedept.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptLEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptLEntity_HI;
import com.sie.watsons.base.feedept.model.inter.ITtaFeeDeptL;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaFeeDeptLServer")
public class TtaFeeDeptLServer extends BaseCommonServer<TtaFeeDeptLEntity_HI> implements ITtaFeeDeptL {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptLServer.class);

	@Autowired
	private ViewObject<TtaFeeDeptLEntity_HI> ttaFeeDeptLDAO_HI;

	@Autowired
	private BaseViewObject<TtaFeeDeptLEntity_HI_RO> ttaFeeDeptLDAO_HI_RO;

	public TtaFeeDeptLServer() {
		super();
	}

	@Override
	public Pagination<TtaFeeDeptLEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaFeeDeptLEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.feedept_Id", "feedeptId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Nbr", "itemNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_Cn", "itemDescCn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_En", "itemDescEn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.if_Effect", "ifEffect", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.line_Code", "lineCode", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "FORSTR(v.line_code) asc", false);
		Pagination<TtaFeeDeptLEntity_HI_RO> findList = ttaFeeDeptLDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaFeeDeptLEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {




		TtaFeeDeptLEntity_HI instance = SaafToolUtils.setEntity(TtaFeeDeptLEntity_HI.class, paramsJSON, ttaFeeDeptLDAO_HI, userId);
		if(SaafToolUtils.isNullOrEmpty(instance.getFeedeptLineId())){
			Integer feedeptId = paramsJSON.getInteger("feedeptId");
			String lineCode = paramsJSON.getString("lineCode");
			Map<String,Object> map=new HashMap<>();
			map.put("feedeptId",feedeptId);
			map.put("lineCode",lineCode);
			List<TtaFeeDeptLEntity_HI> collectionList= ttaFeeDeptLDAO_HI.findByProperty(map);
			if (collectionList!=null&&collectionList.size()>0) {
				throw new IllegalArgumentException("编号重复!请修改!");
			};
		}


		ttaFeeDeptLDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaFeeDeptLEntity_HI instance = ttaFeeDeptLDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaFeeDeptLDAO_HI.delete(instance);
	}


	@Override
	public TtaFeeDeptLEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaFeeDeptLEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.feedept_Line_Id", "feedeptLineId", sql, paramsMap, "=");
		return (TtaFeeDeptLEntity_HI_RO)ttaFeeDeptLDAO_HI_RO.get(sql,paramsMap);
	}

}
