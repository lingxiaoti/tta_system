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
import com.sie.watsons.base.product.model.inter.IPlmProductQaFile;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductQaFileService")
public class PlmProductQaFileService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductQaFileService.class);

	@Autowired
	private IPlmProductQaFile plmProductQaFileServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductQaFileServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteProductQaFileById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			plmProductQaFileServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}