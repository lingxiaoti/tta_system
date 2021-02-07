package com.sie.watsons.base.product.services;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmDataAclLineEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmDataAclLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/plmDataAclLineService")
public class PlmDataAclLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDataAclLineService.class);

	@Autowired
	private IPlmDataAclLine plmDataAclLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.plmDataAclLineServer;
	}




	/**
	 * 2020/07/02
	 *
	 * @Title:
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAclLineInfo")
	public String saveAclLineInfo(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			if(!param.containsKey("enableFlag"))
			{
				param.put("enableFlag","Y");
			}
            if(!param.containsKey("headId")){throw new Exception("缺少参数headId!");}
            if(!param.containsKey("aclType")){throw new Exception("缺少参数aclType!");}
            if(!param.containsKey("groupCode")){throw new Exception("缺少参数groupCode!");}

			PlmDataAclLineEntity_HI headinfo=plmDataAclLineServer.saveOrUpdate(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, headinfo).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 2020/07/09
	 *
	 * @Title:
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteAclLineById")
	public String deleteAclLineById(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			SaafToolUtils.validateJsonParms(param, "lineId");

			Integer lineId=param.getInteger("lineId");
			plmDataAclLineServer.deleteById(lineId);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}