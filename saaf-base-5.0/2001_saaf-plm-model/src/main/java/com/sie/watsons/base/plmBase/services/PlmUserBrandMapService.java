package com.sie.watsons.base.plmBase.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmUserBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmUserBrandMap;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/plmUserBrandMapService")
public class PlmUserBrandMapService extends CommonAbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlmUserBrandMapService.class);
	@Autowired
	private IPlmUserBrandMap plmUserBrandMapServer;

	@Autowired
	private IBaseUsers baseUsersServer;

	@Resource
	private RestTemplate restTemplate;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmUserBrandMapServer;
	}

	public static final String BASE_SERVER = "http://1006-SAAF-BASE-SERVER";

	/**
	 * 获取供应商账号品牌权限列表
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@PostMapping(value = "findBaseUserBrandMapsPage")
	public String findBaseUserBrandMapsPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);

			Pagination<PlmUserBrandMapEntity_HI_RO> results = plmUserBrandMapServer.findBaseUsersPage(param, pageIndex,
					pageRows, getUserSessionBean().getCertificate());

			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, "查询成功");
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("getUserBrandMaps")
	public String getUserBrandMaps(@RequestParam(required = false) Integer brandMapId,
								   @RequestParam(required = false) Integer brandInfoId)
	{
		if(null != brandMapId && null != brandInfoId) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					"查询条件不能为空", 0, null).toString();
		}

		List<PlmUserBrandMapEntity_HI> userBrandMaps = plmUserBrandMapServer.getUserBrandMaps(brandMapId,brandInfoId);

		List<JSONObject> objList = new ArrayList<JSONObject>();
		if(CollectionUtils.isNotEmpty(userBrandMaps)) {
			for(PlmUserBrandMapEntity_HI ubm : userBrandMaps) {
				JSONObject object = new JSONObject();

				HttpHeaders headers = new HttpHeaders();
				headers.add("Certificate",getUserSessionBean().getCertificate());
//				BaseUsersEntity_HI baseUser = restTemplate.getForObject(BASE_SERVER+"/baseUsersService/getByUserId/"
//						+ubm.getSupUserId(),BaseUsersEntity_HI.class, headers);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

				ResponseEntity<BaseUsersEntity_HI> result = restTemplate.exchange(BASE_SERVER+"/baseUsersService/getByUserId/"
						+ubm.getSupUserId(), HttpMethod.GET, entity, BaseUsersEntity_HI.class);

				BaseUsersEntity_HI baseUser = result.getBody();

				object.put("userBrandMapId",ubm.getMapId());
				object.put("userId",ubm.getSupUserId());
				object.put("userName",null != baseUser?baseUser.getUserName():" ");
				object.put("phoneNumber",null != baseUser?baseUser.getPhoneNumber():" ");
				object.put("namePingyin",null != baseUser?baseUser.getNamePingyin():" ");
				object.put("userFullName",null != baseUser?baseUser.getUserFullName():" ");
				object.put("personId",null != baseUser?baseUser.getPersonId():" ");
				object.put("sourceId",null != baseUser?baseUser.getSourceId():" ");
				object.put("sourceCode",null != baseUser?baseUser.getSourceCode():" ");
				object.put("userType",null != baseUser?baseUser.getUserType():" ");
				object.put("emailAddress",null != baseUser?baseUser.getEmailAddress():" ");
				objList.add(object);
			}
		}

		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,"查询成功",
				Long.parseLong(userBrandMaps.size()+""),objList).toString();
	}

	/**
	 * 同步执行product到userBrandInfo
	 */
	@PostMapping("syncToUserBrandMaps")
	public void syncToUserBrandMaps(@RequestParam(required = false) String startDate,
									@RequestParam(required = false) String endDate) {

		Date sDate = null;
		Date eDate = null;
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sDate = DateUtil.parse(startDate,DateUtil.FORMAT_YMD);
			eDate = DateUtil.addDay(DateUtil.parse(endDate,DateUtil.FORMAT_YMD),1);
		}

		if(sDate == null) {
			sDate = DateUtil.addDay(DateUtil.parse(DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD),
					DateUtil.FORMAT_YMD), -1);
		}
		if(eDate == null) {
			eDate = DateUtil.parse(DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD),DateUtil.FORMAT_YMD);
		}

		while(sDate.getTime()<eDate.getTime()) {
			plmUserBrandMapServer.syncToUserBrandMaps(sDate,eDate);
			sDate = DateUtil.addDay(sDate, 1);
		}
	}

	/**
	 * 获取中英文品牌列表
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@PostMapping(value = "findDistinctSupplierBrandMap")
	public String findDistinctSupplierBrandMap(@RequestParam(required = false) String params,
											   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		UserSessionBean userSessionBean = getUserSessionBean();
		//queryParamJSON.put("vendorCodeList", userSessionBean.getVendorCodes());
		queryParamJSON.put("userId", userSessionBean.getUserId());
		queryParamJSON.put("userType", userSessionBean.getUserType());
		Pagination<PlmUserBrandMapEntity_HI_RO> dataList = plmUserBrandMapServer.findDistinctSupplierBrandMap(queryParamJSON,
				pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}
}
