package com.sie.saaf.business.services;

import com.sie.saaf.business.model.entities.TtaPurchaseEntity_HI;
import com.sie.saaf.business.model.entities.TtaVendorInterEntity_HI;
import com.sie.saaf.business.model.inter.ITtaPurchase;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.inter.ITtaVendorInter;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaPurchaseService")
public class TtaPurchaseService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPurchaseService.class);

	@Autowired
	private ITtaPurchase ttaPurchaseServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaPurchaseServer;
	}

	@RequestMapping("/saveBatchPurchase")
	public String saveBatchPurchase(){
		try {
			ITtaPurchase iTtaPurchase = (ITtaPurchase) SpringBeanUtil.getBean("ttaPurchaseServer");
			iTtaPurchase.saveBatchPurchase(new ArrayList<TtaPurchaseEntity_HI>());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}