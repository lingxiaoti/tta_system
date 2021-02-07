package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.PubUsersOrganizationEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IPubUsersOrganization;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xutongyuan
 * @creteTime 2018-10-26
 */
@RestController
@RequestMapping("/pubUsersOrganizationService")
public class PubUsersOrganizationService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(PubUsersOrganizationService.class);
	@Autowired
	private IPubUsersOrganization pubUsersOrganizationServer;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.pubUsersOrganizationServer;
	}



	@RequestMapping(method = RequestMethod.POST,value="findListAssign")
	public String findListAssign(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List<PubUsersOrganizationEntity_HI_RO> listAssign = this.pubUsersOrganizationServer.findListAssign(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", listAssign.size(), listAssign).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value="saveListAssign")
	public String saveListAssign(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON,"userId");
			this.pubUsersOrganizationServer.saveListAssign(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
}
