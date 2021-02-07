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
import com.sie.watsons.base.product.model.inter.IPlmProductDrugfile;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductDrugfileService")
public class PlmProductDrugfileService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductDrugfileService.class);

	@Autowired
	private IPlmProductDrugfile plmProductDrugfileServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductDrugfileServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteDrugfileById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			plmProductDrugfileServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	public static void main(String[] args) {
		String json = "{\"oblist\":[{\"supplierId\":51068,\"lastUpdateDate\":\"2019-09-26 16:03:07\",\"versionNum\":4,\"productCategoryName\":\"进口化妆品\",\"projectCreator\":\"超级管理员\",\"supplierCode\":\"P201909240001\",\"plmDevelopmentInfoId\":142,\"productName\":\"杨树林\",\"productCategory\":\"IMPORTED_COSMETICS\",\"specialApprovalFlag\":\"N\",\"developmentBillStatusName\":\"全部开发完成\",\"barcode\":\"2019001\",\"supplierName\":\"A(purchase,payment>60Dwi)\",\"plmProjectProductDetailId\":148,\"projectRange\":\"商品测试\",\"productCreator\":\"超级管理员\",\"productStatus\":\"TO_BE_INTRODUCED\",\"creationDate\":\"2019-09-26 15:43:13\",\"productStatusName\":\"待引入\",\"createdBy\":1,\"developmentBillStatus\":\"COMPLETED\",\"plmProjectId\":62,\"producer\":\"A(purchase,payment>60Dwi)\","
				+ "\"projectName\":\"商品测试\",\"supplierType\":\"PRODUCER\",\"obId\":\"OB1900056\",\"checked\":true}]}";
	}

}