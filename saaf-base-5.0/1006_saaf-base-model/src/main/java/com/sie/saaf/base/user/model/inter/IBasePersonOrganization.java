package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonOrganizationEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

/**
 * 人员与组织关系
 * 
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
public interface IBasePersonOrganization extends IBaseCommon<BasePersonOrganizationEntity_HI> {

	/**
	 * 保存组织与人员关系
	 * @param queryParamJSON 保存参数<br>
	 * {<br>
	 *     positionId:职位Id,<br>
	 *     orgIds:组织Id,<br>
	 *     personId:[人员Id数组],<br>
	 *     startDate:生效日期,<br>
	 *     endDate:失效日期<br>
	 * }
	 * @return {@link List<BasePersonOrganizationEntity_HI>}
	 * @author ZhangJun
	 * @createTime 2018/1/21 13:47
	 * @description 保存组织与人员关系
	 */
	List<BasePersonOrganizationEntity_HI> saveOrgPerson(JSONObject queryParamJSON);
}
