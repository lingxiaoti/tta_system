package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseRoleMenuEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseRoleMenu;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("baseRoleMenuServer")
public class BaseRoleMenuServer extends BaseCommonServer<BaseRoleMenuEntity_HI> implements IBaseRoleMenu {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRoleMenuServer.class);
//	@Autowired
//	private ViewObject<BaseRoleMenuEntity_HI> baseRoleMenuDAO_HI;

	public BaseRoleMenuServer() {
		super();
	}

	/**
	 * 保存一条记录
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 * @param queryParamJSON JSON参数<br>
	 *     {<br>
	 *         roleMenuId:主键（更新数据时必须传入）<br>
	 *         menuId:菜单Id<br>
	 *         roleId:角色Id<br>
	 *         versionNum:版本号（更新数据必须传入）<br>
	 *         operatorUserId:操作者<br>
	 *     }
	 * @return BaseRoleMenuEntity_HI对象
	 */
	@Override
	public BaseRoleMenuEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return saveOrUpdate(queryParamJSON);
	}

}
