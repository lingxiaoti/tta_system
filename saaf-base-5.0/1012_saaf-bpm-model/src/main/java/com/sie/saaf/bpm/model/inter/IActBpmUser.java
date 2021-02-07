package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.common.bean.UserSessionBean;

import java.util.List;

public interface IActBpmUser {

	public final static String USER_TABLE = "base_users";
	public final static String USER_ID_FIELD = "userId";
	public final static String USER_ID_COLUMN = "user_id";
	public final static String USER_NAME_COLUMN = "user_name";
	public final static String USER_FULL_NAME_COLUMN = "user_full_name";
    public final static String BPM_USER_ID_COLUMN = "user_name";//Activiti用户ID与系统用户表字段映射
    public final static String BPM_USER_ID_FIELD = "userName";//Activiti用户ID与系统用户属性映射
    public final static String BPM_USER_JSONARRAY_FIELD = "saaf_user_list";
    public final static String BPM_USER_JSONOBJECT_FIELD = "saaf_user_info";
    
    /**
     * 根据会话信息获取流程用户ID
     * @param user 会话用户信息
     * @return 流程用户ID
     */
    String getBpmUserId(UserSessionBean user);
    
    /**
     * 根据流程用户ID获取系统用户ID
     * @param userId 流程用户ID
     * @return 系统用户ID
     */
    Object getSysUserId(String userId);
    
    
    /**
     * 根据流程用户ID查询系统用户
     * @author laoqunzhao 2018-05-07
     * @param userId  流程用户ID
     * @return 用户ActIdUserEntity_RO
     */
    ActIdUserEntity_RO findUserByBpmId(String userId);

    /**
     * 根据系统用户ID查询系统用户
     * @author laoqunzhao 2018-05-07
     * @param userId  系统用户ID
     * @return 用户ActIdUserEntity_RO
     */
    ActIdUserEntity_RO findUserBySysId(Object userId);

    /**
     * 根据流程用户ID集合查询系统用户
     * @author laoqunzhao 2018-05-07
     * @param userIds 流程用户ID集合
     * @return 用户集合 List<ActIdUserEntity_RO>
     */
    List<ActIdUserEntity_RO> findUsersByBpmIds(List<String> userIds);

    /**
     * 根据用户ID集合查询系统用户
     * @author laoqunzhao 2018-05-07
     * @param userIds 用户ID集合
     * @return 用户集合 List<ActIdUserEntity_RO>
     */
    List<ActIdUserEntity_RO> findUsersBySysIds(List<?> userIds);

    /**
     * 获取流程角色人员
     * @author laoqunzhao 2018-05-07
     * @param bpmRoleKeys 流程角色KEY
     * @param processInstanceId 流程实例ID
     * @return 用户集合List<ActIdUserEntity_RO>
     */
	List<ActIdUserEntity_RO> findUsersByBpmRoleKeys(List<String> bpmRoleKeys, String processInstanceId);
	
	/**
     * 获取流程角色人员
     * @author laoqunzhao 2018-05-07
     * @param bpmRoleKeys 流程角色KEY
     * @param bpmListId 申请单ID
     * @return 用户集合List<ActIdUserEntity_RO>
     */
	List<ActIdUserEntity_RO> findUsersByBpmRoleKeys(List<String> bpmRoleKeys, Integer bpmListId);
	
	/**
     * 获取组织机构人员
     * @author laoqunzhao 2018-05-07
     * @param orgIds 组织机构ID集合
     * @return 用户集合List<ActIdUserEntity_RO>
     */
	List<ActIdUserEntity_RO> findUsersByOrgIds(List<?> orgIds);
	
	/**
     * 获取用户集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param processInstanceId 流程实例ID
     * @return
     */
	List<ActIdUserEntity_RO> findUsers(List<String> bpmUserIds, List<String> bpmRoleKeys,
									   List<?> orgIds, String processInstanceId);

	/**
     * 获取用户集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param bpmListId 申请单ID
     * @return
     */
    List<ActIdUserEntity_RO> findUsers(List<String> bpmUserIds,
									   List<String> bpmRoleKeys, List<?> orgIds, Integer bpmListId);

    /**
     * 获取用户ID集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param processInstanceId 流程实例ID
     * @return
     */
    List<?> findSysUserIds(List<String> bpmUserIds, List<String> bpmRoleKeys,
						   List<?> orgIds, String processInstanceId);


    /**
     * 获取用户ID集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param bpmListId 申请单ID
     * @return
     */
    List<?> findSysUserIds(List<String> bpmUserIds, List<String> bpmRoleKeys,
						   List<?> orgIds, Integer bpmListId);

    /**
     * 根据用户ID、职位ID查询用户信息
     * @author laoqunzhao 2018-07-17
     * @param userId 用户ID
	 * @param positionId 职位ID
	 * @param departmentId 部门ID
     * @param bpmUserId 是否是流程用户ID： true:是， false:否
     * @return 用户<ActIdUserEntity_RO>
     */
	ActIdUserEntity_RO findUserByPositionId(Object userId, Integer positionId, Integer departmentId, boolean bpmUserId);

	/**
	 * 根据员工ID、职位ID查询用户信息
	 * @author laoqunzhao 2018-09-10
	 * @param personId 员工ID
	 * @param positionId 职位ID
	 * @param departmentId 部门ID
	 * @return 用户<ActIdUserEntity_RO>
	 */
	public ActIdUserEntity_RO findUserByPositionIdAndPersonId(Object personId, Integer positionId, Integer departmentId);

	/**
	 * 获取用户的OU
	 * @param paramsJSON
	 * {
	 *   ouId
	 *   orgId
	 *   responsibilityId
	 * }
	 * @param userId
	 * @return ouID
	 */
	Integer getOuId(JSONObject paramsJSON, Integer userId);

	/**
     * 实现表与用户表关联查询的left join语句
     * @author laoqunzhao 2018-05-07
     * @param field 主表字段
     * @param alias 用户表别名
     * @param bpmUserId 是否通过流程用户ID相应的字段关联
     * @return 用户表left join查询语句
     */
	String getJoinTableSQL(String field, String alias, boolean bpmUserId);

	/**
     * 实现用户根据用户名/姓名模糊查询语句
     * @author laoqunzhao 2018-05-07
     * @param alias 用户表别名
     * @param userNameParam 用户名参数语句
     * @param userFullNameParam 姓名参数语句
     * @return 用户名/姓名模糊查询语句
     */
	String getSearchSQL(String alias, String userNameParam, String userFullNameParam);

	/**
     * 对结果集JSONArray附上用户信息
     * @author laoqunzhao 2018-05-07
     * @param array JSONArray结果集
     * @param jsonKeyField JSON中存储用户key的字段
     * @param bpmUserId 用户key是不是流程用户的ID
     * @param alias 用户信息别名前缀，为避免字段冲突可加上前缀
     */ 
	void appendUserInfo(JSONArray array, String jsonKeyField, boolean bpmUserId, String alias);
	
	/**
     * 对结果集JSONArray附上用户信息
     * @author laoqunzhao 2018-05-07
     * @param array JSONArray结果集
     * @param jsonKeyField JSON中存储用户key的字段
     * @param bpmUserId 用户key是不是流程用户的ID
     * @param isArray 用户key存储的是不是数组，是数组则用","进行分割
     * @param alias 用户信息别名前缀，为避免字段冲突可加上前缀
     */
	void appendUserInfo(JSONArray array, String jsonKeyField, boolean bpmUserId, boolean isArray, String alias);


}
