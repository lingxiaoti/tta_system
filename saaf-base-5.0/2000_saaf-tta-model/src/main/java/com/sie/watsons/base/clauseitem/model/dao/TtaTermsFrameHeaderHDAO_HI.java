package com.sie.watsons.base.clauseitem.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.clauseitem.model.entities.TtaTermsFrameHeaderHEntity_HI;
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

@Component("ttaTermsFrameHeaderHDAO_HI")
public class TtaTermsFrameHeaderHDAO_HI extends BaseCommonDAO_HI<TtaTermsFrameHeaderHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderHDAO_HI.class);

	public TtaTermsFrameHeaderHDAO_HI() {
		super();
	}

	//回写
	public Map<String, Object> callOrderReturn(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_CLAUSE_RETURN(?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("Id"));
					call.setInt(2, (Integer) map.get("userId"));
// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);
					call.registerOutParameter(5, Types.VARCHAR);
					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);
					int newId = call.getInt(5);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("newId",newId);
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
