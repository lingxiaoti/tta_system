package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractRecordHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.contract.model.inter.ITtaContractRecordHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaContractRecordHeaderServer")
public class TtaContractRecordHeaderServer extends BaseCommonServer<TtaContractRecordHeaderEntity_HI> implements ITtaContractRecordHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractRecordHeaderServer.class);

	@Autowired
	private ViewObject<TtaContractRecordHeaderEntity_HI> ttaContractRecordHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaContractRecordHeaderEntity_HI_RO> ttaContractRecordHeaderDAO_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	public TtaContractRecordHeaderServer() {
		super();
	}

	/**
	 * 查询列表
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public Pagination<TtaContractRecordHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaContractRecordHeaderEntity_HI_RO.TTA_LIST_V);

		SaafToolUtils.parperParam(queryParamJSON, "TCR.year", "year", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "TCR.name", "name", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "TCR.sale_type_name", "saleTypeName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "TCR.BRAND", "brand", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "TCR.vendor_Nbr", "vendorNbr", sql, paramsMap, "like");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "TCR.creation_date desc", false);
		Pagination<TtaContractRecordHeaderEntity_HI_RO> findList = ttaContractRecordHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;

	}

	@Override
	public List<TtaContractRecordHeaderEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception {
		StringBuffer sql = new StringBuffer(TtaContractRecordHeaderEntity_HI_RO.TTA_LIST_V);
		sql.append(" and nvl(TCR.receive_status,'A') <> 'C' ");
		List<TtaContractRecordHeaderEntity_HI_RO> contractRecordHeaderList = ttaContractRecordHeaderDAO_HI_RO.findList(sql);
		ArrayList<TtaContractRecordHeaderEntity_HI> objects = new ArrayList<>();
		JSONArray save = paramsJSON.getJSONArray("save");
		for(int i = 0 ;i<save.size();i++){
			JSONObject  json = (JSONObject)save.get(i) ;
			Date date = new Date();
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			TtaContractRecordHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaContractRecordHeaderEntity_HI.class, json, ttaContractRecordHeaderDAO_HI, userId);
			if (null == instance.getTtaContractRecordId()) {
				String ttaContractRecordHeaderCode = codeService.getTtaContractRecordHeaderCode();
				instance.setLogNo(ttaContractRecordHeaderCode);
				instance.setReceiveStatus("A");//新增时设置领用状态为未使用

				//新增时,进行校验
				if (CollectionUtils.isNotEmpty(contractRecordHeaderList)){
					for (TtaContractRecordHeaderEntity_HI_RO entityHiRo : contractRecordHeaderList) {
						if (instance.getVendorNbr().equals(entityHiRo.getVendorNbr()) && instance.getYear().equals(entityHiRo.getYear())){
							StringBuffer msg = new StringBuffer();
							msg.append("系统中已存在同一年度【").append(entityHiRo.getYear()).append("】").
									append("同一个供应商【").append(entityHiRo.getVendorNbr()).append("-").append(entityHiRo.getVendorName()).append("】")
									.append("的业务合同书编号【").append(entityHiRo.getLogNo()).append("】");
							throw new IllegalArgumentException(msg.toString());
						}
					}
				}
			}
			if (null == instance.getYear() || (instance.getYear().intValue() > (ca.get(Calendar.YEAR) + 1))
					|| (instance.getYear().intValue() < (ca.get(Calendar.YEAR) - 1) )) {
				throw new IllegalArgumentException("合同年度必须为当前年度或者上一年度或者未来一年度");
			}
			objects.add(instance);
		}
		ttaContractRecordHeaderDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}

	@Override
	public void findContractVendor(JSONObject jsonObject, int userId) {
		Integer year = jsonObject.getInteger("year");
		String vendorNbr = jsonObject.getString("vendorNbr");
		StringBuffer sql = new StringBuffer(TtaContractRecordHeaderEntity_HI_RO.TTA_LIST_V);
		if (!SaafToolUtils.isNullOrEmpty(year)){
			sql.append(" and TCR.year = ").append(year);
		}
		if (!SaafToolUtils.isNullOrEmpty(vendorNbr)){
			sql.append(" and TCR.vendor_Nbr = '").append(vendorNbr).append("'");
		}
		sql.append(" and nvl(TCR.receive_status,'A') <> 'C' ");//作废
		List<TtaContractRecordHeaderEntity_HI_RO> list = ttaContractRecordHeaderDAO_HI_RO.findList(sql);
		if (list != null && list.size() > 0) {
			StringBuffer msg = new StringBuffer();
			msg.append("系统中已存在同一年度【").append(list.get(0).getYear()).append("】").
					append("同一个供应商【").append(list.get(0).getVendorNbr()).append("-").append(list.get(0).getVendorName()).append("】")
					.append("的业务合同书编号【").append(list.get(0).getLogNo()).append("】,请检查");
			throw new IllegalArgumentException(msg.toString());
		}
	}
}
