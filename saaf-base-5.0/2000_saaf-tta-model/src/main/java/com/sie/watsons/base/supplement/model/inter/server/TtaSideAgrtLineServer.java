package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtHeader;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtLine;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component("ttaSideAgrtLineServer")
public class TtaSideAgrtLineServer extends BaseCommonServer<TtaSideAgrtLineEntity_HI> implements ITtaSideAgrtLine {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtLineServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaSideAgrtLineEntity_HI> ttaSideAgrtLineDAO_HI;

	@Autowired
    private BaseViewObject<TtaSideAgrtLineEntity_HI_RO> ttaSideAgrtLineDAO_HI_RO;

    @Autowired
	private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierEntity_HI_RO;

	public TtaSideAgrtLineServer() {
		super();
	}


	/**
	 * 保存proposal供应商的数据
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<TtaSideAgrtLineEntity_HI> saveProposalSupplier(JSONObject queryParamJSON) throws Exception{
		Assert.notNull(queryParamJSON,"参数不能为空");
		LOGGER.info("参数: {}",queryParamJSON.toString());

		Integer sideAgrtHId = queryParamJSON.getInteger("sideAgrtHId");

		if (sideAgrtHId == null){
			throw new IllegalArgumentException("单据未保存,请先保存,再保存proposal供应商数据");
		}

		if (StringUtils.isBlank(queryParamJSON.getString("sideAgrtVersion"))){
			throw new IllegalArgumentException("请先保存补充协议头信息的版本号");
		}
		String sideAgrtVersion = queryParamJSON.getString("sideAgrtVersion");
		LOGGER.info("参数 sideAgrtHId:{},参数 sideAgrtVersion:{}",sideAgrtHId.toString(),sideAgrtVersion);

		JSONArray proposalSupplierList = queryParamJSON.getJSONArray("proposalSupplierList");

		List<TtaSideAgrtLineEntity_HI> ttaSideAgrtLineEntity_his = new ArrayList<>();
		if (null != proposalSupplierList && proposalSupplierList.size() > 0){
			for (Object supplierObj: proposalSupplierList) {
				JSONObject jsonObject = (JSONObject)supplierObj;
				TtaSideAgrtLineEntity_HI entity_hi = new TtaSideAgrtLineEntity_HI();
				entity_hi.setProposalContractCode(jsonObject.getString("poposalCode"));
				entity_hi.setVendorCode(jsonObject.getString("supplierCode"));
				entity_hi.setVendorName(jsonObject.getString("supplierName"));
				entity_hi.setSideAgrtHId(sideAgrtHId);
				entity_hi.setSideAgrtVersion(sideAgrtVersion);
				entity_hi.setCreationDate(new Date());
				entity_hi.setLastUpdateDate(new Date());
				entity_hi.setLastUpdateLogin(queryParamJSON.getInteger("varUserId"));
				entity_hi.setCreatedBy(queryParamJSON.getInteger("varUserId"));
				entity_hi.setLastUpdatedBy(queryParamJSON.getInteger("varUserId"));
				ttaSideAgrtLineEntity_his.add(entity_hi);

			}
		}
		ttaSideAgrtLineDAO_HI.save(ttaSideAgrtLineEntity_his);
		return ttaSideAgrtLineEntity_his;
	}

	@Override
	public TtaSideAgrtLineEntity_HI deleteSupplierBySideAgrtLineId(Integer id) throws Exception {
		LOGGER.info("参数的id: {}",id);
		TtaSideAgrtLineEntity_HI entity = ttaSideAgrtLineDAO_HI.getById(id);
		if (entity == null) {
			throw new IllegalArgumentException("选择的数据不存在,不能删除");
		}
		ttaSideAgrtLineDAO_HI.delete(entity);
		return entity;
	}


	/**
	 * 查询 proposal补充协议行表信息
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@Override
	public Pagination<TtaSideAgrtLineEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(" from TtaSideAgrtLineEntity_HI as ttaSideAgrtL where 1=1 ");
		SaafToolUtils.parperParam(queryParamJSON, "ttaSideAgrtL.SIDE_AGRT_H_ID", "sideAgrtHId", sb, queryParamMap, "=",true);
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "ttaSideAgrtL.SIDE_AGRT_L_ID asc", true);
		Pagination<TtaSideAgrtLineEntity_HI> findListResult = ttaSideAgrtLineDAO_HI.findPagination(sb, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}
}
