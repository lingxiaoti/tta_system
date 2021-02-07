package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseWechatUsers_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * 微信与用户表关联视图查询
 * @author ZhangJun
 * @createTime 2017-12-27 15:15
 * @description 微信与用户表关联视图查询
 */
@Component("baseWechatUsersDAO_HI_RO")
public class BaseWechatUsersDAO_HI_RO extends DynamicViewObjectImpl<BaseWechatUsers_HI_RO> {

	public BaseWechatUsersDAO_HI_RO() {
		super();
	}
}
