package com.sie.watsons.base.withdrawal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSupplierItemMidDAO_HI")
public class TtaSupplierItemMidDAO_HI extends BaseCommonDAO_HI<TtaSupplierItemMidEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemMidDAO_HI.class);

	public TtaSupplierItemMidDAO_HI() {
		super();
	}

	/*public void saveOrUpdateExecuteSQL(final String sql) {
		Object result = this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				int executeResult = sqlQuery.executeUpdate();
				return executeResult;
			}
		});
		System.out.println(result);
	}*/
}
