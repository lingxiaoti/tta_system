package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartment;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersOrganization_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePosition;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmRole;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("actBpmUserServer")
public class ActBpmUserServer implements IActBpmUser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmUserServer.class);
      
    
    @Autowired
    private IActBpmRole bpmRoleServer;
    
    @Autowired
    private IBaseUsers baseUsersServer;
    
    @Autowired
    private IActBpmProcess bpmProcessServer;
	
	@Autowired
    private IActBpmList bpmListServer;
	
	@Autowired
	private IBasePosition basePositionServer;

	@Autowired
	private IBaseDepartment baseDepartmentServer;

	@Autowired
	private IBaseAccreditCache baseAssreditServer;
    
    @Autowired
    private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;
    
    
    /**
     * 根据会话信息获取流程用户ID
     * @param user 会话用户信息
     * @return 流程用户ID
     */
    @Override
    public String getBpmUserId(UserSessionBean user) {
        if(null != user) {
            return ConvertUtil.getPropValue(user, BPM_USER_ID_FIELD).toString();
        }
        return null;
    }
    
    /**
     * 根据流程用户ID获取系统用户ID
     * @param userId 流程用户ID
     * @return 系统用户ID
     */
    @Override
    public Object getSysUserId(String userId) {
    	ActIdUserEntity_RO user = findUserByBpmId(userId);
        return null==user?null: user.getUserId();
    }
        
    /**
     * 根据流程用户ID查询系统用户
     * @author laoqunzhao 2018-05-07
     * @param userId  流程用户ID
     * @return 系统用户
     */
    @Override
    public ActIdUserEntity_RO findUserByBpmId(String userId) {
    	return findUserByUserId(userId, true);
    }

    /**
     * 根据系统用户ID查询系统用户
     * @author laoqunzhao 2018-05-07
     * @param userId  系统用户ID
     * @return 系统用户
     */
    @Override
    public ActIdUserEntity_RO findUserBySysId(Object userId) {
    	return findUserByUserId(userId, false);
    }
    
    /**
     * 根据流程用户ID集合查询用户
     * @author laoqunzhao 2018-05-07
     * @param userIds 流程用户ID集合
     * @return 系统用户集合 List<ActIdUserEntity_RO>
     */
    @Override
    public List<ActIdUserEntity_RO> findUsersByBpmIds(List<String> userIds){
    	return findUsersByUserIds(userIds, true);
    }
    
    /**
     * 根据用户ID集合查询用户
     * @author laoqunzhao 2018-05-07
     * @param userIds 用户ID集合
     * @return 用户集合 List<ActIdUserEntity_RO>
     */
    @Override
    public List<ActIdUserEntity_RO> findUsersBySysIds(List<?> userIds){
    	return findUsersByUserIds(userIds, false);
    }
    
    /**
     * 获取流程角色人员
     * @author laoqunzhao 2018-05-07
     * @param bpmRoleKeys 流程角色KEY
     * @param processInstanceId 流程实例ID
     * @return 用户集合List<ActIdUserEntity_RO>
     */
    @Override
    public List<ActIdUserEntity_RO> findUsersByBpmRoleKeys(List<String> bpmRoleKeys, String processInstanceId){
    	Integer bpmListId = null;
		if(StringUtils.isNotBlank(processInstanceId)) {
			//针对有可能是子流程的情况获取顶层流程实例ID
			processInstanceId = bpmProcessServer.getTopProcessInstanceId(processInstanceId);
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
			if(bpmList != null) {
				bpmListId = bpmList.getListId();
			}
		}
		return findUsersByBpmRoleKeys(bpmRoleKeys, bpmListId);
    }
    
    /**
     * 获取流程角色人员
     * @author laoqunzhao 2018-05-07
     * @param bpmRoleKeys 流程角色KEY
     * @param bpmListId 申请单ID
     * @return 用户集合List<ActIdUserEntity_RO>
     */
    @Override
    public List<ActIdUserEntity_RO> findUsersByBpmRoleKeys(List<String> bpmRoleKeys, Integer bpmListId){
    	if(null == bpmRoleKeys || bpmRoleKeys.isEmpty()) {
    		return null;
    	}
    	List<String> bpmUserIds = new ArrayList<String>();
        //根据流程角色key集合获取流程用户ID
    	List<Object> bpmRoleUserIds = bpmRoleServer.getRoleBpmUserIds(bpmRoleKeys, bpmListId);
        if(null != bpmRoleUserIds && !bpmRoleUserIds.isEmpty()) {
            //将角色中对应的流程用户ID加入到整个流程用户ID中
        	for(Object userId: bpmRoleUserIds) {
        		if(userId == null) {
        			continue;
        		}
        		if(userId instanceof String) {
        			if(!bpmUserIds.contains(userId.toString())) {
                        bpmUserIds.add(userId.toString());
                    }
        		}else if(userId instanceof List) {
        			@SuppressWarnings("unchecked")
					List<String> userIds = (List<String>) userId;
        			for(String userId_ :userIds) {
        				if(!bpmUserIds.contains(userId_)) {
                            bpmUserIds.add(userId_);
                        }
        			}
        		}
            }
        }
    	if(null != bpmUserIds && !bpmUserIds.isEmpty()) {
    		return findUsersByUserIds(bpmUserIds, true);
    	}
		return null;
    }
    
    /**
     * 获取组织机构人员
     * @author laoqunzhao 2018-05-07
     * @param orgIds 组织机构ID集合
     * @return 用户集合List<ActIdUserEntity_RO>
     */
    @Override
    public List<ActIdUserEntity_RO> findUsersByOrgIds(List<?> orgIds){
    	if(null == orgIds || orgIds.isEmpty()) {
    		return null;
    	}
    	List<BaseUsersOrganization_HI_RO> orgUsers = new ArrayList<BaseUsersOrganization_HI_RO>();
    	for(Object orgId:orgIds) {
        	List<BaseUsersOrganization_HI_RO> tmpOrgUsers = baseUsersServer
        			.findBaseUsersByOrgId((Integer)orgId, null, 1, Integer.MAX_VALUE).getData();
        	if(null != tmpOrgUsers && !tmpOrgUsers.isEmpty()) {
        		orgUsers.addAll(tmpOrgUsers);
        	}
        }
    	if(null != orgUsers && !orgUsers.isEmpty()) {
    		List<Integer> userIds = new ArrayList<Integer>();
    		for(BaseUsersOrganization_HI_RO orgUser: orgUsers) {
    			Integer userId = orgUser.getUserId();
    			if(!userIds.contains(userId)) {
    				userIds.add(userId);
                }
    		}
    		return findUsersByUserIds(userIds, false);
    	}
		return null;
    }
    
    /**
     * 获取用户集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param processInstanceId 流程实例ID
     * @return
     */
    @Override
    public List<ActIdUserEntity_RO> findUsers(List<String> bpmUserIds, List<String> bpmRoleKeys, List<?> orgIds, String processInstanceId){
    	Integer bpmListId = null;
		if(StringUtils.isNotBlank(processInstanceId)) {
			//针对有可能是子流程的情况获取顶层流程实例ID
			processInstanceId = bpmProcessServer.getTopProcessInstanceId(processInstanceId);
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
			if(bpmList != null) {
				bpmListId = bpmList.getListId();
			}
		}
		return findUsers(bpmUserIds, bpmRoleKeys, orgIds, bpmListId);
    }
    
    /**
     * 获取用户集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param bpmListId 申请单ID
     * @return
     */
    @Override
    public List<ActIdUserEntity_RO> findUsers(List<String> bpmUserIds, List<String> bpmRoleKeys, List<?> orgIds, Integer bpmListId){
    	List<ActIdUserEntity_RO> users = null;
    	if(null != bpmUserIds && !bpmUserIds.isEmpty()) {
    		users = findUsersByUserIds(bpmUserIds, true);
    	}
    	//根据流程角色key查询用户
    	if(null != bpmRoleKeys && !bpmRoleKeys.isEmpty()) {
    		List<ActIdUserEntity_RO> roleUsers = findUsersByBpmRoleKeys(bpmRoleKeys, bpmListId);
    		if(null != roleUsers && !roleUsers.isEmpty()) {
    			if(null == users) {
    				users = roleUsers;
    			}else {
    				for(ActIdUserEntity_RO roleUser: roleUsers) {
    					if(!users.contains(roleUser)) {
    						users.add(roleUser);
    					}
    				}
    			}
    		}
        }
    	//根据组织机构ID查询用户
        if(null != orgIds && !orgIds.isEmpty()) {
        	List<ActIdUserEntity_RO> orgUsers = findUsersByOrgIds(orgIds);
    		if(null != orgUsers && !orgUsers.isEmpty()) {
    			if(null == users) {
    				users = orgUsers;
    			}else {
    				for(ActIdUserEntity_RO orgUser: orgUsers) {
    					if(!users.contains(orgUser)) {
    						users.add(orgUser);
    					}
    				}
    			}
    		}
        }      
        return users;
    }
    
    /**
     * 获取用户ID集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param processInstanceId 流程实例ID
     * @return
     */
    @Override
    public List<?> findSysUserIds(List<String> bpmUserIds, List<String> bpmRoleKeys, List<?> orgIds, String processInstanceId){
    	List<ActIdUserEntity_RO> users = findUsers(bpmUserIds, bpmRoleKeys, orgIds, processInstanceId);
    	if(null == users || users.isEmpty()) {
    		return null;
    	}
    	List<Object> userIds = new ArrayList<Object>();
    	for(ActIdUserEntity_RO user: users) {
    		if(!userIds.contains(user.getUserId())) {
    			userIds.add(user.getUserId());
    		}
    	}
    	return userIds;
    }
    
    /**
     * 获取用户ID集合
     * @author laoqunzhao 2018-05-07
     * @param bpmUserIds 流程用户ID
     * @param bpmRoleKeys 流程角色KEY
     * @param orgIds 组织架构ID
     * @param bpmListId 申请单ID
     * @return
     */
    @Override
    public List<?> findSysUserIds(List<String> bpmUserIds, List<String> bpmRoleKeys, List<?> orgIds, Integer bpmListId){
    	List<ActIdUserEntity_RO> users = findUsers(bpmUserIds, bpmRoleKeys, orgIds, bpmListId);
    	if(null == users || users.isEmpty()) {
    		return null;
    	}
    	List<Object> userIds = new ArrayList<Object>();
    	for(ActIdUserEntity_RO user: users) {
    		if(!userIds.contains(user.getUserId())) {
    			userIds.add(user.getUserId());
    		}
    	}
    	return userIds;
    }
    
    /**
     * 根据用户ID、职位ID查询用户信息
     * @author laoqunzhao 2018-07-17
     * @param userId 用户ID
	 * @param positionId 职位ID
	 * @param departmentId 部门ID
     * @param bpmUserId 是否是流程用户ID： true:是， false:否
     * @return 用户<ActIdUserEntity_RO>
     */
    @Override
    public ActIdUserEntity_RO findUserByPositionId(Object userId, Integer positionId, Integer departmentId, boolean bpmUserId) {
		if(userId == null){
			return null;
		}
    	ActIdUserEntity_RO user = findUserByUserId( userId, bpmUserId);
    	if(user != null && user.getPersonId() != null) {
    		List<BasePositionPerson_HI_RO> positions = basePositionServer.findPersonPositionOrgRelationByPersonId(Integer.parseInt(user.getPersonId()));
    		if(positions != null && !positions.isEmpty()) {
    			for(BasePositionPerson_HI_RO position : positions) {
    				if(position.getPositionId() != null && position.getPositionId().equals(positionId)) {
    					user.setPositionId(position.getPositionId());
    					user.setPositionName(position.getPositionName());
    					user.setDepartmentId(position.getDeptId());
    					user.setDepartmentCode(position.getDeptCode());
    					user.setDepartmentName(position.getDeptName());
    					user.setJobId(position.getJobId());
    					user.setJobCode(position.getJobCode());
    					user.setJobName(position.getJobName());
    					//查询部门层级

    					break;
    				}
    			}
    		}
    	}
    	//查询部门信息
		if(departmentId != null && !departmentId.equals(user.getDepartmentId())){
    		user.setDepartmentId(departmentId);
			BaseDepartmentEntity_HI department = baseDepartmentServer.getById(departmentId);
			if(department != null){
				user.setDepartmentCode(department.getDepartmentCode());
				user.setDepartmentName(department.getDepartmentName());
			}
		}
		//查询部门层级
		List<BaseDepartmentEntity_HI_RO> departments = findDepartmentsLeavel(user.getDepartmentId());
		user.setDepartments(departments);
    	return user;
    }

	/**
	 * 根据员工ID、职位ID查询用户信息
	 * @author laoqunzhao 2018-09-10
	 * @param personId 员工ID
	 * @param positionId 职位ID
	 * @param departmentId 部门ID
	 * @return 用户<ActIdUserEntity_RO>
	 */
	@Override
	public ActIdUserEntity_RO findUserByPositionIdAndPersonId(Object personId, Integer positionId, Integer departmentId) {
		if(personId == null){
			return null;
		}
		JSONObject paramsJSON = new JSONObject().fluentPut("personId", personId);
		BaseUsersPerson_HI_RO person = baseUsersServer.findUserSessionInfo(paramsJSON);
		if(person == null){
			return null;
		}
		ActIdUserEntity_RO user = person != null? convertToBpmUser(person): new ActIdUserEntity_RO();
		user.setPersonId(String.valueOf(personId));
		user.setPositionId(positionId);
		//查询职位信息
		List<BasePositionPerson_HI_RO> positions = basePositionServer.findPersonPositionOrgRelationByPersonId(Integer.parseInt(personId.toString()));
		if(positions != null && !positions.isEmpty()) {
			for(BasePositionPerson_HI_RO position : positions) {
				if(position.getPositionId() != null && position.getPositionId().equals(positionId)) {
					user.setPositionId(position.getPositionId());
					user.setPersonName(position.getPositionName());
					user.setDepartmentId(position.getDeptId());
					user.setDepartmentCode(position.getDeptCode());
					user.setDepartmentName(position.getDeptName());
					user.setJobId(position.getJobId());
					user.setJobCode(position.getJobCode());
					user.setJobName(position.getJobName());
					break;
				}
			}
		}
		//查询部门信息
		if(departmentId != null && !departmentId.equals(user.getDepartmentId())){
			user.setDepartmentId(departmentId);
			BaseDepartmentEntity_HI department = baseDepartmentServer.getById(departmentId);
			if(department != null){
				user.setDepartmentCode(department.getDepartmentCode());
				user.setDepartmentName(department.getDepartmentName());
			}
		}
		//查询部门层级
		List<BaseDepartmentEntity_HI_RO> departments = findDepartmentsLeavel(user.getDepartmentId());
		user.setDepartments(departments);
		return user;
	}

    
    /**
     * 实现表与用户表关联查询的left join语句
     * @author laoqunzhao 2018-05-07
     * @param field 主表字段
     * @param alias 用户表别名
     * @param bpmUserId 是否通过流程用户ID相应的字段关联
     * @return 用户表left join查询语句
     */
    @Override
    public String getJoinTableSQL(String field, String alias, boolean bpmUserId) {
    	StringBuffer sql = new StringBuffer(" left join " + USER_TABLE + " " + alias);
    	sql.append(" on " + field + "=" + alias + ".");
    	sql.append(bpmUserId?BPM_USER_ID_COLUMN:USER_ID_COLUMN);
    	return sql.toString();
    }
    
    /**
     * 实现用户根据用户名/姓名模糊查询语句
     * @author laoqunzhao 2018-05-07
     * @param alias 用户表别名
     * @param userNameParam 用户名参数语句
     * @param userFullNameParam 姓名参数语句
     * @return 用户名/姓名模糊查询语句
     */
    @Override
    public String getSearchSQL(String alias, String userNameParam, String userFullNameParam) {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" (UPPER(" + alias + "." + USER_NAME_COLUMN +") LIKE " + userNameParam);
    	sql.append(" OR UPPER(" + alias + "." + USER_FULL_NAME_COLUMN +") LIKE " + userFullNameParam + ")");
    	return sql.toString();
    }
    

    /**
     * 对结果集JSONArray附上用户信息
     * @author laoqunzhao 2018-05-07
     * @param array JSONArray结果集
     * @param jsonKeyField JSON中存储用户key的字段
     * @param bpmUserId 用户key是不是流程用户的ID
     * @param alias 用户信息别名前缀，为避免字段冲突可加上前缀
     */    
    @Override
    public void appendUserInfo(JSONArray array, String jsonKeyField, boolean bpmUserId, String alias) {
        appendUserInfo(array, jsonKeyField, bpmUserId, false, alias);
    }
    
    
    /**
     * 对结果集JSONArray附上用户信息
     * @author laoqunzhao 2018-05-07
     * @param array JSONArray结果集
     * @param jsonKeyField JSON中存储用户key的字段
     * @param bpmUserId 用户key是不是流程用户的ID
     * @param isArray 用户key存储的是不是数组，是数组则用","进行分割
     * @param alias 用户信息别名前缀，为避免字段冲突可加上前缀
     */
    @Override
    public void appendUserInfo(JSONArray array, String jsonKeyField, boolean bpmUserId,boolean isArray, String alias) {
        if(null == array || array.isEmpty() || StringUtils.isBlank(jsonKeyField)) {
            return;
        }
        List<String> userIds = ConvertUtil.getJSONArrayField(array, jsonKeyField, isArray);
        List<ActIdUserEntity_RO> userList = null;
        if(null != userIds && !userIds.isEmpty()) {
        	userList  = findUsersByUserIds(userIds, bpmUserId);
        }
        if(null == userList || userList.isEmpty()) {
            return;
        }
        @SuppressWarnings("unchecked")
        Map<String,ActIdUserEntity_RO> userMap = ConvertUtil.listToMap(userList, bpmUserId?BPM_USER_ID_FIELD:USER_ID_FIELD);
        
        //将对象转换为内置JSONArray的一部分
        Map<String,String> fields = new HashMap<String, String>();
        fields.put("userId", "userId");
        fields.put("userName", "userName");
        fields.put("userFullName", "userFullName");
        ConvertUtil.appendToJSONArray(array, userMap, fields, jsonKeyField, isArray?BPM_USER_JSONARRAY_FIELD:null,alias, true);
    }

	/**
	 * 获取用户的OU
	 * @param paramsJSON
	 * {
	 *  优先级 ouId -> orgId -> responsibilityId
	 *  ouId
	 *  orgId
	 *  responsibilityId
	 * }
	 * @param userId
	 * @return ouID
	 */
	@Override
    public Integer getOuId(JSONObject paramsJSON, Integer userId){
		Integer ouId = paramsJSON.containsKey("ouId")? paramsJSON.getInteger("ouId") : null;
		if(ouId == null && StringUtils.isNotBlank(paramsJSON.getString("orgId"))) {
			ouId = paramsJSON.getInteger("orgId");
		}
		if(ouId == null && StringUtils.isNotBlank(paramsJSON.getString("responsibilityId"))) {
			Integer responsibilityId = paramsJSON.getInteger("responsibilityId");
			ProFileBean proFileBean  = baseAssreditServer.getOrg(userId, responsibilityId);
			ouId = proFileBean == null? null : Integer.valueOf(proFileBean.getProfileValue());
		}
		return ouId;
	}

    
    /**
     * 根据用户ID集合查询用户
     * @author laoqunzhao 2018-05-07
     * @param userId 用户ID
     * @param bpmUserId 是否是流程用户ID： true:是， false:否
     * @return 用户<ActIdUserEntity_RO>
     */
    private ActIdUserEntity_RO findUserByUserId(Object userId, boolean bpmUserId){
        try{
        	StringBuffer sql = new StringBuffer(BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);         
        	sql.append(" and " + (bpmUserId?BPM_USER_ID_COLUMN: USER_ID_COLUMN) + " = :userId");
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId", userId);
            List<BaseUsersPerson_HI_RO> list = baseUsersPersonDAO_HI_RO.findList(sql, paramsMap);
            return null == list || list.isEmpty()? null: convertToBpmUser(list.get(0));
        }catch(Exception e){
        	LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 根据用户ID集合查询用户
     * @author laoqunzhao 2018-05-07
     * @param userIds 用户ID集合
     * @param bpmUserId 是否是流程用户ID： true:是， false:否
     * @return 用户集合 List<ActIdUserEntity_RO>
     */
    private List<ActIdUserEntity_RO> findUsersByUserIds(List<?> userIds, boolean bpmUserId){
        try{
        	StringBuffer sql = new StringBuffer(BaseUsersPerson_HI_RO.QUERY_JOIN_PERSON_SQL);         
        	sql.append(" and " + (bpmUserId?BPM_USER_ID_COLUMN: USER_ID_COLUMN) + " in ('" + StringUtils.join(userIds,"','") + "')");
        	List<BaseUsersPerson_HI_RO> list = baseUsersPersonDAO_HI_RO.findList(sql, new HashMap<String,Object>());
        	return convertToBpmUser(list);
        }catch(Exception e){
        	LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 将系统中的用户bean转换成流程用户bean ActIdUserEntity_RO
     * @author laoqunzhao 2018-05-07
     * @param sysUser 系统用户
     * @return 流程用户 ActIdUserEntity_RO
     */
    private ActIdUserEntity_RO convertToBpmUser(BaseUsersPerson_HI_RO sysUser) {
    	if(null == sysUser) {
    		return null;
    	}
    	ActIdUserEntity_RO user = new ActIdUserEntity_RO();
    	user.setId(ConvertUtil.getPropValue(sysUser, BPM_USER_ID_FIELD).toString());
    	BeanUtils.copyProperties(sysUser, user, new String[] {"id"});
		return user;
    }
    
    /**
     * 将系统中的用户bean转换成流程用户bean ActIdUserEntity_RO
     * @author laoqunzhao 2018-05-07
     * @param sysUsers 系统用户
     * @return 流程用户 集合List<ActIdUserEntity_RO>
     */
    private List<ActIdUserEntity_RO> convertToBpmUser(List<BaseUsersPerson_HI_RO> sysUsers) {
    	if(sysUsers == null || sysUsers.isEmpty()) {
    		return null;
    	}
    	List<ActIdUserEntity_RO> users = new ArrayList<ActIdUserEntity_RO>();
    	for(BaseUsersPerson_HI_RO sysUser: sysUsers) {
    		ActIdUserEntity_RO user = new ActIdUserEntity_RO();
        	user.setId(ConvertUtil.getPropValue(sysUser, BPM_USER_ID_FIELD).toString());
        	BeanUtils.copyProperties(sysUser, user, new String[] {"id"});
        	users.add(user);
    	}
		return users;
    }

    private List<BaseDepartmentEntity_HI_RO> findDepartmentsLeavel(Integer departmentId){
		Integer parentDepartmentId = departmentId;
		JSONObject queryParamJSON = new JSONObject();
		List<BaseDepartmentEntity_HI_RO> departments = new ArrayList<BaseDepartmentEntity_HI_RO>();
		while(parentDepartmentId != null){
			queryParamJSON.put("departmentId", parentDepartmentId);
			List<BaseDepartmentEntity_HI_RO> departments_ = baseDepartmentServer.findDeptTreeList(queryParamJSON);
			if(departments_ != null && !departments_.isEmpty()){
				departments.add(departments_.get(0));
				parentDepartmentId = departments_.get(0).getParentDepartmentId();
			}else{
				parentDepartmentId = null;
			}
		}
		return departments;
	}

}
