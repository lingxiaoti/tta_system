package com.sie.watsons.base.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.inter.IPlmProductFile;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductFileService")
public class PlmProductFileService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductFileService.class);

	@Autowired
	private IPlmProductFile plmProductFileServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductFileServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteProductFileById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			plmProductFileServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	public static void main(String[] args) {
		JSONObject AllJSON = new JSONObject(); // 总json
		AllJSON.put("responsibilityId", "990021");
		AllJSON.put("respId", "990021");
		AllJSON.put("processDefinitionKey", "PLM_PRODUCTADD.-999");
		AllJSON.put("saveonly", "false");
		AllJSON.put("responsibilityId", "990021");
		AllJSON.put("orgId", "0");
		AllJSON.put("businessKey", "1");
		AllJSON.put("title", "商品新增");
		AllJSON.put("positionId", "0");
		AllJSON.put("billNo", "1");
		AllJSON.put("userType", "20");

		JSONArray tasks_assignees = new JSONArray();
		JSONArray variables = new JSONArray();
		JSONObject properties = new JSONObject();
		properties.put("menucode", "HTGL");
		properties.put("opinion", "");
		AllJSON.put("extend", tasks_assignees);
		AllJSON.put("variables", variables);
		AllJSON.put("properties", properties);

		System.out.println(AllJSON.toString());
	}

}