package com.sie.watsons.base.newbreed.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetHeaderEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Component("ttaNewbreedSetHeaderDAO_HI")
public class TtaNewbreedSetHeaderDAO_HI extends BaseCommonDAO_HI<TtaNewbreedSetHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetHeaderDAO_HI.class);

	public TtaNewbreedSetHeaderDAO_HI() {
		super();
	}

	//复制
	public Map<String, Object> callOrderCopy(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_NEWBREED_SET_COPY(?,?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("Id"));
					call.setString(2, (String) map.get("deptCode"));
					call.setInt(3, (Integer) map.get("userId"));
// 设置输出参数
					call.registerOutParameter(4, Types.INTEGER);
					call.registerOutParameter(5, Types.VARCHAR);
					call.registerOutParameter(6, Types.VARCHAR);
					call.execute();
					int xFlag = call.getInt(4);
					String xMsg = call.getString(5);
					String year = call.getString(6);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("pValue",year);
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
