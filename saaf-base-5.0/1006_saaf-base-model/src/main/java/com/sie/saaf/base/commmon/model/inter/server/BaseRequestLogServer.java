package com.sie.saaf.base.commmon.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.entities.BaseRequestLogEntity_HI;
import com.sie.saaf.base.commmon.model.entities.TtaSupplierEntity_RO;
import com.sie.saaf.base.commmon.model.inter.IBaseRequestLog;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("baseRequestLogServer")
public class BaseRequestLogServer implements IBaseRequestLog {
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(BaseRequestLogServer.class);

	public static final String TTA_SUPPLIER = "select supplier_id as supplierId,supplier_code as supplierCode,"
			+ " supplier_short_name  as supplierName,status,currency_code as currencyCode from tta_supplier  where status='Y' ";

	@Autowired
	private ViewObject<BaseRequestLogEntity_HI> baseRequestLogDAO_HI;

	@Autowired
	private BaseViewObject<TtaSupplierEntity_RO> ttaSupplierEntity_HI_RO;

	public BaseRequestLogServer() {
		super();
	}

	public List<BaseRequestLogEntity_HI> findBaseRequestLogInfo(
			JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils
				.fastJsonObj2Map(queryParamJSON);
		List<BaseRequestLogEntity_HI> findListResult = baseRequestLogDAO_HI
				.findList("from BaseRequestLogEntity_HI", queryParamMap);
		return findListResult;
	}

	public BaseRequestLogEntity_HI saveBaseRequestLogInfo(
			JSONObject queryParamJSON) {
		BaseRequestLogEntity_HI baseRequestLogEntity_HI = JSON.parseObject(
				queryParamJSON.toString(), BaseRequestLogEntity_HI.class);
		baseRequestLogEntity_HI.setOperatorUserId(-1);
		baseRequestLogDAO_HI.save(baseRequestLogEntity_HI);
		return baseRequestLogEntity_HI;
	}

	@Override
	public Pagination<TtaSupplierEntity_RO> findPlmSupplier(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(TTA_SUPPLIER);
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				TtaSupplierEntity_RO.class, queryParamJSON, sql, paramsMap);

		Pagination<TtaSupplierEntity_RO> pagination = ttaSupplierEntity_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
