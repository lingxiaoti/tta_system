package com.sie.watsons.base.feedept.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptHEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaFeeDeptHDAO_HI")
public class TtaFeeDeptHDAO_HI extends BaseCommonDAO_HI<TtaFeeDeptHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptHDAO_HI.class);

	public TtaFeeDeptHDAO_HI() {
		super();
	}


	public  Map<String, Object>  callOrder(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_FIT.P_TTA_FEE_DEPT_COPY(?,?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));
					call.setString(3, (String) map.get("dealType"));
// 设置输出参数
					call.registerOutParameter(4, Types.INTEGER);
					call.registerOutParameter(5, Types.VARCHAR);
					call.registerOutParameter(6, Types.INTEGER);

					call.execute();
					int xFlag = call.getInt(4);
					String xMsg = call.getString(5);
					int feedeptId = call.getInt(6);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("feedeptId",feedeptId);
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
