package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheUpdateEvent;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityRoleEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseUserResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseResponsibilityRole;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 职责与角色关联表操作
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
@Component("baseResponsibilityRoleServer")
public class BaseResponsibilityRoleServer extends BaseCommonServer<BaseResponsibilityRoleEntity_HI> implements IBaseResponsibilityRole {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponsibilityRoleServer.class);
	@Autowired
	private ViewObject<BaseResponsibilityRoleEntity_HI> baseResponsibilityRoleDAO_HI;

	@Autowired
	private ViewObject<BaseUserResponsibilityEntity_HI> baseUserResponsibilityDAO_HI;

//	@Autowired
//	private ApplicationContext applicationContext;

	public BaseResponsibilityRoleServer() {
		super();
	}

	/**
	 * 保存一条记录
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * respRoleId:主键（,更新记录时必填）<br>
	 * responsibilityId:职责Id,<br>
	 * roleId:角色Id,<br>
	 * versionNum:版本号，（更新记录时必填）<br>
	 * }
	 *
	 * @return BaseResponsibilityRoleEntity_HI对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public BaseResponsibilityRoleEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 保存职责角色
	 * @param queryParamJSON JSON参数<br>
	 *     {<br>
	 *         responsibilityId:职责Id,<br>
	 *         roleIds:[1,2,3,4]角色数组
	 *     }
	 * @return {@link BaseResponsibilityRoleEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/1/8 12:49
	 * @description 保存职责角色
	 */
	@Override
	public JSONArray saveRespRole(JSONObject queryParamJSON){
		if(!queryParamJSON.containsKey("responsibilityId")){
			throw new IllegalArgumentException("缺少参数 responsibilityId");
		}
		if(!queryParamJSON.containsKey("roleIds")){
			throw new IllegalArgumentException("缺少参数 roleIds");
		}

		Set<Integer> userIdSet=new HashSet<>();
		Set<String> roleIdSet=new HashSet<>();
		Set<String> deleteRespId=new HashSet<>();

		List<BaseResponsibilityRoleEntity_HI> respRoles = new ArrayList<>();

		Integer responsibilityId = queryParamJSON.getInteger("responsibilityId");
		JSONArray roleIds = queryParamJSON.getJSONArray("roleIds");

		JSONObject queryParam = new JSONObject();
		queryParam.put("responsibilityId",responsibilityId);
		List<BaseResponsibilityRoleEntity_HI> baseResponsibilityRoleEntitys = findList(queryParam);
		if(roleIds.isEmpty()){
			for	(BaseResponsibilityRoleEntity_HI obj:baseResponsibilityRoleEntitys){
				deleteRespId.add(obj.getResponsibilityId()+"");
			}
			//没有传入roleIds，则删除该职责的所有用户
			baseResponsibilityRoleDAO_HI.delete(baseResponsibilityRoleEntitys);
		}else{
			/*-------删除不在roleIds中的原始数据-------*/
			for(BaseResponsibilityRoleEntity_HI userResp : baseResponsibilityRoleEntitys){
				boolean deleteFlag = true;
				for(int i=0;i<roleIds.size();i++){
					Integer roleId = roleIds.getInteger(i);

					if(userResp.getRoleId().intValue()==roleId.intValue()){
						deleteFlag = false;//不删除
						roleIds.remove(roleId);//已有数据从roleIds移除，后面不做插入，保留原数据不动
						respRoles.add(userResp);
						break;
					}
				}
				if(deleteFlag){
					deleteRespId.add(userResp.getResponsibilityId()+"");
					this.deleteById(userResp.getRespRoleId());
				}
			}
			/*-------删除不在roleIds中的原始数据end-------*/
			/*-------插入新数据------*/
			for(int i=0;i<roleIds.size();i++){
				Integer roleId = roleIds.getInteger(i);

				JSONObject enittyJSON = new JSONObject();
				enittyJSON.put("roleId",roleId);
				enittyJSON.put("responsibilityId",responsibilityId);
				enittyJSON.put("operatorUserId",queryParamJSON.getInteger("operatorUserId"));

				BaseResponsibilityRoleEntity_HI newEntity = this.saveOrUpdate(enittyJSON);
				respRoles.add(newEntity);
				roleIdSet.add(newEntity.getRoleId()+"");
			}
			/*-------插入新数据end------*/
		}

		//发布缓存更新事件
		if (deleteRespId.size()>0){
			StringBuilder sql=new StringBuilder("from BaseUserResponsibilityEntity_HI where responsibilityId in ('")
					.append(String.join("','",deleteRespId)).append("')");
			List<BaseUserResponsibilityEntity_HI> list= baseUserResponsibilityDAO_HI.findList(sql);
			for (BaseUserResponsibilityEntity_HI obj:list){
				userIdSet.add(obj.getUserId());
			}
		}
		PermissionCacheUpdateEvent event=new PermissionCacheUpdateEvent("update",roleIdSet,userIdSet);
		SpringBeanUtil.applicationContext.publishEvent(event);
		return JSONArray.parseArray(JSONArray.toJSONString(respRoles));
	}
}
