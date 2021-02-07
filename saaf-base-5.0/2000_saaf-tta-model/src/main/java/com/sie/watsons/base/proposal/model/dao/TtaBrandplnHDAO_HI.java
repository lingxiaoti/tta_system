package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnHEntity_HI;
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

@Component("ttaBrandplnHDAO_HI")
public class TtaBrandplnHDAO_HI extends BaseCommonDAO_HI<TtaBrandplnHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnHDAO_HI.class);

	public TtaBrandplnHDAO_HI() {
		super();
	}


	//品牌计划计算
	public Map<String, Object> callBrandPlnCount(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_BRANDPLNL_COUNT(?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));

// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);


					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);

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


	//品牌计划生成
	public Map<String, Object> callBrandPlnGen(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_BRANDPLN_GEN(?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));

// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);


					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);

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
