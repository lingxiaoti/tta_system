package com.sie.wastons.ttadata.model.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.wastons.ttadata.model.entities.readyonly.UserInfoEntity_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.VendorInfoEntity_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.VmiPersonInfoEntity_HI_RO;
import com.sie.wastons.ttadata.model.inter.ITtaUser;
import com.sie.wastons.ttadata.model.inter.server.TtaDataServer;
import com.sie.wastons.view.ApiRequest;
import com.sie.wastons.view.ApiResponse;
import com.yhg.hibernate.core.paging.Pagination;

@Api("基础数据查询")
@Slf4j
@RestController
@RequestMapping("/ttaBaseDataService/v2")
public class TtaBaseDataService {

	@Autowired
	private TtaDataServer ttaDataServer;

	@Autowired
	private ITtaUser ttaUserServer;

	/**
	 * 供应商信息
	 *
	 * @creteTime 2019年9月10日 15:16:34
	 */
	@ApiOperation(value = "供应商信息", notes = "供应商信息")
	@RequestMapping(method = RequestMethod.POST, value = "findVendor")
	public ApiResponse<List<VendorInfoEntity_RO>> findVendor(
			@RequestBody ApiRequest<VendorInfoEntity_RO> params)
			throws IllegalAccessException {
		Pagination<VendorInfoEntity_RO> result = ttaDataServer
				.findVendorInfo(params.getParams(), params.getPageIndex(),
						params.getPageRows());
		return ApiResponse.SUCCESS().fluentSetPageData(result);
	}

	/**
	 * 供应商分组信息
	 *
	 * @param params
	 * @return
	 * @throws IllegalAccessException
	 */

	@ApiOperation(value = "供应商分组信息", notes = "供应商分组信息")
	@RequestMapping(method = RequestMethod.POST, value = "findVendorGroup")
	public ApiResponse<List<VendorInfoEntity_RO>> findVendorGroup(
			@RequestBody ApiRequest<VendorInfoEntity_RO> params)
			throws IllegalAccessException {
		Pagination<VendorInfoEntity_RO> result = ttaDataServer
				.findVendorInfo(params.getParams(), params.getPageIndex(),
						params.getPageRows());
		return ApiResponse.SUCCESS().fluentSetPageData(result);
	}

	@ApiOperation(value = "供应商分名称", notes = "供应商分名称")
	@RequestMapping(method = RequestMethod.POST, value = "getVendorName")
	public String getVendorName(String vendorCode) {
		return ttaDataServer.getVendorName(vendorCode);
	}

	@ApiOperation(value = "供应商编码", notes = "供应商编码")
	@RequestMapping(method = RequestMethod.POST, value = "getVendorCode")
	public List<String> getVendorCode(String vendorName) {
		return ttaDataServer.getVendorCode(vendorName);
	}

	@ApiOperation(value = "供应商是否存在", notes = "供应商是否存在")
	@RequestMapping(method = RequestMethod.POST, value = "vendorIsExist")
	public boolean vendorIsExist(String vendorCode) {
		return ttaDataServer.vendorIsExist(vendorCode);
	}

	@ApiOperation(value = "快码信息", notes = "快码信息")
	@RequestMapping(method = RequestMethod.POST, value = "getLookUpValuesMap")
	public Map<String, String> getLookUpValuesMap(String lookupType) {
		return ttaDataServer.getLookUpValuesMap(lookupType, null);
	}

	@ApiOperation(value = "快码信息", notes = "快码信息")
	@RequestMapping(method = RequestMethod.POST, value = "getLookUpValuesMapPLM")
	public Map<String, String> getLookUpValuesMapPLM(String lookupType) {
		return ttaDataServer.getLookUpValuesMap(lookupType, "PLM");
	}

	@ApiOperation(value = "查询全部快码信息", notes = "查询全部快码信息")
	@RequestMapping(method = RequestMethod.POST, value = "getLookUpValuesForCategory")
	public JSONObject getLookUpValuesForCategory(String lookupType,
			String lookupCode) {
		return ttaDataServer.getLookUpValuesForCategory(lookupType, lookupCode);
	}

	@ApiOperation(value = "审批历史信息", notes = "审批历史信息")
	@RequestMapping(value = "getActivitiesHistoric")
	public JSONObject getActivitiesHistoricturn(String code) {
		return ttaDataServer.getActivitiesHistoric(code);
	}

	@ApiOperation(value = "联合部门信息", notes = "联合部门信息")
	@RequestMapping(value = "getUnionDepartmentInfo")
	public JSONObject getUnionDepartmentInfo(String params) {
		return ttaDataServer.getUnionDepartmentInfo(params);
	}

	@ApiOperation(value = "快码信息", notes = "快码信息")
	@RequestMapping(value = "getLookUpValuesMapOverturn")
	public Map<String, String> getLookUpValuesMapOverturn(String lookupType) {
		return ttaDataServer.getLookUpValuesMapOverturn(lookupType);
	}

	@ApiOperation(value = "用户信息", notes = "用户信息")
	@RequestMapping(value = "getUserInfo")
	public UserInfoEntity_RO getLookUpValuesMapOverturn(Integer userId) {
		return ttaUserServer.getUserInfo(userId);
	}

	@ApiOperation(value = "用户信息", notes = "用户信息")
	@RequestMapping(value = "getUserInfoByUsrName")
	public UserInfoEntity_RO getUserInfoByUsrName(String userName) {
		UserInfoEntity_RO a = ttaUserServer.getUserInfoByUsrName(userName);
		return a;
	}



	@ApiOperation(value = "通过用户类型查询用户信息", notes = "通过用户类型查询用户信息")
	@RequestMapping(method = RequestMethod.POST, value = "getUserInfoByType")
	public List<UserInfoEntity_RO> getUserInfoByUserType(String userType) {
		return ttaUserServer.getUserInfoByType(userType);
	}

	/**
	 * 查询人员信息
	 *
	 * @creteTime 2019年9月10日 15:16:34
	 */
	@ApiOperation(value = "人员信息查询", notes = "人员信息查询")
	@RequestMapping(method = RequestMethod.POST, value = "findPersonInfo")
	public ApiResponse<List<VmiPersonInfoEntity_HI_RO>> findPersonInfo(
			@RequestBody ApiRequest<VmiPersonInfoEntity_HI_RO> params)
			throws IllegalAccessException {
		try {
			Pagination<VmiPersonInfoEntity_HI_RO> result = ttaUserServer
					.findPersonInfo(params);
			return ApiResponse.SUCCESS().fluentSetPageData(result);
		} catch (Exception e) {
			return ApiResponse.FAIL();
		}
	}

	@ApiOperation(value = "供应商分名称", notes = "供应商分名称")
	@RequestMapping(method = RequestMethod.POST, value = "findScoringMemberLov")
	public JSONObject findScoringMemberLov(String params) {
		return ttaDataServer.findScoringMemberLov(params);
	}

	@ApiOperation(value = "通过用户id查询用户上级信息", notes = "通过用户id查询用户上级信息")
	@RequestMapping(method = RequestMethod.POST, value = "getUserParentByuserId")
	public List<UserInfoEntity_RO> findProccessUsersByuserId(Integer userId) {
		return ttaUserServer.findProccessUsersByuserId(userId);
	}

	@ApiOperation(value = "通过用户id查询用户指定职位上级信息", notes = "通过用户id查询用户指定职位上级信息")
	@RequestMapping(method = RequestMethod.POST, value = "findProccessUsersNode")
	public ApiResponse<List<UserInfoEntity_RO>> findProccessUsersNode(
			Integer userId, String jobCode) {
		return ApiResponse.SUCCESS().fluentSetData(
				ttaDataServer.findProccessUsersByuserId(userId, jobCode));
	}

	@ApiOperation(value = "通过用户名称查询用户信息", notes = "通过用户名称查询用户信息")
	@RequestMapping(method = RequestMethod.POST, value = "getUserInfoByUserName")
	public UserInfoEntity_RO getUserInfoByUserName(String userName) {
		return ttaUserServer.getUserInfoByuserName(userName);
	}

	@ApiOperation(value = "接口权限查询", notes = "接口权限查询")
	@RequestMapping(method = RequestMethod.POST, value = "findInterfaceAccessControl")
	public JSONObject findInterfaceAccessControl(String params) {
		return ttaDataServer.findInterfaceAccessControl(params);
	}
}
