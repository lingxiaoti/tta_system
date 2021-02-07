package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.readonly.TtaMonthlyCheckingDAO_HI_RO;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaMonthlyCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaPromotionalLeafletEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaPromotionalLeafletEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaPromotionalLeaflet;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaPromotionalLeafletServer")
public class TtaPromotionalLeafletServer extends BaseCommonServer<TtaPromotionalLeafletEntity_HI> implements ITtaPromotionalLeaflet{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPromotionalLeafletServer.class);

	@Autowired
	private ViewObject<TtaPromotionalLeafletEntity_HI> ttaPromotionalLeafletDAO_HI;

	@Autowired
	private BaseViewObject<TtaPromotionalLeafletEntity_HI_RO> ttaPromotionalLeafletDAO_HI_RO;

	public TtaPromotionalLeafletServer() {
		super();
	}


	@Override
	public Pagination<TtaPromotionalLeafletEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaPromotionalLeafletEntity_HI_RO.QUERY_DW_REPORT_SELECT);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tplef.promotional_leaf_id desc", false);
		Pagination<TtaPromotionalLeafletEntity_HI_RO> findList = ttaPromotionalLeafletDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		for(int i=0;i<findList.getData().size();i++){
			TtaPromotionalLeafletEntity_HI_RO entity_hi_ro = findList.getData().get(i);
			if(entity_hi_ro.getMeaning()==null){
				continue;
			}
			String sum = (Integer.parseInt(findList.getData().get(i).getStandardValue1()) * findList.getData().get(i).getDmPage()) + "";
			findList.getData().get(i).setReceiAmount(sum);

		}

		return findList;
	}

	@Override
	public TtaPromotionalLeafletEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaPromotionalLeafletEntity_HI entry_hi = SaafToolUtils.setEntity(TtaPromotionalLeafletEntity_HI.class, paramsJSON, ttaPromotionalLeafletDAO_HI, userId);
		ttaPromotionalLeafletDAO_HI.saveOrUpdate(entry_hi);
		return entry_hi;
	}


}
