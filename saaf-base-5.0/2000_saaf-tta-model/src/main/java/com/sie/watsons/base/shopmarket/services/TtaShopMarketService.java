package com.sie.watsons.base.shopmarket.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.shopmarket.model.entities.readonly.TtaShopMarketEntity_HI_RO;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.shopmarket.model.inter.server.TtaShopMarketServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaShopMarketService")
public class TtaShopMarketService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketService.class);

	@Autowired
	private TtaShopMarketServer ttaShopMarketServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return null ;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
													@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
													@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<TtaShopMarketEntity_HI_RO> baseJobPagination = ttaShopMarketServer.findShopPagination(queryParamJSON, pageIndex, pageRows);
			JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(baseJobPagination));
			jsonResult.put("status", SUCCESS_STATUS);
			return JSON.toJSONString(jsonResult);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			String ExMsg = e.getMessage();
			if(e instanceof UndeclaredThrowableException && ExMsg ==null ){
				ExMsg =((UndeclaredThrowableException)e).getUndeclaredThrowable().getMessage();
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ExMsg, 0, null).toString();
		}
	}

}