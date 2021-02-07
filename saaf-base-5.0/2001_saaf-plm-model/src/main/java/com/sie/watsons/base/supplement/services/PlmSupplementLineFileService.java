package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLineFile;
import com.yhg.base.utils.SToolUtils;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plmSupplementLineFileService")
public class  PlmSupplementLineFileService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmSupplementLineFileService.class);

	@Autowired
	private IPlmSupplementLineFile plmSupplementLineFileServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmSupplementLineFileServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteProductFileById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			plmSupplementLineFileServer.deleteFile(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveFile")
	public String saveFile(@RequestParam(required = false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			plmSupplementLineFileServer.saveFile(queryParamJSON);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	public static void main(String[] args) {
	}

}