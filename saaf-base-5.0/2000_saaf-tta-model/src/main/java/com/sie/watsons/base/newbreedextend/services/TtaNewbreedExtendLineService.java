package com.sie.watsons.base.newbreedextend.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendLineEntity_HI_RO;
import com.sie.watsons.base.newbreedextend.model.inter.ITtaNewbreedExtendLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaNewbreedExtendLineService")
public class TtaNewbreedExtendLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendLineService.class);

	@Autowired
	private ITtaNewbreedExtendLine ttaNewbreedExtendLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaNewbreedExtendLineServer;
	}

	/**
	 * 查询详情
	 *
	 * @param params
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findByLId")
	@Override
	public String findById(String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			List<TtaNewbreedExtendLineEntity_HI_RO> instance = ttaNewbreedExtendLineServer.findById(jsonObject,jsonObject.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}