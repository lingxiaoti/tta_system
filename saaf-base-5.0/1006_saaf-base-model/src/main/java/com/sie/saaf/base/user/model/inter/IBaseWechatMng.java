package com.sie.saaf.base.user.model.inter;

import com.sie.saaf.base.user.model.entities.BaseWechatMngEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseWechatMng extends IBaseCommon<BaseWechatMngEntity_HI> {

	/**
	 * 根据用户Id查询公众号列表
	 * @param userId
	 * @return 公众号列表,br>
	 * [{
	 *      wxId:主键Id（更新时必填）,<br>
	 *	    userId:用户Id（必填）,<br>
	 *      wxOpenId:公众号Id（必填）,<br>
	 *      unionId:unionId,<br>
	 *      creationDate:创建时间,<br>
	 *      createdBy:创建人,<br>
	 *		lastUpdatedBy:更新人,<br>
	 *		lastUpdateDate:更新时间,<br>
	 *		versionNum:版本号（更新时必填）,<br>
	 *		operatorUserId:操作者,<br>
	 * }]
	 * @author ZhangJun
	 * @createTime 2017/12/27 14:59
	 * @description 根据用户Id查询公众号列表
	 */
	List<BaseWechatMngEntity_HI> findListByUserId(Integer userId);
}
