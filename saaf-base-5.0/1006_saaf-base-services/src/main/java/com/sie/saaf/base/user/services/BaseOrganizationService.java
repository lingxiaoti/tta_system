package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.services.BaseProfileService;
import com.sie.saaf.base.user.model.entities.BaseOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganization_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseOrganization;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ZhangJun
 * @creteTime 2017-12-15
 */
@RestController
@RequestMapping("/baseOrganizationService")
public class BaseOrganizationService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseOrganizationService.class);
    @Autowired
    private IBaseOrganization baseOrganizationServer;

    @Autowired
    private BaseProfileService baseProfileService;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseOrganizationServer;
    }

    /**
     * 保存或更新数据
     *
     * @param params {<br>
     *               orgId: 组织机构Id（更新时必填）<br>
     *               parentOrgId: 父机构Id<br>
     *               oldParentOrgId:旧的父机构Id（当更新层级时需要传入）<br>
     *               orgCode: 组织机构编码<br>
     *               orgName: 组织机构名称<br>
     *               treeType: 组织树类型（行政、预算、核算）<br>
     *               channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     *               businessType: 业务类型（业务、推广）<br>
     *               isDep: 部门/渠道标志<br>
     *               orgType: 类型（ORG：机构；DEPT：部门）<br>
     *               orgLevel: 组织机构层级<br>
     *               isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
     *               startDate: 启用日期<br>
     *               endDate: 失效日期<br>
     *               enabled: 是否启用（Y：启用；N：禁用）<br>
     *               remark: 备注<br>
     *               orgPinyinName: 机构名称(拼音)<br>
     *               orgSimplePinyinName: 机构名称(拼音首字母)<br>
     *               orderNo: 排序号<br>
     *               deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     *               orgHierarchyId: 层级结构<br>
     *               orgEmail: 邮件地址<br>
     *               sourceSystemId: 源系统ID<br>
     *               leaderId: 组织领导Id<br>
     *               operatorUserId:<br>
     *               versionNum: 版本号（更新时必填）<br>
     *               }
     * @return {
     * status:操作是否成功,E:失败，S:成功
     * msg:成功或者失败后消息
     * count:成功的记录数
     * data:成功的数据
     * }
     * @author ZhangJun
     * @creteTime 2017/12/15
     */
    @RequestMapping(method = RequestMethod.POST, value = "save")
    @Override
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        JSONObject queryParamJSON = parseObject(params);
        String orgCode = queryParamJSON.getString("orgCode");
        String orgId = queryParamJSON.getString("orgId");
        List<BaseOrganizationEntity_HI> entitys = this.baseOrganizationServer.findByOrgCode(orgCode);
        if (StringUtils.isEmpty(orgId) && entitys != null && !entitys.isEmpty()) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "组织编码" + orgCode + "已存在", 0, null).toString();
        }
        return super.saveOrUpdate(params);
    }

    /**
     * 删除数据
     *
     * @param params 参数id
     *               {
     *               id:需要删除的数据Id，如果需要删除多个，则用;分隔
     *               }
     * @return {
     * status:操作是否成功,E:失败，S:成功
     * msg:成功或者失败后消息
     * count:成功的记录数
     * data:成功的数据
     * }
     * @author ZhangJun
     * @creteTime 2017/12/15
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    @Override
    public String delete(@RequestParam(required = false) String params) {
        return super.delete(params);
    }

    /**
     * 查找数据
     *
     * @param params    查询参数
     *                  {<br>
     *                  parentOrgId: 父机构Id<br>
     *                  orgCode: 组织机构编码<br>
     *                  orgName: 组织机构名称<br>
     *                  treeType: 组织树类型（行政、预算、核算）<br>
     *                  channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     *                  businessType: 业务类型（业务、推广）<br>
     *                  isDep: 部门/渠道标志<br>
     *                  orgType: 类型（ORG：机构；DEPT：部门）<br>
     *                  orgPinyinName: 机构名称(拼音)<br>
     *                  orgSimplePinyinName: 机构名称(拼音首字母)<br>
     *                  sourceSystemId: 源系统ID<br>
     *                  startDate: 启用日期<br>
     *                  endDate: 失效日期<br>
     *                  enabled: 是否启用（Y：启用；N：禁用）<br>
     *                  deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return {
     * status:操作是否成功,E:失败，S:成功
     * msg:成功或者失败后消息
     * count:成功的记录数
     * data:[{<br>
     * orgId: 组织机构Id<br>
     * parentOrgId: 父机构Id<br>
     * orgCode: 组织机构编码<br>
     * orgName: 组织机构名称<br>
     * treeType: 组织树类型（行政、预算、核算）<br>
     * channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     * businessType: 业务类型（业务、推广）<br>
     * isDep: 部门/渠道标志<br>
     * orgType: 类型（ORG：机构；DEPT：部门）<br>
     * orgLevel: 组织机构层级<br>
     * isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
     * startDate: 启用日期<br>
     * endDate: 失效日期<br>
     * enabled: 是否启用（Y：启用；N：禁用）<br>
     * remark: 备注<br>
     * orgPinyinName: 机构名称(拼音)<br>
     * orgSimplePinyinName: 机构名称(拼音首字母)<br>
     * orderNo: 排序号<br>
     * deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     * orgHierarchyId: 层级结构<br>
     * orgEmail: 邮件地址<br>
     * sourceSystemId: 源系统ID<br>
     * versionNum: 版本号<br>
     * leaderId: 组织领导Id<br>
     * leaderEmployeeNumber: 员工号<br>
     * leaderPersonName: 人员姓名<br>
     * leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
     * leaderSex: 性别<br>
     * leaderBirthDay: 生日<br>
     * leaderCardNo: 身份证号<br>
     * leaderEnableFlag: 启用标识<br>
     * leaderTelPhone: 电话号码<br>
     * leaderMobilePhone: 手机号码<br>
     * leaderEmail: 邮箱地址<br>
     * leaderPostalAddress: 通信地址<br>
     * leaderPostcode: 邮编<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdateDate:更新日期<br>
     * lastUpdatedBy:更新人<br>
     * }]
     * }
     * @author ZhangJun
     * @creteTime 2017/12/15
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
//            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
//            ProFileBean proFileBean= baseAccreditCacheServer.getOrg(getSessionUserId(),queryParamJSON.getInteger("respId"));
//            Assert.notNull(proFileBean,"用户当前职责未配置ou");
//            Set<String> orgIds=new HashSet<>();
//            orgIds.add(proFileBean.getProfileValue());
//            queryParamJSON.put("operationOrgIds",orgIds );
            Pagination findList = baseOrganizationServer.findBaseOrganizationsPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询所有下层组织机构列表
     *
     * @param params    查询列表<br>
     *                  {<br>
     *                  orgHierarchyId:组织机构层级关系(必须),<br>
     *                  parentOrgId: 父机构Id<br>
     *                  orgCode: 组织机构编码<br>
     *                  orgName: 组织机构名称<br>
     *                  treeType: 组织树类型（行政、预算、核算）<br>
     *                  channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     *                  businessType: 业务类型（业务、推广）<br>
     *                  isDep: 部门/渠道标志<br>
     *                  orgType: 类型（ORG：机构；DEPT：部门）<br>
     *                  orgPinyinName: 机构名称(拼音)<br>
     *                  orgSimplePinyinName: 机构名称(拼音首字母)<br>
     *                  sourceSystemId: 源系统ID<br>
     *                  startDate: 启用日期<br>
     *                  endDate: 失效日期<br>
     *                  enabled: 是否启用（Y：启用；N：禁用）<br>
     *                  deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     *                  }
     * @param pageIndex 页码
     * @param pageRows  每页显示记录数
     * @return 下层组织列表<br>
     * [{<br>
     * orgId: 组织机构Id<br>
     * parentOrgId: 父机构Id<br>
     * orgCode: 组织机构编码<br>
     * orgName: 组织机构名称<br>
     * treeType: 组织树类型（行政、预算、核算）<br>
     * channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     * businessType: 业务类型（业务、推广）<br>
     * isDep: 部门/渠道标志<br>
     * orgType: 类型（ORG：机构；DEPT：部门）<br>
     * orgLevel: 组织机构层级<br>
     * isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
     * startDate: 启用日期<br>
     * endDate: 失效日期<br>
     * enabled: 是否启用（Y：启用；N：禁用）<br>
     * remark: 备注<br>
     * orgPinyinName: 机构名称(拼音)<br>
     * orgSimplePinyinName: 机构名称(拼音首字母)<br>
     * orderNo: 排序号<br>
     * deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     * orgHierarchyId: 层级结构<br>
     * orgEmail: 邮件地址<br>
     * sourceSystemId: 源系统ID<br>
     * versionNum: 版本号<br>
     * leaderId: 组织领导Id<br>
     * leaderEmployeeNumber: 员工号<br>
     * leaderPersonName: 人员姓名<br>
     * leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
     * leaderSex: 性别<br>
     * leaderBirthDay: 生日<br>
     * leaderCardNo: 身份证号<br>
     * leaderEnableFlag: 启用标识<br>
     * leaderTelPhone: 电话号码<br>
     * leaderMobilePhone: 手机号码<br>
     * leaderEmail: 邮箱地址<br>
     * leaderPostalAddress: 通信地址<br>
     * leaderPostcode: 邮编<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdateDate:更新日期<br>
     * lastUpdatedBy:更新人<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017-12-12
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAllChildrens")
    public String findAllChildrens(@RequestParam(required = false) String params,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination findList = baseOrganizationServer.findAllChildrens(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询所有上层组织机构列表
     *
     * @param params 查询参数（orgHierarchyId和orgId任选其一）<br>
     *               {<br>
     *               orgHierarchyId:组织机构层级Id,<br>
     *               orgId:组织机构Id,<br>
     *               parentOrgId: 父机构Id<br>
     *               orgCode: 组织机构编码<br>
     *               orgName: 组织机构名称<br>
     *               treeType: 组织树类型（行政、预算、核算）<br>
     *               channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     *               businessType: 业务类型（业务、推广）<br>
     *               isDep: 部门/渠道标志<br>
     *               orgType: 类型（ORG：机构；DEPT：部门）<br>
     *               orgPinyinName: 机构名称(拼音)<br>
     *               orgSimplePinyinName: 机构名称(拼音首字母)<br>
     *               sourceSystemId: 源系统ID<br>
     *               startDate: 启用日期<br>
     *               endDate: 失效日期<br>
     *               enabled: 是否启用（Y：启用；N：禁用）<br>
     *               deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     *               }
     * @return 所有上层组织机构列表<br>
     * [{ <br>
     * orgId: 组织机构Id<br>
     * parentOrgId: 父机构Id<br>
     * orgCode: 组织机构编码<br>
     * orgName: 组织机构名称<br>
     * treeType: 组织树类型（行政、预算、核算）<br>
     * channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     * businessType: 业务类型（业务、推广）<br>
     * isDep: 部门/渠道标志<br>
     * orgType: 类型（ORG：机构；DEPT：部门）<br>
     * orgLevel: 组织机构层级<br>
     * isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
     * startDate: 启用日期<br>
     * endDate: 失效日期<br>
     * enabled: 是否启用（Y：启用；N：禁用）<br>
     * remark: 备注<br>
     * orgPinyinName: 机构名称(拼音)<br>
     * orgSimplePinyinName: 机构名称(拼音首字母)<br>
     * orderNo: 排序号<br>
     * deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     * orgHierarchyId: 层级结构<br>
     * orgEmail: 邮件地址<br>
     * sourceSystemId: 源系统ID<br>
     * versionNum: 版本号<br>
     * leaderId: 组织领导Id<br>
     * leaderEmployeeNumber: 员工号<br>
     * leaderPersonName: 人员姓名<br>
     * leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
     * leaderSex: 性别<br>
     * leaderBirthDay: 生日<br>
     * leaderCardNo: 身份证号<br>
     * leaderEnableFlag: 启用标识<br>
     * leaderTelPhone: 电话号码<br>
     * leaderMobilePhone: 手机号码<br>
     * leaderEmail: 邮箱地址<br>
     * leaderPostalAddress: 通信地址<br>
     * leaderPostcode: 邮编<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdateDate:更新日期<br>
     * lastUpdatedBy:更新人<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017-12-12
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAllParents")
    public String findAllParents(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List findList = baseOrganizationServer.findAllParents(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询当前组织机构下层
     *
     * @param params 查询参数，必须传入orgId <br>
     *               {<br>
     *               orgId:组织机构Id（必填）<br>
     *               parentOrgId: 父机构Id<br>
     *               orgCode: 组织机构编码<br>
     *               orgName: 组织机构名称<br>
     *               treeType: 组织树类型（行政、预算、核算）<br>
     *               channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     *               businessType: 业务类型（业务、推广）<br>
     *               isDep: 部门/渠道标志<br>
     *               orgType: 类型（ORG：机构；DEPT：部门）<br>
     *               orgPinyinName: 机构名称(拼音)<br>
     *               orgSimplePinyinName: 机构名称(拼音首字母)<br>
     *               sourceSystemId: 源系统ID<br>
     *               startDate: 启用日期<br>
     *               endDate: 失效日期<br>
     *               enabled: 是否启用（Y：启用；N：禁用）<br>
     *               deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     *               }
     * @return 下一层组织机构列表<br>
     * [{<br>
     * orgId: 组织机构Id<br>
     * parentOrgId: 父机构Id<br>
     * orgCode: 组织机构编码<br>
     * orgName: 组织机构名称<br>
     * treeType: 组织树类型（行政、预算、核算）<br>
     * channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     * businessType: 业务类型（业务、推广）<br>
     * isDep: 部门/渠道标志<br>
     * orgType: 类型（ORG：机构；DEPT：部门）<br>
     * orgLevel: 组织机构层级<br>
     * isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
     * startDate: 启用日期<br>
     * endDate: 失效日期<br>
     * enabled: 是否启用（Y：启用；N：禁用）<br>
     * remark: 备注<br>
     * orgPinyinName: 机构名称(拼音)<br>
     * orgSimplePinyinName: 机构名称(拼音首字母)<br>
     * orderNo: 排序号<br>
     * deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     * orgHierarchyId: 层级结构<br>
     * orgEmail: 邮件地址<br>
     * sourceSystemId: 源系统ID<br>
     * versionNum: 版本号<br>
     * leaderId: 组织领导Id<br>
     * leaderEmployeeNumber: 员工号<br>
     * leaderPersonName: 人员姓名<br>
     * leaderPersonType: IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购<br>
     * leaderSex: 性别<br>
     * leaderBirthDay: 生日<br>
     * leaderCardNo: 身份证号<br>
     * leaderEnableFlag: 启用标识<br>
     * leaderTelPhone: 电话号码<br>
     * leaderMobilePhone: 手机号码<br>
     * leaderEmail: 邮箱地址<br>
     * leaderPostalAddress: 通信地址<br>
     * leaderPostcode: 邮编<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdateDate:更新日期<br>
     * lastUpdatedBy:更新人<br>
     * }]
     */
    @RequestMapping(method = RequestMethod.POST, value = "findCurrentOrgChildrens")
    public String findCurrentOrgChildrens(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List findList = baseOrganizationServer.findCurrentOrgChildrens(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询指定人员的所属组织机构
     *
     * @param params 查询参数，必须传入personId <br>
     *               {<br>
     *               personId:人员Id（必填）<br>
     *               parentOrgId: 父机构Id<br>
     *               orgCode: 组织机构编码<br>
     *               orgName: 组织机构名称<br>
     *               treeType: 组织树类型（行政、预算、核算）<br>
     *               channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     *               businessType: 业务类型（业务、推广）<br>
     *               isDep: 部门/渠道标志<br>
     *               orgType: 类型（ORG：机构；DEPT：部门）<br>
     *               orgPinyinName: 机构名称(拼音)<br>
     *               orgSimplePinyinName: 机构名称(拼音首字母)<br>
     *               sourceSystemId: 源系统ID<br>
     *               startDate: 启用日期<br>
     *               endDate: 失效日期<br>
     *               enabled: 是否启用（Y：启用；N：禁用）<br>
     *               deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     *               }
     * @return 指定人员所属组织机构列表<br>
     * [{<br>
     * orgId: 组织机构Id<br>
     * parentOrgId: 父机构Id<br>
     * orgCode: 组织机构编码<br>
     * orgName: 组织机构名称<br>
     * treeType: 组织树类型（行政、预算、核算）<br>
     * channelType: 渠道类型(商务、电商、OTC、医务、内部等<br>
     * businessType: 业务类型（业务、推广）<br>
     * isDep: 部门/渠道标志<br>
     * orgType: 类型（ORG：机构；DEPT：部门）<br>
     * orgLevel: 组织机构层级<br>
     * isLeaf: 是否是叶子节点，(0：叶子节点，1：非叶子节点)<br>
     * startDate: 启用日期<br>
     * endDate: 失效日期<br>
     * enabled: 是否启用（Y：启用；N：禁用）<br>
     * remark: 备注<br>
     * orgPinyinName: 机构名称(拼音)<br>
     * orgSimplePinyinName: 机构名称(拼音首字母)<br>
     * orderNo: 排序号<br>
     * deleteFlag: 是否删除（0：未删除；1：已删除）<br>
     * orgHierarchyId: 层级结构<br>
     * orgEmail: 邮件地址<br>
     * sourceSystemId: 源系统ID<br>
     * versionNum: 版本号<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdateDate:更新日期<br>
     * lastUpdatedBy:更新人<br>
     * }]
     */
    @RequestMapping(method = RequestMethod.POST, value = "findOrganizationByPersonId")
    public String findOrganizationByPersonId(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List findList = baseOrganizationServer.findOrganizationByPersonId(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "findBaseOrganizationROEntities")
    public String findBaseOrganizationROEntities() {
        try {
            List<BaseOrganization_HI_RO> list = baseOrganizationServer.findBaseOrganizationROEntities();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findList")
    @Override
    public String findList(String params) {
        return super.findList(params);
    }

    /**
     * 查询库存组织
     * @author ZhangJun
     * @createTime 2018/3/20
     * @description 查询库存组织
     */
    @RequestMapping(method = RequestMethod.POST,value="findCacheOrgInvList")
    public String findCacheOrgInvList(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            queryParamJSON.put("orgType", "INV_ORG");//组织类型 库存组织INV_ORG
            List list = baseOrganizationServer.findCacheOrgInvList(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST,value="saveSyncInvOrganization")
    public String saveSyncInvOrganization(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            JSONObject result = this.baseOrganizationServer.saveSyncInvOrganization(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, new JSONArray().fluentAdd(result)).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="saveInvOrg2Redis")
    public String saveInvOrg2Redis(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            JSONObject result = this.baseOrganizationServer.saveInvOrg2Redis(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", result.getIntValue("count"), result).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * huangminglin
     * 箱码追溯记录查询符合条件的操作子库编码
     *
     * @param params ：{oprSubInv：操作子库编码}
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findOprSubInvList")
    public String findOprSubInvList(@RequestParam(required = false) String params,
                                    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {

        try {
            JSONObject queryParamJSON = parseObject(params);

            queryParamJSON.fluentPut("profileCode", "orgId");

            String profileParams = queryParamJSON.toString();

            //查询profile值
            String profileSqlDatas = baseProfileService.findProfileSqlDatasByResponsibilityId(profileParams);

            JSONObject profileSqlDatasParamJSON = parseObject(profileSqlDatas);

            JSONArray dataJsonArrray = (JSONArray) profileSqlDatasParamJSON.get("data");

            Set<String> valudList = new HashSet<>();

            if(dataJsonArrray != null) {
                for (int i = 0; i < dataJsonArrray.size(); i++) {
                    String value = dataJsonArrray.getString(i);
                    valudList.add(value);
                }
            }

            Pagination oprSubInvList = baseOrganizationServer.findOprSubInvList(valudList, pageIndex, pageRows);

            JSONObject results = JSONObject.parseObject(JSON.toJSONString(oprSubInvList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
