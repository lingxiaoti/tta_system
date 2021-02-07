package com.sie.saaf.demo.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.demo.model.inter.IDemo;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangJun
 * @createTime 2018-10-10 8:20 PM
 * @description
 */
@RestController
@RequestMapping("/demoService")
public class DemoService extends CommonAbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

	@Autowired
	private IDemo demoServer;

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return demoServer;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="transDemo")
	public String transDemo(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        this.demoServer.saveTransactionProduct();
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	    }catch(Exception e){
	        LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
}
