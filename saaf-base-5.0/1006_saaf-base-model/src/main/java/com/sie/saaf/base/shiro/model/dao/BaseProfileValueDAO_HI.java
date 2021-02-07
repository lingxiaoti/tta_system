package com.sie.saaf.base.shiro.model.dao;

import com.sie.saaf.base.shiro.model.entities.BaseProfileValueEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("baseProfileValueDAO_HI")
public class BaseProfileValueDAO_HI extends BaseCommonDAO_HI<BaseProfileValueEntity_HI> {
	public BaseProfileValueDAO_HI() {
		super();
	}

	/**
	 * 更新deleteFlag字段值
	 * @param profileValueIds 主键集合
	 * @param deleteFlag 删除标记
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:45
	 * @description 更新deleteFlag字段值
	 */
	public void updateDeleteFlag(List<Integer> profileValueIds, Integer deleteFlag){
		StringBuffer sb = new StringBuffer("update BaseProfileValueEntity_HI set deleteFlag=:deleteFlag where profileValueId in (:profileValueIds)");

		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sb.toString());
		query.setParameter("deleteFlag",deleteFlag);
		query.setParameterList("profileValueIds",profileValueIds);
		query.executeUpdate();
	}
}
