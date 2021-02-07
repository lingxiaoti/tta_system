package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseOrgStructureEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * @auther: huqitao 2018/6/29
 */
public interface IBaseOrgStructure {
    /**
     * 查询导购的上级人员ID
     * @param userId
     * @return
     */
    Integer findGuidePersonId(Integer userId);

    /**
     * 查询人员信息
     * @param personId
     * @return
     */
    BaseOrgStructureEntity_HI_RO findPersonInfo(Integer personId);

    /**
     * 根据人员、职位，查找人员的直接上级
     * queryParamJSON
     * {
     *     personId：人员ID
     *     positionId：职位ID
     * }
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findImmediateSuperiorInfo(JSONObject queryParamJSON);

//    /**
//     * 根据职位和职务id查询人员的直接上级人员信息
//     * @param queryParamJSON
//     * @return
//     */
//    List<BaseOrgStructureEntity_HI_RO> findPreParentPersonUsers(JSONObject queryParamJSON);

    /**
     * 根据部门orgId查找部门负责人
     * queryParamJSON
     * {
     *    departmentId:部门ID
     * }
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findDeptLeaderInfo(JSONObject queryParamJSON);

    /**
     * 通过userId获取用户信息
     * queryParamJSON
     * {
     *    userId:用户ID
     * }
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findUserInfo(JSONObject queryParamJSON);

    /**
     * 根据当前人员和当前职位，向上查找组织架构树中某个职位（p_find_position_id）的人。用循环或递归的方式
     * queryParamJSON
     * {
     *      personId:人员ID,
     *      positionId:职位ID,
     *      targetPositionId:目标职位ID（用于比较）
     * }
     * @param queryParamJSON
     * @return
     */
    JSONObject findPargetPositionInfo(JSONObject queryParamJSON);

    /**
     * 根据当前人员和当前职位，向上查找组织架构树中某个职位（job_id 或 jobCode）的人。用循环或递归的方式
     * queryParamJSON
     * {
     *      personId:人员ID,
     *      positionId:职位ID,
     *      targetJobId:目标职务ID（用于比较）,
     *      targetJobCode:目标职务编码（用于比较）
     * }
     * @param queryParamJSON
     * @return
     */
    JSONObject findParentJobInfo(JSONObject queryParamJSON);

    /**
     * 根据当前人员和当前职位，向上查找组织架构树中所有的职位。用循环或递归的方式
     * resultJSONArray
     * queryParamJSON
     * {
     *      personId:人员ID,
     *      positionId:职位ID
     * }
     * @param queryParamJSON
     * @return
     */
    JSONArray findAllHigherLevelPosition(int levelFlag, JSONArray resultJSONArray, JSONObject queryParamJSON);

    /**
     * 查询当前职位的所有上级信息
     *
     * @param linkedList
     * @param queryParamJSON
     * @return
     */
    List<JSONArray> findAllMgrJobPersonInfo(List<JSONArray> linkedList, JSONObject queryParamJSON);

    /**
     * 查询上级信息
     *
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findMgrJobPersonInfo(JSONObject queryParamJSON);

    /**
     * 根据人员查询职位
     * queryParamJSON
     * {
     *      personId:人员ID
     * }
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findPositionByPersonId(JSONObject queryParamJSON);

    /**
     * 根据人员查询职位(分页)
     *
     * @param queryParamJSON
     * {
     *    personId:人员ID
     * }
     * @return 人员对应的职位
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findPositionPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     * queryParamJSON
     * {
     *      departmentId:部门ID,
     *      departmentName:部门名称
     * }
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findLowerDeptInfo(JSONObject queryParamJSON);


    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     * queryParamJSON
     * {
     *      departmentId:部门ID,
     *      departmentName:部门名称
     * }
     * @param queryParamJSON
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findBaseDeptInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 根据用户类型获取部门信息
     * queryParamJSON
     * {
     *      departmentId:部门ID,
     *      parentDepartmentId:父departemntId
     *      departmentName:部门名称
     * }*/
     Pagination<BaseOrgStructureEntity_HI_RO> findBaseDeptInfoByParent(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 根据用户类型获取部门信息
     * queryParamJSON
     * {
     *      departmentId:部门ID,
     *      userType:用户类型
     *      departmentName:部门名称
     * }
     * @param queryParamJSON
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findBaseDeptInfoByUserType(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


    /**
     * 询部门信息(分页)
     * queryParamJSON
     * {
     * orgId:部门ID,
     * orgCode:部门编号,
     * orgName:部门名称
     * }
     * @param queryParamJSON
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findDeptInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 根据部门查找人员信息
     * queryParamJSON
     * {
     *     departmentId:部门ID,
     *     departmentCode:部门编号,
     *     departmentName:部门名称
     *     primaryFlag:是否过滤多个职位
     * }
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findPersonInfoByOrgId(JSONObject queryParamJSON);

    /**
     * 查询人员信息
     *
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findAllInfo(JSONObject queryParamJSON);

    /**
     * 查询人员信息 就算有多个职位，也只会返回一个人
     *
     * @param queryParamJSON
     * @return
     */
    List<BaseOrgStructureEntity_HI_RO> findPersonInfoOnlyOnePosition(JSONObject queryParamJSON);

    /**
     * 查询人员信息(分页)
     * queryParamJSON
     * {
     *     departmentId:部门ID,
     *     departmentCode:部门编号,
     *     departmentName:部门名称,
     *     personId:人员ID,
     *     personName:人员名称,
     *     positionId:职位ID,
     *     positionName:职位名称,
     *     mobilePhone:手机号,
     *     employeeNumber:员工号
     * }
     * @param queryParamJSON
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findAllInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询人员信息(分页) 就算有多个职位，也只会返回一个人
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID,
     *                       departmentCode:部门编号,
     *                       departmentName:部门名称,
     *                       personId:人员ID,
     *                       personName:人员名称,
     *                       positionId:职位ID,
     *                       positionName:职位名称,
     *                       mobilePhone:手机号,
     *                       employeeNumber:员工号
     *                       keyword:关键字的模糊搜索
     *                       }
     * @return 人员信息
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findAllInfoOnlyOnePosition(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 根据职位查找当前职位下级人员信息
     *
     * @param positionId 当前职位ID
     * @return 当前职位ID对应的下级人员信息
     */
    List<BaseOrgStructureEntity_HI_RO> findLowerPersonByPositionId(Integer positionId);

    /**
     * 根据职位查找所有下级人员信息(包含多级)
     *
     * @param personInfoLinkedList 当前职位下的下一级人员信息
     * @param positionId           当前职位ID
     * @return 当前职位下级所有人员信息
     */
    List<BaseOrgStructureEntity_HI_RO> findPersonTreeList(List<BaseOrgStructureEntity_HI_RO> personInfoLinkedList, Integer positionId);

    /**
     * 人员权限（找出自己以及自己下级所有扥人员--传入的是USER_ID，员工用户）
     *
     * @param queryParamJSON 参数
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 人员权限
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findAccessPersonPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 经销商权限（找出自己以及自己下级所关联的经销商—传入的是USER_ID，员工用户，经销商用户）
     *
     * @param queryParamJSON 参数
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 经销商权限
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findAccessCustomerPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 经销商权限（不过滤userId 和 userType）
     * @param queryParamJSON 参数
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 经销商权限
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findCustomerPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 经销商对应业务人员（分页）
     *
     * @param queryParamJSON 参数
     * @return 经销商对应业务人员
     */
    List<BaseOrgStructureEntity_HI_RO> findCustomerPersonRelation(JSONObject queryParamJSON);

    /**
     * 部门树
     * @param ouId 事业部ID
     * @return 部门树
     */
    List<BaseOrgStructureEntity_HI_RO> findDeptTreeInfo(Integer ouId);

    /**
     * 部门经销商树
     * @param ouId:事业部ID
     * @return 部门经销商树
     */
    List<BaseOrgStructureEntity_HI_RO> findDeptCusTreeInfo(Integer ouId);

    /**
     * 查询部门下的人员,包含user信息
     * @param queryParamJSON 参数
     * {
     *     ouId：事业部Id(必须)，
     *     departmentId_IN：部门ID列表（1,2,3,4）（必须），
     *     personName：人员名称
     * }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findPersonInDeptPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询部门下的经销商,包含user信息
     * @param queryParamJSON 参数
     * {
     *     ouId：事业部Id(必须)，
     *     departmentId_IN：部门ID列表（1,2,3,4）（必须），
     *     customerName：经销商名称
     * }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findCustomerInDeptPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询部门下的经销商,包含user信息
     * @param queryParamJSON 参数
     * {
     *     ouId：事业部Id(必须)，
     *     custAccountId_IN：经销商ID列表（1,2,3,4）（必须），
     *     storeName：门店名称
     * }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findStoreInCustomerPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询员工供应商银行账号信息
     * @param queryParamJSON
     * {
     *     personId 人员ID
     *     primaryAcctOwnerName_like 收款人
     *     bankAccountNumElectronic_like 银行账号
     * }
     * @return 员工供应商银行账号信息
     */
    List<JSONObject> findPersonCusBankInfoPagination(JSONObject queryParamJSON) throws Exception;

    /**
     * 查询员工雇佣信息
     * @param personId 人员ID
     * @return 员工雇佣信息
     */
    List<JSONObject> findPersonHireInfo(Integer personId) throws Exception;

    /**
     * 查询人员--特殊（分页）
     * @param queryParamJSON
     * @return
     */
    Pagination<BaseOrgStructureEntity_HI_RO> findPersonNewPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
