package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganizationPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganization_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Set;

/**
 * 接口：对组织表base_organization进行CRUD操作
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
public interface IBaseOrganization extends IBaseCommon<BaseOrganizationEntity_HI> {

	/**
	 * 根据组织机构编码查询
	 * @param orgCode 组织机构编码
	 * @return BaseOrganizationEntity_HI列表
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	List<BaseOrganizationEntity_HI> findByOrgCode(String orgCode);

	/**
	 * 分页查询组织机构列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 组织机构列表<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }<br>
	 */
	Pagination<BaseOrganizationPerson_HI_RO> findBaseOrganizationsPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 查询所有下层组织机构列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询列表<br>
	 *            {<br>
	 *            orgHierarchyId:组织机构层级关系(必须),<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @param pageIndex 页码
	 * @param pageRows 每页显示记录数
	 * @return 下层组织列表<br>
	 *         [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 * 
	 */
	Pagination<BaseOrganizationPerson_HI_RO> findAllChildrens(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 查询所有上层组织机构列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询参数（orgHierarchyId和orgId任选其一）<br>
	 *            {<br>
	 *            orgHierarchyId:组织机构层级Id,<br>
	 *            orgId:组织机构Id,<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @return 所有上层组织机构列表<br>
	 *         [{ <br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 */
	List<BaseOrganizationPerson_HI_RO> findAllParents(JSONObject queryParamJSON);

	/**
	 * 查询当前组织机构下层
	 * 
	 * @param queryParamJSON
	 *            查询参数，必须传入orgId <br>
	 *            {<br>
	 *            orgId:组织机构Id（必填）<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @return 下一层组织机构列表<br>
	 *         [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         leaderId: 组织领导Id<br>
	 *         leaderEmployeeNumber: 员工号<br>
	 *         leaderPersonName: 人员姓名<br>
	 *         leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
	 *         leaderSex: 性别<br>
	 *         leaderBirthDay: 生日<br>
	 *         leaderCardNo: 身份证号<br>
	 *         leaderEnableFlag: 启用标识<br>
	 *         leaderTelPhone: 电话号码<br>
	 *         leaderMobilePhone: 手机号码<br>
	 *         leaderEmail: 邮箱地址<br>
	 *         leaderPostalAddress: 通信地址<br>
	 *         leaderPostcode: 邮编<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 */
	List<BaseOrganizationPerson_HI_RO> findCurrentOrgChildrens(JSONObject queryParamJSON);

	/**
	 * 查询指定人员的所属组织机构
	 * 
	 * @param queryParamJSON
	 *            查询参数，必须传入personId <br>
	 * 			{<br>
	 *            personId:人员Id（必填）<br>
	 *            parentOrgId: 父机构Id<br>
	 *            orgCode: 组织机构编码<br>
	 *            orgName: 组织机构名称<br>
	 *            treeType: 组织树类型（行政、预算、核算）<br>
	 *            channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *            businessType: 业务类型（业务、推广）<br>
	 *            isDep: 部门/渠道标志<br>
	 *            orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *            orgPinyinName: 机构名称(拼音)<br>
	 *            orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *            sourceSystemId: 源系统ID<br>
	 *            startDate: 启用日期<br>
	 *            endDate: 失效日期<br>
	 *            enabled: 是否启用（Y：启用；N：禁用）<br>
	 *            deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *            }
	 * @return 指定人员所属组织机构列表<br>
	 *         [{<br>
	 *         orgId: 组织机构Id<br>
	 *         parentOrgId: 父机构Id<br>
	 *         orgCode: 组织机构编码<br>
	 *         orgName: 组织机构名称<br>
	 *         treeType: 组织树类型（行政、预算、核算）<br>
	 *         channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
	 *         businessType: 业务类型（业务、推广）<br>
	 *         isDep: 部门/渠道标志<br>
	 *         orgType: 类型（ORG：机构；DEPT：部门）<br>
	 *         orgLevel: 组织机构层级<br>
	 *         isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
	 *         startDate: 启用日期<br>
	 *         endDate: 失效日期<br>
	 *         enabled: 是否启用（Y：启用；N：禁用）<br>
	 *         remark: 备注<br>
	 *         orgPinyinName: 机构名称(拼音)<br>
	 *         orgSimplePinyinName: 机构名称(拼音首字母)<br>
	 *         orderNo: 排序号<br>
	 *         deleteFlag: 是否删除（0：未删除；1：已删除）<br>
	 *         orgHierarchyId: 层级结构<br>
	 *         orgEmail: 邮件地址<br>
	 *         sourceSystemId: 源系统ID<br>
	 *         versionNum: 版本号<br>
	 *         creationDate:创建日期<br>
	 *         createdBy:创建人<br>
	 *         lastUpdateDate:更新日期<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }]
	 */
	List<BaseOrganizationPerson_HI_RO> findOrganizationByPersonId(JSONObject queryParamJSON);

	List<BaseOrganization_HI_RO> findBaseOrganizationROEntities();

	/**
	 * 同步库存组织
	 * @author ZhangJun
	 * @createTime 2018/3/20
	 * @description 同步库存组织
	 */
	JSONObject saveSyncInvOrganization(JSONObject queryParamJSON);

	/**
	 * 查询缓存数据
	 * @author ZhangJun
	 * @createTime 2018/3/20
	 * @description
	 */
	List findCacheOrgInvList(JSONObject queryParamJSON);

    Pagination findOprSubInvList(Set<String> queryParam, Integer pageIndex, Integer pageRows);

	JSONObject saveInvOrg2Redis(JSONObject queryJSONParam);
}
