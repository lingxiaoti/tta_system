package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.watsons.base.report.model.entities.TtaOiReportFieldHeaderEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@Component("ttaOiReportFieldHeaderDAO_HI")
public class TtaOiReportFieldHeaderDAO_HI extends BaseCommonDAO_HI<TtaOiReportFieldHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportFieldHeaderDAO_HI.class);
	private org.springframework.orm.hibernate3.HibernateTemplate hibernateTemplate;
	public TtaOiReportFieldHeaderDAO_HI() {
		super();
	}

	@Resource(name="hibernateTemplete")
	public void setHibernateTemplate3(org.springframework.orm.hibernate3.HibernateTemplate myHibernateTemplace) {
		this.hibernateTemplate = myHibernateTemplace;

	}
	public org.springframework.orm.hibernate3.HibernateTemplate getHibernateTemplate3() {
		return hibernateTemplate;
	}

	//变更
	public  Map<String, Object>  acAmountCount(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			HibernateTemplate hibernateTemplate =  getHibernateTemplate3();
			//Session session = this.getSessionFactory().getCurrentSession();
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_PROPOSAL_AMOUNT(?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pOrderId"));
					call.setInt(2, (Integer) map.get("pYearIn"));
					call.setInt(3, 1);
// 设置输出参数
					call.registerOutParameter(4, Types.INTEGER);
					call.registerOutParameter(5, Types.VARCHAR);

					call.execute();
					int xFlag = call.getInt(4);
					String xMsg = call.getString(5);
					if (xFlag == 1) {
						conn.commit();
					}
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					close(call);
				}
			});
			return paramsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}

	private void close(CallableStatement cs) {
		try {
			if (cs != null) {
				cs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
