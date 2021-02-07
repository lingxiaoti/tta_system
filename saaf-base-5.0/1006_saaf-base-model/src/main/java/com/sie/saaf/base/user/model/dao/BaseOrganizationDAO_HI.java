package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseOrganizationEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component("baseOrganizationDAO_HI")
public class BaseOrganizationDAO_HI extends BaseCommonDAO_HI<BaseOrganizationEntity_HI> {
	public BaseOrganizationDAO_HI() {
		super();
	}

	/**
	 * 更新DeleteFlag字段值
	 * @param orgIds 主键集合
	 * @param deleteFlag 删除标记
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:45
	 * @description 更新DeleteFlag字段值
	 */
	public void updateDeleteFlag(List<Integer> orgIds, Integer deleteFlag){
		StringBuffer sb = new StringBuffer("update BaseOrganizationEntity_HI set deleteFlag=:deleteFlag where orgId in (:orgIds)");

		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sb.toString());
		query.setParameter("deleteFlag",deleteFlag);
		query.setParameterList("orgIds",orgIds);
		query.executeUpdate();
	}

	/**
	 * 保存库存组织
	 * @author ZhangJun
	 * @createTime 2018/5/2
	 * @description 保存库存组织，由于库存组织目前使用同步机制从ERP同步过来，主键也使用了ERP同步，不能使用Hibernate对象设置主键直接保存，此处先直接使用插入语句的方式
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int saveInvOrg(BaseOrganizationEntity_HI entity){
		StringBuffer sb = new StringBuffer("insert into BASE_ORGANIZATION(");
		sb.append("ORG_ID,PARENT_ORG_ID,ORG_CODE,ORG_NAME,IS_DEP,ORG_TYPE,ORG_LEVEL,IS_LEAF,START_DATE,ENABLED,ORG_PINYIN_NAME,ORG_SIMPLE_PINYIN_NAME,DELETE_FLAG,ORG_HIERARCHY_ID,SOURCE_SYSTEM_ID,CREATION_DATE,CREATED_BY,LAST_UPDATED_BY,LAST_UPDATE_DATE,VERSION_NUM,LAST_UPDATE_LOGIN,ORGANIZATION_ID");
		sb.append(")VALUES(");
		sb.append(":orgId,:parentOrgId,:orgCode,:orgName,:isDep,:orgType,:orgLevel,:isLeaf,:startDate,:enabled,:orgPinyinName,:orgSimplePinyinName,:deleteFlag,:orgHierarchyId,:sourceSystemId,:creationDate,:createBy,:lastUpdateBy,:lastUpdateDate,:versionNum,:lastUpdateLogin,:organizationId");
		sb.append(")");

		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sb.toString());
		query.setParameter("orgId",entity.getOrgId());
		query.setParameter("parentOrgId",entity.getParentOrgId());
		query.setParameter("orgCode",entity.getOrgCode());
		query.setParameter("orgName",entity.getOrgName());
		query.setParameter("isDep",entity.getIsDep());
		query.setParameter("orgType",entity.getOrgType());
		query.setParameter("orgLevel",entity.getOrgLevel());
		query.setParameter("isLeaf",entity.getIsLeaf());
		query.setParameter("startDate",entity.getStartDate());
		query.setParameter("enabled",entity.getEnabled());
		query.setParameter("orgPinyinName",entity.getOrgPinyinName());
		query.setParameter("orgSimplePinyinName",entity.getOrgSimplePinyinName());
		query.setParameter("deleteFlag",entity.getDeleteFlag());
		query.setParameter("orgHierarchyId",entity.getOrgHierarchyId());
		query.setParameter("sourceSystemId",entity.getSourceSystemId());
		query.setParameter("creationDate",new Date());
		query.setParameter("createBy",entity.getOperatorUserId());
		query.setParameter("lastUpdateBy",entity.getOperatorUserId());
		query.setParameter("lastUpdateDate",new Date());
		query.setParameter("versionNum",0);
		query.setParameter("lastUpdateLogin",entity.getOperatorUserId());
		query.setParameter("organizationId",entity.getOrganizationId());

		return query.executeUpdate();
	}
}
