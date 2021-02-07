package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierAttrEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierAttrEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplier.model.inter.ITtaSupplierAttr;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSupplierAttrServer")
public class TtaSupplierAttrServer extends BaseCommonServer<TtaSupplierAttrEntity_HI> implements ITtaSupplierAttr{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierAttrServer.class);

	@Autowired
	private BaseViewObject<BaseAttachmentEntity_HI_RO> baseAttachmentEntity_HI_RO;

	@Autowired
	private ViewObject<TtaSupplierAttrEntity_HI> ttaSupplierAttrDAO_HI;

	public TtaSupplierAttrServer() {
		super();
	}

	@Override
	public Pagination<BaseAttachmentEntity_HI_RO> findBaseAttachmentEntity(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(TtaSupplierAttrEntity_HI_RO.queryAttr());
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(jsonObject, "ba.function_id", "functionId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "ba.business_id", "businessId", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(jsonObject,sql,"ba.creation_date desc",false);
		Pagination<BaseAttachmentEntity_HI_RO> ttaSupplier = baseAttachmentEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return ttaSupplier;
	}
}
