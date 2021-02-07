package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseOrgResponsibilityEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IBaseOrgResponsibility extends IBaseCommon<BaseOrgResponsibilityEntity_HI> {
	/**
	 * 保存数据
	 * @param queryParamJSON 参数
	 * {<br>
	 *     responsibilityId:职责Id,<br>
	 *     orgIds:[1,2,3,4]组织Id数组<br>
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/10 11:21
	 * @description 保存数据
	 */
	void saveOrgResp(JSONObject queryParamJSON);
}
