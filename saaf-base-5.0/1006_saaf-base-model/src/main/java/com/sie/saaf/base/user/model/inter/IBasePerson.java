package com.sie.saaf.base.user.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePersonOrganizationPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 接口：对人员表base_person进行CRUD操作
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
public interface IBasePerson extends IBaseCommon<BasePersonEntity_HI> {

	/**
	 * 获取需要同步的人员数据
	 * 
	 * @param lastSyncTime
	 * @return
	 */
	List<JSONObject> findPerAllPeopleFList(String lastSyncTime);

	/**
	 * 新增或修改人员数据（同步人员时用到）
	 * 
	 * @param basePersonEntity
	 * @return
	 * @throws Exception
	 */
	BasePersonEntity_HI saveOrUpdateBasePerson(
			BasePersonEntity_HI basePersonEntity) throws Exception;

	/**
	 * 通过源系统ID和系统来源查询人员信息
	 * 
	 * @param sourceId
	 *            源系统ID
	 * @param sourceCode
	 *            系统来源
	 * @return
	 */
	List<BasePersonEntity_HI> findBasePersonList(Integer sourceId,
			String sourceCode);

	/**
	 * 根据组织机构Id查询人员列表
	 * 
	 * @param orgId
	 *            组织机构Id
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * 
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
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	Pagination<BasePersonOrganizationPerson_HI_RO> findBasePersonsByOrgId(
			Integer orgId, Integer pageIndex, Integer pageRows);

	/**
	 * 根据职位Id查询人员列表
	 * 
	 * @param positionId
	 *            职位Id
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * 
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
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	Pagination<BasePersonOrganizationPerson_HI_RO> findBasePersonsByPositionId(
			Integer positionId, Integer pageIndex, Integer pageRows);

	/**
	 * 关联base_person_organization查询人员列表
	 * 
	 * @param queryParamJSON
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数 <br>
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
	 * 
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
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	Pagination<BasePersonOrganizationPerson_HI_RO> findBasePersonsJoinPersonOrg(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 保存人员并保存与组织、职位关系
	 * 
	 * @param queryParamJSON
	 *            保存参数 { personId:人员Id,更新时必填） employeeNumber: 员工号,<br>
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
	BasePersonEntity_HI savePersonOrganization(JSONObject queryParamJSON);

	void saveByExcuteSql(String sql);

	/**
	 * 通过当前登录用户id，查询汇报关系，在找到用户类型
	 */
	public List<BaseUsersPerson_HI_RO> findProccessUsers(
			JSONObject queryParamJSON);

	/**
	 * 功能描述：通过流程发起人及汇报关系查询用户信息
	 * 
	 * @date 2019/9/20
	 * @param
	 * @return
	 */
	public List<BaseUsersPerson_HI_RO> findProccessStartUser(
			JSONObject queryParamJSON);

	/**
	 * 功能描述：通过流程发起人及汇报关系查询指定群组的用户
	 * 
	 * @param queryParamJSON
	 * @return
	 */
	List<BaseUserGroupAssignEntity_HI_RO> findAssignStartUser(
			JSONObject queryParamJSON);

	List<BaseUserGroupAssignEntity_HI_RO> findAssignStartUser2(
			JSONObject queryParamJSON);
}
