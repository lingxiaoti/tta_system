package com.sie.saaf.base.shiro.model.dao;

import com.sie.saaf.base.shiro.model.entities.BaseProfileEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("baseProfileDAO_HI")
public class BaseProfileDAO_HI extends BaseCommonDAO_HI<BaseProfileEntity_HI> {
	public BaseProfileDAO_HI() {
		super();
	}

	/**
	 * 更新DeleteFlag字段值
	 * @param profileIds 主键集合
	 * @param deleteFlag 删除标记
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:45
	 * @description 更新DeleteFlag字段值
	 */
	public void updateDeleteFlag(List<Integer> profileIds, Integer deleteFlag){
		StringBuffer sb = new StringBuffer("update BaseProfileEntity_HI set deleteFlag=:deleteFlag where profileId in (:profileIds)");

		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sb.toString());
		query.setParameter("deleteFlag",deleteFlag);
		query.setParameterList("profileIds",profileIds);
		query.executeUpdate();
	}

}
