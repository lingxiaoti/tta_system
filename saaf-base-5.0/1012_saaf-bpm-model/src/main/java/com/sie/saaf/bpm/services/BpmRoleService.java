package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmRoleEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmRole;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.bpm.utils.VerifyUtil;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/bpmRoleService")
public class BpmRoleService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmRoleService.class);

	@Autowired
	private IActBpmRole bpmRoleServer;

	@Autowired
	private IActBpmUser bpmUserServer;

	public BpmRoleService() {
		super();
	}
	
	/**
	 * 流程角色查询
	 * @author laoqunzhao 2018-04-27
     * @param params JSONObject
     * searchKey KEY/名称
     * deleteFlag 删除标记，1.已删除，0.未删除
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="findRoles")
	public String findRoles(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
            JSONObject paramJSON = this.parseObject(params);
            if(!paramJSON.containsKey("deleteFlag")) {
                paramJSON.put("deleteFlag", 0);//只查询未删除的数据
            }
            Pagination<ActBpmRoleEntity_HI> pagination = bpmRoleServer.findRoles(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();  
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
	}
	
	/**
	 * 根据流程KEY查询流程角色
	 * @author laoqunzhao 2018-04-27
	 * @param params JSONObject
	 * roleKeys JSONArray 流程角色KEY
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="findByKeys")
    public String findByKeys(@RequestParam String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            JSONArray jsonRoleKeys = paramJSON.getJSONArray("roleKeys");
            List<String> roleKeys = new ArrayList<String>();
            for(int i=0; i<jsonRoleKeys.size(); i++) {
                roleKeys.add(jsonRoleKeys.getString(i));
            }
            List<ActBpmRoleEntity_HI> result = null;
            if(roleKeys!=null && !roleKeys.isEmpty()) {
                result = bpmRoleServer.findByKeys(roleKeys); 
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", null==result?0:result.size(), result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
	
	/**
	 * 保存角色
	 * @author laoqunzhao 2018-04-27
	 * @param params JSON格式entity
	 * roleId: 主键（自增）,
	 * roleKey: 角色KEY,
	 * roleName: 角色名称,
	 * handlerExpression: 表达式,
	 * handlerExpressionType: 表达式类型："ASSIGN"指定人，"SQL"SQL语句，"URL"URL服务调用
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			Integer roleId = paramsJSON.getInteger("roleId");
			String roleKey = paramsJSON.getString("roleKey");
			String roleName = paramsJSON.getString("roleName");
			VerifyUtil.verifyJSON(paramsJSON, "roleKey" ,true, 32, "流程角色KEY");
			VerifyUtil.verifyJSON(paramsJSON, "roleName" ,true, 64, "流程角色名称");
			VerifyUtil.verifyJSON(paramsJSON, "handlerExpressionType" ,true, 16, "表达式类型");
			if(WorkflowConstant.DEFAULT_ROLE_KEY_START_USER.equals(roleKey.toUpperCase())){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "流程内置角色不可修改！", 0, null).toString();
			}
			if("ASSIGN".equals(paramsJSON.getString("handlerExpressionType"))) {
				VerifyUtil.verifyJSON(paramsJSON, "handlerExpression" ,true, 255, "操作人");
			}else {
				VerifyUtil.verifyJSON(paramsJSON, "handlerExpression" ,true, 255, "表达式");
			}
			if(bpmRoleServer.roleExist(roleId, roleKey, roleName)) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "流程角色已存在！", 0, null).toString();
			}
			bpmRoleServer.save(paramsJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 删除流程角色
	 * @author laoqunzhao 2018-04-27
     * @param params
	 * {
     * roleIds JSONArray 流程角色ID
	 * }
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			bpmRoleServer.delete(paramsJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 批量替换流程角色办理人
	 * @author laoqunzhao 2018-04-27
	 * @param params
	 * {
	 * roleIds 流程角色ID[]
	 * processerOld 原办理人
	 * processerNew 新办理人
	 * }
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="updateRoleProcessers")
	public String updateRoleProcessers(@RequestParam String params){
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "roleIds", "processerOld", "processerNew");
			String processerOld = paramsJSON.getString("processerOld");
			String processerNew = paramsJSON.getString("processerNew");
			ActIdUserEntity_RO user = bpmUserServer.findUserByBpmId(processerOld);
			Assert.notNull(user, "用户" + processerOld + "不存在！");
			user = bpmUserServer.findUserByBpmId(processerNew);
			Assert.notNull(user, "用户" + processerNew + "不存在！");
			JSONArray releIdsJSON = paramsJSON.getJSONArray("roleIds");
			List<Integer> roleIds = new ArrayList<>();
			for(int i=0; i<releIdsJSON.size(); i++){
				roleIds.add(releIdsJSON.getInteger(i));
			}
			bpmRoleServer.updateRoleProcessers(roleIds, processerOld, processerNew, super.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	
}
