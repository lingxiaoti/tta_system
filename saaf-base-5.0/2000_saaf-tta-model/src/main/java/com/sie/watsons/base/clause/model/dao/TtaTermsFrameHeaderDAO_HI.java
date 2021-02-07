package com.sie.watsons.base.clause.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.clause.model.entities.TtaTermsFrameHeaderEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Component("ttaTermsFrameHeaderDAO_HI")
public class TtaTermsFrameHeaderDAO_HI extends BaseCommonDAO_HI<TtaTermsFrameHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderDAO_HI.class);

	public TtaTermsFrameHeaderDAO_HI() {
		super();
	}

	//变更
	public Map<String, Object> callOrder(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_CLAUSE_COPY(?,?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("Id"));
					call.setInt(2, (Integer) map.get("userId"));
// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);
					call.registerOutParameter(5, Types.VARCHAR);
					call.registerOutParameter(6, Types.VARCHAR);
					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);
					int newId = call.getInt(5);
					int pYear = call.getInt(6);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("newId",newId);
					paramsMap.put("pYear",pYear);
					close(call);
				}
			});
			return paramsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}

	//复制
	public Map<String, Object> callOrderCopy(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_CLAUSE_COPY2(?,?,?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("Id"));
					call.setString(2, (String) map.get("deptCode"));
					call.setInt(3, (Integer) map.get("userId"));
// 设置输出参数
					call.registerOutParameter(4, Types.INTEGER);
					call.registerOutParameter(5, Types.VARCHAR);
					call.registerOutParameter(6, Types.INTEGER);
					call.registerOutParameter(7, Types.VARCHAR);
					call.execute();
					int xFlag = call.getInt(4);
					String xMsg = call.getString(5);
					int newId = call.getInt(6);
					int year = call.getInt(7);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("newId",newId);
					paramsMap.put("year",year);
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
