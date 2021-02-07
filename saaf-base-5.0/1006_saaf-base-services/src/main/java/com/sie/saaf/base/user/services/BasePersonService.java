package com.sie.saaf.base.user.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePersonOrganizationPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePerson;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * @author ZhangJun
 * @creteTime 2017-12-15
 */
@RestController
@RequestMapping("/basePersonService")
public class BasePersonService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory
			.getLogger(BasePersonService.class);
	@Autowired
	private IBasePerson basePersonServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.basePersonServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findBasePersonList")
	public String findBasePersonList(
			@RequestParam(required = false) String params) {
		return this.findList(params);
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPersonPagination")
	public String findPersonPagination(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		return super.findPagination(params, pageIndex, pageRows);
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param params
	 *            {<br>
	 *            personId: 人员Id,（更新时必填）<br>
	 *            employeeNumber: 员工号,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *            sex: 性别,<br>
	 *            birthDay: 生日,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 启用标识,<br>
	 *            telPhone: 电话号码,<br>
	 *            mobilePhone: 手机号码,<br>
	 *            email: 邮箱地址,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            operatorUserId:操作人<br>
	 *            versionNum: 版本号,（更新时必填）<br>
	 *            }
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	@Override
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		return super.saveOrUpdate(params);
	}

	/**
	 * 查找数据
	 * 
	 * @param params
	 *            {<br>
	 *            personName: 人员姓名,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: 人员类型,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 是否启用,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            sex: 性别,<br>
	 *            }
	 * @param pageIndex
	 * @param pageRows
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	@Override
	public String findPagination(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.cleanNull(queryParamJSON, "personType");
			Pagination<BasePersonOrganizationPerson_HI_RO> findList = this.basePersonServer
					.findBasePersonsJoinPersonOrg(queryParamJSON, pageIndex,
							pageRows);
			JSONObject results = JSONObject.parseObject(JSON
					.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toJSONString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param params
	 *            参数id { id:需要删除的数据Id，如果需要删除多个，则用;分隔 }
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	@Override
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 根据组织机构Id查询人员列表
	 * 
	 * @param params
	 *            JSON参数 {orgId:组织机构Id}
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 分页人员列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         birthDay: 出生日期,<br>
	 *         cardNo: 身份证号,<br>
	 *         creationDate: 创建时间,<br>
	 *         email: 邮箱地址,<br>
	 *         employeeNumber: 员工号,<br>
	 *         enabled: 是否启用,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         mobilePhone: 手机号,<br>
	 *         personId: 人员,<br>
	 *         personName: 人员姓名,<br>
	 *         personType: 人员类型,<br>
	 *         postalAddress: 通信地址,<br>
	 *         postcode: 邮编,<br>
	 *         sex: 性别,<br>
	 *         telPhone: 电话,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */

	@RequestMapping(method = RequestMethod.POST, value = "findBasePersonsByOrgId")
	public String findBasePersonsByOrgId(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<BasePersonOrganizationPerson_HI_RO> findList = this.basePersonServer
					.findBasePersonsByOrgId(queryParamJSON.getInteger("orgId"),
							pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON
					.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 根据职位Id查询人员列表
	 * 
	 * @param params
	 *            JSON参数 {positionId:职位Id}
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 分页人员列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         birthDay: 出生日期,<br>
	 *         cardNo: 身份证号,<br>
	 *         creationDate: 创建时间,<br>
	 *         email: 邮箱地址,<br>
	 *         employeeNumber: 员工号,<br>
	 *         enabled: 是否启用,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         mobilePhone: 手机号,<br>
	 *         personId: 人员,<br>
	 *         personName: 人员姓名,<br>
	 *         personType: 人员类型,<br>
	 *         postalAddress: 通信地址,<br>
	 *         postcode: 邮编,<br>
	 *         sex: 性别,<br>
	 *         telPhone: 电话,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBasePersonsByPositionId")
	public String findBasePersonsByPositionId(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<BasePersonOrganizationPerson_HI_RO> findList = this.basePersonServer
					.findBasePersonsByPositionId(
							queryParamJSON.getInteger("positionId"), pageIndex,
							pageRows);
			JSONObject results = JSONObject.parseObject(JSON
					.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toJSONString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存人员并保存与组织、职位关系
	 * 
	 * @param params
	 *            保存参数 { personId:人员Id,更新时必填）<br>
	 *            employeeNumber: 员工号,<br>
	 *            personName: 人员姓名,<br>
	 *            personType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购,<br>
	 *            sex: 性别,<br>
	 *            birthDay: 生日,<br>
	 *            cardNo: 身份证号,<br>
	 *            enabled: 启用标识,<br>
	 *            telPhone: 电话号码,<br>
	 *            mobilePhone: 手机号码,<br>
	 *            email: 邮箱地址,<br>
	 *            postalAddress: 通信地址,<br>
	 *            postcode: 邮编,<br>
	 *            operatorUserId:操作人<br>
	 *            versionNum: 版本号,（更新时必填）<br>
	 *            orgIds:[{<br>
	 *            orgId:组织Id,<br>
	 *            positionIds:[1,2,3,4]职位列表<br>
	 *            }] }
	 * @return 人员表信息 BasePersonEntity_HI
	 * @author ZhangJun
	 * @createTime 2018/1/7 20:46
	 * @description 保存人员并保存与组织、职位关系
	 */
	@RequestMapping(method = RequestMethod.POST, value = "savePersonUsers")
	public String savePersonUsers(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			BasePersonEntity_HI entity = this.basePersonServer
					.savePersonOrganization(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0,
					entity).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping("/findProccessUsers")
	public String findProccessUsers(
			@RequestParam(required = false) String params,
			@RequestParam(required = false) String messageIndex) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<BaseUsersPerson_HI_RO> proccessUsers = basePersonServer
					.findProccessUsers(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					proccessUsers.size(), proccessUsers).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping("/findAssignStartUser")
	public String findAssignStartUser(
			@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON, "userId",
					"userGroupCode");
			List<BaseUserGroupAssignEntity_HI_RO> assignUsers = basePersonServer
					.findAssignStartUser(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					assignUsers.size(), assignUsers).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping("/findProccessStartUser")
	public String findProccessStartUser(
			@RequestParam(required = false) String params,
			@RequestParam(required = false) String messageIndex) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<BaseUsersPerson_HI_RO> proccessUsers = basePersonServer
					.findProccessStartUser(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					proccessUsers.size(), proccessUsers).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/*
	 * @RequestMapping("/findTest") public String
	 * findTest(@RequestParam(required=false) String params){ try{ JSONObject
	 * queryParamJSON = parseObject(params);
	 * queryParamJSON.put("params",queryParamJSON); String url =
	 * "http://1002-SAAF-API-GATEWAY/api/baseServer/basePersonService/findProccessUsers"
	 * ; url =
	 * "http://1002-saaf-api-gateway/api/baseServer/basePersonService/findProccessUsers?params={\"userId\":1,\"userType\":20}"
	 * ; //url =
	 * "http://1006-SAAF-BASE-SERVER/basePersonService/findProccessUsers"; //url
	 * = "http://2000-SAAF-TTA-SERVER/ttaTermsFrameHeaderService/updateStatus";
	 * JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(url,
	 * queryParamJSON); return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
	 * "操作成功", 0, 0).toString(); }catch(Exception e){
	 * logger.error(e.getMessage(),e); return
	 * SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0,
	 * null).toString(); } }
	 */

	@RequestMapping("/findAssignStartUser2")
	public String findAssignStartUser2(
			@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON, "userId");
			List<BaseUserGroupAssignEntity_HI_RO> assignUsers = basePersonServer
					.findAssignStartUser2(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					assignUsers.size(), assignUsers).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}
