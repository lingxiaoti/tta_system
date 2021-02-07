package com.sie.saaf.base.shiro.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseRoleResourceEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseRoleResource;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接口：对角色资源表base_role_resource提供CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
@Component("baseRoleResourceServer")
public class BaseRoleResourceServer extends BaseCommonServer<BaseRoleResourceEntity_HI> implements IBaseRoleResource {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRoleResourceServer.class);
//	@Autowired
//	private ViewObject<BaseRoleResourceEntity_HI> baseRoleResourceDAO_HI;

//	public BaseRoleResourceServer() {
//		super();
//	}

	/**
	 * 保存一条记录
	 *
	 * @param queryParamJSON JSON参数 <br>
	 * {<br>
	 * roleResourceId:主键（更新时必填）<br>
	 * resourceId:资源Id标识<br>
	 * roleId:角色Id<br>
	 * resourceValue:值<br>
	 * versionNum:版本号（更新时必填）<br>
	 * }
	 *
	 * @return BaseRoleResourceEntity_HI对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	public BaseRoleResourceEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

}
