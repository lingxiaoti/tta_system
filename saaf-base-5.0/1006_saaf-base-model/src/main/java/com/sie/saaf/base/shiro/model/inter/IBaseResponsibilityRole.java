package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityRoleEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

/**
 * 接口：职责与角色关联表操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
public interface IBaseResponsibilityRole extends IBaseCommon<BaseResponsibilityRoleEntity_HI> {

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
	JSONArray saveRespRole(JSONObject queryParamJSON);
}
