package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseUserResponsibilityEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

/**
 * 接口：对用户与职责关系表base_user_responsibility操作
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
public interface IBaseUserResponsibility extends IBaseCommon<BaseUserResponsibilityEntity_HI> {

	/**
	 * 保存用户职责<br>
	 * 当actionType=0时：responsibilityIds只有一个数据<br>
	 * 当actionType=1时：userIds只有一个数据
	 * @param queryParamJSON 参数<br>
	 *     {<br>
	 *         responsibilityIds:[1,2,3,4]职责标识,<br>
	 *         userIds:[1,2,3,4]用户标识数组,<br>
	 *         actionType:操作类型,0:职责添加用户，1：用户添加职责<br>
	 *     }
	 * @author ZhangJun
	 * @createTime 2018/1/8 12:39
	 * @description 保存用户职责
	 */
	void saveUserResp(JSONObject queryParamJSON);


    void baseUserResponsibilitySyn(JSONObject queryParamJSON);
}
