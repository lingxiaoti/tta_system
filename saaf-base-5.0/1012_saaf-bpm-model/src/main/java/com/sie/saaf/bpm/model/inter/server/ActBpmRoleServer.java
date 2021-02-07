package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.Variables;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmRoleEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmList;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmRole;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.HibernateHandler;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import io.jsonwebtoken.lang.Assert;
import org.activiti.engine.RuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("actBpmRoleServer")
public class ActBpmRoleServer implements IActBpmRole {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmRoleServer.class);
	
	@Autowired
	private ViewObject<ActBpmRoleEntity_HI> bpmRoleDAO_HI;
	
	@Autowired
	private ViewObject<ActBpmTaskConfigEntity_HI> bpmTaskConfigDAO_HI;
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
    private IActBpmProcess bpmProcessServer;
	
	@Autowired
    private IActBpmList bpmListServer;
	
	@Autowired
	private IActBpmUser bpmUserServer;
	
	

	public ActBpmRoleServer() {
		super();
	}
	
	/**
	 * 根据角色KEY查找角色
	 * @author laoqunzhao 2018-04-27
	 * @param roleKey 角色KEY
	 * @return ActBpmRoleEntity_HI
	 */
	@Override
	public ActBpmRoleEntity_HI getByRoleKey(String roleKey) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("roleKey", roleKey);
        properties.put("deleteFlag", 0);
        List<ActBpmRoleEntity_HI>  list = bpmRoleDAO_HI.findByProperty(properties);
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }
	
	/**
	 * 根据流程KEY查询流程角色
	 * @author laoqunzhao 2018-04-27
     * @param roleKeys 角色KEY集合
     * @return List<ActBpmRoleEntity_HI>
	 */
	@Override
    public List<ActBpmRoleEntity_HI> findByKeys(List<String> roleKeys){
    	if(roleKeys == null || roleKeys.isEmpty()) {
    		return null;
    	}
        StringBuffer query = new StringBuffer("from ActBpmRoleEntity_HI bean where bean.roleKey in ('" + StringUtils.join(roleKeys, "','") + "')");
        query.append(" order by bean.roleId asc ");
        return bpmRoleDAO_HI.findList(query.toString());
    }
	
	/**
	 * 流程角色查询
	 * @author laoqunzhao 2018-04-27
     * @param queryParamJSON
     * {
     * searchKey KEY/名称
	 * processer 用户名
     * deleteFlag 删除标记，1.已删除，0.未删除
     * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return Pagination<ActBpmRoleEntity_HI>
	 */
	@Override
	public Pagination<ActBpmRoleEntity_HI> findRoles(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer query = new StringBuffer("from ActBpmRoleEntity_HI where 1=1");
        SaafToolUtils.parperHbmParam(ActBpmRoleEntity_HI.class, queryParamJSON, "deleteFlag", "deleteFlag", query, paramMap, "=");
        String searchKey = StringUtils.trimToNull(queryParamJSON.getString("searchKey"));
        if(searchKey != null) {
        	searchKey = "%" + searchKey.toUpperCase() + "%";
        	query.append(" and (upper(roleKey) like :roleKey or upper(roleName) like :roleName)");
        	paramMap.put("roleKey", searchKey);
        	paramMap.put("roleName", searchKey);
        }
		String processer = StringUtils.trimToNull(queryParamJSON.getString("processer"));
        if(processer != null){
			query.append(" and handlerExpressionType='ASSIGN' and concat(',', handlerExpression, ',') like :processer");
			paramMap.put("processer", "%," + processer + ",%");
		}
        query.append(" order by roleId asc");
		Pagination<ActBpmRoleEntity_HI> pagination = bpmRoleDAO_HI.findPagination(query.toString(), paramMap, pageIndex, pageRows);
		return pagination;
	}

	/**
	 * 保存角色
	 * @author laoqunzhao 2018-04-27
	 * @param paramsJSON
	 * {
	 * roleId: 主键（自增）,
	 * roleKey: 角色KEY,
	 * roleName: 角色名称,
	 * handlerExpression: 表达式,
	 * handlerExpressionType: 表达式类型："ASSIGN". 指定人，"SQL". SQL语句, "URL". URL服务调用
	 * }
	 * @return 成功true,失败false
	 */
	@Override
	public void save(JSONObject paramsJSON) {
		//将json转换成entity
		ActBpmRoleEntity_HI instance = JSON.parseObject(paramsJSON.toString(), ActBpmRoleEntity_HI.class);
		//根据角色ID及角色KEY判断角色是否存在
		boolean existed = roleExist(instance.getRoleId(), instance.getRoleKey(), instance.getRoleName());
		Assert.isTrue(!existed, "流程角色已存在！");
		instance.setDeleteFlag(0);
		if(instance.getRoleId() != null) {
		    ActBpmRoleEntity_HI entity = bpmRoleDAO_HI.getById(instance.getRoleId());
		    entity.setOperatorUserId(instance.getOperatorUserId());
		    entity.setRoleKey(instance.getRoleKey());
		    entity.setRoleName(instance.getRoleName());
		    entity.setHandlerExpression(instance.getHandlerExpression());
		    entity.setHandlerExpressionType(instance.getHandlerExpressionType());
		    bpmRoleDAO_HI.update(entity);
		}else {
			instance.setDeleteFlag(0);
	        bpmRoleDAO_HI.save(instance);
		}
		LOGGER.info("save bpm role: {}", paramsJSON);
	}

	/**
	 * 标记删除流程角色
	 * @author laoqunzhao 2018-04-27
     * @param paramsJSON
     * {
     * roleIds [] 流程角色ID
     * }
	 */
	@Override
	public void delete(JSONObject paramsJSON) {
		SaafToolUtils.validateJsonParms(paramsJSON, "roleIds");
		JSONArray roleIds = paramsJSON.getJSONArray("roleIds");
	    for(int i=0; i<roleIds.size(); i++){
		    int id = roleIds.getIntValue(i);
			ActBpmRoleEntity_HI instance = bpmRoleDAO_HI.getById(id);
			if(instance != null) {
				Assert.isTrue(!WorkflowConstant.DEFAULT_ROLE_KEY_START_USER.equals(instance.getRoleKey()), "流程内置角色不能删除！");
				Assert.isTrue(!roleInUsing(instance.getRoleKey()), "该流程角色存在于当前流程中，不能删除！");
				instance.setDeleteFlag(1);
				instance.setLastUpdateDate(new Date());
				instance.setOperatorUserId(paramsJSON.getInteger("operatorUserId"));
				bpmRoleDAO_HI.update(instance);
			}
		}
	    LOGGER.info("delete bpm roles: {}", paramsJSON);
	}
	
	/**
	 * 物理删除流程角色
	 * @author laoqunzhao 2018-04-27
     * @param paramsJSON
     * {
     * roleIds [] 流程角色ID
     * }
	 */
	@Override
	public void destory(JSONObject paramsJSON) {
	    JSONArray roleIds = paramsJSON.getJSONArray("roleIds");
	    if(roleIds != null && !roleIds.isEmpty()){
	        for(int i=0; i<roleIds.size(); i++){
	            int id = roleIds.getIntValue(i);
	            bpmRoleDAO_HI.delete(id);
	        }
	    }
	    LOGGER.info("destory bpm roles: ", paramsJSON);
	}
	
	/**
	 * 判断角色是否存在
	 * @author laoqunzhao 2018-04-27
	 * @param roleId 角色ID
	 * @param roleKey 角色KEY
	 * @return 存在true,不存在false
	 */
	@Override
	public boolean roleExist(Integer roleId, String roleKey, String roleName) {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("roleKey", roleKey);
	    properties.put("deleteFlag", 0);
	    List<ActBpmRoleEntity_HI>  list = bpmRoleDAO_HI.findByProperty(properties);
	    if(list != null && !list.isEmpty()) {
	        for(ActBpmRoleEntity_HI role: list) {
	            if(!role.getRoleId().equals(roleId)) {
	                return true;
	            }
	        }
	    }
	    properties.remove("roleKey");
	    properties.put("roleName", roleName);
	    list = bpmRoleDAO_HI.findByProperty(properties);
	    if(list != null && !list.isEmpty()) {
	        for(ActBpmRoleEntity_HI role: list) {
	            if(!role.getRoleId().equals(roleId)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	/**
	 * 根据角色KEY获取相应的流程用户ID
	 * @author laoqunzhao 2018-04-27
	 * @param roleKeys 角色KEY集合
	 * @param processInstanceId 流程实例ID
	 * @return List<String>或List<List<String>> 流程用户ID
	 */
	@Override
	public List<Object> getRoleBpmUserIds(List<String> roleKeys, String processInstanceId){
		Integer bpmListId = null;
		if(StringUtils.isNotBlank(processInstanceId)) {
			//针对有可能是子流程的情况获取顶层流程实例ID
			processInstanceId = bpmProcessServer.getTopProcessInstanceId(processInstanceId);
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(processInstanceId);
			if(bpmList != null) {
				bpmListId = bpmList.getListId();
			}
		}
		return getRoleBpmUserIds(roleKeys, bpmListId);
	}
	
	
	/**
	 * 根据角色KEY获取相应的流程用户ID
	 * @author laoqunzhao 2018-04-27
	 * @param roleKeys 角色KEY集合
	 * @param bpmListId 申请单ID
	 * @return List<String>或List<List<String>>流程用户ID
	 */
	@Override
	public List<Object> getRoleBpmUserIds(List<String> roleKeys, Integer bpmListId){
	    if(roleKeys == null || roleKeys.isEmpty()) {
	        return null;
	    }
	    List<Object> assignList = new ArrayList<Object>();
	    for(String roleKey: roleKeys) {
	    	if(WorkflowConstant.DEFAULT_ROLE_KEY_START_USER.equals(roleKey)){
				assignList.add(getStartUserId(bpmListId));
				continue;
			}
	        ActBpmRoleEntity_HI role = getByRoleKey(roleKey);
	        if(role == null || StringUtils.isBlank(role.getHandlerExpression())
	                || StringUtils.isBlank(role.getHandlerExpressionType())) {
	            continue;
	        }
	        switch(role.getHandlerExpressionType()) {
	            case "ASSIGN":
	                String[] assigns = role.getHandlerExpression().split(",");
	                for(String assign: assigns) {
	                    if(!assignList.contains(assign)) {
	                        assignList.add(assign);
	                    }
	                }
	                break;
	            case "SQL":
	            	try {
	            		List<?> members = (List<?>) executeSQL(role.getHandlerExpression(), bpmListId);
	            		if(members != null && !members.isEmpty()) {
	                        for(Object member: members) {
	                            String assign = null;
	                            if(member.getClass().isArray()) {
	                                assign = ((Object[])member)[0].toString();
	                            }else {
	                                assign = member.toString();
	                            }
	                            if(!assignList.contains(assign)) {
	                                assignList.add(assign);
	                            }
	                        }
	            		}
	            	}catch(Exception e) {
	            		LOGGER.error(e.getMessage(), e);
                    }
	                break;
	            case "URL":
	                List<Object> bpmUserIds = getRoleUsersByUrl(role.getHandlerExpression(), bpmListId);
	                if(bpmUserIds != null && !bpmUserIds.isEmpty()) {
	                	for(Object bpmUserId: bpmUserIds) {
	                		if(!assignList.contains(bpmUserId)) {
	                			assignList.add(bpmUserId);
	 		    		   }
	                	}
	                }
	                break;
	            default:break;
	        }
	    }
        return assignList;
	}

	/**
	 * 批量替换流程角色办理人
	 * @author laoqunzhao 2018-10-27
	 * @param roleIds 流程角色ID[]
	 * @param processerOld 原办理人
	 * @param processerNew 新办理人
	 * @param userId 操作人
	 */
	@Override
	public void updateRoleProcessers(List<Integer>roleIds ,String processerOld, String processerNew, Integer userId){
		if(roleIds == null || roleIds.isEmpty() || StringUtils.isBlank(processerOld) || StringUtils.isBlank(processerNew)){
			return;
		}
		StringBuffer query = new StringBuffer("from ActBpmRoleEntity_HI bean where bean.roleId in (" + StringUtils.join(roleIds, ",") + ")");
		List<ActBpmRoleEntity_HI> roles = bpmRoleDAO_HI.findList(query.toString());
		if(roles == null || roles.isEmpty()){
			return;
		}
		List<ActBpmRoleEntity_HI> newRoles = new ArrayList<>();
		for(ActBpmRoleEntity_HI role : roles){
			if(!"ASSIGN".equals(role.getHandlerExpressionType()) || StringUtils.isBlank(role.getHandlerExpression())
					|| !("," + role.getHandlerExpression() + ",").contains("," + processerOld + ",")){
				continue;
			}
			String assign = role.getHandlerExpression();
			if(StringUtils.equals(assign, processerNew)){
				continue;
			}
			//原办理人已有新办理人
			if(assign.matches("(^" + processerNew + ",.+)|(.+," + processerNew + ",.+)|(.+," + processerNew + "$)")){
				processerNew = "";
			}
			role.setHandlerExpression(assign
					.replaceAll("^" + processerOld + "$", processerNew)
					.replaceAll("^" + processerOld + ",", processerNew + ",")
					.replaceAll("," + processerOld + ",", "," + processerNew + ",")
					.replaceAll("," + processerOld + "$", "," +processerNew)
					.replaceAll(",,", ",")
					.replaceAll("^,", "")
					.replaceAll(",$", ""));
			role.setOperatorUserId(userId);
			newRoles.add(role);
		}
		if(newRoles != null && !newRoles.isEmpty()){
			bpmRoleDAO_HI.update(roles);
		}
	}

	/**
	 * 根据角色url查询流程用户信息
	 * @param url 角色人员查询服务
	 * @param bpmListId 申请单ID
	 * @return List<Object>
	 */
	private List<Object> getRoleUsersByUrl(String url, Integer bpmListId){
		if(StringUtils.isBlank(url) || bpmListId == null){
			return null;
		}
		List<Object> bpmUserIds = new ArrayList<Object>();
		ActBpmListEntity_HI bpmList = bpmListServer.getById(bpmListId);
		LOGGER.info("查询流程角色url:{},bpmList:{}",url,JSON.toJSON(bpmList));
		JSONObject paramsJSON = getUrlParams(bpmList, url);
		if(url.contains("?")){
			url = url.substring(0, url.indexOf("?"));
		}
	    int times = 3;//失败请求次数
	    while(times-- > 0) {
	       try {
	           JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(url, paramsJSON);
			   LOGGER.info("查询流程角色结果:{}",resultJSON);
		       if(resultJSON != null && WorkflowConstant.STATUS_SUCESS.equals(resultJSON.getString("status"))) {
				   bpmUserIds = getUsersInResult(resultJSON);
		    	   break;
		       }
	       }catch(Exception e) {
	    		LOGGER.error(e.getMessage(), e);	
	       }
	   }
	   return bpmUserIds;
	}

	private JSONObject getUrlParams(ActBpmListEntity_HI bpmList, String url){
		JSONObject initParamsJSON = new JSONObject();//流程自带参数
		Map<String, Object> variables = bpmProcessServer.jsonToVariables(bpmList.getVariables());
		initParamsJSON.put("userId", variables.get(Variables.startUserId.name()) != null?variables.get(Variables.startUserId.name()) : bpmList.getCreatedBy());
		initParamsJSON.put("positionId", bpmList.getApplyPositionId()!= null? bpmList.getApplyPositionId():bpmList.getPositionId());
		initParamsJSON.put("personId", bpmList.getApplyPersonId() != null? bpmList.getApplyPersonId() : variables.get(Variables.startPersonId.name()));
		initParamsJSON.put("custAccountId", bpmList.getCustAccountId() != null? bpmList.getCustAccountId() : variables.get(Variables.startCustAccountId.name()));
		initParamsJSON.put("departmentId", bpmList.getDepartmentId() != null? bpmList.getDepartmentId() : variables.get(Variables.startDepartmentId.name()));
		initParamsJSON.put("roleType", bpmList.getRoleType() != null? bpmList.getRoleType() : variables.get(Variables.startRoleType.name()));
		initParamsJSON.put("userType", bpmList.getUserType() != null? bpmList.getUserType() : variables.get(Variables.startUserType.name()));
		JSONObject paramsJSON = new JSONObject();
		Pattern p = Pattern.compile("[\\?|&]([\\w]+)=([^&]*)");
		Pattern p_ = Pattern.compile("#\\{([\\w]+)\\}");
		Matcher m = p.matcher(url);
		Matcher m_ = null;
		while(m.find()) {
			String paramName = m.group(1);
			String paramValue = m.group(2);
			if(StringUtils.isBlank(paramValue)){
				paramsJSON.put(paramName, "");
			}else if(paramValue.matches("\\{.*\\}")){
				//JSON格式
				JSONObject paramsJSON_ = JSONObject.parseObject(paramValue);
				for(String key : paramsJSON_.keySet()){
					if(StringUtils.isNotBlank(paramsJSON_.getString(key))){
						m_ = p_.matcher(paramsJSON_.getString(key));
						if(m_.find()) {
							String variableName = m_.group(1);
							if (initParamsJSON.containsKey(variableName) && StringUtils.isNotBlank(initParamsJSON.getString(variableName))) {
								//从初始参数中取值
								paramsJSON_.put(key, initParamsJSON.getString(variableName));
							} else {
								//从流程变量中取值
								Object value = runtimeService.getVariable(bpmList.getProcessInstanceId(), variableName);
								paramsJSON_.put(key, value);
							}
						}
					}
				}
				paramsJSON.put(paramName, paramsJSON_);
			}else {
				m_ = p_.matcher(paramValue);
				if(m_.find()) {
					String variableName = m_.group(1);
					if (initParamsJSON.containsKey(variableName)) {
						//从初始参数中取值
						paramsJSON.put(paramName, initParamsJSON.getString(variableName));
					} else {
						//从流程变量中取值
						Object value = runtimeService.getVariable(bpmList.getProcessInstanceId(), variableName);
						paramsJSON.put(paramName, value);
					}
				}else{
					paramsJSON.put(paramName, paramValue);
				}
			}
		}
		//为空返回默认参数params
		if(paramsJSON.isEmpty()){
			paramsJSON.put("params", initParamsJSON);
		}
		return paramsJSON;
	}

	private List<Object> getUsersInResult(JSONObject resultJSON){
		if(!resultJSON.containsKey("data") || resultJSON.get("data") == null){
			return null;
		}
		List<Object> bpmUserIds = new ArrayList<Object>();
		if(resultJSON.get("data") instanceof JSONObject){
			JSONObject data = resultJSON.getJSONObject("data");
			String bpmUserId = data.containsKey("mgrUserName")?data.getString("mgrUserName"):data.getString("userName");
			if(StringUtils.isNotBlank(bpmUserId) && !bpmUserIds.contains(bpmUserId)) {
				bpmUserIds.add(bpmUserId);
			}
		}else {
			JSONArray array = resultJSON.getJSONArray("data");
			for(int i=0; array != null && i<array.size(); i++) {
				Object obj = array.get(i);
				if(obj instanceof JSONObject) {
					JSONObject data = array.getJSONObject(i);
					String bpmUserId = data.containsKey("mgrUserName")?data.getString("mgrUserName"):data.getString("userName");
					if(StringUtils.isNotBlank(bpmUserId) && !bpmUserIds.contains(bpmUserId)) {
						bpmUserIds.add(bpmUserId);
					}
				}else if(obj instanceof JSONArray) {
					JSONArray data = array.getJSONArray(i);
					List<String> bpmUserIds_ = new ArrayList<String>();
					for(int j=0; data != null && j<data.size(); j++) {
						JSONObject data_ = data.getJSONObject(j);
						String bpmUserId_ = data_.containsKey("mgrUserName")?data_.getString("mgrUserName"):data_.getString("userName");
						if(bpmUserId_ != null && StringUtils.isNotBlank(bpmUserId_.toString()) && !bpmUserIds_.contains(bpmUserId_)) {
							bpmUserIds_.add(bpmUserId_);
						}
					}
					if(!bpmUserIds_.isEmpty()) {
						bpmUserIds.add(bpmUserIds_);
					}
				}

			}
		}
		return bpmUserIds;
	}
	
	/**
	 * 执行SQL语句返回结果
	 * @author laoqunzhao 2018-04-27
	 * @param sql SQL语句
	 * @param bpmListId 申请单ID
	 * @return 查询结果
	 */
	private List<Object> executeSQL(String sql, Integer bpmListId){
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		ActBpmListEntity_HI bpmList = null;
		if(bpmListId != null) {
			bpmList = bpmListServer.getById(bpmListId);
		}
		//提取参数
		Map<String, Object> params = new HashMap<String, Object>();
		Pattern p = Pattern.compile("=\\s*#\\s*\\{([^\\}]+)\\}");
		Matcher m = p.matcher(sql);
		while(m.find()) {
			String variableName = m.group(1).trim();
			//sql = sql.replaceFirst("=\\s*#\\s*\\{[^\\}]+\\}", "=?");
			//业务主键
			if(variableName.toUpperCase().equals("ID")) {
				params.put(variableName, bpmList == null?null :bpmList.getBusinessKey());
			}else{
				//流程变量
				if(bpmList != null && StringUtils.isNotBlank(bpmList.getProcessInstanceId())) {
					params.put(variableName, runtimeService.getVariable(bpmList.getProcessInstanceId(), variableName));
				}else {
					params.put(variableName, null);
				}
			}
		}
		return (List<Object>) executeSQL(sql, params);
	}
	
	private Object executeSQL(String sql, Map<String, Object> params){
	    final String sql_ = sql;
	    final Map<String, Object> params_ = params;
	    HibernateHandler handler = new HibernateHandler() {
            private static final long serialVersionUID = 1L;
            @Override
            public Object doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql_);  
                if (null != params_ && !params_.isEmpty()) {  
                    for(String paramName: params_.keySet()) {
                        query.setParameter(paramName, params_.get(paramName));
                    }
                }  
                return query.list(); 
            } 
	    };
	    return bpmRoleDAO_HI.executeQuery(handler);
	}
	
	/**
	 * 判断角色是否使用
	 * @author laoqunzhao 2018-07-20
	 * @param roleKey 角色KEY
	 * @return true.是  false.否
	 */
	private boolean roleInUsing(String roleKey) {
		if(StringUtils.isBlank(roleKey)) {
			return false;
		}
		StringBuffer hql = new StringBuffer("select count(*) from ActBpmTaskConfigEntity_HI cfg where cfg.deleteFlag=0");
		hql.append(" and ((cfg.ccRoleKeys = :ccrole1 or cfg.ccRoleKeys like :ccrole2 or cfg.ccRoleKeys like :ccrole3 or cfg.ccRoleKeys like :ccrole4)");
		hql.append(" or exists(select 1 from ActBpmTaskProcesserEntity_HI pcs where configId=cfg.configId");
		hql.append(" and (pcs.processerRoleKeys = :role1 or pcs.processerRoleKeys like :role2 or pcs.processerRoleKeys like :role3 or pcs.processerRoleKeys like :role4)))");
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
    	paramsMap.put("role1", roleKey);
		paramsMap.put("role2", "%," + roleKey);
        paramsMap.put("role3", roleKey + ",%");
        paramsMap.put("role4", "%," + roleKey + ",%");
        paramsMap.put("ccrole1", roleKey);
		paramsMap.put("ccrole2", "%," + roleKey);
        paramsMap.put("ccrole3", roleKey + ",%");
        paramsMap.put("ccrole4", "%," + roleKey + ",%");
        Integer count = bpmTaskConfigDAO_HI.count(hql.toString(), paramsMap);
        return count>0? true: false;
	}

	private String getStartUserId(Integer bpmListId){
		if(bpmListId != null) {
			ActBpmListEntity_HI bpmList = bpmListServer.getById(bpmListId);
			if(bpmList != null && StringUtils.isNotBlank(bpmList.getProcessInstanceId())){
				return bpmProcessServer.getStartUserId(bpmList.getProcessInstanceId());
			}
		}
		return null;
	}
}
