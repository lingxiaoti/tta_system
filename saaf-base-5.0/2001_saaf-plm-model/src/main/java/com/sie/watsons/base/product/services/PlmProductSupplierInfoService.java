package com.sie.watsons.base.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierInfoEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.product.model.inter.IPlmProductSupplierInfo;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductSupplierInfoService")
public class PlmProductSupplierInfoService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductSupplierInfoService.class);

	@Autowired
	private IPlmProductSupplierInfo plmProductSupplierInfoServer;

	@Autowired
	private IPlmProductHead plmProductHeadServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductSupplierInfoServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteProductSupplierById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			PlmProductSupplierInfoEntity_HI su = plmProductSupplierInfoServer
					.getById(id);
			Integer headid = su.getProductHeadId();
			String suppliercode = su.getSupplierCode();
			String delsql = "delete PlmProductQaFileEntity_HI where product_head_id="
					+ headid + " and supplier_id='" + suppliercode + "'";
			String delsqlpic = "delete PlmProductPicfileTableEntity_HI where product_head_id="
					+ headid + " and supplier_id='" + suppliercode + "'";
			plmProductHeadServer.execute(delsql);
			plmProductHeadServer.execute(delsqlpic);
			plmProductSupplierInfoServer.deleteById(id);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}