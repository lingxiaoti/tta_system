package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseOrgStructureEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseOrgStructure;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @auther: huqitao 2018/6/29
 */
@RestController
@RequestMapping("/baseOrgStructureService")
public class BaseOrgStructureService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseOrgStructureService.class);
    @Autowired
    private IBaseOrgStructure baseOrgStructureServer;
    @Autowired
    private IBaseAccreditCache baseAccreditCacheServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return null;
    }

    /**
     * 直接上级（包含人员和经销商的直接上级）
     *
     * @param params {
     *               roleType：角色（person：人员，customer：经销商）
     *               personId：人员ID
     *               positionId：职位ID
     *               custAccountId：经销商ID
     *               departmentId：部门ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findImmediateSuperior")
    public String findImmediateSuperior(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON = checkRoleType(queryParamJSON);
            if (!"S".equals(queryParamJSON.getString("status"))) {
                return JSON.toJSONString(queryParamJSON);
            }
//            if (StringUtils.isNotBlank(queryParamJSON.getString("roleType")) && StringUtils.equals(queryParamJSON.getString("roleType"), "guide")) {
//                SaafToolUtils.validateJsonParms(queryParamJSON, "varUserId");
//                Integer targetPersonId = baseOrgStructureServer.findGuidePersonId(queryParamJSON.getInteger("varUserId"));
//                if (targetPersonId == null) {
//                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到导购的直接上级人员ID", 0, null).toString();
//                }
//                BaseOrgStructureEntity_HI_RO personInfo = baseOrgStructureServer.findPersonInfo(targetPersonId);
//                if (personInfo == null) {
//                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "指定职务的人员信息异常", 0, null).toString();
//                }
//                List<BaseOrgStructureEntity_HI_RO> immediateSuperiorInfos = new ArrayList<>();
//                immediateSuperiorInfos.add(personInfo);
//                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", immediateSuperiorInfos.size(), immediateSuperiorInfos).toString();
//            }
            List<BaseOrgStructureEntity_HI_RO> immediateSuperiorInfos = baseOrgStructureServer.findImmediateSuperiorInfo(queryParamJSON);
            if (immediateSuperiorInfos.size() == 0) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在直接上级", 0, null).toString();
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", immediateSuperiorInfos.size(), immediateSuperiorInfos).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据当前人员和当前职位，向上查找组织架构树中某个职位（p_find_position_id）的人。用循环或递归的方式
     *
     * @param params {
     *               personId:人员ID,
     *               positionId:职位ID,
     *               targetPositionId:目标职位ID（用于比较）
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPargetPositionInfo")
    public String findPargetPositionInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON = checkRoleType(queryParamJSON);
            if (!"S".equals(queryParamJSON.getString("status"))) {
                return JSON.toJSONString(queryParamJSON);
            }
            SaafToolUtils.validateJsonParms(queryParamJSON, "positionId", "targetPositionId");
            JSONObject jsonResult = baseOrgStructureServer.findPargetPositionInfo(queryParamJSON);
            if (jsonResult == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在指定职位的人员", 0, null).toString();
            }
            //            { "targetPositionId": 746, "targetPersonId": 5805 }
            Integer targetPersonId = jsonResult.getInteger("targetPersonId");
            BaseOrgStructureEntity_HI_RO personInfo = baseOrgStructureServer.findPersonInfo(targetPersonId);
            if (personInfo == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "指定职务的人员信息异常", 0, null).toString();
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, personInfo).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据当前人员和当前职位，向上查找组织架构树中所有的职位。用循环或递归的方式
     *
     * @param params {
     *               personId:人员ID,
     *               positionId:职位ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAllHigherLevelPosition")
    public String findAllHigherLevelPosition(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "positionId");
            JSONArray resultArray = baseOrgStructureServer.findAllHigherLevelPosition(1, new JSONArray(), queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", resultArray.size(), resultArray).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据角色转换参数
     *
     * @param queryParamJSON
     * @return
     */
    public JSONObject checkRoleType(JSONObject queryParamJSON) {
        if (StringUtils.isNotBlank(queryParamJSON.getString("roleType")) && StringUtils.equals(queryParamJSON.getString("roleType"), "person")) {
            SaafToolUtils.validateJsonParms(queryParamJSON, "positionId");
        } else if (StringUtils.isNotBlank(queryParamJSON.getString("roleType")) && StringUtils.equals(queryParamJSON.getString("roleType"), "customer")) {
            SaafToolUtils.validateJsonParms(queryParamJSON, "custAccountId", "departmentId");
            List<BaseOrgStructureEntity_HI_RO> relationList = baseOrgStructureServer.findCustomerPersonRelation(queryParamJSON);
            if (relationList.size() == 0) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "没有找到经销商对应的业务员信息", 0, null);
            }
            queryParamJSON.put("personId", relationList.get(0).getPersonId());
            queryParamJSON.put("positionId", relationList.get(0).getPositionId());
        } else if (StringUtils.isNotBlank(queryParamJSON.getString("roleType")) && StringUtils.equals(queryParamJSON.getString("roleType"), "guide")) {
            SaafToolUtils.validateJsonParms(queryParamJSON, "varUserId");
        } else {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "异常角色(roleType)类型", 0, null);
        }
        queryParamJSON.put("status", SUCCESS_STATUS);
        return queryParamJSON;
    }

    /**
     * 根据当前人员和职位，向上查找组织架构树中所有的职位及人员信息。用循环或递归的方式
     *
     * @param params {
     *               personId:人员ID,
     *               positionId:职位ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAllMgrJobPersonInfo")
    public String findAllMgrJobPersonInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON = checkRoleType(queryParamJSON);
            if (!"S".equals(queryParamJSON.getString("status"))) {
                return JSON.toJSONString(queryParamJSON);
            }
            List<JSONArray> linkedList = baseOrgStructureServer.findAllMgrJobPersonInfo(new LinkedList<JSONArray>(), queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", linkedList.size(), linkedList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 获取指定职位中所有父层职位中职务为jobId或者jobCode的人员和用户信息
     *
     * @param params {
     *               personId:人员ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPreParentPersonUsers")
    public String findPreParentPersonUsers(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON = checkRoleType(queryParamJSON);
            if (!"S".equals(queryParamJSON.getString("status"))) {
                return JSON.toJSONString(queryParamJSON);
            }
            SaafToolUtils.validateJsonParms(queryParamJSON, "positionId");
            JSONObject jsonResult = baseOrgStructureServer.findParentJobInfo(queryParamJSON);
            if (jsonResult == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在指定职务的人员", 0, null).toString();
            }
//            { "targetJobId": 746, "targetPersonId": 5805 }
            Integer targetPersonId = jsonResult.getInteger("targetPersonId");
            BaseOrgStructureEntity_HI_RO personInfo = baseOrgStructureServer.findPersonInfo(targetPersonId);
            if (personInfo == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "指定职务的人员信息异常", 0, null).toString();
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, personInfo).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据部门departmentId查找部门负责人
     *
     * @param params {
     *               departmentId:部门ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDeptLeaderInfo")
    public String findDeptLeaderInfo(@RequestParam(required = false) String params) {
        try {
            List<BaseOrgStructureEntity_HI_RO> deptLeaderList = new ArrayList<>();
            JSONObject queryParamJSON = parseObject(params);
            //SaafToolUtils.validateJsonParms(queryParamJSON, "departmentId");
            //List<BaseOrgStructureEntity_HI_RO> deptLeaderList = baseOrgStructureServer.findDeptLeaderInfo(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", deptLeaderList.size(), deptLeaderList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 通过userId获取用户信息
     *
     * @param params {
     *               userId:用户IDID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserInfo")
    public String findUserInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "userId");
            List<BaseOrgStructureEntity_HI_RO> userInfoList = baseOrgStructureServer.findUserInfo(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", userInfoList.size(), userInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据人员查询职位
     *
     * @param params {
     *               personId:人员ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPositionByPersonId")
    public String findPositionByPersonId(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "personId");
            List<BaseOrgStructureEntity_HI_RO> positionInfoList = baseOrgStructureServer.findPositionByPersonId(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", positionInfoList.size(), positionInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据人员查询职位
     *
     * @param params {
     *               personId:人员ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPositionPagination")
    public String findPositionPagination(@RequestParam(required = false) String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<BaseOrgStructureEntity_HI_RO> positionPagination = baseOrgStructureServer.findPositionPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(positionPagination));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据职位查询人员
     *
     * @param params {
     *               positionId:职位ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserInfoByPositionId")
    public String findUserInfoByPositionId(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramJSON, "positionId");
            List<BaseOrgStructureEntity_HI_RO> userInfoList = baseOrgStructureServer.findAllInfo(new JSONObject().fluentPut("positionId", paramJSON.getInteger("positionId")));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", userInfoList.size(), userInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据部门和职务查询人员
     *
     * @param params {
     *               departmentId:部门ID,
     *               jobId:职务ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserInfoByOrgIdAndJobId")
    public String findUserInfoByOrgIdAndJobId(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramJSON, "departmentId");
            if (StringUtils.isBlank(paramJSON.getString("jobId")) && StringUtils.isBlank(paramJSON.getString("jobCode"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "参数职位ID和职位编码，其中一个必须有值。", 0, null).toString();
            }
            JSONObject queryParamsJSON = new JSONObject();
            queryParamsJSON.put("departmentId", paramJSON.getInteger("departmentId"));
            if (StringUtils.isNotBlank(paramJSON.getString("jobId"))) {
                queryParamsJSON.put("jobId", paramJSON.getInteger("jobId"));
            }
            if (StringUtils.isNotBlank(paramJSON.getString("jobCode"))) {
                queryParamsJSON.put("jobCode", paramJSON.getString("jobCode"));
            }
            List<BaseOrgStructureEntity_HI_RO> userInfoList = baseOrgStructureServer.findAllInfo(queryParamsJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", userInfoList.size(), userInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     *
     * @param params {
     *               parentDepartmentId:上级部门ID
     *               departmentId:部门ID,
     *               departmentName:部门名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findLowerDeptInfo")
    public String findLowerDeptInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List<BaseOrgStructureEntity_HI_RO> lowerDeptInfoList = baseOrgStructureServer.findLowerDeptInfo(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", lowerDeptInfoList.size(), lowerDeptInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     *
     * @param params {
     *               parentDepartmentName:上级部门名称,
     *               departmentName:部门名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBaseDeptInfo")
    public String findBaseDeptInfo(@RequestParam(required = false) String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Integer respId = queryParamJSON.getInteger("respId");
            Integer varUserId = queryParamJSON.getInteger("varUserId");
            if (null != respId && null != varUserId) {
                ProFileBean proFileBean = baseAccreditCacheServer.getOrg(varUserId, respId);
                if (null != proFileBean) {
                    queryParamJSON.put("orgId", Integer.valueOf(proFileBean.getProfileValue()));
                } else {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取职责相关组织机构出现异常！", 0, null).toString();
                }
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取职责信息出现异常！", 0, null).toString();
            }
            Pagination<BaseOrgStructureEntity_HI_RO> lowerDeptInfoList = baseOrgStructureServer.findBaseDeptInfo(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(lowerDeptInfoList));
            jsonResult.put("status", SUCCESS_STATUS);

            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据用户类型查询不同的部门信息
     *
     * @param params {
     *               parentDepartmentName:上级部门名称,
     *               <p>
     *               departmentName:部门名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBaseDeptInfoByParent")
    public String findBaseDeptInfoByParent(@RequestParam(required = false) String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isNotEmpty(queryParamJSON.getString("sysFlag")) && "bpm".equals(queryParamJSON.getString("sysFlag"))) {
                if (StringUtils.isEmpty(queryParamJSON.getString("orgId"))) {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取对应的事业部信息", 0, null).toString();
                }
            } else {
                Integer respId = queryParamJSON.getInteger("respId");
                Integer varUserId = queryParamJSON.getInteger("varUserId");
                if (null != respId && null != varUserId) {
                    ProFileBean proFileBean = baseAccreditCacheServer.getOrg(varUserId, respId);
                    if (null != proFileBean) {
                        queryParamJSON.put("orgId", Integer.valueOf(proFileBean.getProfileValue()));
                    } else {
                        return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取职责相关组织机构出现异常！", 0, null).toString();
                    }
                    ProFileBean userTypeBean = baseAccreditCacheServer.getUserType(varUserId, respId);
                    if (userTypeBean == null) {
                        return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的userType信息", 0, null).toString();
                    } else {
                        queryParamJSON.put("userType", Integer.valueOf(userTypeBean.getProfileValue()));
                    }
                } else {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取职责信息出现异常！", 0, null).toString();
                }
            }
            Pagination<BaseOrgStructureEntity_HI_RO> lowerDeptInfoList = baseOrgStructureServer.findBaseDeptInfoByParent(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(lowerDeptInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据用户类型查询不同的部门信息
     *
     * @param params {
     *               parentDepartmentName:上级部门名称,
     *               <p>
     *               departmentName:部门名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBaseDeptInfoByUserType")
    public String findBaseDeptInfoByUserType(@RequestParam(required = false) String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Integer respId = queryParamJSON.getInteger("respId");
            Integer varUserId = queryParamJSON.getInteger("varUserId");
            if (null != respId && null != varUserId) {
                ProFileBean proFileBean = baseAccreditCacheServer.getOrg(varUserId, respId);
                if (null != proFileBean) {
                    queryParamJSON.put("orgId", Integer.valueOf(proFileBean.getProfileValue()));
                } else {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取职责相关组织机构出现异常！", 0, null).toString();
                }
                ProFileBean userTypeBean = baseAccreditCacheServer.getUserType(varUserId, respId);
                if (userTypeBean == null) {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的userType信息", 0, null).toString();
                } else {
                    queryParamJSON.put("userType", Integer.valueOf(userTypeBean.getProfileValue()));
                }
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取职责信息出现异常！", 0, null).toString();
            }
            Pagination<BaseOrgStructureEntity_HI_RO> lowerDeptInfoList = baseOrgStructureServer.findBaseDeptInfoByUserType(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(lowerDeptInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     *
     * @param params {
     *               parentDepartmentId:上级部门ID
     *               departmentId:部门ID,
     *               departmentCode:部门编号,
     *               departmentName:部门名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDeptInfo")
    public String findDeptInfo(@RequestParam(required = false) String params,
                               @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                               @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("orgId", ouBean.getProfileValue());
            Pagination<BaseOrgStructureEntity_HI_RO> deptInfoList = baseOrgStructureServer.findDeptInfo(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(deptInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据部门查找人员信息
     *
     * @param params {
     *               departmentId:部门ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonInfoByOrgId")
    public String findPersonInfoByOrgId(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureServer.findPersonInfoByOrgId(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", personInfoList.size(), personInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询人员信息
     *
     * @param params {
     *               personName:人员名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonInfo")
    public String findPersonInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isBlank(queryParamJSON.getString("personName")) && StringUtils.isBlank(queryParamJSON.getString("personId"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "请检查参数personName或personId", 0, null).toString();
            }
            List<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureServer.findAllInfo(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", personInfoList.size(), personInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询人员信息,就算有多个职位，也只会返回一个人
     *
     * @param params {
     *               personName:人员名称
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonInfoOnlyOnePosition")
    public String findPersonInfoOnlyOnePosition(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isBlank(queryParamJSON.getString("personName")) && StringUtils.isBlank(queryParamJSON.getString("personId"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "请检查参数personName或personId", 0, null).toString();
            }
            List<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureServer.findPersonInfoOnlyOnePosition(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", personInfoList.size(), personInfoList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询人员信息（分页）
     *
     * @param params {
     *               departmentId:部门ID,
     *               departmentCode:部门编号,
     *               departmentName:部门名称,
     *               personId:人员ID,
     *               personName:人员名称,
     *               positionId:职位ID,
     *               positionName:职位名称,
     *               mobilePhone:手机号,
     *               employeeNumber:员工号
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPaginationPersonInfo")
    public String findPaginationPersonInfo(@RequestParam(required = false) String params,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureServer.findAllInfo(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(personInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 查询人员信息（分页）就算有多个职位，也只会返回一个人
     *
     * @param params {
     *               departmentId:部门ID,
     *               departmentCode:部门编号,
     *               departmentName:部门名称,
     *               personId:人员ID,
     *               personName:人员名称,
     *               positionId:职位ID,
     *               positionName:职位名称,
     *               mobilePhone:手机号,
     *               employeeNumber:员工号
     *               keyword:关键字的模糊搜索
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAllInfoOnlyOnePosition")
    public String findAllInfoOnlyOnePosition(@RequestParam(required = false) String params,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureServer.findAllInfoOnlyOnePosition(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(personInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据人员和职位查找所有下级人员信息(包含多级)
     *
     * @param params {
     *               positionId:职位ID
     *               }
     * @return 所有下级人员信息(包含多级)
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAllLowerPersonByPosition")
    public String findAllLowerPersonByPosition(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isBlank(queryParamJSON.getString("positionId"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "请检查参数，职位positionId", 0, null).toString();
            }
            List<BaseOrgStructureEntity_HI_RO> personInfoLinkedList = new LinkedList<>();
            personInfoLinkedList = baseOrgStructureServer.findPersonTreeList(personInfoLinkedList, queryParamJSON.getInteger("positionId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", personInfoLinkedList.size(), personInfoLinkedList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 人员控件
     *
     * @param params    {
     *                  responsibilityId:职责ID
     *                  }
     * @param pageIndex 页码
     * @param pageRows  每页查询记录数
     * @return 人员信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAccessPersonPagination")
    public String findAccessPersonPagination(@RequestParam(required = false) String params,
                                             @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "responsibilityId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            ProFileBean userTypeBean = baseAccreditCacheServer.getUserType(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
            if (userTypeBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的userType信息", 0, null).toString();
            }
            if (StringUtils.isBlank(queryParamJSON.getString("needChannelFlag")) || "Y".equals(queryParamJSON.getString("needChannelFlag"))) {
                ProFileBean channelTypeBean = baseAccreditCacheServer.getChannelType(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
                if (channelTypeBean == null) {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的channelType信息", 0, null).toString();
                }
                if (!"90".equals(channelTypeBean.getProfileValue())) {
                    queryParamJSON.put("channelCode", channelTypeBean.getProfileValue());
                }
            }
            queryParamJSON.put("userId", queryParamJSON.getInteger("varUserId"));
            queryParamJSON.put("orgId", ouBean.getProfileValue());
            queryParamJSON.put("userType", userTypeBean.getProfileValue());

            Pagination<BaseOrgStructureEntity_HI_RO> accessPersonList = baseOrgStructureServer.findAccessPersonPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(accessPersonList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 人员控件[获取某一个职责OU下面的所有的人员]
     *
     * @param params    {
     *                  responsibilityId:职责ID
     *                  }
     * @param pageIndex 页码
     * @param pageRows  每页查询记录数
     * @return 人员信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonPagination")
    public String findPersonPagination(@RequestParam(required = false) String params,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            /*queryParamJSON.put("responsibilityId", queryParamJSON.get("operationRespId"));
            SaafToolUtils.validateJsonParms(queryParamJSON, "responsibilityId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("orgId", ouBean.getProfileValue());
            */
            queryParamJSON.put("userType", "10"); //指定全局用户，查询所有的人员
            Pagination<BaseOrgStructureEntity_HI_RO> accessPersonList = baseOrgStructureServer.findAccessPersonPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(accessPersonList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 经销商权限控件
     *
     * @param params    {
     *                  responsibilityId:职责ID
     *                  }
     * @param pageIndex 页码
     * @param pageRows  每页查询记录数
     * @return 经销商信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAccessCustomerPagination")
    public String findAccessCustomerPagination(@RequestParam(required = false) String params,
                                               @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                               @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "responsibilityId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            ProFileBean userTypeBean = baseAccreditCacheServer.getUserType(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
            if (userTypeBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的userType信息", 0, null).toString();
            }
            if (StringUtils.isBlank(queryParamJSON.getString("needChannelFlag")) || "Y".equals(queryParamJSON.getString("needChannelFlag"))) {
                ProFileBean channelTypeBean = baseAccreditCacheServer.getChannelType(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
                if (channelTypeBean == null) {
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的channelType信息", 0, null).toString();
                }
                if (!"90".equals(channelTypeBean.getProfileValue())) {
                    queryParamJSON.put("channelCode", channelTypeBean.getProfileValue());
                }
            }
            queryParamJSON.put("userId", queryParamJSON.getInteger("varUserId"));
            queryParamJSON.put("orgId", ouBean.getProfileValue());
            queryParamJSON.put("userType", userTypeBean.getProfileValue());
            Pagination<BaseOrgStructureEntity_HI_RO> accessCustomerList = baseOrgStructureServer.findAccessCustomerPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(accessCustomerList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 经销商权限控件[获取某一个职责OU下面的所有的经销商]
     *
     * @param params    {
     *                  responsibilityId:职责ID
     *                  }
     * @param pageIndex 页码
     * @param pageRows  每页查询记录数
     * @return 经销商信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findCustomerPagination")
    public String findCustomerPagination(@RequestParam(required = false) String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "responsibilityId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("responsibilityId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }

            queryParamJSON.put("orgId", ouBean.getProfileValue());
            Pagination<BaseOrgStructureEntity_HI_RO> accessCustomerList = baseOrgStructureServer.findCustomerPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(accessCustomerList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 经销商对应业务人员
     *
     * @param params {
     *               custAccountId：经销商ID，
     *               departmentId：部门ID
     *               }
     * @return 经销商对应业务人员列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findCustomerPersonRelation")
    public String findCustomerPersonRelation(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "custAccountId", "departmentId");
            List<BaseOrgStructureEntity_HI_RO> customerPersonRelationList = baseOrgStructureServer.findCustomerPersonRelation(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", customerPersonRelationList.size(), customerPersonRelationList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 部门树、部门经销商树
     *
     * @param params {
     *               ouId：事业部ID，
     *               userType：用户类型
     *               }
     * @return 经销商对应业务人员列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDeptTreeOrDeptCusTree")
    public String findDeptTreeOrDeptCusTree(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "ouId", "userType");

            List<BaseOrgStructureEntity_HI_RO> treeList = new ArrayList<>();
            if ("20".equals(queryParamJSON.getString("userType")) || "30".equals(queryParamJSON.getString("userType"))) {
                //员工用户、经销商用户
                treeList = baseOrgStructureServer.findDeptTreeInfo(queryParamJSON.getInteger("ouId"));
            } else if ("40".equals(queryParamJSON.getString("userType"))) {
                //门店用户
                treeList = baseOrgStructureServer.findDeptCusTreeInfo(queryParamJSON.getInteger("ouId"));
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "当前仅支持员工、经销商、门店用户", 0, null).toString();
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", treeList.size(), treeList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 人员、经销商、门店
     *
     * @param params {
     *               ouId：事业部ID，
     *               keyWordId_IN：部门ID、经销商ID，
     *               keyWordName：人员名称、经销商名称
     *               userType：用户类型
     *               }
     * @return 人员、经销商、门店列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserByKeyWordId")
    public String findUserByKeyWordId(@RequestParam(required = false) String params,
                                      @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "ouId", "userType");

            if (StringUtils.isBlank(queryParamJSON.getString("keyWordId_IN"))) {
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, null).toString();
            }

            String userType = queryParamJSON.getString("userType");
            if (StringUtils.isNotBlank(queryParamJSON.getString("keyWordName"))) {
                if ("20".equals(userType)) { //员工用户
                    queryParamJSON.put("personName", queryParamJSON.getString("keyWordName"));
                } else if ("30".equals(userType)) { //经销商用户
                    queryParamJSON.put("customerName", queryParamJSON.getString("keyWordName"));
                } else if ("40".equals(userType)) { //门店用户
                    queryParamJSON.put("storeName", queryParamJSON.getString("keyWordName"));
                }
            }
            Pagination<BaseOrgStructureEntity_HI_RO> userInfoPagination = null;
            if ("20".equals(userType)) {
                queryParamJSON.put("departmentId_IN", queryParamJSON.getString("keyWordId_IN"));
                userInfoPagination = baseOrgStructureServer.findPersonInDeptPagination(queryParamJSON, pageIndex, pageRows);
            } else if ("30".equals(userType)) {
                queryParamJSON.put("departmentId_IN", queryParamJSON.getString("keyWordId_IN"));
                userInfoPagination = baseOrgStructureServer.findCustomerInDeptPagination(queryParamJSON, pageIndex, pageRows);
            } else if ("40".equals(userType)) {
                queryParamJSON.put("custAccountId_IN", queryParamJSON.getString("keyWordId_IN"));
                userInfoPagination = baseOrgStructureServer.findStoreInCustomerPagination(queryParamJSON, pageIndex, pageRows);
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无效的userType：" + userType, 0, null).toString();
            }
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(userInfoPagination));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询员工供应商银行账号信息
     *
     * @param params {
     *               personId:人员ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonCusBankPagination")
    public String findPersonCusBankPagination(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "personId");
            List<JSONObject> personCusBankList = baseOrgStructureServer.findPersonCusBankInfoPagination(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", personCusBankList.size(), personCusBankList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询员工雇佣信息
     *
     * @param params {
     *               personId:人员ID
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonHireInfo")
    public String findPersonHireInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "personId");
            List<JSONObject> personHireList = baseOrgStructureServer.findPersonHireInfo(queryParamJSON.getInteger("personId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", personHireList.size(), personHireList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPersonNewPagination")
    public String findPersonNewPagination(@RequestParam(required = false) String params,
                                          @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                          @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("ouId", ouBean.getProfileValue());
            Pagination<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureServer.findPersonNewPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(personInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}