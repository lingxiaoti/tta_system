package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsOrderDetailEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsPolistEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsOrderDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaFreeGoodsOrderDetail;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

@Component("ttaFreeGoodsOrderDetailServer")
public class TtaFreeGoodsOrderDetailServer extends BaseCommonServer<TtaFreeGoodsOrderDetailEntity_HI> implements ITtaFreeGoodsOrderDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsOrderDetailServer.class);

	@Autowired
	private ViewObject<TtaFreeGoodsOrderDetailEntity_HI> ttaFreeGoodsOrderDetailDAO_HI;

	@Autowired
	private BaseViewObject<TtaFreeGoodsOrderDetailEntity_HI_RO> ttaFreeGoodsOrderDetailDAO_HI_RO;

	@Autowired
	private ViewObject<TtaFreeGoodsPolistEntity_HI> ttaFreeGoodsPolistDAO_HI;

	public TtaFreeGoodsOrderDetailServer() {
		super();
	}

	@Override
	public Pagination<TtaFreeGoodsOrderDetailEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean userSessionBean) throws Exception {

		StringBuffer sql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		//非 BIC用户
		if (!("45".equals(userSessionBean.getUserType()))) {
			sql.append(TtaFreeGoodsOrderDetailEntity_HI_RO.NO_BIC_FREE_GOODS_POLIST);
			map.put("userId", userSessionBean.getUserId());
		} else {//BIC用户
			sql.append(TtaFreeGoodsOrderDetailEntity_HI_RO.FREE_GOODS_QUERY);
		}

		SaafToolUtils.parperParam(queryParamJSON, "tfgod.times_version", "timesVersion", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tfgod.is_calculate", "isCalculate", sql, map, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tfgod.charge_year", "chargeYear", sql, map, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tfgod.supplier_code", "supplierCode", sql, map, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tfgod.supplier_name", "supplierName", sql, map, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tfgod.order_no", "orderNo", sql, map, "like");

		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tfgod.id desc", false);
		Pagination<TtaFreeGoodsOrderDetailEntity_HI_RO> resultList =ttaFreeGoodsOrderDetailDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	@Override
	public TtaFreeGoodsOrderDetailEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaFreeGoodsOrderDetailEntity_HI instance = SaafToolUtils.setEntity(TtaFreeGoodsOrderDetailEntity_HI.class, paramsJSON, ttaFreeGoodsOrderDetailDAO_HI, userId);
		instance.setLastUpdatedBy(userId);
		instance.setLastUpdateDate(new Date());
		instance.setLastUpdateLogin(userId);
		ttaFreeGoodsOrderDetailDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public List<TtaFreeGoodsOrderDetailEntity_HI> saveOrUpdateAll(JSONObject jsonObject, int userId) throws Exception {
		JSONArray save = jsonObject.getJSONArray("save");
		if (save == null && save.isEmpty()) {
			return new ArrayList<TtaFreeGoodsOrderDetailEntity_HI>();
		}
		List<TtaFreeGoodsOrderDetailEntity_HI> list = save.toJavaList(TtaFreeGoodsOrderDetailEntity_HI.class);
		for (TtaFreeGoodsOrderDetailEntity_HI hi : list) {
			hi.setLastUpdatedBy(userId);
			hi.setLastUpdateDate(new Date());
			hi.setLastUpdateLogin(userId);
		}
		ttaFreeGoodsOrderDetailDAO_HI.save(list);
		return list;
	}

	@Override
	public void batchJoinCount(JSONObject paramJSON, int userId) throws Exception {
		JSONArray selectRowData = paramJSON.getJSONArray("selectRowData");
		JSONObject requestParams = paramJSON.getJSONObject("requestParams");
		Assert.notNull(requestParams.getString("isCalculate"),"请选择是否要加入计算");
		List<TtaFreeGoodsOrderDetailEntity_HI> list = new ArrayList<>();
		for (int i = 0; i < selectRowData.size(); i++) {
			JSONObject  jsonObject = selectRowData.getJSONObject(i);
			TtaFreeGoodsOrderDetailEntity_HI entity_hi = SaafToolUtils.setEntity(TtaFreeGoodsOrderDetailEntity_HI.class, jsonObject, ttaFreeGoodsOrderDetailDAO_HI, userId);
			entity_hi.setIsCalculate(requestParams.getString("isCalculate"));
			list.add(entity_hi);
		}
		ttaFreeGoodsOrderDetailDAO_HI.saveOrUpdateAll(list);
	}

	@Override
	public void batchJoinFeeYear(JSONObject paramJSON, int userId) throws Exception {
		JSONArray selectRowData = paramJSON.getJSONArray("selectRowData");
		JSONObject requestParams = paramJSON.getJSONObject("requestParams");
		Assert.notNull(requestParams.getInteger("chargeYear"),"请先选择费用年度");
		List<TtaFreeGoodsOrderDetailEntity_HI> list = new ArrayList<>();
		for (int i = 0; i < selectRowData.size(); i++) {
			JSONObject  jsonObject = selectRowData.getJSONObject(i);
			TtaFreeGoodsOrderDetailEntity_HI entity_hi = SaafToolUtils.setEntity(TtaFreeGoodsOrderDetailEntity_HI.class, jsonObject, ttaFreeGoodsOrderDetailDAO_HI, userId);
			entity_hi.setChargeYear(requestParams.getInteger("chargeYear"));
			list.add(entity_hi);
		}
		ttaFreeGoodsOrderDetailDAO_HI.saveOrUpdateAll(list);
	}

	@Override
	public void batchDelete(JSONObject jsonObject, int userId) {
		JSONArray ids = jsonObject.getJSONArray("ids");
		for (int i = 0; i < ids.size(); i++) {
			Integer id = ids.getInteger(i);
			TtaFreeGoodsOrderDetailEntity_HI entityHi = ttaFreeGoodsOrderDetailDAO_HI.getById(id);
			String timesVersion = entityHi.getTimesVersion();
			String orderNo = entityHi.getOrderNo();
			String item = entityHi.getItem();
			ttaFreeGoodsOrderDetailDAO_HI.delete(id);
			ttaFreeGoodsPolistDAO_HI.executeSqlUpdate("delete from TTA_FREE_GOODS_POLIST t where t.export_batch = '" + timesVersion + "' and t.order_no = '" + orderNo + "' and t.item = '" + item + "'");
		}
	}
}
