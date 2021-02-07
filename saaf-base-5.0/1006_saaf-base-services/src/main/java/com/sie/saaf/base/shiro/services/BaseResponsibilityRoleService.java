package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheSyncEvent;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityRoleEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseResponsibilityRole;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseResponsibilityRoleService")
public class BaseResponsibilityRoleService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseResponsibilityRoleService.class);
	@Autowired
	private IBaseResponsibilityRole baseResponsibilityRoleServer;

	@Autowired
	private BaseAccreditServer baseAccreditServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseResponsibilityRoleServer;
	}
	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		return super.findPagination(params,pageIndex,pageRows);
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		if(queryParamJSON==null || !queryParamJSON.containsKey("id")){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 id ", 0, null).toString();
		}
		Set<String> roleId=new HashSet<>();
		String[] idArr = queryParamJSON.getString("id").split(";");
		for (String id:idArr){
			BaseResponsibilityRoleEntity_HI obj= baseResponsibilityRoleServer.getById(Integer.valueOf(id));
			if (obj!=null){
				roleId.add(obj.getRoleId()+"");
			}
		}
		Set<Integer> userIds= baseAccreditServer.findRelatedUserId(null,roleId,null,null);
		PermissionCacheSyncEvent event=new PermissionCacheSyncEvent("update",userIds);
		String result=super.delete(params);
		SpringBeanUtil.applicationContext.publishEvent(event);
		return result;
	}
	
	/**
	 * 保存职责角色
	 * @param params 参数<br>
	 *     {<br>
	 *         responsibilityId:职责Id,<br>
	 *         roleIds:[1,2,3,4]角色数组
	 *     }
	 * @author ZhangJun
	 * @createTime 2018/1/8 12:50
	 * @description 保存职责角色
	 */
	@RequestMapping(method = RequestMethod.POST,value="saveRespRole")
	public String saveRespRole(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        this.baseResponsibilityRoleServer.saveRespRole(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
}
